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

import ClayForm, {ClayInput} from '@clayui/form';
import classNames from 'classnames';
import {debounce} from 'frontend-js-web';
import React, {forwardRef, useRef, useState} from 'react';

import {
	getMinQuantity,
	getProductMaxQuantity,
} from '../../utilities/quantities';
import RulesPopover from './RulesPopover';

const InputQuantitySelector = forwardRef(
	(
		{
			alignment,
			className,
			disabled,
			max,
			min,
			name,
			onUpdate,
			quantity,
			step,
		},
		inputRef
	) => {
		const [showPopover, setShowPopover] = useState(false);
		const [unsatisfiedConstrains, setUnsatisfiedConstrain] = useState([]);
		const debouncedSetUpdateConstrainsRef = useRef(
			debounce(setUnsatisfiedConstrain, 500)
		);

		const inputMax = getProductMaxQuantity(max, step);
		const inputMin = getMinQuantity(min, step);

		const getErrors = (value) => {
			const errors = [];

			if (!value || value < min) {
				errors.push('min');
			}

			if (max && value > max) {
				errors.push('max');
			}

			if (step > 1 && value % step) {
				errors.push('multiple');
			}

			return errors;
		};

		return (
			<ClayForm.Group
				className={classNames({
					'has-error': unsatisfiedConstrains.length,
					'mb-0': true,
				})}
			>
				<ClayInput
					className={className}
					disabled={disabled}
					max={inputMax}
					min={inputMin}
					name={name}
					onBlur={() => {
						setShowPopover(false);
					}}
					onChange={({target}) => {
						const numValue = Number(target.value);

						const errors = getErrors(numValue);

						onUpdate({
							errors,
							value: numValue,
						});

						debouncedSetUpdateConstrainsRef.current(errors);
					}}
					onFocus={() => setShowPopover(true)}
					ref={inputRef}
					step={step > 1 ? step : ''}
					type="number"
					value={String(quantity || '')}
				/>

				{showPopover &&
					(step > 1 ||
						min > 1 ||
						unsatisfiedConstrains.includes('max')) && (
						<RulesPopover
							alignment={alignment}
							inputRef={inputRef}
							max={max}
							min={min}
							multiple={step}
							unsatisfiedConstrains={unsatisfiedConstrains}
						/>
					)}
			</ClayForm.Group>
		);
	}
);

InputQuantitySelector.defaultProps = {
	maxQuantity: '',
	minQuantity: 1,
	multipleQuantity: 1,
};

export default InputQuantitySelector;
