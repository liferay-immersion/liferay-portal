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

package com.liferay.portal.workflow.kaleo.service.persistence;

import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;
import com.liferay.portal.workflow.kaleo.exception.NoSuchTimerException;
import com.liferay.portal.workflow.kaleo.model.KaleoTimer;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the kaleo timer service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see KaleoTimerUtil
 * @generated
 */
@ProviderType
public interface KaleoTimerPersistence
	extends BasePersistence<KaleoTimer>, CTPersistence<KaleoTimer> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link KaleoTimerUtil} to access the kaleo timer persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the kaleo timers where kaleoClassName = &#63; and kaleoClassPK = &#63;.
	 *
	 * @param kaleoClassName the kaleo class name
	 * @param kaleoClassPK the kaleo class pk
	 * @return the matching kaleo timers
	 */
	public java.util.List<KaleoTimer> findByKCN_KCPK(
		String kaleoClassName, long kaleoClassPK);

	/**
	 * Returns a range of all the kaleo timers where kaleoClassName = &#63; and kaleoClassPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTimerModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoClassName the kaleo class name
	 * @param kaleoClassPK the kaleo class pk
	 * @param start the lower bound of the range of kaleo timers
	 * @param end the upper bound of the range of kaleo timers (not inclusive)
	 * @return the range of matching kaleo timers
	 */
	public java.util.List<KaleoTimer> findByKCN_KCPK(
		String kaleoClassName, long kaleoClassPK, int start, int end);

	/**
	 * Returns an ordered range of all the kaleo timers where kaleoClassName = &#63; and kaleoClassPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTimerModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoClassName the kaleo class name
	 * @param kaleoClassPK the kaleo class pk
	 * @param start the lower bound of the range of kaleo timers
	 * @param end the upper bound of the range of kaleo timers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo timers
	 */
	public java.util.List<KaleoTimer> findByKCN_KCPK(
		String kaleoClassName, long kaleoClassPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoTimer>
			orderByComparator);

	/**
	 * Returns an ordered range of all the kaleo timers where kaleoClassName = &#63; and kaleoClassPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTimerModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoClassName the kaleo class name
	 * @param kaleoClassPK the kaleo class pk
	 * @param start the lower bound of the range of kaleo timers
	 * @param end the upper bound of the range of kaleo timers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching kaleo timers
	 */
	public java.util.List<KaleoTimer> findByKCN_KCPK(
		String kaleoClassName, long kaleoClassPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoTimer>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first kaleo timer in the ordered set where kaleoClassName = &#63; and kaleoClassPK = &#63;.
	 *
	 * @param kaleoClassName the kaleo class name
	 * @param kaleoClassPK the kaleo class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo timer
	 * @throws NoSuchTimerException if a matching kaleo timer could not be found
	 */
	public KaleoTimer findByKCN_KCPK_First(
			String kaleoClassName, long kaleoClassPK,
			com.liferay.portal.kernel.util.OrderByComparator<KaleoTimer>
				orderByComparator)
		throws NoSuchTimerException;

	/**
	 * Returns the first kaleo timer in the ordered set where kaleoClassName = &#63; and kaleoClassPK = &#63;.
	 *
	 * @param kaleoClassName the kaleo class name
	 * @param kaleoClassPK the kaleo class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo timer, or <code>null</code> if a matching kaleo timer could not be found
	 */
	public KaleoTimer fetchByKCN_KCPK_First(
		String kaleoClassName, long kaleoClassPK,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoTimer>
			orderByComparator);

	/**
	 * Returns the last kaleo timer in the ordered set where kaleoClassName = &#63; and kaleoClassPK = &#63;.
	 *
	 * @param kaleoClassName the kaleo class name
	 * @param kaleoClassPK the kaleo class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo timer
	 * @throws NoSuchTimerException if a matching kaleo timer could not be found
	 */
	public KaleoTimer findByKCN_KCPK_Last(
			String kaleoClassName, long kaleoClassPK,
			com.liferay.portal.kernel.util.OrderByComparator<KaleoTimer>
				orderByComparator)
		throws NoSuchTimerException;

	/**
	 * Returns the last kaleo timer in the ordered set where kaleoClassName = &#63; and kaleoClassPK = &#63;.
	 *
	 * @param kaleoClassName the kaleo class name
	 * @param kaleoClassPK the kaleo class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo timer, or <code>null</code> if a matching kaleo timer could not be found
	 */
	public KaleoTimer fetchByKCN_KCPK_Last(
		String kaleoClassName, long kaleoClassPK,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoTimer>
			orderByComparator);

	/**
	 * Returns the kaleo timers before and after the current kaleo timer in the ordered set where kaleoClassName = &#63; and kaleoClassPK = &#63;.
	 *
	 * @param kaleoTimerId the primary key of the current kaleo timer
	 * @param kaleoClassName the kaleo class name
	 * @param kaleoClassPK the kaleo class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next kaleo timer
	 * @throws NoSuchTimerException if a kaleo timer with the primary key could not be found
	 */
	public KaleoTimer[] findByKCN_KCPK_PrevAndNext(
			long kaleoTimerId, String kaleoClassName, long kaleoClassPK,
			com.liferay.portal.kernel.util.OrderByComparator<KaleoTimer>
				orderByComparator)
		throws NoSuchTimerException;

	/**
	 * Removes all the kaleo timers where kaleoClassName = &#63; and kaleoClassPK = &#63; from the database.
	 *
	 * @param kaleoClassName the kaleo class name
	 * @param kaleoClassPK the kaleo class pk
	 */
	public void removeByKCN_KCPK(String kaleoClassName, long kaleoClassPK);

	/**
	 * Returns the number of kaleo timers where kaleoClassName = &#63; and kaleoClassPK = &#63;.
	 *
	 * @param kaleoClassName the kaleo class name
	 * @param kaleoClassPK the kaleo class pk
	 * @return the number of matching kaleo timers
	 */
	public int countByKCN_KCPK(String kaleoClassName, long kaleoClassPK);

	/**
	 * Returns all the kaleo timers where kaleoClassName = &#63; and kaleoClassPK = &#63; and blocking = &#63;.
	 *
	 * @param kaleoClassName the kaleo class name
	 * @param kaleoClassPK the kaleo class pk
	 * @param blocking the blocking
	 * @return the matching kaleo timers
	 */
	public java.util.List<KaleoTimer> findByKCN_KCPK_Blocking(
		String kaleoClassName, long kaleoClassPK, boolean blocking);

	/**
	 * Returns a range of all the kaleo timers where kaleoClassName = &#63; and kaleoClassPK = &#63; and blocking = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTimerModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoClassName the kaleo class name
	 * @param kaleoClassPK the kaleo class pk
	 * @param blocking the blocking
	 * @param start the lower bound of the range of kaleo timers
	 * @param end the upper bound of the range of kaleo timers (not inclusive)
	 * @return the range of matching kaleo timers
	 */
	public java.util.List<KaleoTimer> findByKCN_KCPK_Blocking(
		String kaleoClassName, long kaleoClassPK, boolean blocking, int start,
		int end);

	/**
	 * Returns an ordered range of all the kaleo timers where kaleoClassName = &#63; and kaleoClassPK = &#63; and blocking = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTimerModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoClassName the kaleo class name
	 * @param kaleoClassPK the kaleo class pk
	 * @param blocking the blocking
	 * @param start the lower bound of the range of kaleo timers
	 * @param end the upper bound of the range of kaleo timers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo timers
	 */
	public java.util.List<KaleoTimer> findByKCN_KCPK_Blocking(
		String kaleoClassName, long kaleoClassPK, boolean blocking, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoTimer>
			orderByComparator);

	/**
	 * Returns an ordered range of all the kaleo timers where kaleoClassName = &#63; and kaleoClassPK = &#63; and blocking = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTimerModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoClassName the kaleo class name
	 * @param kaleoClassPK the kaleo class pk
	 * @param blocking the blocking
	 * @param start the lower bound of the range of kaleo timers
	 * @param end the upper bound of the range of kaleo timers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching kaleo timers
	 */
	public java.util.List<KaleoTimer> findByKCN_KCPK_Blocking(
		String kaleoClassName, long kaleoClassPK, boolean blocking, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoTimer>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first kaleo timer in the ordered set where kaleoClassName = &#63; and kaleoClassPK = &#63; and blocking = &#63;.
	 *
	 * @param kaleoClassName the kaleo class name
	 * @param kaleoClassPK the kaleo class pk
	 * @param blocking the blocking
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo timer
	 * @throws NoSuchTimerException if a matching kaleo timer could not be found
	 */
	public KaleoTimer findByKCN_KCPK_Blocking_First(
			String kaleoClassName, long kaleoClassPK, boolean blocking,
			com.liferay.portal.kernel.util.OrderByComparator<KaleoTimer>
				orderByComparator)
		throws NoSuchTimerException;

	/**
	 * Returns the first kaleo timer in the ordered set where kaleoClassName = &#63; and kaleoClassPK = &#63; and blocking = &#63;.
	 *
	 * @param kaleoClassName the kaleo class name
	 * @param kaleoClassPK the kaleo class pk
	 * @param blocking the blocking
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo timer, or <code>null</code> if a matching kaleo timer could not be found
	 */
	public KaleoTimer fetchByKCN_KCPK_Blocking_First(
		String kaleoClassName, long kaleoClassPK, boolean blocking,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoTimer>
			orderByComparator);

	/**
	 * Returns the last kaleo timer in the ordered set where kaleoClassName = &#63; and kaleoClassPK = &#63; and blocking = &#63;.
	 *
	 * @param kaleoClassName the kaleo class name
	 * @param kaleoClassPK the kaleo class pk
	 * @param blocking the blocking
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo timer
	 * @throws NoSuchTimerException if a matching kaleo timer could not be found
	 */
	public KaleoTimer findByKCN_KCPK_Blocking_Last(
			String kaleoClassName, long kaleoClassPK, boolean blocking,
			com.liferay.portal.kernel.util.OrderByComparator<KaleoTimer>
				orderByComparator)
		throws NoSuchTimerException;

	/**
	 * Returns the last kaleo timer in the ordered set where kaleoClassName = &#63; and kaleoClassPK = &#63; and blocking = &#63;.
	 *
	 * @param kaleoClassName the kaleo class name
	 * @param kaleoClassPK the kaleo class pk
	 * @param blocking the blocking
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo timer, or <code>null</code> if a matching kaleo timer could not be found
	 */
	public KaleoTimer fetchByKCN_KCPK_Blocking_Last(
		String kaleoClassName, long kaleoClassPK, boolean blocking,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoTimer>
			orderByComparator);

	/**
	 * Returns the kaleo timers before and after the current kaleo timer in the ordered set where kaleoClassName = &#63; and kaleoClassPK = &#63; and blocking = &#63;.
	 *
	 * @param kaleoTimerId the primary key of the current kaleo timer
	 * @param kaleoClassName the kaleo class name
	 * @param kaleoClassPK the kaleo class pk
	 * @param blocking the blocking
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next kaleo timer
	 * @throws NoSuchTimerException if a kaleo timer with the primary key could not be found
	 */
	public KaleoTimer[] findByKCN_KCPK_Blocking_PrevAndNext(
			long kaleoTimerId, String kaleoClassName, long kaleoClassPK,
			boolean blocking,
			com.liferay.portal.kernel.util.OrderByComparator<KaleoTimer>
				orderByComparator)
		throws NoSuchTimerException;

	/**
	 * Removes all the kaleo timers where kaleoClassName = &#63; and kaleoClassPK = &#63; and blocking = &#63; from the database.
	 *
	 * @param kaleoClassName the kaleo class name
	 * @param kaleoClassPK the kaleo class pk
	 * @param blocking the blocking
	 */
	public void removeByKCN_KCPK_Blocking(
		String kaleoClassName, long kaleoClassPK, boolean blocking);

	/**
	 * Returns the number of kaleo timers where kaleoClassName = &#63; and kaleoClassPK = &#63; and blocking = &#63;.
	 *
	 * @param kaleoClassName the kaleo class name
	 * @param kaleoClassPK the kaleo class pk
	 * @param blocking the blocking
	 * @return the number of matching kaleo timers
	 */
	public int countByKCN_KCPK_Blocking(
		String kaleoClassName, long kaleoClassPK, boolean blocking);

	/**
	 * Caches the kaleo timer in the entity cache if it is enabled.
	 *
	 * @param kaleoTimer the kaleo timer
	 */
	public void cacheResult(KaleoTimer kaleoTimer);

	/**
	 * Caches the kaleo timers in the entity cache if it is enabled.
	 *
	 * @param kaleoTimers the kaleo timers
	 */
	public void cacheResult(java.util.List<KaleoTimer> kaleoTimers);

	/**
	 * Creates a new kaleo timer with the primary key. Does not add the kaleo timer to the database.
	 *
	 * @param kaleoTimerId the primary key for the new kaleo timer
	 * @return the new kaleo timer
	 */
	public KaleoTimer create(long kaleoTimerId);

	/**
	 * Removes the kaleo timer with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param kaleoTimerId the primary key of the kaleo timer
	 * @return the kaleo timer that was removed
	 * @throws NoSuchTimerException if a kaleo timer with the primary key could not be found
	 */
	public KaleoTimer remove(long kaleoTimerId) throws NoSuchTimerException;

	public KaleoTimer updateImpl(KaleoTimer kaleoTimer);

	/**
	 * Returns the kaleo timer with the primary key or throws a <code>NoSuchTimerException</code> if it could not be found.
	 *
	 * @param kaleoTimerId the primary key of the kaleo timer
	 * @return the kaleo timer
	 * @throws NoSuchTimerException if a kaleo timer with the primary key could not be found
	 */
	public KaleoTimer findByPrimaryKey(long kaleoTimerId)
		throws NoSuchTimerException;

	/**
	 * Returns the kaleo timer with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param kaleoTimerId the primary key of the kaleo timer
	 * @return the kaleo timer, or <code>null</code> if a kaleo timer with the primary key could not be found
	 */
	public KaleoTimer fetchByPrimaryKey(long kaleoTimerId);

	/**
	 * Returns all the kaleo timers.
	 *
	 * @return the kaleo timers
	 */
	public java.util.List<KaleoTimer> findAll();

	/**
	 * Returns a range of all the kaleo timers.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTimerModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of kaleo timers
	 * @param end the upper bound of the range of kaleo timers (not inclusive)
	 * @return the range of kaleo timers
	 */
	public java.util.List<KaleoTimer> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the kaleo timers.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTimerModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of kaleo timers
	 * @param end the upper bound of the range of kaleo timers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of kaleo timers
	 */
	public java.util.List<KaleoTimer> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoTimer>
			orderByComparator);

	/**
	 * Returns an ordered range of all the kaleo timers.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTimerModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of kaleo timers
	 * @param end the upper bound of the range of kaleo timers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of kaleo timers
	 */
	public java.util.List<KaleoTimer> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoTimer>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the kaleo timers from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of kaleo timers.
	 *
	 * @return the number of kaleo timers
	 */
	public int countAll();

}