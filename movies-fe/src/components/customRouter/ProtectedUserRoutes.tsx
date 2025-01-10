// ProtectedUserRoute.tsx
import React from 'react';
import { useAuth } from '../../context/AuthContext';
import { useParams } from 'react-router-dom';
import NotFoundPage from '../../pages/NotFoundPage';

interface ProtectedUserRouteProps {
	children: React.ReactNode;
}

const ProtectedUserRoute: React.FC<ProtectedUserRouteProps> = ({ children }) => {
	const { loggedInUserId } = useAuth();
	const { id } = useParams();

	if (!loggedInUserId || loggedInUserId !== parseInt(id!)) {
		return <NotFoundPage />;
	}

	return <>{children}</>;
};

export default ProtectedUserRoute;
