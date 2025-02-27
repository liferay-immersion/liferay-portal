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

package com.liferay.change.tracking.web.internal.frontend.data.set.view.table;

import com.liferay.change.tracking.web.internal.constants.PublicationsFDSNames;
import com.liferay.frontend.data.set.view.FDSView;
import com.liferay.frontend.data.set.view.table.BaseTableFDSView;
import com.liferay.frontend.data.set.view.table.FDSTableSchema;
import com.liferay.frontend.data.set.view.table.FDSTableSchemaBuilder;
import com.liferay.frontend.data.set.view.table.FDSTableSchemaBuilderFactory;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolver;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Truong
 */
@Component(
	property = "frontend.data.set.name=" + PublicationsFDSNames.PUBLICATIONS_ONGOING,
	service = FDSView.class
)
public class PublicationsOngoingTableFDSView extends BaseTableFDSView {

	@Override
	public FDSTableSchema getFDSTableSchema(Locale locale) {
		FDSTableSchemaBuilder fdsTableSchemaBuilder =
			_fdsTableSchemaBuilderFactory.create();

		return fdsTableSchemaBuilder.add(
			"name", "publication",
			fdsTableSchemaField -> fdsTableSchemaField.setActionId(
				"review-changes"
			).setContentRenderer(
				"actionLink"
			).setSortable(
				true
			)
		).add(
			"description", "description"
		).add(
			"dateModified", "last-modified",
			fdsTableSchemaField -> fdsTableSchemaField.setContentRenderer(
				"dateTime"
			).setSortable(
				true
			)
		).add(
			"dateCreated", "create-date",
			fdsTableSchemaField -> fdsTableSchemaField.setContentRenderer(
				"dateTime"
			).setSortable(
				true
			)
		).add(
			"status", "status",
			fdsTableSchemaField ->
				fdsTableSchemaField.setContentRendererModuleURL(
					_npmResolver.resolveModuleName("change-tracking-web") +
						"/publications/js/components/StatusRenderer")
		).add(
			"ownerName", "owner"
		).build();
	}

	@Reference
	private FDSTableSchemaBuilderFactory _fdsTableSchemaBuilderFactory;

	@Reference
	private NPMResolver _npmResolver;

}