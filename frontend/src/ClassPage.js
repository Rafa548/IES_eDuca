// ClassPage.js
import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import './ClassPage.css';

const ClassPage = ({ match }) => {
  const [classDetails, setClassDetails] = useState(null);
  const [studentDetails, setStudentDetails] = useState(null);
  const studentId = match.params.studentId;
  const classId = match.params.classId;

  const fetchData = async () => {
    try {
      const response = await fetch(`/classes/${classId}`);
      const response1= await fetch(`/students/${studentId}`);
      const data = await response.json();
      const data1 = await response1.json();
      setStudentDetails(data1);
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
	  return sum / grades.length;
	};

	// Add a function to calculate the average grades for a given subject
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
			{calculateSubjectAverage(student, subject.id) || 'N/A'}
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
