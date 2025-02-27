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

package com.liferay.jenkins.results.parser.testray;

import com.liferay.jenkins.results.parser.Build;
import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.TestClassResult;
import com.liferay.jenkins.results.parser.TestResult;
import com.liferay.jenkins.results.parser.TopLevelBuild;
import com.liferay.jenkins.results.parser.test.clazz.FunctionalTestClass;
import com.liferay.jenkins.results.parser.test.clazz.TestClass;
import com.liferay.jenkins.results.parser.test.clazz.group.AxisTestClassGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Michael Hashimoto
 */
public class FunctionalBatchBuildTestrayCaseResult
	extends BatchBuildTestrayCaseResult {

	public FunctionalBatchBuildTestrayCaseResult(
		TestrayBuild testrayBuild, TopLevelBuild topLevelBuild,
		AxisTestClassGroup axisTestClassGroup, TestClass testClass) {

		super(testrayBuild, topLevelBuild, axisTestClassGroup);

		if (!(testClass instanceof FunctionalTestClass)) {
			throw new RuntimeException(
				"Test class is not a functional test class");
		}

		_functionalTestClass = (FunctionalTestClass)testClass;
	}

	@Override
	public String getComponentName() {
		return JenkinsResultsParserUtil.getProperty(
			_functionalTestClass.getPoshiProperties(),
			"testray.main.component.name");
	}

	@Override
	public String getErrors() {
		TestResult testResult = getTestResult();

		if (testResult == null) {
			Build build = getBuild();

			if (build == null) {
				return "Failed to run build on CI";
			}

			String result = build.getResult();

			if (result == null) {
				return "Failed to finish build on CI";
			}

			if (result.equals("ABORTED")) {
				return "Aborted prior to running test";
			}

			if (result.equals("SUCCESS") || result.equals("UNSTABLE")) {
				return "Failed to run test on CI";
			}

			return "Failed prior to running test";
		}

		if (!testResult.isFailing()) {
			return null;
		}

		String errorMessage = testResult.getErrorDetails();

		if (JenkinsResultsParserUtil.isNullOrEmpty(errorMessage)) {
			return "Failed for unknown reason";
		}

		if (errorMessage.contains("\n")) {
			errorMessage = errorMessage.substring(
				0, errorMessage.indexOf("\n"));
		}

		errorMessage = errorMessage.trim();

		if (JenkinsResultsParserUtil.isNullOrEmpty(errorMessage)) {
			return "Failed for unknown reason";
		}

		return errorMessage;
	}

	@Override
	public String getName() {
		return _functionalTestClass.getTestClassMethodName();
	}

	@Override
	public int getPriority() {
		String priority = JenkinsResultsParserUtil.getProperty(
			_functionalTestClass.getPoshiProperties(), "priority");

		if ((priority != null) && priority.matches("\\d+")) {
			return Integer.parseInt(priority);
		}

		return 5;
	}

	@Override
	public Status getStatus() {
		Build build = getBuild();

		if (build == null) {
			return Status.UNTESTED;
		}

		TestResult testResult = getTestResult();

		if (testResult == null) {
			String result = build.getResult();

			if ((result == null) || result.equals("SUCCESS") ||
				result.equals("UNSTABLE")) {

				return Status.UNTESTED;
			}

			return Status.FAILED;
		}

		if (testResult.isFailing()) {
			return Status.FAILED;
		}

		return Status.PASSED;
	}

	@Override
	public String getSubcomponentNames() {
		return JenkinsResultsParserUtil.getProperty(
			_functionalTestClass.getPoshiProperties(),
			"testray.component.names");
	}

	@Override
	public List<TestrayAttachment> getTestrayAttachments() {
		List<TestrayAttachment> testrayAttachments =
			super.getTestrayAttachments();

		testrayAttachments.addAll(getLiferayLogTestrayAttachments());
		testrayAttachments.addAll(getLiferayOSGiLogTestrayAttachments());

		testrayAttachments.add(_getPoshiConsoleTestrayAttachment());
		testrayAttachments.add(_getPoshiReportTestrayAttachment());
		testrayAttachments.add(_getPoshiSummaryTestrayAttachment());

		testrayAttachments.removeAll(Collections.singleton(null));

		return testrayAttachments;
	}

	public TestResult getTestResult() {
		Build build = getBuild();

		if (build == null) {
			return null;
		}

		TestClassResult testClassResult = build.getTestClassResult(
			"com.liferay.poshi.runner.PoshiRunner");

		if (testClassResult == null) {
			testClassResult = build.getTestClassResult(
				"com.liferay.poshi.runner.ParallelPoshiRunner");
		}

		if (testClassResult == null) {
			return null;
		}

		return testClassResult.getTestResult("test[" + getName() + "]");
	}

	@Override
	protected List<TestrayAttachment> getLiferayLogTestrayAttachments() {
		if (getTestResult() == null) {
			return new ArrayList<>();
		}

		return super.getLiferayLogTestrayAttachments();
	}

	@Override
	protected List<TestrayAttachment> getLiferayOSGiLogTestrayAttachments() {
		if (getTestResult() == null) {
			return new ArrayList<>();
		}

		return super.getLiferayOSGiLogTestrayAttachments();
	}

	private TestrayAttachment _getPoshiConsoleTestrayAttachment() {
		if (getTestResult() == null) {
			return null;
		}

		String name = getName();

		name = name.replace("#", "_");

		return getTestrayAttachment(
			getBuild(), "Poshi Console",
			JenkinsResultsParserUtil.combine(
				getAxisBuildURLPath(), "/",
				JenkinsResultsParserUtil.fixURL(name), "/console.txt.gz"));
	}

	private TestrayAttachment _getPoshiReportTestrayAttachment() {
		if (getTestResult() == null) {
			return null;
		}

		String name = getName();

		name = name.replace("#", "_");

		return getTestrayAttachment(
			getBuild(), "Poshi Report",
			JenkinsResultsParserUtil.combine(
				getAxisBuildURLPath(), "/",
				JenkinsResultsParserUtil.fixURL(name), "/index.html.gz"));
	}

	private TestrayAttachment _getPoshiSummaryTestrayAttachment() {
		if (getTestResult() == null) {
			return null;
		}

		String name = getName();

		name = name.replace("#", "_");

		return getTestrayAttachment(
			getBuild(), "Poshi Summary",
			JenkinsResultsParserUtil.combine(
				getAxisBuildURLPath(), "/",
				JenkinsResultsParserUtil.fixURL(name), "/summary.html.gz"));
	}

	private final FunctionalTestClass _functionalTestClass;

}