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

package com.liferay.wiki.web.internal.portlet.configuration.icon;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.configuration.icon.BasePortletConfigurationIcon;
import com.liferay.portal.kernel.portlet.configuration.icon.PortletConfigurationIcon;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.trash.TrashHelper;
import com.liferay.wiki.constants.WikiPortletKeys;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.web.internal.portlet.action.ActionUtil;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Roberto Díaz
 */
@Component(
	property = {
		"javax.portlet.name=" + WikiPortletKeys.WIKI_ADMIN, "path=/wiki/view"
	},
	service = PortletConfigurationIcon.class
)
public class DeletePagePortletConfigurationIcon
	extends BasePortletConfigurationIcon {

	@Override
	public String getMessage(PortletRequest portletRequest) {
		return _language.get(getLocale(portletRequest), "delete");
	}

	@Override
	public String getURL(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		try {
			WikiPage page = ActionUtil.getPage(portletRequest);

			PortletURL portletURL = PortletURLBuilder.create(
				_portal.getControlPanelPortletURL(
					portletRequest, WikiPortletKeys.WIKI_ADMIN,
					PortletRequest.ACTION_PHASE)
			).setActionName(
				"/wiki/edit_page"
			).setCMD(
				Constants.DELETE
			).buildPortletURL();

			if (!page.isDraft() &&
				isTrashEnabled(themeDisplay.getScopeGroupId())) {

				portletURL.setParameter(Constants.CMD, Constants.MOVE_TO_TRASH);
			}
			else {
				portletURL.setParameter(
					"version", String.valueOf(page.getVersion()));
			}

			portletURL.setParameter(
				"redirect",
				PortletURLBuilder.create(
					_portal.getControlPanelPortletURL(
						portletRequest, WikiPortletKeys.WIKI_ADMIN,
						PortletRequest.ACTION_PHASE)
				).setMVCRenderCommandName(
					"/wiki/view_pages"
				).setNavigation(
					"all-pages"
				).setParameter(
					"nodeId", page.getNodeId()
				).buildString());
			portletURL.setParameter("nodeId", String.valueOf(page.getNodeId()));
			portletURL.setParameter("title", page.getTitle());

			return portletURL.toString();
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}

		return StringPool.BLANK;
	}

	@Override
	public double getWeight() {
		return 100;
	}

	@Override
	public boolean isShow(PortletRequest portletRequest) {
		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		try {
			return _wikiPageModelResourcePermission.contains(
				themeDisplay.getPermissionChecker(),
				ActionUtil.getPage(portletRequest), ActionKeys.DELETE);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}

		return false;
	}

	protected boolean isTrashEnabled(long groupId) {
		try {
			if (_trashHelper.isTrashEnabled(groupId)) {
				return true;
			}
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DeletePagePortletConfigurationIcon.class);

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

	@Reference
	private TrashHelper _trashHelper;

	@Reference(target = "(model.class.name=com.liferay.wiki.model.WikiPage)")
	private ModelResourcePermission<WikiPage> _wikiPageModelResourcePermission;

}