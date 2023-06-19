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

import { PhotoIcon, UserCircleIcon } from '@heroicons/react/24/solid';

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
    {/* tailwind ui */}
    <div className="mx-auto max-w-7xl px-4 sm:px-6 lg:px-8">
      {/* We've used 3xl here, but feel free to try other max-widths based on your needs */}
      <div className="mx-auto max-w-3xl">
    <form onSubmit={handleSubmit}>
      <div className="space-y-12">
        <div className="border-b border-gray-900/10 pb-12">
          <h2 className="text-base font-semibold leading-7 text-gray-900">Profile</h2>
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
                  defaultValue={''}
                  value={userBearbeiten.biography}
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

              <form>

                <div className="mt-2 flex items-center gap-x-3">
                    <UserCircleIcon className="h-12 w-12 text-gray-300" aria-hidden="true" />
                    <input type="file" accept=".jpg, .jpeg, .png" onChange={handleFileChange} />
                    {errorMessage && <p>{errorMessage}</p>}
                    <button onClick={handleFileUpload}>Upload</button>
                </div>
              </form>
            </div>

            <div className="col-span-full">
              <label htmlFor="cover-photo" className="block text-sm font-medium leading-6 text-gray-900">
                Banner
              </label>
              <div className="mt-2 flex justify-center rounded-lg border border-dashed border-gray-900/25 px-6 py-10">
                <div className="text-center">
                  <PhotoIcon className="mx-auto h-12 w-12 text-gray-300" aria-hidden="true" />
                  <div className="mt-4 flex text-sm leading-6 text-gray-600">
                    <label
                      htmlFor="file-upload"
                      className="relative cursor-pointer rounded-md bg-white font-semibold text-indigo-600 focus-within:outline-none focus-within:ring-2 focus-within:ring-indigo-600 focus-within:ring-offset-2 hover:text-indigo-500"
                    >
                      <span>Lade ein Bild hoch</span>
                      <input id="file-upload" name="file-upload" type="file" accept=".jpg, .jpeg, .png" className="sr-only" onChange={handleFileChange} />
                    </label>
                      {errorMessage && <p>{errorMessage}</p>}
                    {/* <p className="pl-1">or drag and drop</p> */}
                  </div>
                      <button onClick={handleFileUpload}>Upload</button>
                  <p className="text-xs leading-5 text-gray-600">PNG, JPG, GIF up to 10MB</p>
                </div>
              </div>
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
                  value={userBearbeiten.firstname}
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
                  value={userBearbeiten.lastname}
                           onChange={async (e) => {
                               setUserBearbeiten({...userBearbeiten, lastname: e.target.value})
                           }}
                />
              </div>
            </div>

            <div className="sm:col-span-4">
              <label htmlFor="email" className="block text-sm font-medium leading-6 text-gray-900">
                Email Addresse
              </label>
              <div className="mt-2">
                <input
                  id="email"
                  name="email"
                  type="text"
                  autoComplete="email"
                  className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
                  value={userBearbeiten.email}
                           onChange={async (e) => {
                               setUserBearbeiten({...userBearbeiten, email: e.target.value})
                           }}
                />
              </div>
            </div>
            <div className="sm:col-span-4">
              <label htmlFor="telefonNumber" className="block text-sm font-medium leading-6 text-gray-900">
                Telefonnummer
              </label>
              <div className="mt-2">
                <input
                  id="telefonNumber"
                  name="telefonNumber"
                  type="text"
                  autoComplete="telefonnummer"
                  className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
                  value={constact.telefonNumber || ""}
                           onChange={async (e) => {
                               setContacts({...constact, telefonNumber: e.target.value})
                           }}
                />
              </div>
            </div>
            <div className="sm:col-span-4">
              <label htmlFor="webseite" className="block text-sm font-medium leading-6 text-gray-900">
                Webseite
              </label>
              <div className="mt-2">
                <input
                  id="webseite"
                  name="webseite"
                  type="text"
                  autoComplete="webseite"
                  className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
                  value={constact.address.website}
                           onChange={(e) => {
                               setContacts({
                                   ...constact,
                                   website: e.target.value
                               });
                           }}
                />
              </div>
            </div>

            <div className="sm:col-span-3">
              <label htmlFor="country" className="block text-sm font-medium leading-6 text-gray-900">
                Land
              </label>
              <div className="mt-2">
                <select
                  id="country"
                  name="country"
                  autoComplete="country-name"
                  className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:max-w-xs sm:text-sm sm:leading-6"
                  value={constact.address.country}
                           onChange={(e) => {
                               setContacts({
                                   ...constact,
                                   address: {
                                       ...constact.address,
                                       country: e.target.value
                                   }
                               });
                           }}
                >
                  <option>United States</option>
                  <option>Canada</option>
                  <option>Mexico</option>
                  <option>Germany</option>
                  <option>United Kingdoms</option>
                </select>
              </div>
            </div>

            <div className="col-span-full">
              <label htmlFor="street-address" className="block text-sm font-medium leading-6 text-gray-900">
                Addresse
              </label>
              <div className="mt-2">
                <input
                  type="text"
                  name="street"
                  id="street-address"
                  autoComplete="street-address"
                  className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
                  value={constact.address.street}
                           onChange={(e) => {
                               setContacts({
                                   ...constact,
                                   address: {
                                       ...constact.address,
                                       street: e.target.value
                                   }
                               });
                           }}
                />
              </div>
            </div>

            <div className="sm:col-span-2 sm:col-start-1">
              <label htmlFor="city" className="block text-sm font-medium leading-6 text-gray-900">
                Stadt
              </label>
              <div className="mt-2">
                <input
                  type="text"
                  name="city"
                  id="city"
                  autoComplete="address-level2"
                  className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
                  value={constact.address.city}
                           onChange={(e) => {
                               setContacts({
                                   ...constact,
                                   address: {
                                       ...constact.address,
                                       city: e.target.value
                                   }
                               });
                           }}
                />
              </div>
            </div>

            <div className="sm:col-span-2">
              <label htmlFor="region" className="block text-sm font-medium leading-6 text-gray-900">
                Bundesstat
              </label>
              <div className="mt-2">
                <input
                  type="text"
                  name="region"
                  id="region"
                  autoComplete="address-level1"
                  className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
                />
              </div>
            </div>

            <div className="sm:col-span-2">
              <label htmlFor="postalCode" className="block text-sm font-medium leading-6 text-gray-900">
                ZIP / Postal code
              </label>
              <div className="mt-2">
                <input
                  type="text"
                  name="postalCode"
                  id="postalCode"
                  autoComplete="postalCode"
                  className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
                  value={constact.address.postalCode}
                           onChange={(e) => {
                               setContacts({
                                   ...constact,
                                   address: {
                                       ...constact.address,
                                       postalCode: e.target.value
                                   }
                               });
                           }}
                />
              </div>
            </div>
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
                            value={element.title || ""}
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