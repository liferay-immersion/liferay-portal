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

import {ClayButtonWithIcon} from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import ClayList from '@clayui/list';
import classNames from 'classnames';
import React, {useContext, useRef, useState} from 'react';
import {useDrag, useDrop} from 'react-dnd';

import ViewContext, {TYPES} from '../context';

import './BuilderListItem.scss';

interface IProps {
	aliasColumnText?: string;
	defaultFilter?: boolean;
	defaultSort?: boolean;
	hasDragAndDrop?: boolean;
	index: number;
	label?: string;
	objectFieldName: string;
	onEditing?: (boolean: boolean) => void;
	onEditingObjectFieldName?: (objectFieldName: string) => void;
	onVisibleEditModal?: (boolean: boolean) => void;
	thirdColumnValues?: TThirdColumnValues[];
}

type TThirdColumnValues = {
	label: string;
	value: string;
};

type TItemHover = {
	index: number;
	type: string;
};

type TDraggedOffset = {
	x: number;
	y: number;
} | null;

const BuilderListItem: React.FC<IProps> = ({
	aliasColumnText,
	defaultFilter,
	defaultSort,
	hasDragAndDrop,
	index,
	label,
	objectFieldName,
	onEditing,
	onEditingObjectFieldName,
	onVisibleEditModal,
	thirdColumnValues,
}) => {
	const [active, setActive] = useState<boolean>(false);
	const [_, dispatch] = useContext(ViewContext);

	const ref = useRef<HTMLLIElement>(null);

	const [{isDragging}, dragRef] = useDrag({
		collect: (monitor) => ({
			isDragging: monitor.isDragging(),
		}),
		item: {
			index,
			type: 'FIELD',
		},
	});

	const [, dropRef] = useDrop({
		accept: 'FIELD',
		hover(item: TItemHover, monitor) {
			if (!ref.current) {
				return;
			}

			const draggedIndex = item.index;
			const targetIndex = index;

			if (draggedIndex === targetIndex) {
				return;
			}

			const targetSize = ref.current.getBoundingClientRect();
			const targetCenter = (targetSize.bottom - targetSize.top) / 2;

			const draggedOffset: TDraggedOffset = monitor.getClientOffset();

			if (!draggedOffset) {
				return;
			}

			const draggedTop = draggedOffset.y - targetSize.top;

			if (
				(draggedIndex < targetIndex && draggedTop < targetCenter) ||
				(draggedIndex > targetIndex && draggedTop > targetCenter)
			) {
				return;
			}

			dispatch({
				payload: {draggedIndex, targetIndex},
				type: defaultSort
					? TYPES.CHANGE_OBJECT_VIEW_SORT_COLUMN_ORDER
					: TYPES.CHANGE_OBJECT_VIEW_COLUMN_ORDER,
			});

			item.index = targetIndex;
		},
	});

	const handleDeleteColumn = (
		objectFieldName: string,
		defaultFilter?: boolean,
		defaultSort?: boolean
	) => {
		if (defaultSort) {
			dispatch({
				payload: {objectFieldName},
				type: TYPES.DELETE_OBJECT_VIEW_SORT_COLUMN,
			});
		}
		else if (defaultFilter) {
			dispatch({
				payload: {objectFieldName},
				type: TYPES.DELETE_OBJECT_VIEW_FILTER_COLUMN,
			});
		}
		else {
			dispatch({
				payload: {objectFieldName},
				type: TYPES.DELETE_OBJECT_VIEW_COLUMN,
			});

			dispatch({
				payload: {objectFieldName},
				type: TYPES.DELETE_OBJECT_VIEW_SORT_COLUMN,
			});
		}
	};

	dragRef(dropRef(ref));

	const handleEnableEditModal = (objectFieldName: string) => {
		onEditingObjectFieldName && onEditingObjectFieldName(objectFieldName);
		onEditing && onEditing(true);
		onVisibleEditModal && onVisibleEditModal(true);
	};

	return (
		<ClayList.Item
			className={classNames(
				'lfr-object__object-custom-view-builder-item',
				{
					'lfr-object__object-custom-view-builder-item--dragging': isDragging,
				}
			)}
			flex
			ref={hasDragAndDrop ? ref : null}
		>
			{hasDragAndDrop && (
				<ClayList.ItemField>
					<ClayButtonWithIcon displayType={null} symbol="drag" />
				</ClayList.ItemField>
			)}

			<ClayList.ItemField
				className={classNames({
					'lfr-object__object-builder-list-item-first-column--not-draggable': !hasDragAndDrop,
				})}
				expand
			>
				<ClayList.ItemTitle>{label}</ClayList.ItemTitle>
			</ClayList.ItemField>

			<ClayList.ItemField
				className={classNames({
					'lfr-object__object-builder-list-item-second-column': hasDragAndDrop,
					'lfr-object__object-builder-list-item-second-column--not-draggable': !hasDragAndDrop,
				})}
				expand
			>
				<ClayList.ItemText>{aliasColumnText}</ClayList.ItemText>
			</ClayList.ItemField>

			<ClayList.ItemField
				className={classNames({
					'lfr-object__object-builder-list-item-third-column--not-draggable': !hasDragAndDrop,
				})}
				expand
			>
				<ClayList.ItemText>
					{thirdColumnValues?.map((value, index) => {
						return index !== thirdColumnValues.length - 1
							? `${value.label}, `
							: value.label;
					})}
				</ClayList.ItemText>
			</ClayList.ItemField>

			<ClayDropDown
				active={active}
				menuElementAttrs={{
					className: 'lfr-object__object-builder-list-item-dropdown',
				}}
				onActiveChange={setActive}
				trigger={
					<ClayButtonWithIcon
						displayType="unstyled"
						symbol="ellipsis-v"
					/>
				}
			>
				<ClayDropDown.ItemList>
					<ClayDropDown.Item
						onClick={() => handleEnableEditModal(objectFieldName)}
					>
						<ClayIcon
							className="lfr-object__object-custom-view-builder-item-icon"
							symbol="pencil"
						/>

						{Liferay.Language.get('edit')}
					</ClayDropDown.Item>

					<ClayDropDown.Item
						onClick={() =>
							handleDeleteColumn(
								objectFieldName,
								defaultFilter,
								defaultSort
							)
						}
					>
						<ClayIcon
							className="lfr-object__object-custom-view-builder-item-icon"
							symbol="trash"
						/>

						{Liferay.Language.get('delete')}
					</ClayDropDown.Item>
				</ClayDropDown.ItemList>
			</ClayDropDown>
		</ClayList.Item>
	);
};

export default BuilderListItem;
