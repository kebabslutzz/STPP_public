import React, { useEffect, useState } from 'react';
import './Footer.css'; // Assuming you have some CSS for styling
import { Link, useLocation } from 'react-router-dom';
import { Button } from '@mui/material';
import GroupIcon from '@mui/icons-material/Group';
import MovieIcon from '@mui/icons-material/Movie';

const Footer: React.FC = () => {
	const [isVisible, setIsVisible] = useState(false);
	const location = useLocation();

	const handleScroll = () => {
		const scrollTop = window.scrollY;
		const windowHeight = window.innerHeight;
		const documentHeight = document.documentElement.scrollHeight;
		const isBottom = scrollTop + windowHeight >= documentHeight;

		setIsVisible(isBottom || windowHeight >= documentHeight);
	};

	useEffect(() => {
		window.addEventListener('scroll', handleScroll);
		handleScroll(); // Initial check
		return () => {
			window.removeEventListener('scroll', handleScroll);
		};
	}, []);

	useEffect(() => {
		handleScroll(); // Ensure visibility check on location change
	}, [location]);

	return (
		<footer className={`footer ${isVisible ? 'visible' : 'hidden'}`}>
			<div className='footer-content'>
				<span className='footer-left'>KASPARAS PUTRIUS IFF-1/6</span>
				<span className='footer-center'>T120B165 SAITYNO TAIKOMŲJŲ PROGRAMŲ PROJEKTAVIMAS</span>
				<span className='footer-right'>KAUNAS 2024</span>
			</div>
		</footer>
	);
};

export default Footer;
