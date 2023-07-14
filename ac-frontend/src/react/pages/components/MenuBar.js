import Button from 'react-bootstrap/Button';
import Container from 'react-bootstrap/Container';
import Form from 'react-bootstrap/Form';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import NavDropdown from 'react-bootstrap/NavDropdown';
import { useNavigate } from "react-router-dom";
import { useDispatch } from 'react-redux';
import { FaUserCircle } from 'react-icons/fa';

import Image from 'react-bootstrap/Image';
import { connect } from 'react-redux';

import Avatar from '../../../logo.svg'

import * as authActions from '../../../redux/authentication/AuthenticationAction';

export function MenuBar(props) {
    const dispatch = useDispatch();
    const navigate = useNavigate();

    var userSession = JSON.parse(localStorage.getItem('userSession'));

    const handleLogout = async (e) => {
        e.preventDefault();
        await dispatch(authActions.logoutUser());
        navigate('/');
    }

    if (userSession) {
        /* var logoutButton = (<Nav.Link onClick={handleLogout}>Logout</Nav.Link>); */
        var authUserOptions = (
            <NavDropdown align='end' title={<FaUserCircle size={36} />} id="basic-nav-dropdown" flip>
                <NavDropdown.Item href="/profile-edit">Profile</NavDropdown.Item>
                <NavDropdown.Item href="/gallery-edit">Gallery</NavDropdown.Item>
                <NavDropdown.Item href="/artwork-new">Artworks</NavDropdown.Item>
                <NavDropdown.Divider />
                <NavDropdown.Item onClick={handleLogout}>
                    Logout
                </NavDropdown.Item>
            </NavDropdown>
        );
    } else {
        var loginButton = (<Nav.Link href="/login">Login</Nav.Link>);
        var registerButton = (<Nav.Link href="/register">Register</Nav.Link>);
        /* var searchBar = (<Form className="d-flex">
            <Form.Control
                type="search"
                placeholder="Search by artworks"
                className="me-2"
                aria-label="Search"
            />
            <Button variant="outline-secondary">Search</Button>
        </Form>); */
    }
    return (
        <Navbar sticky="top" bg="dark" variant="dark" expand="lg">
            <Container fluid>
                <Navbar.Brand href={userSession ? "/home" : "/"}>ArtConnect</Navbar.Brand>
                <Navbar.Toggle aria-controls="navbarScroll" />
                <Navbar.Collapse id="navbarScroll">
                    <Nav className="me-auto">
                        {/* {searchBar} */}
                    </Nav>
                    <Nav
                        navbarScroll
                    >
                        <Nav.Link href="/home">Home</Nav.Link>
                        <Nav.Link href="/users">Users</Nav.Link>
                        <Nav.Link href="/artworks">Artwoks</Nav.Link>
                        {loginButton}
                        {registerButton}
                        {authUserOptions}
                    </Nav>
                </Navbar.Collapse>
            </Container>
        </Navbar>
    );
}