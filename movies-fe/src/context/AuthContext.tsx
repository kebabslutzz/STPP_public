import React, { createContext, useContext, useState, ReactNode, useEffect } from 'react';
import { jwtDecode, JwtPayload } from 'jwt-decode';

interface CustomJwtPayload extends JwtPayload {
	role: string;
	sub: string;
	iat: number;
	exp: number;
}

interface AuthContextType {
	isLoggedIn: boolean;
	login: (token: string) => void;
	logout: () => void;
	claims: CustomJwtPayload | null;
	getToken: () => string | undefined;
	loggedInUserId: number | undefined;
	loggedInUserRole: string | undefined;
	tokenExpirationTime: number | undefined;
	tokenCreationTime: number | undefined;
	isAdmin: boolean;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export const AuthProvider: React.FC<{ children: ReactNode }> = ({ children }) => {
	const [isLoggedIn, setIsLoggedIn] = useState<boolean>(!!localStorage.getItem('token'));
	const [claims, setClaims] = useState<CustomJwtPayload | null>(null);
	const [loggedInUserId, setLoggedInUserId] = useState<number | undefined>(undefined);
	const [loggedInUserRole, setLoggedInUserRole] = useState<string | undefined>(undefined);
	const [tokenExpirationTime, setTokenExpirationTime] = useState<number | undefined>(undefined);
	const [tokenCreationTime, setTokenCreationTime] = useState<number | undefined>(undefined);
	const [isAdmin, setIsAdmin] = useState<boolean>(false);

	useEffect(() => {
		const storedToken = localStorage.getItem('token');
		if (storedToken) {
			const decodedClaims = jwtDecode<CustomJwtPayload>(storedToken);
			setLoggedInUserId(Number(decodedClaims.sub));
			setLoggedInUserRole(decodedClaims.role);
			setTokenCreationTime(decodedClaims.iat);
			setTokenExpirationTime(decodedClaims.exp);
			setClaims(decodedClaims);
			setIsAdmin(decodedClaims.role === 'ADMIN');
		}
	}, []);

	const login = (token: string) => {
		localStorage.setItem('token', token);
		setIsLoggedIn(true);
		const decodedClaims = jwtDecode<CustomJwtPayload>(token);
		setLoggedInUserId(Number(decodedClaims.sub));
		setLoggedInUserRole(decodedClaims.role);
		setTokenCreationTime(decodedClaims.iat);
		setTokenExpirationTime(decodedClaims.exp);
		setIsAdmin(decodedClaims.role === 'ADMIN');
		setClaims(decodedClaims);
	};

	const logout = () => {
		localStorage.removeItem('token');
		setIsLoggedIn(false);
		setLoggedInUserId(undefined);
		setLoggedInUserRole(undefined);
		setTokenCreationTime(undefined);
		setTokenExpirationTime(undefined);
		setIsAdmin(false);
		setClaims(null);
	};

	const getToken = () => {
		return localStorage.getItem('token') || undefined;
	};

	return (
		<AuthContext.Provider
			value={{
				isLoggedIn,
				login,
				logout,
				claims,
				getToken,
				loggedInUserId,
				loggedInUserRole,
				tokenCreationTime,
				tokenExpirationTime,
				isAdmin,
			}}
		>
			{children}
		</AuthContext.Provider>
	);
};

export const useAuth = () => {
	const context = useContext(AuthContext);
	if (context === undefined) {
		throw new Error('useAuth must be used within an AuthProvider');
	}
	return context;
};
