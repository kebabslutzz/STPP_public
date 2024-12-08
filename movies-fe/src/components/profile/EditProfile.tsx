// EditProfile.tsx
import React, { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { Container } from '@mui/material';
import useQuery from '../../hooks/useQuery';
import { ENDPOINTS } from '../../constants/endpoints';
import { HTTP_METHODS } from '../../constants/httpsMethods';
import User from '../../interfaces/User';
import UserForm from '../register-login/UserForm';
import { useAuth } from '../../context/AuthContext';
import Loader from '../shared/Loader';

interface EditProfileProps {
	userId: number;
	onClose: () => void;
}

const EditProfilePage: React.FC<EditProfileProps> = ({ userId, onClose }) => {
	const { loggedInUserRole } = useAuth();
	const navigate = useNavigate();

	const {
		data: userData,
		isLoading,
		errors,
		getData: getUserData,
	} = useQuery<User>({
		url: ENDPOINTS.USERS.GET_USER_BY_ID(userId),
		httpMethod: HTTP_METHODS.GET,
	});

	const { sendData: updateUserCommand } = useQuery({
		url: ENDPOINTS.USERS.UPDATE_USER(userId),
		httpMethod: HTTP_METHODS.PUT,
	});

	useEffect(() => {
		getUserData();
	}, [userId]);

	const handleSubmit = async (updatedUserData: User, setFieldError: (field: string, message: string) => void) => {
		try {
			const response = await updateUserCommand(updatedUserData);

			if (response && 'message' in response && response.status === 409) {
				switch (response.message) {
					case 'The username is already taken':
						setFieldError('username', response.message);
						break;
					case 'User with the email already exists':
						setFieldError('email', response.message);
						break;
					default:
						break;
				}
			} else {
				onClose();
			}
		} catch (error) {
			console.error('Error updating user:', error);
		}
	};

	if (isLoading) {
		return <Loader />;
	}

	if (errors || !userData) {
		return <Container>Error loading user data</Container>;
	}

	return (
		<Container className='PageContainer' maxWidth={false}>
			<UserForm
				initialValues={{
					username: userData.username,
					email: userData.email,
					// role: userData.role,
				}}
				onSubmit={handleSubmit}
				submitButtonText='Update Profile'
				excludePassword={true}
			/>
		</Container>
	);
};

export default EditProfilePage;
