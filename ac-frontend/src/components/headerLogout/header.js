import * as React from 'react';
import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import Button from '@mui/material/Button';
import IconButton from '@mui/material/IconButton';
import MenuIcon from '@mui/icons-material/Menu';
import AdbIcon from '@mui/icons-material/Adb';
import {Link, useNavigate} from "react-router-dom";


export default function ButtonAppBar() {
    const navigate = useNavigate();

    const navigateTo=(navigation)=>{
        var navi ="/"+ navigation;
            navigate(navi);
    }
    return (
        <Box sx={{ flexGrow: 1 }}>
            <AppBar position="static">
                <Toolbar>
                    <AdbIcon sx={{ display: { xs: 'none', md: 'flex' }, mr: 1 }} />
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
                        }}
                    >
                        LOGO
                    </Typography>
                    <Button color="inherit" onClick={() => navigateTo("login")}>Login</Button>
                    <p> | </p>
                    <Button color="inherit" onClick={() => navigateTo("register")}>
                        Register</Button>
                </Toolbar>
            </AppBar>
        </Box>
    );
}
