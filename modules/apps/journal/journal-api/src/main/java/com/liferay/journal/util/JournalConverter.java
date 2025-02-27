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

package com.liferay.journal.util;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.storage.Fields;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.xml.Document;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Marcellus Tavares
 * @author Bruno Basto
 */
@ProviderType
public interface JournalConverter {

	public String getContent(
			DDMStructure ddmStructure, Fields ddmFields, long groupId)
		throws Exception;

	public Fields getDDMFields(DDMStructure ddmStructure, String content)
		throws PortalException;

	public Document getDocument(
			DDMStructure ddmStructure, Fields ddmFields, long groupId)
		throws Exception;

}