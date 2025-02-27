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

package com.liferay.commerce.product.definitions.web.internal.frontend.data.set.provider;

import com.liferay.account.model.AccountGroupRel;
import com.liferay.account.service.AccountGroupRelLocalService;
import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.definitions.web.internal.constants.CommerceProductFDSNames;
import com.liferay.commerce.product.definitions.web.internal.model.CProductAccountGroup;
import com.liferay.commerce.product.definitions.web.internal.security.permission.resource.CommerceCatalogPermission;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.service.CPDefinitionService;
import com.liferay.frontend.data.set.provider.FDSActionProvider;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;

import java.util.List;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	property = "fds.data.provider.key=" + CommerceProductFDSNames.PRODUCT_ACCOUNT_GROUPS,
	service = FDSActionProvider.class
)
public class CommerceProductAccountGroupFDSActionProvider
	implements FDSActionProvider {

	@Override
	public List<DropdownItem> getDropdownItems(
			long groupId, HttpServletRequest httpServletRequest, Object model)
		throws PortalException {

		CProductAccountGroup cProductAccountGroup = (CProductAccountGroup)model;

		AccountGroupRel accountGroupRel =
			_accountGroupRelLocalService.getAccountGroupRel(
				cProductAccountGroup.getAccountGroupRelId());

		CPDefinition cpDefinition = _cpDefinitionService.getCPDefinition(
			accountGroupRel.getClassPK());

		return DropdownItemListBuilder.add(
			() -> CommerceCatalogPermission.contains(
				PermissionThreadLocal.getPermissionChecker(), cpDefinition,
				ActionKeys.UPDATE),
			dropdownItem -> {
				PortletURL deleteURL = _getAccountGroupDeleteURL(
					accountGroupRel, httpServletRequest);

				dropdownItem.setHref(deleteURL.toString());

				dropdownItem.setLabel(
					_language.get(httpServletRequest, "delete"));
			}
		).build();
	}

	private PortletURL _getAccountGroupDeleteURL(
		AccountGroupRel accountGroupRel,
		HttpServletRequest httpServletRequest) {

		return PortletURLBuilder.create(
			_portal.getControlPanelPortletURL(
				httpServletRequest, CPPortletKeys.CP_DEFINITIONS,
				PortletRequest.ACTION_PHASE)
		).setActionName(
			"/cp_definitions/edit_cp_definition"
		).setCMD(
			"deleteAccountGroup"
		).setRedirect(
			ParamUtil.getString(
				httpServletRequest, "currentUrl",
				_portal.getCurrentURL(httpServletRequest))
		).setParameter(
			"commerceAccountGroupRelId", accountGroupRel.getAccountGroupRelId()
		).setParameter(
			"cpDefinitionId", accountGroupRel.getClassPK()
		).buildPortletURL();
	}

	@Reference
	private AccountGroupRelLocalService _accountGroupRelLocalService;

	@Reference
	private CPDefinitionService _cpDefinitionService;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

}