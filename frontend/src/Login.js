import React, { useState } from 'react';
import axios from 'axios';
import { jwtDecode as jwt_decode } from 'jwt-decode';
import { useHistory } from 'react-router-dom';
import './Login.css';

const Login = () => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [userType, setUserType] = useState('school_admin');
    const [error, setError] = useState('');
    const history = useHistory();

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
            console.log('Token:', token);
            console.log('Decoded Token:', decodedToken);
            const userRole = decodedToken.payload.Role;



            console.log('Login successful');
            console.log('User role:', userRole);

            // Redirect based on user role
            if (userRole === 'student') {
                history.push('/profile-student'); // Redirect to the student profile page
            } else if (userRole === 'teacher') {
                history.push('/profile-teacher'); // Redirect to the teacher profile page
            } else if (userRole === 'school_admin') {
                history.push('/profile-school-admin'); // Redirect to the school admin profile page
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
            <button onClick={handleUserTypeToggle}>
                Switch to {userType === 'student' ? 'Teacher' : userType === 'teacher' ? 'School Admin' : 'Student'}
            </button>
        </div>
    );
};

export default Login;
