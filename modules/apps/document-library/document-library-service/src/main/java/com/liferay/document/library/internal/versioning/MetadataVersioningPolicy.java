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

package com.liferay.document.library.internal.versioning;

import com.liferay.document.library.kernel.model.DLFileEntryMetadata;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.model.DLFileVersion;
import com.liferay.document.library.kernel.model.DLVersionNumberIncrease;
import com.liferay.document.library.kernel.service.DLFileEntryMetadataLocalService;
import com.liferay.document.library.util.DLFileEntryTypeUtil;
import com.liferay.document.library.versioning.VersioningPolicy;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.DDMStorageEngineManager;
import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.Serializable;

import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(service = VersioningPolicy.class)
public class MetadataVersioningPolicy implements VersioningPolicy {

	@Override
	public DLVersionNumberIncrease computeDLVersionNumberIncrease(
		DLFileVersion previousDLFileVersion, DLFileVersion nextDLFileVersion) {

		if (!Objects.equals(
				previousDLFileVersion.getTitle(),
				nextDLFileVersion.getTitle()) ||
			!Objects.equals(
				previousDLFileVersion.getFileName(),
				nextDLFileVersion.getFileName()) ||
			!Objects.equals(
				previousDLFileVersion.getDescription(),
				nextDLFileVersion.getDescription()) ||
			(previousDLFileVersion.getFileEntryTypeId() !=
				nextDLFileVersion.getFileEntryTypeId()) ||
			_isDLFileEntryTypeUpdated(
				previousDLFileVersion, nextDLFileVersion) ||
			_isExpandoUpdated(previousDLFileVersion, nextDLFileVersion)) {

			return DLVersionNumberIncrease.MINOR;
		}

		return null;
	}

	private boolean _isDLFileEntryTypeUpdated(
		DLFileVersion previousDLFileVersion, DLFileVersion nextDLFileVersion) {

		try {
			DLFileEntryType dlFileEntryType =
				previousDLFileVersion.getDLFileEntryType();

			for (DDMStructure ddmStructure :
					DLFileEntryTypeUtil.getDDMStructures(dlFileEntryType)) {

				DLFileEntryMetadata previousFileEntryMetadata =
					_dlFileEntryMetadataLocalService.fetchFileEntryMetadata(
						ddmStructure.getStructureId(),
						previousDLFileVersion.getFileVersionId());

				if (previousFileEntryMetadata == null) {
					return true;
				}

				DLFileEntryMetadata nextFileEntryMetadata =
					_dlFileEntryMetadataLocalService.getFileEntryMetadata(
						ddmStructure.getStructureId(),
						nextDLFileVersion.getFileVersionId());

				DDMFormValues previousDDMFormValues =
					_ddmStorageEngineManager.getDDMFormValues(
						previousFileEntryMetadata.getDDMStorageId());
				DDMFormValues nextDDMFormValues =
					_ddmStorageEngineManager.getDDMFormValues(
						nextFileEntryMetadata.getDDMStorageId());

				if (!previousDDMFormValues.equals(nextDDMFormValues)) {
					return true;
				}
			}

			return false;
		}
		catch (PortalException portalException) {
			if (_log.isWarnEnabled()) {
				_log.warn(portalException);
			}

			return false;
		}
	}

	private boolean _isExpandoUpdated(
		DLFileVersion previousDLFileVersion, DLFileVersion nextDLFileVersion) {

		ExpandoBridge previousExpandoBridge =
			previousDLFileVersion.getExpandoBridge();
		ExpandoBridge nextExpandoBridge = nextDLFileVersion.getExpandoBridge();

		Map<String, Serializable> previousAttributes =
			previousExpandoBridge.getAttributes();
		Map<String, Serializable> nextAttributes =
			nextExpandoBridge.getAttributes();

		if (!previousAttributes.equals(nextAttributes)) {
			return true;
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MetadataVersioningPolicy.class);

	@Reference
	private DDMStorageEngineManager _ddmStorageEngineManager;

	@Reference
	private DLFileEntryMetadataLocalService _dlFileEntryMetadataLocalService;

}