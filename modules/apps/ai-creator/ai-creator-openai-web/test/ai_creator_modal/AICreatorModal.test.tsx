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

import {act, render, screen} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import {fetch} from 'frontend-js-web';
import React from 'react';

import '@testing-library/jest-dom/extend-expect';

import AICreatorModal from '../../src/main/resources/META-INF/resources/ai_creator_modal/AICreatorModal';

jest.mock('frontend-js-web', () => ({
	fetch: jest.fn(() => Promise.resolve({})),
}));

const mockedFetch = fetch as jest.MockedFunction<
	(url: string, options: {body: FormData}) => Promise<{}>
>;

async function renderModalWithRequest() {
	render(
		<AICreatorModal
			getCompletionURL="/sample-url"
			portletNamespace="namespace"
		/>
	);

	userEvent.type(screen.getByLabelText('description'), 'Sample text');
	userEvent.selectOptions(screen.getByLabelText('tone'), 'friendly');
	userEvent.type(screen.getByLabelText('word-count'), '123');

	await act(async () => {
		userEvent.click(screen.getByRole('button', {name: 'create'}));
	});
}

describe('AICreatorModal', () => {
	afterEach(() => {
		mockedFetch.mockReset();
	});

	it('calls backend with the given config', async () => {
		await renderModalWithRequest();

		expect(mockedFetch).toHaveBeenCalledTimes(1);

		const [[url, {body}]] = mockedFetch.mock.calls;

		expect(url).toBe('/sample-url');
		expect(body.get('namespacecontent')).toBe('Sample text');
		expect(body.get('namespacetone')).toBe('friendly');
		expect(body.get('namespacewords')).toBe('123');
	});

	it('shows error messages if any', async () => {
		mockedFetch.mockReturnValue(
			Promise.resolve(
				new Response(
					JSON.stringify({
						error: {
							message: 'Some testing error',
						},
					})
				)
			)
		);

		await renderModalWithRequest();

		expect(screen.getByText('Some testing error')).toBeInTheDocument();
	});

	it('shows completion result', async () => {
		mockedFetch.mockReturnValue(
			Promise.resolve(
				new Response(
					JSON.stringify({
						completion: {
							content: 'This text has been generated',
						},
					})
				)
			)
		);

		await renderModalWithRequest();

		expect(screen.getByLabelText('content').textContent).toBe(
			'This text has been generated'
		);
	});
});
