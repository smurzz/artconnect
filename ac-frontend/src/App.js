import logo from "./logo.svg";
import "./App.css";
import Login from "./components/login/login";
import Registration from "./components/registration/registration";
import Protected from "./components/protected";
import { Route, Routes, BrowserRouter } from "react-router-dom";

function App() {
  return (
    <Routes>
      <Route path="/register" element={<Registration />}></Route>
      <Route path="/login" element={<Login />} />
      <Route path="/protected" element={<Protected />} />
    </Routes>
  );
}

export default App;
