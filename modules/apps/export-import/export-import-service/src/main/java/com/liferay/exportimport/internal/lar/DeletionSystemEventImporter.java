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

package com.liferay.exportimport.internal.lar;

import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.xml.SecureXMLFactoryProviderUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.ElementHandler;
import com.liferay.portal.kernel.xml.ElementProcessor;

import java.io.StringReader;

import java.util.Set;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

/**
 * @author Zsolt Berentey
 */
public class DeletionSystemEventImporter {

	public static DeletionSystemEventImporter getInstance() {
		return _deletionSystemEventImporter;
	}

	public void importDeletionSystemEvents(
			final PortletDataContext portletDataContext)
		throws Exception {

		if (!MapUtil.getBoolean(
				portletDataContext.getParameterMap(),
				PortletDataHandlerKeys.DELETIONS)) {

			return;
		}

		String xml = portletDataContext.getZipEntryAsString(
			ExportImportPathUtil.getSourceRootPath(portletDataContext) +
				"/deletion-system-events.xml");

		if (xml == null) {
			return;
		}

		XMLReader xmlReader = SecureXMLFactoryProviderUtil.newXMLReader();

		ElementHandler elementHandler = new ElementHandler(
			new ElementProcessor() {

				@Override
				public void processElement(Element element) {
					_importDeletionSystemEvents(portletDataContext, element);
				}

			},
			new String[] {"deletion-system-event"});

		xmlReader.setContentHandler(elementHandler);

		xmlReader.parse(new InputSource(new StringReader(xml)));
	}

	private DeletionSystemEventImporter() {
	}

	private void _importDeletionSystemEvents(
		PortletDataContext portletDataContext, Element element) {

		StagedModelType stagedModelType = new StagedModelType(
			element.attributeValue("class-name"),
			element.attributeValue("referrer-class-name"));

		if (!_shouldImportDeletionSystemEvent(
				portletDataContext, stagedModelType)) {

			return;
		}

		try {
			StagedModelDataHandlerUtil.deleteStagedModel(
				portletDataContext, element);
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					StringBundler.concat(
						"Unable to process deletion for ", stagedModelType,
						" with UUID ", element.attributeValue("uuid")),
					exception);
			}
		}
	}

	private boolean _shouldImportDeletionSystemEvent(
		PortletDataContext portletDataContext,
		StagedModelType stagedModelType) {

		Set<StagedModelType> stagedModelTypes =
			portletDataContext.getDeletionSystemEventStagedModelTypes();

		if (stagedModelTypes.contains(stagedModelType)) {
			return true;
		}

		for (StagedModelType curStagedModelType : stagedModelTypes) {
			if ((curStagedModelType.getClassNameId() ==
					stagedModelType.getClassNameId()) &&
				(StagedModelType.REFERRER_CLASS_NAME_ALL.equals(
					curStagedModelType.getReferrerClassName()) ||
				 (Validator.isNotNull(stagedModelType.getReferrerClassName()) &&
				  StagedModelType.REFERRER_CLASS_NAME_ANY.equals(
					  curStagedModelType.getReferrerClassName())))) {

				return true;
			}
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DeletionSystemEventImporter.class);

	private static final DeletionSystemEventImporter
		_deletionSystemEventImporter = new DeletionSystemEventImporter();

}