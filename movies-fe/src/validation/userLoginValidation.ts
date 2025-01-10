import * as yup from 'yup';

const userLoginValidationSchema = yup.object().shape({
	email: yup.string().email('Email is not valid').required('Email is required'),
	password: yup
		.string()
		.required('Password is required')
		.min(8, 'Password must be at least 8 characters long')
		.max(256, 'Password must be at most 256 characters long'),
});

export default userLoginValidationSchema;
