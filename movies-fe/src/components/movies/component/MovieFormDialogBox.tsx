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

	// const handleClickOutside = (event: React.MouseEvent<HTMLDivElement>) => {
	// 	// Ensure the click is outside the dialog content
	// 	if (dialogRef.current && !dialogRef.current.contains(event.target as Node)) {
	// 		onClose();
	// 	} else {
	// 		console.log('clicked inside');
	// 	}
	// };

	useEffect(() => {
		document.addEventListener('keydown', handleKeyDown);

		return () => {
			document.removeEventListener('keydown', handleKeyDown);
		};
	}, []);

	return (
		<Dialog open={open} onClose={onClose} ref={dialogRef} aria-labelledby='form-dialog-title'>
			<DialogTitle id='form-dialog-title' sx={{ color: '#008080' }}>
				{movie ? 'Edit Movie' : 'Add Movie'}
			</DialogTitle>
			<DialogContent>
				<TextField
					autoFocus
					margin='dense'
					label='Title'
					type='text'
					fullWidth
					value={title}
					onChange={(e) => setTitle(e.target.value)}
					error={!!errors.title}
					helperText={errors.title}
				/>
				<TextField
					margin='dense'
					label='Description'
					type='text'
					fullWidth
					multiline
					rows={4}
					value={description}
					onChange={(e) => setDescription(e.target.value)}
					error={!!errors.description}
					helperText={errors.description}
				/>
				<TextField
					margin='dense'
					label='Director'
					type='text'
					fullWidth
					value={director}
					onChange={(e) => setDirector(e.target.value)}
					error={!!errors.director}
					helperText={errors.director}
				/>
				<TextField
					margin='dense'
					label='Genre'
					type='text'
					fullWidth
					value={genre}
					onChange={(e) => setGenre(e.target.value)}
					error={!!errors.genre}
					helperText={errors.genre}
				/>
				<TextField
					margin='dense'
					label='Rating'
					type='text'
					fullWidth
					inputProps={{
						maxLength: 4,
						step: '1',
					}}
					value={rating !== undefined ? rating.toString() : ''}
					onChange={(e) => {
						const value = e.target.value;
						// Allow empty input
						if (value === '') {
							setRating(undefined);
						} else {
							// Validate and parse the input value
							const regex = /^\d*\.?\d*$/;
							if (regex.test(value)) {
								setRating(value);
							}
						}
					}}
					error={!!errors.rating}
					helperText={errors.rating}
				/>

				<TextField
					margin='dense'
					label='Release Date'
					type='date'
					fullWidth
					InputLabelProps={{
						shrink: true,
					}}
					value={releaseDate ? releaseDate.toISOString().split('T')[0] : ''}
					onChange={(e) => setReleaseDate(new Date(e.target.value))}
					error={!!errors.releaseDate}
					helperText={errors.releaseDate}
				/>
				<TextField
					margin='dense'
					label='Poster'
					type='file'
					fullWidth
					InputLabelProps={{
						shrink: true,
					}}
					onChange={(e: React.ChangeEvent<HTMLInputElement>) => setPoster(e.target.files ? e.target.files[0] : null)}
					error={!!errors.poster}
					helperText={errors.poster}
				/>
			</DialogContent>
			<DialogActions>
				<Button onClick={onClose} className='Button cancel-button' endIcon={<CancelIcon />}>
					Cancel
				</Button>
				<Button onClick={handleSubmit} className={'Button add-edit-button'} endIcon={<SendIcon />}>
					Submit
				</Button>
			</DialogActions>
		</Dialog>
	);
};

export default MovieFormDialogBox;
