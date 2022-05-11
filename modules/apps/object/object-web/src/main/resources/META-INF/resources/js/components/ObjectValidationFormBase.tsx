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

import {ClayInput, ClayToggle} from '@clayui/form';
import React from 'react';

import useForm, {FormError, invalidateRequired} from '../hooks/useForm';
import {defaultLanguageId} from '../utils/locale';

const REQUIRED_MSG = Liferay.Language.get('required');

export default function ObjectValidationFormBase({
	disabled,
	objectValidationTypeLabel,
	setValues,
	values,
}: IProps) {
	return (
		<>
			<label className="text-muted" htmlFor="validationTypeInput">
				{Liferay.Language.get('type')}
			</label>

			<ClayInput
				className="mb-3"
				disabled
				id="validationTypeInput"
				value={objectValidationTypeLabel}
			/>

			<ClayToggle
				disabled={disabled}
				label={Liferay.Language.get('active-validation')}
				name="required"
				onToggle={(active) => setValues({active})}
				toggled={values.active}
			/>
		</>
	);
}

export function useObjectValidationForm({
	initialValues,
	onSubmit,
}: IUseObjectValidationForm) {
	const validate = (validation: Partial<ObjectValidation>) => {
		const errors: ObjectValidationErrors = {};

		const label = validation.name?.[defaultLanguageId];
		const errorMessage = validation.errorLabel?.[defaultLanguageId];
		const script = validation.script;

		if (invalidateRequired(label)) {
			errors.name = REQUIRED_MSG;
		}

		if (invalidateRequired(errorMessage)) {
			errors.errorLabel = REQUIRED_MSG;
		}

		if (invalidateRequired(script)) {
			errors.script = REQUIRED_MSG;
		}

		return errors;
	};

	const {errors, handleChange, handleSubmit, setValues, values} = useForm<
		ObjectValidation
	>({
		initialValues,
		onSubmit,
		validate,
	});

	return {errors, handleChange, handleSubmit, setValues, values};
}
interface IUseObjectValidationForm {
	initialValues: Partial<ObjectValidation>;
	onSubmit: (validation: ObjectValidation) => void;
}
interface IProps {
	disabled: boolean;
	errors: ObjectValidationErrors;
	objectValidationTypeLabel: string;
	setValues: (values: Partial<ObjectValidation>) => void;
	values: Partial<ObjectValidation>;
}

export type ObjectValidationErrors = FormError<ObjectValidation>;
