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

<liferay-ui:error-header />

<liferay-ui:error exception="<%= CommerceOrderImporterTypeException.class %>">

	<%
	String commerceOrderImporterTypeKey = (String)SessionErrors.get(renderRequest, CommerceOrderImporterTypeException.class);
	%>

	<c:choose>
		<c:when test="<%= Validator.isNull(commerceOrderImporterTypeKey) %>">
			<liferay-ui:message key="the-import-process-failed" />
		</c:when>
		<c:otherwise>
			<liferay-ui:message arguments="<%= commerceOrderImporterTypeKey %>" key="the-x-could-not-be-imported" />
		</c:otherwise>
	</c:choose>
</liferay-ui:error>

<liferay-ui:error exception="<%= NoSuchEntryException.class %>" message="to-add-a-product-to-an-order,-first-select-an-account" />
<liferay-ui:error exception="<%= NoSuchOrderException.class %>" message="the-order-could-not-be-found" />
<liferay-ui:error exception="<%= NoSuchOrderNoteException.class %>" message="the-note-could-not-be-found" />

<liferay-ui:error-principal />