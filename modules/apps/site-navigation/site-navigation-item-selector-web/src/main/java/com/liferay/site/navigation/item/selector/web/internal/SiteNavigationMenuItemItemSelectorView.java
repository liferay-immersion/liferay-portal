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

package com.liferay.site.navigation.item.selector.web.internal;

import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorView;
import com.liferay.item.selector.criteria.UUIDItemSelectorReturnType;
import com.liferay.portal.kernel.language.Language;
import com.liferay.site.navigation.item.selector.criterion.SiteNavigationMenuItemItemSelectorCriterion;
import com.liferay.site.navigation.item.selector.web.internal.constants.SiteNavigationItemSelectorWebKeys;
import com.liferay.site.navigation.item.selector.web.internal.display.context.SiteNavigationMenuItemItemSelectorViewDisplayContext;
import com.liferay.site.navigation.type.SiteNavigationMenuItemTypeRegistry;

import java.io.IOException;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.portlet.PortletURL;

import javax.servlet.RequestDispatcher;
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
	property = "item.selector.view.order:Integer=300",
	service = ItemSelectorView.class
)
public class SiteNavigationMenuItemItemSelectorView
	implements ItemSelectorView<SiteNavigationMenuItemItemSelectorCriterion> {

	@Override
	public Class<SiteNavigationMenuItemItemSelectorCriterion>
		getItemSelectorCriterionClass() {

		return SiteNavigationMenuItemItemSelectorCriterion.class;
	}

	public ServletContext getServletContext() {
		return _servletContext;
	}

	@Override
	public List<ItemSelectorReturnType> getSupportedItemSelectorReturnTypes() {
		return _supportedItemSelectorReturnTypes;
	}

	@Override
	public String getTitle(Locale locale) {
		return _language.get(locale, "navigation-menu-items");
	}

	@Override
	public void renderHTML(
			ServletRequest servletRequest, ServletResponse servletResponse,
			SiteNavigationMenuItemItemSelectorCriterion
				siteNavigationMenuItemItemSelectorCriterion,
			PortletURL portletURL, String itemSelectedEventName, boolean search)
		throws IOException, ServletException {

		ServletContext servletContext = getServletContext();

		RequestDispatcher requestDispatcher =
			servletContext.getRequestDispatcher(
				"/view_site_navigation_menu_items.jsp");

		SiteNavigationMenuItemItemSelectorViewDisplayContext
			siteNavigationMenuItemItemSelectorViewDisplayContext =
				new SiteNavigationMenuItemItemSelectorViewDisplayContext(
					(HttpServletRequest)servletRequest, itemSelectedEventName,
					_siteNavigationMenuItemTypeRegistry);

		servletRequest.setAttribute(
			SiteNavigationItemSelectorWebKeys.
				SITE_NAVIGATION_MENU_ITEM_ITEM_SELECTOR_DISPLAY_CONTEXT,
			siteNavigationMenuItemItemSelectorViewDisplayContext);

		requestDispatcher.include(servletRequest, servletResponse);
	}

	private static final List<ItemSelectorReturnType>
		_supportedItemSelectorReturnTypes = Collections.singletonList(
			new UUIDItemSelectorReturnType());

	@Reference
	private Language _language;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.site.navigation.item.selector.web)"
	)
	private ServletContext _servletContext;

	@Reference
	private SiteNavigationMenuItemTypeRegistry
		_siteNavigationMenuItemTypeRegistry;

}