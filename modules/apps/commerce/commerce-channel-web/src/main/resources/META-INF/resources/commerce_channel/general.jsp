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
CommerceChannelDisplayContext commerceChannelDisplayContext = (CommerceChannelDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceChannel commerceChannel = commerceChannelDisplayContext.getCommerceChannel();
long commerceChannelId = commerceChannelDisplayContext.getCommerceChannelId();
List<CommerceCurrency> commerceCurrencies = commerceChannelDisplayContext.getCommerceCurrencies();

String commerceCurrencyCode = commerceChannel.getCommerceCurrencyCode();

Map<String, String> contextParams = HashMapBuilder.<String, String>put(
	"commerceChannelId", String.valueOf(commerceChannel.getCommerceChannelId())
).build();
%>

<liferay-ui:error embed="<%= false %>" exception="<%= AccountEntryStatusException.class %>" message="please-select-a-valid-supplier" />
<liferay-ui:error embed="<%= false %>" exception="<%= AccountEntryTypeException.class %>" message="please-select-a-valid-supplier" />
<liferay-ui:error embed="<%= false %>" exception="<%= DuplicateCommerceChannelAccountEntryIdException.class %>" message="a-supplier-account-can-be-linked-only-to-one-channel" />
<liferay-ui:error embed="<%= false %>" exception="<%= FileExtensionException.class %>" message="please-select-a-valid-jrxml-file" />
<liferay-ui:error embed="<%= false %>" exception="<%= InvalidFileException.class %>" message="please-select-a-valid-jrxml-file" />

<portlet:actionURL name="/commerce_channels/edit_commerce_channel" var="editCommerceChannelActionURL" />

<aui:form action="<%= editCommerceChannelActionURL %>" cssClass="m-0 p-0" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (commerceChannel == null) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="commerceChannelId" type="hidden" value="<%= commerceChannelId %>" />

	<aui:model-context bean="<%= commerceChannel %>" model="<%= CommerceChannel.class %>" />

	<div class="row">
		<div class="col-lg-6 d-flex">
			<commerce-ui:panel
				bodyClasses="flex-fill"
				elementClasses="flex-fill"
				title='<%= LanguageUtil.get(request, "details") %>'
			>
				<aui:input name="name" />

				<aui:select label="currency" name="commerceCurrencyCode" required="<%= true %>" title="currency">

					<%
					for (CommerceCurrency commerceCurrency : commerceCurrencies) {
					%>

						<aui:option label="<%= HtmlUtil.escape(commerceCurrency.getName(locale)) %>" selected="<%= (commerceChannel == null) ? commerceCurrency.isPrimary() : commerceCurrencyCode.equals(commerceCurrency.getCode()) %>" value="<%= HtmlUtil.escape(commerceCurrency.getCode()) %>" />

					<%
					}
					%>

				</aui:select>

				<aui:select label="commerce-site-type" name="settings--commerceSiteType--">

					<%
					for (int commerceSiteType : CommerceChannelConstants.SITE_TYPES) {
					%>

						<aui:option label="<%= CommerceChannelConstants.getSiteTypeLabel(commerceSiteType) %>" selected="<%= commerceSiteType == commerceChannelDisplayContext.getCommerceSiteType() %>" value="<%= commerceSiteType %>" />

					<%
					}
					%>

				</aui:select>

				<c:if test='<%= FeatureFlagManagerUtil.isEnabled("COMMERCE-10890") %>'>
					<aui:select disabled="<%= !commerceChannelDisplayContext.hasManageLinkSupplierPermission() %>" label="link-channel-to-a-supplier" name="accountEntryId" showEmptyOption="<%= true %>">

						<%
						for (AccountEntry accountEntry : commerceChannelDisplayContext.getSupplierAccountEntries()) {
						%>

							<aui:option label="<%= accountEntry.getName() %>" selected="<%= (commerceChannel != null) && (accountEntry.getAccountEntryId() == commerceChannel.getAccountEntryId()) %>" value="<%= accountEntry.getAccountEntryId() %>" />

						<%
						}
						%>

					</aui:select>
				</c:if>
			</commerce-ui:panel>
		</div>

		<div class="col-lg-6 d-flex">
			<commerce-ui:panel
				bodyClasses="flex-fill"
				elementClasses="flex-fill"
				title='<%= LanguageUtil.get(request, "prices") %>'
			>
				<label class="control-label" for="shippingTaxSettings--taxCategoryId--"><liferay-ui:message key="shipping-tax-category" /></label>

				<div class="mb-4" id="autocomplete-root"></div>

				<aui:select label="price-type" name="priceDisplayType">

					<%
					String priceDisplayType = commerceChannel.getPriceDisplayType();
					%>

					<aui:option label="net-price" selected="<%= priceDisplayType.equals(CommercePricingConstants.TAX_EXCLUDED_FROM_PRICE) %>" value="<%= CommercePricingConstants.TAX_EXCLUDED_FROM_PRICE %>" />
					<aui:option label="gross-price" selected="<%= priceDisplayType.equals(CommercePricingConstants.TAX_INCLUDED_IN_PRICE) %>" value="<%= CommercePricingConstants.TAX_INCLUDED_IN_PRICE %>" />
				</aui:select>

				<aui:select label="discounts-target-price-type" name="discountsTargetNetPrice">
					<aui:option label="net-price" selected="<%= commerceChannel.isDiscountsTargetNetPrice() %>" value="true" />
					<aui:option label="gross-price" selected="<%= !commerceChannel.isDiscountsTargetNetPrice() %>" value="false" />
				</aui:select>
			</commerce-ui:panel>
		</div>

		<div class="col-lg-12">
			<commerce-ui:panel
				bodyClasses="flex-fill"
				title='<%= LanguageUtil.get(request, "orders") %>'
			>
				<div class="row">
					<div class="col-lg-6">

						<%
						List<WorkflowDefinition> workflowDefinitions = commerceChannelDisplayContext.getActiveWorkflowDefinitions();

						long typePK = CommerceOrderConstants.TYPE_PK_APPROVAL;
						String typePrefix = "buyer-order-approval";
						%>

						<%@ include file="/commerce_channel/workflow_definition.jspf" %>
					</div>

					<div class="col-lg-6">

						<%
						typePK = CommerceOrderConstants.TYPE_PK_FULFILLMENT;
						typePrefix = "seller-order-acceptance";
						%>

						<%@ include file="/commerce_channel/workflow_definition.jspf" %>
					</div>
				</div>

				<div class="row">
					<div class="col-lg-6">
						<aui:input checked="<%= commerceChannelDisplayContext.isHideShippingPriceZero() %>" helpMessage="configures-whether-an-shipping-price-of-zero-is-shown-during-the-shipping-method-selection-checkout-screen" label="shipping-price-zero" labelOff="show" labelOn="hide" name="settings--hideShippingPriceZero--" type="toggle-switch" />
					</div>

					<div class="col-lg-6">
						<aui:input checked="<%= commerceChannelDisplayContext.isShowPurchaseOrderNumber() %>" helpMessage="configures-whether-the-purchase-order-number-is-shown-or-hidden-in-placed-and-pending-order-details" label="purchase-order-number" labelOff="hide" labelOn="show" name="settings--showPurchaseOrderNumber--" type="toggle-switch" />
					</div>

					<div class="col-lg-6">
						<aui:input checked="<%= commerceChannelDisplayContext.isCheckoutRequestedDeliveryDateEnabled() %>" helpMessage="configures-whether-an-order-requested-delivery-date-can-be-set-during-checkout" label="requested-delivery-date-at-checkout" labelOff="disabled" labelOn="enabled" name="settings--checkoutRequestedDeliveryDateEnabled--" type="toggle-switch" />
					</div>

					<div class="col-lg-6">
						<aui:input checked="<%= commerceChannelDisplayContext.isGuestCheckoutEnabled() %>" helpMessage="configures-whether-a-guest-may-checkout-by-providing-an-email-address-or-if-they-must-sign-in" label="guest-checkout" labelOff="disabled" labelOn="enabled" name="settings--guestCheckoutEnabled--" type="toggle-switch" />
					</div>

					<c:if test='<%= FeatureFlagManagerUtil.isEnabled("COMMERCE-11028") %>'>
						<div class="col-lg-6">
							<aui:input checked="<%= commerceChannelDisplayContext.isRequestQuoteEnabled() %>" helpMessage="allow-buyers-to-request-a-quote-when-no-product-in-the-cart-is-priced-as-price-on-application" label="allow-request-a-quote-on-a-fully-priced-cart" labelOff="disabled" labelOn="enabled" name="orderSettings--requestQuoteEnabled--" type="toggle-switch" />
						</div>
					</c:if>
				</div>

				<div class="row">
					<div class="col-lg-6">
						<aui:input label="maximum-number-of-open-orders-per-account" name="orderSettings--accountCartMaxAllowed--" type="number" value="<%= commerceChannelDisplayContext.getAccountCartMaxAllowed() %>">
							<aui:validator name="number" />
							<aui:validator name="min">0</aui:validator>
						</aui:input>
					</div>

					<div class="col-lg-6">
						<aui:input label="order-importer-date-format" labelOff="disabled" labelOn="enabled" name="format--orderImporterDateFormat--" type="text" value="<%= commerceChannelDisplayContext.getOrderImporterDateFormat() %>" />
					</div>

					<div class="col-lg-6">

						<%
						FileEntry fileEntry = commerceChannelDisplayContext.fetchFileEntry();
						%>

						<aui:model-context bean="<%= fileEntry %>" model="<%= FileEntry.class %>" />

						<portlet:actionURL name="/commerce_channels/upload_jrxml_template" var="uploadJRXMLTemplateURL" />

						<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.IMPORT %>" />
						<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
						<aui:input name="fileEntryId" type="hidden" />

						<label><liferay-ui:message key="print-order-template" /></label>

						<div class="align-items-center d-flex">
							<aui:button name="selectFileButton" value="select-file" />

							<p class="mb-0 ml-3 text-3">
								<span id="<portlet:namespace />fileEntryNameInput"><a><%= (fileEntry != null) ? fileEntry.getFileName() : "" %></a></span>
								<span class="<%= (fileEntry != null) ? "" : "hide" %>" id="<portlet:namespace />fileEntryRemoveIcon" role="button">
									<aui:icon cssClass="icon-monospaced" image="times-circle-full" markupView="lexicon" />
								</span>
							</p>
						</div>
					</div>
				</div>
			</commerce-ui:panel>
		</div>
	</div>
</aui:form>

<c:if test="<%= (commerceChannel.getSiteGroupId() > 0) && commerceChannelDisplayContext.hasAddLayoutPermission() && commerceChannelDisplayContext.hasUnsatisfiedCommerceHealthChecks() %>">
	<commerce-ui:panel
		bodyClasses="p-0"
		title='<%= LanguageUtil.get(request, "health-checks") %>'
	>
		<frontend-data-set:classic-display
			contextParams="<%= contextParams %>"
			dataProviderKey="<%= CommerceChannelFDSNames.CHANNEL_HEALTH_CHECK %>"
			id="<%= CommerceChannelFDSNames.CHANNEL_HEALTH_CHECK %>"
			itemsPerPage="<%= 10 %>"
			showManagementBar="<%= false %>"
		/>
	</commerce-ui:panel>
</c:if>

<commerce-ui:panel
	bodyClasses="p-0"
	title='<%= LanguageUtil.get(request, "payment-methods") %>'
>
	<frontend-data-set:classic-display
		contextParams="<%= contextParams %>"
		dataProviderKey="<%= CommerceChannelFDSNames.PAYMENT_METHOD %>"
		id="<%= CommerceChannelFDSNames.PAYMENT_METHOD %>"
		itemsPerPage="<%= 10 %>"
		selectedItemsKey="key"
		showManagementBar="<%= false %>"
	/>
</commerce-ui:panel>

<commerce-ui:panel
	bodyClasses="p-0"
	title='<%= LanguageUtil.get(request, "shipping-methods") %>'
>
	<frontend-data-set:classic-display
		contextParams="<%= contextParams %>"
		dataProviderKey="<%= CommerceChannelFDSNames.SHIPPING_METHOD %>"
		id="<%= CommerceChannelFDSNames.SHIPPING_METHOD %>"
		itemsPerPage="<%= 10 %>"
		selectedItemsKey="key"
		showManagementBar="<%= false %>"
	/>
</commerce-ui:panel>

<commerce-ui:panel
	bodyClasses="p-0"
	title='<%= LanguageUtil.get(request, "tax-calculations") %>'
>
	<frontend-data-set:classic-display
		contextParams="<%= contextParams %>"
		dataProviderKey="<%= CommerceChannelFDSNames.TAX_METHOD %>"
		id="<%= CommerceChannelFDSNames.TAX_METHOD %>"
		itemsPerPage="<%= 10 %>"
		selectedItemsKey="key"
		showManagementBar="<%= false %>"
	/>
</commerce-ui:panel>

<%
String shippingTaxCategoryId = StringPool.BLANK;
String shippingTaxCategoryLabel = LanguageUtil.get(request, "no-tax-category");

CPTaxCategory shippingTaxCategory = commerceChannelDisplayContext.getActiveShippingTaxCategory();

if (shippingTaxCategory != null) {
	shippingTaxCategoryId = String.valueOf(shippingTaxCategory.getCPTaxCategoryId());
	shippingTaxCategoryLabel = shippingTaxCategory.getName(locale);
}
%>

<aui:script require="commerce-frontend-js/components/autocomplete/entry as autocomplete, commerce-frontend-js/utilities/eventsDefinitions as events">
	autocomplete.default('autocomplete', 'autocomplete-root', {
		apiUrl: '/o/headless-commerce-admin-channel/v1.0/tax-categories',
		initialLabel: '<%= HtmlUtil.escapeJS(shippingTaxCategoryLabel) %>',
		initialValue: '<%= shippingTaxCategoryId %>',
		inputId: 'shippingTaxCategoryId',
		inputName:
			'<%= liferayPortletResponse.getNamespace() %>shippingTaxSettings--taxCategoryId--',
		itemsKey: 'id',
		itemsLabel: ['name', 'LANG'],
		onValueUpdated: function (value, shippingTaxData) {
			if (value) {
				window.document.querySelector('#shippingTaxCategoryId').value =
					shippingTaxData.id;
			}
			else {
				window.document.querySelector('#shippingTaxCategoryId').value = 0;
			}
		},
	});
</aui:script>

<liferay-frontend:component
	context='<%=
		HashMapBuilder.<String, Object>put(
			"itemSelectorURL", commerceChannelDisplayContext.getImageItemSelectorURL()
		).build()
	%>'
	module="js/CommerceChannelGeneral"
/>