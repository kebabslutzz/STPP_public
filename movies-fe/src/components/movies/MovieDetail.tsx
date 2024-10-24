import React, { useEffect, useState } from 'react';
import Movie from '../../interfaces/Movie';
import Loader from '../shared/Loader';
import './MovieDetail.css';
import Discussion from '../../interfaces/Discussion';
import useQuery from '../../hooks/useQuery';
import { ENDPOINTS } from '../../constants/endpoints';
import { HTTP_METHODS } from '../../constants/httpsMethods';
import { Button, Container, List, ListItem, ListItemText, Divider, Typography } from '@mui/material';
import StarIcon from '@mui/icons-material/Star';
import DiscussionListItem from '../discussions/DisccusionListItem';
import Poster from '../../interfaces/Poster';
import MovieFormDialogBox from './component/MovieFormDialogBox';
import DeleteConfirmationDialog from '../dialog/DeleteConfirmationDialog';
import { useNavigate } from 'react-router-dom';
import DiscussionFormDialogBox from '../discussions/DiscussionFormDialogBox';
import EditIcon from '@mui/icons-material/Edit';
import ForumIcon from '@mui/icons-material/Forum';
import HideImageIcon from '@mui/icons-material/HideImage';
import DeleteIcon from '@mui/icons-material/Delete';
import { useAuth } from '../../context/AuthContext';

interface MovieDetailProps {
	movieId: number;
}

const MovieDetail: React.FC<MovieDetailProps> = ({ movieId }) => {
	const [discussionList, setDiscussionList] = useState<Discussion[]>([]);
	const [poster, setPoster] = useState<Poster | null>(null);
	const [isDialogOpen, setIsDialogOpen] = useState(false);
	const [listOfErrors, setListOfErrors] = useState<string[]>([]);
	const [isDeleteDialogOpen, setIsDeleteDialogOpen] = useState(false);
	const [isDiscussionDialogOpen, setIsDiscussionDialogOpen] = useState(false);
	const [openMovie, setOpenMovie] = useState<Movie | null>(null);
	const navigate = useNavigate();
	const { isAdmin, isLoggedIn, loggedInUserId } = useAuth();

	const handleOpenDialog = () => {
		setIsDialogOpen(true);
	};

	const handleCloseDialog = () => {
		setIsDialogOpen(false);
	};

	const handleEditSuccess = async () => {
		getDiscussionData();
	};

	const handleOpenDeleteDialog = () => {
		setIsDeleteDialogOpen(true);
	};

	const handleCloseDeleteDialog = () => {
		setIsDeleteDialogOpen(false);
	};

	const handleOpenDiscussionDialog = () => {
		setIsDiscussionDialogOpen(true);
	};

	const handleCloseDiscussionDialog = () => {
		setIsDiscussionDialogOpen(false);
	};

	const {
		data: movie,
		isLoading: isMovieLoading,
		errors: movieErrors,
		getData: getMovieData,
	} = useQuery<Movie>({
		url: ENDPOINTS.MOVIES.GET_MOVIE_BY_ID(movieId),
		httpMethod: HTTP_METHODS.GET,
	});

	useEffect(() => {
		if (!movie) {
			getMovieData();
		}
	}, [getMovieData, movie]);

	useEffect(() => {
		if (movie) {
			setOpenMovie({
				...movie,
				releaseDate: new Date(movie.releaseDate),
			});
		}
	}, [movie]);

	const {
		data: discussions,
		isLoading: isDiscussionsLoading,
		errors: errorsDiscussions,
		getData: getDiscussionData,
	} = useQuery<Discussion[]>({
		url: ENDPOINTS.DISCUSSIONS.GET_ALL_DISCUSSIONS_BY_MOVIE_ID(movieId ?? 0),
		httpMethod: HTTP_METHODS.GET,
	});

	useEffect(() => {
		if (!discussions) {
			getDiscussionData();
		}
	}, []);

	useEffect(() => {
		if (discussions) {
			setDiscussionList(discussions);
		}
	}, [discussions]);

	const {
		data: posterBytes,
		isLoading: isLoadingPoster,
		errors: errorsPoster,
		getData: getPosterData,
	} = useQuery<Poster>({
		url: ENDPOINTS.POSTER.GET_POSTER(openMovie?.posterId! ?? 0),
		httpMethod: HTTP_METHODS.GET,
	});

	const refetchPoster = useEffect(() => {
		if (openMovie?.posterId && openMovie.posterId !== 0 && !poster) {
			getPosterData();
		}
	}, [openMovie?.posterId, poster]);

	const resetPoster = useEffect(() => {
		if (posterBytes) {
			setPoster(posterBytes);
		}
	}, [posterBytes]);

	const base64String = poster?.poster ? `data:image/jpeg;base64,${poster.poster}` : null;

	const updateMovieCommand = useQuery({
		url: ENDPOINTS.MOVIES.UPDATE_MOVIE(movieId),
		httpMethod: HTTP_METHODS.PUT,
		onSuccess: handleEditSuccess,
	});

	const onEditSubmit = async (newMovie: Movie, poster?: File) => {
		if (isAdmin) {
			try {
				let posterId: number | null = newMovie.posterId ? newMovie.posterId : null;

				// Step 1: Upload Poster if it exists
				if (poster!) {
					const formData = new FormData();
					formData.append('file', poster);
					let posterResponse: Response;

					if (newMovie.posterId) {
						posterResponse = await fetch(ENDPOINTS.POSTER.UPDATE_POSTER(newMovie.posterId), {
							method: HTTP_METHODS.PUT,
							body: formData,
						});

						if (!posterResponse.ok) {
							throw new Error('Failed to upload poster');
						}
					} else {
						posterResponse = await fetch(ENDPOINTS.POSTER.CREATE_POSTER, {
							method: HTTP_METHODS.POST,
							body: formData,
						});

						if (!posterResponse.ok) {
							throw new Error('Failed to upload poster');
						}
					}

					const posterData = await posterResponse.json();
					// newMovie!.posterId = posterData.id;
					posterId = Number(posterData.id);
				}

				// Step 2: Create Movie
				const { id, ...newMovieWithoutId } = newMovie;

				const movieWithPoster: Movie = {
					...newMovieWithoutId,
					...(posterId ? { posterId } : {}),
				};
				const movieResponse = await updateMovieCommand.sendData(movieWithPoster);
				if (movieResponse?.status === 200) {
					setOpenMovie(movieWithPoster);

					console.log('current poster id:', newMovie.posterId);
					console.log('new poster id:', movieWithPoster.posterId);
					// Fetch the updated poster if it was changed
					if (posterId != newMovie.posterId) {
						getPosterData();
					}
				}
			} catch (error) {
				setListOfErrors([String((error as Error).message)]);
				console.error(listOfErrors);
			}
		}
	};

	const { errors: deleteMovieErrors, sendData: deleteMovie } = useQuery<void>({
		url: ENDPOINTS.MOVIES.DELETE_MOVIE(movieId),
		httpMethod: HTTP_METHODS.DELETE,
	});

	const handleDelete = async () => {
		await deleteMovie();
		if (!deleteMovieErrors) {
			navigate('/movies');
		}
	};

	const createDiscussionCommand = useQuery({
		url: ENDPOINTS.MOVIES.CREATE_DISCUSSION(movieId),
		httpMethod: HTTP_METHODS.POST,
	});

	const handleDiscussionSubmit = async (newDiscussion: Discussion) => {
		if (isLoggedIn) {
			newDiscussion.movieId = movieId;
			newDiscussion.userId = loggedInUserId;
			const discussionResponse = await createDiscussionCommand.sendData(newDiscussion);
			if (discussionResponse?.status === 201 && 'data' in discussionResponse) {
				let createdDiscussion = discussionResponse?.data as Discussion;
				setDiscussionList((prev) => [createdDiscussion, ...prev]);
			}
		}
	};

	if (isDiscussionsLoading) return <Loader />;
	if (errorsDiscussions) return <div>{errorsDiscussions.join(', ')}</div>;
	if (isMovieLoading) return <Loader />;
	if (movieErrors) return <div>{movieErrors.join(', ')}</div>;

	return (
		<Container className='MovieDetailContainer' maxWidth={false}>
			<Typography className='Movie-title'>{openMovie?.title}</Typography>
			<div className='MovieDetail'>
				{base64String ? (
					<div className='MoviePosterContainer'>
						<img src={base64String} alt={movie?.title} className='MoviePosterDetails' />
					</div>
				) : (
					<div className='NoImageTextDetails'>
						<span>
							Movie Poster Not Available
							<HideImageIcon />
						</span>
					</div>
				)}
				<div>
					<List>
						<ListItem>
							<ListItemText>
								<strong>Director:</strong> {openMovie?.director}
							</ListItemText>
						</ListItem>
						<Divider variant='middle' component='li' className='divider' />
						<ListItem>
							<ListItemText>
								<strong>Genre:</strong> {openMovie?.genre}
							</ListItemText>
						</ListItem>
						<Divider variant='middle' component='li' className='divider' />
						<ListItem>
							<ListItemText>
								<strong>Rating:</strong> {openMovie?.rating}/10
								<StarIcon className='starIcon' />
							</ListItemText>
						</ListItem>
						<Divider variant='middle' component='li' className='divider' />
						<ListItem>
							<ListItemText>
								<strong>Release Date:</strong> {new Date(openMovie?.releaseDate!).toDateString()}
							</ListItemText>
						</ListItem>
						<Divider variant='middle' component='li' className='divider' />

						<ListItem>
							<ListItemText>{openMovie?.description}</ListItemText>
						</ListItem>
						<Divider variant='middle' component='li' className='divider' />
						{isAdmin && (
							<ListItem>
								<Button
									className='Button add-edit-button'
									onClick={handleOpenDialog}
									variant='text'
									endIcon={<EditIcon />}
								>
									Edit Movie
								</Button>
								<Button
									className='Button delete-button'
									onClick={handleOpenDeleteDialog}
									variant='text'
									endIcon={<DeleteIcon />}
								>
									Delete Movie
								</Button>
							</ListItem>
						)}
					</List>
				</div>
			</div>
			<div>
				<div className='discussion-header'>
					{!discussionList || discussionList.length === 0 ? (
						<div>No discussions yet</div>
					) : (
						<div className='most-recent-discussions'>Most recent discussions</div>
					)}
					{isLoggedIn && (
						<Button
							onClick={handleOpenDiscussionDialog}
							variant='text'
							className='Button add-edit-button'
							endIcon={<ForumIcon />}
						>
							Create Discussion
						</Button>
					)}
				</div>
			</div>
			<List component='nav' aria-label='discussions'>
				{discussionList && discussionList.length > 0 && (
					<>
						{discussionList.map((discussion) => (
							<DiscussionListItem key={discussion.id} discussion={discussion} movie={openMovie!} />
						))}
					</>
				)}
			</List>
			{isDialogOpen && (
				<MovieFormDialogBox
					movie={openMovie!}
					onClose={handleCloseDialog}
					onSubmit={onEditSubmit}
					open={isDialogOpen}
				/>
			)}
			<DeleteConfirmationDialog
				open={isDeleteDialogOpen}
				onClose={handleCloseDeleteDialog}
				onConfirm={handleDelete}
				text='Are you sure you want to delete this movie?'
			/>
			<DiscussionFormDialogBox
				open={isDiscussionDialogOpen}
				onClose={handleCloseDiscussionDialog}
				onSubmit={handleDiscussionSubmit}
				title='Create Discussion'
			/>
		</Container>
	);
};

export default MovieDetail;
