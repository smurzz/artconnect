import Login from "./components/login/login";
import Registration from "./components/registration/registration";
import Protected from "./components/protected";
import { Route, Routes, BrowserRouter } from "react-router-dom";
import Layout from "./components/Layout/Layout";

function App() {
  return (
    <Layout>
      <Routes>
        <Route path="/register" element={<Registration />}></Route>
        <Route path="/login" element={<Login />} />
        <Route path="/protected" element={<Protected />} />
      </Routes>
    </Layout>
  );
}

export default App;
