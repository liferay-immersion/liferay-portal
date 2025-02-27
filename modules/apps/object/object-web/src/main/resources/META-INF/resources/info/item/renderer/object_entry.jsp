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

<%@ include file="/info/item/renderer/init.jsp" %>

<%
ObjectEntry objectEntry = (ObjectEntry)request.getAttribute(ObjectWebKeys.OBJECT_ENTRY);

String friendlyURL = assetDisplayPageFriendlyURLProvider.getFriendlyURL(new InfoItemReference(objectEntry.getModelClassName(), new ClassPKInfoItemIdentifier(objectEntry.getObjectEntryId())), themeDisplay);

Map<String, Serializable> objectEntryValues = (Map<String, Serializable>)request.getAttribute(ObjectWebKeys.OBJECT_ENTRY_VALUES);

for (Map.Entry<String, Serializable> entry : objectEntryValues.entrySet()) {
%>

	<td class="table-cell-expand-smallest">
		<c:choose>
			<c:when test="<%= entry.getValue() instanceof Link %>">

				<%
				Link fileEntryLink = (Link)entry.getValue();
				%>

				<a class="text-truncate-inline" href="<%= fileEntryLink.getHref() %>">
					<span class="text-truncate"><%= fileEntryLink.getLabel() %></span>
				</a>
			</c:when>
			<c:otherwise>
				<c:choose>
					<c:when test="<%= Validator.isNotNull(friendlyURL) %>">
						<a class="text-truncate-inline" href="<%= friendlyURL %>">
							<span class="text-truncate"><%= entry.getValue() %></span>
						</a>
					</c:when>
					<c:otherwise>
						<span class="text-truncate-inline">
							<span class="text-truncate"><%= entry.getValue() %></span>
						</span>
					</c:otherwise>
				</c:choose>
			</c:otherwise>
		</c:choose>
	</td>

<%
}
%>