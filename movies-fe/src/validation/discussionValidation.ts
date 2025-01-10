import exp from 'constants';
import * as yup from 'yup';

const discussionValidationSchema = yup.object().shape({
	title: yup
		.string()
		.required('Title is required')
		.min(1, 'Title must be at least 1 character long')
		.max(256, 'Title must be at most 256 characters long')
		.required('Title is required')
		.matches(/.*\S.*/, 'Title must not be blank'), // Ensure title is not just spaces
});

export default discussionValidationSchema;
