/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.osb.faro.service.base;

import com.liferay.osb.faro.model.FaroPreferences;
import com.liferay.osb.faro.service.FaroPreferencesLocalService;
import com.liferay.osb.faro.service.FaroPreferencesLocalServiceUtil;
import com.liferay.osb.faro.service.persistence.FaroPreferencesPersistence;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.aop.AopService;
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
import com.liferay.portal.kernel.service.PersistedModelLocalService;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PortalUtil;

import java.io.Serializable;

import java.util.List;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides the base implementation for the faro preferences local service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.osb.faro.service.impl.FaroPreferencesLocalServiceImpl}.
 * </p>
 *
 * @author Matthew Kong
 * @see com.liferay.osb.faro.service.impl.FaroPreferencesLocalServiceImpl
 * @generated
 */
public abstract class FaroPreferencesLocalServiceBaseImpl
	extends BaseLocalServiceImpl
	implements AopService, FaroPreferencesLocalService,
			   IdentifiableOSGiService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Use <code>FaroPreferencesLocalService</code> via injection or a <code>org.osgi.util.tracker.ServiceTracker</code> or use <code>FaroPreferencesLocalServiceUtil</code>.
	 */

	/**
	 * Adds the faro preferences to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect FaroPreferencesLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param faroPreferences the faro preferences
	 * @return the faro preferences that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public FaroPreferences addFaroPreferences(FaroPreferences faroPreferences) {
		faroPreferences.setNew(true);

		return faroPreferencesPersistence.update(faroPreferences);
	}

	/**
	 * Creates a new faro preferences with the primary key. Does not add the faro preferences to the database.
	 *
	 * @param faroPreferencesId the primary key for the new faro preferences
	 * @return the new faro preferences
	 */
	@Override
	@Transactional(enabled = false)
	public FaroPreferences createFaroPreferences(long faroPreferencesId) {
		return faroPreferencesPersistence.create(faroPreferencesId);
	}

	/**
	 * Deletes the faro preferences with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect FaroPreferencesLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param faroPreferencesId the primary key of the faro preferences
	 * @return the faro preferences that was removed
	 * @throws PortalException if a faro preferences with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public FaroPreferences deleteFaroPreferences(long faroPreferencesId)
		throws PortalException {

		return faroPreferencesPersistence.remove(faroPreferencesId);
	}

	/**
	 * Deletes the faro preferences from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect FaroPreferencesLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param faroPreferences the faro preferences
	 * @return the faro preferences that was removed
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public FaroPreferences deleteFaroPreferences(
		FaroPreferences faroPreferences) {

		return faroPreferencesPersistence.remove(faroPreferences);
	}

	@Override
	public <T> T dslQuery(DSLQuery dslQuery) {
		return faroPreferencesPersistence.dslQuery(dslQuery);
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
			FaroPreferences.class, clazz.getClassLoader());
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
		return faroPreferencesPersistence.findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.osb.faro.model.impl.FaroPreferencesModelImpl</code>.
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

		return faroPreferencesPersistence.findWithDynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.osb.faro.model.impl.FaroPreferencesModelImpl</code>.
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

		return faroPreferencesPersistence.findWithDynamicQuery(
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
		return faroPreferencesPersistence.countWithDynamicQuery(dynamicQuery);
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

		return faroPreferencesPersistence.countWithDynamicQuery(
			dynamicQuery, projection);
	}

	@Override
	public FaroPreferences fetchFaroPreferences(long faroPreferencesId) {
		return faroPreferencesPersistence.fetchByPrimaryKey(faroPreferencesId);
	}

	/**
	 * Returns the faro preferences with the primary key.
	 *
	 * @param faroPreferencesId the primary key of the faro preferences
	 * @return the faro preferences
	 * @throws PortalException if a faro preferences with the primary key could not be found
	 */
	@Override
	public FaroPreferences getFaroPreferences(long faroPreferencesId)
		throws PortalException {

		return faroPreferencesPersistence.findByPrimaryKey(faroPreferencesId);
	}

	@Override
	public ActionableDynamicQuery getActionableDynamicQuery() {
		ActionableDynamicQuery actionableDynamicQuery =
			new DefaultActionableDynamicQuery();

		actionableDynamicQuery.setBaseLocalService(faroPreferencesLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(FaroPreferences.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName("faroPreferencesId");

		return actionableDynamicQuery;
	}

	@Override
	public IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			new IndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setBaseLocalService(
			faroPreferencesLocalService);
		indexableActionableDynamicQuery.setClassLoader(getClassLoader());
		indexableActionableDynamicQuery.setModelClass(FaroPreferences.class);

		indexableActionableDynamicQuery.setPrimaryKeyPropertyName(
			"faroPreferencesId");

		return indexableActionableDynamicQuery;
	}

	protected void initActionableDynamicQuery(
		ActionableDynamicQuery actionableDynamicQuery) {

		actionableDynamicQuery.setBaseLocalService(faroPreferencesLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(FaroPreferences.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName("faroPreferencesId");
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel createPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return faroPreferencesPersistence.create(
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
				"Implement FaroPreferencesLocalServiceImpl#deleteFaroPreferences(FaroPreferences) to avoid orphaned data");
		}

		return faroPreferencesLocalService.deleteFaroPreferences(
			(FaroPreferences)persistedModel);
	}

	@Override
	public BasePersistence<FaroPreferences> getBasePersistence() {
		return faroPreferencesPersistence;
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return faroPreferencesPersistence.findByPrimaryKey(primaryKeyObj);
	}

	/**
	 * Returns a range of all the faro preferenceses.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.osb.faro.model.impl.FaroPreferencesModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of faro preferenceses
	 * @param end the upper bound of the range of faro preferenceses (not inclusive)
	 * @return the range of faro preferenceses
	 */
	@Override
	public List<FaroPreferences> getFaroPreferenceses(int start, int end) {
		return faroPreferencesPersistence.findAll(start, end);
	}

	/**
	 * Returns the number of faro preferenceses.
	 *
	 * @return the number of faro preferenceses
	 */
	@Override
	public int getFaroPreferencesesCount() {
		return faroPreferencesPersistence.countAll();
	}

	/**
	 * Updates the faro preferences in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect FaroPreferencesLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param faroPreferences the faro preferences
	 * @return the faro preferences that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public FaroPreferences updateFaroPreferences(
		FaroPreferences faroPreferences) {

		return faroPreferencesPersistence.update(faroPreferences);
	}

	@Deactivate
	protected void deactivate() {
		FaroPreferencesLocalServiceUtil.setService(null);
	}

	@Override
	public Class<?>[] getAopInterfaces() {
		return new Class<?>[] {
			FaroPreferencesLocalService.class, IdentifiableOSGiService.class,
			PersistedModelLocalService.class
		};
	}

	@Override
	public void setAopProxy(Object aopProxy) {
		faroPreferencesLocalService = (FaroPreferencesLocalService)aopProxy;

		FaroPreferencesLocalServiceUtil.setService(faroPreferencesLocalService);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return FaroPreferencesLocalService.class.getName();
	}

	protected Class<?> getModelClass() {
		return FaroPreferences.class;
	}

	protected String getModelClassName() {
		return FaroPreferences.class.getName();
	}

	/**
	 * Performs a SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) {
		try {
			DataSource dataSource = faroPreferencesPersistence.getDataSource();

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

	protected FaroPreferencesLocalService faroPreferencesLocalService;

	@Reference
	protected FaroPreferencesPersistence faroPreferencesPersistence;

	@Reference
	protected com.liferay.counter.kernel.service.CounterLocalService
		counterLocalService;

	private static final Log _log = LogFactoryUtil.getLog(
		FaroPreferencesLocalServiceBaseImpl.class);

}