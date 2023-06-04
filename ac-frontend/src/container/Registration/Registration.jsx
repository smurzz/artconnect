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
import Login from "../Login/Login"

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
export default function SignUp() {
  const [success, setSuccess] = useState(false);
  const errRef = useRef();

  const [firstname, setFirstname] = useState("");
  const [firstnameFocus, setFirstnameFocus] = useState(false);

  const [lastname, setLastname] = useState("");
  const [lastnameFocus, setLastnameFocus] = useState(false);

  const [email, setEmail] = useState("");
  const [emailFocus, setEmailFocus] = useState(false);

  const [pwd, setPwd] = useState("");
  const [pwdFocus, setPwdFocus] = useState(false);

  const [matchPwd, setMatchPwd] = useState("");
  const [validMatch, setValidMatch] = useState(false);
  const [matchFocus, setMatchFocus] = useState(false);

  const [errMsg, setErrMsg] = useState("");
  const navigate = useNavigate();

  //Hook that makes sure both passwords are the same
  useEffect(() => {
    setValidMatch(pwd === matchPwd);
  }, [pwd, matchPwd]);

  //Hook that clears Errormessages an soon as a User changes the Input inside the Formfield
  useEffect(() => {
    setErrMsg("");
  }, [email, firstname, lastname, pwd, matchPwd]);

  const handleSubmit = async (event) => {
    event.preventDefault();
    console.log("first Name: "+ firstname);
   const response = await ApiService.postRegister({
      firstname: firstname,
      lastname: lastname,
      email: email,
      password: pwd,
    });

    if(response === "success"){
      setPwd("");
      setMatchPwd("");
      setEmail("");
      setFirstname("");
      setLastname("");
      navigate("/login", { state: { message: response.data} });
    }else{
      setErrMsg(response);
      errRef.current.focus();
    }
  };

  return (
    <ThemeProvider theme={defaultTheme}>
      <Container component="main" maxWidth="xs">
        <CssBaseline />
        <Box
          sx={{
            marginTop: 8,
            display: 'flex',
            flexDirection: 'column',
            alignItems: 'center',
          }}
        >
          <Avatar sx={{ m: 1, bgcolor: 'secondary.main' }}>
            <LockOutlinedIcon />
          </Avatar>
          <p ref={errRef} className={errMsg ? "errmsg" : "offscreen"} aria-live="assertive">{errMsg}</p>
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
                  onChange={(e) => setFirstname(e.target.value)}
                  onKeyPress={(e) => { e.key === 'Enter' && e.preventDefault(); }}
                  placeholder="Enter your First Name"
                  autoFocus
                />
              </Grid>
              <Grid item xs={12} sm={6}>
                <TextField
                  fullWidth
                  onKeyPress={(e) => { e.key === 'Enter' && e.preventDefault(); }}
                  onChange={(e) => setLastname(e.target.value)}
                  required
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
                  onChange={(e) => setEmail(e.target.value)}
                  placeholder="Enter your email"
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
                  onChange={(e) => setPwd(e.target.value)}
                  value={pwd}
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
            </Grid>
            <Button
              type="submit"
              fullWidth
              variant="contained"
              sx={{ mt: 3, mb: 2 }}
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