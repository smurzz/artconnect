import React, { useState, useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { useNavigate } from "react-router-dom";
import { Button, Alert } from 'react-bootstrap';
import { FaEtsy, FaInstagram, FaFacebook, FaTwitter } from 'react-icons/fa';

import '../layout/css/homePublic.css';
import { MenuBar } from './components/MenuBar';
import Footer from './components/Footer';
import ProfilePhotoDefault from '../images/user.png';
import LoadingPage from './components/LoadingPage';
import Exhibition from './components/Exhibition';
import SocialMedias from './components/SocialMedias';
import DeleteUserModel from './components/DeleteUserModel';
import HomePublic from './HomePublic';

import * as userActions from '../../redux/user/UserAction';
import * as authActions from '../../redux/authentication/AuthenticationAction';
import RefreshTokenExpiredError from '../../redux/exceptions/RefreshTokenExpiredError';


function MyProfile() {
    var userSession = JSON.parse(localStorage.getItem('userSession'));

    const [selectedFile, setSelectedFile] = useState(null);
    const [isLoading, setIsLoading] = useState(false);
    const [success, setSuccess] = useState(false);
    const [successMessage, setSuccessMessage] = useState('');
    const [errorMessage, setErrorMessage] = useState('');

    const dispatch = useDispatch();
    const navigate = useNavigate();
    const userData = useSelector(state => state.users);
    const [user, setUser] = useState(null);
    const [imageUrl, setImageUrl] = useState({});

    /* get current user by email */
    useEffect(() => {
        const fetchData = async () => {
            let dataToken = JSON.parse(localStorage.getItem('userSession')).accessTokenData;
            let email = dataToken.sub;
            await dispatch(userActions.getUserByEmail(email));
        };

        fetchData();
    }, [dispatch]);

    /* add new profile photo */
    useEffect(() => {
        const fetchData = async () => {
            if (selectedFile) {
                const formData = new FormData();
                formData.append('file', selectedFile);
                await dispatch(userActions.addUserProfilePhoto(formData));
            }
        };

        fetchData();

        if (userData.profilePhoto) {
            window.location.reload();
        }
    }, [selectedFile, dispatch, userData.profilePhoto]);

    /* set profile photo in the image container */
    useEffect(() => {
        if (userData.user) {
            setUser(userData.user);
            const contentType = userData.user.profilePhoto?.contentType ?? null;
            const imageData =  userData.user.profilePhoto?.image.data ?? null;
            const imageSrc = (contentType && imageData)
                ? `data:${contentType};base64,${imageData}`
                : ProfilePhotoDefault;
            setImageUrl(imageSrc);
        }
    }, [userData.user, userData.profilePhoto]);

    /* check if the response status is ok */
    useEffect(() => {
        const resSuccess = userData.status === 200;
        setSuccess(resSuccess);
    }, [userData.status]);

    /* put a message by success */
    useEffect(() => {
        if (success) {
            setSuccessMessage(<Alert className="text-center m-4" key='success' variant='success'>
                <p> Your changes are successfully saved! </p>
            </Alert>)
        } else {
            setSuccessMessage(null);
        }
    }, [success]);

    /* put a message by error */
    useEffect(() => {
        if (userData.error && userData.error.response) {
            setErrorMessage(<Alert className="text-center m-4" key='f' variant='danger'>
                <p> {userData.error.response.data.message} </p>
            </Alert>)
        } else if (userData.error instanceof RefreshTokenExpiredError) {
            dispatch(authActions.logoutUser());
            navigate('/');
        } else {
            setErrorMessage(null);
        }
    }, [userData.error, dispatch, navigate]);

    /* set a image-file for updating */
    const handleFileSelect = async (e) => {
        setSelectedFile(e.target.files[0]);
    }

    /* update exhibitions list */
    const handleExhibitionsUpdate = (updatedExhibitions) => {
        setUser((prevUser) => ({
            ...prevUser,
            exhibitions: updatedExhibitions,
        }));
    };

    /* update social medias list */
    const handleSocialMediasUpdate = (updatedSocialMedias) => {
        setUser((prevUser) => ({
            ...prevUser,
            socialMedias: updatedSocialMedias,
        }));
    };

    /* update user profile */
    const handleSubmit = async (e) => {
        e.preventDefault();
        const { profilePhoto, ...updatedUser } = user;
        await dispatch(userActions.editUser(updatedUser.id, updatedUser));
    }

    if (!user && userSession) {
        return <LoadingPage />;
    }

    if (!user && !userSession) {
        return <HomePublic />;
    }

    return (
        <div>
            <MenuBar />
            <section className="h-100 gradient-custom-2 bg-body-tertiary">
                <div className="container-md h-100 m-auto">
                    <div className="row d-flex justify-content-center align-items-center h-100">
                        <div className="col w-100">
                            <div className="card">
                                <div className="rounded-top text-white d-flex flex-row" style={{ backgroundColor: '#212529', height: '250px' }}>
                                    <div className="ms-4 d-flex flex-column" style={{ marginTop: '8rem', zIndex: '1' }}>
                                        <div className="mb-1">
                                            <img id="profileImage"
                                                src={imageUrl}
                                                alt="Profile"
                                                className="img-thumbnail"
                                                style={{
                                                    width: '150px',
                                                    height: '150px',
                                                    objectFit: 'cover',
                                                    objectPosition: '50% 50%',
                                                    zIndex: '1'
                                                }} />
                                        </div>
                                        <div className="btn btn-outline-dark w-100 btn-sm" disabled={isLoading} style={{ zIndex: '1' }}>
                                            <label className="form-label m-1" for="fileInput">Edit profile</label>
                                            <input
                                                type="file"
                                                className="form-control d-none"
                                                id="fileInput"
                                                accept="image/png, image/jpeg"
                                                onChange={handleFileSelect}
                                            />
                                        </div>
                                    </div>
                                    <div className="ms-3" style={{ marginTop: '10rem' }}>
                                        <h5><a href={`/user/${user.id}`} className="link-light text-decoration-none">{user.firstname + " " + user.lastname}</a></h5>
                                        <p>{user.contacts && user.contacts.address ? user.contacts.address.city : null}</p>
                                    </div>
                                </div>
                                <div className="p-4 text-black" style={{ backgroundColor: '#f8f9fa' }}>
                                    <div className="d-flex justify-content-end text-center py-1">
                                        {user.socialMedias && user.socialMedias.length > 0 ? (
                                            user.socialMedias.map((media, index) => (
                                                <div className='m-1' key={index}>
                                                    <a href={media.link} class="link-dark">
                                                        {media.title.toLowerCase() === "instagram" && <FaInstagram size={24} />}
                                                        {media.title.toLowerCase() === "etsy" && <FaEtsy size={24} />}
                                                        {media.title.toLowerCase() === "facebook" && <FaFacebook size={24} />}
                                                        {media.title.toLowerCase() === "twitter" && <FaTwitter size={24} />}
                                                    </a>
                                                </div>
                                            ))
                                        ) : (
                                            <p></p>
                                        )}
                                    </div>
                                </div>

                                <div className="card-body p-4 text-black">
                                    <div className='personal-info-container mb-4'>
                                        <p className="lead fw-normal mb-1">Personal information</p>
                                        <div className="p-4" style={{ backgroundColor: '#f8f9fa' }}>
                                            <div className="row mb-4">
                                                <div className="col">
                                                    <div className="form-outline">
                                                        <label className="form-label" for="fName">First name</label>
                                                        <input
                                                            type="text"
                                                            id="fName"
                                                            className="form-control"
                                                            name='firstName'
                                                            value={user.firstname || ''}
                                                            onChange={async (e) => {
                                                                const value = e.target.value.trim();
                                                                if (value !== '') {
                                                                    setUser({ ...user, firstname: value });
                                                                }
                                                            }}
                                                        />
                                                    </div>
                                                </div>
                                                <div className="col">
                                                    <div className="form-outline">
                                                        <label className="form-label" for="lName">Last name</label>
                                                        <input
                                                            type="text"
                                                            id="lName"
                                                            className="form-control"
                                                            name='lastName'
                                                            value={user.lastname || ''}
                                                            onChange={async (e) => {
                                                                const value = e.target.value.trim();
                                                                if (value !== '') {
                                                                    setUser({ ...user, lastname: value });
                                                                }
                                                            }}
                                                        />
                                                    </div>
                                                </div>
                                            </div>
                                            <div className="row mb-4">
                                                <div className="col">
                                                    <div className="form-outline">
                                                        <label className="form-label" for="birthday">Date of birthday</label>
                                                        <input
                                                            type="date"
                                                            id="birthday"
                                                            className="form-control"
                                                            name='dateOfBirthday'
                                                            value={user.dateOfBirthday || ''}
                                                            onChange={async (e) => { setUser({ ...user, dateOfBirthday: e.target.value }) }}
                                                            max={new Date().toISOString().split('T')[0]}
                                                        />
                                                    </div>
                                                </div>
                                                <div className="col">
                                                    <div className="form-check">
                                                    <label className="form-check-label" for="flexCheckChecked">Birthday visible</label>
                                                        <input
                                                            type="checkbox"
                                                            id="birthdayVisible"
                                                            className="form-check-input"
                                                            name='isDateOfBirthVisible'
                                                            checked={user.dateOfBirthday && user.isDateOfBirthVisible === 'PUBLIC'}
                                                            onChange={async (e) => {
                                                                const checked = e.target.checked;
                                                                const updatedValue = checked ? 'PUBLIC' : 'PRIVATE';
                                                                setUser({ ...user, isDateOfBirthVisible: updatedValue });
                                                              }}
                                                        />
                                                        
                                                    </div>
                                                </div>
                                            </div>
                                            <div className="form-outline mb-4">
                                                <label className="form-label" for="tel-number">Phone</label>
                                                <input
                                                    type="number"
                                                    id="tel-number"
                                                    name='telefonNumber'
                                                    className="form-control"
                                                    value={(user.contacts && user.contacts.telefonNumber) || ''}
                                                    onChange={async (e) => { setUser({ ...user, contacts: { ...user.contacts, telefonNumber: e.target.value } }); }}
                                                />
                                            </div>
                                            <div className="form-outline mb-4">
                                                <label className="form-label" for="website">Website</label>
                                                <input
                                                    type="text"
                                                    id="website"
                                                    className="form-control"
                                                    value={(user.contacts && user.contacts.website) || ''}
                                                    onChange={async (e) => { setUser({ ...user, contacts: { ...user.contacts, website: e.target.value } }); }}
                                                />
                                            </div>
                                        </div>
                                    </div>
                                    <div className='address-container mb-4'>
                                        <p className="lead fw-normal mb-1">Address</p>
                                        <div className="p-4" style={{ backgroundColor: '#f8f9fa' }}>
                                            <div className="col">
                                                <div className="form-outline">
                                                    <label className="form-label" for="street">Street</label>
                                                    <input
                                                        type="text"
                                                        id="street"
                                                        className="form-control"
                                                        name='street'
                                                        value={(user.contacts && user.contacts.address && user.contacts.address.street) || ''}
                                                        onChange={async (e) => { setUser({ ...user, contacts: { ...user.contacts, address: { ...user.contacts.address, street: e.target.value } } }); }}
                                                    />
                                                </div>
                                            </div>
                                            <div className="col">
                                                <div className="form-outline">
                                                    <label className="form-label" for="postCode">Postal Code</label>
                                                    <input
                                                        type="text"
                                                        id="postCode"
                                                        className="form-control"
                                                        name='postCode'
                                                        value={(user.contacts && user.contacts.address && user.contacts.address.postalCode) || ''}
                                                        onChange={async (e) => { setUser({ ...user, contacts: { ...user.contacts, address: { ...user.contacts.address, postalCode: e.target.value } } }); }}
                                                    />
                                                </div>
                                            </div>
                                            <div className="col">
                                                <div className="form-outline">
                                                    <label className="form-label" for="city">City</label>
                                                    <input
                                                        type="text"
                                                        id="city"
                                                        className="form-control mb-1"
                                                        name='city'
                                                        value={(user.contacts && user.contacts.address && user.contacts.address.city) || ''}
                                                        onChange={async (e) => { setUser({ ...user, contacts: { ...user.contacts, address: { ...user.contacts.address, city: e.target.value } } }); }}
                                                    />
                                                </div>
                                            </div>
                                            <div className="col">
                                                <div className="form-outline">
                                                    <label className="form-label" for="country">Country</label>
                                                    <input
                                                        type="text"
                                                        id="country"
                                                        className="form-control mb-1"
                                                        name='country'
                                                        value={(user.contacts && user.contacts.address && user.contacts.address.country) || ''}
                                                        onChange={async (e) => { setUser({ ...user, contacts: { ...user.contacts, address: { ...user.contacts.address, country: e.target.value } } }); }}
                                                    />
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div className="biography-container mb-5">
                                        <p className="lead fw-normal mb-1">Biography</p>
                                        <div className="p-4" style={{ backgroundColor: '#f8f9fa' }}>
                                            <div className="form-group">
                                                <textarea
                                                    className="form-control"
                                                    name='biography'
                                                    id="formControlTextarea"
                                                    rows="3"
                                                    value={user.biography || ''}
                                                    onChange={async (e) => { setUser({ ...user, biography: e.target.value }); }}
                                                >
                                                </textarea>
                                            </div>
                                        </div>
                                    </div>
                                    <div className="exhibition-container mb-5">
                                        <p className="lead fw-normal mb-1">Exhibitions</p>
                                        <Exhibition userExhib={user.exhibitions ? user.exhibitions : null} onUpdateExhibitions={handleExhibitionsUpdate} />
                                    </div>
                                    <div className="exhibition-container mb-5">
                                        <p className="lead fw-normal mb-1">Social Medias</p>
                                        <SocialMedias socialMedias={user.socialMedias ? user.socialMedias : null} onUpdateSocialMedias={handleSocialMediasUpdate} />
                                    </div>
                                    {successMessage}
                                    {errorMessage}

                                    <div class="d-grid gap-2">
                                        <Button variant="dark" size="lg" onClick={handleSubmit}>Save</Button>
                                        <DeleteUserModel userId={user.id} />
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
            <Footer />
        </div>
    );
}

export default MyProfile;
