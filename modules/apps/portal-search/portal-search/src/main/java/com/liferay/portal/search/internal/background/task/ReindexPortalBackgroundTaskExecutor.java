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

package com.liferay.portal.search.internal.background.task;

import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
import com.liferay.petra.executor.PortalExecutorManager;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskExecutor;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.background.task.ReindexBackgroundTaskConstants;
import com.liferay.portal.kernel.search.background.task.ReindexStatusMessageSenderUtil;
import com.liferay.portal.search.internal.SearchEngineInitializer;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrew Betts
 */
@Component(
	immediate = true,
	property = "background.task.executor.class.name=com.liferay.portal.search.internal.background.task.ReindexPortalBackgroundTaskExecutor",
	service = BackgroundTaskExecutor.class
)
public class ReindexPortalBackgroundTaskExecutor
	extends BaseReindexBackgroundTaskExecutor {

	@Override
	public BackgroundTaskExecutor clone() {
		return this;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_indexers = ServiceTrackerListFactory.open(
			bundleContext, (Class<Indexer<?>>)(Class<?>)Indexer.class,
			"(!(system.index=true))");

		_systemIndexers = ServiceTrackerListFactory.open(
			bundleContext, (Class<Indexer<?>>)(Class<?>)Indexer.class,
			"(system.index=true)");
	}

	@Deactivate
	protected void deactivate() {
		_indexers.close();
		_systemIndexers.close();
	}

	@Override
	protected void reindex(String className, long[] companyIds)
		throws Exception {

		for (long companyId : companyIds) {
			ReindexStatusMessageSenderUtil.sendStatusMessage(
				ReindexBackgroundTaskConstants.PORTAL_START, companyId,
				companyIds);

			if (_log.isInfoEnabled()) {
				_log.info("Start reindexing company " + companyId);
			}

			try {
				SearchEngineInitializer searchEngineInitializer =
					new SearchEngineInitializer(
						_bundleContext, companyId, _indexers,
						_portalExecutorManager, _systemIndexers);

				searchEngineInitializer.reindex();
			}
			catch (Exception exception) {
				_log.error(exception);
			}
			finally {
				ReindexStatusMessageSenderUtil.sendStatusMessage(
					ReindexBackgroundTaskConstants.PORTAL_END, companyId,
					companyIds);

				if (_log.isInfoEnabled()) {
					_log.info("Finished reindexing company " + companyId);
				}
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ReindexPortalBackgroundTaskExecutor.class);

	private BundleContext _bundleContext;
	private ServiceTrackerList<Indexer<?>> _indexers;

	@Reference
	private PortalExecutorManager _portalExecutorManager;

	private ServiceTrackerList<Indexer<?>> _systemIndexers;

}