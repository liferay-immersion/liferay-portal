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

package com.liferay.portal.service.persistence.impl;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.NoSuchUserTrackerException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.UserTracker;
import com.liferay.portal.kernel.model.UserTrackerTable;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.UserTrackerPersistence;
import com.liferay.portal.kernel.service.persistence.UserTrackerUtil;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.model.impl.UserTrackerImpl;
import com.liferay.portal.model.impl.UserTrackerModelImpl;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the user tracker service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class UserTrackerPersistenceImpl
	extends BasePersistenceImpl<UserTracker> implements UserTrackerPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>UserTrackerUtil</code> to access the user tracker persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		UserTrackerImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByCompanyId;
	private FinderPath _finderPathWithoutPaginationFindByCompanyId;
	private FinderPath _finderPathCountByCompanyId;

	/**
	 * Returns all the user trackers where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching user trackers
	 */
	@Override
	public List<UserTracker> findByCompanyId(long companyId) {
		return findByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the user trackers where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserTrackerModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of user trackers
	 * @param end the upper bound of the range of user trackers (not inclusive)
	 * @return the range of matching user trackers
	 */
	@Override
	public List<UserTracker> findByCompanyId(
		long companyId, int start, int end) {

		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the user trackers where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserTrackerModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of user trackers
	 * @param end the upper bound of the range of user trackers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching user trackers
	 */
	@Override
	public List<UserTracker> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<UserTracker> orderByComparator) {

		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the user trackers where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserTrackerModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of user trackers
	 * @param end the upper bound of the range of user trackers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching user trackers
	 */
	@Override
	public List<UserTracker> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<UserTracker> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByCompanyId;
				finderArgs = new Object[] {companyId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByCompanyId;
			finderArgs = new Object[] {
				companyId, start, end, orderByComparator
			};
		}

		List<UserTracker> list = null;

		if (useFinderCache) {
			list = (List<UserTracker>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (UserTracker userTracker : list) {
					if (companyId != userTracker.getCompanyId()) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(3);
			}

			sb.append(_SQL_SELECT_USERTRACKER_WHERE);

			sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(UserTrackerModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				list = (List<UserTracker>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first user tracker in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user tracker
	 * @throws NoSuchUserTrackerException if a matching user tracker could not be found
	 */
	@Override
	public UserTracker findByCompanyId_First(
			long companyId, OrderByComparator<UserTracker> orderByComparator)
		throws NoSuchUserTrackerException {

		UserTracker userTracker = fetchByCompanyId_First(
			companyId, orderByComparator);

		if (userTracker != null) {
			return userTracker;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchUserTrackerException(sb.toString());
	}

	/**
	 * Returns the first user tracker in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user tracker, or <code>null</code> if a matching user tracker could not be found
	 */
	@Override
	public UserTracker fetchByCompanyId_First(
		long companyId, OrderByComparator<UserTracker> orderByComparator) {

		List<UserTracker> list = findByCompanyId(
			companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last user tracker in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user tracker
	 * @throws NoSuchUserTrackerException if a matching user tracker could not be found
	 */
	@Override
	public UserTracker findByCompanyId_Last(
			long companyId, OrderByComparator<UserTracker> orderByComparator)
		throws NoSuchUserTrackerException {

		UserTracker userTracker = fetchByCompanyId_Last(
			companyId, orderByComparator);

		if (userTracker != null) {
			return userTracker;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchUserTrackerException(sb.toString());
	}

	/**
	 * Returns the last user tracker in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user tracker, or <code>null</code> if a matching user tracker could not be found
	 */
	@Override
	public UserTracker fetchByCompanyId_Last(
		long companyId, OrderByComparator<UserTracker> orderByComparator) {

		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<UserTracker> list = findByCompanyId(
			companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the user trackers before and after the current user tracker in the ordered set where companyId = &#63;.
	 *
	 * @param userTrackerId the primary key of the current user tracker
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next user tracker
	 * @throws NoSuchUserTrackerException if a user tracker with the primary key could not be found
	 */
	@Override
	public UserTracker[] findByCompanyId_PrevAndNext(
			long userTrackerId, long companyId,
			OrderByComparator<UserTracker> orderByComparator)
		throws NoSuchUserTrackerException {

		UserTracker userTracker = findByPrimaryKey(userTrackerId);

		Session session = null;

		try {
			session = openSession();

			UserTracker[] array = new UserTrackerImpl[3];

			array[0] = getByCompanyId_PrevAndNext(
				session, userTracker, companyId, orderByComparator, true);

			array[1] = userTracker;

			array[2] = getByCompanyId_PrevAndNext(
				session, userTracker, companyId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected UserTracker getByCompanyId_PrevAndNext(
		Session session, UserTracker userTracker, long companyId,
		OrderByComparator<UserTracker> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_USERTRACKER_WHERE);

		sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(UserTrackerModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(companyId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(userTracker)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<UserTracker> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the user trackers where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (UserTracker userTracker :
				findByCompanyId(
					companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(userTracker);
		}
	}

	/**
	 * Returns the number of user trackers where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching user trackers
	 */
	@Override
	public int countByCompanyId(long companyId) {
		FinderPath finderPath = _finderPathCountByCompanyId;

		Object[] finderArgs = new Object[] {companyId};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_USERTRACKER_WHERE);

			sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				count = (Long)query.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 =
		"userTracker.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByUserId;
	private FinderPath _finderPathWithoutPaginationFindByUserId;
	private FinderPath _finderPathCountByUserId;

	/**
	 * Returns all the user trackers where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the matching user trackers
	 */
	@Override
	public List<UserTracker> findByUserId(long userId) {
		return findByUserId(userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the user trackers where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserTrackerModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of user trackers
	 * @param end the upper bound of the range of user trackers (not inclusive)
	 * @return the range of matching user trackers
	 */
	@Override
	public List<UserTracker> findByUserId(long userId, int start, int end) {
		return findByUserId(userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the user trackers where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserTrackerModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of user trackers
	 * @param end the upper bound of the range of user trackers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching user trackers
	 */
	@Override
	public List<UserTracker> findByUserId(
		long userId, int start, int end,
		OrderByComparator<UserTracker> orderByComparator) {

		return findByUserId(userId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the user trackers where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserTrackerModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of user trackers
	 * @param end the upper bound of the range of user trackers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching user trackers
	 */
	@Override
	public List<UserTracker> findByUserId(
		long userId, int start, int end,
		OrderByComparator<UserTracker> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByUserId;
				finderArgs = new Object[] {userId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByUserId;
			finderArgs = new Object[] {userId, start, end, orderByComparator};
		}

		List<UserTracker> list = null;

		if (useFinderCache) {
			list = (List<UserTracker>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (UserTracker userTracker : list) {
					if (userId != userTracker.getUserId()) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(3);
			}

			sb.append(_SQL_SELECT_USERTRACKER_WHERE);

			sb.append(_FINDER_COLUMN_USERID_USERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(UserTrackerModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userId);

				list = (List<UserTracker>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first user tracker in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user tracker
	 * @throws NoSuchUserTrackerException if a matching user tracker could not be found
	 */
	@Override
	public UserTracker findByUserId_First(
			long userId, OrderByComparator<UserTracker> orderByComparator)
		throws NoSuchUserTrackerException {

		UserTracker userTracker = fetchByUserId_First(
			userId, orderByComparator);

		if (userTracker != null) {
			return userTracker;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("userId=");
		sb.append(userId);

		sb.append("}");

		throw new NoSuchUserTrackerException(sb.toString());
	}

	/**
	 * Returns the first user tracker in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user tracker, or <code>null</code> if a matching user tracker could not be found
	 */
	@Override
	public UserTracker fetchByUserId_First(
		long userId, OrderByComparator<UserTracker> orderByComparator) {

		List<UserTracker> list = findByUserId(userId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last user tracker in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user tracker
	 * @throws NoSuchUserTrackerException if a matching user tracker could not be found
	 */
	@Override
	public UserTracker findByUserId_Last(
			long userId, OrderByComparator<UserTracker> orderByComparator)
		throws NoSuchUserTrackerException {

		UserTracker userTracker = fetchByUserId_Last(userId, orderByComparator);

		if (userTracker != null) {
			return userTracker;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("userId=");
		sb.append(userId);

		sb.append("}");

		throw new NoSuchUserTrackerException(sb.toString());
	}

	/**
	 * Returns the last user tracker in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user tracker, or <code>null</code> if a matching user tracker could not be found
	 */
	@Override
	public UserTracker fetchByUserId_Last(
		long userId, OrderByComparator<UserTracker> orderByComparator) {

		int count = countByUserId(userId);

		if (count == 0) {
			return null;
		}

		List<UserTracker> list = findByUserId(
			userId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the user trackers before and after the current user tracker in the ordered set where userId = &#63;.
	 *
	 * @param userTrackerId the primary key of the current user tracker
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next user tracker
	 * @throws NoSuchUserTrackerException if a user tracker with the primary key could not be found
	 */
	@Override
	public UserTracker[] findByUserId_PrevAndNext(
			long userTrackerId, long userId,
			OrderByComparator<UserTracker> orderByComparator)
		throws NoSuchUserTrackerException {

		UserTracker userTracker = findByPrimaryKey(userTrackerId);

		Session session = null;

		try {
			session = openSession();

			UserTracker[] array = new UserTrackerImpl[3];

			array[0] = getByUserId_PrevAndNext(
				session, userTracker, userId, orderByComparator, true);

			array[1] = userTracker;

			array[2] = getByUserId_PrevAndNext(
				session, userTracker, userId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected UserTracker getByUserId_PrevAndNext(
		Session session, UserTracker userTracker, long userId,
		OrderByComparator<UserTracker> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_USERTRACKER_WHERE);

		sb.append(_FINDER_COLUMN_USERID_USERID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(UserTrackerModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(userId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(userTracker)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<UserTracker> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the user trackers where userId = &#63; from the database.
	 *
	 * @param userId the user ID
	 */
	@Override
	public void removeByUserId(long userId) {
		for (UserTracker userTracker :
				findByUserId(
					userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(userTracker);
		}
	}

	/**
	 * Returns the number of user trackers where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching user trackers
	 */
	@Override
	public int countByUserId(long userId) {
		FinderPath finderPath = _finderPathCountByUserId;

		Object[] finderArgs = new Object[] {userId};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_USERTRACKER_WHERE);

			sb.append(_FINDER_COLUMN_USERID_USERID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userId);

				count = (Long)query.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_USERID_USERID_2 =
		"userTracker.userId = ?";

	private FinderPath _finderPathWithPaginationFindBySessionId;
	private FinderPath _finderPathWithoutPaginationFindBySessionId;
	private FinderPath _finderPathCountBySessionId;

	/**
	 * Returns all the user trackers where sessionId = &#63;.
	 *
	 * @param sessionId the session ID
	 * @return the matching user trackers
	 */
	@Override
	public List<UserTracker> findBySessionId(String sessionId) {
		return findBySessionId(
			sessionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the user trackers where sessionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserTrackerModelImpl</code>.
	 * </p>
	 *
	 * @param sessionId the session ID
	 * @param start the lower bound of the range of user trackers
	 * @param end the upper bound of the range of user trackers (not inclusive)
	 * @return the range of matching user trackers
	 */
	@Override
	public List<UserTracker> findBySessionId(
		String sessionId, int start, int end) {

		return findBySessionId(sessionId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the user trackers where sessionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserTrackerModelImpl</code>.
	 * </p>
	 *
	 * @param sessionId the session ID
	 * @param start the lower bound of the range of user trackers
	 * @param end the upper bound of the range of user trackers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching user trackers
	 */
	@Override
	public List<UserTracker> findBySessionId(
		String sessionId, int start, int end,
		OrderByComparator<UserTracker> orderByComparator) {

		return findBySessionId(sessionId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the user trackers where sessionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserTrackerModelImpl</code>.
	 * </p>
	 *
	 * @param sessionId the session ID
	 * @param start the lower bound of the range of user trackers
	 * @param end the upper bound of the range of user trackers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching user trackers
	 */
	@Override
	public List<UserTracker> findBySessionId(
		String sessionId, int start, int end,
		OrderByComparator<UserTracker> orderByComparator,
		boolean useFinderCache) {

		sessionId = Objects.toString(sessionId, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindBySessionId;
				finderArgs = new Object[] {sessionId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindBySessionId;
			finderArgs = new Object[] {
				sessionId, start, end, orderByComparator
			};
		}

		List<UserTracker> list = null;

		if (useFinderCache) {
			list = (List<UserTracker>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (UserTracker userTracker : list) {
					if (!sessionId.equals(userTracker.getSessionId())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(3);
			}

			sb.append(_SQL_SELECT_USERTRACKER_WHERE);

			boolean bindSessionId = false;

			if (sessionId.isEmpty()) {
				sb.append(_FINDER_COLUMN_SESSIONID_SESSIONID_3);
			}
			else {
				bindSessionId = true;

				sb.append(_FINDER_COLUMN_SESSIONID_SESSIONID_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(UserTrackerModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindSessionId) {
					queryPos.add(sessionId);
				}

				list = (List<UserTracker>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first user tracker in the ordered set where sessionId = &#63;.
	 *
	 * @param sessionId the session ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user tracker
	 * @throws NoSuchUserTrackerException if a matching user tracker could not be found
	 */
	@Override
	public UserTracker findBySessionId_First(
			String sessionId, OrderByComparator<UserTracker> orderByComparator)
		throws NoSuchUserTrackerException {

		UserTracker userTracker = fetchBySessionId_First(
			sessionId, orderByComparator);

		if (userTracker != null) {
			return userTracker;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("sessionId=");
		sb.append(sessionId);

		sb.append("}");

		throw new NoSuchUserTrackerException(sb.toString());
	}

	/**
	 * Returns the first user tracker in the ordered set where sessionId = &#63;.
	 *
	 * @param sessionId the session ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user tracker, or <code>null</code> if a matching user tracker could not be found
	 */
	@Override
	public UserTracker fetchBySessionId_First(
		String sessionId, OrderByComparator<UserTracker> orderByComparator) {

		List<UserTracker> list = findBySessionId(
			sessionId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last user tracker in the ordered set where sessionId = &#63;.
	 *
	 * @param sessionId the session ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user tracker
	 * @throws NoSuchUserTrackerException if a matching user tracker could not be found
	 */
	@Override
	public UserTracker findBySessionId_Last(
			String sessionId, OrderByComparator<UserTracker> orderByComparator)
		throws NoSuchUserTrackerException {

		UserTracker userTracker = fetchBySessionId_Last(
			sessionId, orderByComparator);

		if (userTracker != null) {
			return userTracker;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("sessionId=");
		sb.append(sessionId);

		sb.append("}");

		throw new NoSuchUserTrackerException(sb.toString());
	}

	/**
	 * Returns the last user tracker in the ordered set where sessionId = &#63;.
	 *
	 * @param sessionId the session ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user tracker, or <code>null</code> if a matching user tracker could not be found
	 */
	@Override
	public UserTracker fetchBySessionId_Last(
		String sessionId, OrderByComparator<UserTracker> orderByComparator) {

		int count = countBySessionId(sessionId);

		if (count == 0) {
			return null;
		}

		List<UserTracker> list = findBySessionId(
			sessionId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the user trackers before and after the current user tracker in the ordered set where sessionId = &#63;.
	 *
	 * @param userTrackerId the primary key of the current user tracker
	 * @param sessionId the session ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next user tracker
	 * @throws NoSuchUserTrackerException if a user tracker with the primary key could not be found
	 */
	@Override
	public UserTracker[] findBySessionId_PrevAndNext(
			long userTrackerId, String sessionId,
			OrderByComparator<UserTracker> orderByComparator)
		throws NoSuchUserTrackerException {

		sessionId = Objects.toString(sessionId, "");

		UserTracker userTracker = findByPrimaryKey(userTrackerId);

		Session session = null;

		try {
			session = openSession();

			UserTracker[] array = new UserTrackerImpl[3];

			array[0] = getBySessionId_PrevAndNext(
				session, userTracker, sessionId, orderByComparator, true);

			array[1] = userTracker;

			array[2] = getBySessionId_PrevAndNext(
				session, userTracker, sessionId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected UserTracker getBySessionId_PrevAndNext(
		Session session, UserTracker userTracker, String sessionId,
		OrderByComparator<UserTracker> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_USERTRACKER_WHERE);

		boolean bindSessionId = false;

		if (sessionId.isEmpty()) {
			sb.append(_FINDER_COLUMN_SESSIONID_SESSIONID_3);
		}
		else {
			bindSessionId = true;

			sb.append(_FINDER_COLUMN_SESSIONID_SESSIONID_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(UserTrackerModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindSessionId) {
			queryPos.add(sessionId);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(userTracker)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<UserTracker> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the user trackers where sessionId = &#63; from the database.
	 *
	 * @param sessionId the session ID
	 */
	@Override
	public void removeBySessionId(String sessionId) {
		for (UserTracker userTracker :
				findBySessionId(
					sessionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(userTracker);
		}
	}

	/**
	 * Returns the number of user trackers where sessionId = &#63;.
	 *
	 * @param sessionId the session ID
	 * @return the number of matching user trackers
	 */
	@Override
	public int countBySessionId(String sessionId) {
		sessionId = Objects.toString(sessionId, "");

		FinderPath finderPath = _finderPathCountBySessionId;

		Object[] finderArgs = new Object[] {sessionId};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_USERTRACKER_WHERE);

			boolean bindSessionId = false;

			if (sessionId.isEmpty()) {
				sb.append(_FINDER_COLUMN_SESSIONID_SESSIONID_3);
			}
			else {
				bindSessionId = true;

				sb.append(_FINDER_COLUMN_SESSIONID_SESSIONID_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindSessionId) {
					queryPos.add(sessionId);
				}

				count = (Long)query.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_SESSIONID_SESSIONID_2 =
		"userTracker.sessionId = ?";

	private static final String _FINDER_COLUMN_SESSIONID_SESSIONID_3 =
		"(userTracker.sessionId IS NULL OR userTracker.sessionId = '')";

	public UserTrackerPersistenceImpl() {
		setModelClass(UserTracker.class);

		setModelImplClass(UserTrackerImpl.class);
		setModelPKClass(long.class);

		setTable(UserTrackerTable.INSTANCE);
	}

	/**
	 * Caches the user tracker in the entity cache if it is enabled.
	 *
	 * @param userTracker the user tracker
	 */
	@Override
	public void cacheResult(UserTracker userTracker) {
		EntityCacheUtil.putResult(
			UserTrackerImpl.class, userTracker.getPrimaryKey(), userTracker);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the user trackers in the entity cache if it is enabled.
	 *
	 * @param userTrackers the user trackers
	 */
	@Override
	public void cacheResult(List<UserTracker> userTrackers) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (userTrackers.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (UserTracker userTracker : userTrackers) {
			if (EntityCacheUtil.getResult(
					UserTrackerImpl.class, userTracker.getPrimaryKey()) ==
						null) {

				cacheResult(userTracker);
			}
		}
	}

	/**
	 * Clears the cache for all user trackers.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		EntityCacheUtil.clearCache(UserTrackerImpl.class);

		FinderCacheUtil.clearCache(UserTrackerImpl.class);
	}

	/**
	 * Clears the cache for the user tracker.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(UserTracker userTracker) {
		EntityCacheUtil.removeResult(UserTrackerImpl.class, userTracker);
	}

	@Override
	public void clearCache(List<UserTracker> userTrackers) {
		for (UserTracker userTracker : userTrackers) {
			EntityCacheUtil.removeResult(UserTrackerImpl.class, userTracker);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		FinderCacheUtil.clearCache(UserTrackerImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			EntityCacheUtil.removeResult(UserTrackerImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new user tracker with the primary key. Does not add the user tracker to the database.
	 *
	 * @param userTrackerId the primary key for the new user tracker
	 * @return the new user tracker
	 */
	@Override
	public UserTracker create(long userTrackerId) {
		UserTracker userTracker = new UserTrackerImpl();

		userTracker.setNew(true);
		userTracker.setPrimaryKey(userTrackerId);

		userTracker.setCompanyId(CompanyThreadLocal.getCompanyId());

		return userTracker;
	}

	/**
	 * Removes the user tracker with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param userTrackerId the primary key of the user tracker
	 * @return the user tracker that was removed
	 * @throws NoSuchUserTrackerException if a user tracker with the primary key could not be found
	 */
	@Override
	public UserTracker remove(long userTrackerId)
		throws NoSuchUserTrackerException {

		return remove((Serializable)userTrackerId);
	}

	/**
	 * Removes the user tracker with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the user tracker
	 * @return the user tracker that was removed
	 * @throws NoSuchUserTrackerException if a user tracker with the primary key could not be found
	 */
	@Override
	public UserTracker remove(Serializable primaryKey)
		throws NoSuchUserTrackerException {

		Session session = null;

		try {
			session = openSession();

			UserTracker userTracker = (UserTracker)session.get(
				UserTrackerImpl.class, primaryKey);

			if (userTracker == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchUserTrackerException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(userTracker);
		}
		catch (NoSuchUserTrackerException noSuchEntityException) {
			throw noSuchEntityException;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	protected UserTracker removeImpl(UserTracker userTracker) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(userTracker)) {
				userTracker = (UserTracker)session.get(
					UserTrackerImpl.class, userTracker.getPrimaryKeyObj());
			}

			if (userTracker != null) {
				session.delete(userTracker);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (userTracker != null) {
			clearCache(userTracker);
		}

		return userTracker;
	}

	@Override
	public UserTracker updateImpl(UserTracker userTracker) {
		boolean isNew = userTracker.isNew();

		if (!(userTracker instanceof UserTrackerModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(userTracker.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(userTracker);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in userTracker proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom UserTracker implementation " +
					userTracker.getClass());
		}

		UserTrackerModelImpl userTrackerModelImpl =
			(UserTrackerModelImpl)userTracker;

		if (!userTrackerModelImpl.hasSetModifiedDate()) {
			ServiceContext serviceContext =
				ServiceContextThreadLocal.getServiceContext();

			Date date = new Date();

			if (serviceContext == null) {
				userTracker.setModifiedDate(date);
			}
			else {
				userTracker.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(userTracker);
			}
			else {
				userTracker = (UserTracker)session.merge(userTracker);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		EntityCacheUtil.putResult(
			UserTrackerImpl.class, userTrackerModelImpl, false, true);

		if (isNew) {
			userTracker.setNew(false);
		}

		userTracker.resetOriginalValues();

		return userTracker;
	}

	/**
	 * Returns the user tracker with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the user tracker
	 * @return the user tracker
	 * @throws NoSuchUserTrackerException if a user tracker with the primary key could not be found
	 */
	@Override
	public UserTracker findByPrimaryKey(Serializable primaryKey)
		throws NoSuchUserTrackerException {

		UserTracker userTracker = fetchByPrimaryKey(primaryKey);

		if (userTracker == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchUserTrackerException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return userTracker;
	}

	/**
	 * Returns the user tracker with the primary key or throws a <code>NoSuchUserTrackerException</code> if it could not be found.
	 *
	 * @param userTrackerId the primary key of the user tracker
	 * @return the user tracker
	 * @throws NoSuchUserTrackerException if a user tracker with the primary key could not be found
	 */
	@Override
	public UserTracker findByPrimaryKey(long userTrackerId)
		throws NoSuchUserTrackerException {

		return findByPrimaryKey((Serializable)userTrackerId);
	}

	/**
	 * Returns the user tracker with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param userTrackerId the primary key of the user tracker
	 * @return the user tracker, or <code>null</code> if a user tracker with the primary key could not be found
	 */
	@Override
	public UserTracker fetchByPrimaryKey(long userTrackerId) {
		return fetchByPrimaryKey((Serializable)userTrackerId);
	}

	/**
	 * Returns all the user trackers.
	 *
	 * @return the user trackers
	 */
	@Override
	public List<UserTracker> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the user trackers.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserTrackerModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of user trackers
	 * @param end the upper bound of the range of user trackers (not inclusive)
	 * @return the range of user trackers
	 */
	@Override
	public List<UserTracker> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the user trackers.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserTrackerModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of user trackers
	 * @param end the upper bound of the range of user trackers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of user trackers
	 */
	@Override
	public List<UserTracker> findAll(
		int start, int end, OrderByComparator<UserTracker> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the user trackers.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserTrackerModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of user trackers
	 * @param end the upper bound of the range of user trackers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of user trackers
	 */
	@Override
	public List<UserTracker> findAll(
		int start, int end, OrderByComparator<UserTracker> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindAll;
				finderArgs = FINDER_ARGS_EMPTY;
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindAll;
			finderArgs = new Object[] {start, end, orderByComparator};
		}

		List<UserTracker> list = null;

		if (useFinderCache) {
			list = (List<UserTracker>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_USERTRACKER);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_USERTRACKER;

				sql = sql.concat(UserTrackerModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<UserTracker>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the user trackers from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (UserTracker userTracker : findAll()) {
			remove(userTracker);
		}
	}

	/**
	 * Returns the number of user trackers.
	 *
	 * @return the number of user trackers
	 */
	@Override
	public int countAll() {
		Long count = (Long)FinderCacheUtil.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_USERTRACKER);

				count = (Long)query.uniqueResult();

				FinderCacheUtil.putResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	@Override
	protected EntityCache getEntityCache() {
		return EntityCacheUtil.getEntityCache();
	}

	@Override
	protected String getPKDBName() {
		return "userTrackerId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_USERTRACKER;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return UserTrackerModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the user tracker persistence.
	 */
	public void afterPropertiesSet() {
		_valueObjectFinderCacheListThreshold = GetterUtil.getInteger(
			PropsUtil.get(PropsKeys.VALUE_OBJECT_FINDER_CACHE_LIST_THRESHOLD));

		_finderPathWithPaginationFindAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathCountAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0], new String[0], false);

		_finderPathWithPaginationFindByCompanyId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCompanyId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"companyId"}, true);

		_finderPathWithoutPaginationFindByCompanyId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCompanyId",
			new String[] {Long.class.getName()}, new String[] {"companyId"},
			true);

		_finderPathCountByCompanyId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCompanyId",
			new String[] {Long.class.getName()}, new String[] {"companyId"},
			false);

		_finderPathWithPaginationFindByUserId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUserId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"userId"}, true);

		_finderPathWithoutPaginationFindByUserId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUserId",
			new String[] {Long.class.getName()}, new String[] {"userId"}, true);

		_finderPathCountByUserId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUserId",
			new String[] {Long.class.getName()}, new String[] {"userId"},
			false);

		_finderPathWithPaginationFindBySessionId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findBySessionId",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"sessionId"}, true);

		_finderPathWithoutPaginationFindBySessionId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findBySessionId",
			new String[] {String.class.getName()}, new String[] {"sessionId"},
			true);

		_finderPathCountBySessionId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countBySessionId",
			new String[] {String.class.getName()}, new String[] {"sessionId"},
			false);

		UserTrackerUtil.setPersistence(this);
	}

	public void destroy() {
		UserTrackerUtil.setPersistence(null);

		EntityCacheUtil.removeCache(UserTrackerImpl.class.getName());
	}

	private static final String _SQL_SELECT_USERTRACKER =
		"SELECT userTracker FROM UserTracker userTracker";

	private static final String _SQL_SELECT_USERTRACKER_WHERE =
		"SELECT userTracker FROM UserTracker userTracker WHERE ";

	private static final String _SQL_COUNT_USERTRACKER =
		"SELECT COUNT(userTracker) FROM UserTracker userTracker";

	private static final String _SQL_COUNT_USERTRACKER_WHERE =
		"SELECT COUNT(userTracker) FROM UserTracker userTracker WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "userTracker.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No UserTracker exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No UserTracker exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		UserTrackerPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return FinderCacheUtil.getFinderCache();
	}

}