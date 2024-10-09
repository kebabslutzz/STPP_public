import React from 'react';
import { Box, Button, Divider, Typography, Grid } from '@mui/material';
import './CommentListItem.css';
import Comment from '../../interfaces/Comment';
import DeleteConfirmationDialog from '../dialog/DeleteConfirmationDialog';

interface CommentListItemProps {
	comment: Comment;
	onDelete: (comment: Comment) => void;
	onUpdate: (comment: Comment) => void;
}

const CommentListItem: React.FC<CommentListItemProps> = ({ comment, onDelete, onUpdate }) => {
	const [deleteDialogOpen, setDeleteDialogOpen] = React.useState(false);

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

	return (
		<Box className='CommentListItem' sx={{ flexGrow: 1 }}>
			<Grid container spacing={1}>
				<Grid item xs={12} className='top-side'>
					{/* {`${formatDate(comment?.dateModified!) ?? 'N/A'}`} */}
					{`${comment.dateModified ? formatDate(comment.dateModified) : 'N/A'}`}
				</Grid>
				<Grid item xs={2} className='grid-item'>
					<Typography variant='body1' className='user-box'>{`User ID: ${comment.userId}`}</Typography>
				</Grid>
				<Grid item xs={10}>
					<Typography variant='body1' className='comment-content-box'>{`${comment.content}`}</Typography>
					<Box className='button-container'>
						<Button onClick={handleDeleteDialogOpen} className='Button delete-comment-button'>
							Delete
						</Button>
						<Button onClick={handleEditComment} className='Button edit-comment-button'>
							Edit
						</Button>
					</Box>
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
