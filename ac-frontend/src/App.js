import Registration from "./container/Registration/Registration";
import { Route, Routes, BrowserRouter } from "react-router-dom";
import Layout from "./components/Layout/Layout";
import ProtectedRoutes from "./container/protectedRoutes";
import ForgotPassword from "./container/ForgotPassword/ForgotPassword";
import ResetPassword from "./container/resetPassword/ResetPassword";
import Home from "./container/Home/HomeTailwind";
import Galerie2 from "./container/Galerie/Galerie2";
import Login from "./container/Login/Login";
import ResetPasswortSuccess from "./container/ForgotPassword/ForgetPasswordSuccess"
import NotFound from "./container/404/404";
import Bearbeiten from "./container/ProfilBearbeiten/bearbeiten";
import DeleteUser from "./container/DeleteUser/deleteUser"
import DeleteUserAnswer from "./container/DeleteUser/deleteUserAnswer"
import DetailImage from "./container/BildAnsicht/ImageView"
import DetailImage1 from "./container/BildAnsicht/ImageView1"
import DetailImage2 from "./container/BildAnsicht/ImageView2"
import DetailImage3 from "./container/BildAnsicht/ImageView3"
import DetailImage4 from "./container/BildAnsicht/ImageView4"
import DetailImage5 from "./container/BildAnsicht/ImageView5"
import DetailImage6 from "./container/BildAnsicht/ImageView6"
import DetailImage7 from "./container/BildAnsicht/ImageView7"
import DetailImage8 from "./container/BildAnsicht/ImageView8"

import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap/dist/js/bootstrap.bundle.min";

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
          {/*Galerie*/}
          <Route path="/galerie" element={<Galerie2 />}></Route> {/*Galerie eines Künstlers*/}
          <Route path="/galerie/DetailImage/1" element={<DetailImage/>}></Route>
        <Route path="/deleteUser" element ={<DeleteUser/>}></Route>
          <Route path="/galerie/DetailImage/2" element={<DetailImage1/>}></Route>
          <Route path="/galerie/DetailImage/3" element={<DetailImage2/>}></Route>
          <Route path="/galerie/DetailImage/4" element={<DetailImage3/>}></Route>
          <Route path="/galerie/DetailImage/5" element={<DetailImage4/>}></Route>
          <Route path="/galerie/DetailImage/6" element={<DetailImage5/>}></Route>
          <Route path="/galerie/DetailImage/7" element={<DetailImage6/>}></Route>
          <Route path="/galerie/DetailImage/8" element={<DetailImage7/>}></Route>
          <Route path="/galerie/DetailImage/9" element={<DetailImage8/>}></Route>
        <Route path="/deleteUserAnswer" element ={<DeleteUserAnswer/>}></Route>
          {/*User bearbeiten*/}
            <Route path="/bearbeiten" element={<Bearbeiten />}></Route>
          <Route  element={<ProtectedRoutes />}>
              {/*Protected Routes*/}
          </Route>
        <Route path='*' element={<NotFound/>}/>
      </Routes>
    </Layout>
  );
}

export default App;
