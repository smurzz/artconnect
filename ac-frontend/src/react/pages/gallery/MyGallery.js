
import React, { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { useNavigate } from "react-router-dom";
import { Button, Alert } from 'react-bootstrap';
import MenuBar from '../components/MenuBar';
import Footer from '../components/Footer';
import HomePublic from '../home/HomePublic';
import DeleteGalleryModel from '../components/gallery/DeleteGalleryModel';

import '../../layout/css/homePublic.css';

import * as galleryActions from '../../../redux/gallery/GalleryAction';
import * as authActions from '../../../redux/authentication/AuthenticationAction';
import RefreshTokenExpiredError from '../../../redux/exceptions/RefreshTokenExpiredError';
import LoadingPage from '../home/LoadingPage';

function MyGallery() {
    var userSession = JSON.parse(localStorage.getItem('userSession'));

    const dispatch = useDispatch();
    const navigate = useNavigate();
    const galleryData = useSelector(state => state.gallery);
    const [gallery, setGallery] = useState(galleryData.gallery || '');
    const [success, setSuccess] = useState(false);
    const [error, setError] = useState(false);
    const [successMessage, setSuccessMessage] = useState('');
    const [errorMessage, setErrorMessage] = useState('');
    const [selectedValues, setSelectedValues] = useState(new Set(gallery.categories || []));

    /* get gallery from a current user */
    useEffect(() => {
        const fetchData = async () => {
            await dispatch(galleryActions.getMyGallery());
        };
        fetchData();
    }, [dispatch]);

    useEffect(() => {
        if (galleryData.gallery) {
            setGallery(galleryData.gallery);
            setSelectedValues(new Set(galleryData.gallery?.categories || []));
        }
        console.log(gallery);
    }, [galleryData.gallery]);

    /* check if the response status is ok */
    useEffect(() => {
        const resSuccess = galleryData.status === 201 || galleryData.status === 200;
        setSuccess(resSuccess);
    }, [galleryData.status]);

    /* put a message by success */
    useEffect(() => {
        if (success) {
            setErrorMessage('');
            setSuccessMessage(<Alert className="text-center m-4" key='success' variant='success'>
                <p> Your changes are successfully saved! </p>
            </Alert>);
        } else {
            setSuccessMessage(null);
        }
    }, [success]);

    /* put a message by error */
    useEffect(() => {
        if (galleryData.error && galleryData?.error?.status !== 404) {
            setError(true);
            setErrorMessage(<Alert className="text-center m-4" key='f' variant='danger'>
                <p> {galleryData.error.message} </p>
            </Alert>)
        } else if (galleryData.error instanceof RefreshTokenExpiredError) {
            dispatch(authActions.logoutUser());
            navigate('/');
        } else {
            setErrorMessage(null);
        }
    }, [galleryData.error, dispatch, navigate]);

    /* update user profile */
    const handleSubmit = async (e) => {
        if (!gallery.title) {
            setErrorMessage(<Alert className="text-center m-4" key='f' variant='danger'>
                <p> Title cannot be empty. </p>
            </Alert>);
            return;
        }

        const galleryRequest = (galleryData && (({ id, artworks, ownerId, ownerName, ranking, ...rest }) => rest)(gallery)) || {};
        galleryData.gallery ? await dispatch(galleryActions.editGallery(gallery.id, galleryRequest)) : await dispatch(galleryActions.createGallery(gallery));
    }

    /* add / remove categories */
    const handleCheckboxChange = (e) => {
        const { value, checked } = e.target;
        const updatedValues = new Set(selectedValues);

        if (checked) {
            updatedValues.add(value);
        } else {
            updatedValues.delete(value);
        }

        setSelectedValues(updatedValues);
        setGallery({ ...gallery, categories: Array.from(updatedValues) });
    };

    if (galleryData.pending && !gallery) {
        return <LoadingPage />;
    }

    if (!userSession) {
        return <HomePublic />;
    }

    return (
        <div className='h-100'>
            <MenuBar />
            <div className="container-md m-auto">
                <div className="row d-flex justify-content-center">
                    <div className="col w-100">
                        <div className="rounded-top text-white d-flex flex-row align-items-center justify-content-center" style={{ backgroundColor: '#212529', height: '250px' }}>
                            <h1 className="fw-light">{(gallery && gallery.id) ? ("Update Your Gallery") : ("Create Gallery")}</h1>
                        </div>
                        <div className="card-body p-4 text-black">
                            <a as="button" class="btn btn-dark mb-3" href='javascript:history.back()'>Back</a>
                            <div className='personal-info-container mb-2'>
                                <p className="lead fw-normal mb-1">Gallery Information</p>
                                <div className="p-2" style={{ backgroundColor: '#f8f9fa' }}>
                                    <div className="row mb-2">
                                        <div className="col">
                                            <div className="form-outline mb-4">
                                                <label className="form-label" for="title">Title</label>
                                                <input
                                                    type="text"
                                                    id="title"
                                                    className="form-control"
                                                    value={gallery.title || ''}
                                                    onChange={async (e) => { setGallery({ ...gallery, title: e.target.value }); }}
                                                    required
                                                />
                                            </div>
                                            <div className="form-outline mb-2">
                                                <label className="form-label" for="description">Description</label>
                                                <textarea
                                                    type="text"
                                                    id="description"
                                                    className="form-control"
                                                    placeholder="Write about your gallery here"
                                                    style={{ height: "100px" }}
                                                    value={gallery.description || ''}
                                                    onChange={async (e) => { setGallery({ ...gallery, description: e.target.value }); }}
                                                />
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div className='categories-container mb-2'>
                                <p className="lead fw-normal mb-1">Categories</p>
                                <div className="p-2" style={{ backgroundColor: '#f8f9fa' }}>
                                    <div className="row mb-4">
                                        <div className="col">
                                            <div className="form-outline mb-4">
                                                <input
                                                    type="checkbox"
                                                    className="btn-check"
                                                    id="btn-check-2-outlined-print"
                                                    value="PRINT"
                                                    checked={selectedValues.has('PRINT')}
                                                    onChange={handleCheckboxChange}
                                                />
                                                <label className="btn btn-outline-secondary m-2" htmlFor="btn-check-2-outlined-print">Print</label>

                                                <input
                                                    type="checkbox"
                                                    className="btn-check"
                                                    id="btn-check-2-outlined-painting"
                                                    value="PAINTING"
                                                    checked={selectedValues.has('PAINTING')}
                                                    onChange={handleCheckboxChange}
                                                />
                                                <label className="btn btn-outline-secondary m-2" htmlFor="btn-check-2-outlined-painting">Painting</label>

                                                <input
                                                    type="checkbox"
                                                    className="btn-check"
                                                    id="btn-check-2-outlined-drawing"
                                                    value="DRAWING_ILLUSTRATION"
                                                    checked={selectedValues.has('DRAWING_ILLUSTRATION')}
                                                    onChange={handleCheckboxChange}
                                                />
                                                <label className="btn btn-outline-secondary m-2" htmlFor="btn-check-2-outlined-drawing">Drawing Illustration</label>

                                                <input
                                                    type="checkbox"
                                                    className="btn-check"
                                                    id="btn-check-2-outlined-photography"
                                                    value="PHOTOGRAPHY"
                                                    checked={selectedValues.has('PHOTOGRAPHY')}
                                                    onChange={handleCheckboxChange}
                                                />
                                                <label className="btn btn-outline-secondary m-2" htmlFor="btn-check-2-outlined-photography">Photography</label>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div className='result-container mb-2'>
                                {successMessage}
                                {errorMessage}
                            </div>

                            <div class="d-grid gap-2">
                                <Button variant="dark" size="lg" onClick={handleSubmit}>Save</Button>
                                {galleryData.gallery ? <DeleteGalleryModel galleryId={galleryData.gallery.id} /> : null}
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <Footer />
        </div>

    );
}

export default MyGallery;