import exp from 'constants';
import { PassThrough } from 'stream';
import * as yup from 'yup';

const userValidationSchema = yup.object().shape({
	username: yup
		.string()
		.required('Username is required')
		.min(8, 'Username must be at least 8 character long')
		.max(256, 'Username must be at most 256 characters long')
		.matches(/.*\S.*/, 'Username must not be blank')
		.matches(/^\S+$/, 'Username must not contain spaces'),
	email: yup.string().email('Email is not valid').required('Email is required'),
	role: yup
		.string()
		.required('Role is required')
		.matches(/.*\S.*/, 'Role must not be blank')
		.min(1, 'Role must be at least 1 character long')
		.max(256, 'Role must be at most 256 characters long'),
	password: yup
		.string()
		.required('Password is required')
		.min(8, 'Password must be at least 8 characters long')
		.max(256, 'Password must be at most 256 characters long'),
});

export default userValidationSchema;
