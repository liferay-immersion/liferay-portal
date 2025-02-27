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

package com.liferay.fragment.web.internal.portlet.action;

import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.fragment.constants.FragmentPortletKeys;
import com.liferay.fragment.helper.DefaultInputFragmentEntryConfigurationProvider;
import com.liferay.fragment.item.selector.FragmentEntryItemSelectorReturnType;
import com.liferay.fragment.item.selector.criterion.FragmentEntryItemSelectorCriterion;
import com.liferay.item.selector.ItemSelector;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.constants.MVCRenderConstants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;

import java.io.IOException;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Víctor Galán
 */
@Component(
	property = {
		"javax.portlet.name=" + FragmentPortletKeys.FRAGMENT,
		"mvc.command.name=/fragment/select_default_input_fragment_entry"
	},
	service = MVCRenderCommand.class
)
public class SelectDefaultInputFragmentEntryMVCRenderCommand
	implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		FragmentEntryItemSelectorCriterion fragmentEntryItemSelectorCriterion =
			new FragmentEntryItemSelectorCriterion();

		fragmentEntryItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			new FragmentEntryItemSelectorReturnType());

		String inputType = ParamUtil.getString(renderRequest, "inputType");

		if (!Objects.equals(
				inputType,
				DefaultInputFragmentEntryConfigurationProvider.
					FORM_INPUT_SUBMIT_BUTTON)) {

			fragmentEntryItemSelectorCriterion.setInputTypes(
				new HashSet<>(Collections.singletonList(inputType)));

			fragmentEntryItemSelectorCriterion.setType(
				FragmentConstants.TYPE_INPUT);
		}

		RequestBackedPortletURLFactory requestBackedPortletURLFactory =
			RequestBackedPortletURLFactoryUtil.create(renderRequest);

		try {
			HttpServletResponse httpServletResponse =
				_portal.getHttpServletResponse(renderResponse);

			httpServletResponse.sendRedirect(
				String.valueOf(
					_itemSelector.getItemSelectorURL(
						requestBackedPortletURLFactory,
						renderResponse.getNamespace() + "selectFragment",
						fragmentEntryItemSelectorCriterion)));
		}
		catch (IOException ioException) {
			_log.error(ioException);

			return "/error.jsp";
		}

		return MVCRenderConstants.MVC_PATH_VALUE_SKIP_DISPATCH;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SelectDefaultInputFragmentEntryMVCRenderCommand.class);

	@Reference
	private ItemSelector _itemSelector;

	@Reference
	private Portal _portal;

}