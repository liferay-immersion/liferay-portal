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

import com.liferay.document.library.kernel.model.DLFileVersion;
import com.liferay.document.library.kernel.model.DLVersionNumberIncrease;
import com.liferay.document.library.kernel.service.DLFileVersionLocalService;
import com.liferay.document.library.versioning.VersioningPolicy;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.DigesterUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;
import java.io.InputStream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	property = "service.ranking:Integer=100", service = VersioningPolicy.class
)
public class ContentVersioningPolicy implements VersioningPolicy {

	@Override
	public DLVersionNumberIncrease computeDLVersionNumberIncrease(
		DLFileVersion previousDLFileVersion, DLFileVersion nextDLFileVersion) {

		long previousSize = previousDLFileVersion.getSize();
		long nextSize = nextDLFileVersion.getSize();

		if ((previousSize == 0) && (nextSize >= 0)) {
			return null;
		}

		if (previousSize != nextSize) {
			return DLVersionNumberIncrease.MAJOR;
		}

		String previousChecksum = _computeChecksum(previousDLFileVersion);

		if (previousChecksum == null) {
			return null;
		}

		String nextChecksum = _computeChecksum(nextDLFileVersion);

		if ((nextChecksum == null) || previousChecksum.equals(nextChecksum)) {
			return null;
		}

		return DLVersionNumberIncrease.MAJOR;
	}

	private String _computeChecksum(DLFileVersion dlFileVersion) {
		if (Validator.isNotNull(dlFileVersion.getChecksum())) {
			return dlFileVersion.getChecksum();
		}

		try (InputStream inputStream = dlFileVersion.getContentStream(false)) {
			dlFileVersion.setChecksum(DigesterUtil.digestBase64(inputStream));

			dlFileVersion = _dlFileVersionLocalService.updateDLFileVersion(
				dlFileVersion);

			return dlFileVersion.getChecksum();
		}
		catch (IOException | PortalException exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception);
			}

			return null;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ContentVersioningPolicy.class);

	@Reference
	private DLFileVersionLocalService _dlFileVersionLocalService;

}