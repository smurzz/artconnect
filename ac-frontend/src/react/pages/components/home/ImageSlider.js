import Carousel from 'react-bootstrap/Carousel';

import Image1 from '../../../images/slider/picture1.jpg';
import Image2 from '../../../images/slider/picture2.jpg';
import Image3 from '../../../images/slider/picture3.jpg';
import Image4 from '../../../images/slider/picture4.jpg';
import Image5 from '../../../images/slider/picture5.jpg';
import '../../../layout/css/homePublic.css';

export function ImageSlider() {
    const caption =
        (<Carousel.Caption>
            <h1>Unleash Your Artistic Journey: Connect, Showcase, Inspire.</h1>
            <h5>Join our ArtConnect Community: Where thousands of 
                artists from around the world are already connecting and showcasing their talent.</h5>
        </Carousel.Caption>);

    return (
        <div className="image-slider-container">
            <Carousel className='h-100'>
                <Carousel.Item interval={2500}>
                    <div
                        className="carousel-background-image"
                        style={{ backgroundImage: `url(${Image1})` }}
                    ></div>
                   {caption}
                </Carousel.Item>
                <Carousel.Item interval={2500}>
                    <div
                        className="carousel-background-image"
                        style={{ backgroundImage: `url(${Image2})` }}
                    ></div>
                    {caption}
                </Carousel.Item>
                <Carousel.Item interval={2500}>
                    <div
                        className="carousel-background-image"
                        style={{ backgroundImage: `url(${Image3})` }}
                    ></div>
                   {caption}
                </Carousel.Item>
                <Carousel.Item interval={2500} className='carousel-item'>
                    <div
                        className="carousel-background-image"
                        style={{ backgroundImage: `url(${Image4})` }}
                    ></div>
                    {caption}
                </Carousel.Item>
                <Carousel.Item interval={2500}>
                    <div
                        className="carousel-background-image"
                        style={{ backgroundImage: `url(${Image5})` }}
                    ></div>
                    {caption}
                </Carousel.Item>
            </Carousel>
        </div>
    );
}