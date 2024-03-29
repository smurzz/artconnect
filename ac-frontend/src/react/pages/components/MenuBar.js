import Container from 'react-bootstrap/Container';
import { useNavigate } from "react-router-dom";
import { useDispatch } from 'react-redux';
import { FaUserCircle } from 'react-icons/fa';
import {Nav, Navbar, NavDropdown} from 'react-bootstrap';

import * as authActions from '../../../redux/authentication/AuthenticationAction';

export default function MenuBar(props) {
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
                <NavDropdown.Item href="/gallery-edit">Gallery</NavDropdown.Item>
                <NavDropdown.Item href="/profile-edit">
                    Profile
                </NavDropdown.Item>
                <NavDropdown.Divider />
                <NavDropdown.Item onClick={handleLogout}>
                    Logout
                </NavDropdown.Item>
            </NavDropdown>
        );
    } else {
        var loginButton = (<Nav.Link className='link-light' href="/login">Login</Nav.Link>);
        var registerButton = (<Nav.Link className='link-light' href="/register">Register</Nav.Link>);
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
                        <Nav.Link href="/home">Home</Nav.Link>
                        <Nav.Link href="/users">Users</Nav.Link>
                        <Nav.Link href="/artworks">Artwoks</Nav.Link>
                    </Nav>
                    <Nav
                        navbarScroll
                    >
                        {/* <NavDropdown title="Link" id="navbarScrollingDropdown">
                            <NavDropdown.Item href="#action3">Action</NavDropdown.Item>
                            <NavDropdown.Item href="#action4">
                                Another action
                            </NavDropdown.Item>
                            <NavDropdown.Divider />
                            <NavDropdown.Item href="#action5">
                                Something else here
                            </NavDropdown.Item>
                        </NavDropdown>
                        <Nav.Link href="#" disabled>
                            Link
                        </Nav.Link> */}
                        {loginButton}
                        {registerButton}
                        {authUserOptions}
                    </Nav>
                </Navbar.Collapse>
            </Container>
        </Navbar>
    );
}