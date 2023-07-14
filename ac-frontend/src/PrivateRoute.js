import React from 'react';
import { BrowserRouter as Route, Navigate } from 'react-router-dom';

const PrivateRoute = ({ path, element: Component }) => {
    var userSession = JSON.parse(localStorage.getItem('userSession'));
    var token = userSession.accessToken;

    const isAuthenticated = () => {
        return token !== null;
    };

    return (
        <Route
            exact path={path}
            element={isAuthenticated() ? <Component /> : <Navigate to="/login" replace />}
        />
    );
}

export default PrivateRoute;