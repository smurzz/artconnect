import React from 'react'
import "./home.css"

import { Carousel } from 'react-responsive-carousel';
import 'react-responsive-carousel/lib/styles/carousel.min.css';
import Header from "../../components/headerLogout/header"

import Image1 from './slide_Images/matilda2.jpg';
import Image2 from './slide_Images/matilda3.jpg';
import Image3 from './slide_Images/picture1.jpg';
import Image4 from './slide_Images/picture2.jpg';
import Image5 from './slide_Images/picture3.jpg';
import Image6 from './slide_Images/picture4.jpg';
import Image7 from './slide_Images/picture5.jpg';

const home = () => {
  return (
    <div className="home-container">
      <div className="slider-container">
        <h1 className="heading_primary txt_set">Welcome to ArtConnect</h1>
        <div className="slider">
          <Carousel className="slider"
            showThumbs={false}
            showStatus={false}
            infiniteLoop={true}
            autoPlay={true}
            interval={5000}
          >
            {/* */}
            <div>
              <img className='slide-image float' src={Image1} alt="matilda_2" />
            </div>
            <div>
              <img className='slide-image float' src={Image2} alt="matild_3" />
            </div>
            <div>
              <img className='slide-image float' src={Image3} alt="picture1" />
            </div>
            <div>
              <img className='slide-image float' src={Image4} alt="picture2" />
            </div>
            <div>
              <img className='slide-image float' src={Image5} alt="picture3" />
            </div>
            <div>
              <img className="slide-image float" src={Image6} alt="picture4" />
            </div>
            <div>
              <img className='slide-image float' src={Image7} alt="picture5" />
            </div>

          </Carousel>
        </div>
      </div>
    </div>
  )
}

export default home