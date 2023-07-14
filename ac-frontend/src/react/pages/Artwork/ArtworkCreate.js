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
import {HeartIcon, MinusIcon, PlusIcon} from '@heroicons/react/24/outline'
import 'bootstrap-icons/font/bootstrap-icons.css';
import ProfilePhotoDefault from '../../images/user.png';
import ImageTMP from '../../images/placeholder.jpg';
import * as artworkActions from '../../../redux/artwork/ArtworkAction';
import {useParams} from 'react-router-dom';

function ArtworkCreate() {
    var userSession = JSON.parse(localStorage.getItem('userSession'));
    let {id} = useParams();

    const [selectedFile, setSelectedFile] = useState(null);
    const [isLoading, setIsLoading] = useState(false);
    const [success, setSuccess] = useState(false);
    const [successMessage, setSuccessMessage] = useState('');
    const [errorMessage, setErrorMessage] = useState('');

    const dispatch = useDispatch();
    const artworkData = useSelector(state => state.artwork);
    const [user, setUser] = useState(null);
    const [imageUrl, setImageUrl] = useState({});
    const [reload, setReload] = useState(false);

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
    const handleSubmit = async (e) => {
        e.preventDefault();
    }

    if (!artworkData.artworks && !artworkData.error) {
        return <LoadingPage/>;
    }

    if (artworkData.error) {
        return <NotFoundPage/>;
    }
    return (
        <div className="home-public-container">
            <MenuBar/>
            {/*Images*/}
            <section className="h-100 gradient-custom-2 bg-body-tertiary">
                <div className="container-md h-100 m-auto">
                    <div className="row d-flex justify-content-center align-items-top h-100">
                        <div className="col w-100">
                            <div className="card ">
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
                            </div>

                            <div className="card-body p-4 text-black">
                                <div className='personal-info-container mb-4'>
                                    <p className="lead fw-normal mb-1">Personal information</p>
                                    <div className="p-4" style={{backgroundColor: '#f8f9fa'}}>
                                        <div className="row mb-4">
                                            <div className="col">
                                                <div className="form-outline">
                                                    <label className="form-label" htmlFor="fName">First name</label>
                                                    <input
                                                        type="text"
                                                        id="fName"
                                                        className="form-control"
                                                        name='firstName'
                                                    />
                                                </div>
                                            </div>
                                            <div className="col">
                                                <div className="form-outline">
                                                    <label className="form-label" htmlFor="lName">Last name</label>
                                                    <input
                                                        type="text"
                                                        id="lName"
                                                        className="form-control"
                                                        name='lastName'
                                                    />
                                                </div>
                                            </div>
                                        </div>
                                        <div className="row mb-4">
                                            <div className="col">
                                                <div className="form-outline">
                                                    <label className="form-label" htmlFor="birthday">Date of
                                                        birthday</label>
                                                    <input
                                                        type="date"
                                                        id="birthday"
                                                        className="form-control"
                                                    />
                                                </div>
                                            </div>
                                            <div className="col">
                                                <div className="form-check">
                                                    <label className="form-check-label" htmlFor="flexCheckChecked">Birthday
                                                        visible</label>
                                                    <input
                                                        type="checkbox"
                                                        id="birthdayVisible"
                                                        className="form-check-input"
                                                        name='isDateOfBirthVisible'
                                                    />

                                                </div>
                                            </div>
                                        </div>
                                        <div className="form-outline mb-4">
                                            <label className="form-label" htmlFor="tel-number">Phone</label>
                                            <input
                                                type="number"
                                                id="tel-number"
                                                name='telefonNumber'
                                                className="form-control"
                                            />
                                        </div>
                                        <div className="form-outline mb-4">
                                            <label className="form-label" htmlFor="website">Website</label>
                                            <input
                                                type="text"
                                                id="website"
                                                className="form-control"
                                            />
                                        </div>
                                    </div>
                                </div>
                                <div className='address-container mb-4'>
                                    <p className="lead fw-normal mb-1">Address</p>
                                    <div className="p-4" style={{backgroundColor: '#f8f9fa'}}>
                                        <div className="col">
                                            <div className="form-outline">
                                                <label className="form-label" htmlFor="street">Street</label>
                                                <input
                                                    type="text"
                                                    id="street"
                                                    className="form-control"
                                                    name='street'
                                                />
                                            </div>
                                        </div>
                                        <div className="col">
                                            <div className="form-outline">
                                                <label className="form-label" htmlFor="postCode">Postal Code</label>
                                                <input
                                                    type="text"
                                                    id="postCode"
                                                    className="form-control"
                                                    name='postCode'
                                                />
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>

            {/*Header*/}
            <Footer/>
        </div>
    );
}

export default ArtworkCreate;
