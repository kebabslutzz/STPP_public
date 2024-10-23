interface User {
	id?: number;
	username?: string;
	email?: string;
	role?: string;
	status?: string;
	dateCreated?: Date;
	dateModified?: Date;
	password?: string;
	token?: string;
}

export default User;
