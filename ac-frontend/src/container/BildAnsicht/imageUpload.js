import React, { useState } from 'react';
import { GalerieApiService } from '../../lib/apiGalerie';
import { useParams } from 'react-router-dom';
import { Card, Button } from 'react-bootstrap';
import {useNavigate, Link} from "react-router-dom";
import Image2 from './../Galerie/imgSlides/original2.jpg';

export default function ImageUploadComponent() {
    const [selectedFileMain, setSelectedFileMain] = useState(null);
    const [selectedFile1, setSelectedFile1] = useState(null);
    const [selectedFile2, setSelectedFile2] = useState(null);
    const [selectedFile3, setSelectedFile3] = useState(null);
    const [selectedFile4, setSelectedFile4] = useState(null);
    const[noFileUploaded, setNoFileUploaded] = useState(null);
    const navigate = useNavigate();

    const { id } = useParams();

    const handleFileChange = (event, index) => {
        const file = event.target.files[0];
        if (file) {
            const fileSize = file.size / 1024 / 1024; // Size in MB
            const allowedFormats = ['image/jpeg', 'image/jpg', 'image/png'];

            if (fileSize <= 5 && allowedFormats.includes(file.type)) {
                switch (index) {
                    case 0:
                        setSelectedFileMain(file);
                        break;
                    case 1:
                        setSelectedFile1(file);
                        break;
                    case 2:
                        setSelectedFile2(file);
                        break;
                    case 3:
                        setSelectedFile3(file);
                        break;
                    case 4:
                        setSelectedFile4(file);
                        break;
                    default:
                        break;
                }
            } else {


                switch (index) {
                    case 0:
                        setSelectedFileMain(null);
                        break;
                    case 1:
                        setSelectedFile1(null);
                        break;
                    case 2:
                        setSelectedFile2(null);
                        break;
                    case 3:
                        setSelectedFile3(null);
                        break;
                    case 4:
                        setSelectedFile4(null);
                        break;
                    default:
                        break;
                }
            }
        }
    };
    const handleFileUpload = async (event) => {
        setNoFileUploaded(false);
        event.preventDefault();
        if (selectedFileMain) {
            console.log("image uploaded Main")
            let resultMain= await connectionToBackend(selectedFileMain);
        }

        if (selectedFile1) {
            console.log("image uploaded 1")
           let result1= await connectionToBackend(selectedFile1);
        }

        if (selectedFile2) {
            console.log("image uploaded 2")
            let result2= await connectionToBackend(selectedFile2);
        }

        if (selectedFile3) {
            console.log("image uploaded 3")
            let result3= await connectionToBackend(selectedFile3);
        }

        if (selectedFile4) {
            console.log("image uploaded 4")
            let result4= await connectionToBackend(selectedFile4);
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
        <div className="mx-auto max-w-2xl px-4 py-16 sm:px-6 sm:py-24 lg:max-w-7xl lg:px-8">
            {noFileUploaded == true && alert("Please Upload a file")}
            <div className="card-group d-flex justify-content-between">
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
                                Upload a big image here.
                            </Card.Text>
                            <input type="file" accept=".jpg, .jpeg, .png" onChange={(event)=>{handleFileChange(event,0)}} />
                        </Card.Body>
                    </Card>
                </div>
                <div className="small-cards d-flex justify-content-between flex-column"
                >
                    <Card>
                        <Card.Img
                            className="imageUploadFile"
                            style={{ height: "12rem"}}
                            variant="top"
                            src={selectedFile1 !== null ? URL.createObjectURL(selectedFile1) : Image2}
                        />
                        <Card.Body>
                            <Card.Title>Small Image</Card.Title>
                            <Card.Text>
                                Upload a small image here.
                            </Card.Text>
                            <input type="file" accept=".jpg, .jpeg, .png"onChange={(event)=>{handleFileChange(event,1)}} />

                            {/* Add input and button for small image 1 */}
                        </Card.Body>
                    </Card>
                    <Card>
                        <Card.Img
                            className="imageUploadFile"
                            style={{ height: "12rem"}}
                            variant="top"
                            src={selectedFile2 !== null ? URL.createObjectURL(selectedFile2) : Image2}
                        />
                        <Card.Body>
                            <Card.Title>Small Image</Card.Title>
                            <Card.Text>
                                Upload a small image here.
                            </Card.Text>
                            <input type="file" accept=".jpg, .jpeg, .png" onChange={(event)=>{handleFileChange(event,2)}} />

                            {/* Add input and button for small image 2 */}
                        </Card.Body>
                    </Card>
                </div>
                    <div className="small-cards d-flex justify-content-between flex-column"
                    >
                    <Card>
                        <Card.Img
                            className="imageUploadFile"
                            style={{ height: "12rem"}}
                            variant="top"
                            src={selectedFile3 !== null ? URL.createObjectURL(selectedFile3) : Image2}
                        />
                        <Card.Body>
                            <Card.Title>Small Image</Card.Title>
                            <Card.Text>
                                Upload a small image here.
                            </Card.Text>
                            <input type="file" accept=".jpg, .jpeg, .png" onChange={(event)=>{handleFileChange(event,3)}} />
                            {/* Add input and button for small image 3 */}
                        </Card.Body>
                    </Card>
                    <Card>
                        <Card.Img
                            className="imageUploadFile"
                            style={{ height: "12rem"}}
                            variant="top"
                            src={selectedFile4 !== null ? URL.createObjectURL(selectedFile4) : Image2}
                        />
                        <Card.Body>
                            <Card.Title>Small Image</Card.Title>
                            <Card.Text>
                                Upload a small image here.
                            </Card.Text>
                            <input type="file" accept=".jpg, .jpeg, .png" onChange={(event)=>{handleFileChange(event,4)}} />
                            {/* Add input and button for small image 4 */}
                        </Card.Body>
                    </Card>
                </div>
            </div>
            <Button variant="primary" onClick={handleFileUpload}>Upload</Button>
        </div>
    );
}
