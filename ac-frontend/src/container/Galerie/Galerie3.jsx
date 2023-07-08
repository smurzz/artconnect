import React, {useEffect, useState} from 'react';
import {useNavigate, Link} from "react-router-dom";
import {GalerieApiService} from "../../lib/apiGalerie"

import Image1 from './imgSlides/original.jpg';
import Image2 from './imgSlides/original2.jpg';
import Image3 from './imgSlides/original3.jpg';
import Image4 from './imgSlides/original4.jpg';
import Image5 from './imgSlides/original5.jpg';
import Image6 from './imgSlides/original6.jpg';
import Image7 from './imgSlides/original7.jpg';
import Image8 from './imgSlides/original8.jpg';
import Image9 from './imgSlides/original9.jpg';

const artworks = [
    {
      id: 1,
      price: 20,
      name: 'Olivia Montague',
      href: '/galerie/DetailImage/1',
      imageSrc: Image1,
      imageAlt: 'Tall slender porcelain bottle with natural clay textured body and cork stopper.',
    },
    {
      id: 2,
      price: 20,
      name: 'Sebastian Wolfe',
      href: '/galerie/DetailImage/2',
      imageSrc: Image2,
      imageAlt: 'Olive drab green insulated bottle with flared screw lid and flat top.',
    },
    {
      id: 3,
      price: 20,
      name: 'Isabella Marchand',
      href: '/galerie/DetailImage/3',
      imageSrc: Image3,
      imageAlt: 'Person using a pen to cross a task off a productivity paper card.',
    },
    {
      id: 4,
      price: 20,
      name: 'Alexander Hartmann',
      href: '/galerie/DetailImage/4',
      imageSrc: Image4,
      imageAlt: 'Hand holding black machined steel mechanical pencil with brass tip and top.',
    },
    {
      id: 5,
      price: 20,
      name: 'Victoria Lefevre',
      href: '/galerie/DetailImage/5',
      imageSrc: Image5,
      imageAlt: 'Hand holding black machined steel mechanical pencil with brass tip and top.',
    },
    {
      id: 6,
      price: 20,
      name: 'Gabriel Delacroix',
      href: '/galerie/DetailImage/6',
      imageSrc: Image6,
      imageAlt: 'Hand holding black machined steel mechanical pencil with brass tip and top.',
    },
    {
      id: 7,
      price: 20,
      name: 'Sophia Davenport',
      href: '/galerie/DetailImage/7',
      imageSrc: Image7,
      imageAlt: 'Hand holding black machined steel mechanical pencil with brass tip and top.',
    },
    {
      id: 8,
      price: 20,
      name: 'Lucas Beaumont',
      href: '/galerie/DetailImage/8',
      imageSrc: Image8,
      imageAlt: 'Hand holding black machined steel mechanical pencil with brass tip and top.',
    },
    {
      id: 9,
      price: 20,
      name: 'Amelia Rousseau',
      href: '/galerie/DetailImage/9',
      imageSrc: Image9,
      imageAlt: 'Hand holding black machined steel mechanical pencil with brass tip and top.',
    },
    // More products...
  ]

const Galerie3 = () => {
    const [galerie, setGalerie] = useState([]);

    useEffect(() => {
        async function getGaleryData() {
            const getGalerie= await GalerieApiService.getUnsecuredData("/galleries");
            console.log("getgalerie: "+ JSON.stringify(getGalerie.data));
            setGalerie(getGalerie.data);
        }

        getGaleryData();

    }, [])
    

  return (
    <div className='container'>
                        <div className="grid grid-cols-1 gap-x-6 gap-y-10 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 xl:gap-x-8">
                            {galerie?.map((artwork) => (
                                <Link to={`/galerie/DetailImage/${artwork.id}`}>
                                    <div className="aspect-h-1 aspect-w-1 w-full overflow-hidden rounded-lg bg-gray-200 xl:aspect-h-8 xl:aspect-w-7">
                                        <img
                                            src={artwork.imageSrc}
                                            alt="{product.imageAlt}"
                                            className="h-full w-full object-cover object-center group-hover:opacity-75"
                                        />
                                    </div>
                                    <h3 className="link mt-4 text-sm text-gray-700">{artwork.name}</h3>
                                    <p className="link mt-1 text-lg font-medium text-gray-900">{artwork.description}</p>
                                    <p className="link mt-1 text-lg font-medium text-gray-900">{artwork.price} Euro</p>

                                </Link>
                            ))}
                        </div>
    </div>
  )
}

export default Galerie3