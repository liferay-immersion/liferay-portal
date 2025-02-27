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
import com.liferay.portal.workflow.kaleo.model.KaleoNode;

/**
 * Provides a wrapper for {@link KaleoNodeLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see KaleoNodeLocalService
 * @generated
 */
public class KaleoNodeLocalServiceWrapper
	implements KaleoNodeLocalService, ServiceWrapper<KaleoNodeLocalService> {

	public KaleoNodeLocalServiceWrapper() {
		this(null);
	}

	public KaleoNodeLocalServiceWrapper(
		KaleoNodeLocalService kaleoNodeLocalService) {

		_kaleoNodeLocalService = kaleoNodeLocalService;
	}

	/**
	 * Adds the kaleo node to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect KaleoNodeLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param kaleoNode the kaleo node
	 * @return the kaleo node that was added
	 */
	@Override
	public KaleoNode addKaleoNode(KaleoNode kaleoNode) {
		return _kaleoNodeLocalService.addKaleoNode(kaleoNode);
	}

	@Override
	public KaleoNode addKaleoNode(
			long kaleoDefinitionId, long kaleoDefinitionVersionId,
			com.liferay.portal.workflow.kaleo.definition.Node node,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kaleoNodeLocalService.addKaleoNode(
			kaleoDefinitionId, kaleoDefinitionVersionId, node, serviceContext);
	}

	/**
	 * Creates a new kaleo node with the primary key. Does not add the kaleo node to the database.
	 *
	 * @param kaleoNodeId the primary key for the new kaleo node
	 * @return the new kaleo node
	 */
	@Override
	public KaleoNode createKaleoNode(long kaleoNodeId) {
		return _kaleoNodeLocalService.createKaleoNode(kaleoNodeId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kaleoNodeLocalService.createPersistedModel(primaryKeyObj);
	}

	@Override
	public void deleteCompanyKaleoNodes(long companyId) {
		_kaleoNodeLocalService.deleteCompanyKaleoNodes(companyId);
	}

	@Override
	public void deleteKaleoDefinitionVersionKaleoNodes(
		long kaleoDefinitionVersionId) {

		_kaleoNodeLocalService.deleteKaleoDefinitionVersionKaleoNodes(
			kaleoDefinitionVersionId);
	}

	/**
	 * Deletes the kaleo node from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect KaleoNodeLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param kaleoNode the kaleo node
	 * @return the kaleo node that was removed
	 */
	@Override
	public KaleoNode deleteKaleoNode(KaleoNode kaleoNode) {
		return _kaleoNodeLocalService.deleteKaleoNode(kaleoNode);
	}

	/**
	 * Deletes the kaleo node with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect KaleoNodeLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param kaleoNodeId the primary key of the kaleo node
	 * @return the kaleo node that was removed
	 * @throws PortalException if a kaleo node with the primary key could not be found
	 */
	@Override
	public KaleoNode deleteKaleoNode(long kaleoNodeId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kaleoNodeLocalService.deleteKaleoNode(kaleoNodeId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kaleoNodeLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _kaleoNodeLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _kaleoNodeLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _kaleoNodeLocalService.dynamicQuery();
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

		return _kaleoNodeLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.workflow.kaleo.model.impl.KaleoNodeModelImpl</code>.
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

		return _kaleoNodeLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.workflow.kaleo.model.impl.KaleoNodeModelImpl</code>.
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

		return _kaleoNodeLocalService.dynamicQuery(
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

		return _kaleoNodeLocalService.dynamicQueryCount(dynamicQuery);
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

		return _kaleoNodeLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public KaleoNode fetchKaleoNode(long kaleoNodeId) {
		return _kaleoNodeLocalService.fetchKaleoNode(kaleoNodeId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _kaleoNodeLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _kaleoNodeLocalService.getIndexableActionableDynamicQuery();
	}

	@Override
	public java.util.List<KaleoNode> getKaleoDefinitionVersionKaleoNodes(
		long kaleoDefinitionVersionId) {

		return _kaleoNodeLocalService.getKaleoDefinitionVersionKaleoNodes(
			kaleoDefinitionVersionId);
	}

	/**
	 * Returns the kaleo node with the primary key.
	 *
	 * @param kaleoNodeId the primary key of the kaleo node
	 * @return the kaleo node
	 * @throws PortalException if a kaleo node with the primary key could not be found
	 */
	@Override
	public KaleoNode getKaleoNode(long kaleoNodeId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kaleoNodeLocalService.getKaleoNode(kaleoNodeId);
	}

	/**
	 * Returns a range of all the kaleo nodes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.workflow.kaleo.model.impl.KaleoNodeModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of kaleo nodes
	 * @param end the upper bound of the range of kaleo nodes (not inclusive)
	 * @return the range of kaleo nodes
	 */
	@Override
	public java.util.List<KaleoNode> getKaleoNodes(int start, int end) {
		return _kaleoNodeLocalService.getKaleoNodes(start, end);
	}

	/**
	 * Returns the number of kaleo nodes.
	 *
	 * @return the number of kaleo nodes
	 */
	@Override
	public int getKaleoNodesCount() {
		return _kaleoNodeLocalService.getKaleoNodesCount();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _kaleoNodeLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kaleoNodeLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Updates the kaleo node in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect KaleoNodeLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param kaleoNode the kaleo node
	 * @return the kaleo node that was updated
	 */
	@Override
	public KaleoNode updateKaleoNode(KaleoNode kaleoNode) {
		return _kaleoNodeLocalService.updateKaleoNode(kaleoNode);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _kaleoNodeLocalService.getBasePersistence();
	}

	@Override
	public CTPersistence<KaleoNode> getCTPersistence() {
		return _kaleoNodeLocalService.getCTPersistence();
	}

	@Override
	public Class<KaleoNode> getModelClass() {
		return _kaleoNodeLocalService.getModelClass();
	}

	@Override
	public <R, E extends Throwable> R updateWithUnsafeFunction(
			UnsafeFunction<CTPersistence<KaleoNode>, R, E> updateUnsafeFunction)
		throws E {

		return _kaleoNodeLocalService.updateWithUnsafeFunction(
			updateUnsafeFunction);
	}

	@Override
	public KaleoNodeLocalService getWrappedService() {
		return _kaleoNodeLocalService;
	}

	@Override
	public void setWrappedService(KaleoNodeLocalService kaleoNodeLocalService) {
		_kaleoNodeLocalService = kaleoNodeLocalService;
	}

	private KaleoNodeLocalService _kaleoNodeLocalService;

}