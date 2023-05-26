import React, { useEffect, useState } from "react";
import { useLocation } from "react-router-dom";
import { AuthService } from "../lib/util";
import axios from "axios";

function Protected() {
  const location = useLocation();
  const [securedData, setSecuredData] = useState(null);

  useEffect(() => {
    console.log("inside Protected: ", AuthService.getCurrentToken());
    const token = AuthService.getCurrentToken();
    console.log(token);
    AuthService.getDataSecured("/")
      .then((response) => {
        console.log(response);
        setSecuredData(response.data);
      })
      .catch((error) => {
        console.log(error);
        setSecuredData(error.message);
      });
  }, []);

  return (
    <div>
      <h1>{location.state.message}</h1>
      <h2>{securedData}</h2>
      {/* <button onClick={saveToken}></button> */}
    </div>
  );
}

export default Protected;
