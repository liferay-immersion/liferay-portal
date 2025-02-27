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

package org.gradle.internal.resource.transport.http;

import org.gradle.internal.resource.connector.ResourceConnectorSpecification;
import org.gradle.internal.resource.transfer.DefaultExternalResourceConnector;
import org.gradle.internal.resource.transfer.ExternalResourceConnector;

/**
 * @author Andrea Di Giorgi
 */
public class LiferayHttpConnectorFactory extends HttpConnectorFactory {

	public LiferayHttpConnectorFactory(
		SslContextFactory sslContextFactory,
		HttpClientHelper.Factory httpClientHelperFactory) {

		super(sslContextFactory, httpClientHelperFactory);

		_sslContextFactory = sslContextFactory;
		_httpClientHelperFactory = httpClientHelperFactory;
	}

	@Override
	public ExternalResourceConnector createResourceConnector(
		ResourceConnectorSpecification resourceConnectorSpecification) {

		DefaultHttpSettings.Builder builder = DefaultHttpSettings.builder();

		builder.withAuthenticationSettings(
			resourceConnectorSpecification.getAuthentications());
		builder.withSslContextFactory(_sslContextFactory);

		HttpSettings httpSettings = builder.build();

		HttpClientHelper httpClientHelper = _httpClientHelperFactory.create(
			httpSettings);

		HttpResourceAccessor httpResourceAccessor =
			new LiferayHttpResourceAccessor(httpClientHelper);

		HttpResourceLister httpResourceLister = new HttpResourceLister(
			httpResourceAccessor);

		HttpResourceUploader httpResourceUploader = new HttpResourceUploader(
			httpClientHelper);

		return new DefaultExternalResourceConnector(
			httpResourceAccessor, httpResourceLister, httpResourceUploader);
	}

	private final HttpClientHelper.Factory _httpClientHelperFactory;
	private final SslContextFactory _sslContextFactory;

}