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

import React from 'react';
import {createRoot} from 'react-dom/client';

import BuildQueue from './common/components/BuildQueue.js';
import {Liferay} from './common/services/liferay/liferay.js';

const App = () => {
	return (
		<div>
			<h2>Build Queue</h2>

			{Liferay.ThemeDisplay.isSignedIn() && <BuildQueue />}
		</div>
	);
};

class WebComponent extends HTMLElement {
	connectedCallback() {
		createRoot(this).render(<App />);
	}
}

const ELEMENT_ID = 'liferay-jethr0-custom-element';

if (!customElements.get(ELEMENT_ID)) {
	customElements.define(ELEMENT_ID, WebComponent);
}
