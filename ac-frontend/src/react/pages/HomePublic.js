import React, { useState } from 'react';
import { MenuBar } from './components/MenuBar';
import { ImageSlider } from './components/ImageSlider';
import '../layout/css/homePublic.css';
import HomePrivate from './HomePrivate';
import Footer from './components/Footer';

function HomePublic() {

  var userSession = JSON.parse(localStorage.getItem('userSession'));

  return(
    !userSession ? <div className="home-public-container">
      <MenuBar />
      <ImageSlider/>
      <Footer />
    </div> : <HomePrivate />
  );
}

export default HomePublic;