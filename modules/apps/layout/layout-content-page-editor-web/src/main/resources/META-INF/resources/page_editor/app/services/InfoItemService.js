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

import {config} from '../config/index';
import serviceFetch from './serviceFetch';

export default {

	/**
	 * Get available list item renderers for the list style
	 * @param {object} options
	 * @param {string} options.itemSubtype itemSubtype
	 * @param {string} options.itemType itemType
	 * @param {string} options.listStyle listStyle
	 * @param {function} options.onNetworkStatus
	 */
	getAvailableListItemRenderers({
		itemSubtype,
		itemType,
		listStyle,
		onNetworkStatus,
	}) {
		return serviceFetch(
			config.getAvailableListItemRenderersURL,
			{
				body: {
					itemSubtype,
					itemType,
					listStyle,
				},
			},
			onNetworkStatus
		);
	},

	/**
	 * Get available list renderers for the class name
	 * @param {object} options
	 * @param {string} options.className className
	 * @param {function} options.onNetworkStatus
	 */
	getAvailableListRenderers({className, onNetworkStatus}) {
		return serviceFetch(
			config.getAvailableListRenderersURL,
			{
				body: {
					className,
				},
			},
			onNetworkStatus
		);
	},

	/**
	 * Get available structure mapping fields
	 * @param {object} options
	 * @param {string} options.classNameId Asset's className
	 * @param {string} options.classTypeId Asset's classTypeId
	 */
	getAvailableStructureMappingFields({classNameId, classTypeId}) {
		return serviceFetch(
			config.mappingFieldsURL,
			{
				body: {
					classNameId,
					classTypeId,
				},
			},
			() => {}
		);
	},

	/**
	 * Get available templates for an asset
	 * @param {object} options
	 * @param {string} options.className Asset's className
	 * @param {string} options.classPK Asset's classPK
	 * @param {function} options.onNetworkStatus
	 */
	getAvailableTemplates({className, classPK, onNetworkStatus}) {
		return serviceFetch(
			config.getAvailableTemplatesURL,
			{
				body: {
					className,
					classPK,
				},
			},
			onNetworkStatus
		);
	},

	/**
	 * Get the error message of an action
	 * @param {object} options
	 * @param {string} options.classNameId Asset's className
	 * @param {string} options.fieldId
	 */
	getInfoItemActionErrorMessage({classNameId, fieldId}) {
		return serviceFetch(
			config.getInfoItemActionErrorMessageURL,
			{
				body: {
					classNameId,
					fieldId,
				},
			},
			() => {}
		);
	},

	/**
	 * Get an item's value
	 * @param {object} options
	 * @param {string} options.classNameId Asset's className
	 * @param {string} options.classPK Asset's classPK
	 * @param {string} options.externalReferenceCode Asset's externalReferenceCode
	 * @param {string} options.fieldId
	 * @param {string} [options.languageId]
	 * @param {function} options.onNetworkStatus
	 */
	getInfoItemFieldValue({
		classNameId,
		classPK,
		editableTypeOptions,
		externalReferenceCode,
		fieldId,
		languageId,
		onNetworkStatus,
	}) {
		const body = {
			classNameId,
			editableTypeOptions: JSON.stringify(editableTypeOptions),
			fieldId,
			languageId,
		};

		if (classPK) {
			body.classPK = classPK;
		}

		if (externalReferenceCode) {
			body.externalReferenceCode = externalReferenceCode;
		}

		return serviceFetch(
			config.getInfoItemFieldValueURL,
			{
				body,
			},
			onNetworkStatus
		);
	},

	/**
	 * Get page contents
	 * @param {object} options
	 * @param {function} options.onNetworkStatus
	 */
	getPageContents({onNetworkStatus, segmentsExperienceId}) {
		return serviceFetch(
			config.getPageContentsURL,
			{
				body: {
					segmentsExperienceId,
				},
			},
			onNetworkStatus
		);
	},
};
