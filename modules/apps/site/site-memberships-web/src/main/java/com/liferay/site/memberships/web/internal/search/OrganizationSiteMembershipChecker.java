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

package com.liferay.site.memberships.web.internal.search;

import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.service.OrganizationLocalServiceUtil;

import javax.portlet.RenderResponse;

/**
 * @author Charles May
 */
public class OrganizationSiteMembershipChecker extends EmptyOnClickRowChecker {

	public OrganizationSiteMembershipChecker(
		RenderResponse renderResponse, Group group) {

		super(renderResponse);

		_group = group;
	}

	@Override
	public boolean isChecked(Object object) {
		Organization organization = (Organization)object;

		try {
			if (OrganizationLocalServiceUtil.hasGroupOrganization(
					_group.getGroupId(), organization.getOrganizationId()) ||
				(_group.getOrganizationId() ==
					organization.getOrganizationId())) {

				return true;
			}
		}
		catch (Exception exception) {
			_log.error(exception);
		}

		return false;
	}

	@Override
	public boolean isDisabled(Object object) {
		Organization organization = (Organization)object;

		if (_group.getOrganizationId() == organization.getOrganizationId()) {
			return true;
		}

		return isChecked(object);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		OrganizationSiteMembershipChecker.class);

	private final Group _group;

}