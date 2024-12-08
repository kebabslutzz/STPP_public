// UserFormDialogBox.tsx
import React from 'react';
import { Dialog, DialogActions, DialogContent, DialogTitle, Button, TextField, MenuItem } from '@mui/material';
import { Formik, Form, Field, ErrorMessage } from 'formik';
import * as Yup from 'yup';
import User from '../../interfaces/User';
import { ROLES } from '../../constants/userRoles';
import CancelIcon from '@mui/icons-material/Cancel';
import SendIcon from '@mui/icons-material/Send';

const roleValidationSchema = Yup.object().shape({
	role: Yup.string().required('Role is required'),
});

interface UserFormDialogBoxProps {
	open: boolean;
	onClose: () => void;
	onSubmit: (role: string) => void;
	currentRole?: string;
}

const UserFormDialogBox: React.FC<UserFormDialogBoxProps> = ({ open, onClose, onSubmit, currentRole }) => {
	return (
		<Dialog className='Dialog' open={open} onClose={onClose}>
			<DialogTitle sx={{ color: '#008080' }}>Update User Role</DialogTitle>
			<DialogContent>
				<Formik
					initialValues={{
						role: currentRole || '',
					}}
					validationSchema={roleValidationSchema}
					onSubmit={(values, { setSubmitting }) => {
						onSubmit(values.role);
						setSubmitting(false);
						onClose();
					}}
				>
					{({ isSubmitting, errors, touched }) => (
						<Form>
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
									Update Role
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
