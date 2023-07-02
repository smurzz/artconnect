import * as React from 'react';
import {GalerieApiService} from "../../lib/apiGalerie"
import {storageService} from "../../lib/localStorage"
import {ApiService} from "../../lib/api";
import { PlusIcon } from '@heroicons/react/20/solid'
import {useNavigate, Link} from "react-router-dom";
import {StarIcon} from '@heroicons/react/20/solid'

import Image1 from './imgSlides/original.jpg';
import Image2 from './imgSlides/original2.jpg';
import Image3 from './imgSlides/original3.jpg';
import Image4 from './imgSlides/original4.jpg';
import Image5 from './imgSlides/original5.jpg';
import Image6 from './imgSlides/original6.jpg';
import Image7 from './imgSlides/original7.jpg';
import Image8 from './imgSlides/original8.jpg';
import Image9 from './imgSlides/original9.jpg';
import Header from "../../components/headerComponent/headerLogedIn";
import Profil from "../../components/UserProfileHeader/userProfile"
import {useEffect, useState} from "react";
function classNames(...classes) {
    return classes.filter(Boolean).join(' ')
}

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


const products = [
    {
      id: 1,
      name: 'Olivia Montague',
      href: '/galerie/DetailImage/1',
      imageSrc: Image1,
      imageAlt: 'Tall slender porcelain bottle with natural clay textured body and cork stopper.',
    },
    {
      id: 2,
      name: 'Sebastian Wolfe',
      href: '/galerie/DetailImage/2',
      imageSrc: Image2,
      imageAlt: 'Olive drab green insulated bottle with flared screw lid and flat top.',
    },
    {
      id: 3,
      name: 'Isabella Marchand',
      href: '/galerie/DetailImage/3',
      imageSrc: Image3,
      imageAlt: 'Person using a pen to cross a task off a productivity paper card.',
    },
    {
      id: 4,
      name: 'Alexander Hartmann',
      href: '/galerie/DetailImage/4',
      imageSrc: Image4,
      imageAlt: 'Hand holding black machined steel mechanical pencil with brass tip and top.',
    },
    {
      id: 5,
      name: 'Victoria Lefevre',
      href: '/galerie/DetailImage/5',
      imageSrc: Image5,
      imageAlt: 'Hand holding black machined steel mechanical pencil with brass tip and top.',
    },
    {
      id: 6,
      name: 'Gabriel Delacroix',
      href: '/galerie/DetailImage/6',
      imageSrc: Image6,
      imageAlt: 'Hand holding black machined steel mechanical pencil with brass tip and top.',
    },
    {
      id: 7,
      name: 'Sophia Davenport',
      href: '/galerie/DetailImage/7',
      imageSrc: Image7,
      imageAlt: 'Hand holding black machined steel mechanical pencil with brass tip and top.',
    },
    {
      id: 8,
      name: 'Lucas Beaumont',
      href: '/galerie/DetailImage/8',
      imageSrc: Image8,
      imageAlt: 'Hand holding black machined steel mechanical pencil with brass tip and top.',
    },
    {
      id: 9,
      name: 'Amelia Rousseau',
      href: '/galerie/DetailImage/9',
      imageSrc: Image9,
      imageAlt: 'Hand holding black machined steel mechanical pencil with brass tip and top.',
    },
    // More products...
  ]

  const profile = {
    name: 'Vyacheslav',
    email: 'Vyacheslav@example.com',
    web: 'Vyacheslav.com',
    avatar:
      'https://images.unsplash.com/photo-1463453091185-61582044d556?ixlib=rb-=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=facearea&facepad=8&w=1024&h=1024&q=80',
    backgroundImage:
      'https://images.unsplash.com/photo-1444628838545-ac4016a5418a?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=1950&q=80',
    fields: [
      ['Phone', '(555) 123-4567'],
      ['Email', 'Vyacheslav@example.com'],
      ['Title', 'Senior Front-End Developer'],
      ['Team', 'Product Development'],
      ['Location', 'San Francisco'],
      ['Sits', 'Oasis, 4th floor'],
      ['Salary', '12$'],
      ['Birthday', 'June 8, 1990'],
    ],
  }

const GalleryHeader = ({ gallery }) => {
    const { title, description, categories } = gallery;
    useEffect(()=>{
        console.log("categories: "+categories);

    },[])


    return (
        <div>
            <h1>{title}</h1>
            <h3 className="sr-only">Reviews</h3>
                <div className="flex items-center">
                    {[0, 1, 2, 3, 4].map((rating) => (
                        <StarIcon
                            key={rating}
                            className={classNames(
                                product.rating > rating ? 'text-indigo-500' : 'text-gray-300',
                                'h-5 w-5 flex-shrink-0'
                            )}
                            aria-hidden="true"
                        />
                    ))}
                </div>
            <p>{description}</p>
            { categories && <p className="tag tag-sm">{categories}</p>}
        </div>
    );
};

export default function Gallery() {

    const [gallerie, setGallerie]=useState([])
    const [user, setUser] = useState()
    const [emptyGallerie,setEmptyGallerie] = useState(false);
    const [artwork, setArtwork]= useState([]);
    const navigate = useNavigate();
    //lad die Userdaten aus dem Backend, wenn es ein userFoto gibt, convertiert er es in eine brauchbare URL
    useEffect(() => {
        async function getUserData() {
            const result = await storageService.getUser();
            const urlGetUser = `http://localhost:8080/users?email=${result}`.replace(/"/g, '');
            const userProfile = await ApiService.getDataSecuredWithParameter(urlGetUser);
            setUser(userProfile.data);
            const userGallerieId = await storageService.getGallerieId();
            const userGallerieIdClean = userGallerieId.replace(/"/g, "");
            const getGalerie= await GalerieApiService.getSecuredData("/galleries/"+userGallerieIdClean);
            console.log("getgalerie: "+ getGalerie);
            if(getGalerie == null){
                console.log("setEmpty :"+ true);
                setEmptyGallerie(true);
            }else{
                console.log("getGAllerie: "+ JSON.stringify(getGalerie.data.artworks));
                const artWork= getGalerie.data.artworks;
                console.log("artwork: "+ artWork);
                setArtwork(artWork);
                console.log("artwork: "+ artwork);
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
    const navigateToEditGallerie=()=>{
        navigate(`/postGalerie/${user.id}`);
    }
    return (
        <>
            <Header/>
            <Profil></Profil>
            {emptyGallerie ==true ?
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
                            className="inline-flex items-center rounded-md bg-indigo-600 px-3 py-2 text-sm font-semibold text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600"
                        >
                            <PlusIcon className="-ml-0.5 mr-1.5 h-5 w-5" aria-hidden="true" />
                            New gallery
                        </button>
                    </div>
                </div>
                :
                <div className="bg-white">
                    <div className="mx-auto max-w-2xl px-4 py-16 sm:px-6 sm:py-24 lg:max-w-7xl lg:px-8">
                        <GalleryHeader gallery={gallerie}/>
                        <button onClick={()=>{navigate("/newArt")}}
                                type="button"
                                className="inline-flex items-center rounded-md bg-indigo-600 px-3 py-2 text-sm font-semibold text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600"
                        >
                            <PlusIcon className="-ml-0.5 mr-1.5 h-5 w-5" aria-hidden="true" />
                            New Artpiece
                        </button>
                        <h2 className="sr-only">Products</h2>

                        <div className="grid grid-cols-1 gap-x-6 gap-y-10 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 xl:gap-x-8">
                            {artwork.map((product) => (
                                <Link to={`/galerie/DetailImage/${product.id}`}>
                                    <div className="aspect-h-1 aspect-w-1 w-full overflow-hidden rounded-lg bg-gray-200 xl:aspect-h-8 xl:aspect-w-7">
                                        <img
                                            src={Image1}
                                            alt="{product.imageAlt}"
                                            className="h-full w-full object-cover object-center group-hover:opacity-75"
                                        />
                                    </div>
                                    <h3 className="link mt-4 text-sm text-gray-700">{product.title}</h3>
                                    <p className="link mt-1 text-lg font-medium text-gray-900">{product.description}</p>
                                    <p className="link mt-1 text-lg font-medium text-gray-900">{product.price} Euro</p>

                                </Link>
                            ))}
                        </div>
                    </div>
                </div>
            }




      </>
    );
}