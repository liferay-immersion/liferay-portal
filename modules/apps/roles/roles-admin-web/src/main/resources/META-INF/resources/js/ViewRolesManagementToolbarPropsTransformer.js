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
	getCheckedCheckboxes,
	openConfirmModal,
	postForm,
} from 'frontend-js-web';

export default function propsTransformer({
	additionalProps: {deleteRolesURL},
	portletNamespace,
	...otherProps
}) {
	return {
		...otherProps,
		onActionButtonClick() {
			const form = document.getElementById(`${portletNamespace}fm`);

			if (!form) {
				return;
			}

			const deleteRoleIds = getCheckedCheckboxes(
				form,
				`${portletNamespace}allRowIds`
			);

			openConfirmModal({
				message: Liferay.Language.get(
					'are-you-sure-you-want-to-delete-this-role?-task-assignments-may-be-deleted'
				),
				onConfirm: (isConfirmed) => {
					if (isConfirmed) {
						postForm(form, {
							data: {
								deleteRoleIds,
							},
							url: deleteRolesURL,
						});
					}
				},
			});
		},
	};
}
