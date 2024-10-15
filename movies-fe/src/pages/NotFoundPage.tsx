// src/pages/NotFoundPage.tsx
import React from 'react';
import { Container } from '@mui/material';

const NotFoundPage: React.FC = () => {
	return (
		<Container className='NotFoundPage'>
			<h1>404 - Page Not Found</h1>
			<p>Sorry, the page you are looking for does not exist.</p>
		</Container>
	);
};

export default NotFoundPage;
