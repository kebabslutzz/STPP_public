import React from 'react';
import { useAuth } from '../../context/AuthContext';
import NotFoundPage from '../../pages/NotFoundPage';

interface ProtectedRouteProps {
	children: React.ReactNode;
}

const ProtectedRoute: React.FC<ProtectedRouteProps> = ({ children }) => {
	const { isAdmin } = useAuth();

	if (!isAdmin) {
		return <NotFoundPage />;
	}

	return <>{children}</>;
};

export default ProtectedRoute;
