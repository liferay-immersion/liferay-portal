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

export declare const headers: Headers;
export declare function fetchJSON<T>({
	init,
	input,
}: {
	init?: RequestInit;
	input: RequestInfo;
}): Promise<T>;
export declare function getItems<T>({url}: {url: string}): Promise<T[]>;
export declare function updateData({
	dataToUpdate,
	onError,
	onSuccess,
	url,
}: {
	dataToUpdate: Partial<ItemData>;
	onError: (error: string) => void;
	onSuccess: voidReturn;
	url: string;
}): Promise<void>;
