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

package com.liferay.document.library.internal.bulk.selection.action;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.asset.util.AssetHelper;
import com.liferay.bulk.selection.BulkSelection;
import com.liferay.bulk.selection.BulkSelectionAction;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.BaseModelPermissionCheckerUtil;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.SetUtil;

import java.io.Serializable;

import java.util.Map;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	property = "bulk.selection.action.key=edit.tags",
	service = BulkSelectionAction.class
)
public class EditTagsBulkSelectionAction
	implements BulkSelectionAction<AssetEntry> {

	@Override
	public void execute(
			User user, BulkSelection<AssetEntry> bulkSelection,
			Map<String, Serializable> inputMap)
		throws Exception {

		Set<String> toAddTagNamesSet = _toStringSet(inputMap, "toAddTagNames");
		Set<String> toRemoveTagNamesSet = _toStringSet(
			inputMap, "toRemoveTagNames");

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(user);

		bulkSelection.forEach(
			assetEntry -> {
				try {
					if (!_hasEditPermission(assetEntry, permissionChecker)) {
						return;
					}

					String[] newTagNames = new String[0];

					if (SetUtil.isNotEmpty(toAddTagNamesSet)) {
						newTagNames = (String[])inputMap.get("toAddTagNames");
					}

					if (MapUtil.getBoolean(inputMap, "append")) {
						Set<String> currentTagNamesSet = SetUtil.fromArray(
							assetEntry.getTagNames());

						currentTagNamesSet.removeAll(toRemoveTagNamesSet);

						currentTagNamesSet.addAll(toAddTagNamesSet);

						currentTagNamesSet.removeIf(
							tagName -> !_assetHelper.isValidWord(tagName));

						newTagNames = currentTagNamesSet.toArray(new String[0]);
					}

					_assetEntryLocalService.updateEntry(
						assetEntry.getUserId(), assetEntry.getGroupId(),
						assetEntry.getClassName(), assetEntry.getClassPK(),
						assetEntry.getCategoryIds(), newTagNames);
				}
				catch (PortalException portalException) {
					if (_log.isWarnEnabled()) {
						_log.warn(portalException);
					}
				}
			});
	}

	private boolean _hasEditPermission(
			AssetEntry assetEntry, PermissionChecker permissionChecker)
		throws PortalException {

		AssetRenderer<?> assetRenderer = assetEntry.getAssetRenderer();

		if (assetRenderer != null) {
			return assetRenderer.hasEditPermission(permissionChecker);
		}

		return BaseModelPermissionCheckerUtil.containsBaseModelPermission(
			permissionChecker, assetEntry.getGroupId(),
			assetEntry.getClassName(), assetEntry.getClassPK(),
			ActionKeys.UPDATE);
	}

	private Set<String> _toStringSet(
		Map<String, Serializable> map, String key) {

		try {
			return SetUtil.fromArray(
				(String[])map.getOrDefault(key, new String[0]));
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}

		return SetUtil.fromArray(new String[0]);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EditTagsBulkSelectionAction.class);

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private AssetHelper _assetHelper;

}