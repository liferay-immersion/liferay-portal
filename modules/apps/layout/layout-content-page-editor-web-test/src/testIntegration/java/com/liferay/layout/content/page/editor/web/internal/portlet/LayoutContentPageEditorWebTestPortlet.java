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

package com.liferay.layout.content.page.editor.web.internal.portlet;

import com.liferay.fragment.processor.PortletRegistry;
import com.liferay.layout.content.page.editor.web.internal.portlet.constants.LayoutContentPageEditorWebPortletKeys;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;

import javax.portlet.Portlet;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Lourdes Fernández Besada
 */
@Component(
	property = {
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.instanceable=true",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.scopeable=true", "javax.portlet.display-name=Test",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.name=" + LayoutContentPageEditorWebPortletKeys.LAYOUT_CONTENT_PAGE_EDITOR_WEB_TEST_PORTLET,
		"javax.portlet.version=3.0"
	},
	service = Portlet.class
)
public class LayoutContentPageEditorWebTestPortlet extends MVCPortlet {

	@Activate
	protected void activate() {
		_portletRegistry.registerAlias(
			_ALIAS,
			LayoutContentPageEditorWebPortletKeys.
				LAYOUT_CONTENT_PAGE_EDITOR_WEB_TEST_PORTLET);
	}

	@Deactivate
	protected void deactivate() {
		_portletRegistry.unregisterAlias(_ALIAS);
	}

	private static final String _ALIAS = "test";

	@Reference
	private PortletRegistry _portletRegistry;

}