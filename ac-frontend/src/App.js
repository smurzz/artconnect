import logo from './logo.svg';
import './App.css';
import Login from './Components/login/login';
import Registration from './Components/registration/registration';

function App() {
  return (
    <Routes>
    <Route path="/register" element={<Registration />}></Route>
    <Route path="/login" element={<Login />} />
    <Route path="/protected" element={<Protected/>}/>
  </Routes>
  );
}

export default App;
