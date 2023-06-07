import React from 'react'
import "./home.css"

import { Carousel } from 'react-responsive-carousel';
import 'react-responsive-carousel/lib/styles/carousel.min.css';
import './home.css';
import Header from "../../components/headerLogout/header"

const home = () => {
  return (
    <html>
    <Header/>
    <main>

    <article >
      <header className='container center-text'>
        <h1 className='heading_primary'>Willkomen bei ArtConnect</h1> 
      </header>
      <section>
    
      <div >
      <Carousel
        showThumbs={false}
        showStatus={false}
        infiniteLoop={true}
        autoPlay={true}
        interval={5000}
      >
        <div>
        <img  src="slide_Images\matilda1.jpg" alt="matilda_1" width={500} height={750}/>
         {/*<p className="legend">Image 1</p>*/}
        </div>
        <div>
          <img  src="slide_Images\matilda2.jpg" alt="matilda2" width={500} height={750}/>
           {/*<p className="legend">Image 2</p>*/}
        </div>
        <div>
          <img  src="slide_Images\matilda3.jpg" alt="matilda3" width={500} height={750} />
          {/*<p className="legend">Image 3</p>*/}
        </div>
        <div>
          <img  src="slide_Images\matilda4.jpg" alt="matilda3" width={500} height={750}/>
         {/*<p className="legend">Image 4</p>*/}
        </div>
      </Carousel>
    </div>


{/* 
<div id="carouselExampleIndicators" class="carousel slide">
  <div class="carousel-indicators">
    <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="0" class="active" aria-current="true" aria-label="Slide 1"></button>
    <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="1" aria-label="Slide 2"></button>
    <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="2" aria-label="Slide 3"></button>
  </div>
  <div class="carousel-inner center-text">
    <div class="carousel-item active">
      <img src="slide_Images\matilda1.jpg" class="d-block w-50 center-text" alt="..."/>
    </div>
    <div class="carousel-item center-text">
      <img src="slide_Images\matilda2.jpg" class="d-block w-50 center-text" alt="..."/>
    </div>
    <div class="carousel-item center-text">
      <img src="slide_Images\matilda3.jpg" class="d-block w-50 center-text" alt="..."/>
    </div>
  </div>
  <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide="prev">
    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
    <span class="visually-hidden">Previous</span>
  </button>
  <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide="next">
    <span class="carousel-control-next-icon" aria-hidden="true"></span>
    <span class="visually-hidden">Next</span>
  </button>
</div>
  */}
      </section>
      <footer>

      </footer>
    </article>

    </main>
    </html>
   

  )
}

export default home