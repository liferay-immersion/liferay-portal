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

package com.liferay.portal.events;

import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.events.SimpleAction;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.security.auto.login.AutoLogin;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;

import org.osgi.framework.BundleContext;

/**
 * @author Stian Sigvartsen
 */
public class SetupAdminAutoLoginStartupAction extends SimpleAction {

	@Override
	public void run(String[] ids) throws ActionException {
		_bundleContext.registerService(
			AutoLogin.class, new SetupAdminAutoLogin(),
			HashMapDictionaryBuilder.put(
				"component.name", SetupAdminAutoLogin.class.getName()
			).build());
	}

	private final BundleContext _bundleContext =
		SystemBundleUtil.getBundleContext();

}