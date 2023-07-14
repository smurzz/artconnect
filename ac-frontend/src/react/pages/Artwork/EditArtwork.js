import React, {useState, useEffect} from 'react';
import {MenuBar} from '../components/MenuBar';
import {useDispatch, useSelector} from 'react-redux';
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';
import Footer from '../components/Footer';
import LoadingPage from '../components/LoadingPage';
import NotFoundPage from '../errors/NotFoundPage';
import '../../layout/css/homePublic.css';
import EditMaterials from "../components/EditMaterials"
import '../../layout/css/users.css';
import {HeartIcon, MinusIcon, PlusIcon} from '@heroicons/react/24/outline'
import 'bootstrap-icons/font/bootstrap-icons.css';
import ProfilePhotoDefault from '../../images/user.png';
import ImageTMP from '../../images/placeholder.jpg';
import * as artworkActions from '../../../redux/artwork/ArtworkAction';
import {useParams} from 'react-router-dom';


function EditArtWork() {
    var userSession = JSON.parse(localStorage.getItem('userSession'));
    let {id} = useParams();

    const [selectedFile, setSelectedFile] = useState(null);
    const [isLoading, setIsLoading] = useState(false);
    const [success, setSuccess] = useState(false);
    const [successMessage, setSuccessMessage] = useState('');
    const [errorMessage, setErrorMessage] = useState('');
    const currentYear = new Date().getFullYear();
    const dispatch = useDispatch();
    const artworkData = useSelector(state => state.artwork);
    const [artwork, setArtwork] = useState({
        title: "",
        images: [],
        description: "",
        yearOfCreation: "",
        materials: [],
        tags: [],
        dimension: {
            height: "",
            width: "",
            depth: ""
        },
        artDirections: [],
        price: 0,
        location: "",
        comments: null,
        likes: 0
    });

    const handleSubmit = async (e) => {
        const requestBody = {};
        e.preventDefault();
        // Validate the title
        if (artwork.title.trim() !== "") {
            requestBody.title = artwork.title;
        }
        if (artwork.description.trim() !== "") {
            requestBody.description = artwork.description;
        }
        if (artwork.materials.length > 0) {
            requestBody.materials = artwork.materials;
        }
        if (artwork.tags.length > 0) {
            requestBody.tags = artwork.tags;
        }

        if (artwork.dimension.height !== "") {
            requestBody.dimension = {
                ...requestBody.dimension,
                height: artwork.dimension.height
            };
        } else {
            requestBody.dimension = {
                ...requestBody.dimension,
                height: artworkData.artwork.dimension?.height
            };
        }
        if (artwork.dimension.width !== "") {
            requestBody.dimension = {
                ...requestBody.dimension,
                width: artwork.dimension.width
            };
        } else {
            requestBody.dimension = {
                ...requestBody.dimension,
                width: artworkData.artwork.dimension?.width
            };
        }
        if (artwork.dimension.depth !== "") {
            requestBody.dimension = {
                ...requestBody.dimension,
                depth: artwork.dimension.depth
            };
        } else {
            requestBody.dimension = {
                ...requestBody.dimension,
                depth: artworkData.artwork.dimension.depth
            };
        }
        if (artwork.artDirections.length > 0) {
            requestBody.artDirections = artwork.artDirections;
        }

        if (artwork.price > 0) {
            requestBody.price = artwork.price;
        }

        if (artwork.location.trim() !== "") {
            requestBody.location = artwork.location;
        }
        if (artwork.yearOfCreation !== "") {
            const year = parseInt(artwork.yearOfCreation, 10);
            if (year > currentYear) {
                alert(`You cannot be a time traveler! Please choose a year in the present or past.`);
            } else {
                requestBody.yearOfCreation = artwork.yearOfCreation;
            }

        }
    };

    //Art direction check Box
    const [selectedValues, setSelectedValues] = useState(new Set(artworkData.artwork?.artDirections || []));
    useEffect(() => {
        if (artworkData.artwork) {
            setSelectedValues(new Set(artworkData.artwork?.artDirections));
        }
    }, [artworkData.artwork]);

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

    const handleDimensionChange = (property, value) => {
        setArtwork((prevArtwork) => ({
            ...prevArtwork,
            dimension: {
                ...prevArtwork.dimension,
                [property]: parseFloat(value)
            }
        }));
    };

    const handleMaterialUpdate = (updatedMaterials) => {
        setArtwork((preArt) => ({
            ...preArt,
            materials: updatedMaterials,
        }));
        console.log("materials update: "+ JSON.stringify(artworkData.artwork.materials))
    };
    /* get current user by email */
    useEffect(() => {
        const fetchData = async () => {
            await dispatch(artworkActions.getArtwork(id));

        };

        fetchData();
    }, [dispatch]);

    /* add new profile photo */
    useEffect(() => {
        const fetchData = async () => {
            if (selectedFile) {
                const formData = new FormData();
                formData.append('file', selectedFile);
                await dispatch(artworkActions.addArtworkImage(id, formData));
            }
        };

        fetchData();

    }, [selectedFile, dispatch]);

    /* set a image-file for updating */
    const handleFileSelect = async (e) => {
        setSelectedFile(e.target.files[0]);
    }


    /* update user profile */

    if (!artworkData.artworks && !artworkData.error) {
        return <LoadingPage/>;
    }

    if (artworkData.error) {
        return <NotFoundPage/>;
    }

    return (
        <div>
            <MenuBar/>
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
                                                 src={artworkData.artwork?.images?.length > 0 ? (`data:${artworkData.artwork?.images[0]?.contentType};base64,${artworkData.artwork?.images[0]?.image.data}`) : ImageTMP}
                                                 alt="Profile"
                                                 className="img-thumbnail"
                                                 style={{
                                                     width: '200px',
                                                     height: '200px',
                                                     objectFit: 'cover',
                                                     objectPosition: '50% 50%',
                                                     zIndex: '1'
                                                 }}/>
                                        </div>
                                    </div>
                                    <div className="ms-4 d-flex flex-column" style={{marginTop: '8rem', zIndex: '1'}}>
                                        <div className="mb-1">
                                            <img id="profileImage"
                                                 src={artworkData.artwork?.images?.length > 1 ? (`data:${artworkData.artwork?.images[1]?.contentType};base64,${artworkData.artwork?.images[1]?.image.data}`) : ImageTMP}
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
                                        <div className="btn btn-outline-dark w-100 btn-sm" disabled={isLoading}
                                             style={{zIndex: '1'}}>
                                            <label className="form-label m-1" htmlFor="fileInput">Add Pictures</label>
                                            <input
                                                type="file"
                                                className="form-control d-none"
                                                id="fileInput"
                                                accept="image/png, image/jpeg"
                                                onChange={handleFileSelect}
                                            />
                                        </div>
                                    </div>
                                    <div className="ms-4 d-flex flex-column" style={{marginTop: '8rem', zIndex: '1'}}>
                                        <div className="mb-1">
                                            <img id="profileImage"
                                                 src={artworkData.artwork?.images?.length > 2 ? (`data:${artworkData.artwork?.images[2]?.contentType};base64,${artworkData.artwork?.images[2]?.image.data}`) : ImageTMP}
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
                                                 src={artworkData.artwork?.images?.length > 3 ? (`data:${artworkData.artwork?.images[3]?.contentType};base64,${artworkData.artwork?.images[3]?.image.data}`) : ImageTMP}
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
                                                 src={artworkData.artwork?.images?.length > 4 ? (`data:${artworkData.artwork?.images[4]?.contentType};base64,${artworkData.artwork?.images[4]?.image.data}`) : ImageTMP}
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
                                <div className="card-body p-4 text-black my-5">
                                    <p className="lead fw-normal mb-1 my-5">About the Artwork</p>
                                    {/*first block*/}
                                    <div className="p-4" style={{backgroundColor: '#f8f9fa'}}>
                                        <div className="row mb-4">
                                            <div className="col-8">
                                                <div className="form-outline">
                                                    <label className="form-label" htmlFor="fName">Titel</label>
                                                    <input
                                                        type="text"
                                                        id="fName"
                                                        className="form-control"
                                                        name='firstName'
                                                        defaultValue={artworkData.artwork?.title}
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
                                                        defaultValue={artworkData.artwork?.yearOfCreation}
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
                                                        defaultValue={artworkData.artwork?.price}
                                                        onChange={(e) => setArtwork({
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
                                                    <label className="form-label" htmlFor="fName">Height in cm</label>
                                                    <input
                                                        type="text"
                                                        id="fName"
                                                        className="form-control"
                                                        name='firstName'
                                                        defaultValue={artworkData.artwork?.dimension.height}
                                                        onChange={(e) => setArtwork({
                                                            ...artwork.dimension,
                                                            height: e.target.value
                                                        })}
                                                    />
                                                </div>
                                            </div>
                                            <div className="col">
                                                <div className="form-outline">
                                                    <label className="form-label" htmlFor="lName">Width in cm</label>
                                                    <input
                                                        type="number"
                                                        className="form-control"
                                                        name="yearOfCreation"
                                                        defaultValue={artworkData.artwork?.dimension?.width}
                                                        onChange={(e) => setArtwork({
                                                            ...artwork.dimension,
                                                            width: e.target.value
                                                        })}
                                                    />
                                                </div>
                                            </div>
                                            <div className="col">
                                                <div className="form-outline">
                                                    <label className="form-label" htmlFor="lPrice">Depth in cm</label>
                                                    <input
                                                        type="number"
                                                        className="form-control"
                                                        name="yearOfCreation"
                                                        defaultValue={artworkData.artwork?.dimension?.depth}
                                                        onChange={(e) => setArtwork({
                                                            ...artwork.dimension,
                                                            depth: e.target.value
                                                        })}
                                                    />
                                                </div>
                                            </div>
                                        </div>
                                        <div className="row mb-4">
                                        <div className="col">
                                            <div className="form-outline">
                                                <label className="form-label" htmlFor="fLocation">Location</label>
                                                <input
                                                    type="text"
                                                    id="fLocation"
                                                    className="form-control"
                                                    name='location'
                                                    defaultValue={artworkData.artwork?.location}
                                                    onChange={(e) => setArtwork({
                                                        ...artwork,
                                                        location: e.target.value
                                                    })}
                                                />
                                            </div>
                                        </div>
                                        </div>
                                    </div>
                                    {/*biografie*/}
                                    <div className="biography-container mb-5">
                                        <p className="lead fw-normal mb-1 mt-3">Description</p>
                                        <div className="p-4" style={{ backgroundColor: '#f8f9fa' }}>
                                            <div className="form-group">
                                                <textarea
                                                    className="form-control"
                                                    name='biography'
                                                    id="formControlTextarea"
                                                    rows="3"
                                                    defaultValue={artworkData.artwork?.description}
                                                    onChange={(e) => setArtwork({...artwork, description: e.target.value})}
                                                >
                                                </textarea>
                                            </div>
                                        </div>
                                    </div>
                                    {/*materials*/}
                                    <div className="exhibition-container mb-5">
                                        <p className="lead fw-normal mb-1">Materials</p>
                                        <EditMaterials artMaterials={artworkData.artwork?.materials ? artworkData.artwork?.materials : null} onUpdateMaterials={handleMaterialUpdate} />
                                    </div>
                                    <div className='biography-container mb-5'>
                                        <p className="lead fw-normal mb-1">artDirections</p>
                                        <div className="p-2" style={{ backgroundColor: '#f8f9fa' }}>
                                            <div className="row mb-4">
                                                <div className="col">
                                                    <div className="form-outline mb-4">
                                                        <input
                                                            type="checkbox"
                                                            className="btn-check"
                                                            id="btn-check-2-outlined-print"
                                                            value="ABSTRACT"
                                                            checked={selectedValues.has('ABSTRACT')}
                                                            onChange={handleCheckboxChange}
                                                        />
                                                        <label className="btn btn-outline-secondary m-2" htmlFor="btn-check-2-outlined-print">ABSTRACT</label>

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
                                </div>


                            </div>
                        </div>
                    </div>
                </div>
</section>
    <Footer/>
</div>);
}

export default EditArtWork;
