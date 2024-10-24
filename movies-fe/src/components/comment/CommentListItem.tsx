import React from 'react';
import { Box, Button, Divider, Typography, Grid } from '@mui/material';
import './CommentListItem.css';
import Comment from '../../interfaces/Comment';
import DeleteConfirmationDialog from '../dialog/DeleteConfirmationDialog';
import DeleteIcon from '@mui/icons-material/Delete';
import EditIcon from '@mui/icons-material/Edit';
import { useAuth } from '../../context/AuthContext';
import { Link } from 'react-router-dom';

interface CommentListItemProps {
	comment: Comment;
	onDelete: (comment: Comment) => void;
	onUpdate: (comment: Comment) => void;
}

const CommentListItem: React.FC<CommentListItemProps> = ({ comment, onDelete, onUpdate }) => {
	const [deleteDialogOpen, setDeleteDialogOpen] = React.useState(false);
	const { isAdmin, loggedInUserId } = useAuth();

	const formatDate = (date: Date | string) => {
		const dateObj = new Date(date);
		const options: Intl.DateTimeFormatOptions = {
			weekday: 'short',
			month: 'short',
			year: 'numeric',
			hour: '2-digit',
			minute: '2-digit',
			hour12: false,
		};
		return new Intl.DateTimeFormat('en-US', options).format(dateObj);
	};

	const handleEditComment = () => {
		onUpdate(comment);
	};

	const handleDeleteDialogOpen = () => {
		setDeleteDialogOpen(true);
	};
	const handleDeleteDialogClose = () => {
		setDeleteDialogOpen(false);
	};

	const handleDeleteComment = () => {
		console.log('comment.id', comment.id);
		if (comment.id) {
			onDelete(comment);
			handleDeleteDialogClose();
		}
	};

	const handleUserLinkClick = (event: React.MouseEvent<HTMLAnchorElement, MouseEvent>) => {
		event.stopPropagation();
	};

	return (
		<Box className='CommentListItem' sx={{ flexGrow: 1 }}>
			<Grid container spacing={1}>
				<Grid item xs={12} className='top-side'>
					{/* {`${formatDate(comment?.dateModified!) ?? 'N/A'}`} */}
					{`${comment.dateModified ? formatDate(comment.dateModified) : 'N/A'}`}
				</Grid>
				<Grid item xs={2} className='grid-item'>
					<Typography variant='body1' className='user-box'>
						by{' '}
						<Link to={`/users/${comment.userId}`} onClick={handleUserLinkClick}>
							{comment.username}
						</Link>
					</Typography>
				</Grid>
				<Grid item xs={10}>
					<Typography variant='body1' className='comment-content-box'>{`${comment.content}`}</Typography>
					{(isAdmin || loggedInUserId === comment.userId) && (
						<Box className='button-container'>
							<Button onClick={handleDeleteDialogOpen} className='Button delete-button' endIcon={<DeleteIcon />}>
								Delete
							</Button>
							{loggedInUserId === comment.userId && (
								<Button onClick={handleEditComment} className='Button add-edit-button' endIcon={<EditIcon />}>
									Edit
								</Button>
							)}
						</Box>
					)}
				</Grid>
			</Grid>
			<DeleteConfirmationDialog
				open={deleteDialogOpen}
				onClose={handleDeleteDialogClose}
				onConfirm={handleDeleteComment}
				text='Are you sure you want to delete this comment?'
			/>
		</Box>
	);
};

export default CommentListItem;
