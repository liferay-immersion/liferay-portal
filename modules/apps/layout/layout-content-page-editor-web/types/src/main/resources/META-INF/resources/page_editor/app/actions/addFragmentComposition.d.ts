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

export interface FragmentComposition {
	fragmentCollectionId: string;
	fragmentCollectionName: string;
	fragmentEntryKey: string;
	groupId: string;
	icon: string;
	imagePreviewURL: string;
	name: string;
	type: 'composition';
}
export default function addFragmentComposition({
	fragmentCollectionId,
	fragmentComposition,
}: {
	fragmentCollectionId: string;
	fragmentComposition: FragmentComposition;
}): {
	readonly fragmentCollectionId: string;
	readonly fragmentComposition: FragmentComposition;
	readonly type: 'ADD_FRAGMENT_COMPOSITION';
};
