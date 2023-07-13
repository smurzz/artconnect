import React, {useState, useEffect} from 'react';
import {MenuBar} from '../components/MenuBar';
import {useDispatch, useSelector} from 'react-redux';
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';
import Footer from '../components/Footer';
import LoadingPage from '../components/LoadingPage';
import NotFoundPage from '../errors/NotFoundPage';
import '../../layout/css/homePublic.css';
import '../../layout/css/users.css';

import ProfilePhotoDefault from '../../images/user.png';
import ImageTMP from '../../images/placeholder.jpg';
import * as artworkActions from '../../../redux/artwork/ArtworkAction';
import { useParams } from 'react-router-dom';
function ArtworkDetailPage() {
    let { id } = useParams();
    const dispatch = useDispatch();
    const artworkData = useSelector(state => state.artwork);

    useEffect(() => {
            const fetchData = async () => {
                console.log("id form Url: "+ id);
                await dispatch(artworkActions.getArtwork(id));
                console.log(artworkData.artwork)
            };
            fetchData();
    }, [dispatch]);

    if (!artworkData.artworks && !artworkData.error) {
        return <LoadingPage/>;
    }

    if (artworkData.error) {
        return <NotFoundPage/>;
    }

    const handleSearchSubmit = async (event) => {
    };

    return (
        <div className="home-public-container">
            <MenuBar/>
            {/*Images*/}
            <section className="h-100 gradient-custom-2 bg-body-tertiary">
                <div className="container-md h-100 m-auto">
                    <div className="row d-flex justify-content-center align-items-center h-100">
                        <div className="col w-100">
                            <div className="card">
                                <div className="rounded-top text-white d-flex flex-row justify-content-center"
                                     style={{backgroundColor: '#212529', height: '250px'}}>
                                    <div className="ms-4 d-flex flex-column" style={{marginTop: '8rem', zIndex: '1'}}>
                                        <div className="mb-1">
                                            <img id="profileImage"
                                                 src="https://dummyimage.com/850x350/dee2e6/6c757d.jpg"
                                                 alt="Profile"
                                                 className="img-thumbnail"
                                                 style={{
                                                     width: '150px',
                                                     height: '150px',
                                                     objectFit: 'cover',
                                                     objectPosition: '50% 50%',
                                                     zIndex: '1'
                                                 }}/>
                                        </div>
                                    </div>
                                    <div className="ms-4 d-flex flex-column" style={{marginTop: '8rem', zIndex: '1'}}>
                                        <div className="mb-1">
                                            <img id="profileImage"
                                                 src="https://dummyimage.com/850x350/dee2e6/6c757d.jpg"
                                                 alt="Profile"
                                                 className="img-thumbnail"
                                                 style={{
                                                     width: '150px',
                                                     height: '150px',
                                                     objectFit: 'cover',
                                                     objectPosition: '50% 50%',
                                                     zIndex: '1'
                                                 }}/>
                                        </div>
                                    </div>
                                    <div className="ms-4 d-flex flex-column" style={{marginTop: '8rem', zIndex: '1'}}>
                                        <div className="mb-1">
                                            <img id="profileImage"
                                                 src="https://dummyimage.com/850x350/dee2e6/6c757d.jpg"
                                                 alt="Profile"
                                                 className="img-thumbnail"
                                                 style={{
                                                     width: '150px',
                                                     height: '150px',
                                                     objectFit: 'cover',
                                                     objectPosition: '50% 50%',
                                                     zIndex: '1'
                                                 }}/>
                                        </div>
                                    </div>
                                    <div className="ms-4 d-flex flex-column" style={{marginTop: '8rem', zIndex: '1'}}>
                                        <div className="mb-1">
                                            <img id="profileImage"
                                                 src="https://dummyimage.com/850x350/dee2e6/6c757d.jpg"
                                                 alt="Profile"
                                                 className="img-thumbnail"
                                                 style={{
                                                     width: '150px',
                                                     height: '150px',
                                                     objectFit: 'cover',
                                                     objectPosition: '50% 50%',
                                                     zIndex: '1'
                                                 }}/>
                                        </div>
                                    </div>
                                </div>

                            </div>
                        </div>
                    </div>
                </div>
            </section>
            {/*Header*/}
            <section className="h-100 gradient-custom-2 bg-body-tertiary">
                <div className="col w-100">
                    <div className="card">
            <header className=" bg-light border-bottom mb-4">
                <div className="container">
                    <div className="text-center my-5">
                        <h1 className="fw-bolder">{artworkData.artwork?.title}</h1>
                        <p className="lead mb-0">{artworkData.artwork?.description}</p>
                    </div>
                </div>
            </header>

            <div className="container">
                <div className="row">
                    <div className="col-lg-8">
                        {/*big Image*/}

                        <div className="card mb-4">
                            <img className="card-img-top" src="https://dummyimage.com/850x350/dee2e6/6c757d.jpg"/>
                        </div>
                    </div>

                    <div className="col-lg-4">
                        <div className="card mb-4">
                            <div className="card-header">Search</div>
                            <div className="card-body">
                                <div className="input-group">
                                    <input className="form-control" type="text" placeholder="Enter search term..."
                                           aria-label="Enter search term..." aria-describedby="button-search"/>
                                    <button className="btn btn-primary" id="button-search" type="button">Go!</button>
                                </div>
                            </div>
                        </div>
                        {artworkData.artwork?.materials?.map((material) => (
                            <div className="card mb-4">
                                <div className="card-header">Categories</div>
                                <div className="card-body">
                                    <div className="row">
                                        <div className="col-sm-6">
                                            <ul className="list-unstyled mb-0">
                                                <li><a href="#!">Web Design</a></li>
                                                <li><a href="#!">HTML</a></li>
                                                <li><a href="#!">Freebies</a></li>
                                            </ul>
                                        </div>
                                        <div className="col-sm-6">
                                            <ul className="list-unstyled mb-0">
                                                <li><a href="#!">JavaScript</a></li>
                                                <li><a href="#!">CSS</a></li>
                                                <li><a href="#!">Tutorials</a></li>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        ))}
                        <div className="card mb-4">
                            <div className="card-header">Side Widget</div>
                            <div className="card-body">You can put anything you want inside of these side widgets. They
                                are easy to use, and feature the Bootstrap 5 card component!
                            </div>
                        </div>
                    </div>
                </div>
            </div>
                    </div>
                </div>
            </section>
            <Footer/>
        </div>
    );
}

export default ArtworkDetailPage;
