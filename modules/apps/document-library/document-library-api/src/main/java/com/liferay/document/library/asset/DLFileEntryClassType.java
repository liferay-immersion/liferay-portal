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

package com.liferay.document.library.asset;

import com.liferay.asset.kernel.model.ClassTypeField;
import com.liferay.asset.model.DDMStructureClassType;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalServiceUtil;
import com.liferay.document.library.util.DLFileEntryTypeUtil;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Adolfo Pérez
 */
public class DLFileEntryClassType extends DDMStructureClassType {

	public DLFileEntryClassType(
		long classTypeId, String classTypeName, String languageId) {

		super(classTypeId, classTypeName, languageId);
	}

	@Override
	public List<ClassTypeField> getClassTypeFields() throws PortalException {
		List<ClassTypeField> classTypeFields = new ArrayList<>();

		List<DDMStructure> ddmStructures = DLFileEntryTypeUtil.getDDMStructures(
			DLFileEntryTypeLocalServiceUtil.getDLFileEntryType(
				getClassTypeId()));

		for (DDMStructure ddmStructure : ddmStructures) {
			classTypeFields.addAll(
				getClassTypeFields(ddmStructure.getStructureId()));
		}

		return classTypeFields;
	}

}