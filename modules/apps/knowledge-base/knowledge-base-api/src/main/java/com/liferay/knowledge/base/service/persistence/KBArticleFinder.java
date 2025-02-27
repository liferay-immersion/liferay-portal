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

package com.liferay.knowledge.base.service.persistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public interface KBArticleFinder {

	public int countByUrlTitle(
		long groupId, String kbFolderUrlTitle, String kbArticleUrlTitle,
		int[] status);

	public int filterCountByKeywords(long groupId, String keywords, int status);

	public java.util.List<com.liferay.knowledge.base.model.KBArticle>
		filterFindByKeywords(
			long groupId, String keywords, int status, int start, int end);

	public java.util.List<com.liferay.knowledge.base.model.KBArticle>
		findByUrlTitle(
			long groupId, String kbFolderUrlTitle, String kbArticleUrlTitle,
			int[] status, int start, int end);

}