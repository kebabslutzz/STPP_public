import * as yup from 'yup';

const userEditValidationSchema = yup.object().shape({
	username: yup
		.string()
		.required('Username is required')
		.min(8, 'Username must be at least 8 characters long')
		.max(256, 'Username must be at most 256 characters long')
		.matches(/.*\S.*/, 'Username must not be blank')
		.matches(/^\S+$/, 'Username must not contain spaces'),
	email: yup.string().email('Email is not valid').required('Email is required'),
});

export default userEditValidationSchema;
