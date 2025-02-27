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

package com.liferay.search.experiences.rest.client.serdes.v1_0;

import com.liferay.search.experiences.rest.client.dto.v1_0.QueryConfiguration;
import com.liferay.search.experiences.rest.client.dto.v1_0.QueryEntry;
import com.liferay.search.experiences.rest.client.json.BaseJSONParser;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Generated;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@Generated("")
public class QueryConfigurationSerDes {

	public static QueryConfiguration toDTO(String json) {
		QueryConfigurationJSONParser queryConfigurationJSONParser =
			new QueryConfigurationJSONParser();

		return queryConfigurationJSONParser.parseToDTO(json);
	}

	public static QueryConfiguration[] toDTOs(String json) {
		QueryConfigurationJSONParser queryConfigurationJSONParser =
			new QueryConfigurationJSONParser();

		return queryConfigurationJSONParser.parseToDTOs(json);
	}

	public static String toJSON(QueryConfiguration queryConfiguration) {
		if (queryConfiguration == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (queryConfiguration.getApplyIndexerClauses() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"applyIndexerClauses\": ");

			sb.append(queryConfiguration.getApplyIndexerClauses());
		}

		if (queryConfiguration.getQueryEntries() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"queryEntries\": ");

			sb.append("[");

			for (int i = 0; i < queryConfiguration.getQueryEntries().length;
				 i++) {

				sb.append(
					String.valueOf(queryConfiguration.getQueryEntries()[i]));

				if ((i + 1) < queryConfiguration.getQueryEntries().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		QueryConfigurationJSONParser queryConfigurationJSONParser =
			new QueryConfigurationJSONParser();

		return queryConfigurationJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		QueryConfiguration queryConfiguration) {

		if (queryConfiguration == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (queryConfiguration.getApplyIndexerClauses() == null) {
			map.put("applyIndexerClauses", null);
		}
		else {
			map.put(
				"applyIndexerClauses",
				String.valueOf(queryConfiguration.getApplyIndexerClauses()));
		}

		if (queryConfiguration.getQueryEntries() == null) {
			map.put("queryEntries", null);
		}
		else {
			map.put(
				"queryEntries",
				String.valueOf(queryConfiguration.getQueryEntries()));
		}

		return map;
	}

	public static class QueryConfigurationJSONParser
		extends BaseJSONParser<QueryConfiguration> {

		@Override
		protected QueryConfiguration createDTO() {
			return new QueryConfiguration();
		}

		@Override
		protected QueryConfiguration[] createDTOArray(int size) {
			return new QueryConfiguration[size];
		}

		@Override
		protected void setField(
			QueryConfiguration queryConfiguration, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "applyIndexerClauses")) {
				if (jsonParserFieldValue != null) {
					queryConfiguration.setApplyIndexerClauses(
						(Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "queryEntries")) {
				if (jsonParserFieldValue != null) {
					Object[] jsonParserFieldValues =
						(Object[])jsonParserFieldValue;

					QueryEntry[] queryEntriesArray =
						new QueryEntry[jsonParserFieldValues.length];

					for (int i = 0; i < queryEntriesArray.length; i++) {
						queryEntriesArray[i] = QueryEntrySerDes.toDTO(
							(String)jsonParserFieldValues[i]);
					}

					queryConfiguration.setQueryEntries(queryEntriesArray);
				}
			}
		}

	}

	private static String _escape(Object object) {
		String string = String.valueOf(object);

		for (String[] strings : BaseJSONParser.JSON_ESCAPE_STRINGS) {
			string = string.replace(strings[0], strings[1]);
		}

		return string;
	}

	private static String _toJSON(Map<String, ?> map) {
		StringBuilder sb = new StringBuilder("{");

		@SuppressWarnings("unchecked")
		Set set = map.entrySet();

		@SuppressWarnings("unchecked")
		Iterator<Map.Entry<String, ?>> iterator = set.iterator();

		while (iterator.hasNext()) {
			Map.Entry<String, ?> entry = iterator.next();

			sb.append("\"");
			sb.append(entry.getKey());
			sb.append("\": ");

			Object value = entry.getValue();

			Class<?> valueClass = value.getClass();

			if (value instanceof Map) {
				sb.append(_toJSON((Map)value));
			}
			else if (valueClass.isArray()) {
				Object[] values = (Object[])value;

				sb.append("[");

				for (int i = 0; i < values.length; i++) {
					sb.append("\"");
					sb.append(_escape(values[i]));
					sb.append("\"");

					if ((i + 1) < values.length) {
						sb.append(", ");
					}
				}

				sb.append("]");
			}
			else if (value instanceof String) {
				sb.append("\"");
				sb.append(_escape(entry.getValue()));
				sb.append("\"");
			}
			else {
				sb.append(String.valueOf(entry.getValue()));
			}

			if (iterator.hasNext()) {
				sb.append(", ");
			}
		}

		sb.append("}");

		return sb.toString();
	}

}