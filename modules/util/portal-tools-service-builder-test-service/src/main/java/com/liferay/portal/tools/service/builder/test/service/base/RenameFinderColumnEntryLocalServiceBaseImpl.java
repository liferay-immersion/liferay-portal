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

package com.liferay.portal.tools.service.builder.test.service.base;

import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DefaultActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.module.framework.service.IdentifiableOSGiService;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.BaseLocalServiceImpl;
import com.liferay.portal.kernel.service.PersistedModelLocalServiceRegistry;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;
import com.liferay.portal.tools.service.builder.test.model.RenameFinderColumnEntry;
import com.liferay.portal.tools.service.builder.test.service.RenameFinderColumnEntryLocalService;
import com.liferay.portal.tools.service.builder.test.service.RenameFinderColumnEntryLocalServiceUtil;
import com.liferay.portal.tools.service.builder.test.service.persistence.RenameFinderColumnEntryPersistence;

import java.io.Serializable;

import java.util.List;

import javax.sql.DataSource;

/**
 * Provides the base implementation for the rename finder column entry local service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.portal.tools.service.builder.test.service.impl.RenameFinderColumnEntryLocalServiceImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portal.tools.service.builder.test.service.impl.RenameFinderColumnEntryLocalServiceImpl
 * @generated
 */
public abstract class RenameFinderColumnEntryLocalServiceBaseImpl
	extends BaseLocalServiceImpl
	implements IdentifiableOSGiService, RenameFinderColumnEntryLocalService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Use <code>RenameFinderColumnEntryLocalService</code> via injection or a <code>org.osgi.util.tracker.ServiceTracker</code> or use <code>RenameFinderColumnEntryLocalServiceUtil</code>.
	 */

	/**
	 * Adds the rename finder column entry to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect RenameFinderColumnEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param renameFinderColumnEntry the rename finder column entry
	 * @return the rename finder column entry that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public RenameFinderColumnEntry addRenameFinderColumnEntry(
		RenameFinderColumnEntry renameFinderColumnEntry) {

		renameFinderColumnEntry.setNew(true);

		return renameFinderColumnEntryPersistence.update(
			renameFinderColumnEntry);
	}

	/**
	 * Creates a new rename finder column entry with the primary key. Does not add the rename finder column entry to the database.
	 *
	 * @param renameFinderColumnEntryId the primary key for the new rename finder column entry
	 * @return the new rename finder column entry
	 */
	@Override
	@Transactional(enabled = false)
	public RenameFinderColumnEntry createRenameFinderColumnEntry(
		long renameFinderColumnEntryId) {

		return renameFinderColumnEntryPersistence.create(
			renameFinderColumnEntryId);
	}

	/**
	 * Deletes the rename finder column entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect RenameFinderColumnEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param renameFinderColumnEntryId the primary key of the rename finder column entry
	 * @return the rename finder column entry that was removed
	 * @throws PortalException if a rename finder column entry with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public RenameFinderColumnEntry deleteRenameFinderColumnEntry(
			long renameFinderColumnEntryId)
		throws PortalException {

		return renameFinderColumnEntryPersistence.remove(
			renameFinderColumnEntryId);
	}

	/**
	 * Deletes the rename finder column entry from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect RenameFinderColumnEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param renameFinderColumnEntry the rename finder column entry
	 * @return the rename finder column entry that was removed
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public RenameFinderColumnEntry deleteRenameFinderColumnEntry(
		RenameFinderColumnEntry renameFinderColumnEntry) {

		return renameFinderColumnEntryPersistence.remove(
			renameFinderColumnEntry);
	}

	@Override
	public <T> T dslQuery(DSLQuery dslQuery) {
		return renameFinderColumnEntryPersistence.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(DSLQuery dslQuery) {
		Long count = dslQuery(dslQuery);

		return count.intValue();
	}

	@Override
	public DynamicQuery dynamicQuery() {
		Class<?> clazz = getClass();

		return DynamicQueryFactoryUtil.forClass(
			RenameFinderColumnEntry.class, clazz.getClassLoader());
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
		return renameFinderColumnEntryPersistence.findWithDynamicQuery(
			dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.RenameFinderColumnEntryModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return renameFinderColumnEntryPersistence.findWithDynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.RenameFinderColumnEntryModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<T> orderByComparator) {

		return renameFinderColumnEntryPersistence.findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(DynamicQuery dynamicQuery) {
		return renameFinderColumnEntryPersistence.countWithDynamicQuery(
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
		DynamicQuery dynamicQuery, Projection projection) {

		return renameFinderColumnEntryPersistence.countWithDynamicQuery(
			dynamicQuery, projection);
	}

	@Override
	public RenameFinderColumnEntry fetchRenameFinderColumnEntry(
		long renameFinderColumnEntryId) {

		return renameFinderColumnEntryPersistence.fetchByPrimaryKey(
			renameFinderColumnEntryId);
	}

	/**
	 * Returns the rename finder column entry with the primary key.
	 *
	 * @param renameFinderColumnEntryId the primary key of the rename finder column entry
	 * @return the rename finder column entry
	 * @throws PortalException if a rename finder column entry with the primary key could not be found
	 */
	@Override
	public RenameFinderColumnEntry getRenameFinderColumnEntry(
			long renameFinderColumnEntryId)
		throws PortalException {

		return renameFinderColumnEntryPersistence.findByPrimaryKey(
			renameFinderColumnEntryId);
	}

	@Override
	public ActionableDynamicQuery getActionableDynamicQuery() {
		ActionableDynamicQuery actionableDynamicQuery =
			new DefaultActionableDynamicQuery();

		actionableDynamicQuery.setBaseLocalService(
			renameFinderColumnEntryLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(RenameFinderColumnEntry.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName(
			"renameFinderColumnEntryId");

		return actionableDynamicQuery;
	}

	@Override
	public IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			new IndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setBaseLocalService(
			renameFinderColumnEntryLocalService);
		indexableActionableDynamicQuery.setClassLoader(getClassLoader());
		indexableActionableDynamicQuery.setModelClass(
			RenameFinderColumnEntry.class);

		indexableActionableDynamicQuery.setPrimaryKeyPropertyName(
			"renameFinderColumnEntryId");

		return indexableActionableDynamicQuery;
	}

	protected void initActionableDynamicQuery(
		ActionableDynamicQuery actionableDynamicQuery) {

		actionableDynamicQuery.setBaseLocalService(
			renameFinderColumnEntryLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(RenameFinderColumnEntry.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName(
			"renameFinderColumnEntryId");
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel createPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return renameFinderColumnEntryPersistence.create(
			((Long)primaryKeyObj).longValue());
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException {

		if (_log.isWarnEnabled()) {
			_log.warn(
				"Implement RenameFinderColumnEntryLocalServiceImpl#deleteRenameFinderColumnEntry(RenameFinderColumnEntry) to avoid orphaned data");
		}

		return renameFinderColumnEntryLocalService.
			deleteRenameFinderColumnEntry(
				(RenameFinderColumnEntry)persistedModel);
	}

	@Override
	public BasePersistence<RenameFinderColumnEntry> getBasePersistence() {
		return renameFinderColumnEntryPersistence;
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return renameFinderColumnEntryPersistence.findByPrimaryKey(
			primaryKeyObj);
	}

	/**
	 * Returns a range of all the rename finder column entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.RenameFinderColumnEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of rename finder column entries
	 * @param end the upper bound of the range of rename finder column entries (not inclusive)
	 * @return the range of rename finder column entries
	 */
	@Override
	public List<RenameFinderColumnEntry> getRenameFinderColumnEntries(
		int start, int end) {

		return renameFinderColumnEntryPersistence.findAll(start, end);
	}

	/**
	 * Returns the number of rename finder column entries.
	 *
	 * @return the number of rename finder column entries
	 */
	@Override
	public int getRenameFinderColumnEntriesCount() {
		return renameFinderColumnEntryPersistence.countAll();
	}

	/**
	 * Updates the rename finder column entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect RenameFinderColumnEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param renameFinderColumnEntry the rename finder column entry
	 * @return the rename finder column entry that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public RenameFinderColumnEntry updateRenameFinderColumnEntry(
		RenameFinderColumnEntry renameFinderColumnEntry) {

		return renameFinderColumnEntryPersistence.update(
			renameFinderColumnEntry);
	}

	/**
	 * Returns the rename finder column entry local service.
	 *
	 * @return the rename finder column entry local service
	 */
	public RenameFinderColumnEntryLocalService
		getRenameFinderColumnEntryLocalService() {

		return renameFinderColumnEntryLocalService;
	}

	/**
	 * Sets the rename finder column entry local service.
	 *
	 * @param renameFinderColumnEntryLocalService the rename finder column entry local service
	 */
	public void setRenameFinderColumnEntryLocalService(
		RenameFinderColumnEntryLocalService
			renameFinderColumnEntryLocalService) {

		this.renameFinderColumnEntryLocalService =
			renameFinderColumnEntryLocalService;
	}

	/**
	 * Returns the rename finder column entry persistence.
	 *
	 * @return the rename finder column entry persistence
	 */
	public RenameFinderColumnEntryPersistence
		getRenameFinderColumnEntryPersistence() {

		return renameFinderColumnEntryPersistence;
	}

	/**
	 * Sets the rename finder column entry persistence.
	 *
	 * @param renameFinderColumnEntryPersistence the rename finder column entry persistence
	 */
	public void setRenameFinderColumnEntryPersistence(
		RenameFinderColumnEntryPersistence renameFinderColumnEntryPersistence) {

		this.renameFinderColumnEntryPersistence =
			renameFinderColumnEntryPersistence;
	}

	/**
	 * Returns the counter local service.
	 *
	 * @return the counter local service
	 */
	public com.liferay.counter.kernel.service.CounterLocalService
		getCounterLocalService() {

		return counterLocalService;
	}

	/**
	 * Sets the counter local service.
	 *
	 * @param counterLocalService the counter local service
	 */
	public void setCounterLocalService(
		com.liferay.counter.kernel.service.CounterLocalService
			counterLocalService) {

		this.counterLocalService = counterLocalService;
	}

	public void afterPropertiesSet() {
		persistedModelLocalServiceRegistry.register(
			"com.liferay.portal.tools.service.builder.test.model.RenameFinderColumnEntry",
			renameFinderColumnEntryLocalService);

		RenameFinderColumnEntryLocalServiceUtil.setService(
			renameFinderColumnEntryLocalService);
	}

	public void destroy() {
		persistedModelLocalServiceRegistry.unregister(
			"com.liferay.portal.tools.service.builder.test.model.RenameFinderColumnEntry");

		RenameFinderColumnEntryLocalServiceUtil.setService(null);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return RenameFinderColumnEntryLocalService.class.getName();
	}

	protected Class<?> getModelClass() {
		return RenameFinderColumnEntry.class;
	}

	protected String getModelClassName() {
		return RenameFinderColumnEntry.class.getName();
	}

	/**
	 * Performs a SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) {
		try {
			DataSource dataSource =
				renameFinderColumnEntryPersistence.getDataSource();

			DB db = DBManagerUtil.getDB();

			sql = db.buildSQL(sql);
			sql = PortalUtil.transformSQL(sql);

			SqlUpdate sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(
				dataSource, sql);

			sqlUpdate.update();
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
	}

	@BeanReference(type = RenameFinderColumnEntryLocalService.class)
	protected RenameFinderColumnEntryLocalService
		renameFinderColumnEntryLocalService;

	@BeanReference(type = RenameFinderColumnEntryPersistence.class)
	protected RenameFinderColumnEntryPersistence
		renameFinderColumnEntryPersistence;

	@ServiceReference(
		type = com.liferay.counter.kernel.service.CounterLocalService.class
	)
	protected com.liferay.counter.kernel.service.CounterLocalService
		counterLocalService;

	private static final Log _log = LogFactoryUtil.getLog(
		RenameFinderColumnEntryLocalServiceBaseImpl.class);

	@ServiceReference(type = PersistedModelLocalServiceRegistry.class)
	protected PersistedModelLocalServiceRegistry
		persistedModelLocalServiceRegistry;

}