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
import axios from "../../api/axios";
import Modul from "../../components/Modul/Modul";
import CircularProgress from '@mui/material/CircularProgress';


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

export default function ForgotPassword() {
    const navigate = useNavigate();
    const [email, setEmail] = useState("");
    const [msg, setMsg] = useState("");
    const [resetPassword, setResetPassword] = useState(false);
    const [error, setError] = useState(false);
    const [loading, setLoading] = useState(false);


    const handleSubmit = async (event) => {
        event.preventDefault();
        setLoading(true);
        await new Promise((resolve) => setTimeout(resolve, 300));
        var _headers = {
            headers: {
                "Content-Type": "application/json",
            },
        };
        try {
            let result = await axios.get(FORGET_URL, {
                params: {
                    email: email,
                },
            }, _headers);

            setLoading(false);
                setMsg(result.data);
                setResetPassword(true);
                console.log("success");
        } catch (e) {
            setMsg(JSON.stringify(e.response.data.message));
            setError(true);
            setResetPassword(false);
        }

    };
  return (
    <ThemeProvider theme={defaultTheme}>
      <Grid container component="main" sx={{ height: '100vh' }}>
          {resetPassword &&
              <Modul data={{
                  message: msg,
                  success:true,
                  error: false,
                  type: "resetPassword"
              }}/>
          }
          {error &&
              <Modul data={{
                  message: msg,
                  success:false,
                  error:true,
                  type: "resetPassword"
              }}/>
          }
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
              Forgot Password
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
                onChange={(e) => setEmail(e.target.value)}
                value={email}
              />
              <Button
                type="submit"
                fullWidth
                variant="contained"
                sx={{ mt: 3, mb: 2 }}
              >
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
              </Grid>
              <Copyright sx={{ mt: 5 }} />
            </Box>
          </Box>
        </Grid>
      </Grid>
    </ThemeProvider>
  );
}