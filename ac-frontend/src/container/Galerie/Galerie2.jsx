import * as React from 'react';


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

export default function Gallery() {
    return (
        <>
          <Header/>
    <Profil></Profil>

        <div className="bg-white">
        <div className="mx-auto max-w-2xl px-4 py-16 sm:px-6 sm:py-24 lg:max-w-7xl lg:px-8">
          <h2 className="sr-only">Products</h2>
  
          <div className="grid grid-cols-1 gap-x-6 gap-y-10 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 xl:gap-x-8">
            {products.map((product) => (
              <a key={product.id} href={product.href} className="group">
                <div className="aspect-h-1 aspect-w-1 w-full overflow-hidden rounded-lg bg-gray-200 xl:aspect-h-8 xl:aspect-w-7">
                  <img
                    src={product.imageSrc}
                    alt={product.imageAlt}
                    className="h-full w-full object-cover object-center group-hover:opacity-75"
                  />
                </div>
                <h3 className="link mt-4 text-sm text-gray-700">{product.name}</h3>
                <p className="link mt-1 text-lg font-medium text-gray-900">{product.price}</p>
              </a>
            ))}
          </div>
        </div>
      </div>
      </>
    );
}