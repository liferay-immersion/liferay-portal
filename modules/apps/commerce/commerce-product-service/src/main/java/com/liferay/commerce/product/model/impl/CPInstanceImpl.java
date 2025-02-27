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

package com.liferay.commerce.product.model.impl;

import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstanceUnitOfMeasure;
import com.liferay.commerce.product.model.CPSubscriptionInfo;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.service.CPDefinitionLocalServiceUtil;
import com.liferay.commerce.product.service.CPInstanceUnitOfMeasureLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;

import java.util.List;

/**
 * @author Marco Leo
 * @author Andrea Di Giorgi
 * @author Alessio Antonio Rendina
 * @author Luca Pellizzon
 */
public class CPInstanceImpl extends CPInstanceBaseImpl {

	@Override
	public CommerceCatalog getCommerceCatalog() throws PortalException {
		CPDefinition cpDefinition = getCPDefinition();

		return cpDefinition.getCommerceCatalog();
	}

	@Override
	public CPDefinition getCPDefinition() throws PortalException {
		return CPDefinitionLocalServiceUtil.getCPDefinition(
			getCPDefinitionId());
	}

	@Override
	public CPInstanceUnitOfMeasure getCPInstanceUnitOfMeasure(String key)
		throws PortalException {

		return CPInstanceUnitOfMeasureLocalServiceUtil.
			getCPInstanceUnitOfMeasure(getCPInstanceId(), key);
	}

	@Override
	public List<CPInstanceUnitOfMeasure> getCPInstanceUnitOfMeasures(
		int start, int end,
		OrderByComparator<CPInstanceUnitOfMeasure> orderByComparator) {

		return CPInstanceUnitOfMeasureLocalServiceUtil.
			getCPInstanceUnitOfMeasures(
				getCPInstanceId(), start, end, orderByComparator);
	}

	@Override
	public CPSubscriptionInfo getCPSubscriptionInfo() throws PortalException {
		if (isOverrideSubscriptionInfo() &&
			(isSubscriptionEnabled() || isDeliverySubscriptionEnabled())) {

			return new CPSubscriptionInfo(
				getSubscriptionLength(), getSubscriptionType(),
				getSubscriptionTypeSettingsUnicodeProperties(),
				getMaxSubscriptionCycles(), getDeliverySubscriptionLength(),
				getDeliverySubscriptionType(),
				getDeliverySubscriptionTypeSettingsUnicodeProperties(),
				getDeliveryMaxSubscriptionCycles());
		}
		else if (!isOverrideSubscriptionInfo()) {
			CPDefinition cpDefinition = getCPDefinition();

			if (cpDefinition.isSubscriptionEnabled() ||
				cpDefinition.isDeliverySubscriptionEnabled()) {

				return new CPSubscriptionInfo(
					cpDefinition.getSubscriptionLength(),
					cpDefinition.getSubscriptionType(),
					cpDefinition.getSubscriptionTypeSettingsUnicodeProperties(),
					cpDefinition.getMaxSubscriptionCycles(),
					cpDefinition.getDeliverySubscriptionLength(),
					cpDefinition.getDeliverySubscriptionType(),
					cpDefinition.
						getDeliverySubscriptionTypeSettingsUnicodeProperties(),
					cpDefinition.getDeliveryMaxSubscriptionCycles());
			}
		}

		return null;
	}

	@Override
	public UnicodeProperties
		getDeliverySubscriptionTypeSettingsUnicodeProperties() {

		if (_deliverySubscriptionTypeSettingsUnicodeProperties == null) {
			_deliverySubscriptionTypeSettingsUnicodeProperties =
				UnicodePropertiesBuilder.create(
					true
				).fastLoad(
					getDeliverySubscriptionTypeSettings()
				).build();
		}

		return _deliverySubscriptionTypeSettingsUnicodeProperties;
	}

	@Override
	public UnicodeProperties getSubscriptionTypeSettingsUnicodeProperties() {
		if (_subscriptionTypeSettingsUnicodeProperties == null) {
			_subscriptionTypeSettingsUnicodeProperties =
				UnicodePropertiesBuilder.create(
					true
				).fastLoad(
					getSubscriptionTypeSettings()
				).build();
		}

		return _subscriptionTypeSettingsUnicodeProperties;
	}

	@Override
	public void setDeliverySubscriptionTypeSettings(
		String subscriptionTypeSettings) {

		super.setDeliverySubscriptionTypeSettings(subscriptionTypeSettings);

		_deliverySubscriptionTypeSettingsUnicodeProperties = null;
	}

	@Override
	public void setDeliverySubscriptionTypeSettingsUnicodeProperties(
		UnicodeProperties deliverySubscriptionTypeSettingsUnicodeProperties) {

		_deliverySubscriptionTypeSettingsUnicodeProperties =
			deliverySubscriptionTypeSettingsUnicodeProperties;

		if (_deliverySubscriptionTypeSettingsUnicodeProperties == null) {
			_deliverySubscriptionTypeSettingsUnicodeProperties =
				new UnicodeProperties();
		}

		super.setDeliverySubscriptionTypeSettings(
			_deliverySubscriptionTypeSettingsUnicodeProperties.toString());
	}

	@Override
	public void setSubscriptionTypeSettings(String subscriptionTypeSettings) {
		super.setSubscriptionTypeSettings(subscriptionTypeSettings);

		_subscriptionTypeSettingsUnicodeProperties = null;
	}

	@Override
	public void setSubscriptionTypeSettingsUnicodeProperties(
		UnicodeProperties subscriptionTypeSettingsUnicodeProperties) {

		_subscriptionTypeSettingsUnicodeProperties =
			subscriptionTypeSettingsUnicodeProperties;

		if (_subscriptionTypeSettingsUnicodeProperties == null) {
			_subscriptionTypeSettingsUnicodeProperties =
				new UnicodeProperties();
		}

		super.setSubscriptionTypeSettings(
			_subscriptionTypeSettingsUnicodeProperties.toString());
	}

	private UnicodeProperties
		_deliverySubscriptionTypeSettingsUnicodeProperties;
	private UnicodeProperties _subscriptionTypeSettingsUnicodeProperties;

}