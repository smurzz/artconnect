// import React, { Fragment, useEffect, useState } from 'react';
// import { Link } from "react-router-dom";
// import { GalerieApiService } from "../../lib/apiGalerie"
// import Searchbar from './Searchbar';
// import Banner from './Banner';
// import './filter.css';

// import Image1 from './imgSlides/original.jpg';
// import Image2 from './imgSlides/original2.jpg';
// import Image3 from './imgSlides/original3.jpg';
// import Image4 from './imgSlides/original4.jpg';
// import Image5 from './imgSlides/original5.jpg';
// import Image6 from './imgSlides/original6.jpg';
// import Image7 from './imgSlides/original7.jpg';
// import Image8 from './imgSlides/original8.jpg';
// import Image9 from './imgSlides/original9.jpg';

// const artworks = [
//     {
//       id: 1,
//       price: 20,
//       name: 'Olivia Montague',
//       href: '/galerie/DetailImage/1',
//       imageSrc: Image1,
//       imageAlt: 'Tall slender porcelain bottle with natural clay textured body and cork stopper.',
//       tags: ['Drawing']
//     },
//     {
//       id: 2,
//       price: 20,
//       name: 'Sebastian Wolfe',
//       href: '/galerie/DetailImage/2',
//       imageSrc: Image2,
//       imageAlt: 'Olive drab green insulated bottle with flared screw lid and flat top.',
//       tags: ['Drawing']
//     },
//     {
//       id: 3,
//       price: 20,
//       name: 'Isabella Marchand',
//       href: '/galerie/DetailImage/3',
//       imageSrc: Image3,
//       imageAlt: 'Person using a pen to cross a task off a productivity paper card.',
//       tags: ['ceramic', 'sculpture']
//     },
//     {
//       id: 4,
//       price: 20,
//       name: 'Alexander Hartmann',
//       href: '/galerie/DetailImage/4',
//       imageSrc: Image4,
//       imageAlt: 'Hand holding black machined steel mechanical pencil with brass tip and top.',
//       tags: ['Photography']
//     },
//     {
//       id: 5,
//       price: 20,
//       name: 'Victoria Lefevre',
//       href: '/galerie/DetailImage/5',
//       imageSrc: Image5,
//       imageAlt: 'Hand holding black machined steel mechanical pencil with brass tip and top.',
//       tags: ['Painting']
//     },
//     {
//       id: 6,
//       price: 20,
//       name: 'Gabriel Delacroix',
//       href: '/galerie/DetailImage/6',
//       imageSrc: Image6,
//       imageAlt: 'Hand holding black machined steel mechanical pencil with brass tip and top.',
//       tags: ['Photography']
//     },
//     {
//       id: 7,
//       price: 20,
//       name: 'Sophia Davenport',
//       href: '/galerie/DetailImage/7',
//       imageSrc: Image7,
//       imageAlt: 'Hand holding black machined steel mechanical pencil with brass tip and top.',
//       tags: ['glass', 'bottle']
//     },
//     {
//       id: 8,
//       price: 20,
//       name: 'Lucas Beaumont',
//       href: '/galerie/DetailImage/8',
//       imageSrc: Image8,
//       imageAlt: 'Hand holding black machined steel mechanical pencil with brass tip and top.',
//       tags: ['glass', 'bottle']
//     },
//     {
//       id: 9,
//       price: 20,
//       name: 'Amelia Rousseau',
//       href: '/galerie/DetailImage/9',
//       imageSrc: Image9,
//       imageAlt: 'Hand holding black machined steel mechanical pencil with brass tip and top.',
//       tags: ['Photography']
//     },
//     // More products...
//   ]

// const profile = {
//     photography:'https://images.unsplash.com/photo-1471341971476-ae15ff5dd4ea?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=3132&q=80',
//     videography:'https://images.unsplash.com/photo-1535540878298-a155c6d065ef?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1770&q=80',
//     marketing:'https://images.unsplash.com/photo-1568992687947-868a62a9f521?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=2064&q=80',
//   }

// const Galerie3 = () => {
//     const [filteredArtworks, setFilteredArtworks] = useState([]);
//     const [selectedTag, setSelectedTag] = useState('');
  
//     const handleSearch = (searchTerm) => {
//       const filtered = artworks.filter((artwork) =>
//         artwork.name.toLowerCase().includes(searchTerm.toLowerCase())
//       );
//       setFilteredArtworks(filtered);
//     };
  
//     const handleTagFilter = (tag) => {
//       setSelectedTag(tag);
//       if (tag === '') {
//         setFilteredArtworks(artworks);
//       } else {
//         const filtered = artworks.filter((artwork) =>
//           artwork.tags.includes(tag)
//         );
//         setFilteredArtworks(filtered);
//       }
//     };

//   return (
//     <div className='container'>
//         <div className='mt-12'>
//             <Banner img={profile.marketing} />
//         </div>

//       <h1 className='mb-7 mt-7'>Browse the world of ArtConnect</h1>

//       <Searchbar onSearch={handleSearch} />

//     <div className="tag-buttons mt-7 mb-7">
//         <button
//           onClick={() => handleTagFilter('')}
//           className={`tag-button ${selectedTag === '' ? 'tag-button-active' : ''}`}
//         >
//           All
//         </button>
//         <button
//           onClick={() => handleTagFilter('Painting')}
//           className={`tag-button ${selectedTag === 'Painting' ? 'tag-button-active' : ''}`}
//         >
//           Painting
//         </button>
//         <button
//           onClick={() => handleTagFilter('Photography')}
//           className={`tag-button ${selectedTag === 'Photography' ? 'tag-button-active' : ''}`}
//         >
//           Photography
//         </button>
//         <button
//           onClick={() => handleTagFilter('Drawing')}
//           className={`tag-button ${selectedTag === 'Drawing' ? 'tag-button-active' : ''}`}
//         >
//           Drawing
//         </button>
//       </div>

//       <div className="grid grid-cols-1 gap-x-6 gap-y-10 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 xl:gap-x-8">
//         {filteredArtworks.map((artwork) => (
//             <>
//             <Link to={`/galerie/DetailImage/${artwork.id}`}>
//                 <div className="aspect-h-1 aspect-w-1 w-full overflow-hidden rounded-lg bg-gray-200 xl:aspect-h-8 xl:aspect-w-7">
//                 <img
//                     src={artwork.imageSrc}
//                     alt="{product.imageAlt}"
//                     className="h-full w-full object-cover object-center group-hover:opacity-75"
//                 />
//                 </div>
//                 <h3 className="link mt-4 text-sm text-gray-700">{artwork.name}</h3>
//                 <div className='pl-64'>
//                     <img
//                         className="inline-block h-10 w-10 rounded-full"
//                         src="https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=facearea&facepad=2&w=256&h=256&q=80"
//                         alt=""
//                     />
//                 </div>
//                 <p className="link mt-1 text-lg font-medium text-gray-900">{artwork.description}</p>
//                 <p className="link mt-1 text-lg font-medium text-gray-900">{artwork.price} Euro</p>
//             </Link>
            
//             </>
//         ))}
//       </div>
//     </div>
//   )
// }

// export default Galerie3;

import React, { useState, useEffect } from 'react';
import { Link } from "react-router-dom";
import { GalerieApiService } from "../../lib/apiGalerie";
import Searchbar from './Searchbar';
import Banner from './Banner';
import './filter.css';

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
    // {
    //   id: 1,
    //   price: 20,
    //   name: 'Olivia Montague',
    //   href: '/galerie/DetailImage/1',
    //   imageSrc: Image1,
    //   imageAlt: 'Tall slender porcelain bottle with natural clay textured body and cork stopper.',
    //   tags: ['Drawing']
    // },
    // {
    //   id: 2,
    //   price: 20,
    //   name: 'Sebastian Wolfe',
    //   href: '/galerie/DetailImage/2',
    //   imageSrc: Image2,
    //   imageAlt: 'Olive drab green insulated bottle with flared screw lid and flat top.',
    //   tags: ['Drawing']
    // },
    // {
    //   id: 3,
    //   price: 20,
    //   name: 'Isabella Marchand',
    //   href: '/galerie/DetailImage/3',
    //   imageSrc: Image3,
    //   imageAlt: 'Person using a pen to cross a task off a productivity paper card.',
    //   tags: ['ceramic', 'sculpture']
    // },
    // {
    //   id: 4,
    //   price: 20,
    //   name: 'Alexander Hartmann',
    //   href: '/galerie/DetailImage/4',
    //   imageSrc: Image4,
    //   imageAlt: 'Hand holding black machined steel mechanical pencil with brass tip and top.',
    //   tags: ['Photography']
    // },
    // {
    //   id: 5,
    //   price: 20,
    //   name: 'Victoria Lefevre',
    //   href: '/galerie/DetailImage/5',
    //   imageSrc: Image5,
    //   imageAlt: 'Hand holding black machined steel mechanical pencil with brass tip and top.',
    //   tags: ['Painting']
    // },
    // {
    //   id: 6,
    //   price: 20,
    //   name: 'Gabriel Delacroix',
    //   href: '/galerie/DetailImage/6',
    //   imageSrc: Image6,
    //   imageAlt: 'Hand holding black machined steel mechanical pencil with brass tip and top.',
    //   tags: ['Photography']
    // },
    // {
    //   id: 7,
    //   price: 20,
    //   name: 'Sophia Davenport',
    //   href: '/galerie/DetailImage/7',
    //   imageSrc: Image7,
    //   imageAlt: 'Hand holding black machined steel mechanical pencil with brass tip and top.',
    //   tags: ['glass', 'bottle']
    // },
    // {
    //   id: 8,
    //   price: 20,
    //   name: 'Lucas Beaumont',
    //   href: '/galerie/DetailImage/8',
    //   imageSrc: Image8,
    //   imageAlt: 'Hand holding black machined steel mechanical pencil with brass tip and top.',
    //   tags: ['glass', 'bottle']
    // },
    // {
    //   id: 9,
    //   price: 20,
    //   name: 'Amelia Rousseau',
    //   href: '/galerie/DetailImage/9',
    //   imageSrc: Image9,
    //   imageAlt: 'Hand holding black machined steel mechanical pencil with brass tip and top.',
    //   tags: ['Photography']
    // },
    // More products...
        {
            "id": "649ab1637af2d57328bb0cc7",
            "title": "Amelia Rousseau",
            "images": [Image1],
            "description": null,
            "yearOfCreation": null,
            "materials": null,
            "dimension": null,
            "price": 20,
            "location": null,
            "createdAt": "2023-06-27T09:52:35.565+00:00",
            "comments": null,
            "ownerId": "6499f827716de01a654c37ac",
            "galleryId": "649a91377af2d57328bb0ca5",
            "ownerName": "user user",
            "galleryTitle": "title3",
            "likes": 0,
            "tags": ['Drawing']
        },
        {
            "id": "649ab1637af2d57328bb0cc7",
            "title": "Lucas Beaumont",
            "images": [Image2],
            "description": null,
            "yearOfCreation": null,
            "materials": null,
            "dimension": null,
            "price": 20,
            "location": null,
            "createdAt": "2023-06-27T09:52:35.565+00:00",
            "comments": null,
            "ownerId": "6499f827716de01a654c37ac",
            "galleryId": "649a91377af2d57328bb0ca5",
            "ownerName": "user user",
            "galleryTitle": "title3",
            "likes": 0,
            "tags": ['Drawing']
        },
        {
            "id": "649ab1637af2d57328bb0cc7",
            "title": "Sophia Davenport",
            "images": [Image3],
            "description": null,
            "yearOfCreation": null,
            "materials": null,
            "dimension": null,
            "price": 20,
            "location": null,
            "createdAt": "2023-06-27T09:52:35.565+00:00",
            "comments": null,
            "ownerId": "6499f827716de01a654c37ac",
            "galleryId": "649a91377af2d57328bb0ca5",
            "ownerName": "user user",
            "galleryTitle": "title3",
            "likes": 0,
            "tags": ['Photography']
        },
        {
            "id": "649ab1637af2d57328bb0cc7",
            "title": "Gabriel Delacroix",
            "images": [Image4],
            "description": null,
            "yearOfCreation": null,
            "materials": null,
            "dimension": null,
            "price": 20,
            "location": null,
            "createdAt": "2023-06-27T09:52:35.565+00:00",
            "comments": null,
            "ownerId": "6499f827716de01a654c37ac",
            "galleryId": "649a91377af2d57328bb0ca5",
            "ownerName": "user user",
            "galleryTitle": "title3",
            "likes": 0,
            "tags": ['Photography']
        },
        {
            "id": "649ab1637af2d57328bb0cc7",
            "title": "Olivia Montague",
            "images": [Image5],
            "description": null,
            "yearOfCreation": null,
            "materials": null,
            "dimension": null,
            "price": 20,
            "location": null,
            "createdAt": "2023-06-27T09:52:35.565+00:00",
            "comments": null,
            "ownerId": "6499f827716de01a654c37ac",
            "galleryId": "649a91377af2d57328bb0ca5",
            "ownerName": "user user",
            "galleryTitle": "title3",
            "likes": 0,
            "tags": ['Painting']
        },
        {
            "id": "649ab1637af2d57328bb0cc7",
            "title": "titleArtwork",
            "images": [Image6],
            "description": null,
            "yearOfCreation": null,
            "materials": null,
            "dimension": null,
            "price": 20,
            "location": null,
            "createdAt": "2023-06-27T09:52:35.565+00:00",
            "comments": null,
            "ownerId": "6499f827716de01a654c37ac",
            "galleryId": "649a91377af2d57328bb0ca5",
            "ownerName": "user user",
            "galleryTitle": "title3",
            "likes": 0,
            "tags": ['Photography']
        }
  ]

const profile = {
    photography:'https://images.unsplash.com/photo-1471341971476-ae15ff5dd4ea?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=3132&q=80',
    videography:'https://images.unsplash.com/photo-1535540878298-a155c6d065ef?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1770&q=80',
    marketing:'https://images.unsplash.com/photo-1568992687947-868a62a9f521?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=2064&q=80',
  }

const Galerie3 = () => {
  const [filteredArtworks, setFilteredArtworks] = useState([]);
  const [selectedTag, setSelectedTag] = useState('');
  const [searchTerm, setSearchTerm] = useState('');
  const [searchResults, setSearchResults] = useState([]);
  const [showCommandPalette, setShowCommandPalette] = useState(false);

  useEffect(() => {
    filterArtworks();
  }, [selectedTag, searchTerm]);

  const filterArtworks = () => {
    let filtered = artworks;

    if (selectedTag !== '') {
      filtered = filtered.filter((artwork) =>
        artwork.tags.includes(selectedTag)
      );
    }

    if (searchTerm !== '') {
      filtered = filtered.filter((artwork) =>
        artwork.title.toLowerCase().includes(searchTerm.toLowerCase())
      );
      setShowCommandPalette(true);
      setSearchResults(filtered.slice(0, 3).map((artwork) => artwork.title));
    } else {
      setShowCommandPalette(false);
    }

    setFilteredArtworks(filtered);
  };

  const handleTagFilter = (tag) => {
    setSelectedTag(tag);
  };

  const handleSearch = (searchTerm) => {
    setSearchTerm(searchTerm);
  };

  const handleCommandPaletteItemClick = (item) => {
    setSearchTerm(item);
    setShowCommandPalette(false);
  };

  return (
    <div className='container'>
         <div className='mt-12'>
             <Banner img={profile.marketing} />
         </div>

      <h1 className='mb-7 mt-7'>Browse the world of ArtConnect</h1>

      <Searchbar onSearch={handleSearch} />

      {showCommandPalette && (
        <ul className="command-palette">
          {searchResults.map((result, index) => (
            <li
              key={index}
              onClick={() => handleCommandPaletteItemClick(result)}
            >
              {result}
            </li>
          ))}
        </ul>
      )}


     <div className="tag-buttons mt-7 mb-7">
          <button
          onClick={() => handleTagFilter('')}
          className={`tag-button ${selectedTag === '' ? 'tag-button-active' : ''}`}
        >
          All
        </button>
        <button
          onClick={() => handleTagFilter('Painting')}
          className={`tag-button ${selectedTag === 'Painting' ? 'tag-button-active' : ''}`}
        >
          Painting
        </button>
        <button
          onClick={() => handleTagFilter('Photography')}
          className={`tag-button ${selectedTag === 'Photography' ? 'tag-button-active' : ''}`}
        >
          Photography
        </button>
        <button
          onClick={() => handleTagFilter('Drawing')}
          className={`tag-button ${selectedTag === 'Drawing' ? 'tag-button-active' : ''}`}
        >
          Drawing
        </button>
      </div>

        <div className="grid grid-cols-1 gap-x-6 gap-y-10 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 xl:gap-x-8">
        {filteredArtworks.map((artwork) => (
            <>
            <Link to={`/galerie/DetailImage/${artwork.id}`}>
                <div className="aspect-h-1 aspect-w-1 w-full overflow-hidden rounded-lg bg-gray-200 xl:aspect-h-8 xl:aspect-w-7">
                <img
                    src={artwork.images}
                    alt="{product.imageAlt}"
                    className="h-full w-full object-cover object-center group-hover:opacity-75"
                />
                </div>
                <h3 className="link mt-4 text-sm text-gray-700">{artwork.title}</h3>
                <div className='pl-64'>
                    <img
                        className="inline-block h-10 w-10 rounded-full"
                        src="https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=facearea&facepad=2&w=256&h=256&q=80"
                        alt=""
                    />
                </div>
                <p className="link mt-1 text-lg font-medium text-gray-900">{artwork.description}</p>
                <p className="link mt-1 text-lg font-medium text-gray-900">{artwork.price} Euro</p>
            </Link>
            
            </>
        ))}
      </div>
      
    </div>
  )
}

export default Galerie3;

