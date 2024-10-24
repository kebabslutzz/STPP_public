import React, { Suspense } from 'react';
import { createBrowserRouter, Navigate, RouterProvider } from 'react-router-dom';
import Layout from '../layout/Layout';
import ROUTE_PATHS from '../../constants/routePaths';
import Loader from '../shared/Loader';
import MovieDetailPage from '../../pages/MovieDetailPage';
import DiscussionDetail from '../discussions/DiscussionDetails';
import NotFoundPage from '../../pages/NotFoundPage';
import RegisterPage from '../../pages/RegisterPage';
import LoginPage from '../../pages/LoginPage';
import Logout from '../register-login/Logout';
import ProtectedRoute from './ProtectedRoute';

const UsersPage = React.lazy(() => import('../../pages/UsersPage'));
const MoviesPage = React.lazy(() => import('../../pages/MoviesPage'));

const router = createBrowserRouter([
	{
		path: '/',
		element: <Layout />,
		children: [
			{
				path: '/',
				element: <Navigate to={ROUTE_PATHS.HOME} replace />,
			},
			{
				path: ROUTE_PATHS.USERS,
				element: (
					<ProtectedRoute>
						<Suspense fallback={<Loader />}>
							<UsersPage />
						</Suspense>
					</ProtectedRoute>
				),
				children: [
					{
						path: ':id',
						element: (
							<Suspense fallback={<Loader />}>
								<UsersPage />
							</Suspense>
						),
					},
				],
			},
			{
				path: ROUTE_PATHS.HOME,
				element: (
					<Suspense fallback={<Loader />}>
						<MoviesPage />
					</Suspense>
				),
			},
			{
				path: `${ROUTE_PATHS.HOME}/:id${ROUTE_PATHS.DISCUSSIONS}`,

				element: (
					<Suspense fallback={<Loader />}>
						<MovieDetailPage />
					</Suspense>
				),
			},
			{
				path: `${ROUTE_PATHS.HOME}/:movieId${ROUTE_PATHS.DISCUSSIONS}/:discussionId${ROUTE_PATHS.COMMENTS}`,

				element: (
					<Suspense fallback={<Loader />}>
						<DiscussionDetail />
					</Suspense>
				),
			},
			{
				path: `${ROUTE_PATHS.REGISTER}`,

				element: (
					<Suspense fallback={<Loader />}>
						<RegisterPage />
					</Suspense>
				),
			},
			{
				path: `${ROUTE_PATHS.LOGIN}`,

				element: (
					<Suspense fallback={<Loader />}>
						<LoginPage />
					</Suspense>
				),
			},
			{
				path: `${ROUTE_PATHS.LOGOUT}`,

				element: (
					<Suspense fallback={<Loader />}>
						<Logout />
					</Suspense>
				),
			},
			{
				path: '*',
				element: <NotFoundPage />,
			},
		],
	},
]);

function CustomRouter() {
	return (
		<>
			<RouterProvider router={router} />
		</>
	);
}

export default CustomRouter;
