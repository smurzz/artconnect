import React, {useState} from 'react';
import {MenuBar} from './components/MenuBar';
import {ImageSlider} from './components/ImageSlider';
import '../layout/css/homePublic.css';
import HomePrivate from './HomePrivate';
import Footer from './components/Footer';

//Images
import ImageArtHome from "../images/homePublic/pensilArtwork.png"
import ImagePencil from "../images/homePublic/ArtworkHome.png"
import Ronny from "../images/homePublic/ronny.png"
import Mona from "../images/homePublic/mona.png"
import Komola from "../images/homePublic/komola.png"
import Oezkan from "../images/homePublic/oezkan.png"
import Sofya from "../images/homePublic/sofya.png"
import Aaron from "../images/homePublic/aaron.png"
import Image1 from '../images/slider/picture1.jpg';
import Image2 from '../images/slider/picture2.jpg';
import Image3 from '../images/slider/picture3.jpg';
import {Carousel} from 'react-responsive-carousel';
import 'react-responsive-carousel/lib/styles/carousel.min.css';
import "./homePublic.css"

function HomePublic() {

    var userSession = JSON.parse(localStorage.getItem('userSession'));

    return (
        !userSession ? <div className="home-public-container">
            <MenuBar/>
            {/*slider*/}
            <div className="bg-white">
                <Carousel className="carousel-inner"
                          showThumbs={false}
                          showStatus={false}
                          infiniteLoop={true}
                          autoPlay={true}
                          interval={5000}
                >
                    <div className="carouselItem">
                        <div className="container">
                            <div
                                className="slider__text d-flex flex-column justify-content-start align-items-start container__color m-4">
                                <h1 >CREATE
                                    ART</h1>
                                <p className="lead fw-normal text-white textLeft">
                                    "They sit for hours in cafés, talking incessantly about culture, art, revolution,
                                    and so on and so forth, poisoning the air with theories upon theories that never
                                    come true."
                                    Are you like Frida Kahlo and prefer to create art rather than rationalize it with
                                    words?
                                    Then upload your works for free on our platform. </p>
                            </div>
                        </div>
                        <img className="d-block w-100 h-100 overflow-hidden" src={Image1} alt="First slide"/>
                    </div>

                    <div className="carouselItem">
                        <div className="container">
                            <div
                                className="slider__text d-flex flex-column justify-content-start align-items-start  container__color m-4">
                                <h1 className="text-4xl font-bold tracking-tight text-gray-900 sm:text-6xl color ">SUPPORT
                                    ART</h1>
                                <p className="lead fw-normal text-white textLeft">
                                    "Art does not reproduce the visible, but rather makes visible." This was the motto
                                    of Paul Klee, which inspired his theory of form and color. Art makes things visible,
                                    and artists need to be made visible. You can support us by giving their artworks a
                                    new home.
                                </p>
                            </div>
                        </div>
                        <img className="d-block w-100 h-100 overflow-hidden" src={Image2} alt="First slide"/>
                    </div>

                    <div className="carouselItem">
                        <div className="container">
                            <div
                                className="m-4 slider__text d-flex flex-column justify-content-start align-items-start container__color">
                                <h1 className="text-4xl font-bold tracking-tight text-gray-900 sm:text-6xl color">EXPERIENCE
                                    ART</h1>
                                <p className="lead fw-normal text-white textLeft">
                                    "The art is not the bread, but rather the wine of life." In line with Jean Paul's
                                    thought, more than 120
                                    artists are already showcasing their works on our website. Let yourself be inspired
                                    and take a glimpse into
                                    the world of art and its artists.
                                </p>
                            </div>
                        </div>
                        <img className="d-block w-100 h-100 overflow-hidden" src={Image3} alt="First slide"/>
                    </div>
                </Carousel>
            </div>
            <section className="py-5 bg-light" id="scroll-target">
                <div className="container px-5 my-5">
                    <div className="row gx-5 align-items-center">
                        <div className="col-lg-6"><img className="img-fluid rounded mb-5 mb-lg-0"
                                                       src={ImagePencil}/></div>
                        <div className="col-lg-6">
                            <h2 className="fw-bolder">Our Misson</h2>
                            <blockquote className="blockquote text-muted">
                                <p className="mb-0">Only 10 percent of all visual artists in Germany can make a living
                                    from
                                    their work. This is the sobering result of a survey conducted by Hergen Wöbken from
                                    the
                                    Institute for Strategic Development among artists in Berlin in 2018.</p>
                            </blockquote>
                            <p className="lead fw-normal text-muted mb-0">Thats why we made it our mission to give a
                                helping hand to artists by providing a platform where
                                they can display their artwork for free. We believe in supporting and promoting the
                                talents of artists,
                                and our platform aims to create opportunities for them to showcase their work to a wider
                                audience.</p>


                        </div>

                    </div>
                </div>
            </section>
            <section className="py-5 ">
                <div className="container px-5 my-5">
                    <div className="text-center">
                        <h2 className="fw-bolder">Our team</h2>
                        <p className="lead fw-normal text-muted mb-5">Dedicated to quality and your success</p>
                    </div>
                    <div className="row gx-5 row-cols-1 row-cols-sm-2 row-cols-xl-5 justify-content-center">
                        <div className="col mb-5 mb-5 mb-xl-0">
                            <div className="text-center">
                                <img className="img-fluid rounded-circle mb-4 px-2"
                                     src={Ronny} alt="..."/>
                                <h5 className="fw-bolder">Ronny Schmitz</h5>
                                <div className="fst-italic text-muted">Project Manager</div>
                            </div>
                        </div>
                        <div className="col mb-5 mb-5 mb-xl-0">
                            <div className="text-center">
                                <img className="img-fluid rounded-circle mb-4 px-2"
                                     src={Aaron} alt="..."/>
                                <h5 className="fw-bolder">Aaron Engelmann</h5>
                                <div className="fst-italic text-muted">Web-Developer</div>
                            </div>
                        </div>
                        <div className="col mb-5 mb-5 mb-xl-0">
                            <div className="text-center">
                                <img className="img-fluid rounded-circle mb-4 px-2"
                                     src={Oezkan} alt="..."/>
                                <h5 className="fw-bolder">Özkan Kizilkan</h5>
                                <div className="fst-italic text-muted">Software Developer</div>
                            </div>
                        </div>
                        <div className="col mb-5 mb-5 mb-xl-0">
                            <div className="text-center">
                                <img className="img-fluid rounded-circle mb-4 px-2"
                                     src={Sofya} alt="..."/>
                                <h5 className="fw-bolder">Sofya</h5>
                                <div className="fst-italic text-muted">Sofya Murzakova</div>
                            </div>
                        </div>
                        <div className="col mb-5 mb-5 mb-xl-0">
                            <div className="text-center">
                                <img className="img-fluid rounded-circle mb-4 px-2"
                                     src={Mona} alt="..."/>
                                <h5 className="fw-bolder">Mona Becher</h5>
                                <div className="fst-italic text-muted">Project Manager</div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
            <Footer/>
        </div> : <HomePrivate/>
    );
}

export default HomePublic;