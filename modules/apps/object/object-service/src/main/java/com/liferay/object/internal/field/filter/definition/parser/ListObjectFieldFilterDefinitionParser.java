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

package com.liferay.object.internal.field.filter.definition.parser;

import com.liferay.list.type.model.ListTypeEntry;
import com.liferay.list.type.service.ListTypeEntryLocalService;
import com.liferay.object.constants.ObjectViewFilterConstants;
import com.liferay.object.exception.ObjectViewFilterColumnException;
import com.liferay.object.field.filter.definition.parser.ObjectFieldFilterDefinitionParser;
import com.liferay.object.model.ObjectViewFilterColumn;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Feliphe Marinho
 */
@Component(
	immediate = true,
	property = {
		"object.field.filter.type.key=" + ObjectViewFilterConstants.FILTER_TYPE_EXCLUDES,
		"object.field.filter.type.key=" + ObjectViewFilterConstants.FILTER_TYPE_INCLUDES
	},
	service = {
		ListObjectFieldFilterDefinitionParser.class,
		ObjectFieldFilterDefinitionParser.class
	}
)
public class ListObjectFieldFilterDefinitionParser
	implements ObjectFieldFilterDefinitionParser {

	@Override
	public Map<String, Object> parse(
			long listTypeDefinitionId, Locale locale,
			ObjectViewFilterColumn objectViewFilterColumn)
		throws PortalException {

		return HashMapBuilder.<String, Object>put(
			"exclude",
			ObjectViewFilterConstants.FILTER_TYPE_EXCLUDES.equals(
				objectViewFilterColumn.getFilterType())
		).put(
			"itemsValues",
			() -> {
				JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
					objectViewFilterColumn.getDefinition());

				JSONArray jsonArray = jsonObject.getJSONArray(
					StringUtil.toLowerCase(
						objectViewFilterColumn.getFilterType()));

				if (Objects.equals(
						objectViewFilterColumn.getObjectFieldName(),
						"status")) {

					return _toIntegerArray(jsonArray);
				}

				return _toLabelValuePairs(
					jsonArray, locale, listTypeDefinitionId);
			}
		).build();
	}

	@Override
	public void validate(
			long listTypeDefinitionId,
			ObjectViewFilterColumn objectViewFilterColumn)
		throws PortalException {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			objectViewFilterColumn.getDefinition());

		JSONArray jsonArray = jsonObject.getJSONArray(
			StringUtil.toLowerCase(objectViewFilterColumn.getFilterType()));

		if (jsonArray == null) {
			throw new ObjectViewFilterColumnException(
				String.format(
					"No such property %s",
					StringUtil.toLowerCase(
						objectViewFilterColumn.getFilterType())));
		}

		try {
			if (Objects.equals(
					objectViewFilterColumn.getObjectFieldName(), "status")) {

				_toIntegerArray(jsonArray);
			}
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			String message = String.format(
				"For \"%s\" field, the value of property \"%s\" needs to be " +
					"an array of ",
				objectViewFilterColumn.getObjectFieldName(),
				StringUtil.toLowerCase(objectViewFilterColumn.getFilterType()));

			if (Objects.equals(
					objectViewFilterColumn.getObjectFieldName(), "status")) {

				throw new ObjectViewFilterColumnException(message + "numbers.");
			}

			throw new ObjectViewFilterColumnException(message + "strings.");
		}
	}

	private Integer[] _toIntegerArray(JSONArray jsonArray) {
		Integer[] newArray = new Integer[jsonArray.length()];

		for (int i = 0; i < jsonArray.length(); i++) {
			newArray[i] = (Integer)jsonArray.get(i);
		}

		return newArray;
	}

	private List<Map<String, String>> _toLabelValuePairs(
		JSONArray jsonArray, Locale locale, long listTypeDefinitionId) {

		List<Map<String, String>> preloadedValues = new ArrayList<>();

		for (int i = 0; i < jsonArray.length(); i++) {
			ListTypeEntry listTypeEntry =
				_listTypeEntryLocalService.fetchListTypeEntry(
					listTypeDefinitionId, jsonArray.getString(i));

			preloadedValues.add(
				HashMapBuilder.put(
					"label", listTypeEntry.getName(locale)
				).put(
					"value", jsonArray.getString(i)
				).build());
		}

		return preloadedValues;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ListObjectFieldFilterDefinitionParser.class);

	@Reference
	private ListTypeEntryLocalService _listTypeEntryLocalService;

}