import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';
import 'bootstrap/dist/css/bootstrap.min.css';
import Login from './Login';

// Create a root using ReactDOM.createRoot
const root = ReactDOM.createRoot(document.getElementById('root'));

// Render the Login component inside the root
root.render(
    <React.StrictMode>
        <Login />
    </React.StrictMode>
);

// Report web vitals (performance metrics)
reportWebVitals();

