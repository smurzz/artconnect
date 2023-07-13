import Registration from "./container/Registration/Registration";
import { Route, Routes, BrowserRouter } from "react-router-dom";
import Layout from "./components/Layout/Layout";
import ProtectedRoutes from "./container/protectedRoutes";
import ForgotPassword from "./container/ForgotPassword/ForgotPassword";
import ResetPassword from "./container/resetPassword/ResetPassword";
import Home from "./container/Home/HomeTailwind";
import Galerie2 from "./container/Galerie/Galerie2"
import PostGalerie from "./container/Galerie/postGalerie"
import EditGalerie from "./container/Galerie/editGalerie";
import BildErstellen from "./container/BildAnsicht/BildErstellen"
import Login from "./container/Login/Login";
import ResetPasswortSuccess from "./container/ForgotPassword/ForgetPasswordSuccess"
import NotFound from "./container/404/404";
import Bearbeiten from "./container/ProfilBearbeiten/bearbeiten";
import DeleteUser from "./container/DeleteUser/deleteUser"
import DeleteUserAnswer from "./container/DeleteUser/deleteUserAnswer"
import DetailImage from "./container/BildAnsicht/ImageView"
import ImageUploadComponent from "./container/BildAnsicht/imageUpload";
import editGalerie from "./container/Galerie/editGalerie"
import BildBearbeiten from "./container/BildAnsicht/BildBearbeiten"
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap/dist/js/bootstrap.bundle.min";
import Album from "./container/loggedInUsers/GalleryForAll/GalleryForAll"
import GallerieOfOtherUser from "./container/loggedInUsers/GalleryForAll/galerieOfOtherUser"
import AlbumArtWork from "./container/loggedInUsers/ArtworkForAll/ArtWorkGallerie"
import DetailedImage from"./container/loggedInUsers/ArtworkForAll/DetailedImage"
function App() {
  return (
    <Layout>
      <Routes>
        <Route path="/" element={<Home />}></Route>{/*ausgeloggte Homeseite*/}
        <Route path="/register" element={<Registration />}></Route>
        <Route path="/login" element={<Login />} />
        {/*Forgot Password routes*/}
        <Route path ="/forgot" element ={<ForgotPassword/>}/>
        <Route path ="/resetSuccess" element ={<ResetPasswortSuccess/>}/>
        <Route path ="/reset-password" element ={<ResetPassword/>}/>
        {/*GalleryForAll*/}
        <Route path="/galleryOtherUser/:id" element ={<GallerieOfOtherUser/>}></Route>
        <Route path="/openGallery" element={<Album/>}></Route>
        {/*Artwork for All*/}
        <Route path="/openArtwork" element={<AlbumArtWork/>}></Route>
        <Route path="/openArtwork/:id" element={<DetailedImage/>}></Route>
          {/*User bearbeiten*/}

          <Route  element={<ProtectedRoutes />}>
              {/*Protected Routes*/}
            {/*User Routen*/}
            <Route path="/bearbeiten" element={<Bearbeiten />}></Route>
            <Route path="/deleteUser" element ={<DeleteUser/>}></Route>
            <Route path="/deleteUserAnswer" element ={<DeleteUserAnswer/>}></Route>

            {/*Galerie Routen*/}
            <Route path="/galerie" element={<Galerie2 />}></Route> {/*Galerie eines Künstlers*/}
            <Route path ="/editGallery" element ={<EditGalerie/>}/>
            <Route path="/postGalerie/:id" element ={<PostGalerie/>}></Route>

            {/*Artwork Routen*/}
            <Route path="/uploadImage/:id" element ={<ImageUploadComponent/>}></Route>
            <Route path="/newArt" element={<BildErstellen/>}></Route>
            <Route path="/editArt" element ={<BildBearbeiten/>}></Route>
            <Route path="/galerie/DetailImage/:id" element={<DetailImage/>}></Route>
          </Route>
        <Route path='*' element={<NotFound/>}/>
      </Routes>
    </Layout>
  );
}

export default App;
