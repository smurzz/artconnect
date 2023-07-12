import * as React from 'react';
import {useEffect, useState} from "react";
import {GalerieApiService} from "../../lib/apiGalerie"
import {storageService} from "../../lib/localStorage"
import {ApiService} from "../../lib/api";
import {useNavigate, Link} from "react-router-dom";
import {GalerieService} from "../../lib/apiGalerie";
import PopupModal from "../../components/ModalPopUp/ModalPopUp";
import {logikService} from  "../../lib/service"
import HeaderLogedIn from "../../components/headerComponent/headerLogedIn";
import HeaderLogedOut from "../../components/headerComponent/headerLogout"
const MaterialForm = ({materials, setMaterials}) => {
    const [newMaterial, setNewMaterial] = useState('');
    const handleAddMaterial = (event) => {
        console.log("test");
        event.preventDefault();
        if (newMaterial.trim() !== '') {
            if (materials.length < 10) {
                setMaterials([...materials, newMaterial]);
                setNewMaterial('');
            } else {
                alert('Oops! We can not let you have to power of adding more then 10 Materials. Sorry!');
            }
        }
    };


    const handleDeleteMaterials = (event, index) => {
        event.preventDefault();
        console.log("handleDeleteMaterials: "+ index);
        const updatedMaterials = [...materials];
        updatedMaterials.splice(index, 1);
        setMaterials(updatedMaterials);
    };

    return (
        <div>
            <label>Materials:</label>
            <p>You can add upto 10 Materials</p>
            <ul>
                {materials.map((material, index) => (
                    <li key={index}>
                        {material}
                        <button className="btn btn-secondary mx-3" onClick={(event) => handleDeleteMaterials(event,index)}>Delete
                        </button>
                    </li>
                ))}
            </ul>
            <input
                type="text"
                value={newMaterial}
                onChange={(e) => setNewMaterial(e.target.value)}
            />
            <button className="btn btn-secondary mx-3" onClick={handleAddMaterial}>Add Material</button>
        </div>
    );
};
const TagForm = ({tags, setTags}) => {
    const [newTag, setNewTag] = useState('');
    const handleAddTag = (event) => {
        event.preventDefault();
        if (newTag.trim() !== '') {
            if (tags.length < 10) {
                setTags([...tags, newTag]);
                setNewTag('');
            } else {
                alert("Oops! We have decided that nobody should posse the Power to add more than 10 tags. You also shall not break the rule!");
            }
        }
    };
    const handleDeleteTag = (event,index) => {
        event.preventDefault();
        const updatedTags = [...tags];
        updatedTags.splice(index, 1);
        setTags(updatedTags);
    };
    return (
        <>
                <div>
                    <label>Tags:</label>
                    <p>You can add upto 10 Tags</p>
                    <ul>
                        {tags.map((tag, index) => (
                            <li key={index}>
                                {tag}
                                <button className="btn btn-secondary mx-3" onClick={(event) => handleDeleteTag(event,index)}>Delete
                                </button>
                            </li>
                        ))}
                    </ul>
                    <input
                        type="text"
                        value={newTag}
                        onChange={(e) => setNewTag(e.target.value)}
                    />
                    <button className="btn btn-secondary mx-3" onClick={handleAddTag}>Add Tag</button>
                </div>


        </>
    );
};

const BildErstellen = () => {
    const [titleError, setTitleError] = useState("");
    const [isTitleError, setIsTitleError] = useState(false);
    const [openModalPost, setOpenModalPost] = useState(false)
    const [idImage, setIdImage] = useState("");
    const [success, setSuccess]= useState(false);
    const currentYear = new Date().getFullYear();
    const [isLoggedIn, setIsLoggedIn] = React.useState(false);
    const navigate = useNavigate();
    useEffect(()=>{
        async function getLoggedIn(){
            const loggedInHeader = await logikService.isLoggedIn();
            setIsLoggedIn(loggedInHeader);
            console.log("loggedIn: " + loggedInHeader)
        }
        getLoggedIn();
    },[])

    const artDirectionsOptions = [
        "ABSTRACT",
        "REALISM",
        "IMPRESSIONISM",
        "SURREALISM",
        "EXPRESSIONISM",
        "MINIMALISM",
        "CUBISM",
        "POP_ART",
        "CONCEPTUAL_ART",
        "STREET_ART_GRAFFITI"
    ];

    //Modal
    const handleOpenModalPost = () => {
        setOpenModalPost(true);
    };

    const handleClosePostModal = () => {
        setOpenModalPost(false);
    };
    const handleArtDirectionChange = (artDirection) => {
        if (artwork.artDirections.includes(artDirection)) {
            setArtwork((prevArtwork) => ({
                ...prevArtwork,
                artDirections: prevArtwork.artDirections.filter((ad) => ad !== artDirection)
            }));
        } else {
            setArtwork((prevArtwork) => ({
                ...prevArtwork,
                artDirections: [...prevArtwork.artDirections, artDirection]
            }));
        }
    };

    const [artwork, setArtwork] = useState({
        title: "",
        images: [],
        description: "",
        yearOfCreation: "",
        materials: [],
        tags: [],
        dimension: {
            height: "",
            width: "",
            depth: ""
        },
        artDirections: [],
        price: 0,
        location: "",
        comments: null,
        likes: 0
    });

    const handleSubmit = async (e) => {
        e.preventDefault();
        // Validate the title
        if (artwork.title.trim() === "") {
            setIsTitleError(true);
            setTitleError("the Title cant be blank. Please Enter a valid title")
            return;
        }
        if (artwork.yearOfCreation !== "") {
            const year = parseInt(artwork.yearOfCreation, 10);
            if (year > currentYear) {
                alert(`You cannot be a time traveler! Please choose a year in the present or past.`);
                return;
            }
        }
        const result = await GalerieApiService.postSecuredData("/artworks", artwork)
        if(result !== null){
            setIdImage(result.data.id);
            handleOpenModalPost();
            setSuccess(true);
        }
        console.log(JSON.stringify(artwork));
    };

    useEffect(() => {
        setIsTitleError(false);
        setTitleError("");
    }, [artwork])

    const handleDimensionChange = (property, value) => {
        setArtwork((prevArtwork) => ({
            ...prevArtwork,
            dimension: {
                ...prevArtwork.dimension,
                [property]: parseFloat(value)
            }
        }));
    };

    return (
        <>
        {isLoggedIn? <HeaderLogedIn/>:<HeaderLogedOut/>}
        <div className="mx-auto max-w-7xl px-4 sm:px-6 lg:px-8 mt-7">

        <div>
        <div>


            <div>
                <p className="text-base font-semibold  text-gray-900">Post artwork</p>
                <div className="marginBottom">
                    <button
                        onClick={()=>{navigate("/galerie")}}
                        type="button"
                        className="inline-flex justify-center rounded-md bg-white px-3 py-2 text-sm font-semibold text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 hover:bg-gray-50"
                    >
                        <span>Back to Profile</span>
                    </button>
                </div>
            </div>
            <form onSubmit={handleSubmit}>
                <div className="form-group">
                    {isTitleError && <div className="alert alert-danger" role="alert">
                        {titleError}
                    </div>}
                    <div className="form-group">
                        <label>Title:</label>
                        <input
                            type="text"
                            className="form-control"
                            name="title"
                            value={artwork.title}
                            onChange={(e) => setArtwork({...artwork, title: e.target.value})}
                        />
                    </div>
                    <div className="form-group">
                        <label>Description:</label>
                        <input
                            type="text"
                            className="form-control"
                            name="description"
                            value={artwork.description}
                            onChange={(e) => setArtwork({...artwork, description: e.target.value})}
                        />
                    </div>
                    <div className="form-group">
                        <label>Year of Creation:</label>
                        <input
                            type="number"
                            className="form-control"
                            name="yearOfCreation"
                            value={artwork.yearOfCreation}
                            onChange={(e) => setArtwork({...artwork, yearOfCreation: e.target.value})}
                        />
                    </div>
                    <div className="form-group">

                        <label>Price:</label>
                        <input
                            type="number"
                            className="form-control"
                            name="yearOfCreation"
                            value={artwork.price}
                            onChange={(e) => setArtwork({...artwork, price: e.target.value})}
                        />
                    </div>
                    <MaterialForm
                        materials={artwork.materials}
                        setMaterials={(materials) => setArtwork({...artwork, materials})}
                    />
                    <TagForm
                        tags={artwork.tags}
                        setTags={(tags) => setArtwork({...artwork, tags})}/>
                    <p>Dimensions:</p>
                    <div className="d-flex align-items-center">
                        <div className="mr-3">
                        </div>
                        <div className="d-flex">
                            <input
                                type="number"
                                className="form-control mr-2"
                                name="height"
                                value={artwork.dimension.height}
                                onChange={(e) => handleDimensionChange("height", e.target.value)}
                            />

                            <input
                                type="number"
                                className="form-control mr-2"
                                name="width"
                                value={artwork.dimension.width}
                                onChange={(e) => handleDimensionChange("width", e.target.value)}
                            />

                            <input
                                type="number"
                                className="form-control"
                                name="depth"
                                value={artwork.dimension.depth}
                                onChange={(e) => handleDimensionChange("depth", e.target.value)}
                            />
                        </div>
                    </div>
                    <label>Art Directions:</label>
                    <div className="row">
                        {artDirectionsOptions.map((artDirection) => (
                            <div key={artDirection} className="col-md-3 mb-3">
                                <div className="form-check">
                                    <input
                                        type="checkbox"
                                        className="form-check-input"
                                        name="artDirections"
                                        value={artDirection}
                                        checked={artwork.artDirections.includes(artDirection)}
                                        onChange={() => handleArtDirectionChange(artDirection)}
                                    />
                                    <label className="form-check-label">{artDirection}</label>
                                </div>
                            </div>
                        ))}
                    </div>
                    <label>Location:</label>
                    <input
                        type="text"
                        className="form-control"
                        name="location"
                        value={artwork.location}
                        onChange={(e) => setArtwork({...artwork, location: e.target.value})}
                    />
                </div>
                <div className="mt-6 flex items-center justify-end gap-x-6">
                    <button type="button" className="text-sm font-semibold leading-6 text-gray-900"
                            onClick={() => {
                                navigate("/galerie");
                            }}
                    >
                        cancel
                    </button>
                    <button
                        type="submit"
                        className="inline-flex items-center rounded-md bg-indigo-600 px-3 py-2 text-sm font-semibold text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600 mt-7 mb-7"
                    >
                        save
                    </button>
                </div>

            </form>
        </div>
            {success && <PopupModal open={openModalPost} handleClose={handleClosePostModal} userId={idImage} />}


        </div>
        </div>
        </>
    )

};

export default BildErstellen;