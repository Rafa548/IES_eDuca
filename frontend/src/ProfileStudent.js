import React, { useState, useEffect } from 'react';
import axios from 'axios';

const ProfileStudent = ({ studentEmail }) => {
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
        // Fetch student data from the server when the component mounts
        // Replace the API endpoint with the actual endpoint for fetching student data
        axios.get(`http://localhost:8080/students?email=${studentEmail}`)
            .then(response => {
                setStudentData(response.data);
            })
            .catch(error => {
                setError('Error fetching student data');
            });
    }, [studentEmail]); // Fetch data when studentEmail changes

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

            // Replace the API endpoint with the actual endpoint for updating student data
            const response = await axios.put(`http://localhost:8080/api/student/profile?email=${studentEmail}`, passwordPayload);
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
                {/* Rest of the form remains the same */}
                {/* ... */}
            </form>
        </div>
    );
};

export default ProfileStudent;

