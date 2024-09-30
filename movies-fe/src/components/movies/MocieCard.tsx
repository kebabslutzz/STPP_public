import React, { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import './MovieCard.css';
import ROUTE_PATHS from '../../constants/routePaths';
import useQuery from '../../hooks/useQuery';
import { ENDPOINTS } from '../../constants/endpoints';
import { HTTP_METHODS } from '../../constants/httpsMethods';

interface MovieCardProps {
	id: number;
	title: string;
	description: string;
	posterId: number | undefined;
}

const MovieCard: React.FC<MovieCardProps> = ({ id, title, description, posterId }) => {
	const [poster, setPoster] = React.useState<string | null>(null);
	const navigate = useNavigate();

	console.log('posterId:', posterId);

	const {
		data: posterBytes,
		isLoading,
		errors,
		getData,
	} = useQuery<string>({
		url: ENDPOINTS.POSTER.GET_POSTER(posterId ? posterId : 0),
		httpMethod: HTTP_METHODS.GET,
	});

	useEffect(() => {
		if (!posterBytes) {
			getData();
		}
	}, []);

	useEffect(() => {
		if (posterBytes) {
			setPoster(posterBytes);
		}
	}, [posterBytes]);

	const handleCardClick = () => {
		navigate(`${ROUTE_PATHS.HOME}/${id}${ROUTE_PATHS.DISCUSSIONS}`);
	};

	console.log('posterBytes:', posterBytes);

	const base64String = posterBytes ? `data:image/jpeg;base64,${posterBytes}` : 'path/to/default/poster.jpg';

	return (
		<div className='MovieCard' onClick={handleCardClick}>
			<div className='MovieCardInner'>
				<div className='MovieCardFront'>
					{isLoading ? (
						<p>Loading...</p>
					) : errors ? (
						<p>Error loading poster</p>
					) : (
						<img src={base64String} alt={`${title} poster`} className='MoviePoster' />
					)}
				</div>
				<div className='MovieCardBack'>
					<p>{description}</p>
				</div>
			</div>
			<h2 className='MovieTitle'>{title}</h2>
		</div>
	);
};

export default MovieCard;
