import {Link, useNavigate} from "react-router-dom";
import * as React from 'react';
import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import Button from '@mui/material/Button';
import IconButton from '@mui/material/IconButton';
import MenuIcon from '@mui/icons-material/Menu';
import BrushIcon from '@mui/icons-material/Brush';
import Avatar from '@mui/material/Avatar';
import { purple } from '@mui/material/colors';
import "./header.css";

export default function ButtonAppBar() {
    const navigate = useNavigate();
    const navigateTo=(navigation)=>{
        var registerLoginNavigation ="/"+ navigation;
        navigate(registerLoginNavigation);
    }
    return (
        <Box sx={{ flexGrow: 1 }}>
            <AppBar position="static">
                <Toolbar sx={{ mr: 2 ,  justifyContent: 'space-between' }}>
                    <div className="flexBox">
                        <BrushIcon sx={{ display: { xs: 'none', md: 'flex' }, mr: 1 }}></BrushIcon>
                        <Typography
                            variant="h6"
                            noWrap
                            component="a"
                            href="/"
                            sx={{
                                mr: 2,
                                display: { xs: 'none', md: 'flex' },
                                fontFamily: 'monospace',
                                fontWeight: 700,
                                letterSpacing: '.3rem',
                                color: 'inherit',
                                textDecoration: 'none',
                            }}>
                            <Link to="/"> Artconnect </Link>
                        </Typography>
                    </div>
                    <div>
                    <Button color="inherit" edge="end" onClick={() => navigateTo("login")}  sx={{ mr: 2}} >Login</Button>
                    <Button color="inherit" onClick={() => navigateTo("register")}>Register</Button>
                    </div>
                </Toolbar>
            </AppBar>
        </Box>
    );
}

