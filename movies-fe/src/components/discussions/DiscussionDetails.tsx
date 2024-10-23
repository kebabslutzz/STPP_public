import React, { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import useQuery from '../../hooks/useQuery';
import Discussion from '../../interfaces/Discussion';
import Comment from '../../interfaces/Comment';
import { ENDPOINTS } from '../../constants/endpoints';
import { HTTP_METHODS } from '../../constants/httpsMethods';
import Loader from '../shared/Loader';
import { Button, Container, List } from '@mui/material';
import DiscussionFormDialogBox from './DiscussionFormDialogBox';
import CommentBox from '../comment/CommentBox';
import DeleteConfirmationDialog from '../dialog/DeleteConfirmationDialog';
import CommentListItem from '../comment/CommentListItem';
import EditIcon from '@mui/icons-material/Edit';
import DeleteIcon from '@mui/icons-material/Delete';
import AddCommentIcon from '@mui/icons-material/AddComment';

const DiscussionDetail: React.FC = () => {
	const [comments, setComments] = useState<Comment[]>([]);
	const [isDiscussionDialogOpen, setIsDiscussionDialogOpen] = useState(false);
	const [discussion, setDiscussion] = useState<Discussion | null>(null);
	const [isCommentBoxVisible, setCommentBoxIsVisible] = useState<boolean>(false);
	const [isDeleteDialogOpen, setIsDeleteDialogOpen] = useState(false);
	const [selectedCommentId, setSelectedCommentId] = useState<number | null>(null);
	const [commentToUpdate, setCommentToUpdate] = useState<Comment | null>(null);
	const { movieId, discussionId } = useParams<{ movieId: string; discussionId: string }>();
	const navigate = useNavigate();

	const {
		data: fetchedDiscussion,
		isLoading: isDiscussionLoading,
		errors: discussionErrors,
		getData: getDiscussionData,
	} = useQuery<Discussion>({
		url: ENDPOINTS.MOVIES.GET_DISCUSSION_BY_ID(Number(movieId), Number(discussionId)),
		httpMethod: HTTP_METHODS.GET,
	});

	useEffect(() => {
		if (!fetchedDiscussion) {
			getDiscussionData();
		}
	}, [fetchedDiscussion, getDiscussionData]);

	useEffect(() => {
		if (fetchedDiscussion) {
			setDiscussion(fetchedDiscussion);
		}
	}, [fetchedDiscussion]);

	const {
		data: fetchedComments,
		isLoading: isCommentsLoading,
		errors: commentsErrors,
		getData: getCommentsData,
	} = useQuery<Comment[]>({
		url: ENDPOINTS.COMMENTS.GET_ALL_COMMENTS_BY_DISCUSSION_ID(Number(movieId), Number(discussionId)),
		httpMethod: HTTP_METHODS.GET,
	});

	useEffect(() => {
		if (!fetchedComments) {
			getCommentsData();
		}
	}, [fetchedComments, getCommentsData]);

	useEffect(() => {
		if (fetchedComments) {
			setComments(fetchedComments);
		}
	}, [fetchedComments]);

	const handleOpenDiscussionDialog = () => {
		setIsDiscussionDialogOpen(true);
	};

	const handleCloseDiscussionDialog = () => {
		setIsDiscussionDialogOpen(false);
	};

	const handleNewReplyClick = () => {
		setCommentBoxIsVisible(true);
	};

	const handleCancelReply = () => {
		setCommentToUpdate(null);
		setCommentBoxIsVisible(false);
	};

	const handleOpenDeleteDialog = () => {
		setIsDeleteDialogOpen(true);
	};

	const handleCloseDeleteDialog = () => {
		setIsDeleteDialogOpen(false);
	};

	const editDiscussionCommand = useQuery({
		url: ENDPOINTS.MOVIES.EDIT_DISCUSSION(Number(movieId), Number(discussionId)),
		httpMethod: HTTP_METHODS.PATCH,
	});

	const handleDiscussionSubmit = async (newDiscussion: Discussion) => {
		newDiscussion.userId = 1; // Hardcoded user id for now
		const discussionResponse = await editDiscussionCommand.sendData(newDiscussion);
		if (discussionResponse?.status === 200) {
			setDiscussion(newDiscussion);
		}
	};

	const createCommentCommand = useQuery({
		url: ENDPOINTS.MOVIES.CREATE_COMMENT(Number(movieId), Number(discussionId)),
		httpMethod: HTTP_METHODS.POST,
	});

	const handleAddComment = async (newComment: Comment) => {
		if (commentToUpdate == null) {
			newComment.userId = 1; // Hardcoded user id for now
			setCommentBoxIsVisible(false);
			// const updatedComments = [...comments, newComment];
			// setComments(updatedComments);
			const commentResponse = await createCommentCommand.sendData(newComment);
			if (commentResponse?.status === 201 && 'data' in commentResponse) {
				let createdComment = commentResponse?.data as Comment;
				setComments((prev) => [createdComment, ...prev]);
				// getCommentsData();
			}
		} else {
			commentToUpdate.content = newComment.content;
			console.log('Update comment');
			console.log('comment:', commentToUpdate);
			setCommentBoxIsVisible(false);
			const commentUpdateResponse = await updateCommentCommand.sendData(commentToUpdate);
			setCommentToUpdate(null);
			if (commentUpdateResponse?.status === 200 && 'data' in commentUpdateResponse) {
				let updatedComment = commentUpdateResponse?.data as Comment;
				setComments((prev) => prev.map((comment) => (comment.id === updatedComment.id ? updatedComment : comment)));
			}
		}
	};

	const {
		isLoading: isDeletingDiscussion,
		errors: deleteErrorsDiscussion,
		sendData: deleteDiscussion,
	} = useQuery<void>({
		url: ENDPOINTS.MOVIES.DELETE_DISCUSSION(Number(movieId), Number(discussionId)),
		httpMethod: HTTP_METHODS.DELETE,
		onSuccess: () => {
			navigate(`/movies/${movieId}/discussions`);
		},
	});

	const handleDeleteDiscussion = async () => {
		console.log('Delete discussion');
		await deleteDiscussion();
	};
	const {
		isLoading: isDeletingComment,
		errors: deleteCommentErrors,
		sendData: deleteComment,
	} = useQuery<void>({
		url: ENDPOINTS.MOVIES.DELETE_COMMENT(
			Number(movieId),
			Number(discussionId),
			selectedCommentId ? selectedCommentId : 0
		),
		httpMethod: HTTP_METHODS.DELETE,
		onSuccess: () => {
			getCommentsData();
		},
	});

	useEffect(() => {
		if (selectedCommentId !== null) {
			deleteComment();
		}
	}, [selectedCommentId]);

	const handleCommentDelete = async (comment: Comment) => {
		console.log('Delete comment');
		console.log('comment:', comment);
		setSelectedCommentId(comment.id ?? null);
	};

	const updateCommentCommand = useQuery({
		url: ENDPOINTS.MOVIES.UPDATE_COMMENT(Number(movieId), Number(discussionId), commentToUpdate?.id ?? 0),
		httpMethod: HTTP_METHODS.PATCH,
	});

	const handleCommentUpdate = async (comment: Comment) => {
		setCommentToUpdate(comment!);
		setCommentBoxIsVisible(true);
	};

	if (isCommentsLoading) return <Loader />;
	if (commentsErrors) return <div>{commentsErrors.join(', ')}</div>;

	return (
		<Container className='PageContainer'>
			<div className='DiscussionDetail'>
				<div className='discussion-header'>{discussion && <h1>{discussion.title}</h1>}</div>
				<div className='discussion-buttons'>
					<Button onClick={handleOpenDiscussionDialog} className='Button add-edit-button' endIcon={<EditIcon />}>
						Edit Discussion
					</Button>
					<Button onClick={handleOpenDeleteDialog} className='Button delete-button' endIcon={<DeleteIcon />}>
						Delete Discussion
					</Button>
					<Button onClick={handleNewReplyClick} className='Button add-edit-button' endIcon={<AddCommentIcon />}>
						New Reply
					</Button>
				</div>
				<List component='nav' aria-label='discussions'>
					{comments && comments.length > 0 && (
						<>
							{comments.map((comment) => (
								<CommentListItem
									key={comment.id}
									comment={comment!}
									onDelete={handleCommentDelete}
									onUpdate={handleCommentUpdate}
								/>
							))}
						</>
					)}
				</List>
				<DiscussionFormDialogBox
					open={isDiscussionDialogOpen}
					onClose={handleCloseDiscussionDialog}
					onSubmit={handleDiscussionSubmit}
					discussion={discussion!}
					title='Create Discussion'
				/>
			</div>
			{isCommentBoxVisible && (
				<CommentBox
					comment={commentToUpdate!}
					onSubmit={handleAddComment}
					visible={isCommentBoxVisible}
					onCancel={handleCancelReply}
				/>
			)}{' '}
			<DeleteConfirmationDialog
				open={isDeleteDialogOpen}
				onClose={handleCloseDeleteDialog}
				onConfirm={handleDeleteDiscussion}
				text='Are you sure you want to delete this discussion?'
			/>
		</Container>
	);
};

export default DiscussionDetail;
