import React from 'react';
import { useNavigate } from 'react-router-dom';
import Discussion from '../../interfaces/Discussion';
import ROUTE_PATHS from '../../constants/routePaths';
import Movie from '../../interfaces/Movie';
import { Box, Divider, List, ListItem, ListItemText, Typography } from '@mui/material';
import './DiscussionListItem.css';

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
		<Box onClick={handleItemClick} className='DiscussionListItem'>
			<Box className='left-side'>
				<Typography variant='body1'>{`User ID: ${discussion.userId}`}</Typography>
				<Divider orientation='vertical' flexItem className='separator' style={{ height: '24px' }} />
				<Typography variant='body1'>{`Title: ${discussion.title}`}</Typography>
			</Box>
			<Box className='right-side'>
				<Divider orientation='vertical' flexItem className='separator' style={{ height: '24px' }} />
				<Typography variant='body1'>{`Comments: 1`}</Typography>
				<Divider orientation='vertical' flexItem className='separator' style={{ height: '24px' }} />
				<Typography variant='body1'>{`Date: 2000-01-01`}</Typography>
			</Box>
		</Box>
	);
};

export default DiscussionListItem;
