<%--
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
--%>

<%@ include file="/init.jsp" %>

<%
SearchContainer<?> searchContainer = (SearchContainer<?>)request.getAttribute("liferay-ui:search:searchContainer");
%>

<liferay-ui:search-toggle
	buttonLabel="search"
	displayTerms="<%= searchContainer.getDisplayTerms() %>"
	id="toggle_id_audit_event_search"
>
	<aui:input label="user-id" name="userId" value="<%= (userId != 0) ? String.valueOf(userId) : StringPool.BLANK %>" />

	<aui:input label="user-name" name="userName" value="<%= userName %>" />

	<c:if test='<%= FeatureFlagManagerUtil.isEnabled("LPS-177194") %>'>
		<aui:input label="group-id" name="groupId" value="<%= (groupId != 0) ? String.valueOf(groupId) : StringPool.BLANK %>" />
	</c:if>

	<aui:input label="resource-id" name="classPK" value="<%= classPK %>" />

	<aui:input label="class-name" name="className" value="<%= className %>" />

	<aui:input label="resource-action" name="eventType" value="<%= eventType %>" />

	<aui:input label="client-ip" name="clientIP" value="<%= clientIP %>" />

	<aui:input label="client-host" name="clientHost" value="<%= clientHost %>" />

	<aui:input label="server-name" name="serverName" value="<%= serverName %>" />

	<aui:input label="server-port" name="serverPort" value="<%= (serverPort != 0) ? String.valueOf(serverPort) : StringPool.BLANK %>" />

	<aui:field-wrapper label="start-date">
		<liferay-ui:input-date
			dayParam="startDateDay"
			dayValue="<%= startDateDay %>"
			monthParam="startDateMonth"
			monthValue="<%= startDateMonth %>"
			name="startDate"
			yearParam="startDateYear"
			yearValue="<%= startDateYear %>"
		/>

		<liferay-ui:input-time
			amPmParam="startDateAmPm"
			amPmValue="<%= startDateAmPm %>"
			hourParam="startDateHour"
			hourValue="<%= startDateHour %>"
			minuteParam="startDateMinute"
			minuteValue="<%= startDateMinute %>"
			name="startDateTime"
		/>
	</aui:field-wrapper>

	<aui:field-wrapper label="end-date">
		<liferay-ui:input-date
			dayParam="endDateDay"
			dayValue="<%= endDateDay %>"
			monthParam="endDateMonth"
			monthValue="<%= endDateMonth %>"
			name="endDate"
			yearParam="endDateYear"
			yearValue="<%= endDateYear %>"
		/>

		<liferay-ui:input-time
			amPmParam="endDateAmPm"
			amPmValue="<%= endDateAmPm %>"
			hourParam="endDateHour"
			hourValue="<%= endDateHour %>"
			minuteParam="endDateMinute"
			minuteValue="<%= endDateMinute %>"
			name="endDateTime"
		/>
	</aui:field-wrapper>
</liferay-ui:search-toggle>