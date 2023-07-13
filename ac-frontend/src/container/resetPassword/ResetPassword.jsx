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
import { ApiService } from "../../lib/api";
import {Link, useNavigate, useLocation} from "react-router-dom";
import {logikService} from  "../../lib/service"
import HeaderLogedIn from "../../components/headerComponent/headerLogedIn";
import HeaderLogedOut from "../../components/headerComponent/headerLogout";

//lineaer loading
import LinearProgress from '@mui/material/LinearProgress';
import Header from "../../components/headerComponent/headerLogout";
//Imports Dialog:
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap/dist/js/bootstrap.bundle.min";
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';

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

// TODO remove, this demo shouldn't need to reset the theme.

const defaultTheme = createTheme();

export default function ResetPassword() {
    const location = useLocation();
    const searchParams = new URLSearchParams(location.search);
    const token = searchParams.get('token');
    const navigate = useNavigate();
    const [pwd, setPwd] = useState("");
    const [matchPwd, setMatchPwd] = useState("");
    const [validMatch, setValidMatch] = useState(false);
    const [loading, setLoading] = useState(false);

    //öffnen und schließen des Error Message Dialoges
    const [errorMessage, setErrorMessage] = useState(false);
    const [open, setOpen] = React.useState(false);
    const [titel, setTitel] = React.useState("");
    const [message, setMessage] = React.useState("");
    const [errAlert, seterrAlert] = React.useState("");
    const [success, setSuccess] = React.useState(false);
    const [isLoggedIn, setIsLoggedIn] = React.useState(false);
    useEffect(()=>{
        async function getLoggedIn(){
            const loggedInHeader = await logikService.isLoggedIn();
            setIsLoggedIn(loggedInHeader);
            console.log("loggedIn: " + loggedInHeader)
        }
        getLoggedIn();
    },[])

    const handleClose = () => {
        setOpen(false);
        setTitel("");
        setMessage("");
        setLoading(false);
        if(success){
            navigate("/Login");
        }
    };

    useEffect(() => {
        setErrorMessage(false);
        seterrAlert("");
        setTitel("");
        setMessage("");
    }, [pwd, matchPwd]);

    const userDataValid = () => {
        console.log("Inside UserDataValid");
        const errArray=[];
        const isValidPassword = pwd.length >= 3;
        if (!pwd.trim() | !isValidPassword) {
            errArray.push("Password should contain at least 3 characters")
        }
        if (pwd !== matchPwd) {
            console.log("passwords dont match. Please enter the same password twice");
            errArray.push("passwords dont match. Please enter the same password twice")
        }

        if(errArray.length > 0){
            console.log(errArray);
            setErrorMessage(true);
            seterrAlert(errArray.map(str => <p>{str}</p>));
            return false;
        }else{
            return true;
        }
    }


    const handleSubmit = async (event) => {
        event.preventDefault();
        if(userDataValid() == true){
            setLoading(true);
                const response = await ApiService.postResetPassword({
                    password: pwd,
                    token: token
                });
                if(response === "success"){
                    setLoading(false);
                    setOpen(true);
                    setSuccess(true);
                    setTitel("Success");
                    setMessage("Congratulations! Your password has been successfully reset.");
                    console.log("success");
                }else{
                    setLoading(false);
                    setOpen(true);
                    setTitel("Error");
                    console.log("error");
                    setMessage("Opps! Something went wrong! Please try again later.");
                }
                console.log("handle submit");
        }
    };

    return (
        <ThemeProvider theme={defaultTheme}>
            {isLoggedIn? <HeaderLogedIn/>:<HeaderLogedOut/>}
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
                <Grid item xs={12} sm={8} md={5} component={Paper} elevation={6} square>
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
                            Forgot Password
                        </Typography>
                        <Box component="form" noValidate onSubmit={handleSubmit} sx={{ mt: 1 }}>
                            {errorMessage && <div className="alert alert-danger" role="alert">
                                {errAlert}
                            </div>}
                            <TextField
                                margin="normal"
                                type="password"
                                required
                                fullWidth
                                placeholder="Enter your password"
                                id="password"
                                label="Password"
                                name="password"
                                autoComplete="email"
                                autoFocus
                                onChange={(e) => setPwd(e.target.value)}
                                value={pwd}
                            />
                            <TextField
                                type="password"
                                placeholder="Confirm your password"
                                onChange={(e) => setMatchPwd(e.target.value)}
                                value={matchPwd}
                                margin="normal"
                                required
                                fullWidth
                                id="confirmPassword"
                                label="Confirm Password"
                                name="matchPwd"
                                autoComplete="matchPwd"
                                autoFocus
                                onChange={(e) => setMatchPwd(e.target.value)}
                                value={matchPwd}
                            />
                            <Grid item sm={12}>

                                <Box sx={{ width: '100%' }}>
                                    {loading &&   <LinearProgress />}
                                </Box>

                            </Grid>
                            <Button
                            type="submit"
                            fullWidth
                            variant="contained"
                            disabled={loading}
                            sx={{
                            mt: 3,
                            mb: 2,
                            backgroundColor: '#434544',
                            '&:hover': {
                                backgroundColor: '#0a0a0a ',
                            }
                        }}>
                                Reset password
                            </Button>
                            <Grid container>
                                <Grid item xs>
                                </Grid>
                                <Grid item>
                                    <Link to="/Register" className="body2">
                                        Don't have an account? Sign Up
                                    </Link>
                                </Grid>
                                { open &&
                                    <div >
                                        <Dialog
                                            open={open}
                                            maxWidth="xs" // Set the maxWidth to limit the dialog's width
                                            onClose={handleClose}
                                            aria-labelledby="alert-dialog-title"
                                            aria-describedby="alert-dialog-description"
                                        >
                                            <DialogTitle id="alert-dialog-title">
                                                {titel}
                                            </DialogTitle>
                                            <DialogContent>
                                                <DialogContentText id="alert-dialog-description">
                                                    {message}
                                                </DialogContentText>
                                            </DialogContent>
                                            <DialogActions>
                                                <Button onClick={handleClose} autoFocus>
                                                    Ok
                                                </Button>
                                            </DialogActions>
                                        </Dialog>
                                    </div>}
                            </Grid>
                            <Copyright sx={{ mt: 5 }} />
                        </Box>
                    </Box>
                </Grid>
            </Grid>
        </ThemeProvider>
    );
}