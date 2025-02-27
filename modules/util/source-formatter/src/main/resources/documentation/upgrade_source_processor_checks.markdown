# Checks for .bnd, .ftl, .gradle, .java, .jsp, .jspf or .vm

Check | Category | Description
----- | -------- | -----------
UpgradeBNDIncludeResourceCheck | [Upgrade](upgrade_checks.markdown#upgrade-checks) | Checks if the property value `-includeresource` or `Include-Resource` exists and removes it |
UpgradeGetImagePreviewURLMethodCheck | [Upgrade](upgrade_checks.markdown#upgrade-checks) | Replaces the references of the method 'DLUtil.getImagePreviewURL' with the method 'getImagePreviewURL' of 'DLURLHelper' class |
UpgradeGetPortletGroupIdMethodCheck | [Upgrade](upgrade_checks.markdown#upgrade-checks) | Run code migration of the method 'getPortletGroupId' to 'getScopeGroupId' |
UpgradeGradleIncludeResourceCheck | [Upgrade](upgrade_checks.markdown#upgrade-checks) | Replaces with `compileInclude` the configuration attribute for dependencies in `build.gradle` that are listed at `Include-Resource` property at `bnd.bnd` associated file. |
UpgradeJavaAddFolderParameterCheck | [Upgrade](upgrade_checks.markdown#upgrade-checks) | Fill the new parameter of the method `addFolder` of `JournalFolderService`, `JournalFolderLocalService`, and `JournalFolderLocalServiceUtil` classes |
UpgradeJavaAssetEntryAssetCategoriesCheck | [Upgrade](upgrade_checks.markdown#upgrade-checks) | Replaces methods referring to class `AssetEntryAssetCategory` in class `AssetCategoryLocalService` with equivalent methods in class `AssetEntryAssetCategoryRelLocalService`. |
UpgradeJavaCheck | [Upgrade](upgrade_checks.markdown#upgrade-checks) | Performs upgrade checks for `java` files |
UpgradeJavaExtractTextMethodCheck | [Upgrade](upgrade_checks.markdown#upgrade-checks) | Replaces the references of the method `HtmlUtil.extractText(` with the method `extractText(` of `HtmlParser` class |
UpgradeJavaFDSActionProviderCheck | [Upgrade](upgrade_checks.markdown#upgrade-checks) | Reorder parameters in the getDropdownItems method of the FDSDataProvider interface |
UpgradeJavaFDSDataProviderCheck | [Upgrade](upgrade_checks.markdown#upgrade-checks) | Reorder parameters in the getItems and getItemsCount methods of the FDSDataProvider interface |
UpgradeJavaMultiVMPoolUtilCheck | [Upgrade](upgrade_checks.markdown#upgrade-checks) | Replaces the references of the MultiVMPoolUtil class and also its methods usages. |
UpgradeJavaPortletSharedSearchSettingsCheck | [Upgrade](upgrade_checks.markdown#upgrade-checks) | Replaces the Optional return type of the methods `getParameterValues` and `getPortletPreferences` of `PortletSharedSearchSettings` class |
UpgradeJavaServiceReferenceAnnotationCheck | [Upgrade](upgrade_checks.markdown#upgrade-checks) | Run code migration to replace '@ServiceReference' by '@Reference' |
UpgradeSetResultsSetTotalMethodCheck | [Upgrade](upgrade_checks.markdown#upgrade-checks) | Run code migration of method searchContainer.setResults to the searchContainer.setResultsAndTotal and delete searchContainer.setTotal |
UpgradeVelocityCommentMigrationCheck | [Upgrade](upgrade_checks.markdown#upgrade-checks) | Run code migration of comments from a Velocity file to a Freemarker file with the syntax replacements |
UpgradeVelocityFileImportMigrationCheck | [Upgrade](upgrade_checks.markdown#upgrade-checks) | Run code migration of file import from a Velocity file to a Freemarker file with the syntax replacements |
UpgradeVelocityForeachMigrationCheck | [Upgrade](upgrade_checks.markdown#upgrade-checks) | Run code migration of references to Foreach statement from a Velocity file to a Freemarker file with the syntax replacements |
UpgradeVelocityIfStatementsMigrationCheck | [Upgrade](upgrade_checks.markdown#upgrade-checks) | Run code migration of references to If statements from a Velocity file to a Freemarker file with the syntax replacements |
UpgradeVelocityLiferayTaglibReferenceMigrationCheck | [Upgrade](upgrade_checks.markdown#upgrade-checks) | Run code migration of references to specific Liferay taglib from a Velocity file to a Freemarker file with the syntax replacements |
UpgradeVelocityMacroDeclarationMigrationCheck | [Upgrade](upgrade_checks.markdown#upgrade-checks) | Run code migration of references to Macro statement from a Velocity file to a Freemarker file with the syntax replacements |
UpgradeVelocityMacroReferenceMigrationCheck | [Upgrade](upgrade_checks.markdown#upgrade-checks) | Run code migration of references to a custom Macro statement from a Velocity file to a Freemarker file with the syntax replacements |
UpgradeVelocityVariableReferenceMigrationCheck | [Upgrade](upgrade_checks.markdown#upgrade-checks) | Run code migration of references to variables from a Velocity file to a Freemarker file with the syntax replacements |
UpgradeVelocityVariableSetMigrationCheck | [Upgrade](upgrade_checks.markdown#upgrade-checks) | Run code migration of set variables from a Velocity file to a Freemarker file with the syntax replacements |