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

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import ClayForm, {ClayInput} from '@clayui/form';
import ClayModal, {useModal} from '@clayui/modal';
import {fetch} from 'frontend-js-web';
import React, {FormEvent, useEffect, useRef, useState} from 'react';

import {openToast} from './SidePanelContent';

interface IProps {
	nameMaxLength: string;
	portletNamespace: string;
}

interface IFile {
	fileName?: string;
	inputFile?: Blob | null;
	inputFileValue?: string;
}

const HEADER = new Headers({
	'Accept': 'application/json',
	'Content-Type': 'application/json',
});

const ModalImportObjectDefinition: React.FC<IProps> = ({
	nameMaxLength,
	portletNamespace,
}) => {
	const [visible, setVisible] = useState(false);
	const inputFileRef = useRef() as React.MutableRefObject<HTMLInputElement>;
	const [name, setName] = useState('');
	const importObjectDefinitionModalComponentId = `${portletNamespace}importObjectDefinitionModal`;
	const importObjectDefinitionFormId = `${portletNamespace}importObjectDefinitionForm`;
	const nameInputId = `${portletNamespace}name`;
	const objectDefinitionJSONInputId = `${portletNamespace}objectDefinitionJSON`;
	const [{fileName, inputFile, inputFileValue}, setFile] = useState<IFile>(
		{}
	);
	const {observer, onClose} = useModal({
		onClose: () => {
			setVisible(false);
			setFile({
				fileName: '',
				inputFile: null,
				inputFileValue: '',
			});
			setName('');
		},
	});

	useEffect(() => {
		Liferay.component(
			importObjectDefinitionModalComponentId,
			{
				open: () => {
					setVisible(true);
				},
			},
			{
				destroyOnNavigate: true,
			}
		);

		return () =>
			Liferay.destroyComponent(importObjectDefinitionModalComponentId);
	}, [importObjectDefinitionModalComponentId, setVisible]);

	const validate = (value: string) => {
		let error: string | null = null;

		const regexFirstCharactersIsUpper: RegExp = /^[a-z]/;
		const regexSpecialCharacters: RegExp = /.*[@!#$%^&*()/\\]/;

		if (regexFirstCharactersIsUpper.test(value)) {
			error = Liferay.Language.get(
				'the-first-character-of-a-name-must-be-an-upper-case-letter'
			);
		}
		else if (regexSpecialCharacters.test(value)) {
			error = Liferay.Language.get(
				'name-must-only-contain-letters-and-digits'
			);
		}

		return error;
	};

	const getTypeError = (type: string) => {
		switch (type) {
			case 'ObjectDefinitionNameException.MustBeginWithUpperCaseLetter':
				return Liferay.Language.get(
					'the-first-character-of-a-name-must-be-an-upper-case-letter'
				);
			case 'ObjectDefinitionNameException.MustNotBeDuplicate':
				return Liferay.Language.get(
					'this-name-is-already-in-use-try-another-one'
				);
			case 'ObjectDefinitionNameException.MustOnlyContainLettersAndDigits':
				return Liferay.Language.get(
					'name-must-only-contain-letters-and-digits'
				);
			default:
				return Liferay.Language.get(
					'the-structure-was-not-successfully-imported'
				);
		}
	};

	const onSubmit = async (event: FormEvent) => {
		event.preventDefault();

		const error = validate(name);

		if (error) {
			openToast({
				message: error,
				type: 'danger',
			});

			return;
		}

		const response = await fetch(
			'/o/object-admin/v1.0/object-definitions',
			{
				body: inputFileValue,
				headers: HEADER,
				method: 'POST',
			}
		);

		if (response.ok) {
			openToast({
				message: Liferay.Language.get(
					'the-object-action-was-created-successfully'
				),
				type: 'success',
			});

			setVisible(false);
		}
		else {
			const jsonResponse = (await response.json()) as any;

			openToast({
				message: getTypeError(jsonResponse.type),
				type: 'danger',
			});
		}
	};

	return visible ? (
		<ClayModal observer={observer}>
			<ClayModal.Header>
				{Liferay.Language.get('import-object')}
			</ClayModal.Header>

			<ClayModal.Body>
				<ClayForm id={importObjectDefinitionFormId} onSubmit={onSubmit}>
					<ClayAlert
						displayType="info"
						title={`${Liferay.Language.get('info')}:`}
					>
						{Liferay.Language.get(
							'the-import-process-will-run-in-the-background-and-may-take-a-few-minutes'
						)}
					</ClayAlert>

					<ClayForm.Group>
						<label htmlFor={nameInputId}>
							{Liferay.Language.get('name')}
						</label>

						<ClayInput
							id={nameInputId}
							maxLength={Number(nameMaxLength)}
							name={nameInputId}
							onChange={(event) => setName(event.target.value)}
							type="text"
							value={name}
						/>
					</ClayForm.Group>

					<ClayForm.Group>
						<label htmlFor={objectDefinitionJSONInputId}>
							{Liferay.Language.get('json-file')}
						</label>

						<ClayInput.Group>
							<ClayInput.GroupItem prepend>
								<ClayInput
									disabled
									id={objectDefinitionJSONInputId}
									type="text"
									value={fileName}
								/>
							</ClayInput.GroupItem>

							<ClayInput.GroupItem append shrink>
								<ClayButton
									displayType="secondary"
									onClick={() => inputFileRef.current.click()}
								>
									{Liferay.Language.get('select')}
								</ClayButton>
							</ClayInput.GroupItem>

							{inputFile && (
								<ClayInput.GroupItem shrink>
									<ClayButton
										displayType="secondary"
										onClick={() => {
											setFile({
												fileName: '',
												inputFile: null,
												inputFileValue: '',
											});
										}}
									>
										{Liferay.Language.get('clear')}
									</ClayButton>
								</ClayInput.GroupItem>
							)}
						</ClayInput.Group>
					</ClayForm.Group>

					<input
						className="d-none"
						name={objectDefinitionJSONInputId}
						onChange={({target}) => {
							const fileData = new FileReader();
							const inputFile = target.files?.item(0);

							fileData.onload = function (event) {
								const value = event.target?.result as string;

								setFile({
									fileName: inputFile?.name,
									inputFile,
									inputFileValue: value as string,
								});
							};

							fileData.readAsText(inputFile as Blob);
						}}
						ref={inputFileRef}
						type="file"
						value=""
					/>
				</ClayForm>
			</ClayModal.Body>

			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						<ClayButton displayType="secondary" onClick={onClose}>
							{Liferay.Language.get('cancel')}
						</ClayButton>

						<ClayButton
							disabled={!inputFile || !name}
							form={importObjectDefinitionFormId}
							type="submit"
						>
							{Liferay.Language.get('import')}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</ClayModal>
	) : null;
};

export default ModalImportObjectDefinition;
