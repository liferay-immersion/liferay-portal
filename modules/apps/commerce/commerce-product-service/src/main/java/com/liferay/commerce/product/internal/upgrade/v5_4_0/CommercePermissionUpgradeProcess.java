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

package com.liferay.commerce.product.internal.upgrade.v5_4_0;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.ResourceAction;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Danny Situ
 */
public class CommercePermissionUpgradeProcess extends UpgradeProcess {

	public CommercePermissionUpgradeProcess(
		ResourceActionLocalService resourceActionLocalService,
		ResourcePermissionLocalService resourcePermissionLocalService) {

		_resourceActionLocalService = resourceActionLocalService;
		_resourcePermissionLocalService = resourcePermissionLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		_updateViewCommerceCatalogPermission();
	}

	private void _updateViewCommerceCatalogPermission() throws Exception {
		ResourceAction resourceAction =
			_resourceActionLocalService.fetchResourceAction(
				"com.liferay.commerce.product",
				"MANAGE_COMMERCE_PRODUCT_CHANNEL_VISIBILITY");

		if (resourceAction == null) {
			return;
		}

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				StringBundler.concat(
					"select companyId, resourcePermissionId, roleId, primKey ",
					"from ResourcePermission where name = ",
					"'com.liferay.commerce.catalog' and scope = 1"));
			ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				ResourcePermission resourcePermission =
					_resourcePermissionLocalService.getResourcePermission(
						resultSet.getLong(2));

				resourceAction =
					_resourceActionLocalService.fetchResourceAction(
						"com.liferay.commerce.catalog",
						"VIEW_COMMERCE_CATALOGS");

				if ((resourceAction != null) &&
					_resourcePermissionLocalService.hasActionId(
						resourcePermission, resourceAction)) {

					_resourcePermissionLocalService.addResourcePermission(
						resultSet.getLong(1), "com.liferay.commerce.product", 1,
						resultSet.getString(4), resultSet.getLong(3),
						"MANAGE_COMMERCE_PRODUCT_CHANNEL_VISIBILITY");
				}
			}
		}
	}

	private final ResourceActionLocalService _resourceActionLocalService;
	private final ResourcePermissionLocalService
		_resourcePermissionLocalService;

}