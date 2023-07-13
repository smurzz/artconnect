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


export default function DeleteUserAnswer() {
    const navigate = useNavigate();
    const location = useLocation();
    const { userWantsToBeDeleted } = location.state;
    const [isLoggedIn, setIsLoggedIn] = React.useState(false);
    useEffect(()=>{
        async function getLoggedIn(){
            const loggedInHeader = await logikService.isLoggedIn();
            setIsLoggedIn(loggedInHeader);
            console.log("loggedIn: " + loggedInHeader)
        }
        getLoggedIn();
    },[])
    return (
        <>
            {isLoggedIn? <HeaderLogedIn/>:<HeaderLogedOut/>}
            <div className="container mt-20">
                <div className="row justify-content-center ">
                    <div className="col-12 col-sm-10 col-md-8 col-lg-6">
                        <div className="text-center mt-7">
                            {userWantsToBeDeleted ?
                                (<div>
                                    <h3>We're sorry to see you go.</h3>
                                <p className="text-base font-semibold text-black">
                                    Once again, thank you for being a part of our journey. We wish you all the best in your future endeavors.
                                </p>
                                </div>)
                                :
                                (<div>
                                    <h3>We are Glad you are staying.</h3>
                            <p className="text-base font-semibold text-black">
                                Navigate to our homepage and enjoy the fascinating Art of all the amazing artists we are hosting on our Webiste.
                                <Link to="/">Go to our homepage</Link>
                            </p>
                                </div>)}
                        </div>
                    </div>
                </div>
            </div>
        </>
    );
}
