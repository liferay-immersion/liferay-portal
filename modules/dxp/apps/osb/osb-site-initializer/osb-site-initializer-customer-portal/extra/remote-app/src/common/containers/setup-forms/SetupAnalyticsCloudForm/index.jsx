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
import {useQuery} from '@apollo/client';
import ClayForm from '@clayui/form';
import {FieldArray, Formik} from 'formik';
import {useEffect, useMemo, useState} from 'react';
import {useAppPropertiesContext} from '~/common/contexts/AppPropertiesContext';
import {
	addAnalyticsCloudWorkspace,
	addIncidentReportAnalyticsCloud,
	getAnalyticsCloudPageInfo,
	getAnalyticsCloudWorkspace,
	updateAccountSubscriptionGroups,
} from '../../../../common/services/liferay/graphql/queries';
import {
	isLowercaseAndNumbers,
	isValidEmail,
	isValidEmailDomain,
	isValidFriendlyURL,
	maxLength,
} from '../../../../common/utils/validations.form';
import {STATUS_TAG_TYPE_NAMES} from '../../../../routes/customer-portal/utils/constants';
import i18n from '../../../I18n';
import {Button, Input, Select} from '../../../components';
import useBannedDomains from '../../../hooks/useBannedDomains';
import NotificationQueueService from '../../../services/actions/notificationAction';
import getInitialAnalyticsInvite from '../../../utils/getInitialAnalyticsInvite';
import getKebabCase from '../../../utils/getKebabCase';
import Layout from '../Layout';
import IncidentReportInput from './IncidentReportInput';

const BLANK_TEXT = '< none >';
const INITIAL_SETUP_ADMIN_COUNT = 1;
const FETCH_DELAY_AFTER_TYPING = 500;
const MAX_LENGTH = 255;

const SetupAnalyticsCloudPage = ({
	client,
	errors,
	handlePage,
	leftButton,
	project,
	setFieldValue,
	setFormAlreadySubmitted,
	subscriptionGroupId,
	touched,
	values,
}) => {
	const [baseButtonDisabled, setBaseButtonDisabled] = useState(true);

	const bannedDomainsOwnerEmail = useBannedDomains(
		values?.activations?.ownerEmailAddress,
		FETCH_DELAY_AFTER_TYPING
	);

	const bannedDomainsAllowedDomains = useBannedDomains(
		values?.activations?.allowedEmailDomains,
		FETCH_DELAY_AFTER_TYPING
	);

	useEffect(() => {}, [values?.activations?.allowedEmailDomains]);

	const {data} = useQuery(getAnalyticsCloudPageInfo, {
		variables: {
			accountSubscriptionsFilter: `(accountKey eq '${project?.accountKey}') and (hasDisasterDataCenterRegion eq true)`,
		},
	});

	const {featureFlags} = useAppPropertiesContext();

	const analyticsDataCenterLocations = useMemo(
		() =>
			data?.c?.analyticsCloudDataCenterLocations?.items.map(({name}) => ({
				label: i18n.translate(getKebabCase(name)),
				value: name,
			})) || [],
		[data]
	);

	const hasDisasterRecovery = !!data?.c?.accountSubscriptions?.items?.length;

	useEffect(() => {
		if (analyticsDataCenterLocations.length) {
			setFieldValue(
				'activations.dataCenterLocation',
				analyticsDataCenterLocations[0].value
			);

			if (hasDisasterRecovery) {
				setFieldValue(
					'activations.disasterDataCenterLocation',
					analyticsDataCenterLocations[0].value
				);
			}
		}
	}, [analyticsDataCenterLocations, hasDisasterRecovery, setFieldValue]);

	useEffect(() => {
		const hasTouched = !Object.keys(touched).length;
		const hasError = Object.keys(errors).length;

		setBaseButtonDisabled(hasTouched || hasError);
	}, [touched, errors]);

	const handleSubmit = async () => {
		const analyticsCloud = values?.activations;

		const getAnalyticsCloudSubmittedStatus = async (accountKey) => {
			const {data} = await client.query({
				query: getAnalyticsCloudWorkspace,
				variables: {
					filter: `accountKey eq '${accountKey}'`,
				},
			});

			if (data) {
				const status = !!data.c?.analyticsCloudWorkspaces?.items
					?.length;

				return status;
			}

			return false;
		};

		const alreadySubmitted = await getAnalyticsCloudSubmittedStatus(
			project.accountKey
		);
		if (alreadySubmitted) {
			setFormAlreadySubmitted(true);
		}

		if (!alreadySubmitted) {
			const {data} = await client.mutate({
				context: {
					displaySuccess: false,
					type: 'liferay-rest',
				},
				mutation: addAnalyticsCloudWorkspace,
				variables: {
					analyticsCloudWorkspace: {
						accountKey: project.accountKey,
						allowedEmailDomains: analyticsCloud.allowedEmailDomains,
						dataCenterLocation: analyticsCloud.dataCenterLocation,
						ownerEmailAddress: analyticsCloud.ownerEmailAddress,
						r_accountEntryToAnalyticsCloudWorkspace_accountEntryId:
							project?.id,
						timeZone: analyticsCloud.timeZone,
						workspaceFriendlyUrl:
							analyticsCloud.workspaceFriendlyUrl,
						workspaceName: analyticsCloud.workspaceName,
					},
				},
			});

			if (data) {
				const analyticsCloudWorkspaceId =
					data?.createAnalyticsCloudWorkspace?.id;

				await client.mutate({
					context: {
						displaySuccess: false,
						type: 'liferay-rest',
					},
					mutation: updateAccountSubscriptionGroups,
					variables: {
						accountSubscriptionGroup: {
							accountKey: project.accountKey,
							activationStatus: STATUS_TAG_TYPE_NAMES.inProgress,
							r_accountEntryToAccountSubscriptionGroup_accountEntryId:
								project?.id,
						},
						id: subscriptionGroupId,
					},
				});

				await Promise.all(
					analyticsCloud?.incidentReportContact?.map(({email}) => {
						return client.mutate({
							context: {
								displaySuccess: false,
								type: 'liferay-rest',
							},
							mutation: addIncidentReportAnalyticsCloud,
							variables: {
								IncidentReportContactAnalyticsCloud: {
									analyticsCloudWorkspaceId,
									emailAddress: email,
									r_accountEntryToIncidentReportContactAC_accountEntryId:
										project.id,
								},
							},
						});
					})
				);

				if (featureFlags.includes('LPS-181031')) {
					const emailIncidentReportContact = analyticsCloud?.incidentReportContact
						?.map(({email}) => email)
						.join(', ');

					const notificationTemplateService = new NotificationQueueService(
						client
					);

					await notificationTemplateService.send(
						'SETUP-ANALYTICS-CLOUD-ENVIRONMENT-NOTIFICATION-TEMPLATE',
						{
							'[%AC_DATA_CENTER_LOCATION]':
								analyticsCloud.dataCenterLocation,
							'[%AC_DATA_TIME]': new Date().toUTCString(),
							'[%AC_EMAIL_DOMAINS]':
								analyticsCloud.allowedEmailDomains ||
								BLANK_TEXT,
							'[%AC_INCIDENT_REPORT_CONTACT]': emailIncidentReportContact,
							'[%AC_OWNER_EMAIL]':
								analyticsCloud.ownerEmailAddress,
							'[%AC_TIME_ZONE]':
								analyticsCloud.timeZone || BLANK_TEXT,
							'[%AC_WORKSPACE_FRIENDLY_URL]':
								analyticsCloud.workspaceFriendlyUrl ||
								BLANK_TEXT,
							'[%AC_WORKSPACE_NAME]':
								analyticsCloud.workspaceName,
							'[%PROJECT_ID]': project?.code,
						}
					);
				}
			}

			handlePage(true);
		}
	};

	return (
		<Layout
			className="pt-1 px-3"
			footerProps={{
				leftButton: (
					<Button
						borderless
						className="text-neutral-10"
						onClick={() => handlePage(false)}
					>
						{leftButton}
					</Button>
				),
				middleButton: (
					<Button
						disabled={baseButtonDisabled}
						displayType="primary"
						onClick={handleSubmit}
					>
						{i18n.translate('submit')}
					</Button>
				),
			}}
			headerProps={{
				helper: i18n.translate(
					'we-ll-need-a-few-details-to-finish-creating-your-analytics-cloud-workspace'
				),
				title: i18n.translate('set-up-analytics-cloud'),
			}}
		>
			<FieldArray
				name="activations.incidentReportContact"
				render={({pop, push}) => (
					<>
						<ClayForm.Group className="pb-1">
							<Input
								groupStyle="pb-1"
								helper={i18n.translate(
									'this-user-will-create-and-manage-the-analytics-cloud-workspace-and-must-have-a-liferay-com-account-the-owner-email-can-be-updated-via-a-support-ticket-if-needed'
								)}
								label={i18n.translate('owner-email')}
								name="activations.ownerEmailAddress"
								placeholder="user@company.com"
								required
								type="email"
								validations={[
									(value) =>
										isValidEmail(
											value,
											bannedDomainsOwnerEmail
										),
								]}
							/>

							<Input
								groupStyle="pb-1"
								helper={i18n.translate(
									'lowercase-letters-and-numbers-only-project-ids-cannot-be-changed'
								)}
								label={i18n.translate('workspace-name')}
								name="activations.workspaceName"
								placeholder="superbank1"
								required
								type="text"
								validations={[
									(value) => maxLength(value, MAX_LENGTH),
									(value) => isLowercaseAndNumbers(value),
								]}
							/>

							<Select
								groupStyle="pb-1"
								helper={i18n.translate(
									'select-a-server-location-for-your-data-to-be-stored'
								)}
								key={analyticsDataCenterLocations}
								label={i18n.translate('data-center-location')}
								name="activations.dataCenterLocation"
								options={analyticsDataCenterLocations}
								required
							/>

							{hasDisasterRecovery && (
								<Select
									groupStyle="mb-0 pt-2"
									label={i18n.translate(
										'Disaster Recovery Data Center Location'
									)}
									name="activations.disasterDataCenterLocation"
									options={analyticsDataCenterLocations}
									required
								/>
							)}

							<Input
								groupStyle="pb-1"
								helper={i18n.translate(
									'please-note-that-the-friendly-url-cannot-be-changed-once-added'
								)}
								label={i18n.translate('workspace-friendly-url')}
								name="activations.workspaceFriendlyUrl"
								placeholder="/myurl"
								type="text"
								validations={[
									(value) => isValidFriendlyURL(value),
								]}
							/>

							<Input
								groupStyle="pb-1"
								helper={i18n.translate(
									'anyone-with-an-email-address-at-the-provided-domains-can-request-access-to-your-workspace-if-multiple-separate-domains-by-commas'
								)}
								label={i18n.translate('allowed-email-domains')}
								name="activations.allowedEmailDomains"
								placeholder="@mycompany.com"
								type="text"
								validations={[
									() =>
										isValidEmailDomain(
											bannedDomainsAllowedDomains
										),
								]}
							/>

							<Input
								groupStyle="pb-1"
								helper={i18n.translate(
									'enter-the-timezone-to-be-used-for-all-data-reporting-in-your-workspace'
								)}
								label={i18n.translate('time-zone')}
								name="activations.timeZone"
								placeholder="UTC-04:00"
								type="text"
							/>

							<ClayForm.Group>
								{values?.activations?.incidentReportContact?.map(
									(activation, index) => (
										<IncidentReportInput
											activation={activation}
											id={index}
											key={index}
										/>
									)
								)}
							</ClayForm.Group>
						</ClayForm.Group>

						{values?.activations?.incidentReportContact.length >
							INITIAL_SETUP_ADMIN_COUNT && (
							<Button
								className="ml-3 my-2 text-brandy-secondary"
								displayType="secondary"
								onClick={() => pop()}
								prependIcon="hr"
								small
							>
								{i18n.translate(
									'remove-incident-report-contact'
								)}
							</Button>
						)}

						<Button
							className="btn-outline-primary ml-3 my-2 rounded-xs"
							onClick={() => {
								push(
									getInitialAnalyticsInvite(
										values?.activations
											?.incidentReportContact
									)
								);
							}}
							prependIcon="plus"
							small
						>
							{i18n.translate('add-incident-report-contact')}
						</Button>
					</>
				)}
			/>
		</Layout>
	);
};

const SetupAnalyticsCloudForm = (props) => {
	return (
		<Formik
			initialValues={{
				activations: {
					allowedEmailDomains: '',
					dataCenterLocation: '',
					disasterDataCenterLocation: '',
					incidentReportContact: [getInitialAnalyticsInvite()],
					ownerEmailAddress: '',
					timeZone: '',
					workspaceName: '',
					workspaceURL: '',
				},
			}}
			validateOnChange
		>
			{(formikProps) => (
				<SetupAnalyticsCloudPage {...props} {...formikProps} />
			)}
		</Formik>
	);
};

export default SetupAnalyticsCloudForm;
