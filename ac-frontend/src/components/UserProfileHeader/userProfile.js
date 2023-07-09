import React, {Fragment, useEffect, useState} from 'react'
import {useNavigate, Link} from "react-router-dom";
import EmptyGalerie from '../../container/Galerie/Galerie2'
import {EnvelopeIcon, PhoneIcon} from '@heroicons/react/20/solid'
import {Menu, Transition} from '@headlessui/react'
import {CodeBracketIcon, EllipsisVerticalIcon, FlagIcon, StarIcon} from '@heroicons/react/20/solid'
import {PaperClipIcon} from '@heroicons/react/20/solid'
import {storageService} from "../../lib/localStorage"
import {ApiService} from "../../lib/api";
import {GalerieApiService} from "../../lib/apiGalerie"

// Hier bitte richtiges User Bild rein
import Image from '../../images/placeholderUser.png';


const profile = {
    name: 'Vyacheslav Thomas',
    email: 'Vyacheslav@example.com',
    web: 'Vyacheslav.com',
    avatar: 'https://images.unsplash.com/photo-1463453091185-61582044d556?ixlib=rb-=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=facearea&facepad=8&w=1024&h=1024&q=80',
    backgroundImage: 'https://images.unsplash.com/photo-1444628838545-ac4016a5418a?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=1950&q=80',
    fields: [['Phone', '(555) 123-4567'], ['Email', 'Vyacheslav@example.com'], ['Title', 'Senior Front-End Developer'], ['Team', 'Product Development'], ['Location', 'San Francisco'], ['Sits', 'Oasis, 4th floor'], ['Salary', '12$'], ['Birthday', 'June 8, 1990'],],
}

function classNames(...classes) {
    return classes.filter(Boolean).join(' ')
}

const Profile = () => {
    const [user, setUser] = useState([])
    const [image, setImage] = useState([]);

    //lad die Userdaten aus dem Backend, wenn es ein userFoto gibt, convertiert er es in eine brauchbare URL
    useEffect(() => {
        async function getUserData() {
            const result = await storageService.getUser();
            const urlGetUser = `/users?email=${result}`.replace(/"/g, '');
            const userProfile = await ApiService.getDataSecuredWithParameter(urlGetUser);
            setUser(userProfile.data);
            console.log("user Profile: "+ userProfile.data.dateOfBirthday )
            if (! userProfile.data.profilePhoto?.image?.data ) {
                setImage(Image);

                //Blank Picture
                console.log("Image undefined.")
            } else {
                const byteCharacters = atob(userProfile.data.profilePhoto.image.data);
                const byteNumbers = new Array(byteCharacters.length);
                for (let i = 0; i < byteCharacters.length; i++) {
                    byteNumbers[i] = byteCharacters.charCodeAt(i);
                }
                const byteArray = new Uint8Array(byteNumbers);
                // Create URL for the binary image data
                const blob = new Blob([byteArray], {type: 'image/png'}); // Adjust the 'type' according to the actual image format
                const url = URL.createObjectURL(blob);
                setImage(url);
            }

        }

        getUserData();

    }, [])

    //formatiert das Geburtsdatum schöner
    const formatDate = (dateString) => {
        const options = { year: 'numeric', month: 'long', day: 'numeric' };
        const date = new Date(dateString);
        return date.toLocaleDateString(undefined, options);
    };

    const navigate = useNavigate();

    return (<>
            <div>
                <div>
                    <div>
                        <img className="h-32 w-full object-cover lg:h-48" src={profile.backgroundImage} alt=""/>
                    </div>
                    <div className="mx-auto max-w-7xl px-4 sm:px-6 lg:px-8">
                        <div className="-mt-12 sm:-mt-16 sm:flex sm:items-end sm:space-x-5">
                            <div className="flex">
                                <img className="h-24 w-24 rounded-full ring-4 ring-white sm:h-32 sm:w-32"
                                     src={image} alt=""/>
                            </div>
                            <div
                                className="mt-6 sm:flex sm:min-w-0 sm:flex-1 sm:items-center sm:justify-end sm:space-x-6 sm:pb-1">
                                <div className="mt-6 min-w-0 flex-1 sm:hidden md:block">
                                    <h1 className="truncate text-2xl font-bold text-gray-900">{(user.firstname ? user.firstname + " " : " ") + (user.lastname ? user.lastname : " ")}</h1>
                                    <p className="truncate text-2xl font-bold text-gray-900"> {user.dateOfBirthday ?"Geburtstag:" + formatDate(user.dateOfBirthday) : " "}</p>
                                </div>
                                <div
                                    className="mt-6 flex flex-col justify-stretch space-y-3 sm:flex-row sm:space-x-4 sm:space-y-0">
                                    <button
                                        onClick={() => {
                                            navigate("/bearbeiten", { state: { user: user, imageProps: image } });
                                        }}
                                        type="button"
                                        className="inline-flex items-center rounded-md bg-indigo-600 px-3 py-2 text-sm font-semibold text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600 mt-7 mb-7"
                                    >
                                        <span>Edit User</span>
                                    </button>
                                </div>
                            </div>
                        </div>
                        <div className="mt-6 hidden min-w-0 flex-1 sm:block md:hidden">
                            <h1 className="truncate text-2xl font-bold text-gray-900">{(user.firstname ? user.firstname + " " : " ") + (user.lastname ? user.lastname : " ")}</h1>
                        </div>
                    </div>
                    <div className="mx-auto max-w-7xl px-4 sm:px-6 lg:px-8">
                                                                        <span className="flex-grow">
                {(user.biography ? user.biography + " " : " ")}
              </span>
                    </div>
                    <div className="mx-auto max-w-7xl px-4 sm:px-6 lg:px-8">
                        <p className="bold-text">Exhibitions</p>
                        {user.exhibitions && user.exhibitions.length > 0 ? (
                            <ul style={{ listStyleType: 'disc' }}>
                                {user.exhibitions.map((exhibitions, index) => (
                                    <li key={index}>
                                        <p>{exhibitions.title}</p>
                                        <ul>
                                            <li>
                                                <p><p>{exhibitions.location} | {exhibitions.year} | {exhibitions.description}</p></p>
                                            </li>
                                        </ul>
                                    </li>
                                ))}
                            </ul>
                        ) : (
                            <p>No social media accounts found.</p>
                        )}
                    </div>

                    <div className="mx-auto max-w-7xl px-4 sm:px-6 lg:px-8">
                        <p className="bold-text">Social Media</p>
                        {user.socialMedias && user.socialMedias.length > 0 ? (
                            <ul style={{ listStyleType: 'disc' }}>
                                {user.socialMedias.map((socialMedia, index) => (
                                    <li key={index}>
                                        <a href={socialMedia.link} target="_blank">{socialMedia.title}</a>
                                    </li>
                                ))}
                            </ul>
                        ) : (
                            <p>No social media accounts found.</p>
                        )}
                    </div>

                </div>
                <div className="mx-auto max-w-7xl px-4 sm:px-6 lg:px-8">
                    <div className="mx-auto max-w-3xl">
                    </div>
                    <div className="mt-6 border-t border-gray-100">

                    </div>
                </div>
            </div>
        </>

    )
}

export default Profile
