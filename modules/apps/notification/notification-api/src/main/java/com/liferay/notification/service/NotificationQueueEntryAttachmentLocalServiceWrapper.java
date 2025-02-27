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

package com.liferay.notification.service;

import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * Provides a wrapper for {@link NotificationQueueEntryAttachmentLocalService}.
 *
 * @author Gabriel Albuquerque
 * @see NotificationQueueEntryAttachmentLocalService
 * @generated
 */
public class NotificationQueueEntryAttachmentLocalServiceWrapper
	implements NotificationQueueEntryAttachmentLocalService,
			   ServiceWrapper<NotificationQueueEntryAttachmentLocalService> {

	public NotificationQueueEntryAttachmentLocalServiceWrapper() {
		this(null);
	}

	public NotificationQueueEntryAttachmentLocalServiceWrapper(
		NotificationQueueEntryAttachmentLocalService
			notificationQueueEntryAttachmentLocalService) {

		_notificationQueueEntryAttachmentLocalService =
			notificationQueueEntryAttachmentLocalService;
	}

	@Override
	public com.liferay.notification.model.NotificationQueueEntryAttachment
			addNotificationQueueEntryAttachment(
				long companyId, long fileEntryId, long notificationQueueEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _notificationQueueEntryAttachmentLocalService.
			addNotificationQueueEntryAttachment(
				companyId, fileEntryId, notificationQueueEntryId);
	}

	/**
	 * Adds the notification queue entry attachment to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect NotificationQueueEntryAttachmentLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param notificationQueueEntryAttachment the notification queue entry attachment
	 * @return the notification queue entry attachment that was added
	 */
	@Override
	public com.liferay.notification.model.NotificationQueueEntryAttachment
		addNotificationQueueEntryAttachment(
			com.liferay.notification.model.NotificationQueueEntryAttachment
				notificationQueueEntryAttachment) {

		return _notificationQueueEntryAttachmentLocalService.
			addNotificationQueueEntryAttachment(
				notificationQueueEntryAttachment);
	}

	/**
	 * Creates a new notification queue entry attachment with the primary key. Does not add the notification queue entry attachment to the database.
	 *
	 * @param notificationQueueEntryAttachmentId the primary key for the new notification queue entry attachment
	 * @return the new notification queue entry attachment
	 */
	@Override
	public com.liferay.notification.model.NotificationQueueEntryAttachment
		createNotificationQueueEntryAttachment(
			long notificationQueueEntryAttachmentId) {

		return _notificationQueueEntryAttachmentLocalService.
			createNotificationQueueEntryAttachment(
				notificationQueueEntryAttachmentId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _notificationQueueEntryAttachmentLocalService.
			createPersistedModel(primaryKeyObj);
	}

	/**
	 * Deletes the notification queue entry attachment with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect NotificationQueueEntryAttachmentLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param notificationQueueEntryAttachmentId the primary key of the notification queue entry attachment
	 * @return the notification queue entry attachment that was removed
	 * @throws PortalException if a notification queue entry attachment with the primary key could not be found
	 */
	@Override
	public com.liferay.notification.model.NotificationQueueEntryAttachment
			deleteNotificationQueueEntryAttachment(
				long notificationQueueEntryAttachmentId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _notificationQueueEntryAttachmentLocalService.
			deleteNotificationQueueEntryAttachment(
				notificationQueueEntryAttachmentId);
	}

	/**
	 * Deletes the notification queue entry attachment from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect NotificationQueueEntryAttachmentLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param notificationQueueEntryAttachment the notification queue entry attachment
	 * @return the notification queue entry attachment that was removed
	 */
	@Override
	public com.liferay.notification.model.NotificationQueueEntryAttachment
		deleteNotificationQueueEntryAttachment(
			com.liferay.notification.model.NotificationQueueEntryAttachment
				notificationQueueEntryAttachment) {

		return _notificationQueueEntryAttachmentLocalService.
			deleteNotificationQueueEntryAttachment(
				notificationQueueEntryAttachment);
	}

	@Override
	public void deleteNotificationQueueEntryAttachments(
			long notificationQueueEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_notificationQueueEntryAttachmentLocalService.
			deleteNotificationQueueEntryAttachments(notificationQueueEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _notificationQueueEntryAttachmentLocalService.
			deletePersistedModel(persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _notificationQueueEntryAttachmentLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _notificationQueueEntryAttachmentLocalService.dslQueryCount(
			dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _notificationQueueEntryAttachmentLocalService.dynamicQuery();
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

		return _notificationQueueEntryAttachmentLocalService.dynamicQuery(
			dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.notification.model.impl.NotificationQueueEntryAttachmentModelImpl</code>.
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

		return _notificationQueueEntryAttachmentLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.notification.model.impl.NotificationQueueEntryAttachmentModelImpl</code>.
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

		return _notificationQueueEntryAttachmentLocalService.dynamicQuery(
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

		return _notificationQueueEntryAttachmentLocalService.dynamicQueryCount(
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

		return _notificationQueueEntryAttachmentLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.notification.model.NotificationQueueEntryAttachment
		fetchNotificationQueueEntryAttachment(
			long notificationQueueEntryAttachmentId) {

		return _notificationQueueEntryAttachmentLocalService.
			fetchNotificationQueueEntryAttachment(
				notificationQueueEntryAttachmentId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _notificationQueueEntryAttachmentLocalService.
			getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _notificationQueueEntryAttachmentLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the notification queue entry attachment with the primary key.
	 *
	 * @param notificationQueueEntryAttachmentId the primary key of the notification queue entry attachment
	 * @return the notification queue entry attachment
	 * @throws PortalException if a notification queue entry attachment with the primary key could not be found
	 */
	@Override
	public com.liferay.notification.model.NotificationQueueEntryAttachment
			getNotificationQueueEntryAttachment(
				long notificationQueueEntryAttachmentId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _notificationQueueEntryAttachmentLocalService.
			getNotificationQueueEntryAttachment(
				notificationQueueEntryAttachmentId);
	}

	/**
	 * Returns a range of all the notification queue entry attachments.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.notification.model.impl.NotificationQueueEntryAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of notification queue entry attachments
	 * @param end the upper bound of the range of notification queue entry attachments (not inclusive)
	 * @return the range of notification queue entry attachments
	 */
	@Override
	public java.util.List
		<com.liferay.notification.model.NotificationQueueEntryAttachment>
			getNotificationQueueEntryAttachments(int start, int end) {

		return _notificationQueueEntryAttachmentLocalService.
			getNotificationQueueEntryAttachments(start, end);
	}

	/**
	 * Returns the number of notification queue entry attachments.
	 *
	 * @return the number of notification queue entry attachments
	 */
	@Override
	public int getNotificationQueueEntryAttachmentsCount() {
		return _notificationQueueEntryAttachmentLocalService.
			getNotificationQueueEntryAttachmentsCount();
	}

	@Override
	public java.util.List
		<com.liferay.notification.model.NotificationQueueEntryAttachment>
			getNotificationQueueEntryNotificationQueueEntryAttachments(
				long notificationQueueEntryId) {

		return _notificationQueueEntryAttachmentLocalService.
			getNotificationQueueEntryNotificationQueueEntryAttachments(
				notificationQueueEntryId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _notificationQueueEntryAttachmentLocalService.
			getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _notificationQueueEntryAttachmentLocalService.getPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Updates the notification queue entry attachment in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect NotificationQueueEntryAttachmentLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param notificationQueueEntryAttachment the notification queue entry attachment
	 * @return the notification queue entry attachment that was updated
	 */
	@Override
	public com.liferay.notification.model.NotificationQueueEntryAttachment
		updateNotificationQueueEntryAttachment(
			com.liferay.notification.model.NotificationQueueEntryAttachment
				notificationQueueEntryAttachment) {

		return _notificationQueueEntryAttachmentLocalService.
			updateNotificationQueueEntryAttachment(
				notificationQueueEntryAttachment);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _notificationQueueEntryAttachmentLocalService.
			getBasePersistence();
	}

	@Override
	public NotificationQueueEntryAttachmentLocalService getWrappedService() {
		return _notificationQueueEntryAttachmentLocalService;
	}

	@Override
	public void setWrappedService(
		NotificationQueueEntryAttachmentLocalService
			notificationQueueEntryAttachmentLocalService) {

		_notificationQueueEntryAttachmentLocalService =
			notificationQueueEntryAttachmentLocalService;
	}

	private NotificationQueueEntryAttachmentLocalService
		_notificationQueueEntryAttachmentLocalService;

}