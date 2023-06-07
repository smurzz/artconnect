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
            img: "https://oli-baustoffe-images.s3.eu-central-1.amazonaws.com/Olibaustoffe_bilder/DJI_0005.JPG",
            width: 180,
            height: 100,
        },
        {
            img: "https://oli-baustoffe-images.s3.eu-central-1.amazonaws.com/Olibaustoffe_bilder/DJI_0006.JPG",
            width: 180,
            height: 100,
        },
        {
            img: "https://oli-baustoffe-images.s3.eu-central-1.amazonaws.com/Olibaustoffe_bilder/DJI_0007.JPG",
            width: 180,
            height: 100,
        },
        {
            img: "https://oli-baustoffe-images.s3.eu-central-1.amazonaws.com/Olibaustoffe_bilder/DJI_0008.JPG",
            width: 180,
            height: 100,
        },
        {
            img: "https://oli-baustoffe-images.s3.eu-central-1.amazonaws.com/Olibaustoffe_bilder/DJI_0013.JPG",
            width: 180,
            height: 100,
        },
        {
            img: "https://oli-baustoffe-images.s3.eu-central-1.amazonaws.com/Olibaustoffe_bilder/DJI_0015.JPG",
            width: 180,
            height: 100,
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
                                <img src={slide.img} alt='test' class="card-img-top"  />
                                <div class="card-body">
                                    <h5 class="card-title">Card title</h5>
                                    <p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>
                                    <a href="#" class="btn btn-primary">Go somewhere</a>
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