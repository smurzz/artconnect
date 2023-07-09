import * as React from 'react';
import {useState, useEffect, useRef} from "react";
import {Link, Route, Routes, useNavigate, useLocation} from "react-router-dom";
import "./bearbeiten.css";
import axios from "../../api/axios";
import {ApiService} from "../../lib/api";
import { format } from 'date-fns';
import CssBaseline from '@mui/material/CssBaseline';
import Typography from '@mui/material/Typography';
import Container from '@mui/material/Container';
import Button from '@mui/material/Button';
import Grid from '@mui/material/Grid';
import Box from '@mui/material/Box';
import { PhotoIcon, UserCircleIcon } from '@heroicons/react/24/solid';
import {logikService} from  "../../lib/service"
import HeaderLogedIn from "../../components/headerComponent/headerLogedIn";
import HeaderLogedOut from "../../components/headerComponent/headerLogout";

import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';

import Header from "../../components/headerComponent/headerLogedIn"


function Bearbeiten(props) {
    const navigate = useNavigate();

    //User und Image Opject von der UserprofilSeite
    const location = useLocation();
    const { user, imageProps } = location.state;

    //DateOfBirthday
    const [dateOfBirth, setDateOfBirth] = useState( new Date());
    const [isDateOfBirthVisible, setIsDateOfBirthVisible] = useState(false);
    //file Upload
    const [selectedFile, setSelectedFile] = useState(null);

    //Error Handeling fileupload
    const [errorMessage, setErrorMessage] = useState(false);
    const [errAlert, seterrAlert] = React.useState("");
    const [successMessage, setSuccessMessage] = React.useState("");
    const [success, setSuccess] = useState(false);
    const [constact, setContacts] = useState(false);

    //edit userdata from BE
    const [userBearbeiten, setUserBearbeiten] = useState(
        {
            firstname: "",
            lastname: "",
            email: "",
            biography: "",
            dateOfBirthday: "",
            telefonNumber: "",
            street: "",
            postCode: "",
            city:"",
            country:"",
            website:""
        }
    )
    const [isLoggedIn, setIsLoggedIn] = React.useState(false);
    useEffect(()=>{
        async function getLoggedIn(){
            const loggedInHeader = await logikService.isLoggedIn();
            setIsLoggedIn(loggedInHeader);
            console.log("loggedIn: " + loggedInHeader)
        }
        getLoggedIn();
    },[])

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

    //lad die Userdaten aus dem Backend, wenn es ein userFoto gibt, convertiert er es in eine brauchbare URL
    useEffect(() => {
        if(user.dateOfBirthday) {
            setIsDateOfBirthVisible(true);
        }else{
            setIsDateOfBirthVisible(false);
        }
        if(user.socialMedias != undefined){
            setSocailMedia([...socialMedia, ...user.socialMedias])
            console.log("socialMedias: 1"+ JSON.stringify(socialMedia))
        }
        if(user.exhibitions != undefined){
            setExhibitionValues([...exhibitionValues, ...user.exhibitions])
            console.log("socialMedias: 1"+ JSON.stringify(socialMedia))
        }
    }, [])
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
        console.log(selectedFile);
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
        }else{
            setErrorMessage(true);
            seterrAlert("You need to select a foto to upload it");
        }
    }
    useEffect(()=>{
        setSuccess(false);
        setErrorMessage(false);
    },[selectedFile]);

const handleSubmit = async (event) => {
    event.preventDefault();
    const requestBody = {};
    // Add userBearbeiten fields
    if (userBearbeiten.firstname !== "") {
        requestBody.firstname = userBearbeiten.firstname;
    }

    if (userBearbeiten.lastname !== "") {
        requestBody.lastname = userBearbeiten.lastname;
    }

        requestBody.biography = userBearbeiten.biography;


    if(userBearbeiten.dateOfBirthday !==""){
        console.log("date of Birth: "+ userBearbeiten.dateOfBirthday );
        requestBody.dateOfBirthday = userBearbeiten.dateOfBirthday;
    }
    if(isDateOfBirthVisible){
        requestBody.isDateOfBirthVisible ="PUBLIC";
    }else{
        requestBody.isDateOfBirthVisible ="PRIVATE";
    }
    // Add exhibitionValues fields
    const exhibitions = exhibitionValues.filter((exhibition) => exhibition.title !== "" && exhibition.location !== "" && exhibition.year !== "" && exhibition.description !== "");
    if (exhibitions.length > 0) {
        requestBody.exhibitions = exhibitions;
    }
    else if(user.exhibitions?.length >= 1){
        requestBody.exhibitions =  [

        ]

    }

    requestBody.contacts = {
        ...user.contacts,
        address: {
            ...user.contacts?.address,
            ...(userBearbeiten.street !== "" && { street: userBearbeiten.street }),
            ...(userBearbeiten.postCode !== "" && { postalCode: userBearbeiten.postCode }),
            ...(userBearbeiten.city !== "" && { city: userBearbeiten.city }),
            ...(userBearbeiten.country !== "" && { country: userBearbeiten.country })
        },
        ...(userBearbeiten.telefonNumber !== "" && { telefonNumber: userBearbeiten.telefonNumber }),
        ...(userBearbeiten.website !== "" && { website: userBearbeiten.website })
    };

    // Add socialMedia fields
    const socialMedias = socialMedia.filter((media) => media.title !== "" && media.link !== "");
    if (socialMedias.length > 0) {
        requestBody.socialMedias = socialMedias;
    }
    else if(user.socialMedias?.length >= 1){
        requestBody.socialMedias =  []
    }
console.log("requestBody: "+ JSON.stringify(requestBody));
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
        {isLoggedIn? <HeaderLogedIn/>:<HeaderLogedOut/>}
    {/* tailwind ui */}
    <div className="mx-auto max-w-7xl px-4 sm:px-6 lg:px-8">
      {/* We've used 3xl here, but feel free to try other max-widths based on your needs */}
      <div className="mx-auto max-w-3xl">
          <div className="border-b border-gray-900/10 pb-12">
              <div>
                  <p className="text-base font-semibold leading-7 text-gray-900">Edit Profil</p>
                  <div className="marginBottom">
                      <button
                          onClick={()=>{navigate("/galerie")}}
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

                          <button onClick={handleFileUpload}
                                  className="rounded-md bg-indigo-600 px-3 py-2 text-sm font-semibold text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600"

                          >save</button>
                      </div>
                  </form>
              </div>

              <p className="mt-1 text-sm leading-6 text-gray-600">
                  Diese Informationen werden öffentlich dargestellt. Sei also Vorsichtig welche Informationen du preis gibst!
              </p>


          </div>
    <form onSubmit={handleSubmit}>
      <div className="space-y-12">
        <div className="border-b border-gray-900/10 pb-12">
          <h2 className="text-base font-semibold leading-7 text-gray-900">Persönliche Informationen</h2>

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

            </div>

          <div className="mt-10 grid grid-cols-1 gap-x-6 gap-y-8 sm:grid-cols-6">
            <div className="sm:col-span-3">
              <label htmlFor="firstname" className="block text-sm font-medium leading-6 text-gray-900">
                Vorname*
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
                Nachname*
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
                        onChange={(date) => {
                            setDateOfBirth(date);

                            // Convert Date to LocalDate
                            const localDateString = format(date, 'yyyy-MM-dd');

                            // Update userBearbeiten state
                            setUserBearbeiten((prevState) => ({
                                ...prevState,
                                dateOfBirthday: localDateString
                            }));
                        }}
                    />
                </div>
                <div className="flex items-center mt-2">
                    <input
                        type="checkbox"
                        id="dateVisibility"
                        name="dateVisibility"
                        className="form-checkbox h-4 w-4 text-indigo-600 transition duration-150 ease-in-out"
                        checked={isDateOfBirthVisible}
                        onChange={() => setIsDateOfBirthVisible(!isDateOfBirthVisible)}
                    />
                    <label htmlFor="dateVisibility" className="ml-2 text-sm text-gray-700">
                        Should we display your Birthday on your Profil?
                    </label>
                </div>
            </div>

              <div className="sm:col-span-4">
              <p className="underline">Adresss</p>
                  <label className="inputField">phoneNumber</label>
                  <input className="inputField" type="text" name="phoneNUmber"
                         defaultValue= {user.contacts?.telefonNumber? user.contacts.telefonNumber : "" }
                         onChange={async (e) => {
                             setUserBearbeiten({...userBearbeiten, telefonNumber: e.target.value})
                         }}
                  />

              <label className="inputField">street</label>
              <input className="inputField" type="text" name="street"
                     defaultValue= {user.contacts?.address?.street ? user.contacts.address.street : "" }
                     onChange={async (e) => {
                         setUserBearbeiten({...userBearbeiten, street: e.target.value})
                     }}
                    />

              <label className="inputField">postalCode</label>
              <input className="inputField" type="text" name="postalCode"
                     defaultValue= {user.contacts?.address?.postalCode? user.contacts.address.postalCode : "" }
                     onChange={async (e) => {
                         setUserBearbeiten({...userBearbeiten, postalCode: e.target.value})
                     }}
                 />
              <label className="inputField">city</label>
              <input className="inputField" type="text" name="city"
                     defaultValue= {user.contacts?.address?.city? user.contacts.address.city : "" }
                     onChange={async (e) => {
                         setUserBearbeiten({...userBearbeiten, city: e.target.value})
                     }}
                    />

              <label className="inputField">country</label>
              <input className="inputField" type="text" name="country"
                     defaultValue= {user.contacts?.address?.country? user.contacts.address.country : "" }
                     onChange={async (e) => {
                         setUserBearbeiten({...userBearbeiten, country: e.target.value})
                     }}
              />

              <label className="inputField">website</label>
              <input className="inputField" type="text" name="website"
                     defaultValue= {user.contacts?.website? user.contacts.website : ""}
                     onChange={async (e) => {
                         setUserBearbeiten({...userBearbeiten, website: e.target.value})
                     }}
              />
              <br/>
                  </div>
          </div>
        </div>
          {/*Exhibitions*/}
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
          type="button"
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
          type="button"
          className="rounded-md bg-indigo-600 px-3 py-2 text-sm font-semibold text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600"
          onClick={() => addSocialMediaField()}
        >
          Hinzufügen
        </button>
       </div>

        </div>
      </div>

      <div className="mt-6 flex items-center justify-end gap-x-6">
        <button type="button" className="text-sm font-semibold leading-6 text-gray-900"
                onClick={() => {
                    navigate("/galerie");
                }}
        >
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
        <button
            onClick={() => {
            navigate("/deleteUser",  { state: { userId: user.id}});
        }}>
            Profil löschen
        </button>
    {/* tailwind ui */}
    </>
);
}

export default Bearbeiten;