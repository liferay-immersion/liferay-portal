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
import com.liferay.portal.workflow.kaleo.model.KaleoNotificationRecipient;

/**
 * Provides a wrapper for {@link KaleoNotificationRecipientLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see KaleoNotificationRecipientLocalService
 * @generated
 */
public class KaleoNotificationRecipientLocalServiceWrapper
	implements KaleoNotificationRecipientLocalService,
			   ServiceWrapper<KaleoNotificationRecipientLocalService> {

	public KaleoNotificationRecipientLocalServiceWrapper() {
		this(null);
	}

	public KaleoNotificationRecipientLocalServiceWrapper(
		KaleoNotificationRecipientLocalService
			kaleoNotificationRecipientLocalService) {

		_kaleoNotificationRecipientLocalService =
			kaleoNotificationRecipientLocalService;
	}

	/**
	 * Adds the kaleo notification recipient to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect KaleoNotificationRecipientLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param kaleoNotificationRecipient the kaleo notification recipient
	 * @return the kaleo notification recipient that was added
	 */
	@Override
	public KaleoNotificationRecipient addKaleoNotificationRecipient(
		KaleoNotificationRecipient kaleoNotificationRecipient) {

		return _kaleoNotificationRecipientLocalService.
			addKaleoNotificationRecipient(kaleoNotificationRecipient);
	}

	@Override
	public KaleoNotificationRecipient addKaleoNotificationRecipient(
			long kaleoDefinitionId, long kaleoDefinitionVersionId,
			long kaleoNotificationId,
			com.liferay.portal.workflow.kaleo.definition.Recipient recipient,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kaleoNotificationRecipientLocalService.
			addKaleoNotificationRecipient(
				kaleoDefinitionId, kaleoDefinitionVersionId,
				kaleoNotificationId, recipient, serviceContext);
	}

	/**
	 * Creates a new kaleo notification recipient with the primary key. Does not add the kaleo notification recipient to the database.
	 *
	 * @param kaleoNotificationRecipientId the primary key for the new kaleo notification recipient
	 * @return the new kaleo notification recipient
	 */
	@Override
	public KaleoNotificationRecipient createKaleoNotificationRecipient(
		long kaleoNotificationRecipientId) {

		return _kaleoNotificationRecipientLocalService.
			createKaleoNotificationRecipient(kaleoNotificationRecipientId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kaleoNotificationRecipientLocalService.createPersistedModel(
			primaryKeyObj);
	}

	@Override
	public void deleteCompanyKaleoNotificationRecipients(long companyId) {
		_kaleoNotificationRecipientLocalService.
			deleteCompanyKaleoNotificationRecipients(companyId);
	}

	@Override
	public void deleteKaleoDefinitionVersionKaleoNotificationRecipients(
		long kaleoDefinitionVersionId) {

		_kaleoNotificationRecipientLocalService.
			deleteKaleoDefinitionVersionKaleoNotificationRecipients(
				kaleoDefinitionVersionId);
	}

	/**
	 * Deletes the kaleo notification recipient from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect KaleoNotificationRecipientLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param kaleoNotificationRecipient the kaleo notification recipient
	 * @return the kaleo notification recipient that was removed
	 */
	@Override
	public KaleoNotificationRecipient deleteKaleoNotificationRecipient(
		KaleoNotificationRecipient kaleoNotificationRecipient) {

		return _kaleoNotificationRecipientLocalService.
			deleteKaleoNotificationRecipient(kaleoNotificationRecipient);
	}

	/**
	 * Deletes the kaleo notification recipient with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect KaleoNotificationRecipientLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param kaleoNotificationRecipientId the primary key of the kaleo notification recipient
	 * @return the kaleo notification recipient that was removed
	 * @throws PortalException if a kaleo notification recipient with the primary key could not be found
	 */
	@Override
	public KaleoNotificationRecipient deleteKaleoNotificationRecipient(
			long kaleoNotificationRecipientId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kaleoNotificationRecipientLocalService.
			deleteKaleoNotificationRecipient(kaleoNotificationRecipientId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kaleoNotificationRecipientLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _kaleoNotificationRecipientLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _kaleoNotificationRecipientLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _kaleoNotificationRecipientLocalService.dynamicQuery();
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

		return _kaleoNotificationRecipientLocalService.dynamicQuery(
			dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.workflow.kaleo.model.impl.KaleoNotificationRecipientModelImpl</code>.
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

		return _kaleoNotificationRecipientLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.workflow.kaleo.model.impl.KaleoNotificationRecipientModelImpl</code>.
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

		return _kaleoNotificationRecipientLocalService.dynamicQuery(
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

		return _kaleoNotificationRecipientLocalService.dynamicQueryCount(
			dynamicQuery);
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

		return _kaleoNotificationRecipientLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public KaleoNotificationRecipient fetchKaleoNotificationRecipient(
		long kaleoNotificationRecipientId) {

		return _kaleoNotificationRecipientLocalService.
			fetchKaleoNotificationRecipient(kaleoNotificationRecipientId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _kaleoNotificationRecipientLocalService.
			getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _kaleoNotificationRecipientLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the kaleo notification recipient with the primary key.
	 *
	 * @param kaleoNotificationRecipientId the primary key of the kaleo notification recipient
	 * @return the kaleo notification recipient
	 * @throws PortalException if a kaleo notification recipient with the primary key could not be found
	 */
	@Override
	public KaleoNotificationRecipient getKaleoNotificationRecipient(
			long kaleoNotificationRecipientId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kaleoNotificationRecipientLocalService.
			getKaleoNotificationRecipient(kaleoNotificationRecipientId);
	}

	/**
	 * Returns a range of all the kaleo notification recipients.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.workflow.kaleo.model.impl.KaleoNotificationRecipientModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of kaleo notification recipients
	 * @param end the upper bound of the range of kaleo notification recipients (not inclusive)
	 * @return the range of kaleo notification recipients
	 */
	@Override
	public java.util.List<KaleoNotificationRecipient>
		getKaleoNotificationRecipients(int start, int end) {

		return _kaleoNotificationRecipientLocalService.
			getKaleoNotificationRecipients(start, end);
	}

	@Override
	public java.util.List<KaleoNotificationRecipient>
		getKaleoNotificationRecipients(long kaleoNotificationId) {

		return _kaleoNotificationRecipientLocalService.
			getKaleoNotificationRecipients(kaleoNotificationId);
	}

	/**
	 * Returns the number of kaleo notification recipients.
	 *
	 * @return the number of kaleo notification recipients
	 */
	@Override
	public int getKaleoNotificationRecipientsCount() {
		return _kaleoNotificationRecipientLocalService.
			getKaleoNotificationRecipientsCount();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _kaleoNotificationRecipientLocalService.
			getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kaleoNotificationRecipientLocalService.getPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Updates the kaleo notification recipient in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect KaleoNotificationRecipientLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param kaleoNotificationRecipient the kaleo notification recipient
	 * @return the kaleo notification recipient that was updated
	 */
	@Override
	public KaleoNotificationRecipient updateKaleoNotificationRecipient(
		KaleoNotificationRecipient kaleoNotificationRecipient) {

		return _kaleoNotificationRecipientLocalService.
			updateKaleoNotificationRecipient(kaleoNotificationRecipient);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _kaleoNotificationRecipientLocalService.getBasePersistence();
	}

	@Override
	public CTPersistence<KaleoNotificationRecipient> getCTPersistence() {
		return _kaleoNotificationRecipientLocalService.getCTPersistence();
	}

	@Override
	public Class<KaleoNotificationRecipient> getModelClass() {
		return _kaleoNotificationRecipientLocalService.getModelClass();
	}

	@Override
	public <R, E extends Throwable> R updateWithUnsafeFunction(
			UnsafeFunction<CTPersistence<KaleoNotificationRecipient>, R, E>
				updateUnsafeFunction)
		throws E {

		return _kaleoNotificationRecipientLocalService.updateWithUnsafeFunction(
			updateUnsafeFunction);
	}

	@Override
	public KaleoNotificationRecipientLocalService getWrappedService() {
		return _kaleoNotificationRecipientLocalService;
	}

	@Override
	public void setWrappedService(
		KaleoNotificationRecipientLocalService
			kaleoNotificationRecipientLocalService) {

		_kaleoNotificationRecipientLocalService =
			kaleoNotificationRecipientLocalService;
	}

	private KaleoNotificationRecipientLocalService
		_kaleoNotificationRecipientLocalService;

}