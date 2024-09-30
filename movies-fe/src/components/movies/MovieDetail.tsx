import React, { useEffect, useState } from 'react';
import Movie from '../../interfaces/Movie';
import Loader from '../shared/Loader';
import './MovieDetail.css';
import Discussion from '../../interfaces/Discussion';
import useQuery from '../../hooks/useQuery';
import { ENDPOINTS } from '../../constants/endpoints';
import { HTTP_METHODS } from '../../constants/httpsMethods';
import { List } from '@mui/material';
import DiscussionListItem from '../discussions/DisccusionListItem';
// import DiscussionList from '../discussions/DiscussionList';

interface MovieDetailProps {
	movie: Movie;
}

const MovieDetail: React.FC<MovieDetailProps> = ({ movie }) => {
	const [discussionList, setDiscussionList] = useState<Discussion[]>([]);
	const [imageUrl, setImageUrl] = useState<string | undefined>(undefined);

	// console.log('Calling movies api');
	const {
		data: discussions,
		isLoading: isLoadingMovie,
		errors: errorsMovie,
		getData: getDataMovie,
	} = useQuery<Discussion[]>({
		url: ENDPOINTS.DISCUSSIONS.GET_ALL_DISCUSSIONS_BY_MOVIE_ID(movie.id ? movie.id : 0),
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
	} = useQuery<string>({
		url: ENDPOINTS.POSTER.GET_POSTER(movie.posterId ? movie.posterId : 0),
		httpMethod: HTTP_METHODS.GET,
	});

	useEffect(() => {
		if (!posterBytes) {
			getDataPoster();
		}
	}, []);

	useEffect(() => {
		if (posterBytes) {
			setImageUrl(`data:image/jpeg;base64,${posterBytes}`);
		}
	}, [posterBytes]);

	if (isLoadingMovie) return <Loader />;
	if (errorsMovie) return <div>{errorsMovie.join(', ')}</div>;

	return (
		<div>
			<div className='MovieDetail'>
				{movie.posterId && (
					<div className='MoviePosterContainer'>
						<img src={imageUrl} alt={`${movie.title} poster`} className='MoviePosterDetails' />
					</div>
				)}
				<div className='MovieInfo'>
					<h1>{movie.title}</h1>
					<p>{movie.description}</p>
					<p>
						<strong>Director:</strong> {movie.director}
					</p>
					<p>
						<strong>Genre:</strong> {movie.genre}
					</p>
					<p>
						<strong>Rating:</strong> {movie.rating}
					</p>
					<p>
						<strong>Release Date:</strong> {movie.releaseDate.toDateString()}
					</p>
				</div>
			</div>
			<List component='nav' aria-label='discussions'>
				{!discussionList || discussionList.length === 0 ? (
					<div>No discussions yet</div>
				) : (
					discussionList.map((discussion) => (
						<DiscussionListItem key={discussion.id} discussion={discussion} movie={movie} />
					))
				)}
			</List>
		</div>
	);
};

export default MovieDetail;
