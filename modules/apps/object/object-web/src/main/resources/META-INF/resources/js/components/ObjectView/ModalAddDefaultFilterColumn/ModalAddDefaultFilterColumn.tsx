/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import ClayButton from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import ClayForm from '@clayui/form';
import ClayModal from '@clayui/modal';
import {fetch} from 'frontend-js-web';
import React, {
	FormEvent,
	useCallback,
	useContext,
	useEffect,
	useMemo,
	useState,
} from 'react';

import {defaultLanguageId} from '../../../utils/locale';
import AutoComplete from '../../Form/AutoComplete';
import {CheckboxItem} from '../../Form/CheckBoxItem';
import CustomSelect from '../../Form/CustomSelect/CustomSelect';
import Input from '../../Form/Input';
import ViewContext, {TYPES} from '../context';
import {
	TLabelValueObject,
	TName,
	TObjectField,
	TWorkflowStatus,
} from '../types';

const headers = new Headers({
	'Accept': 'application/json',
	'Content-Type': 'application/json',
});

const PICKLIST_OPERATORS: TLabelValueObject[] = [
	{
		label: Liferay.Language.get('includes'),
		value: 'includes',
	},
	{
		label: Liferay.Language.get('excludes'),
		value: 'excludes',
	},
];

export function ModalAddDefaultFilterColumn({
	editingFilter,
	editingObjectFieldName,
	header,
	observer,
	onClose,
}: IProps) {
	const [
		{
			objectFields,
			objectView: {objectViewFilterColumns},
			workflowStatusJSONArray,
		},
		dispatch,
	] = useContext(ViewContext);

	const [availableFields, setAvailableFields] = useState(objectFields);

	const [items, setItems] = useState<IItem[]>([]);

	const [selectedFilterBy, setSelectedFilterBy] = useState<TObjectField>();

	const [selectedFilterType, setSelectedFilterType] = useState<
		TLabelValueObject
	>();

	const [query, setQuery] = useState<string>('');

	const [active, setActive] = useState(false);

	const filteredAvailableFields = useMemo(() => {
		return availableFields.filter(({label}) => {
			return label[defaultLanguageId]
				.toLowerCase()
				.includes(query.toLowerCase());
		});
	}, [availableFields, query]);

	const getCheckedWokflowStatusItems = (
		itemValues: TWorkflowStatus[]
	): IItem[] => {
		let newItemsValues: IItem[] = [];

		objectViewFilterColumns.map((objectViewFilterColumn) => {
			const definition = objectViewFilterColumn.definition;

			const valuesArray = definition[objectViewFilterColumn.filterType];

			const [editingFilterType] = PICKLIST_OPERATORS.filter(
				(filterType) =>
					filterType.value === objectViewFilterColumn.filterType
			);

			setSelectedFilterType({
				label: editingFilterType?.label,
				value: editingFilterType?.value,
			});

			newItemsValues = itemValues.map((itemValue) => {
				const item = {
					checked: false,
					label: itemValue.label,
					value: itemValue.value,
				};

				if (valuesArray.includes(itemValue.value)) {
					item.checked = true;
				}

				return item;
			});
		});

		return newItemsValues;
	};

	const getCheckedPickListItems = (itemValues: TPickListValue[]): IItem[] => {
		let newItemsValues: IItem[] = [];

		objectViewFilterColumns.map((objectViewFilterColumn) => {
			const definition = objectViewFilterColumn.definition;

			const valuesArray = definition[objectViewFilterColumn.filterType];

			const [editingFilterType] = PICKLIST_OPERATORS.filter(
				(filterType) =>
					filterType.value === objectViewFilterColumn.filterType
			);

			setSelectedFilterType({
				label: editingFilterType.label,
				value: editingFilterType.value,
			});

			newItemsValues = itemValues.map((itemValue) => {
				const item = {
					checked: false,
					label: itemValue.name,
					value: itemValue.key,
				};

				if (valuesArray.includes(itemValue.key)) {
					item.checked = true;
				}

				return item;
			});
		});

		return newItemsValues;
	};

	const setFieldValues = useCallback(
		(objectField: TObjectField) => {
			if (objectField?.businessType === 'Picklist') {
				const makeFetch = async () => {
					const response = await fetch(
						`/o/headless-admin-list-type/v1.0/list-type-definitions/${objectField.listTypeDefinitionId}/list-type-entries`,
						{
							headers,
							method: 'GET',
						}
					);

					const {items = []} = (await response.json()) as {
						items?: TPickListValue[];
					};

					if (editingFilter) {
						setItems(getCheckedPickListItems(items));
					}
					else {
						setItems(
							items.map((item) => {
								return {
									label: item.name,
									value: item.key,
								};
							})
						);
					}
				};

				makeFetch();
			}
			else {
				let newItems: IItem[] = [];

				if (editingFilter) {
					newItems = getCheckedWokflowStatusItems(
						workflowStatusJSONArray
					);
				}
				else {
					newItems = workflowStatusJSONArray.map((worflowStatus) => {
						return {
							label: worflowStatus.label,
							value: worflowStatus.value,
						};
					});
				}

				setItems(newItems);
			}
		},
		// eslint-disable-next-line react-hooks/exhaustive-deps
		[]
	);

	useEffect(() => {
		const filteredPickListFields = objectFields.filter((objectField) => {
			if (
				(objectField.businessType === 'Picklist' ||
					objectField.name === 'status') &&
				!objectField.hasFilter
			) {
				return objectField;
			}
		});

		setAvailableFields(filteredPickListFields);
	}, [objectFields]);

	useEffect(() => {
		if (!selectedFilterBy) {
			setItems([]);
		}
		else {
			setFieldValues(selectedFilterBy);
		}
	}, [setFieldValues, selectedFilterBy, workflowStatusJSONArray]);

	useEffect(() => {
		if (editingFilter) {
			const objectField = objectFields.find((objectField) => {
				if (objectField.name === editingObjectFieldName) {
					return objectField;
				}
			});

			objectField && setFieldValues(objectField);
		}

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [editingFilter]);

	const onSubmit = (event: FormEvent) => {
		event.preventDefault();

		const checkedItems = items.filter((item) => item.checked);

		if (editingFilter) {
			dispatch({
				payload: {
					filterType: selectedFilterType?.value,
					objectFieldName: editingObjectFieldName,
					valueList: checkedItems,
				},
				type: TYPES.EDIT_OBJECT_VIEW_FILTER_COLUMN,
			});
		}
		else {
			dispatch({
				payload: {
					filterType: selectedFilterType?.value,
					objectFieldName: selectedFilterBy?.name,
					valueList: checkedItems,
				},
				type: TYPES.ADD_OBJECT_VIEW_FILTER_COLUMN,
			});
		}

		onClose();
	};

	return (
		<ClayModal observer={observer}>
			<ClayForm onSubmit={onSubmit}>
				<ClayModal.Header>{header}</ClayModal.Header>

				<ClayModal.Body>
					{!editingFilter && (
						<AutoComplete
							emptyStateMessage="there-are-no-columns-available"
							items={filteredAvailableFields}
							label={Liferay.Language.get('filter-by')}
							onChangeQuery={setQuery}
							onSelectItem={setSelectedFilterBy}
							query={query}
							required
							value={selectedFilterBy?.label[defaultLanguageId]}
						>
							{({label}) => (
								<div className="d-flex justify-content-between">
									<div>{label[defaultLanguageId]}</div>
								</div>
							)}
						</AutoComplete>
					)}

					<CustomSelect
						label={Liferay.Language.get('filter-type')}
						onChange={(target: TLabelValueObject) =>
							setSelectedFilterType(target)
						}
						options={PICKLIST_OPERATORS}
						value={selectedFilterType?.label}
					/>

					<ClayDropDown
						active={active}
						onActiveChange={setActive}
						trigger={
							<Input
								label={Liferay.Language.get('value')}
								placeholder={Liferay.Language.get(
									'choose-an-option'
								)}
								value={items
									.reduce<string[]>((acc, value) => {
										if (value.checked) {
											acc.push(value.label);
										}

										return acc;
									}, [])
									.join(', ')}
							/>
						}
					>
						<ClayDropDown.ItemList>
							{items.map(({checked, label, value}) => (
								<CheckboxItem
									checked={checked}
									key={value}
									label={label}
									onChange={({target: {checked}}) => {
										setItems(
											items.map((item) =>
												item.label === label
													? {
															...item,
															checked,
													  }
													: item
											)
										);
									}}
								/>
							))}
						</ClayDropDown.ItemList>
					</ClayDropDown>
				</ClayModal.Body>

				<ClayModal.Footer
					last={
						<ClayButton.Group spaced>
							<ClayButton
								displayType="secondary"
								onClick={() => onClose()}
							>
								{Liferay.Language.get('cancel')}
							</ClayButton>

							<ClayButton
								disabled={
									!selectedFilterBy && !editingObjectFieldName
								}
								displayType="primary"
								type="submit"
							>
								{Liferay.Language.get('save')}
							</ClayButton>
						</ClayButton.Group>
					}
				/>
			</ClayForm>
		</ClayModal>
	);
}

interface IProps {
	editingFilter: boolean;
	editingObjectFieldName: string;
	header: string;
	observer: any;
	onClose: () => void;
}
interface IItem extends TLabelValueObject {
	checked?: boolean;
}

type TPickListValue = {
	dateCreated: string;
	dateModified: number;
	id: number;
	key: string;
	name: string;
	name_i18n: TName;
	type: string;
};
