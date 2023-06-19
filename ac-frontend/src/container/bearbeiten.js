import * as React from 'react';
import CssBaseline from '@mui/material/CssBaseline';
import axios from "../api/axios";

import Typography from '@mui/material/Typography';
import Container from '@mui/material/Container';

import {useState, useEffect, useRef} from "react";
import {Link, Route, Routes, useNavigate} from "react-router-dom";
import {ApiService} from "../lib/api";
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import CardMedia from '@mui/material/CardMedia';
import Grid from '@mui/material/Grid';
import Box from '@mui/material/Box';
import "./bearbeiten.css"

//lineaer loading
import LinearProgress from '@mui/material/LinearProgress';

import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap/dist/js/bootstrap.bundle.min";
//redux
import {useDispatch} from "react-redux";
import {connect} from 'react-redux';


function Bearbeiten(props) {
    //triggers Statechanges so the component reloads
    const navigate = useNavigate();

    const [image, setImage] = useState("");


    //load all Userdata from BE hier werden die User aus dem backend über
    const [users, setUsers] = useState([]);


    const [imageUrl, setImageUrl] = useState('');

    //lad die Userdaten aus dem Backend, wenn es ein userFoto gibt, convertiert er es in eine brauchbare URL
    useEffect(() => {
        async function getUserData (){
            const result = await ApiService.getDataSecured("/users/");
            setUsers(result.data);
            console.log("Users Image: "+ JSON.stringify(result.data[0].profilePhoto.image.data));
           /* const updatedUsers = result.data.map(user => {
                if(user.profilePhoto && user.profilePhoto.image.data){
                    const byteCharacters = atob(result.data[0].profilePhoto.image.data);
                    const byteNumbers = new Array(byteCharacters.length);
                    for (let i = 0; i < byteCharacters.length; i++) {
                        byteNumbers[i] = byteCharacters.charCodeAt(i);
                    }
                    const byteArray = new Uint8Array(byteNumbers);

                    // Create URL for the binary image data
                    const blob = new Blob([byteArray], { type: 'image/png' }); // Adjust the 'type' according to the actual image format
                    const url = URL.createObjectURL(blob);
                    setUsers({ ...user, imageUrl: url });
                }
            }*/
            const byteCharacters = atob(result.data[0].profilePhoto.image.data);
            const byteNumbers = new Array(byteCharacters.length);
            for (let i = 0; i < byteCharacters.length; i++) {
                byteNumbers[i] = byteCharacters.charCodeAt(i);
            }
            const byteArray = new Uint8Array(byteNumbers);

            // Create URL for the binary image data
            const blob = new Blob([byteArray], { type: 'image/png' }); // Adjust the 'type' according to the actual image format
            const url = URL.createObjectURL(blob);
            setImageUrl(url);
            console.log("url: "+ url);

            // Clean up the URL when the component is unmounted
            return () => {
                URL.revokeObjectURL(url);
            };
        }
       getUserData();
        //Convert Base64-encoded image data to binary form

    }, [])


    //edit userdata from BE
    const [userBearbeiten, setUserBearbeiten] = useState(
        {
            firstname: "",
            lastname: "",
            email: "",
            biography: "",
        }
    )
    //Contacts
    const [constact, setContacts] = useState({
        telefonNumber: "",
        address: {
            street: "",
            postalCode: "",
            city: "",
            country: ""
        },
    })
    //add ExhibitionValues
    const [exhibitionValues, setExhibitionValues] = useState([{title: "", location: "", year: "", description: ""}])
    let handleExhibition = (i, e) => {
        let newExhibition = [...exhibitionValues];
        newExhibition[i][e.target.name] = e.target.value;
        setExhibitionValues(newExhibition);
    }
    let addExhibitionFields = () => {
        setExhibitionValues([...exhibitionValues, {title: "", location: "", year: "", description: ""}])
    }
    let removeExhibitionFields = (i) => {
        let newExhibitions = [...exhibitionValues];
        newExhibitions.splice(i, 1);
        setExhibitionValues(newExhibitions)
    }
    //add Social Media
    const [socialMedia, setSocailMedia] = useState([{title: "", link: ""}])
    let handleSocailMedia = (i, e) => {
        let newSocial = [...socialMedia];
        newSocial[i][e.target.name] = e.target.value;
        setSocailMedia(newSocial);
    }
    let addSocialMediaField = () => {
        setSocailMedia([...socialMedia, {title: "", link: ""}])
    }
    let removeSocialMediaFields = (i) => {
        let newSocialMedia = [...socialMedia];
        newSocialMedia.splice(i, 1);
        setSocailMedia(newSocialMedia)
    }

    //file Upload
    const [selectedFile, setSelectedFile] = useState(null);
    const [errorMessage, setErrorMessage] = useState('');

    const handleFileChange = (event) => {
        const file = event.target.files[0];
        if (file) {
            const fileSize = file.size / 1024 / 1024; // Size in MB
            const allowedFormats = ['image/jpeg', 'image/jpg', 'image/png'];

            if (fileSize <= 5 && allowedFormats.includes(file.type)) {
                setSelectedFile(file);
                console.log("Fileupload: "+ selectedFile)
                setErrorMessage('');
            } else {
                setSelectedFile(null);
                setErrorMessage('Invalid file format or size exceeds 5MB.');
            }
        }
    };



    const handleFileUpload = async (event) => {
        event.preventDefault();
        console.log("inside handleFileUpload")
        if (selectedFile) {
            const formData = new FormData();
            formData.append('file', selectedFile);
            console.log("inside handle FileUpload: "+JSON.stringify( users[0]));
            await ApiService.sendImage(formData);
        }
    }

const handleSubmit = async (event) => {
    event.preventDefault();
    //build the request Body:
    // Create the request body object
    const requestBody = {};
    // Add userBearbeiten fields
    if (userBearbeiten.firstname !== "") {
        requestBody.firstname = userBearbeiten.firstname;
    }
    if (userBearbeiten.lastname !== "") {
        requestBody.lastname = userBearbeiten.lastname;
    }
    if (userBearbeiten.email !== "") {
        requestBody.email = userBearbeiten.email;
    }
    if (userBearbeiten.biography !== "") {
        requestBody.biography = userBearbeiten.biography;
    }
    // Add exhibitionValues fields
    const exhibitions = exhibitionValues.filter((exhibition) => exhibition.title !== "" && exhibition.link !== "");
    if (exhibitions.length > 0) {
        requestBody.exhibitions = exhibitions;
    }
    // Add constact fields
    if (constact.telefonNumber !== "") {
        requestBody.telefonNumber = constact.telefonNumber;
    }
    const addressFields = Object.values(constact.address);
    if (addressFields.some((field) => field !== "")) {
        requestBody.address = constact.address;
    }
    if (constact.website !== "") {
        requestBody.website = constact.website;
    }
    // Add socialMedia fields
    const socialMedias = socialMedia.filter((media) => media.title !== "" && media.link !== "");
    if (socialMedias.length > 0) {
        requestBody.socialMedias = socialMedias;
    }
    console.log("Request Body: ", requestBody);
    const result = await ApiService.patchdataSecured("/users/" + users[0].id, requestBody);
}
return (
    <>
        <p>
            <Container sx={{py: 8}} maxWidth="md">
                {/*user map*/}
                <Grid container spacing={4}>
                    {users.length > 0 && (
                        users.map((card) => (
                            <Grid>
                                <Card
                                    sx={{height: '100%', display: 'flex', flexDirection: 'column'}}
                                >
                                    <CardMedia
                                        component="div"
                                        sx={{
                                            // 16:9
                                            pt: '56.25%',
                                        }}
                                        image={imageUrl}
                                    />
                                    <CardContent sx={{flexGrow: 1}}>
                                        <Typography gutterBottom variant="h5" component="h2">
                                            <p>{card.firstname + " " + card.lastname} </p>
                                            <p>{card.email + " " + card.biography} </p>
                                            <p>{JSON.stringify(card.exhibitions) + " " + JSON.stringify(card.contacts)} </p>
                                            <p>{JSON.stringify(card.socialMedias) + " "} </p>
                                        </Typography>
                                    </CardContent>
                                </Card>
                            </Grid>
                        ))
                    )}

                </Grid>
            </Container>
        </p>
        <Container component="main">
            <CssBaseline/>
            <form onSubmit={handleSubmit}>
                <div className="button-section">
                    <br/>
                    <br/>

                    <p className="underline">User Credentials</p>
                    <label className="inputField">firstname</label>
                    <input className="inputField" type="text" name="firstname" value={userBearbeiten.firstname}
                           onChange={async (e) => {
                               setUserBearbeiten({...userBearbeiten, firstname: e.target.value})
                           }}/>
                    <label className="inputField">lastname</label>
                    <input className="inputField" type="text" name="lastname" value={userBearbeiten.lastname}
                           onChange={async (e) => {
                               setUserBearbeiten({...userBearbeiten, lastname: e.target.value})
                           }}/>
                    <label className="inputField">email</label>
                    <input className="inputField" type="text" name="email" value={userBearbeiten.email}
                           onChange={async (e) => {
                               setUserBearbeiten({...userBearbeiten, email: e.target.value})
                           }}/>
                    <label className="inputField">biography</label>
                    <input className="inputField" type="text" name="biography" value={userBearbeiten.biography}
                           onChange={async (e) => {
                               setUserBearbeiten({...userBearbeiten, biography: e.target.value})
                           }}/>

                    <p className="underline">Exhibitions</p>
                    {exhibitionValues.map((element, index) => (

                        <div className="form-inline" key={index}>
                            {
                                index ?
                                    <Button type="button" className="button inputField remove"
                                            onClick={() => removeExhibitionFields(index)}>Remove</Button>
                                    : null
                            }
                            <label className="inputField">Titel</label>
                            <input className="inputField" type="text" name="title" value={element.title || ""}
                                   onChange={e => handleExhibition(index, e)}/>
                            <label className="inputField">location</label>
                            <input className="inputField" type="text" name="location" value={element.location || ""}
                                   onChange={e => handleExhibition(index, e)}/>
                            <label className="inputField">year</label>
                            <input className="inputField" type="text" name="year" value={element.year || ""}
                                   onChange={e => handleExhibition(index, e)}/>
                            <label className="inputField">description</label>
                            <input className="inputField" type="text" name="description"
                                   value={element.description || ""} onChange={e => handleExhibition(index, e)}/>
                        </div>
                    ))}

                    <Button className="button add" type="button" onClick={() => addExhibitionFields()}>Add</Button>
                    <br/>

                    <p className="underline">Telefonnummer</p>
                    <label className="inputField">telefonNumber</label>
                    <input className="inputField" type="text" name="telefonNumber" value={constact.telefonNumber || ""}
                           onChange={async (e) => {
                               setContacts({...constact, telefonNumber: e.target.value})
                           }}/>

                    <p className="underline">Adresss</p>
                    <label className="inputField">street</label>
                    <input className="inputField" type="text" name="street" value={constact.address.street}
                           onChange={(e) => {
                               setContacts({
                                   ...constact,
                                   address: {
                                       ...constact.address,
                                       street: e.target.value
                                   }
                               });
                           }}/>

                    <label className="inputField">postalCode</label>
                    <input className="inputField" type="text" name="postalCode" value={constact.address.postalCode}
                           onChange={(e) => {
                               setContacts({
                                   ...constact,
                                   address: {
                                       ...constact.address,
                                       postalCode: e.target.value
                                   }
                               });
                           }}/>
                    <label className="inputField">city</label>
                    <input className="inputField" type="text" name="city" value={constact.address.city}
                           onChange={(e) => {
                               setContacts({
                                   ...constact,
                                   address: {
                                       ...constact.address,
                                       city: e.target.value
                                   }
                               });
                           }}/>

                    <label className="inputField">country</label>
                    <input className="inputField" type="text" name="country" value={constact.address.country}
                           onChange={(e) => {
                               setContacts({
                                   ...constact,
                                   address: {
                                       ...constact.address,
                                       country: e.target.value
                                   }
                               });
                           }}
                    />

                    <label className="inputField">website</label>
                    <input className="inputField" type="text" name="country" value={constact.address.website}
                           onChange={(e) => {
                               setContacts({
                                   ...constact,
                                   website: e.target.value
                               });
                           }}
                    />
                    <br/>
                    <p className="underline">Social Media</p>
                    {socialMedia.map((element, index) => (

                        <div className="form-inline" key={index}>
                            {
                                index ?
                                    <Button type="button" className="button inputField remove"
                                            onClick={() => removeSocialMediaFields(index)}>Remove</Button>
                                    : null
                            }
                            <label className="inputField">Title</label>
                            <input className="inputField" type="text" name="title" value={element.title || ""}
                                   onChange={e => handleSocailMedia(index, e)}/>
                            <label className="inputField">location</label>
                            <input className="inputField" type="text" name="link" value={element.link || ""}
                                   onChange={e => handleSocailMedia(index, e)}/>
                        </div>
                    ))}
                    <Button className="button add" type="button" onClick={() => addSocialMediaField()}>Add</Button>
                </div>
                <Button className="button submit" type="submit">Submit</Button>
            </form>
            <form>
                <p className="underline">Upload Image</p>
                <div>
                    <input type="file" accept=".jpg, .jpeg, .png" onChange={handleFileChange} />
                    {errorMessage && <p>{errorMessage}</p>}
                    <button onClick={handleFileUpload}>Upload</button>
                </div>
            </form>
        </Container>
    </>
);
}

export default Bearbeiten;