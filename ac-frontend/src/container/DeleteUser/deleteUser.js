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

    return (<>
            {isLoggedIn? <HeaderLogedIn/>:<HeaderLogedOut/>}
            <div className="container mt-20">
                <div className="row justify-content-center ">
                    <div className="col-12 col-sm-10 col-md-8 col-lg-6">
                        <div className="text-center mt-7">
                            <h3>We Are Sad to See You Want to Delete Your Account</h3>
                            <p className="text-base font-semibold text-black">
                                Please keep in mind that by deleting your account, you will lose access
                                to all your saved data, preferences, and any contributions you have made
                                to our platform.
                            </p>
                            <p className="text-base font-semibold text-black">
                                If you have any questions or require further assistance, please don't
                                hesitate to reach out to our customer support team. Thank you for being
                                a part of our community.
                            </p>
                            <button
                                className="inline-flex items-center rounded-md bg-indigo-600 px-3 py-2 text-sm font-semibold text-white shadow-sm hover:bg-blue-100 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-bg-blue-100 mt-7 mb-7"
                                onClick={()=>deleteUser(false)}>
                                No. I want to stay
                            </button>
                            <button
                                className=" mx-7 inline-flex items-center rounded-md bg-blue-200 px-3 py-2 text-sm font-semibold text-slate-700 shadow-sm hover:bg-blue-300 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:bg-blue-300 mt-7 mb-7"
                                onClick ={()=>deleteUser(true)}>
                                Delete my Account
                            </button>
                        </div>
                    </div>
                </div>
            </div>

        </>
    );
}