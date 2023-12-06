// ClassPage.js
import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import './ClassPage.css';
import WebSocketService from './WebSocketService';

const ClassPage = ({ match }) => {
  const [classDetails, setClassDetails] = useState(null);
  const [studentDetails, setStudentDetails] = useState(null);
  const [subjectAverages, setSubjectAverages] = useState({});

  const studentId = match.params.studentId;
  const classId = match.params.classId;

  //notifications
  const [notifications, setNotifications] = useState([]);
  const webSocketService = WebSocketService(onMessage);

  // Define onMessage function before using it in useEffect
  function onMessage(notification) {
    //console.log('Received notification:', notification);
    setNotifications((prevNotifications) => [...prevNotifications, notification]);
  }

  useEffect(() => {
    console.log('Connecting to WebSocket...');
    webSocketService.connect();

    // Subscribe to the class (replace 'ClassName' with the actual class name)
    const subscription = webSocketService.subscribeToClass('ClassName');

    return () => {
      // Check if subscription is not null before unsubscribing
      if (subscription) {
        subscription.unsubscribe();
      }
      console.log('Disconnecting from WebSocket...');
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

      const averages = {};
      for (const student of data.students) {
        for (const subject of data.subjects) {
          const gradeResponse = await fetch(`/classes/${data.classname}/${student.nmec}/${subject.name}/grade`);
          const gradeData = await gradeResponse.text();

          averages[`${student.id}-${subject.id}`] = gradeData || 'N/A';
        }
      }
      setSubjectAverages(averages);

    } catch (error) {
      console.error('Error fetching class details:', error);
    }
  };

  /* const calculateAverage = (grades) => {
    if (grades.length === 0) {
      return undefined;
    }

    const sum = grades.reduce((accumulator, grade) => accumulator + grade.grade, 0);
    return (sum / grades.length).toFixed(1);
  };

  const calculateSubjectAverage = (student, subjectId) => {
    const subjectGrades = student.grades.filter((grade) => grade.subject.id === subjectId);
    return calculateAverage(subjectGrades);
  }; */


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
                {classDetails.subjects.map((subject) => {
                  const averageKey = `${student.id}-${subject.id}`;
                  const average = subjectAverages[averageKey] || 'N/A';
                  const isBelowTen = average !== 'N/A' && parseFloat(average) < 10;

                  return (
                    <td key={subject.id} style={{ color: isBelowTen ? 'red' : 'black' }}>
                      {average}
                    </td>
                  );
                })}
              </tr>
            ))}
          </tbody>
        </table>
      </div>
      <div>
        <h1>Notifications</h1>
        <ul>
          {notifications.slice(-10).map((notification, index) => (
            <li key={index}>{notification.message}</li>
          ))}
        </ul>
      </div>
    </div>
  );
};

export default ClassPage;
