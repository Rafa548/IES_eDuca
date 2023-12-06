// App.js
import React, { useState, useEffect } from 'react';
import { BrowserRouter as Router, Route, Link, Switch } from 'react-router-dom';
import logo from './logo.svg';
import './App.css';
import ClassPage from './ClassPage';
import Navbar from './Navbar';
import WebSocketService from './WebSocketService';
import { useAuth } from './AuthContext';



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
  const { token } = useAuth();
  //save token to local storage
  useEffect(() => { localStorage.setItem('token', token); }, [token]);
    //notifications
  /* const [notifications, setNotifications] = useState([]);
  const webSocketService = WebSocketService(onMessage);

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
  }, [webSocketService]); */



  

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
