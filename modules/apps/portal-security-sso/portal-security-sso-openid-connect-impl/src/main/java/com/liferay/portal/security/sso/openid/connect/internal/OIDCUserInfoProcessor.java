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

package com.liferay.portal.security.sso.openid.connect.internal;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.UserEmailAddressException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.ListType;
import com.liferay.portal.kernel.model.Region;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.service.AddressLocalService;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.CountryLocalService;
import com.liferay.portal.kernel.service.ListTypeLocalService;
import com.liferay.portal.kernel.service.PhoneLocalService;
import com.liferay.portal.kernel.service.RegionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.sso.openid.connect.OpenIdConnectServiceException;
import com.liferay.portal.security.sso.openid.connect.internal.exception.StrangersNotAllowedException;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Arthur Chan
 */
@Component(immediate = true, service = OIDCUserInfoProcessor.class)
public class OIDCUserInfoProcessor {

	public long processUserInfo(
			long companyId, String issuer, ServiceContext serviceContext,
			String userInfoJSON, String userInfoMapperJSON)
		throws Exception {

		long userId = _getUserId(companyId, userInfoJSON, userInfoMapperJSON);

		if (userId > 0) {
			return userId;
		}

		userId = _addUser(
			companyId, _getRoleIds(companyId, issuer), serviceContext,
			userInfoJSON, userInfoMapperJSON);

		_addAddress(serviceContext, userId, userInfoJSON, userInfoMapperJSON);

		_addPhone(serviceContext, userId, userInfoJSON, userInfoMapperJSON);

		return userId;
	}

	private void _addAddress(
			ServiceContext serviceContext, long userId, String userInfoJSON,
			String userInfoMapperJSON)
		throws Exception {

		JSONObject userInfoMapperJSONObject = _jsonFactory.createJSONObject(
			userInfoMapperJSON);

		JSONObject addressMapperJSONObject =
			userInfoMapperJSONObject.getJSONObject("address");

		if (addressMapperJSONObject == null) {
			return;
		}

		JSONObject userInfoJSONObject = _jsonFactory.createJSONObject(
			userInfoJSON);

		try {
			ListType contactAddressListType = _listTypeLocalService.getListType(
				_getClaimString(
					"addressType", addressMapperJSONObject, userInfoJSONObject),
				Contact.class.getName() + ".address");

			if (contactAddressListType == null) {

				// Type is not a must by contract, but required by Liferay

				List<ListType> contactAddressListTypes =
					_listTypeLocalService.getListTypes(
						Contact.class.getName() + ".address");

				contactAddressListType = contactAddressListTypes.get(0);
			}

			Country country = null;

			Region region = null;

			String countryClaim = _getClaimString(
				"country", addressMapperJSONObject, userInfoJSONObject);

			User user = _userLocalService.fetchUser(userId);

			if (Validator.isNotNull(countryClaim)) {
				if ((countryClaim.charAt(0) >= '0') &&
					(countryClaim.charAt(0) <= '9')) {

					country = _countryLocalService.getCountryByNumber(
						user.getCompanyId(), countryClaim);
				}
				else if (countryClaim.length() == 2) {
					country = _countryLocalService.fetchCountryByA2(
						user.getCompanyId(),
						StringUtil.toUpperCase(countryClaim));
				}
				else if (countryClaim.length() == 3) {
					country = _countryLocalService.fetchCountryByA3(
						user.getCompanyId(),
						StringUtil.toUpperCase(countryClaim));
				}
				else {
					country = _countryLocalService.fetchCountryByName(
						user.getCompanyId(),
						StringUtil.toLowerCase(countryClaim));
				}

				String regionClaim = _getClaimString(
					"region", addressMapperJSONObject, userInfoJSONObject);

				if ((country != null) && Validator.isNotNull(regionClaim)) {
					region = _regionLocalService.fetchRegion(
						country.getCountryId(),
						StringUtil.toUpperCase(regionClaim));
				}
			}

			String streetClaim = _getClaimString(
				"street", addressMapperJSONObject, userInfoJSONObject);

			String[] streetParts = streetClaim.split("\n");

			_addressLocalService.addAddress(
				null, user.getUserId(), Contact.class.getName(),
				user.getContactId(), null, null,
				(streetParts.length > 0) ? streetParts[0] : null,
				(streetParts.length > 1) ? streetParts[1] : null,
				(streetParts.length > 2) ? streetParts[2] : null,
				_getClaimString(
					"city", addressMapperJSONObject, userInfoJSONObject),
				_getClaimString(
					"zip", addressMapperJSONObject, userInfoJSONObject),
				(region == null) ? 0 : region.getRegionId(),
				(country == null) ? 0 : country.getCountryId(),
				contactAddressListType.getListTypeId(), false, false, null,
				serviceContext);
		}
		catch (Exception exception) {
			throw new Exception(
				"Unable to add address for user: " + userId, exception);
		}
	}

	private void _addPhone(
			ServiceContext serviceContext, long userId, String userInfoJSON,
			String userInfoMapperJSON)
		throws Exception {

		JSONObject userInfoMapperJSONObject = _jsonFactory.createJSONObject(
			userInfoMapperJSON);

		JSONObject phoneMapperJSONObject =
			userInfoMapperJSONObject.getJSONObject("phone");

		if (phoneMapperJSONObject == null) {
			return;
		}

		JSONObject userInfoJSONObject = _jsonFactory.createJSONObject(
			userInfoJSON);

		try {
			ListType contactPhoneListType = _listTypeLocalService.getListType(
				_getClaimString(
					"phoneType", phoneMapperJSONObject, userInfoJSONObject),
				Contact.class.getName() + ".phone");

			if (contactPhoneListType == null) {

				// Type is not a must by contract, but required by Liferay

				List<ListType> contactPhoneListTypes =
					_listTypeLocalService.getListTypes(
						Contact.class.getName() + ".phone");

				contactPhoneListType = contactPhoneListTypes.get(0);
			}

			User user = _userLocalService.fetchUser(userId);

			_phoneLocalService.addPhone(
				user.getUserId(), Contact.class.getName(), user.getContactId(),
				_getClaimString(
					"phone", phoneMapperJSONObject, userInfoJSONObject),
				null, contactPhoneListType.getListTypeId(), false,
				serviceContext);
		}
		catch (Exception exception) {
			throw new Exception(
				"Unable to add phone for user: " + userId, exception);
		}
	}

	private long _addUser(
			long companyId, long[] propertyRoleIds,
			ServiceContext serviceContext, String userInfoJSON,
			String userInfoMapperJSON)
		throws Exception {

		JSONObject userInfoJSONObject = _jsonFactory.createJSONObject(
			userInfoJSON);

		JSONObject userInfoMapperJSONObject = _jsonFactory.createJSONObject(
			userInfoMapperJSON);

		JSONObject userMapperJSONObject =
			userInfoMapperJSONObject.getJSONObject("user");

		String emailAddress = _getClaimString(
			"emailAddress", userMapperJSONObject, userInfoJSONObject);
		String firstName = _getClaimString(
			"firstName", userMapperJSONObject, userInfoJSONObject);
		String lastName = _getClaimString(
			"lastName", userMapperJSONObject, userInfoJSONObject);

		if (Validator.isNull(firstName) || Validator.isNull(lastName) ||
			Validator.isNull(emailAddress)) {

			throw new OpenIdConnectServiceException.UserMappingException(
				StringBundler.concat(
					"Unable to map OpenId Connect user to the portal, missing ",
					"or invalid profile information: {emailAddresss=",
					emailAddress, ", firstName=", firstName, ", lastName=",
					lastName, "}"));
		}

		_checkAddUser(companyId, emailAddress);

		JSONObject contactMapperJSONObject =
			userInfoMapperJSONObject.getJSONObject("contact");

		long creatorUserId = 0;
		boolean autoPassword = true;
		String password1 = null;
		String password2 = null;
		String screenName = _getClaimString(
			"screenName", userMapperJSONObject, userInfoJSONObject);
		long prefixId = 0;
		long suffixId = 0;
		int[] birthday = _getBirthday(
			contactMapperJSONObject, userInfoJSONObject);
		long[] groupIds = null;
		long[] organizationIds = null;
		long[] userGroupIds = null;
		boolean sendEmail = false;

		long[] roleIds = _getRoleIds(
			companyId, userInfoJSONObject,
			userInfoMapperJSONObject.getJSONObject("users_roles"));

		if ((roleIds == null) || (roleIds.length == 0)) {
			roleIds = propertyRoleIds;
		}

		User user = _userLocalService.addUser(
			creatorUserId, companyId, autoPassword, password1, password2,
			Validator.isNull(screenName), screenName, emailAddress,
			_getLocale(companyId, userInfoJSONObject, userMapperJSONObject),
			firstName,
			_getClaimString(
				"middleName", userMapperJSONObject, userInfoJSONObject),
			lastName, prefixId, suffixId,
			_isMale(contactMapperJSONObject, userInfoJSONObject), birthday[1],
			birthday[2], birthday[0],
			_getClaimString(
				"jobTitle", userMapperJSONObject, userInfoJSONObject),
			groupIds, organizationIds, roleIds, userGroupIds, sendEmail,
			serviceContext);

		user = _userLocalService.updatePasswordReset(user.getUserId(), false);

		return user.getUserId();
	}

	private void _checkAddUser(long companyId, String emailAddress)
		throws Exception {

		Company company = _companyLocalService.getCompany(companyId);

		if (!company.isStrangers()) {
			throw new StrangersNotAllowedException(companyId);
		}

		if (!company.isStrangersWithMx() &&
			company.hasCompanyMx(emailAddress)) {

			throw new UserEmailAddressException.MustNotUseCompanyMx(
				emailAddress);
		}
	}

	private int[] _getBirthday(
		JSONObject contactMapperJSONObject, JSONObject userInfoJSONObject) {

		int[] birthday = new int[3];

		birthday[0] = 1970;
		birthday[1] = Calendar.JANUARY;
		birthday[2] = 1;

		String birthdate;

		try {
			birthdate = _getClaimString(
				"birthdate", contactMapperJSONObject, userInfoJSONObject);

			if (Validator.isNull(birthdate)) {
				return birthday;
			}

			String[] birthdateParts = birthdate.split("-");

			if (!birthdateParts[0].equals("0000")) {
				birthday[0] = Integer.parseInt(birthdateParts[0]);
			}

			if (birthdateParts.length == 3) {
				birthday[1] = Integer.parseInt(birthdateParts[1]) - 1;
				birthday[2] = Integer.parseInt(birthdateParts[2]);
			}
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to parse user birthday, use default value");
			}
		}

		return birthday;
	}

	private JSONArray _getClaimJSONArray(
		String fieldName, JSONObject mapperJSONObject,
		JSONObject userInfoJSONObject) {

		Object claim = _getClaimObject(
			fieldName, mapperJSONObject, userInfoJSONObject);

		if ((claim == null) || (claim instanceof JSONArray)) {
			return (JSONArray)claim;
		}

		return null;
	}

	private Object _getClaimObject(
		String fieldName, JSONObject mapperJSONObject,
		JSONObject userInfoJSONObject) {

		String mappedClaim = mapperJSONObject.getString(fieldName);

		String[] mappedClaimChain = mappedClaim.split("->");

		Object claim = userInfoJSONObject.get(mappedClaimChain[0]);

		for (int i = 1; i < mappedClaimChain.length; ++i) {
			JSONObject claimJSONObject = (JSONObject)claim;

			claim = claimJSONObject.get(mappedClaimChain[i]);
		}

		return claim;
	}

	private String _getClaimString(
		String fieldName, JSONObject mapperJSONObject,
		JSONObject userInfoJSONObject) {

		Object claimObject = _getClaimObject(
			fieldName, mapperJSONObject, userInfoJSONObject);

		if ((claimObject != null) && !(claimObject instanceof String)) {
			throw new IllegalArgumentException("Claim is not a string");
		}

		return (String)claimObject;
	}

	private Locale _getLocale(
		long companyId, JSONObject userInfoJSONObject,
		JSONObject userMapperJSONObject) {

		String languageId = null;

		try {
			languageId = _getClaimString(
				"languageId", userMapperJSONObject, userInfoJSONObject);
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to get locale from userInfo", exception);
			}
		}

		if (Validator.isNotNull(languageId)) {
			return new Locale(languageId);
		}

		try {
			Company company = _companyLocalService.getCompany(companyId);

			return company.getLocale();
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to get locale from company " + companyId,
					exception);
			}

			return new Locale("en-us");
		}
	}

	private long[] _getRoleIds(
		long companyId, JSONObject userInfoJSONObject,
		JSONObject usersRolesMapperJSONObject) {

		if ((usersRolesMapperJSONObject == null) ||
			(usersRolesMapperJSONObject.length() < 1)) {

			return null;
		}

		try {
			JSONArray rolesJSONArray = _getClaimJSONArray(
				"roles", usersRolesMapperJSONObject, userInfoJSONObject);

			long[] roleIds = new long[rolesJSONArray.length()];

			for (int i = 0; i < rolesJSONArray.length(); ++i) {
				Role role = _roleLocalService.fetchRole(
					companyId, (String)rolesJSONArray.get(i));

				roleIds[i] = role.getRoleId();
			}

			return roleIds;
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to assign roles " + exception);
			}

			return null;
		}
	}

	private long[] _getRoleIds(long companyId, String issuer) {
		if (Validator.isNull(issuer) ||
			!Objects.equals(
				issuer,
				_props.get(
					"open.id.connect.user.info.processor.impl.issuer"))) {

			return null;
		}

		String roleName = _props.get(
			"open.id.connect.user.info.processor.impl.regular.role");

		if (Validator.isNull(roleName)) {
			return null;
		}

		Role role = _roleLocalService.fetchRole(companyId, roleName);

		if (role == null) {
			return null;
		}

		if (role.getType() == RoleConstants.TYPE_REGULAR) {
			return new long[] {role.getRoleId()};
		}

		if (_log.isInfoEnabled()) {
			_log.info("Role " + roleName + " is not a regular role");
		}

		return null;
	}

	private long _getUserId(
			long companyId, String userInfoJSON, String userInfoMapperJSON)
		throws Exception {

		JSONObject userInfoJSONObject = _jsonFactory.createJSONObject(
			userInfoJSON);

		JSONObject userInfoMapperJSONObject = _jsonFactory.createJSONObject(
			userInfoMapperJSON);

		JSONObject userMapperJSONObject =
			userInfoMapperJSONObject.getJSONObject("user");

		User user = _userLocalService.fetchUserByEmailAddress(
			companyId,
			_getClaimString(
				"emailAddress", userMapperJSONObject, userInfoJSONObject));

		if (user != null) {
			return user.getUserId();
		}

		return 0;
	}

	private boolean _isMale(
		JSONObject contactMapperJSONObject, JSONObject userInfoJSONObject) {

		try {
			String gender = _getClaimString(
				"gender", contactMapperJSONObject, userInfoJSONObject);

			if (Validator.isNull(gender) || gender.equals("male")) {
				return true;
			}
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to determine user gender " + exception);
			}
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		OIDCUserInfoProcessor.class);

	@Reference
	private AddressLocalService _addressLocalService;

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private CountryLocalService _countryLocalService;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private ListTypeLocalService _listTypeLocalService;

	@Reference
	private PhoneLocalService _phoneLocalService;

	@Reference
	private Props _props;

	@Reference
	private RegionLocalService _regionLocalService;

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference
	private UserLocalService _userLocalService;

}