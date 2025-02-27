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

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import ClayForm from '@clayui/form';
import ClayModal, {ClayModalProvider, useModal} from '@clayui/modal';
import {Observer} from '@clayui/modal/lib/types';
import {API, Input, Toggle} from '@liferay/object-js-components-web';
import React, {useEffect, useState} from 'react';

import {defaultLanguageId} from '../../utils/constants';
import {toCamelCase} from '../../utils/string';
import PicklistDefaultValueSelect from './DefaultValueFields/PicklistDefaultValueSelect';
import ObjectFieldFormBase from './ObjectFieldFormBase';
import {useObjectFieldForm} from './useObjectFieldForm';

import './AddObjectField.scss';

interface IModal extends IProps {
	observer: Observer;
	onClose: () => void;
}

interface IProps {
	apiURL: string;
	creationLanguageId: Liferay.Language.Locale;
	objectDefinitionExternalReferenceCode: string;
	objectFieldTypes: ObjectFieldType[];
	objectName: string;
}

function ModalAddObjectField({
	apiURL,
	creationLanguageId,
	objectDefinitionExternalReferenceCode,
	objectFieldTypes,
	objectName,
	observer,
	onClose,
}: IModal) {
	const [error, setError] = useState<string>('');
	const [objectDefinition, setObjectDefinition] = useState<
		ObjectDefinition
	>();

	const initialValues: Partial<ObjectField> = {
		indexed: true,
		indexedAsKeyword: false,
		indexedLanguageId: null,
		listTypeDefinitionExternalReferenceCode: '',
		listTypeDefinitionId: 0,
		readOnly: 'false',
		readOnlyConditionExpression: '',
		required: false,
	};

	const onSubmit = async (field: Partial<ObjectField>) => {
		if (
			field.businessType === 'Aggregation' ||
			field.businessType === 'Formula'
		) {
			field.readOnly = 'true';
			delete field.readOnlyConditionExpression;
		}

		if (!Liferay.FeatureFlags['LPS-170122']) {
			delete field.readOnly;
			delete field.readOnlyConditionExpression;
		}

		if (field.label) {
			field = {
				...field,
				name:
					field.name ||
					toCamelCase(field.label[defaultLanguageId] as string, true),
			};

			delete field.listTypeDefinitionId;

			try {
				await API.save(apiURL, field, 'POST');

				onClose();
				window.location.reload();
			}
			catch (error) {
				setError((error as Error).message);
			}
		}
	};

	const {
		errors,
		handleChange,
		handleSubmit,
		setValues,
		values,
	} = useObjectFieldForm({
		initialValues,
		onSubmit,
	});

	const showEnableTranslationToggle =
		values.businessType === 'LongText' ||
		values.businessType === 'RichText' ||
		values.businessType === 'Text';

	useEffect(() => {
		if (!objectDefinition) {
			const makeFetch = async () => {
				const objectDefinitionResponse = await API.getObjectDefinitionByExternalReferenceCode(
					objectDefinitionExternalReferenceCode
				);

				setObjectDefinition(objectDefinitionResponse);
			};

			makeFetch();
		}

		if (Liferay.FeatureFlags['LPS-172017']) {
			if (
				objectDefinition?.enableLocalization &&
				showEnableTranslationToggle
			) {
				setValues({
					localized: true,
				});

				return;
			}

			setValues({
				localized: false,
			});

			return;
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [values.businessType]);

	return (
		<ClayModal observer={observer}>
			<ClayForm onSubmit={handleSubmit}>
				<ClayModal.Header>
					{Liferay.Language.get('new-field')}
				</ClayModal.Header>

				<ClayModal.Body>
					{error && (
						<ClayAlert displayType="danger">{error}</ClayAlert>
					)}

					<Input
						error={errors.label}
						label={Liferay.Language.get('label')}
						name="label"
						onChange={({target: {value}}) => {
							setValues({label: {[defaultLanguageId]: value}});
						}}
						required
						value={values.label?.[defaultLanguageId]}
					/>

					<ObjectFieldFormBase
						errors={errors}
						handleChange={handleChange}
						objectDefinition={objectDefinition}
						objectDefinitionExternalReferenceCode={
							objectDefinitionExternalReferenceCode
						}
						objectField={values}
						objectFieldTypes={objectFieldTypes}
						objectName={objectName}
						setValues={setValues}
					>
						{Liferay.FeatureFlags['LPS-172017'] &&
							showEnableTranslationToggle && (
								<div className="lfr-objects-add-object-field-enable-translations-toggle">
									<Toggle
										disabled={
											!objectDefinition?.enableLocalization
										}
										label={Liferay.Language.get(
											'enable-entry-translations'
										)}
										onToggle={(localized) =>
											setValues({
												localized,
												required: Liferay.FeatureFlags[
													'LPS-172017'
												]
													? !localized &&
													  values.required
													: values.required,
											})
										}
										toggled={values.localized}
										tooltip={Liferay.Language.get(
											'users-will-be-able-to-add-translations-for-the-entries-of-this-field'
										)}
									/>
								</div>
							)}
					</ObjectFieldFormBase>

					{values.state && (
						<PicklistDefaultValueSelect
							creationLanguageId={creationLanguageId}
							defaultValue={
								values.objectFieldSettings?.find(
									(setting) => setting.name === 'defaultValue'
								)?.value
							}
							error={errors.defaultValue}
							label={Liferay.Language.get('default-value')}
							required
							setValues={setValues}
							values={values}
						/>
					)}
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

							<ClayButton type="submit">
								{Liferay.Language.get('save')}
							</ClayButton>
						</ClayButton.Group>
					}
				/>
			</ClayForm>
		</ClayModal>
	);
}

export default function AddObjectField({
	apiURL,
	creationLanguageId,
	objectDefinitionExternalReferenceCode,
	objectFieldTypes,
	objectName,
}: IProps) {
	const [isVisible, setVisibility] = useState<boolean>(false);
	const {observer, onClose} = useModal({onClose: () => setVisibility(false)});

	useEffect(() => {
		Liferay.on('addObjectField', () => setVisibility(true));

		return () => Liferay.detach('addObjectField');
	}, []);

	const applyFeatureFlag = () => {
		return objectFieldTypes.filter((objectFieldType) => {
			if (!Liferay.FeatureFlags['LPS-164948']) {
				return objectFieldType.businessType !== 'Formula';
			}
		});
	};

	return (
		<ClayModalProvider>
			{isVisible && (
				<ModalAddObjectField
					apiURL={apiURL}
					creationLanguageId={creationLanguageId}
					objectDefinitionExternalReferenceCode={
						objectDefinitionExternalReferenceCode
					}
					objectFieldTypes={
						!Liferay.FeatureFlags['LPS-164948']
							? applyFeatureFlag()
							: objectFieldTypes
					}
					objectName={objectName}
					observer={observer}
					onClose={onClose}
				/>
			)}
		</ClayModalProvider>
	);
}
