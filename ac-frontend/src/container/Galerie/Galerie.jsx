import React from 'react'

import {AiOutlineArrowLeft} from 'react-icons/ai';
import {AiOutlineArrowRight} from 'react-icons/ai';
import {GrClose} from 'react-icons/gr';
import { useState } from 'react';
import Header from '../../components/header/header'
import './Galerie.css';

const Galerie = () => {

    const GalleryImages = [
        {
            // img: Images.dachbaustoffe
            img: "img\\Rectangleabstract.png",
            width: 180,
            height: 100,
            titel: "Capturing the Essence",
            text: "With each brushstroke, I danced between illumination and obscurity, seeking to unveil the hidden depths of existence."
        },
        {
            img: "https://oli-baustoffe-images.s3.eu-central-1.amazonaws.com/Olibaustoffe_bilder/DJI_0006.JPG",
            width: 180,
            height: 100,
            titel: "Embracing Colors",
            text: "Splashing pigments onto the canvas, I immersed myself in a symphony of hues, allowing the colors to sing harmoniously and ignite joy."
        },
        {
            img: "https://oli-baustoffe-images.s3.eu-central-1.amazonaws.com/Olibaustoffe_bilder/DJI_0007.JPG",
            width: 180,
            height: 100,
            titel: "Beyond the Surface",
            text: "Peering beyond the external facade, I delved into the soul's intricate labyrinth, painting the intangible emotions that lie beneath."

        },
        {
            img: "https://oli-baustoffe-images.s3.eu-central-1.amazonaws.com/Olibaustoffe_bilder/DJI_0008.JPG",
            width: 180,
            height: 100,
            titel: "In Motion",
            text: "As my hand raced across the canvas, I captured fleeting moments, immortalizing the raw emotions that pulsated through every stroke."
        },
        {
            img: "https://oli-baustoffe-images.s3.eu-central-1.amazonaws.com/Olibaustoffe_bilder/DJI_0013.JPG",
            width: 180,
            height: 100,
            titel: "Whispers of Nature",
            text: "Amidst tranquil landscapes, I breathed in the earth's whispers, translating its serenity onto the canvas, a testament to nature's eternal embrace."
        },
        {
            img: "https://oli-baustoffe-images.s3.eu-central-1.amazonaws.com/Olibaustoffe_bilder/DJI_0015.JPG",
            width: 180,
            height: 100,
            titel: "The Human Canvas",
            text: "Gazing into the eyes of my subjects, I beheld a mosaic of tales etched upon their faces, and with each stroke, I unraveled their untold narratives."
        },
    ]

    const [slideNumber, setSlideNumber] = useState(0)
    const [openModal, setOpenModal] = useState(false)

    const handleOpenModal = (index) => {
        setSlideNumber(index)
        setOpenModal(true)
    }

    const handleCloseModal = () => {
        setOpenModal(false);
    }

    const prevSlide = () => {
        slideNumber === 0 
        ? setSlideNumber(GalleryImages.length - 1 ) 
        : setSlideNumber( slideNumber - 1 )
    }  

    const nextSlide  = () => {
        slideNumber + 1 === GalleryImages.length 
        ? setSlideNumber(0)
        : setSlideNumber( slideNumber + 1 )
    }

  return (
    <>
        <Header/>
        <div className='container center-text margin-bottom-md'>
            <h1 className='heading_primary'>Galerie</h1>
            {openModal && 
                    <div className='sliderWrap'>
                        <GrClose className='btnClose' onClick={handleCloseModal} />
                        <AiOutlineArrowLeft className='btnPrev' onClick={prevSlide}/>
                        <AiOutlineArrowRight className='btnNext' onClick={nextSlide}/>
                        <div className='fullScreenImage'>
                            <img src={GalleryImages[slideNumber].img} alt='test'width={520} height={320} />
                        </div>
                    </div>
                }
                <div className={"container grid grid--3-cols margin-bottom-md galleryWrap"}>
                    {GalleryImages.map((slide, index) => {
                        return(
                            <div 
                                className='single card' 
                                key={index}
                                onClick={()=>handleOpenModal(index)}>
                                <img src="imgSlides\matilda1.jpg" alt='test' class="card-img-top"  />
                                <div class="card-body">
                                    <h5 class="card-title">{slide.titel}</h5>
                                    <p class="card-text">{slide.text}</p>
                                    <a href="#" class="btn btn-primary">View</a>
                                </div>
                            </div>
                        )
                    })}
                </div>
        </div>
    </>
  )
}
export default Galerie;