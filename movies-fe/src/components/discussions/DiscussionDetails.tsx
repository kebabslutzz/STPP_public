import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import useQuery from '../../hooks/useQuery';
import Discussion from '../../interfaces/Discussion';
import Comment from '../../interfaces/Comment';
import { ENDPOINTS } from '../../constants/endpoints';
import { HTTP_METHODS } from '../../constants/httpsMethods';
import Loader from '../shared/Loader';

interface DiscussionDetailProps {
	discussion?: Discussion;
}

const DiscussionDetail: React.FC<DiscussionDetailProps> = ({ discussion }) => {
	const { discussionId } = useParams<{ discussionId: string }>();
	const { movieId } = useParams<{ movieId: string }>();
	const [comments, setComments] = useState<Comment[]>([]);

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

	if (isCommentsLoading) return <Loader />;
	if (commentsErrors) return <div>{commentsErrors.join(', ')}</div>;

	return (
		<div className='DiscussionDetail'>
			{discussion && <h2>{discussion.title}</h2>}
			<ul>
				{comments.map((comment) => (
					<li key={comment.id}>{comment.content}</li>
				))}
			</ul>
		</div>
	);
};

export default DiscussionDetail;
