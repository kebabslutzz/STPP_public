// src/validation/MovieValidation.ts
import exp from 'constants';
import * as yup from 'yup';

const movieValidationSchema = yup.object().shape({
	title: yup
		.string()
		.required('Title is required')
		.min(1, 'Title must be at least 1 character long')
		.max(256, 'Title must be at most 256 characters long'),
	description: yup
		.string()
		.required('Description is required')
		.min(1, 'Description must be at least 1 character long')
		.max(1024, 'Description must be at most 256 characters long'),
	director: yup
		.string()
		.required('Director is required')
		.min(1, 'Director must be at least 1 character long')
		.max(256, 'Director must be at most 256 characters long'),
	genre: yup
		.string()
		.required('Genre is required')
		.min(1, 'Genre must be at least 1 character long')
		.max(256, 'Genre must be at most 256 characters long'),
	rating: yup
		.number()
		.required('Rating is required')
		.min(1, 'Rating must be at least 1')
		.max(10, 'Rating must be at most 10'),
	releaseDate: yup.date().required('Release Date is required'),
	poster: yup.mixed().required('Poster is required'),
});

export default movieValidationSchema;
