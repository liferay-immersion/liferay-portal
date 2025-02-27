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

package com.liferay.portal.workflow.kaleo.forms.model.impl;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.impl.BaseModelImpl;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.workflow.kaleo.forms.model.KaleoProcessLink;
import com.liferay.portal.workflow.kaleo.forms.model.KaleoProcessLinkModel;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.sql.Blob;
import java.sql.Types;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * The base model implementation for the KaleoProcessLink service. Represents a row in the &quot;KaleoProcessLink&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface <code>KaleoProcessLinkModel</code> exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link KaleoProcessLinkImpl}.
 * </p>
 *
 * @author Marcellus Tavares
 * @see KaleoProcessLinkImpl
 * @generated
 */
public class KaleoProcessLinkModelImpl
	extends BaseModelImpl<KaleoProcessLink> implements KaleoProcessLinkModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a kaleo process link model instance should use the <code>KaleoProcessLink</code> interface instead.
	 */
	public static final String TABLE_NAME = "KaleoProcessLink";

	public static final Object[][] TABLE_COLUMNS = {
		{"kaleoProcessLinkId", Types.BIGINT}, {"companyId", Types.BIGINT},
		{"kaleoProcessId", Types.BIGINT}, {"workflowTaskName", Types.VARCHAR},
		{"DDMTemplateId", Types.BIGINT}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
		new HashMap<String, Integer>();

	static {
		TABLE_COLUMNS_MAP.put("kaleoProcessLinkId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("kaleoProcessId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("workflowTaskName", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("DDMTemplateId", Types.BIGINT);
	}

	public static final String TABLE_SQL_CREATE =
		"create table KaleoProcessLink (kaleoProcessLinkId LONG not null primary key,companyId LONG,kaleoProcessId LONG,workflowTaskName VARCHAR(75) null,DDMTemplateId LONG)";

	public static final String TABLE_SQL_DROP = "drop table KaleoProcessLink";

	public static final String ORDER_BY_JPQL =
		" ORDER BY kaleoProcessLink.kaleoProcessLinkId ASC";

	public static final String ORDER_BY_SQL =
		" ORDER BY KaleoProcessLink.kaleoProcessLinkId ASC";

	public static final String DATA_SOURCE = "liferayDataSource";

	public static final String SESSION_FACTORY = "liferaySessionFactory";

	public static final String TX_MANAGER = "liferayTransactionManager";

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getColumnBitmask(String)}
	 */
	@Deprecated
	public static final long KALEOPROCESSID_COLUMN_BITMASK = 1L;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getColumnBitmask(String)}
	 */
	@Deprecated
	public static final long WORKFLOWTASKNAME_COLUMN_BITMASK = 2L;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *		#getColumnBitmask(String)}
	 */
	@Deprecated
	public static final long KALEOPROCESSLINKID_COLUMN_BITMASK = 4L;

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public static void setEntityCacheEnabled(boolean entityCacheEnabled) {
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public static void setFinderCacheEnabled(boolean finderCacheEnabled) {
	}

	public KaleoProcessLinkModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _kaleoProcessLinkId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setKaleoProcessLinkId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _kaleoProcessLinkId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return KaleoProcessLink.class;
	}

	@Override
	public String getModelClassName() {
		return KaleoProcessLink.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		Map<String, Function<KaleoProcessLink, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		for (Map.Entry<String, Function<KaleoProcessLink, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<KaleoProcessLink, Object> attributeGetterFunction =
				entry.getValue();

			attributes.put(
				attributeName,
				attributeGetterFunction.apply((KaleoProcessLink)this));
		}

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Map<String, BiConsumer<KaleoProcessLink, Object>>
			attributeSetterBiConsumers = getAttributeSetterBiConsumers();

		for (Map.Entry<String, Object> entry : attributes.entrySet()) {
			String attributeName = entry.getKey();

			BiConsumer<KaleoProcessLink, Object> attributeSetterBiConsumer =
				attributeSetterBiConsumers.get(attributeName);

			if (attributeSetterBiConsumer != null) {
				attributeSetterBiConsumer.accept(
					(KaleoProcessLink)this, entry.getValue());
			}
		}
	}

	public Map<String, Function<KaleoProcessLink, Object>>
		getAttributeGetterFunctions() {

		return AttributeGetterFunctionsHolder._attributeGetterFunctions;
	}

	public Map<String, BiConsumer<KaleoProcessLink, Object>>
		getAttributeSetterBiConsumers() {

		return AttributeSetterBiConsumersHolder._attributeSetterBiConsumers;
	}

	private static class AttributeGetterFunctionsHolder {

		private static final Map<String, Function<KaleoProcessLink, Object>>
			_attributeGetterFunctions;

		static {
			Map<String, Function<KaleoProcessLink, Object>>
				attributeGetterFunctions =
					new LinkedHashMap
						<String, Function<KaleoProcessLink, Object>>();

			attributeGetterFunctions.put(
				"kaleoProcessLinkId", KaleoProcessLink::getKaleoProcessLinkId);
			attributeGetterFunctions.put(
				"companyId", KaleoProcessLink::getCompanyId);
			attributeGetterFunctions.put(
				"kaleoProcessId", KaleoProcessLink::getKaleoProcessId);
			attributeGetterFunctions.put(
				"workflowTaskName", KaleoProcessLink::getWorkflowTaskName);
			attributeGetterFunctions.put(
				"DDMTemplateId", KaleoProcessLink::getDDMTemplateId);

			_attributeGetterFunctions = Collections.unmodifiableMap(
				attributeGetterFunctions);
		}

	}

	private static class AttributeSetterBiConsumersHolder {

		private static final Map<String, BiConsumer<KaleoProcessLink, Object>>
			_attributeSetterBiConsumers;

		static {
			Map<String, BiConsumer<KaleoProcessLink, ?>>
				attributeSetterBiConsumers =
					new LinkedHashMap
						<String, BiConsumer<KaleoProcessLink, ?>>();

			attributeSetterBiConsumers.put(
				"kaleoProcessLinkId",
				(BiConsumer<KaleoProcessLink, Long>)
					KaleoProcessLink::setKaleoProcessLinkId);
			attributeSetterBiConsumers.put(
				"companyId",
				(BiConsumer<KaleoProcessLink, Long>)
					KaleoProcessLink::setCompanyId);
			attributeSetterBiConsumers.put(
				"kaleoProcessId",
				(BiConsumer<KaleoProcessLink, Long>)
					KaleoProcessLink::setKaleoProcessId);
			attributeSetterBiConsumers.put(
				"workflowTaskName",
				(BiConsumer<KaleoProcessLink, String>)
					KaleoProcessLink::setWorkflowTaskName);
			attributeSetterBiConsumers.put(
				"DDMTemplateId",
				(BiConsumer<KaleoProcessLink, Long>)
					KaleoProcessLink::setDDMTemplateId);

			_attributeSetterBiConsumers = Collections.unmodifiableMap(
				(Map)attributeSetterBiConsumers);
		}

	}

	@Override
	public long getKaleoProcessLinkId() {
		return _kaleoProcessLinkId;
	}

	@Override
	public void setKaleoProcessLinkId(long kaleoProcessLinkId) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_kaleoProcessLinkId = kaleoProcessLinkId;
	}

	@Override
	public long getCompanyId() {
		return _companyId;
	}

	@Override
	public void setCompanyId(long companyId) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_companyId = companyId;
	}

	@Override
	public long getKaleoProcessId() {
		return _kaleoProcessId;
	}

	@Override
	public void setKaleoProcessId(long kaleoProcessId) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_kaleoProcessId = kaleoProcessId;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getColumnOriginalValue(String)}
	 */
	@Deprecated
	public long getOriginalKaleoProcessId() {
		return GetterUtil.getLong(
			this.<Long>getColumnOriginalValue("kaleoProcessId"));
	}

	@Override
	public String getWorkflowTaskName() {
		if (_workflowTaskName == null) {
			return "";
		}
		else {
			return _workflowTaskName;
		}
	}

	@Override
	public void setWorkflowTaskName(String workflowTaskName) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_workflowTaskName = workflowTaskName;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getColumnOriginalValue(String)}
	 */
	@Deprecated
	public String getOriginalWorkflowTaskName() {
		return getColumnOriginalValue("workflowTaskName");
	}

	@Override
	public long getDDMTemplateId() {
		return _DDMTemplateId;
	}

	@Override
	public void setDDMTemplateId(long DDMTemplateId) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_DDMTemplateId = DDMTemplateId;
	}

	public long getColumnBitmask() {
		if (_columnBitmask > 0) {
			return _columnBitmask;
		}

		if ((_columnOriginalValues == null) ||
			(_columnOriginalValues == Collections.EMPTY_MAP)) {

			return 0;
		}

		for (Map.Entry<String, Object> entry :
				_columnOriginalValues.entrySet()) {

			if (!Objects.equals(
					entry.getValue(), getColumnValue(entry.getKey()))) {

				_columnBitmask |= _columnBitmasks.get(entry.getKey());
			}
		}

		return _columnBitmask;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(
			getCompanyId(), KaleoProcessLink.class.getName(), getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public KaleoProcessLink toEscapedModel() {
		if (_escapedModel == null) {
			Function<InvocationHandler, KaleoProcessLink>
				escapedModelProxyProviderFunction =
					EscapedModelProxyProviderFunctionHolder.
						_escapedModelProxyProviderFunction;

			_escapedModel = escapedModelProxyProviderFunction.apply(
				new AutoEscapeBeanHandler(this));
		}

		return _escapedModel;
	}

	@Override
	public Object clone() {
		KaleoProcessLinkImpl kaleoProcessLinkImpl = new KaleoProcessLinkImpl();

		kaleoProcessLinkImpl.setKaleoProcessLinkId(getKaleoProcessLinkId());
		kaleoProcessLinkImpl.setCompanyId(getCompanyId());
		kaleoProcessLinkImpl.setKaleoProcessId(getKaleoProcessId());
		kaleoProcessLinkImpl.setWorkflowTaskName(getWorkflowTaskName());
		kaleoProcessLinkImpl.setDDMTemplateId(getDDMTemplateId());

		kaleoProcessLinkImpl.resetOriginalValues();

		return kaleoProcessLinkImpl;
	}

	@Override
	public KaleoProcessLink cloneWithOriginalValues() {
		KaleoProcessLinkImpl kaleoProcessLinkImpl = new KaleoProcessLinkImpl();

		kaleoProcessLinkImpl.setKaleoProcessLinkId(
			this.<Long>getColumnOriginalValue("kaleoProcessLinkId"));
		kaleoProcessLinkImpl.setCompanyId(
			this.<Long>getColumnOriginalValue("companyId"));
		kaleoProcessLinkImpl.setKaleoProcessId(
			this.<Long>getColumnOriginalValue("kaleoProcessId"));
		kaleoProcessLinkImpl.setWorkflowTaskName(
			this.<String>getColumnOriginalValue("workflowTaskName"));
		kaleoProcessLinkImpl.setDDMTemplateId(
			this.<Long>getColumnOriginalValue("DDMTemplateId"));

		return kaleoProcessLinkImpl;
	}

	@Override
	public int compareTo(KaleoProcessLink kaleoProcessLink) {
		long primaryKey = kaleoProcessLink.getPrimaryKey();

		if (getPrimaryKey() < primaryKey) {
			return -1;
		}
		else if (getPrimaryKey() > primaryKey) {
			return 1;
		}
		else {
			return 0;
		}
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof KaleoProcessLink)) {
			return false;
		}

		KaleoProcessLink kaleoProcessLink = (KaleoProcessLink)object;

		long primaryKey = kaleoProcessLink.getPrimaryKey();

		if (getPrimaryKey() == primaryKey) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return (int)getPrimaryKey();
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	@Override
	public boolean isEntityCacheEnabled() {
		return true;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	@Override
	public boolean isFinderCacheEnabled() {
		return true;
	}

	@Override
	public void resetOriginalValues() {
		_columnOriginalValues = Collections.emptyMap();

		_columnBitmask = 0;
	}

	@Override
	public CacheModel<KaleoProcessLink> toCacheModel() {
		KaleoProcessLinkCacheModel kaleoProcessLinkCacheModel =
			new KaleoProcessLinkCacheModel();

		kaleoProcessLinkCacheModel.kaleoProcessLinkId = getKaleoProcessLinkId();

		kaleoProcessLinkCacheModel.companyId = getCompanyId();

		kaleoProcessLinkCacheModel.kaleoProcessId = getKaleoProcessId();

		kaleoProcessLinkCacheModel.workflowTaskName = getWorkflowTaskName();

		String workflowTaskName = kaleoProcessLinkCacheModel.workflowTaskName;

		if ((workflowTaskName != null) && (workflowTaskName.length() == 0)) {
			kaleoProcessLinkCacheModel.workflowTaskName = null;
		}

		kaleoProcessLinkCacheModel.DDMTemplateId = getDDMTemplateId();

		return kaleoProcessLinkCacheModel;
	}

	@Override
	public String toString() {
		Map<String, Function<KaleoProcessLink, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			(5 * attributeGetterFunctions.size()) + 2);

		sb.append("{");

		for (Map.Entry<String, Function<KaleoProcessLink, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<KaleoProcessLink, Object> attributeGetterFunction =
				entry.getValue();

			sb.append("\"");
			sb.append(attributeName);
			sb.append("\": ");

			Object value = attributeGetterFunction.apply(
				(KaleoProcessLink)this);

			if (value == null) {
				sb.append("null");
			}
			else if (value instanceof Blob || value instanceof Date ||
					 value instanceof Map || value instanceof String) {

				sb.append(
					"\"" + StringUtil.replace(value.toString(), "\"", "'") +
						"\"");
			}
			else {
				sb.append(value);
			}

			sb.append(", ");
		}

		if (sb.index() > 1) {
			sb.setIndex(sb.index() - 1);
		}

		sb.append("}");

		return sb.toString();
	}

	private static class EscapedModelProxyProviderFunctionHolder {

		private static final Function<InvocationHandler, KaleoProcessLink>
			_escapedModelProxyProviderFunction =
				ProxyUtil.getProxyProviderFunction(
					KaleoProcessLink.class, ModelWrapper.class);

	}

	private long _kaleoProcessLinkId;
	private long _companyId;
	private long _kaleoProcessId;
	private String _workflowTaskName;
	private long _DDMTemplateId;

	public <T> T getColumnValue(String columnName) {
		Function<KaleoProcessLink, Object> function =
			AttributeGetterFunctionsHolder._attributeGetterFunctions.get(
				columnName);

		if (function == null) {
			throw new IllegalArgumentException(
				"No attribute getter function found for " + columnName);
		}

		return (T)function.apply((KaleoProcessLink)this);
	}

	public <T> T getColumnOriginalValue(String columnName) {
		if (_columnOriginalValues == null) {
			return null;
		}

		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		return (T)_columnOriginalValues.get(columnName);
	}

	private void _setColumnOriginalValues() {
		_columnOriginalValues = new HashMap<String, Object>();

		_columnOriginalValues.put("kaleoProcessLinkId", _kaleoProcessLinkId);
		_columnOriginalValues.put("companyId", _companyId);
		_columnOriginalValues.put("kaleoProcessId", _kaleoProcessId);
		_columnOriginalValues.put("workflowTaskName", _workflowTaskName);
		_columnOriginalValues.put("DDMTemplateId", _DDMTemplateId);
	}

	private transient Map<String, Object> _columnOriginalValues;

	public static long getColumnBitmask(String columnName) {
		return _columnBitmasks.get(columnName);
	}

	private static final Map<String, Long> _columnBitmasks;

	static {
		Map<String, Long> columnBitmasks = new HashMap<>();

		columnBitmasks.put("kaleoProcessLinkId", 1L);

		columnBitmasks.put("companyId", 2L);

		columnBitmasks.put("kaleoProcessId", 4L);

		columnBitmasks.put("workflowTaskName", 8L);

		columnBitmasks.put("DDMTemplateId", 16L);

		_columnBitmasks = Collections.unmodifiableMap(columnBitmasks);
	}

	private long _columnBitmask;
	private KaleoProcessLink _escapedModel;

}