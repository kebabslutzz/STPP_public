import React from 'react';

interface ErrorDisplayProps {
	errors: string[];
}

const ErrorDisplay: React.FC<ErrorDisplayProps> = ({ errors }) => {
	return (
		<div>
			<h4>The server right now is not responding, please try again later</h4>
			<div>{errors.join(', ')}</div>
		</div>
	);
};

export default ErrorDisplay;
