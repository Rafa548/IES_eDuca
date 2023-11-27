import logo from './logo.svg';
import './App.css';
import React, { useState, useEffect } from 'react';

const App = () => {
  const [classes, setClasses] = useState([]);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await fetch('/classes');
        const data = await response.json();
        setClasses(data);
      } catch (error) {
        console.error('Error fetching data:', error);
      }
    };

    fetchData();
  }, []);

  return (
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <div className="App-intro">
          <h2>Classes</h2>
          {classes.map((sClass) => (
            <div key={sClass.id} className="class-container">
              <h3>{sClass.classname}</h3>
              <p>School: {sClass.school}</p>
              <div className="section">
                <h4>Subjects:</h4>
                <ul>
                  {sClass.subjects.map((subject) => (
                    <li key={subject.id}>{subject.name}</li>
                  ))}
                </ul>
              </div>
              <div className="section">
                <h4>Students:</h4>
                <ul>
                  {sClass.students.map((student) => (
                    <li key={student.id}>
                      {student.name} ({student.email})
                    </li>
                  ))}
                </ul>
              </div>
            </div>
          ))}
        </div>
      </header>
    </div>
  );
};

export default App;
