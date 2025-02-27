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

package com.liferay.ai.creator.openai.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Lourdes Fernández Besada
 */
@ExtendedObjectClassDefinition(
	category = "ai-creator", generateUI = false,
	scope = ExtendedObjectClassDefinition.Scope.COMPANY
)
@Meta.OCD(
	id = "com.liferay.ai.creator.openai.configuration.AICreatorOpenAICompanyConfiguration",
	localization = "content/Language",
	name = "ai-creator-openai-company-configuration-name"
)
public interface AICreatorOpenAICompanyConfiguration {

	@Meta.AD(
		deflt = "true", name = "enable-openai-to-create-content",
		required = false
	)
	public boolean enableOpenAIToCreateContent();

	@Meta.AD(deflt = "", name = "api-key", required = false)
	public String apiKey();

}