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

package com.liferay.knowledge.base.service;

import com.liferay.knowledge.base.model.KBComment;
import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link KBCommentService}.
 *
 * @author Brian Wing Shun Chan
 * @see KBCommentService
 * @generated
 */
public class KBCommentServiceWrapper
	implements KBCommentService, ServiceWrapper<KBCommentService> {

	public KBCommentServiceWrapper() {
		this(null);
	}

	public KBCommentServiceWrapper(KBCommentService kbCommentService) {
		_kbCommentService = kbCommentService;
	}

	@Override
	public KBComment deleteKBComment(KBComment kbComment)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kbCommentService.deleteKBComment(kbComment);
	}

	@Override
	public KBComment deleteKBComment(long kbCommentId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kbCommentService.deleteKBComment(kbCommentId);
	}

	@Override
	public KBComment getKBComment(long kbCommentId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kbCommentService.getKBComment(kbCommentId);
	}

	@Override
	public java.util.List<KBComment> getKBComments(
			long groupId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kbCommentService.getKBComments(groupId, status, start, end);
	}

	@Override
	public java.util.List<KBComment> getKBComments(
			long groupId, int status, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator<KBComment>
				orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kbCommentService.getKBComments(
			groupId, status, start, end, orderByComparator);
	}

	@Override
	public java.util.List<KBComment> getKBComments(
			long groupId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator<KBComment>
				orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kbCommentService.getKBComments(
			groupId, start, end, orderByComparator);
	}

	@Override
	public java.util.List<KBComment> getKBComments(
			long groupId, String className, long classPK, int status, int start,
			int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kbCommentService.getKBComments(
			groupId, className, classPK, status, start, end);
	}

	@Override
	public java.util.List<KBComment> getKBComments(
			long groupId, String className, long classPK, int status, int start,
			int end,
			com.liferay.portal.kernel.util.OrderByComparator<KBComment>
				orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kbCommentService.getKBComments(
			groupId, className, classPK, status, start, end, orderByComparator);
	}

	@Override
	public java.util.List<KBComment> getKBComments(
			long groupId, String className, long classPK, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator<KBComment>
				orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kbCommentService.getKBComments(
			groupId, className, classPK, start, end, orderByComparator);
	}

	@Override
	public int getKBCommentsCount(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kbCommentService.getKBCommentsCount(groupId);
	}

	@Override
	public int getKBCommentsCount(long groupId, int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kbCommentService.getKBCommentsCount(groupId, status);
	}

	@Override
	public int getKBCommentsCount(long groupId, String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kbCommentService.getKBCommentsCount(
			groupId, className, classPK);
	}

	@Override
	public int getKBCommentsCount(
			long groupId, String className, long classPK, int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kbCommentService.getKBCommentsCount(
			groupId, className, classPK, status);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _kbCommentService.getOSGiServiceIdentifier();
	}

	@Override
	public KBComment updateKBComment(
			long kbCommentId, long classNameId, long classPK, String content,
			int status,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kbCommentService.updateKBComment(
			kbCommentId, classNameId, classPK, content, status, serviceContext);
	}

	@Override
	public KBComment updateKBComment(
			long kbCommentId, long classNameId, long classPK, String content,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kbCommentService.updateKBComment(
			kbCommentId, classNameId, classPK, content, serviceContext);
	}

	@Override
	public KBComment updateStatus(
			long kbCommentId, int status,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kbCommentService.updateStatus(
			kbCommentId, status, serviceContext);
	}

	@Override
	public KBCommentService getWrappedService() {
		return _kbCommentService;
	}

	@Override
	public void setWrappedService(KBCommentService kbCommentService) {
		_kbCommentService = kbCommentService;
	}

	private KBCommentService _kbCommentService;

}