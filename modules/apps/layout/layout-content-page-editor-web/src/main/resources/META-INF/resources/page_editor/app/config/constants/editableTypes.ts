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

/**
 * Available editable types
 */
export const EDITABLE_TYPES = {
	'action': 'action',
	'backgroundImage': 'background-image',
	'date-time': 'date-time',
	'html': 'html',
	'image': 'image',
	'link': 'link',
	'rich-text': 'rich-text',
	'text': 'text',
} as const;

export type EditableType = typeof EDITABLE_TYPES[keyof typeof EDITABLE_TYPES];
