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

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.service.AssetCategoryLocalServiceUtil;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.service.CPDefinitionLocalServiceUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
public class CPDisplayLayoutImpl extends CPDisplayLayoutBaseImpl {

	@Override
	public AssetCategory fetchAssetCategory() {
		String className = getClassName();

		if (className.equals(AssetCategory.class.getName())) {
			return AssetCategoryLocalServiceUtil.fetchAssetCategory(
				getClassPK());
		}

		return null;
	}

	@Override
	public CPDefinition fetchCPDefinition() {
		String className = getClassName();

		if (className.equals(CPDefinition.class.getName())) {
			return CPDefinitionLocalServiceUtil.fetchCPDefinition(getClassPK());
		}

		return null;
	}

	@Override
	public Layout fetchLayout() {
		if (Validator.isNull(getLayoutUuid())) {
			return null;
		}

		Layout layout = LayoutLocalServiceUtil.fetchLayoutByUuidAndGroupId(
			getLayoutUuid(), getGroupId(), false);

		if (layout == null) {
			layout = LayoutLocalServiceUtil.fetchLayoutByUuidAndGroupId(
				getLayoutUuid(), getGroupId(), true);
		}

		return layout;
	}

}