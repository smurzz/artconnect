import React from 'react';
import Box from '@mui/material/Box';
import Link from '@mui/material/Link';
import Typography from '@mui/material/Typography';

export default function Footer() {
  return (
    <>
      <Box sx={{ bgcolor: 'background.paper', p: 6 }} component="footer">
        <Typography variant="h6" align="center" gutterBottom>
          ArtConnect
        </Typography>
        <Typography
          variant="subtitle1"
          align="center"
          color="text.secondary"
          component="p"
        >
          Website is made by @ArtConnect Team
        </Typography>
        <Typography variant="body2" color="text.secondary" align="center">
          {'ArtConnect © '}
          <Link color="inherit" href="https://github.com/smurzz/artconnect/">
            GitHub
          </Link>{' '}
          {new Date().getFullYear()}
          {'.'}
        </Typography>
      </Box>
    </>
  )
}