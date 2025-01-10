interface Discussion {
	id?: number;
	title: string;
	movieId?: number;
	userId?: number;
	username?: string;
	dateCreated?: Date;
	commentCount?: number;
}

export default Discussion;
