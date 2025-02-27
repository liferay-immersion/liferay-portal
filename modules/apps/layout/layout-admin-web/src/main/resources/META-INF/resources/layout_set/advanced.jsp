<%--
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
--%>

<%@ include file="/init.jsp" %>

<%
Group guestGroup = GroupLocalServiceUtil.getGroup(company.getCompanyId(), GroupConstants.GUEST);
%>

<liferay-ui:error-marker
	key="<%= WebKeys.ERROR_SECTION %>"
	value="advanced"
/>

<c:choose>
	<c:when test="<%= !layoutsAdminDisplayContext.isPrivateLayout() && (layoutsAdminDisplayContext.getLiveGroupId() != guestGroup.getGroupId()) %>">

		<%
		Group group = layoutsAdminDisplayContext.getLiveGroup();
		%>

		<c:choose>
			<c:when test="<%= group.isPrivateLayoutsEnabled() %>">
				<aui:input helpMessage='<%= LanguageUtil.format(request, "you-can-configure-the-top-level-pages-of-this-public-site-to-merge-with-the-top-level-pages-of-the-public-x-site", HtmlUtil.escape(guestGroup.getDescriptiveName(locale)), false) %>' label='<%= LanguageUtil.format(request, "merge-x-public-pages", HtmlUtil.escape(guestGroup.getDescriptiveName(locale)), false) %>' name="mergeGuestPublicPages" type="checkbox" value='<%= PropertiesParamUtil.getBoolean(layoutsAdminDisplayContext.getGroupTypeSettingsUnicodeProperties(), request, "mergeGuestPublicPages") %>' />
			</c:when>
			<c:otherwise>
				<aui:input helpMessage='<%= LanguageUtil.format(request, "you-can-configure-the-top-level-pages-of-this-site-to-merge-with-the-top-level-pages-of-the-x-site", HtmlUtil.escape(guestGroup.getDescriptiveName(locale)), false) %>' label='<%= LanguageUtil.format(request, "merge-x-pages", HtmlUtil.escape(guestGroup.getDescriptiveName(locale)), false) %>' name="mergeGuestPublicPages" type="checkbox" value='<%= PropertiesParamUtil.getBoolean(layoutsAdminDisplayContext.getGroupTypeSettingsUnicodeProperties(), request, "mergeGuestPublicPages") %>' />
			</c:otherwise>
		</c:choose>
	</c:when>
	<c:otherwise>
		<clay:alert
			displayType="info"
			message="there-are-no-available-advanced-settings-for-these-pages"
		/>
	</c:otherwise>
</c:choose>