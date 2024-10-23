import axios, { AxiosError, AxiosRequestConfig, AxiosResponse } from 'axios';

export type ErrorResponse = {
	status: number;
	message: string;
	exception: AxiosError;
};

export type Query = {
	[key: string]: string | number | boolean | any;
};

interface ErrorResponseData {
	message: string;
}

type RequestParams = {
	url: string;
	httpMethod: string;
	queryParams?: Query;
	body?: any;
	token?: string;
};

export type ApiResponse<T> = {
	data: T;
	status: number;
};

const getErrorMessages = (error: AxiosError<ErrorResponseData>): string => {
	if (!error.response) {
		return 'Network Error';
	}

	// Extract the error message from the server response
	const serverMessage = error.response.data?.message || error.message;
	return serverMessage;
	// return error.message;
};

const createErrorResponse = (error: AxiosError<ErrorResponseData>): ErrorResponse => {
	return {
		status: error.response?.status || 500,
		message: getErrorMessages(error),
		exception: error,
	};
};

const makeRequestAsync = async <T>({
	url,
	httpMethod,
	queryParams,
	body,
	token,
}: RequestParams): Promise<ApiResponse<T> | ErrorResponse> => {
	const request: AxiosRequestConfig = {
		url,
		method: httpMethod,
		params: queryParams,
		data: body,
		headers: {
			'Content-Type': 'application/json',
			...(token && { Authorization: `Bearer ${token}` }), // Conditionally add the token to headers
		},
	};

	try {
		const response: AxiosResponse<T> = await axios(request);
		return {
			data: response.data,
			status: response.status,
		};
	} catch (error) {
		return createErrorResponse(error as AxiosError<ErrorResponseData>);
	}
};

const apiService = {
	createErrorResponse,
	makeRequestAsync,
};

export default apiService;
