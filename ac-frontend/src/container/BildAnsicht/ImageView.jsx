import {useEffect, useState} from 'react'
import {useParams, useNavigate} from 'react-router-dom';
import {Disclosure, RadioGroup, Tab} from '@headlessui/react'
import {StarIcon} from '@heroicons/react/20/solid'
import {HeartIcon, MinusIcon, PlusIcon} from '@heroicons/react/24/outline'
import {GalerieApiService} from "../../lib/apiGalerie"
import {storageService} from "../../lib/localStorage"
import Image1 from '../../images/defaultArtworkPlaceholder.png';
import React from "react";
import Header from "../../components/headerComponent/headerLogedIn"
import {logikService} from  "../../lib/service"
import HeaderLogedIn from "../../components/headerComponent/headerLogedIn";
import HeaderLogedOut from "../../components/headerComponent/headerLogout";
import ModalSuccess from "../../components/ModalPopUp/ModalSuccess";

const product = {
    name: 'Olivia Montague',
    rating: 4,
    images: [
        {
            id: 1,
            name: 'Angled view',
            src: Image1,
            alt: 'Angled front view with bag zipped and handles upright.',
        },
    ],
    colors: [
        {name: 'Washed Black', bgColor: 'bg-gray-700', selectedColor: 'ring-gray-700'},
        {name: 'White', bgColor: 'bg-white', selectedColor: 'ring-gray-400'},
        {name: 'Washed Gray', bgColor: 'bg-gray-500', selectedColor: 'ring-gray-500'},
    ],
    description: `
    <p>
        I painted this Image in 1986, I paid attention to detail, capturing the subtle play of light and shadow.
        The use of pencil medium adds a delicate and precise quality to the artwork, 
        showcasing shading. Overall, the artwork conveys a sense of nostalgia and evokes a feeling of calmness.
    </p>
  `,
    details: [
        {
            name: 'Details',
            items: [
                'Size A1',
                'Drawn with a Pencil',
                'Black and White',
                'Created in 1986',
            ],
        },
        // More sections...
    ],
}

function classNames(...classes) {
    return classes.filter(Boolean).join(' ')
}

export default function Example() {
    const [selectedColor, setSelectedColor] = useState(product.colors[0])
    const {id} = useParams();
    const [artwork, setArtwork] = useState();
    const navigate = useNavigate();
    const [isLoggedIn, setIsLoggedIn] = React.useState(false);
    const [success, setSuccess]= React.useState(false);
    const [failure, setFailure] = React.useState(false);
    const [openModal, setOpenModal] =React.useState(false);

    //Modal
    const handleOpenModal = () => {
        setOpenModal(true);
    };

    const handleCloseModal = () => {
        setSuccess(false);
        setFailure(false);
        setOpenModal(false);
    };

    useEffect(() => {
        async function getUserData() {
            const loggedInHeader = await logikService.isLoggedIn();
            setIsLoggedIn(loggedInHeader);
            const getArtwork = await GalerieApiService.getSecuredData("/artworks/" + id);
            console.log("artwork: " + JSON.stringify(getArtwork.data));
            setArtwork(getArtwork.data);
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
            console.log("Image undefined.")
        }
    }

    async function deleteArtwort(id) {
        console.log("deleteArtwork: " + id);
        const result = await GalerieApiService.deleteSecuredData("/artworks/" + id);
        if(result =="success"){
            setSuccess(true);
            handleOpenModal();
        }else{
            setFailure(true);
        }

    }

    return (
        <>
            {isLoggedIn? <HeaderLogedIn/>:<HeaderLogedOut/>}
            <div className="bg-white">
                {success && <ModalSuccess open={openModal} handleClose={handleCloseModal} meassageHeader="Delete Artwork" message="You have successfully deleted the Artwork" url="/galerie"/>}
                {failure && <ModalSuccess open={openModal} handleClose={handleCloseModal} meassageHeader="Delete Artwork" message="Opps. Something went wrong, please Try again later!" url="/galerie" />}
                <div className="mx-auto max-w-2xl px-4 py-16 sm:px-6 sm:py-24 lg:max-w-7xl lg:px-8">
                    <div className="lg:grid lg:grid-cols-2 lg:items-start lg:gap-x-8">
                        {/* Image gallery */}
                        <Tab.Group as="div" className="flex flex-col-reverse">
                            {/* Image selector */}
                            <Tab.Panels className="aspect-h-1 aspect-w-1 w-full">
                                {artwork && artwork.images && artwork.images[0] ? (
                                    <Tab.Panel>
                                        <img
                                            src={convertImage(artwork.images[0].image.data)}
                                            className="h-full w-full object-cover object-center sm:rounded-lg"
                                        />
                                    </Tab.Panel>
                                ) : <Tab.Panel>
                                    <img
                                        src={Image1}
                                        className="h-full w-full object-cover object-center sm:rounded-lg"
                                    />
                                </Tab.Panel>}
                            </Tab.Panels>
                        </Tab.Group>

                        {/* Product info */}
                        <div className="mt-10 px-4 sm:mt-16 sm:px-0 lg:mt-0">
                            <div className="flex items-center mb-7">
                                <h1 className="text-3xl font-bold tracking-tight text-gray-900">{artwork?.title}</h1>
                                <button
                                    onClick={() => {
                                        navigate("/editArt", {state: {artworkOld: artwork, artworkId: id}});
                                    }}
                                    className="inline-flex items-center rounded-md bg-indigo-600 px-3 py-2 text-sm font-semibold text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600 m-7"
                                >
                                    Edit Artwork
                                </button>

                                <button
                                    onClick={() => {
                                        deleteArtwort(id);

                                    }}
                                    className="inline-flex items-center rounded-md px-3 py-2 text-sm font-semibold text-black shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600 "
                                >
                                    Delete Artwork
                                </button>
                            </div>
                            <div className="like-count text-gray-400">{artwork?.location === "" ?
                                <span>unknown, </span> : <span>{artwork?.location}, </span>} {artwork?.yearOfCreation}
                            </div>
                            {artwork?.tags.map((tag) => (
                                <span className="tag tag-sm">#{tag}</span>
                            ))}

                            <div className="mt-3">
                                <h2 className="sr-only">Product information</h2>
                                {/* <p className="text-3xl tracking-tight text-gray-900">{product.price}</p> */}
                            </div>


                            <div className="mt-6">
                                <h3 className="sr-only">Description</h3>

                                <div
                                    className="space-y-6 text-base text-gray-700"
                                    dangerouslySetInnerHTML={{__html: artwork?.description}}
                                />
                            </div>

                            <form className="mt-6">

                                <div className="mt-10 flex">
                                    <button
                                        //   type="submit"
                                        className="flex max-w-xs flex-1 items-center justify-center rounded-md border border-transparent bg-indigo-600 px-8 py-3 text-base font-medium text-white hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-offset-2 focus:ring-offset-gray-50 sm:w-full"
                                    >
                                        <a className="link w" href='http://localhost:3000/404'>
                                            Chat with the Artist
                                        </a>
                                    </button>

                                    <button
                                        type="button"
                                        className="ml-4 flex items-center justify-center rounded-md px-3 py-3 text-gray-400 hover:bg-gray-100 hover:text-gray-500"
                                    >
                                        <HeartIcon className="h-6 w-6 flex-shrink-0" aria-hidden="true"/>
                                        <span className="sr-only">Add to favorites</span>
                                        <span className="like-count"> {artwork?.likes} likes</span>
                                    </button>

                                </div>
                            </form>

                            <section aria-labelledby="details-heading" className="mt-12">
                                <h2 id="details-heading" className="sr-only">
                                    Additional details
                                </h2>

                                <div className="divide-y divide-gray-200 border-t">
                                    <Disclosure as="div" key="details">
                                        {({open}) => (
                                            <>
                                                <h3>
                                                    <Disclosure.Button
                                                        className="group relative flex w-full items-center justify-between py-6 text-left">
                            <span
                                className={classNames(open ? 'text-indigo-600' : 'text-gray-900', 'text-sm font-medium')}
                            >
                              Details
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
                                                    <ul role="list">
                                                        <li>
                                                            Dimension: <span>Height: {artwork?.height}</span>
                                                            <span>Width: {artwork?.dimension.width}</span>
                                                            <span>Depth: {artwork?.dimension.depth}</span>
                                                        </li>
                                                        <li>Price: {artwork?.price} Euro</li>
                                                    </ul>
                                                </Disclosure.Panel>
                                            </>
                                        )}
                                    </Disclosure>
                                </div>
                                <div className="divide-y divide-gray-200 border-t">
                                    <Disclosure as="div" key="materials">
                                        {({open}) => (
                                            <>
                                                <h3>
                                                    <Disclosure.Button
                                                        className="group relative flex w-full items-center justify-between py-6 text-left">
                            <span
                                className={classNames(open ? 'text-indigo-600' : 'text-gray-900', 'text-sm font-medium')}
                            >
                              Materials
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
                                                    <ul role="list">
                                                        {artwork?.materials.map((material) => (
                                                            <li>{material}</li>))}
                                                    </ul>
                                                </Disclosure.Panel>
                                            </>
                                        )}
                                    </Disclosure>
                                </div>
                            </section>
                        </div>
                    </div>
                </div>
            </div>

        </>
    )
}