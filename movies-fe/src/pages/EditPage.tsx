import React from 'react';
import UserLogin from '../components/register-login/UserLogin';
import EditProfilePage from '../components/profile/EditProfile';
import { useNavigate, useParams } from 'react-router-dom';
import { Container } from '@mui/material';

const EditPage: React.FC = () => {
	const { id } = useParams();
	const navigate = useNavigate();

	const handleClose = () => {
		navigate(`/users/${id}`);
	};

	if (!id) {
		return <Container>User not found</Container>;
	}

	return <EditProfilePage userId={parseInt(id)} onClose={handleClose} />;
};

export default EditPage;
