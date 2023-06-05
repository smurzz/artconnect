import * as React from 'react';
import Avatar from '@mui/material/Avatar';
import Button from '@mui/material/Button';
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
import { createTheme, ThemeProvider } from '@mui/material/styles';
import { useState, useEffect, useRef} from "react";
import { Link, Route, Routes, useNavigate } from "react-router-dom";
import { ApiService } from "../../lib/api";
import Login from "../Login/Login";
import Modul from "../../components/Modul/Modul";
import CircularProgress from '@mui/material/CircularProgress';

//redux
import {useDispatch} from "react-redux";
import { connect } from 'react-redux';
import * as authenticationActions from '../../redux/authentication/AuthenticationAction';


// gets the auth value from the entire state of the component
const mapStateToProps = state => {
  const { auth } = state
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

  const [success, setSuccess] = useState(false);
  const [error, setError] = useState(false);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    console.log("props Status: "+props.status)
    setLoading(props.pending)
  }, [props.pending]);

  useEffect(() => {
    console.log("success: "+ success);
    setSuccess (props.status === 200);
    console.log("props message: "+props.message)
  }, [props.status, props.message, props.signupUserAction]);

  useEffect(() => {
    setError(props.errorSignup !== null);
    console.log("props error: "+props.errorSignup)
  }, [props.errorSignup, props.signupUserAction]);

  const handleSubmit = async (event) => {
    event.preventDefault();
    setLoading(true);
    await new Promise((resolve) => setTimeout(resolve, 300));
    props.signupUserAction(user);

  }
  return (
    <ThemeProvider theme={defaultTheme}>
      <Container component="main" maxWidth="xs">
        <CssBaseline />
        {success &&
            <Modul data={{
              message: props.message,
              success:true,
              error: false,
              type: "registration"
            }}/>
        }
        {
            error &&
            <Modul data={{
              message: props.errorSignup,
              success:false,
              error:true,
              type:"registration"
            }}/>
        }
        <Box
          sx={{
            marginTop: 8,
            display: 'flex',
            flexDirection: 'column',
            alignItems: 'center',
          }}
        >
          <Avatar sx={{ m: 1, bgcolor: 'secondary.light' }}>
            {loading ?<CircularProgress /> :<LockOutlinedIcon />}
          </Avatar>
          <Typography component="h1" variant="h5">
            Sign up
          </Typography>
          <Box component="form" noValidate onSubmit={handleSubmit} sx={{ mt: 3 }}>
            <Grid container spacing={2}>
              <Grid item xs={12} sm={6}>
                <TextField
                  autoComplete="given-name"
                  name="firstName"
                  required
                  fullWidth
                  id="firstName"
                  label="First Name"
                  onChange={async (e) => { setUser({ ...user, firstname: e.target.value }) }}
                  onKeyPress={(e) => { e.key === 'Enter' && e.preventDefault(); }}
                  placeholder="Enter your First Name"
                  autoFocus
                />
              </Grid>
              <Grid item xs={12} sm={6}>
                <TextField
                  fullWidth
                  onKeyPress={(e) => { e.key === 'Enter' && e.preventDefault(); }}
                  onChange={async (e) => { setUser({ ...user, lastname: e.target.value }) }}                  required
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
                  onKeyPress={(e) => { e.key === 'Enter' && e.preventDefault(); }}
                  onChange={async (e) => { setUser({ ...user, email: e.target.value }) }}
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
                  onKeyPress={(e) => { e.key === 'Enter' && e.preventDefault(); }}
                  onChange={async (e) => { setUser({ ...user, password: e.target.value }); }}
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
                  control={<Checkbox value="allowExtraEmails" color="primary" />}
                  label="I want to receive inspiration, marketing promotions and updates via email."
                />
              </Grid>
            </Grid><Button
                type="submit"
                fullWidth
                variant="contained"
                sx={{mt: 3, mb: 2}}
            >
              Sign Up
            </Button>
            <Grid container justifyContent="flex-end">
              <Grid item>
                <Link to="/Login" className="text">
                  Already have an account? Sign in
                </Link>
              </Grid>
            </Grid>
          </Box>
        </Box>
        <Copyright sx={{ mt: 5 }} />
      </Container>
    </ThemeProvider>
  );
}
const mapDispatchToProps = dispatch => {
  return {
    signupUserAction: (user) => dispatch(authenticationActions.signupUser(user))
  }
}

//SignUp
const ConnectedSignup = connect(mapStateToProps, mapDispatchToProps)(SignUp)
export default ConnectedSignup;