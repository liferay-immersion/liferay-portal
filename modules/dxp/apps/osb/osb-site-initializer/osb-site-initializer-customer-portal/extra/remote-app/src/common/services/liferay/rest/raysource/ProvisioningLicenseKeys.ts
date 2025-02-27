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

import SearchBuilder from '~/common/core/SearchBuilder';
import {baseFetcher} from '../../fetcher';

type ProvisioningLicenseKeysConstructor = {
	provisioningServerAPI: string;
	sessionId: string;
};

class ProvisioningLicenseKeys {
	private fetcher: ReturnType<typeof baseFetcher>;

	constructor(props: ProvisioningLicenseKeysConstructor) {
		this.fetcher = baseFetcher(props.provisioningServerAPI, {
			headers: {
				'Okta-Session-ID': props.sessionId,
			},
		});
	}

	public getCommonLicenseKey(
		accountKey: string,
		productName: string,
		environment: string,
		dateEnd: string,
		dateStart: string
	) {
		return this.fetcher(
			`/accounts/${accountKey}/product-groups/${productName}/product-environment/${environment}/common-license-key?dateEnd=${dateEnd}&dateStart=${dateStart}`
		);
	}

	public async getDevelopmentLicenseKey(
		accountKey: string,
		selectedVersion: string,
		productName: string
	) {
		return this.fetcher(
			`/accounts/${accountKey}/product-groups/${productName}/product-version/${selectedVersion}/development-license-key`
		);
	}

	public async getActivationDownloadKey(licenseKey: string) {
		return this.fetcher(`/license-keys/${licenseKey}/download`);
	}

	public async getAggregatedActivationDownloadKey(selectedKeysIDs: string) {
		return this.fetcher(`/license-keys/download?${selectedKeysIDs}`);
	}

	public async getMultipleActivationDownloadKey(selectedKeysIDs: string) {
		return this.fetcher(`/license-keys/download-zip?${selectedKeysIDs}`);
	}

	public async getExportedLicenseKeys(
		accountKey: string,
		productName: string
	) {
		const filter = new SearchBuilder()
			.eq('active', true)
			.and()
			.startsWith('productName', productName)
			.build();

		return this.fetcher(
			`/accounts/${accountKey}/license-keys/export?filter=${filter})`
		);
	}

	public async associateContactRoleNameByEmailByProject({
		accountKey,
		emailURI,
		firstName,
		lastName,
		roleName,
	}: {
		accountKey: string;
		emailURI: string;
		firstName: string;
		lastName: string;
		roleName: string;
	}) {
		return this.fetcher(
			`/accounts/${accountKey}/contacts/by-email-address/${emailURI}/roles?contactRoleNames=${roleName}&firstName=${firstName}&lastName=${lastName}`,
			{
				method: 'PUT',
			}
		);
	}

	public async deleteContactRoleNameByEmailByProject(
		accountKey: string,
		emailURI: string,
		rolesToDelete: string
	) {
		return this.fetcher(
			`/accounts/${accountKey}/contacts/by-email-address/${emailURI}/roles?${rolesToDelete}`,
			{
				method: 'DELETE',
			}
		);
	}

	public async putDeactivateKeys(licenseKeyIds: string) {
		return this.fetcher(`/license-keys/deactivate?${licenseKeyIds}`, {
			method: 'PUT',
		});
	}

	public async getNewGenerateKeyFormValues(
		accountKey: string,
		productGroupName: string
	) {
		return this.fetcher(
			`/accounts/${accountKey}/product-groups/${productGroupName}/generate-form`
		);
	}

	public async createNewGenerateKey(accountKey: string, licenseKey: string) {
		return this.fetcher(`/accounts/${accountKey}/license-keys`, {
			body: JSON.stringify([licenseKey]),
			method: 'POST',
		});
	}

	public async putSubscriptionInKey(licenseKeyIds: string) {
		return this.fetcher(
			`/license-keys/subscriptions?licenseKeyIds=${licenseKeyIds}`,
			{
				method: 'PUT',
			}
		);
	}

	public async deleteSubscriptionInKey(licenseKeyIds: string) {
		return this.fetcher(
			`/license-keys/subscriptions?licenseKeyIds=${licenseKeyIds}`,
			{
				method: 'DELETE',
			}
		);
	}

	public async getSubscriptionInKey(licenseKeyIds: string) {
		return this.fetcher(
			`/license-keys/subscriptions?licenseKeyId=${licenseKeyIds}`
		);
	}
}

export default ProvisioningLicenseKeys;
