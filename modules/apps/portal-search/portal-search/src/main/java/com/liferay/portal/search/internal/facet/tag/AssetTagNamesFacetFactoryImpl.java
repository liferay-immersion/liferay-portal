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

package com.liferay.portal.search.internal.facet.tag;

import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.search.facet.Facet;
import com.liferay.portal.search.facet.tag.AssetTagNamesFacetFactory;
import com.liferay.portal.search.internal.facet.FacetImpl;

import org.osgi.service.component.annotations.Component;

/**
 * @author André de Oliveira
 */
@Component(service = AssetTagNamesFacetFactory.class)
public class AssetTagNamesFacetFactoryImpl
	implements AssetTagNamesFacetFactory {

	@Override
	public String getFacetClassName() {
		return Field.ASSET_TAG_NAMES + ".raw";
	}

	@Override
	public Facet newInstance(SearchContext searchContext) {
		return new FacetImpl(Field.ASSET_TAG_NAMES + ".raw", searchContext);
	}

}