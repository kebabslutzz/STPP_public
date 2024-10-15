interface Movie {
	id?: number;
	title: string;
	description: string;
	director: string;
	genre: string;
	rating?: number;
	releaseDate: Date;
	posterId?: number;
}

export default Movie;
