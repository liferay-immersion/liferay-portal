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

package com.liferay.journal.web.internal.info.item.provider;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.info.item.provider.InfoItemObjectVariationProvider;
import com.liferay.journal.model.JournalArticle;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;

/**
 * @author Eudaldo Alonso
 */
@Component(service = InfoItemObjectVariationProvider.class)
public class JournalArticleInfoItemObjectVariationProvider
	implements InfoItemObjectVariationProvider<JournalArticle> {

	@Override
	public String getInfoItemFormVariationKey(JournalArticle journalArticle) {
		if (journalArticle == null) {
			return null;
		}

		DDMStructure ddmStructure = journalArticle.getDDMStructure();

		return String.valueOf(ddmStructure.getStructureId());
	}

	@Override
	public String getInfoItemFormVariationLabel(
		JournalArticle journalArticle, Locale locale) {

		DDMStructure ddmStructure = journalArticle.getDDMStructure();

		return ddmStructure.getName(locale);
	}

}