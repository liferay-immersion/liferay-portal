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

import './Card.scss';

export default function Card({
	children,
	className,
	title,
	tooltip,
	...otherProps
}: IProps) {
	return (
		<div
			{...otherProps}
			className={classNames(className, 'lfr-objects__card')}
		>
			<div className="lfr-objects__card-header">
				<h3 className="lfr-objects__card-title">{title}</h3>

				{tooltip && (
					<span
						className="ml-2"
						data-tooltip-align="top"
						title={tooltip.content}
					>
						<ClayIcon
							className="lfr-objects__card-header-tooltip-icon"
							symbol={tooltip.symbol}
						/>
					</span>
				)}
			</div>

			<div className={classNames(className, 'lfr-objects__card-body')}>
				{children}
			</div>
		</div>
	);
}

interface IProps extends React.HTMLAttributes<HTMLDivElement> {
	title: string;
	tooltip?: ITooltip | null;
}

interface ITooltip {
	content: string;
	symbol: string;
}
