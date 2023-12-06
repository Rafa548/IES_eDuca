// App.js
import React, { useState, useEffect } from 'react';
import { BrowserRouter as Router, Route, Link, Switch } from 'react-router-dom';
import logo from './logo.svg';
import './App.css';
import ClassPage from './ClassPage';
import Navbar from './Navbar';

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

  const [token, setToken] = useState(null);

  useEffect(() => {
    const fetchToken = async () => {
      try {
        const response = await fetch('http://localhost:8080/auth/signin', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify({
            email: 'admin@gmail.com',
            password: 'admin',
          }),
        });

        const data = await response.json();

        if (response.ok) {
          setToken(data.token);
          console.log('Token fetched successfully', data.token);
        } else {
          console.error('Authentication error:', data.error);
        }
      } catch (error) {
        console.error('Error fetching token:', error);
      }
    };

    fetchToken(); // Fetch token on component mount
  }, []);

  const [classes, setClasses] = useState([]);

  useEffect(() => {
	  const fetchData = async () => {
	    try {
        if (!token) {
          // If token is null, exit early to avoid making unauthorized request
          return;
        }
	      const response = await fetch('/classes', { headers: { Authorization: `Bearer ${token}` } });
	      const data = await response.json();
	      setClasses(data);
	    } catch (error) {
	      console.error('Error fetching data:', error);
        console.log('bearer token: ', token);
	    }
	  };

	  fetchData(); // Initial data fetch

	  
	  const intervalId = setInterval(fetchData, 3000);

	  // Clean up the interval when the component unmounts
	  return () => clearInterval(intervalId);
	}, [token]);

  return (
      <Router>
        <Navbar />
        <div className="App">
          <div className="App-body">
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
        </div>
      </Router>
  );
};

export default App;
