/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import MDFRequestActivity from '../interfaces/mdfRequestActivity';

export default function getTotalMDFRequest(
	mdfRequestActivities: MDFRequestActivity[]
) {
	return mdfRequestActivities.reduce(
		(previousValue: number, currentValue: MDFRequestActivity) => {
			const sumAmount = !currentValue.removed
				? currentValue.mdfRequestAmount
				: 0;

			return previousValue + sumAmount;
		},
		0
	);
}
