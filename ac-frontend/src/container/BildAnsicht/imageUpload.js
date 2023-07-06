import React, { useState } from 'react';
import { GalerieApiService } from '../../lib/apiGalerie';
import { useParams } from 'react-router-dom';
import { Card, Button } from 'react-bootstrap';
import Image2 from './../Galerie/imgSlides/original2.jpg';

export default function ImageUploadComponent() {
    const [selectedFile, setSelectedFile] = useState(null);
    const { id } = useParams();

    const handleFileChange = (event) => {
        const file = event.target.files[0];
        if (file) {
            const fileSize = file.size / 1024 / 1024; // Size in MB
            const allowedFormats = ['image/jpeg', 'image/jpg', 'image/png'];

            if (fileSize <= 5 && allowedFormats.includes(file.type)) {
                setSelectedFile(file);
            } else {
                setSelectedFile(null);
            }
        }
    };

    const handleFileUpload = async (event) => {
        event.preventDefault();
        console.log(selectedFile);
        if (selectedFile) {
            const formData = new FormData();
            formData.append('file', selectedFile);
            const result = await GalerieApiService.postSecuredImage(`/artworks/add-photo/${id}`, formData);
        }
    };

    return (
        <div className="mx-auto max-w-2xl px-4 py-16 sm:px-6 sm:py-24 lg:max-w-7xl lg:px-8">

        <div className="col-span-full">
            <div className="card-group">
                <div className="big-card">
                    <Card>
                        <Card.Img style={{ height: "30rem" , margin-right: "3rem"}} variant="top" src={selectedFile !== null ? URL.createObjectURL(selectedFile) : {Image2}} />
                        <Card.Body>
                            <Card.Title>Big Image</Card.Title>
                            <Card.Text>
                                Upload a big image here.
                            </Card.Text>
                            <input type="file" accept=".jpg, .jpeg, .png" onChange={handleFileChange} />
                            <Button variant="primary" onClick={handleFileUpload}>Upload</Button>
                        </Card.Body>
                    </Card>
                </div>
                <div className="small-cards">
                    <Card>
                        <Card.Img variant="top" src="" />
                        <Card.Body>
                            <Card.Title>Small Image 1</Card.Title>
                            <Card.Text>
                                Upload a small image here.
                            </Card.Text>
                            {/* Add input and button for small image 1 */}
                        </Card.Body>
                    </Card>
                    <Card>
                        <Card.Img variant="top" src="" />
                        <Card.Body>
                            <Card.Title>Small Image 2</Card.Title>
                            <Card.Text>
                                Upload a small image here.
                            </Card.Text>
                            {/* Add input and button for small image 2 */}
                        </Card.Body>
                    </Card>
                    <Card>
                        <Card.Img variant="top" src="" />
                        <Card.Body>
                            <Card.Title>Small Image 3</Card.Title>
                            <Card.Text>
                                Upload a small image here.
                            </Card.Text>
                            {/* Add input and button for small image 3 */}
                        </Card.Body>
                    </Card>
                    <Card>
                        <Card.Img variant="top" src="" />
                        <Card.Body>
                            <Card.Title>Small Image 4</Card.Title>
                            <Card.Text>
                                Upload a small image here.
                            </Card.Text>
                            {/* Add input and button for small image 4 */}
                        </Card.Body>
                    </Card>
                </div>
            </div>
        </div>
        </div>
    );
}
