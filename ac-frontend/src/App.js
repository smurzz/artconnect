import Registration from "./container/Registration/Registration";
import { Route, Routes, BrowserRouter } from "react-router-dom";
import Layout from "./components/Layout/Layout";
import ProtectedRoutes from "./container/protectedRoutes";
import ForgotPassword from "./container/ForgotPassword/ForgotPassword";
import ResetPassword from "./container/resetPassword/ResetPassword";
import LandingPage from "./container/Landingpage"
import Home from "./container/Home/home";
import HomeTailwind from "./container/Home3/HomeTailwind";
import Galerie from "./container/Galerie/Galerie";
import Galerie2 from "./container/Galerie/Galerie2";
import Login from "./container/Login/Login";
import ResetPasswortSuccess from "./container/ForgotPassword/ForgetPasswordSuccess"
import Protected from "./container/protected";
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap/dist/js/bootstrap.bundle.min";

function App() {
  return (
    <Layout>
      <Routes>
        <Route path="/home " element={<Home />}></Route>
        <Route path="/" element={<HomeTailwind />}></Route>
        <Route path="/register" element={<Registration />}></Route>
        <Route path="/login" element={<Login />} />
          <Route path ="/forgot" element ={<ForgotPassword/>}/>
        <Route path ="/resetSuccess" element ={<ResetPasswortSuccess/>}/>
        <Route path ="/reset-password" element ={<ResetPassword/>}/>
        <Route path ="/l" element ={<LandingPage/>}/>
          <Route element={<ProtectedRoutes />}>
            <Route path="/protected" element={<Protected />} />
            <Route path="/galerie" element={<Galerie2 />}></Route>
          </Route>
      </Routes>
    </Layout>
  );
}

export default App;
