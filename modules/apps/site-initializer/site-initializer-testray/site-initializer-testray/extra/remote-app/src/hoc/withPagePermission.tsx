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

import React, {Component, useMemo} from 'react';
import {Navigate, useMatch, useOutletContext} from 'react-router-dom';
import Loading from '~/components/Loading/Loading';
import Rest from '~/core/Rest';
import {useFetch} from '~/hooks/useFetch';
import {ObjectActionsItems} from '~/services/rest';

type PageProperties = {
	createPath?: string;
	deniedChildren?: React.ReactNode | string;
	redirectTo?: string;
	restImpl: Rest;
};

type CheckPermissionProps = {
	children: any;
	properties: PageProperties;
};

const CheckPermission: React.FC<CheckPermissionProps> = ({
	children,
	properties: {
		createPath = '',
		restImpl,
		deniedChildren,
		redirectTo = `/404?type=permission`,
	},
}) => {
	const outletContext = useOutletContext<{actions: ObjectActionsItems}>();
	const isCreatePathMatching = useMatch(createPath);
	const restImplMemoized = useMemo(() => restImpl, [restImpl]);

	const updatePermission = useMemo(
		() =>
			!!(
				outletContext?.actions?.update ??
				outletContext?.actions?.replace
			),
		[outletContext?.actions?.replace, outletContext?.actions?.update]
	);

	const {data: createPermission, loading} = useFetch<
		ReturnType<typeof restImplMemoized.getPagePermission>
	>(restImplMemoized.uri, {
		swrConfig: {
			fetcher: restImplMemoized.getPagePermission.bind(restImplMemoized),
			shouldFetch: !!isCreatePathMatching,
		},
	});

	if (loading) {
		return <Loading />;
	}

	if (createPermission || updatePermission) {
		return children;
	}

	if (deniedChildren) {
		return deniedChildren;
	}

	return <Navigate to={redirectTo} />;
};

export function withPagePermission<T extends object>(
	WrappedComponent: React.ComponentType<T>,
	properties: PageProperties
) {
	return class extends Component<T> {
		render() {
			return (
				<CheckPermission properties={properties}>
					<WrappedComponent {...this.props} />
				</CheckPermission>
			);
		}
	};
}
