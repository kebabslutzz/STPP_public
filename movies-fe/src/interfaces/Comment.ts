interface Comment {
	id?: number;
	content: string;
	userId?: number;
	username?: string;
	discussionId?: number;
	dateModified?: string;
}

export default Comment;
