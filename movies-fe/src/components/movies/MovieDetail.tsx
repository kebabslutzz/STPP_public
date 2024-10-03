import React, { useEffect, useState } from 'react';
import Movie from '../../interfaces/Movie';
import Loader from '../shared/Loader';
import './MovieDetail.css';
import Discussion from '../../interfaces/Discussion';
import useQuery from '../../hooks/useQuery';
import { ENDPOINTS } from '../../constants/endpoints';
import { HTTP_METHODS } from '../../constants/httpsMethods';
import { Button, Container, List } from '@mui/material';
import DiscussionListItem from '../discussions/DisccusionListItem';
import Poster from '../../interfaces/Poster';
import MovieFormDialogBox from './component/MovieFormDialogBox';
// import DiscussionList from '../discussions/DiscussionList';

interface MovieDetailProps {
	movieId: number;
}

const MovieDetail: React.FC<MovieDetailProps> = ({ movieId }) => {
	const [discussionList, setDiscussionList] = useState<Discussion[]>([]);
	const [imageUrl, setImageUrl] = useState<string | undefined>(undefined);
	const [isDialogOpen, setIsDialogOpen] = useState(false);
	const [listOfErrors, setListOfErrors] = useState<string[]>([]);

	const [openMovie, setOpenMovie] = useState<Movie | null>(null);
	const {
		data: movie,
		isLoading,
		errors,
		getData,
	} = useQuery<Movie>({
		url: ENDPOINTS.MOVIES.GET_MOVIE_BY_ID(movieId),
		httpMethod: HTTP_METHODS.GET,
	});

	useEffect(() => {
		if (!movie) {
			getData();
		}
	}, [getData, movie]);

	useEffect(() => {
		if (movie) {
			setOpenMovie({
				...movie,
				releaseDate: new Date(movie.releaseDate),
			});
		}
	}, [movie]);

	// console.log('Calling movies api');
	const {
		data: discussions,
		isLoading: isLoadingMovie,
		errors: errorsMovie,
		getData: getDataMovie,
	} = useQuery<Discussion[]>({
		url: ENDPOINTS.DISCUSSIONS.GET_ALL_DISCUSSIONS_BY_MOVIE_ID(openMovie?.id ? openMovie.id : 0),
		httpMethod: HTTP_METHODS.GET,
	});

	useEffect(() => {
		if (!discussions) {
			getDataMovie();
		}
	}, []);

	useEffect(() => {
		if (discussions) {
			setDiscussionList(discussions);
		}
	}, [discussions]);

	const {
		data: posterBytes,
		isLoading: isLoadingPoster,
		errors: errorsPoster,
		getData: getDataPoster,
	} = useQuery<Poster>({
		url: ENDPOINTS.POSTER.GET_POSTER(openMovie?.posterId! ?? 0),
		httpMethod: HTTP_METHODS.GET,
	});

	useEffect(() => {
		if (!posterBytes) {
			getDataPoster();
		}
	}, [openMovie?.posterId, getDataPoster]);

	useEffect(() => {
		if (posterBytes) {
			let base64String = posterBytes?.poster
				? `data:image/jpeg;base64,${posterBytes.poster}`
				: 'path/to/default/poster.jpg';
			setImageUrl(base64String);
		}
	}, [posterBytes]);

	const handleOpenDialog = () => {
		setIsDialogOpen(true);
	};

	const handleCloseDialog = () => {
		setIsDialogOpen(false);
	};
	const onEditSuccess = () => {
		// getDataMovie();
		// getDataPoster();
	};

	const updateMovieCommand = useQuery({
		url: ENDPOINTS.MOVIES.UPDATE_MOVIE,
		httpMethod: HTTP_METHODS.PUT,
		// onSuccess: onEditSuccess,
	});

	const onEditSubmit = async (newMovie: Movie, poster: File) => {
		try {
			let posterId: number | null = newMovie.posterId ? newMovie.posterId : null;

			console.log('EDITING MOVIE WITH POSTER NOW!');
			if (poster) {
				console.log('UPDATING POSTER NOW!');
				const formData = new FormData();
				formData.append('file', poster);
				console.log('body:', formData);
				let posterResponse: Response;

				if (newMovie.posterId) {
					console.log(' ID YRA', newMovie?.posterId);
					posterResponse = await fetch(ENDPOINTS.POSTER.UPDATE_POSTER(newMovie.posterId), {
						method: HTTP_METHODS.PUT,
						body: formData,
					});

					if (!posterResponse.ok) {
						throw new Error('Failed to upload poster');
					}
				} else {
					posterResponse = await fetch(ENDPOINTS.POSTER.CREATE_POSTER, {
						method: HTTP_METHODS.POST,
						body: formData,
					});

					if (!posterResponse.ok) {
						throw new Error('Failed to upload poster');
					}
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
				...(posterId ? { posterId } : {}),
			};
			console.log('EDITED MOVIE:', movieWithPoster);
			const movieResponse = await updateMovieCommand.sendData(movieWithPoster);

			// if (!movieResponse?.data.id) {
			// 	throw new Error('Failed to create movie');
			// }

			// onCreateMovieSuccess(movieResponse.data);
		} catch (error) {
			setListOfErrors([String((error as Error).message)]);
		}
	};

	const handleDelete = async () => {};

	if (isLoadingMovie) return <Loader />;
	if (errorsMovie) return <div>{errorsMovie.join(', ')}</div>;
	if (isLoading) return <Loader />;
	if (errors) return <div>{errors.join(', ')}</div>;

	return (
		<Container className='MovieDetailContainer' maxWidth={false}>
			<Button
				className='add-movie-button'
				onClick={handleOpenDialog}
				variant='text'
				sx={{ color: '#dddbcb', backgroundColor: '#008080 !important' }}
				// endIcon={<AddIcon />}
			>
				Edit Movie
			</Button>
			{/* <Button
				className='delete-movie-button'
				onClick={handleDelete}
				variant='text'
				sx={{ color: '#dddbcb', backgroundColor: '#ff0000 !important' }}
			>
				Delete Movie
			</Button> */}
			<h1 style={{ color: '#dddbcb', textAlign: 'center', fontSize: '2.5em', marginBottom: '20px' }}>
				{openMovie?.title}
			</h1>
			{isDialogOpen && (
				<MovieFormDialogBox
					movie={openMovie!}
					onClose={handleCloseDialog}
					onSubmit={onEditSubmit}
					open={isDialogOpen}
				/>
			)}
			<div className='MovieDetail'>
				{openMovie?.posterId && (
					<div className='MoviePosterContainer'>
						<img src={imageUrl} alt={`${openMovie.title} poster`} className='MoviePosterDetails' />
					</div>
				)}
				<div className='MovieInfo'>
					<p>
						<strong>Director:</strong> {openMovie?.director}
					</p>
					<p>
						<strong>Genre:</strong> {openMovie?.genre}
					</p>
					<p>
						<strong>Rating:</strong> {openMovie?.rating}
					</p>
					<p>
						<strong>Release Date:</strong> {new Date(openMovie?.releaseDate!).toDateString()}
					</p>
					<p>{openMovie?.description}</p>
				</div>
			</div>
			<List component='nav' aria-label='discussions'>
				{!discussionList || discussionList.length === 0 ? (
					<div>No discussions yet</div>
				) : (
					<>
						<div>Most recent discussions</div>
						{discussionList.map((discussion) => (
							<DiscussionListItem key={discussion.id} discussion={discussion} movie={openMovie!} />
						))}
					</>
				)}
			</List>
		</Container>
	);
};

export default MovieDetail;
