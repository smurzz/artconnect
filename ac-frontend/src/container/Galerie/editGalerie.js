import React, { useState, useEffect } from 'react';
import { useParams} from 'react-router-dom';
import Header from "../../components/headerComponent/headerLogedIn";
import {GalerieApiService} from "../../lib/apiGalerie"

const EditGalerie = () => {

    const { id } = useParams();
    const [gallerie, setGallerie] = useState({
        title: '',
        description: '',
        categories: []
    });

    
    const handleSubmit = async (e) => {
        e.preventDefault();
        console.log(id)
        await GalerieApiService.postGalerieWithId(id, gallerie);
        console.log(gallerie);
    };

    return (
        <div className="container">
            <Header/>
            <form onSubmit={handleSubmit}>
                <div className="form-group">
                    <label>Title:</label>
                    <input
                        type="text"
                        className="form-control"
                        name="title"
                        value={gallerie.title}
                        onChange={async (e) => {
                            setGallerie({...gallerie, title: e.target.value})
                        }}
                    />
                </div>
                <div className="form-group">
                    <label>Description:</label>
                    <textarea
                        className="form-control"
                        name="description"
                        onChange={async (e) => {
                            setGallerie({...gallerie, description: e.target.value})
                        }}
                        value={gallerie.description}
                    />
                </div>
                <div className="form-group">
                    <label>Categories:</label>
                    <input
                        type="text"
                        className="form-control"
                        name="categories"
                        value={gallerie.categories}
                        onChange={async (e) => {
                            setGallerie({...gallerie, categories: e.target.value})
                        }}
                    />
                </div>
                <button type="submit" className="btn btn-primary">
                    Submit
                </button>
            </form>
        </div>
    );
};

export default EditGalerie;
