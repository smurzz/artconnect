import * as React from 'react';
import {GalerieApiService} from "../../lib/apiGalerie"
import {storageService} from "../../lib/localStorage"
import {ApiService} from "../../lib/api";
import {PlusIcon} from '@heroicons/react/20/solid'
import {useNavigate, Link} from "react-router-dom";
import {StarIcon} from '@heroicons/react/20/solid'
import CircularProgress from '@mui/material/CircularProgress';
import Box from '@mui/material/Box';
import Image1 from '../../images/defaultArtworkPlaceholder.png';
import {logikService} from "../../lib/service"
import HeaderLogedIn from "../../components/headerComponent/headerLogedIn";
import HeaderLogedOut from "../../components/headerComponent/headerLogout";
import Profil from "../../components/UserProfileHeader/userProfile"
import {useEffect, useState} from "react";
import ModalSuccess from "../../components/ModalPopUp/ModalSuccess"
import {Disclosure, RadioGroup, Tab} from '@headlessui/react'
import {MinusIcon} from '@heroicons/react/24/outline'

function classNames(...classes) {
    return classes.filter(Boolean).join(' ')
}

const product = {
    rating: 4
}
const GalleryHeader = ({gallery, id}) => {
    const {title, description, categories} = gallery;
    const [openModal, setOpenModal] = useState(false)
    const [starUserRating, setStarUserRating] = useState();
    const [ranking, setRanking] = useState();
    const [success, setSuccess] = useState(false);
    const navigate = useNavigate();
    const galerieId = id;

    useEffect(()=>{
        async function getGallerieData(){
            const userGallerieId = await GalerieApiService.getSecuredData("/galleries/myGallery");
            const result = await GalerieApiService.getSecuredData("/galleries/"+ userGallerieId.data.id);
            setRanking(result.data.ranking);
            console.log("getGallerieData: "+ result.data.ranking)
        }
        getGallerieData()
    },[])
    const handleCloseModal = () => {
        setOpenModal(false);
    };

    async function deleteGalerie() {

        console.log()
        //GalerieApiService.putSecuredParameter("/galleries/64a91ed469a57e1c3a4d54d3/rating?value=3")
        const result = await GalerieApiService.deleteSecuredData("/galleries/" + galerieId);
        setOpenModal(true);
    }

    async function sendUserRating() {
        const result = await GalerieApiService.postSecuredData("/galleries/" + galerieId + "/rating?value=" + starUserRating, {});
        setSuccess(true);
    }


    return (
        <div>
            <ModalSuccess open={openModal} handleClose={handleCloseModal} meassageHeader="Delete gallery"
                          message="Your gallery has been successfully deleted" url="/"/>
            <div className="flex items-center ">
                <h1 className="mr-4">{title}</h1>
                <button
                    onClick={() => {
                        navigate("/editGallery", {state: {gallery: gallery, galerieId: id}});
                    }}
                    className="inline-flex items-center rounded-md bg-indigo-600 px-3 py-2 text-sm font-semibold text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600"
                >
                    Edit Gallery
                </button>
                <button
                    onClick={() => {
                        deleteGalerie();

                    }}
                    className=" mx-7 inline-flex items-center rounded-md bg-blue-200 px-3 py-2 text-sm font-semibold text-slate-700 shadow-sm hover:bg-blue-300 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:bg-blue-300 mt-7 mb-7"
                >
                    Delete Galerie
                </button>
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
                                            onClick={() => setStarUserRating(index+1)}
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
        </div>

    );
};

export default function Gallery() {
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

            const result = await storageService.getUser();
            const urlGetUser = `/users?email=${result}`.replace(/"/g, '');
            const userProfile = await ApiService.getDataSecuredWithParameter(urlGetUser);
            setUser(userProfile.data);
            const userGallerieId = await GalerieApiService.getSecuredData("/galleries/myGallery");
            if (userGallerieId === null) {
                setEmptyGallerie(true);
            } else {
                const getGalerie = await GalerieApiService.getSecuredData("/galleries/" + userGallerieId.data.id);
                setGalerieId(userGallerieId.data.id);
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
        }

        getUserData();

    }, [])
    const navigateToEditGallerie = () => {
        navigate(`/postGalerie/${user.id}`);
    }

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
            <Profil></Profil>
            {emptyGallerie == true ?
                <div className="text-center">
                    <svg
                        className="mx-auto h-12 w-12 text-gray-400"
                        fill="none"
                        viewBox="0 0 24 24"
                        stroke="currentColor"
                        aria-hidden="true"
                    >
                        <path
                            vectorEffect="non-scaling-stroke"
                            strokeLinecap="round"
                            strokeLinejoin="round"
                            strokeWidth={2}
                            d="M9 13h6m-3-3v6m-9 1V7a2 2 0 012-2h6l2 2h6a2 2 0 012 2v8a2 2 0 01-2 2H5a2 2 0 01-2-2z"
                        />
                    </svg>
                    <h3 className="mt-2 text-sm font-semibold text-gray-900">No galleries</h3>
                    <p className="mt-1 text-sm text-gray-500">Get started by creating a new gallery.</p>
                    <div className="mt-6">
                        <button onClick={navigateToEditGallerie}
                                type="button"
                                className="inline-flex items-center rounded-md bg-indigo-600 px-3 py-2 text-sm font-semibold text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600">
                            <PlusIcon className="-ml-0.5 mr-1.5 h-5 w-5" aria-hidden="true"/>
                            New gallery
                        </button>
                    </div>
                </div>
                :
                <div className="bg-white">
                    <div className="mx-auto max-w-2xl px-4 py-7 sm:px-6 sm:py-7 lg:max-w-7xl">
                        <GalleryHeader gallery={gallerie} id={galerieId}/>
                        <button onClick={() => {
                            navigate("/newArt")
                        }}
                                type="button"
                                className="inline-flex items-center rounded-md bg-indigo-600 px-3 py-2 text-sm font-semibold text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600 mt-7 mb-7"
                        >
                            <PlusIcon className="-ml-0.5 mr-1.5 h-5 w-5" aria-hidden="true"/>
                            New Artpiece
                        </button>
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
                                        <div className="d-flex flex-wrap justify-content-between ">
                                            <h3 className="link text-lg font-medium text-gray-900 flex-grow-1">{product.title}</h3>
                                            <p className="link mt-1 text-lg font-medium text-gray-900">{product.price} Euro</p>
                                        </div>
                                        <p className="link text-sm text-gray-700 ">{product.description}</p>
                                    </div>
                                </Link>

                            ))}
                        </div>
                    </div>
                </div>
            }


        </>
    );
}
