import React, {useEffect, useState} from 'react';
import {useDispatch, useSelector} from 'react-redux';
import {useNavigate} from "react-router-dom";
import {Button, Alert} from 'react-bootstrap';
import '../../layout/css/homePublic.css';
import {MenuBar} from '../components/MenuBar';
import Footer from '../components/Footer';
import LoadingPage from '../components/LoadingPage';
import HomePublic from '../HomePublic';
import ImageTMP from '../../images/placeholder.jpg';


import * as artworkAction from '../../../redux/artwork/ArtworkAction';
import * as authActions from '../../../redux/authentication/AuthenticationAction';
import RefreshTokenExpiredError from '../../../redux/exceptions/RefreshTokenExpiredError';
import {InputTags} from "react-bootstrap-tagsinput";


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

    const [isLoading, setIsLoading] = useState(false);
    /* add new profile photo */

    const fetchData = async () => {
        if (selectedFile && artworkData.artwork) {
            const formData = new FormData();
            formData.append('file', selectedFile);
            await dispatch(artworkAction.addArtworkImage(artworkData.artwork.id, formData));
            navigate("/home")
        }
    };


    /* set a image-file for updating */
    const handleFileSelect = async (e) => {
        setSelectedFile(e.target.files[0]);
    }

    /* check if the response status is ok */
    useEffect(() => {
        const resSuccess = artworkData.status === 201 || artworkData.status === 200;
        if(resSuccess){

        }
        setSuccess(resSuccess);
    }, [artworkData.status]);


    /* put a message by success */
    useEffect(() => {
        if (success) {
            setErrorMessage('');
            setSuccessMessage(<Alert className="text-center m-4" key='success' variant='success'>
                <p> Your changes are successfully saved! Please upload up to 5 images</p>
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
        console.log("submit: " + JSON.stringify(artwork))
        if(artwork?.title == ""){
            setErrorMessage("The title can't be empty!")
            setError(true);
        }else{
            await dispatch(artworkAction.createArtwork(artwork));
        }
    }



    /* add / remove categories */
    const handleCheckboxChange = (e) => {
        const { value, checked } = e.target;
        setSelectedValues((prevSelectedValues) => {
            const updatedValues = new Set(prevSelectedValues);
            if (checked) {
                updatedValues.add(value);
            } else {
                updatedValues.delete(value);
            }
            return updatedValues;
        });
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


    /* if (userSession && !galleryData.ga) {
    return <LoadingPage />;
    } */


    if (!userSession) {
        return <HomePublic/>;
    }


    return (
        <div className='h-100'>
            <MenuBar/>
            <div className="container-md m-auto">
                <div className="row d-flex justify-content-center">
                    <div className="col w-100">
                        <h1 className="fw-light">Create Artwork</h1>
                    </div>


                    <div className="card-body p-4 text-black">
                        <div className='personal-info-container mb-2'>
                            <p className="lead fw-normal mb-1">Artwork Information</p>
                            <div className="p-2" style={{backgroundColor: '#f8f9fa'}}>
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
                                                        defaultValue=""
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
                                                        defaultValue="" onChange={(e) => setArtwork({
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
                                                        defaultValue="" onChange={(e) => setArtwork({
                                                        ...artwork,
                                                        price: e.target.value
                                                    })}
                                                    />
                                                </div>
                                            </div>
                                            <div className="row mb-4">
                                                <div className="col">
                                                    <div className="form-outline">
                                                        <label className="form-label" htmlFor="fName">Height in
                                                            cm</label>
                                                        <input
                                                            type="number"
                                                            className="form-control mr-2"
                                                            name="height"
                                                            defaultValue={artworkData.artwork?.dimension?.height}
                                                            onChange={(e) => handleDimensionChange("height", e.target.value)}
                                                        />


                                                    </div>
                                                </div>
                                                <div className="col">
                                                    <div className="form-outline">
                                                        <label className="form-label" htmlFor="lName">Width in
                                                            cm</label>
                                                        <input
                                                            type="number"
                                                            className="form-control"
                                                            name="yearOfCreation"
                                                            defaultValue={artworkData.artwork?.dimension?.width}
                                                            onChange={(e) => handleDimensionChange("width", e.target.value)}
                                                        />
                                                    </div>
                                                </div>
                                                <div className="col">
                                                    <div className="form-outline">
                                                        <label className="form-label" htmlFor="lPrice">Depth in
                                                            cm</label>
                                                        <input
                                                            type="number"
                                                            className="form-control"
                                                            name="yearOfCreation"
                                                            defaultValue={artworkData.artwork?.dimension?.depth}
                                                            onChange={(e) => handleDimensionChange("depth", e.target.value)}
                                                        />
                                                    </div>
                                                </div>
                                                <div className="row mb-12">
                                                    <div className="col-12">
                                                        <div className="form-outline">
                                                            <label className="form-label" htmlFor="fLocation">Location</label>
                                                            <input
                                                                type="text"
                                                                id="fLocation"
                                                                className="form-control"
                                                                name='location'
                                                                defaultValue=""
                                                                onChange={(e) => setArtwork({
                                                                    ...artwork,
                                                                    location: e.target.value
                                                                })}
                                                            />
                                                        </div>
                                                    </div>
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
                                                style={{height: "100px"}}
                                                defaultValue=""
                                                onChange={async (e) => {
                                                    setArtwork({...artwork, description: e.target.value});
                                                }}
                                            />
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div style={{margin: 10}}>
                            <p className="lead fw-normal mb-1 mt-3">Tags</p>
                            <div className="input-group">


                                <InputTags
                                    /* style={{ backgroundColor: "red" }} */
                                    values={artwork?.tags || []}
                                    onTags={(value) => setArtwork({
                                        ...artwork,
                                        tags: value.values
                                    }, console.log(artwork))}
                                />
                                <button
                                    className="btn btn-outline-secondary"
                                    type="button"
                                    data-testid="button-clearAll"
                                    onClick={() => {
                                        setArtwork({...artwork, tags: []});
                                    }}
                                >
                                    Delete all
                                </button>
                            </div>
                            <hr/>
                            <ol>
                                {artwork?.tags?.length > 0 &&
                                    artwork.tags.map((item, index) => (
                                        <li key={item + index}>{item}</li>
                                    ))}
                            </ol>
                        </div>


                        <div style={{margin: 10}}>
                            <p className="lead fw-normal mb-1 mt-3">Materials</p>
                            <div className="input-group">
                                <InputTags
                                    /* style={{ backgroundColor: "red" }} */
                                    values={artwork?.materials || []}
                                    onTags={(value) => setArtwork({
                                        ...artwork,
                                        materials: value.values
                                    }, console.log(artwork))}
                                />
                                <button
                                    className="btn btn-outline-secondary"
                                    type="button"
                                    data-testid="button-clearAll"
                                    onClick={() => {
                                        setArtwork({...artwork, materials: []});
                                    }}
                                >
                                    Delete all
                                </button>
                            </div>
                            <hr/>
                            <ol>
                                {artwork?.materials?.length > 0 &&
                                    artwork.materials.map((item, index) => (
                                        <li key={item + index}>{item}</li>
                                    ))}
                            </ol>
                        </div>
                        <div className='categories-container mb-2'>
                            <p className="lead fw-normal mb-1">Directions</p>
                            <div className="p-2" style={{backgroundColor: '#f8f9fa'}}>
                                <div className="row mb-4">
                                    <div className="col">
                                        <div className="form-outline mb-4">
                                            <div className="form-outline mb-4">
                                                <input
                                                    type="checkbox"
                                                    className="btn-check"
                                                    id="btn-check-1-outlined-print"
                                                    value="ABSTRACT"
                                                    checked={selectedValues.has('ABSTRACT')}
                                                    onChange={handleCheckboxChange}
                                                />
                                                <label className="btn btn-outline-secondary m-2"
                                                       htmlFor="btn-check-1-outlined-print">ABSTRACT</label>


                                                <input
                                                    type="checkbox"
                                                    className="btn-check"
                                                    id="btn-check-2-outlined-print"
                                                    value="REALISM"
                                                    checked={selectedValues.has('REALISM')}
                                                    onChange={handleCheckboxChange}
                                                />
                                                <label className="btn btn-outline-secondary m-2"
                                                       htmlFor="btn-check-2-outlined-print">REALISM</label>
                                                <input
                                                    type="checkbox"
                                                    className="btn-check"
                                                    id="btn-check-3-outlined-print"
                                                    value="IMPRESSIONISM"
                                                    checked={selectedValues.has('IMPRESSIONISM')}
                                                    onChange={handleCheckboxChange}
                                                />
                                                <label className="btn btn-outline-secondary m-2"
                                                       htmlFor="btn-check-3-outlined-print">IMPRESSIONISM</label>


                                                <input
                                                    type="checkbox"
                                                    className="btn-check"
                                                    id="btn-check-4-outlined-print"
                                                    value="SURREALISM"
                                                    checked={selectedValues.has('SURREALISM')}
                                                    onChange={handleCheckboxChange}
                                                />
                                                <label className="btn btn-outline-secondary m-2"
                                                       htmlFor="btn-check-4-outlined-print">SURREALISM</label>


                                                <input
                                                    type="checkbox"
                                                    className="btn-check"
                                                    id="btn-check-5-outlined-print"
                                                    value="EXPRESSIONISM"
                                                    checked={selectedValues.has('EXPRESSIONISM')}
                                                    onChange={handleCheckboxChange}
                                                />
                                                <label className="btn btn-outline-secondary m-2"
                                                       htmlFor="btn-check-5-outlined-print">EXPRESSIONISM</label>


                                                <input
                                                    type="checkbox"
                                                    className="btn-check"
                                                    id="btn-check-6-outlined-print"
                                                    value="MINIMALISM"
                                                    checked={selectedValues.has('MINIMALISM')}
                                                    onChange={handleCheckboxChange}
                                                />
                                                <label className="btn btn-outline-secondary m-2"
                                                       htmlFor="btn-check-6-outlined-print">MINIMALISM</label>


                                                <input
                                                    type="checkbox"
                                                    className="btn-check"
                                                    id="btn-check-7-outlined-print"
                                                    value="CUBISM"
                                                    checked={selectedValues.has('CUBISM')}
                                                    onChange={handleCheckboxChange}
                                                />
                                                <label className="btn btn-outline-secondary m-2"
                                                       htmlFor="btn-check-7-outlined-print">CUBISM</label>


                                                <input
                                                    type="checkbox"
                                                    className="btn-check"
                                                    id="btn-check-8-outlined-print"
                                                    value="POP_ART"
                                                    checked={selectedValues.has('POP_ART')}
                                                    onChange={handleCheckboxChange}
                                                />
                                                <label className="btn btn-outline-secondary m-2"
                                                       htmlFor="btn-check-8-outlined-print">POP ART</label>


                                                <input
                                                    type="checkbox"
                                                    className="btn-check"
                                                    id="btn-check-9-outlined-print"
                                                    value="CONCEPTUAL_ART"
                                                    checked={selectedValues.has('CONCEPTUAL_ART')}
                                                    onChange={handleCheckboxChange}
                                                />
                                                <label className="btn btn-outline-secondary m-2"
                                                       htmlFor="btn-check-9-outlined-print">CONCEPTUAL ART</label>


                                                <input
                                                    type="checkbox"
                                                    className="btn-check"
                                                    id="btn-check-10-outlined-print"
                                                    value="STREET_ART_GRAFFITI"
                                                    checked={selectedValues.has('STREET_ART_GRAFFITI')}
                                                    onChange={handleCheckboxChange}
                                                />
                                                <label className="btn btn-outline-secondary m-2"
                                                       htmlFor="btn-check-10-outlined-print">STREET ART/GRAFFITI</label>


                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        {artworkData.artwork ? (<div className='categories-container mb-2'>
                            <p className="lead fw-normal mb-1">File Upload</p>
                            <div className="p-2" style={{backgroundColor: '#f8f9fa'}}>
                                <div className="mb-3">
                                    <label htmlFor="formFile" className="form-label">File</label>
                                    <input onChange={handleFileSelect} accept=".jpe, .jpeg, .png"
                                           className="form-control" type="file" id="formFile" multiple/>
                                </div>
                            </div>
                        </div>) : (null)}
                        <div className='result-container mb-2'>
                            {successMessage}
                            {errorMessage}
                        </div>


                        <div class="d-grid gap-2">
                            {success? <Button variant="dark" size="lg" onClick={()=>fetchData()}>Upload Images</Button>: <Button variant="dark" size="lg" onClick={handleSubmit}>Save</Button>
                            } </div>
                    </div>
                </div>
            </div>
            <Footer/>
        </div>


    );
}


export default ArtworkNew