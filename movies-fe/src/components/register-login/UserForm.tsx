import React from 'react';
import { Formik, Form, Field, ErrorMessage } from 'formik';
import { TextField, Button, Container, IconButton, InputAdornment } from '@mui/material';
import { Visibility, VisibilityOff } from '@mui/icons-material';
import User from '../../interfaces/User';
import userRegisterValidationSchema from '../../validation/userRegisterValidation';
// import { useNavigate } from 'react-router-dom';

interface UserFormProps {
	initialValues: User;
	onSubmit: (values: User, setFieldError: (field: string, message: string) => void) => void;
	submitButtonText: string;
}

const UserForm: React.FC<UserFormProps> = ({ initialValues, onSubmit, submitButtonText }) => {
	const [showPassword, setShowPassword] = React.useState(false);
	const [showConfirmPassword, setShowConfirmPassword] = React.useState(false);

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

	return (
		<Container className='PageContainer' maxWidth={false}>
			<Container className='LoginRegister' maxWidth={false}>
				<h1 className='h1'>{submitButtonText}</h1>
				<Formik
					initialValues={initialValues}
					validationSchema={userRegisterValidationSchema}
					onSubmit={(values, { setSubmitting, setFieldError }) => {
						const { confirmPassword, ...user } = values;
						onSubmit(user, setFieldError);
						setSubmitting(false); // Ensure to set submitting to false after submission
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
								helperText={<ErrorMessage name='username' component='div' className='helperText' />}
								className='textField-root'
							/>
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
							<Field
								as={TextField}
								margin='dense'
								label='Confirm Password'
								type={showConfirmPassword ? 'text' : 'password'}
								fullWidth
								name='confirmPassword'
								error={touched.confirmPassword && !!errors.confirmPassword}
								helperText={<ErrorMessage name='confirmPassword' component='div' className='helperText' />}
								className='textField-root'
								InputProps={{
									endAdornment: (
										<InputAdornment position='end'>
											<IconButton
												aria-label='toggle confirm password visibility'
												onClick={handleClickShowConfirmPassword}
												onMouseDown={handleMouseDownConfirmPassword}
												edge='end'
												sx={{ color: 'white' }}
											>
												{showConfirmPassword ? <VisibilityOff /> : <Visibility />}
											</IconButton>
										</InputAdornment>
									),
								}}
							/>
							<Button type='submit' className='Button add-edit-button' disabled={isSubmitting}>
								{submitButtonText}
							</Button>
						</Form>
					)}
				</Formik>
			</Container>
		</Container>
	);
};

export default UserForm;
