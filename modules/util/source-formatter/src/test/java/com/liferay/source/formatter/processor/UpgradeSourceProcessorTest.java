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

package com.liferay.source.formatter.processor;

import com.liferay.source.formatter.SourceFormatterArgs;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * @author Kevin Lee
 */
public class UpgradeSourceProcessorTest extends BaseSourceProcessorTestCase {

	@Test
	public void testGradleUpgradeReleaseDxpCheck() throws Exception {
		test("upgrade/GradleUpgradeReleaseDxpCheck.testgradle");
	}

	@Test
	public void testPropertiesUpgradeLiferayPluginPackageFileCheck()
		throws Exception {

		test("upgrade/liferay-plugin-package.testproperties");
	}

	@Test
	public void testUpgradeBNDIncludeResourceCheck() throws Exception {
		test("upgrade/upgrade-include-resource-check/bnd.testbnd");
	}

	@Test
	public void testUpgradeGetImagePreviewURLMethodCheck() throws Exception {
		test("upgrade/UpgradeJavaGetImagePreviewURLMethodCheck.testjava");
		test("upgrade/UpgradeJSPGetImagePreviewURLMethodCheck.testjsp");
	}

	@Test
	public void testUpgradeGetPortletGroupIdMethodCheck() throws Exception {
		test("upgrade/UpgradeFTLGetPortletGroupIdMethodCheck.testftl");
		test("upgrade/UpgradeJavaGetPortletGroupIdMethodCheck.testjava");
		test("upgrade/UpgradeJSPGetPortletGroupIdMethodCheck.testjsp");
	}

	@Test
	public void testUpgradeGradleIncludeResourceCheck() throws Exception {
		test(
			SourceProcessorTestParameters.create(
				"upgrade/upgrade-include-resource-check/build.testgradle"
			).addDependentFileName(
				"upgrade/upgrade-include-resource-check/bnd.testbnd"
			));
	}

	@Test
	public void testUpgradeJavaAddFolderParameterCheck() throws Exception {
		test("upgrade/UpgradeJavaAddFolderParameterCheck.testjava");
	}

	@Test
	public void testUpgradeJavaAssetEntryAssetCategoriesCheck()
		throws Exception {

		test("upgrade/UpgradeJavaAssetEntryAssetCategoriesCheck.testjava");
	}

	@Test
	public void testUpgradeJavaCheck() throws Exception {
		test("upgrade/UpgradeJavaCheck.testjava");
	}

	@Test
	public void testUpgradeJavaExtractTextMethodCheck() throws Exception {
		test("upgrade/UpgradeJavaExtractTextMethodCheck.testjava");
	}

	@Test
	public void testUpgradeJavaFDSActionProviderCheck() throws Exception {
		test("upgrade/UpgradeJavaFDSActionProviderCheck.testjava");
	}

	@Test
	public void testUpgradeJavaFDSDataProviderCheck() throws Exception {
		test("upgrade/UpgradeJavaFDSDataProviderCheck.testjava");
	}

	@Test
	public void testUpgradeJavaModelPermissionsCheck() throws Exception {
		test("upgrade/UpgradeJavaModelPermissionsCheck.testjava");
	}

	@Test
	public void testUpgradeJavaMultiVMPoolUtilCheck() throws Exception {
		test(
			"upgrade/UpgradeJavaMultiVMPoolUtilCheck.testjava",
			"Could not resolve types for MultiVMPool.getPortalCache(). " +
				"Replace 'TO_BE_REPLACED' with the correct type");
	}

	@Test
	public void testUpgradeJavaPortletSharedSearchSettingsCheck()
		throws Exception {

		test("upgrade/UpgradeJavaPortletSharedSearchSettingsCheck.testjava");
	}

	@Test
	public void testUpgradeJavaServiceReferenceAnnotationCheck()
		throws Exception {

		test("upgrade/UpgradeJavaServiceReferenceAnnotationCheck.testjava");
	}

	@Test
	public void testUpgradeJavaServiceTrackerListCheck() throws Exception {
		test("upgrade/UpgradeJavaServiceTrackerListCheck.testjava");
	}

	@Test
	public void testUpgradeSetResultsSetTotalMethodCheck() throws Exception {
		test("upgrade/UpgradeJavaSetResultsSetTotalMethodCheck.testjava");
		test("upgrade/UpgradeJSPSetResultsSetTotalMethodCheck.testjsp");
		test("upgrade/UpgradeJSPFSetResultsSetTotalMethodCheck.testjspf");
	}

	@Test
	public void testUpgradeVelocityMigrationCheck() throws Exception {
		test(
			SourceProcessorTestParameters.create(
				"upgrade/UpgradeVelocityMigrationCheck.testvm"
			).setExpectedFileName(
				"upgrade/migrated/UpgradeVelocityMigrationCheck.testftl"
			));
	}

	@Test
	public void testXMLUpgradeDTDVersionCheck() throws Exception {
		test("upgrade/XMLUpgradeDTDVersionCheck.testxml");
	}

	@Override
	protected SourceFormatterArgs getSourceFormatterArgs() {
		List<String> checkCategoryNames = new ArrayList<>();

		checkCategoryNames.add("Upgrade");

		List<String> sourceFormatterProperties = new ArrayList<>();

		sourceFormatterProperties.add(
			"upgrade.to.version=" + _UPGRADE_TO_VERSION);

		SourceFormatterArgs sourceFormatterArgs =
			super.getSourceFormatterArgs();

		sourceFormatterArgs.setCheckCategoryNames(checkCategoryNames);
		sourceFormatterArgs.setJavaParserEnabled(false);
		sourceFormatterArgs.setSourceFormatterProperties(
			sourceFormatterProperties);

		return sourceFormatterArgs;
	}

	private static final String _UPGRADE_TO_VERSION = "7.4.13.u27";

}