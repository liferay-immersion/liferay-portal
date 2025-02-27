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

package com.liferay.osb.faro.model.impl;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;
import com.liferay.osb.faro.model.FaroPreferences;
import com.liferay.osb.faro.model.FaroPreferencesModel;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.impl.BaseModelImpl;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringUtil;

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
 * The base model implementation for the FaroPreferences service. Represents a row in the &quot;OSBFaro_FaroPreferences&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface <code>FaroPreferencesModel</code> exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link FaroPreferencesImpl}.
 * </p>
 *
 * @author Matthew Kong
 * @see FaroPreferencesImpl
 * @generated
 */
public class FaroPreferencesModelImpl
	extends BaseModelImpl<FaroPreferences> implements FaroPreferencesModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a faro preferences model instance should use the <code>FaroPreferences</code> interface instead.
	 */
	public static final String TABLE_NAME = "OSBFaro_FaroPreferences";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT}, {"faroPreferencesId", Types.BIGINT},
		{"groupId", Types.BIGINT}, {"companyId", Types.BIGINT},
		{"userId", Types.BIGINT}, {"userName", Types.VARCHAR},
		{"createTime", Types.BIGINT}, {"modifiedTime", Types.BIGINT},
		{"ownerId", Types.BIGINT}, {"preferences", Types.VARCHAR}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
		new HashMap<String, Integer>();

	static {
		TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("faroPreferencesId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("createTime", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("modifiedTime", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("ownerId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("preferences", Types.VARCHAR);
	}

	public static final String TABLE_SQL_CREATE =
		"create table OSBFaro_FaroPreferences (mvccVersion LONG default 0 not null,faroPreferencesId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createTime LONG,modifiedTime LONG,ownerId LONG,preferences STRING null)";

	public static final String TABLE_SQL_DROP =
		"drop table OSBFaro_FaroPreferences";

	public static final String ORDER_BY_JPQL =
		" ORDER BY faroPreferences.faroPreferencesId ASC";

	public static final String ORDER_BY_SQL =
		" ORDER BY OSBFaro_FaroPreferences.faroPreferencesId ASC";

	public static final String DATA_SOURCE = "liferayDataSource";

	public static final String SESSION_FACTORY = "liferaySessionFactory";

	public static final String TX_MANAGER = "liferayTransactionManager";

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getColumnBitmask(String)}
	 */
	@Deprecated
	public static final long GROUPID_COLUMN_BITMASK = 1L;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getColumnBitmask(String)}
	 */
	@Deprecated
	public static final long OWNERID_COLUMN_BITMASK = 2L;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *		#getColumnBitmask(String)}
	 */
	@Deprecated
	public static final long FAROPREFERENCESID_COLUMN_BITMASK = 4L;

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

	public FaroPreferencesModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _faroPreferencesId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setFaroPreferencesId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _faroPreferencesId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return FaroPreferences.class;
	}

	@Override
	public String getModelClassName() {
		return FaroPreferences.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		Map<String, Function<FaroPreferences, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		for (Map.Entry<String, Function<FaroPreferences, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<FaroPreferences, Object> attributeGetterFunction =
				entry.getValue();

			attributes.put(
				attributeName,
				attributeGetterFunction.apply((FaroPreferences)this));
		}

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Map<String, BiConsumer<FaroPreferences, Object>>
			attributeSetterBiConsumers = getAttributeSetterBiConsumers();

		for (Map.Entry<String, Object> entry : attributes.entrySet()) {
			String attributeName = entry.getKey();

			BiConsumer<FaroPreferences, Object> attributeSetterBiConsumer =
				attributeSetterBiConsumers.get(attributeName);

			if (attributeSetterBiConsumer != null) {
				attributeSetterBiConsumer.accept(
					(FaroPreferences)this, entry.getValue());
			}
		}
	}

	public Map<String, Function<FaroPreferences, Object>>
		getAttributeGetterFunctions() {

		return AttributeGetterFunctionsHolder._attributeGetterFunctions;
	}

	public Map<String, BiConsumer<FaroPreferences, Object>>
		getAttributeSetterBiConsumers() {

		return AttributeSetterBiConsumersHolder._attributeSetterBiConsumers;
	}

	private static class AttributeGetterFunctionsHolder {

		private static final Map<String, Function<FaroPreferences, Object>>
			_attributeGetterFunctions;

		static {
			Map<String, Function<FaroPreferences, Object>>
				attributeGetterFunctions =
					new LinkedHashMap
						<String, Function<FaroPreferences, Object>>();

			attributeGetterFunctions.put(
				"mvccVersion", FaroPreferences::getMvccVersion);
			attributeGetterFunctions.put(
				"faroPreferencesId", FaroPreferences::getFaroPreferencesId);
			attributeGetterFunctions.put(
				"groupId", FaroPreferences::getGroupId);
			attributeGetterFunctions.put(
				"companyId", FaroPreferences::getCompanyId);
			attributeGetterFunctions.put("userId", FaroPreferences::getUserId);
			attributeGetterFunctions.put(
				"userName", FaroPreferences::getUserName);
			attributeGetterFunctions.put(
				"createTime", FaroPreferences::getCreateTime);
			attributeGetterFunctions.put(
				"modifiedTime", FaroPreferences::getModifiedTime);
			attributeGetterFunctions.put(
				"ownerId", FaroPreferences::getOwnerId);
			attributeGetterFunctions.put(
				"preferences", FaroPreferences::getPreferences);

			_attributeGetterFunctions = Collections.unmodifiableMap(
				attributeGetterFunctions);
		}

	}

	private static class AttributeSetterBiConsumersHolder {

		private static final Map<String, BiConsumer<FaroPreferences, Object>>
			_attributeSetterBiConsumers;

		static {
			Map<String, BiConsumer<FaroPreferences, ?>>
				attributeSetterBiConsumers =
					new LinkedHashMap<String, BiConsumer<FaroPreferences, ?>>();

			attributeSetterBiConsumers.put(
				"mvccVersion",
				(BiConsumer<FaroPreferences, Long>)
					FaroPreferences::setMvccVersion);
			attributeSetterBiConsumers.put(
				"faroPreferencesId",
				(BiConsumer<FaroPreferences, Long>)
					FaroPreferences::setFaroPreferencesId);
			attributeSetterBiConsumers.put(
				"groupId",
				(BiConsumer<FaroPreferences, Long>)FaroPreferences::setGroupId);
			attributeSetterBiConsumers.put(
				"companyId",
				(BiConsumer<FaroPreferences, Long>)
					FaroPreferences::setCompanyId);
			attributeSetterBiConsumers.put(
				"userId",
				(BiConsumer<FaroPreferences, Long>)FaroPreferences::setUserId);
			attributeSetterBiConsumers.put(
				"userName",
				(BiConsumer<FaroPreferences, String>)
					FaroPreferences::setUserName);
			attributeSetterBiConsumers.put(
				"createTime",
				(BiConsumer<FaroPreferences, Long>)
					FaroPreferences::setCreateTime);
			attributeSetterBiConsumers.put(
				"modifiedTime",
				(BiConsumer<FaroPreferences, Long>)
					FaroPreferences::setModifiedTime);
			attributeSetterBiConsumers.put(
				"ownerId",
				(BiConsumer<FaroPreferences, Long>)FaroPreferences::setOwnerId);
			attributeSetterBiConsumers.put(
				"preferences",
				(BiConsumer<FaroPreferences, String>)
					FaroPreferences::setPreferences);

			_attributeSetterBiConsumers = Collections.unmodifiableMap(
				(Map)attributeSetterBiConsumers);
		}

	}

	@Override
	public long getMvccVersion() {
		return _mvccVersion;
	}

	@Override
	public void setMvccVersion(long mvccVersion) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_mvccVersion = mvccVersion;
	}

	@Override
	public long getFaroPreferencesId() {
		return _faroPreferencesId;
	}

	@Override
	public void setFaroPreferencesId(long faroPreferencesId) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_faroPreferencesId = faroPreferencesId;
	}

	@Override
	public long getGroupId() {
		return _groupId;
	}

	@Override
	public void setGroupId(long groupId) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_groupId = groupId;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getColumnOriginalValue(String)}
	 */
	@Deprecated
	public long getOriginalGroupId() {
		return GetterUtil.getLong(this.<Long>getColumnOriginalValue("groupId"));
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
	public long getUserId() {
		return _userId;
	}

	@Override
	public void setUserId(long userId) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_userId = userId;
	}

	@Override
	public String getUserUuid() {
		try {
			User user = UserLocalServiceUtil.getUserById(getUserId());

			return user.getUuid();
		}
		catch (PortalException portalException) {
			return "";
		}
	}

	@Override
	public void setUserUuid(String userUuid) {
	}

	@Override
	public String getUserName() {
		if (_userName == null) {
			return "";
		}
		else {
			return _userName;
		}
	}

	@Override
	public void setUserName(String userName) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_userName = userName;
	}

	@Override
	public long getCreateTime() {
		return _createTime;
	}

	@Override
	public void setCreateTime(long createTime) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_createTime = createTime;
	}

	@Override
	public long getModifiedTime() {
		return _modifiedTime;
	}

	@Override
	public void setModifiedTime(long modifiedTime) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_modifiedTime = modifiedTime;
	}

	@Override
	public long getOwnerId() {
		return _ownerId;
	}

	@Override
	public void setOwnerId(long ownerId) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_ownerId = ownerId;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getColumnOriginalValue(String)}
	 */
	@Deprecated
	public long getOriginalOwnerId() {
		return GetterUtil.getLong(this.<Long>getColumnOriginalValue("ownerId"));
	}

	@Override
	public String getPreferences() {
		if (_preferences == null) {
			return "";
		}
		else {
			return _preferences;
		}
	}

	@Override
	public void setPreferences(String preferences) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_preferences = preferences;
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
			getCompanyId(), FaroPreferences.class.getName(), getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public FaroPreferences toEscapedModel() {
		if (_escapedModel == null) {
			Function<InvocationHandler, FaroPreferences>
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
		FaroPreferencesImpl faroPreferencesImpl = new FaroPreferencesImpl();

		faroPreferencesImpl.setMvccVersion(getMvccVersion());
		faroPreferencesImpl.setFaroPreferencesId(getFaroPreferencesId());
		faroPreferencesImpl.setGroupId(getGroupId());
		faroPreferencesImpl.setCompanyId(getCompanyId());
		faroPreferencesImpl.setUserId(getUserId());
		faroPreferencesImpl.setUserName(getUserName());
		faroPreferencesImpl.setCreateTime(getCreateTime());
		faroPreferencesImpl.setModifiedTime(getModifiedTime());
		faroPreferencesImpl.setOwnerId(getOwnerId());
		faroPreferencesImpl.setPreferences(getPreferences());

		faroPreferencesImpl.resetOriginalValues();

		return faroPreferencesImpl;
	}

	@Override
	public FaroPreferences cloneWithOriginalValues() {
		FaroPreferencesImpl faroPreferencesImpl = new FaroPreferencesImpl();

		faroPreferencesImpl.setMvccVersion(
			this.<Long>getColumnOriginalValue("mvccVersion"));
		faroPreferencesImpl.setFaroPreferencesId(
			this.<Long>getColumnOriginalValue("faroPreferencesId"));
		faroPreferencesImpl.setGroupId(
			this.<Long>getColumnOriginalValue("groupId"));
		faroPreferencesImpl.setCompanyId(
			this.<Long>getColumnOriginalValue("companyId"));
		faroPreferencesImpl.setUserId(
			this.<Long>getColumnOriginalValue("userId"));
		faroPreferencesImpl.setUserName(
			this.<String>getColumnOriginalValue("userName"));
		faroPreferencesImpl.setCreateTime(
			this.<Long>getColumnOriginalValue("createTime"));
		faroPreferencesImpl.setModifiedTime(
			this.<Long>getColumnOriginalValue("modifiedTime"));
		faroPreferencesImpl.setOwnerId(
			this.<Long>getColumnOriginalValue("ownerId"));
		faroPreferencesImpl.setPreferences(
			this.<String>getColumnOriginalValue("preferences"));

		return faroPreferencesImpl;
	}

	@Override
	public int compareTo(FaroPreferences faroPreferences) {
		long primaryKey = faroPreferences.getPrimaryKey();

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

		if (!(object instanceof FaroPreferences)) {
			return false;
		}

		FaroPreferences faroPreferences = (FaroPreferences)object;

		long primaryKey = faroPreferences.getPrimaryKey();

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
	public CacheModel<FaroPreferences> toCacheModel() {
		FaroPreferencesCacheModel faroPreferencesCacheModel =
			new FaroPreferencesCacheModel();

		faroPreferencesCacheModel.mvccVersion = getMvccVersion();

		faroPreferencesCacheModel.faroPreferencesId = getFaroPreferencesId();

		faroPreferencesCacheModel.groupId = getGroupId();

		faroPreferencesCacheModel.companyId = getCompanyId();

		faroPreferencesCacheModel.userId = getUserId();

		faroPreferencesCacheModel.userName = getUserName();

		String userName = faroPreferencesCacheModel.userName;

		if ((userName != null) && (userName.length() == 0)) {
			faroPreferencesCacheModel.userName = null;
		}

		faroPreferencesCacheModel.createTime = getCreateTime();

		faroPreferencesCacheModel.modifiedTime = getModifiedTime();

		faroPreferencesCacheModel.ownerId = getOwnerId();

		faroPreferencesCacheModel.preferences = getPreferences();

		String preferences = faroPreferencesCacheModel.preferences;

		if ((preferences != null) && (preferences.length() == 0)) {
			faroPreferencesCacheModel.preferences = null;
		}

		return faroPreferencesCacheModel;
	}

	@Override
	public String toString() {
		Map<String, Function<FaroPreferences, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			(5 * attributeGetterFunctions.size()) + 2);

		sb.append("{");

		for (Map.Entry<String, Function<FaroPreferences, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<FaroPreferences, Object> attributeGetterFunction =
				entry.getValue();

			sb.append("\"");
			sb.append(attributeName);
			sb.append("\": ");

			Object value = attributeGetterFunction.apply((FaroPreferences)this);

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

		private static final Function<InvocationHandler, FaroPreferences>
			_escapedModelProxyProviderFunction =
				ProxyUtil.getProxyProviderFunction(
					FaroPreferences.class, ModelWrapper.class);

	}

	private long _mvccVersion;
	private long _faroPreferencesId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private long _createTime;
	private long _modifiedTime;
	private long _ownerId;
	private String _preferences;

	public <T> T getColumnValue(String columnName) {
		Function<FaroPreferences, Object> function =
			AttributeGetterFunctionsHolder._attributeGetterFunctions.get(
				columnName);

		if (function == null) {
			throw new IllegalArgumentException(
				"No attribute getter function found for " + columnName);
		}

		return (T)function.apply((FaroPreferences)this);
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

		_columnOriginalValues.put("mvccVersion", _mvccVersion);
		_columnOriginalValues.put("faroPreferencesId", _faroPreferencesId);
		_columnOriginalValues.put("groupId", _groupId);
		_columnOriginalValues.put("companyId", _companyId);
		_columnOriginalValues.put("userId", _userId);
		_columnOriginalValues.put("userName", _userName);
		_columnOriginalValues.put("createTime", _createTime);
		_columnOriginalValues.put("modifiedTime", _modifiedTime);
		_columnOriginalValues.put("ownerId", _ownerId);
		_columnOriginalValues.put("preferences", _preferences);
	}

	private transient Map<String, Object> _columnOriginalValues;

	public static long getColumnBitmask(String columnName) {
		return _columnBitmasks.get(columnName);
	}

	private static final Map<String, Long> _columnBitmasks;

	static {
		Map<String, Long> columnBitmasks = new HashMap<>();

		columnBitmasks.put("mvccVersion", 1L);

		columnBitmasks.put("faroPreferencesId", 2L);

		columnBitmasks.put("groupId", 4L);

		columnBitmasks.put("companyId", 8L);

		columnBitmasks.put("userId", 16L);

		columnBitmasks.put("userName", 32L);

		columnBitmasks.put("createTime", 64L);

		columnBitmasks.put("modifiedTime", 128L);

		columnBitmasks.put("ownerId", 256L);

		columnBitmasks.put("preferences", 512L);

		_columnBitmasks = Collections.unmodifiableMap(columnBitmasks);
	}

	private long _columnBitmask;
	private FaroPreferences _escapedModel;

}