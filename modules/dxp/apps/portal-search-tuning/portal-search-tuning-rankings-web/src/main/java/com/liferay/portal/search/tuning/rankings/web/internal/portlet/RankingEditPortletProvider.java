/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.search.tuning.rankings.web.internal.portlet;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.BasePortletProvider;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.search.tuning.rankings.web.internal.constants.ResultRankingsPortletKeys;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;

/**
 * @author Kevin Tan
 */
@Component(
	property = "model.class.name=com.liferay.portal.kernel.search.Document",
	service = PortletProvider.class
)
public class RankingEditPortletProvider extends BasePortletProvider {

	@Override
	public String getPortletName() {
		return ResultRankingsPortletKeys.RESULT_RANKINGS;
	}

	@Override
	public PortletURL getPortletURL(HttpServletRequest httpServletRequest)
		throws PortalException {

		return PortletURLBuilder.create(
			super.getPortletURL(httpServletRequest)
		).setMVCPath(
			"/add_results_rankings.jsp"
		).buildPortletURL();
	}

	@Override
	public Action[] getSupportedActions() {
		return _supportedActions;
	}

	private final Action[] _supportedActions = {Action.EDIT};

}