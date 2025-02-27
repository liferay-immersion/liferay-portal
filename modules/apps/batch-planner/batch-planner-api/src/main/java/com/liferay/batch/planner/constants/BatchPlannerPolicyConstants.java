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

package com.liferay.batch.planner.constants;

import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.Collections;
import java.util.Map;

/**
 * @author Igor Beslic
 */
public class BatchPlannerPolicyConstants {

	public static final Map<String, String> exportPlanPolicyNameTypes =
		Collections.unmodifiableMap(
			HashMapBuilder.put(
				"containsHeaders", "checkbox"
			).put(
				"headlessEndpoint", "text"
			).put(
				"siteId", "text"
			).build());
	public static final Map<String, String> importPlanPolicyNameTypes =
		Collections.unmodifiableMap(
			HashMapBuilder.put(
				"containsHeaders", "checkbox"
			).put(
				"createStrategy", "text"
			).put(
				"delimiter", "text"
			).put(
				"enclosingCharacter", "text"
			).put(
				"headlessEndpoint", "text"
			).put(
				"onErrorFail", "checkbox"
			).put(
				"siteId", "text"
			).put(
				"updateStrategy", "text"
			).build());

}