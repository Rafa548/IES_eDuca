import React, { useState, useEffect } from 'react';
import axios from 'axios';
import Navbar from './Navbar';

const ProfileStudent = ({ location }) => {
    const [studentEmail, setStudentEmail] = useState('');
    console.log("email", studentEmail);
    const [studentData, setStudentData] = useState({
        id: '',
        nmec: '',
        school: '',
        studentclass: {},
        name: '',
        email: '',
        password: '',
    });
    const [error, setError] = useState('');

    useEffect(() => {
        // Extract studentEmail from URL parameter
        const searchParams = new URLSearchParams(location.search);
        const emailFromParam = searchParams.get('email');

        if (emailFromParam) {
            setStudentEmail(emailFromParam);
            console.log("emailparams", emailFromParam);

            // Fetch student data from the server using the email and token
            const token = localStorage.getItem('token');
            axios.get(`http://localhost:8080/students?email=${emailFromParam}`, {
                headers: {
                    Authorization: `Bearer ${token}`,
                }
            })
                .then(response => {
                    setStudentData(response.data);
                })
                .catch(error => {
                    setError('Error fetching student data');
                });
        } else {
            setError('No student email provided');
        }
    }, [location.search]);

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setStudentData(prevData => ({
            ...prevData,
            [name]: value,
        }));
    };

    const handleSaveChanges = async () => {
        try {
            // Create a payload only containing the password field
            const passwordPayload = {
                password: studentData.password,
            };

            // Fetch student data from the server using the email and token
            const token = localStorage.getItem('token');
            const response = await axios.put(`http://localhost:8080/students?email=${studentEmail}`, passwordPayload, {
                headers: {
                    Authorization: `Bearer ${token}`,
                }
            });
            console.log('Password updated successfully:', response.data);
        } catch (error) {
            setError('Error updating password');
        }
    };

    return (
        <div>
            <h2>Edit Profile</h2>
            {error && <p style={{ color: 'red' }}>{error}</p>}
            <form>
                {/* Include form elements for student profile */}
                {/* Example: */}
                <label>Name:</label>
                <input type="text" name="name" value={studentData.name} onChange={handleInputChange} />
                <label>Email:</label>
                <input type="text" name="email" value={studentData.email} onChange={handleInputChange} />
                <label>Password:</label>
                <input type="password" name="password" value={studentData.password} onChange={handleInputChange} />
                <button type="button" onClick={handleSaveChanges}>Save Changes</button>
            </form>
        </div>
    );
};


export default ProfileStudent;
