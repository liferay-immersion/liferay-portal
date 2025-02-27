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

package com.liferay.portal.workflow.kaleo.runtime.internal.petra.executor;

import com.liferay.petra.concurrent.NoticeableExecutorService;
import com.liferay.petra.concurrent.NoticeableFuture;
import com.liferay.petra.concurrent.ThreadPoolHandlerAdapter;
import com.liferay.petra.executor.PortalExecutorConfig;
import com.liferay.petra.executor.PortalExecutorManager;
import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactory;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.NamedThreadFactory;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.PortalRunMode;
import com.liferay.portal.workflow.kaleo.runtime.ExecutionContext;
import com.liferay.portal.workflow.kaleo.runtime.graph.GraphWalker;
import com.liferay.portal.workflow.kaleo.runtime.graph.PathElement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rafael Praxedes
 */
@Component(service = GraphWalkerPortalExecutor.class)
public class GraphWalkerPortalExecutor {

	public void execute(PathElement pathElement, boolean waitForCompletion) {
		if (PortalRunMode.isTestMode()) {
			_walk(pathElement);

			return;
		}

		ExecutionContext executionContext = pathElement.getExecutionContext();

		ServiceContext serviceContext = executionContext.getServiceContext();

		long companyId = serviceContext.getCompanyId();

		long ctCollectionId = CTCollectionThreadLocal.getCTCollectionId();

		if (waitForCompletion) {
			NoticeableFuture<?> noticeableFuture =
				_noticeableExecutorService.submit(
					() -> {
						try (SafeCloseable safeCloseable =
								CompanyThreadLocal.setWithSafeCloseable(
									companyId, ctCollectionId)) {

							_walk(pathElement);
						}
					});

			try {
				noticeableFuture.get();
			}
			catch (ExecutionException executionException) {
				_log.error(executionException);
			}
			catch (InterruptedException interruptedException) {
				_log.error(interruptedException);
			}
		}
		else {
			_noticeableExecutorService.submit(
				() -> {
					try (SafeCloseable safeCloseable =
							CompanyThreadLocal.setWithSafeCloseable(
								companyId, ctCollectionId)) {

						_walk(pathElement);
					}
				});
		}
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_registerPortalExecutorConfig(bundleContext);

		_noticeableExecutorService = _portalExecutorManager.getPortalExecutor(
			GraphWalkerPortalExecutor.class.getName());
	}

	@Deactivate
	protected void deactivate() {
		_noticeableExecutorService.shutdown();

		_serviceRegistration.unregister();
	}

	private void _registerPortalExecutorConfig(BundleContext bundleContext) {
		PortalExecutorConfig portalExecutorConfig = new PortalExecutorConfig(
			GraphWalkerPortalExecutor.class.getName(), 1, 1, 60,
			TimeUnit.SECONDS, Integer.MAX_VALUE,
			new NamedThreadFactory(
				GraphWalkerPortalExecutor.class.getName(), Thread.NORM_PRIORITY,
				PortalClassLoaderUtil.getClassLoader()),
			new ThreadPoolExecutor.AbortPolicy(),
			new ThreadPoolHandlerAdapter() {

				@Override
				public void afterExecute(
					Runnable runnable, Throwable throwable) {

					CentralizedThreadLocal.clearShortLivedThreadLocals();
				}

			});

		_serviceRegistration = bundleContext.registerService(
			PortalExecutorConfig.class, portalExecutorConfig, null);
	}

	private void _walk(PathElement pathElement) {
		String name = PrincipalThreadLocal.getName();
		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		try {
			ExecutionContext executionContext =
				pathElement.getExecutionContext();

			ServiceContext serviceContext =
				executionContext.getServiceContext();

			if (PrincipalThreadLocal.getUserId() == 0) {
				PrincipalThreadLocal.setName(serviceContext.getUserId());
			}

			if (permissionChecker == null) {
				PermissionThreadLocal.setPermissionChecker(
					_defaultPermissionCheckerFactory.create(
						_userLocalService.getUser(serviceContext.getUserId())));
			}

			Queue<List<PathElement>> queue = new LinkedList<>();

			queue.add(Collections.singletonList(pathElement));

			List<PathElement> pathElements = null;

			while ((pathElements = queue.poll()) != null) {
				for (PathElement curPathElement : pathElements) {
					List<PathElement> remainingPathElements = new ArrayList<>();

					_graphWalker.follow(
						curPathElement.getStartNode(),
						curPathElement.getTargetNode(), remainingPathElements,
						curPathElement.getExecutionContext());

					if (!remainingPathElements.isEmpty()) {
						queue.add(remainingPathElements);
					}
				}
			}
		}
		catch (Throwable throwable) {
			_log.error(throwable, throwable);
		}
		finally {
			PrincipalThreadLocal.setName(name);

			PermissionThreadLocal.setPermissionChecker(permissionChecker);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		GraphWalkerPortalExecutor.class);

	@Reference
	private PermissionCheckerFactory _defaultPermissionCheckerFactory;

	@Reference
	private GraphWalker _graphWalker;

	private NoticeableExecutorService _noticeableExecutorService;

	@Reference
	private PortalExecutorManager _portalExecutorManager;

	private ServiceRegistration<PortalExecutorConfig> _serviceRegistration;

	@Reference
	private UserLocalService _userLocalService;

}