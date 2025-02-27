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

package com.liferay.portal.language.override.internal;

import com.liferay.petra.concurrent.DCLSingleton;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.language.LanguageOverrideProvider;
import com.liferay.portal.language.override.internal.provider.PLOOriginalTranslationThreadLocal;
import com.liferay.portal.language.override.model.PLOEntry;
import com.liferay.portal.language.override.service.PLOEntryLocalService;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Drew Brokke
 */
@Component(
	service = {
		LanguageOverrideProvider.class, PLOLanguageOverrideProvider.class
	}
)
public class PLOLanguageOverrideProvider implements LanguageOverrideProvider {

	@Override
	public ResourceBundle getOverrideResourceBundle(Locale locale) {
		Map<String, OverrideResourceBundle> overrideResourceBundles =
			_overrideResourceBundlesDCLSingleton.getSingleton(_supplier);

		if (overrideResourceBundles.isEmpty() ||
			PLOOriginalTranslationThreadLocal.isUseOriginalTranslation()) {

			return null;
		}

		return overrideResourceBundles.get(
			_encodeKey(
				CompanyThreadLocal.getCompanyId(),
				_language.getLanguageId(locale)));
	}

	protected void add(PLOEntry ploEntry) {
		_add(
			_overrideResourceBundlesDCLSingleton.getSingleton(_supplier),
			ploEntry);
	}

	protected void remove(PLOEntry ploEntry) {
		Map<String, OverrideResourceBundle> overrideResourceBundles =
			_overrideResourceBundlesDCLSingleton.getSingleton(_supplier);

		overrideResourceBundles.computeIfPresent(
			_encodeKey(ploEntry.getCompanyId(), ploEntry.getLanguageId()),
			(key, value) -> {
				value.remove(ploEntry.getKey());

				if (value.isEmpty()) {
					return null;
				}

				return value;
			});
	}

	protected void update(PLOEntry ploEntry) {
		Map<String, OverrideResourceBundle> overrideResourceBundles =
			_overrideResourceBundlesDCLSingleton.getSingleton(_supplier);

		overrideResourceBundles.computeIfPresent(
			_encodeKey(ploEntry.getCompanyId(), ploEntry.getLanguageId()),
			(key, value) -> {
				value.put(ploEntry.getKey(), ploEntry.getValue());

				return value;
			});
	}

	private void _add(
		Map<String, OverrideResourceBundle> overrideResourceBundles,
		PLOEntry ploEntry) {

		overrideResourceBundles.compute(
			_encodeKey(ploEntry.getCompanyId(), ploEntry.getLanguageId()),
			(key, value) -> {
				if (value == null) {
					value = new OverrideResourceBundle(new HashMap<>());
				}

				value.put(ploEntry.getKey(), ploEntry.getValue());

				return value;
			});
	}

	private Map<String, OverrideResourceBundle>
		_createOverrideResourceBundles() {

		Map<String, OverrideResourceBundle> overrideResourceBundles =
			new ConcurrentHashMap<>();

		_companyLocalService.forEachCompanyId(
			companyId -> {
				for (PLOEntry ploEntry :
						_ploEntryLocalService.getPLOEntries(companyId)) {

					_add(overrideResourceBundles, ploEntry);
				}
			});

		return overrideResourceBundles;
	}

	private String _encodeKey(long companyId, String languageId) {
		return StringBundler.concat(companyId, StringPool.POUND, languageId);
	}

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private Language _language;

	private final DCLSingleton<Map<String, OverrideResourceBundle>>
		_overrideResourceBundlesDCLSingleton = new DCLSingleton<>();

	@Reference
	private PLOEntryLocalService _ploEntryLocalService;

	private final Supplier<Map<String, OverrideResourceBundle>> _supplier =
		this::_createOverrideResourceBundles;

	private class OverrideResourceBundle extends ResourceBundle {

		@Override
		public Enumeration<String> getKeys() {
			return Collections.enumeration(_overrideMap.keySet());
		}

		public boolean isEmpty() {
			return _overrideMap.isEmpty();
		}

		public void put(String key, String value) {
			_overrideMap.put(key, value);
		}

		public void remove(String key) {
			_overrideMap.remove(key);
		}

		@Override
		protected Object handleGetObject(String key) {
			return _overrideMap.get(key);
		}

		@Override
		protected Set<String> handleKeySet() {
			return _overrideMap.keySet();
		}

		private OverrideResourceBundle(Map<String, String> overrideMap) {
			_overrideMap = overrideMap;
		}

		private final Map<String, String> _overrideMap;

	}

}