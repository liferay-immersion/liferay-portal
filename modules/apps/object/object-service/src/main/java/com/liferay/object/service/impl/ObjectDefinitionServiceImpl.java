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

package com.liferay.object.service.impl;

import com.liferay.object.constants.ObjectActionKeys;
import com.liferay.object.constants.ObjectConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.service.base.ObjectDefinitionServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 * @author Brian Wing Shun Chan
 */
@Component(
	property = {
		"json.web.service.context.name=object",
		"json.web.service.context.path=ObjectDefinition"
	},
	service = AopService.class
)
public class ObjectDefinitionServiceImpl
	extends ObjectDefinitionServiceBaseImpl {

	@Override
	public ObjectDefinition addCustomObjectDefinition(
			boolean enableComments, boolean enableLocalization,
			Map<Locale, String> labelMap, String name, String panelAppOrder,
			String panelCategoryKey, Map<Locale, String> pluralLabelMap,
			boolean portlet, String scope, String storageType,
			List<ObjectField> objectFields)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), null,
			ObjectActionKeys.ADD_OBJECT_DEFINITION);

		return objectDefinitionLocalService.addCustomObjectDefinition(
			getUserId(), enableComments, enableLocalization, labelMap, name,
			panelAppOrder, panelCategoryKey, pluralLabelMap, portlet, scope,
			storageType, objectFields);
	}

	@Override
	public ObjectDefinition addObjectDefinition(
			String externalReferenceCode, boolean modifiable, boolean system)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), null,
			ObjectActionKeys.ADD_OBJECT_DEFINITION);

		return objectDefinitionLocalService.addObjectDefinition(
			externalReferenceCode, getUserId(), modifiable, system);
	}

	@Override
	public ObjectDefinition addSystemObjectDefinition(
			String externalReferenceCode, long userId, boolean enableComments,
			Map<Locale, String> labelMap, String name, String panelAppOrder,
			String panelCategoryKey, Map<Locale, String> pluralLabelMap,
			String scope, List<ObjectField> objectFields)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), null,
			ObjectActionKeys.ADD_OBJECT_DEFINITION);

		return objectDefinitionLocalService.addSystemObjectDefinition(
			externalReferenceCode, userId, null, null, enableComments, labelMap,
			true, name, panelAppOrder, panelCategoryKey, null, null,
			pluralLabelMap, scope, null, 1, WorkflowConstants.STATUS_DRAFT,
			objectFields);
	}

	@Override
	public ObjectDefinition deleteObjectDefinition(long objectDefinitionId)
		throws PortalException {

		_objectDefinitionModelResourcePermission.check(
			getPermissionChecker(), objectDefinitionId, ActionKeys.DELETE);

		return objectDefinitionLocalService.deleteObjectDefinition(
			objectDefinitionId);
	}

	@Override
	public ObjectDefinition fetchObjectDefinitionByExternalReferenceCode(
			String externalReferenceCode, long companyId)
		throws PortalException {

		ObjectDefinition objectDefinition =
			objectDefinitionLocalService.
				fetchObjectDefinitionByExternalReferenceCode(
					externalReferenceCode, companyId);

		if (objectDefinition != null) {
			_objectDefinitionModelResourcePermission.check(
				getPermissionChecker(), objectDefinition, ActionKeys.VIEW);
		}

		return objectDefinition;
	}

	@Override
	public ObjectDefinition getObjectDefinition(long objectDefinitionId)
		throws PortalException {

		_objectDefinitionModelResourcePermission.check(
			getPermissionChecker(), objectDefinitionId, ActionKeys.VIEW);

		return objectDefinitionLocalService.getObjectDefinition(
			objectDefinitionId);
	}

	@Override
	public ObjectDefinition getObjectDefinitionByExternalReferenceCode(
			String externalReferenceCode, long companyId)
		throws PortalException {

		ObjectDefinition objectDefinition =
			objectDefinitionLocalService.
				getObjectDefinitionByExternalReferenceCode(
					externalReferenceCode, companyId);

		_objectDefinitionModelResourcePermission.check(
			getPermissionChecker(), objectDefinition, ActionKeys.VIEW);

		return objectDefinition;
	}

	@Override
	public List<ObjectDefinition> getObjectDefinitions(int start, int end) {
		return objectDefinitionLocalService.getObjectDefinitions(start, end);
	}

	@Override
	public List<ObjectDefinition> getObjectDefinitions(
		long companyId, int start, int end) {

		return objectDefinitionPersistence.findByCompanyId(
			companyId, start, end);
	}

	@Override
	public int getObjectDefinitionsCount() throws PortalException {
		return objectDefinitionLocalService.getObjectDefinitionsCount();
	}

	@Override
	public int getObjectDefinitionsCount(long companyId)
		throws PortalException {

		return objectDefinitionLocalService.getObjectDefinitionsCount(
			companyId);
	}

	@Override
	public ObjectDefinition publishCustomObjectDefinition(
			long objectDefinitionId)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), null,
			ObjectActionKeys.PUBLISH_OBJECT_DEFINITION);

		return objectDefinitionLocalService.publishCustomObjectDefinition(
			getUserId(), objectDefinitionId);
	}

	@Override
	public ObjectDefinition publishSystemObjectDefinition(
			long objectDefinitionId)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), null,
			ObjectActionKeys.PUBLISH_OBJECT_DEFINITION);

		return objectDefinitionLocalService.publishSystemObjectDefinition(
			getUserId(), objectDefinitionId);
	}

	public ObjectDefinition updateCustomObjectDefinition(
			String externalReferenceCode, long objectDefinitionId,
			long accountEntryRestrictedObjectFieldId,
			long descriptionObjectFieldId, long titleObjectFieldId,
			boolean accountEntryRestricted, boolean active,
			boolean enableCategorization, boolean enableComments,
			boolean enableLocalization, boolean enableObjectEntryHistory,
			Map<Locale, String> labelMap, String name, String panelAppOrder,
			String panelCategoryKey, boolean portlet,
			Map<Locale, String> pluralLabelMap, String scope)
		throws PortalException {

		_objectDefinitionModelResourcePermission.check(
			getPermissionChecker(), objectDefinitionId, ActionKeys.UPDATE);

		return objectDefinitionLocalService.updateCustomObjectDefinition(
			externalReferenceCode, objectDefinitionId,
			accountEntryRestrictedObjectFieldId, descriptionObjectFieldId,
			titleObjectFieldId, accountEntryRestricted, active,
			enableCategorization, enableComments, enableLocalization,
			enableObjectEntryHistory, labelMap, name, panelAppOrder,
			panelCategoryKey, portlet, pluralLabelMap, scope);
	}

	@Override
	public ObjectDefinition updateExternalReferenceCode(
			long objectDefinitionId, String externalReferenceCode)
		throws PortalException {

		_objectDefinitionModelResourcePermission.check(
			getPermissionChecker(), objectDefinitionId, ActionKeys.UPDATE);

		return objectDefinitionLocalService.updateExternalReferenceCode(
			objectDefinitionId, externalReferenceCode);
	}

	@Override
	public ObjectDefinition updateSystemObjectDefinition(
			String externalReferenceCode, long objectDefinitionId,
			long titleObjectFieldId)
		throws PortalException {

		_objectDefinitionModelResourcePermission.check(
			getPermissionChecker(), objectDefinitionId, ActionKeys.UPDATE);

		return objectDefinitionLocalService.updateSystemObjectDefinition(
			externalReferenceCode, objectDefinitionId, titleObjectFieldId);
	}

	@Override
	public ObjectDefinition updateTitleObjectFieldId(
			long objectDefinitionId, long titleObjectFieldId)
		throws PortalException {

		_objectDefinitionModelResourcePermission.check(
			getPermissionChecker(), objectDefinitionId, ActionKeys.UPDATE);

		return objectDefinitionLocalService.updateTitleObjectFieldId(
			objectDefinitionId, titleObjectFieldId);
	}

	@Reference(
		target = "(model.class.name=com.liferay.object.model.ObjectDefinition)"
	)
	private ModelResourcePermission<ObjectDefinition>
		_objectDefinitionModelResourcePermission;

	@Reference(target = "(resource.name=" + ObjectConstants.RESOURCE_NAME + ")")
	private PortletResourcePermission _portletResourcePermission;

}