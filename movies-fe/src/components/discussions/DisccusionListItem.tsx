import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import Discussion from '../../interfaces/Discussion';
import ROUTE_PATHS from '../../constants/routePaths';
import Movie from '../../interfaces/Movie';
import { Box, Typography, Grid, Container, Divider } from '@mui/material';
import './DiscussionListItem.css';

interface DiscussionListItemProps {
	discussion: Discussion;
	movie: Movie;
}

const DiscussionListItem: React.FC<DiscussionListItemProps> = ({ discussion, movie }) => {
	const navigate = useNavigate();

	const handleUserLinkClick = (event: React.MouseEvent<HTMLAnchorElement, MouseEvent>) => {
		event.stopPropagation();
	};

	const handleItemClick = () => {
		navigate(`${ROUTE_PATHS.HOME}/${movie.id}${ROUTE_PATHS.DISCUSSIONS}/${discussion.id}${ROUTE_PATHS.COMMENTS}`);
	};

	const formatDate = (date: Date | string) => {
		const dateObj = new Date(date);
		return dateObj.toISOString().split('T')[0];
	};

	return (
		<Box onClick={handleItemClick} className='DiscussionListItemContainer'>
			<Container className='DiscussionListItemTitle'>{discussion.title}</Container>
			<Divider className='first-divider' flexItem orientation='vertical' sx={{ my: 1, backgroundColor: 'white' }} />
			<Box className='DiscussionListItemUserComments'>
				<Container className='DiscussionListItemComments'>{discussion.commentCount} comments</Container>
				<Divider orientation='vertical' flexItem sx={{ my: 1, backgroundColor: 'white' }} />
				<Container className='DiscussionListItemUserDate'>
					by{' '}
					<Link to={`/users/${discussion.userId}`} onClick={handleUserLinkClick}>
						{discussion.username}
					</Link>
					<br />
					{discussion.dateCreated ? formatDate(discussion.dateCreated) : 'N/A'}
				</Container>
			</Box>
		</Box>
	);
};

export default DiscussionListItem;
