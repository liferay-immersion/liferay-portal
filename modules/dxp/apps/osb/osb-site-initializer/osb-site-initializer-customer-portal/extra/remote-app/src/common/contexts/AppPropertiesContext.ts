/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import {ApolloClient} from '@apollo/client';
import {createContext, useContext} from 'react';

export const AppPropertiesContext = createContext({
	articleAccountSupportURL: '',
	articleDeployingActivationKeysURL: '',
	articleGettingStartedWithLiferayEnterpriseSearchURL: '',
	articleWhatIsMyInstanceSizingValueURL: '',
	client: null,
	featureFlags: [],
	gravatarAPI: '',
	importDate: null,
	oktaSessionAPI: '',
	provisioningServerAPI: '',
	submitSupportTicketURL: '',
});

export function useAppPropertiesContext() {
	const context = useContext(AppPropertiesContext);

	type ContextType = Omit<typeof context, 'client'> & {
		client: ApolloClient<any>;
	};

	return (context as unknown) as ContextType;
}
