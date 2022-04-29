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

/// <reference types="react" />

import {TObjectColumn} from '../types';
import './BuilderScreen.scss';
interface IProps {
	defaultFilter?: boolean;
	defaultSort?: boolean;
	emptyState: {
		buttonText: string;
		description: string;
		title: string;
	};
	firstColumnHeader: string;
	hasDragAndDrop?: boolean;
	objectColumns: TObjectColumn[];
	onEditing?: (boolean: boolean) => void;
	onEditingObjectFieldName?: (objectFieldName: string) => void;
	onVisibleEditModal: (boolean: boolean) => void;
	onVisibleModal: (boolean: boolean) => void;
	secondColumnHeader: string;
	thirdColumnHeader?: string;
	title: string;
}
export declare function BuilderScreen({
	defaultFilter,
	defaultSort,
	emptyState,
	firstColumnHeader,
	hasDragAndDrop,
	objectColumns,
	onEditing,
	onEditingObjectFieldName,
	onVisibleEditModal,
	onVisibleModal,
	secondColumnHeader,
	thirdColumnHeader,
	title,
}: IProps): JSX.Element;
export {};
