import React, { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { useNavigate } from "react-router-dom";
import { MenuBar } from './components/MenuBar';
import { ImageSlider } from './components/ImageSlider';
import '../layout/css/homePublic.css';
import Footer from './components/Footer';
import LoadingPage from './components/LoadingPage';
import HomePublic from './HomePublic';

import ImageTMP from '../images/pictures.png';

import * as galleryActions from '../../redux/gallery/GalleryAction';

function HomePrivate() {
    var userSession = JSON.parse(localStorage.getItem('userSession'));

    const dispatch = useDispatch();
    const navigate = useNavigate();
    const galleryData = useSelector(state => state.gallery);
    const [gallery, setGallery] = useState(galleryData.gallery || null);

    const placeholder = (
        <img
            src={ImageTMP}
            alt="Thumbnail"
            className="card-img-top"
            style={{ width: '100%', height: '225px', color: '#eceeef' }}
        />
    );

    /* get gallery from a current user */
    useEffect(() => {
        const fetchData = async () => {
            await dispatch(galleryActions.getMyGallery());
        };

        fetchData();
    }, [dispatch]);

    useEffect(() => {
        setGallery(galleryData.gallery);
    }, [galleryData]);

    if (!gallery && !galleryData.error) {
        return <LoadingPage />;
    }

    if (!gallery && !userSession) {
        return <HomePublic />;
    }

    let addArtworkButton = gallery ?
        (<a href="/artwork-edit" className="btn btn-dark m-1">Add Artwork</a>) :
        (<a href="/gallery-edit" className="btn btn-outline-dark m-1">Create Gallery</a>);

    let edditGalleryButton = gallery &&
        (<a href="/gallery-edit" className="btn btn-secondary m-1">Edit Gallery</a>);

    let rating = gallery && (
        <div class="container">
            <div class="starrating risingstar d-flex justify-content-center flex-row-reverse">
                <input type="radio" id="star5" name="rating" value="5" /><label for="star5" title="5 star"></label>
                <input type="radio" id="star4" name="rating" value="4" /><label for="star4" title="4 star"></label>
                <input type="radio" id="star3" name="rating" value="3" /><label for="star3" title="3 star"></label>
                <input type="radio" id="star2" name="rating" value="2" /><label for="star2" title="2 star"></label>
                <input type="radio" id="star1" name="rating" value="1" /><label for="star1" title="1 star"></label>
            </div>
        </div>);

    return (userSession ?
        <div className="home-public-container">
            <MenuBar />
            <div className="container text-center m-auto">
                <section className="py-5 text-center container">
                    <div className="row py-lg-5">
                        <div className="col-lg-6 col-md-8 mx-auto">
                            <h1 className="fw-light">Wellcome {gallery?.ownerName ? gallery.ownerName : null}!</h1>
                            <p className="lead text-body-secondary">{gallery?.description ? gallery.description :
                                (<p className="lead text-body-secondary"><a className='link-light text-decoration-none' href='/edit-gallery'>Add description to your gallery</a></p>)}</p>
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

                        <div className="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3">
                            {gallery.artworks?.length > 0 ? (
                                gallery.artworks.map((artwork, index) => (
                                    <div className="col">
                                        <div className="card shadow-sm">
                                            <img
                                                src={artwork.images?.length > 0 ? (`data:${artwork.images[0]?.contentType};base64,${artwork.images[0]?.image.data}`) : ImageTMP}
                                                alt="Thumbnail"
                                                className="card-img-top"
                                                style={{ width: '100%', height: '225px', color: '#eceeef' }}
                                            />
                                            <div className="card-body">
                                                <h5 class="card-title">{artwork.title}</h5>
                                                <p className="card-text">{artwork?.description?.substring(0, 20) || "No description"}...</p>
                                                <div className="d-flex justify-content-between align-items-center">
                                                    <div className="btn-group">
                                                        <button type="button" className="btn btn-sm btn-outline-secondary">View</button>
                                                        <button type="button" className="btn btn-sm btn-outline-secondary">Edit</button>
                                                    </div>
                                                    <small className="text-body-secondary">{artwork?.price ? (artwork.price + "Euro") : null}</small>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                ))
                            ) : (<p className='lead text-body-secondary'>No artwork yet.</p>)}
                        </div>
                    </div>
                </div>

            </div>
            <Footer />
        </div> : <HomePublic />
    );
}

export default HomePrivate;