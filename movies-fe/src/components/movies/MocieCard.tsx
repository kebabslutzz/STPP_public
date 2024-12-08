import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './MovieCard.css';
import ROUTE_PATHS from '../../constants/routePaths';
import useQuery from '../../hooks/useQuery';
import { ENDPOINTS } from '../../constants/endpoints';
import { HTTP_METHODS } from '../../constants/httpsMethods';
import Poster from '../../interfaces/Poster';
import HideImageIcon from '@mui/icons-material/HideImage';

interface MovieCardProps {
	id: number;
	title: string;
	description: string;
	posterId: number | undefined;
}

const MovieCard: React.FC<MovieCardProps> = ({ id, title, description, posterId }) => {
	const [poster, setPoster] = React.useState<Poster | null>(null);
	const [isFlipped, setIsFlipped] = useState(false);
	const navigate = useNavigate();

	const { data: posterBytes, getData: getPosterData } = useQuery<Poster>({
		url: ENDPOINTS.POSTER.GET_POSTER(posterId ? posterId : 0),
		httpMethod: HTTP_METHODS.GET,
	});

	useEffect(() => {
		if (posterId && posterId !== 0) {
			getPosterData();
		}
	}, [posterId]);

	useEffect(() => {
		if (posterBytes) {
			setPoster(posterBytes);
		}
	}, [posterBytes]);

	const handleCardClick = (e: React.MouseEvent | React.TouchEvent) => {
		const isTouchEvent = 'touches' in e;
		if (isTouchEvent) {
			e.preventDefault();
			setIsFlipped(!isFlipped);
		} else {
			navigate(`${ROUTE_PATHS.HOME}/${id}${ROUTE_PATHS.DISCUSSIONS}`);
		}
	};

	const base64String = poster?.poster ? `data:image/jpeg;base64,${poster.poster}` : null;

	return (
		<div className='MovieCard' onClick={handleCardClick} onTouchStart={handleCardClick}>
			<div className='MovieCardInner'>
				{base64String ? (
					<div className='MovieCardFront' style={{ backgroundImage: `url(${base64String})` }} />
				) : (
					<div className='MovieCardFront NoImageText'>
						<span>
							Movie Poster Not Available
							<HideImageIcon />
						</span>
					</div>
				)}
				<div className='MovieCardBack'>
					<p>{description}</p>
				</div>
			</div>
			<h2 className='MovieCardTitle'>{title}</h2>
		</div>
	);
};

export default MovieCard;
