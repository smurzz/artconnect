import React from 'react';
import { Link } from 'react-router-dom';
const Navigation = () => {
    return (
        <nav>
            <ul>
                <li>
                    <Link to="/">Home</Link>
                </li>
                <li>
                    <Link to="/Login">My Books</Link>
                </li>
                <li>
                    <Link to="/Register">Favorites</Link>
                </li>
            </ul>
        </nav>
    );
};
export default Navigation;