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

package com.liferay.configuration.admin.display;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

import java.io.Serializable;

/**
 * @author Drew Brokke
 */
public interface ConfigurationVisibilityController {

	public default String getKey() {
		Class<? extends ConfigurationVisibilityController> clazz = getClass();

		return clazz.getName();
	}

	public boolean isVisible(
		ExtendedObjectClassDefinition.Scope scope, Serializable scopePK);

}