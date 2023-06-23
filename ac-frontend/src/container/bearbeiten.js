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
import "./bearbeiten.css";
import Header from "../components/headerComponent/headerLogedIn"
import { useLocation } from 'react-router-dom';
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';

//lineaer loading
import LinearProgress from '@mui/material/LinearProgress';

import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap/dist/js/bootstrap.bundle.min";
//redux
import {useDispatch} from "react-redux";
import {connect} from 'react-redux';
import { PhotoIcon, UserCircleIcon } from '@heroicons/react/24/solid';

function Bearbeiten(props) {
    const navigate = useNavigate();

    //triggers Statechanges so the component reloads
    const location = useLocation();
    const { user, imageProps } = location.state;

    const [image, setImage] = useState("");

    //load all Userdata from BE hier werden die User aus dem backend über
    const [users, setUsers] = useState([]);
    const [dateOfBirth, setDateOfBirth] = useState( new Date());
    const [imageUrl, setImageUrl] = useState('');


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

    //lad die Userdaten aus dem Backend, wenn es ein userFoto gibt, convertiert er es in eine brauchbare URL
    useEffect(() => {
        if(user.socialMedias != undefined){
            setSocailMedia([...socialMedia, ...user.socialMedias])
            console.log("socialMedias: 1"+ JSON.stringify(socialMedia))
        }
        if(user.exhibitions != undefined){
            setExhibitionValues([...exhibitionValues, ...user.exhibitions])
            console.log("socialMedias: 1"+ JSON.stringify(socialMedia))
        }
        async function getUserData (){
            const result = await ApiService.getDataSecured("/users/");
            setUsers(result.data);
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

            // Clean up the URL when the component is unmounted
            return () => {
                URL.revokeObjectURL(url);
            };
        }
        getUserData();
        //Convert Base64-encoded image data to binary form

    }, [])

    //file Upload
    const [selectedFile, setSelectedFile] = useState(null);

    const [errAlert, seterrAlert] = React.useState("");
    const [errorMessage, setErrorMessage] = useState(false);

    const [successMessage, setSuccessMessage] = React.useState("");
    const [success, setSuccess] = useState(false);


    const handleFileChange = (event) => {
        const file = event.target.files[0];
        if (file) {
            const fileSize = file.size / 1024 / 1024; // Size in MB
            const allowedFormats = ['image/jpeg', 'image/jpg', 'image/png'];

            if (fileSize <= 5 && allowedFormats.includes(file.type)) {
                setSelectedFile(file);
                setErrorMessage('');
            } else {
                setSelectedFile(null);
                setErrorMessage(true);
                seterrAlert('Invalid file format or size exceeds 5MB.');
            }
        }
    };



    const handleFileUpload = async (event) => {
        event.preventDefault();

        if (selectedFile) {
            const formData = new FormData();
            formData.append('file', selectedFile);
            const imageUploadSuccessfull =await ApiService.sendImage(formData);
            if(imageUploadSuccessfull == null){


                setErrorMessage(true);
                seterrAlert("We couldnt Upload your Image! Please try again");
            }else{
                setSuccess(true);
                setSuccessMessage("You Uploaded a new Profil Picture");
            }
        }
    }
    
const handleSubmit = async (event) => {
    event.preventDefault();
    const requestBody = {};
    // Add userBearbeiten fields
    if (userBearbeiten.firstname !== "") {
        requestBody.firstname = userBearbeiten.firstname;
    }

        const currentDate = new Date();
        const formattedDate = currentDate.toISOString().split('T')[0];

    if (userBearbeiten.lastname !== "") {
        requestBody.lastname = userBearbeiten.lastname;
    }
    if (userBearbeiten.biography !== "") {
        requestBody.biography = userBearbeiten.biography;
    }
    // Add exhibitionValues fields
    const exhibitions = exhibitionValues.filter((exhibition) => exhibition.title !== "" && exhibition.link !== "");
    if (exhibitions.length > 0) {
        requestBody.exhibitions = exhibitions;
    }
    const addressFields = Object.values(constact.telefonNumber);
    if (addressFields.some((field) => field !== "")) {
        requestBody.contacts.telefonNumber = constact.telefonNumber;
    }
    if (constact.website !== "") {
        requestBody.website = constact.website;
    }
    // Add socialMedia fields
    const socialMedias = socialMedia.filter((media) => media.title !== "" && media.link !== "");
    if (socialMedias.length > 0) {
        requestBody.socialMedias = socialMedias;
    }
console.log("requestBody: "+ requestBody);
    const result = await ApiService.patchdataSecured("/users/" + user.id, requestBody);
    if(result == null){
        navigate("/galerie");
        setErrorMessage(true);
        seterrAlert("We couldnt Upload your Image! Please try again");
    }else{
        navigate("/galerie");
    }
}

return (
    <>
        <Header/>
    {/* tailwind ui */}
    <div className="mx-auto max-w-7xl px-4 sm:px-6 lg:px-8">
      {/* We've used 3xl here, but feel free to try other max-widths based on your needs */}
      <div className="mx-auto max-w-3xl">
    <form onSubmit={handleSubmit}>
      <div className="space-y-12">
        <div className="border-b border-gray-900/10 pb-12">
            <div>
                <p className="text-base font-semibold leading-7 text-gray-900">Profile</p>
                <div className="marginBottom">
                <button
                    onClick={() => {
                        navigate("/galerie", { state: { user: user, imageProps: image } });
                    }}
                    type="button"
                    className="inline-flex justify-center rounded-md bg-white px-3 py-2 text-sm font-semibold text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 hover:bg-gray-50"
                >
                    <span>Back to Profile</span>
                </button>
                </div>
                {errorMessage && <div className="alert alert-danger" role="alert">
                    {errAlert}
                </div>}
            </div>

          <p className="mt-1 text-sm leading-6 text-gray-600">
            Diese Informationen werden öffentlich dargestellt. Sei also Vorsichtig welche Informationen du preis gibst!
          </p>

          <div className="mt-10 grid grid-cols-1 gap-x-6 gap-y-8 sm:grid-cols-6">

            <div className="col-span-full">
              <label htmlFor="biography" className="block text-sm font-medium leading-6 text-gray-900">
                Biographie
              </label>
              <div className="mt-2">
                <textarea
                  id="biography"
                  name="biography"
                  rows={3}
                  className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
                  defaultValue= {user.biography? user.biography : " " }
                           onChange={async (e) => {
                               setUserBearbeiten({...userBearbeiten, biography: e.target.value})
                           }}
                />
              </div>
              <p className="mt-3 text-sm leading-6 text-gray-600">Schreib ein paar Sätze über dich.</p>
            </div>

            <div className="col-span-full">
              <label htmlFor="photo" className="block text-sm font-medium leading-6 text-gray-900">
                Profil Bild
              </label>
                {success && <div className="alert alert-primary" role="alert">
                    {successMessage}
                </div>}
              <form>

                <div className="mt-2 flex items-center gap-x-3">
                    <UserCircleIcon className="h-12 w-12 text-gray-300" aria-hidden="true" />
                    <input type="file" accept=".jpg, .jpeg, .png" onChange={handleFileChange} />

                    <button onClick={handleFileUpload}>Upload</button>
                </div>
              </form>
            </div>

          </div>
        </div>

        <div className="border-b border-gray-900/10 pb-12">
          <h2 className="text-base font-semibold leading-7 text-gray-900">Persönliche Informationen</h2>

          <div className="mt-10 grid grid-cols-1 gap-x-6 gap-y-8 sm:grid-cols-6">
            <div className="sm:col-span-3">
              <label htmlFor="firstname" className="block text-sm font-medium leading-6 text-gray-900">
                Vorname
              </label>
              <div className="mt-2">
                <input
                  type="text"
                  name="firstname"
                  id="firstname"
                  autoComplete="given-name"
                  className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
                  defaultValue= {user.firstname? user.firstname : " " }
                           onChange={async (e) => {
                               setUserBearbeiten({...userBearbeiten, firstname: e.target.value})
                           }}
                />
              </div>
            </div>

            <div className="sm:col-span-3">
              <label htmlFor="lastname" className="block text-sm font-medium leading-6 text-gray-900">
                Nachname
              </label>
              <div className="mt-2">
                <input
                  type="text"
                  name="lastname"
                  id="lastname"
                  autoComplete="family-name"
                  className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
                  defaultValue= {user.lastname? user.lastname : " " }
                           onChange={async (e) => {
                               setUserBearbeiten({...userBearbeiten, lastname: e.target.value})
                           }}
                />
              </div>
            </div>
              {/*date of birth*/}
            <div className="sm:col-span-4">
                <label htmlFor="birthday" className="block text-sm font-medium leading-6 text-gray-900">
                    Date of Birth
                </label>
                <div className="mt-2">
                    <DatePicker
                        id="birthday"
                        name="birthday"
                        autoComplete="birthday"
                        className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
                        selected={dateOfBirth}
                        onChange={(date) => setDateOfBirth(date)}
                    />
                </div>
            </div>
              {/*telefon number*/}

          </div>
        </div>

        <div className="border-b border-gray-900/10 pb-12">
        <h2 className="text-base font-semibold leading-7 text-gray-900">Ausstellung</h2>
        {
            exhibitionValues.map((element, index) => (
                <div className="form-inline" key={index}>
                    {
                        index ?
                            <Button type="button" className="button inputField remove"
                                    onClick={() => removeExhibitionFields(index)}>Remove</Button>
                            : null
                    } 

                    <div className="sm:col-span-4">
                            <label htmlFor="title" className="block text-sm font-medium leading-6 text-gray-900">
                                Title
                            </label>
                            <div className="mt-2">
                                <input
                                id="title"
                                name="title"
                                type="text"
                                autoComplete="Title"
                                className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
                                value={element.title || ""}
                                   onChange={e => handleExhibition(index, e)}
                                />
                            </div>
                    </div>
                    <div className="sm:col-span-4">
                            <label htmlFor="location" className="block text-sm font-medium leading-6 text-gray-900">
                                Ort
                            </label>
                            <div className="mt-2">
                                <input
                                id="location"
                                name="location"
                                type="text"
                                autoComplete="Location"
                                className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
                                value={element.location || ""}
                                   onChange={e => handleExhibition(index, e)}
                                />
                            </div>
                    </div>
                    <div className="sm:col-span-4">
                            <label htmlFor="Beschreibung" className="block text-sm font-medium leading-6 text-gray-900">
                                Beschreibung
                            </label>
                            <div className="mt-2">
                                <input
                                id="description"
                                name="description"
                                type="text"
                                autoComplete="description"
                                className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
                                value={element.description || ""} onChange={e => handleExhibition(index, e)}
                                />
                            </div>
                    </div>
                    <div className="sm:col-span-4">
                            <label htmlFor="year" className="block text-sm font-medium leading-6 text-gray-900">
                                Jahr
                            </label>
                            <div className="mt-2">
                                <input
                                id="year"
                                name="year"
                                type="text"
                                autoComplete="Jahr"
                                className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
                                value={element.year || ""}
                                   onChange={e => handleExhibition(index, e)}
                                />
                            </div>
                    </div>
                </div>
            ))
        }
       <div className='pt-7'>
       <button
          type="submit"
          className="rounded-md bg-indigo-600 px-3 py-2 text-sm font-semibold text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600"
          onClick={() => addExhibitionFields()}
        >
          Hinzufügen
        </button>
       </div>

       
        </div>

        <div className="border-b border-gray-900/10 pb-12">
        <h2 className="text-base font-semibold leading-7 text-gray-900">Social Media</h2>

        {
            socialMedia.map((element, index) => (
                <div className="form-inline" key={index}>
                    {
                        index ?
                            <Button type="button" className="button inputField remove"
                                    onClick={() => removeSocialMediaFields(index)}>Remove</Button>
                            : null
                    }
                    <div className="sm:col-span-2 sm:col-start-1">
                        <label htmlFor="title" className="block text-sm font-medium leading-6 text-gray-900">
                            Title
                        </label>
                        <div className="mt-2">
                            <input
                            type="text"
                            name="title"
                            id="title"
                            autoComplete="address-level2"
                            className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
                            defaultValue={element.title || " "}
                                   onChange={e => handleSocailMedia(index, e)}
                            />
                        </div>
                        </div>

                        <div className="sm:col-span-2">
                        <label htmlFor="link" className="block text-sm font-medium leading-6 text-gray-900">
                            Location
                        </label>
                        <div className="mt-2">
                            <input
                            type="text"
                            name="link"
                            id="link"
                            autoComplete="address-level1"
                            className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
                            value={element.link || ""}
                                   onChange={e => handleSocailMedia(index, e)}
                            />
                        </div>
                    </div>
                </div>
            ))
        }

       <div className='pt-7'>
       <button
          type="submit"
          className="rounded-md bg-indigo-600 px-3 py-2 text-sm font-semibold text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600"
          onClick={() => addSocialMediaField()}
        >
          Hinzufügen
        </button>
       </div>

        </div>
      </div>

      <div className="mt-6 flex items-center justify-end gap-x-6">
        <button type="button" className="text-sm font-semibold leading-6 text-gray-900">
          Abbrechen
        </button>
        <button
          type="submit"
          className="rounded-md bg-indigo-600 px-3 py-2 text-sm font-semibold text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600"
        >
          Speichern
        </button>
      </div>
    </form>
    </div>
    </div>
    {/* tailwind ui */}
    </>
);
}

export default Bearbeiten;