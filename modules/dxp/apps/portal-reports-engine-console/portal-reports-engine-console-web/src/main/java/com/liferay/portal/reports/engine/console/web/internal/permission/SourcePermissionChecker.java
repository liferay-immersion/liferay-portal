/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.reports.engine.console.web.internal.permission;

import com.liferay.osgi.util.service.Snapshot;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.reports.engine.console.model.Source;

/**
 * @author Leon Chi
 */
public class SourcePermissionChecker {

	public static boolean contains(
			PermissionChecker permissionChecker, long sourceId, String actionId)
		throws PortalException {

		ModelResourcePermission<Source> modelResourcePermission =
			_sourceModelResourcePermissionSnapshot.get();

		return modelResourcePermission.contains(
			permissionChecker, sourceId, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, Source source, String actionId)
		throws PortalException {

		ModelResourcePermission<Source> modelResourcePermission =
			_sourceModelResourcePermissionSnapshot.get();

		return modelResourcePermission.contains(
			permissionChecker, source, actionId);
	}

	private static final Snapshot<ModelResourcePermission<Source>>
		_sourceModelResourcePermissionSnapshot = new Snapshot<>(
			SourcePermissionChecker.class,
			Snapshot.cast(ModelResourcePermission.class),
			"(model.class.name=com.liferay.portal.reports.engine.console." +
				"model.Source)");

}