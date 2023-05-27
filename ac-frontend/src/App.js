import Login from "./components/login/login";
import Registration from "./components/registration/registration";
import Protected from "./components/protected";
import { Route, Routes, BrowserRouter } from "react-router-dom";
import Layout from "./components/Layout/Layout";
import ProtectedRoutes from "./components/protectedRoutes";
import ForgotPassword from "./components/forgotPassword";
import ResetPassword from "./components/resetPassword";

function App() {
  return (
    <Layout>
      <Routes>
        <Route path="/register" element={<Registration />}></Route>
        <Route path="/login" element={<Login />} />
          <Route path ="/forgot" element ={<ForgotPassword/>}/>
        <Route path ="/reset-password" element ={<ResetPassword/>}/>
          <Route element={<ProtectedRoutes />}>
        <Route path="/protected" element={<Protected />} />
          </Route>
      </Routes>
    </Layout>
  );
}

export default App;
