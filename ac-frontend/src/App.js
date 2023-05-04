import logo from './logo.svg';
import './App.css';
import React from "react";
import axios from "axios";

const baseURL = "http://localhost:8080/auth/";
/* "start": "set PORT=3001 && react-scripts start", */

function App() {
  const [post, setPost] = React.useState(null);

  React.useEffect(() => {
    axios.get(baseURL).then((response) => {
      setPost(response.data);
    });
  }, []);

  if (!post) return null;

  return (
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <p>
          Message from ArtConnect Backend:
        </p>
        <p>{post}</p>
      </header>
    </div>
  );
}

export default App;
