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

package com.liferay.calendar.search.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.calendar.constants.CalendarActionKeys;
import com.liferay.calendar.model.Calendar;
import com.liferay.calendar.model.CalendarBooking;
import com.liferay.calendar.model.CalendarResource;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.settings.LocalizedValuesMap;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.test.util.FieldValuesAssert;
import com.liferay.portal.test.rule.Inject;

import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Wade Cao
 * @author André de Oliveira
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
@Sync
public class CalendarBookingIndexerIndexedFieldsTest
	extends BaseCalendarIndexerTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		setIndexerClass(CalendarBooking.class);
	}

	@Test
	public void testIndexedFields() throws Exception {
		String originalTitle = "entity title";
		String translatedTitle = "entitas neve";

		String description = StringUtil.toLowerCase(
			RandomTestUtil.randomString());

		CalendarBooking calendarBooking = addCalendarBooking(
			new LocalizedValuesMap() {
				{
					put(LocaleUtil.US, originalTitle);
					put(LocaleUtil.HUNGARY, translatedTitle);
				}
			},
			new LocalizedValuesMap() {
				{
					put(LocaleUtil.US, originalTitle);
					put(LocaleUtil.HUNGARY, translatedTitle);
				}
			},
			new LocalizedValuesMap() {
				{
					put(LocaleUtil.US, description);
					put(LocaleUtil.HUNGARY, description);
				}
			});

		Map<String, String> map = HashMapBuilder.put(
			Field.CLASS_NAME_ID,
			String.valueOf(portal.getClassNameId(Calendar.class))
		).build();

		indexedFieldsFixture.populatePriority("0.0", map);

		map.put(Field.RELATED_ENTRY, "true");
		map.put(Field.STAGING_GROUP, "false");
		map.put(Field.STATUS, "0");
		map.put(
			"statusByUserId",
			String.valueOf(calendarBooking.getStatusByUserId()));
		map.put("viewActionId", CalendarActionKeys.VIEW_BOOKING_DETAILS);

		populateTitle(originalTitle, map);

		populateTranslatedTitle(translatedTitle, map);

		CalendarResource calendarResource =
			calendarBooking.getCalendarResource();

		populateCalendarResource(calendarResource, map);

		Calendar calendar = calendarResource.getDefaultCalendar();

		populateCalendar(calendar, map);

		populateCalendarBooking(calendarBooking, map);

		indexedFieldsFixture.populateRoleIdFields(
			calendarBooking.getCompanyId(), Calendar.class.getName(),
			calendarBooking.getCalendarId(), calendarBooking.getGroupId(),
			CalendarActionKeys.VIEW_BOOKING_DETAILS, map);

		indexedFieldsFixture.populateUID(
			calendarBooking.getModelClassName(),
			calendarBooking.getCalendarBookingId(), map);

		String keywords = "nev";

		SearchResponse searchResponse = searchOnlyOneSearchResponse(
			keywords, LocaleUtil.HUNGARY);

		FieldValuesAssert.assertFieldValues(
			map, name -> !name.equals("score") && !name.equals("timestamp"),
			searchResponse);
	}

	protected CalendarBooking addCalendarBooking(
			LocalizedValuesMap titleLocalizedValuesMap,
			LocalizedValuesMap nameLocalizedValuesMap,
			LocalizedValuesMap descriptionLocalizedValuesMap)
		throws PortalException {

		ServiceContext serviceContext = getServiceContext();

		Calendar calendar = addCalendar(
			nameLocalizedValuesMap, descriptionLocalizedValuesMap,
			serviceContext);

		return addCalendarBooking(
			titleLocalizedValuesMap, calendar, serviceContext);
	}

	protected void populateCalendar(
		Calendar calendar, Map<String, String> map) {

		map.put(Field.DEFAULT_LANGUAGE_ID, calendar.getDefaultLanguageId());
		map.put(Field.USER_ID, String.valueOf(calendar.getUserId()));
		map.put(
			Field.USER_NAME, StringUtil.toLowerCase(calendar.getUserName()));
		map.put("visible", "true");
	}

	protected void populateCalendarBooking(
			CalendarBooking calendarBooking, Map<String, String> map)
		throws Exception {

		map.put(
			Field.ASSET_ENTRY_ID,
			String.valueOf(_getAssetEntryId(calendarBooking)));
		map.put(
			Field.CLASS_PK, String.valueOf(calendarBooking.getCalendarId()));
		map.put(Field.ENTRY_CLASS_NAME, calendarBooking.getModelClassName());
		map.put(
			Field.ENTRY_CLASS_PK,
			String.valueOf(calendarBooking.getCalendarBookingId()));
		map.put(
			"assetEntryId_sortable",
			String.valueOf(_getAssetEntryId(calendarBooking)));
		map.put(
			"calendarBookingId",
			String.valueOf(calendarBooking.getCalendarBookingId()));
		map.put("endTime", String.valueOf(calendarBooking.getEndTime()));
		map.put(
			"endTime_sortable", String.valueOf(calendarBooking.getEndTime()));
		map.put("startTime", String.valueOf(calendarBooking.getStartTime()));
		map.put(
			"startTime_sortable",
			String.valueOf(calendarBooking.getStartTime()));

		indexedFieldsFixture.populateViewCount(
			CalendarBooking.class, calendarBooking.getCalendarBookingId(), map);

		populateDates(calendarBooking, map);
	}

	protected void populateCalendarResource(
		CalendarResource calendarResource, Map<String, String> map) {

		map.put(
			Field.COMPANY_ID, String.valueOf(calendarResource.getCompanyId()));
		map.put(Field.GROUP_ID, String.valueOf(calendarResource.getGroupId()));
		map.put(
			Field.SCOPE_GROUP_ID,
			String.valueOf(calendarResource.getGroupId()));
	}

	protected void populateDates(
		CalendarBooking calendarBooking, Map<String, String> map) {

		indexedFieldsFixture.populateDate(
			Field.CREATE_DATE, calendarBooking.getCreateDate(), map);
		indexedFieldsFixture.populateDate(
			Field.MODIFIED_DATE, calendarBooking.getModifiedDate(), map);
		indexedFieldsFixture.populateDate(Field.PUBLISH_DATE, new Date(0), map);

		indexedFieldsFixture.populateExpirationDateWithForever(map);
	}

	protected void populateTitle(String title, Map<String, String> map) {
		map.put(Field.TITLE + "_en_US", title);

		map.put("localized_title", title);

		Set<Locale> locales = LanguageUtil.getAvailableLocales();

		locales.forEach(
			locale -> {
				String mapKey = StringBundler.concat(
					"localized_title_", locale.getLanguage(), "_",
					locale.getCountry());

				String mapKeySortable = mapKey + "_sortable";

				map.put(mapKey, title);

				map.put(mapKeySortable, title);
			});
	}

	protected void populateTranslatedTitle(
		String title, Map<String, String> map) {

		map.put(Field.TITLE + "_hu_HU", title);

		map.put("localized_title_hu_HU", title);
		map.put("localized_title_hu_HU_sortable", title);
	}

	@Inject
	protected Portal portal;

	private long _getAssetEntryId(CalendarBooking calendarBooking) {
		AssetEntry assetEntry = _assetEntryLocalService.fetchEntry(
			CalendarBooking.class.getName(),
			calendarBooking.getCalendarBookingId());

		if (assetEntry == null) {
			return 0;
		}

		return assetEntry.getEntryId();
	}

	@Inject
	private AssetEntryLocalService _assetEntryLocalService;

}