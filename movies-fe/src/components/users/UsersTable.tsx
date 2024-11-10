import { Button, Table, TableBody, TableCell, TableContainer, TableHead, TableRow } from '@mui/material';
import EditIcon from '@mui/icons-material/Edit';
import DeleteIcon from '@mui/icons-material/Delete';
import User from '../../interfaces/User';
import './UsersTable.css';
import PersonRemoveIcon from '@mui/icons-material/PersonRemove';
import { useAuth } from '../../context/AuthContext';

interface UserListProps {
	users: User[];
	onDelete: (id: number) => void;
	onEdit: (user: User) => void;
}

const UsersTable: React.FC<UserListProps> = ({ users, onDelete, onEdit }) => {
	const { isAdmin } = useAuth();

	const handleUpdateUser = (user: User) => {
		onEdit(user);
	};

	const handleDeleteUser = (id: number) => {
		onDelete(id);
	};

	return (
		<TableContainer className='TableContainer'>
			<Table>
				<TableHead>
					<TableRow className='TableRow'>
						<TableCell>User ID</TableCell>
						<TableCell>User Name</TableCell>
						<TableCell>User Email</TableCell>
						<TableCell>User Role</TableCell>
						<TableCell>Created</TableCell>
						<TableCell>Modified</TableCell>
						<TableCell>Action</TableCell>
					</TableRow>
				</TableHead>
				<TableBody>
					{users.map((user) => (
						<TableRow key={user.id}>
							<TableCell>{user.id}</TableCell>
							<TableCell>{user.username}</TableCell>
							<TableCell>{user.email}</TableCell>
							<TableCell>{user.role}</TableCell>
							<TableCell>{new Date(user.dateCreated!).toDateString()}</TableCell>
							<TableCell>{new Date(user.dateModified!).toDateString()}</TableCell>
							{isAdmin && (
								<TableCell>
									<Button
										className='Button add-edit-button'
										onClick={() => handleUpdateUser(user)}
										variant='contained'
										color='primary'
										endIcon={<EditIcon />}
									>
										Edit
									</Button>
									<Button
										className='Button delete-button'
										onClick={() => handleDeleteUser(user.id!)}
										variant='contained'
										color='secondary'
										endIcon={<PersonRemoveIcon />}
									>
										Delete
									</Button>
								</TableCell>
							)}
						</TableRow>
					))}
				</TableBody>
			</Table>
		</TableContainer>
	);
};

export default UsersTable;
