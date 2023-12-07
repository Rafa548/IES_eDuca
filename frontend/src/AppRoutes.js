import React from 'react';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import Login from './Login';
import ProfileStudent from './ProfileStudent';

const AppRoutes = () => {
    return (
        <Router>
            <Switch>
                <Route path="/login" component={Login} />
                <Route path="/profile-student" component={ProfileStudent} />
                {/* Add other routes for different user roles */}
            </Switch>
        </Router>
    );
};

export default AppRoutes;