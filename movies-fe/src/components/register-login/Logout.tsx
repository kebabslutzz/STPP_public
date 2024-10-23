import React, { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../../context/AuthContext';

const Logout: React.FC = () => {
	const navigate = useNavigate();
	const { logout } = useAuth();

	useEffect(() => {
		// Clear user data from localStorage
		logout();
		// Redirect to login page
		navigate('/movies');
	}, [logout, navigate]);

	return null;
};

export default Logout;
