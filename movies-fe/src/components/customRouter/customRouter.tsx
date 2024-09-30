import React, { Suspense } from 'react';
import { createBrowserRouter, Navigate, RouterProvider } from 'react-router-dom';
// import './App.css';
// import Movies from './components/Movies';
// import Discussions from './components/Discussions';
// import Comments from './components/Comments';
// import CommentDetail from './components/CommentDetail';
import Layout from '../layout/Layout';
import ROUTE_PATHS from '../../constants/routePaths';
import Loader from '../shared/Loader';
import MovieDetailPage from '../../pages/MovieDetailPage';
import DiscussionDetail from '../discussions/DiscussionDetails';

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
					<Suspense fallback={<div>...Loading...</div>}>
						<UsersPage />
					</Suspense>
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
