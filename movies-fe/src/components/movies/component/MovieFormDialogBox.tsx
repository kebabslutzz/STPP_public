import React, { useEffect, useRef, useState } from 'react';
import useQuery from '../../../hooks/useQuery';
import { ENDPOINTS } from '../../../constants/endpoints';
import { HTTP_METHODS } from '../../../constants/httpsMethods';
import Movie from '../../../interfaces/Movie';
import SendIcon from '@mui/icons-material/Send';
import './MovieFormDialogBox.css';
import { Button, Dialog, DialogActions, DialogContent, DialogTitle, FormHelperText, TextField } from '@mui/material';
import Poster from '../../../interfaces/Poster';

interface MovieFormDialogBoxProps {
	onClose: () => void;
	onSubmit: (movie: Movie, poster: File) => void;
	open: boolean;
}

const MovieFormDialogBox: React.FC<MovieFormDialogBoxProps> = ({ onClose, onSubmit, open }) => {
	const [title, setTitle] = useState('');
	const [description, setDescription] = useState('');
	const [director, setDirector] = useState('');
	const [genre, setGenre] = useState('');
	const [rating, setRating] = useState<string | undefined>('0.0');
	const [releaseDate, setReleaseDate] = useState<Date>(new Date());
	const [poster, setPoster] = useState<File | null>(null); // Ensure the type is File | null
	const [errors, setErrors] = useState<{ [key: string]: string }>({});
	const dialogRef = useRef<HTMLDivElement>(null);

	const handleSubmit = async () => {
		const newErrors: { [key: string]: string } = {};
		if (!title) newErrors.title = 'Title is required';
		if (!description) newErrors.description = 'Description is required';
		if (!rating) newErrors.rating = 'Rating is required';
		if (!director) newErrors.director = 'Director is required';
		if (!genre) newErrors.genre = 'Genre is required';

		if (Object.keys(newErrors).length > 0) {
			setErrors(newErrors);
			return;
		}

		const newMovie: Movie = {
			title,
			description,
			director,
			genre,
			rating: Number(rating),
			releaseDate,
		};

		onSubmit(newMovie, poster as File);
		onClose();

		// try {
		// 	// Step 1: Create Poster
		// 	let posterId;
		// 	if (poster) {
		// 		const handlePosterCreateSubmit = async (values: Poster) => {
		// 			await createPoster.sendData(values.file);
		// 		};
		// 		// const formData = new FormData();
		// 		// formData.append('file', poster);
		// 		// const posterResponse = await fetch(ENDPOINTS.POSTER.CREATE_POSTER, {
		// 		// 	method: HTTP_METHODS.POST,
		// 		// 	body: formData,
		// 		// });
		// 		// if (!posterResponse.ok) {
		// 		// 	throw new Error('Failed to upload poster');
		// 		// }
		// 		// const posterData = await posterResponse.json();
		// 		// posterId = posterData.id;
		// 	}

		// 	// Step 2: Create Movie
		// 	const newMovie: Movie = {
		// 		id: Math.random(), // Replace with proper ID generation
		// 		title,
		// 		description,
		// 		director,
		// 		genre,
		// 		rating,
		// 		releaseDate,
		// 		posterId,
		// 	};

		// 	const movieResponse = await fetch(ENDPOINTS.MOVIES.CREATE_MOVIE, {
		// 		method: HTTP_METHODS.POST,
		// 		headers: {
		// 			'Content-Type': 'application/json',
		// 		},
		// 		body: JSON.stringify(newMovie),
		// 	});

		// 	if (!movieResponse.ok) {
		// 		throw new Error('Failed to create movie');
		// 	}

		// 	const createdMovie = await movieResponse.json();
		// 	onSubmit(createdMovie);
		// 	onClose();
		// } catch (error) {
		// 	setErrors({ submit: error.message });
		// }
	};

	const handleKeyDown = (event: KeyboardEvent) => {
		if (event.key === 'Escape') {
			onClose();
		}
	};

	const handleClickOutside = (event: React.MouseEvent<HTMLDivElement>) => {
		// Ensure the click is outside the dialog content
		if (dialogRef.current && !dialogRef.current.contains(event.target as Node)) {
			onClose();
		} else {
			console.log('clicked inside');
		}
	};

	useEffect(() => {
		document.addEventListener('keydown', handleKeyDown);

		return () => {
			document.removeEventListener('keydown', handleKeyDown);
		};
	}, []);

	return (
		<Dialog open={open} onClose={onClose} aria-labelledby='form-dialog-title'>
			<DialogTitle id='form-dialog-title'>Add Movie</DialogTitle>
			<DialogContent>
				{/* {errors.length > 0 && (
					<div className='Errors'>
						{errors.map((error, index) => (
							<p key={index}>{error}</p>
						))}
					</div>
				)} */}
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
				{/* <input
					type='file'
					onChange={(e) => setPoster(e.target.files ? e.target.files[0] : null)}
					style={{ marginTop: '10px' }}
				/> */}
				{errors.poster && <FormHelperText error>{errors.poster}</FormHelperText>}
			</DialogContent>
			<DialogActions>
				<Button onClick={onClose} color='primary'>
					Cancel
				</Button>
				<Button variant='contained' onClick={handleSubmit} color='primary' endIcon={<SendIcon />}>
					Submit
				</Button>
			</DialogActions>
		</Dialog>
	);
};

export default MovieFormDialogBox;
