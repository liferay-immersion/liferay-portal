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

package com.liferay.frontend.data.set.views.web.internal.fragment.renderer;

import com.liferay.client.extension.type.FDSCellRendererCET;
import com.liferay.client.extension.type.manager.CETManager;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.fragment.renderer.FragmentRendererContext;
import com.liferay.fragment.util.configuration.FragmentEntryConfigurationParser;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.object.rest.manager.v1_0.DefaultObjectEntryManager;
import com.liferay.object.rest.manager.v1_0.DefaultObjectEntryManagerProvider;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManagerRegistry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.template.react.renderer.ComponentDescriptor;
import com.liferay.portal.template.react.renderer.ReactRenderer;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Daniel Sanz
 * @author Marko Cikos
 */
@Component(service = FragmentRenderer.class)
public class FDSViewFragmentRenderer implements FragmentRenderer {

	@Override
	public String getCollectionKey() {
		return "content-display";
	}

	@Override
	public String getConfiguration(
		FragmentRendererContext fragmentRendererContext) {

		return JSONUtil.put(
			"fieldSets",
			JSONUtil.putAll(
				JSONUtil.put(
					"fields",
					JSONUtil.putAll(
						JSONUtil.put(
							"label", "data-set-view"
						).put(
							"name", "itemSelector"
						).put(
							"type", "itemSelector"
						).put(
							"typeOptions", JSONUtil.put("itemType", "FDSView")
						))))
		).toString();
	}

	@Override
	public String getIcon() {
		return "table";
	}

	public String getLabel(Locale locale) {
		return _language.get(locale, "data-set");
	}

	public boolean isSelectable(HttpServletRequest httpServletRequest) {
		if (!FeatureFlagManagerUtil.isEnabled("LPS-164563")) {
			return false;
		}

		return true;
	}

	@Override
	public void render(
			FragmentRendererContext fragmentRendererContext,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		try {
			PrintWriter printWriter = httpServletResponse.getWriter();

			FragmentEntryLink fragmentEntryLink =
				fragmentRendererContext.getFragmentEntryLink();

			JSONObject jsonObject =
				(JSONObject)_fragmentEntryConfigurationParser.getFieldValue(
					getConfiguration(fragmentRendererContext),
					fragmentEntryLink.getEditableValues(),
					fragmentRendererContext.getLocale(), "itemSelector");

			String externalReferenceCode = jsonObject.getString(
				"externalReferenceCode");

			ObjectEntry fdsViewObjectEntry = null;

			ObjectDefinition fdsViewObjectDefinition =
				_objectDefinitionLocalService.fetchObjectDefinition(
					fragmentEntryLink.getCompanyId(), "FDSView");

			if (externalReferenceCode != StringPool.BLANK) {
				fdsViewObjectEntry = _getObjectEntry(
					fragmentEntryLink.getCompanyId(), externalReferenceCode,
					fdsViewObjectDefinition);
			}

			if ((fdsViewObjectEntry == null) &&
				fragmentRendererContext.isEditMode()) {

				printWriter.write(
					StringBundler.concat(
						"<div class=\"portlet-msg-info\">",
						_language.get(
							httpServletRequest, "select-a-data-set-view"),
						"</div>"));
			}

			if (fdsViewObjectEntry == null) {
				return;
			}

			printWriter.write(
				_buildFragmentHTML(
					fdsViewObjectEntry, fdsViewObjectDefinition,
					fragmentRendererContext, httpServletRequest));
		}
		catch (Exception exception) {
			throw new IOException(exception);
		}
	}

	private String _buildFragmentHTML(
			ObjectEntry fdsViewObjectEntry,
			ObjectDefinition fdsViewObjectDefinition,
			FragmentRendererContext fragmentRendererContext,
			HttpServletRequest httpServletRequest)
		throws Exception {

		StringBundler sb = new StringBundler(5);

		sb.append("<div id=\"");
		sb.append(fragmentRendererContext.getFragmentElementId());
		sb.append("\" >");

		ComponentDescriptor componentDescriptor = new ComponentDescriptor(
			"{FrontendDataSet} from frontend-data-set-web",
			fragmentRendererContext.getFragmentElementId(), null, true);

		Writer writer = new CharArrayWriter();

		FragmentEntryLink fragmentEntryLink =
			fragmentRendererContext.getFragmentEntryLink();

		Map<String, Object> fdsViewObjectEntryProperties =
			fdsViewObjectEntry.getProperties();

		String fdsEntryObjectEntryERC = String.valueOf(
			fdsViewObjectEntryProperties.get(
				"r_fdsEntryFDSViewRelationship_c_fdsEntryERC"));

		ObjectDefinition fdsEntryObjectDefinition =
			_objectDefinitionLocalService.fetchObjectDefinition(
				fragmentEntryLink.getCompanyId(), "FDSEntry");

		ObjectEntry fdsEntryObjectEntry = _getObjectEntry(
			fragmentEntryLink.getCompanyId(), fdsEntryObjectEntryERC,
			fdsEntryObjectDefinition);

		_reactRenderer.renderReact(
			componentDescriptor,
			HashMapBuilder.<String, Object>put(
				"apiURL", _getAPIURL(fdsEntryObjectEntry, httpServletRequest)
			).put(
				"id", "FDS_" + fragmentRendererContext.getFragmentElementId()
			).put(
				"namespace", fragmentRendererContext.getFragmentElementId()
			).put(
				"pagination", _getPaginationJSONObject(fdsViewObjectEntry)
			).put(
				"style", "fluid"
			).put(
				"views",
				JSONUtil.putAll(
					JSONUtil.put(
						"contentRenderer", "table"
					).put(
						"name", "table"
					).put(
						"schema",
						JSONUtil.put(
							"fields",
							_getFieldsJSONArray(
								fragmentEntryLink, fdsViewObjectDefinition,
								fdsViewObjectEntry))
					))
			).build(),
			httpServletRequest, writer);

		sb.append(writer.toString());

		sb.append("</div>");

		return sb.toString();
	}

	private String _getAPIURL(
		ObjectEntry fdsEntryObjectEntry,
		HttpServletRequest httpServletRequest) {

		Map<String, Object> properties = fdsEntryObjectEntry.getProperties();

		StringBundler sb = new StringBundler(3);

		sb.append("/o");
		sb.append(
			StringUtil.replaceLast(
				String.valueOf(properties.get("restApplication")), "/v1.0",
				StringPool.BLANK));
		sb.append(String.valueOf(properties.get("restEndpoint")));

		return _interpolateURL(sb.toString(), httpServletRequest);
	}

	private JSONArray _getFieldsJSONArray(
			FragmentEntryLink fragmentEntryLink,
			ObjectDefinition objectDefinition, ObjectEntry objectEntry)
		throws Exception {

		List<ObjectEntry> fdsFieldObjectEntries = new ArrayList<>(
			_getRelatedObjectEntries(
				objectDefinition, objectEntry, "fdsViewFDSFieldRelationship"));

		Map<String, Object> fdsViewProperties = objectEntry.getProperties();

		List<Long> fdsFieldIds = ListUtil.toList(
			Arrays.asList(
				StringUtil.split(
					(String)fdsViewProperties.get("fdsFieldsOrder"),
					StringPool.COMMA)),
			Long::parseLong);

		Collections.sort(
			fdsFieldObjectEntries,
			Comparator.comparing(
				ObjectEntry::getId,
				Comparator.comparingInt(fdsFieldIds::indexOf)));

		return JSONUtil.toJSONArray(
			fdsFieldObjectEntries,
			(ObjectEntry fdsFieldObjectEntry) -> {
				Map<String, Object> fdsFieldProperties =
					fdsFieldObjectEntry.getProperties();

				JSONObject jsonObject = JSONUtil.put(
					"contentRenderer",
					String.valueOf(fdsFieldProperties.get("renderer"))
				).put(
					"fieldName", String.valueOf(fdsFieldProperties.get("name"))
				).put(
					"label", String.valueOf(fdsFieldProperties.get("label"))
				).put(
					"sortable", (boolean)fdsFieldProperties.get("sortable")
				);

				String rendererType = String.valueOf(
					fdsFieldProperties.get("rendererType"));

				if (!Objects.equals(rendererType, "clientExtension")) {
					return jsonObject;
				}

				FDSCellRendererCET fdsCellRendererCET =
					(FDSCellRendererCET)_cetManager.getCET(
						fragmentEntryLink.getCompanyId(),
						String.valueOf(fdsFieldProperties.get("renderer")));

				return jsonObject.put(
					"contentRendererClientExtension", true
				).put(
					"contentRendererModuleURL",
					"default from " + fdsCellRendererCET.getURL()
				);
			});
	}

	private ObjectEntry _getObjectEntry(
			long companyId, String externalReferenceCode,
			ObjectDefinition objectDefinition)
		throws Exception {

		DTOConverterContext dtoConverterContext =
			new DefaultDTOConverterContext(
				false, null, null, null, null, LocaleUtil.getSiteDefault(),
				null, null);

		DefaultObjectEntryManager defaultObjectEntryManager =
			DefaultObjectEntryManagerProvider.provide(
				_objectEntryManagerRegistry.getObjectEntryManager(
					objectDefinition.getStorageType()));

		return defaultObjectEntryManager.getObjectEntry(
			companyId, dtoConverterContext, externalReferenceCode,
			objectDefinition, null);
	}

	private JSONObject _getPaginationJSONObject(ObjectEntry fdsViewObjectEntry)
		throws Exception {

		Map<String, Object> properties = fdsViewObjectEntry.getProperties();

		return JSONUtil.put(
			"deltas",
			JSONUtil.toJSONArray(
				StringUtil.split(
					String.valueOf(properties.get("listOfItemsPerPage")),
					StringPool.COMMA_AND_SPACE),
				(String itemPerPage) -> JSONUtil.put(
					"label", GetterUtil.getInteger(itemPerPage)))
		).put(
			"initialDelta",
			String.valueOf(properties.get("defaultItemsPerPage"))
		);
	}

	private Collection<ObjectEntry> _getRelatedObjectEntries(
			ObjectDefinition objectDefinition, ObjectEntry objectEntry,
			String relationshipName)
		throws Exception {

		DTOConverterContext dtoConverterContext =
			new DefaultDTOConverterContext(
				false, null, null, null, null, LocaleUtil.getSiteDefault(),
				null, null);

		DefaultObjectEntryManager defaultObjectEntryManager =
			DefaultObjectEntryManagerProvider.provide(
				_objectEntryManagerRegistry.getObjectEntryManager(
					objectDefinition.getStorageType()));

		Page<ObjectEntry> relatedObjectEntriesPage =
			defaultObjectEntryManager.getObjectEntryRelatedObjectEntries(
				dtoConverterContext, objectDefinition, objectEntry.getId(),
				relationshipName,
				Pagination.of(QueryUtil.ALL_POS, QueryUtil.ALL_POS));

		return relatedObjectEntriesPage.getItems();
	}

	private String _interpolateURL(
		String apiUrl, HttpServletRequest httpServletRequest) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		apiUrl = StringUtil.replace(
			apiUrl, "{siteId}", String.valueOf(themeDisplay.getScopeGroupId()));
		apiUrl = StringUtil.replace(
			apiUrl, "{scopeKey}",
			String.valueOf(themeDisplay.getScopeGroupId()));
		apiUrl = StringUtil.replace(
			apiUrl, "{userId}", String.valueOf(themeDisplay.getUserId()));

		if (StringUtil.contains(apiUrl, "{") && _log.isWarnEnabled()) {
			_log.warn("Unsupported parameter in API URL: " + apiUrl);
		}

		return apiUrl;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FDSViewFragmentRenderer.class);

	@Reference
	private CETManager _cetManager;

	@Reference
	private FragmentEntryConfigurationParser _fragmentEntryConfigurationParser;

	@Reference
	private Language _language;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectEntryManagerRegistry _objectEntryManagerRegistry;

	@Reference
	private ReactRenderer _reactRenderer;

}