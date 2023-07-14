import {useEffect, useState} from 'react'
import { useParams, useNavigate} from 'react-router-dom';
import {Disclosure, RadioGroup, Tab} from '@headlessui/react'
import {StarIcon} from '@heroicons/react/20/solid'
import {HeartIcon, MinusIcon, PlusIcon} from '@heroicons/react/24/outline'
import {GalerieApiService} from "../../lib/apiGalerie"
import {storageService} from "../../lib/localStorage"
import Image1 from './../Galerie/imgSlides/original.jpg';
import React from "react";
import Header from "../../components/headerComponent/headerLogedIn"
import axios from 'axios';
import './FiveStarRating.css';



import { FaStar } from 'react-icons/fa';

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
        // More images...
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

export default function Example({galleryId}) {
    const [selectedColor, setSelectedColor] = useState(product.colors[0])
    const {id} = useParams();
    const [artwork, setArtwork] = useState();
    useEffect(() => {
        async function getUserData() {
            const getArtwork= await GalerieApiService.getSecuredData("/artworks/"+id);
            console.log("artwork: "+ JSON.stringify(getArtwork.data));
            setArtwork(getArtwork.data);
        }
        getUserData();

    }, [])


    const [selectedRating, setSelectedRating] = useState(0);

    const handleRatingClick = async (rating) => {
      try {                                                                 // where to get galleryId from.
        const response = await axios.post(`http://localhost:8080/galleries/${galleryId}/rating`, { rating });
        console.log(response.data); // Handle the API response as needed
      } catch (error) {
        console.error('Error submitting rating:', error);
      }
    };

        const renderStars = () => {
            const stars = [];
            for (let i = 1; i <= 5; i++) {
              const starClassName = i <= selectedRating ? 'star selected' : 'star';
              stars.push(
                <span
                  key={i}
                  className={starClassName}
                  onClick={() => handleRatingClick(i)}
                >
                  ★
                </span>
              );
            }
            return stars;
          };


    return (
        <>
            <Header></Header>
            <div className="bg-white">

                <div className="mx-auto max-w-2xl px-4 py-16 sm:px-6 sm:py-24 lg:max-w-7xl lg:px-8">

                    <div className="lg:grid lg:grid-cols-2 lg:items-start lg:gap-x-8">
                        {/* Image gallery */}
                        <Tab.Group as="div" className="flex flex-col-reverse">
                            {/* Image selector */}
                            <div className="mx-auto mt-6 hidden w-full max-w-2xl sm:block lg:max-w-none">
                                <Tab.List className="grid grid-cols-4 gap-6">
                                    {product.images.map((image) => (
                                        <Tab
                                            key={image.id}
                                            className="relative flex h-24 cursor-pointer items-center justify-center rounded-md bg-white text-sm font-medium uppercase text-gray-900 hover:bg-gray-50 focus:outline-none focus:ring focus:ring-opacity-50 focus:ring-offset-4"
                                        >
                                            {({selected}) => (
                                                <>
                                                    <span className="sr-only">{image.name}</span>
                                                    <span className="absolute inset-0 overflow-hidden rounded-md">
                          <img src={image.src} alt="" className="h-full w-full object-cover object-center"/>
                        </span>
                                                    <span
                                                        className={classNames(
                                                            selected ? 'ring-indigo-500' : 'ring-transparent',
                                                            'pointer-events-none absolute inset-0 rounded-md ring-2 ring-offset-2'
                                                        )}
                                                        aria-hidden="true"
                                                    />
                                                </>
                                            )}
                                        </Tab>
                                    ))}
                                </Tab.List>
                            </div>

                            <Tab.Panels className="aspect-h-1 aspect-w-1 w-full">
                                {product.images.map((image) => (
                                    <Tab.Panel key={image.id}>
                                        <img
                                            src={image.src}
                                            alt={image.alt}
                                            className="h-full w-full object-cover object-center sm:rounded-lg"
                                        />
                                    </Tab.Panel>
                                ))}
                            </Tab.Panels>
                        </Tab.Group>

                        {/* Product info */}
                        <div className="mt-10 px-4 sm:mt-16 sm:px-0 lg:mt-0">
                            <h1 className="text-3xl font-bold tracking-tight text-gray-900">{artwork?.title}</h1>
                            <div className="like-count text-gray-400">{artwork?.location === "" ? <span>unknown, </span> : <span>{artwork?.location}, </span>} {artwork?.yearOfCreation}
                            </div>
                             {artwork?.tags.map((tag)=>(
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


                                    {/* Favoriete button */}
                                        <div className="rating">
                                            {renderStars()}
                                        </div>

                                    {/* <button
                                        type="button"
                                        className="ml-4 flex items-center justify-center rounded-md px-3 py-3 text-gray-400 hover:bg-gray-100 hover:text-gray-500"
                                    >
                                        <HeartIcon className="h-6 w-6 flex-shrink-0" aria-hidden="true"/>
                                        <span className="sr-only">Add to favorites</span>
                                        <span className="like-count"> {artwork?.likes} likes</span>
                                    </button> */}

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
                                                            Dimension: <span>Height: {artwork?.height}</span> <span>Width: {artwork?.dimension.width}</span> <span>Depth: {artwork?.dimension.depth}</span>
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