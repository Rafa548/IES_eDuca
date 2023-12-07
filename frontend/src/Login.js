import React, { useState } from 'react';
import axios from 'axios';
import { jwtDecode as jwt_decode } from 'jwt-decode';
import { Redirect, useHistory } from 'react-router-dom';
import AppRoutes from './AppRoutes';
import './Login.css';

const Login = () => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [userType, setUserType] = useState('school_admin');
    const [error, setError] = useState('');
    const [redirectTo, setRedirectTo] = useState(null);
    const history = useHistory(); // Use the useHistory hook

    const handleLogin = async (e) => {
        e.preventDefault();

        try {
            let response;

            if (userType === 'school_admin') {
                response = await axios.post(`http://localhost:8080/auth/signin`, {
                    email,
                    password,
                });
            } else {
                const schoolAdminToken = localStorage.getItem('token');
                response = await axios.post(`http://localhost:8080/auth/signin`, {
                    email,
                    password,
                    schoolAdminToken,
                });
            }

            const token = response.data.token;
            localStorage.setItem('token', token);

            // Decode the token to get user information, including the role
            const decodedToken = jwt_decode(token);
            const userRole = decodedToken.role;
            const userEmail = decodedToken.sub;

            console.log('Decoded token:', decodedToken);
            console.log('Token:', token);
            console.log('Login successful');
            console.log('User role:', userRole);
            console.log('User email:', userEmail);

            if (userRole === 'ROLE_STUDENT') {
                setRedirectTo({
                    pathname: '/profile-student',
                    search: `?email=${encodeURIComponent(userEmail)}`,
                });
            } else if (userRole === 'ROLE_TEACHER') {
                setRedirectTo('/profile-teacher');
            } else if (userRole === 'school_admin') {
                setRedirectTo('/profile-school-admin');
            }
        } catch (error) {
            console.log(error);
            setError(`Invalid ${userType} credentials`);
        }
    };

    const handleUserTypeToggle = () => {
        const userTypesOrder = ['school_admin', 'student', 'teacher'];
        const currentIndex = userTypesOrder.indexOf(userType);
        const nextIndex = (currentIndex + 1) % userTypesOrder.length;
        setUserType(userTypesOrder[nextIndex]);
    };

    // Render the Redirect component if redirectTo is set
    if (redirectTo) {
        // Instead of returning Redirect, use history.push
        history.push(redirectTo);
    }

    return (
        <div>
            <h2>Login</h2>
            {error && <p style={{ color: 'red' }}>{error}</p>}
            <form onSubmit={handleLogin}>
                <label>{`${userType === 'student' ? 'Student' : userType === 'teacher' ? 'Teacher' : 'School Admin'} Email:`}</label>
                <input type="text" value={email} onChange={(e) => setEmail(e.target.value)} />
                <label>Password:</label>
                <input type="password" value={password} onChange={(e) => setPassword(e.target.value)} />
                <button type="submit">Login</button>
            </form>
            <AppRoutes />
            <button onClick={handleUserTypeToggle}>
                Switch to {userType === 'student' ? 'Teacher' : userType === 'teacher' ? 'School Admin' : 'Student'}
            </button>
        </div>
    );
};

export default Login;

