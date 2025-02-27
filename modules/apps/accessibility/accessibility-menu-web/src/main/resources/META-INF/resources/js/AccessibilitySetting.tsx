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

import {ClayToggle} from '@clayui/form';
import React from 'react';

const KEY_EVENT = 'Enter';

type Props = {
	className: string;
	disabled: boolean;
	label: string;
	onChange: (value: boolean) => void;
	value: boolean;
};

const AccessibilitySetting = ({
	className,
	disabled,
	label,
	onChange,
	value,
}: Props) => (
	<li className={className}>
		<ClayToggle
			disabled={disabled}
			label={label}
			onKeyDown={(event) => {
				if (!disabled && event.key === KEY_EVENT) {
					onChange(!value);
				}
			}}
			onToggle={onChange}
			toggled={value}
		/>
	</li>
);

export default AccessibilitySetting;
