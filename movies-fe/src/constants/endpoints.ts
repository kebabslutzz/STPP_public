const BASE_URL = 'http://localhost:8080/api/v1';

export const ENDPOINTS = {
	USERS: {
		GET_ALL_USERS: `${BASE_URL}/users`,
		GET_USER_BY_ID: (id: number) => `${BASE_URL}/users/${id}`,
		CREATE_USER: `${BASE_URL}/users`,
		DELETE_USER: (id: number) => `${BASE_URL}/users/${id}`,
		PUT_USER: `${BASE_URL}/users/`,
	},
	MOVIES: {
		GET_ALL_MOVIES: `${BASE_URL}/movies`,
		GET_MOVIE_BY_ID: (id: number) => `${BASE_URL}/movies/${id}`,
		CREATE_MOVIE: `${BASE_URL}/movies`,
		DELETE_MOVIE: (id: number) => `${BASE_URL}/movies/${id}`,
		UPDATE_MOVIE: `${BASE_URL}/movies`,
		ADD_POSTER: (id: number) => `${BASE_URL}/movies/${id}/poster`,
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
