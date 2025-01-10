import { useState } from 'react';
import apiService, { ApiResponse, Query, ErrorResponse } from '../services/apiService';
import { HTTP_METHODS } from '../constants/httpsMethods';
import queryService from '../services/queryService';
import { useAuth } from '../context/AuthContext';

type UseQueryArguments<T> = {
	url: string;
	queryParams?: Query;
	httpMethod?: string;
	id?: number;
	mapper?: (data: any) => T;
	onSuccess?: (data: T) => void;
	getToken?: () => string | undefined;
};

export default function useQuery<T>({
	url,
	id,
	httpMethod,
	mapper = (data) => data as T,
	onSuccess = () => {},
}: UseQueryArguments<T>) {
	if (!url) {
		throw new Error('URL is required');
	}

	const [data, setData] = useState<T | null>(null);
	const [isLoading, setIsLoading] = useState(false);
	const [errors, setErrors] = useState<string[] | null>(null);
	const { getToken } = useAuth();

	const getData = async (params?: Query) => {
		setIsLoading(true);

		try {
			let requestUrl = url;

			const response = await apiService.makeRequestAsync<T>({
				url: requestUrl,
				httpMethod: HTTP_METHODS.GET,
				token: getToken(),
			});

			if ('message' in response) {
				setErrors([response.message]);
			} else {
				const mappedData = mapper(response.data);
				setData(mappedData);
				onSuccess(mappedData);
				return mappedData;
			}
		} catch (error) {
			setErrors([error as string]);
		} finally {
			setIsLoading(false);
		}
		return null;
	};

	const sendData = async (values?: Query) => {
		setIsLoading(true);
		const sanitizedValues = values ? queryService.sanitize(values) : undefined;
		try {
			const response = await apiService.makeRequestAsync<T>({
				url,
				queryParams: id ? { id } : undefined,
				body: sanitizedValues,
				httpMethod: httpMethod || HTTP_METHODS.POST,
				token: getToken(),
			});
			if ('message' in response) {
				setErrors([response.message]);
				return response;
			} else {
				onSuccess(response.data);
				return response;
			}
		} catch (error) {
			setErrors([error as string]);
		} finally {
			setIsLoading(false);
		}
	};

	return {
		data,
		isLoading,
		errors,
		setData,
		getData,
		sendData,
	};
}
