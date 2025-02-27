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

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskAssignment;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the kaleo task assignment service. This utility wraps <code>com.liferay.portal.workflow.kaleo.service.persistence.impl.KaleoTaskAssignmentPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see KaleoTaskAssignmentPersistence
 * @generated
 */
public class KaleoTaskAssignmentUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void clearCache(KaleoTaskAssignment kaleoTaskAssignment) {
		getPersistence().clearCache(kaleoTaskAssignment);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#fetchByPrimaryKeys(Set)
	 */
	public static Map<Serializable, KaleoTaskAssignment> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<KaleoTaskAssignment> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<KaleoTaskAssignment> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<KaleoTaskAssignment> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<KaleoTaskAssignment> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static KaleoTaskAssignment update(
		KaleoTaskAssignment kaleoTaskAssignment) {

		return getPersistence().update(kaleoTaskAssignment);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static KaleoTaskAssignment update(
		KaleoTaskAssignment kaleoTaskAssignment,
		ServiceContext serviceContext) {

		return getPersistence().update(kaleoTaskAssignment, serviceContext);
	}

	/**
	 * Returns all the kaleo task assignments where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching kaleo task assignments
	 */
	public static List<KaleoTaskAssignment> findByCompanyId(long companyId) {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	 * Returns a range of all the kaleo task assignments where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTaskAssignmentModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of kaleo task assignments
	 * @param end the upper bound of the range of kaleo task assignments (not inclusive)
	 * @return the range of matching kaleo task assignments
	 */
	public static List<KaleoTaskAssignment> findByCompanyId(
		long companyId, int start, int end) {

		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the kaleo task assignments where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTaskAssignmentModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of kaleo task assignments
	 * @param end the upper bound of the range of kaleo task assignments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo task assignments
	 */
	public static List<KaleoTaskAssignment> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<KaleoTaskAssignment> orderByComparator) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the kaleo task assignments where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTaskAssignmentModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of kaleo task assignments
	 * @param end the upper bound of the range of kaleo task assignments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching kaleo task assignments
	 */
	public static List<KaleoTaskAssignment> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<KaleoTaskAssignment> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first kaleo task assignment in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo task assignment
	 * @throws NoSuchTaskAssignmentException if a matching kaleo task assignment could not be found
	 */
	public static KaleoTaskAssignment findByCompanyId_First(
			long companyId,
			OrderByComparator<KaleoTaskAssignment> orderByComparator)
		throws com.liferay.portal.workflow.kaleo.exception.
			NoSuchTaskAssignmentException {

		return getPersistence().findByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the first kaleo task assignment in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo task assignment, or <code>null</code> if a matching kaleo task assignment could not be found
	 */
	public static KaleoTaskAssignment fetchByCompanyId_First(
		long companyId,
		OrderByComparator<KaleoTaskAssignment> orderByComparator) {

		return getPersistence().fetchByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last kaleo task assignment in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo task assignment
	 * @throws NoSuchTaskAssignmentException if a matching kaleo task assignment could not be found
	 */
	public static KaleoTaskAssignment findByCompanyId_Last(
			long companyId,
			OrderByComparator<KaleoTaskAssignment> orderByComparator)
		throws com.liferay.portal.workflow.kaleo.exception.
			NoSuchTaskAssignmentException {

		return getPersistence().findByCompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last kaleo task assignment in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo task assignment, or <code>null</code> if a matching kaleo task assignment could not be found
	 */
	public static KaleoTaskAssignment fetchByCompanyId_Last(
		long companyId,
		OrderByComparator<KaleoTaskAssignment> orderByComparator) {

		return getPersistence().fetchByCompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the kaleo task assignments before and after the current kaleo task assignment in the ordered set where companyId = &#63;.
	 *
	 * @param kaleoTaskAssignmentId the primary key of the current kaleo task assignment
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next kaleo task assignment
	 * @throws NoSuchTaskAssignmentException if a kaleo task assignment with the primary key could not be found
	 */
	public static KaleoTaskAssignment[] findByCompanyId_PrevAndNext(
			long kaleoTaskAssignmentId, long companyId,
			OrderByComparator<KaleoTaskAssignment> orderByComparator)
		throws com.liferay.portal.workflow.kaleo.exception.
			NoSuchTaskAssignmentException {

		return getPersistence().findByCompanyId_PrevAndNext(
			kaleoTaskAssignmentId, companyId, orderByComparator);
	}

	/**
	 * Removes all the kaleo task assignments where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public static void removeByCompanyId(long companyId) {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	 * Returns the number of kaleo task assignments where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching kaleo task assignments
	 */
	public static int countByCompanyId(long companyId) {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	 * Returns all the kaleo task assignments where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @return the matching kaleo task assignments
	 */
	public static List<KaleoTaskAssignment> findByKaleoDefinitionVersionId(
		long kaleoDefinitionVersionId) {

		return getPersistence().findByKaleoDefinitionVersionId(
			kaleoDefinitionVersionId);
	}

	/**
	 * Returns a range of all the kaleo task assignments where kaleoDefinitionVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTaskAssignmentModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param start the lower bound of the range of kaleo task assignments
	 * @param end the upper bound of the range of kaleo task assignments (not inclusive)
	 * @return the range of matching kaleo task assignments
	 */
	public static List<KaleoTaskAssignment> findByKaleoDefinitionVersionId(
		long kaleoDefinitionVersionId, int start, int end) {

		return getPersistence().findByKaleoDefinitionVersionId(
			kaleoDefinitionVersionId, start, end);
	}

	/**
	 * Returns an ordered range of all the kaleo task assignments where kaleoDefinitionVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTaskAssignmentModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param start the lower bound of the range of kaleo task assignments
	 * @param end the upper bound of the range of kaleo task assignments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo task assignments
	 */
	public static List<KaleoTaskAssignment> findByKaleoDefinitionVersionId(
		long kaleoDefinitionVersionId, int start, int end,
		OrderByComparator<KaleoTaskAssignment> orderByComparator) {

		return getPersistence().findByKaleoDefinitionVersionId(
			kaleoDefinitionVersionId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the kaleo task assignments where kaleoDefinitionVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTaskAssignmentModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param start the lower bound of the range of kaleo task assignments
	 * @param end the upper bound of the range of kaleo task assignments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching kaleo task assignments
	 */
	public static List<KaleoTaskAssignment> findByKaleoDefinitionVersionId(
		long kaleoDefinitionVersionId, int start, int end,
		OrderByComparator<KaleoTaskAssignment> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByKaleoDefinitionVersionId(
			kaleoDefinitionVersionId, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first kaleo task assignment in the ordered set where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo task assignment
	 * @throws NoSuchTaskAssignmentException if a matching kaleo task assignment could not be found
	 */
	public static KaleoTaskAssignment findByKaleoDefinitionVersionId_First(
			long kaleoDefinitionVersionId,
			OrderByComparator<KaleoTaskAssignment> orderByComparator)
		throws com.liferay.portal.workflow.kaleo.exception.
			NoSuchTaskAssignmentException {

		return getPersistence().findByKaleoDefinitionVersionId_First(
			kaleoDefinitionVersionId, orderByComparator);
	}

	/**
	 * Returns the first kaleo task assignment in the ordered set where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo task assignment, or <code>null</code> if a matching kaleo task assignment could not be found
	 */
	public static KaleoTaskAssignment fetchByKaleoDefinitionVersionId_First(
		long kaleoDefinitionVersionId,
		OrderByComparator<KaleoTaskAssignment> orderByComparator) {

		return getPersistence().fetchByKaleoDefinitionVersionId_First(
			kaleoDefinitionVersionId, orderByComparator);
	}

	/**
	 * Returns the last kaleo task assignment in the ordered set where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo task assignment
	 * @throws NoSuchTaskAssignmentException if a matching kaleo task assignment could not be found
	 */
	public static KaleoTaskAssignment findByKaleoDefinitionVersionId_Last(
			long kaleoDefinitionVersionId,
			OrderByComparator<KaleoTaskAssignment> orderByComparator)
		throws com.liferay.portal.workflow.kaleo.exception.
			NoSuchTaskAssignmentException {

		return getPersistence().findByKaleoDefinitionVersionId_Last(
			kaleoDefinitionVersionId, orderByComparator);
	}

	/**
	 * Returns the last kaleo task assignment in the ordered set where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo task assignment, or <code>null</code> if a matching kaleo task assignment could not be found
	 */
	public static KaleoTaskAssignment fetchByKaleoDefinitionVersionId_Last(
		long kaleoDefinitionVersionId,
		OrderByComparator<KaleoTaskAssignment> orderByComparator) {

		return getPersistence().fetchByKaleoDefinitionVersionId_Last(
			kaleoDefinitionVersionId, orderByComparator);
	}

	/**
	 * Returns the kaleo task assignments before and after the current kaleo task assignment in the ordered set where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoTaskAssignmentId the primary key of the current kaleo task assignment
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next kaleo task assignment
	 * @throws NoSuchTaskAssignmentException if a kaleo task assignment with the primary key could not be found
	 */
	public static KaleoTaskAssignment[]
			findByKaleoDefinitionVersionId_PrevAndNext(
				long kaleoTaskAssignmentId, long kaleoDefinitionVersionId,
				OrderByComparator<KaleoTaskAssignment> orderByComparator)
		throws com.liferay.portal.workflow.kaleo.exception.
			NoSuchTaskAssignmentException {

		return getPersistence().findByKaleoDefinitionVersionId_PrevAndNext(
			kaleoTaskAssignmentId, kaleoDefinitionVersionId, orderByComparator);
	}

	/**
	 * Removes all the kaleo task assignments where kaleoDefinitionVersionId = &#63; from the database.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 */
	public static void removeByKaleoDefinitionVersionId(
		long kaleoDefinitionVersionId) {

		getPersistence().removeByKaleoDefinitionVersionId(
			kaleoDefinitionVersionId);
	}

	/**
	 * Returns the number of kaleo task assignments where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @return the number of matching kaleo task assignments
	 */
	public static int countByKaleoDefinitionVersionId(
		long kaleoDefinitionVersionId) {

		return getPersistence().countByKaleoDefinitionVersionId(
			kaleoDefinitionVersionId);
	}

	/**
	 * Returns all the kaleo task assignments where kaleoClassName = &#63; and kaleoClassPK = &#63;.
	 *
	 * @param kaleoClassName the kaleo class name
	 * @param kaleoClassPK the kaleo class pk
	 * @return the matching kaleo task assignments
	 */
	public static List<KaleoTaskAssignment> findByKCN_KCPK(
		String kaleoClassName, long kaleoClassPK) {

		return getPersistence().findByKCN_KCPK(kaleoClassName, kaleoClassPK);
	}

	/**
	 * Returns a range of all the kaleo task assignments where kaleoClassName = &#63; and kaleoClassPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTaskAssignmentModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoClassName the kaleo class name
	 * @param kaleoClassPK the kaleo class pk
	 * @param start the lower bound of the range of kaleo task assignments
	 * @param end the upper bound of the range of kaleo task assignments (not inclusive)
	 * @return the range of matching kaleo task assignments
	 */
	public static List<KaleoTaskAssignment> findByKCN_KCPK(
		String kaleoClassName, long kaleoClassPK, int start, int end) {

		return getPersistence().findByKCN_KCPK(
			kaleoClassName, kaleoClassPK, start, end);
	}

	/**
	 * Returns an ordered range of all the kaleo task assignments where kaleoClassName = &#63; and kaleoClassPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTaskAssignmentModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoClassName the kaleo class name
	 * @param kaleoClassPK the kaleo class pk
	 * @param start the lower bound of the range of kaleo task assignments
	 * @param end the upper bound of the range of kaleo task assignments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo task assignments
	 */
	public static List<KaleoTaskAssignment> findByKCN_KCPK(
		String kaleoClassName, long kaleoClassPK, int start, int end,
		OrderByComparator<KaleoTaskAssignment> orderByComparator) {

		return getPersistence().findByKCN_KCPK(
			kaleoClassName, kaleoClassPK, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the kaleo task assignments where kaleoClassName = &#63; and kaleoClassPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTaskAssignmentModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoClassName the kaleo class name
	 * @param kaleoClassPK the kaleo class pk
	 * @param start the lower bound of the range of kaleo task assignments
	 * @param end the upper bound of the range of kaleo task assignments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching kaleo task assignments
	 */
	public static List<KaleoTaskAssignment> findByKCN_KCPK(
		String kaleoClassName, long kaleoClassPK, int start, int end,
		OrderByComparator<KaleoTaskAssignment> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByKCN_KCPK(
			kaleoClassName, kaleoClassPK, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first kaleo task assignment in the ordered set where kaleoClassName = &#63; and kaleoClassPK = &#63;.
	 *
	 * @param kaleoClassName the kaleo class name
	 * @param kaleoClassPK the kaleo class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo task assignment
	 * @throws NoSuchTaskAssignmentException if a matching kaleo task assignment could not be found
	 */
	public static KaleoTaskAssignment findByKCN_KCPK_First(
			String kaleoClassName, long kaleoClassPK,
			OrderByComparator<KaleoTaskAssignment> orderByComparator)
		throws com.liferay.portal.workflow.kaleo.exception.
			NoSuchTaskAssignmentException {

		return getPersistence().findByKCN_KCPK_First(
			kaleoClassName, kaleoClassPK, orderByComparator);
	}

	/**
	 * Returns the first kaleo task assignment in the ordered set where kaleoClassName = &#63; and kaleoClassPK = &#63;.
	 *
	 * @param kaleoClassName the kaleo class name
	 * @param kaleoClassPK the kaleo class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo task assignment, or <code>null</code> if a matching kaleo task assignment could not be found
	 */
	public static KaleoTaskAssignment fetchByKCN_KCPK_First(
		String kaleoClassName, long kaleoClassPK,
		OrderByComparator<KaleoTaskAssignment> orderByComparator) {

		return getPersistence().fetchByKCN_KCPK_First(
			kaleoClassName, kaleoClassPK, orderByComparator);
	}

	/**
	 * Returns the last kaleo task assignment in the ordered set where kaleoClassName = &#63; and kaleoClassPK = &#63;.
	 *
	 * @param kaleoClassName the kaleo class name
	 * @param kaleoClassPK the kaleo class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo task assignment
	 * @throws NoSuchTaskAssignmentException if a matching kaleo task assignment could not be found
	 */
	public static KaleoTaskAssignment findByKCN_KCPK_Last(
			String kaleoClassName, long kaleoClassPK,
			OrderByComparator<KaleoTaskAssignment> orderByComparator)
		throws com.liferay.portal.workflow.kaleo.exception.
			NoSuchTaskAssignmentException {

		return getPersistence().findByKCN_KCPK_Last(
			kaleoClassName, kaleoClassPK, orderByComparator);
	}

	/**
	 * Returns the last kaleo task assignment in the ordered set where kaleoClassName = &#63; and kaleoClassPK = &#63;.
	 *
	 * @param kaleoClassName the kaleo class name
	 * @param kaleoClassPK the kaleo class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo task assignment, or <code>null</code> if a matching kaleo task assignment could not be found
	 */
	public static KaleoTaskAssignment fetchByKCN_KCPK_Last(
		String kaleoClassName, long kaleoClassPK,
		OrderByComparator<KaleoTaskAssignment> orderByComparator) {

		return getPersistence().fetchByKCN_KCPK_Last(
			kaleoClassName, kaleoClassPK, orderByComparator);
	}

	/**
	 * Returns the kaleo task assignments before and after the current kaleo task assignment in the ordered set where kaleoClassName = &#63; and kaleoClassPK = &#63;.
	 *
	 * @param kaleoTaskAssignmentId the primary key of the current kaleo task assignment
	 * @param kaleoClassName the kaleo class name
	 * @param kaleoClassPK the kaleo class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next kaleo task assignment
	 * @throws NoSuchTaskAssignmentException if a kaleo task assignment with the primary key could not be found
	 */
	public static KaleoTaskAssignment[] findByKCN_KCPK_PrevAndNext(
			long kaleoTaskAssignmentId, String kaleoClassName,
			long kaleoClassPK,
			OrderByComparator<KaleoTaskAssignment> orderByComparator)
		throws com.liferay.portal.workflow.kaleo.exception.
			NoSuchTaskAssignmentException {

		return getPersistence().findByKCN_KCPK_PrevAndNext(
			kaleoTaskAssignmentId, kaleoClassName, kaleoClassPK,
			orderByComparator);
	}

	/**
	 * Removes all the kaleo task assignments where kaleoClassName = &#63; and kaleoClassPK = &#63; from the database.
	 *
	 * @param kaleoClassName the kaleo class name
	 * @param kaleoClassPK the kaleo class pk
	 */
	public static void removeByKCN_KCPK(
		String kaleoClassName, long kaleoClassPK) {

		getPersistence().removeByKCN_KCPK(kaleoClassName, kaleoClassPK);
	}

	/**
	 * Returns the number of kaleo task assignments where kaleoClassName = &#63; and kaleoClassPK = &#63;.
	 *
	 * @param kaleoClassName the kaleo class name
	 * @param kaleoClassPK the kaleo class pk
	 * @return the number of matching kaleo task assignments
	 */
	public static int countByKCN_KCPK(
		String kaleoClassName, long kaleoClassPK) {

		return getPersistence().countByKCN_KCPK(kaleoClassName, kaleoClassPK);
	}

	/**
	 * Returns all the kaleo task assignments where kaleoClassName = &#63; and kaleoClassPK = &#63; and assigneeClassName = &#63;.
	 *
	 * @param kaleoClassName the kaleo class name
	 * @param kaleoClassPK the kaleo class pk
	 * @param assigneeClassName the assignee class name
	 * @return the matching kaleo task assignments
	 */
	public static List<KaleoTaskAssignment> findByKCN_KCPK_ACN(
		String kaleoClassName, long kaleoClassPK, String assigneeClassName) {

		return getPersistence().findByKCN_KCPK_ACN(
			kaleoClassName, kaleoClassPK, assigneeClassName);
	}

	/**
	 * Returns a range of all the kaleo task assignments where kaleoClassName = &#63; and kaleoClassPK = &#63; and assigneeClassName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTaskAssignmentModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoClassName the kaleo class name
	 * @param kaleoClassPK the kaleo class pk
	 * @param assigneeClassName the assignee class name
	 * @param start the lower bound of the range of kaleo task assignments
	 * @param end the upper bound of the range of kaleo task assignments (not inclusive)
	 * @return the range of matching kaleo task assignments
	 */
	public static List<KaleoTaskAssignment> findByKCN_KCPK_ACN(
		String kaleoClassName, long kaleoClassPK, String assigneeClassName,
		int start, int end) {

		return getPersistence().findByKCN_KCPK_ACN(
			kaleoClassName, kaleoClassPK, assigneeClassName, start, end);
	}

	/**
	 * Returns an ordered range of all the kaleo task assignments where kaleoClassName = &#63; and kaleoClassPK = &#63; and assigneeClassName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTaskAssignmentModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoClassName the kaleo class name
	 * @param kaleoClassPK the kaleo class pk
	 * @param assigneeClassName the assignee class name
	 * @param start the lower bound of the range of kaleo task assignments
	 * @param end the upper bound of the range of kaleo task assignments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo task assignments
	 */
	public static List<KaleoTaskAssignment> findByKCN_KCPK_ACN(
		String kaleoClassName, long kaleoClassPK, String assigneeClassName,
		int start, int end,
		OrderByComparator<KaleoTaskAssignment> orderByComparator) {

		return getPersistence().findByKCN_KCPK_ACN(
			kaleoClassName, kaleoClassPK, assigneeClassName, start, end,
			orderByComparator);
	}

	/**
	 * Returns an ordered range of all the kaleo task assignments where kaleoClassName = &#63; and kaleoClassPK = &#63; and assigneeClassName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTaskAssignmentModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoClassName the kaleo class name
	 * @param kaleoClassPK the kaleo class pk
	 * @param assigneeClassName the assignee class name
	 * @param start the lower bound of the range of kaleo task assignments
	 * @param end the upper bound of the range of kaleo task assignments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching kaleo task assignments
	 */
	public static List<KaleoTaskAssignment> findByKCN_KCPK_ACN(
		String kaleoClassName, long kaleoClassPK, String assigneeClassName,
		int start, int end,
		OrderByComparator<KaleoTaskAssignment> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByKCN_KCPK_ACN(
			kaleoClassName, kaleoClassPK, assigneeClassName, start, end,
			orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first kaleo task assignment in the ordered set where kaleoClassName = &#63; and kaleoClassPK = &#63; and assigneeClassName = &#63;.
	 *
	 * @param kaleoClassName the kaleo class name
	 * @param kaleoClassPK the kaleo class pk
	 * @param assigneeClassName the assignee class name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo task assignment
	 * @throws NoSuchTaskAssignmentException if a matching kaleo task assignment could not be found
	 */
	public static KaleoTaskAssignment findByKCN_KCPK_ACN_First(
			String kaleoClassName, long kaleoClassPK, String assigneeClassName,
			OrderByComparator<KaleoTaskAssignment> orderByComparator)
		throws com.liferay.portal.workflow.kaleo.exception.
			NoSuchTaskAssignmentException {

		return getPersistence().findByKCN_KCPK_ACN_First(
			kaleoClassName, kaleoClassPK, assigneeClassName, orderByComparator);
	}

	/**
	 * Returns the first kaleo task assignment in the ordered set where kaleoClassName = &#63; and kaleoClassPK = &#63; and assigneeClassName = &#63;.
	 *
	 * @param kaleoClassName the kaleo class name
	 * @param kaleoClassPK the kaleo class pk
	 * @param assigneeClassName the assignee class name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo task assignment, or <code>null</code> if a matching kaleo task assignment could not be found
	 */
	public static KaleoTaskAssignment fetchByKCN_KCPK_ACN_First(
		String kaleoClassName, long kaleoClassPK, String assigneeClassName,
		OrderByComparator<KaleoTaskAssignment> orderByComparator) {

		return getPersistence().fetchByKCN_KCPK_ACN_First(
			kaleoClassName, kaleoClassPK, assigneeClassName, orderByComparator);
	}

	/**
	 * Returns the last kaleo task assignment in the ordered set where kaleoClassName = &#63; and kaleoClassPK = &#63; and assigneeClassName = &#63;.
	 *
	 * @param kaleoClassName the kaleo class name
	 * @param kaleoClassPK the kaleo class pk
	 * @param assigneeClassName the assignee class name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo task assignment
	 * @throws NoSuchTaskAssignmentException if a matching kaleo task assignment could not be found
	 */
	public static KaleoTaskAssignment findByKCN_KCPK_ACN_Last(
			String kaleoClassName, long kaleoClassPK, String assigneeClassName,
			OrderByComparator<KaleoTaskAssignment> orderByComparator)
		throws com.liferay.portal.workflow.kaleo.exception.
			NoSuchTaskAssignmentException {

		return getPersistence().findByKCN_KCPK_ACN_Last(
			kaleoClassName, kaleoClassPK, assigneeClassName, orderByComparator);
	}

	/**
	 * Returns the last kaleo task assignment in the ordered set where kaleoClassName = &#63; and kaleoClassPK = &#63; and assigneeClassName = &#63;.
	 *
	 * @param kaleoClassName the kaleo class name
	 * @param kaleoClassPK the kaleo class pk
	 * @param assigneeClassName the assignee class name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo task assignment, or <code>null</code> if a matching kaleo task assignment could not be found
	 */
	public static KaleoTaskAssignment fetchByKCN_KCPK_ACN_Last(
		String kaleoClassName, long kaleoClassPK, String assigneeClassName,
		OrderByComparator<KaleoTaskAssignment> orderByComparator) {

		return getPersistence().fetchByKCN_KCPK_ACN_Last(
			kaleoClassName, kaleoClassPK, assigneeClassName, orderByComparator);
	}

	/**
	 * Returns the kaleo task assignments before and after the current kaleo task assignment in the ordered set where kaleoClassName = &#63; and kaleoClassPK = &#63; and assigneeClassName = &#63;.
	 *
	 * @param kaleoTaskAssignmentId the primary key of the current kaleo task assignment
	 * @param kaleoClassName the kaleo class name
	 * @param kaleoClassPK the kaleo class pk
	 * @param assigneeClassName the assignee class name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next kaleo task assignment
	 * @throws NoSuchTaskAssignmentException if a kaleo task assignment with the primary key could not be found
	 */
	public static KaleoTaskAssignment[] findByKCN_KCPK_ACN_PrevAndNext(
			long kaleoTaskAssignmentId, String kaleoClassName,
			long kaleoClassPK, String assigneeClassName,
			OrderByComparator<KaleoTaskAssignment> orderByComparator)
		throws com.liferay.portal.workflow.kaleo.exception.
			NoSuchTaskAssignmentException {

		return getPersistence().findByKCN_KCPK_ACN_PrevAndNext(
			kaleoTaskAssignmentId, kaleoClassName, kaleoClassPK,
			assigneeClassName, orderByComparator);
	}

	/**
	 * Removes all the kaleo task assignments where kaleoClassName = &#63; and kaleoClassPK = &#63; and assigneeClassName = &#63; from the database.
	 *
	 * @param kaleoClassName the kaleo class name
	 * @param kaleoClassPK the kaleo class pk
	 * @param assigneeClassName the assignee class name
	 */
	public static void removeByKCN_KCPK_ACN(
		String kaleoClassName, long kaleoClassPK, String assigneeClassName) {

		getPersistence().removeByKCN_KCPK_ACN(
			kaleoClassName, kaleoClassPK, assigneeClassName);
	}

	/**
	 * Returns the number of kaleo task assignments where kaleoClassName = &#63; and kaleoClassPK = &#63; and assigneeClassName = &#63;.
	 *
	 * @param kaleoClassName the kaleo class name
	 * @param kaleoClassPK the kaleo class pk
	 * @param assigneeClassName the assignee class name
	 * @return the number of matching kaleo task assignments
	 */
	public static int countByKCN_KCPK_ACN(
		String kaleoClassName, long kaleoClassPK, String assigneeClassName) {

		return getPersistence().countByKCN_KCPK_ACN(
			kaleoClassName, kaleoClassPK, assigneeClassName);
	}

	/**
	 * Caches the kaleo task assignment in the entity cache if it is enabled.
	 *
	 * @param kaleoTaskAssignment the kaleo task assignment
	 */
	public static void cacheResult(KaleoTaskAssignment kaleoTaskAssignment) {
		getPersistence().cacheResult(kaleoTaskAssignment);
	}

	/**
	 * Caches the kaleo task assignments in the entity cache if it is enabled.
	 *
	 * @param kaleoTaskAssignments the kaleo task assignments
	 */
	public static void cacheResult(
		List<KaleoTaskAssignment> kaleoTaskAssignments) {

		getPersistence().cacheResult(kaleoTaskAssignments);
	}

	/**
	 * Creates a new kaleo task assignment with the primary key. Does not add the kaleo task assignment to the database.
	 *
	 * @param kaleoTaskAssignmentId the primary key for the new kaleo task assignment
	 * @return the new kaleo task assignment
	 */
	public static KaleoTaskAssignment create(long kaleoTaskAssignmentId) {
		return getPersistence().create(kaleoTaskAssignmentId);
	}

	/**
	 * Removes the kaleo task assignment with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param kaleoTaskAssignmentId the primary key of the kaleo task assignment
	 * @return the kaleo task assignment that was removed
	 * @throws NoSuchTaskAssignmentException if a kaleo task assignment with the primary key could not be found
	 */
	public static KaleoTaskAssignment remove(long kaleoTaskAssignmentId)
		throws com.liferay.portal.workflow.kaleo.exception.
			NoSuchTaskAssignmentException {

		return getPersistence().remove(kaleoTaskAssignmentId);
	}

	public static KaleoTaskAssignment updateImpl(
		KaleoTaskAssignment kaleoTaskAssignment) {

		return getPersistence().updateImpl(kaleoTaskAssignment);
	}

	/**
	 * Returns the kaleo task assignment with the primary key or throws a <code>NoSuchTaskAssignmentException</code> if it could not be found.
	 *
	 * @param kaleoTaskAssignmentId the primary key of the kaleo task assignment
	 * @return the kaleo task assignment
	 * @throws NoSuchTaskAssignmentException if a kaleo task assignment with the primary key could not be found
	 */
	public static KaleoTaskAssignment findByPrimaryKey(
			long kaleoTaskAssignmentId)
		throws com.liferay.portal.workflow.kaleo.exception.
			NoSuchTaskAssignmentException {

		return getPersistence().findByPrimaryKey(kaleoTaskAssignmentId);
	}

	/**
	 * Returns the kaleo task assignment with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param kaleoTaskAssignmentId the primary key of the kaleo task assignment
	 * @return the kaleo task assignment, or <code>null</code> if a kaleo task assignment with the primary key could not be found
	 */
	public static KaleoTaskAssignment fetchByPrimaryKey(
		long kaleoTaskAssignmentId) {

		return getPersistence().fetchByPrimaryKey(kaleoTaskAssignmentId);
	}

	/**
	 * Returns all the kaleo task assignments.
	 *
	 * @return the kaleo task assignments
	 */
	public static List<KaleoTaskAssignment> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the kaleo task assignments.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTaskAssignmentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of kaleo task assignments
	 * @param end the upper bound of the range of kaleo task assignments (not inclusive)
	 * @return the range of kaleo task assignments
	 */
	public static List<KaleoTaskAssignment> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the kaleo task assignments.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTaskAssignmentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of kaleo task assignments
	 * @param end the upper bound of the range of kaleo task assignments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of kaleo task assignments
	 */
	public static List<KaleoTaskAssignment> findAll(
		int start, int end,
		OrderByComparator<KaleoTaskAssignment> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the kaleo task assignments.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTaskAssignmentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of kaleo task assignments
	 * @param end the upper bound of the range of kaleo task assignments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of kaleo task assignments
	 */
	public static List<KaleoTaskAssignment> findAll(
		int start, int end,
		OrderByComparator<KaleoTaskAssignment> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the kaleo task assignments from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of kaleo task assignments.
	 *
	 * @return the number of kaleo task assignments
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static KaleoTaskAssignmentPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(
		KaleoTaskAssignmentPersistence persistence) {

		_persistence = persistence;
	}

	private static volatile KaleoTaskAssignmentPersistence _persistence;

}