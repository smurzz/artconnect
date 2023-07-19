import React, { useState, useEffect, useRef } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import { Button, Alert } from 'react-bootstrap';
import MenuBar from '../components/MenuBar';
import Footer from '../components/Footer';
import LoadingPage from '../home/LoadingPage';
import NotFoundPage from '../errors/NotFoundPage';
import HomePublic from '../home/HomePublic';
import DeleteArtworkModel from '../components/artwork/DeleteArtworkModel';
import { InputTags } from "react-bootstrap-tagsinput";

import ImageTMP from '../../images/no-image.png';

import "react-bootstrap-tagsinput/dist/index.css";
import '../../layout/css/homePublic.css';
import '../../layout/css/users.css';
import 'bootstrap-icons/font/bootstrap-icons.css';

import * as artworkActions from '../../../redux/artwork/ArtworkAction';
import RefreshTokenExpiredError from '../../../redux/exceptions/RefreshTokenExpiredError';

function ArtworkEdit() {
    var userSession = JSON.parse(localStorage.getItem('userSession'));
    let { id } = useParams();
    const dispatch = useDispatch();
    const navigate = useNavigate();

    const artworkData = useSelector(state => state.artwork);
    const [artwork, setArtwork] = useState(null);
    const [selectedFile, setSelectedFile] = useState(null);
    const [success, setSuccess] = useState(false);
    const [error, setError] = useState(false);
    const [successMessage, setSuccessMessage] = useState('');
    const [errorMessage, setErrorMessage] = useState('');
    const [selectedValues, setSelectedValues] = useState(new Set());
    const isUpdatedRef = useRef(false);

    /* get artwork for edit */
    useEffect(() => {
        const fetchData = async () => {
            await dispatch(artworkActions.getArtwork(id));
            isUpdatedRef.current = false;
        };
        fetchData();
    }, [dispatch, isUpdatedRef.current]);

    useEffect(() => {
        if (artworkData.artwork) {
            setArtwork(artworkData.artwork);
            if (artworkData.artwork.artDirections) {
                setSelectedValues(new Set(artworkData.artwork.artDirections));
            }
        }
        console.log(artwork);
    }, [artworkData.artwork]);

    /* add new profile photo */
    useEffect(() => {
        const fetchData = async () => {
            if (selectedFile) {
                const formData = new FormData();
                formData.append('file', selectedFile);
                await dispatch(artworkActions.addArtworkImage(id, formData));
                error ? isUpdatedRef.current = false : isUpdatedRef.current = true;
            }
        };
        fetchData();
    }, [selectedFile, dispatch]);

    /* check if the response status is ok */
    useEffect(() => {
        const resSuccess = artworkData.status === 200 || artworkData.status === 201 ||  artworkData.status === 204;
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
            setSuccessMessage('');
            setErrorMessage(<Alert className="text-center m-4" key='f' variant='danger'>
                <p> {artworkData.error.message} </p>
            </Alert>)
        } else if (artworkData.error instanceof RefreshTokenExpiredError) {
            dispatch(artworkData.logoutUser());
            navigate('/');
        } else {
            setErrorMessage(null);
        }
    }, [artworkData.error, dispatch, navigate]);

    const artworkDataValid = () => {
        const errArray = [];
        const currentDate = new Date();
        const currentYear = currentDate.getFullYear();

        const isValidTitle = artwork?.title?.length >= 0;
        const isValidYearOfCreation = !artwork?.yearOfCreation || (artwork?.yearOfCreation <= currentYear && artwork?.yearOfCreation >= 0);
        const isValidPrice = !artwork?.price || artwork?.price >= 0;
        const isValidHeight = !artwork?.dimension?.height || artwork?.dimension?.height >= 0.0;
        const isValidWidth = !artwork?.dimension?.width || artwork?.dimension?.width >= 0.0;
        const isValidDepth = !artwork?.dimension?.depth || artwork?.dimension?.depth >= 0.0;
        const isValidTags = !artwork?.tags || artwork?.tags?.length <= 10;
        const isValidMaterials = !artwork?.materials || artwork?.materials?.length <= 10;

        if (!artwork?.title?.trim() || !isValidTitle) {
            errArray.push("Title cannot be empty.")
        }

        if (!isValidTags) {
            errArray.push("Tags can have at most 10 values")
        }

        if (!isValidMaterials) {
            errArray.push("Materials can have at most 10 values")
        }

        if (!isValidYearOfCreation) {
            errArray.push("Year cannot be greater than the current year or negative");
        }

        if (!isValidHeight) {
            errArray.push("Height can not be negative");
        }

        if (!isValidWidth) {
            errArray.push("Width can not be negative");
        }

        if (!isValidDepth) {
            errArray.push("Depth can not be negative");
        }

        if (!isValidPrice) {
            errArray.push("Price can not be negative");
        }

        if (errArray.length > 0) {
            setSuccessMessage('');
            setErrorMessage(<Alert className="alarm text-center mt-3" variant='danger'>{errArray.map(str => <p>{str}</p>)}</Alert>);
            return false;
        } else {
            return true;
        }
    }

    const handleSubmit = async (e) => {
        const artworkDataValidTest = artworkDataValid();
        if (artworkDataValidTest) {
            const artworkRequest = (artworkData && (({ id, comments, images, likes, createdAt, galleryId, galleryTitle, ownerId, ownerName, ...rest }) => rest)(artwork)) || {};
            await dispatch(artworkActions.editArtwork(id, artworkRequest));
        }
    };

    const handleRemoveImage = async (imageId) => {
        if (imageId) {
            await dispatch(artworkActions.deleteImageInArtworkById(id, imageId));
            error ? isUpdatedRef.current = false : isUpdatedRef.current = true;
        }
    }

    const handleCheckboxChange = async (e) => {
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

    const handleDimensionChange = (property, value) => {
        setArtwork((prevArtwork) => ({
            ...prevArtwork,
            dimension: {
                ...prevArtwork.dimension,
                [property]: parseFloat(value)
            }
        }));
    };

    const handleFileSelect = async (e) => {
        setSelectedFile(e.target.files[0]);
    }

    if (error && artworkData?.error?.status === 404) {
        return <NotFoundPage />;
    }

    if (artworkData.pending) {
        return <LoadingPage />;
    }

    if (!userSession) {
        return <HomePublic />;
    }

    return (
        <div>
            <MenuBar />
            <section className="h-100 gradient-custom-2 bg-body-tertiary">
                <div className="container-md h-100 m-auto">
                    <div className="row row-cols-1 row-cols-md-5 g-4" style={{ backgroundColor: '#212529', minHeight: '250px', padding: ' 0 2rem 2rem 2rem' }}>
                        <div className="d-flex flex-column align-items-center">
                            <div className='mb-2' style={{ zIndex: '1', position: 'relative' }}>
                                <img id="profileImage"
                                    src={artwork?.images?.length > 0 ? (`data:${artwork?.images[0]?.contentType};base64,${artwork?.images[0]?.image.data}`) : ImageTMP}
                                    alt="Profile"
                                    className="img-thumbnail"
                                    style={{
                                        minWidth: '200px',
                                        height: '200px',
                                        objectFit: 'cover',
                                        objectPosition: '50% 50%',
                                        zIndex: '1'
                                    }} />
                                {artwork?.images?.length > 0 && (
                                    <div
                                        style={{
                                            position: 'absolute',
                                            top: '8px',
                                            right: '8px',
                                            zIndex: '2',
                                            cursor: 'pointer',
                                            color: 'white'
                                        }}
                                        onClick={() => handleRemoveImage(artwork.images[0].id)}
                                    >
                                        <i className="bi bi-trash"></i>
                                    </div>
                                )}
                            </div>
                            {artwork?.images?.length < 5 ? <div className="btn btn-outline-light w-100 btn-sm" style={{ zIndex: '1' }}>
                                <label className="form-label m-1" htmlFor="fileInput">Add Picture</label>
                                <input
                                    type="file"
                                    className="form-control d-none"
                                    id="fileInput"
                                    accept="image/png, image/jpeg"                                  
                                    onChange={handleFileSelect}
                                />
                            </div> : null }
                        </div>
                        <div className="d-flex flex-column align-items-center">
                            <div className='mb-2' style={{ zIndex: '1', position: 'relative' }}>
                                <img
                                    id="profileImage"
                                    src={artwork?.images?.length > 1 ? (`data:${artwork?.images[1]?.contentType};base64,${artwork?.images[1]?.image.data}`) : ImageTMP}
                                    alt="Profile"
                                    className="img-thumbnail"
                                    style={{
                                        height: '150px',
                                        objectFit: 'cover',
                                        objectPosition: '50% 50%',
                                        zIndex: '1'
                                    }}
                                />
                                {artwork?.images?.length > 1 && (
                                    <div
                                        style={{
                                            position: 'absolute',
                                            top: '8px',
                                            right: '8px',
                                            zIndex: '2',
                                            cursor: 'pointer',
                                            color: 'white'
                                        }}
                                        onClick={() => handleRemoveImage(artwork.images[1].id)}
                                    >
                                        <i className="bi bi-trash"></i>
                                    </div>
                                )}
                            </div>

                        </div>
                        <div className="d-flex flex-column align-items-center">
                            <div className='mb-2' style={{ zIndex: '1', position: 'relative' }}>
                                <img
                                    id="profileImage"
                                    src={artwork?.images?.length > 2 ? (`data:${artwork?.images[2]?.contentType};base64,${artwork?.images[2]?.image.data}`) : ImageTMP}
                                    alt="Profile"
                                    className="img-thumbnail"
                                    style={{
                                        height: '150px',
                                        objectFit: 'cover',
                                        objectPosition: '50% 50%',
                                        zIndex: '1'
                                    }}
                                />
                                {artwork?.images?.length > 2 && (
                                    <div
                                        style={{
                                            position: 'absolute',
                                            top: '8px',
                                            right: '8px',
                                            zIndex: '2',
                                            cursor: 'pointer',
                                            color: 'white'
                                        }}
                                        onClick={() => handleRemoveImage(artwork?.images[2]?.id)}
                                    >
                                        <i className="bi bi-trash"></i>
                                    </div>
                                )}
                            </div>

                        </div>
                        <div className="d-flex flex-column align-items-center">
                            <div className='mb-2' style={{ zIndex: '1', position: 'relative' }}>
                                <img
                                    id="profileImage"
                                    src={artwork?.images?.length > 3 ? (`data:${artwork?.images[3]?.contentType};base64,${artwork?.images[3]?.image.data}`) : ImageTMP}
                                    alt="Profile"
                                    className="img-thumbnail"
                                    style={{
                                        /* width: '150px', */
                                        height: '150px',
                                        objectFit: 'cover',
                                        objectPosition: '50% 50%',
                                        zIndex: '1'
                                    }}
                                />
                                {artwork?.images?.length > 3 && (
                                    <div
                                        style={{
                                            position: 'absolute',
                                            top: '8px',
                                            right: '8px',
                                            zIndex: '2',
                                            cursor: 'pointer',
                                            color: 'white'
                                        }}
                                        onClick={() => handleRemoveImage(artwork.images[3].id)}
                                    >
                                        <i className="bi bi-trash"></i>
                                    </div>
                                )}
                            </div>

                        </div>
                        <div className="d-flex flex-column align-items-center">
                            <div className='mb-2' style={{ zIndex: '1', position: 'relative' }}>
                                <img
                                    id="profileImage"
                                    src={artwork?.images?.length > 4 ? (`data:${artwork?.images[4]?.contentType};base64,${artwork?.images[4]?.image.data}`) : ImageTMP}
                                    alt="Profile"
                                    className="img-thumbnail"
                                    style={{
                                        height: '150px',
                                        objectFit: 'cover',
                                        objectPosition: '50% 50%',
                                        zIndex: '1'
                                    }}
                                />
                                {artwork?.images?.length > 4 && (
                                    <div
                                        style={{
                                            position: 'absolute',
                                            top: '8px',
                                            right: '8px',
                                            zIndex: '2',
                                            cursor: 'pointer',
                                            color: 'white'
                                        }}
                                        onClick={() => handleRemoveImage(artwork.images[4].id)}
                                    >
                                        <i className="bi bi-trash"></i>
                                    </div>
                                )}
                            </div>

                        </div>
                    </div>
                    <div className="row d-flex justify-content-center align-items-center h-100">
                        <div className="col w-100">
                            <div className="card">
                                <div className="card-body p-4 text-black">
                                    <a as="button" class="btn btn-dark mb-3" href='javascript:history.back()'>Back</a>
                                    <p className="lead fw-normal mb-1 my-2">About the Artwork</p>

                                    <div className="p-4" style={{ backgroundColor: '#f8f9fa' }}>
                                        <div className="row mb-4">
                                            <div className="col-8">
                                                <div className="form-outline">
                                                    <label className="form-label" htmlFor="title">Titel</label>
                                                    <input
                                                        type="text"
                                                        id="title"
                                                        className="form-control"
                                                        name='title'
                                                        value={artwork?.title}
                                                        onChange={async (e) => { setArtwork({ ...artwork, title: e.target.value }); }}
                                                    />
                                                </div>
                                            </div>
                                            <div className="col">
                                                <div className="form-outline">
                                                    <label className="form-label" htmlFor="yearOfCreation">Year of
                                                        Creation</label>
                                                    <input
                                                        type="number"
                                                        className="form-control"
                                                        name="yearOfCreation"
                                                        value={artwork?.yearOfCreation}
                                                        onChange={async (e) => setArtwork({
                                                            ...artwork,
                                                            yearOfCreation: e.target.value
                                                        })}
                                                    />
                                                </div>
                                            </div>
                                            <div className="col">
                                                <div className="form-outline">
                                                    <label className="form-label" htmlFor="price">Price in Euro</label>
                                                    <input
                                                        type="number"
                                                        className="form-control"
                                                        name="price"
                                                        value={artwork?.price}
                                                        onChange={async (e) => setArtwork({
                                                            ...artwork,
                                                            price: e.target.value
                                                        })}
                                                    />
                                                </div>
                                            </div>
                                        </div>
                                        <div className="row mb-4">
                                            <div className="col">
                                                <div className="form-outline">
                                                    <label className="form-label" htmlFor="height">Height in cm</label>
                                                    <input
                                                        type="number"
                                                        className="form-control mr-2"
                                                        name="height"
                                                        value={artwork?.dimension?.height}
                                                        onChange={async (e) => handleDimensionChange("height", e.target.value)}
                                                    />

                                                </div>
                                            </div>
                                            <div className="col">
                                                <div className="form-outline">
                                                    <label className="form-label" htmlFor="width">Width in cm</label>
                                                    <input
                                                        type="number"
                                                        className="form-control"
                                                        name="width"
                                                        value={artwork?.dimension?.width}
                                                        onChange={async (e) => handleDimensionChange("width", e.target.value)}
                                                    />
                                                </div>
                                            </div>
                                            <div className="col">
                                                <div className="form-outline">
                                                    <label className="form-label" htmlFor="depth">Depth in cm</label>
                                                    <input
                                                        type="number"
                                                        className="form-control"
                                                        name="depth"
                                                        value={artwork?.dimension?.depth}
                                                        onChange={async (e) => handleDimensionChange("depth", e.target.value)}
                                                    />
                                                </div>
                                            </div>
                                        </div>
                                        <div className="row mb-4">
                                            <div className="col">
                                                <div className="form-outline">
                                                    <label className="form-label" htmlFor="location">Location</label>
                                                    <input
                                                        type="text"
                                                        id="location"
                                                        className="form-control"
                                                        name='location'
                                                        value={artwork?.location}
                                                        onChange={async (e) => setArtwork({
                                                            ...artwork,
                                                            location: e.target.value
                                                        })}
                                                    />
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div className="description-container mb-5">
                                        <p className="lead fw-normal mb-1 mt-3">Description</p>
                                        <div className="p-4" style={{ backgroundColor: '#f8f9fa' }}>
                                            <div className="form-group">
                                                <textarea
                                                    className="form-control"
                                                    name='description'
                                                    id="formControlTextarea"
                                                    rows="3"
                                                    value={artwork?.description}
                                                    onChange={async (e) => setArtwork({ ...artwork, description: e.target.value })}
                                                >
                                                </textarea>
                                            </div>
                                        </div>
                                    </div>
                                    <div className="tags-container mb-5">
                                        <p className="lead fw-normal mb-1 mt-3">Tags</p>
                                        <div className="input-group">

                                            <InputTags
                                                values={artwork?.tags || []}
                                                onTags={(value) => setArtwork({ ...artwork, tags: value.values })}
                                            />
                                            <button
                                                className="btn btn-outline-secondary"
                                                type="button"
                                                data-testid="button-clearAll"
                                                onClick={() => {
                                                    setArtwork({ ...artwork, tags: [] });
                                                }}
                                            >
                                                Delete all
                                            </button>
                                        </div>
                                    </div>
                                    <div className="materials-container mb-5">
                                        <p className="lead fw-normal mb-1 mt-3">Materials</p>
                                        <div className="input-group">
                                            <InputTags
                                                /* style={{ backgroundColor: "red" }} */
                                                values={artwork?.materials || []}
                                                onTags={(value) => setArtwork({ ...artwork, materials: value.values })}
                                            />
                                            <button
                                                className="btn btn-outline-secondary"
                                                type="button"
                                                data-testid="button-clearAll"
                                                onClick={() => {
                                                    setArtwork({ ...artwork, materials: [] });
                                                }}
                                            >
                                                Delete all
                                            </button>
                                        </div>
                                    </div>
                                    <div className='art-directions-container mb-5'>
                                        <p className="lead fw-normal mb-1">Art Directions</p>
                                        <div className="p-2" style={{ backgroundColor: '#f8f9fa' }}>
                                            <div className="row mb-4">
                                                <div className="col">
                                                    <div className="form-outline mb-4">
                                                        <input
                                                            type="checkbox"
                                                            className="btn-check"
                                                            id="btn-check-1-outlined-print"
                                                            value="ABSTRACT"
                                                            checked={selectedValues.has('ABSTRACT')}
                                                            onChange={handleCheckboxChange}
                                                        />
                                                        <label className="btn btn-outline-secondary m-2" htmlFor="btn-check-1-outlined-print">ABSTRACT</label>

                                                        <input
                                                            type="checkbox"
                                                            className="btn-check"
                                                            id="btn-check-2-outlined-print"
                                                            value="REALISM"
                                                            checked={selectedValues.has('REALISM')}
                                                            onChange={handleCheckboxChange}
                                                        />
                                                        <label className="btn btn-outline-secondary m-2" htmlFor="btn-check-2-outlined-print">REALISM</label>

                                                        <input
                                                            type="checkbox"
                                                            className="btn-check"
                                                            id="btn-check-3-outlined-print"
                                                            value="IMPRESSIONISM"
                                                            checked={selectedValues.has('IMPRESSIONISM')}
                                                            onChange={handleCheckboxChange}
                                                        />
                                                        <label className="btn btn-outline-secondary m-2" htmlFor="btn-check-3-outlined-print">IMPRESSIONISM</label>

                                                        <input
                                                            type="checkbox"
                                                            className="btn-check"
                                                            id="btn-check-4-outlined-print"
                                                            value="SURREALISM"
                                                            checked={selectedValues.has('SURREALISM')}
                                                            onChange={handleCheckboxChange}
                                                        />
                                                        <label className="btn btn-outline-secondary m-2" htmlFor="btn-check-4-outlined-print">SURREALISM</label>

                                                        <input
                                                            type="checkbox"
                                                            className="btn-check"
                                                            id="btn-check-5-outlined-print"
                                                            value="EXPRESSIONISM"
                                                            checked={selectedValues.has('EXPRESSIONISM')}
                                                            onChange={handleCheckboxChange}
                                                        />
                                                        <label className="btn btn-outline-secondary m-2" htmlFor="btn-check-5-outlined-print">EXPRESSIONISM</label>

                                                        <input
                                                            type="checkbox"
                                                            className="btn-check"
                                                            id="btn-check-6-outlined-print"
                                                            value="MINIMALISM"
                                                            checked={selectedValues.has('MINIMALISM')}
                                                            onChange={handleCheckboxChange}
                                                        />
                                                        <label className="btn btn-outline-secondary m-2" htmlFor="btn-check-6-outlined-print">MINIMALISM</label>

                                                        <input
                                                            type="checkbox"
                                                            className="btn-check"
                                                            id="btn-check-7-outlined-print"
                                                            value="CUBISM"
                                                            checked={selectedValues.has('CUBISM')}
                                                            onChange={handleCheckboxChange}
                                                        />
                                                        <label className="btn btn-outline-secondary m-2" htmlFor="btn-check-7-outlined-print">CUBISM</label>

                                                        <input
                                                            type="checkbox"
                                                            className="btn-check"
                                                            id="btn-check-8-outlined-print"
                                                            value="POP_ART"
                                                            checked={selectedValues.has('POP_ART')}
                                                            onChange={handleCheckboxChange}
                                                        />
                                                        <label className="btn btn-outline-secondary m-2" htmlFor="btn-check-8-outlined-print">POP ART</label>

                                                        <input
                                                            type="checkbox"
                                                            className="btn-check"
                                                            id="btn-check-9-outlined-print"
                                                            value="CONCEPTUAL_ART"
                                                            checked={selectedValues.has('CONCEPTUAL_ART')}
                                                            onChange={handleCheckboxChange}
                                                        />
                                                        <label className="btn btn-outline-secondary m-2" htmlFor="btn-check-9-outlined-print">CONCEPTUAL ART</label>

                                                        <input
                                                            type="checkbox"
                                                            className="btn-check"
                                                            id="btn-check-10-outlined-print"
                                                            value="STREET_ART_GRAFFITI"
                                                            checked={selectedValues.has('STREET_ART_GRAFFITI')}
                                                            onChange={handleCheckboxChange}
                                                        />
                                                        <label className="btn btn-outline-secondary m-2" htmlFor="btn-check-10-outlined-print">STREET ART/GRAFFITI</label>

                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    {successMessage}
                                    {errorMessage}
                                    <div className="d-grid gap-2">
                                        <Button variant="dark" size="lg" onClick={handleSubmit}>Save</Button>
                                        {artwork ? <DeleteArtworkModel artworkId={artwork.id} /> : null}
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
            <Footer />
        </div>);
}

export default ArtworkEdit;