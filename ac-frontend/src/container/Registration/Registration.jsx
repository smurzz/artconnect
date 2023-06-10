import * as React from 'react';
import Avatar from '@mui/material/Avatar';
import CssBaseline from '@mui/material/CssBaseline';
import TextField from '@mui/material/TextField';
import FormControlLabel from '@mui/material/FormControlLabel';
import Checkbox from '@mui/material/Checkbox';
import LinkMa from '@mui/material/Link';
import Grid from '@mui/material/Grid';
import Box from '@mui/material/Box';
import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import Typography from '@mui/material/Typography';
import Container from '@mui/material/Container';
import {createTheme, ThemeProvider} from '@mui/material/styles';
import {useState, useEffect, useRef} from "react";
import {Link, Route, Routes, useNavigate} from "react-router-dom";
import {ApiService} from "../../lib/api";
import Login from "../Login/Login";
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';
import Header from "../../components/headerLogout/header";

//lineaer loading
import LinearProgress from '@mui/material/LinearProgress';

import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap/dist/js/bootstrap.bundle.min";
//redux
import {useDispatch} from "react-redux";
import {connect} from 'react-redux';
import * as authenticationActions from '../../Redux/authentication/AuthenticationAction';


// gets the auth value from the entire state of the component
const mapStateToProps = state => {
    const {auth} = state
    return auth;
}

function Copyright(props) {
    return (
        <Typography variant="body2" color="text.secondary" align="center" {...props}>
            {'Copyright © '}
            <LinkMa color="inherit" href="https://mui.com/">
                Your Website
            </LinkMa>{' '}
            {new Date().getFullYear()}
            {'.'}
        </Typography>
    );
}

const defaultTheme = createTheme();

function SignUp(props) {
    //triggers Statechanges so the component reloads
    const dispatch = useDispatch();
    const [user, setUser] = useState(
        {
            firstname: "",
            lastname: "",
            email: "",
            password: ""
        });

    const navigate = useNavigate();
    //Anzeigen des Lagebalkens
    const [loading, setLoading] = useState(false);
    //Öffnen und schließen der Error und Success Dialoge
    const [errorMessage, setErrorMessage] = useState(false);
    const [open, setOpen] = React.useState(false);
    const [success, setSuccess] = React.useState(false);
    const [titel, setTitel] = React.useState("");
    const [message, setMessage] = React.useState("");
    const [errAlert, seterrAlert] = React.useState("");
    useEffect(() => {
        setLoading(props.pending)
    }, [props.pending]);

    useEffect(() => {
        if (props.status === 200) {
            setOpen(true);
            setSuccess(true);
            setTitel("Registration successful");
            setMessage("Please click on the verification link sent to your email to complete the registration process. Thank you!");
        }
    }, [props.status, props.message, props.signupUserAction]);

    //Immer wenn ein Fehler aus dem Backend kommt, wird der Fehler Dialog angezeigt
    useEffect(() => {
        if (props.errorSignup !== null ){
            setOpen(true);
            console.log("inside this method");
            if( props.errorSignup == "Email already exists") {
                console.log("Use Effect error sign up: "+ props.errorSignup);
                setTitel("Your Email is already Registered");
                setMessage("`Oops! Your email has already been registered. Please proceed to the login page.");
            } else {
                setOpen(true);
                console.log("Use Effect error sign up: "+ props.errorSignup);
                setTitel("Oops! Something went wrong");
                setMessage(props.errorSignup);
            }
        }
    }, [props.errorSignup]);

    //Sobald der User einen neuen Wert im Feld eingibt wird die Fehler messages wieder entfernt
    useEffect(() => {
        setErrorMessage(false);
        seterrAlert("");
        setTitel("");
        setMessage("");
    }, [user]);

    //öffnen und schließen der Usermessages
    const handleClose = () => {
        if (success) {
            setOpen(false);
            props.resetState();
            navigate("/login");
        }
        setOpen(false);
        props.resetState();
        setTitel("");
        setMessage("");
    };

    const userDataValid = () => {
        console.log("Inside UserDataValid");
        const errArray=[];
    const {firstname, lastname, email, password} = user;
    const isValidFirstname = firstname.length >= 2;
    const isValidLastname = lastname.length >= 2;
    const isValidEmail = /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);
    const isValidPassword = password.length >= 3;

    if (!firstname.trim() | !isValidFirstname) {
        errArray.push("First name should contain at least 2 characters")
    }
    if (!lastname.trim() |!isValidLastname ) {
        errArray.push("Last name should contain at least 2 characters")
    }
    if (!email.trim() | !isValidEmail) {
        errArray.push("Invalid email address")
    }
    if (!password.trim() | !isValidPassword) {
        console.log("Password cannot be empty");
        errArray.push("Password should contain at least 3 characters")
    }

    if(errArray.length > 0){
        console.log(errArray);
        setErrorMessage(true);
        seterrAlert(errArray.map(str => <p>{str}</p>));
        return false;
    }else{
        console.log("valid true");
        return true;
    }
}

    //Sendet Userdaten ans BE
    const handleSubmit = async (event) => {
        event.preventDefault();
        if(userDataValid() == true){
            setLoading(true);
            await new Promise((resolve) => setTimeout(resolve, 300));
            props.signupUserAction(user);
        }
    }

    return (
        <ThemeProvider theme={defaultTheme}>
            <Header/>
            <Container component="main" maxWidth="xs">
                <CssBaseline/>
                <Box
                    sx={{
                        marginTop: 8,
                        display: 'flex',
                        flexDirection: 'column',
                        alignItems: 'center',
                    }}
                >
                    <Avatar sx={{m: 1, bgcolor: 'secondary.light'}}>
                       <LockOutlinedIcon/>
                    </Avatar>
                    <Typography component="h1" variant="h5">
                        Sign up
                    </Typography>
                    <Box component="form" noValidate onSubmit={handleSubmit} sx={{mt: 3}}>

                        <Grid container spacing={2}>
                            <Grid item sm={12}>
                                {errorMessage && <div className="alert alert-danger" role="alert">
                                    {errAlert}
                                </div>}
                            </Grid>
                            <Grid item xs={12} sm={6}>
                                <TextField
                                    autoComplete="given-name"
                                    name="firstName"
                                    required
                                    fullWidth
                                    id="firstName"
                                    label="First Name"
                                    onChange={async (e) => {
                                        setUser({...user, firstname: e.target.value})
                                    }}
                                    onKeyPress={(e) => {
                                        e.key === 'Enter' && e.preventDefault();
                                    }}
                                    placeholder="Enter your First Name"
                                    autoFocus
                                />
                            </Grid>
                            <Grid item xs={12} sm={6}>
                                <TextField
                                    fullWidth
                                    onKeyPress={(e) => {
                                        e.key === 'Enter' && e.preventDefault();
                                    }}
                                    onChange={async (e) => {
                                        setUser({...user, lastname: e.target.value})
                                    }} required
                                    placeholder="Enter your Last Name"
                                    id="lastName"
                                    label="Last Name"
                                    name="lastName"
                                    autoComplete="family-name"
                                />
                            </Grid>
                            <Grid item xs={12}>
                                <TextField
                                    required
                                    fullWidth
                                    onKeyPress={(e) => {
                                        e.key === 'Enter' && e.preventDefault();
                                    }}
                                    onChange={async (e) => {
                                        setUser({...user, email: e.target.value})
                                    }}
                                    id="email"
                                    label="Email Address"
                                    name="email"
                                    autoComplete="email"
                                />
                            </Grid>
                            <Grid item xs={12}>
                                <TextField
                                    required
                                    fullWidth
                                    onKeyPress={(e) => {
                                        e.key === 'Enter' && e.preventDefault();
                                    }}
                                    onChange={async (e) => {
                                        setUser({...user, password: e.target.value});
                                    }}
                                    value={user.password}
                                    placeholder="Enter your password"
                                    name="password"
                                    label="Password"
                                    type="password"
                                    id="password"
                                    autoComplete="new-password"
                                />
                            </Grid>
                            <Grid item xs={12}>
                                <FormControlLabel
                                    control={<Checkbox value="allowExtraEmails" color="primary"/>}
                                    label="I want to receive inspiration, marketing promotions and updates via email."
                                />
                            </Grid>
                            <Grid item sm={12}>

                                    <Box sx={{ width: '100%' }}>
                                        {loading &&   <LinearProgress />}
                                    </Box>

                            </Grid>
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
                        }}
                    >
                        Sign Up
                    </Button>
                        <Grid container justifyContent="flex-end">
                            <Grid item>
                                <Link to="/Login" className="text">
                                    Already have an account? Sign in
                                </Link>
                            </Grid>
                            { open &&
                                <div >
                                    <Dialog
                                        className ="styleVisible"
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
                    </Box>

                </Box>
                <Copyright sx={{mt: 5}}/>
            </Container>
        </ThemeProvider>
    );
}

const mapDispatchToProps = dispatch => {
    return {
        resetState:() => dispatch(authenticationActions.getResetStateAction()),
        signupUserAction: (user) => dispatch(authenticationActions.signupUser(user))
    }
}

//SignUp
const ConnectedSignup = connect(mapStateToProps, mapDispatchToProps)(SignUp)
export default ConnectedSignup;