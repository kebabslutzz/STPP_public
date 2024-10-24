import React from 'react';
import { useParams } from 'react-router-dom';
import UserProfile from '../components/profile/UserProfile';

const ProfilePage: React.FC = () => {
	const { id } = useParams<{ id: string }>();

	return (
		<div className='PageContainer'>
			<UserProfile userId={Number(id)} />
		</div>
	);
};

export default ProfilePage;
