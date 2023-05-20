import React from "react";
import { useLocation } from "react-router-dom";
import { AuthService } from "../lib/util";

function Protected() {
  const location = useLocation();
  const saveToken = async (e) => {
    console.log("inside Protected: ", AuthService.getCurrentToken());
  };
  return (
    <div>
      <h1>{location.state.message}</h1>
      <button onClick={saveToken}></button>
    </div>
  );
}

export default Protected;
