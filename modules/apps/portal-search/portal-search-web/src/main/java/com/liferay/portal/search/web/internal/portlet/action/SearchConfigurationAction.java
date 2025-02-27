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

package com.liferay.portal.search.web.internal.portlet.action;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.portlet.DefaultConfigurationAction;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.search.web.constants.SearchPortletKeys;
import com.liferay.portal.search.web.facet.SearchFacet;
import com.liferay.portal.search.web.internal.facet.SearchFacetRegistry;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alexander Chow
 */
@Component(
	property = "javax.portlet.name=" + SearchPortletKeys.SEARCH,
	service = ConfigurationAction.class
)
public class SearchConfigurationAction extends DefaultConfigurationAction {

	@Override
	public String getJspPath(HttpServletRequest httpServletRequest) {
		return "/configuration.jsp";
	}

	@Override
	public void processAction(
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		JSONArray facetsJSONArray = _jsonFactory.createJSONArray();

		for (SearchFacet searchFacet : searchFacetRegistry.getSearchFacets()) {
			JSONObject facetJSONObject = JSONUtil.put(
				"className", searchFacet.getFacetClassName()
			).put(
				"data", searchFacet.getJSONData(actionRequest)
			).put(
				"fieldName", searchFacet.getFieldName()
			).put(
				"id", searchFacet.getId()
			).put(
				"label", searchFacet.getLabel()
			).put(
				"order", searchFacet.getOrder()
			);

			boolean displayFacet = ParamUtil.getBoolean(
				actionRequest, searchFacet.getClassName() + "displayFacet");

			facetJSONObject.put("static", !displayFacet);

			double weight = ParamUtil.getDouble(
				actionRequest, searchFacet.getClassName() + "weight");

			facetJSONObject.put("weight", weight);

			facetsJSONArray.put(facetJSONObject);
		}

		JSONObject jsonObject = JSONUtil.put("facets", facetsJSONArray);

		setPreference(
			actionRequest, "searchConfiguration", jsonObject.toString());

		super.processAction(portletConfig, actionRequest, actionResponse);
	}

	@Reference
	protected SearchFacetRegistry searchFacetRegistry;

	@Reference
	private JSONFactory _jsonFactory;

}