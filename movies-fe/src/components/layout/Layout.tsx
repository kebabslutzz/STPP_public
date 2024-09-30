import React from 'react';
import { Box } from '@mui/material';
import { Outlet } from 'react-router-dom';
import styles from './Layout.module.css';

const Layout = () => {
	return (
		<Box className={styles.FlexAround}>
			<Box flexGrow='1' display='flex'>
				<Box component='main' flexGrow='1'>
					<Outlet />
				</Box>
			</Box>
		</Box>
	);
};

export default Layout;
