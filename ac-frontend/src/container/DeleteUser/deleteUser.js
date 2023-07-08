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
import {logikService} from  "../../lib/service"
import HeaderLogedIn from "../../components/headerComponent/headerLogedIn";
import HeaderLogedOut from "../../components/headerComponent/headerLogout"
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


export default function DeleteUser() {
    const navigate = useNavigate();
    const [loading, setLoading] = useState(false);

    const location = useLocation();
    const { userId } = location.state;
    const [isLoggedIn, setIsLoggedIn] = React.useState(false);
    useEffect(()=>{
        async function getLoggedIn(){
            const loggedInHeader = await logikService.isLoggedIn();
            setIsLoggedIn(loggedInHeader);
            console.log("loggedIn: " + loggedInHeader)
        }
        getLoggedIn();
    },[])

    const deleteUser = async (userShouldBeDeleted)=>{
        if(userShouldBeDeleted == true){
            setLoading(true);
            let result = await ApiService.deleteUser(userId);
            setLoading(true);
            if(result =="success"){
                navigate("/deleteUserAnswer",{state:{userWantsToBeDeleted: true }});
            }
        }else{
            navigate("/deleteUserAnswer",{state:{userWantsToBeDeleted: false}});
        }
    }
    const handleSubmit = async (event) => {
        event.preventDefault();
    };

    return (
        <ThemeProvider theme={defaultTheme}>
            {isLoggedIn? <HeaderLogedIn/>:<HeaderLogedOut/>}
            <main>
                <Grid container component="main" sx={{ height: '100vh' }}>
                    {loading && <CssBaseline/>}
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
                        <Box
                            sx={{
                                my: 8,
                                mx: 4,
                                display: 'flex',
                                flexDirection: 'column',
                                alignItems: 'center',
                            }}
                        >
                            <Avatar sx={{ m: 1, bgcolor: 'secondary.light' }}>
                                <LockOutlinedIcon />
                            </Avatar>

                            <Typography component="h1" variant="h5">
                                Are you sure you want to delete your Account?
                                {userId}
                            </Typography>
                            <Box component="form" noValidate onSubmit={handleSubmit} sx={{ mt: 1 }}>
                                <Grid item sm={12}>
                                    <Box sx={{ width: '100%' }}>
                                        <LinearProgress />
                                    </Box>
                                </Grid>
                                <Button
                                    type="button"
                                    fullWidth
                                    variant="contained"
                                    onClick ={()=>deleteUser(true)}
                                    on
                                    sx={{
                                        mt: 3,
                                        mb: 2,
                                        backgroundColor: '#434544',
                                        '&:hover': {
                                            backgroundColor: '#0a0a0a ',
                                        }
                                    }}>
                                    Yes.
                                </Button>
                                <Button
                                    type="button"
                                    fullWidth
                                    variant="contained"
                                    onClick ={()=>deleteUser(false)}

                                    sx={{
                                        mt: 3,
                                        mb: 2,
                                        backgroundColor: '#434544',
                                        '&:hover': {
                                            backgroundColor: '#0a0a0a ',
                                        }
                                    }}>
                                    No. I want to stay
                                </Button>
                                <Grid container>
                                    <Grid item xs>
                                    </Grid>

                                </Grid>
                                <Copyright sx={{ mt: 5 }} />
                            </Box>
                        </Box>
                    </Grid>
                </Grid>
            </main>
        </ThemeProvider>
    );
}