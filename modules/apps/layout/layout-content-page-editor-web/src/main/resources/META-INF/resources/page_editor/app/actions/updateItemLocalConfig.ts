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

import {UPDATE_ITEM_LOCAL_CONFIG} from './types';

export default function updateItemLocalConfig({
	disableUndo = false,
	itemConfig,
	itemId,
	overridePreviousConfig = false,
}: {
	disableUndo?: boolean;
	itemConfig: {
		loading?: boolean;
		showMessagePreview?: boolean;
		showPreview?: boolean;
	};
	itemId: string;
	overridePreviousConfig?: boolean;
}) {
	return {
		disableUndo,
		itemConfig,
		itemId,
		overridePreviousConfig,
		type: UPDATE_ITEM_LOCAL_CONFIG,
	} as const;
}
