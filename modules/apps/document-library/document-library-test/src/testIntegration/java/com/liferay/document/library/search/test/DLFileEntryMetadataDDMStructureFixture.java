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

package com.liferay.document.library.search.test;

import com.liferay.document.library.kernel.model.DLFileEntryMetadata;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.model.DLFileEntryTypeConstants;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalService;
import com.liferay.document.library.test.util.search.FileEntryBlueprint;
import com.liferay.document.library.test.util.search.FileEntrySearchFixture;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.SetUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * @author Igor Fabiano Nazar
 * @author Lucas Marques de Paula
 */
public class DLFileEntryMetadataDDMStructureFixture {

	public DLFileEntryMetadataDDMStructureFixture(
		DLFixture dlFixture, DLAppLocalService dlAppLocalService,
		DDMStructureLocalService ddmStructureLocalService,
		DLFileEntryTypeLocalService dlFileEntryTypeLocalService) {

		_dlFixture = dlFixture;
		_ddmStructureLocalService = ddmStructureLocalService;
		_dlFileEntryTypeLocalService = dlFileEntryTypeLocalService;

		_fileEntrySearchFixture = new FileEntrySearchFixture(dlAppLocalService);

		_fileEntrySearchFixture.setUp();
	}

	public void setUp() {
		_fileEntrySearchFixture.setUp();
	}

	public void tearDown() throws Exception {
		_fileEntrySearchFixture.tearDown();

		deleteAllFileEntryTypes();
		deleteAllDDMStructures();
	}

	protected DLFileEntryType addDLFileEntryType(
			Long groupId, DDMStructure ddmStructure)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		DLFileEntryType dlFileEntryType =
			_dlFileEntryTypeLocalService.addFileEntryType(
				_dlFixture.getUserId(), groupId, ddmStructure.getStructureId(),
				null,
				Collections.singletonMap(
					LocaleUtil.getSiteDefault(), "New File Entry Type"),
				Collections.singletonMap(
					LocaleUtil.getSiteDefault(), "New File Entry Type"),
				DLFileEntryTypeConstants.FILE_ENTRY_TYPE_SCOPE_DEFAULT,
				serviceContext);

		_dlFileEntryTypeLocalService.addDDMStructureLinks(
			dlFileEntryType.getFileEntryTypeId(),
			SetUtil.fromArray(ddmStructure.getStructureId()));

		_fileEntryTypes.add(dlFileEntryType);

		return dlFileEntryType;
	}

	protected FileEntry addFileEntry(String fileName, long fileEntryTypeId)
		throws IOException, PortalException {

		Class<?> clazz = getClass();

		try (InputStream inputStream = clazz.getResourceAsStream(
				"dependencies/" + fileName)) {

			return _fileEntrySearchFixture.addFileEntry(
				new FileEntryBlueprint() {
					{
						addAttributes(
							HashMapBuilder.<String, Serializable>put(
								"fileEntryTypeId", fileEntryTypeId
							).build());
						setFileName(fileName);
						setGroupId(_dlFixture.getGroupId());
						setInputStream(inputStream);
						setTitle(fileName);
						setUserId(_dlFixture.getUserId());
					}
				});
		}
	}

	protected DDMStructure createStructureWithDLFileEntry(
			String fileName, Locale locale)
		throws Exception {

		DDMForm ddmForm = DDMStructureTestUtil.getSampleDDMForm(
			new Locale[] {locale}, locale);

		long groupId = _dlFixture.getGroupId();

		DDMStructure ddmStructure = DDMStructureTestUtil.addStructure(
			groupId, DLFileEntryMetadata.class.getName(), ddmForm, locale);

		_ddmStructures.add(ddmStructure);

		DLFileEntryType dlFileEntryType = addDLFileEntryType(
			groupId, ddmStructure);

		addFileEntry(fileName, dlFileEntryType.getFileEntryTypeId());

		return ddmStructure;
	}

	protected void deleteAllDDMStructures() throws PortalException {
		for (DDMStructure ddmStructure : _ddmStructures) {
			_ddmStructureLocalService.deleteDDMStructure(ddmStructure);
		}

		_ddmStructures.clear();
	}

	protected void deleteAllFileEntryTypes() throws PortalException {
		for (DLFileEntryType dlFileEntryType : _fileEntryTypes) {
			_dlFileEntryTypeLocalService.deleteFileEntryType(dlFileEntryType);
		}

		_fileEntryTypes.clear();
	}

	private final DDMStructureLocalService _ddmStructureLocalService;
	private final List<DDMStructure> _ddmStructures = new ArrayList<>();
	private final DLFileEntryTypeLocalService _dlFileEntryTypeLocalService;
	private final DLFixture _dlFixture;
	private final FileEntrySearchFixture _fileEntrySearchFixture;
	private final List<DLFileEntryType> _fileEntryTypes = new ArrayList<>();

}