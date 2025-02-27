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

package com.liferay.layout.internal.model.listener;

import com.liferay.client.extension.service.ClientExtensionEntryRelLocalService;
import com.liferay.friendly.url.service.FriendlyURLEntryLocalService;
import com.liferay.layout.friendly.url.LayoutFriendlyURLEntryHelper;
import com.liferay.layout.model.LayoutLocalization;
import com.liferay.layout.service.LayoutClassedModelUsageLocalService;
import com.liferay.layout.service.LayoutLocalizationLocalService;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 * @author David Truong
 */
@Component(service = ModelListener.class)
public class LayoutModelListener extends BaseModelListener<Layout> {

	@Override
	public void onAfterRemove(Layout layout) {
		if (layout == null) {
			return;
		}

		_clientExtensionEntryRelLocalService.deleteClientExtensionEntryRels(
			_portal.getClassNameId(Layout.class), layout.getPlid());

		_friendlyURLEntryLocalService.deleteFriendlyURLEntry(
			layout.getGroupId(),
			_layoutFriendlyURLEntryHelper.getClassNameId(
				layout.isPrivateLayout()),
			layout.getPlid());

		if (layout.isTypeAssetDisplay()) {
			_friendlyURLEntryLocalService.deleteFriendlyURLEntry(
				layout.getGroupId(),
				_layoutFriendlyURLEntryHelper.getClassNameId(
					!layout.isPrivateLayout()),
				layout.getPlid());
		}

		for (Locale locale :
				_language.getAvailableLocales(layout.getGroupId())) {

			LayoutLocalization layoutLocalization =
				_layoutLocalizationLocalService.fetchLayoutLocalization(
					layout.getGroupId(), LocaleUtil.toLanguageId(locale),
					layout.getPlid());

			if (layoutLocalization != null) {
				_layoutLocalizationLocalService.deleteLayoutLocalization(
					layoutLocalization);
			}
		}
	}

	@Override
	public void onBeforeRemove(Layout layout) throws ModelListenerException {
		_layoutClassedModelUsageLocalService.
			deleteLayoutClassedModelUsagesByPlid(layout.getPlid());
	}

	@Reference
	private ClientExtensionEntryRelLocalService
		_clientExtensionEntryRelLocalService;

	@Reference
	private FriendlyURLEntryLocalService _friendlyURLEntryLocalService;

	@Reference
	private Language _language;

	@Reference
	private LayoutClassedModelUsageLocalService
		_layoutClassedModelUsageLocalService;

	@Reference
	private LayoutFriendlyURLEntryHelper _layoutFriendlyURLEntryHelper;

	@Reference
	private LayoutLocalizationLocalService _layoutLocalizationLocalService;

	@Reference
	private Portal _portal;

}