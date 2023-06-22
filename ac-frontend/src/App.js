import Registration from "./container/Registration/Registration";
import { Route, Routes, BrowserRouter } from "react-router-dom";
import Layout from "./components/Layout/Layout";
import ProtectedRoutes from "./container/protectedRoutes";
import ForgotPassword from "./container/ForgotPassword/ForgotPassword";
import ResetPassword from "./container/resetPassword/ResetPassword";
import Home from "./container/Home3/HomeTailwind";
import Galerie2 from "./container/Galerie/Galerie2";
import Login from "./container/Login/Login";
import ResetPasswortSuccess from "./container/ForgotPassword/ForgetPasswordSuccess"
import NotFound from "./container/404/404";
import DetailImage from "./container/BildAnsicht/ImageView"
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap/dist/js/bootstrap.bundle.min";
import Bearbeiten from "./container/bearbeiten"

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
          <Route path="/galerie/DetailImage" element={<DetailImage/>}></Route>
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
