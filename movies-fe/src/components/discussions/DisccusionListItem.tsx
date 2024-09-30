import React from 'react';
import { useNavigate } from 'react-router-dom';
import Discussion from '../../interfaces/Discussion';
import ROUTE_PATHS from '../../constants/routePaths';
import Movie from '../../interfaces/Movie';

interface DiscussionListItemProps {
	discussion: Discussion;
	movie: Movie;
}

const DiscussionListItem: React.FC<DiscussionListItemProps> = ({ discussion, movie }) => {
	const navigate = useNavigate();

	const handleItemClick = () => {
		navigate(`${ROUTE_PATHS.HOME}/${movie.id}${ROUTE_PATHS.DISCUSSIONS}/${discussion.id}${ROUTE_PATHS.COMMENTS}`);
	};

	return (
		<div onClick={handleItemClick} className='DiscussionListItem'>
			<h3>{discussion.title}</h3>
		</div>
	);
};

export default DiscussionListItem;
