import React, { useEffect, useState } from 'react';
import './Header.css';
import { Link } from 'react-router-dom';
import { Button, Drawer, List, ListItemButton, ListItemIcon, ListItemText } from '@mui/material';
import GroupIcon from '@mui/icons-material/Group';
import MovieIcon from '@mui/icons-material/Movie';
import PersonAddIcon from '@mui/icons-material/PersonAdd';
import LoginIcon from '@mui/icons-material/Login';
import LogoutIcon from '@mui/icons-material/Logout';
import MenuIcon from '@mui/icons-material/Menu';
import { useAuth } from '../../context/AuthContext';
import AccountBoxIcon from '@mui/icons-material/AccountBox';

const Header: React.FC = () => {
	const [isVisible, setIsVisible] = useState(true);
	const [drawerOpen, setDrawerOpen] = useState(false);
	const { isLoggedIn, loggedInUserRole, loggedInUserId } = useAuth();

	const handleScroll = () => {
		const scrollTop = window.scrollY;
		const isTop = scrollTop === 0;
		setIsVisible(isTop);
	};

	useEffect(() => {
		window.addEventListener('scroll', handleScroll);
		return () => window.removeEventListener('scroll', handleScroll);
	}, []);

	return (
		<header className={`header ${isVisible ? 'visible' : 'hidden'}`}>
			<div className='header-content'>
				<nav className='specialButton'>
					<Button component={Link} to='/movies' startIcon={<MovieIcon />} className='custom-button'>
						Movies
					</Button>
				</nav>
				{/* Burger Menu Button */}
				<div className='burger-menu' onClick={() => setDrawerOpen(true)}>
					<MenuIcon />
				</div>

				{/* Drawer for small screens */}
				<Drawer anchor='top' open={drawerOpen} onClose={() => setDrawerOpen(false)}>
					<List>
						{loggedInUserRole === 'ADMIN' && (
							<ListItemButton component={Link} to='/users' onClick={() => setDrawerOpen(false)}>
								<ListItemIcon>
									<GroupIcon />
								</ListItemIcon>
								<ListItemText primary='Users' />
							</ListItemButton>
						)}
						{isLoggedIn ? (
							<>
								<ListItemButton component={Link} to='/logout' onClick={() => setDrawerOpen(false)}>
									<ListItemIcon>
										<LogoutIcon />
									</ListItemIcon>
									<ListItemText primary='Logout' />
								</ListItemButton>
								<ListItemButton component={Link} to={`/users/${loggedInUserId}`} onClick={() => setDrawerOpen(false)}>
									<ListItemIcon>
										<AccountBoxIcon />
									</ListItemIcon>
									<ListItemText primary='Profile' />
								</ListItemButton>
							</>
						) : (
							<>
								<ListItemButton component={Link} to='/register' onClick={() => setDrawerOpen(false)}>
									<ListItemIcon>
										<PersonAddIcon />
									</ListItemIcon>
									<ListItemText primary='Register' />
								</ListItemButton>
								<ListItemButton component={Link} to='/login' onClick={() => setDrawerOpen(false)}>
									<ListItemIcon>
										<LoginIcon />
									</ListItemIcon>
									<ListItemText primary='Login' />
								</ListItemButton>
							</>
						)}
					</List>
				</Drawer>

				{/* Regular Navigation for large screens */}
				<nav className='nav-links'>
					{loggedInUserRole === 'ADMIN' && (
						<Button component={Link} to='/users' startIcon={<GroupIcon />} className='custom-button'>
							Users
						</Button>
					)}
					{/* <Button component={Link} to='/movies' startIcon={<MovieIcon />} className='custom-button'>
						Movies
					</Button> */}
					{isLoggedIn ? (
						<>
							<Button component={Link} to='/logout' startIcon={<LogoutIcon />} className='custom-button'>
								Logout
							</Button>
							<Button
								component={Link}
								to={`/users/${loggedInUserId}`}
								startIcon={<AccountBoxIcon />}
								className='custom-button'
							>
								Profile
							</Button>
						</>
					) : (
						<>
							<Button component={Link} to='/register' startIcon={<PersonAddIcon />} className='custom-button'>
								Register
							</Button>
							<Button component={Link} to='/login' startIcon={<LoginIcon />} className='custom-button'>
								Login
							</Button>
						</>
					)}
				</nav>
			</div>
		</header>
	);
};

export default Header;
