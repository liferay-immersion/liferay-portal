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

package com.liferay.user.groups.admin.web.internal.search;

import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.permission.UserGroupPermissionUtil;

import javax.portlet.RenderResponse;

/**
 * @author Drew Brokke
 */
public class UserGroupChecker extends EmptyOnClickRowChecker {

	public UserGroupChecker(RenderResponse renderResponse) {
		super(renderResponse);
	}

	@Override
	public boolean isDisabled(Object object) {
		UserGroup userGroup = (UserGroup)object;

		if (!UserGroupPermissionUtil.contains(
				PermissionThreadLocal.getPermissionChecker(),
				userGroup.getUserGroupId(), ActionKeys.DELETE)) {

			return true;
		}

		return super.isDisabled(object);
	}

}