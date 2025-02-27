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

package com.liferay.portal.kernel.model;

import com.liferay.portal.kernel.bean.AutoEscape;

import java.util.Date;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The base model interface for the Company service. Represents a row in the &quot;Company&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This interface and its corresponding implementation <code>com.liferay.portal.model.impl.CompanyModelImpl</code> exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in <code>com.liferay.portal.model.impl.CompanyImpl</code>.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see Company
 * @generated
 */
@ProviderType
public interface CompanyModel
	extends AuditedModel, BaseModel<Company>, MVCCModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. All methods that expect a company model instance should use the {@link Company} interface instead.
	 */

	/**
	 * Returns the primary key of this company.
	 *
	 * @return the primary key of this company
	 */
	public long getPrimaryKey();

	/**
	 * Sets the primary key of this company.
	 *
	 * @param primaryKey the primary key of this company
	 */
	public void setPrimaryKey(long primaryKey);

	/**
	 * Returns the mvcc version of this company.
	 *
	 * @return the mvcc version of this company
	 */
	@Override
	public long getMvccVersion();

	/**
	 * Sets the mvcc version of this company.
	 *
	 * @param mvccVersion the mvcc version of this company
	 */
	@Override
	public void setMvccVersion(long mvccVersion);

	/**
	 * Returns the company ID of this company.
	 *
	 * @return the company ID of this company
	 */
	@Override
	public long getCompanyId();

	/**
	 * Sets the company ID of this company.
	 *
	 * @param companyId the company ID of this company
	 */
	@Override
	public void setCompanyId(long companyId);

	/**
	 * Returns the user ID of this company.
	 *
	 * @return the user ID of this company
	 */
	@Override
	public long getUserId();

	/**
	 * Sets the user ID of this company.
	 *
	 * @param userId the user ID of this company
	 */
	@Override
	public void setUserId(long userId);

	/**
	 * Returns the user uuid of this company.
	 *
	 * @return the user uuid of this company
	 */
	@Override
	public String getUserUuid();

	/**
	 * Sets the user uuid of this company.
	 *
	 * @param userUuid the user uuid of this company
	 */
	@Override
	public void setUserUuid(String userUuid);

	/**
	 * Returns the user name of this company.
	 *
	 * @return the user name of this company
	 */
	@AutoEscape
	@Override
	public String getUserName();

	/**
	 * Sets the user name of this company.
	 *
	 * @param userName the user name of this company
	 */
	@Override
	public void setUserName(String userName);

	/**
	 * Returns the create date of this company.
	 *
	 * @return the create date of this company
	 */
	@Override
	public Date getCreateDate();

	/**
	 * Sets the create date of this company.
	 *
	 * @param createDate the create date of this company
	 */
	@Override
	public void setCreateDate(Date createDate);

	/**
	 * Returns the modified date of this company.
	 *
	 * @return the modified date of this company
	 */
	@Override
	public Date getModifiedDate();

	/**
	 * Sets the modified date of this company.
	 *
	 * @param modifiedDate the modified date of this company
	 */
	@Override
	public void setModifiedDate(Date modifiedDate);

	/**
	 * Returns the web ID of this company.
	 *
	 * @return the web ID of this company
	 */
	@AutoEscape
	public String getWebId();

	/**
	 * Sets the web ID of this company.
	 *
	 * @param webId the web ID of this company
	 */
	public void setWebId(String webId);

	/**
	 * Returns the mx of this company.
	 *
	 * @return the mx of this company
	 */
	@AutoEscape
	public String getMx();

	/**
	 * Sets the mx of this company.
	 *
	 * @param mx the mx of this company
	 */
	public void setMx(String mx);

	/**
	 * Returns the home url of this company.
	 *
	 * @return the home url of this company
	 */
	@AutoEscape
	public String getHomeURL();

	/**
	 * Sets the home url of this company.
	 *
	 * @param homeURL the home url of this company
	 */
	public void setHomeURL(String homeURL);

	/**
	 * Returns the logo ID of this company.
	 *
	 * @return the logo ID of this company
	 */
	public long getLogoId();

	/**
	 * Sets the logo ID of this company.
	 *
	 * @param logoId the logo ID of this company
	 */
	public void setLogoId(long logoId);

	/**
	 * Returns the max users of this company.
	 *
	 * @return the max users of this company
	 */
	public int getMaxUsers();

	/**
	 * Sets the max users of this company.
	 *
	 * @param maxUsers the max users of this company
	 */
	public void setMaxUsers(int maxUsers);

	/**
	 * Returns the active of this company.
	 *
	 * @return the active of this company
	 */
	public boolean getActive();

	/**
	 * Returns <code>true</code> if this company is active.
	 *
	 * @return <code>true</code> if this company is active; <code>false</code> otherwise
	 */
	public boolean isActive();

	/**
	 * Sets whether this company is active.
	 *
	 * @param active the active of this company
	 */
	public void setActive(boolean active);

	/**
	 * Returns the name of this company.
	 *
	 * @return the name of this company
	 */
	@AutoEscape
	public String getName();

	/**
	 * Sets the name of this company.
	 *
	 * @param name the name of this company
	 */
	public void setName(String name);

	/**
	 * Returns the legal name of this company.
	 *
	 * @return the legal name of this company
	 */
	@AutoEscape
	public String getLegalName();

	/**
	 * Sets the legal name of this company.
	 *
	 * @param legalName the legal name of this company
	 */
	public void setLegalName(String legalName);

	/**
	 * Returns the legal ID of this company.
	 *
	 * @return the legal ID of this company
	 */
	@AutoEscape
	public String getLegalId();

	/**
	 * Sets the legal ID of this company.
	 *
	 * @param legalId the legal ID of this company
	 */
	public void setLegalId(String legalId);

	/**
	 * Returns the legal type of this company.
	 *
	 * @return the legal type of this company
	 */
	@AutoEscape
	public String getLegalType();

	/**
	 * Sets the legal type of this company.
	 *
	 * @param legalType the legal type of this company
	 */
	public void setLegalType(String legalType);

	/**
	 * Returns the sic code of this company.
	 *
	 * @return the sic code of this company
	 */
	@AutoEscape
	public String getSicCode();

	/**
	 * Sets the sic code of this company.
	 *
	 * @param sicCode the sic code of this company
	 */
	public void setSicCode(String sicCode);

	/**
	 * Returns the ticker symbol of this company.
	 *
	 * @return the ticker symbol of this company
	 */
	@AutoEscape
	public String getTickerSymbol();

	/**
	 * Sets the ticker symbol of this company.
	 *
	 * @param tickerSymbol the ticker symbol of this company
	 */
	public void setTickerSymbol(String tickerSymbol);

	/**
	 * Returns the industry of this company.
	 *
	 * @return the industry of this company
	 */
	@AutoEscape
	public String getIndustry();

	/**
	 * Sets the industry of this company.
	 *
	 * @param industry the industry of this company
	 */
	public void setIndustry(String industry);

	/**
	 * Returns the type of this company.
	 *
	 * @return the type of this company
	 */
	@AutoEscape
	public String getType();

	/**
	 * Sets the type of this company.
	 *
	 * @param type the type of this company
	 */
	public void setType(String type);

	/**
	 * Returns the size of this company.
	 *
	 * @return the size of this company
	 */
	@AutoEscape
	public String getSize();

	/**
	 * Sets the size of this company.
	 *
	 * @param size the size of this company
	 */
	public void setSize(String size);

	/**
	 * Returns the index name current of this company.
	 *
	 * @return the index name current of this company
	 */
	@AutoEscape
	public String getIndexNameCurrent();

	/**
	 * Sets the index name current of this company.
	 *
	 * @param indexNameCurrent the index name current of this company
	 */
	public void setIndexNameCurrent(String indexNameCurrent);

	/**
	 * Returns the index name next of this company.
	 *
	 * @return the index name next of this company
	 */
	@AutoEscape
	public String getIndexNameNext();

	/**
	 * Sets the index name next of this company.
	 *
	 * @param indexNameNext the index name next of this company
	 */
	public void setIndexNameNext(String indexNameNext);

	@Override
	public Company cloneWithOriginalValues();

	public default String toXmlString() {
		return null;
	}

}