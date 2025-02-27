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

package com.liferay.object.rest.internal.vulcan.openapi.contributor;

import com.liferay.object.constants.ObjectActionTriggerConstants;
import com.liferay.object.constants.ObjectRelationshipConstants;
import com.liferay.object.model.ObjectAction;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.rest.internal.vulcan.openapi.contributor.util.OpenAPIContributorUtil;
import com.liferay.object.rest.openapi.v1_0.ObjectEntryOpenAPIResource;
import com.liferay.object.rest.openapi.v1_0.ObjectEntryOpenAPIResourceProvider;
import com.liferay.object.service.ObjectActionLocalService;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.object.system.SystemObjectDefinitionManagerRegistry;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.openapi.OpenAPIContext;
import com.liferay.portal.vulcan.resource.OpenAPIResource;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.media.ArraySchema;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MapSchema;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.ObjectSchema;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.osgi.framework.BundleContext;

/**
 * @author Alejandro Tardín
 */
public class ObjectEntryOpenAPIContributor extends BaseOpenAPIContributor {

	public ObjectEntryOpenAPIContributor(
		boolean addRelatedSchemas, BundleContext bundleContext,
		DTOConverterRegistry dtoConverterRegistry,
		ObjectActionLocalService objectActionLocalService,
		ObjectDefinition objectDefinition,
		ObjectDefinitionLocalService objectDefinitionLocalService,
		ObjectEntryOpenAPIResource objectEntryOpenAPIResource,
		ObjectEntryOpenAPIResourceProvider objectEntryOpenAPIResourceProvider,
		ObjectRelationshipLocalService objectRelationshipLocalService,
		OpenAPIResource openAPIResource,
		SystemObjectDefinitionManagerRegistry
			systemObjectDefinitionManagerRegistry) {

		_addRelatedSchemas = addRelatedSchemas;
		_bundleContext = bundleContext;
		_objectActionLocalService = objectActionLocalService;
		_objectDefinition = objectDefinition;
		_objectDefinitionLocalService = objectDefinitionLocalService;
		_objectEntryOpenAPIResource = objectEntryOpenAPIResource;
		_objectEntryOpenAPIResourceProvider =
			objectEntryOpenAPIResourceProvider;
		_objectRelationshipLocalService = objectRelationshipLocalService;
		_openAPIResource = openAPIResource;

		init(dtoConverterRegistry, systemObjectDefinitionManagerRegistry);
	}

	@Override
	public void contribute(OpenAPI openAPI, OpenAPIContext openAPIContext)
		throws Exception {

		List<ObjectAction> objectActions =
			_objectActionLocalService.getObjectActions(
				_objectDefinition.getObjectDefinitionId(),
				ObjectActionTriggerConstants.KEY_STANDALONE);
		Map<ObjectRelationship, ObjectDefinition> relatedObjectDefinitionsMap =
			_getRelatedObjectDefinitionsMap();

		Paths paths = openAPI.getPaths();

		for (String key : new ArrayList<>(paths.keySet())) {
			if (!key.contains("objectActionName") &&
				!key.contains("objectRelationshipName")) {

				continue;
			}

			if (key.contains("objectActionName")) {
				ListUtil.isNotEmptyForEach(
					objectActions,
					objectAction -> _addObjectActionPathItem(
						key, objectAction, paths));
			}
			else if (key.contains("objectRelationshipName")) {
				for (Map.Entry<ObjectRelationship, ObjectDefinition> entry :
						relatedObjectDefinitionsMap.entrySet()) {

					ObjectRelationship objectRelationship = entry.getKey();

					ObjectDefinition relatedObjectDefinition = entry.getValue();

					String relatedSchemaName = getSchemaName(
						relatedObjectDefinition);

					if (_addRelatedSchemas) {
						_addObjectRelationshipSchema(
							relatedObjectDefinition, openAPI,
							relatedSchemaName);
					}

					if (Objects.equals(
							objectRelationship.getType(),
							ObjectRelationshipConstants.TYPE_MANY_TO_MANY) ||
						(Objects.equals(
							objectRelationship.getType(),
							ObjectRelationshipConstants.TYPE_ONE_TO_MANY) &&
						 (objectRelationship.getObjectDefinitionId1() ==
							 _objectDefinition.getObjectDefinitionId()))) {

						_addObjectRelationshipPathItem(
							key, objectRelationship, paths, relatedSchemaName);
					}

					if (_addRelatedSchemas && (relatedSchemaName != null)) {
						_setSchemaDescription(
							objectRelationship, openAPI, relatedSchemaName);

						openAPI.getComponents(
						).getSchemas(
						).get(
							_objectDefinition.getShortName()
						).getProperties(
						).put(
							objectRelationship.getName(),
							_getSchema(objectRelationship, relatedSchemaName)
						);
					}
					else {
						openAPI.getComponents(
						).getSchemas(
						).get(
							_objectDefinition.getShortName()
						).getProperties(
						).put(
							objectRelationship.getName(),
							_getSchema(objectRelationship, null)
						);
					}
				}
			}

			paths.remove(key);
		}

		if (!_objectDefinition.isEnableCategorization()) {
			Components components = openAPI.getComponents();

			Map<String, Schema> schemas = components.getSchemas();

			Schema objectDefinitionSchema = schemas.get(
				_objectDefinition.getShortName());

			Map<String, Schema> properties =
				objectDefinitionSchema.getProperties();

			properties.remove("keywords");
			properties.remove("taxonomyCategoryBriefs");
			properties.remove("taxonomyCategoryIds");

			schemas.remove("TaxonomyCategoryBrief");
		}

		if ((openAPIContext != null) &&
			FeatureFlagManagerUtil.isEnabled("LPS-180090")) {

			MapSchema collectionActionsMapSchema = _getActionsMapSchema(
				openAPI, "Page" + _objectDefinition.getShortName());

			collectionActionsMapSchema.setAdditionalProperties(null);
			collectionActionsMapSchema.setProperties(
				_getCollectionActionSchemas(
					openAPIContext, openAPI.getPaths()));

			MapSchema individualActionsMapSchema = _getActionsMapSchema(
				openAPI, _objectDefinition.getShortName());

			individualActionsMapSchema.setAdditionalProperties(null);
			individualActionsMapSchema.setProperties(
				_getIndividualActionSchemas(
					openAPIContext, openAPI.getPaths()));

			MapSchema permissionActionsMapSchema = _getActionsMapSchema(
				openAPI, "PagePermission");

			permissionActionsMapSchema.setAdditionalProperties(null);
			permissionActionsMapSchema.setProperties(
				_getIndividualActionSchemas(
					openAPIContext, openAPI.getPaths()));
		}
	}

	private void _addObjectActionPathItem(
		String key, ObjectAction objectAction, Paths paths) {

		paths.addPathItem(
			StringUtil.replace(
				key, new String[] {"objectEntry", "{objectActionName}"},
				new String[] {
					StringUtil.lowerCaseFirstLetter(
						_objectDefinition.getShortName()),
					objectAction.getName()
				}),
			_createObjectActionPathItem(objectAction, paths.get(key)));
	}

	private void _addObjectRelationshipPathItem(
		String key, ObjectRelationship objectRelationship, Paths paths,
		String schemaName) {

		paths.addPathItem(
			StringUtil.replace(
				key,
				new String[] {
					"currentObjectEntry", "{objectRelationshipName}",
					"relatedObjectEntry"
				},
				new String[] {
					StringUtil.lowerCaseFirstLetter(
						_objectDefinition.getShortName()),
					objectRelationship.getName(),
					StringUtil.lowerCaseFirstLetter(schemaName)
				}),
			_createObjectRelationshipPathItem(
				paths.get(key), objectRelationship, schemaName));
	}

	private void _addObjectRelationshipSchema(
			ObjectDefinition objectDefinition, OpenAPI openAPI,
			String schemaName)
		throws Exception {

		Components components = openAPI.getComponents();

		Map<String, Schema> schemas = components.getSchemas();

		if (schemas.containsKey(schemaName)) {
			return;
		}

		Map<String, Schema> sourceSchemas = null;

		if (objectDefinition.isUnmodifiableSystemObject()) {
			sourceSchemas = OpenAPIContributorUtil.getSystemObjectSchemas(
				_bundleContext, getExternalDTOClassName(objectDefinition),
				_openAPIResource);
		}
		else {
			ObjectEntryOpenAPIResource objectEntryOpenAPIResource =
				_objectEntryOpenAPIResourceProvider.
					getObjectEntryOpenAPIResource(objectDefinition);

			if (objectEntryOpenAPIResource != null) {
				sourceSchemas = objectEntryOpenAPIResource.getSchemas();
			}
			else {
				sourceSchemas = new HashMap<>();
			}
		}

		OpenAPIContributorUtil.copySchemas(
			schemaName, sourceSchemas,
			objectDefinition.isUnmodifiableSystemObject(), openAPI);
	}

	private String _buildActionsURL(
		OpenAPIContext openAPIContext, String pathName) {

		return openAPIContext.getBaseURL() +
			StringUtil.removeFirst(pathName, StringPool.SLASH);
	}

	private Schema _createActionSchema(
		OpenAPIContext openAPIContext,
		Map.Entry<PathItem.HttpMethod, Operation> operation, String pathName) {

		Schema actionSchema = new Schema();

		actionSchema.setProperties(
			HashMapBuilder.put(
				"href",
				() -> {
					StringSchema stringSchema = new StringSchema();

					stringSchema.setDefault(
						_buildActionsURL(openAPIContext, pathName));

					return stringSchema;
				}
			).put(
				"method",
				() -> {
					StringSchema stringSchema = new StringSchema();

					stringSchema.setDefault(operation.getKey());

					return stringSchema;
				}
			).build());

		return actionSchema;
	}

	private PathItem _createObjectActionPathItem(
		ObjectAction objectAction, PathItem pathItem) {

		Map<PathItem.HttpMethod, Operation> operations =
			pathItem.readOperationsMap();

		Operation operation = operations.get(PathItem.HttpMethod.PUT);

		if (operation == null) {
			return new PathItem();
		}

		return new PathItem() {
			{
				put(
					new Operation() {
						{
							operationId(
								StringBundler.concat(
									"put", _objectDefinition.getShortName(),
									StringUtil.upperCaseFirstLetter(
										objectAction.getName())));
							parameters(_getParameters(operation, null));
							responses(operation.getResponses());
							tags(operation.getTags());
						}
					});
			}
		};
	}

	private PathItem _createObjectRelationshipPathItem(
		PathItem existingPathItem, ObjectRelationship objectRelationship,
		String schemaName) {

		PathItem pathItem = new PathItem();

		Map<PathItem.HttpMethod, Operation> operations =
			existingPathItem.readOperationsMap();

		if (operations.containsKey(PathItem.HttpMethod.DELETE)) {
			pathItem.delete(
				_getObjectRelationshipDeleteOperation(
					objectRelationship, existingPathItem.getDelete(),
					schemaName));
		}

		if (operations.containsKey(PathItem.HttpMethod.GET)) {
			pathItem.get(
				_getObjectRelationshipGetOperation(
					objectRelationship, existingPathItem.getGet(), schemaName));
		}

		if (operations.containsKey(PathItem.HttpMethod.PUT)) {
			pathItem.put(
				_getObjectRelationshipPutOperation(
					objectRelationship, existingPathItem.getPut(), schemaName));
		}

		return pathItem;
	}

	private MapSchema _getActionsMapSchema(OpenAPI openAPI, String schemaName) {
		Components components = openAPI.getComponents();

		Map<String, Schema> schemas1 = components.getSchemas();

		Schema schema = schemas1.get(schemaName);

		Map<String, Schema> schemas2 = schema.getProperties();

		return (MapSchema)schemas2.get("actions");
	}

	private Map<String, Schema> _getCollectionActionSchemas(
		OpenAPIContext openAPIContext, Paths paths) {

		Map<String, Schema> collectionActionSchemas = new HashMap<>();

		for (Map.Entry<String, PathItem> key : paths.entrySet()) {
			String pathName = key.getKey();

			PathItem pathItem = key.getValue();

			Map<PathItem.HttpMethod, Operation> operations =
				pathItem.readOperationsMap();

			if (StringUtil.equals(_objectDefinition.getScope(), "site")) {
				if (pathName.equals("/scopes/{scopeKey}")) {
					_setCollectionActionSchemas(
						collectionActionSchemas, openAPIContext, operations,
						pathName);
				}
			}
			else {
				if (pathName.equals(StringPool.SLASH)) {
					_setCollectionActionSchemas(
						collectionActionSchemas, openAPIContext, operations,
						pathName);
				}
				else if (pathName.equals("/batch")) {
					_setCollectionActionSchemas(
						collectionActionSchemas, openAPIContext, operations,
						pathName);
				}
			}
		}

		return collectionActionSchemas;
	}

	private Content _getContent(Content originalContent, String schemaName) {
		Content content = new Content();

		Schema schema = null;

		if (schemaName != null) {
			schema = new Schema();

			schema.set$ref(schemaName);
		}

		for (String key : originalContent.keySet()) {
			Schema finalSchema = schema;

			content.addMediaType(
				key,
				new MediaType() {
					{
						setSchema(finalSchema);
					}
				});
		}

		return content;
	}

	private String _getDescription(ObjectRelationship objectRelationship) {
		return StringBundler.concat(
			"Information about the relationship ", objectRelationship.getName(),
			" can be embedded with \"nestedFields\".");
	}

	private Map<String, Schema> _getIndividualActionSchemas(
		OpenAPIContext openAPIContext, Paths paths) {

		Map<String, Schema> individualActionSchemas = new HashMap<>();

		String objectEntryIdPathName = StringBundler.concat(
			StringPool.SLASH, StringPool.OPEN_CURLY_BRACE,
			StringUtil.lowerCaseFirstLetter(_objectDefinition.getShortName()),
			"Id}");

		for (Map.Entry<String, PathItem> key : paths.entrySet()) {
			String pathName = key.getKey();

			PathItem pathItem = key.getValue();

			Map<PathItem.HttpMethod, Operation> operations =
				pathItem.readOperationsMap();

			if (pathName.equals(objectEntryIdPathName)) {
				_setIndividualActionSchemas(
					individualActionSchemas, openAPIContext, operations,
					pathName);
			}
			else if (pathName.equals(objectEntryIdPathName + "/permissions")) {
				_setIndividualActionSchemas(
					individualActionSchemas, openAPIContext, operations,
					pathName);
			}
			else if (pathName.contains("by-external-reference-code") &&
					 pathName.contains("object-actions")) {

				_setIndividualActionSchemas(
					individualActionSchemas, openAPIContext, operations,
					pathName);
			}
		}

		return individualActionSchemas;
	}

	private ApiResponses _getObjectRelationshipApiResponses(
		Operation operation, String schemaName) {

		ApiResponses apiResponses = new ApiResponses();

		ApiResponses operationApiResponses = operation.getResponses();

		for (Map.Entry<String, ApiResponse> entry :
				operationApiResponses.entrySet()) {

			ApiResponse apiResponse = entry.getValue();

			apiResponses.put(
				entry.getKey(),
				new ApiResponse() {
					{
						setContent(
							_getContent(apiResponse.getContent(), schemaName));
						setDescription(apiResponse.getDescription());
					}
				});
		}

		return apiResponses;
	}

	private Operation _getObjectRelationshipDeleteOperation(
		ObjectRelationship objectRelationship, Operation operation,
		String schemaName) {

		return new Operation() {
			{
				operationId(
					StringBundler.concat(
						"delete", _objectDefinition.getShortName(),
						StringUtil.upperCaseFirstLetter(
							objectRelationship.getName()),
						schemaName));
				parameters(_getParameters(operation, schemaName));
				responses(_getObjectRelationshipApiResponses(operation, null));
				tags(operation.getTags());
			}
		};
	}

	private Operation _getObjectRelationshipGetOperation(
		ObjectRelationship objectRelationship, Operation operation,
		String schemaName) {

		return new Operation() {
			{
				operationId(
					StringBundler.concat(
						"get", _objectDefinition.getShortName(),
						StringUtil.upperCaseFirstLetter(
							objectRelationship.getName()),
						schemaName, "Page"));
				parameters(_getParameters(operation, schemaName));
				responses(
					_getObjectRelationshipApiResponses(
						operation,
						OpenAPIContributorUtil.getPageSchemaName(schemaName)));
				tags(operation.getTags());
			}
		};
	}

	private Operation _getObjectRelationshipPutOperation(
		ObjectRelationship objectRelationship, Operation operation,
		String schemaName) {

		return new Operation() {
			{
				operationId(
					StringBundler.concat(
						"put", _objectDefinition.getShortName(),
						StringUtil.upperCaseFirstLetter(
							objectRelationship.getName()),
						schemaName));
				parameters(_getParameters(operation, schemaName));
				responses(
					_getObjectRelationshipApiResponses(operation, schemaName));
				tags(operation.getTags());
			}
		};
	}

	private List<Parameter> _getParameters(
		Operation operation, String schemaName) {

		List<Parameter> parameters = new ArrayList<>();

		for (Parameter parameter : operation.getParameters()) {
			String parameterName = parameter.getName();

			if (Objects.equals(parameterName, "objectActionName") ||
				Objects.equals(parameterName, "objectRelationshipName")) {

				continue;
			}

			if (Objects.equals(parameterName, "currentObjectEntryId")) {
				parameterName = StringUtil.replace(
					parameterName, "currentObjectEntry",
					StringUtil.lowerCaseFirstLetter(
						_objectDefinition.getShortName()));
			}
			else if (Objects.equals(parameterName, "relatedObjectEntryId")) {
				parameterName = StringUtil.replace(
					parameterName, "relatedObjectEntry",
					StringUtil.lowerCaseFirstLetter(schemaName));
			}

			String finalParameterName = parameterName;

			parameters.add(
				new Parameter() {
					{
						in(parameter.getIn());
						name(finalParameterName);
						required(parameter.getRequired());
						schema(parameter.getSchema());
					}
				});
		}

		return parameters;
	}

	private ObjectDefinition _getRelatedObjectDefinition(
		ObjectRelationship objectRelationship) {

		if (_objectDefinition.getObjectDefinitionId() ==
				objectRelationship.getObjectDefinitionId2()) {

			return _objectDefinitionLocalService.fetchObjectDefinition(
				objectRelationship.getObjectDefinitionId1());
		}

		return _objectDefinitionLocalService.fetchObjectDefinition(
			objectRelationship.getObjectDefinitionId2());
	}

	private Map<ObjectRelationship, ObjectDefinition>
			_getRelatedObjectDefinitionsMap()
		throws Exception {

		Map<ObjectRelationship, ObjectDefinition> relatedObjectDefinitionsMap =
			new HashMap<>();

		List<ObjectRelationship> objectRelationships =
			_objectRelationshipLocalService.getAllObjectRelationships(
				_objectDefinition.getObjectDefinitionId());

		for (ObjectRelationship objectRelationship : objectRelationships) {
			relatedObjectDefinitionsMap.put(
				objectRelationship,
				_getRelatedObjectDefinition(objectRelationship));
		}

		return relatedObjectDefinitionsMap;
	}

	private Schema _getSchema(
		ObjectRelationship objectRelationship, String schemaName) {

		ObjectSchema objectSchema = new ObjectSchema();

		objectSchema.set$ref(schemaName);

		if (Objects.equals(
				objectRelationship.getType(),
				ObjectRelationshipConstants.TYPE_MANY_TO_MANY) ||
			(Objects.equals(
				objectRelationship.getType(),
				ObjectRelationshipConstants.TYPE_ONE_TO_MANY) &&
			 (objectRelationship.getObjectDefinitionId1() ==
				 _objectDefinition.getObjectDefinitionId()))) {

			return new ArraySchema() {
				{
					setDescription(_getDescription(objectRelationship));
					setItems(objectSchema);
				}
			};
		}

		objectSchema.setDescription(_getDescription(objectRelationship));

		return objectSchema;
	}

	private void _setCollectionActionSchemas(
		Map<String, Schema> actionSchemas, OpenAPIContext openAPIContext,
		Map<PathItem.HttpMethod, Operation> operations, String pathName) {

		for (Map.Entry<PathItem.HttpMethod, Operation> operation :
				operations.entrySet()) {

			PathItem.HttpMethod pathItemHttpMethod = operation.getKey();

			Schema actionSchema = _createActionSchema(
				openAPIContext, operation, pathName);

			if (Objects.equals(
					pathItemHttpMethod, PathItem.HttpMethod.DELETE) &&
				pathName.contains("/batch")) {

				actionSchemas.put("deleteBatch", actionSchema);
			}
			else if (Objects.equals(
						pathItemHttpMethod, PathItem.HttpMethod.POST)) {

				if (pathName.contains("/batch")) {
					actionSchemas.put("createBatch", actionSchema);
				}
				else if (pathName.contains("scopeKey")) {
					actionSchemas.put("create", actionSchema);
				}
				else if (pathName.equals("/")) {
					actionSchemas.put("create", actionSchema);
				}
			}
			else if (Objects.equals(
						pathItemHttpMethod, PathItem.HttpMethod.PUT) &&
					 pathName.contains("/batch")) {

				actionSchemas.put("updateBatch", actionSchema);
			}
		}
	}

	private void _setIndividualActionSchemas(
		Map<String, Schema> actionSchemas, OpenAPIContext openAPIContext,
		Map<PathItem.HttpMethod, Operation> operations, String pathName) {

		for (Map.Entry<PathItem.HttpMethod, Operation> operation :
				operations.entrySet()) {

			PathItem.HttpMethod pathItemHttpMethod = operation.getKey();

			Schema actionSchema = _createActionSchema(
				openAPIContext, operation, pathName);

			if (Objects.equals(pathItemHttpMethod, PathItem.HttpMethod.GET)) {
				if (pathName.contains("/permissions")) {
					actionSchemas.put("permissions", actionSchema);
				}
				else {
					actionSchemas.put(
						StringUtil.toLowerCase(pathItemHttpMethod.name()),
						actionSchema);
				}
			}
			else if (Objects.equals(
						pathItemHttpMethod, PathItem.HttpMethod.PATCH)) {

				actionSchemas.put("update", actionSchema);
			}
			else if (Objects.equals(
						pathItemHttpMethod, PathItem.HttpMethod.PUT) &&
					 !pathName.contains("/permissions")) {

				if (pathName.contains("object-actions")) {
					actionSchemas.put(
						StringUtil.extractLast(pathName, StringPool.SLASH),
						actionSchema);
				}
				else {
					actionSchemas.put("replace", actionSchema);
				}
			}
			else if (!pathName.contains("/permissions")) {
				actionSchemas.put(
					StringUtil.toLowerCase(pathItemHttpMethod.name()),
					actionSchema);
			}
		}
	}

	private void _setSchemaDescription(
		ObjectRelationship objectRelationship, OpenAPI openAPI,
		String relatedSchemaName) {

		if (Objects.equals(
				objectRelationship.getType(),
				ObjectRelationshipConstants.TYPE_ONE_TO_MANY) &&
			(objectRelationship.getObjectDefinitionId2() ==
				_objectDefinition.getObjectDefinitionId())) {

			Components components = openAPI.getComponents();

			Map<String, Schema> schemas = components.getSchemas();

			Schema schema = schemas.get(relatedSchemaName);

			schema.setDescription(_getDescription(objectRelationship));
		}
	}

	private final boolean _addRelatedSchemas;
	private final BundleContext _bundleContext;
	private final ObjectActionLocalService _objectActionLocalService;
	private final ObjectDefinition _objectDefinition;
	private final ObjectDefinitionLocalService _objectDefinitionLocalService;
	private final ObjectEntryOpenAPIResource _objectEntryOpenAPIResource;
	private final ObjectEntryOpenAPIResourceProvider
		_objectEntryOpenAPIResourceProvider;
	private final ObjectRelationshipLocalService
		_objectRelationshipLocalService;
	private final OpenAPIResource _openAPIResource;

}