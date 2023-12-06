import React, { useState } from 'react';
import axios from 'axios';
import './Login.css';

const Login = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [userType, setUserType] = useState('student');
    const [error, setError] = useState('');

    const handleLogin = async (e) => {
        e.preventDefault();

        try {
            const response = await axios.post(`http://localhost:8080/api/login/${userType}`, {
                username,
                password,
            });

            console.log(response.data); // Handle the response as needed (e.g., redirect on successful login)
        } catch (error) {
            setError(`Invalid ${userType} credentials`);
        }
    };

    const handleUserTypeToggle = () => {
        setUserType((prevUserType) => (prevUserType === 'student' ? 'teacher' : 'student'));
    };

    return (
        <div>
            <h2>Login</h2>
            {error && <p style={{ color: 'red' }}>{error}</p>}
            <form onSubmit={handleLogin}>
                <label>{`${userType === 'student' ? 'Student' : 'Teacher'} Username:`}</label>
                <input type="text" value={username} onChange={(e) => setUsername(e.target.value)} />
                <label>Password:</label>
                <input type="password" value={password} onChange={(e) => setPassword(e.target.value)} />
                <button type="submit">Login</button>
            </form>
            <button onClick={handleUserTypeToggle}>
                Switch to {userType === 'student' ? 'Teacher' : 'Student'}
            </button>
        </div>
    );
};

export default Login;
