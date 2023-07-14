import React, { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { useParams } from "react-router-dom";
import { MenuBar } from './components/MenuBar';
import '../layout/css/homePublic.css';
import Footer from './components/Footer';
import LoadingPage from './components/LoadingPage';
import NotFoundPage from './errors/NotFoundPage';

import ImageTMP from '../images/pictures.png';

import * as galleryActions from '../../redux/gallery/GalleryAction';

function Gallery() {
    var userSession = JSON.parse(localStorage.getItem('userSession'));
    const { id } = useParams();

    const dispatch = useDispatch();
    const galleryData = useSelector(state => state.gallery);
    const [rating, setRating] = useState(null);
    const [error, setError] = useState();
    const [gallery, setGallery] = useState(null);

    /* get gallery by id */
    useEffect(() => {
        const fetchData = async () => {
            await dispatch(galleryActions.getGallery(id));
        };

        fetchData();
    }, [dispatch, id]);

    useEffect(() => {
        const fetchData = async () => {
            if(rating && userSession){
                await dispatch(galleryActions.putRating(id, parseInt(rating)));
                window.location.reload();
            }
        };

        fetchData();
    }, [dispatch, id, rating]);

    useEffect(() => {
        if (galleryData.gallery) {
            console.log(galleryData.gallery);
            setGallery(galleryData.gallery);
        }
    }, [galleryData.status, galleryData.gallery, error]);

    useEffect(() => {
        if (galleryData.error) {
            console.log(galleryData.error);
            setError(galleryData.error);
        }
    }, [galleryData.error]);

    const onChangeRating = async (e) => {
        console.log(e.target.value);
        setRating(e.target.value);
    }

    if (!gallery && !error) {
        return <LoadingPage />;
    }

    if (error) {
        return <NotFoundPage />;
    }

    return (
        <div className="home-public-container">
            <MenuBar />
            <div className="container text-center m-auto">
                <section className="py-5 text-center container">
                    <div className="row py-lg-3">
                        <div className="col-lg-6 col-md-8 mx-auto">
                            <h1 className="fw-light">Wellcome to {gallery?.title ? gallery.title : ("to ArtConnect")} Gallery!</h1>
                            <h5 className="fw-light">by {gallery?.ownerName ? (<a href={`/user/${gallery.ownerId}`} className="link-dark">{gallery.ownerName}</a>) : ("no name")}!</h5>
                            <p className="lead text-body-secondary mt-3">{gallery?.description ? gallery.description : ("No description.")} </p>
                            <div class="container">
                                {<div class="starrating risingstar d-flex justify-content-center flex-row-reverse" onChange={onChangeRating}>
                                    <input type="radio" id="star5" name="rating" value={5} checked={gallery.ranking <= 5 && gallery.ranking > 4} /><label for="star5" title="5 star"></label>
                                    <input type="radio" id="star4" name="rating" value={4} checked={gallery.ranking <= 4 && gallery.ranking > 3} /><label for="star4" title="4 star"></label>
                                    <input type="radio" id="star3" name="rating" value={3} checked={gallery.ranking <= 3 && gallery.ranking > 2} /><label for="star3" title="3 star"></label>
                                    <input type="radio" id="star2" name="rating" value={2} checked={gallery.ranking <= 2 && gallery.ranking > 1} /><label for="star2" title="2 star"></label>
                                    <input type="radio" id="star1" name="rating" value={1} checked={gallery.ranking === 1} /><label for="star1" title="1 star"></label>
                                </div>}
                            </div>
                        </div>
                    </div>
                </section>

                <div className="album py-5 bg-body-tertiary">
                    <div className="container-md">

                        <div className="row row-cols-1 row-cols-md-3 g-4">
                            {gallery.artworks?.length > 0 ? (
                                gallery.artworks.map((artwork, index) => (
                                    <div className="col" key={index}>
                                        <div className="card">
                                            <img
                                                src={artwork.images?.length > 0 ? (`data:${artwork.images[0]?.contentType};base64,${artwork.images[0]?.image.data}`) : ImageTMP}
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
                                                        <button type="button" className="btn btn-sm btn-outline-secondary">View</button>
                                                    </div>
                                                    <small className="text-body-secondary">{artwork?.price ? (artwork.price + "Euro") : null}</small>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                ))
                            ) : null}
                        </div>
                    </div>
                </div>

            </div>
            <Footer />
        </div>
    );
}

export default Gallery;