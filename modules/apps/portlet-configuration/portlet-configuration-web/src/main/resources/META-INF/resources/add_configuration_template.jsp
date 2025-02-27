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
String redirect = ParamUtil.getString(request, "redirect");
%>

<portlet:actionURL name="updateArchivedSetup" var="updateArchivedSetupURL">
	<portlet:param name="mvcPath" value="/edit_configuration_templates.jsp" />
	<portlet:param name="portletConfiguration" value="<%= Boolean.TRUE.toString() %>" />
</portlet:actionURL>

<div class="cadmin portlet-configuration-add-template">
	<aui:form action="<%= updateArchivedSetupURL %>" cssClass="form" id="fm" method="post" name="fm">
		<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
		<aui:input name="portletResource" type="hidden" value="<%= portletResource %>" />

		<div class="portlet-configuration-body-content">
			<clay:container-fluid>
				<liferay-ui:error exception="<%= PortletItemNameException.class %>" message="please-enter-a-valid-setup-name" />

				<div class="sheet">
					<div class="panel-group panel-group-flush">
						<aui:fieldset>

							<%
							String name = StringPool.BLANK;

							boolean useCustomTitle = GetterUtil.getBoolean(portletPreferences.getValue("portletSetupUseCustomTitle", null));

							if (useCustomTitle) {
								name = PortletConfigurationUtil.getPortletTitle(portletPreferences, LocaleUtil.toLanguageId(themeDisplay.getSiteDefaultLocale()));
							}
							%>

							<aui:input name="name" placeholder="name" required="<%= true %>" type="text" value="<%= name %>">
								<aui:validator name="maxLength">75</aui:validator>
							</aui:input>
						</aui:fieldset>
					</div>
				</div>
			</clay:container-fluid>
		</div>

		<aui:button-row>
			<liferay-frontend:edit-form-buttons
				redirect="<%= redirect %>"
			/>
		</aui:button-row>
	</aui:form>
</div>

<liferay-frontend:component
	module="js/AddConfigurationTemplateEventHandler"
/>