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

package com.liferay.jenkins.results.parser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONObject;

import org.junit.Test;

/**
 * @author Michael Hashimoto
 */
public class GitHubRemoteGitRepositoryTest extends GitRepositoryTest {

	@Test
	public void testGetLabel() throws Exception {
		String labelName = "ci:test - success";

		GitHubRemoteGitRepository gitHubRemoteGitRepository =
			_getGitHubRemoteGitRepository();

		gitHubRemoteGitRepository.getLabel(labelName);
	}

	@Test
	public void testGetLabelRequestURL() throws Exception {
		RemoteGitRepository remoteGitRepository =
			GitRepositoryFactory.getRemoteGitRepository(
				"github.com", NAME_REPOSITORY, USERNAME_REPOSITORY);

		if (!(remoteGitRepository instanceof GitHubRemoteGitRepository)) {
			throw new RuntimeException(
				"Invalid GitHubRemoteGitRepository instance");
		}

		GitHubRemoteGitRepository gitHubRemoteGitRepository =
			(GitHubRemoteGitRepository)remoteGitRepository;

		gitHubRemoteGitRepository.setLabelRequestURL(null);

		String expectedLabelRequestURL = JenkinsResultsParserUtil.combine(
			"https://api.github.com/repos/", USERNAME_REPOSITORY, "/",
			NAME_REPOSITORY, "/labels");

		if (!expectedLabelRequestURL.contains(
				gitHubRemoteGitRepository.getLabelRequestURL())) {

			errorCollector.addError(
				new Throwable(
					JenkinsResultsParserUtil.combine(
						"Unexpected label request URL was found: ",
						gitHubRemoteGitRepository.getLabelRequestURL())));
		}
	}

	@Test
	public void testGetLabels() throws Exception {
		GitHubRemoteGitRepository gitHubRemoteGitRepository =
			_getGitHubRemoteGitRepository();

		List<String> expectedLabelNames = new ArrayList<>();

		Collections.addAll(
			expectedLabelNames, "ci:test - failure", "ci:test - pending",
			"ci:test - success", "ci:test:relevant - failure",
			"ci:test:relevant - pending", "ci:test:relevant - success");

		for (GitHubRemoteGitRepository.Label label :
				gitHubRemoteGitRepository.getLabels()) {

			String actualLabelName = label.getName();

			if (!expectedLabelNames.contains(actualLabelName)) {
				errorCollector.addError(
					new Throwable(
						"Unexpected label was found: " + actualLabelName));
			}

			expectedLabelNames.remove(actualLabelName);
		}

		if (!expectedLabelNames.isEmpty()) {
			errorCollector.addError(
				new Throwable(
					"Expected labels were not found: " + expectedLabelNames));
		}
	}

	@Test
	public void testHasLabel() throws Exception {
		GitHubRemoteGitRepository gitHubRemoteGitRepository =
			_getGitHubRemoteGitRepository();

		if (!gitHubRemoteGitRepository.hasLabel("ci:test - success")) {
			errorCollector.addError(
				new Throwable("hasLabel could not find expected label"));
		}

		if (gitHubRemoteGitRepository.hasLabel("unexpected label")) {
			errorCollector.addError(
				new Throwable("hasLabel found unexpected label"));
		}
	}

	@Test
	public void testLabelEquals() throws Exception {
		JSONObject jsonObject = new JSONObject();

		jsonObject.put(
			"color", "c7e8cb"
		).put(
			"default", false
		).put(
			"description", "This is a description"
		).put(
			"id", 884437936
		).put(
			"name", "ci:test - success"
		).put(
			"node_id", "MDU6TGFiZWw4ODQ0Mzc5MzY="
		).put(
			"url",
			JenkinsResultsParserUtil.combine(
				"https://api.github.com/repos/liferay/liferay-portal/labels/",
				"ci:test%20-%20success")
		);

		GitHubRemoteGitRepository.Label expectedLabel =
			new GitHubRemoteGitRepository.Label(
				jsonObject, _getGitHubRemoteGitRepository());

		GitHubRemoteGitRepository.Label actualLabel = _getLabel(
			"ci:test - success");

		if (!expectedLabel.equals(actualLabel)) {
			errorCollector.addError(
				new Throwable(
					JenkinsResultsParserUtil.combine(
						"Expected does not match actual\nExpected: ",
						expectedLabel.toString(), "\nActual:   ",
						actualLabel.toString())));
		}
	}

	@Test
	public void testLabelGetColor() throws Exception {
		String expectedColor = "c7e8cb";

		GitHubRemoteGitRepository.Label label = _getLabel("ci:test - success");

		String actualColor = label.getColor();

		if (!expectedColor.equals(actualColor)) {
			errorCollector.addError(
				new Throwable(
					JenkinsResultsParserUtil.combine(
						"Expected does not match actual\nExpected: ",
						expectedColor, "\nActual:   ", actualColor)));
		}
	}

	@Test
	public void testLabelGetDescription() throws Exception {
		String expectedDescription = "This is a description";

		GitHubRemoteGitRepository.Label label = _getLabel("ci:test - success");

		String actualDescription = label.getDescription();

		if (!expectedDescription.equals(actualDescription)) {
			errorCollector.addError(
				new Throwable(
					JenkinsResultsParserUtil.combine(
						"Expected does not match actual\nExpected: ",
						expectedDescription, "\nActual:   ",
						actualDescription)));
		}
	}

	@Test
	public void testLabelGetGitHubRemoteGitRepository() throws Exception {
		GitHubRemoteGitRepository expectedGitHubRemoteGitRepository =
			_getGitHubRemoteGitRepository();

		GitHubRemoteGitRepository.Label label =
			expectedGitHubRemoteGitRepository.getLabel("ci:test - success");

		GitHubRemoteGitRepository actualGitHubRemoteGitRepository =
			label.getGitHubRemoteGitRepository();

		if (!expectedGitHubRemoteGitRepository.equals(
				actualGitHubRemoteGitRepository)) {

			errorCollector.addError(
				new Throwable(
					JenkinsResultsParserUtil.combine(
						"Expected does not match actual\nExpected: ",
						expectedGitHubRemoteGitRepository.toString(),
						"\nActual:   ",
						actualGitHubRemoteGitRepository.toString())));
		}
	}

	@Test
	public void testLabelGetName() throws Exception {
		String expectedLabelName = "ci:test - success";

		GitHubRemoteGitRepository.Label label = _getLabel(expectedLabelName);

		String actualLabelName = label.getName();

		if (!expectedLabelName.equals(actualLabelName)) {
			errorCollector.addError(
				new Throwable(
					JenkinsResultsParserUtil.combine(
						"Expected does not match actual\nExpected: ",
						expectedLabelName, "\nActual:   ", expectedLabelName)));
		}
	}

	private GitHubRemoteGitRepository _getGitHubRemoteGitRepository() {
		RemoteGitRepository remoteGitRepository =
			GitRepositoryFactory.getRemoteGitRepository(
				"github.com", NAME_REPOSITORY, USERNAME_REPOSITORY);

		if (!(remoteGitRepository instanceof GitHubRemoteGitRepository)) {
			errorCollector.addError(
				new Throwable("Invalid GitHubRemoteGitRepository instance"));
		}

		GitHubRemoteGitRepository gitHubRemoteGitRepository =
			(GitHubRemoteGitRepository)remoteGitRepository;

		TestSample testSample = new TestSample(dependenciesDirs, "labels");

		try {
			gitHubRemoteGitRepository.setLabelRequestURL(
				JenkinsResultsParserUtil.getLocalURL(
					toURLString(testSample.getSampleDir())));

			return gitHubRemoteGitRepository;
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	private GitHubRemoteGitRepository.Label _getLabel(String labelName) {
		GitHubRemoteGitRepository gitHubRemoteGitRepository =
			_getGitHubRemoteGitRepository();

		return gitHubRemoteGitRepository.getLabel(labelName);
	}

}