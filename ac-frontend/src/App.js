import './App.css';
import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import HomePublic from './react/pages/home/HomePublic';
import HomePrivate from './react/pages/home/HomePrivate';
import UsersPage from './react/pages/user/UsersPage';
import ArtworksPage from "./react/pages/artwork/ArtworksPage";
import Login from './react/pages/home/Login';
import MyProfile from './react/pages/user/MyProfile';
import MyGallery from './react/pages/gallery/MyGallery';
import Profile from './react/pages/user/Profile';
import Gallery from './react/pages/gallery/Gallery';
import Register from './react/pages/home/Register';
import ForgotPassword from './react/pages/home/ForgotPassword';
import ResetPassword from './react/pages/home/ResetPassword';
import NotFound from "./react/pages/errors/NotFoundPage"
import ArtworkDetails from './react/pages/artwork/ArtworkDetails';
import ArtworkCreate from './react/pages/artwork/ArtworkCreate';
import ArtworkEdit from './react/pages/artwork/ArtworkEdit';

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
        <Route exact path='/artworks-edit/:id' element={<ArtworkEdit/>}/>
        <Route exact path='/artwork-create' element={<ArtworkCreate />} />
        <Route exact path='/users' element={< UsersPage />} />
        <Route exact path='/user/:id' element={< Profile />} />
        <Route exact path='/gallery/:id' element={< Gallery />} />
        <Route exact path='/artworks' element={<ArtworksPage />} />
        <Route exact path='/artworks/:id' element={<ArtworkDetails />} />
        <Route path='*' element={<NotFound />} />

      </Routes>
    </Router>
  );
}

export default App;
