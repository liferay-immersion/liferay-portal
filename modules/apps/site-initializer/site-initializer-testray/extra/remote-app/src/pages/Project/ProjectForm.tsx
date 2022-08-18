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

import {useEffect} from 'react';
import {useForm} from 'react-hook-form';
import {useOutletContext} from 'react-router-dom';
import {KeyedMutator} from 'swr';

import Form from '../../components/Form';
import Container from '../../components/Layout/Container';
import {useHeader} from '../../hooks';
import useFormActions from '../../hooks/useFormActions';
import i18n from '../../i18n';
import yupSchema, {yupResolver} from '../../schema/yup';
import {
	TestrayProject,
	createProject,
	updateProject,
} from '../../services/rest';

type ProjectFormType = typeof yupSchema.project.__outputType;

const ProjectForm = () => {
	const {
		form: {onClose, onError, onSave, onSubmit},
	} = useFormActions();

	const projectOutlet = useOutletContext<{
		mutateTestrayProject: KeyedMutator<TestrayProject>;
		testrayProject?: TestrayProject;
	}>();

	const testrayProject = projectOutlet?.testrayProject;

	const {setTabs} = useHeader();

	useEffect(() => {
		if (testrayProject) {
			setTimeout(() => setTabs([]), 100);
		}
	}, [setTabs, testrayProject]);

	const {
		formState: {errors},
		handleSubmit,
		register,
	} = useForm<ProjectFormType>({
		defaultValues: {
			description: testrayProject?.description,
			id: testrayProject?.id.toString(),
			name: testrayProject?.name,
		},
		resolver: yupResolver(yupSchema.project),
	});

	const _onSubmit = (form: ProjectFormType) =>
		onSubmit(form, {
			create: createProject,
			update: updateProject,
		})
			.then((response) => {
				if (form.id) {
					projectOutlet.mutateTestrayProject(response);
				}
			})
			.then(onSave)
			.catch(onError);

	const inputProps = {
		errors,
		register,
	};

	return (
		<Container className="container">
			<Form.Input
				{...inputProps}
				label={i18n.translate('name')}
				name="name"
				required
			/>

			<Form.Input
				{...inputProps}
				label={i18n.translate('description')}
				name="description"
			/>

			<Form.Footer onClose={onClose} onSubmit={handleSubmit(_onSubmit)} />
		</Container>
	);
};

export default ProjectForm;
