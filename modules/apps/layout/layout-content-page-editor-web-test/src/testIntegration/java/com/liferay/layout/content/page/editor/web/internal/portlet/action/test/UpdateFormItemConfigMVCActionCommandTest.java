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

package com.liferay.layout.content.page.editor.web.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.runtime.ServiceComponentRuntime;
import org.osgi.service.component.runtime.dto.ComponentDescriptionDTO;

/**
 * @author Lourdes Fernández Besada
 */
@RunWith(Arquillian.class)
public class UpdateFormItemConfigMVCActionCommandTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_company = _companyLocalService.getCompany(_group.getCompanyId());
	}

	private Company _company;

	@Inject
	private CompanyLocalService _companyLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject(
		filter = "mvc.command.name=/layout_content_page_editor/update_form_item_config"
	)
	private MVCActionCommand _mvcActionCommand;

	@Inject
	private ServiceComponentRuntime _serviceComponentRuntime;

	private class ComponentEnablerTemporarySwapper implements AutoCloseable {

		public ComponentEnablerTemporarySwapper(
			String bundleSymbolicName, String componentClassName,
			boolean enabled) {

			BundleContext bundleContext = SystemBundleUtil.getBundleContext();

			ComponentDescriptionDTO componentDescriptionDTO = null;

			for (Bundle bundle : bundleContext.getBundles()) {
				String symbolicName = bundle.getSymbolicName();

				if (symbolicName.startsWith(bundleSymbolicName)) {
					componentDescriptionDTO =
						_serviceComponentRuntime.getComponentDescriptionDTO(
							bundle, componentClassName);

					break;
				}
			}

			Assert.assertNotNull(componentDescriptionDTO);

			_componentDescriptionDTO = componentDescriptionDTO;

			_componentEnabled = _serviceComponentRuntime.isComponentEnabled(
				_componentDescriptionDTO);

			if (enabled) {
				_serviceComponentRuntime.enableComponent(
					_componentDescriptionDTO);
			}
			else {
				_serviceComponentRuntime.disableComponent(
					_componentDescriptionDTO);
			}
		}

		@Override
		public void close() throws Exception {
			if (_componentDescriptionDTO == null) {
				return;
			}

			if (_componentEnabled) {
				_serviceComponentRuntime.enableComponent(
					_componentDescriptionDTO);
			}
			else {
				_serviceComponentRuntime.disableComponent(
					_componentDescriptionDTO);
			}
		}

		private final ComponentDescriptionDTO _componentDescriptionDTO;
		private final boolean _componentEnabled;

	}

}