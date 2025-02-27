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

package com.liferay.object.web.internal.object.entries.portlet;

import com.liferay.object.constants.ObjectWebKeys;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.scope.ObjectScopeProviderRegistry;
import com.liferay.object.service.ObjectActionLocalService;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectViewLocalService;
import com.liferay.object.web.internal.object.entries.display.context.ViewObjectEntriesDisplayContext;
import com.liferay.object.web.internal.object.entries.frontend.data.set.filter.factory.ObjectFieldFDSFilterFactoryRegistry;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Marco Leo
 * @author Brian Wing Shun Chan
 */
public class ObjectEntriesPortlet extends MVCPortlet {

	public ObjectEntriesPortlet(
		ObjectActionLocalService objectActionLocalService,
		long objectDefinitionId,
		ObjectDefinitionLocalService objectDefinitionLocalService,
		ObjectFieldFDSFilterFactoryRegistry objectFieldFDSFilterFactoryRegistry,
		ObjectFieldLocalService objectFieldLocalService,
		ObjectScopeProviderRegistry objectScopeProviderRegistry,
		ObjectViewLocalService objectViewLocalService, Portal portal,
		PortletResourcePermission portletResourcePermission) {

		_objectActionLocalService = objectActionLocalService;
		_objectDefinitionId = objectDefinitionId;
		_objectDefinitionLocalService = objectDefinitionLocalService;
		_objectFieldFDSFilterFactoryRegistry =
			objectFieldFDSFilterFactoryRegistry;
		_objectFieldLocalService = objectFieldLocalService;
		_objectScopeProviderRegistry = objectScopeProviderRegistry;
		_objectViewLocalService = objectViewLocalService;
		_portal = portal;
		_portletResourcePermission = portletResourcePermission;
	}

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.fetchObjectDefinition(
				_objectDefinitionId);

		renderRequest.setAttribute(
			ObjectWebKeys.OBJECT_DEFINITION, objectDefinition);
		renderRequest.setAttribute(
			WebKeys.PORTLET_DISPLAY_CONTEXT,
			new ViewObjectEntriesDisplayContext(
				_portal.getHttpServletRequest(renderRequest),
				_objectActionLocalService, _objectFieldFDSFilterFactoryRegistry,
				_objectFieldLocalService,
				_objectScopeProviderRegistry.getObjectScopeProvider(
					objectDefinition.getScope()),
				_objectViewLocalService, _portletResourcePermission,
				objectDefinition.getRESTContextPath()));

		super.render(renderRequest, renderResponse);
	}

	private final ObjectActionLocalService _objectActionLocalService;
	private final long _objectDefinitionId;
	private final ObjectDefinitionLocalService _objectDefinitionLocalService;
	private final ObjectFieldFDSFilterFactoryRegistry
		_objectFieldFDSFilterFactoryRegistry;
	private final ObjectFieldLocalService _objectFieldLocalService;
	private final ObjectScopeProviderRegistry _objectScopeProviderRegistry;
	private final ObjectViewLocalService _objectViewLocalService;
	private final Portal _portal;
	private final PortletResourcePermission _portletResourcePermission;

}