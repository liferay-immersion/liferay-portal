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

String backURL = ParamUtil.getString(request, "backURL", redirect);

if (Validator.isNull(backURL)) {
	backURL = String.valueOf(renderResponse.createRenderURL());
}

ObjectEntryDisplayContext objectEntryDisplayContext = (ObjectEntryDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

ObjectDefinition objectDefinition = objectEntryDisplayContext.getObjectDefinition1();
ObjectEntry objectEntry = objectEntryDisplayContext.getObjectEntry();

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(backURL);
%>

<portlet:actionURL name="/object_entries/edit_object_entry" var="editObjectEntryURL" />

<liferay-frontend:edit-form
	action="<%= editObjectEntryURL %>"
	name="fm"
>
	<liferay-frontend:edit-form-body>
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (objectEntry == null) ? Constants.ADD : Constants.UPDATE %>" />
		<aui:input name="externalReferenceCode" type="hidden" value='<%= (objectEntry == null) ? "" : objectEntry.getExternalReferenceCode() %>' />
		<aui:input name="objectDefinitionId" type="hidden" value="<%= objectDefinition.getObjectDefinitionId() %>" />
		<aui:input name="ddmFormValues" type="hidden" value="" />

		<clay:sheet-section>
			<clay:row>
				<clay:col
					md="12"
				>
					<%= objectEntryDisplayContext.renderDDMForm(pageContext) %>
				</clay:col>
			</clay:row>
		</clay:sheet-section>

		<%@ include file="/object_entries/object_entry/categorization.jspf" %>
	</liferay-frontend:edit-form-body>

	<c:if test="<%= !objectEntryDisplayContext.isReadOnly() %>">
		<liferay-frontend:edit-form-footer>
			<liferay-frontend:edit-form-buttons
				redirect="<%= backURL %>"
				submitOnClick='<%= "event.preventDefault(); " + liferayPortletResponse.getNamespace() + "submitObjectEntry();" %>'
			/>
		</liferay-frontend:edit-form-footer>
	</c:if>
</liferay-frontend:edit-form>

<c:if test="<%= !objectEntryDisplayContext.isReadOnly() %>">
	<aui:script>
		function <portlet:namespace />getExternalReferenceCode() {
			return String(
				'<%= (objectEntry == null) ? "" : objectEntry.getExternalReferenceCode() %>'
			);
		}

		function <portlet:namespace />getInputValues(element, selector) {
			return Array.from(element.querySelectorAll(selector)).map(
				(item) => item.value
			);
		}

		function <portlet:namespace />getPath(externalReferenceCode) {
			const scope = '<%= objectDefinition.getScope() %>';
			const contextPath = '/o<%= objectDefinition.getRESTContextPath() %>';
			const pathScopedBySite = contextPath.concat(
				`/scopes/\${themeDisplay.getSiteGroupId()}`
			);

			const postPath = scope === 'site' ? pathScopedBySite : contextPath;

			let patchPath = scope === 'site' ? pathScopedBySite : contextPath;

			patchPath = patchPath.concat(
				'/by-external-reference-code/',
				`\${externalReferenceCode}`
			);

			return externalReferenceCode ? patchPath : postPath;
		}

		function <portlet:namespace />getValues(fields) {
			return fields.reduce((obj, field) => {
				let value = field.value;
				if (field.type === 'select' && !field.multiple) {
					value = {key: value.length ? field.value[0] : ''};
				}

				let fieldName = field.fieldName;

				if (field.localizable) {
					fieldName += '_i18n';

					if (typeof value == 'string') {
						value = JSON.parse(value);
					}
				}

				return Object.assign(obj, {[fieldName]: value});
			}, {});
		}

		Liferay.provide(
			window,
			'<portlet:namespace />submitObjectEntry',
			() => {
				const form = document.getElementById('<portlet:namespace />fm');

				const DDMFormInstance = Liferay.component('editObjectEntry');

				const current = DDMFormInstance.reactComponentRef.current;

				current.validate().then((result) => {
					if (result) {
						const fields = current.getFields();
						let shouldSubmitForm = true;

						fields.forEach((field) => {
							if (
								field.displayStyle === 'singleline' &&
								field.type === 'text' &&
								field.value.length > 280
							) {
								shouldSubmitForm = false;

								Liferay.Util.openToast({
									message: Liferay.Util.sub(
										'<liferay-ui:message key="the-entry-value-exceeds-the-maximum-length-of-x-characters-for-object-field-x" />',
										'280',
										'"' + field.fieldName + '"'
									),
									type: 'danger',
								});

								return false;
							}
						});

						if (shouldSubmitForm) {
							let values = <portlet:namespace />getValues(fields);
							const categoriesContent = document.getElementById(
								'<portlet:namespace />categorization'
							);
							const externalReferenceCode = <portlet:namespace />getExternalReferenceCode();
							const path = <portlet:namespace />getPath(
								externalReferenceCode
							);

							if (categoriesContent) {
								values = Object.assign(
									values,
									{
										['categoryIds']: <portlet:namespace />getInputValues(
											categoriesContent,
											'input[name^="<portlet:namespace />assetCategoryIds"]'
										),
									},
									{
										['tagNames']: <portlet:namespace />getInputValues(
											categoriesContent,
											'input[name^="<portlet:namespace />assetTagNames"]'
										),
									}
								);
							}

							const autoRelatedValue = {
								['relationshipField']:
									'<%= objectEntryDisplayContext.getObjectRelationshipERCObjectFieldName() %>',
								['parentObjectEntryERC']:
									'<%= objectEntryDisplayContext.getParentObjectEntryId() %>',
							};

							if (autoRelatedValue['relationshipField'] !== 'null') {
								values = Object.assign(values, {
									[autoRelatedValue['relationshipField']]:
										autoRelatedValue['parentObjectEntryERC'],
								});
							}

							Liferay.Util.fetch(path, {
								body: JSON.stringify(values),
								headers: new Headers({
									'Accept': 'application/json',
									'Content-Type': 'application/json',
								}),
								method: externalReferenceCode ? 'PATCH' : 'POST',
							})
								.then((response) => {
									if (response.status === 401) {
										window.location.reload();
									}
									else if (response.ok) {
										Liferay.Util.openToast({
											message:
												'<%= HtmlUtil.escapeJS(LanguageUtil.get(request, "your-request-completed-successfully")) %>',
											type: 'success',
										});

										response.json().then((payload) => {
											var portletURL = new Liferay.PortletURL.createURL(
												'<%= currentURLObj %>'
											);

											portletURL.setParameter(
												'externalReferenceCode',
												payload.externalReferenceCode
											);

											Liferay.Util.navigate(
												portletURL.toString()
											);
										});
									}
									else {
										return response.json();
									}
								})
								.then((response) => {
									if (response && response.title) {
										Liferay.Util.openToast({
											message: response.title,
											type: 'danger',
										});
									}
								});
						}
					}
				});
			},
			['liferay-portlet-url']
		);
	</aui:script>
</c:if>