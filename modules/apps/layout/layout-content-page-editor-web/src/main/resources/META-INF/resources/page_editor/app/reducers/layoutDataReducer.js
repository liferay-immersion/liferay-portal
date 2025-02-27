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

import {
	ADD_FRAGMENT_ENTRY_LINKS,
	ADD_ITEM,
	DELETE_ITEM,
	DUPLICATE_ITEM,
	MOVE_ITEM,
	UPDATE_COLLECTION_DISPLAY_COLLECTION,
	UPDATE_COL_SIZE,
	UPDATE_FORM_ITEM_CONFIG,
	UPDATE_FRAGMENT_ENTRY_LINK_CONFIGURATION,
	UPDATE_ITEM_CONFIG,
	UPDATE_ITEM_LOCAL_CONFIG,
	UPDATE_PREVIEW_IMAGE,
	UPDATE_ROW_COLUMNS,
} from '../actions/types';
import {setIn} from '../utils/setIn';

export const INITIAL_STATE = {
	items: {},
};

export default function layoutDataReducer(layoutData = INITIAL_STATE, action) {
	switch (action.type) {
		case UPDATE_COL_SIZE:
		case UPDATE_COLLECTION_DISPLAY_COLLECTION:
		case ADD_FRAGMENT_ENTRY_LINKS:
		case ADD_ITEM:
		case DELETE_ITEM:
		case DUPLICATE_ITEM:
		case MOVE_ITEM:
		case UPDATE_FRAGMENT_ENTRY_LINK_CONFIGURATION:
		case UPDATE_ROW_COLUMNS:
			return action.layoutData;

		case UPDATE_FORM_ITEM_CONFIG:
		case UPDATE_ITEM_CONFIG: {
			const {itemId, layoutData: nextLayoutData} = action;

			const nextItem = nextLayoutData.items[itemId] || {};
			const previousItem = layoutData.items[itemId] || {};

			return {
				...nextLayoutData,
				items: {
					...nextLayoutData.items,
					[itemId]: {
						...nextItem,
						config: {
							...(action.overridePreviousConfig
								? {}
								: previousItem.config),
							...nextItem.config,
						},
					},
				},
			};
		}

		case UPDATE_ITEM_LOCAL_CONFIG: {
			const {itemConfig, itemId} = action;

			const item = layoutData.items[itemId] || {};

			return {
				...layoutData,
				items: {
					...layoutData.items,
					[itemId]: {
						...item,
						config: {
							...(action.overridePreviousConfig
								? {}
								: item.config),
							...itemConfig,
						},
					},
				},
			};
		}

		case UPDATE_PREVIEW_IMAGE: {
			const newItems = Object.fromEntries(
				Object.entries(layoutData.items).map(([key, value]) => {
					const newValue =
						value.config?.styles?.backgroundImage?.classPK ===
						action.fileEntryId
							? setIn(
									value,
									[
										'config',
										'styles',
										'backgroundImage',
										'url',
									],
									action.previewURL
							  )
							: value;

					return [key, newValue];
				})
			);

			return {
				...layoutData,
				items: newItems,
			};
		}

		default:
			return layoutData;
	}
}
