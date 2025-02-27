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

package com.liferay.account.service.http;

import com.liferay.account.service.AccountEntryUserRelServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>AccountEntryUserRelServiceUtil</code> service
 * utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it requires an additional
 * <code>HttpPrincipal</code> parameter.
 *
 * <p>
 * The benefits of using the HTTP utility is that it is fast and allows for
 * tunneling without the cost of serializing to text. The drawback is that it
 * only works with Java.
 * </p>
 *
 * <p>
 * Set the property <b>tunnel.servlet.hosts.allowed</b> in portal.properties to
 * configure security.
 * </p>
 *
 * <p>
 * The HTTP utility is only generated for remote services.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class AccountEntryUserRelServiceHttp {

	public static com.liferay.account.model.AccountEntryUserRel
			addAccountEntryUserRel(
				HttpPrincipal httpPrincipal, long accountEntryId,
				long creatorUserId, String screenName, String emailAddress,
				java.util.Locale locale, String firstName, String middleName,
				String lastName, long prefixListTypeId, long suffixListTypeId,
				String jobTitle,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				AccountEntryUserRelServiceUtil.class, "addAccountEntryUserRel",
				_addAccountEntryUserRelParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, accountEntryId, creatorUserId, screenName,
				emailAddress, locale, firstName, middleName, lastName,
				prefixListTypeId, suffixListTypeId, jobTitle, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.account.model.AccountEntryUserRel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.account.model.AccountEntryUserRel
			addAccountEntryUserRelByEmailAddress(
				HttpPrincipal httpPrincipal, long accountEntryId,
				String emailAddress, long[] accountRoleIds,
				String userExternalReferenceCode,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				AccountEntryUserRelServiceUtil.class,
				"addAccountEntryUserRelByEmailAddress",
				_addAccountEntryUserRelByEmailAddressParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, accountEntryId, emailAddress, accountRoleIds,
				userExternalReferenceCode, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.account.model.AccountEntryUserRel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void addAccountEntryUserRels(
			HttpPrincipal httpPrincipal, long accountEntryId,
			long[] accountUserIds)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				AccountEntryUserRelServiceUtil.class, "addAccountEntryUserRels",
				_addAccountEntryUserRelsParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, accountEntryId, accountUserIds);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.account.model.AccountEntryUserRel
			addPersonTypeAccountEntryUserRel(
				HttpPrincipal httpPrincipal, long accountEntryId,
				long creatorUserId, String screenName, String emailAddress,
				java.util.Locale locale, String firstName, String middleName,
				String lastName, long prefixListTypeId, long suffixListTypeId,
				String jobTitle,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				AccountEntryUserRelServiceUtil.class,
				"addPersonTypeAccountEntryUserRel",
				_addPersonTypeAccountEntryUserRelParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, accountEntryId, creatorUserId, screenName,
				emailAddress, locale, firstName, middleName, lastName,
				prefixListTypeId, suffixListTypeId, jobTitle, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.account.model.AccountEntryUserRel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void deleteAccountEntryUserRelByEmailAddress(
			HttpPrincipal httpPrincipal, long accountEntryId,
			String emailAddress)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				AccountEntryUserRelServiceUtil.class,
				"deleteAccountEntryUserRelByEmailAddress",
				_deleteAccountEntryUserRelByEmailAddressParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, accountEntryId, emailAddress);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void deleteAccountEntryUserRels(
			HttpPrincipal httpPrincipal, long accountEntryId,
			long[] accountUserIds)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				AccountEntryUserRelServiceUtil.class,
				"deleteAccountEntryUserRels",
				_deleteAccountEntryUserRelsParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, accountEntryId, accountUserIds);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.account.model.AccountEntryUserRel
			fetchAccountEntryUserRel(
				HttpPrincipal httpPrincipal, long accountEntryUserRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				AccountEntryUserRelServiceUtil.class,
				"fetchAccountEntryUserRel",
				_fetchAccountEntryUserRelParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, accountEntryUserRelId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.account.model.AccountEntryUserRel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.account.model.AccountEntryUserRel
			fetchAccountEntryUserRel(
				HttpPrincipal httpPrincipal, long accountEntryId,
				long accountUserId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				AccountEntryUserRelServiceUtil.class,
				"fetchAccountEntryUserRel",
				_fetchAccountEntryUserRelParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, accountEntryId, accountUserId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.account.model.AccountEntryUserRel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.account.model.AccountEntryUserRel
			getAccountEntryUserRel(
				HttpPrincipal httpPrincipal, long accountEntryId,
				long accountUserId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				AccountEntryUserRelServiceUtil.class, "getAccountEntryUserRel",
				_getAccountEntryUserRelParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, accountEntryId, accountUserId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.account.model.AccountEntryUserRel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List<com.liferay.account.model.AccountEntryUserRel>
			getAccountEntryUserRelsByAccountEntryId(
				HttpPrincipal httpPrincipal, long accountEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				AccountEntryUserRelServiceUtil.class,
				"getAccountEntryUserRelsByAccountEntryId",
				_getAccountEntryUserRelsByAccountEntryIdParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, accountEntryId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List
				<com.liferay.account.model.AccountEntryUserRel>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List<com.liferay.account.model.AccountEntryUserRel>
			getAccountEntryUserRelsByAccountEntryId(
				HttpPrincipal httpPrincipal, long accountEntryId, int start,
				int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				AccountEntryUserRelServiceUtil.class,
				"getAccountEntryUserRelsByAccountEntryId",
				_getAccountEntryUserRelsByAccountEntryIdParameterTypes10);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, accountEntryId, start, end);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List
				<com.liferay.account.model.AccountEntryUserRel>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List<com.liferay.account.model.AccountEntryUserRel>
			getAccountEntryUserRelsByAccountUserId(
				HttpPrincipal httpPrincipal, long accountUserId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				AccountEntryUserRelServiceUtil.class,
				"getAccountEntryUserRelsByAccountUserId",
				_getAccountEntryUserRelsByAccountUserIdParameterTypes11);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, accountUserId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List
				<com.liferay.account.model.AccountEntryUserRel>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static long getAccountEntryUserRelsCountByAccountEntryId(
			HttpPrincipal httpPrincipal, long accountEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				AccountEntryUserRelServiceUtil.class,
				"getAccountEntryUserRelsCountByAccountEntryId",
				_getAccountEntryUserRelsCountByAccountEntryIdParameterTypes12);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, accountEntryId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return ((Long)returnObj).longValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void inviteUser(
			HttpPrincipal httpPrincipal, long accountEntryId,
			long[] accountRoleIds, String emailAddress,
			com.liferay.portal.kernel.model.User inviter,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				AccountEntryUserRelServiceUtil.class, "inviteUser",
				_inviteUserParameterTypes13);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, accountEntryId, accountRoleIds, emailAddress,
				inviter, serviceContext);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void setPersonTypeAccountEntryUser(
			HttpPrincipal httpPrincipal, long accountEntryId, long userId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				AccountEntryUserRelServiceUtil.class,
				"setPersonTypeAccountEntryUser",
				_setPersonTypeAccountEntryUserParameterTypes14);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, accountEntryId, userId);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		AccountEntryUserRelServiceHttp.class);

	private static final Class<?>[] _addAccountEntryUserRelParameterTypes0 =
		new Class[] {
			long.class, long.class, String.class, String.class,
			java.util.Locale.class, String.class, String.class, String.class,
			long.class, long.class, String.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[]
		_addAccountEntryUserRelByEmailAddressParameterTypes1 = new Class[] {
			long.class, String.class, long[].class, String.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _addAccountEntryUserRelsParameterTypes2 =
		new Class[] {long.class, long[].class};
	private static final Class<?>[]
		_addPersonTypeAccountEntryUserRelParameterTypes3 = new Class[] {
			long.class, long.class, String.class, String.class,
			java.util.Locale.class, String.class, String.class, String.class,
			long.class, long.class, String.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[]
		_deleteAccountEntryUserRelByEmailAddressParameterTypes4 = new Class[] {
			long.class, String.class
		};
	private static final Class<?>[] _deleteAccountEntryUserRelsParameterTypes5 =
		new Class[] {long.class, long[].class};
	private static final Class<?>[] _fetchAccountEntryUserRelParameterTypes6 =
		new Class[] {long.class};
	private static final Class<?>[] _fetchAccountEntryUserRelParameterTypes7 =
		new Class[] {long.class, long.class};
	private static final Class<?>[] _getAccountEntryUserRelParameterTypes8 =
		new Class[] {long.class, long.class};
	private static final Class<?>[]
		_getAccountEntryUserRelsByAccountEntryIdParameterTypes9 = new Class[] {
			long.class
		};
	private static final Class<?>[]
		_getAccountEntryUserRelsByAccountEntryIdParameterTypes10 = new Class[] {
			long.class, int.class, int.class
		};
	private static final Class<?>[]
		_getAccountEntryUserRelsByAccountUserIdParameterTypes11 = new Class[] {
			long.class
		};
	private static final Class<?>[]
		_getAccountEntryUserRelsCountByAccountEntryIdParameterTypes12 =
			new Class[] {long.class};
	private static final Class<?>[] _inviteUserParameterTypes13 = new Class[] {
		long.class, long[].class, String.class,
		com.liferay.portal.kernel.model.User.class,
		com.liferay.portal.kernel.service.ServiceContext.class
	};
	private static final Class<?>[]
		_setPersonTypeAccountEntryUserParameterTypes14 = new Class[] {
			long.class, long.class
		};

}