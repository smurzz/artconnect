import React, { useState, useEffect } from 'react';
import { useParams, useNavigate} from 'react-router-dom';
import Header from "../../components/headerComponent/headerLogedIn";
import {GalerieApiService} from "../../lib/apiGalerie";
import {storageService} from "../../lib/localStorage";
import {logikService} from  "../../lib/service"
import HeaderLogedIn from "../../components/headerComponent/headerLogedIn";
import HeaderLogedOut from "../../components/headerComponent/headerLogout";
const PostGalerie = () => {
    const navigate = useNavigate();
    const { id } = useParams();
    const [gallerie, setGallerie] = useState({
        title: '',
        description: '',
        categories: []
    });
    const [isLoggedIn, setIsLoggedIn] = React.useState(false);
    useEffect(()=>{
        async function getLoggedIn(){
            const loggedInHeader = await logikService.isLoggedIn();
            setIsLoggedIn(loggedInHeader);
            console.log("loggedIn: " + loggedInHeader)
        }
        getLoggedIn();
    },[])
    //Categorie handeling
    const categoriesOptions = [
        "PRINT",
        "PAINTING",
        "DRAWING_ILLUSTRATION",
        "PHOTOGRAPHY"
    ];

    const handleCategoryChange = (category) => {
        if (gallerie.categories.includes(category)) {
            // Remove category if already selected
            setGallerie(prevGallerie => ({
                ...prevGallerie,
                categories: prevGallerie.categories.filter(cat => cat !== category)
            }));
        } else {
            // Add category if not selected
            setGallerie(prevGallerie => ({
                ...prevGallerie,
                categories: [...prevGallerie.categories, category]
            }));
        }
    };
    const handleSubmit = async (e) => {
        e.preventDefault();
        console.log("postGallerie: "+ id)
        const result = await GalerieApiService.postSecuredData("/galleries", gallerie);
        if(result){
            console.log("haldesubmit Galleries: "+ result.data.id);
            await storageService.saveGallerieId(result.data.id);
            const local = await storageService.getGallerieId();
                console.log("localstorage call: "+ local)
        }

        navigate('/galerie');
        console.log(gallerie);
    };

    return (
        <div className="container">
            {isLoggedIn? <HeaderLogedIn/>:<HeaderLogedOut/>}
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
                    {categoriesOptions.map(category => (
                        <div key={category} className="form-check">
                            <input
                                type="checkbox"
                                className="form-check-input"
                                name="categories"
                                value={category}
                                checked={gallerie.categories.includes(category)}
                                onChange={() => handleCategoryChange(category)}
                            />
                            <label className="form-check-label">{category}</label>
                        </div>
                    ))}
                </div>
                <button type="submit" className="btn btn-primary">
                    Submit
                </button>
            </form>
        </div>
    );
};

export default PostGalerie;
