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

package com.liferay.object.web.internal.object.entries.frontend.data.set.filter.factory;

import com.liferay.frontend.data.set.filter.FDSFilter;
import com.liferay.list.type.model.ListTypeDefinition;
import com.liferay.list.type.service.ListTypeDefinitionLocalService;
import com.liferay.object.constants.ObjectViewFilterColumnConstants;
import com.liferay.object.field.filter.parser.ObjectFieldFilterParser;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectViewFilterColumn;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.web.internal.object.entries.frontend.data.set.filter.ListTypeEntryAutocompleteFDSFilter;
import com.liferay.object.web.internal.object.entries.frontend.data.set.filter.ObjectEntryStatusCheckBoxFDSFilter;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.Locale;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Feliphe Marinho
 */
@Component(
	immediate = true,
	property = {
		"object.field.filter.type.key=" + ObjectViewFilterColumnConstants.FILTER_TYPE_EXCLUDES,
		"object.field.filter.type.key=" + ObjectViewFilterColumnConstants.FILTER_TYPE_INCLUDES
	},
	service = ObjectFieldFDSFilterFactory.class
)
public class ListTypeEntryObjectFieldFDSFilterFactory
	implements ObjectFieldFDSFilterFactory {

	public FDSFilter create(
			Locale locale, long objectDefinitionId,
			ObjectViewFilterColumn objectViewFilterColumn)
		throws PortalException {

		if (Objects.equals(
				objectViewFilterColumn.getObjectFieldName(), "status")) {

			return new ObjectEntryStatusCheckBoxFDSFilter(
				_objectFieldFilterParser.parse(
					0L, locale, objectViewFilterColumn));
		}

		ObjectField objectField = _objectFieldLocalService.fetchObjectField(
			objectDefinitionId, objectViewFilterColumn.getObjectFieldName());

		ListTypeDefinition listTypeDefinition =
			_listTypeDefinitionLocalService.getListTypeDefinition(
				objectField.getListTypeDefinitionId());

		return new ListTypeEntryAutocompleteFDSFilter(
			objectField.getName(), listTypeDefinition.getName(locale),
			objectField.getListTypeDefinitionId(),
			_objectFieldFilterParser.parse(
				objectField.getListTypeDefinitionId(), locale,
				objectViewFilterColumn));
	}

	@Reference
	private ListTypeDefinitionLocalService _listTypeDefinitionLocalService;

	@Reference(
		target = "(object.field.filter.type.key=" + ObjectViewFilterColumnConstants.FILTER_TYPE_EXCLUDES + ")"
	)
	private ObjectFieldFilterParser _objectFieldFilterParser;

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

}