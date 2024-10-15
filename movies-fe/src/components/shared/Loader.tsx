import React from 'react';
import { CircularProgress } from '@mui/material';
import './Loader.css';

interface LoaderProps {
	errors?: string[] | null;
	textNeeded?: boolean;
}

const Loader: React.FC<LoaderProps> = ({ errors, textNeeded }) => (
	<div>
		<CircularProgress className='CircularProgress' />
		{textNeeded && (
			<>
				<div className='Loader'>Loading...</div>
				{errors && errors.length > 0 && (
					<div className='LoaderErrors'>
						<h4>The server right now is not responding, please try again later</h4>
						<div>{errors.join(', ')}</div>
					</div>
				)}
			</>
		)}
	</div>
);

export default Loader;
