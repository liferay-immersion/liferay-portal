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

package com.liferay.jethr0.event.handler;

import com.liferay.jethr0.build.Build;
import com.liferay.jethr0.build.parameter.BuildParameter;
import com.liferay.jethr0.build.queue.BuildQueue;
import com.liferay.jethr0.build.repository.BuildParameterRepository;
import com.liferay.jethr0.build.repository.BuildRepository;
import com.liferay.jethr0.jenkins.JenkinsQueue;
import com.liferay.jethr0.project.Project;
import com.liferay.jethr0.project.repository.ProjectRepository;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class CreateBuildEventHandler extends BaseObjectEventHandler {

	@Override
	public String process() throws Exception {
		JSONObject messageJSONObject = getMessageJSONObject();

		Project project = getProject(
			messageJSONObject.optJSONObject("project"));

		BuildRepository buildRepository = getBuildRepository();

		JSONObject buildJSONObject = validateBuildJSONObject(
			messageJSONObject.optJSONObject("build"));

		Build build = buildRepository.add(project, buildJSONObject);

		JSONObject parametersJSONObject = buildJSONObject.optJSONObject(
			"parameters");

		if ((parametersJSONObject != null) && !parametersJSONObject.isEmpty()) {
			BuildParameterRepository buildParameterRepository =
				getBuildParameterRepository();

			for (String key : parametersJSONObject.keySet()) {
				BuildParameter buildParameter = buildParameterRepository.add(
					build, key, parametersJSONObject.getString(key));

				build.addBuildParameter(buildParameter);

				buildParameter.setBuild(build);
			}
		}

		if (project.getState() == Project.State.COMPLETED) {
			project.setState(Project.State.QUEUED);

			ProjectRepository projectRepository = getProjectRepository();

			projectRepository.update(project);
		}

		BuildQueue buildQueue = getBuildQueue();

		buildQueue.addProject(project);

		JenkinsQueue jenkinsQueue = getJenkinsQueue();

		jenkinsQueue.invoke();

		return project.toString();
	}

	protected CreateBuildEventHandler(
		EventHandlerContext eventHandlerContext, JSONObject messageJSONObject) {

		super(eventHandlerContext, messageJSONObject);
	}

}