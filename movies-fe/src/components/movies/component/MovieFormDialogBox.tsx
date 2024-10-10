import React, { useEffect, useRef, useState } from 'react';
import useQuery from '../../../hooks/useQuery';
import { ENDPOINTS } from '../../../constants/endpoints';
import { HTTP_METHODS } from '../../../constants/httpsMethods';
import Movie from '../../../interfaces/Movie';
import SendIcon from '@mui/icons-material/Send';
import './MovieFormDialogBox.css';
import { Button, Dialog, DialogActions, DialogContent, DialogTitle, FormHelperText, TextField } from '@mui/material';
import Poster from '../../../interfaces/Poster';
import movieValidationSchema from '../../../validation/movieValidation';
import * as yup from 'yup';
import CancelIcon from '@mui/icons-material/Cancel';
import { ErrorMessage, Field, Formik, Form } from 'formik';
import { idText } from 'typescript';

interface MovieFormDialogBoxProps {
	onClose: () => void;
	onSubmit: (movie: Movie, poster: File) => void;
	open: boolean;
	movie?: Movie;
}

const MovieFormDialogBox: React.FC<MovieFormDialogBoxProps> = ({ onClose, onSubmit, open, movie }) => {
	const [title, setTitle] = useState(movie?.title || '');
	const [description, setDescription] = useState(movie?.description || '');
	const [director, setDirector] = useState(movie?.director || '');
	const [genre, setGenre] = useState(movie?.genre || '');
	const [rating, setRating] = useState<string | undefined>(movie?.rating?.toString() || '0.0');
	const [releaseDate, setReleaseDate] = useState<Date>(movie?.releaseDate || new Date());
	const [poster, setPoster] = useState<File | null>(null); // Ensure the type is File | null
	const [errors, setErrors] = useState<{ [key: string]: string }>({});
	const [posterID, setPosterID] = useState<number | undefined>(movie?.posterId || undefined);
	const dialogRef = useRef<HTMLDivElement>(null);

	const handleSubmit = async (event: React.FormEvent) => {
		event.preventDefault(); // Prevent form submission

		const newMovie: Movie = {
			id: movie?.id,
			title,
			description,
			director,
			genre,
			rating: Number(rating),
			releaseDate,
			posterId: posterID,
		};

		try {
			await movieValidationSchema.validate(
				{ title, description, director, genre, rating, releaseDate, poster },
				{ abortEarly: false }
			);
			onSubmit(newMovie, poster!);
			onClose();
		} catch (validationErrors) {
			const newErrors: { [key: string]: string } = {};
			(validationErrors as yup.ValidationError).inner.forEach((error) => {
				if (error.path) newErrors[error.path] = error.message;
			});
			setErrors(newErrors);
		}
	};

	const handleKeyDown = (event: KeyboardEvent) => {
		if (event.key === 'Escape') {
			onClose();
		}
	};

	useEffect(() => {
		document.addEventListener('keydown', handleKeyDown);

		return () => {
			document.removeEventListener('keydown', handleKeyDown);
		};
	}, []);

	return (
		<Dialog open={open} onClose={onClose}>
			<DialogTitle sx={{ color: '#008080' }}>{movie ? 'Edit Movie' : 'Add Movie'}</DialogTitle>
			<DialogContent>
				<Formik
					initialValues={{
						id: movie?.id || undefined,
						title: movie?.title || '',
						description: movie?.description || '',
						director: movie?.director || '',
						genre: movie?.genre || '',
						rating: movie?.rating || '1.0',
						releaseDate: movie?.releaseDate
							? movie.releaseDate.toISOString().split('T')[0]
							: new Date().toISOString().split('T')[0],
					}}
					validationSchema={movieValidationSchema}
					onSubmit={(values, { setSubmitting }) => {
						const newMovie: Movie = {
							...values,
							releaseDate: new Date(values.releaseDate),
							rating: Number(values.rating),
						};
						console.log('newMovie', newMovie);
						onSubmit(newMovie, poster!);
						setSubmitting(false);
						onClose();
					}}
				>
					{({ isSubmitting, setFieldValue, errors, touched }) => (
						<Form>
							<Field
								as={TextField}
								margin='dense'
								label='Title'
								type='text'
								fullWidth
								name='title'
								error={touched.title && !!errors.title}
								helperText={<ErrorMessage name='title' component='div' />}
							/>
							<Field
								as={TextField}
								margin='dense'
								label='Description'
								type='text'
								fullWidth
								name='description'
								error={touched.description && !!errors.description}
								helperText={<ErrorMessage name='description' component='div' />}
							/>
							<Field
								as={TextField}
								margin='dense'
								label='Director'
								type='text'
								fullWidth
								name='director'
								error={touched.director && !!errors.director}
								helperText={<ErrorMessage name='director' component='div' />}
							/>
							<Field
								as={TextField}
								margin='dense'
								label='Genre'
								type='text'
								fullWidth
								name='genre'
								error={touched.genre && !!errors.genre}
								helperText={<ErrorMessage name='genre' component='div' />}
							/>
							<Field
								as={TextField}
								margin='dense'
								label='Rating'
								type='text'
								fullWidth
								name='rating'
								error={touched.rating && !!errors.rating}
								helperText={<ErrorMessage name='rating' component='div' />}
							/>
							<Field
								as={TextField}
								margin='dense'
								label='Release Date'
								type='date'
								fullWidth
								name='releaseDate'
								InputLabelProps={{
									shrink: true,
								}}
								error={touched.releaseDate && !!errors.releaseDate}
								helperText={<ErrorMessage name='releaseDate' component='div' />}
							/>
							<TextField
								margin='dense'
								label='Poster'
								type='file'
								fullWidth
								InputLabelProps={{
									shrink: true,
								}}
								onChange={(e: React.ChangeEvent<HTMLInputElement>) => {
									setPoster(e.target.files ? e.target.files[0] : null);
									setFieldValue('poster', e.target.files ? e.target.files[0] : null);
								}}
								// error={!!errors.poster}
								helperText={<ErrorMessage name='poster' component='div' />}
							/>
							<DialogActions>
								<Button onClick={onClose} className='Button cancel-button' endIcon={<CancelIcon />}>
									Cancel
								</Button>
								<Button type='submit' className='Button add-edit-button' disabled={isSubmitting} endIcon={<SendIcon />}>
									Submit
								</Button>
							</DialogActions>
						</Form>
					)}
				</Formik>
			</DialogContent>
		</Dialog>

		// <Dialog open={open} onClose={onClose} ref={dialogRef} aria-labelledby='form-dialog-title'>
		// 	<DialogTitle id='form-dialog-title' sx={{ color: '#008080' }}>
		// 		{movie ? 'Edit Movie' : 'Add Movie'}
		// 	</DialogTitle>
		// 	<DialogContent>
		// 		<TextField
		// 			autoFocus
		// 			margin='dense'
		// 			label='Title'
		// 			type='text'
		// 			fullWidth
		// 			value={title}
		// 			onChange={(e) => setTitle(e.target.value)}
		// 			error={!!errors.title}
		// 			helperText={errors.title}
		// 		/>
		// 		<TextField
		// 			margin='dense'
		// 			label='Description'
		// 			type='text'
		// 			fullWidth
		// 			multiline
		// 			rows={4}
		// 			value={description}
		// 			onChange={(e) => setDescription(e.target.value)}
		// 			error={!!errors.description}
		// 			helperText={errors.description}
		// 		/>
		// 		<TextField
		// 			margin='dense'
		// 			label='Director'
		// 			type='text'
		// 			fullWidth
		// 			value={director}
		// 			onChange={(e) => setDirector(e.target.value)}
		// 			error={!!errors.director}
		// 			helperText={errors.director}
		// 		/>
		// 		<TextField
		// 			margin='dense'
		// 			label='Genre'
		// 			type='text'
		// 			fullWidth
		// 			value={genre}
		// 			onChange={(e) => setGenre(e.target.value)}
		// 			error={!!errors.genre}
		// 			helperText={errors.genre}
		// 		/>
		// 		<TextField
		// 			margin='dense'
		// 			label='Rating'
		// 			type='text'
		// 			fullWidth
		// 			inputProps={{
		// 				maxLength: 4,
		// 				step: '1',
		// 			}}
		// 			value={rating !== undefined ? rating.toString() : ''}
		// 			onChange={(e) => {
		// 				const value = e.target.value;
		// 				// Allow empty input
		// 				if (value === '') {
		// 					setRating(undefined);
		// 				} else {
		// 					// Validate and parse the input value
		// 					const regex = /^\d*\.?\d*$/;
		// 					if (regex.test(value)) {
		// 						setRating(value);
		// 					}
		// 				}
		// 			}}
		// 			error={!!errors.rating}
		// 			helperText={errors.rating}
		// 		/>

		// 		<TextField
		// 			margin='dense'
		// 			label='Release Date'
		// 			type='date'
		// 			fullWidth
		// 			InputLabelProps={{
		// 				shrink: true,
		// 			}}
		// 			value={releaseDate ? releaseDate.toISOString().split('T')[0] : ''}
		// 			onChange={(e) => setReleaseDate(new Date(e.target.value))}
		// 			error={!!errors.releaseDate}
		// 			helperText={errors.releaseDate}
		// 		/>
		// 		<TextField
		// 			margin='dense'
		// 			label='Poster'
		// 			type='file'
		// 			fullWidth
		// 			InputLabelProps={{
		// 				shrink: true,
		// 			}}
		// 			onChange={(e: React.ChangeEvent<HTMLInputElement>) => setPoster(e.target.files ? e.target.files[0] : null)}
		// 			error={!!errors.poster}
		// 			helperText={errors.poster}
		// 		/>
		// 	</DialogContent>
		// 	<DialogActions>
		// 		<Button onClick={onClose} className='Button cancel-button' endIcon={<CancelIcon />}>
		// 			Cancel
		// 		</Button>
		// 		<Button onClick={handleSubmit} className={'Button add-edit-button'} endIcon={<SendIcon />}>
		// 			Submit
		// 		</Button>
		// 	</DialogActions>
		// </Dialog>
	);
};

export default MovieFormDialogBox;
