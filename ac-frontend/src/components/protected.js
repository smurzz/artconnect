import React, { useEffect, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import { ApiService } from "../lib/api";
import axios from "axios";

function Protected() {
    const navigate = useNavigate();
  const [securedData, setSecuredData] = useState(null);

  useEffect(() => {
    ApiService.getDataSecured("/")
      .then((response) => {
          if(response !== null){
              console.log(response);
              setSecuredData(response.data);
          }else{
              setSecuredData("token is expired");
              navigate("/login");
          }
      })
  }, []);

  return (
    <div>
        <h1>Test Test</h1>
      <h2>{securedData}</h2>
    </div>
  );
}

export default Protected;
