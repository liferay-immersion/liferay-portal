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

package com.liferay.dynamic.data.mapping.web.internal.portlet.action;

import com.liferay.dynamic.data.mapping.constants.DDMPortletKeys;
import com.liferay.dynamic.data.mapping.constants.DDMTemplateConstants;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMStructureService;
import com.liferay.dynamic.data.mapping.service.DDMTemplateService;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Localization;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Leonardo Barros
 */
@Component(
	property = {
		"javax.portlet.name=" + DDMPortletKeys.DYNAMIC_DATA_MAPPING,
		"mvc.command.name=/dynamic_data_mapping/copy_structure"
	},
	service = MVCActionCommand.class
)
public class CopyStructureMVCActionCommand extends BaseDDMMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		DDMStructure structure = _copyStructure(actionRequest);

		setRedirectAttribute(actionRequest, structure);
	}

	@Override
	protected String getSaveAndContinueRedirect(
			ActionRequest actionRequest, DDMStructure structure,
			String redirect)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		LiferayPortletURL portletURL = PortletURLFactoryUtil.create(
			actionRequest, themeDisplay.getPpid(), PortletRequest.RENDER_PHASE);

		portletURL.setParameter("mvcPath", "/copy_structure");
		portletURL.setParameter(
			"classNameId",
			String.valueOf(_portal.getClassNameId(DDMStructure.class)), false);
		portletURL.setParameter(
			"classPK", String.valueOf(structure.getStructureId()), false);
		portletURL.setParameter(
			"copyFormTemplates",
			ParamUtil.getString(actionRequest, "copyFormTemplates"), false);
		portletURL.setParameter(
			"copyDisplayTemplates",
			ParamUtil.getString(actionRequest, "copyDisplayTemplates"), false);
		portletURL.setWindowState(actionRequest.getWindowState());

		return portletURL.toString();
	}

	private DDMStructure _copyStructure(ActionRequest actionRequest)
		throws Exception {

		long classPK = ParamUtil.getLong(actionRequest, "classPK");

		Map<Locale, String> nameMap = _localization.getLocalizationMap(
			actionRequest, "name");
		Map<Locale, String> descriptionMap = _localization.getLocalizationMap(
			actionRequest, "description");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			DDMStructure.class.getName(), actionRequest);

		DDMStructure structure = _ddmStructureService.copyStructure(
			classPK, nameMap, descriptionMap, serviceContext);

		_copyTemplates(actionRequest, classPK, structure.getStructureId());

		return structure;
	}

	private void _copyTemplates(
			ActionRequest actionRequest, long oldClassPK, long newClassPK)
		throws Exception {

		long classNameId = _portal.getClassNameId(DDMStructure.class);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			DDMTemplate.class.getName(), actionRequest);

		long resourceClassNameId = ParamUtil.getLong(
			actionRequest, "resourceClassNameId");
		boolean copyDisplayTemplates = ParamUtil.getBoolean(
			actionRequest, "copyDisplayTemplates");

		if (copyDisplayTemplates) {
			_ddmTemplateService.copyTemplates(
				classNameId, oldClassPK, resourceClassNameId, newClassPK,
				DDMTemplateConstants.TEMPLATE_TYPE_DISPLAY, serviceContext);
		}

		boolean copyFormTemplates = ParamUtil.getBoolean(
			actionRequest, "copyFormTemplates");

		if (copyFormTemplates) {
			_ddmTemplateService.copyTemplates(
				classNameId, oldClassPK, resourceClassNameId, newClassPK,
				DDMTemplateConstants.TEMPLATE_TYPE_FORM, serviceContext);
		}
	}

	@Reference
	private DDMStructureService _ddmStructureService;

	@Reference
	private DDMTemplateService _ddmTemplateService;

	@Reference
	private Localization _localization;

	@Reference
	private Portal _portal;

}