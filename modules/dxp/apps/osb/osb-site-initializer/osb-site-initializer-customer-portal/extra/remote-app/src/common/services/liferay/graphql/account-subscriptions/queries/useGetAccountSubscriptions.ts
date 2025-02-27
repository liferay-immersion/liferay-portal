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

import {gql, useLazyQuery, useQuery} from '@apollo/client';

const GET_ACCOUNT_SUBSCRIPTIONS = gql`
	query getAccountSubscriptions(
		$filter: String
		$page: Int = 1
		$pageSize: Int = 20
	) {
		c {
			accountSubscriptions(
				filter: $filter
				page: $page
				pageSize: $pageSize
			) {
				items {
					accountKey
					accountSubscriptionGroupERC
					accountSubscriptionId
					endDate
					externalReferenceCode
					instanceSize
					name
					quantity
					startDate
					subscriptionStatus
					productKey
				}
			}
		}
	}
`;

export function useGetAccountSubscriptions(
	options = {
		filter: '',
		notifyOnNetworkStatusChange: false,
		page: 1,
		pageSize: 20,
		skip: false,
	}
) {
	return useQuery(GET_ACCOUNT_SUBSCRIPTIONS, {
		fetchPolicy: 'cache-and-network',
		nextFetchPolicy: 'cache-first',
		notifyOnNetworkStatusChange: options.notifyOnNetworkStatusChange,
		skip: options.skip,
		variables: {
			filter: options.filter || '',
			page: options.page || 1,
			pageSize: options.pageSize || 20,
		},
	});
}

export function useLazyGetAccountSubscriptions(
	options = {
		notifyOnNetworkStatusChange: false,
	}
) {
	return useLazyQuery(GET_ACCOUNT_SUBSCRIPTIONS, {
		fetchPolicy: 'cache-and-network',
		nextFetchPolicy: 'cache-first',
		notifyOnNetworkStatusChange: options.notifyOnNetworkStatusChange,
	});
}
