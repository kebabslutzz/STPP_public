import React, { useEffect, useState } from 'react';
import useQuery from '../../hooks/useQuery';
import Movie from '../../interfaces/Movie';
import { ENDPOINTS } from '../../constants/endpoints';
import { HTTP_METHODS } from '../../constants/httpsMethods';
import Loader from '../shared/Loader';
import { FormMethod, Outlet, useNavigate } from 'react-router-dom';
import MovieCard from './MocieCard';
import ROUTE_PATHS from '../../constants/routePaths';
import MovieFormDialogBox from './component/MovieFormDialogBox';
import { format } from 'path';
import Poster from '../../interfaces/Poster';
import { json } from 'stream/consumers';
import { Button, Container } from '@mui/material';
import './MovieList.css';
import AddIcon from '@mui/icons-material/Add';

const MovieList: React.FC = () => {
	const [movieList, setMovieList] = useState<Movie[]>([]);
	const [isDialogOpen, setIsDialogOpen] = useState(false);
	const [posterId, setPosterId] = useState<number | null>(null);
	const navigate = useNavigate();
	const [listOfErrors, setListOfErrors] = useState<string[]>([]);
	const [movieToCreate, setMovieToCreate] = useState<Movie | null>(null);

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
	// const handleViewClick = (id: number) => {
	// 	// navigate(`/movies/${id}`);
	// 	navigate(`${ROUTE_PATHS.HOME}/${id}${ROUTE_PATHS.DISCUSSIONS}`);
	// };

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
			const formData = new FormData();
			formData.append('file', poster);
			// const body = JSON.stringify(formData);
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
			const posterId: number = Number(posterData.id);
			console.log('CREATED POSTER ID:', posterId);
			newMovie.posterId = posterId;

			// Step 2: Create Movie
			const movieWithPoster: Movie = {
				...newMovie,
				posterId,
			};
			console.log('movieWithPoster:', movieWithPoster);
			const movieResponse = await createMovieCommand.sendData(movieWithPoster);
			//  => {
			// body: JSON.stringify(movieWithPoster),
			// };

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
			<div className='header-row'>
				<h1>Top Trending Movies Right Now!</h1>
				<Button
					className='add-movie-button'
					onClick={handleOpenDialog}
					variant='text'
					sx={{ color: '#dddbcb', backgroundColor: '#008080 !important' }}
					endIcon={<AddIcon />}
				>
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
