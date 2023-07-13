import * as React from 'react';
import AppBar from '@mui/material/AppBar';
import Button from '@mui/material/Button';
import CameraIcon from '@mui/icons-material/PhotoCamera';
import Card from '@mui/material/Card';
import CardActions from '@mui/material/CardActions';
import CardContent from '@mui/material/CardContent';
import CardMedia from '@mui/material/CardMedia';
import CssBaseline from '@mui/material/CssBaseline';
import Grid from '@mui/material/Grid';
import Stack from '@mui/material/Stack';
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import Container from '@mui/material/Container';
import {createTheme, ThemeProvider} from '@mui/material/styles';
import {StarIcon} from '@heroicons/react/20/solid';
import Image1 from "../../../images/defaultArtworkPlaceholder.png"
import HeaderLogedIn from "../../../components/headerComponent/headerLogedIn";
import HeaderLogedOut from "../../../components/headerComponent/headerLogout";
import {useEffect} from "react";
import {logikService} from "../../../lib/service"
import {GalerieApiService} from "../../../lib/apiGalerie"
import {useNavigate, Link, useParams} from "react-router-dom";


const cards = [1, 2, 3, 4, 5, 6, 7, 8, 9];

// TODO remove, this demo shouldn't need to reset the theme.
const defaultTheme = createTheme();
function classNames(...classes) {
    return classes.filter(Boolean).join(' ')
}
export default function AlbumArtWork() {
    const [isLoggedIn, setIsLoggedIn] = React.useState(false);
    const [galleries, setGalleries] = React.useState([]);
    const [artworks, setArtworks] = React.useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        async function getUserData() {
            //loggedIn logik
            const loggedInHeader = await logikService.isLoggedIn();
            const resultGetArtworks =await GalerieApiService.getUnsecuredData("/artworks");
            setArtworks(resultGetArtworks.data)
            setIsLoggedIn(loggedInHeader)
            console.log("Artworkgalleries: "+ JSON.stringify(resultGetArtworks.data))
        }

        getUserData()
    }, []);

    function convertImage(data) {
        if (data) {
            const byteCharacters = atob(data);
            const byteNumbers = new Array(byteCharacters.length);
            for (let i = 0; i < byteCharacters.length; i++) {
                byteNumbers[i] = byteCharacters.charCodeAt(i);
            }
            const byteArray = new Uint8Array(byteNumbers);
            // Create URL for the binary image data
            const blob = new Blob([byteArray], {type: 'image/png'}); // Adjust the 'type' according to the actual image format
            const url = URL.createObjectURL(blob);
            return url;

            //Blank Picture
        }
    }

    return (<>
            {isLoggedIn ? <HeaderLogedIn/> : <HeaderLogedOut/>}
        <ThemeProvider theme={defaultTheme}>
                <CssBaseline/>
                <main>

                    <Box
                        sx={{
                            bgcolor: 'background.paper'
                        }}
                    >
                        <Container maxWidth="sm">
                            <div className="container mt-20">
                                <div className="row justify-content-center ">
                                    <div className="col-12 col-sm-10 col-md-8 col-lg-6">
                                        <h3>Artworks</h3>
                                    </div>
                                </div>
                            </div>
                            <p className="text-base font-semibold text-black">
                                Our hosted Artworks showcase the breathtaking creations of talented artists, immersing you in a symphony of colors, shapes, and emotions.
                            </p>
                        </Container>
                    </Box>
                    <Container sx={{py: 8}} maxWidth="md">

                        <Grid container spacing={4}>
                            {artworks?.map((card) => (
                                <Grid item key={card} xs={12} sm={6} md={4}>
                                    <Card
                                        sx={{height: '100%', display: 'flex', flexDirection: 'column'}}
                                    >
                                        <img
                                            src={card.images[0]?.image.data ? convertImage(card.artworks[0]?.images[0]?.image.data) : Image1}
                                            alt="{product.imageAlt}"
                                            className="h-full w-full object-cover object-center group-hover:opacity-75"
                                        />
                                        <CardContent sx={{flexGrow: 1}}>
                                            <Typography gutterBottom variant="h5" component="h2">
                                                {card.title}
                                            </Typography>
                                            {card.tags?.map((tag) => (
                                                <span className="tag tag-sm">#{tag}</span>
                                            ))}
                                            <p className="link mt-1 text-lg font-medium text-gray-900">{card.price} Euro</p>
                                        </CardContent>
                                        <CardActions>
                                            <Button onClick={()=>{
                                                navigate("/openArtwork/"+ card.id)
                                            }}

                                                    size="small">View</Button>
                                        </CardActions>
                                    </Card>
                                </Grid>
                            ))}
                        </Grid>
                    </Container>
                </main>
            </ThemeProvider> */}
        </>
    );
}
