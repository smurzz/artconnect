import {useNavigate} from "react-router-dom";
import * as React from 'react';
import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import Button from '@mui/material/Button';
import Link from '@mui/material/Link';
import TextField from '@mui/material/TextField';
import BrushIcon from '@mui/icons-material/Brush';
import { useTheme } from '@mui/material/styles';
import useMediaQuery from '@mui/material/useMediaQuery';
import "./header.css";

export default function ButtonAppBar() {
    const theme = useTheme();
    const navigate = useNavigate();
    const navigateTo=(navigation)=>{
        var registerLoginNavigation ="/"+ navigation;
        navigate(registerLoginNavigation);
    }

    const isMobile = useMediaQuery(theme.breakpoints.down('md'));

    return (
        <Box sx={{ flexGrow: 1 }}>
            <AppBar position="static" style={{ backgroundColor: '#0a0a0a' }}>
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
                    {/*         <Link underline="hover" to="/"> Artconnect </Link> */}
                            <Link href="#" underline="hover"> ArtConnect </Link>
                        </Typography>
                    </div>
                    <div className="searchBar">
                        <TextField
                            id="search"
                            label="Search"
                            variant="outlined"
                            size="small"
                            sx={{
                                mx: isMobile ? 'auto' : 'unset',
                                backgroundColor: '#ffffff',
                                borderRadius: '20px',
                                width: isMobile ? '100%' : '500px',
                            }}
                            // Add any necessary event handlers or search functionality here
                        />
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

