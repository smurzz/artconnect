import React, { useState, useEffect } from 'react';
import { MenuBar } from '../components/MenuBar';
import Moment from 'react-moment';
import { useDispatch, useSelector } from 'react-redux';
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';
import Footer from '../components/Footer';
import LoadingPage from '../components/LoadingPage';
import NotFoundPage from '../errors/NotFoundPage';
import '../../layout/css/homePublic.css';
import '../../layout/css/users.css';
import { HeartIcon, MinusIcon, PlusIcon } from '@heroicons/react/24/outline'
import 'bootstrap-icons/font/bootstrap-icons.css';
import ProfilePhotoDefault from '../../images/user.png';
import ImageTMP from '../../images/images.svg';
import * as artworkActions from '../../../redux/artwork/ArtworkAction';
import { useParams } from 'react-router-dom';
import Modal from 'react-bootstrap/Modal';
import MessageArtworkModal from '../components/MessageArtworkModal';


function ArtworkDetailPage() {
    let { id } = useParams();
    const dispatch = useDispatch();

    const [showModal, setShowModal] = useState(false);
    const [imageSrc, setImageSrc] = useState('');
    const [comment, setComment] = useState({
        commentText: ''
    });
    const artworkData = useSelector(state => state.artwork);
    var userSession = JSON.parse(localStorage.getItem('userSession'));

    async function setLike() {
        await dispatch(artworkActions.addRemoveLike(id))
    }

    if (userSession) {
        var likeArtLoggedIn = (
            <Button
                onClick={() => { setLike() }}>
                <div className="card-body">
                    <span className="bi bi-heart"></span>
                    <span className="like-count"> {artworkData.artwork?.likes} likes</span>
                </div>
            </Button>)
    } else {
        var likeArtLoggedOut = (
            <div className="card-body">
                <span className="bi bi-heart"></span>
                <span className="like-count"> {artworkData.artwork?.likes} likes</span>
            </div>)
    }

    useEffect(() => {
        const fetchData = async () => {
            console.log("id form Url: " + id);
            await dispatch(artworkActions.getArtwork(id));
        };
        fetchData();
    }, [dispatch]);

    useEffect(() => {
        if(artworkData.status === 201){
            setComment('');
        }
    }, [artworkData]);


    const handleSubmit = async (e) => {
        e.preventDefault();
        if(comment){
            await dispatch(artworkActions.postComment(id, comment));
        }
    }

    if (!artworkData.artworks && !artworkData.error) {
        return <LoadingPage />;
    }

    if (artworkData.error) {
        return <NotFoundPage />;
    }


    return (
        <div className="home-public-container">
            <MenuBar />
            {/*Images*/}
            <section className="h-100 gradient-custom-2 bg-body-tertiary">
                <div className="container-md h-100 m-auto">
                    <div className="row d-flex justify-content-center align-items-center h-100">
                        <div className="col w-100">
                            <div className="card ">
                                <div className="rounded-top text-white d-flex flex-row justify-content-center"
                                    style={{ backgroundColor: '#212529', height: '250px' }}>
                                    <div className="ms-4 d-flex flex-column" style={{ marginTop: '8rem', zIndex: '1' }}>
                                        <div className="mb-1">
                                            <img id="profileImage"
                                                src={artworkData.artwork?.images && artworkData.artwork?.images?.length > 1 ? (`data:${artworkData.artwork?.images[1]?.contentType};base64,${artworkData.artwork?.images[1]?.image.data}`) : ImageTMP}
                                                alt="Profile"
                                                className="img-thumbnail"
                                                style={{
                                                    width: '150px',
                                                    height: '150px',
                                                    objectFit: 'cover',
                                                    objectPosition: '50% 50%',
                                                    zIndex: '1'
                                                }}
                                                onClick={() => {
                                                    setImageSrc(artworkData.artwork?.images && artworkData.artwork?.images?.length > 1 ? (`data:${artworkData.artwork?.images[1]?.contentType};base64,${artworkData.artwork?.images[1]?.image.data}`) : ImageTMP);
                                                    setShowModal(true);
                                                }}
                                            />
                                        </div>
                                    </div>
                                    <div className="ms-4 d-flex flex-column" style={{ marginTop: '8rem', zIndex: '1' }}>
                                        <div className="mb-1">
                                            <img id="profileImage"
                                                src={artworkData.artwork?.images && artworkData.artwork?.images?.length > 3 ? (`data:${artworkData.artwork?.images[3]?.contentType};base64,${artworkData.artwork?.images[3]?.image.data}`) : ImageTMP}
                                                alt="Profile"
                                                className="img-thumbnail"
                                                style={{
                                                    width: '150px',
                                                    height: '150px',
                                                    objectFit: 'cover',
                                                    objectPosition: '50% 50%',
                                                    zIndex: '1'
                                                }}
                                                onClick={() => {
                                                    setImageSrc(artworkData.artwork?.images && artworkData.artwork?.images?.length > 3 ? (`data:${artworkData.artwork?.images[3]?.contentType};base64,${artworkData.artwork?.images[3]?.image.data}`) : ImageTMP);
                                                    setShowModal(true);
                                                }} />
                                        </div>
                                    </div>
                                    <div className="ms-4 d-flex flex-column" style={{ marginTop: '8rem', zIndex: '1' }}>
                                        <div className="mb-1">
                                            <img id="profileImage"
                                                src={artworkData.artwork?.images && artworkData.artwork?.images?.length > 3 ? (`data:${artworkData.artwork?.images[3]?.contentType};base64,${artworkData.artwork?.images[3]?.image.data}`) : ImageTMP}
                                                alt="Profile"
                                                className="img-thumbnail"
                                                style={{
                                                    width: '150px',
                                                    height: '150px',
                                                    objectFit: 'cover',
                                                    objectPosition: '50% 50%',
                                                    zIndex: '1'
                                                }}
                                                onClick={() => {
                                                    setImageSrc( artworkData.artwork?.images && artworkData.artwork?.images?.length > 3 ? (`data:${artworkData.artwork?.images[3]?.contentType};base64,${artworkData.artwork?.images[3]?.image.data}`) : ImageTMP);
                                                    setShowModal(true);
                                                }} />
                                        </div>
                                    </div>
                                    <div className="ms-4 d-flex flex-column" style={{ marginTop: '8rem', zIndex: '1' }}>
                                        <div className="mb-1">
                                            <img id="profileImage"
                                                src={artworkData.artwork?.images && artworkData.artwork?.images?.length > 4 ? (`data:${artworkData.artwork?.images[4]?.contentType};base64,${artworkData.artwork?.images[4]?.image.data}`) : ImageTMP}
                                                alt="Profile"
                                                className="img-thumbnail"
                                                style={{
                                                    width: '150px',
                                                    height: '150px',
                                                    objectFit: 'cover',
                                                    objectPosition: '50% 50%',
                                                    zIndex: '1'
                                                }}
                                                onClick={() => {
                                                    setImageSrc(artworkData.artwork?.images && artworkData.artwork?.images?.length > 2 ? (`data:${artworkData.artwork?.images[4]?.contentType};base64,${artworkData.artwork?.images[4]?.image.data}`) : ImageTMP);
                                                    setShowModal(true);
                                                }} />
                                        </div>
                                    </div>
                                </div>

                            </div>
                        </div>
                    </div>
                </div>
            </section>
            {/*Header*/}
            <section className="h-100 gradient-custom-2 ">
                <div className="container-md h-100 m-auto">
                    <div className="row d-flex justify-content-center align-items-center h-100">
                        <header className=" bg-light border-bottom mb-4">
                            <div className="container ">
                                <div className="text-center my-5">
                                    <h1 className="fw-bolder">{artworkData.artwork?.title}</h1>
                                    <div className="like-count text-gray-400">{artworkData.artwork?.location === "" ?
                                        <span>unknown, </span> :
                                        <span>{artworkData.artwork?.location}, </span>} {artworkData.artwork?.yearOfCreation}
                                    </div>
                                    {artworkData.artwork?.artDirections.map((tag) => (
                                        <span className="tag tag-sm">{tag}</span>
                                    ))}
                                    <p className="lead mb-0">{artworkData.artwork?.description}</p>
                                </div>
                            </div>
                        </header>
                    </div>
                </div>
            </section>
            <div className="container">
                <div className="row">
                    <div className="col-lg-8">
                        {/*big Image*/}
                        <div className="card mb-4" style={{ height: '40rem' }}>
                            <div style={{ position: 'relative', width: '100%', height: '100%' }}>
                                <img
                                    className="card-img-top gradient-custom-2 bg-body-tertiary"
                                    src={artworkData.artwork?.images &&
                                        artworkData.artwork?.images?.length > 0
                                            ? `data:${artworkData.artwork?.images[0]?.contentType};base64,${artworkData.artwork?.images[0]?.image.data}`
                                            : ImageTMP
                                    }
                                    style={{
                                        position: 'absolute',
                                        top: 0,
                                        left: 0,
                                        width: '100%',
                                        height: '100%',
                                        objectFit: 'contain',
                                        objectPosition: 'center',
                                    }}
                                />
                            </div>
                        </div>
                    </div>

                    <div className="col-lg-4">
                        <div className="card mb-4">
                            <div className="card-header">About</div>
                            <div className="card-body">
                                <div className="card-body d-flex flex-column">
                                    <ul className="list-group list-group-flush flex-grow-1">
                                        {artworkData.artwork?.price &&
                                            <li className="list-group-item">Price: {artworkData?.artwork?.price} Euro</li>}
                                        {artworkData.artwork?.dimension &&
                                            <li className="list-group-item">Height: {artworkData?.artwork?.dimension?.height}cm
                                                Width: {artworkData?.artwork?.dimension?.width}cm
                                                Depth: {artworkData?.artwork?.dimension?.depth}cm</li>}
                                    </ul>
                                </div>
                            </div>
                        </div>
                        {artworkData.artwork?.materials && artworkData.artwork?.materials?.length > 0 &&
                            <div className="card mb-4">
                                <div className="card-header">Materials</div>
                                <div className="card-body">
                                    <div className="row">
                                        <div className="col-sm-12">
                                            <ul className="list-unstyled mb-0 d-flex flex-row flex-wrap">
                                                {artworkData?.artwork?.materials?.map((material) => (

                                                    <li className="mx-2">{material}</li>
                                                ))}
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        }
                        {artworkData?.artwork?.tags?.length > 0 &&
                            <div className="card mb-4">
                                <div className="card-header">Tags</div>
                                <div className="card-body">
                                    <div className="row">
                                        <div className="col-sm-12">
                                            <ul className="list-unstyled mb-0 d-flex flex-row flex-wrap">
                                                {artworkData?.artwork?.tags?.map((tag) => (

                                                    <li className="mx-2 tag tag-sm">#{tag}</li>
                                                ))}
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        }
                       
                        <div className="card mb-4">
                            <div className="card-header">Contact the artist</div>
                            <div className="card-body">
                                {artworkData?.artwork && <MessageArtworkModal artwork={artworkData?.artwork} />}

                            </div>
                        </div>
                        <div className="card mb-4">
                            <div className="card-body">
                                {likeArtLoggedOut}
                                {likeArtLoggedIn}
                            </div>
                        </div>
                    </div>

                    <div class="col-md-8 col-lg-8 w-100">
                        <div class="card shadow-0 border" style={{ backgroundColor: "#f0f2f5" }}>
                            <div class="card-body p-4">
                                <div class="form-outline mb-4">
                                    <input 
                                        type="text" 
                                        id="comment" 
                                        class="form-control" 
                                        placeholder="Type comment..." 
                                        value={comment.textComment}
                                        onChange={async (e) => { e.preventDefault(); setComment({ ...comment, commentText: e.target.value }) }}
                                        />
                                    {/*  <label class="form-label" for="addANote">+ Add a note</label> */}
                                    <button type="button" class="btn btn-outline-secondary mt-2" onClick={handleSubmit}>Comment</button>
                                </div>

                                <div class="card mb-4">
                                    {artworkData.artwork?.comments?.length > 0 ? (
                                        artworkData.artwork?.comments.map((comment, index) => (
                                            <div class="card-body" key={index}>
                                                <p>{comment.text}</p>

                                                <div class="d-flex justify-content-between">
                                                    <div class="d-flex flex-row align-items-center">
                                                        <img src={ProfilePhotoDefault} alt="avatar" width="25"
                                                            height="25" />
                                                        <p class="small mb-0 ms-2">{comment.authorName}</p>
                                                    </div>
                                                    <div class="d-flex flex-row align-items-center">
                                                        <p class="small text-muted mb-0">Posted: </p>
                                                        <i class="far fa-thumbs-up mx-2 fa-xs text-black" style={{ marginTop: "-0.16rem" }}></i>
                                                        <p class="small text-muted mb-0"><Moment format="dddd, MMMM Do YYYY, h:mm:ss a">{comment.createdAt}</Moment></p>
                                                    </div>
                                                </div>
                                            </div>
                                        ))
                                    ) : null}

                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            {showModal && (
                <div class="modal fade" id="imagemodal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                    <Modal show={showModal} onHide={() => setShowModal(false)}>
                        <Modal.Body>
                            <button type="button" className="btn-close" aria-label="Close" onClick={() => setShowModal(false)}></button>
                            <img className="imagepreview" src={imageSrc} style={{ width: '100%' }} alt="Preview" />
                        </Modal.Body>
                    </Modal>
                </div>
            )}
            <Footer />
        </div>
    );
}

export default ArtworkDetailPage;
