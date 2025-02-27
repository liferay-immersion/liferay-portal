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

import {openConfirmModal} from 'frontend-js-web';

const ACTIONS = {
	resetCustomizationView(itemData) {
		openConfirmModal({
			message: Liferay.Language.get(
				'are-you-sure-you-want-to-reset-your-customizations-to-default'
			),
			onConfirm: (isConfirmed) => {
				if (isConfirmed) {
					submitForm(
						document.hrefFm,
						itemData.resetCustomizationViewURL
					);
				}
			},
		});
	},

	toggleCustomizedViewMessage(itemData) {
		submitForm(document.hrefFm, itemData.toggleCustomizedViewMessageURL);
	},
};

export default function propsTransformer({actions, items, ...props}) {
	const updateItem = (item) => {
		const newItem = {
			...item,
			onClick(event) {
				const action = item.data?.action;

				if (action) {
					event.preventDefault();

					ACTIONS[action]?.(item.data);
				}
			},
		};

		if (Array.isArray(item.items)) {
			newItem.items = item.items.map(updateItem);
		}

		return newItem;
	};

	return {
		...props,
		actions: actions?.map(updateItem),
		items: items?.map(updateItem),
	};
}
