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

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/clay" prefix="clay" %><%@
taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %><%@
taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %><%@
taglib uri="http://liferay.com/tld/site-navigation" prefix="liferay-site-navigation" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %><%@
taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>

<%@ page import="com.liferay.frontend.taglib.clay.servlet.taglib.util.JSPNavigationItemList" %><%@
page import="com.liferay.petra.string.StringBundler" %><%@
page import="com.liferay.petra.string.StringPool" %><%@
page import="com.liferay.portal.kernel.backgroundtask.BackgroundTask" %><%@
page import="com.liferay.portal.kernel.backgroundtask.BackgroundTaskStatus" %><%@
page import="com.liferay.portal.kernel.backgroundtask.BackgroundTaskStatusRegistryUtil" %><%@
page import="com.liferay.portal.kernel.dao.search.SearchContainer" %><%@
page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.language.UnicodeLanguageUtil" %><%@
page import="com.liferay.portal.kernel.model.User" %><%@
page import="com.liferay.portal.kernel.portlet.LiferayPortletURL" %><%@
page import="com.liferay.portal.kernel.portlet.PortletURLUtil" %><%@
page import="com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder" %><%@
page import="com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil" %><%@
page import="com.liferay.portal.kernel.repository.model.FileEntry" %><%@
page import="com.liferay.portal.kernel.util.FastDateFormatFactoryUtil" %><%@
page import="com.liferay.portal.kernel.util.GetterUtil" %><%@
page import="com.liferay.portal.kernel.util.KeyValuePair" %><%@
page import="com.liferay.portal.kernel.util.ParamUtil" %><%@
page import="com.liferay.portal.kernel.util.PortalUtil" %><%@
page import="com.liferay.portal.kernel.util.StringUtil" %><%@
page import="com.liferay.portal.kernel.util.Validator" %><%@
page import="com.liferay.portal.kernel.util.WebKeys" %><%@
page import="com.liferay.site.navigation.taglib.servlet.taglib.util.BreadcrumbEntriesUtil" %><%@
page import="com.liferay.taglib.aui.AUIUtil" %><%@
page import="com.liferay.taglib.search.ResultRow" %><%@
page import="com.liferay.user.associated.data.display.UADDisplay" %><%@
page import="com.liferay.user.associated.data.web.internal.constants.UADConstants" %><%@
page import="com.liferay.user.associated.data.web.internal.constants.UADWebKeys" %><%@
page import="com.liferay.user.associated.data.web.internal.dao.search.UADExportProcessResultRowSplitter" %><%@
page import="com.liferay.user.associated.data.web.internal.display.ScopeDisplay" %><%@
page import="com.liferay.user.associated.data.web.internal.display.UADApplicationExportDisplay" %><%@
page import="com.liferay.user.associated.data.web.internal.display.UADApplicationSummaryDisplay" %><%@
page import="com.liferay.user.associated.data.web.internal.display.UADEntity" %><%@
page import="com.liferay.user.associated.data.web.internal.display.UADHierarchyDisplay" %><%@
page import="com.liferay.user.associated.data.web.internal.display.UADInfoPanelDisplay" %><%@
page import="com.liferay.user.associated.data.web.internal.display.ViewUADEntitiesDisplay" %><%@
page import="com.liferay.user.associated.data.web.internal.display.context.UADExportProcessDisplayContext" %><%@
page import="com.liferay.user.associated.data.web.internal.display.context.UADExportProcessManagementToolbarDisplayContext" %><%@
page import="com.liferay.user.associated.data.web.internal.display.context.ViewUADEntitiesManagementToolbarDisplayContext" %><%@
page import="com.liferay.user.associated.data.web.internal.search.UADApplicationExportDisplayChecker" %><%@
page import="com.liferay.user.associated.data.web.internal.util.SafeDisplayValueUtil" %><%@
page import="com.liferay.user.associated.data.web.internal.util.UADExportProcessUtil" %><%@
page import="com.liferay.user.associated.data.web.internal.util.UADLanguageUtil" %><%@
page import="com.liferay.users.admin.constants.UsersAdminPortletKeys" %>

<%@ page import="java.io.Serializable" %>

<%@ page import="java.text.Format" %>

<%@ page import="java.util.Collections" %><%@
page import="java.util.Date" %><%@
page import="java.util.List" %><%@
page import="java.util.Map" %><%@
page import="java.util.Objects" %><%@
page import="java.util.TreeMap" %>

<%@ page import="javax.portlet.ActionRequest" %><%@
page import="javax.portlet.PortletException" %><%@
page import="javax.portlet.PortletRequest" %><%@
page import="javax.portlet.PortletURL" %>

<liferay-frontend:defineObjects />

<liferay-theme:defineObjects />

<portlet:defineObjects />

<%
User selectedUser = PortalUtil.getSelectedUser(request);
%>