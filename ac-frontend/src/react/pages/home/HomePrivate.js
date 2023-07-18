import React, { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { useNavigate } from "react-router-dom";
import MenuBar from '../components/MenuBar';
import '../../layout/css/homePublic.css';
import Footer from '../components/Footer';
import LoadingPage from '../home/LoadingPage';
import HomePublic from '../home/HomePublic';

import ImageTMP from '../../images/no-image.png';

import * as galleryActions from '../../../redux/gallery/GalleryAction';
import * as authActions from '../../../redux/authentication/AuthenticationAction';
import RefreshTokenExpiredError from '../../../redux/exceptions/RefreshTokenExpiredError';

function HomePrivate() {
    var userSession = JSON.parse(localStorage.getItem('userSession'));

    const dispatch = useDispatch();
    const navigate = useNavigate();
    const galleryData = useSelector(state => state.gallery);
    const [error, setError] = useState();
    const [gallery, setGallery] = useState(null);

    /* get gallery from a current user */
    useEffect(() => {
        if (!gallery && userSession && !error) {
            const fetchData = async () => {
                await dispatch(galleryActions.getMyGallery());
            };

            fetchData();
        }
    }, [dispatch, gallery]);

    /* set a gallery */
    useEffect(() => {
        if (galleryData.gallery) {
            setGallery(galleryData.gallery);
        }
    }, [galleryData.status, galleryData.gallery, error]);

    /* set an error message */
    useEffect(() => {
        if (galleryData.error) {
            setError(galleryData.error);
        } else if (galleryData.error instanceof RefreshTokenExpiredError) {
            dispatch(authActions.logoutUser());
            navigate('/');
        } else {
            setError(null);
        }
    }, [galleryData.error]);

    if (galleryData.pending) {
        return <LoadingPage />;
    }

    if (!gallery && !userSession) {
        return <HomePublic />;
    }

    let addArtworkButton = gallery ?
        (<a href="/artwork-create" className="btn btn-dark m-1">Add Artwork</a>) :
        (<a href="/gallery-edit" className="btn btn-dark m-1">Create Gallery</a>);

    let edditGalleryButton = gallery &&
        (<a href="/gallery-edit" className="btn btn-secondary m-1">Edit Gallery</a>);

    let rating = gallery && (
        <div class="container">
            {<div class="starrating risingstar d-flex justify-content-center flex-row-reverse">
                <input type="radio" id="star5" name="rating" value="5" checked={gallery.ranking <= 5 && gallery.ranking > 4} disabled /><label for="star5" title="5 star"></label>
                <input type="radio" id="star4" name="rating" value="4" checked={gallery.ranking <= 4 && gallery.ranking > 3} disabled /><label for="star4" title="4 star"></label>
                <input type="radio" id="star3" name="rating" value="3" checked={gallery.ranking <= 3 && gallery.ranking > 2} disabled /><label for="star3" title="3 star"></label>
                <input type="radio" id="star2" name="rating" value="2" checked={gallery.ranking <= 2 && gallery.ranking > 1} disabled /><label for="star2" title="2 star"></label>
                <input type="radio" id="star1" name="rating" value="1" checked={gallery.ranking === 1} disabled /><label for="star1" title="1 star"></label>
            </div>}
        </div>);

    return (
        <div className="home-public-container">
            <MenuBar />
            <div className="container text-center m-auto">
                <section className="py-5 text-center container">
                    <div className="row py-lg-3">
                        <div className="col-lg-8 col-md-8 mx-auto">
                            <h1 className="fw-light">Wellcome {gallery?.ownerName ? (<a href={`/user/${gallery.ownerId}`} className="link-dark text-decoration-none">{gallery.ownerName}</a>) : ("to ArtConnect")}!</h1>
                            <p className="lead text-body-secondary">{gallery?.description ? gallery.description :
                                ("You don't have a gallery yet. Start showcasing your artworks and sharing your creative journey by creating a personalized gallery.")}
                            </p>
                            <p>

                                {addArtworkButton}
                                {edditGalleryButton}
                            </p>
                            {rating}
                        </div>
                    </div>
                </section>

                <div className="album py-5 bg-body-tertiary">
                    <div className="container-md">

                        <div className="row row-cols-1 row-cols-md-3 g-4 justify-content-center">
                            {gallery?.artworks?.length > 0 ? (
                                gallery.artworks.map((artwork, index) => (
                                    <div className="col" key={index}>
                                        <div className="card">
                                            <img
                                                src={artwork?.images?.length > 0 ? (`data:${artwork.images[0]?.contentType};base64,${artwork.images[0]?.image.data}`) : ImageTMP}
                                                alt="Thumbnail"
                                                className="card-img-top"
                                                style={{
                                                    objectFit: 'cover',
                                                    objectPosition: 'center',
                                                    height: '225px',
                                                    color: '#eceeef'
                                                }}
                                            />
                                            <div className="card-body">
                                                <h5 class="card-title">{artwork.title}</h5>
                                                <p className="card-text">{artwork?.description?.substring(0, 20) || "No description"}...</p>
                                                <div className="d-flex justify-content-between align-items-center">
                                                    <div className="btn-group">
                                                        <a as="button" className="btn btn-sm btn-outline-secondary" href={`/artworks/${artwork.id}`}>View</a>
                                                        <a as="button" className="btn btn-sm btn-outline-secondary" href={`/artworks-edit/${artwork.id}`}>Edit</a>
                                                    </div>
                                                    <small className="text-body-secondary">{artwork?.price ? (artwork.price + "Euro") : null}</small>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                ))
                            ) : (
                                <div className="col">
                                    <p className="lead text-body-secondary">No Artworks</p>
                                </div>
                            )}
                        </div>
                    </div>
                </div>
            </div>
            <Footer />
        </div>
    );
}

export default HomePrivate;