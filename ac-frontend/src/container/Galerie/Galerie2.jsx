import * as React from 'react';
import Button from '@mui/material/Button';
import Card from '@mui/material/Card';
import CardActions from '@mui/material/CardActions';
import CardContent from '@mui/material/CardContent';
import CardMedia from '@mui/material/CardMedia';
import Grid from '@mui/material/Grid';
import Stack from '@mui/material/Stack';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import Container from '@mui/material/Container';
import { createTheme, ThemeProvider } from '@mui/material/styles';
import Header from '../../components/header/header';

import Image1 from './imgSlides/original.jpg';
import Image2 from './imgSlides/original2.jpg';
import Image3 from './imgSlides/original3.jpg';
import Image4 from './imgSlides/original4.jpg';
import Image5 from './imgSlides/original5.jpg';
import Image6 from './imgSlides/original6.jpg';
import Image7 from './imgSlides/original7.jpg';
import Image8 from './imgSlides/original8.jpg';
import Image9 from './imgSlides/original9.jpg';

const card1 = {
    url: `${Image1}`,
    title: "Wandering Dunes",
    description: "Dedication to Immigrants from Central Asia (Sketch), 2002, Ink on paper"
}

const card2 = {
    url: `${Image2}`,
    title: "Totality Inventory",
    description: "Roster of Infinity, 1976, Paper, pencil, ink"
}

const card3 = {
    url: `${Image3}`,
    title: "Lenin’s Plans of Monumental Propaganda",
    description: "1984 Pencil and collage on paper"
}

const card4 = {
    url: `${Image4}`,
    title: "Moslem",
    description: "Single-channel video, Duration: 17:41 min."
}

const card5 = {
    url: `${Image5}`,
    title: "Malevich",
    description: " The Great Turning Point, 1990, Pencil, ink, gouache and collage on paper, Each: 29.7 x 42 cm."
}

const card6 = {
    url: `${Image6}`,
    title: "1m²",
    description: "1978-2007, Installation, matchboxes."
}

const card7 = {
    url: `${Image7}`,
    title: "Mantras of the USSR #1",
    description: "1977, Collage and pencil on paper, 30 x 42 cm."
}

const card8 = {
    url: `${Image8}`,
    title: "Red Mantra",
    description: "The victory of communism is inevitable, dalla serie, 1979"
}
const card9 = {
    url: `${Image9}`,
    title: "Breathe Quietly",
    description: "1976-2013 Illustation view at Laura Bulian Gallery"
}

const cards = [card1, card2, card3, card4, card5, card6, card7, card8, card9];

// TODO remove, this demo shouldn't need to reset the theme.
const defaultTheme = createTheme();

export default function Gallery() {
    return (
        <ThemeProvider theme={defaultTheme}>
            <Header />
            <main>
                {/* Hero unit */}
                <Box
                    sx={{
                        bgcolor: 'background.paper',
                        pt: 8,
                        pb: 6,
                    }}
                >
                    <Container maxWidth="sm">
                        <Typography
                            component="h1"
                            variant="h2"
                            align="center"
                            color="text.primary"
                            gutterBottom
                        >
                            Hello Vyacheslav!
                        </Typography>
                        <Typography variant="h5" align="center" color="text.secondary" paragraph>
                            Welcome to your account! Here, you can unleash your creativity and create 
                            your very own gallery of artwork. Let your imagination soar as you showcase 
                            your artistic talents to the world. We're excited to see the masterpieces 
                            you'll share with us.
                        </Typography>
                        <Stack
                            sx={{ pt: 4 }}
                            direction="row"
                            spacing={2}
                            justifyContent="center"
                        >
                            <Button variant="contained" sx={{ backgroundColor: '#434544', '&:hover': { backgroundColor: '#0a0a0a'} }}>
                                Add Artwork
                            </Button>
                            <Button variant="outlined" sx={{ backgroundColor: '#fff', borderColor: '#434544', color: '#434544', '&:hover': { backgroundColor: '#f7f7f7', borderColor: '#0a0a0a',} }}>Update Gallery</Button>
                        </Stack>
                    </Container>
                </Box>
                <Container sx={{ py: 8 }} maxWidth="md">
                    {/* End hero unit */}
                    <Grid container spacing={4}>
                        {cards.map((card) => (
                            <Grid item key={card.title} xs={12} sm={6} md={4}>
                                <Card
                                    sx={{ height: '100%', display: 'flex', flexDirection: 'column' }}
                                >
                                    <CardMedia
                                        component="div"
                                        sx={{
                                            // 16:9
                                            pt: '56.25%',
                                        }}
                                        image={card.url}
                                    />
                                    <CardContent sx={{ flexGrow: 1 }}>
                                        <Typography gutterBottom variant="h5" component="h2">
                                            {card.title}
                                        </Typography>
                                        <Typography>
                                            {card.desc}
                                        </Typography>
                                    </CardContent>
                                    <CardActions>
                                        <Button size="small">View</Button>
                                        <Button size="small">Edit</Button>
                                    </CardActions>
                                </Card>
                            </Grid>
                        ))}
                    </Grid>
                </Container>
            </main>
        </ThemeProvider>
    );
}