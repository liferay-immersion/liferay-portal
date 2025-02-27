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

package com.liferay.asset.list.model;

import com.liferay.portal.kernel.bean.AutoEscape;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.MVCCModel;
import com.liferay.portal.kernel.model.ShardedModel;
import com.liferay.portal.kernel.model.StagedGroupedModel;
import com.liferay.portal.kernel.model.TypedModel;
import com.liferay.portal.kernel.model.change.tracking.CTModel;

import java.util.Date;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The base model interface for the AssetListEntryUsage service. Represents a row in the &quot;AssetListEntryUsage&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This interface and its corresponding implementation <code>com.liferay.asset.list.model.impl.AssetListEntryUsageModelImpl</code> exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in <code>com.liferay.asset.list.model.impl.AssetListEntryUsageImpl</code>.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetListEntryUsage
 * @generated
 */
@ProviderType
public interface AssetListEntryUsageModel
	extends BaseModel<AssetListEntryUsage>, CTModel<AssetListEntryUsage>,
			MVCCModel, ShardedModel, StagedGroupedModel, TypedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. All methods that expect a asset list entry usage model instance should use the {@link AssetListEntryUsage} interface instead.
	 */

	/**
	 * Returns the primary key of this asset list entry usage.
	 *
	 * @return the primary key of this asset list entry usage
	 */
	@Override
	public long getPrimaryKey();

	/**
	 * Sets the primary key of this asset list entry usage.
	 *
	 * @param primaryKey the primary key of this asset list entry usage
	 */
	@Override
	public void setPrimaryKey(long primaryKey);

	/**
	 * Returns the mvcc version of this asset list entry usage.
	 *
	 * @return the mvcc version of this asset list entry usage
	 */
	@Override
	public long getMvccVersion();

	/**
	 * Sets the mvcc version of this asset list entry usage.
	 *
	 * @param mvccVersion the mvcc version of this asset list entry usage
	 */
	@Override
	public void setMvccVersion(long mvccVersion);

	/**
	 * Returns the ct collection ID of this asset list entry usage.
	 *
	 * @return the ct collection ID of this asset list entry usage
	 */
	@Override
	public long getCtCollectionId();

	/**
	 * Sets the ct collection ID of this asset list entry usage.
	 *
	 * @param ctCollectionId the ct collection ID of this asset list entry usage
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId);

	/**
	 * Returns the uuid of this asset list entry usage.
	 *
	 * @return the uuid of this asset list entry usage
	 */
	@AutoEscape
	@Override
	public String getUuid();

	/**
	 * Sets the uuid of this asset list entry usage.
	 *
	 * @param uuid the uuid of this asset list entry usage
	 */
	@Override
	public void setUuid(String uuid);

	/**
	 * Returns the asset list entry usage ID of this asset list entry usage.
	 *
	 * @return the asset list entry usage ID of this asset list entry usage
	 */
	public long getAssetListEntryUsageId();

	/**
	 * Sets the asset list entry usage ID of this asset list entry usage.
	 *
	 * @param assetListEntryUsageId the asset list entry usage ID of this asset list entry usage
	 */
	public void setAssetListEntryUsageId(long assetListEntryUsageId);

	/**
	 * Returns the group ID of this asset list entry usage.
	 *
	 * @return the group ID of this asset list entry usage
	 */
	@Override
	public long getGroupId();

	/**
	 * Sets the group ID of this asset list entry usage.
	 *
	 * @param groupId the group ID of this asset list entry usage
	 */
	@Override
	public void setGroupId(long groupId);

	/**
	 * Returns the company ID of this asset list entry usage.
	 *
	 * @return the company ID of this asset list entry usage
	 */
	@Override
	public long getCompanyId();

	/**
	 * Sets the company ID of this asset list entry usage.
	 *
	 * @param companyId the company ID of this asset list entry usage
	 */
	@Override
	public void setCompanyId(long companyId);

	/**
	 * Returns the user ID of this asset list entry usage.
	 *
	 * @return the user ID of this asset list entry usage
	 */
	@Override
	public long getUserId();

	/**
	 * Sets the user ID of this asset list entry usage.
	 *
	 * @param userId the user ID of this asset list entry usage
	 */
	@Override
	public void setUserId(long userId);

	/**
	 * Returns the user uuid of this asset list entry usage.
	 *
	 * @return the user uuid of this asset list entry usage
	 */
	@Override
	public String getUserUuid();

	/**
	 * Sets the user uuid of this asset list entry usage.
	 *
	 * @param userUuid the user uuid of this asset list entry usage
	 */
	@Override
	public void setUserUuid(String userUuid);

	/**
	 * Returns the user name of this asset list entry usage.
	 *
	 * @return the user name of this asset list entry usage
	 */
	@AutoEscape
	@Override
	public String getUserName();

	/**
	 * Sets the user name of this asset list entry usage.
	 *
	 * @param userName the user name of this asset list entry usage
	 */
	@Override
	public void setUserName(String userName);

	/**
	 * Returns the create date of this asset list entry usage.
	 *
	 * @return the create date of this asset list entry usage
	 */
	@Override
	public Date getCreateDate();

	/**
	 * Sets the create date of this asset list entry usage.
	 *
	 * @param createDate the create date of this asset list entry usage
	 */
	@Override
	public void setCreateDate(Date createDate);

	/**
	 * Returns the modified date of this asset list entry usage.
	 *
	 * @return the modified date of this asset list entry usage
	 */
	@Override
	public Date getModifiedDate();

	/**
	 * Sets the modified date of this asset list entry usage.
	 *
	 * @param modifiedDate the modified date of this asset list entry usage
	 */
	@Override
	public void setModifiedDate(Date modifiedDate);

	/**
	 * Returns the fully qualified class name of this asset list entry usage.
	 *
	 * @return the fully qualified class name of this asset list entry usage
	 */
	@Override
	public String getClassName();

	public void setClassName(String className);

	/**
	 * Returns the class name ID of this asset list entry usage.
	 *
	 * @return the class name ID of this asset list entry usage
	 */
	@Override
	public long getClassNameId();

	/**
	 * Sets the class name ID of this asset list entry usage.
	 *
	 * @param classNameId the class name ID of this asset list entry usage
	 */
	@Override
	public void setClassNameId(long classNameId);

	/**
	 * Returns the container key of this asset list entry usage.
	 *
	 * @return the container key of this asset list entry usage
	 */
	@AutoEscape
	public String getContainerKey();

	/**
	 * Sets the container key of this asset list entry usage.
	 *
	 * @param containerKey the container key of this asset list entry usage
	 */
	public void setContainerKey(String containerKey);

	/**
	 * Returns the container type of this asset list entry usage.
	 *
	 * @return the container type of this asset list entry usage
	 */
	public long getContainerType();

	/**
	 * Sets the container type of this asset list entry usage.
	 *
	 * @param containerType the container type of this asset list entry usage
	 */
	public void setContainerType(long containerType);

	/**
	 * Returns the key of this asset list entry usage.
	 *
	 * @return the key of this asset list entry usage
	 */
	@AutoEscape
	public String getKey();

	/**
	 * Sets the key of this asset list entry usage.
	 *
	 * @param key the key of this asset list entry usage
	 */
	public void setKey(String key);

	/**
	 * Returns the plid of this asset list entry usage.
	 *
	 * @return the plid of this asset list entry usage
	 */
	public long getPlid();

	/**
	 * Sets the plid of this asset list entry usage.
	 *
	 * @param plid the plid of this asset list entry usage
	 */
	public void setPlid(long plid);

	/**
	 * Returns the type of this asset list entry usage.
	 *
	 * @return the type of this asset list entry usage
	 */
	public int getType();

	/**
	 * Sets the type of this asset list entry usage.
	 *
	 * @param type the type of this asset list entry usage
	 */
	public void setType(int type);

	/**
	 * Returns the last publish date of this asset list entry usage.
	 *
	 * @return the last publish date of this asset list entry usage
	 */
	@Override
	public Date getLastPublishDate();

	/**
	 * Sets the last publish date of this asset list entry usage.
	 *
	 * @param lastPublishDate the last publish date of this asset list entry usage
	 */
	@Override
	public void setLastPublishDate(Date lastPublishDate);

	@Override
	public AssetListEntryUsage cloneWithOriginalValues();

	public default String toXmlString() {
		return null;
	}

}