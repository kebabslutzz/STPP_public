import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import useQuery from '../hooks/useQuery';
import Movie from '../interfaces/Movie';
import { ENDPOINTS } from '../constants/endpoints';
import { HTTP_METHODS } from '../constants/httpsMethods';
import Loader from '../components/shared/Loader';
import MovieDetail from '../components/movies/MovieDetail';

const MovieDetailPage: React.FC = () => {
	const { id } = useParams<{ id: string }>();
	const [openMovie, setOpenMovie] = useState<Movie | null>(null);
	const {
		data: movie,
		isLoading,
		errors,
		getData,
	} = useQuery<Movie>({
		url: ENDPOINTS.MOVIES.GET_MOVIE_BY_ID(Number(id)),
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

	if (isLoading) return <Loader />;
	if (errors) return <div>{errors.join(', ')}</div>;

	return <div className='PageContainer'>{openMovie && <MovieDetail movie={openMovie} />}</div>;
};

export default MovieDetailPage;
