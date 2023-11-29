import React from 'react';
import './Navbar.css';

const Navbar = () => {
    return (
        <nav className="navbar">
            <div className="navbar-brand">
                <span>eDuca</span>
            </div>
            <ul className="navbar-nav">
                <li>
                    <span className="nav-item">Home</span>
                    <span className="nav-item">Profile</span>
                    <span className="nav-item">Alerts</span>
                </li>
            </ul>
        </nav>
    );
};

export default Navbar;