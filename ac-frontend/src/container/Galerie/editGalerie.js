import React, { useState, useEffect } from 'react';
import {Link, Route, Routes, useNavigate, useLocation, useParams} from "react-router-dom";
import {GalerieApiService} from "../../lib/apiGalerie"
import {storageService} from "../../lib/localStorage"
import {logikService} from  "../../lib/service"
import HeaderLogedIn from "../../components/headerComponent/headerLogedIn";
import HeaderLogedOut from "../../components/headerComponent/headerLogout";
function EditGalerie (props) {
    const navigate = useNavigate();
    //User und Image Opject von der UserprofilSeite
    const location = useLocation();
    const { gallery, galerieId } = location.state;
    //Categorie handeling
    const [isLoggedIn, setIsLoggedIn] = React.useState(false);
    useEffect(()=>{
        async function getLoggedIn(){
            const loggedInHeader = await logikService.isLoggedIn();
            setIsLoggedIn(loggedInHeader);
            console.log("loggedIn: " + loggedInHeader)
        }
        getLoggedIn();
    },[])
    const categoriesOptions = [
        "PRINT",
        "PAINTING",
        "DRAWING_ILLUSTRATION",
        "PHOTOGRAPHY"
    ];
    const [gallerie, setGallerie] = useState({
        title: '',
        description: '',
        categories: []
    });

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
        const requestBody = {};
        if(gallerie.title !== ""){
            requestBody.title = gallerie.title;
        }
        if(gallerie.description !== ""){
            requestBody.description = gallerie.description;
        }
        if(gallerie.categories.length >0){
            requestBody.categories = gallerie.categories;
        }
        console.log(requestBody);
        await GalerieApiService.putSecuredData("/galleries/"+galerieId.replace(/"/g, ''),requestBody);
        navigate("/galerie")
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
                        defaultValue={gallery.title}
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
                        defaultValue={gallery.description}

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

export default EditGalerie;
