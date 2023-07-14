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
import { useParams } from 'react-router-dom';
function ArtWorkUser() {
    const [isLoading, setIsLoading] = useState(false);
    let { id } = useParams();
    const dispatch = useDispatch();
    const artworkData = useSelector(state => state.artwork);

    useEffect(() => {
        const fetchData = async () => {
            await dispatch(artworkActions.getArtwork(id));
        };
        fetchData();
    }, [dispatch]);

    if (!artworkData.artworks && !artworkData.error) {
        return <LoadingPage/>;
    }

    if (artworkData.error) {
        return <NotFoundPage/>;
    }

    const handleSearchSubmit = async (event) => {
    };

    return (
        <div className="home-public-container">
            <MenuBar/>
            {/*Images*/}
            <section className="h-100 gradient-custom-2 bg-body-tertiary">
                <div className="container-md h-100 m-auto">
                    <div className="row d-flex justify-content-center align-items-center h-100">
                        <div className="col w-100">
                            <div className="card ">
                                <div className="rounded-top text-white d-flex flex-row justify-content-center"
                                     style={{backgroundColor: '#212529', height: '250px'}}>
                                    <div className="ms-4 d-flex flex-column" style={{marginTop: '8rem', zIndex: '1'}}>
                                        <div className="mb-1">
                                            <img id="profileImage"
                                                 src={artworkData.artwork?.images?.length > 1?  (`data:${artworkData.artwork?.images[1]?.contentType};base64,${artworkData.artwork?.images[1]?.image.data}`) : ImageTMP}
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
                                                 src={artworkData.artwork?.images?.length > 2 ?  (`data:${artworkData.artwork?.images[2]?.contentType};base64,${artworkData.artwork?.images[2]?.image.data}`) : ImageTMP}
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
                                                 src={artworkData.artwork?.images?.length > 3 ?  (`data:${artworkData.artwork?.images[3]?.contentType};base64,${artworkData.artwork?.images[3]?.image.data}`) : ImageTMP}
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
                                                 src={artworkData.artwork?.images?.length > 4 ?  (`data:${artworkData.artwork?.images[4]?.contentType};base64,${artworkData.artwork?.images[4]?.image.data}`) : ImageTMP}
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
            {/*Header*/}
            <section className="h-100 gradient-custom-2 ">
                <div className="container-md h-100 m-auto">
                    <div className="row d-flex justify-content-center align-items-center h-100">
                        <header className=" bg-light border-bottom mb-4">
                            <div className="container ">
                                <div className="text-center my-5">
                                    <h1 className="fw-bolder">{artworkData.artwork?.title}</h1>
                                    <div className="like-count text-gray-400">{artworkData.artwork?.location === "" ?
                                        <span>unknown, </span> : <span>{artworkData.artwork?.location}, </span>} {artworkData.artwork?.yearOfCreation}
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
                        <div className="card mb-4" style={{ width: '52rem', height: '40rem' }}>
                            <img
                                className="card-img-top gradient-custom-2 bg-body-tertiary"
                                src={
                                    artworkData.artwork?.images?.length > 0
                                        ? `data:${artworkData.artwork?.images[0]?.contentType};base64,${artworkData.artwork?.images[0]?.image.data}`
                                        : ImageTMP
                                }
                                style={{ objectFit: 'contain', width: '100%', height: '100%',
                                    maxHeight: '100%',
                                    maxWidth: '100%'}}
                            />
                        </div>
                    </div>

                    <div className="col-lg-4">
                        <div className="card mb-4">
                            <div className="card-header">About</div>
                            <div className="card-body">
                                <div className="card-body d-flex flex-column">
                                    <ul className="list-group list-group-flush flex-grow-1">
                                        {artworkData.artwork?.price && <li className="list-group-item">Price: {artworkData.artwork.price} Euro</li>}
                                        {artworkData.artwork?.dimension && <li className="list-group-item">Height: {artworkData.artwork.dimension.height}cm Width: {artworkData.artwork.dimension.width}cm Depth: {artworkData.artwork.dimension.depth}cm</li>}
                                    </ul>
                                </div>
                            </div>
                        </div>
                        {artworkData.artwork?.materials.length > 0 &&
                            <div className="card mb-4">
                                <div className="card-header">Materials</div>
                                <div className="card-body">
                                    <div className="row">
                                        <div className="col-sm-12">
                                            <ul className="list-unstyled mb-0 d-flex flex-row flex-wrap">
                                                {artworkData.artwork?.materials?.map((material) => (

                                                    <li className="mx-2">{material}</li>
                                                ))}
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        }
                        {artworkData.artwork?.tags.length > 0 &&
                            <div className="card mb-4">
                                <div className="card-header">Tags</div>
                                <div className="card-body">
                                    <div className="row">
                                        <div className="col-sm-12">
                                            <ul className="list-unstyled mb-0 d-flex flex-row flex-wrap">
                                                {artworkData.artwork?.tags?.map((tag) => (

                                                    <li className="mx-2 tag tag-sm">#{tag}</li>
                                                ))}
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        }
                        <div className="card mb-4">
                            <div className="card-body">
                                <span className="bi bi-heart"></span>
                                <span className="like-count"> {artworkData.artwork?.likes} likes</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <Footer/>
        </div>
    );
}

export default ArtWorkUser;
