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

package com.liferay.knowledge.base.internal.upgrade.v1_1_0.util;

import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.knowledge.base.service.KBArticleLocalServiceUtil;
import com.liferay.portal.kernel.upgrade.util.BaseUpgradeColumnImpl;
import com.liferay.portal.kernel.upgrade.util.UpgradeColumn;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

/**
 * @author Peter Shin
 */
public class KBArticleMainUpgradeColumnImpl extends BaseUpgradeColumnImpl {

	public KBArticleMainUpgradeColumnImpl(
		KBArticleAttachmentsHelper kbArticleAttachmentsHelper,
		UpgradeColumn kbArticleIdColumn, UpgradeColumn resourcePrimKeyColumn) {

		super("main");

		_kbArticleAttachmentsHelper = kbArticleAttachmentsHelper;
		_kbArticleIdColumn = kbArticleIdColumn;
		_resourcePrimKeyColumn = resourcePrimKeyColumn;
	}

	@Override
	public Object getNewValue(Object oldValue) throws Exception {
		Long resourcePrimKey = (Long)_resourcePrimKeyColumn.getOldValue();

		KBArticle kbArticle = KBArticleLocalServiceUtil.getLatestKBArticle(
			resourcePrimKey, WorkflowConstants.STATUS_ANY);

		if (kbArticle.isApproved()) {
			return Boolean.TRUE;
		}

		if (kbArticle.isFirstVersion()) {
			return Boolean.FALSE;
		}

		kbArticle = KBArticleLocalServiceUtil.getLatestKBArticle(
			resourcePrimKey, WorkflowConstants.STATUS_APPROVED);

		Long kbArticleId = (Long)_kbArticleIdColumn.getOldValue();

		if (kbArticle.getKbArticleId() != kbArticleId) {
			return Boolean.FALSE;
		}

		_kbArticleAttachmentsHelper.updateAttachments(kbArticle);

		return Boolean.TRUE;
	}

	private final KBArticleAttachmentsHelper _kbArticleAttachmentsHelper;
	private final UpgradeColumn _kbArticleIdColumn;
	private final UpgradeColumn _resourcePrimKeyColumn;

}