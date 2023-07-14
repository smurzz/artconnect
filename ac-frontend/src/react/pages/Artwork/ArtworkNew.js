import React, { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { useNavigate } from "react-router-dom";
import { Button, Alert } from 'react-bootstrap';
import '../../layout/css/homePublic.css';
import { MenuBar } from '../components/MenuBar';
import Footer from '../components/Footer';
import LoadingPage from '../components/LoadingPage';
import HomePublic from '../HomePublic';
import ImageTMP from '../../images/placeholder.jpg';

import * as artworkAction from '../../../redux/artwork/ArtworkAction';
import * as authActions from '../../../redux/authentication/AuthenticationAction';
import RefreshTokenExpiredError from '../../../redux/exceptions/RefreshTokenExpiredError';

function ArtworkNew() {
    var userSession = JSON.parse(localStorage.getItem('userSession'));
    const dispatch = useDispatch();
    const navigate = useNavigate();
    const [artwork, setArtwork] = useState('');
    const [success, setSuccess] = useState(false);
    const [error, setError] = useState(false);
    const [successMessage, setSuccessMessage] = useState('');
    const [errorMessage, setErrorMessage] = useState('');
    const [selectedValues, setSelectedValues] = useState(new Set([]));
    const artworkData = useSelector(state => state.artwork);
    const [selectedFile, setSelectedFile] = useState(null);
    const [id, setId] = useState(null);
    const [isLoading, setIsLoading]= useState(false);
    /* add new profile photo */
    useEffect(() => {
        const fetchData = async () => {
            if (selectedFile) {
                const formData = new FormData();
                formData.append('file', selectedFile);
                await dispatch(artworkAction.addArtworkImage(id, formData));
            }
        };

        fetchData();

    }, [selectedFile, dispatch]);

    /* set a image-file for updating */
    const handleFileSelect = async (e) => {
        setSelectedFile(e.target.files[0]);
    }


    /* check if the response status is ok */
    useEffect(() => {
        const resSuccess = artworkData.status === 201 || artworkData.status === 200;
        setSuccess(resSuccess);
    }, [artworkData.status]);

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
        if (artworkData.error && artworkData?.error?.status !== 404) {
            setError(true);
            setErrorMessage(<Alert className="text-center m-4" key='f' variant='danger'>
                <p> {artworkData.error.message} </p>
            </Alert>)
        } else if (artworkData.error instanceof RefreshTokenExpiredError) {
            dispatch(authActions.logoutUser());
            navigate('/');
        } else {
            setErrorMessage(null);
        }
    }, [artworkData.error, dispatch, navigate]);

    /* update user profile */
    const handleSubmit = async (e) => {
        console.log("submit: "+ JSON.stringify(artwork))
        //await dispatch(artworkAction.createArtwork(artwork));
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
        setArtwork({ ...artwork, artDirections: Array.from(updatedValues) });
    };

    /*  if (userSession && !galleryData.ga) {
         return <LoadingPage />;
     } */

    if (!userSession) {
        return <HomePublic />;
    }

    return (
        <div className='h-100'>
            <MenuBar />
            <div className="container-md m-auto">
                <div className="row d-flex justify-content-center">
                    <div className="col w-100">
                            <h1 className="fw-light">Create Artwork</h1>
                        </div>

                        <div className="card-body p-4 text-black">
                            <div className='personal-info-container mb-2'>
                                <p className="lead fw-normal mb-1">Artwork Information</p>
                                <div className="p-2" style={{ backgroundColor: '#f8f9fa' }}>
                                    <div className="row mb-2">
                                        <div className="col">
                                            <div className="row mb-4">
                                                <div className="col-8">
                                                    <div className="form-outline">
                                                        <label className="form-label" htmlFor="fName">Titel</label>
                                                        <input
                                                            type="text"
                                                            id="fName"
                                                            className="form-control"
                                                            name='firstName'
                                                            value=""
                                                            onChange={(e) => setArtwork({
                                                                ...artwork,
                                                                title: e.target.value
                                                            })}
                                                        />
                                                    </div>
                                                </div>
                                                <div className="col">
                                                    <div className="form-outline">
                                                        <label className="form-label" htmlFor="lName">Year of
                                                            Creation</label>
                                                        <input
                                                            type="number"
                                                            className="form-control"
                                                            name="yearOfCreation"
                                                            value=""
                                                            onChange={(e) => setArtwork({
                                                                ...artwork,
                                                                yearOfCreation: e.target.value
                                                            })}
                                                        />
                                                    </div>
                                                </div>
                                                <div className="col">
                                                    <div className="form-outline">
                                                        <label className="form-label" htmlFor="lPrice">Price in Euro</label>
                                                        <input
                                                            type="number"
                                                            className="form-control"
                                                            name="yearOfCreation"
                                                            value=""
                                                            onChange={(e) => setArtwork({
                                                                ...artwork,
                                                                price: e.target.value
                                                            })}
                                                        />
                                                    </div>
                                                </div>
                                            </div>
                                            <div className="form-outline mb-2">
                                                <label className="form-label" for="description">Description</label>
                                                <textarea
                                                    type="text"
                                                    id="description"
                                                    className="form-control"
                                                    placeholder="Write about your gallery here"
                                                    style={{ height: "100px" }}
                                                    value={''}
                                                    onChange={async (e) => { setArtwork({ ...artwork, description: e.target.value }); }}
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
                            </div>
                        </div>
                    </div>
                </div>
            <Footer />
        </div>

    );
}

export default ArtworkNew;