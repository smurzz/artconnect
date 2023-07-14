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
import {Alert} from 'react-bootstrap';
import DeleteArtworkModel from '../../pages/components/DeleteArtworkModel';


import {InputTags} from "react-bootstrap-tagsinput";
import "react-bootstrap-tagsinput/dist/index.css";


function ImageUpload() {
    var userSession = JSON.parse(localStorage.getItem('userSession'));
    let {id} = useParams();
    const dispatch = useDispatch();

    const artworkData = useSelector(state => state.artwork);
    const [selectedFile, setSelectedFile] = useState(null);
    const [isLoading, setIsLoading] = useState(false);
    const [success, setSuccess] = useState(false);
    const [successMessage, setSuccessMessage] = useState('');
    const [errorMessage, setErrorMessage] = useState('');

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
    const [state, setState] = useState([]);


    /* update user profile */

    if (!artworkData.artworks && !artworkData.error) {
        return <LoadingPage/>;
    }

    if (artworkData.error) {
        alert(artworkData.error)
        //return <NotFoundPage/>;
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

                            </div>
                        </div>
                    </div>
                </div>
            </section>
            <Footer/>
        </div>);
}

export default ImageUpload;
