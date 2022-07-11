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

import {fetch} from 'frontend-js-web';

import {HEADERS} from './constants';

export async function fetchJSON<T>(input: RequestInfo, init?: RequestInit) {
	const result = await fetch(input, {
		headers: HEADERS,
		method: 'GET',
		...init,
	});

	return (await result.json()) as T;
}

async function getList<T>(url: string) {
	const {items} = await fetchJSON<{items: T[]}>(url);

	return items;
}

export async function getNotificationTemplates() {
	return await getList<NotificationTemplate>(
		'/o/notification/v1.0/notification-templates'
	);
}

export async function getObjectDefinitions() {
	return await getList<ObjectDefinition>(
		'/o/object-admin/v1.0/object-definitions?page=-1'
	);
}

export async function getObjectFields(objectDefinitionId: number) {
	return await getList<ObjectField>(
		`/o/object-admin/v1.0/object-definitions/${objectDefinitionId}/object-fields`
	);
}

export async function getObjectRelationships(objectDefinitionId: number) {
	return await getList<ObjectRelationship>(
		`/o/object-admin/v1.0/object-definitions/${objectDefinitionId}/object-relationships`
	);
}

export async function getPickLists() {
	return await getList<PickList>(
		'/o/headless-admin-list-type/v1.0/list-type-definitions?pageSize=-1'
	);
}

export async function getPickListItems(pickListId: number) {
	return await getList<PickListItem>(
		`/o/headless-admin-list-type/v1.0/list-type-definitions/${pickListId}/list-type-entries`
	);
}

export async function save(
	url: string,
	item: any,
	method: 'PUT' | 'POST' = 'PUT'
) {
	const response = await fetch(url, {
		body: JSON.stringify(item),
		headers: HEADERS,
		method,
	});

	if (response.status === 401) {
		window.location.reload();
	}
	else if (!response.ok) {
		const {
			title = Liferay.Language.get('an-error-occurred'),
		} = await response.json();

		throw new Error(title);
	}
}

export async function updateRelationship({
	objectRelationshipId,
	...others
}: ObjectRelationship) {
	return await save(
		`/o/object-admin/v1.0/object-relationships/${objectRelationshipId}`,
		others
	);
}
