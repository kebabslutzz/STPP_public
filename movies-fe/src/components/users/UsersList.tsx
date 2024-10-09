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
// import AddIcon from '@mui/icons-material/Add';
import Loader from '../shared/Loader';
import PersonAddIcon from '@mui/icons-material/PersonAdd';

const UsersList: React.FC = () => {
	const [users, setUsers] = useState<User[]>([]);
	const [userToUpdate, setUserToUpdate] = useState<User | null>(null);
	const [isUserSubmitFormOpen, setUserSubmitFormOpen] = useState(false);
	const [userToDeleteId, setUserToDeleteId] = useState<number | null>(null);
	const [deleteUserFormOpen, setDeleteUserFormOpen] = useState(false);

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
		console.log('Delete user with id: ', id);
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

	const updateUserCommand = useQuery<User>({
		url: ENDPOINTS.USERS.UPDATE_USER(userToUpdate?.id!),
		httpMethod: HTTP_METHODS.PUT,
	});

	const createUserCommand = useQuery<User>({
		url: ENDPOINTS.USERS.CREATE_USER,
		httpMethod: HTTP_METHODS.POST,
	});

	const handleUserSubmission = async (user: User) => {
		if (userToUpdate) {
			const newUser: User = {
				...userToUpdate,
				email: user.email,
				role: user.role,
				status: user.status,
				username: user.username,
				password: user.password,
			};
			const updateUserResponse = await updateUserCommand.sendData(newUser);
			if (updateUserResponse?.status === 200) {
				setUsers((prevUsers) => prevUsers.map((u) => (u.id === newUser.id ? newUser : u)));
			}
		} else {
			user.password = 'password'; // TEMPORARY SOLUTION
			const createUserResponse = await createUserCommand.sendData(user);
			if (createUserResponse?.status === 201) {
				setUsers((prevUsers) => [createUserResponse.data, ...prevUsers]);
			}
		}
	};

	if (isUsersLoading) return <Loader />;
	if (usersErrors) return <div>{usersErrors.join(', ')}</div>;

	return (
		<Container className='Container'>
			<>User List</>
			{fetchedUsers && <UsersTable users={users} onDelete={handleDeleteDialogFormOpen} onEdit={handleEditUser} />}
			<Button className='Button add-movie-button' onClick={handleUserCreateClick} endIcon={<PersonAddIcon />}>
				Add user
			</Button>
			<UserFormDialogBox
				open={isUserSubmitFormOpen}
				onClose={handleUserSubmitFormClose}
				onSubmit={handleUserSubmission}
				user={userToUpdate || undefined}
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
