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

package com.liferay.document.library.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil;
import com.liferay.document.library.configuration.DLConfiguration;
import com.liferay.document.library.kernel.exception.DuplicateFileEntryException;
import com.liferay.document.library.kernel.exception.DuplicateFileEntryExternalReferenceCodeException;
import com.liferay.document.library.kernel.exception.DuplicateFolderNameException;
import com.liferay.document.library.kernel.exception.FileExtensionException;
import com.liferay.document.library.kernel.exception.InvalidFileVersionException;
import com.liferay.document.library.kernel.exception.NoSuchFolderException;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.document.library.kernel.model.DLFileEntryMetadata;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.model.DLFileEntryTypeConstants;
import com.liferay.document.library.kernel.model.DLFileVersion;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.model.DLVersionNumberIncrease;
import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.document.library.kernel.service.DLFileEntryLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLFileEntryMetadataLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLFileVersionLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLTrashLocalServiceUtil;
import com.liferay.document.library.kernel.store.DLStoreRequest;
import com.liferay.document.library.kernel.store.DLStoreUtil;
import com.liferay.document.library.kernel.util.DLAppHelperThreadLocal;
import com.liferay.document.library.test.util.DLTestUtil;
import com.liferay.document.library.util.DLFileEntryTypeUtil;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.DDMStorageEngineManager;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestUtil;
import com.liferay.dynamic.data.mapping.util.DDMBeanTranslatorUtil;
import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.model.ExpandoColumnConstants;
import com.liferay.expando.kernel.model.ExpandoTable;
import com.liferay.expando.kernel.service.ExpandoColumnLocalServiceUtil;
import com.liferay.expando.kernel.service.ExpandoTableLocalServiceUtil;
import com.liferay.petra.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.test.util.ConfigurationTemporarySwapper;
import com.liferay.portal.kernel.interval.IntervalActionProcessor;
import com.liferay.portal.kernel.lock.Lock;
import com.liferay.portal.kernel.lock.LockManagerUtil;
import com.liferay.portal.kernel.lock.NoSuchLockException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.repository.LocalRepository;
import com.liferay.portal.kernel.repository.RepositoryProviderUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.constants.TestDataConstants;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Michael C. Han
 * @author Sergio González
 */
@RunWith(Arquillian.class)
public class DLFileEntryLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test(expected = FileExtensionException.class)
	public void testAddFileEntryShouldFailIfSourceFileNameExtensionNotSupported()
		throws Exception {

		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				DLFileEntryServiceTestUtil.getConfigurationTemporarySwapper(
					"fileExtensions", new String[] {".doc"})) {

			DLFileEntryLocalServiceUtil.addFileEntry(
				null, TestPropsValues.getUserId(), _group.getGroupId(),
				_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				"file.jpg", ContentTypes.TEXT_PLAIN, "file",
				StringUtil.randomString(), StringPool.BLANK, StringPool.BLANK,
				-1, new HashMap<>(), null,
				new ByteArrayInputStream(new byte[0]), 0, null, null,
				ServiceContextTestUtil.getServiceContext(
					_group.getGroupId(), TestPropsValues.getUserId()));
		}
	}

	@Test
	public void testAddFileEntryWhenValidateExtensionDisabled()
		throws Exception {

		boolean enabled = DLAppHelperThreadLocal.isEnabled();

		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					DLConfiguration.class.getName(),
					HashMapDictionaryBuilder.<String, Object>put(
						"fileExtensions", "png"
					).build())) {

			DLAppHelperThreadLocal.setEnabled(false);

			DLFileEntryLocalServiceUtil.addFileEntry(
				null, TestPropsValues.getUserId(), _group.getGroupId(),
				_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				RandomTestUtil.randomString(),
				ContentTypes.APPLICATION_OCTET_STREAM,
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), StringUtil.randomString(),
				DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT,
				null, null, new UnsyncByteArrayInputStream(new byte[0]), 0,
				null, null,
				ServiceContextTestUtil.getServiceContext(_group.getGroupId()));
		}
		finally {
			DLAppHelperThreadLocal.setEnabled(enabled);
		}
	}

	@Test(expected = FileExtensionException.class)
	public void testAddFileEntryWhenValidateExtensionEnabled()
		throws Exception {

		boolean enabled = DLAppHelperThreadLocal.isEnabled();

		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					DLConfiguration.class.getName(),
					HashMapDictionaryBuilder.<String, Object>put(
						"fileExtensions", "png"
					).build())) {

			DLAppHelperThreadLocal.setEnabled(true);

			DLFileEntryLocalServiceUtil.addFileEntry(
				null, TestPropsValues.getUserId(), _group.getGroupId(),
				_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				RandomTestUtil.randomString(),
				ContentTypes.APPLICATION_OCTET_STREAM,
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				StringUtil.randomString(), RandomTestUtil.randomString(),
				DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT,
				null, null, new UnsyncByteArrayInputStream(new byte[0]), 0,
				null, null,
				ServiceContextTestUtil.getServiceContext(_group.getGroupId()));
		}
		finally {
			DLAppHelperThreadLocal.setEnabled(enabled);
		}
	}

	@Test
	public void testAddFileEntryWithExpirationDateReviewDateUpdateDeletingThem()
		throws Exception {

		String content = StringUtil.randomString();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		Date expirationDate = new Date();
		Date reviewDate = new Date();

		DLFileEntry dlFileEntry = DLFileEntryLocalServiceUtil.addFileEntry(
			null, TestPropsValues.getUserId(), _group.getGroupId(),
			_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			"file.txt", ContentTypes.TEXT_PLAIN, "file.txt",
			StringUtil.randomString(), StringPool.BLANK, StringPool.BLANK, -1,
			new HashMap<>(), null, new ByteArrayInputStream(content.getBytes()),
			0, expirationDate, reviewDate, serviceContext);

		Assert.assertEquals(expirationDate, dlFileEntry.getExpirationDate());
		Assert.assertEquals(reviewDate, dlFileEntry.getReviewDate());

		DLFileVersion dlFileVersion = dlFileEntry.getFileVersion();

		Assert.assertEquals(expirationDate, dlFileVersion.getExpirationDate());
		Assert.assertEquals(reviewDate, dlFileVersion.getReviewDate());

		dlFileEntry = DLFileEntryLocalServiceUtil.updateFileEntry(
			dlFileEntry.getUserId(), dlFileEntry.getFileEntryId(),
			dlFileEntry.getFileName(), dlFileEntry.getMimeType(),
			dlFileEntry.getTitle(), StringUtil.randomString(),
			dlFileEntry.getTitle(), StringPool.BLANK,
			DLVersionNumberIncrease.fromMajorVersion(false),
			dlFileEntry.getFileEntryTypeId(), new HashMap<>(), null,
			new ByteArrayInputStream(content.getBytes()), 0, null, null,
			serviceContext);

		dlFileVersion = dlFileEntry.getFileVersion();

		Assert.assertNull(dlFileVersion.getExpirationDate());
		Assert.assertNull(dlFileVersion.getReviewDate());

		dlFileEntry = DLFileEntryLocalServiceUtil.updateStatus(
			TestPropsValues.getUserId(), dlFileVersion.getFileVersionId(),
			WorkflowConstants.STATUS_APPROVED, serviceContext, new HashMap<>());

		Assert.assertNull(dlFileEntry.getExpirationDate());
		Assert.assertNull(dlFileEntry.getReviewDate());
	}

	@Test
	public void testAddFileEntryWithExternalReferenceCode() throws Exception {
		String externalReferenceCode = StringUtil.randomString();

		DLFileEntry dlFileEntry = DLFileEntryLocalServiceUtil.addFileEntry(
			externalReferenceCode, TestPropsValues.getUserId(),
			_group.getGroupId(), _group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString(), ContentTypes.TEXT_PLAIN,
			StringUtil.randomString(), StringUtil.randomString(),
			StringPool.BLANK, StringPool.BLANK, -1, new HashMap<>(), null,
			new ByteArrayInputStream(new byte[0]), 0, null, null,
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId()));

		Assert.assertEquals(
			externalReferenceCode, dlFileEntry.getExternalReferenceCode());

		dlFileEntry =
			DLFileEntryLocalServiceUtil.getDLFileEntryByExternalReferenceCode(
				externalReferenceCode, _group.getGroupId());

		Assert.assertEquals(
			externalReferenceCode, dlFileEntry.getExternalReferenceCode());
	}

	@Test
	public void testAddFileEntryWithoutExternalReferenceCode()
		throws Exception {

		DLFileEntry dlFileEntry1 = DLFileEntryLocalServiceUtil.addFileEntry(
			null, TestPropsValues.getUserId(), _group.getGroupId(),
			_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString(), ContentTypes.TEXT_PLAIN,
			StringUtil.randomString(), StringUtil.randomString(),
			StringPool.BLANK, StringPool.BLANK, -1, new HashMap<>(), null,
			new ByteArrayInputStream(new byte[0]), 0, null, null,
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId()));

		String externalReferenceCode = dlFileEntry1.getExternalReferenceCode();

		Assert.assertEquals(externalReferenceCode, dlFileEntry1.getUuid());

		DLFileEntry dlFileEntry2 =
			DLFileEntryLocalServiceUtil.getDLFileEntryByExternalReferenceCode(
				externalReferenceCode, _group.getGroupId());

		Assert.assertEquals(dlFileEntry1, dlFileEntry2);
	}

	@Test
	public void testAddsFileEntryWithExpirationDateReviewDate()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		Date expirationDate = new Date();
		Date reviewDate = new Date();

		DLFileEntry dlFileEntry = DLFileEntryLocalServiceUtil.addFileEntry(
			null, TestPropsValues.getUserId(), _group.getGroupId(),
			_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString(), ContentTypes.TEXT_PLAIN,
			StringUtil.randomString(), StringUtil.randomString(),
			StringPool.BLANK, StringPool.BLANK, -1, new HashMap<>(), null,
			new ByteArrayInputStream(new byte[0]), 0, expirationDate,
			reviewDate, serviceContext);

		Assert.assertEquals(expirationDate, dlFileEntry.getExpirationDate());
		Assert.assertEquals(reviewDate, dlFileEntry.getReviewDate());
	}

	@Test
	public void testAddsFileEntryWithNoFileEntryType() throws Exception {
		DLFileEntry dlFileEntry = DLFileEntryLocalServiceUtil.addFileEntry(
			null, TestPropsValues.getUserId(), _group.getGroupId(),
			_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString(), ContentTypes.TEXT_PLAIN,
			StringUtil.randomString(), StringUtil.randomString(),
			StringPool.BLANK, StringPool.BLANK, -1, new HashMap<>(), null,
			new ByteArrayInputStream(new byte[0]), 0, null, null,
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId()));

		Assert.assertEquals(
			DLFileEntryTypeLocalServiceUtil.getDefaultFileEntryTypeId(
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID),
			dlFileEntry.getFileEntryTypeId());
	}

	@Test
	public void testAddsFileEntryWithoutExpirationDate() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		Date reviewDate = new Date();

		DLFileEntry dlFileEntry = DLFileEntryLocalServiceUtil.addFileEntry(
			null, TestPropsValues.getUserId(), _group.getGroupId(),
			_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString(), ContentTypes.TEXT_PLAIN,
			StringUtil.randomString(), StringUtil.randomString(),
			StringPool.BLANK, StringPool.BLANK, -1, new HashMap<>(), null,
			new ByteArrayInputStream(new byte[0]), 0, null, reviewDate,
			serviceContext);

		Assert.assertNull(dlFileEntry.getExpirationDate());
		Assert.assertEquals(reviewDate, dlFileEntry.getReviewDate());
	}

	@Test
	public void testAddsFileEntryWithoutReviewDate() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		Date expirationDate = new Date();

		DLFileEntry dlFileEntry = DLFileEntryLocalServiceUtil.addFileEntry(
			null, TestPropsValues.getUserId(), _group.getGroupId(),
			_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString(), ContentTypes.TEXT_PLAIN,
			StringUtil.randomString(), StringUtil.randomString(),
			StringPool.BLANK, StringPool.BLANK, -1, new HashMap<>(), null,
			new ByteArrayInputStream(new byte[0]), 0, expirationDate, null,
			serviceContext);

		Assert.assertNull(dlFileEntry.getReviewDate());
		Assert.assertEquals(expirationDate, dlFileEntry.getExpirationDate());
	}

	@Test
	public void testChangeOriginalExtensionAfterChangingTheFileName()
		throws Exception {

		String content = StringUtil.randomString();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		DLFileEntry dlFileEntry = DLFileEntryLocalServiceUtil.addFileEntry(
			null, TestPropsValues.getUserId(), _group.getGroupId(),
			_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			"file.txt", ContentTypes.TEXT_PLAIN, "file.txt",
			StringUtil.randomString(), StringPool.BLANK, StringPool.BLANK, -1,
			new HashMap<>(), null, new ByteArrayInputStream(content.getBytes()),
			0, null, null, serviceContext);

		FileEntry fileEntry = DLAppServiceUtil.updateFileEntry(
			dlFileEntry.getFileEntryId(), "file.pdf", null, "file.txt",
			StringPool.BLANK, StringPool.BLANK, StringPool.BLANK,
			DLVersionNumberIncrease.fromMajorVersion(false), null, 0,
			dlFileEntry.getExpirationDate(), dlFileEntry.getReviewDate(),
			serviceContext);

		Assert.assertEquals(
			content, StringUtil.read(fileEntry.getContentStream()));

		Assert.assertEquals("pdf", fileEntry.getExtension());

		Assert.assertEquals("file.txt", fileEntry.getTitle());

		Assert.assertEquals("file.pdf", fileEntry.getFileName());

		Assert.assertEquals(ContentTypes.TEXT_PLAIN, fileEntry.getMimeType());
	}

	@Test
	public void testCheckinFileEntryDeletesPWC() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		DLFileEntry dlFileEntry = DLFileEntryLocalServiceUtil.addFileEntry(
			null, TestPropsValues.getUserId(), _group.getGroupId(),
			_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString(), ContentTypes.TEXT_PLAIN,
			StringUtil.randomString(), StringUtil.randomString(),
			StringPool.BLANK, StringPool.BLANK, -1, new HashMap<>(), null,
			new ByteArrayInputStream(new byte[0]), 0, null, null,
			serviceContext);

		DLFileEntryLocalServiceUtil.checkOutFileEntry(
			TestPropsValues.getUserId(), dlFileEntry.getFileEntryId(),
			dlFileEntry.getFileEntryTypeId(), serviceContext);

		DLFileEntryLocalServiceUtil.checkInFileEntry(
			TestPropsValues.getUserId(), dlFileEntry.getFileEntryId(),
			DLVersionNumberIncrease.MAJOR, StringPool.BLANK, serviceContext);

		Assert.assertFalse(
			DLStoreUtil.hasFile(
				dlFileEntry.getCompanyId(), dlFileEntry.getDataRepositoryId(),
				dlFileEntry.getName(),
				DLFileEntryConstants.PRIVATE_WORKING_COPY_VERSION));
	}

	@Test
	public void testCheckInFileEntryRefreshesStoreUUID() throws Exception {
		DLFileEntry dlFileEntry = DLFileEntryLocalServiceUtil.addFileEntry(
			null, TestPropsValues.getUserId(), _group.getGroupId(),
			_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString(), ContentTypes.TEXT_PLAIN,
			StringUtil.randomString(), StringUtil.randomString(),
			StringPool.BLANK, StringPool.BLANK, -1, new HashMap<>(), null,
			new ByteArrayInputStream(new byte[0]), 0, null, null,
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId()));

		DLFileVersion dlFileVersion = dlFileEntry.getLatestFileVersion(true);

		String storeUUID1 = dlFileVersion.getStoreUUID();

		Assert.assertNotNull(storeUUID1);

		DLFileEntryLocalServiceUtil.checkOutFileEntry(
			TestPropsValues.getUserId(), dlFileEntry.getFileEntryId(),
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		dlFileVersion = dlFileEntry.getLatestFileVersion(true);

		String storeUUID2 = dlFileVersion.getStoreUUID();

		Assert.assertNotNull(storeUUID2);
		Assert.assertNotEquals(storeUUID1, storeUUID2);

		byte[] bytes = RandomTestUtil.randomBytes();

		DLFileEntryLocalServiceUtil.updateFileEntry(
			TestPropsValues.getUserId(), dlFileEntry.getFileEntryId(),
			StringUtil.randomString(), ContentTypes.APPLICATION_OCTET_STREAM,
			StringUtil.randomString(), StringUtil.randomString(),
			StringPool.BLANK, StringPool.BLANK, DLVersionNumberIncrease.NONE,
			dlFileEntry.getFileEntryTypeId(), Collections.emptyMap(), null,
			new ByteArrayInputStream(bytes), bytes.length, null, null,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		DLFileEntryLocalServiceUtil.checkInFileEntry(
			TestPropsValues.getUserId(), dlFileEntry.getFileEntryId(),
			DLVersionNumberIncrease.NONE, StringPool.BLANK,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		dlFileVersion = dlFileEntry.getLatestFileVersion(true);

		Assert.assertNotNull(dlFileVersion.getStoreUUID());
		Assert.assertNotEquals(storeUUID2, dlFileVersion.getStoreUUID());
	}

	@Test
	public void testCheckoutFileEntryCreatesPWC() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		DLFileEntry dlFileEntry = DLFileEntryLocalServiceUtil.addFileEntry(
			null, TestPropsValues.getUserId(), _group.getGroupId(),
			_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString(), ContentTypes.TEXT_PLAIN,
			StringUtil.randomString(), StringUtil.randomString(),
			StringPool.BLANK, StringPool.BLANK, -1, new HashMap<>(), null,
			new ByteArrayInputStream(new byte[0]), 0, null, null,
			serviceContext);

		DLFileEntryLocalServiceUtil.checkOutFileEntry(
			TestPropsValues.getUserId(), dlFileEntry.getFileEntryId(),
			dlFileEntry.getFileEntryTypeId(), serviceContext);

		DLFileVersion dlFileVersion = dlFileEntry.getLatestFileVersion(true);

		Assert.assertEquals(
			DLFileEntryConstants.PRIVATE_WORKING_COPY_VERSION,
			dlFileVersion.getVersion());
		Assert.assertTrue(
			DLStoreUtil.hasFile(
				dlFileEntry.getCompanyId(), dlFileEntry.getDataRepositoryId(),
				dlFileEntry.getName(), dlFileVersion.getStoreFileName()));
	}

	@Test
	public void testCopyFileEntry() throws Exception {
		ExpandoTable expandoTable =
			ExpandoTableLocalServiceUtil.addDefaultTable(
				PortalUtil.getDefaultCompanyId(), DLFileEntry.class.getName());

		ExpandoColumnLocalServiceUtil.addColumn(
			expandoTable.getTableId(), "ExpandoAttributeName",
			ExpandoColumnConstants.STRING, StringPool.BLANK);

		try {
			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext(
					_group.getGroupId(), TestPropsValues.getUserId());

			Folder folder = DLAppServiceUtil.addFolder(
				null, _group.getGroupId(),
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				serviceContext);

			long fileEntryTypeId = populateServiceContextFileEntryType(
				serviceContext);

			serviceContext.setExpandoBridgeAttributes(
				HashMapBuilder.<String, Serializable>put(
					"ExpandoAttributeName", "ExpandoAttributeValue"
				).build());

			FileEntry fileEntry = DLAppLocalServiceUtil.addFileEntry(
				null, TestPropsValues.getUserId(), _group.getGroupId(),
				folder.getFolderId(), RandomTestUtil.randomString(),
				ContentTypes.TEXT_PLAIN, TestDataConstants.TEST_BYTE_ARRAY,
				null, null, serviceContext);

			serviceContext = ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

			Folder destinationFolder = DLAppServiceUtil.addFolder(
				null, _group.getGroupId(),
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				serviceContext);

			DLFileEntry copyDLFileEntry =
				DLFileEntryLocalServiceUtil.copyFileEntry(
					TestPropsValues.getUserId(), _group.getGroupId(),
					_group.getGroupId(), fileEntry.getFileEntryId(),
					destinationFolder.getFolderId(), null, serviceContext);

			ExpandoBridge expandoBridge = copyDLFileEntry.getExpandoBridge();

			String attributeValue = GetterUtil.getString(
				expandoBridge.getAttribute("ExpandoAttributeName"));

			Assert.assertEquals("ExpandoAttributeValue", attributeValue);

			Assert.assertEquals(
				fileEntryTypeId, copyDLFileEntry.getFileEntryTypeId());

			DLFileVersion copyDLFileVersion = copyDLFileEntry.getFileVersion();

			DLFileEntryType dlFileEntryType =
				DLFileEntryTypeLocalServiceUtil.getFileEntryType(
					copyDLFileVersion.getFileEntryTypeId());

			List<DDMStructure> copyDDMStructures =
				DLFileEntryTypeUtil.getDDMStructures(dlFileEntryType);

			DDMStructure copyDDMStructure = copyDDMStructures.get(0);

			DLFileEntryMetadata dlFileEntryMetadata =
				DLFileEntryMetadataLocalServiceUtil.getFileEntryMetadata(
					copyDDMStructure.getStructureId(),
					copyDLFileVersion.getFileVersionId());

			DDMFormValues copyDDMFormValues =
				_ddmStorageEngineManager.getDDMFormValues(
					dlFileEntryMetadata.getDDMStorageId());

			List<DDMFormFieldValue> ddmFormFieldValues =
				copyDDMFormValues.getDDMFormFieldValues();

			DDMFormFieldValue ddmFormFieldValue = ddmFormFieldValues.get(0);

			Value value = ddmFormFieldValue.getValue();

			Assert.assertEquals("Text1", ddmFormFieldValue.getName());

			Assert.assertEquals("Text 1 Value", value.getString(LocaleUtil.US));
		}
		finally {
			ExpandoTableLocalServiceUtil.deleteTable(expandoTable);
		}
	}

	@Test
	public void testDeleteFileEntries() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		Folder folder = DLAppServiceUtil.addFolder(
			null, _group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			serviceContext);

		for (int i = 0; i < 20; i++) {
			FileEntry fileEntry = DLAppLocalServiceUtil.addFileEntry(
				null, TestPropsValues.getUserId(), _group.getGroupId(),
				folder.getFolderId(), RandomTestUtil.randomString(),
				ContentTypes.TEXT_PLAIN, TestDataConstants.TEST_BYTE_ARRAY,
				null, null, serviceContext);

			LocalRepository localRepository =
				RepositoryProviderUtil.getFileEntryLocalRepository(
					fileEntry.getFileEntryId());

			DLTrashLocalServiceUtil.moveFileEntryToTrash(
				TestPropsValues.getUserId(), localRepository.getRepositoryId(),
				fileEntry.getFileEntryId());
		}

		for (int i = 0; i < IntervalActionProcessor.INTERVAL_DEFAULT; i++) {
			DLAppLocalServiceUtil.addFileEntry(
				null, TestPropsValues.getUserId(), _group.getGroupId(),
				folder.getFolderId(), RandomTestUtil.randomString(),
				ContentTypes.TEXT_PLAIN, TestDataConstants.TEST_BYTE_ARRAY,
				null, null, serviceContext);
		}

		DLFileEntryLocalServiceUtil.deleteFileEntries(
			_group.getGroupId(), folder.getFolderId(), false);

		Assert.assertEquals(
			20,
			DLFileEntryLocalServiceUtil.getFileEntriesCount(
				_group.getGroupId(), folder.getFolderId()));
	}

	@Test(expected = InvalidFileVersionException.class)
	public void testDoesNotDeleteUnapprovedVersion() throws Exception {
		DLFileEntry dlFileEntry = DLFileEntryLocalServiceUtil.addFileEntry(
			null, TestPropsValues.getUserId(), _group.getGroupId(),
			_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString(), ContentTypes.TEXT_PLAIN,
			StringUtil.randomString(), StringUtil.randomString(),
			StringPool.BLANK, StringPool.BLANK,
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT,
			new HashMap<>(), null, new ByteArrayInputStream(new byte[0]), 0,
			null, null,
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId()));

		DLFileVersion dlFileVersion = dlFileEntry.getLatestFileVersion(true);

		DLFileEntryLocalServiceUtil.deleteFileVersion(
			TestPropsValues.getUserId(), dlFileEntry.getFileEntryId(),
			dlFileVersion.getVersion());
	}

	@Test(expected = DuplicateFileEntryExternalReferenceCodeException.class)
	public void testDuplicateFileEntryExternalReferenceCode() throws Exception {
		DLFolder dlFolder = DLTestUtil.addDLFolder(_group.getGroupId());
		String externalReferenceCode = StringUtil.randomString();
		Map<String, com.liferay.dynamic.data.mapping.kernel.DDMFormValues>
			ddmFormValuesMap = Collections.emptyMap();
		InputStream inputStream = new ByteArrayInputStream(new byte[0]);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		DLFileEntryLocalServiceUtil.addFileEntry(
			externalReferenceCode, TestPropsValues.getUserId(),
			dlFolder.getGroupId(), dlFolder.getRepositoryId(),
			dlFolder.getFolderId(), StringUtil.randomString(),
			ContentTypes.TEXT_PLAIN, StringUtil.randomString(),
			StringUtil.randomString(), StringPool.BLANK, StringPool.BLANK,
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT,
			ddmFormValuesMap, null, inputStream, 0, null, null, serviceContext);

		DLFileEntryLocalServiceUtil.addFileEntry(
			externalReferenceCode, TestPropsValues.getUserId(),
			dlFolder.getGroupId(), dlFolder.getRepositoryId(),
			dlFolder.getFolderId(), StringUtil.randomString(),
			ContentTypes.TEXT_PLAIN, StringUtil.randomString(),
			StringUtil.randomString(), StringPool.BLANK, StringPool.BLANK,
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT,
			ddmFormValuesMap, null, inputStream, 0, null, null, serviceContext);
	}

	@Test
	public void testDuplicateFileIsIgnored() throws Exception {
		DLFolder dlFolder = DLTestUtil.addDLFolder(_group.getGroupId());
		Map<String, com.liferay.dynamic.data.mapping.kernel.DDMFormValues>
			ddmFormValuesMap = Collections.emptyMap();
		InputStream inputStream = new ByteArrayInputStream(new byte[0]);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		DLFileEntry dlFileEntry = addAndApproveFileEntry(
			dlFolder, ddmFormValuesMap, inputStream, serviceContext);

		DLStoreUtil.updateFile(
			DLStoreRequest.builder(
				dlFileEntry.getCompanyId(), dlFileEntry.getRepositoryId(),
				dlFileEntry.getName()
			).fileExtension(
				dlFileEntry.getExtension()
			).sourceFileName(
				StringUtil.randomString()
			).validateFileExtension(
				false
			).versionLabel(
				"2.0"
			).build(),
			inputStream);

		dlFileEntry = updateAndApproveDLFileEntry(
			dlFileEntry, inputStream, ddmFormValuesMap, serviceContext);

		dlFileEntry = DLFileEntryLocalServiceUtil.getFileEntry(
			dlFileEntry.getFileEntryId());

		Assert.assertEquals("2.0", dlFileEntry.getVersion());
	}

	@Test(expected = DuplicateFileEntryException.class)
	public void testDuplicateTitleFileEntry() throws Exception {
		DLFolder dlFolder = DLTestUtil.addDLFolder(_group.getGroupId());
		String title = StringUtil.randomString();
		Map<String, com.liferay.dynamic.data.mapping.kernel.DDMFormValues>
			ddmFormValuesMap = Collections.emptyMap();
		InputStream inputStream = new ByteArrayInputStream(new byte[0]);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		DLFileEntryLocalServiceUtil.addFileEntry(
			null, TestPropsValues.getUserId(), dlFolder.getGroupId(),
			dlFolder.getRepositoryId(), dlFolder.getFolderId(),
			StringUtil.randomString(), ContentTypes.TEXT_PLAIN, title,
			StringUtil.randomString(), StringPool.BLANK, StringPool.BLANK,
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT,
			ddmFormValuesMap, null, inputStream, 0, null, null, serviceContext);

		DLFileEntryLocalServiceUtil.addFileEntry(
			null, TestPropsValues.getUserId(), dlFolder.getGroupId(),
			dlFolder.getRepositoryId(), dlFolder.getFolderId(),
			StringUtil.randomString(), ContentTypes.TEXT_PLAIN, title,
			StringUtil.randomString(), StringPool.BLANK, StringPool.BLANK,
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT,
			ddmFormValuesMap, null, inputStream, 0, null, null, serviceContext);
	}

	@Test
	public void testExtensionValidationWithSystemScopedFileEntryType()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		DDMStructure ddmStructure = DDMStructureTestUtil.addStructure(
			_group.getGroupId(), DLFileEntryMetadata.class.getName());

		DLFileEntryType dlFileEntryType =
			DLFileEntryTypeLocalServiceUtil.addFileEntryType(
				TestPropsValues.getUserId(), _group.getGroupId(),
				ddmStructure.getStructureId(), null,
				Collections.singletonMap(LocaleUtil.US, "New File Entry Type"),
				Collections.singletonMap(LocaleUtil.US, "New File Entry Type"),
				DLFileEntryTypeConstants.FILE_ENTRY_TYPE_SCOPE_DEFAULT,
				serviceContext);

		dlFileEntryType.setScope(
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_SCOPE_SYSTEM);

		dlFileEntryType = DLFileEntryTypeLocalServiceUtil.updateDLFileEntryType(
			dlFileEntryType);

		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					DLConfiguration.class.getName(),
					HashMapDictionaryBuilder.<String, Object>put(
						"fileExtensions", ".jpg"
					).build())) {

			DLFileEntryLocalServiceUtil.addFileEntry(
				null, TestPropsValues.getUserId(), _group.getGroupId(),
				_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				RandomTestUtil.randomString(),
				ContentTypes.APPLICATION_OCTET_STREAM,
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				StringUtil.randomString(), RandomTestUtil.randomString(),
				dlFileEntryType.getFileEntryTypeId(), null, null,
				new UnsyncByteArrayInputStream(new byte[0]), 0, null, null,
				serviceContext);
		}
	}

	@Test
	public void testFileNameUpdatedWhenUpdatingAFileEntryKeepingFileVersionLabel()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		FileEntry fileEntry = DLAppLocalServiceUtil.addFileEntry(
			null, TestPropsValues.getUserId(), _group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, StringPool.BLANK,
			ContentTypes.TEXT_PLAIN, "FE1.exe", StringPool.BLANK,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			(byte[])null, null, null, serviceContext);

		Assert.assertEquals("FE1.exe", fileEntry.getFileName());

		FileVersion fileVersion = fileEntry.getFileVersion();

		Assert.assertEquals("FE1.exe", fileVersion.getFileName());

		fileEntry = DLAppLocalServiceUtil.updateFileEntry(
			TestPropsValues.getUserId(), fileEntry.getFileEntryId(), "FE2.txt",
			ContentTypes.TEXT_PLAIN, "FE1.exe", StringPool.BLANK,
			fileEntry.getDescription(), RandomTestUtil.randomString(),
			DLVersionNumberIncrease.MINOR, TestDataConstants.TEST_BYTE_ARRAY,
			fileEntry.getExpirationDate(), fileEntry.getReviewDate(),
			serviceContext);

		Assert.assertEquals("FE2.txt", fileEntry.getFileName());

		fileVersion = fileEntry.getFileVersion();

		Assert.assertEquals("FE2.txt", fileVersion.getFileName());
	}

	@Test
	public void testGetNoAssetEntries() throws Exception {
		DLFolder dlFolder = DLTestUtil.addDLFolder(_group.getGroupId());

		byte[] bytes = TestDataConstants.TEST_BYTE_ARRAY;

		InputStream inputStream = new ByteArrayInputStream(bytes);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(dlFolder.getGroupId());

		FileEntry assetFileEntry = DLAppLocalServiceUtil.addFileEntry(
			null, TestPropsValues.getUserId(), dlFolder.getRepositoryId(),
			dlFolder.getFolderId(), RandomTestUtil.randomString(),
			ContentTypes.TEXT_PLAIN, RandomTestUtil.randomString(),
			StringPool.BLANK, StringPool.BLANK, StringPool.BLANK, inputStream,
			bytes.length, null, null, serviceContext);

		inputStream = new ByteArrayInputStream(bytes);

		FileEntry noAssetFileEntry = DLAppLocalServiceUtil.addFileEntry(
			null, TestPropsValues.getUserId(), dlFolder.getRepositoryId(),
			dlFolder.getFolderId(), RandomTestUtil.randomString(),
			ContentTypes.TEXT_PLAIN, RandomTestUtil.randomString(),
			StringPool.BLANK, StringPool.BLANK, StringPool.BLANK, inputStream,
			bytes.length, null, null, serviceContext);

		AssetEntry assetEntry = AssetEntryLocalServiceUtil.fetchEntry(
			DLFileEntry.class.getName(), noAssetFileEntry.getFileEntryId());

		Assert.assertNotNull(assetEntry);

		AssetEntryLocalServiceUtil.deleteAssetEntry(assetEntry);

		List<DLFileEntry> dlFileEntries =
			DLFileEntryLocalServiceUtil.getNoAssetFileEntries();

		Assert.assertFalse(dlFileEntries.contains(assetFileEntry.getModel()));
		Assert.assertTrue(dlFileEntries.contains(noAssetFileEntry.getModel()));
	}

	@Test
	public void testKeepsOriginalExtensionAfterChangingTheTitle()
		throws Exception {

		String content = StringUtil.randomString();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		DLFileEntry dlFileEntry = DLFileEntryLocalServiceUtil.addFileEntry(
			null, TestPropsValues.getUserId(), _group.getGroupId(),
			_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			"file.txt", ContentTypes.TEXT_PLAIN, "file.txt",
			StringUtil.randomString(), StringPool.BLANK, StringPool.BLANK, -1,
			new HashMap<>(), null, new ByteArrayInputStream(content.getBytes()),
			0, null, null, serviceContext);

		FileEntry fileEntry = DLAppServiceUtil.updateFileEntry(
			dlFileEntry.getFileEntryId(), "file.txt", null, "file.pdf",
			StringPool.BLANK, StringPool.BLANK, StringPool.BLANK,
			DLVersionNumberIncrease.fromMajorVersion(false), null, 0,
			dlFileEntry.getExpirationDate(), dlFileEntry.getReviewDate(),
			serviceContext);

		Assert.assertEquals(
			content, StringUtil.read(fileEntry.getContentStream()));
		Assert.assertEquals("txt", fileEntry.getExtension());
		Assert.assertEquals(ContentTypes.TEXT_PLAIN, fileEntry.getMimeType());
	}

	@Test
	public void testMoveFileEntryToFolderNotEmpty() throws Exception {
		DLFolder originDLFolder = DLTestUtil.addDLFolder(_group.getGroupId());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		DLFileEntry dlFileEntry = DLFileEntryLocalServiceUtil.addFileEntry(
			null, TestPropsValues.getUserId(), originDLFolder.getGroupId(),
			originDLFolder.getRepositoryId(), originDLFolder.getFolderId(),
			StringUtil.randomString(), ContentTypes.TEXT_PLAIN,
			StringUtil.randomString(), StringUtil.randomString(),
			StringPool.BLANK, StringPool.BLANK,
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT,
			new HashMap<>(), null, new ByteArrayInputStream(new byte[0]), 0,
			null, null, serviceContext);

		DLFolder destinationDLFolder = DLTestUtil.addDLFolder(
			_group.getGroupId());

		DLFileEntryLocalServiceUtil.addFileEntry(
			null, TestPropsValues.getUserId(), destinationDLFolder.getGroupId(),
			destinationDLFolder.getRepositoryId(),
			destinationDLFolder.getFolderId(), StringUtil.randomString(),
			ContentTypes.TEXT_PLAIN, StringUtil.randomString(),
			StringUtil.randomString(), StringPool.BLANK, StringPool.BLANK,
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT,
			new HashMap<>(), null, new ByteArrayInputStream(new byte[0]), 0,
			null, null, serviceContext);

		DLFileEntryLocalServiceUtil.moveFileEntry(
			TestPropsValues.getUserId(), dlFileEntry.getFileEntryId(),
			destinationDLFolder.getFolderId(), serviceContext);

		dlFileEntry = DLFileEntryLocalServiceUtil.getDLFileEntry(
			dlFileEntry.getFileEntryId());

		DLFolder dlFileEntryFolder = dlFileEntry.getFolder();

		Assert.assertEquals(
			dlFileEntryFolder.getFolderId(), destinationDLFolder.getFolderId());
	}

	@Test(expected = DuplicateFileEntryException.class)
	public void testMoveFileEntryToFolderWithSameFileName() throws Exception {
		DLFolder originDLFolder = DLTestUtil.addDLFolder(_group.getGroupId());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		String title = StringUtil.randomString();

		DLFileEntry dlFileEntry = DLFileEntryLocalServiceUtil.addFileEntry(
			null, TestPropsValues.getUserId(), originDLFolder.getGroupId(),
			originDLFolder.getRepositoryId(), originDLFolder.getFolderId(),
			StringUtil.randomString(), ContentTypes.TEXT_PLAIN, title,
			StringUtil.randomString(), StringPool.BLANK, StringPool.BLANK,
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT,
			new HashMap<>(), null, new ByteArrayInputStream(new byte[0]), 0,
			null, null, serviceContext);

		DLFolder destinationDLFolder = DLTestUtil.addDLFolder(
			_group.getGroupId());

		DLFileEntryLocalServiceUtil.addFileEntry(
			null, TestPropsValues.getUserId(), destinationDLFolder.getGroupId(),
			destinationDLFolder.getRepositoryId(),
			destinationDLFolder.getFolderId(), StringUtil.randomString(),
			ContentTypes.TEXT_PLAIN, title, StringUtil.randomString(),
			StringPool.BLANK, StringPool.BLANK,
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT,
			new HashMap<>(), null, new ByteArrayInputStream(new byte[0]), 0,
			null, null, serviceContext);

		DLFileEntryLocalServiceUtil.moveFileEntry(
			TestPropsValues.getUserId(), dlFileEntry.getFileEntryId(),
			destinationDLFolder.getFolderId(), serviceContext);
	}

	@Test(expected = NoSuchFolderException.class)
	public void testMoveFileEntryToInvalidDLFolder() throws Exception {
		DLFolder originDLFolder = DLTestUtil.addDLFolder(_group.getGroupId());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		DLFileEntry dlFileEntry = DLFileEntryLocalServiceUtil.addFileEntry(
			null, TestPropsValues.getUserId(), originDLFolder.getGroupId(),
			originDLFolder.getRepositoryId(), originDLFolder.getFolderId(),
			StringUtil.randomString(), ContentTypes.TEXT_PLAIN,
			StringUtil.randomString(), StringUtil.randomString(),
			StringPool.BLANK, StringPool.BLANK,
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT,
			new HashMap<>(), null, new ByteArrayInputStream(new byte[0]), 0,
			null, null, serviceContext);

		Group destinationGroup = GroupTestUtil.addGroup();

		DLFolder destinationDLFolder = DLTestUtil.addDLFolder(
			destinationGroup.getGroupId());

		try {
			DLFileEntryLocalServiceUtil.moveFileEntry(
				TestPropsValues.getUserId(), dlFileEntry.getFileEntryId(),
				destinationDLFolder.getFolderId(), serviceContext);
		}
		finally {
			GroupLocalServiceUtil.deleteGroup(destinationGroup);
		}
	}

	@Test
	public void testUpdateExpirationDateReviewDate() throws Exception {
		String content = StringUtil.randomString();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		DLFileEntry dlFileEntry = DLFileEntryLocalServiceUtil.addFileEntry(
			null, TestPropsValues.getUserId(), _group.getGroupId(),
			_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			"file.txt", ContentTypes.TEXT_PLAIN, "file.txt",
			StringUtil.randomString(), StringPool.BLANK, StringPool.BLANK, -1,
			new HashMap<>(), null, new ByteArrayInputStream(content.getBytes()),
			0, null, null, serviceContext);

		Assert.assertNull(dlFileEntry.getExpirationDate());
		Assert.assertNull(dlFileEntry.getReviewDate());

		DLFileVersion dlFileVersion = dlFileEntry.getFileVersion();

		Assert.assertNull(dlFileVersion.getExpirationDate());
		Assert.assertNull(dlFileVersion.getReviewDate());

		Date expirationDate = new Date();
		Date reviewDate = new Date();

		dlFileEntry = DLFileEntryLocalServiceUtil.updateFileEntry(
			dlFileEntry.getUserId(), dlFileEntry.getFileEntryId(),
			dlFileEntry.getFileName(), dlFileEntry.getMimeType(),
			dlFileEntry.getTitle(), dlFileEntry.getTitle(),
			StringUtil.randomString(), StringPool.BLANK,
			DLVersionNumberIncrease.fromMajorVersion(false),
			dlFileEntry.getFileEntryTypeId(), new HashMap<>(), null,
			new ByteArrayInputStream(content.getBytes()), 0, expirationDate,
			reviewDate, serviceContext);

		dlFileVersion = dlFileEntry.getFileVersion();

		Assert.assertEquals(expirationDate, dlFileVersion.getExpirationDate());
		Assert.assertEquals(reviewDate, dlFileVersion.getReviewDate());

		dlFileEntry = DLFileEntryLocalServiceUtil.updateStatus(
			TestPropsValues.getUserId(), dlFileVersion.getFileVersionId(),
			WorkflowConstants.STATUS_APPROVED, serviceContext, new HashMap<>());

		Assert.assertEquals(expirationDate, dlFileEntry.getExpirationDate());
		Assert.assertEquals(reviewDate, dlFileEntry.getReviewDate());
	}

	@Test
	public void testUpdateFileEntryRefreshesStoreUUID() throws Exception {
		DLFileEntry dlFileEntry = DLFileEntryLocalServiceUtil.addFileEntry(
			null, TestPropsValues.getUserId(), _group.getGroupId(),
			_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString(), ContentTypes.TEXT_PLAIN,
			StringUtil.randomString(), StringUtil.randomString(),
			StringPool.BLANK, StringPool.BLANK, -1, new HashMap<>(), null,
			new ByteArrayInputStream(new byte[0]), 0, null, null,
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId()));

		DLFileVersion dlFileVersion =
			DLFileVersionLocalServiceUtil.getFileVersion(
				dlFileEntry.getFileEntryId(), dlFileEntry.getVersion());

		String storeUUID = dlFileVersion.getStoreUUID();

		Assert.assertNotNull(storeUUID);

		dlFileEntry = DLFileEntryLocalServiceUtil.updateFileEntry(
			TestPropsValues.getUserId(), dlFileEntry.getFileEntryId(),
			StringUtil.randomString(), ContentTypes.TEXT_PLAIN,
			StringUtil.randomString(), StringUtil.randomString(),
			StringPool.BLANK, StringPool.BLANK, DLVersionNumberIncrease.NONE,
			dlFileEntry.getFileEntryTypeId(), null, null,
			new ByteArrayInputStream(new byte[0]), 0, null, null,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		dlFileVersion = DLFileVersionLocalServiceUtil.getFileVersion(
			dlFileEntry.getFileEntryId(), dlFileEntry.getVersion());

		Assert.assertNotNull(dlFileVersion.getStoreUUID());
		Assert.assertNotEquals(storeUUID, dlFileVersion.getStoreUUID());
	}

	@Test(expected = FileExtensionException.class)
	public void testUpdateFileEntryShouldFailIfSourceFileNameExtensionNotSupported()
		throws Exception {

		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				DLFileEntryServiceTestUtil.getConfigurationTemporarySwapper(
					"fileExtensions", new String[] {".txt"})) {

			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext(
					_group.getGroupId(), TestPropsValues.getUserId());

			DLFileEntry dlFileEntry = DLFileEntryLocalServiceUtil.addFileEntry(
				null, TestPropsValues.getUserId(), _group.getGroupId(),
				_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				"file.txt", ContentTypes.TEXT_PLAIN, "file.txt",
				StringUtil.randomString(), StringPool.BLANK, StringPool.BLANK,
				-1, new HashMap<>(), null,
				new ByteArrayInputStream(new byte[0]), 0, null, null,
				serviceContext);

			DLFileEntryLocalServiceUtil.updateFileEntry(
				dlFileEntry.getUserId(), dlFileEntry.getFileEntryId(),
				"file.jpg", dlFileEntry.getMimeType(), dlFileEntry.getTitle(),
				dlFileEntry.getTitle(), StringUtil.randomString(),
				StringPool.BLANK,
				DLVersionNumberIncrease.fromMajorVersion(false),
				dlFileEntry.getFileEntryTypeId(), new HashMap<>(), null,
				new ByteArrayInputStream(new byte[0]), 0, null, null,
				serviceContext);
		}
	}

	@Test(expected = DuplicateFolderNameException.class)
	public void testValidateFileFailsWithAnExistingFolder() throws Exception {
		Folder folder = DLAppServiceUtil.addFolder(
			null, _group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId()));

		DLFileEntryLocalServiceUtil.validateFile(
			_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, -1,
			RandomTestUtil.randomString(), folder.getName());
	}

	@Test
	public void testVerifyFileEntryCheckout() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		DLFileEntry dlFileEntry = DLFileEntryLocalServiceUtil.addFileEntry(
			null, TestPropsValues.getUserId(), _group.getGroupId(),
			_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString(), ContentTypes.TEXT_PLAIN,
			StringUtil.randomString(), StringUtil.randomString(),
			StringPool.BLANK, StringPool.BLANK, -1, new HashMap<>(), null,
			new ByteArrayInputStream(new byte[0]), 0, null, null,
			serviceContext);

		DLFileEntryLocalServiceUtil.checkOutFileEntry(
			TestPropsValues.getUserId(), dlFileEntry.getFileEntryId(),
			dlFileEntry.getFileEntryTypeId(), serviceContext);

		Lock lock = LockManagerUtil.getLock(
			DLFileEntry.class.getName(), dlFileEntry.getFileEntryId());

		Assert.assertTrue(
			DLFileEntryLocalServiceUtil.verifyFileEntryCheckOut(
				dlFileEntry.getFileEntryId(), lock.getUuid()));
	}

	@Test(expected = NoSuchLockException.class)
	public void testVerifyFileEntryCheckoutWithUnlockedFileEntry()
		throws Exception {

		DLFileEntry dlFileEntry = DLFileEntryLocalServiceUtil.addFileEntry(
			null, TestPropsValues.getUserId(), _group.getGroupId(),
			_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString(), ContentTypes.TEXT_PLAIN,
			StringUtil.randomString(), StringUtil.randomString(),
			StringPool.BLANK, StringPool.BLANK, -1, new HashMap<>(), null,
			new ByteArrayInputStream(new byte[0]), 0, null, null,
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId()));

		DLFileEntryLocalServiceUtil.verifyFileEntryCheckOut(
			dlFileEntry.getFileEntryId(), RandomTestUtil.randomString());
	}

	@Test
	public void testVerifyFileEntryCheckoutWithWrongLock() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		DLFileEntry dlFileEntry1 = DLFileEntryLocalServiceUtil.addFileEntry(
			null, TestPropsValues.getUserId(), _group.getGroupId(),
			_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString(), ContentTypes.TEXT_PLAIN,
			StringUtil.randomString(), StringUtil.randomString(),
			StringPool.BLANK, StringPool.BLANK, -1, new HashMap<>(), null,
			new ByteArrayInputStream(new byte[0]), 0, null, null,
			serviceContext);

		DLFileEntryLocalServiceUtil.checkOutFileEntry(
			TestPropsValues.getUserId(), dlFileEntry1.getFileEntryId(),
			dlFileEntry1.getFileEntryTypeId(), serviceContext);

		DLFileEntry dlFileEntry2 = DLFileEntryLocalServiceUtil.addFileEntry(
			null, TestPropsValues.getUserId(), _group.getGroupId(),
			_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString(), ContentTypes.TEXT_PLAIN,
			StringUtil.randomString(), StringUtil.randomString(),
			StringPool.BLANK, StringPool.BLANK, -1, new HashMap<>(), null,
			new ByteArrayInputStream(new byte[0]), 0, null, null,
			serviceContext);

		DLFileEntryLocalServiceUtil.checkOutFileEntry(
			TestPropsValues.getUserId(), dlFileEntry2.getFileEntryId(),
			dlFileEntry2.getFileEntryTypeId(), serviceContext);

		Lock dlFileEntry2LockUuid = LockManagerUtil.getLock(
			DLFileEntry.class.getName(), dlFileEntry2.getFileEntryId());

		Assert.assertFalse(
			DLFileEntryLocalServiceUtil.verifyFileEntryCheckOut(
				dlFileEntry1.getFileEntryId(), dlFileEntry2LockUuid.getUuid()));
	}

	protected DLFileEntry addAndApproveFileEntry(
			DLFolder dlFolder,
			Map<String, com.liferay.dynamic.data.mapping.kernel.DDMFormValues>
				ddmFormValuesMap,
			InputStream inputStream, ServiceContext serviceContext)
		throws Exception {

		DLFileEntry dlFileEntry = DLFileEntryLocalServiceUtil.addFileEntry(
			null, TestPropsValues.getUserId(), dlFolder.getGroupId(),
			dlFolder.getRepositoryId(), dlFolder.getFolderId(),
			StringUtil.randomString(), ContentTypes.TEXT_PLAIN,
			StringUtil.randomString(), StringUtil.randomString(),
			StringPool.BLANK, StringPool.BLANK,
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT,
			ddmFormValuesMap, null, inputStream, 0, null, null, serviceContext);

		DLFileVersion dlFileVersion = dlFileEntry.getLatestFileVersion(true);

		return DLFileEntryLocalServiceUtil.updateStatus(
			TestPropsValues.getUserId(), dlFileVersion.getFileVersionId(),
			WorkflowConstants.STATUS_APPROVED, serviceContext, new HashMap<>());
	}

	protected DDMForm createDDMForm() {
		DDMForm ddmForm = new DDMForm();

		ddmForm.addAvailableLocale(LocaleUtil.US);

		DDMFormField ddmFormField = new DDMFormField("Text1", "text");

		ddmFormField.setDataType("string");

		LocalizedValue label = new LocalizedValue(LocaleUtil.US);

		label.addString(LocaleUtil.US, "Text1");

		ddmFormField.setLabel(label);

		ddmFormField.setLocalizable(false);

		ddmForm.addDDMFormField(ddmFormField);

		ddmForm.setDefaultLocale(LocaleUtil.US);

		return ddmForm;
	}

	protected DDMFormValues createDDMFormValues() throws Exception {
		DDMForm ddmForm = createDDMForm();

		DDMFormValues ddmFormValues = new DDMFormValues(ddmForm);

		ddmFormValues.addAvailableLocale(LocaleUtil.US);

		DDMFormFieldValue ddmFormFieldValue = new DDMFormFieldValue();

		ddmFormFieldValue.setInstanceId("baga");
		ddmFormFieldValue.setName("Text1");
		ddmFormFieldValue.setValue(new UnlocalizedValue("Text 1 Value"));

		ddmFormValues.addDDMFormFieldValue(ddmFormFieldValue);

		ddmFormValues.setDefaultLocale(LocaleUtil.US);

		return ddmFormValues;
	}

	protected long populateServiceContextFileEntryType(
			ServiceContext serviceContext)
		throws Exception {

		DDMStructure ddmStructure = DDMStructureTestUtil.addStructure(
			_group.getGroupId(), DLFileEntryMetadata.class.getName(),
			createDDMForm(), LocaleUtil.US);

		DLFileEntryType dlFileEntryType =
			DLFileEntryTypeLocalServiceUtil.addFileEntryType(
				TestPropsValues.getUserId(), _group.getGroupId(),
				ddmStructure.getStructureId(), null,
				Collections.singletonMap(LocaleUtil.US, "New File Entry Type"),
				Collections.singletonMap(LocaleUtil.US, "New File Entry Type"),
				DLFileEntryTypeConstants.FILE_ENTRY_TYPE_SCOPE_DEFAULT,
				serviceContext);

		serviceContext.setAttribute(
			"fileEntryTypeId", dlFileEntryType.getFileEntryTypeId());

		DDMFormValues ddmFormValues = createDDMFormValues();

		String className =
			com.liferay.dynamic.data.mapping.kernel.DDMFormValues.class.
				getName();

		serviceContext.setAttribute(
			className + StringPool.POUND + ddmStructure.getStructureId(),
			DDMBeanTranslatorUtil.translate(ddmFormValues));

		return dlFileEntryType.getFileEntryTypeId();
	}

	protected DLFileEntry updateAndApproveDLFileEntry(
			DLFileEntry dlFileEntry, InputStream inputStream,
			Map<String, com.liferay.dynamic.data.mapping.kernel.DDMFormValues>
				ddmFormValuesMap,
			ServiceContext serviceContext)
		throws Exception {

		dlFileEntry = DLFileEntryLocalServiceUtil.updateFileEntry(
			TestPropsValues.getUserId(), dlFileEntry.getFileEntryId(),
			StringUtil.randomString(), ContentTypes.TEXT_PLAIN,
			StringUtil.randomString(), StringUtil.randomString(),
			StringPool.BLANK, StringPool.BLANK, DLVersionNumberIncrease.MAJOR,
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT,
			ddmFormValuesMap, null, inputStream, 0,
			dlFileEntry.getExpirationDate(), dlFileEntry.getReviewDate(),
			serviceContext);

		DLFileVersion dlFileVersion = dlFileEntry.getLatestFileVersion(true);

		return DLFileEntryLocalServiceUtil.updateStatus(
			TestPropsValues.getUserId(), dlFileVersion.getFileVersionId(),
			WorkflowConstants.STATUS_APPROVED, serviceContext, new HashMap<>());
	}

	@Inject
	private DDMStorageEngineManager _ddmStorageEngineManager;

	@DeleteAfterTestRun
	private Group _group;

}