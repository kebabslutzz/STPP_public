import React from 'react';
import { Formik, Form, Field, ErrorMessage } from 'formik';
import userLoginValidationSchema from '../../validation/userLoginValidation';
import { Button, Container, IconButton, InputAdornment, TextField, Typography } from '@mui/material';
import './UserRegister.css';
import LoginIcon from '@mui/icons-material/Login';
import { Visibility, VisibilityOff } from '@mui/icons-material';
import useQuery from '../../hooks/useQuery';
import { HTTP_METHODS } from '../../constants/httpsMethods';
import { ENDPOINTS } from '../../constants/endpoints';
import User from '../../interfaces/User';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../../context/AuthContext';

interface LoginResponse {
	token: string;
}

const UserLogin: React.FC = () => {
	const [showPassword, setShowPassword] = React.useState(false);
	const navigate = useNavigate();
	const { login } = useAuth();

	const handleClickShowPassword = () => {
		setShowPassword(!showPassword);
	};

	const handleMouseDownPassword = (event: React.MouseEvent<HTMLButtonElement>) => {
		event.preventDefault();
	};

	const { sendData: loginUserCommand, errors: loginUserErrors } = useQuery<LoginResponse>({
		url: ENDPOINTS.USERS.LOGIN,
		httpMethod: HTTP_METHODS.POST,
	});

	const handleUserLoginSubmit = async (user: User, setFieldError: (field: string, message: string) => void) => {
		try {
			const response = await loginUserCommand(user);
			// console.log('errors', loginUserErrors);
			// console.log('response:', response);

			if ('status' in response! && response.status === 200 && 'headers' in response && response.headers) {
				const token = response.headers['authorization']; // Extract the token from the header
				// console.log('token:', token);
				const extractedToken = token.split(' ')[1]; // Assuming the format is 'Bearer <token>'
				// console.log('Token:', extractedToken);
				login(extractedToken);
				navigate('/movies');
			} else if ('message' in response!) {
				console.error('Error response:', response.message);
			}
		} catch (error) {
			console.error('Error logging in user:', error);
		}
	};

	return (
		<Container className='PageContainer' maxWidth={false}>
			<Container className='LoginRegister' maxWidth={false}>
				<h1 className='h1'>Login</h1>
				<Formik
					initialValues={{ email: '', password: '' }}
					validationSchema={userLoginValidationSchema}
					onSubmit={(values, { setSubmitting, setFieldError }) => {
						const user: User = {
							email: values.email,
							password: values.password,
						};
						console.log('user', user);
						handleUserLoginSubmit(user, setFieldError);
						setSubmitting(false); // Ensure to set submitting to false after submission
					}}
				>
					{({ isSubmitting, errors, touched }) => (
						<Form>
							<Field
								as={TextField}
								margin='dense'
								label='Email'
								type='email'
								fullWidth
								name='email'
								error={touched.email && !!errors.email}
								helperText={<ErrorMessage name='email' component='div' className='helperText' />}
								className='textField-root'
							/>
							<Field
								as={TextField}
								margin='dense'
								label='Password'
								type={showPassword ? 'text' : 'password'}
								fullWidth
								name='password'
								error={touched.password && !!errors.password}
								helperText={<ErrorMessage name='password' component='div' className='helperText' />}
								className='textField-root'
								InputProps={{
									endAdornment: (
										<InputAdornment position='end'>
											<IconButton
												aria-label='toggle password visibility'
												onClick={handleClickShowPassword}
												onMouseDown={handleMouseDownPassword}
												edge='end'
												sx={{ color: 'white' }}
											>
												{showPassword ? <VisibilityOff /> : <Visibility />}
											</IconButton>
										</InputAdornment>
									),
								}}
							/>
							{loginUserErrors && (
								<Typography variant='body2' className='error-message'>
									{loginUserErrors[0]}
								</Typography>
							)}
							<Container className='ButtonContainer' maxWidth={false}>
								<Button
									type='submit'
									className='Button add-edit-button'
									disabled={isSubmitting}
									endIcon={<LoginIcon />}
								>
									Login
								</Button>
							</Container>
						</Form>
					)}
				</Formik>
			</Container>
		</Container>
	);
};

export default UserLogin;
