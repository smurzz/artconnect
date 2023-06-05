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
import {Link, useNavigate} from "react-router-dom";
import { ApiService } from "../../lib/api";
import AlertDialog from "../../components/Modul/Modul";
import CircularProgress from '@mui/material/CircularProgress';
import Modul from "../../components/Modul/Modul";



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

export default function SignInSide() {
    const navigate = useNavigate();
    const [loading, setLoading] = useState(false);
    const [email, setEmail] = useState("");
    const [pwd, setPwd] = useState("");
    const [errMsg, setErrMsg] =useState("");
    const [msgModal, setMsgModal] = useState(false);
    useEffect(() => {
    }, [errMsg]);


    const handleSubmit = async (event) => {
        event.preventDefault();
        setLoading(true);
        await new Promise((resolve) => setTimeout(resolve, 300));
        await ApiService.postLogin({
            email: email,
            password: pwd,
        }).then((res)=>{
            setLoading(false);
            if(res === "success"){
                navigate("/galerie", { state: { message: "login message" } });
            }else{
                setMsgModal(true);
                setErrMsg(res);
                console.log(res);
            }
        });
    };

  return (
    <ThemeProvider theme={defaultTheme}>
        {
            msgModal &&
            <Modul data={{
                message: errMsg,
                success:false,
                error:true,
                type: "login"
            }}/>
        }
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
                      {loading ?<CircularProgress /> :<LockOutlinedIcon />}
                  </Avatar>
            <Typography component="h1" variant="h5">
              Sign in
            </Typography>
            <Box component="form" noValidate onSubmit={handleSubmit} sx={{ mt: 1 }}>
              <TextField
                margin="normal"
                required
                fullWidth
                id="email"
                label="Email Address"
                name="email"
                autoComplete="email"
                autoFocus
                onKeyPress={(e) => { e.key === 'Enter' && e.preventDefault(); }}
                onChange={(e) => setEmail(e.target.value)}
                value={email}
              />
              <TextField
                margin="normal"
                required
                fullWidth
                onKeyPress={(e) => { e.key === 'Enter' && e.preventDefault(); }}
                onChange={(e) => setPwd(e.target.value)}
                value={pwd}
                name="password"
                label="Password"
                type="password"
                id="password"
                autoComplete="current-password"
              />
              <Button
                type="submit"
                fullWidth
                variant="contained"
                sx={{ mt: 3, mb: 2 }}
              >
                Sign In
              </Button>
              <Grid container>
                <Grid item xs>
                    <Link to="/forgot" className="body2">
                        Forgot password?
                    </Link>
                </Grid>
                <Grid item>
                    <Link to="/Register" className="body2">
                        Don't have an account? Sign Up
                    </Link>
                    {!{errMsg} && <AlertDialog/>}
                </Grid>
              </Grid>
              <Copyright sx={{ mt: 5 }} />
            </Box>
          </Box>
        </Grid>
      </Grid>
    </ThemeProvider>
  );
}