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

package com.liferay.portal.security.auto.login.remember.me;

import com.liferay.portal.kernel.cookies.CookiesManagerUtil;
import com.liferay.portal.kernel.cookies.constants.CookiesConstants;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auto.login.AutoLogin;
import com.liferay.portal.kernel.security.auto.login.AutoLoginException;
import com.liferay.portal.kernel.security.auto.login.BaseAutoLogin;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(service = AutoLogin.class)
public class RememberMeAutoLogin extends BaseAutoLogin {

	@Override
	protected String[] doHandleException(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, Exception exception)
		throws AutoLoginException {

		if (_log.isDebugEnabled()) {
			_log.debug(exception);
		}

		removeCookies(httpServletRequest, httpServletResponse);

		throw new AutoLoginException(exception);
	}

	@Override
	protected String[] doLogin(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		String autoUserId = CookiesManagerUtil.getCookieValue(
			CookiesConstants.NAME_ID, httpServletRequest, false);
		String autoPassword = CookiesManagerUtil.getCookieValue(
			CookiesConstants.NAME_PASSWORD, httpServletRequest, false);
		String rememberMe = CookiesManagerUtil.getCookieValue(
			CookiesConstants.NAME_REMEMBER_ME, httpServletRequest, false);

		// LEP-5188

		String proxyPath = _portal.getPathProxy();
		String contextPath = _portal.getPathContext();

		if (proxyPath.equals(contextPath)) {
			if (Validator.isNotNull(httpServletRequest.getContextPath())) {
				rememberMe = Boolean.TRUE.toString();
			}
		}
		else {
			if (!contextPath.equals(httpServletRequest.getContextPath())) {
				rememberMe = Boolean.TRUE.toString();
			}
		}

		String[] credentials = null;

		if (Validator.isNotNull(autoUserId) &&
			Validator.isNotNull(autoPassword) &&
			Validator.isNotNull(rememberMe)) {

			Company company = _portal.getCompany(httpServletRequest);

			if (company.isAutoLogin()) {
				KeyValuePair kvp = _userLocalService.decryptUserId(
					company.getCompanyId(), autoUserId, autoPassword);

				credentials = new String[3];

				credentials[0] = kvp.getKey();
				credentials[1] = kvp.getValue();
				credentials[2] = Boolean.FALSE.toString();
			}
		}

		// LPS-11218

		if (credentials != null) {
			Company company = _portal.getCompany(httpServletRequest);

			User guestUser = _userLocalService.getGuestUser(
				company.getCompanyId());

			long userId = GetterUtil.getLong(credentials[0]);

			if (guestUser.getUserId() == userId) {
				removeCookies(httpServletRequest, httpServletResponse);

				return null;
			}
		}

		return credentials;
	}

	protected void removeCookies(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		String domain = CookiesManagerUtil.getDomain(httpServletRequest);

		CookiesManagerUtil.deleteCookies(
			domain, httpServletRequest, httpServletResponse,
			CookiesConstants.NAME_ID);
		CookiesManagerUtil.deleteCookies(
			domain, httpServletRequest, httpServletResponse,
			CookiesConstants.NAME_PASSWORD);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		RememberMeAutoLogin.class);

	@Reference
	private Portal _portal;

	@Reference
	private UserLocalService _userLocalService;

}