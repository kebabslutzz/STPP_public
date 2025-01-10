import React, { useEffect, useState } from 'react';
import User from '../../interfaces/User';
import useQuery from '../../hooks/useQuery';
import { ENDPOINTS } from '../../constants/endpoints';
import { HTTP_METHODS } from '../../constants/httpsMethods';
import { Button, Container } from '@mui/material';
import './UsersList.css';
import UsersTable from './UsersTable';
import UserFormDialogBox from './UserFormDialogBox';
import DeleteConfirmationDialog from '../dialog/DeleteConfirmationDialog';
import Loader from '../shared/Loader';
import PersonAddIcon from '@mui/icons-material/PersonAdd';
import ErrorDisplay from '../shared/ErrorDisplay';
import { useAuth } from '../../context/AuthContext';

interface UserRoleEditRequestDTO {
	role: string;
}

const UsersList: React.FC = () => {
	const [users, setUsers] = useState<User[]>([]);
	const [userToUpdate, setUserToUpdate] = useState<User | null>(null);
	const [isUserSubmitFormOpen, setUserSubmitFormOpen] = useState(false);
	const [userToDeleteId, setUserToDeleteId] = useState<number | null>(null);
	const [deleteUserFormOpen, setDeleteUserFormOpen] = useState(false);
	const { isAdmin } = useAuth();
	// const [updateRoleQuery, setUpdateRoleQuery] = useState<ReturnType<typeof useQuery<User>> | null>(null);

	const {
		data: fetchedUsers,
		isLoading: isUsersLoading,
		errors: usersErrors,
		getData: getUsersData,
	} = useQuery<User[]>({
		url: ENDPOINTS.USERS.GET_ALL_USERS,
		httpMethod: HTTP_METHODS.GET,
	});

	useEffect(() => {
		if (!fetchedUsers) {
			getUsersData();
		}
	}, []);

	useEffect(() => {
		if (fetchedUsers) {
			setUsers(fetchedUsers);
		}
	}, [fetchedUsers]);

	const handleDeleteDialogFormOpen = (id: number) => {
		setDeleteUserFormOpen(true);
		setUserToDeleteId(id);
		// console.log('Delete user with id: ', id);
	};

	const handleDeleteDialogFormClose = () => {
		setDeleteUserFormOpen(false);
	};

	const deleteUserCommand = useQuery<User>({
		url: ENDPOINTS.USERS.DELETE_USER(userToDeleteId!),
		httpMethod: HTTP_METHODS.DELETE,
	});

	const handleDeleteUser = async () => {
		const deleteUserResponse = await deleteUserCommand.sendData();
		if (deleteUserResponse?.status === 204) {
			setUsers((prevUsers) => prevUsers.filter((u) => u.id !== userToDeleteId));
		}
		handleDeleteDialogFormClose();
	};

	const handleEditUser = (user: User) => {
		setUserToUpdate(user);
		setUserSubmitFormOpen(true);
	};

	const handleUserSubmitFormClose = () => {
		setUserSubmitFormOpen(false);
		setUserToUpdate(null);
	};

	const handleUserCreateClick = () => {
		setUserSubmitFormOpen(true);
	};

	// ... other state
	const userIdRef = React.useRef<number | null>(null);

	useEffect(() => {
		if (userToUpdate?.id) {
			userIdRef.current = userToUpdate.id;
		}
	}, [userToUpdate]);

	const updateRoleCommand = useQuery<UserRoleEditRequestDTO>({
		url: userIdRef.current ? ENDPOINTS.USERS.UPDATE_USER_ROLE(userIdRef.current) : ENDPOINTS.USERS.GET_ALL_USERS,
		httpMethod: HTTP_METHODS.PUT,
	});

	const handleUserSubmission = async (role: string) => {
		if (!userToUpdate?.id) {
			console.error('User ID is missing');
			return;
		}

		const roleUpdateDto: UserRoleEditRequestDTO = {
			role: role,
		};

		const response = await updateRoleCommand.sendData(roleUpdateDto);

		if (response && 'data' in response) {
			setUsers((prevUsers) => prevUsers.map((u) => (u.id === userToUpdate.id ? { ...u, role } : u)));
			handleUserSubmitFormClose();
		} else {
			console.error('Failed to update user role:', response?.message);
		}
	};

	return (
		<Container className='PageContainer' maxWidth={false}>
			<div className='Header-row'>
				<h1>User List</h1>
			</div>
			{isUsersLoading && <Loader errors={usersErrors} textNeeded />}
			{usersErrors && !isUsersLoading && <ErrorDisplay errors={usersErrors} />}
			{fetchedUsers && <UsersTable users={users} onDelete={handleDeleteDialogFormOpen} onEdit={handleEditUser} />}

			<UserFormDialogBox
				open={isUserSubmitFormOpen}
				onClose={handleUserSubmitFormClose}
				onSubmit={handleUserSubmission}
				currentRole={userToUpdate?.role}
			/>
			<DeleteConfirmationDialog
				open={deleteUserFormOpen}
				onClose={handleDeleteDialogFormClose}
				onConfirm={handleDeleteUser}
				text='Are you sure you want to delete this user?'
			/>
		</Container>
	);
};

export default UsersList;
