/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.antivirus.async.store.retry;

import com.liferay.portal.kernel.messaging.Message;

/**
 * @author Raymond Augé
 */
public interface AntivirusAsyncRetryScheduler {

	public void schedule(Message message);

	public void unschedule(Message message);

}