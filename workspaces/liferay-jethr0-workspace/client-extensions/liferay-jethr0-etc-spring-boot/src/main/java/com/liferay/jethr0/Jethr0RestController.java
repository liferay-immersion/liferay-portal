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

package com.liferay.jethr0;

import com.liferay.jethr0.event.handler.EventHandler;
import com.liferay.jethr0.event.handler.EventHandlerFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Michael Hashimoto
 */
@RestController
public class Jethr0RestController {

	@PostMapping(consumes = "application/json", produces = "application/json")
	public ResponseEntity<String> process(@RequestBody String body) {
		if (_log.isDebugEnabled()) {
			_log.debug("Processing " + body);
		}

		JSONObject bodyJSONObject = new JSONObject(body);

		EventHandler.EventType eventType = EventHandler.EventType.valueOf(
			bodyJSONObject.optString("eventTrigger"));

		if ((eventType == EventHandler.EventType.BUILD_COMPLETED) ||
			(eventType == EventHandler.EventType.BUILD_STARTED) ||
			(eventType == EventHandler.EventType.COMPUTER_BUSY) ||
			(eventType == EventHandler.EventType.COMPUTER_IDLE) ||
			(eventType == EventHandler.EventType.COMPUTER_OFFLINE) ||
			(eventType == EventHandler.EventType.COMPUTER_ONLINE) ||
			(eventType ==
				EventHandler.EventType.COMPUTER_TEMPORARILY_OFFLINE) ||
			(eventType == EventHandler.EventType.COMPUTER_TEMPORARILY_ONLINE) ||
			(eventType == EventHandler.EventType.CREATE_BUILD) ||
			(eventType == EventHandler.EventType.CREATE_PROJECT) ||
			(eventType == EventHandler.EventType.QUEUE_PROJECT)) {

			EventHandler eventHandler = _eventHandlerFactory.newEventHandler(
				bodyJSONObject);

			if (eventHandler == null) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}

			try {
				return new ResponseEntity<>(
					eventHandler.process(), HttpStatus.OK);
			}
			catch (Exception exception) {
				if (_log.isWarnEnabled()) {
					_log.warn(exception);
				}

				return new ResponseEntity<>(
					exception.getMessage(), HttpStatus.BAD_REQUEST);
			}
		}

		return new ResponseEntity<>("{}", HttpStatus.OK);
	}

	private static final Log _log = LogFactory.getLog(
		Jethr0RestController.class);

	@Autowired
	private EventHandlerFactory _eventHandlerFactory;

}