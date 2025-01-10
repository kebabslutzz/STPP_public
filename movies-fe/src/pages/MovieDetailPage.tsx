import React from 'react';
import { useParams } from 'react-router-dom';
import MovieDetail from '../components/movies/MovieDetail';

const MovieDetailPage: React.FC = () => {
	const { id } = useParams<{ id: string }>();

	const viewportWidth = window.innerWidth;
	console.log(viewportWidth);

	return (
		// <div className='PageContainer'>
		<MovieDetail movieId={Number(id)} />
		// </div>
	);
};

export default MovieDetailPage;
