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

import {LAYOUT_DATA_ITEM_TYPES} from '../config/constants/layoutDataItemTypes';
import {formIsRestricted} from './formIsRestricted';

export function hasRestrictedParent(item, layoutData, restrictedItemIds) {
	if (item.type === LAYOUT_DATA_ITEM_TYPES.form) {
		return formIsRestricted(item);
	}

	if (item.type === LAYOUT_DATA_ITEM_TYPES.collection) {
		return restrictedItemIds.has(item.itemId);
	}

	const parent = layoutData?.items?.[item.parentId];

	if (!parent) {
		return false;
	}

	return hasRestrictedParent(parent, layoutData, restrictedItemIds);
}
