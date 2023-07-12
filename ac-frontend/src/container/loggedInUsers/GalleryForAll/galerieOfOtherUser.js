import * as React from 'react';
import {GalerieApiService} from "../../../lib/apiGalerie"
import {storageService} from "../../../lib/localStorage"
import {ApiService} from "../../../lib/api";
import {PlusIcon} from '@heroicons/react/20/solid'
import {useNavigate, Link, useParams} from "react-router-dom";
import {StarIcon} from '@heroicons/react/20/solid'
import CircularProgress from '@mui/material/CircularProgress';
import Box from '@mui/material/Box';
import Image1 from '../../../images/defaultArtworkPlaceholder.png';
import Image from "../../../images/placeholderUser.png"
import {logikService} from "../../../lib/service"
import HeaderLogedIn from "../../../components/headerComponent/headerLogedIn";
import HeaderLogedOut from "../../../components/headerComponent/headerLogout";
import {useEffect, useState} from "react";
import ModalSuccess from "../../../components/ModalPopUp/ModalSuccess"
import {Disclosure, RadioGroup, Tab} from '@headlessui/react'
import {MinusIcon} from '@heroicons/react/24/outline'

const product = {
    rating: 4
}
const GalleryHeader = ({gallery, isLoggedIn}) => {
    const {title, description, categories} = gallery;
    const [starUserRating, setStarUserRating] = useState();
    const [ranking, setRanking] = useState();
    const [success, setSuccess] = useState(false);
    const navigate = useNavigate();
    const {id} = useParams();

    useEffect(() => {
        async function getGallerieData() {
            const result = await GalerieApiService.getUnsecuredData("/galleries/" + id);
            setRanking(result.data.ranking);
        }

        getGallerieData()
    }, [])


    async function sendUserRating() {
        const result = await GalerieApiService.postSecuredData("/galleries/" + id + "/rating?value=" + starUserRating, {});
        setSuccess(true);
    }


    return (
        <div>
            <div className="flex items-center ">
                <h1 className="mr-4">{title}</h1>
            </div>
            <h3 className="sr-only">Reviews</h3>
            {categories && <p className="tag tag-sm">{categories}</p>}
            <p>{description}</p>
            <div className="flex items-center">
                {[0, 1, 2, 3, 4].map((rating) => (

                    <StarIcon
                        key={rating}
                        className={classNames(
                            ranking > rating ? 'text-indigo-500' : 'text-gray-300',
                            'h-5 w-5 flex-shrink-0'
                        )}
                        aria-hidden="true"
                    />
                ))}
            </div>
            {/*dislosure*/}
            {isLoggedIn &&

                <section aria-labelledby="details-heading" className="mt-7">
                    <div className="divide-y divide-gray-200 border-t">
                        <Disclosure as="div" key="details">
                            {({open}) => (
                                <>
                                    <h3>
                                        <Disclosure.Button
                                            className="group relative flex w-full items-center justify-between py-1 text-left">
                            <span
                                className={classNames(open ? 'text-indigo-600' : 'text-gray-900', 'text-sm font-medium')}>
                              Add your own ranking
                            </span>
                                            <span className="ml-6 flex items-center">
                              {open ? (
                                  <MinusIcon
                                      className="block h-6 w-6 text-indigo-400 group-hover:text-indigo-500"
                                      aria-hidden="true"
                                  />
                              ) : (
                                  <PlusIcon
                                      className="block h-6 w-6 text-gray-400 group-hover:text-gray-500"
                                      aria-hidden="true"
                                  />
                              )}
                            </span>
                                        </Disclosure.Button>
                                    </h3>
                                    <Disclosure.Panel as="div" className="prose prose-sm pb-6">
                                        <div className="mx-auto max-w-7xl px-4 sm:px-6 lg:px-8">
                                            {success && < div className="alert alert-primary" role="alert">
                                                "Hurray! Thank you for your vote."
                                            </div>}
                                            <div className="mx-auto max-w-7xl px-4 sm:px-6 lg:px-8 flex items-center">

                                                {[0, 1, 2, 3, 4].map((rating, index) => (
                                                    <button
                                                        disabled={success}
                                                        onClick={() => setStarUserRating(index + 1)}
                                                    >
                                                        <StarIcon
                                                            key={rating}
                                                            className={classNames(
                                                                starUserRating > rating ? 'text-indigo-500' : 'text-gray-300',
                                                                'h-5 w-5 flex-shrink-0'
                                                            )}
                                                            aria-hidden="true"
                                                        />
                                                    </button>
                                                ))}
                                                {!success &&
                                                    <button
                                                        onClick={() => {
                                                            sendUserRating();

                                                        }}
                                                        className=" mx-7 inline-flex items-center rounded-md bg-blue-200 px-3 py-2 text-sm font-semibold text-slate-700 shadow-sm hover:bg-blue-300 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:bg-blue-300 mt-7 mb-7"
                                                    >
                                                        Submit
                                                    </button>
                                                }

                                            </div>
                                        </div>
                                    </Disclosure.Panel>
                                </>
                            )}
                        </Disclosure>
                    </div>
                </section>
            }

        </div>

    );
};

const profile = {
    backgroundImage: 'https://images.unsplash.com/photo-1444628838545-ac4016a5418a?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=1950&q=80',
    fields: [['Phone', '(555) 123-4567'], ['Email', 'Vyacheslav@example.com'], ['Title', 'Senior Front-End Developer'], ['Team', 'Product Development'], ['Location', 'San Francisco'], ['Sits', 'Oasis, 4th floor'], ['Salary', '12$'], ['Birthday', 'June 8, 1990'],],
}

function classNames(...classes) {
    return classes.filter(Boolean).join(' ')
}

const ProfilUser = () => {
    const [user, setUser] = useState([])
    const [image, setImage] = useState([]);
    const {id} = useParams();
    //lad die Userdaten aus dem Backend, wenn es ein userFoto gibt, convertiert er es in eine brauchbare URL
    useEffect(() => {
        async function getUserData() {
            const result = await GalerieApiService.getSecuredData("/galleries/" + id);
            const urlGetUser = result.data.ownerId;
            const userProfile = await GalerieApiService.getUnsecuredData("/users/" + urlGetUser);
            console.log("OwnerID: "+ urlGetUser);
            setUser(userProfile.data);
            if (!userProfile.data.profilePhoto?.image?.data) {
                setImage(Image);
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
        const options = {year: 'numeric', month: 'long', day: 'numeric'};
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
                        <div className="mt-12 sm:-mt-16 sm:flex justify-start sm:space-x-5">
                            <div className="flex">
                                <img className="h-30 w-30 rounded-full ring-4 ring-white sm:h-32 sm:w-32"
                                     src={image} alt=""/>
                            </div>
                        </div>
                        <div className="mt-6 hidden min-w-0 flex-1 sm:block md:hidden">
                            <h1 className="truncate text-2xl font-bold text-gray-900">{(user.firstname ? user.firstname + " " : " ") + (user.lastname ? user.lastname : " ")}</h1>
                        </div>
                    </div>
                    <div className="mx-auto max-w-7xl sm:px-6 lg:px-8">
                        <div className="mt-6 sm:flex sm:min-w-0 sm:flex-1 sm:items-center sm:space-x-6 sm:pb-1">
                            <div className="mt-6 min-w-0 flex-1 sm:hidden md:block">
                                <h1 className="truncate text-2xl font-bold text-gray-900">{(user.firstname ? user.firstname + " " : " ") + (user.lastname ? user.lastname : " ")}</h1>
                                <p className="mx-auto max-w-7xl px-4 sm:px-6 lg:px-8"> {user.dateOfBirthday ? formatDate(user.dateOfBirthday) : " "}</p>
                            </div>
>
                        </div>

                        <span className="flex-grow">
                {(user.biography ? user.biography + " " : " ")}
              </span>
                    </div>

                    <div className="d-flex justify-content-between mx-auto max-w-7xl px-4 sm:px-6 lg:px-8">
                        <div className="flex-grow-1 mx-auto max-w-7xl px-2 sm:px-4 lg:px-8">
                            <section aria-labelledby="details-heading" className="mt-7">
                                <div className="divide-y divide-gray-200 border-t">
                                    <Disclosure as="div" key="details">
                                        {({open}) => (
                                            <>
                                                <h3>
                                                    <Disclosure.Button
                                                        className="group relative flex w-full items-center justify-between py-1 text-left">
                            <span
                                className={classNames(open ? 'text-indigo-600' : 'text-gray-900', 'text-sm font-medium')}>
                              Social Media
                            </span>
                                                        <span className="ml-6 flex items-center">
                              {open ? (
                                  <MinusIcon
                                      className="block h-6 w-6 text-indigo-400 group-hover:text-indigo-500"
                                      aria-hidden="true"
                                  />
                              ) : (
                                  <PlusIcon
                                      className="block h-6 w-6 text-gray-400 group-hover:text-gray-500"
                                      aria-hidden="true"
                                  />
                              )}
                            </span>
                                                    </Disclosure.Button>
                                                </h3>
                                                <Disclosure.Panel as="div" className="prose prose-sm pb-6">
                                                    <div className="mx-auto max-w-7xl px-4 sm:px-6 lg:px-8">
                                                        <div className="mx-auto max-w-7xl px-4 sm:px-6 lg:px-8">
                                                            {user.socialMedias && user.socialMedias.length > 0 ? (
                                                                <div className="mx-auto max-w-7xl px-4 sm:px-6 lg:px-8">
                                                                    {user.socialMedias.map((socialMedia, index) => (
                                                                        <a
                                                                            href={socialMedia.link}
                                                                            target="_blank">{socialMedia.title}</a>
                                                                    ))}
                                                                </div>

                                                            ) : (
                                                                <p>No social media accounts found.</p>
                                                            )}
                                                        </div>
                                                    </div>
                                                </Disclosure.Panel>
                                            </>
                                        )}
                                    </Disclosure>
                                </div>
                            </section>
                        </div>
                        <div className="flex-grow-1 mx-auto max-w-7xl px-2 sm:px-4 lg:px-8">
                            <section aria-labelledby="details-heading" className="mt-7">
                                <div className="divide-y divide-gray-200 border-t">
                                    <Disclosure as="div" key="details">
                                        {({open}) => (
                                            <>
                                                <h3>
                                                    <Disclosure.Button
                                                        className="group relative flex w-full items-center justify-between py-1 text-left">
                            <span
                                className={classNames(open ? 'text-indigo-600' : 'text-gray-900', 'text-sm font-medium')}>
                              Exhibitions
                            </span>
                                                        <span className="ml-6 flex items-center">
                              {open ? (
                                  <MinusIcon
                                      className="block h-6 w-6 text-indigo-400 group-hover:text-indigo-500"
                                      aria-hidden="true"
                                  />
                              ) : (
                                  <PlusIcon
                                      className="block h-6 w-6 text-gray-400 group-hover:text-gray-500"
                                      aria-hidden="true"
                                  />
                              )}
                            </span>
                                                    </Disclosure.Button>
                                                </h3>
                                                <Disclosure.Panel as="div" className="prose prose-sm pb-6">
                                                    <div className="mx-auto max-w-7xl px-4 sm:px-6 lg:px-8">
                                                        {user.exhibitions && user.exhibitions.length > 0 ? (
                                                            <p>
                                                                {user.exhibitions.map((exhibitions, index) => (
                                                                    <p>
                                                                        {exhibitions.title}: {exhibitions.location} | {exhibitions.year} | {exhibitions.description}
                                                                    </p>
                                                                ))}
                                                            </p>
                                                        ) : (
                                                            <p>No social media accounts found.</p>
                                                        )}
                                                    </div>
                                                </Disclosure.Panel>
                                            </>
                                        )}
                                    </Disclosure>
                                </div>
                            </section>
                        </div>
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

export default function GallerieOfOtherUser() {
    const {id} = useParams();
    const [gallerie, setGallerie] = useState([])
    const [user, setUser] = useState()
    const [emptyGallerie, setEmptyGallerie] = useState(false);
    const [artwork, setArtwork] = useState([]);
    const [galerieId, setGalerieId] = useState();
    const navigate = useNavigate();
    const [isLoggedIn, setIsLoggedIn] = React.useState(false);

    //Immer wenn ein Fehler aus dem Backend kommt, wird der Fehler Dialog angezeigt

    //lad die Userdaten aus dem Backend, wenn es ein userFoto gibt, convertiert er es in eine brauchbare URL
    useEffect(() => {
        async function getUserData() {
            //loggedIn logik
            const loggedInHeader = await logikService.isLoggedIn();
            setIsLoggedIn(loggedInHeader);

            const result = await GalerieApiService.getUnsecuredData("/galleries/" + id);
            const urlGetUser = result.data.ownerId;
            const userProfile = await GalerieApiService.getUnsecuredData("/users/" + urlGetUser);
            setUser(userProfile.data);
            const getGalerie = result;
            setGalerieId(id);
            const artWork = getGalerie.data.artworks;
            setArtwork(artWork);
            gallerie.title = getGalerie.data.title;
            setGallerie({
                title: getGalerie.data.title,
                description: getGalerie.data.description,
                categories: getGalerie.data.categories.join(', ')
            })
            setEmptyGallerie(false);

        }

        getUserData();

    }, [])


    function convertImage(data) {
        if (data) {
            const byteCharacters = atob(data);
            const byteNumbers = new Array(byteCharacters.length);
            for (let i = 0; i < byteCharacters.length; i++) {
                byteNumbers[i] = byteCharacters.charCodeAt(i);
            }
            const byteArray = new Uint8Array(byteNumbers);
            // Create URL for the binary image data
            const blob = new Blob([byteArray], {type: 'image/png'}); // Adjust the 'type' according to the actual image format
            const url = URL.createObjectURL(blob);
            return url;

            //Blank Picture
        }
    }

    return (
        <>
            {isLoggedIn ? <HeaderLogedIn/> : <HeaderLogedOut/>}
            <ProfilUser/>
                <div className="bg-white">
                    <div className="mx-auto max-w-2xl px-4 py-7 sm:px-6 sm:py-7 lg:max-w-7xl">
                        <GalleryHeader gallery={gallerie} isLoggedIn={isLoggedIn}/>
                        <h2 className="sr-only">Products</h2>

                        <div
                            className="grid grid-cols-1 gap-x-6 gap-y-10 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 xl:gap-x-8">
                            {artwork.map((product) => (
                                <Link to={`/galerie/DetailImage/${product.id}`} key={product.id}>

                                    <div
                                        className="aspect-h-1 aspect-w-1 w-full overflow-hidden rounded-lg bg-gray-200 xl:aspect-h-8 xl:aspect-w-7">
                                        <img
                                            src={product.images[0]?.image.data ? convertImage(product.images[0].image.data) : Image1}
                                            alt="{product.imageAlt}"
                                            className="h-full w-full object-cover object-center group-hover:opacity-75"
                                        />
                                    </div>
                                    <div className="flex flex-col justify-between mt-4 ">
                                        <div className="flex  flex-wrap">
                                            {product?.artDirections.map((tag) => (
                                                <span className="tag tag-sm">#{tag}</span>
                                            ))}
                                        </div>
                                        <div className="d-flex flex-wrap justify-content-between mt-4 mx-7">
                                            <h3 className="link text-lg font-medium text-gray-900 flex-grow-1">{product.title}</h3>
                                            <p className="link mt-1 text-lg font-medium text-gray-900">{product.price} Euro</p>
                                        </div>
                                        <p className="link text-sm text-gray-700 my-7">{product.description}</p>
                                    </div>
                                </Link>

                            ))}
                        </div>
                    </div>
                </div>



        </>
    );
}
