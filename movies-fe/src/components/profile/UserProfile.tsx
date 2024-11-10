import React, { useEffect } from 'react';
import User from '../../interfaces/User';
import useQuery from '../../hooks/useQuery';
import { ENDPOINTS } from '../../constants/endpoints';
import { HTTP_METHODS } from '../../constants/httpsMethods';
import { Button, Container, List, ListItem, ListItemText, Typography } from '@mui/material';
import { useAuth } from '../../context/AuthContext';
import EditIcon from '@mui/icons-material/Edit';
import UserForm from '../register-login/UserForm';

interface UserProfileProps {
	userId: number;
}

const UserProfile: React.FC<UserProfileProps> = ({ userId }) => {
	const [user, setUser] = React.useState<User>({});
	const [openEditDialog, setOpenEditDialog] = React.useState(false);
	const { loggedInUserId, loggedInUserRole } = useAuth();

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
		if (userId && !userData) {
			getUserData();
		}
	}, [getUserData, userData, userId]);

	useEffect(() => {
		if (userData) {
			setUser({
				...userData,
			});
		}
	}, [userData]);

	const handleUserFormDialogBoxOpen = () => {
		setOpenEditDialog(true);
	};

	const { sendData: editUserCommand, errors: editUserErrors } = useQuery({
		url: ENDPOINTS.USERS.UPDATE_USER(userId!),
		httpMethod: HTTP_METHODS.PUT,
	});

	const handleUserEditSubmit = async (updatedUser: User, setFieldError: (field: string, message: string) => void) => {
		try {
			updatedUser.role = loggedInUserRole;
			const response = await editUserCommand(updatedUser);
			// console.log('errors', editUserErrors);
			// console.log('response:', response);
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
				setUser(updatedUser);
				setOpenEditDialog(false);
			}
		} catch (error) {
			console.error('Error creating user:', error);
		}
	};

	if (!userId) {
		return (
			<Container className='PageContainer'>
				<Typography variant='h6'>User not found</Typography>
			</Container>
		);
	}

	return (
		<Container className='PageContainer'>
			<h2>User Information</h2>
			<List>
				<ListItem>
					<ListItemText>
						<strong>Username:</strong> {user.username || 'N/A'}
					</ListItemText>
				</ListItem>
				<ListItem>
					<ListItemText>
						<strong>Email:</strong> {user.email || 'N/A'}
					</ListItemText>
				</ListItem>
				<ListItem>
					<ListItemText>
						<strong>Date Created:</strong> {user.dateCreated ? new Date(user.dateCreated).toLocaleDateString() : 'N/A'}
					</ListItemText>
				</ListItem>
			</List>
			{loggedInUserId === userId && (
				<Button className='Button add-edit-button' endIcon={<EditIcon />} onClick={handleUserFormDialogBoxOpen}>
					Edit Profile
				</Button>
			)}
			{openEditDialog && (
				<UserForm initialValues={user} onSubmit={handleUserEditSubmit} submitButtonText='Update Profile' />
			)}
		</Container>
	);
};

export default UserProfile;
