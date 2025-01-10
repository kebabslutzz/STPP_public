import React from 'react';
import { Formik, Form, Field, ErrorMessage } from 'formik';
import userRegisterValidationSchema from '../../validation/userRegisterValidation';
import { Button, Container, IconButton, InputAdornment, TextField } from '@mui/material';
import './UserRegister.css';
import PersonAddIcon from '@mui/icons-material/PersonAdd';
import { Visibility, VisibilityOff } from '@mui/icons-material';
import useQuery from '../../hooks/useQuery';
import { HTTP_METHODS } from '../../constants/httpsMethods';
import { ENDPOINTS } from '../../constants/endpoints';
import User from '../../interfaces/User';
import { useNavigate } from 'react-router-dom';
import UserRegisterForm from './UserForm';
import UserFormDialogBox from '../users/UserFormDialogBox';
import UserForm from './UserForm';

const UserRegister: React.FC = () => {
	const [showPassword, setShowPassword] = React.useState(false);
	const [showConfirmPassword, setShowConfirmPassword] = React.useState(false);
	const navigate = useNavigate();

	const handleClickShowPassword = () => {
		setShowPassword(!showPassword);
	};

	const handleClickShowConfirmPassword = () => {
		setShowConfirmPassword(!showConfirmPassword);
	};

	const handleMouseDownPassword = (event: React.MouseEvent<HTMLButtonElement>) => {
		event.preventDefault();
	};

	const handleMouseDownConfirmPassword = (event: React.MouseEvent<HTMLButtonElement>) => {
		event.preventDefault();
	};

	const { sendData: registerUserCommand, errors: createUserErrors } = useQuery({
		url: ENDPOINTS.USERS.REGISTER,
		httpMethod: HTTP_METHODS.POST,
	});

	const handleUserCreateSubmit = async (newUser: User, setFieldError: (field: string, message: string) => void) => {
		try {
			const response = await registerUserCommand(newUser);
			// console.log('errors', createUserErrors);
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
				navigate('/login');
			}
		} catch (error) {
			console.error('Error creating user:', error);
		}
	};

	return (
		<UserForm
			initialValues={{ username: '', email: '', password: '', confirmPassword: '' }}
			onSubmit={handleUserCreateSubmit}
			submitButtonText='Register'
		/>
	);

	// return (
	// 	<Container className='PageContainer' maxWidth={false}>
	// 		<Container className='LoginRegister' maxWidth={false}>
	// 			<h1 className='h1'>Register</h1>
	// 			<Formik
	// 				initialValues={{ username: '', email: '', password: '', confirmPassword: '' }}
	// 				validationSchema={userRegisterValidationSchema}
	// 				onSubmit={(values, { setSubmitting, setFieldError }) => {
	// 					const { confirmPassword, ...user } = values;
	// 					const newUser: User = {
	// 						...values,
	// 					};
	// 					console.log('user', newUser);
	// 					handleUserCreateSubmit(newUser, setFieldError);
	// 					setSubmitting(false); // Ensure to set submitting to false after submission
	// 				}}
	// 			>
	// 				{({ isSubmitting, errors, touched }) => (
	// 					<Form>
	// 						<Field
	// 							as={TextField}
	// 							margin='dense'
	// 							label='Username'
	// 							type='text'
	// 							fullWidth
	// 							name='username'
	// 							error={touched.username && !!errors.username}
	// 							helperText={<ErrorMessage name='username' component='div' className='helperText' />}
	// 							className='textField-root'
	// 						/>
	// 						<Field
	// 							as={TextField}
	// 							margin='dense'
	// 							label='Email'
	// 							type='email'
	// 							fullWidth
	// 							name='email'
	// 							error={touched.email && !!errors.email}
	// 							helperText={<ErrorMessage name='email' component='div' className='helperText' />}
	// 							className='textField-root'
	// 						/>
	// 						<Field
	// 							as={TextField}
	// 							margin='dense'
	// 							label='Password'
	// 							type={showPassword ? 'text' : 'password'}
	// 							fullWidth
	// 							name='password'
	// 							error={touched.password && !!errors.password}
	// 							helperText={<ErrorMessage name='password' component='div' className='helperText' />}
	// 							className='textField-root'
	// 							InputProps={{
	// 								endAdornment: (
	// 									<InputAdornment position='end'>
	// 										<IconButton
	// 											aria-label='toggle password visibility'
	// 											onClick={handleClickShowPassword}
	// 											onMouseDown={handleMouseDownPassword}
	// 											edge='end'
	// 											sx={{ color: 'white' }}
	// 										>
	// 											{showPassword ? <VisibilityOff /> : <Visibility />}
	// 										</IconButton>
	// 									</InputAdornment>
	// 								),
	// 							}}
	// 						/>
	// 						<Field
	// 							as={TextField}
	// 							margin='dense'
	// 							label='Confirm Password'
	// 							type={showConfirmPassword ? 'text' : 'password'}
	// 							fullWidth
	// 							name='confirmPassword'
	// 							error={touched.confirmPassword && !!errors.confirmPassword}
	// 							helperText={<ErrorMessage name='confirmPassword' component='div' className='helperText' />}
	// 							className='textField-root'
	// 							InputProps={{
	// 								endAdornment: (
	// 									<InputAdornment position='end'>
	// 										<IconButton
	// 											aria-label='toggle confirm password visibility'
	// 											onClick={handleClickShowConfirmPassword}
	// 											onMouseDown={handleMouseDownConfirmPassword}
	// 											edge='end'
	// 											sx={{ color: 'white' }}
	// 										>
	// 											{showConfirmPassword ? <VisibilityOff /> : <Visibility />}
	// 										</IconButton>
	// 									</InputAdornment>
	// 								),
	// 							}}
	// 						/>
	// 						<Container className='ButtonContainer' maxWidth={false}>
	// 							<Button
	// 								type='submit'
	// 								className='Button add-edit-button'
	// 								disabled={isSubmitting}
	// 								endIcon={<PersonAddIcon />}
	// 							>
	// 								Register
	// 							</Button>
	// 						</Container>
	// 					</Form>
	// 				)}
	// 			</Formik>
	// 		</Container>
	// 	</Container>
	// );
};

export default UserRegister;
