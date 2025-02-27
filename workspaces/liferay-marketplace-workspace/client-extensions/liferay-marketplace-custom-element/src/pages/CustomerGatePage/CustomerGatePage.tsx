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

import './CustomerGatePage.scss';

import ClayButton from '@clayui/button';
import ClayLink from '@clayui/link';
import {useEffect, useState} from 'react';

import magnifyingGlass from '../../assets/images/magnifying_glass.svg';
import menu from '../../assets/images/menu.svg';
import {GateCard} from '../../components/Card/GateCard';
import {Footer} from '../../components/Footer/Footer';
import {Header} from '../../components/Header/Header';
import {getMyUserAccount} from '../../utils/api';
import CreateCustomerAccountForm from './CustomerForm';

type Steps = {
	page: 'onboarding' | 'customerGateForm';
};

export function CustomerGatePage() {
	const [step, setStep] = useState<Steps>({page: 'onboarding'});
	const [myUser, setMyUser] = useState<UserAccount>();

	const {origin} = window.location;

	useEffect(() => {
		const getUserInfo = async () => {
			const userData = await getMyUserAccount();
			setMyUser(userData);
		};

		getUserInfo();
	}, []);

	return (
		<>
			{step.page === 'onboarding' && (
				<div className="customer-gate-page-container">
					<div className="customer-gate-page-body">
						<Header
							description="We are happy to have you interested in the Liferay Marketplace. At the moment, we are working on enhancing the experience for our customers in the Marketplace and access is invite only. If you are an existing Liferay customer, please keep an eye out for an announcement related to the new Marketplace in the coming months!"
							title="Becoming a Liferay Marketplace Customer"
						/>

						<GateCard
							description="Explore over 800 apps available in the Liferay Marketplace from a variety of publishers. Apps allow you to accelerate your Liferay development get to market faster. "
							image={{
								description: 'Magnifying Glass',
								svg: magnifyingGlass,
							}}
							title="Discover and customize "
						/>

						<GateCard
							description="Manage all your app purchases and subscriptions in one place, read other users reviews, get notifications when updates are available and get the most out of our Apps catalog."
							image={{
								description: 'Menu ',
								svg: menu,
							}}
							title="Manage All Your Apps in One Place"
						/>

						<hr className="customer-gate-page-divider" />

						<div className="customer-gate-page-button-container">
							<ClayButton
								className="customer-gate-page-button"
								onClick={() => {
									setStep({page: 'customerGateForm'});
								}}
							>
								Get Started
							</ClayButton>

							<div>
								<ClayLink
									className="customer-gate-page-link"
									href={`${origin}/c/portal/login`}
								>
									Learn more about becoming a Liferay
									Customer.
								</ClayLink>
							</div>
						</div>
					</div>
				</div>
			)}

			{step.page === 'customerGateForm' && (
				<CreateCustomerAccountForm setStep={setStep} user={myUser} />
			)}

			<Footer />
		</>
	);
}
