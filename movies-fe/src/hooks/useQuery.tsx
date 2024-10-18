import { useState } from 'react';
import apiService, { Query } from '../services/apiService';
import { HTTP_METHODS } from '../constants/httpsMethods';
import queryService from '../services/queryService';

type UseQueryArguments<T> = {
	url: string;
	queryParams?: Query;
	httpMethod?: string;
	id?: number;
	mapper?: (data: any) => T;
	onSuccess?: (data: T) => void;
	token?: string;
};

export default function useQuery<T>({
	url,
	id,
	httpMethod,
	mapper = (data) => data as T,
	onSuccess = () => {},
	token,
}: UseQueryArguments<T>) {
	if (!url) {
		throw new Error('URL is required');
	}
	// console.log('in api service');
	// console.log('url in query:', url, 'method', httpMethod);

	const [data, setData] = useState<T | null>(null);
	const [isLoading, setIsLoading] = useState(false);
	const [errors, setErrors] = useState<string[] | null>(null);
	// console.log('set some constants');

	const getData = async (params?: Query) => {
		// console.log('getData');
		setIsLoading(true);

		try {
			// console.log('try block');
			let requestUrl = url;

			// console.log('requestUrl:', requestUrl);
			const response = await apiService.makeRequestAsync<T>({
				url: requestUrl,
				httpMethod: HTTP_METHODS.GET,
				token,
			});

			if ('message' in response) {
				// console.log('response.message:', response.message);
				setErrors([response.message]);
			} else {
				const mappedData = mapper(response.data);
				setData(mappedData);
				onSuccess(mappedData);
				// console.log('mappedData:', mappedData);
				return mappedData;
			}
		} catch (error) {
			// console.log('query error:', error);
			setErrors([error as string]);
		} finally {
			// console.log('finally');
			setIsLoading(false);
		}
		// console.log('return null');
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
				token,
			});
			if ('message' in response) {
				setErrors([response.message]);
			} else {
				onSuccess(response.data);
				return response;
			}
		} catch (error) {
			setErrors([error as string]);
		} finally {
			setIsLoading(false);
		}
		return null;
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
