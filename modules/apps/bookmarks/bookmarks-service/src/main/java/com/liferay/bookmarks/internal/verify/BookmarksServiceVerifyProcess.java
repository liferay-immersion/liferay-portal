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

package com.liferay.bookmarks.internal.verify;

import com.liferay.bookmarks.constants.BookmarksPortletKeys;
import com.liferay.bookmarks.model.BookmarksFolder;
import com.liferay.bookmarks.service.BookmarksFolderLocalService;
import com.liferay.exportimport.kernel.staging.Staging;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.verify.VerifyProcess;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author     Raymond Augé
 * @author     Alexander Chow
 * @deprecated As of Mueller (7.2.x), with no direct replacement
 */
@Component(service = VerifyProcess.class)
@Deprecated
public class BookmarksServiceVerifyProcess extends VerifyProcess {

	@Override
	protected void doVerify() throws Exception {
		if (FeatureFlagManagerUtil.isEnabled("LPS-157670")) {
			updateStagedPortletNames();
		}

		_updateFolderAssets();
		_verifyTree();
	}

	protected void updateStagedPortletNames() throws PortalException {
		ActionableDynamicQuery groupActionableDynamicQuery =
			_groupLocalService.getActionableDynamicQuery();

		groupActionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> {
				Property siteProperty = PropertyFactoryUtil.forName("site");

				dynamicQuery.add(siteProperty.eq(Boolean.TRUE));
			});
		groupActionableDynamicQuery.setPerformActionMethod(
			(ActionableDynamicQuery.PerformActionMethod<Group>)group -> {
				UnicodeProperties typeSettingsUnicodeProperties =
					group.getTypeSettingsProperties();

				if (typeSettingsUnicodeProperties == null) {
					return;
				}

				String propertyKey = _staging.getStagedPortletId(
					BookmarksPortletKeys.BOOKMARKS);

				String propertyValue =
					typeSettingsUnicodeProperties.getProperty(propertyKey);

				if (Validator.isNull(propertyValue)) {
					return;
				}

				typeSettingsUnicodeProperties.remove(propertyKey);

				propertyKey = _staging.getStagedPortletId(
					BookmarksPortletKeys.BOOKMARKS_ADMIN);

				typeSettingsUnicodeProperties.put(propertyKey, propertyValue);

				group.setTypeSettingsProperties(typeSettingsUnicodeProperties);

				_groupLocalService.updateGroup(group);
			});

		groupActionableDynamicQuery.performActions();
	}

	private void _updateFolderAssets() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			List<BookmarksFolder> folders =
				_bookmarksFolderLocalService.getNoAssetFolders();

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Processing " + folders.size() + " folders with no asset");
			}

			for (BookmarksFolder folder : folders) {
				try {
					_bookmarksFolderLocalService.updateAsset(
						folder.getUserId(), folder, null, null, null, null);
				}
				catch (Exception exception) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							StringBundler.concat(
								"Unable to update asset for folder ",
								folder.getFolderId(), ": ",
								exception.getMessage()));
					}
				}
			}

			if (_log.isDebugEnabled()) {
				_log.debug("Assets verified for folders");
			}
		}
	}

	private void _verifyTree() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			_companyLocalService.forEachCompanyId(
				companyId -> _bookmarksFolderLocalService.rebuildTree(
					companyId));
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BookmarksServiceVerifyProcess.class);

	@Reference
	private BookmarksFolderLocalService _bookmarksFolderLocalService;

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Staging _staging;

}