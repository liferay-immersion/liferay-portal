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

package com.liferay.journal.content.web.internal.display.context;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil;
import com.liferay.asset.kernel.service.AssetEntryServiceUtil;
import com.liferay.asset.util.LinkedAssetEntryIdsUtil;
import com.liferay.document.library.kernel.document.conversion.DocumentConversionUtil;
import com.liferay.dynamic.data.mapping.item.selector.DDMTemplateItemSelectorReturnType;
import com.liferay.dynamic.data.mapping.item.selector.criterion.DDMTemplateItemSelectorCriterion;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalServiceUtil;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.criteria.JournalArticleItemSelectorReturnType;
import com.liferay.item.selector.criteria.constants.ItemSelectorCriteriaConstants;
import com.liferay.item.selector.criteria.info.item.criterion.InfoItemItemSelectorCriterion;
import com.liferay.journal.configuration.JournalServiceConfiguration;
import com.liferay.journal.constants.JournalContentPortletKeys;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.constants.JournalWebKeys;
import com.liferay.journal.content.web.internal.configuration.JournalContentPortletInstanceConfiguration;
import com.liferay.journal.content.web.internal.constants.JournalContentWebKeys;
import com.liferay.journal.content.web.internal.security.permission.resource.JournalArticlePermission;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleDisplay;
import com.liferay.journal.model.JournalArticleResource;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.journal.service.JournalArticleResourceLocalServiceUtil;
import com.liferay.journal.util.JournalContent;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.portlet.LiferayRenderRequest;
import com.liferay.portal.kernel.portlet.LiferayRenderResponse;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletRequestModel;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.trash.TrashHandlerRegistryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portlet.LiferayPortletUtil;
import com.liferay.staging.StagingGroupHelper;
import com.liferay.staging.StagingGroupHelperUtil;
import com.liferay.trash.TrashHelper;
import com.liferay.trash.constants.TrashActionKeys;
import com.liferay.trash.model.TrashEntry;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletMode;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;

/**
 * @author Eudaldo Alonso
 */
public class JournalContentDisplayContext {

	public static JournalContentDisplayContext create(
			PortletRequest portletRequest, PortletResponse portletResponse,
			long ddmStructureClassNameId,
			ModelResourcePermission<DDMTemplate>
				ddmTemplateModelResourcePermission,
			ItemSelector itemSelector, TrashHelper trashHelper)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		JournalContentDisplayContext journalContentDisplayContext =
			(JournalContentDisplayContext)portletRequest.getAttribute(
				getRequestAttributeName(portletDisplay.getId()));

		if (journalContentDisplayContext == null) {
			JournalContentPortletInstanceConfiguration
				journalContentPortletInstanceConfiguration =
					portletDisplay.getPortletInstanceConfiguration(
						JournalContentPortletInstanceConfiguration.class);

			journalContentDisplayContext = new JournalContentDisplayContext(
				portletRequest, portletResponse, themeDisplay,
				journalContentPortletInstanceConfiguration,
				ddmStructureClassNameId, ddmTemplateModelResourcePermission,
				itemSelector, trashHelper);

			portletRequest.setAttribute(
				getRequestAttributeName(portletDisplay.getId()),
				journalContentDisplayContext);
		}

		return journalContentDisplayContext;
	}

	public static String getRequestAttributeName(String portletName) {
		return JournalContentWebKeys.JOURNAL_CONTENT_DISPLAY_CONTEXT +
			StringPool.POUND + portletName;
	}

	public boolean articleCommentsEnabled() throws Exception {
		JournalServiceConfiguration journalServiceConfiguration =
			ConfigurationProviderUtil.getCompanyConfiguration(
				JournalServiceConfiguration.class,
				_themeDisplay.getCompanyId());

		return journalServiceConfiguration.articleCommentsEnabled();
	}

	public JournalArticle getArticle() throws PortalException {
		if (_article != null) {
			return _article;
		}

		_article = _getArticleByPreviewAssetEntryId();

		if ((_article != null) &&
			JournalArticlePermission.contains(
				_themeDisplay.getPermissionChecker(), _article,
				ActionKeys.UPDATE)) {

			return _article;
		}

		long articleResourcePrimKey = ParamUtil.getLong(
			_portletRequest, "articleResourcePrimKey");

		if (articleResourcePrimKey == -1) {
			return _article;
		}

		if (articleResourcePrimKey == 0) {
			if (Validator.isBlank(getArticleId())) {
				return null;
			}

			JournalArticleResource articleResource =
				JournalArticleResourceLocalServiceUtil.fetchArticleResource(
					getArticleGroupId(), getArticleId());

			if (articleResource != null) {
				articleResourcePrimKey = articleResource.getResourcePrimKey();
			}
		}

		_article = JournalArticleLocalServiceUtil.fetchLatestArticle(
			articleResourcePrimKey, WorkflowConstants.STATUS_ANY, true);

		return _article;
	}

	public JournalArticleDisplay getArticleDisplay() throws PortalException {
		if (_articleDisplay != null) {
			return _articleDisplay;
		}

		JournalArticle article = getArticle();

		if (article == null) {
			return null;
		}

		String viewMode = ParamUtil.getString(
			_portletRequest, "viewMode", null);
		String languageId = ParamUtil.getString(
			_portletRequest, "languageId", _themeDisplay.getLanguageId());
		int page = ParamUtil.getInteger(_portletRequest, "page", 1);

		if (article.isApproved()) {
			JournalContent journalContent =
				(JournalContent)_portletRequest.getAttribute(
					JournalWebKeys.JOURNAL_CONTENT);

			if (journalContent == null) {
				return null;
			}

			_articleDisplay = journalContent.getDisplay(
				article.getGroupId(), article.getArticleId(),
				article.getVersion(), getDDMTemplateKey(), viewMode, languageId,
				page,
				new PortletRequestModel(_portletRequest, _portletResponse),
				_themeDisplay);
		}
		else {
			try {
				_articleDisplay =
					JournalArticleLocalServiceUtil.getArticleDisplay(
						article, getDDMTemplateKey(), viewMode, languageId,
						page,
						new PortletRequestModel(
							_portletRequest, _portletResponse),
						_themeDisplay);
			}
			catch (PortalException portalException) {
				_log.error(portalException);
			}
		}

		return _articleDisplay;
	}

	public long getArticleGroupId() {
		if (_articleGroupId != null) {
			return _articleGroupId;
		}

		_articleGroupId = ParamUtil.getLong(
			_portletRequest, "groupId",
			_journalContentPortletInstanceConfiguration.groupId());

		if (_articleGroupId <= 0) {
			_articleGroupId = _themeDisplay.getScopeGroupId();
		}

		return _articleGroupId;
	}

	public String getArticleId() {
		if (_articleId != null) {
			return _articleId;
		}

		_articleId = ParamUtil.getString(
			_portletRequest, "articleId",
			_journalContentPortletInstanceConfiguration.articleId());

		return _articleId;
	}

	public long getAssetEntryId() throws PortalException {
		AssetEntry assetEntry = _getAssetEntry();

		if (assetEntry == null) {
			return 0;
		}

		return assetEntry.getEntryId();
	}

	public AssetRenderer<JournalArticle> getAssetRenderer()
		throws PortalException {

		JournalArticle article = getArticle();

		if (article == null) {
			return null;
		}

		AssetRendererFactory<JournalArticle> assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClass(
				JournalArticle.class);

		if (assetRendererFactory == null) {
			return null;
		}

		return assetRendererFactory.getAssetRenderer(article, 0);
	}

	public DDMStructure getDDMStructure() throws PortalException {
		JournalArticle article = getArticle();

		if (article == null) {
			return null;
		}

		return article.getDDMStructure();
	}

	public DDMTemplate getDDMTemplate() throws PortalException {
		if (_ddmTemplate != null) {
			return _ddmTemplate;
		}

		_ddmTemplate = _getDDMTemplate(getDDMTemplateKey());

		return _ddmTemplate;
	}

	public String getDDMTemplateKey() throws PortalException {
		if (_ddmTemplateKey != null) {
			return _ddmTemplateKey;
		}

		String ddmTemplateKey = ParamUtil.getString(
			_portletRequest, "ddmTemplateKey",
			_journalContentPortletInstanceConfiguration.ddmTemplateKey());

		if (Validator.isNotNull(ddmTemplateKey)) {
			_ddmTemplateKey = ddmTemplateKey;
		}

		JournalArticle article = getArticle();

		if (article == null) {
			return _ddmTemplateKey;
		}

		if (Validator.isNull(_ddmTemplateKey) ||
			_ddmTemplateKey.equals(article.getDDMTemplateKey())) {

			_ddmTemplateKey = article.getDDMTemplateKey();

			return _ddmTemplateKey;
		}

		for (DDMTemplate ddmTemplate : getDDMTemplates()) {
			if (_ddmTemplateKey.equals(ddmTemplate.getTemplateKey())) {
				return _ddmTemplateKey;
			}
		}

		_ddmTemplateKey = article.getDDMTemplateKey();

		return _ddmTemplateKey;
	}

	public List<DDMTemplate> getDDMTemplates() throws PortalException {
		if (_ddmTemplates != null) {
			return _ddmTemplates;
		}

		JournalArticle article = getArticle();

		if (article == null) {
			return Collections.emptyList();
		}

		try {
			_ddmTemplates = DDMTemplateLocalServiceUtil.getTemplates(
				article.getGroupId(),
				PortalUtil.getClassNameId(DDMStructure.class),
				article.getDDMStructureId(), true);
		}
		catch (PortalException portalException) {
			_log.error(
				"Unable to get DDM temmplate for article " + article.getId(),
				portalException);
		}

		return _ddmTemplates;
	}

	public DDMTemplate getDefaultDDMTemplate() throws PortalException {
		if (_defaultDDMTemplate != null) {
			return _defaultDDMTemplate;
		}

		JournalArticle article = getArticle();

		_defaultDDMTemplate = _getDDMTemplate(article.getDDMTemplateKey());

		return _defaultDDMTemplate;
	}

	public long getGroupId() {
		StagingGroupHelper stagingGroupHelper =
			StagingGroupHelperUtil.getStagingGroupHelper();

		return stagingGroupHelper.getStagedPortletGroupId(
			_themeDisplay.getScopeGroupId(), JournalPortletKeys.JOURNAL);
	}

	public PortletURL getItemSelectorURL() {
		LiferayRenderRequest liferayRenderRequest =
			(LiferayRenderRequest)LiferayPortletUtil.getLiferayPortletRequest(
				_portletRequest);

		RequestBackedPortletURLFactory requestBackedPortletURLFactory =
			RequestBackedPortletURLFactoryUtil.create(liferayRenderRequest);

		LiferayRenderResponse liferayRenderResponse =
			(LiferayRenderResponse)LiferayPortletUtil.getLiferayPortletResponse(
				_portletResponse);

		InfoItemItemSelectorCriterion itemSelectorCriterion =
			new InfoItemItemSelectorCriterion();

		itemSelectorCriterion.setItemType(JournalArticle.class.getName());
		itemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			new JournalArticleItemSelectorReturnType());
		itemSelectorCriterion.setStatus(WorkflowConstants.STATUS_ANY);

		return _itemSelector.getItemSelectorURL(
			requestBackedPortletURLFactory, _getGroup(),
			_themeDisplay.getScopeGroupId(),
			liferayRenderResponse.getNamespace() + "selectedItem",
			itemSelectorCriterion);
	}

	public Map<String, Object> getJournalTemplateContext() {
		return HashMapBuilder.<String, Object>put(
			"actionURL",
			() -> PortletURLBuilder.create(
				PortletURLFactoryUtil.create(
					_portletRequest, JournalContentPortletKeys.JOURNAL_CONTENT,
					PortletRequest.RESOURCE_PHASE)
			).setMVCPath(
				"/journal_template_resources.jsp"
			).setParameter(
				"articleResourcePrimKey",
				() -> {
					AssetRendererFactory<JournalArticle> assetRendererFactory =
						AssetRendererFactoryRegistryUtil.
							getAssetRendererFactoryByClass(
								JournalArticle.class);

					AssetRenderer<JournalArticle> assetRenderer =
						assetRendererFactory.getAssetRenderer(getArticle(), 0);

					return assetRenderer.getClassPK();
				}
			).setWindowState(
				LiferayWindowState.EXCLUSIVE
			).buildString()
		).put(
			"portletNamespace",
			PortalUtil.getPortletNamespace(
				JournalContentPortletKeys.JOURNAL_CONTENT)
		).put(
			"portletURL",
			() -> {
				RequestBackedPortletURLFactory requestBackedPortletURLFactory =
					RequestBackedPortletURLFactoryUtil.create(_portletRequest);

				DDMTemplateItemSelectorCriterion
					ddmTemplateItemSelectorCriterion =
						new DDMTemplateItemSelectorCriterion();

				ddmTemplateItemSelectorCriterion.setClassNameId(
					PortalUtil.getClassNameId(JournalArticle.class.getName()));

				DDMStructure ddmStructure = getDDMStructure();

				if (ddmStructure != null) {
					ddmTemplateItemSelectorCriterion.setDDMStructureId(
						ddmStructure.getStructureId());
				}

				ddmTemplateItemSelectorCriterion.
					setDesiredItemSelectorReturnTypes(
						new DDMTemplateItemSelectorReturnType());

				return String.valueOf(
					_itemSelector.getItemSelectorURL(
						requestBackedPortletURLFactory, "selectDDMTemplate",
						ddmTemplateItemSelectorCriterion));
			}
		).build();
	}

	public JournalArticle getLatestArticle() throws PortalException {
		if (_latestArticle != null) {
			return _latestArticle;
		}

		JournalArticle article = getArticle();

		if (article == null) {
			return null;
		}

		_latestArticle = JournalArticleLocalServiceUtil.fetchLatestArticle(
			article.getGroupId(), article.getArticleId(),
			WorkflowConstants.STATUS_ANY);

		return _latestArticle;
	}

	public String getPortletResource() {
		if (_portletResource != null) {
			return _portletResource;
		}

		_portletResource = ParamUtil.getString(
			_portletRequest, "portletResource");

		return _portletResource;
	}

	public String getScopeGroupType() {
		Group scopeGroup = _themeDisplay.getScopeGroup();

		if (scopeGroup.isDepot()) {
			return ItemSelectorCriteriaConstants.SCOPE_GROUP_TYPE_ASSET_LIBRARY;
		}

		if (scopeGroup.getGroupId() == _themeDisplay.getCompanyGroupId()) {
			return ItemSelectorCriteriaConstants.SCOPE_GROUP_TYPE_GLOBAL;
		}

		if (scopeGroup.isLayout()) {
			return ItemSelectorCriteriaConstants.SCOPE_GROUP_TYPE_PAGE;
		}

		return ItemSelectorCriteriaConstants.SCOPE_GROUP_TYPE_SITE;
	}

	public JournalArticle getSelectedArticle() {
		PortletPreferences portletPreferences =
			_portletRequest.getPreferences();

		long assetEntryId = GetterUtil.getLong(
			portletPreferences.getValue("assetEntryId", StringPool.BLANK));

		AssetEntry assetEntry = AssetEntryLocalServiceUtil.fetchAssetEntry(
			assetEntryId);

		if (assetEntry == null) {
			return null;
		}

		return JournalArticleLocalServiceUtil.fetchLatestArticle(
			assetEntry.getClassPK());
	}

	public String getURLEdit() {
		try {
			AssetRendererFactory<JournalArticle> assetRendererFactory =
				AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClass(
					JournalArticle.class);

			AssetRenderer<JournalArticle> latestArticleAssetRenderer =
				assetRendererFactory.getAssetRenderer(
					getArticle(), AssetRendererFactory.TYPE_LATEST_APPROVED);

			return PortletURLBuilder.create(
				latestArticleAssetRenderer.getURLEdit(
					PortalUtil.getLiferayPortletRequest(_portletRequest), null,
					LiferayWindowState.NORMAL, _themeDisplay.getURLCurrent())
			).setPortletResource(
				() -> {
					PortletDisplay portletDisplay =
						_themeDisplay.getPortletDisplay();

					return portletDisplay.getPortletName();
				}
			).buildString();
		}
		catch (Exception exception) {
			_log.error("Unable to get edit URL", exception);

			return StringPool.BLANK;
		}
	}

	public String getURLEditTemplate() throws Exception {
		DDMTemplate ddmTemplate = getDDMTemplate();

		if (ddmTemplate == null) {
			return StringPool.BLANK;
		}

		return PortletURLBuilder.create(
			PortalUtil.getControlPanelPortletURL(
				_portletRequest, JournalPortletKeys.JOURNAL,
				PortletRequest.RENDER_PHASE)
		).setMVCPath(
			"/edit_ddm_template.jsp"
		).setRedirect(
			_themeDisplay.getURLCurrent()
		).setParameter(
			"ddmTemplateId", ddmTemplate.getTemplateId()
		).setPortletMode(
			PortletMode.VIEW
		).buildString();
	}

	public String getURLViewHistory() {
		try {
			JournalArticle article = getArticle();

			return PortletURLBuilder.create(
				PortalUtil.getControlPanelPortletURL(
					_portletRequest,
					GroupLocalServiceUtil.fetchGroup(article.getGroupId()),
					JournalPortletKeys.JOURNAL, 0, 0,
					PortletRequest.RENDER_PHASE)
			).setMVCPath(
				"/view_article_history.jsp"
			).setBackURL(
				_themeDisplay.getURLCurrent()
			).setParameter(
				"articleId", article.getArticleId()
			).buildString();
		}
		catch (Exception exception) {
			_log.error("Unable to get view history URL", exception);

			return StringPool.BLANK;
		}
	}

	public boolean hasRestorePermission() throws PortalException {
		JournalArticle selectedArticle = getSelectedArticle();

		if ((selectedArticle == null) || !selectedArticle.isInTrash()) {
			return false;
		}

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			JournalArticle.class.getName());

		TrashEntry trashEntry = _trashHelper.getTrashEntry(selectedArticle);

		return trashHandler.hasTrashPermission(
			_themeDisplay.getPermissionChecker(), 0, trashEntry.getClassPK(),
			TrashActionKeys.RESTORE);
	}

	public boolean hasViewPermission() throws PortalException {
		if (_hasViewPermission != null) {
			return _hasViewPermission;
		}

		_hasViewPermission = true;

		JournalArticle article = getArticle();

		if (article != null) {
			_hasViewPermission = JournalArticlePermission.contains(
				_themeDisplay.getPermissionChecker(), article, ActionKeys.VIEW);
		}

		return _hasViewPermission;
	}

	public void incrementViewCounter() throws PortalException {
		JournalArticle article = getArticle();
		JournalArticleDisplay articleDisplay = getArticleDisplay();

		if ((article == null) || !hasViewPermission() ||
			(articleDisplay == null) || isExpired() ||
			!isEnableViewCountIncrement()) {

			return;
		}

		AssetEntryServiceUtil.incrementViewCounter(
			articleDisplay.getCompanyId(), JournalArticle.class.getName(),
			articleDisplay.getResourcePrimKey());
	}

	public boolean isDefaultTemplate() {
		String ddmTemplateKey = ParamUtil.getString(
			_portletRequest, "ddmTemplateKey");

		if (Validator.isNotNull(ddmTemplateKey)) {
			return false;
		}

		ddmTemplateKey =
			_journalContentPortletInstanceConfiguration.ddmTemplateKey();

		if (Validator.isNotNull(ddmTemplateKey)) {
			return false;
		}

		return true;
	}

	public boolean isEnabledContentMetadataAssetAddonEntry(String key) {
		String contentMetadataAssetAddonEntryKeysString =
			_journalContentPortletInstanceConfiguration.
				contentMetadataAssetAddonEntryKeys();

		if (Validator.isNull(contentMetadataAssetAddonEntryKeysString)) {
			return false;
		}

		String[] contentMetadataAssetAddonEntryKeys = StringUtil.split(
			contentMetadataAssetAddonEntryKeysString);

		if (ArrayUtil.contains(contentMetadataAssetAddonEntryKeys, key)) {
			return true;
		}

		return false;
	}

	public boolean isEnabledConversion(String extension) {
		if (!DocumentConversionUtil.isEnabled() ||
			!ArrayUtil.contains(
				DocumentConversionUtil.getConversions("html"), extension)) {

			return false;
		}

		return true;
	}

	public boolean isEnabledUserToolAssetAddonEntry(String key) {
		String userToolAssetAddonEntryKeysString =
			_journalContentPortletInstanceConfiguration.
				userToolAssetAddonEntryKeys();

		if (Validator.isNull(userToolAssetAddonEntryKeysString)) {
			return false;
		}

		String[] userToolAssetAddonEntryKeys = StringUtil.split(
			userToolAssetAddonEntryKeysString);

		if (ArrayUtil.contains(userToolAssetAddonEntryKeys, key)) {
			return true;
		}

		return false;
	}

	public boolean isEnableViewCountIncrement() {
		return _journalContentPortletInstanceConfiguration.
			enableViewCountIncrement();
	}

	public boolean isExpired() throws PortalException {
		if (_expired != null) {
			return _expired;
		}

		JournalArticle article = getArticle();

		_expired = article.isExpired();

		if (!_expired) {
			Date expirationDate = article.getExpirationDate();

			if ((expirationDate != null) && expirationDate.before(new Date())) {
				_expired = true;
			}
		}

		return _expired;
	}

	public boolean isPreview() {
		if (_preview != null) {
			return _preview;
		}

		JournalArticle article = _getArticleByPreviewAssetEntryId();

		if (article == null) {
			_preview = false;

			return _preview;
		}

		_preview = true;

		return _preview;
	}

	public boolean isShowArticle() throws PortalException {
		if (_showArticle != null) {
			return _showArticle;
		}

		JournalArticle article = getArticle();

		if (article == null) {
			_showArticle = false;

			return _showArticle;
		}

		JournalArticleDisplay articleDisplay = getArticleDisplay();

		if ((articleDisplay == null) || !hasViewPermission()) {
			_showArticle = false;

			return _showArticle;
		}

		if ((article.isPending() || article.isScheduled() || isExpired()) &&
			!isPreview()) {

			_showArticle = false;

			return _showArticle;
		}

		_showArticle = true;

		return _showArticle;
	}

	public boolean isShowEditArticleIcon() throws PortalException {
		if (_showEditArticleIcon != null) {
			return _showEditArticleIcon;
		}

		_showEditArticleIcon = false;

		Group group = _themeDisplay.getScopeGroup();

		if (group.hasStagingGroup() && _STAGING_LIVE_GROUP_LOCKING_ENABLED) {
			return _showEditArticleIcon;
		}

		JournalArticle latestArticle = getLatestArticle();

		if (latestArticle == null) {
			return _showEditArticleIcon;
		}

		_showEditArticleIcon = JournalArticlePermission.contains(
			_themeDisplay.getPermissionChecker(), latestArticle,
			ActionKeys.UPDATE);

		return _showEditArticleIcon;
	}

	public boolean isShowEditTemplateIcon() throws PortalException {
		if (_showEditTemplateIcon != null) {
			return _showEditTemplateIcon;
		}

		_showEditTemplateIcon = false;

		DDMTemplate ddmTemplate = getDDMTemplate();

		if (ddmTemplate == null) {
			return _showEditTemplateIcon;
		}

		try {
			_showEditTemplateIcon =
				_ddmTemplateModelResourcePermission.contains(
					_themeDisplay.getPermissionChecker(), ddmTemplate,
					ActionKeys.UPDATE);
		}
		catch (PortalException portalException) {
			_log.error(
				"Unable to check permission on DDM template " +
					ddmTemplate.getTemplateId(),
				portalException);
		}

		return _showEditTemplateIcon;
	}

	public boolean isShowSelectArticleLink() {
		if (_showSelectArticleLink != null) {
			return _showSelectArticleLink;
		}

		Layout layout = _themeDisplay.getLayout();

		if (layout.isLayoutPrototypeLinkActive()) {
			_showSelectArticleLink = false;

			return _showSelectArticleLink;
		}

		Group scopeGroup = _themeDisplay.getScopeGroup();

		StagingGroupHelper stagingGroupHelper =
			StagingGroupHelperUtil.getStagingGroupHelper();

		if (stagingGroupHelper.isLocalLiveGroup(scopeGroup) ||
			stagingGroupHelper.isRemoteLiveGroup(scopeGroup)) {

			_showSelectArticleLink = false;

			return _showSelectArticleLink;
		}

		_showSelectArticleLink = true;

		return _showSelectArticleLink;
	}

	private JournalContentDisplayContext(
			PortletRequest portletRequest, PortletResponse portletResponse,
			ThemeDisplay themeDisplay,
			JournalContentPortletInstanceConfiguration
				journalContentPortletInstanceConfiguration,
			long ddmStructureClassNameId,
			ModelResourcePermission<DDMTemplate>
				ddmTemplateModelResourcePermission,
			ItemSelector itemSelector, TrashHelper trashHelper)
		throws PortalException {

		_portletRequest = portletRequest;
		_portletResponse = portletResponse;
		_themeDisplay = themeDisplay;
		_journalContentPortletInstanceConfiguration =
			journalContentPortletInstanceConfiguration;
		_ddmStructureClassNameId = ddmStructureClassNameId;
		_ddmTemplateModelResourcePermission =
			ddmTemplateModelResourcePermission;
		_itemSelector = itemSelector;
		_trashHelper = trashHelper;

		AssetEntry assetEntry = _getAssetEntry();

		if (isShowArticle() && (assetEntry != null)) {
			LinkedAssetEntryIdsUtil.addLinkedAssetEntryId(
				portletRequest, assetEntry.getEntryId());
		}

		if (Validator.isNull(getPortletResource()) && !isShowArticle()) {
			portletRequest.setAttribute(
				WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.TRUE);
		}
	}

	private JournalArticle _getArticleByPreviewAssetEntryId() {
		long previewClassNameId = ParamUtil.getLong(
			_portletRequest, "previewClassNameId");
		long previewClassPK = ParamUtil.getLong(
			_portletRequest, "previewClassPK");

		if ((previewClassNameId <= 0) || (previewClassPK <= 0)) {
			return null;
		}

		AssetEntry assetEntry = AssetEntryLocalServiceUtil.fetchEntry(
			previewClassNameId, previewClassPK);

		if (assetEntry == null) {
			return null;
		}

		AssetRendererFactory<?> assetRendererFactory =
			assetEntry.getAssetRendererFactory();

		if (assetRendererFactory == null) {
			return null;
		}

		int previewType = ParamUtil.getInteger(
			_portletRequest, "previewType",
			AssetRendererFactory.TYPE_LATEST_APPROVED);

		try {
			AssetRenderer<?> assetRenderer =
				assetRendererFactory.getAssetRenderer(
					assetEntry.getClassPK(), previewType);

			return (JournalArticle)assetRenderer.getAssetObject();
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}

		return null;
	}

	private AssetEntry _getAssetEntry() throws PortalException {
		JournalArticle article = getArticle();

		if (article == null) {
			return null;
		}

		AssetRendererFactory<JournalArticle> assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClass(
				JournalArticle.class);

		AssetRenderer<JournalArticle> assetRenderer =
			assetRendererFactory.getAssetRenderer(article, 0);

		return AssetEntryLocalServiceUtil.fetchEntry(
			JournalArticle.class.getName(), assetRenderer.getClassPK());
	}

	private DDMTemplate _getDDMTemplate(String ddmTemplateKey)
		throws PortalException {

		JournalArticle article = getArticle();

		if (article == null) {
			return null;
		}

		return DDMTemplateLocalServiceUtil.fetchTemplate(
			article.getGroupId(), _ddmStructureClassNameId, ddmTemplateKey,
			true);
	}

	private Group _getGroup() {
		StagingGroupHelper stagingGroupHelper =
			StagingGroupHelperUtil.getStagingGroupHelper();

		return stagingGroupHelper.getStagedPortletGroup(
			_themeDisplay.getScopeGroup(), JournalPortletKeys.JOURNAL);
	}

	private static final boolean _STAGING_LIVE_GROUP_LOCKING_ENABLED =
		GetterUtil.getBoolean(
			PropsUtil.get(PropsKeys.STAGING_LIVE_GROUP_LOCKING_ENABLED));

	private static final Log _log = LogFactoryUtil.getLog(
		JournalContentDisplayContext.class);

	private JournalArticle _article;
	private JournalArticleDisplay _articleDisplay;
	private Long _articleGroupId;
	private String _articleId;
	private final long _ddmStructureClassNameId;
	private DDMTemplate _ddmTemplate;
	private String _ddmTemplateKey;
	private final ModelResourcePermission<DDMTemplate>
		_ddmTemplateModelResourcePermission;
	private List<DDMTemplate> _ddmTemplates;
	private DDMTemplate _defaultDDMTemplate;
	private Boolean _expired;
	private Boolean _hasViewPermission;
	private final ItemSelector _itemSelector;
	private final JournalContentPortletInstanceConfiguration
		_journalContentPortletInstanceConfiguration;
	private JournalArticle _latestArticle;
	private final PortletRequest _portletRequest;
	private String _portletResource;
	private final PortletResponse _portletResponse;
	private Boolean _preview;
	private Boolean _showArticle;
	private Boolean _showEditArticleIcon;
	private Boolean _showEditTemplateIcon;
	private Boolean _showSelectArticleLink;
	private final ThemeDisplay _themeDisplay;
	private final TrashHelper _trashHelper;

}