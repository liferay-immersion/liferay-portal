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

package com.liferay.headless.commerce.admin.pricing.resource.v2_0.test;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.headless.commerce.admin.pricing.client.dto.v2_0.DiscountProductGroup;
import com.liferay.headless.commerce.admin.pricing.client.http.HttpInvoker;
import com.liferay.headless.commerce.admin.pricing.client.pagination.Page;
import com.liferay.headless.commerce.admin.pricing.client.pagination.Pagination;
import com.liferay.headless.commerce.admin.pricing.client.resource.v2_0.DiscountProductGroupResource;
import com.liferay.headless.commerce.admin.pricing.client.serdes.v2_0.DiscountProductGroupSerDes;
import com.liferay.petra.function.UnsafeTriConsumer;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.search.test.util.SearchTestRule;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.resource.EntityModelResource;

import java.lang.reflect.Method;

import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.annotation.Generated;

import javax.ws.rs.core.MultivaluedHashMap;

import org.apache.commons.lang.time.DateUtils;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Zoltán Takács
 * @generated
 */
@Generated("")
public abstract class BaseDiscountProductGroupResourceTestCase {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		_dateFormat = DateFormatFactoryUtil.getSimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");
	}

	@Before
	public void setUp() throws Exception {
		irrelevantGroup = GroupTestUtil.addGroup();
		testGroup = GroupTestUtil.addGroup();

		testCompany = CompanyLocalServiceUtil.getCompany(
			testGroup.getCompanyId());

		_discountProductGroupResource.setContextCompany(testCompany);

		DiscountProductGroupResource.Builder builder =
			DiscountProductGroupResource.builder();

		discountProductGroupResource = builder.authentication(
			"test@liferay.com", "test"
		).locale(
			LocaleUtil.getDefault()
		).build();
	}

	@After
	public void tearDown() throws Exception {
		GroupTestUtil.deleteGroup(irrelevantGroup);
		GroupTestUtil.deleteGroup(testGroup);
	}

	@Test
	public void testClientSerDesToDTO() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper() {
			{
				configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
				configure(
					SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true);
				enable(SerializationFeature.INDENT_OUTPUT);
				setDateFormat(new ISO8601DateFormat());
				setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
				setSerializationInclusion(JsonInclude.Include.NON_NULL);
				setVisibility(
					PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
				setVisibility(
					PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE);
			}
		};

		DiscountProductGroup discountProductGroup1 =
			randomDiscountProductGroup();

		String json = objectMapper.writeValueAsString(discountProductGroup1);

		DiscountProductGroup discountProductGroup2 =
			DiscountProductGroupSerDes.toDTO(json);

		Assert.assertTrue(equals(discountProductGroup1, discountProductGroup2));
	}

	@Test
	public void testClientSerDesToJSON() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper() {
			{
				configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
				configure(
					SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true);
				setDateFormat(new ISO8601DateFormat());
				setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
				setSerializationInclusion(JsonInclude.Include.NON_NULL);
				setVisibility(
					PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
				setVisibility(
					PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE);
			}
		};

		DiscountProductGroup discountProductGroup =
			randomDiscountProductGroup();

		String json1 = objectMapper.writeValueAsString(discountProductGroup);
		String json2 = DiscountProductGroupSerDes.toJSON(discountProductGroup);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		DiscountProductGroup discountProductGroup =
			randomDiscountProductGroup();

		discountProductGroup.setDiscountExternalReferenceCode(regex);
		discountProductGroup.setProductGroupExternalReferenceCode(regex);

		String json = DiscountProductGroupSerDes.toJSON(discountProductGroup);

		Assert.assertFalse(json.contains(regex));

		discountProductGroup = DiscountProductGroupSerDes.toDTO(json);

		Assert.assertEquals(
			regex, discountProductGroup.getDiscountExternalReferenceCode());
		Assert.assertEquals(
			regex, discountProductGroup.getProductGroupExternalReferenceCode());
	}

	@Test
	public void testDeleteDiscountProductGroup() throws Exception {
		Assert.assertTrue(false);
	}

	@Test
	public void testGraphQLDeleteDiscountProductGroup() throws Exception {
		Assert.assertTrue(false);
	}

	@Test
	public void testGetDiscountByExternalReferenceCodeDiscountProductGroupsPage()
		throws Exception {

		String externalReferenceCode =
			testGetDiscountByExternalReferenceCodeDiscountProductGroupsPage_getExternalReferenceCode();
		String irrelevantExternalReferenceCode =
			testGetDiscountByExternalReferenceCodeDiscountProductGroupsPage_getIrrelevantExternalReferenceCode();

		Page<DiscountProductGroup> page =
			discountProductGroupResource.
				getDiscountByExternalReferenceCodeDiscountProductGroupsPage(
					externalReferenceCode, Pagination.of(1, 10));

		Assert.assertEquals(0, page.getTotalCount());

		if (irrelevantExternalReferenceCode != null) {
			DiscountProductGroup irrelevantDiscountProductGroup =
				testGetDiscountByExternalReferenceCodeDiscountProductGroupsPage_addDiscountProductGroup(
					irrelevantExternalReferenceCode,
					randomIrrelevantDiscountProductGroup());

			page =
				discountProductGroupResource.
					getDiscountByExternalReferenceCodeDiscountProductGroupsPage(
						irrelevantExternalReferenceCode, Pagination.of(1, 2));

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantDiscountProductGroup),
				(List<DiscountProductGroup>)page.getItems());
			assertValid(
				page,
				testGetDiscountByExternalReferenceCodeDiscountProductGroupsPage_getExpectedActions(
					irrelevantExternalReferenceCode));
		}

		DiscountProductGroup discountProductGroup1 =
			testGetDiscountByExternalReferenceCodeDiscountProductGroupsPage_addDiscountProductGroup(
				externalReferenceCode, randomDiscountProductGroup());

		DiscountProductGroup discountProductGroup2 =
			testGetDiscountByExternalReferenceCodeDiscountProductGroupsPage_addDiscountProductGroup(
				externalReferenceCode, randomDiscountProductGroup());

		page =
			discountProductGroupResource.
				getDiscountByExternalReferenceCodeDiscountProductGroupsPage(
					externalReferenceCode, Pagination.of(1, 10));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(discountProductGroup1, discountProductGroup2),
			(List<DiscountProductGroup>)page.getItems());
		assertValid(
			page,
			testGetDiscountByExternalReferenceCodeDiscountProductGroupsPage_getExpectedActions(
				externalReferenceCode));
	}

	protected Map<String, Map<String, String>>
			testGetDiscountByExternalReferenceCodeDiscountProductGroupsPage_getExpectedActions(
				String externalReferenceCode)
		throws Exception {

		Map<String, Map<String, String>> expectedActions = new HashMap<>();

		return expectedActions;
	}

	@Test
	public void testGetDiscountByExternalReferenceCodeDiscountProductGroupsPageWithPagination()
		throws Exception {

		String externalReferenceCode =
			testGetDiscountByExternalReferenceCodeDiscountProductGroupsPage_getExternalReferenceCode();

		DiscountProductGroup discountProductGroup1 =
			testGetDiscountByExternalReferenceCodeDiscountProductGroupsPage_addDiscountProductGroup(
				externalReferenceCode, randomDiscountProductGroup());

		DiscountProductGroup discountProductGroup2 =
			testGetDiscountByExternalReferenceCodeDiscountProductGroupsPage_addDiscountProductGroup(
				externalReferenceCode, randomDiscountProductGroup());

		DiscountProductGroup discountProductGroup3 =
			testGetDiscountByExternalReferenceCodeDiscountProductGroupsPage_addDiscountProductGroup(
				externalReferenceCode, randomDiscountProductGroup());

		Page<DiscountProductGroup> page1 =
			discountProductGroupResource.
				getDiscountByExternalReferenceCodeDiscountProductGroupsPage(
					externalReferenceCode, Pagination.of(1, 2));

		List<DiscountProductGroup> discountProductGroups1 =
			(List<DiscountProductGroup>)page1.getItems();

		Assert.assertEquals(
			discountProductGroups1.toString(), 2,
			discountProductGroups1.size());

		Page<DiscountProductGroup> page2 =
			discountProductGroupResource.
				getDiscountByExternalReferenceCodeDiscountProductGroupsPage(
					externalReferenceCode, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<DiscountProductGroup> discountProductGroups2 =
			(List<DiscountProductGroup>)page2.getItems();

		Assert.assertEquals(
			discountProductGroups2.toString(), 1,
			discountProductGroups2.size());

		Page<DiscountProductGroup> page3 =
			discountProductGroupResource.
				getDiscountByExternalReferenceCodeDiscountProductGroupsPage(
					externalReferenceCode, Pagination.of(1, 3));

		assertEqualsIgnoringOrder(
			Arrays.asList(
				discountProductGroup1, discountProductGroup2,
				discountProductGroup3),
			(List<DiscountProductGroup>)page3.getItems());
	}

	protected DiscountProductGroup
			testGetDiscountByExternalReferenceCodeDiscountProductGroupsPage_addDiscountProductGroup(
				String externalReferenceCode,
				DiscountProductGroup discountProductGroup)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetDiscountByExternalReferenceCodeDiscountProductGroupsPage_getExternalReferenceCode()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetDiscountByExternalReferenceCodeDiscountProductGroupsPage_getIrrelevantExternalReferenceCode()
		throws Exception {

		return null;
	}

	@Test
	public void testPostDiscountByExternalReferenceCodeDiscountProductGroup()
		throws Exception {

		DiscountProductGroup randomDiscountProductGroup =
			randomDiscountProductGroup();

		DiscountProductGroup postDiscountProductGroup =
			testPostDiscountByExternalReferenceCodeDiscountProductGroup_addDiscountProductGroup(
				randomDiscountProductGroup);

		assertEquals(randomDiscountProductGroup, postDiscountProductGroup);
		assertValid(postDiscountProductGroup);
	}

	protected DiscountProductGroup
			testPostDiscountByExternalReferenceCodeDiscountProductGroup_addDiscountProductGroup(
				DiscountProductGroup discountProductGroup)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetDiscountIdDiscountProductGroupsPage() throws Exception {
		Long id = testGetDiscountIdDiscountProductGroupsPage_getId();
		Long irrelevantId =
			testGetDiscountIdDiscountProductGroupsPage_getIrrelevantId();

		Page<DiscountProductGroup> page =
			discountProductGroupResource.getDiscountIdDiscountProductGroupsPage(
				id, null, null, Pagination.of(1, 10), null);

		Assert.assertEquals(0, page.getTotalCount());

		if (irrelevantId != null) {
			DiscountProductGroup irrelevantDiscountProductGroup =
				testGetDiscountIdDiscountProductGroupsPage_addDiscountProductGroup(
					irrelevantId, randomIrrelevantDiscountProductGroup());

			page =
				discountProductGroupResource.
					getDiscountIdDiscountProductGroupsPage(
						irrelevantId, null, null, Pagination.of(1, 2), null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantDiscountProductGroup),
				(List<DiscountProductGroup>)page.getItems());
			assertValid(
				page,
				testGetDiscountIdDiscountProductGroupsPage_getExpectedActions(
					irrelevantId));
		}

		DiscountProductGroup discountProductGroup1 =
			testGetDiscountIdDiscountProductGroupsPage_addDiscountProductGroup(
				id, randomDiscountProductGroup());

		DiscountProductGroup discountProductGroup2 =
			testGetDiscountIdDiscountProductGroupsPage_addDiscountProductGroup(
				id, randomDiscountProductGroup());

		page =
			discountProductGroupResource.getDiscountIdDiscountProductGroupsPage(
				id, null, null, Pagination.of(1, 10), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(discountProductGroup1, discountProductGroup2),
			(List<DiscountProductGroup>)page.getItems());
		assertValid(
			page,
			testGetDiscountIdDiscountProductGroupsPage_getExpectedActions(id));
	}

	protected Map<String, Map<String, String>>
			testGetDiscountIdDiscountProductGroupsPage_getExpectedActions(
				Long id)
		throws Exception {

		Map<String, Map<String, String>> expectedActions = new HashMap<>();

		return expectedActions;
	}

	@Test
	public void testGetDiscountIdDiscountProductGroupsPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long id = testGetDiscountIdDiscountProductGroupsPage_getId();

		DiscountProductGroup discountProductGroup1 =
			randomDiscountProductGroup();

		discountProductGroup1 =
			testGetDiscountIdDiscountProductGroupsPage_addDiscountProductGroup(
				id, discountProductGroup1);

		for (EntityField entityField : entityFields) {
			Page<DiscountProductGroup> page =
				discountProductGroupResource.
					getDiscountIdDiscountProductGroupsPage(
						id, null,
						getFilterString(
							entityField, "between", discountProductGroup1),
						Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(discountProductGroup1),
				(List<DiscountProductGroup>)page.getItems());
		}
	}

	@Test
	public void testGetDiscountIdDiscountProductGroupsPageWithFilterDoubleEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DOUBLE);

		if (entityFields.isEmpty()) {
			return;
		}

		Long id = testGetDiscountIdDiscountProductGroupsPage_getId();

		DiscountProductGroup discountProductGroup1 =
			testGetDiscountIdDiscountProductGroupsPage_addDiscountProductGroup(
				id, randomDiscountProductGroup());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		DiscountProductGroup discountProductGroup2 =
			testGetDiscountIdDiscountProductGroupsPage_addDiscountProductGroup(
				id, randomDiscountProductGroup());

		for (EntityField entityField : entityFields) {
			Page<DiscountProductGroup> page =
				discountProductGroupResource.
					getDiscountIdDiscountProductGroupsPage(
						id, null,
						getFilterString(
							entityField, "eq", discountProductGroup1),
						Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(discountProductGroup1),
				(List<DiscountProductGroup>)page.getItems());
		}
	}

	@Test
	public void testGetDiscountIdDiscountProductGroupsPageWithFilterStringEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long id = testGetDiscountIdDiscountProductGroupsPage_getId();

		DiscountProductGroup discountProductGroup1 =
			testGetDiscountIdDiscountProductGroupsPage_addDiscountProductGroup(
				id, randomDiscountProductGroup());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		DiscountProductGroup discountProductGroup2 =
			testGetDiscountIdDiscountProductGroupsPage_addDiscountProductGroup(
				id, randomDiscountProductGroup());

		for (EntityField entityField : entityFields) {
			Page<DiscountProductGroup> page =
				discountProductGroupResource.
					getDiscountIdDiscountProductGroupsPage(
						id, null,
						getFilterString(
							entityField, "eq", discountProductGroup1),
						Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(discountProductGroup1),
				(List<DiscountProductGroup>)page.getItems());
		}
	}

	@Test
	public void testGetDiscountIdDiscountProductGroupsPageWithPagination()
		throws Exception {

		Long id = testGetDiscountIdDiscountProductGroupsPage_getId();

		DiscountProductGroup discountProductGroup1 =
			testGetDiscountIdDiscountProductGroupsPage_addDiscountProductGroup(
				id, randomDiscountProductGroup());

		DiscountProductGroup discountProductGroup2 =
			testGetDiscountIdDiscountProductGroupsPage_addDiscountProductGroup(
				id, randomDiscountProductGroup());

		DiscountProductGroup discountProductGroup3 =
			testGetDiscountIdDiscountProductGroupsPage_addDiscountProductGroup(
				id, randomDiscountProductGroup());

		Page<DiscountProductGroup> page1 =
			discountProductGroupResource.getDiscountIdDiscountProductGroupsPage(
				id, null, null, Pagination.of(1, 2), null);

		List<DiscountProductGroup> discountProductGroups1 =
			(List<DiscountProductGroup>)page1.getItems();

		Assert.assertEquals(
			discountProductGroups1.toString(), 2,
			discountProductGroups1.size());

		Page<DiscountProductGroup> page2 =
			discountProductGroupResource.getDiscountIdDiscountProductGroupsPage(
				id, null, null, Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<DiscountProductGroup> discountProductGroups2 =
			(List<DiscountProductGroup>)page2.getItems();

		Assert.assertEquals(
			discountProductGroups2.toString(), 1,
			discountProductGroups2.size());

		Page<DiscountProductGroup> page3 =
			discountProductGroupResource.getDiscountIdDiscountProductGroupsPage(
				id, null, null, Pagination.of(1, 3), null);

		assertEqualsIgnoringOrder(
			Arrays.asList(
				discountProductGroup1, discountProductGroup2,
				discountProductGroup3),
			(List<DiscountProductGroup>)page3.getItems());
	}

	@Test
	public void testGetDiscountIdDiscountProductGroupsPageWithSortDateTime()
		throws Exception {

		testGetDiscountIdDiscountProductGroupsPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, discountProductGroup1, discountProductGroup2) -> {
				BeanTestUtil.setProperty(
					discountProductGroup1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetDiscountIdDiscountProductGroupsPageWithSortDouble()
		throws Exception {

		testGetDiscountIdDiscountProductGroupsPageWithSort(
			EntityField.Type.DOUBLE,
			(entityField, discountProductGroup1, discountProductGroup2) -> {
				BeanTestUtil.setProperty(
					discountProductGroup1, entityField.getName(), 0.1);
				BeanTestUtil.setProperty(
					discountProductGroup2, entityField.getName(), 0.5);
			});
	}

	@Test
	public void testGetDiscountIdDiscountProductGroupsPageWithSortInteger()
		throws Exception {

		testGetDiscountIdDiscountProductGroupsPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, discountProductGroup1, discountProductGroup2) -> {
				BeanTestUtil.setProperty(
					discountProductGroup1, entityField.getName(), 0);
				BeanTestUtil.setProperty(
					discountProductGroup2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetDiscountIdDiscountProductGroupsPageWithSortString()
		throws Exception {

		testGetDiscountIdDiscountProductGroupsPageWithSort(
			EntityField.Type.STRING,
			(entityField, discountProductGroup1, discountProductGroup2) -> {
				Class<?> clazz = discountProductGroup1.getClass();

				String entityFieldName = entityField.getName();

				Method method = clazz.getMethod(
					"get" + StringUtil.upperCaseFirstLetter(entityFieldName));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanTestUtil.setProperty(
						discountProductGroup1, entityFieldName,
						Collections.singletonMap("Aaa", "Aaa"));
					BeanTestUtil.setProperty(
						discountProductGroup2, entityFieldName,
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else if (entityFieldName.contains("email")) {
					BeanTestUtil.setProperty(
						discountProductGroup1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
					BeanTestUtil.setProperty(
						discountProductGroup2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
				}
				else {
					BeanTestUtil.setProperty(
						discountProductGroup1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
					BeanTestUtil.setProperty(
						discountProductGroup2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
				}
			});
	}

	protected void testGetDiscountIdDiscountProductGroupsPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer
				<EntityField, DiscountProductGroup, DiscountProductGroup,
				 Exception> unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		Long id = testGetDiscountIdDiscountProductGroupsPage_getId();

		DiscountProductGroup discountProductGroup1 =
			randomDiscountProductGroup();
		DiscountProductGroup discountProductGroup2 =
			randomDiscountProductGroup();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(
				entityField, discountProductGroup1, discountProductGroup2);
		}

		discountProductGroup1 =
			testGetDiscountIdDiscountProductGroupsPage_addDiscountProductGroup(
				id, discountProductGroup1);

		discountProductGroup2 =
			testGetDiscountIdDiscountProductGroupsPage_addDiscountProductGroup(
				id, discountProductGroup2);

		for (EntityField entityField : entityFields) {
			Page<DiscountProductGroup> ascPage =
				discountProductGroupResource.
					getDiscountIdDiscountProductGroupsPage(
						id, null, null, Pagination.of(1, 2),
						entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(discountProductGroup1, discountProductGroup2),
				(List<DiscountProductGroup>)ascPage.getItems());

			Page<DiscountProductGroup> descPage =
				discountProductGroupResource.
					getDiscountIdDiscountProductGroupsPage(
						id, null, null, Pagination.of(1, 2),
						entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(discountProductGroup2, discountProductGroup1),
				(List<DiscountProductGroup>)descPage.getItems());
		}
	}

	protected DiscountProductGroup
			testGetDiscountIdDiscountProductGroupsPage_addDiscountProductGroup(
				Long id, DiscountProductGroup discountProductGroup)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetDiscountIdDiscountProductGroupsPage_getId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetDiscountIdDiscountProductGroupsPage_getIrrelevantId()
		throws Exception {

		return null;
	}

	@Test
	public void testPostDiscountIdDiscountProductGroup() throws Exception {
		DiscountProductGroup randomDiscountProductGroup =
			randomDiscountProductGroup();

		DiscountProductGroup postDiscountProductGroup =
			testPostDiscountIdDiscountProductGroup_addDiscountProductGroup(
				randomDiscountProductGroup);

		assertEquals(randomDiscountProductGroup, postDiscountProductGroup);
		assertValid(postDiscountProductGroup);
	}

	protected DiscountProductGroup
			testPostDiscountIdDiscountProductGroup_addDiscountProductGroup(
				DiscountProductGroup discountProductGroup)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

	protected void assertContains(
		DiscountProductGroup discountProductGroup,
		List<DiscountProductGroup> discountProductGroups) {

		boolean contains = false;

		for (DiscountProductGroup item : discountProductGroups) {
			if (equals(discountProductGroup, item)) {
				contains = true;

				break;
			}
		}

		Assert.assertTrue(
			discountProductGroups + " does not contain " + discountProductGroup,
			contains);
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(
		DiscountProductGroup discountProductGroup1,
		DiscountProductGroup discountProductGroup2) {

		Assert.assertTrue(
			discountProductGroup1 + " does not equal " + discountProductGroup2,
			equals(discountProductGroup1, discountProductGroup2));
	}

	protected void assertEquals(
		List<DiscountProductGroup> discountProductGroups1,
		List<DiscountProductGroup> discountProductGroups2) {

		Assert.assertEquals(
			discountProductGroups1.size(), discountProductGroups2.size());

		for (int i = 0; i < discountProductGroups1.size(); i++) {
			DiscountProductGroup discountProductGroup1 =
				discountProductGroups1.get(i);
			DiscountProductGroup discountProductGroup2 =
				discountProductGroups2.get(i);

			assertEquals(discountProductGroup1, discountProductGroup2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<DiscountProductGroup> discountProductGroups1,
		List<DiscountProductGroup> discountProductGroups2) {

		Assert.assertEquals(
			discountProductGroups1.size(), discountProductGroups2.size());

		for (DiscountProductGroup discountProductGroup1 :
				discountProductGroups1) {

			boolean contains = false;

			for (DiscountProductGroup discountProductGroup2 :
					discountProductGroups2) {

				if (equals(discountProductGroup1, discountProductGroup2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				discountProductGroups2 + " does not contain " +
					discountProductGroup1,
				contains);
		}
	}

	protected void assertValid(DiscountProductGroup discountProductGroup)
		throws Exception {

		boolean valid = true;

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (discountProductGroup.getActions() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"discountExternalReferenceCode",
					additionalAssertFieldName)) {

				if (discountProductGroup.getDiscountExternalReferenceCode() ==
						null) {

					valid = false;
				}

				continue;
			}

			if (Objects.equals("discountId", additionalAssertFieldName)) {
				if (discountProductGroup.getDiscountId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"discountProductGroupId", additionalAssertFieldName)) {

				if (discountProductGroup.getDiscountProductGroupId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("productGroup", additionalAssertFieldName)) {
				if (discountProductGroup.getProductGroup() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"productGroupExternalReferenceCode",
					additionalAssertFieldName)) {

				if (discountProductGroup.
						getProductGroupExternalReferenceCode() == null) {

					valid = false;
				}

				continue;
			}

			if (Objects.equals("productGroupId", additionalAssertFieldName)) {
				if (discountProductGroup.getProductGroupId() == null) {
					valid = false;
				}

				continue;
			}

			throw new IllegalArgumentException(
				"Invalid additional assert field name " +
					additionalAssertFieldName);
		}

		Assert.assertTrue(valid);
	}

	protected void assertValid(Page<DiscountProductGroup> page) {
		assertValid(page, Collections.emptyMap());
	}

	protected void assertValid(
		Page<DiscountProductGroup> page,
		Map<String, Map<String, String>> expectedActions) {

		boolean valid = false;

		java.util.Collection<DiscountProductGroup> discountProductGroups =
			page.getItems();

		int size = discountProductGroups.size();

		if ((page.getLastPage() > 0) && (page.getPage() > 0) &&
			(page.getPageSize() > 0) && (page.getTotalCount() > 0) &&
			(size > 0)) {

			valid = true;
		}

		Assert.assertTrue(valid);

		assertValid(page.getActions(), expectedActions);
	}

	protected void assertValid(
		Map<String, Map<String, String>> actions1,
		Map<String, Map<String, String>> actions2) {

		for (String key : actions2.keySet()) {
			Map action = actions1.get(key);

			Assert.assertNotNull(key + " does not contain an action", action);

			Map<String, String> expectedAction = actions2.get(key);

			Assert.assertEquals(
				expectedAction.get("method"), action.get("method"));
			Assert.assertEquals(expectedAction.get("href"), action.get("href"));
		}
	}

	protected String[] getAdditionalAssertFieldNames() {
		return new String[0];
	}

	protected List<GraphQLField> getGraphQLFields() throws Exception {
		List<GraphQLField> graphQLFields = new ArrayList<>();

		for (java.lang.reflect.Field field :
				getDeclaredFields(
					com.liferay.headless.commerce.admin.pricing.dto.v2_0.
						DiscountProductGroup.class)) {

			if (!ArrayUtil.contains(
					getAdditionalAssertFieldNames(), field.getName())) {

				continue;
			}

			graphQLFields.addAll(getGraphQLFields(field));
		}

		return graphQLFields;
	}

	protected List<GraphQLField> getGraphQLFields(
			java.lang.reflect.Field... fields)
		throws Exception {

		List<GraphQLField> graphQLFields = new ArrayList<>();

		for (java.lang.reflect.Field field : fields) {
			com.liferay.portal.vulcan.graphql.annotation.GraphQLField
				vulcanGraphQLField = field.getAnnotation(
					com.liferay.portal.vulcan.graphql.annotation.GraphQLField.
						class);

			if (vulcanGraphQLField != null) {
				Class<?> clazz = field.getType();

				if (clazz.isArray()) {
					clazz = clazz.getComponentType();
				}

				List<GraphQLField> childrenGraphQLFields = getGraphQLFields(
					getDeclaredFields(clazz));

				graphQLFields.add(
					new GraphQLField(field.getName(), childrenGraphQLFields));
			}
		}

		return graphQLFields;
	}

	protected String[] getIgnoredEntityFieldNames() {
		return new String[0];
	}

	protected boolean equals(
		DiscountProductGroup discountProductGroup1,
		DiscountProductGroup discountProductGroup2) {

		if (discountProductGroup1 == discountProductGroup2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (!equals(
						(Map)discountProductGroup1.getActions(),
						(Map)discountProductGroup2.getActions())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"discountExternalReferenceCode",
					additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						discountProductGroup1.
							getDiscountExternalReferenceCode(),
						discountProductGroup2.
							getDiscountExternalReferenceCode())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("discountId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						discountProductGroup1.getDiscountId(),
						discountProductGroup2.getDiscountId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"discountProductGroupId", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						discountProductGroup1.getDiscountProductGroupId(),
						discountProductGroup2.getDiscountProductGroupId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("productGroup", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						discountProductGroup1.getProductGroup(),
						discountProductGroup2.getProductGroup())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"productGroupExternalReferenceCode",
					additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						discountProductGroup1.
							getProductGroupExternalReferenceCode(),
						discountProductGroup2.
							getProductGroupExternalReferenceCode())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("productGroupId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						discountProductGroup1.getProductGroupId(),
						discountProductGroup2.getProductGroupId())) {

					return false;
				}

				continue;
			}

			throw new IllegalArgumentException(
				"Invalid additional assert field name " +
					additionalAssertFieldName);
		}

		return true;
	}

	protected boolean equals(
		Map<String, Object> map1, Map<String, Object> map2) {

		if (Objects.equals(map1.keySet(), map2.keySet())) {
			for (Map.Entry<String, Object> entry : map1.entrySet()) {
				if (entry.getValue() instanceof Map) {
					if (!equals(
							(Map)entry.getValue(),
							(Map)map2.get(entry.getKey()))) {

						return false;
					}
				}
				else if (!Objects.deepEquals(
							entry.getValue(), map2.get(entry.getKey()))) {

					return false;
				}
			}

			return true;
		}

		return false;
	}

	protected java.lang.reflect.Field[] getDeclaredFields(Class clazz)
		throws Exception {

		return TransformUtil.transform(
			ReflectionUtil.getDeclaredFields(clazz),
			field -> {
				if (field.isSynthetic()) {
					return null;
				}

				return field;
			},
			java.lang.reflect.Field.class);
	}

	protected java.util.Collection<EntityField> getEntityFields()
		throws Exception {

		if (!(_discountProductGroupResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_discountProductGroupResource;

		EntityModel entityModel = entityModelResource.getEntityModel(
			new MultivaluedHashMap());

		if (entityModel == null) {
			return Collections.emptyList();
		}

		Map<String, EntityField> entityFieldsMap =
			entityModel.getEntityFieldsMap();

		return entityFieldsMap.values();
	}

	protected List<EntityField> getEntityFields(EntityField.Type type)
		throws Exception {

		return TransformUtil.transform(
			getEntityFields(),
			entityField -> {
				if (!Objects.equals(entityField.getType(), type) ||
					ArrayUtil.contains(
						getIgnoredEntityFieldNames(), entityField.getName())) {

					return null;
				}

				return entityField;
			});
	}

	protected String getFilterString(
		EntityField entityField, String operator,
		DiscountProductGroup discountProductGroup) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("actions")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("discountExternalReferenceCode")) {
			sb.append("'");
			sb.append(
				String.valueOf(
					discountProductGroup.getDiscountExternalReferenceCode()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("discountId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("discountProductGroupId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("productGroup")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("productGroupExternalReferenceCode")) {
			sb.append("'");
			sb.append(
				String.valueOf(
					discountProductGroup.
						getProductGroupExternalReferenceCode()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("productGroupId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		throw new IllegalArgumentException(
			"Invalid entity field " + entityFieldName);
	}

	protected String invoke(String query) throws Exception {
		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.body(
			JSONUtil.put(
				"query", query
			).toString(),
			"application/json");
		httpInvoker.httpMethod(HttpInvoker.HttpMethod.POST);
		httpInvoker.path("http://localhost:8080/o/graphql");
		httpInvoker.userNameAndPassword("test@liferay.com:test");

		HttpInvoker.HttpResponse httpResponse = httpInvoker.invoke();

		return httpResponse.getContent();
	}

	protected JSONObject invokeGraphQLMutation(GraphQLField graphQLField)
		throws Exception {

		GraphQLField mutationGraphQLField = new GraphQLField(
			"mutation", graphQLField);

		return JSONFactoryUtil.createJSONObject(
			invoke(mutationGraphQLField.toString()));
	}

	protected JSONObject invokeGraphQLQuery(GraphQLField graphQLField)
		throws Exception {

		GraphQLField queryGraphQLField = new GraphQLField(
			"query", graphQLField);

		return JSONFactoryUtil.createJSONObject(
			invoke(queryGraphQLField.toString()));
	}

	protected DiscountProductGroup randomDiscountProductGroup()
		throws Exception {

		return new DiscountProductGroup() {
			{
				discountExternalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				discountId = RandomTestUtil.randomLong();
				discountProductGroupId = RandomTestUtil.randomLong();
				productGroupExternalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				productGroupId = RandomTestUtil.randomLong();
			}
		};
	}

	protected DiscountProductGroup randomIrrelevantDiscountProductGroup()
		throws Exception {

		DiscountProductGroup randomIrrelevantDiscountProductGroup =
			randomDiscountProductGroup();

		return randomIrrelevantDiscountProductGroup;
	}

	protected DiscountProductGroup randomPatchDiscountProductGroup()
		throws Exception {

		return randomDiscountProductGroup();
	}

	protected DiscountProductGroupResource discountProductGroupResource;
	protected Group irrelevantGroup;
	protected Company testCompany;
	protected Group testGroup;

	protected static class BeanTestUtil {

		public static void copyProperties(Object source, Object target)
			throws Exception {

			Class<?> sourceClass = _getSuperClass(source.getClass());

			Class<?> targetClass = target.getClass();

			for (java.lang.reflect.Field field :
					sourceClass.getDeclaredFields()) {

				if (field.isSynthetic()) {
					continue;
				}

				Method getMethod = _getMethod(
					sourceClass, field.getName(), "get");

				Method setMethod = _getMethod(
					targetClass, field.getName(), "set",
					getMethod.getReturnType());

				setMethod.invoke(target, getMethod.invoke(source));
			}
		}

		public static boolean hasProperty(Object bean, String name) {
			Method setMethod = _getMethod(
				bean.getClass(), "set" + StringUtil.upperCaseFirstLetter(name));

			if (setMethod != null) {
				return true;
			}

			return false;
		}

		public static void setProperty(Object bean, String name, Object value)
			throws Exception {

			Class<?> clazz = bean.getClass();

			Method setMethod = _getMethod(
				clazz, "set" + StringUtil.upperCaseFirstLetter(name));

			if (setMethod == null) {
				throw new NoSuchMethodException();
			}

			Class<?>[] parameterTypes = setMethod.getParameterTypes();

			setMethod.invoke(bean, _translateValue(parameterTypes[0], value));
		}

		private static Method _getMethod(Class<?> clazz, String name) {
			for (Method method : clazz.getMethods()) {
				if (name.equals(method.getName()) &&
					(method.getParameterCount() == 1) &&
					_parameterTypes.contains(method.getParameterTypes()[0])) {

					return method;
				}
			}

			return null;
		}

		private static Method _getMethod(
				Class<?> clazz, String fieldName, String prefix,
				Class<?>... parameterTypes)
			throws Exception {

			return clazz.getMethod(
				prefix + StringUtil.upperCaseFirstLetter(fieldName),
				parameterTypes);
		}

		private static Class<?> _getSuperClass(Class<?> clazz) {
			Class<?> superClass = clazz.getSuperclass();

			if ((superClass == null) || (superClass == Object.class)) {
				return clazz;
			}

			return superClass;
		}

		private static Object _translateValue(
			Class<?> parameterType, Object value) {

			if ((value instanceof Integer) &&
				parameterType.equals(Long.class)) {

				Integer intValue = (Integer)value;

				return intValue.longValue();
			}

			return value;
		}

		private static final Set<Class<?>> _parameterTypes = new HashSet<>(
			Arrays.asList(
				Boolean.class, Date.class, Double.class, Integer.class,
				Long.class, Map.class, String.class));

	}

	protected class GraphQLField {

		public GraphQLField(String key, GraphQLField... graphQLFields) {
			this(key, new HashMap<>(), graphQLFields);
		}

		public GraphQLField(String key, List<GraphQLField> graphQLFields) {
			this(key, new HashMap<>(), graphQLFields);
		}

		public GraphQLField(
			String key, Map<String, Object> parameterMap,
			GraphQLField... graphQLFields) {

			_key = key;
			_parameterMap = parameterMap;
			_graphQLFields = Arrays.asList(graphQLFields);
		}

		public GraphQLField(
			String key, Map<String, Object> parameterMap,
			List<GraphQLField> graphQLFields) {

			_key = key;
			_parameterMap = parameterMap;
			_graphQLFields = graphQLFields;
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder(_key);

			if (!_parameterMap.isEmpty()) {
				sb.append("(");

				for (Map.Entry<String, Object> entry :
						_parameterMap.entrySet()) {

					sb.append(entry.getKey());
					sb.append(": ");
					sb.append(entry.getValue());
					sb.append(", ");
				}

				sb.setLength(sb.length() - 2);

				sb.append(")");
			}

			if (!_graphQLFields.isEmpty()) {
				sb.append("{");

				for (GraphQLField graphQLField : _graphQLFields) {
					sb.append(graphQLField.toString());
					sb.append(", ");
				}

				sb.setLength(sb.length() - 2);

				sb.append("}");
			}

			return sb.toString();
		}

		private final List<GraphQLField> _graphQLFields;
		private final String _key;
		private final Map<String, Object> _parameterMap;

	}

	private static final com.liferay.portal.kernel.log.Log _log =
		LogFactoryUtil.getLog(BaseDiscountProductGroupResourceTestCase.class);

	private static DateFormat _dateFormat;

	@Inject
	private com.liferay.headless.commerce.admin.pricing.resource.v2_0.
		DiscountProductGroupResource _discountProductGroupResource;

}