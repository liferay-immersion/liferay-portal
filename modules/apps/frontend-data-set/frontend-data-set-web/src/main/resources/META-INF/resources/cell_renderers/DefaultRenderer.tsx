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

import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import React from 'react';

// @ts-ignore

import TooltipTextRenderer from './TooltipTextRenderer';

interface DefaultRendererOptions {
	truncate?: boolean;
}

type DefaultRendererValue =
	| string
	| number
	| boolean
	| null
	| undefined
	| {
			icon?: string;
			iconSymbol?: string;
			label?: string;
			label_i18n?: string;
			text?: string;
	  };

const Wrapper = ({
	children,
	options,
}: {
	children: React.ReactNode;
	options: DefaultRendererOptions;
}) => {
	return options?.truncate ? (
		<span
			className={classNames(
				'default-renderer__text-truncate',
				'text-truncate'
			)}
		>
			{children}
		</span>
	) : (
		<>{children}</>
	);
};

const DefaultRenderer: React.FC<{
	options: DefaultRendererOptions;
	value: DefaultRendererValue;
}> = ({options, value}) => {
	if (
		typeof value === 'number' ||
		typeof value === 'string' ||
		React.isValidElement(value)
	) {
		return <Wrapper options={options}>{value}</Wrapper>;
	}

	if (typeof value === 'boolean') {
		return (
			<Wrapper options={options}>
				{value
					? Liferay.Language.get('yes')
					: Liferay.Language.get('no')}
			</Wrapper>
		);
	}

	if (value === null || typeof value !== 'object') {
		return null;
	}

	if (value.icon) {
		return <ClayIcon symbol={value.icon} />;
	}

	if (!!value.iconSymbol && !!value.text) {
		return <TooltipTextRenderer value={value} />;
	}

	if (value.label_i18n) {
		return <Wrapper options={options}>{value.label_i18n}</Wrapper>;
	}

	if (value.label) {
		return <Wrapper options={options}>{value.label}</Wrapper>;
	}

	return null;
};

export default DefaultRenderer;
