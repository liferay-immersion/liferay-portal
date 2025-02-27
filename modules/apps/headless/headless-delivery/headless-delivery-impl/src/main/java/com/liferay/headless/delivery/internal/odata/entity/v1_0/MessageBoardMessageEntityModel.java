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

package com.liferay.headless.delivery.internal.odata.entity.v1_0;

import com.liferay.headless.common.spi.odata.entity.EntityFieldsMapFactory;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.odata.entity.BooleanEntityField;
import com.liferay.portal.odata.entity.CollectionEntityField;
import com.liferay.portal.odata.entity.ComplexEntityField;
import com.liferay.portal.odata.entity.DateTimeEntityField;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.odata.entity.IntegerEntityField;
import com.liferay.portal.odata.entity.StringEntityField;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author Javier Gamarra
 */
public class MessageBoardMessageEntityModel implements EntityModel {

	public MessageBoardMessageEntityModel(List<EntityField> entityFields) {
		_entityFieldsMap = EntityFieldsMapFactory.create(
			new BooleanEntityField("answered", locale -> "answered"),
			new BooleanEntityField("showAsQuestion", locale -> "question"),
			new CollectionEntityField(
				new StringEntityField(
					"keywords", locale -> "assetTagNames.raw")),
			new CollectionEntityField(
				new IntegerEntityField(
					"taxonomyCategoryIds", locale -> "assetCategoryIds")),
			new ComplexEntityField(
				"creator",
				Collections.singletonList(
					new IntegerEntityField("id", locale -> "userId"))),
			new ComplexEntityField("customFields", entityFields),
			new DateTimeEntityField(
				"dateCreated",
				locale -> Field.getSortableFieldName(Field.CREATE_DATE),
				locale -> Field.CREATE_DATE),
			new DateTimeEntityField(
				"dateModified",
				locale -> Field.getSortableFieldName(Field.MODIFIED_DATE),
				locale -> Field.MODIFIED_DATE),
			new DateTimeEntityField(
				"lastPostDate",
				locale -> Field.getSortableFieldName("lastPostDate"),
				locale -> "lastPostDate"),
			new EntityField(
				"showAsAnswer", EntityField.Type.BOOLEAN,
				locale -> Field.getSortableFieldName("answer_String"),
				locale -> "answer", String::valueOf),
			new IntegerEntityField(
				"childMessagesCount", locale -> "childMessagesCount"),
			new IntegerEntityField("creatorId", locale -> Field.USER_ID),
			new IntegerEntityField(
				"messageBoardSectionId", locale -> Field.CATEGORY_ID),
			new IntegerEntityField(
				"messageBoardThreadId", locale -> Field.ROOT_ENTRY_CLASS_PK),
			new IntegerEntityField(
				"parentMessageBoardMessageId", locale -> "parentMessageId"),
			new IntegerEntityField(
				"ratingsStatTotalScore",
				locale -> Field.getSortableFieldName("ratingsStatTotalScore")),
			new IntegerEntityField(
				"viewCount", locale -> Field.getSortableFieldName("viewCount")),
			new StringEntityField(
				"friendlyUrlPath",
				locale -> Field.getSortableFieldName("urlSubject_String")),
			new StringEntityField(
				"headline",
				locale -> Field.getSortableFieldName(
					"localized_title_".concat(
						LocaleUtil.toLanguageId(locale)))));
	}

	@Override
	public Map<String, EntityField> getEntityFieldsMap() {
		return _entityFieldsMap;
	}

	private final Map<String, EntityField> _entityFieldsMap;

}