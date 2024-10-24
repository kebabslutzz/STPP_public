import React, { useEffect } from 'react';
import User from '../../interfaces/User';
import useQuery from '../../hooks/useQuery';
import { ENDPOINTS } from '../../constants/endpoints';
import { HTTP_METHODS } from '../../constants/httpsMethods';

interface UserProfileProps {
	userId: number;
}

const UserProfile: React.FC<UserProfileProps> = ({ userId }) => {
	const [user, setUser] = React.useState<User>({});

	const {
		data: userData,
		isLoading: isUserLoading,
		errors: userErrors,
		getData: getUserData,
	} = useQuery<User>({
		url: ENDPOINTS.USERS.GET_USER_BY_ID(userId),
		httpMethod: HTTP_METHODS.GET,
	});

	useEffect(() => {
		if (!userData) {
			getUserData();
		}
	}, [getUserData, userData]);

	useEffect(() => {
		if (userData) {
			setUser({
				...userData,
			});
		}
	}, [userData]);

	return (
		<div className='user-info'>
			<h2>User Information</h2>
			<p>
				<strong>Username:</strong> {user.username || 'N/A'}
			</p>
			<p>
				<strong>Email:</strong> {user.email || 'N/A'}
			</p>
			<p>
				<strong>Date Created:</strong> {user.dateCreated ? new Date(user.dateCreated).toLocaleDateString() : 'N/A'}
			</p>
		</div>
	);
};

export default UserProfile;
