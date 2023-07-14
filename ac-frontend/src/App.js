import './App.css';
import React from 'react';
import { BrowserRouter as Router, Route, Routes, Navigate } from 'react-router-dom';
import HomePublic from './react/pages/HomePublic';
import HomePrivate from './react/pages/HomePrivate';
import UsersPage from './react/pages/UsersPage';
import ArtworksPage from "./react/pages/Artwork/ArtworksPage";
import Login from './react/pages/Login';
import MyProfile from './react/pages/MyProfile';
import MyGallery from './react/pages/MyGallery';
import Profile from './react/pages/Profile';
import Gallery from './react/pages/Gallery';
import { Register } from './react/pages/Register';
import { ForgotPassword } from './react/pages/ForgotPassword';
import { ResetPassword } from './react/pages/ResetPassword';
import NotFound from "./react/pages/errors/NotFoundPage"
import ArtworkDetailPage from "./react/pages/Artwork/ArtworkDetails";

function App() {

  return (
    <Router>
      <Routes>
        <Route exact path="/" element={<HomePublic />} />
        <Route exact path='/login' element={<Login />} />
        <Route exact path='/register' element={<Register />} />
        <Route exact path='/forgot-password' element={< ForgotPassword />} />
        <Route exact path='/reset-password' element={< ResetPassword />} />
        <Route exact path='/home' element={< HomePrivate />} />
        <Route exact path='/profile-edit' element={< MyProfile />} />
        <Route exact path='/gallery-edit' element={< MyGallery />} />
        <Route exact path='/users' element={< UsersPage />} />
        <Route exact path='/user/:id' element={< Profile />} />
        <Route exact path='/gallery/:id' element={< Gallery />} />
        <Route exact path='/artworks' element={<ArtworksPage />} />
        <Route exact path='/artworks/:id' element={<ArtworkDetailPage />} />
        <Route path='*' element={<NotFound />} />

      </Routes>
    </Router>
  );
}

export default App;
