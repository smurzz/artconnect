import Registration from "./container/Registration/Registration";
import { Route, Routes, BrowserRouter } from "react-router-dom";
import Layout from "./components/Layout/Layout";
import ProtectedRoutes from "./components/protectedRoutes";
import ForgotPassword from "./container/ForgotPassword/ForgotPassword";
import ResetPassword from "./container/resetPassword/ResetPassword";
import Home from "./container/Home/home";
import Galerie from "./container/Galerie/Galerie";
import Login from "./container/Login/Login";

function App() {
  return (
    <Layout>
      <Routes>
        <Route path="/" element={<Home />}></Route>
        <Route path="/register" element={<Registration />}></Route>
        <Route path="/login" element={<Login />} />
          <Route path ="/forgot" element ={<ForgotPassword/>}/>
        <Route path ="/reset-password" element ={<ResetPassword/>}/>
          <Route element={<ProtectedRoutes />}>
            <Route path="/galerie" element={<Galerie />}></Route>
          </Route>
      </Routes>
    </Layout>
  );
}

export default App;
