import React, { useEffect, useState } from 'react';
import useQuery from '../../hooks/useQuery';
import Movie from '../../interfaces/Movie';
import { ENDPOINTS } from '../../constants/endpoints';
import { HTTP_METHODS } from '../../constants/httpsMethods';
import Loader from '../shared/Loader';
import { Outlet } from 'react-router-dom';
import MovieCard from './MocieCard';
import MovieFormDialogBox from './component/MovieFormDialogBox';
import { Button, Container } from '@mui/material';
import './MovieList.css';
import AddIcon from '@mui/icons-material/Add';

const MovieList: React.FC = () => {
	const [movieList, setMovieList] = useState<Movie[]>([]);
	const [isDialogOpen, setIsDialogOpen] = useState(false);
	const [listOfErrors, setListOfErrors] = useState<string[]>([]);

	console.log('Calling movies api');
	const {
		data: movies,
		isLoading,
		errors,
		getData,
	} = useQuery<Movie[]>({
		url: ENDPOINTS.MOVIES.GET_ALL_MOVIES,
		httpMethod: HTTP_METHODS.GET,
	});

	const refresh = useEffect(() => {
		if (!movies) {
			getData();
		}
	}, []);

	useEffect(() => {
		if (movies) {
			setMovieList(movies);
		}
	}, [movies]);

	console.log('movies:', movieList);

	console.log('MoviesPage rendered');

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

	const handleCreateSuccess = async (newMovie: Movie, poster: File) => {
		try {
			let posterId: number | null = null;

			if (poster) {
				const formData = new FormData();
				formData.append('file', poster);
				console.log('body:', formData);

				const posterResponse = await fetch(ENDPOINTS.POSTER.CREATE_POSTER, {
					method: HTTP_METHODS.POST,
					body: formData,
				});

				if (!posterResponse.ok) {
					throw new Error('Failed to upload poster');
				}

				console.log('POSTER RESPONSE:', posterResponse);
				const posterData = await posterResponse.json();
				console.log('POSTER DATA:', posterData);
				posterId = Number(posterData.id);
				console.log('CREATED POSTER ID:', posterId);
			}

			// Step 2: Create Movie
			const movieWithPoster: Movie = {
				...newMovie,
				...(posterId && { posterId }), // Conditionally include posterId
			};
			console.log('movieWithPoster:', movieWithPoster);
			const movieResponse = await createMovieCommand.sendData(movieWithPoster);

			if (!movieResponse?.data.id) {
				throw new Error('Failed to create movie');
			}

			// onCreateMovieSuccess(movieResponse.data);
		} catch (error) {
			setListOfErrors([String((error as Error).message)]);
		}
	};

	// const onCreateSuccess = (response: Movie) => {
	// 	const newMovie: Movie = response;
	// 	setMovieList((currentMovies) => {
	// 		return [newMovie, ...currentMovies];
	// 	});
	// };

	// const onCreateSubmit = async (values: Movie, poster: string) => {
	// 	const formData = new FormData();
	// 	formData.append('movie', JSON.stringify(values));
	// 	formData.append('poster', poster);
	// 	await createMovieCommand.sendData(formData);
	// };

	// const onCreateSuccess = (response: Movie) => {
	// 	const newMovie: Movie = response;
	// 	setMovieList((currentMovies) => {
	// 		return [newMovie, ...currentMovies];
	// 	});
	// };

	// const createMovie = useQuery({
	// 	url: ENDPOINTS.MOVIES.CREATE_MOVIE,
	// 	httpMethod: HTTP_METHODS.POST,
	// 	onSuccess: onCreateSuccess,
	// });

	// const onCreatePosterSuccess = (response: string) => {
	// 	const newPosterId: number = Number(response)!;

	// 	setPosterId(newPosterId);

	// 	movieToCreate!.posterId = posterId!;
	// 	createMovieCommand.sendData(movieToCreate!);
	// };

	// const createPosterCommand = useQuery({
	// 	url: ENDPOINTS.POSTER.CREATE_POSTER,
	// 	httpMethod: HTTP_METHODS.POST,
	// 	onSuccess: onCreatePosterSuccess,
	// });

	// const handleCreateSuccess = async (newMovie: Movie, poster: File) => {
	// 	setMovieToCreate(newMovie);
	// 	const formatData = new FormData();
	// 	formatData.append('file', poster);
	// 	await createPosterCommand.sendData(formatData);
	// 	handleCloseDialog();
	// };

	// const createMovieCommand = useQuery({
	// 	url: ENDPOINTS.MOVIES.CREATE_MOVIE,
	// 	httpMethod: HTTP_METHODS.POST,
	// 	onSuccess: onCreateSuccess,
	// });

	const handleOpenDialog = () => {
		setIsDialogOpen(true);
	};

	const handleCloseDialog = () => {
		setIsDialogOpen(false);
	};

	if (isLoading) return <Loader />;
	if (errors) return <div>{errors.join(', ')}</div>;

	return (
		// <div className='PageContainer'>
		<Container className='PageContainer' maxWidth={false}>
			<div className='Header-row'>
				<h1>Top Trending Movies Right Now!</h1>
				<Button className='Button add-edit-button' onClick={handleOpenDialog} endIcon={<AddIcon />}>
					Add Movie
				</Button>
			</div>
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
				<MovieFormDialogBox open={isDialogOpen} onClose={handleCloseDialog} onSubmit={handleCreateSuccess} />
			)}
			<Outlet /> {/* This will render nested routes */}
			{/* </div> */}
		</Container>
	);
};

export default MovieList;
