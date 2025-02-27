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

package com.liferay.headless.delivery.dto.v1_0;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;
import com.liferay.portal.vulcan.util.ObjectMapperUtil;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.annotation.Generated;

import javax.validation.Valid;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@GraphQLName(
	description = "Represents a fragment field with an action.",
	value = "FragmentFieldAction"
)
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "FragmentFieldAction")
public class FragmentFieldAction implements Serializable {

	public static FragmentFieldAction toDTO(String json) {
		return ObjectMapperUtil.readValue(FragmentFieldAction.class, json);
	}

	public static FragmentFieldAction unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(
			FragmentFieldAction.class, json);
	}

	@Schema(
		description = "The fragment field's action. Must be mapped to an external value."
	)
	@Valid
	public Object getAction() {
		return action;
	}

	public void setAction(Object action) {
		this.action = action;
	}

	@JsonIgnore
	public void setAction(
		UnsafeSupplier<Object, Exception> actionUnsafeSupplier) {

		try {
			action = actionUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(
		description = "The fragment field's action. Must be mapped to an external value."
	)
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Object action;

	@Schema(
		description = "The action execution result in case the action fails."
	)
	@Valid
	public ActionExecutionResult getOnError() {
		return onError;
	}

	public void setOnError(ActionExecutionResult onError) {
		this.onError = onError;
	}

	@JsonIgnore
	public void setOnError(
		UnsafeSupplier<ActionExecutionResult, Exception>
			onErrorUnsafeSupplier) {

		try {
			onError = onErrorUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(
		description = "The action execution result in case the action fails."
	)
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected ActionExecutionResult onError;

	@Schema(
		description = "The action execution result in case the action succeeds."
	)
	@Valid
	public ActionExecutionResult getOnSuccess() {
		return onSuccess;
	}

	public void setOnSuccess(ActionExecutionResult onSuccess) {
		this.onSuccess = onSuccess;
	}

	@JsonIgnore
	public void setOnSuccess(
		UnsafeSupplier<ActionExecutionResult, Exception>
			onSuccessUnsafeSupplier) {

		try {
			onSuccess = onSuccessUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(
		description = "The action execution result in case the action succeeds."
	)
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected ActionExecutionResult onSuccess;

	@Schema(description = "The fragment field's text.")
	@Valid
	public Object getText() {
		return text;
	}

	public void setText(Object text) {
		this.text = text;
	}

	@JsonIgnore
	public void setText(UnsafeSupplier<Object, Exception> textUnsafeSupplier) {
		try {
			text = textUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(description = "The fragment field's text.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Object text;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof FragmentFieldAction)) {
			return false;
		}

		FragmentFieldAction fragmentFieldAction = (FragmentFieldAction)object;

		return Objects.equals(toString(), fragmentFieldAction.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		if (action != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"action\": ");

			if (action instanceof Map) {
				sb.append(JSONFactoryUtil.createJSONObject((Map<?, ?>)action));
			}
			else if (action instanceof String) {
				sb.append("\"");
				sb.append(_escape((String)action));
				sb.append("\"");
			}
			else {
				sb.append(action);
			}
		}

		if (onError != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"onError\": ");

			sb.append(String.valueOf(onError));
		}

		if (onSuccess != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"onSuccess\": ");

			sb.append(String.valueOf(onSuccess));
		}

		if (text != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"text\": ");

			if (text instanceof Map) {
				sb.append(JSONFactoryUtil.createJSONObject((Map<?, ?>)text));
			}
			else if (text instanceof String) {
				sb.append("\"");
				sb.append(_escape((String)text));
				sb.append("\"");
			}
			else {
				sb.append(text);
			}
		}

		sb.append("}");

		return sb.toString();
	}

	@Schema(
		accessMode = Schema.AccessMode.READ_ONLY,
		defaultValue = "com.liferay.headless.delivery.dto.v1_0.FragmentFieldAction",
		name = "x-class-name"
	)
	public String xClassName;

	private static String _escape(Object object) {
		return StringUtil.replace(
			String.valueOf(object), _JSON_ESCAPE_STRINGS[0],
			_JSON_ESCAPE_STRINGS[1]);
	}

	private static boolean _isArray(Object value) {
		if (value == null) {
			return false;
		}

		Class<?> clazz = value.getClass();

		return clazz.isArray();
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
			sb.append(_escape(entry.getKey()));
			sb.append("\": ");

			Object value = entry.getValue();

			if (_isArray(value)) {
				sb.append("[");

				Object[] valueArray = (Object[])value;

				for (int i = 0; i < valueArray.length; i++) {
					if (valueArray[i] instanceof String) {
						sb.append("\"");
						sb.append(valueArray[i]);
						sb.append("\"");
					}
					else {
						sb.append(valueArray[i]);
					}

					if ((i + 1) < valueArray.length) {
						sb.append(", ");
					}
				}

				sb.append("]");
			}
			else if (value instanceof Map) {
				sb.append(_toJSON((Map<String, ?>)value));
			}
			else if (value instanceof String) {
				sb.append("\"");
				sb.append(_escape(value));
				sb.append("\"");
			}
			else {
				sb.append(value);
			}

			if (iterator.hasNext()) {
				sb.append(", ");
			}
		}

		sb.append("}");

		return sb.toString();
	}

	private static final String[][] _JSON_ESCAPE_STRINGS = {
		{"\\", "\"", "\b", "\f", "\n", "\r", "\t"},
		{"\\\\", "\\\"", "\\b", "\\f", "\\n", "\\r", "\\t"}
	};

}