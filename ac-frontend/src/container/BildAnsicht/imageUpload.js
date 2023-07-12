import React, {useEffect, useState} from 'react';
import { GalerieApiService } from '../../lib/apiGalerie';
import { useParams } from 'react-router-dom';
import { Card, Button } from 'react-bootstrap';
import {useNavigate, Link} from "react-router-dom";
import Image2 from '../../images/defaultArtworkPlaceholder.png';
import {logikService} from  "../../lib/service"
import HeaderLogedIn from "../../components/headerComponent/headerLogedIn";
import HeaderLogedOut from "../../components/headerComponent/headerLogout"
export default function ImageUploadComponent() {
    const [selectedFileMain, setSelectedFileMain] = useState(null);
    const [selectedFile1, setSelectedFile1] = useState(null);
    const [selectedFile2, setSelectedFile2] = useState(null);
    const [selectedFile3, setSelectedFile3] = useState(null);
    const [selectedFile4, setSelectedFile4] = useState(null);
    const[noFileUploaded, setNoFileUploaded] = useState(null);
    const navigate = useNavigate();
    const [isLoggedIn, setIsLoggedIn] = React.useState(false);
    useEffect(()=>{
        async function getLoggedIn(){
            const loggedInHeader = await logikService.isLoggedIn();
            setIsLoggedIn(loggedInHeader);
        }
        getLoggedIn();
    },[])

    const { id } = useParams();
    const handleFileChange = (event, index) => {
        const file = event.target.files[0];
        if (file) {
            const fileSize = file.size / 1024 / 1024; // Size in MB
            const allowedFormats = ['image/jpeg', 'image/jpg', 'image/png'];

            if (fileSize <= 5 && allowedFormats.includes(file.type)) {
                        setSelectedFileMain(file);
            } else {
                        setSelectedFileMain(null);
            }
        }
    };
    const handleFileUpload = async (event) => {
        setNoFileUploaded(false);
        console.log("datarouting");
        event.preventDefault();
        if (selectedFileMain) {
            console.log("image uploaded Main")
            let resultMain= await connectionToBackend(selectedFileMain);
        }
        console.log("image uploaded")
        navigate("/galerie");
    };

    async function connectionToBackend(file) {
        const formData = new FormData();
        formData.append('file', file);
        console.log("connectionToBackend");
        const result = await GalerieApiService.postSecuredImage(`/artworks/add-photo/${id}`, formData);
        return result;
    }

    return (
        <>
        {isLoggedIn? <HeaderLogedIn/>:<HeaderLogedOut/>}
        <div className="mx-auto max-w-2xl px-4 py-16 sm:px-6 sm:py-24 lg:max-w-7xl lg:px-8">

            {noFileUploaded == true && alert("Please Upload a file")}
            <div className="card-group d-flex justify-content-around">
                <div className="big-card">
                    <Card style={{ marginRight: "2rem" }}>
                        <Card.Img
                            className="imageUploadFile"
                            style={{ height: "30rem"}}
                            variant="top"
                            src={selectedFileMain !== null ? URL.createObjectURL(selectedFileMain) : Image2}
                        />                        <Card.Body>
                            <Card.Title>Big Image</Card.Title>
                            <Card.Text>
                                Upload Image
                            </Card.Text>
                            <input type="file" accept=".jpg, .jpeg, .png" onChange={(event)=>{handleFileChange(event,0)}} />
                        <button className="btn btn-primary mt-7" onClick={handleFileUpload}>Upload</button>
                        </Card.Body>
                    </Card>
                </div>
            </div>
        </div>
        </>
    );
}
