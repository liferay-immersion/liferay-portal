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

package com.liferay.change.tracking.service.impl;

import com.liferay.change.tracking.constants.CTActionKeys;
import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.model.CTCollectionTable;
import com.liferay.change.tracking.model.CTRemote;
import com.liferay.change.tracking.model.CTRemoteTable;
import com.liferay.change.tracking.service.base.CTRemoteServiceBaseImpl;
import com.liferay.petra.sql.dsl.DSLFunctionFactoryUtil;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.dao.orm.custom.sql.CustomSQL;
import com.liferay.portal.kernel.dao.orm.WildcardMode;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = {
		"json.web.service.context.name=ct",
		"json.web.service.context.path=CTRemote"
	},
	service = AopService.class
)
public class CTRemoteServiceImpl extends CTRemoteServiceBaseImpl {

	@Override
	public CTRemote addCTRemote(String name, String description, String url)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), null, CTActionKeys.ADD_REMOTE);

		return ctRemoteLocalService.addCTRemote(
			getUserId(), name, description, url);
	}

	@Override
	public CTRemote deleteCTRemote(CTRemote ctRemote) throws PortalException {
		_ctRemoteModelResourcePermission.check(
			getPermissionChecker(), ctRemote, ActionKeys.DELETE);

		return ctRemoteLocalService.deleteCTRemote(ctRemote);
	}

	@Override
	public CTRemote deleteCTRemote(long ctRemoteId) throws PortalException {
		_ctRemoteModelResourcePermission.check(
			getPermissionChecker(), ctRemoteId, ActionKeys.DELETE);

		return ctRemoteLocalService.deleteCTRemote(ctRemoteId);
	}

	public List<CTRemote> getCTRemotes(
		String keywords, int start, int end,
		OrderByComparator<CTRemote> orderByComparator) {

		String[] keywordsArray = _customSQL.keywords(
			keywords, true, WildcardMode.SURROUND);

		DSLQuery dslQuery = DSLQueryFactoryUtil.select(
			CTRemoteTable.INSTANCE
		).from(
			CTRemoteTable.INSTANCE
		).where(
			CTRemoteTable.INSTANCE.companyId.eq(
				CompanyThreadLocal.getCompanyId()
			).and(
				Predicate.or(
					_customSQL.getKeywordsPredicate(
						DSLFunctionFactoryUtil.lower(
							CTCollectionTable.INSTANCE.name),
						keywordsArray),
					_customSQL.getKeywordsPredicate(
						DSLFunctionFactoryUtil.lower(
							CTCollectionTable.INSTANCE.description),
						keywordsArray))
			)
		).orderBy(
			CTRemoteTable.INSTANCE, orderByComparator
		).limit(
			start, end
		);

		return ctRemotePersistence.dslQuery(dslQuery);
	}

	public int getCTRemotesCount(String keywords) {
		String[] keywordsArray = _customSQL.keywords(
			keywords, true, WildcardMode.SURROUND);

		DSLQuery dslQuery = DSLQueryFactoryUtil.count(
		).from(
			CTRemoteTable.INSTANCE
		).where(
			CTRemoteTable.INSTANCE.companyId.eq(
				CompanyThreadLocal.getCompanyId()
			).and(
				Predicate.or(
					_customSQL.getKeywordsPredicate(
						DSLFunctionFactoryUtil.lower(
							CTCollectionTable.INSTANCE.name),
						keywordsArray),
					_customSQL.getKeywordsPredicate(
						DSLFunctionFactoryUtil.lower(
							CTCollectionTable.INSTANCE.description),
						keywordsArray))
			)
		);

		return ctRemotePersistence.dslQueryCount(dslQuery);
	}

	@Override
	public CTRemote updateCTRemote(
			long ctRemoteId, String name, String description, String url)
		throws PortalException {

		_ctRemoteModelResourcePermission.check(
			getPermissionChecker(), ctRemoteId, ActionKeys.UPDATE);

		return ctRemoteLocalService.updateCTRemote(
			ctRemoteId, name, description, url);
	}

	@Reference(
		target = "(model.class.name=com.liferay.change.tracking.model.CTRemote)"
	)
	private ModelResourcePermission<CTRemote> _ctRemoteModelResourcePermission;

	@Reference
	private CustomSQL _customSQL;

	@Reference(target = "(resource.name=" + CTConstants.RESOURCE_NAME + ")")
	private PortletResourcePermission _portletResourcePermission;

	@Reference
	private UserLocalService _userLocalService;

}