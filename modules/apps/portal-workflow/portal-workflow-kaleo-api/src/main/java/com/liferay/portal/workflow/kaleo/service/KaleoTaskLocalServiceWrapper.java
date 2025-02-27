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

package com.liferay.portal.workflow.kaleo.service;

import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;
import com.liferay.portal.workflow.kaleo.model.KaleoTask;

/**
 * Provides a wrapper for {@link KaleoTaskLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see KaleoTaskLocalService
 * @generated
 */
public class KaleoTaskLocalServiceWrapper
	implements KaleoTaskLocalService, ServiceWrapper<KaleoTaskLocalService> {

	public KaleoTaskLocalServiceWrapper() {
		this(null);
	}

	public KaleoTaskLocalServiceWrapper(
		KaleoTaskLocalService kaleoTaskLocalService) {

		_kaleoTaskLocalService = kaleoTaskLocalService;
	}

	/**
	 * Adds the kaleo task to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect KaleoTaskLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param kaleoTask the kaleo task
	 * @return the kaleo task that was added
	 */
	@Override
	public KaleoTask addKaleoTask(KaleoTask kaleoTask) {
		return _kaleoTaskLocalService.addKaleoTask(kaleoTask);
	}

	@Override
	public KaleoTask addKaleoTask(
			long kaleoDefinitionId, long kaleoDefinitionVersionId,
			long kaleoNodeId,
			com.liferay.portal.workflow.kaleo.definition.Task task,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kaleoTaskLocalService.addKaleoTask(
			kaleoDefinitionId, kaleoDefinitionVersionId, kaleoNodeId, task,
			serviceContext);
	}

	/**
	 * Creates a new kaleo task with the primary key. Does not add the kaleo task to the database.
	 *
	 * @param kaleoTaskId the primary key for the new kaleo task
	 * @return the new kaleo task
	 */
	@Override
	public KaleoTask createKaleoTask(long kaleoTaskId) {
		return _kaleoTaskLocalService.createKaleoTask(kaleoTaskId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kaleoTaskLocalService.createPersistedModel(primaryKeyObj);
	}

	@Override
	public void deleteCompanyKaleoTasks(long companyId) {
		_kaleoTaskLocalService.deleteCompanyKaleoTasks(companyId);
	}

	@Override
	public void deleteKaleoDefinitionVersionKaleoTasks(
		long kaleoDefinitionVersionId) {

		_kaleoTaskLocalService.deleteKaleoDefinitionVersionKaleoTasks(
			kaleoDefinitionVersionId);
	}

	/**
	 * Deletes the kaleo task from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect KaleoTaskLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param kaleoTask the kaleo task
	 * @return the kaleo task that was removed
	 */
	@Override
	public KaleoTask deleteKaleoTask(KaleoTask kaleoTask) {
		return _kaleoTaskLocalService.deleteKaleoTask(kaleoTask);
	}

	/**
	 * Deletes the kaleo task with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect KaleoTaskLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param kaleoTaskId the primary key of the kaleo task
	 * @return the kaleo task that was removed
	 * @throws PortalException if a kaleo task with the primary key could not be found
	 */
	@Override
	public KaleoTask deleteKaleoTask(long kaleoTaskId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kaleoTaskLocalService.deleteKaleoTask(kaleoTaskId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kaleoTaskLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _kaleoTaskLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _kaleoTaskLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _kaleoTaskLocalService.dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return _kaleoTaskLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.workflow.kaleo.model.impl.KaleoTaskModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {

		return _kaleoTaskLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.workflow.kaleo.model.impl.KaleoTaskModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {

		return _kaleoTaskLocalService.dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return _kaleoTaskLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return _kaleoTaskLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public KaleoTask fetchKaleoTask(long kaleoTaskId) {
		return _kaleoTaskLocalService.fetchKaleoTask(kaleoTaskId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _kaleoTaskLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _kaleoTaskLocalService.getIndexableActionableDynamicQuery();
	}

	@Override
	public KaleoTask getKaleoNodeKaleoTask(long kaleoNodeId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kaleoTaskLocalService.getKaleoNodeKaleoTask(kaleoNodeId);
	}

	/**
	 * Returns the kaleo task with the primary key.
	 *
	 * @param kaleoTaskId the primary key of the kaleo task
	 * @return the kaleo task
	 * @throws PortalException if a kaleo task with the primary key could not be found
	 */
	@Override
	public KaleoTask getKaleoTask(long kaleoTaskId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kaleoTaskLocalService.getKaleoTask(kaleoTaskId);
	}

	/**
	 * Returns a range of all the kaleo tasks.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.workflow.kaleo.model.impl.KaleoTaskModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of kaleo tasks
	 * @param end the upper bound of the range of kaleo tasks (not inclusive)
	 * @return the range of kaleo tasks
	 */
	@Override
	public java.util.List<KaleoTask> getKaleoTasks(int start, int end) {
		return _kaleoTaskLocalService.getKaleoTasks(start, end);
	}

	/**
	 * Returns the number of kaleo tasks.
	 *
	 * @return the number of kaleo tasks
	 */
	@Override
	public int getKaleoTasksCount() {
		return _kaleoTaskLocalService.getKaleoTasksCount();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _kaleoTaskLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kaleoTaskLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Updates the kaleo task in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect KaleoTaskLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param kaleoTask the kaleo task
	 * @return the kaleo task that was updated
	 */
	@Override
	public KaleoTask updateKaleoTask(KaleoTask kaleoTask) {
		return _kaleoTaskLocalService.updateKaleoTask(kaleoTask);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _kaleoTaskLocalService.getBasePersistence();
	}

	@Override
	public CTPersistence<KaleoTask> getCTPersistence() {
		return _kaleoTaskLocalService.getCTPersistence();
	}

	@Override
	public Class<KaleoTask> getModelClass() {
		return _kaleoTaskLocalService.getModelClass();
	}

	@Override
	public <R, E extends Throwable> R updateWithUnsafeFunction(
			UnsafeFunction<CTPersistence<KaleoTask>, R, E> updateUnsafeFunction)
		throws E {

		return _kaleoTaskLocalService.updateWithUnsafeFunction(
			updateUnsafeFunction);
	}

	@Override
	public KaleoTaskLocalService getWrappedService() {
		return _kaleoTaskLocalService;
	}

	@Override
	public void setWrappedService(KaleoTaskLocalService kaleoTaskLocalService) {
		_kaleoTaskLocalService = kaleoTaskLocalService;
	}

	private KaleoTaskLocalService _kaleoTaskLocalService;

}