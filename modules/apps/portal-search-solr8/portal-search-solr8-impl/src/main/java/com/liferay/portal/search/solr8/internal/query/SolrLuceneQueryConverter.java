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

package com.liferay.portal.search.solr8.internal.query;

import com.liferay.portal.kernel.search.Query;

import org.osgi.service.component.annotations.Component;

/**
 * @author Joao Victor Alves
 */
@Component(
	property = "search.engine.impl=Solr", service = LuceneQueryConverter.class
)
public class SolrLuceneQueryConverter
	extends BaseQueryVisitor implements LuceneQueryConverter {

	@Override
	public org.apache.lucene.search.Query convert(Query query) {
		return query.accept(this);
	}

}