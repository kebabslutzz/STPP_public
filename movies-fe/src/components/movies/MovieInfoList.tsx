import { Box, Button, Divider, List, ListItem, ListItemText } from '@mui/material';
import Movie from '../../interfaces/Movie';
import StarIcon from '@mui/icons-material/Star';
import DeleteIcon from '@mui/icons-material/Delete';
import EditIcon from '@mui/icons-material/Edit';
import './MovieInfoList.css';

interface MovieInfoProps {
	movie: Movie;
	isAdmin: boolean;
	onEdit: () => void;
	onDelete: () => void;
}

const MovieInfoList: React.FC<MovieInfoProps> = ({ movie, isAdmin, onEdit, onDelete }) => {
	return (
		<>
			<List>
				<ListItem>
					<ListItemText>
						<strong>Director:</strong> {movie?.director}
					</ListItemText>
				</ListItem>
				<Divider variant='middle' component='li' className='divider' />
				<ListItem>
					<ListItemText>
						<strong>Genre:</strong> {movie?.genre}
					</ListItemText>
				</ListItem>
				<Divider variant='middle' component='li' className='divider' />
				<ListItem>
					<ListItemText>
						<strong>Rating:</strong> {movie?.rating}/10
						<StarIcon className='starIcon' />
					</ListItemText>
				</ListItem>
				<Divider variant='middle' component='li' className='divider' />
				<ListItem>
					<ListItemText>
						<strong>Release Date:</strong> {new Date(movie?.releaseDate!).toDateString()}
					</ListItemText>
				</ListItem>
				<Divider variant='middle' component='li' className='divider' />

				<ListItem>
					<ListItemText>{movie?.description}</ListItemText>
				</ListItem>
				<Divider variant='middle' component='li' className='divider' />
				{isAdmin && (
					<Box className='ButtonContainer'>
						<ListItem className='ButtonContainerListItem'>
							<Button className='Button add-edit-button' onClick={onEdit} variant='text' endIcon={<EditIcon />}>
								Edit Movie
							</Button>
						</ListItem>
						<ListItem className='ButtonContainerListItem'>
							<Button className='Button delete-button' onClick={onDelete} variant='text' endIcon={<DeleteIcon />}>
								Delete Movie
							</Button>
						</ListItem>
					</Box>
				)}
			</List>
		</>
	);
};

export default MovieInfoList;
