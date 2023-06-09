import * as React from 'react';
import Avatar from '@mui/material/Avatar';
import Button from '@mui/material/Button';
import CssBaseline from '@mui/material/CssBaseline';
import TextField from '@mui/material/TextField';
import FormControlLabel from '@mui/material/FormControlLabel';
import Checkbox from '@mui/material/Checkbox';
import LinkMa from '@mui/material/Link';
import Paper from '@mui/material/Paper';
import Box from '@mui/material/Box';
import Grid from '@mui/material/Grid';
import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import Typography from '@mui/material/Typography';
import { createTheme, ThemeProvider } from '@mui/material/styles';
import {useEffect, useState} from "react";
import {Link, useNavigate, useLocation} from "react-router-dom";
import { ApiService } from "../../lib/api";
import axios from "../../api/axios";
//lineaer loading
import LinearProgress from '@mui/material/LinearProgress';
import Header from "../../components/headerComponent/headerLogout"

//Imports Dialog:
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap/dist/js/bootstrap.bundle.min";

const FORGET_URL = "/forgot-password"
function Copyright(props) {
    return (
        <Typography variant="body2" color="text.secondary" align="center" {...props}>
            {'Copyright © '}
            <LinkMa
                color="inherit" href="https://mui.com/">
                Your Website
            </LinkMa>{' '}
            {new Date().getFullYear()}
            {'.'}
        </Typography>
    );
}

const defaultTheme = createTheme();


export default function DeleteUserAnswer() {
    const navigate = useNavigate();

    const location = useLocation();
    const { userWantsToBeDeleted } = location.state;



    return (
        <ThemeProvider theme={defaultTheme}>
            <Header/>
            <main>
                <Grid container component="main" sx={{ height: '100vh' }}>
                    <CssBaseline />
                    <Grid
                        item
                        xs={false}
                        sm={4}
                        md={7}
                        sx={{
                            backgroundImage: 'url(https://source.unsplash.com/random?wallpapers)',
                            backgroundRepeat: 'no-repeat',
                            backgroundColor: (t) =>
                                t.palette.mode === 'light' ? t.palette.grey[50] : t.palette.grey[900],
                            backgroundSize: 'cover',
                            backgroundPosition: 'center',
                        }}
                    />
                    <Grid item xs={12} sm={8} md={5} component={Paper} square>
                        {userWantsToBeDeleted ? <Typography component="h1" variant="h5">
                            We are sorry you chose to leave us.
                        </Typography> :
                            <Typography component="h1" variant="h5">
                            We are Glad you are staying.
                            Navigate to our homepage and enjoy the fascinating Art of all the amazing artists we are hosting on our Webiste.
                            <Link to="/">Go to our homepage</Link>
                            </Typography>}
                    </Grid>
                </Grid>
            </main>
        </ThemeProvider>
    );
}