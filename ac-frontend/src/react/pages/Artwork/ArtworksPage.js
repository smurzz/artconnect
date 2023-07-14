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

import ImageTMP from '../../images/images.svg';

import * as artworkActions from '../../../redux/artwork/ArtworkAction';

function ArtworksPage() {
    const dispatch = useDispatch();

    const [searchValue, setSearchValue] = useState('');
    const artworkData = useSelector(state => state.artwork);
    const [tags, setTags] = useState([]);

    useEffect(() => {
        if (!searchValue) {
            const fetchData = async () => {
                await dispatch(artworkActions.getArtworks());
            };
            fetchData();
        }
    }, [dispatch, searchValue]);

    if (!artworkData.artworks && !artworkData.error) {
        return <LoadingPage/>;
    }

    if (artworkData.error) {
        return <NotFoundPage/>;
    }

    const handleSearchSubmit = async (event) => {
        event.preventDefault();
        var splitedSeachValue = searchValue.split(' ').toString();

        setTags(splitedSeachValue);
        await dispatch(artworkActions.getArtworksByTags(splitedSeachValue));
    };

    return (
        <div className="home-public-container">
            <MenuBar/>
            <div className="container text-center m-auto">
                <section className="py-3 text-center container">
                    <div className="row py-lg-5">
                        <div className="col-lg-6 col-md-8 mx-auto">
                            <h1 className="fw-light">Artworks</h1>
                        </div>
                        <Form className="d-flex mt-3" onSubmit={handleSearchSubmit}>
                            <Form.Control
                                type="search"
                                placeholder="Search by tags"
                                className="me-2"
                                aria-label="Search"
                                value={searchValue}
                                onChange={async (e) => {
                                    setSearchValue(e.target.value);
                                }}
                            />
                            <Button variant="outline-secondary" onClick={handleSearchSubmit}>Search</Button>
                        </Form>
                    </div>
                </section>

                <div className="album py-5">
                    <div className="container-md">

                        <div className="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3 justify-content-center">
                            {artworkData.artworks?.length > 0 ? (
                                artworkData.artworks.map((artwork, index) => (
                                    <div className="col">
                                        <div className="card shadow-sm">
                                            <img
                                                src={artwork.images?.length > 0 ? (`data:${artwork.images[0]?.contentType};base64,${artwork.images[0]?.image.data}`) : ImageTMP}
                                                alt="Thumbnail"
                                                className="card-img-top"
                                                style={{ objectFit: 'cover',width: '100%', height: '300px', color: '#eceeef'}}
                                            />
                                            <div className="card-body">
                                                <h5 className="card-title">{artwork.title}</h5>
                                                <p className="card-text"> {artwork?.description?.substring(0, 20) || "No description "}...</p>
                                                <div className="d-flex justify-content-between align-items-center">
                                                    <div className="btn-group">

                                                        <p><a className="btn btn-secondary" href={`/artwork/${artwork.id}`}>View</a></p>
                                                    </div>
                                                    <small
                                                        className="text-body-secondary">{artwork?.price ? (artwork.price + "Euro") : null}</small>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                ))
                            ) : (<div className="d-flex align-items-center justify-content-center flex-column">
                                    <p className='lead text-body-secondary'>No artworks found with Tags:</p>
                                    <div>
                                        {tags?.map((tag) => (
                                            <p className='lead text-body-secondary tag tag-sm'>#{tag}</p>
                                        ))}
                                    </div>
                                </div>
                            )}
                        </div>
                    </div>
                </div>
            </div>
            <Footer/>
        </div>
    );
}

export default ArtworksPage;
