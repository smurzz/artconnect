import * as React from 'react';
import { Link, useNavigate } from "react-router-dom";

import "./LandingPage.css";
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap/dist/js/bootstrap.bundle.min";

//allgemein Material
import Typography from '@mui/material/Typography';
import {useTheme} from '@mui/material/styles';
import {styled} from '@mui/material/styles';

//Carousel
import {Carousel} from 'react-responsive-carousel';
import 'react-responsive-carousel/lib/styles/carousel.min.css';
import Avatar from '@mui/material/Avatar';
//Cards
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import CardMedia from '@mui/material/CardMedia';
import CardHeader from '@mui/material/CardHeader'
import CardActions from '@mui/material/CardActions';
import Collapse from '@mui/material/Collapse';
import IconButton, {IconButtonProps} from '@mui/material/IconButton';
import FavoriteIcon from '@mui/icons-material/Favorite';
import ShareIcon from '@mui/icons-material/Share';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import MoreVertIcon from '@mui/icons-material/MoreVert';

const FORGET_URL = "/Home";
// TODO remove, this demo shouldn't need to reset the theme.
const data = [
    {
        image: require("./Home/slide_Images/picture3.jpg"),
        caption: "Caption",
        description: "Description Here"
    },
    {
        image: require("./Home/slide_Images/picture2.jpg"),
        caption: "Caption",
        description: "Description Here"
    },
    {
        image: require("./Home/slide_Images/picture4.jpg"),
        caption: "Caption",
        description: "Description Here"
    },
    {
        image: require("../images/artists1.png"),
        caption: "Caption",
        description: "Description Here"
    },
    {
        image: require("../images/artists2.png"),
        caption: "Caption",
        description: "Description Here"
    },
    {
        image: require("../images/artists3.png"),
        caption: "Caption",
        description: "Description Here"
    },

    {
        image: require("../images/sliderOne.png"),
        caption: "Caption",
        description: "Description Here"
    },
    {
        image: require("../images/sliderTwo.png"),
        caption: "Caption",
        description: "Description Here"
    },
    {
        image: require("../images/sliderThree.png"),
        caption: "Caption",
        description: "Description Here"
    }

]

interface
ExpandMoreProps
extends
IconButtonProps
{
    expand: boolean;
}

const ExpandMore = styled((props: ExpandMoreProps) => {
    const {expand, ...other} = props;
    return <IconButton {...other} />;
})(({theme, expand}) => ({
    transform: !expand ? 'rotate(0deg)' : 'rotate(180deg)',
    marginLeft: 'auto',
    transition: theme.transitions.create('transform', {
        duration: theme.transitions.duration.shortest,
    }),
}));

function AuthButton() {
    const navigate = useNavigate();
    const handleNav = (nav) => {
        if (nav == "reg") {
            navigate("/register");
        } else {
            navigate("/login");
        }
    }
    return (
        <>
        <button type="button" className="btn btn-dark mr-3 loginButton" onClick={() => {
            handleNav()
        }}>Login</button>
    <button type="button" className="btn btn-dark" onClick={() => {
        handleNav("reg")
    }}>Register</button>
        </>
)
}

export default function Landingpage() {
    const [expanded, setExpanded] = React.useState(false);

    const handleExpandClick = () => {
        setExpanded(!expanded);
    };
    const theme = useTheme();

    return (
        <>
            <div>
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
                                className="slider__text d-flex flex-column justify-content-start align-items-start container__color">
                                <h1 className="h1__thin textLeft">CREATE ART</h1>
                                <p className="text__body textLeft">
                                    "They sit for hours in cafés, talking incessantly about culture, art, revolution,
                                    and so on and so forth, poisoning the air with theories upon theories that never
                                    come true."
                                    Are you like Frida Kahlo and prefer to create art rather than rationalize it with
                                    words?
                                    Then upload your works for free on our platform. </p>
                                <div>
                                    <AuthButton/>
                                </div>
                            </div>
                        </div>
                        <img className="d-block w-100 h-100 overflow-hidden" src={data[6].image} alt="First slide"/>
                    </div>

                    <div className="carouselItem">
                        <div className="container">
                            <div
                                className="slider__text d-flex flex-column justify-content-start align-items-start  container__color">
                                <h1 className="h1__thin textLeft">SUPPORT ART</h1>
                                <p className="text__body textLeft">
                                    "Art does not reproduce the visible, but rather makes visible." This was the motto
                                    of Paul Klee, which inspired his theory of form and color. Art makes things visible,
                                    and artists need to be made visible. You can support us by giving their artworks a
                                    new home.
                                </p>
                                <div>
                                    <AuthButton/>
                                </div>
                            </div>
                        </div>
                        <img className="d-block w-100 h-100 overflow-hidden" src={data[7].image} alt="First slide"/>
                    </div>

                    <div className="carouselItem">
                        <div className="container">
                            <div
                                className="slider__text d-flex flex-column justify-content-start align-items-start  container__color">
                                <h1 className="h1__thin textLeft">EXPERIENCE ART</h1>
                                <p className="text__body textLeft">
                                    "The art is not the bread, but rather the wine of life." In line with Jean Paul's
                                    thought, more than 120
                                    artists are already showcasing their works on our website. Let yourself be inspired
                                    and take a glimpse into
                                    the world of art and its artists.
                                </p>
                                <div>
                                    <AuthButton/>
                                </div>
                            </div>
                        </div>
                        <img className="d-block w-100 h-100 overflow-hidden" src={data[8].image} alt="First slide"/>
                    </div>
                </Carousel>
            </div>


            <div className="container">
                <div className="container">
                    <p className="h3__thin marginBetweenContainer">Favourits of the Week!</p>
                    <p className="text__regular">Step into our Galerie and ignite your imagination with the exquisite
                        craftsmanship of our extraordinary artists!</p>
                </div>
                <div className="flexResponsive">
                    <Card className="marginBetweenComponentes container" sx={{maxWidth: "22em"}}>
                        <CardHeader
                            avatar={
                                <Avatar aria-label="recipe">
                                    <img src={data[3].image}/>
                                </Avatar>
                            }
                            action={
                                <IconButton aria-label="settings">
                                    <MoreVertIcon/>
                                </IconButton>
                            }
                            title="Tiffany Emmens"
                            subheader="September 14, 2016"
                        />
                        <CardMedia className="cardImage"
                                   component="img"
                                   height="194"
                                   image={data[0].image}
                                   alt="Paella dish"
                        />
                        <CardActions disableSpacing>
                            <IconButton aria-label="add to favorites">
                                <FavoriteIcon/>
                            </IconButton>
                            <ExpandMore
                                expand={expanded}
                                onClick={handleExpandClick}
                                aria-expanded={expanded}
                                aria-label="show more"
                            >
                                <ExpandMoreIcon/>
                            </ExpandMore>
                        </CardActions>
                        <Collapse in={expanded} timeout="auto" unmountOnExit>
                            <CardContent>
                                <Typography paragraph>Eternal Serenity</Typography>
                                <Typography paragraph>
                                    "Eternal Serenity" is a captivating art piece that encapsulates the serene beauty of
                                    a tranquil
                                    landscape. With delicate brushstrokes and a soft color palette, the artist
                                    transports viewers to
                                    a tranquil oasis, where rolling hills meet a calm, reflective lake. The painting
                                    radiates a sense of
                                    stillness and harmony, inviting viewers to find solace in the gentle embrace of
                                    nature. "Eternal
                                    Serenity" serves as a visual sanctuary, reminding us of the timeless tranquility
                                    that exists beyond
                                    the chaos of everyday life.
                                </Typography>
                                <Typography>
                                    Set aside off of the heat to let rest for 10 minutes, and then serve.
                                </Typography>
                            </CardContent>
                        </Collapse>
                    </Card>
                    <Card className="marginBetweenComponentes container" sx={{maxWidth: "22em"}}>
                        <CardHeader
                            avatar={
                                <Avatar aria-label="recipe">
                                    <img src={data[4].image}/>
                                </Avatar>
                            }
                            action={
                                <IconButton aria-label="settings">
                                    <MoreVertIcon/>
                                </IconButton>
                            }
                            title="Tom Sayer"
                            subheader="September 14, 2016"
                        />
                        <CardMedia className="cardImage"
                                   component="img"
                                   height="194"
                                   image={data[0].image}
                                   alt="Paella dish"
                        />
                        <CardActions disableSpacing>
                            <IconButton aria-label="add to favorites">
                                <FavoriteIcon/>
                            </IconButton>
                            <IconButton aria-label="share">
                                <ShareIcon/>
                            </IconButton>
                            <ExpandMore
                                expand={expanded}
                                onClick={handleExpandClick}
                                aria-expanded={expanded}
                                aria-label="show more"
                            >
                                <ExpandMoreIcon/>
                            </ExpandMore>
                        </CardActions>
                        <Collapse in={expanded} timeout="auto" unmountOnExit>
                            <CardContent>
                                <Typography paragraph>Eternal Serenity</Typography>
                                <Typography paragraph>
                                    "Eternal Serenity" is a captivating art piece that encapsulates the serene beauty of
                                    a tranquil
                                    landscape. With delicate brushstrokes and a soft color palette, the artist
                                    transports viewers to
                                    a tranquil oasis, where rolling hills meet a calm, reflective lake. The painting
                                    radiates a sense of
                                    stillness and harmony, inviting viewers to find solace in the gentle embrace of
                                    nature. "Eternal
                                    Serenity" serves as a visual sanctuary, reminding us of the timeless tranquility
                                    that exists beyond
                                    the chaos of everyday life.
                                </Typography>
                                <Typography>
                                    Set aside off of the heat to let rest for 10 minutes, and then serve.
                                </Typography>
                            </CardContent>
                        </Collapse>
                    </Card>
                    <Card className="marginBetweenComponentes container" sx={{maxWidth: "22em"}}>
                        <CardHeader
                            avatar={
                                <Avatar aria-label="recipe">
                                    <img src={data[5].image}/>
                                </Avatar>
                            }
                            action={
                                <IconButton aria-label="settings">
                                    <MoreVertIcon/>
                                </IconButton>
                            }
                            title="Tammy Samsung"
                            subheader="September 14, 2016"
                        />
                        <CardMedia className="cardImage"
                                   component="img"
                                   height="194"
                                   image={data[0].image}
                                   alt="Paella dish"
                        />
                        <CardActions disableSpacing>
                            <IconButton aria-label="add to favorites">
                                <FavoriteIcon/>
                            </IconButton>
                            <IconButton aria-label="share">
                                <ShareIcon/>
                            </IconButton>
                            <ExpandMore
                                expand={expanded}
                                onClick={handleExpandClick}
                                aria-expanded={expanded}
                                aria-label="show more"
                            >
                                <ExpandMoreIcon/>
                            </ExpandMore>
                        </CardActions>
                        <Collapse in={expanded} timeout="auto" unmountOnExit>
                            <CardContent>
                                <Typography paragraph>Eternal Serenity</Typography>
                                <Typography paragraph>
                                    "Eternal Serenity" is a captivating art piece that encapsulates the serene beauty of
                                    a tranquil
                                    landscape. With delicate brushstrokes and a soft color palette, the artist
                                    transports viewers to
                                    a tranquil oasis, where rolling hills meet a calm, reflective lake. The painting
                                    radiates a sense of
                                    stillness and harmony, inviting viewers to find solace in the gentle embrace of
                                    nature. "Eternal
                                    Serenity" serves as a visual sanctuary, reminding us of the timeless tranquility
                                    that exists beyond
                                    the chaos of everyday life.
                                </Typography>
                                <Typography>
                                    Set aside off of the heat to let rest for 10 minutes, and then serve.
                                </Typography>
                            </CardContent>
                        </Collapse>
                    </Card>


                </div>
            </div>
        </>
    );
}