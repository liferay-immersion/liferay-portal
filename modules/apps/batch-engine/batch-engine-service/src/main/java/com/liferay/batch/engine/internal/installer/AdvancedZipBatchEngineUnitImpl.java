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

package com.liferay.batch.engine.internal.installer;

import com.liferay.batch.engine.internal.json.AdvancedJSONReader;
import com.liferay.batch.engine.unit.BatchEngineUnit;
import com.liferay.batch.engine.unit.BatchEngineUnitConfiguration;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * @author Igor Beslic
 */
public class AdvancedZipBatchEngineUnitImpl implements BatchEngineUnit {

	public AdvancedZipBatchEngineUnitImpl(ZipFile zipFile, ZipEntry zipEntry) {
		_zipFile = zipFile;
		_zipEntry = zipEntry;
	}

	@Override
	public BatchEngineUnitConfiguration getBatchEngineUnitConfiguration()
		throws IOException {

		try (InputStream inputStream = _zipFile.getInputStream(_zipEntry)) {
			AdvancedJSONReader<BatchEngineUnitConfiguration>
				advancedJSONReader = new AdvancedJSONReader<>(inputStream);

			return advancedJSONReader.getObject(
				"configuration", BatchEngineUnitConfiguration.class);
		}
	}

	@Override
	public InputStream getConfigurationInputStream() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getDataFileName() {
		return _zipEntry.getName();
	}

	@Override
	public InputStream getDataInputStream() throws IOException {
		try (InputStream inputStream = _zipFile.getInputStream(_zipEntry)) {
			ByteArrayOutputStream byteArrayOutputStream =
				new ByteArrayOutputStream();

			AdvancedJSONReader<?> advancedJSONReader = new AdvancedJSONReader<>(
				inputStream);

			advancedJSONReader.transferJSONArray(
				"items", byteArrayOutputStream);

			return new ByteArrayInputStream(
				byteArrayOutputStream.toByteArray());
		}
	}

	@Override
	public String getFileName() {
		return _zipFile.getName();
	}

	@Override
	public boolean isValid() {
		if (_zipEntry == null) {
			return false;
		}

		try (InputStream inputStream = _zipFile.getInputStream(_zipEntry)) {
			AdvancedJSONReader<?> advancedJSONReader = new AdvancedJSONReader<>(
				inputStream);

			return advancedJSONReader.hasKey("items");
		}
		catch (IOException ioException) {
			_log.error(
				"Unable to get data in file " + _zipEntry.getName(),
				ioException);
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AdvancedZipBatchEngineUnitImpl.class);

	private final ZipEntry _zipEntry;
	private final ZipFile _zipFile;

}