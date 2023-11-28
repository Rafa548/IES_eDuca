// App.js
import React, { useState, useEffect } from 'react';
import { BrowserRouter as Router, Route, Link, Switch } from 'react-router-dom';
import logo from './logo.svg';
import './App.css';
import ClassPage from './ClassPage';

const ClassCard = ({ sClass }) => (
  <div className="class-container">
    <Link to={`/class/${sClass.classname}`}>
      <h3>{sClass.classname}</h3>
    </Link>
    {/* ... (rest of the class details) */}
  </div>
);

const ClassList = ({ classes }) => (
  <div className="class-container-wrapper">
    {classes.map((sClass) => (
      <ClassCard key={sClass.id} sClass={sClass} />
    ))}
  </div>
);

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
    <Router>
      <div className="App">
        <header className="App-header">
          
          <div className="App-intro">
            <h2>Classes</h2>
            <Switch>
              <Route path="/class/:classId" component={ClassPage} />
              <Route path="/" render={() => <ClassList classes={classes} />} />
            </Switch>
          </div>
        </header>
      </div>
    </Router>
  );
};

export default App;
