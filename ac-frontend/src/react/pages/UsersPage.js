import React, { useState, useEffect } from 'react';
import { MenuBar } from './components/MenuBar';
import { useDispatch, useSelector } from 'react-redux';
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';
import Footer from './components/Footer';
import LoadingPage from './components/LoadingPage';
import NotFoundPage from './errors/NotFoundPage';
import '../layout/css/homePublic.css';
import '../layout/css/users.css';

import ProfilePhotoDefault from '../images/user.png';

import ImageTMP from '../images/placeholder.jpg';

import * as userActions from '../../redux/user/UserAction';

function UsersPage() {
    const dispatch = useDispatch();

    const [searchValue, setSearchValue] = useState('');
    const userData = useSelector(state => state.users);

    useEffect(() => {
        if (!searchValue) {
            const fetchData = async () => {
                await dispatch(userActions.getUsers());
            };
            fetchData();
        }
    }, [dispatch, searchValue]);

    if (!userData.users && !userData.error) {
        return <LoadingPage />;
    }

    if (userData.error) {
        return <NotFoundPage />;
    }

    const handleSearchSubmit = async (event) => {
        event.preventDefault();
        var splitedSeachValue = searchValue.split(' ');
        console.log(splitedSeachValue);
        if (splitedSeachValue.length > 1) {
            await dispatch(userActions.getUsersByName(splitedSeachValue[0], splitedSeachValue[1]));
        } else {
            await dispatch(userActions.getUsersByName(null, splitedSeachValue[0]));
        }
    };

    return (
        <div className="home-public-container">
            <MenuBar />
            <div className="container text-center m-auto">
                <section className="py-3 text-center container">
                    <div className="row py-lg-5">
                        <div className="col-lg-6 col-md-8 mx-auto">
                            <h1 className="fw-light">Artists and art lovers</h1>
                        </div>
                        <Form className="d-flex mt-3" onSubmit={handleSearchSubmit}>
                            <Form.Control
                                type="search"
                                placeholder="Search by users"
                                className="me-2"
                                aria-label="Search"
                                value={searchValue}
                                onChange={async (e) => { setSearchValue(e.target.value); }}
                            />
                            <Button variant="outline-secondary" onClick={handleSearchSubmit}>Search</Button>
                        </Form>
                    </div>
                </section>

                <div className="row mt-5 mb-5">
                    {userData.users?.length > 0 ? (
                        userData.users.map((user, index) => (
                            <div className="col-lg-2"  key={index}>
                                <img
                                    src={user?.profilePhoto ? (`data:${user.profilePhoto?.contentType};base64,${user.profilePhoto?.image.data}`) : ProfilePhotoDefault}
                                    alt="Thumbnail"
                                    className="rounded-circle"
                                    style={{
                                        width: '140px',
                                        height: '140px',
                                        objectFit: 'cover',
                                        objectPosition: 'center',
                                    }}
                                />
                                <h4 className="fw-normal">{user.firstname} {user.lastname}</h4>
                                {/* <p>Some representative placeholder content for the three columns of text below the carousel. This is the first column.</p> */}
                                <p><a className="btn btn-secondary" href={`/user/${user.id}`}>View profile &raquo;</a></p>
                            </div>
                        ))
                    ) : (<p>No users found.</p>)}
                </div>
            </div>
            <Footer />
        </div>
    );
}

export default UsersPage;
