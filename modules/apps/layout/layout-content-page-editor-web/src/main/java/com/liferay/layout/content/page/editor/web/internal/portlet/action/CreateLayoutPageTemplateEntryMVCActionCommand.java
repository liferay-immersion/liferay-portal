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

package com.liferay.layout.content.page.editor.web.internal.portlet.action;

import com.liferay.layout.content.page.editor.constants.ContentPageEditorPortletKeys;
import com.liferay.layout.page.template.admin.constants.LayoutPageTemplateAdminPortletKeys;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.exception.LayoutPageTemplateEntryNameException;
import com.liferay.layout.page.template.model.LayoutPageTemplateCollection;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateCollectionService;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.ModelHintsUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Locale;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(
	property = {
		"javax.portlet.name=" + ContentPageEditorPortletKeys.CONTENT_PAGE_EDITOR_PORTLET,
		"mvc.command.name=/layout_content_page_editor/create_layout_page_template_entry"
	},
	service = MVCActionCommand.class
)
public class CreateLayoutPageTemplateEntryMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long segmentsExperienceId = ParamUtil.getLong(
			actionRequest, "segmentsExperienceId");
		Layout sourceLayout = _layoutLocalService.getLayout(
			themeDisplay.getPlid());
		long layoutPageTemplateCollectionId = ParamUtil.getLong(
			actionRequest, "layoutPageTemplateCollectionId");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			LayoutPageTemplateEntry.class.getName(), actionRequest);

		if (layoutPageTemplateCollectionId <= 0) {
			String layoutPageTemplateCollectionName = ParamUtil.getString(
				actionRequest, "layoutPageTemplateCollectionName");
			String layoutPageTemplateCollectionDescription =
				ParamUtil.getString(
					actionRequest, "layoutPageTemplateCollectionDescription");

			LayoutPageTemplateCollection layoutPageTemplateCollection =
				_layoutPageTemplateCollectionService.
					addLayoutPageTemplateCollection(
						themeDisplay.getScopeGroupId(),
						layoutPageTemplateCollectionName,
						layoutPageTemplateCollectionDescription,
						serviceContext);

			layoutPageTemplateCollectionId =
				layoutPageTemplateCollection.
					getLayoutPageTemplateCollectionId();
		}

		JSONObject jsonObject = _jsonFactory.createJSONObject();

		try {
			LayoutPageTemplateEntry layoutPageTemplateEntry =
				_layoutPageTemplateEntryService.
					createLayoutPageTemplateEntryFromLayout(
						segmentsExperienceId, sourceLayout,
						_getUniqueName(sourceLayout, themeDisplay.getLocale()),
						layoutPageTemplateCollectionId, serviceContext);

			jsonObject.put(
				"url",
				PortletURLBuilder.create(
					_portal.getControlPanelPortletURL(
						_portal.getHttpServletRequest(actionRequest),
						themeDisplay.getScopeGroup(),
						LayoutPageTemplateAdminPortletKeys.
							LAYOUT_PAGE_TEMPLATES,
						0, 0, PortletRequest.RENDER_PHASE)
				).setTabs1(
					"page-templates"
				).setParameter(
					"layoutPageTemplateCollectionId",
					layoutPageTemplateEntry.getLayoutPageTemplateCollectionId()
				).setParameter(
					"orderByType", "desc"
				).buildString());
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			String errorMessage = null;

			if (exception instanceof
					LayoutPageTemplateEntryNameException.MustNotBeDuplicate) {

				errorMessage = _language.get(
					themeDisplay.getLocale(),
					"a-page-template-entry-with-that-name-already-exists");
			}
			else if (exception instanceof
						LayoutPageTemplateEntryNameException.MustNotBeNull) {

				errorMessage = _language.get(
					themeDisplay.getLocale(), "name-must-not-be-empty");
			}
			else if (exception instanceof
						LayoutPageTemplateEntryNameException.
							MustNotContainInvalidCharacters) {

				LayoutPageTemplateEntryNameException.
					MustNotContainInvalidCharacters lptene =
						(LayoutPageTemplateEntryNameException.
							MustNotContainInvalidCharacters)exception;

				errorMessage = _language.format(
					themeDisplay.getLocale(),
					"name-cannot-contain-the-following-invalid-character-x",
					lptene.character);
			}
			else if (exception instanceof
						LayoutPageTemplateEntryNameException.
							MustNotExceedMaximumSize) {

				int nameMaxLength = ModelHintsUtil.getMaxLength(
					LayoutPageTemplateEntry.class.getName(), "name");

				errorMessage = _language.format(
					themeDisplay.getLocale(),
					"please-enter-a-name-with-fewer-than-x-characters",
					nameMaxLength);
			}

			if (Validator.isNull(errorMessage)) {
				errorMessage = _language.get(
					themeDisplay.getLocale(), "an-unexpected-error-occurred");
			}

			jsonObject.put("error", errorMessage);
		}

		JSONPortletResponseUtil.writeJSON(
			actionRequest, actionResponse, jsonObject);
	}

	private String _getUniqueName(Layout layout, Locale locale) {
		String name = StringBundler.concat(
			layout.getName(locale), " - ",
			_language.get(locale, "page-template"));

		for (int i = 2;; i++) {
			LayoutPageTemplateEntry targetLayoutPageTemplateEntry =
				_layoutPageTemplateEntryLocalService.
					fetchLayoutPageTemplateEntry(
						layout.getGroupId(), name,
						LayoutPageTemplateEntryTypeConstants.TYPE_BASIC);

			if (targetLayoutPageTemplateEntry == null) {
				break;
			}

			name = StringBundler.concat(
				layout.getName(locale), " - ",
				_language.get(locale, "page-template"), StringPool.SPACE, i);
		}

		return name;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CreateLayoutPageTemplateEntryMVCActionCommand.class);

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Language _language;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutPageTemplateCollectionService
		_layoutPageTemplateCollectionService;

	@Reference
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Reference
	private LayoutPageTemplateEntryService _layoutPageTemplateEntryService;

	@Reference
	private Portal _portal;

}