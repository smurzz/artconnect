import React, { useState, useEffect } from 'react';
import Moment from 'react-moment';
import { Card, Modal } from 'react-bootstrap';
import { useDispatch, useSelector } from 'react-redux';
import { useParams } from "react-router-dom";
import MenuBar from '../components/MenuBar';
import Footer from '../components/Footer';
import NotFoundPage from '../errors/NotFoundPage';
import LoadingPage from '../home/LoadingPage';

import '../../layout/css/homePublic.css';
import { FaEtsy, FaInstagram, FaFacebook, FaTwitter } from 'react-icons/fa';
import ProfilePhotoDefault from '../../images/user.png';

import * as userActions from '../../../redux/user/UserAction';
import MessageModal from '../components/user/MessageModal';

function Profile() {
    const userSession = JSON.parse(localStorage.getItem('userSession'));

    const { id } = useParams();
    const dispatch = useDispatch();

    const [showModal, setShowModal] = useState(false);
    const userData = useSelector(state => state.users);
    const [user, setUser] = useState(null);
    const [imageUrl, setImageUrl] = useState({});

    useEffect(() => {
        const fetchData = async () => {
            await dispatch(userActions.getUserById(id));
        };

        fetchData();
    }, [dispatch, id]);

    useEffect(() => {
        if (userData.user) {
            setUser(userData.user);
            const contentType = userData.user.profilePhoto?.contentType ?? null;
            const imageData = userData.user.profilePhoto?.image.data ?? null;
            const imageSrc = (contentType && imageData)
                ? `data:${contentType};base64,${imageData}`
                : ProfilePhotoDefault;
            setImageUrl(imageSrc);
        }
    }, [userData.user, userData.profilePhoto]);

    if (!user && !userData.error) {
        return <LoadingPage />;
    }

    if (userData.error) {
        return <NotFoundPage />;
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
                                                }}
                                                onClick={() => {
                                                    setShowModal(true);
                                                }} />
                                            <div>
                                                {userSession && <MessageModal userData={user} />}
                                            </div>
                                        </div>
                                    </div>
                                    <div className="ms-3" style={{ marginTop: '10rem' }}>
                                        <h5><a href={`/user/${user.id}`} className="link-light text-decoration-none">{user.firstname + " " + user.lastname}</a></h5>
                                        <p className="mb-1">{user.contacts && user.contacts.address ? user.contacts.address.city : null}</p>
                                        <p className="text-secondary mb-1"> Joined at {user.createdAt ? (<Moment format="DD/MM/YYYY">{user.createdAt}</Moment>) : null}</p>
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
                                <a as="button" class="btn btn-dark mb-3" href='javascript:history.back()'>Back</a>
                                    <div className='biography-container mb-4'>
                                        <div className="mb-1">
                                            <p className="lead fw-normal mb-1">Biography</p>
                                            <div class="p-4">
                                                <p className="font-italic mb-1">{user.biography || 'No biography'}</p>
                                            </div>

                                        </div>
                                    </div>
                                    <div className='contacts-container mb-4'>
                                        <p className="lead fw-normal mb-1">Contacts</p>
                                        <div class="p-4">
                                            <div className="mb-1">
                                                <dl class="row">
                                                    <dt class="col-sm-3" style={{ fontWeight: "normal" }}>Telefon</dt>
                                                    <dd class="col-sm-9">{user.contacts?.telefonNumber || "No data"}</dd>

                                                    <dt class="col-sm-3" style={{ fontWeight: "normal" }}>City</dt>
                                                    <dd class="col-sm-9">{user.contacts?.address?.city ?? "No data"}</dd>

                                                    <dt class="col-sm-3" style={{ fontWeight: "normal" }}>Country</dt>
                                                    <dd class="col-sm-9">{user.contacts?.address?.country ?? "No data"}</dd>
                                                </dl>
                                            </div>
                                        </div>
                                    </div>
                                    <div className='exhibition-container mb-4'>
                                        <p className="lead fw-normal mb-1">Exhibitions</p>
                                        <div class="p-4">
                                            <div className="mb-1">
                                                {user.exhibitions?.length > 0 ? (
                                                    user.exhibitions.map((exhibition, index) => (
                                                        <Card className='mb-3' key={index}>
                                                            <Card.Header as="h5">{exhibition.title}</Card.Header>
                                                            <Card.Body>
                                                                <Card.Title as="h6">{exhibition.location}, {exhibition.year}</Card.Title>
                                                                <Card.Text>
                                                                    {exhibition.description}
                                                                </Card.Text>
                                                            </Card.Body>
                                                        </Card>
                                                    ))
                                                ) : (
                                                    <p>No exhibitions found.</p>
                                                )}
                                            </div>
                                        </div>
                                    </div>

                                    <div className='social-medias-container mb-4'>
                                        <p className="lead fw-normal mb-1">Social medias</p>
                                        <div className="p-4">
                                            <div className="mb-1">
                                                {user.socialMedias?.length > 0 ? (
                                                    user.socialMedias.map((media, index) => (
                                                        <div key={index}>
                                                            <h6><a className="link-dark text-decoration-none" href={media.link}>{media.title.toLowerCase()}</a></h6>
                                                        </div>
                                                    ))
                                                ) : (
                                                    <p>No social medias found.</p>
                                                )}
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
            {showModal && (
                <div class="modal fade bd-example-modal-lg" id="imagemodal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                    <Modal show={showModal} onHide={() => setShowModal(false)}>
                        <Modal.Body>
                            <button type="button" className="btn-close" aria-label="Close" onClick={() => setShowModal(false)}></button>
                            <img className="imagepreview" src={imageUrl} style={{ maxWidth: '100%' }} alt="Preview" />
                        </Modal.Body>
                    </Modal>
                </div>
            )}
            <Footer />
        </div>
    );
}

export default Profile;