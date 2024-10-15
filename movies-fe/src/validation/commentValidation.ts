import exp from 'constants';
import * as yup from 'yup';

const commentValidationSchema = yup.object().shape({
	content: yup
		.string()
		.min(1, 'Comment must be at least 1 character long')
		.max(1024, 'Comment must be at most 1024 characters long')
		.required('Comment is required')
		.matches(/.*\S.*/, 'Comment must not be blank'), // Ensure title is not just spaces
});

export default commentValidationSchema;
