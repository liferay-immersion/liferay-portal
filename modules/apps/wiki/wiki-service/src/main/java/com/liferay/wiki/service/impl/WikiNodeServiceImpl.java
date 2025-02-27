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

package com.liferay.wiki.service.impl;

import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.wiki.constants.WikiConstants;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.service.base.WikiNodeServiceBaseImpl;

import java.io.InputStream;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides the remote service for accessing, adding, deleting, importing,
 * subscription handling of, trash handling of, and updating wiki nodes. Its
 * methods include permission checks.
 *
 * @author Brian Wing Shun Chan
 * @author Charles May
 */
@Component(
	property = {
		"json.web.service.context.name=wiki",
		"json.web.service.context.path=WikiNode"
	},
	service = AopService.class
)
public class WikiNodeServiceImpl extends WikiNodeServiceBaseImpl {

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link #addNode(String,
	 *             String, String, ServiceContext)}
	 */
	@Deprecated
	@Override
	public WikiNode addNode(
			String name, String description, ServiceContext serviceContext)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			ActionKeys.ADD_NODE);

		return wikiNodeLocalService.addNode(
			getUserId(), name, description, serviceContext);
	}

	@Override
	public WikiNode addNode(
			String externalReferenceCode, String name, String description,
			ServiceContext serviceContext)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			ActionKeys.ADD_NODE);

		return wikiNodeLocalService.addNode(
			externalReferenceCode, getUserId(), name, description,
			serviceContext);
	}

	@Override
	public void deleteNode(long nodeId) throws PortalException {
		WikiNode node = wikiNodeLocalService.getNode(nodeId);

		_wikiNodeModelResourcePermission.check(
			getPermissionChecker(), node, ActionKeys.DELETE);

		wikiNodeLocalService.deleteNode(node);
	}

	@Override
	public WikiNode getNode(long nodeId) throws PortalException {
		WikiNode node = wikiNodeLocalService.getNode(nodeId);

		_wikiNodeModelResourcePermission.check(
			getPermissionChecker(), node, ActionKeys.VIEW);

		return node;
	}

	@Override
	public WikiNode getNode(long groupId, String name) throws PortalException {
		WikiNode node = wikiNodeLocalService.getNode(groupId, name);

		_wikiNodeModelResourcePermission.check(
			getPermissionChecker(), node, ActionKeys.VIEW);

		return node;
	}

	@Override
	public List<WikiNode> getNodes(long groupId) throws PortalException {
		return getNodes(groupId, WorkflowConstants.STATUS_APPROVED);
	}

	@Override
	public List<WikiNode> getNodes(long groupId, int status)
		throws PortalException {

		List<WikiNode> nodes = wikiNodePersistence.filterFindByG_S(
			groupId, status);

		if (nodes.isEmpty()) {
			nodes = new ArrayList<>();

			List<WikiNode> allNodes = wikiNodeLocalService.getNodes(
				groupId, status);

			for (WikiNode node : allNodes) {
				if (_wikiNodeModelResourcePermission.contains(
						getPermissionChecker(), node, ActionKeys.VIEW)) {

					nodes.add(node);
				}
			}
		}

		return nodes;
	}

	@Override
	public List<WikiNode> getNodes(long groupId, int start, int end) {
		return getNodes(groupId, WorkflowConstants.STATUS_APPROVED, start, end);
	}

	@Override
	public List<WikiNode> getNodes(
		long groupId, int status, int start, int end) {

		return wikiNodePersistence.filterFindByG_S(groupId, status, start, end);
	}

	@Override
	public List<WikiNode> getNodes(
		long groupId, int status, int start, int end,
		OrderByComparator<WikiNode> orderByComparator) {

		return wikiNodePersistence.filterFindByG_S(
			groupId, status, start, end, orderByComparator);
	}

	@Override
	public int getNodesCount(long groupId) {
		return getNodesCount(groupId, WorkflowConstants.STATUS_APPROVED);
	}

	@Override
	public int getNodesCount(long groupId, int status) {
		return wikiNodePersistence.filterCountByG_S(groupId, status);
	}

	@Override
	public WikiNode getWikiNodeByExternalReferenceCode(
			long groupId, String externalReferenceCode)
		throws PortalException {

		WikiNode node = wikiNodeLocalService.getWikiNodeByExternalReferenceCode(
			externalReferenceCode, groupId);

		_wikiNodeModelResourcePermission.check(
			getPermissionChecker(), node, ActionKeys.VIEW);

		return node;
	}

	@Override
	public void importPages(
			long nodeId, InputStream[] inputStreams,
			Map<String, String[]> options)
		throws PortalException {

		_wikiNodeModelResourcePermission.check(
			getPermissionChecker(), nodeId, ActionKeys.IMPORT);

		wikiNodeLocalService.importPages(
			getUserId(), nodeId, inputStreams, options);
	}

	@Override
	public WikiNode moveNodeToTrash(long nodeId) throws PortalException {
		_wikiNodeModelResourcePermission.check(
			getPermissionChecker(), nodeId, ActionKeys.DELETE);

		return wikiNodeLocalService.moveNodeToTrash(getUserId(), nodeId);
	}

	@Override
	public void restoreNodeFromTrash(long nodeId) throws PortalException {
		WikiNode node = wikiNodeLocalService.getNode(nodeId);

		_wikiNodeModelResourcePermission.check(
			getPermissionChecker(), nodeId, ActionKeys.DELETE);

		wikiNodeLocalService.restoreNodeFromTrash(getUserId(), node);
	}

	@Override
	public void subscribeNode(long nodeId) throws PortalException {
		_wikiNodeModelResourcePermission.check(
			getPermissionChecker(), nodeId, ActionKeys.SUBSCRIBE);

		wikiNodeLocalService.subscribeNode(getUserId(), nodeId);
	}

	@Override
	public void unsubscribeNode(long nodeId) throws PortalException {
		_wikiNodeModelResourcePermission.check(
			getPermissionChecker(), nodeId, ActionKeys.SUBSCRIBE);

		wikiNodeLocalService.unsubscribeNode(getUserId(), nodeId);
	}

	@Override
	public WikiNode updateNode(
			long nodeId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException {

		_wikiNodeModelResourcePermission.check(
			getPermissionChecker(), nodeId, ActionKeys.UPDATE);

		return wikiNodeLocalService.updateNode(
			nodeId, name, description, serviceContext);
	}

	@Reference(target = "(resource.name=" + WikiConstants.RESOURCE_NAME + ")")
	private PortletResourcePermission _portletResourcePermission;

	@Reference(target = "(model.class.name=com.liferay.wiki.model.WikiNode)")
	private ModelResourcePermission<WikiNode> _wikiNodeModelResourcePermission;

}