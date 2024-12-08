import { CrisisAlert } from '@mui/icons-material';

const BASE_URL = 'http://localhost:8080/api/v1';
// const BASE_URL = 'http://13.48.86.147:8080/api/v1';

export const ENDPOINTS = {
	USERS: {
		GET_ALL_USERS: `${BASE_URL}/users`,
		GET_USER_BY_ID: (id: number) => `${BASE_URL}/users/${id}`,
		CREATE_USER: `${BASE_URL}/users`,
		REGISTER: `${BASE_URL}/users/signup`,
		// CHANGE THIS
		LOGIN: `${BASE_URL}/users/login`,
		DELETE_USER: (id: number) => `${BASE_URL}/users/${id}`,
		UPDATE_USER: (id: number) => `${BASE_URL}/users/${id}`,
		UPDATE_USER_ROLE: (id: number) => `${BASE_URL}/users/${id}/role`,
	},
	MOVIES: {
		GET_ALL_MOVIES: `${BASE_URL}/movies`,
		GET_MOVIE_BY_ID: (id: number) => `${BASE_URL}/movies/${id}`,
		CREATE_MOVIE: `${BASE_URL}/movies`,
		DELETE_MOVIE: (id: number) => `${BASE_URL}/movies/${id}`,
		UPDATE_MOVIE: (id: number) => `${BASE_URL}/movies/${id}`,
		ADD_POSTER: (id: number) => `${BASE_URL}/movies/${id}/poster`,
		CREATE_DISCUSSION: (id: number) => `${BASE_URL}/movies/${id}/discussions`,
		EDIT_DISCUSSION: (movieId: number, discussionId: number) =>
			`${BASE_URL}/movies/${movieId}/discussions/${discussionId}`,
		GET_DISCUSSION_BY_ID: (movieId: number, discussionId: number) =>
			`${BASE_URL}/movies/${movieId}/discussions/${discussionId}`,
		CREATE_COMMENT: (movieId: number, discussionId: number) =>
			`${BASE_URL}/movies/${movieId}/discussions/${discussionId}/comments`,
		DELETE_COMMENT: (movieId: number, discussionId: number, commentId: number) =>
			`${BASE_URL}/movies/${movieId}/discussions/${discussionId}/comments/${commentId}`,
		DELETE_DISCUSSION: (movieId: number, discussionId: number) =>
			`${BASE_URL}/movies/${movieId}/discussions/${discussionId}`,
		UPDATE_COMMENT: (movieId: number, discussionId: number, commentId: number) =>
			`${BASE_URL}/movies/${movieId}/discussions/${discussionId}/comments/${commentId}`,
	},
	DISCUSSIONS: {
		GET_ALL_DISCUSSIONS_BY_MOVIE_ID: (id: number) => `${BASE_URL}/movies/${id}/discussions`,
	},
	COMMENTS: {
		GET_ALL_COMMENTS_BY_DISCUSSION_ID: (movieId: number, id: number) =>
			`${BASE_URL}/movies/${movieId}/discussions/${id}/comments`,
	},
	POSTER: {
		GET_POSTER: (id: number) => `${BASE_URL}/files/${id}`,
		CREATE_POSTER: `${BASE_URL}/files`,
		UPDATE_POSTER: (posterId: number) => `${BASE_URL}/files/${posterId}`,
	},
};
