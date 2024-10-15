import React, { useEffect, useState } from 'react';
import './Header.css'; // Assuming you have some CSS for styling
import { Link } from 'react-router-dom';
import { Button } from '@mui/material';
import GroupIcon from '@mui/icons-material/Group';
import MovieIcon from '@mui/icons-material/Movie';

const Header: React.FC = () => {
	const [isVisible, setIsVisible] = useState(true);

	const handleScroll = () => {
		const scrollTop = window.scrollY;
		const isTop = scrollTop === 0;

		setIsVisible(isTop);
	};

	useEffect(() => {
		window.addEventListener('scroll', handleScroll);
		return () => {
			window.removeEventListener('scroll', handleScroll);
		};
	}, []);

	return (
		<header className={`header ${isVisible ? 'visible' : 'hidden'}`}>
			<div className='header-content'>
				<nav>
					<Button component={Link} to='/users' startIcon={<GroupIcon />} variant='text' className='custom-button'>
						Users
					</Button>
				</nav>
				<nav>
					<Button component={Link} to='/movies' startIcon={<MovieIcon />} variant='text' className='custom-button'>
						Movies
					</Button>
				</nav>
			</div>
		</header>
	);
};

export default Header;
