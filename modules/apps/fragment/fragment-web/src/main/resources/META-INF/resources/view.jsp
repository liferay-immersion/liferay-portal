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
List<FragmentCollection> fragmentCollections = (List<FragmentCollection>)request.getAttribute(FragmentWebKeys.FRAGMENT_COLLECTIONS);
Map<String, List<FragmentCollection>> inheritedFragmentCollections = (Map<String, List<FragmentCollection>>)request.getAttribute(FragmentWebKeys.INHERITED_FRAGMENT_COLLECTIONS);
List<FragmentCollection> systemFragmentCollections = (List<FragmentCollection>)request.getAttribute(FragmentWebKeys.SYSTEM_FRAGMENT_COLLECTIONS);

List<FragmentCollectionContributor> fragmentCollectionContributors = fragmentEntriesDisplayContext.getFragmentCollectionContributors(locale);
%>

<liferay-ui:error embed="<%= false %>" exception="<%= DuplicateFragmentCollectionKeyException.class %>">

	<%
	DuplicateFragmentCollectionKeyException dfcke = (DuplicateFragmentCollectionKeyException)errorException;
	%>

	<liferay-ui:message arguments='<%= "<em>" + dfcke.getMessage() + "</em>" %>' key="a-fragment-set-with-the-key-x-already-exists" />
</liferay-ui:error>

<liferay-ui:error embed="<%= false %>" exception="<%= DuplicateFragmentEntryKeyException.class %>">

	<%
	DuplicateFragmentEntryKeyException dfeke = (DuplicateFragmentEntryKeyException)errorException;
	%>

	<liferay-ui:message arguments='<%= "<em>" + dfeke.getMessage() + "</em>" %>' key="a-fragment-entry-with-the-key-x-already-exists" />
</liferay-ui:error>

<liferay-ui:error embed="<%= false %>" exception="<%= InvalidFileException.class %>" message="the-selected-file-is-not-a-valid-zip-file" />

<liferay-ui:success key="fragmentEntryCopied" message="the-fragment-was-copied-successfully" />

<clay:container-fluid
	cssClass="container-view"
>
	<clay:row>
		<clay:col
			lg="3"
		>
			<nav class="menubar menubar-transparent menubar-vertical-expand-lg">
				<ul class="nav nav-nested">
					<li class="nav-item">
						<portlet:renderURL var="editFragmentCollectionURL">
							<portlet:param name="mvcRenderCommandName" value="/fragment/edit_fragment_collection" />
							<portlet:param name="redirect" value="<%= currentURL %>" />
						</portlet:renderURL>

						<c:choose>
							<c:when test="<%= ListUtil.isNotEmpty(fragmentCollections) || ListUtil.isNotEmpty(fragmentCollectionContributors) || MapUtil.isNotEmpty(inheritedFragmentCollections) %>">
								<clay:content-row
									cssClass="mb-4"
									verticalAlign="center"
								>
									<clay:content-col
										expand="<%= true %>"
									>
										<strong class="text-uppercase">
											<liferay-ui:message key="fragment-sets" />
										</strong>
									</clay:content-col>

									<clay:content-col>
										<ul class="navbar-nav">
											<li>
												<c:if test="<%= FragmentPermission.contains(permissionChecker, scopeGroupId, FragmentActionKeys.MANAGE_FRAGMENT_ENTRIES) %>">
													<clay:link
														borderless="<%= true %>"
														cssClass="component-action"
														href="<%= editFragmentCollectionURL %>"
														icon="plus"
														type="button"
													/>
												</c:if>
											</li>
											<li>

												<%
												Map<String, Object> fragmentCollectionsViewContext = fragmentEntriesDisplayContext.getFragmentCollectionsViewContext();
												%>

												<clay:dropdown-actions
													additionalProps='<%=
														HashMapBuilder.<String, Object>put(
															"deleteFragmentCollectionURL", fragmentCollectionsViewContext.get("deleteFragmentCollectionURL")
														).put(
															"exportFragmentCollectionsURL", fragmentCollectionsViewContext.get("exportFragmentCollectionsURL")
														).put(
															"importURL", fragmentCollectionsViewContext.get("importURL")
														).put(
															"viewDeleteFragmentCollectionsURL", fragmentCollectionsViewContext.get("viewDeleteFragmentCollectionsURL")
														).put(
															"viewExportFragmentCollectionsURL", fragmentCollectionsViewContext.get("viewExportFragmentCollectionsURL")
														).put(
															"viewImportURL", fragmentCollectionsViewContext.get("viewImportURL")
														).build()
													%>'
													aria-label='<%= LanguageUtil.get(request, "show-actions") %>'
													dropdownItems="<%= fragmentEntriesDisplayContext.getCollectionsDropdownItems() %>"
													propsTransformer="js/FragmentCollectionViewDefaultPropsTransformer"
												/>
											</li>
										</ul>
									</clay:content-col>
								</clay:content-row>

								<ul class="mb-2 nav nav-stacked">
									<c:if test="<%= ListUtil.isNotEmpty(fragmentCollectionContributors) || ListUtil.isNotEmpty(systemFragmentCollections) %>">
										<span class="text-truncate">
											<liferay-ui:message key="default" />
										</span>

										<%
										for (FragmentCollectionContributor fragmentCollectionContributor : fragmentCollectionContributors) {
										%>

											<li class="nav-item">
												<a
													class="d-flex nav-link <%= Objects.equals(fragmentCollectionContributor.getFragmentCollectionKey(), fragmentEntriesDisplayContext.getFragmentCollectionKey()) ? "active" : StringPool.BLANK %>"
													href="<%=
														PortletURLBuilder.createRenderURL(
															renderResponse
														).setParameter(
															"fragmentCollectionKey", fragmentCollectionContributor.getFragmentCollectionKey()
														).buildString()
													%>"
												>
													<span class="text-truncate"><%= HtmlUtil.escape(fragmentCollectionContributor.getName(locale)) %></span>

													<span class="ml-1 text-muted">
														<clay:icon
															symbol="lock"
														/>
													</span>
												</a>
											</li>

										<%
										}

										for (FragmentCollection fragmentCollection : systemFragmentCollections) {
										%>

											<li class="nav-item">
												<a
													class="d-flex nav-link <%= (fragmentCollection.getFragmentCollectionId() == fragmentEntriesDisplayContext.getFragmentCollectionId()) ? "active" : StringPool.BLANK %>"
													href="<%=
														PortletURLBuilder.createRenderURL(
															renderResponse
														).setParameter(
															"fragmentCollectionId", fragmentCollection.getFragmentCollectionId()
														).buildString()
													%>"
												>
													<span class="text-truncate"><%= HtmlUtil.escape(fragmentCollection.getName()) %></span>

													<c:if test="<%= fragmentEntriesDisplayContext.isLocked(fragmentCollection) %>">
														<span class="ml-1 text-muted">
															<clay:icon
																symbol="lock"
															/>
														</span>
													</c:if>
												</a>
											</li>

										<%
										}
										%>

									</c:if>
								</ul>

								<ul class="mb-2 nav nav-stacked">

									<%
									for (Map.Entry<String, List<FragmentCollection>> entry : inheritedFragmentCollections.entrySet()) {
									%>

										<span class="text-truncate"><%= entry.getKey() %></span>

										<%
										for (FragmentCollection fragmentCollection : entry.getValue()) {
										%>

											<li class="nav-item">
												<a
													class="d-flex nav-link <%= (fragmentCollection.getFragmentCollectionId() == fragmentEntriesDisplayContext.getFragmentCollectionId()) ? "active" : StringPool.BLANK %>"
													href="<%=
														PortletURLBuilder.createRenderURL(
															renderResponse
														).setParameter(
															"fragmentCollectionId", fragmentCollection.getFragmentCollectionId()
														).buildString()
													%>"
												>
													<span class="text-truncate"><%= HtmlUtil.escape(fragmentCollection.getName()) %></span>

													<span class="ml-1 text-muted">
														<clay:icon
															symbol="lock"
														/>
													</span>
												</a>
											</li>

									<%
										}
									}
									%>

								</ul>

								<ul class="mb-2 nav nav-stacked">
									<c:if test="<%= ListUtil.isNotEmpty(fragmentCollections) %>">
										<span class="text-truncate"><%= HtmlUtil.escape(fragmentEntriesDisplayContext.getGroupName(scopeGroupId)) %></span>

										<%
										for (FragmentCollection fragmentCollection : fragmentCollections) {
										%>

											<li class="nav-item">
												<a
													class="d-flex nav-link <%= (fragmentCollection.getFragmentCollectionId() == fragmentEntriesDisplayContext.getFragmentCollectionId()) ? "active" : StringPool.BLANK %>"
													href="<%=
														PortletURLBuilder.createRenderURL(
															renderResponse
														).setParameter(
															"fragmentCollectionId", fragmentCollection.getFragmentCollectionId()
														).buildString()
													%>"
												>
													<span class="text-truncate"><%= HtmlUtil.escape(fragmentCollection.getName()) %></span>

													<c:if test="<%= fragmentEntriesDisplayContext.isLocked(fragmentCollection) %>">
														<span class="ml-1 text-muted">
															<clay:icon
																symbol="lock"
															/>
														</span>
													</c:if>
												</a>
											</li>

										<%
										}
										%>

									</c:if>
								</ul>
							</c:when>
							<c:otherwise>
								<p class="text-uppercase">
									<strong><liferay-ui:message key="fragment-sets" /></strong>
								</p>

								<liferay-frontend:empty-result-message
									actionDropdownItems="<%= FragmentPermission.contains(permissionChecker, scopeGroupId, FragmentActionKeys.MANAGE_FRAGMENT_ENTRIES) ? fragmentEntriesDisplayContext.getActionDropdownItems() : null %>"
									additionalProps="<%= fragmentEntriesDisplayContext.getFragmentCollectionsViewContext() %>"
									animationType="<%= EmptyResultMessageKeys.AnimationType.NONE %>"
									buttonPropsTransformer="js/FragmentCollectionViewButtonPropsTransformer"
									description='<%= LanguageUtil.get(request, "fragment-sets-are-needed-to-create-fragments") %>'
									elementType='<%= LanguageUtil.get(request, "fragment-sets") %>'
									propsTransformer="js/FragmentCollectionViewDefaultPropsTransformer"
									propsTransformerServletContext="<%= application %>"
								/>
							</c:otherwise>
						</c:choose>
					</li>
				</ul>
			</nav>
		</clay:col>

		<clay:col
			lg="9"
		>
			<c:if test="<%= (fragmentEntriesDisplayContext.getFragmentCollection() != null) || (fragmentEntriesDisplayContext.getFragmentCollectionContributor() != null) %>">
				<clay:sheet
					size="full"
				>
					<h2 class="sheet-title">
						<clay:content-row
							verticalAlign="center"
						>
							<clay:content-col>
								<%= fragmentEntriesDisplayContext.getFragmentCollectionName() %>
							</clay:content-col>

							<c:if test="<%= fragmentEntriesDisplayContext.showFragmentCollectionActions() %>">
								<clay:content-col
									cssClass="inline-item-after"
								>

									<%
									FragmentCollectionActionDropdownItemsProvider fragmentCollectionActionDropdownItemsProvider = new FragmentCollectionActionDropdownItemsProvider(fragmentEntriesDisplayContext, request, renderResponse);
									%>

									<clay:dropdown-actions
										aria-label='<%= LanguageUtil.get(request, "show-actions") %>'
										dropdownItems="<%= fragmentCollectionActionDropdownItemsProvider.getActionDropdownItems() %>"
										propsTransformer="js/FragmentCollectionDropdownPropsTransformer"
									/>
								</clay:content-col>
							</c:if>
						</clay:content-row>
					</h2>

					<clay:sheet-section>
						<c:if test="<%= !ListUtil.isEmpty(fragmentEntriesDisplayContext.getNavigationItems()) %>">
							<clay:navigation-bar
								navigationItems="<%= fragmentEntriesDisplayContext.getNavigationItems() %>"
							/>
						</c:if>

						<c:choose>
							<c:when test="<%= fragmentEntriesDisplayContext.isSelectedFragmentCollectionContributor() %>">
								<liferay-util:include page="/view_contributed_fragment_entries.jsp" servletContext="<%= application %>" />
							</c:when>
							<c:otherwise>
								<c:choose>
									<c:when test="<%= fragmentEntriesDisplayContext.isViewResources() %>">
										<liferay-util:include page="/view_resources.jsp" servletContext="<%= application %>" />
									</c:when>
									<c:otherwise>
										<liferay-util:include page="/view_fragment_entries.jsp" servletContext="<%= application %>" />
									</c:otherwise>
								</c:choose>
							</c:otherwise>
						</c:choose>
					</clay:sheet-section>
				</clay:sheet>
			</c:if>
		</clay:col>
	</clay:row>
</clay:container-fluid>

<aui:form cssClass="hide" name="fm">
</aui:form>

<%
ImportDisplayContext importDisplayContext = new ImportDisplayContext(request, renderRequest);

List<String> draftFragmentsImporterResultEntries = importDisplayContext.getFragmentsImporterResultEntries(FragmentsImporterResultEntry.Status.IMPORTED_DRAFT);
%>

<aui:script>
	<c:if test="<%= ListUtil.isNotEmpty(draftFragmentsImporterResultEntries) %>">
		Liferay.Util.openToast({
			message:
				'<liferay-ui:message arguments='<%= "<strong>" + StringUtil.merge(draftFragmentsImporterResultEntries, StringPool.COMMA_AND_SPACE) + "</strong>" %>' key="the-following-fragments-have-validation-issues.-they-have-been-left-in-draft-status-x" />',
			title: '<liferay-ui:message key="warning" />:',
			type: 'warning',
		});
	</c:if>

	<%
	List<String> invalidFragmentsImporterResultEntries = importDisplayContext.getFragmentsImporterResultEntries(FragmentsImporterResultEntry.Status.INVALID);
	%>

	<c:if test="<%= ListUtil.isNotEmpty(invalidFragmentsImporterResultEntries) %>">
		Liferay.Util.openToast({
			message:
				'<liferay-ui:message arguments='<%= "<strong>" + StringUtil.merge(invalidFragmentsImporterResultEntries, StringPool.COMMA_AND_SPACE) + "</strong>" %>' key="the-following-fragments-could-not-be-imported-x" />',
			title: '<liferay-ui:message key="warning" />:',
			type: 'warning',
		});
	</c:if>
</aui:script>