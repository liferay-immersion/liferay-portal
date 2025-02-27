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

import {ClayInput} from '@clayui/form';
import {ClassicEditor} from 'frontend-editor-ckeditor-web';
import React, {useEffect, useMemo, useRef, useState} from 'react';

import {FieldBase} from '../FieldBase/ReactFieldBase.es';
import LocalesDropdown from '../util/localizable/LocalesDropdown';
import {
	convertStringToObject,
	getEditingValue,
	getInitialInternalValue,
	normalizeLocaleId,
	transformAvailableLocalesAndValue,
} from '../util/localizable/transform.es';

const INITIAL_DEFAULT_LOCALE = {
	icon: themeDisplay.getDefaultLanguageId(),
	localeId: themeDisplay.getDefaultLanguageId(),
};
const INITIAL_EDITING_LOCALE = {
	icon: normalizeLocaleId(themeDisplay.getDefaultLanguageId()),
	localeId: themeDisplay.getDefaultLanguageId(),
};

const RichText = ({
	availableLocales,
	defaultLocale = INITIAL_DEFAULT_LOCALE,
	editable,
	editingLanguageId,
	editingLocale = INITIAL_EDITING_LOCALE,
	editorConfig,
	fieldName,
	id,
	locale,
	name,
	localizedObjectField,
	onBlur,
	onChange,
	onFocus,
	predefinedValue = '',
	readOnly,
	value,
	visible,
	...otherProps
}) => {
	const editorRef = useRef();

	const contents = useMemo(
		() => (editable ? predefinedValue : value ?? predefinedValue),
		[editable, predefinedValue, value]
	);

	const [currentAvailableLocales, setCurrentAvailableLocales] = useState(
		availableLocales
	);
	const [currentEditingLocale, setCurrentEditingLocale] = useState(
		editingLocale
	);
	const [currentValue, setCurrentValue] = useState(
		convertStringToObject(
			contents,
			editingLanguageId ?? locale ?? defaultLocale?.localeId
		)
	);
	const [currentInternalValue, setCurrentInternalValue] = useState(
		getInitialInternalValue({
			editingLocale: currentEditingLocale,
			value: currentValue,
		})
	);

	useEffect(() => {
		const editor = editorRef.current?.editor;

		if (editor) {
			editor.config.contentsLangDirection =
				Liferay.Language.direction[currentEditingLocale.localeId];
			editor.config.contentsLanguage = currentEditingLocale.localeId;
			editor.setData(currentInternalValue);
		}
		const {availableLocales} = {
			...transformAvailableLocalesAndValue({
				availableLocales: currentAvailableLocales,
				defaultLocale,
				value: currentValue,
			}),
		};

		setCurrentAvailableLocales(availableLocales);
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [currentEditingLocale]);

	useEffect(() => {
		changeLanguage(editingLanguageId ?? locale ?? defaultLocale?.localeId);
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [editingLanguageId, locale, predefinedValue]);

	const changeLanguage = (localeId) => {
		if (!localeId) {
			return;
		}
		let newEditingLocale = {};

		if (currentAvailableLocales) {
			const index = currentAvailableLocales?.findIndex(
				(availableLocale) => availableLocale.localeId === localeId
			);
			newEditingLocale = currentAvailableLocales[index];
		}
		else {
			newEditingLocale = {localeId};
		}

		setCurrentEditingLocale({
			...newEditingLocale,
			icon: normalizeLocaleId(newEditingLocale.localeId),
		});
		setCurrentInternalValue(
			getEditingValue({
				defaultLocale,
				editingLocale: newEditingLocale,
				fieldName,
				value: convertStringToObject(contents, localeId),
			})
		);
	};

	return (
		<FieldBase
			{...otherProps}
			id={id}
			name={name}
			readOnly={readOnly}
			style={readOnly ? {pointerEvents: 'none'} : null}
			visible={visible}
		>
			<ClayInput.Group>
				<ClayInput.GroupItem>
					<ClassicEditor
						className="w-100"
						contents={
							currentValue
								? currentValue[currentEditingLocale?.localeId]
								: ''
						}
						editorConfig={editorConfig}
						name={name}
						onBlur={onBlur}
						onChange={(content) => {
							if (
								currentValue[currentEditingLocale?.localeId] !==
								content
							) {
								const newValue = {
									...currentValue,
									[currentEditingLocale.localeId]: content,
								};

								setCurrentValue(newValue);
								setCurrentInternalValue(content);

								const {availableLocales} = {
									...transformAvailableLocalesAndValue({
										availableLocales: currentAvailableLocales,
										defaultLocale,
										value: newValue,
									}),
								};

								setCurrentAvailableLocales(availableLocales);

								onChange({
									target: {
										value: localizedObjectField
											? newValue
											: newValue[
													currentEditingLocale
														?.localeId
											  ],
									},
								});
							}
						}}
						onFocus={onFocus}
						onSetData={({
							data: {dataValue: value},
							editor: {mode},
						}) => {
							if (mode === 'source') {
								onChange({target: {value}});
							}
						}}
						readOnly={readOnly}
						ref={editorRef}
					/>
				</ClayInput.GroupItem>

				<input
					id={id}
					name={name}
					type="hidden"
					value={
						localizedObjectField
							? currentValue || ''
							: currentValue
							? currentValue[currentEditingLocale?.localeId]
							: ''
					}
				/>

				{localizedObjectField && (
					<ClayInput.GroupItem
						className="liferay-ddm-form-field-localizable-text"
						shrink
					>
						<LocalesDropdown
							availableLocales={currentAvailableLocales}
							editingLocale={currentEditingLocale}
							fieldName={fieldName}
							onLanguageClicked={(localeId) => {
								changeLanguage(localeId);
							}}
						/>
					</ClayInput.GroupItem>
				)}
			</ClayInput.Group>
		</FieldBase>
	);
};

export default RichText;
