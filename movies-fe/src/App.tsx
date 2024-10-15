import React, { useEffect, useState } from 'react';
import './App.css';
import CustomRouter from './components/customRouter/customRouter';
// import Users from './components/Users';

function App() {
	const [darkMode, setDarkMode] = useState(true);

	useEffect(() => {
		const savedMode = localStorage.getItem('darkMode');
		if (savedMode) {
			setDarkMode(JSON.parse(savedMode));
		}
	}, []);

	useEffect(() => {
		if (darkMode) {
			document.documentElement.classList.add('dark-mode');
		} else {
			document.documentElement.classList.remove('dark-mode');
		}
		localStorage.setItem('darkMode', JSON.stringify(darkMode));
	}, [darkMode]);

	return (
		<>
			<CustomRouter />
		</>
	);
}

export default App;
