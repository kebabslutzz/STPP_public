import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import Discussion from '../../interfaces/Discussion';
import ROUTE_PATHS from '../../constants/routePaths';
import Movie from '../../interfaces/Movie';
import { Box, Typography, Grid } from '@mui/material';
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
		<Box onClick={handleItemClick} className='DiscussionListItem' sx={{ flexGrow: 1 }}>
			<Grid container spacing={2}>
				<Grid item xs={8} md={10}>
					<Box className='content-box'>
						<Typography variant='body1'>{discussion.title}</Typography>
					</Box>
				</Grid>
				<Grid item xs={2} md={1}>
					<Box className='comments-box'>
						<Typography variant='body1'>{discussion.commentCount} comments</Typography>
					</Box>
				</Grid>
				<Grid item xs={2} md={1}>
					<Box className='user-date-box'>
						<Typography variant='body2'>
							by{' '}
							<Link to={`/users/${discussion.userId}`} onClick={handleUserLinkClick}>
								{discussion.username}
							</Link>
						</Typography>
						<Typography variant='body2'>
							{discussion.dateCreated ? formatDate(discussion.dateCreated) : 'N/A'}
						</Typography>
					</Box>
				</Grid>
			</Grid>
		</Box>
	);
};

export default DiscussionListItem;
