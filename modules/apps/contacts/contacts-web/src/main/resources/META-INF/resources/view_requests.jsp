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

<liferay-ui:header
	title="requests"
/>

<liferay-ui:search-container
	emptyResultsMessage="you-have-no-pending-requests"
	total="<%= SocialRequestLocalServiceUtil.getReceiverUserRequestsCount(themeDisplay.getUserId(), SocialRequestConstants.STATUS_PENDING) %>"
>
	<liferay-ui:search-container-results
		results="<%= SocialRequestLocalServiceUtil.getReceiverUserRequests(themeDisplay.getUserId(), SocialRequestConstants.STATUS_PENDING, searchContainer.getStart(), searchContainer.getEnd()) %>"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.social.kernel.model.SocialRequest"
		escapedModel="<%= true %>"
		keyProperty="requestId"
		modelVar="socialRequest"
	>

		<%
		User user2 = UserLocalServiceUtil.getUser(socialRequest.getUserId());
		%>

		<liferay-ui:search-container-column-text
			name="requests"
		>
			<liferay-portlet:renderURL varImpl="rowURL">
				<portlet:param name="mvcPath" value="/view_user.jsp" />
				<portlet:param name="backURL" value="<%= currentURL %>" />
				<portlet:param name="userId" value="<%= String.valueOf(user2.getUserId()) %>" />
			</liferay-portlet:renderURL>

			<div class="lfr-user-portrait">
				<a href="<%= rowURL %>"><img alt="<liferay-ui:message escapeAttribute="<%= true %>" key="avatar" />" class="avatar" src="<%= user2.getPortraitURL(themeDisplay) %>" /></a>
			</div>

			<%
			String taglibCreatorUserName = "<a href=\"" + rowURL.toString() + "\">" + user2.getFullName() + "</a>";
			%>

			<div class="lfr-user-data">
				<div class="lfr-user-data-title">
					<liferay-ui:message arguments="<%= taglibCreatorUserName %>" key="request-social-networking-summary-add-connection" translateArguments="<%= false %>" />
				</div>
			</div>

			<div class="lfr-user-action">
				<portlet:actionURL name="updateSocialRequest" var="confirmURL">
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="requestId" value="<%= String.valueOf(socialRequest.getRequestId()) %>" />
					<portlet:param name="status" value="<%= String.valueOf(SocialRequestConstants.STATUS_CONFIRM) %>" />
				</portlet:actionURL>

				<span class="lfr-user-action-confirm lfr-user-action-item">
					<a href="<%= confirmURL %>"><liferay-ui:message key="confirm" /></a>
				</span>

				<portlet:actionURL name="updateSocialRequest" var="ignoreURL">
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="requestId" value="<%= String.valueOf(socialRequest.getRequestId()) %>" />
					<portlet:param name="status" value="<%= String.valueOf(SocialRequestConstants.STATUS_IGNORE) %>" />
				</portlet:actionURL>

				<span class="lfr-user-action-ignore lfr-user-action-item">
					<a href="<%= ignoreURL %>"><liferay-ui:message key="ignore" /></a>
				</span>
			</div>
		</liferay-ui:search-container-column-text>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator
		markupView="lexicon"
	/>
</liferay-ui:search-container>