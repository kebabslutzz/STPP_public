import React, { useEffect, useState } from 'react';
import useQuery from '../../hooks/useQuery';
import Movie from '../../interfaces/Movie';
import { ENDPOINTS } from '../../constants/endpoints';
import { HTTP_METHODS } from '../../constants/httpsMethods';
import Loader from '../shared/Loader';
import MovieCard from './MocieCard';
import MovieFormDialogBox from './component/MovieFormDialogBox';
import { Button, Container } from '@mui/material';
import './MovieList.css';
import AddIcon from '@mui/icons-material/Add';
import ErrorDisplay from '../shared/ErrorDisplay';

const MovieList: React.FC = () => {
	const [movieList, setMovieList] = useState<Movie[]>([]);
	const [isDialogOpen, setIsDialogOpen] = useState(false);
	const [listOfErrors, setListOfErrors] = useState<string[]>([]);

	const handleOpenDialog = () => {
		setIsDialogOpen(true);
	};

	const handleCloseDialog = () => {
		setIsDialogOpen(false);
	};

	const {
		data: movies,
		isLoading: isLoadingMovies,
		errors: movieErrors,
		getData,
	} = useQuery<Movie[]>({
		url: ENDPOINTS.MOVIES.GET_ALL_MOVIES,
		httpMethod: HTTP_METHODS.GET,
	});

	useEffect(() => {
		if (!movies) {
			getData();
		}
	}, []);

	useEffect(() => {
		if (movies) {
			setMovieList(movies);
		}
	}, [movies]);

	const onCreateMovieSuccess = (response: Movie) => {
		const newMovie: Movie = response;
		setMovieList((currentMovies) => {
			return [newMovie, ...currentMovies];
		});
	};

	const createMovieCommand = useQuery({
		url: ENDPOINTS.MOVIES.CREATE_MOVIE,
		httpMethod: HTTP_METHODS.POST,
		onSuccess: onCreateMovieSuccess,
	});

	const handleMovieCreationSubmission = async (newMovie: Movie, poster?: File) => {
		try {
			let posterId: number | null = null;

			if (poster!) {
				const formData = new FormData();
				formData.append('file', poster!);

				const posterResponse = await fetch(ENDPOINTS.POSTER.CREATE_POSTER, {
					method: HTTP_METHODS.POST,
					body: formData,
				});

				if (!posterResponse.ok) {
					throw new Error('Failed to upload poster');
				}

				const posterData = await posterResponse.json();
				posterId = Number(posterData.id);
			}

			// Step 2: Create Movie
			const movieWithPoster: Movie = {
				...newMovie,
				...(posterId && { posterId }), // Conditionally include posterId
			};
			const movieResponse = await createMovieCommand.sendData(movieWithPoster);

			if (!movieResponse?.data.id) {
				throw new Error('Failed to create movie');
			}
		} catch (error) {
			setListOfErrors([String((error as Error).message)]);
			console.log('Error creating movie', listOfErrors);
		}
	};

	return (
		<Container className='PageContainer' maxWidth={false}>
			<div className='Header-row'>
				<h1>Top Trending Movies Right Now!</h1>

				{!isLoadingMovies && !movieErrors && (
					<Button className='Button add-edit-button' onClick={handleOpenDialog} endIcon={<AddIcon />}>
						Add Movie
					</Button>
				)}
			</div>
			{isLoadingMovies && <Loader errors={movieErrors} />}
			{movieErrors && !isLoadingMovies && <ErrorDisplay errors={movieErrors} />}
			<div className='MovieList'>
				{movieList.map((movie) => (
					<MovieCard
						key={movie.id}
						id={movie.id!}
						title={movie.title}
						description={movie.description}
						posterId={movie.posterId}
					/>
				))}
			</div>
			{isDialogOpen && (
				<MovieFormDialogBox open={isDialogOpen} onClose={handleCloseDialog} onSubmit={handleMovieCreationSubmission} />
			)}
		</Container>
	);
};

export default MovieList;
