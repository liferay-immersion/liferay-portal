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

package com.liferay.layout.content.page.editor.web.internal.util;

import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.info.permission.provider.InfoPermissionProvider;
import com.liferay.layout.content.page.editor.web.internal.constants.ContentPageEditorConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalServiceUtil;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Eudaldo Alonso
 */
public class ObjectUtil {

	public static Map<String, List<Map<String, Object>>>
		getLayoutElementMapsListMap(
			long companyId, InfoItemServiceRegistry infoItemServiceRegistry,
			PermissionChecker permissionChecker) {

		Map<String, List<Map<String, Object>>> layoutElementMapsListMap =
			new HashMap<>(ContentPageEditorConstants.layoutElementMapsListMap);

		if (hideInputFragments(
				companyId, infoItemServiceRegistry, permissionChecker)) {

			layoutElementMapsListMap.remove("INPUTS");
		}

		return layoutElementMapsListMap;
	}

	public static Boolean hideInputFragments(
		long companyId, InfoItemServiceRegistry infoItemServiceRegistry,
		PermissionChecker permissionChecker) {

		if (!FeatureFlagManagerUtil.isEnabled("LPS-183727") &&
			_isLayoutTypeAssetDisplay()) {

			return true;
		}

		List<ObjectDefinition> objectDefinitions =
			ObjectDefinitionLocalServiceUtil.getObjectDefinitions(
				companyId, true, false, WorkflowConstants.STATUS_APPROVED);

		if (objectDefinitions.isEmpty()) {
			return true;
		}

		for (ObjectDefinition objectDefinition : objectDefinitions) {
			if (_hasPermissions(
					objectDefinition, infoItemServiceRegistry,
					permissionChecker)) {

				return false;
			}
		}

		return true;
	}

	private static boolean _hasPermissions(
		ObjectDefinition objectDefinition,
		InfoItemServiceRegistry infoItemServiceRegistry,
		PermissionChecker permissionChecker) {

		InfoPermissionProvider infoPermissionProvider =
			infoItemServiceRegistry.getFirstInfoItemService(
				InfoPermissionProvider.class, objectDefinition.getClassName());

		if ((infoPermissionProvider == null) ||
			infoPermissionProvider.hasViewPermission(permissionChecker)) {

			return true;
		}

		return false;
	}

	private static boolean _isLayoutTypeAssetDisplay() {
		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if (serviceContext == null) {
			return false;
		}

		ThemeDisplay themeDisplay = serviceContext.getThemeDisplay();

		if (themeDisplay == null) {
			return false;
		}

		Layout layout = themeDisplay.getLayout();

		if ((layout != null) && layout.isTypeAssetDisplay()) {
			return true;
		}

		return false;
	}

}