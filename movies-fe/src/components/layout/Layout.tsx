import React from 'react';
import { Box } from '@mui/material';
import { Outlet } from 'react-router-dom';
import styles from './Layout.module.css';
import Header from './Header';
import Footer from './Footer';

const Layout = () => {
	return (
		<Box className={styles.FlexAround}>
			<Header />
			<Box flexGrow='1' display='flex'>
				<Box component='main' flexGrow='1'>
					<Outlet />
				</Box>
			</Box>
			<Footer />
		</Box>
	);
};

export default Layout;
