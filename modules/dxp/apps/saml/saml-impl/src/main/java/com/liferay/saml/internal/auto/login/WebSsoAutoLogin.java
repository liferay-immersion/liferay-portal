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

package com.liferay.saml.internal.auto.login;

import com.liferay.portal.kernel.cookies.CookiesManagerUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auto.login.AutoLogin;
import com.liferay.portal.kernel.security.auto.login.AutoLoginException;
import com.liferay.portal.kernel.security.auto.login.BaseAutoLogin;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.saml.constants.SamlWebKeys;
import com.liferay.saml.persistence.model.SamlIdpSsoSession;
import com.liferay.saml.persistence.service.SamlIdpSsoSessionLocalService;
import com.liferay.saml.runtime.configuration.SamlProviderConfigurationHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mika Koivisto
 */
@Component(service = AutoLogin.class)
public class WebSsoAutoLogin extends BaseAutoLogin {

	@Override
	protected String[] doLogin(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws AutoLoginException {

		try {
			if (!_samlProviderConfigurationHelper.isEnabled() ||
				!_samlProviderConfigurationHelper.isRoleIdp()) {

				return null;
			}

			String samlSsoSessionId = CookiesManagerUtil.getCookieValue(
				SamlWebKeys.SAML_SSO_SESSION_ID, httpServletRequest);

			if (Validator.isNull(samlSsoSessionId)) {
				return null;
			}

			HttpSession httpSession = httpServletRequest.getSession(false);

			if (httpSession != null) {
				boolean forceReauthentication = GetterUtil.getBoolean(
					httpSession.getAttribute(
						SamlWebKeys.FORCE_REAUTHENTICATION));

				if (forceReauthentication) {
					return null;
				}
			}

			SamlIdpSsoSession samlIdpSsoSession =
				_samlIdpSsoSessionLocalService.fetchSamlIdpSso(
					samlSsoSessionId);

			if ((samlIdpSsoSession == null) || samlIdpSsoSession.isExpired()) {
				return null;
			}

			User user = _userLocalService.fetchUserById(
				samlIdpSsoSession.getUserId());

			if (user == null) {
				return null;
			}

			String[] credentials = new String[3];

			credentials[0] = String.valueOf(user.getUserId());
			credentials[1] = user.getPassword();
			credentials[2] = Boolean.TRUE.toString();

			return credentials;
		}
		catch (Exception exception) {
			throw new AutoLoginException(exception);
		}
	}

	@Reference
	private SamlIdpSsoSessionLocalService _samlIdpSsoSessionLocalService;

	@Reference
	private SamlProviderConfigurationHelper _samlProviderConfigurationHelper;

	@Reference
	private UserLocalService _userLocalService;

}