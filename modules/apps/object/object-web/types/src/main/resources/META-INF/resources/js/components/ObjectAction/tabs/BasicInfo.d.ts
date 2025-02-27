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

import {FormError} from '@liferay/object-js-components-web';
import React from 'react';
interface BasicInfoProps {
	errors: FormError<ObjectAction & ObjectActionParameters>;
	handleChange: React.ChangeEventHandler<HTMLInputElement>;
	isApproved: boolean;
	readOnly?: boolean;
	setValues: (values: Partial<ObjectAction>) => void;
	values: Partial<ObjectAction>;
}
export default function BasicInfo({
	errors,
	handleChange,
	isApproved,
	readOnly,
	setValues,
	values,
}: BasicInfoProps): JSX.Element;
export {};
