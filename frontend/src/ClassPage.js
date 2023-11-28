// ClassPage.js
import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import './ClassPage.css';

const ClassPage = ({ match }) => {
  const [classDetails, setClassDetails] = useState(null);
  const classId = match.params.classId;

  const fetchData = async () => {
    try {
      const response = await fetch(`/classes/${classId}`);
      const data = await response.json();
      setClassDetails(data);
    } catch (error) {
      console.error('Error fetching class details:', error);
    }
  };

  useEffect(() => {
    fetchData(); // Initial data fetch

    // Set up an interval to fetch updated data every, for example, 10 seconds
    const intervalId = setInterval(fetchData, 3000);

    // Clean up the interval when the component unmounts
    return () => clearInterval(intervalId);
  }, [classId]);

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
                    {student.grades.find((grade) => grade.subjectId === subject.id)?.value || 'N/A'}
                  </td>
                ))}
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default ClassPage;
