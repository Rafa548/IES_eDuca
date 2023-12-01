// ClassPage.js
import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import './ClassPage.css';
import WebSocketService from './WebSocketService';

const ClassPage = ({ match }) => {
  const [classDetails, setClassDetails] = useState(null);
  const [studentDetails, setStudentDetails] = useState(null);

  const studentId = match.params.studentId;
  const classId = match.params.classId;

  //notifications
  const [notifications, setNotifications] = useState([]);
  const webSocketService = WebSocketService(onMessage);

  // Define onMessage function before using it in useEffect
  function onMessage(notification) {
    setNotifications((prevNotifications) => [...prevNotifications, notification]);
  }

  useEffect(() => {
    webSocketService.connect();

    // Subscribe to the class (replace 'ClassName' with the actual class name)
    const subscription = webSocketService.subscribeToClass('ClassName');

    return () => {
      // Check if subscription is not null before unsubscribing
      if (subscription) {
        subscription.unsubscribe();
      }
      webSocketService.disconnect();
    };
  }, [webSocketService]);

  const fetchData = async () => {
    try {
      if (studentId) {
        const response1 = await fetch(`/students/${studentId}`);
        const data1 = await response1.json();
        setStudentDetails(data1);
      }

      const response = await fetch(`/classes/${classId}`);
      const data = await response.json();
      setClassDetails(data);
    } catch (error) {
      console.error('Error fetching class details:', error);
    }
  };

  const calculateAverage = (grades) => {
    if (grades.length === 0) {
      return undefined;
    }

    const sum = grades.reduce((accumulator, grade) => accumulator + grade.grade, 0);
    return (sum / grades.length).toFixed(1);
  };

  const calculateSubjectAverage = (student, subjectId) => {
    const subjectGrades = student.grades.filter((grade) => grade.subject.id === subjectId);
    return calculateAverage(subjectGrades);
  };

  useEffect(() => {
    fetchData(); // Initial data fetch

    // Set up an interval to fetch updated data every, for example, 10 seconds
    const intervalId = setInterval(fetchData, 3000);

    // Clean up the interval when the component unmounts
    return () => clearInterval(intervalId);
  }, [classId, studentId]);

  if (!classDetails) {
    return <div>Loading...</div>;
  }

  return (
    <div>
      <Link to="/">Go Back to Classes</Link>
      <h2>{classDetails.classname}</h2>
      <p>School: {classDetails.school}</p>

      <div className="table-container">
        <table>
          <thead>
            <tr>
              <th>Student</th>
              {classDetails.subjects.map((subject) => (
                <th key={subject.id}>{subject.name}</th>
              ))}
            </tr>
          </thead>
          <tbody>
            {classDetails.students.map((student) => (
              <tr key={student.id}>
                <td>{student.name}</td>
                {classDetails.subjects.map((subject) => (
                  <td key={subject.id}>
                    {calculateSubjectAverage(student, subject.id) || 'N/A'}
                  </td>
                ))}
              </tr>
            ))}
          </tbody>
        </table>
      </div>
      <div>
        <h1>Notifications</h1>
        <ul>
          {notifications.map((notification, index) => (
            <li key={index}>{notification.message}</li>
          ))}
        </ul>
      </div>
    </div>
  );
};

export default ClassPage;
