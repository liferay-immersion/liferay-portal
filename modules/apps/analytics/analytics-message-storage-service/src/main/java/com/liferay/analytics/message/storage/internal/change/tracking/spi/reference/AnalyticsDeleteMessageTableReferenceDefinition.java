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

package com.liferay.analytics.message.storage.internal.change.tracking.spi.reference;

import com.liferay.analytics.message.storage.model.AnalyticsDeleteMessageTable;
import com.liferay.analytics.message.storage.service.persistence.AnalyticsDeleteMessagePersistence;
import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Truong
 */
@Component(service = TableReferenceDefinition.class)
public class AnalyticsDeleteMessageTableReferenceDefinition
	implements TableReferenceDefinition<AnalyticsDeleteMessageTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<AnalyticsDeleteMessageTable>
			childTableReferenceInfoBuilder) {
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<AnalyticsDeleteMessageTable>
			parentTableReferenceInfoBuilder) {
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _analyticsDeleteMessagePersistence;
	}

	@Override
	public AnalyticsDeleteMessageTable getTable() {
		return AnalyticsDeleteMessageTable.INSTANCE;
	}

	@Reference
	private AnalyticsDeleteMessagePersistence
		_analyticsDeleteMessagePersistence;

}