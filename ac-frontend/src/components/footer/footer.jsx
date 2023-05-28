import React from 'react';
import styles from './footer.css';
// import Images from '../../constants/images';
import { AiFillTwitterCircle } from 'react-icons/ai';
import { AiFillFacebook } from 'react-icons/ai';
import { AiFillLinkedin } from 'react-icons/ai';
import AdbIcon from '@mui/icons-material/Adb';
import Typography from '@mui/material/Typography';

export default function Footer() {
  return (
    <>
        <div className='grid grid__footer container'>
          <div className='logo_col'>
            <a href="#" className='footer_logo'>
              {/* <img className='logo' alt="Omnifood logo" src='Images.logo.src' /> */}
          <AdbIcon sx={{ display: { xs: 'none', md: 'flex' }, mr: 1 }}  className='logo'/>
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
            </a>
  
            <ul className='social_links'>
              <li>
                <a className='footer_link' href="#"
                //   ><ion-icon className="social-icon" name="logo-instagram"></ion-icon
                ><AiFillTwitterCircle /></a>
              </li>
              <li>
                <a className='footer_link' href="#"
                //   ><ion-icon className="social-icon" name="logo-facebook"></ion-icon
                ><AiFillFacebook /></a>
              </li>
              <li>
                <a className='footer_link' href="#"
                //   ><ion-icon className="social-icon" name="logo-twitter"></ion-icon
                ><AiFillLinkedin /></a>
              </li>
            </ul>
  
            <p className='copyright'>
              {/* Copyright &copy; <span className={styles.year}>2027</span> by Engelmann Development. All rights reserved. */}
            </p>
          </div>
          <div className='address_col'>
            <p className='footer_heading'>Kontakt</p>
            <address className='contacts'>
              <p className='adress'>
              Artconnect str, 10711 Berlin
              </p>
              <p>
                <a className='footer_link' href="tel:030-89060840">030-89060840</a
                ><br />
                <a className='footer_link' href="info@olibaustoffe.de"
                  >info@artconnect.de</a>
              </p>
            </address>
          </div>
          <nav className='nav_col'>
            <p className='footer_heading'>Home</p>
            <ul className='footer_nav'>
              <li><a className='footer_link' href="http://localhost:3000/uebersicht">Entdecken</a></li>
              <li><a className='footer_link' href="http://localhost:3000/gallerie">Galerie</a></li>
              <li><a className='footer_link' href="http://localhost:3000/kontakt">Kontakt</a></li>
              <li><a className='footer_link' href="#">Auktionen</a></li>
            </ul>
          </nav>
  
          <nav className="nav_col margin_left_helper">
            <p className='footer_heading'>Firma</p>
            <ul className='footer_nav'>
              <li><a className='footer_link' href="http://localhost:3000/agb">AGB</a></li>
              <li><a className='footer_link' href="http://localhost:3000/impressum">Impressum</a></li>
              <li><a className='footer_link' href="http://localhost:3000/datenschutz">Datenschutz</a></li>
              {/* <li><a className={styles.footer_links} href="#"></a></li> */}
            </ul>
          </nav>
  
          {/* <nav className={styles.nav_col}>
            <p className={styles.footer_heading}>Resources</p>
            <ul className={styles.footer_nav}>
              <li><a className={styles.footer_links} href="#">Recipe directory</a></li>
              <li><a className={styles.footer_links} href="#">Help center</a></li>
              <li><a className={styles.footer_links} href="#">Privacy & terms</a></li>
            </ul>
          </nav> */}
        </div>
    </>
  )
}