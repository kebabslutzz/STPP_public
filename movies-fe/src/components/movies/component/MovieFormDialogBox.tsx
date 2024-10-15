import React, { useEffect, useState } from 'react';
import Movie from '../../../interfaces/Movie';
import SendIcon from '@mui/icons-material/Send';
import './MovieFormDialogBox.css';
import { Button, Dialog, DialogActions, DialogContent, DialogTitle, TextField } from '@mui/material';
import movieValidationSchema from '../../../validation/movieValidation';
import CancelIcon from '@mui/icons-material/Cancel';
import { ErrorMessage, Field, Formik, Form } from 'formik';

interface MovieFormDialogBoxProps {
	onClose: () => void;
	onSubmit: (movie: Movie, poster?: File) => void;
	open: boolean;
	movie?: Movie;
}

const MovieFormDialogBox: React.FC<MovieFormDialogBoxProps> = ({ onClose, onSubmit, open, movie }) => {
	const [poster, setPoster] = useState<File | null>(null); // Ensure the type is File | null

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
						id: movie?.id! || undefined,
						title: movie?.title || '',
						description: movie?.description || '',
						director: movie?.director || '',
						genre: movie?.genre || '',
						rating: movie?.rating || '1.0',
						releaseDate: movie?.releaseDate
							? movie.releaseDate.toISOString().split('T')[0]
							: new Date().toISOString().split('T')[0],
						posterId: movie?.posterId || undefined,
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
	);
};

export default MovieFormDialogBox;
