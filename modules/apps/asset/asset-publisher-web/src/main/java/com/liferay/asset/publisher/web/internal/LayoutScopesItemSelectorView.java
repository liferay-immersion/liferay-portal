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

package com.liferay.asset.publisher.web.internal;

import com.liferay.asset.publisher.util.AssetPublisherHelper;
import com.liferay.asset.publisher.web.internal.display.context.LayoutScopesItemSelectorViewDisplayContext;
import com.liferay.asset.publisher.web.internal.item.selector.SitesItemSelectorViewDescriptor;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorView;
import com.liferay.item.selector.ItemSelectorViewDescriptorRenderer;
import com.liferay.item.selector.criteria.GroupItemSelectorReturnType;
import com.liferay.item.selector.criteria.group.criterion.GroupItemSelectorCriterion;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.io.IOException;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.portlet.PortletURL;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	property = "item.selector.view.order:Integer=100",
	service = ItemSelectorView.class
)
public class LayoutScopesItemSelectorView
	implements ItemSelectorView<GroupItemSelectorCriterion> {

	@Override
	public Class<GroupItemSelectorCriterion> getItemSelectorCriterionClass() {
		return GroupItemSelectorCriterion.class;
	}

	@Override
	public List<ItemSelectorReturnType> getSupportedItemSelectorReturnTypes() {
		return _supportedItemSelectorReturnTypes;
	}

	@Override
	public String getTitle(Locale locale) {
		return ResourceBundleUtil.getString(
			_portal.getResourceBundle(locale), "pages");
	}

	@Override
	public boolean isVisible(
		GroupItemSelectorCriterion groupItemSelectorCriterion,
		ThemeDisplay themeDisplay) {

		if (!groupItemSelectorCriterion.isIncludeLayoutScopes()) {
			return false;
		}

		Layout layout = themeDisplay.getLayout();

		int groupsCount = _groupLocalService.getGroupsCount(
			themeDisplay.getCompanyId(), Layout.class.getName(),
			layout.getGroupId());

		if (groupsCount > 0) {
			return true;
		}

		return false;
	}

	@Override
	public void renderHTML(
			ServletRequest servletRequest, ServletResponse servletResponse,
			GroupItemSelectorCriterion groupItemSelectorCriterion,
			PortletURL portletURL, String itemSelectedEventName, boolean search)
		throws IOException, ServletException {

		LayoutScopesItemSelectorViewDisplayContext
			layoutScopesItemSelectorViewDisplayContext =
				new LayoutScopesItemSelectorViewDisplayContext(
					(HttpServletRequest)servletRequest, _assetPublisherHelper,
					groupItemSelectorCriterion, portletURL);

		_itemSelectorViewDescriptorRenderer.renderHTML(
			servletRequest, servletResponse, groupItemSelectorCriterion,
			portletURL, itemSelectedEventName, search,
			new SitesItemSelectorViewDescriptor(
				(HttpServletRequest)servletRequest,
				layoutScopesItemSelectorViewDisplayContext));
	}

	private static final List<ItemSelectorReturnType>
		_supportedItemSelectorReturnTypes = Collections.singletonList(
			new GroupItemSelectorReturnType());

	@Reference
	private AssetPublisherHelper _assetPublisherHelper;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private ItemSelectorViewDescriptorRenderer<GroupItemSelectorCriterion>
		_itemSelectorViewDescriptorRenderer;

	@Reference
	private Portal _portal;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.asset.publisher.web)"
	)
	private ServletContext _servletContext;

}