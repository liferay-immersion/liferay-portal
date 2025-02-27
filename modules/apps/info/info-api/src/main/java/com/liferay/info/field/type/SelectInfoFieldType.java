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

package com.liferay.info.field.type;

import java.util.Collection;

/**
 * @author Alicia Garcia
 */
public class SelectInfoFieldType implements InfoFieldType {

	public static final Attribute<SelectInfoFieldType, Boolean> INLINE =
		new Attribute<>();

	public static final SelectInfoFieldType INSTANCE =
		new SelectInfoFieldType();

	public static final Attribute
		<SelectInfoFieldType, Collection<OptionInfoFieldType>> OPTIONS =
			new Attribute<>();

	@Override
	public String getName() {
		return "select";
	}

	private SelectInfoFieldType() {
	}

}