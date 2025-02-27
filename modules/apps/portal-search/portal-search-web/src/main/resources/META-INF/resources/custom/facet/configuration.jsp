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

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %><%@
taglib uri="http://liferay.com/tld/learn" prefix="liferay-learn" %><%@
taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %><%@
taglib uri="http://liferay.com/tld/template" prefix="liferay-template" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page import="com.liferay.portal.kernel.util.Constants" %><%@
page import="com.liferay.portal.search.web.internal.custom.facet.configuration.CustomFacetPortletInstanceConfiguration" %><%@
page import="com.liferay.portal.search.web.internal.custom.facet.display.context.CustomFacetDisplayContext" %><%@
page import="com.liferay.portal.search.web.internal.custom.facet.portlet.CustomFacetPortlet" %><%@
page import="com.liferay.portal.search.web.internal.custom.facet.portlet.CustomFacetPortletPreferences" %><%@
page import="com.liferay.portal.search.web.internal.custom.facet.portlet.CustomFacetPortletPreferencesImpl" %><%@
page import="com.liferay.portal.search.web.internal.util.PortletPreferencesJspUtil" %>

<liferay-frontend:defineObjects />

<liferay-theme:defineObjects />

<portlet:defineObjects />

<%
CustomFacetDisplayContext customFacetDisplayContext = new CustomFacetDisplayContext(request);

CustomFacetPortletInstanceConfiguration customFacetPortletInstanceConfiguration = customFacetDisplayContext.getCustomFacetPortletInstanceConfiguration();

CustomFacetPortletPreferences customFacetPortletPreferences = new CustomFacetPortletPreferencesImpl(portletPreferences);
%>

<liferay-portlet:actionURL portletConfiguration="<%= true %>" var="configurationActionURL" />

<liferay-portlet:renderURL portletConfiguration="<%= true %>" var="configurationRenderURL" />

<liferay-frontend:edit-form
	action="<%= configurationActionURL %>"
	method="post"
	name="fm"
>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= configurationRenderURL %>" />

	<liferay-frontend:edit-form-body>
		<liferay-frontend:fieldset
			collapsible="<%= true %>"
			label="display-settings"
		>
			<div class="display-template">
				<liferay-template:template-selector
					className="<%= CustomFacetPortlet.class.getName() %>"
					displayStyle="<%= customFacetPortletInstanceConfiguration.displayStyle() %>"
					displayStyleGroupId="<%= customFacetDisplayContext.getDisplayStyleGroupId() %>"
					refreshURL="<%= configurationRenderURL %>"
					showEmptyOption="<%= true %>"
				/>
			</div>
		</liferay-frontend:fieldset>

		<liferay-frontend:fieldset
			collapsible="<%= true %>"
			label="advanced-configuration"
		>
			<div class="form-group">
				<aui:input helpMessage="aggregation-field-help" label="aggregation-field" name="<%= PortletPreferencesJspUtil.getInputName(CustomFacetPortletPreferences.PREFERENCE_KEY_AGGREGATION_FIELD) %>" value="<%= customFacetPortletPreferences.getAggregationField() %>" wrapperCssClass="c-mb-0" />

				<div class="form-feedback-group">
					<div class="form-text">
						<liferay-ui:message key="aggregation-field-input-help" />

						<liferay-learn:message
							key="custom-facet"
							resource="portal-search-web"
						/>
					</div>
				</div>
			</div>

			<aui:input helpMessage="custom-heading-help" label="custom-heading" name="<%= PortletPreferencesJspUtil.getInputName(CustomFacetPortletPreferences.PREFERENCE_KEY_CUSTOM_HEADING) %>" value="<%= customFacetPortletPreferences.getCustomHeading() %>" />

			<aui:input helpMessage="custom-parameter-name-help" label="custom-parameter-name" name="<%= PortletPreferencesJspUtil.getInputName(CustomFacetPortletPreferences.PREFERENCE_KEY_PARAMETER_NAME) %>" value="<%= customFacetPortletPreferences.getParameterName() %>" />

			<aui:input label="max-terms" name="<%= PortletPreferencesJspUtil.getInputName(CustomFacetPortletPreferences.PREFERENCE_KEY_MAX_TERMS) %>" value="<%= customFacetPortletPreferences.getMaxTerms() %>" />

			<aui:input label="frequency-threshold" name="<%= PortletPreferencesJspUtil.getInputName(CustomFacetPortletPreferences.PREFERENCE_KEY_FREQUENCY_THRESHOLD) %>" value="<%= customFacetPortletPreferences.getFrequencyThreshold() %>" />

			<aui:select label="order-terms-by" name="<%= PortletPreferencesJspUtil.getInputName(CustomFacetPortletPreferences.PREFERENCE_KEY_ORDER) %>" value="<%= customFacetPortletPreferences.getOrder() %>">
				<aui:option label="term-frequency-descending" value="count:desc" />
				<aui:option label="term-frequency-ascending" value="count:asc" />
				<aui:option label="term-value-ascending" value="key:asc" />
				<aui:option label="term-value-descending" value="key:desc" />
			</aui:select>

			<aui:input label="display-frequencies" name="<%= PortletPreferencesJspUtil.getInputName(CustomFacetPortletPreferences.PREFERENCE_KEY_FREQUENCIES_VISIBLE) %>" type="checkbox" value="<%= customFacetPortletPreferences.isFrequenciesVisible() %>" />

			<aui:input helpMessage="enter-the-key-of-an-alternate-search-this-widget-is-participating-on-if-not-set-widget-participates-on-default-search" label="federated-search-key" name="<%= PortletPreferencesJspUtil.getInputName(CustomFacetPortletPreferences.PREFERENCE_KEY_FEDERATED_SEARCH_KEY) %>" type="text" value="<%= customFacetPortletPreferences.getFederatedSearchKey() %>" />
		</liferay-frontend:fieldset>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<liferay-frontend:edit-form-buttons />
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>