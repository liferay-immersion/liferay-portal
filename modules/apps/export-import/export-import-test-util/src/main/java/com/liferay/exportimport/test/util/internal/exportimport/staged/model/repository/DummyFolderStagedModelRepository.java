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

package com.liferay.exportimport.test.util.internal.exportimport.staged.model.repository;

import com.liferay.exportimport.kernel.lar.ManifestSummary;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerRegistryUtil;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.exportimport.test.util.model.DummyFolder;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.Conjunction;
import com.liferay.portal.kernel.dao.orm.Criterion;
import com.liferay.portal.kernel.dao.orm.Disjunction;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.NoSuchModelException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.service.BaseLocalServiceImpl;
import com.liferay.portal.kernel.service.SystemEventLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Akos Thurzo
 */
@Component(
	property = "model.class.name=com.liferay.exportimport.test.util.model.DummyFolder",
	service = StagedModelRepository.class
)
public class DummyFolderStagedModelRepository
	implements StagedModelRepository<DummyFolder> {

	@Override
	public DummyFolder addStagedModel(
			PortletDataContext portletDataContext, DummyFolder dummyFolder1)
		throws PortalException {

		if ((portletDataContext != null) &&
			(portletDataContext.getUserIdStrategy() != null)) {

			dummyFolder1.setUserId(
				portletDataContext.getUserId(dummyFolder1.getUserUuid()));
		}

		DummyFolder dummyFolder2 = new DummyFolder();

		dummyFolder1.setId(dummyFolder2.getId());

		_dummyFolders.add(dummyFolder1);

		return dummyFolder1;
	}

	@Override
	public void deleteStagedModel(DummyFolder dummyFolder)
		throws PortalException {

		if (_dummyFolders.remove(dummyFolder)) {
			systemEventLocalService.addSystemEvent(
				0, dummyFolder.getGroupId(), dummyFolder.getModelClassName(),
				dummyFolder.getPrimaryKey(), dummyFolder.getUuid(),
				StringPool.BLANK, SystemEventConstants.TYPE_DELETE,
				StringPool.BLANK);
		}
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		_dummyFolders.removeIf(
			dummyFolder ->
				Objects.equals(uuid, dummyFolder.getUuid()) &&
				(groupId == dummyFolder.getGroupId()));
	}

	@Override
	public void deleteStagedModels(PortletDataContext portletDataContext)
		throws PortalException {

		_dummyFolders.clear();
	}

	@Override
	public DummyFolder fetchMissingReference(String uuid, long groupId) {
		return fetchStagedModelByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public DummyFolder fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		for (DummyFolder dummyFolder : _dummyFolders) {
			if (Objects.equals(uuid, dummyFolder.getUuid()) &&
				(groupId == dummyFolder.getGroupId())) {

				return dummyFolder;
			}
		}

		return null;
	}

	@Override
	public List<DummyFolder> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return ListUtil.filter(
			_dummyFolders,
			dummyFolder ->
				Objects.equals(uuid, dummyFolder.getUuid()) &&
				(companyId == dummyFolder.getCompanyId()));
	}

	public List<DummyFolder> getDummyFolders(long groupId) {
		return ListUtil.filter(
			_dummyFolders, dummyFolder -> groupId == dummyFolder.getGroupId());
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		ExportActionableDynamicQuery exportActionableDynamicQuery =
			new ExportActionableDynamicQuery() {

				@Override
				public long performCount() throws PortalException {
					ManifestSummary manifestSummary =
						portletDataContext.getManifestSummary();

					StagedModelType stagedModelType = getStagedModelType();

					long modelAdditionCount = _dummyFolders.size();

					manifestSummary.addModelAdditionCount(
						stagedModelType, modelAdditionCount);

					manifestSummary.addModelDeletionCount(stagedModelType, 0);

					return modelAdditionCount;
				}

				@Override
				protected Projection getCountProjection() {
					return ProjectionFactoryUtil.countDistinct(
						"resourcePrimKey");
				}

			};

		exportActionableDynamicQuery.setBaseLocalService(
			new DummyFolderBaseLocalServiceImpl());

		Class<?> clazz = getClass();

		exportActionableDynamicQuery.setClassLoader(clazz.getClassLoader());

		exportActionableDynamicQuery.setModelClass(DummyFolder.class);
		exportActionableDynamicQuery.setPrimaryKeyPropertyName("id");
		exportActionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> {
				Criterion modifiedDateCriterion =
					portletDataContext.getDateRangeCriteria("modifiedDate");

				if (modifiedDateCriterion != null) {
					Conjunction conjunction =
						RestrictionsFactoryUtil.conjunction();

					conjunction.add(modifiedDateCriterion);

					Disjunction disjunction =
						RestrictionsFactoryUtil.disjunction();

					disjunction.add(
						RestrictionsFactoryUtil.gtProperty(
							"modifiedDate", "lastPublishDate"));

					Property lastPublishDateProperty =
						PropertyFactoryUtil.forName("lastPublishDate");

					disjunction.add(lastPublishDateProperty.isNull());

					conjunction.add(disjunction);

					modifiedDateCriterion = conjunction;
				}

				Criterion statusDateCriterion =
					portletDataContext.getDateRangeCriteria("statusDate");

				if ((modifiedDateCriterion != null) &&
					(statusDateCriterion != null)) {

					Disjunction disjunction =
						RestrictionsFactoryUtil.disjunction();

					disjunction.add(modifiedDateCriterion);
					disjunction.add(statusDateCriterion);

					dynamicQuery.add(disjunction);
				}

				StagedModelType stagedModelType =
					exportActionableDynamicQuery.getStagedModelType();

				long referrerClassNameId =
					stagedModelType.getReferrerClassNameId();

				Property classNameIdProperty = PropertyFactoryUtil.forName(
					"classNameId");

				if ((referrerClassNameId !=
						StagedModelType.REFERRER_CLASS_NAME_ID_ALL) &&
					(referrerClassNameId !=
						StagedModelType.REFERRER_CLASS_NAME_ID_ANY)) {

					dynamicQuery.add(
						classNameIdProperty.eq(
							stagedModelType.getReferrerClassNameId()));
				}
				else if (referrerClassNameId ==
							StagedModelType.REFERRER_CLASS_NAME_ID_ANY) {

					dynamicQuery.add(classNameIdProperty.isNotNull());
				}

				Property workflowStatusProperty = PropertyFactoryUtil.forName(
					"status");

				if (portletDataContext.isInitialPublication()) {
					dynamicQuery.add(
						workflowStatusProperty.ne(
							WorkflowConstants.STATUS_IN_TRASH));
				}
				else {
					StagedModelDataHandler<?> stagedModelDataHandler =
						StagedModelDataHandlerRegistryUtil.
							getStagedModelDataHandler(
								DummyFolder.class.getName());

					dynamicQuery.add(
						workflowStatusProperty.in(
							stagedModelDataHandler.getExportableStatuses()));
				}
			});
		exportActionableDynamicQuery.setCompanyId(
			portletDataContext.getCompanyId());
		exportActionableDynamicQuery.setGroupId(
			portletDataContext.getScopeGroupId());
		exportActionableDynamicQuery.setPerformActionMethod(
			(DummyFolder dummyFolder) ->
				StagedModelDataHandlerUtil.exportStagedModel(
					portletDataContext, dummyFolder));
		exportActionableDynamicQuery.setStagedModelType(
			new StagedModelType(
				portal.getClassNameId(DummyFolder.class.getName()),
				StagedModelType.REFERRER_CLASS_NAME_ID_ALL));

		return exportActionableDynamicQuery;
	}

	public DummyFolder getFolder(long folderId) {
		for (DummyFolder dummyFolder : _dummyFolders) {
			if (folderId == dummyFolder.getId()) {
				return dummyFolder;
			}
		}

		throw new RuntimeException(new NoSuchModelException());
	}

	@Override
	public void restoreStagedModel(
			PortletDataContext portletDataContext, DummyFolder dummyFolder)
		throws PortletDataException {
	}

	@Override
	public DummyFolder saveStagedModel(DummyFolder dummyFolder)
		throws PortalException {

		deleteStagedModel(dummyFolder);

		addStagedModel(null, dummyFolder);

		return dummyFolder;
	}

	@Override
	public DummyFolder updateStagedModel(
			PortletDataContext portletDataContext, DummyFolder dummyFolder)
		throws PortalException {

		DummyFolder existingDummyFolder = _fetchDummyFolder(dummyFolder);

		existingDummyFolder.setUserId(
			portletDataContext.getUserId(dummyFolder.getUserUuid()));

		return dummyFolder;
	}

	public class DummyFolderBaseLocalServiceImpl extends BaseLocalServiceImpl {

		public List<DummyFolder> dynamicQuery(DynamicQuery dynamicQuery) {
			try {
				Object detachedCriteria = ReflectionTestUtil.getFieldValue(
					dynamicQuery, "_detachedCriteria");

				Object criteriaImpl = ReflectionTestUtil.invoke(
					detachedCriteria, "getCriteriaImpl", new Class<?>[0]);

				Iterator<?> iterator = ReflectionTestUtil.invoke(
					criteriaImpl, "iterateExpressionEntries", new Class<?>[0]);

				if (!iterator.hasNext()) {
					return _dummyFolders;
				}

				Predicate<DummyFolder> predicate = getPredicate(
					String.valueOf(iterator.next()));

				while (iterator.hasNext()) {
					predicate = predicate.and(
						getPredicate(String.valueOf(iterator.next())));
				}

				return ListUtil.filter(_dummyFolders, predicate);
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		}

		public long dynamicQueryCount(
			DynamicQuery dynamicQuery, Projection projection) {

			return _dummyFolders.size();
		}

		public Predicate<DummyFolder> getPredicate(String expression) {
			if (expression.startsWith("groupId=")) {
				return dummyFolder ->
					dummyFolder.getGroupId() == Long.valueOf(
						expression.substring("groupId=".length()));
			}

			if (expression.contains("id>-1")) {
				return dummyFolder -> dummyFolder.getId() > -1;
			}

			if (expression.startsWith("companyId=")) {
				return dummyFolder ->
					dummyFolder.getCompanyId() == Long.valueOf(
						expression.substring("companyId=".length()));
			}

			return dummyFolder -> true;
		}

		@Override
		protected ClassLoader getClassLoader() {
			return super.getClassLoader();
		}

		@Override
		protected Map<Locale, String> getLocalizationMap(String value) {
			return super.getLocalizationMap(value);
		}

	}

	@Reference
	protected Portal portal;

	@Reference
	protected SystemEventLocalService systemEventLocalService;

	private DummyFolder _fetchDummyFolder(DummyFolder dummyFolder)
		throws NoSuchModelException {

		int i = _dummyFolders.indexOf(dummyFolder);

		if (i < 0) {
			throw new NoSuchModelException();
		}

		return _dummyFolders.get(i);
	}

	private final List<DummyFolder> _dummyFolders = new ArrayList<>();

}