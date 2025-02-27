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
import {
	fetch,
	navigate,
	objectToFormData,
	openSelectionModal,
	openToast,
	sub,
} from 'frontend-js-web';
import React, {useState} from 'react';

const DDMFolderSelector = ({
	copyActionURL,
	fileShortcutId,
	itemType,
	portletNamespace,
	redirect,
	selectionModalURL,
	sourceFileEntryId,
	sourceFileName,
	sourceFolderId,
	sourceFolderName,
	sourceRepositoryId,
}) => {
	const [copyButtonDisabled, setCopyButtonDisabled] = useState(true);
	const [
		destinationParentFolderName,
		setDestinationParentFolderName,
	] = useState('');
	const [destinationParentFolderId, setDestinationParentFolderId] = useState(
		-1
	);
	const [destinationRepositoryId, setDestinationRepositoryId] = useState(-1);

	const handleSelectButtonClick = (event) => {
		event.preventDefault();

		openSelectionModal({
			onSelect(selectedItem) {
				if (!selectedItem) {
					return;
				}

				setDestinationParentFolderName(selectedItem.foldername);
				setDestinationParentFolderId(selectedItem.folderid);
				setDestinationRepositoryId(selectedItem.repositoryid);
				setCopyButtonDisabled(false);
			},
			selectEventName: `${portletNamespace}folderSelected`,
			title: sub(Liferay.Language.get('select-x'), 'folder'),
			url: selectionModalURL,
		});
	};

	const handleSubmit = (event) => {
		event.preventDefault();

		const bodyContentObject = objectToFormData(
			itemType === 'folder'
				? {
						[`${portletNamespace}sourceRepositoryId`]: sourceRepositoryId,
						[`${portletNamespace}sourceFolderId`]: sourceFolderId,
						[`${portletNamespace}destinationParentFolderId`]: destinationParentFolderId,
						[`${portletNamespace}destinationRepositoryId`]: destinationRepositoryId,
				  }
				: {
						[`${portletNamespace}fileEntryId`]: sourceFileEntryId,
						[`${portletNamespace}fileShortcutId`]: fileShortcutId,
						[`${portletNamespace}destinationFolderId`]: destinationParentFolderId,
						[`${portletNamespace}destinationRepositoryId`]: destinationRepositoryId,
				  }
		);

		fetch(copyActionURL, {
			body: bodyContentObject,
			method: 'POST',
		})
			.then((response) => response.json())
			.then(({errorMessage}) => {
				if (errorMessage) {
					openToast({
						message: errorMessage,
						title: Liferay.Language.get('error'),
						type: 'danger',
					});
				}
				else {
					navigate(redirect);
				}
			})
			.catch((error) => {
				openToast({
					message: error.message,
					title: Liferay.Language.get('error'),
					type: 'danger',
				});
			});
	};

	return (
		<ClayForm onSubmit={handleSubmit}>
			<ClayAlert
				className="c-mb-4"
				displayType="warning"
				title={Liferay.Language.get('alert')}
			>
				{Liferay.Language.get('document-library-copy-folder-help')}
			</ClayAlert>

			<ClayForm.Group>
				<label htmlFor={`${portletNamespace}copyFromInput`}>
					{Liferay.Language.get('copy-from')}
				</label>

				<ClayInput
					className="c-mb-3"
					disabled
					id={`${portletNamespace}copyFromInput`}
					placeholder={sourceFolderName || sourceFileName}
					type="text"
				/>

				<label htmlFor={`${portletNamespace}copyToInput`}>
					{Liferay.Language.get('copy-to')}
				</label>

				<ClayInput.Group>
					<ClayInput.GroupItem>
						<ClayInput
							disabled
							id={`${portletNamespace}copyToInput`}
							placeholder={destinationParentFolderName}
							type="text"
						/>
					</ClayInput.GroupItem>

					<ClayInput.GroupItem shrink>
						<ClayButton
							displayType="secondary"
							onClick={handleSelectButtonClick}
						>
							{Liferay.Language.get('select')}
						</ClayButton>
					</ClayInput.GroupItem>
				</ClayInput.Group>
			</ClayForm.Group>

			<ClayButton.Group spaced>
				<ClayButton
					disabled={copyButtonDisabled}
					displayType="primary"
					type="submit"
				>
					{Liferay.Language.get('copy')}
				</ClayButton>

				<ClayButton
					displayType="secondary"
					onClick={() => navigate(redirect)}
				>
					{Liferay.Language.get('cancel')}
				</ClayButton>
			</ClayButton.Group>
		</ClayForm>
	);
};

export default DDMFolderSelector;
