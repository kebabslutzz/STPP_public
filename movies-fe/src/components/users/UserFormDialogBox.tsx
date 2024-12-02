import React from 'react';
import { Dialog, DialogActions, DialogContent, DialogTitle, Button, TextField, MenuItem } from '@mui/material';
import { Formik, Form, Field, ErrorMessage } from 'formik';
import User from '../../interfaces/User';
import userValidationAdminSchema from '../../validation/userValidationAdmin';
import { ROLES } from '../../constants/userRoles';
import CancelIcon from '@mui/icons-material/Cancel';
import SendIcon from '@mui/icons-material/Send';
import { useAuth } from '../../context/AuthContext';

interface UserFormDialogBoxProps {
	open: boolean;
	onClose: () => void;
	onSubmit: (user: User) => void;
	user?: User;
	canEditRole?: boolean;
}

const UserFormDialogBox: React.FC<UserFormDialogBoxProps> = ({ open, onClose, onSubmit, user, canEditRole }) => {
	const { loggedInUserRole } = useAuth();

	return (
		<Dialog className='Dialog' open={open} onClose={onClose}>
			<DialogTitle sx={{ color: '#008080' }}>{user ? 'Edit User' : 'Add User'}</DialogTitle>
			<DialogContent>
				<Formik
					initialValues={{
						username: user?.username || '',
						email: user?.email || '',
						role: user?.role || loggedInUserRole, // Default role if not provided
					}}
					validationSchema={userValidationAdminSchema}
					onSubmit={(values, { setSubmitting }) => {
						// console.log('Submitting form with values:', values);
						const newUser: User = {
							...user,
							username: values.username,
							email: values.email,
							role: values.role,
						};
						onSubmit(newUser);
						setSubmitting(false);
						onClose();
					}}
				>
					{({ isSubmitting, errors, touched }) => (
						<Form>
							<Field
								as={TextField}
								margin='dense'
								label='Username'
								type='text'
								fullWidth
								name='username'
								error={touched.username && !!errors.username}
								helperText={<ErrorMessage name='username' component='div' />}
							/>
							<Field
								as={TextField}
								margin='dense'
								label='Email'
								type='email'
								fullWidth
								name='email'
								error={touched.email && !!errors.email}
								helperText={<ErrorMessage name='email' component='div' />}
							/>
							<Field
								as={TextField}
								select
								margin='dense'
								label='Role'
								fullWidth
								name='role'
								error={touched.role && !!errors.role}
								helperText={<ErrorMessage name='role' component='div' />}
							>
								{Object.values(ROLES).map((role) => (
									<MenuItem key={role} value={role}>
										{role}
									</MenuItem>
								))}
							</Field>
							<DialogActions>
								<Button className='Button cancel-button' onClick={onClose} endIcon={<CancelIcon />}>
									Cancel
								</Button>
								<Button type='submit' className='Button add-edit-button' disabled={isSubmitting} endIcon={<SendIcon />}>
									Submit
								</Button>
							</DialogActions>
						</Form>
					)}
				</Formik>
			</DialogContent>
		</Dialog>
	);
};

export default UserFormDialogBox;
