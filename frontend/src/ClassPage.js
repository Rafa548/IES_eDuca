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

  const calculateAverage = (grades) => {
    if (grades.length === 0) {
      return undefined;
    }

    const sum = grades.reduce((accumulator, grade) => accumulator + grade.value, 0);
    return sum / grades.length;
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
                  {classDetails.subjects.map((subject) => {
                    const studentGradesForSubject = student.grades.filter((grade) => grade.subjectId === subject.id);
                    const averageGrade = calculateAverage(studentGradesForSubject);
                    return (
                        <td key={subject.id}>
                          {averageGrade !== undefined ? averageGrade.toFixed(2) : 'N/A'}
                        </td>
                    );
                  })}
                </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default ClassPage;
