import React from "react";
import { useLocation } from "react-router-dom";
import { AuthService } from "../lib/util";
import axios from "axios";

function Protected() {
  const location = useLocation();
  const saveToken = async (e) => {
    console.log("inside Protected: ", AuthService.getCurrentToken());
    const token = AuthService.getCurrentToken();
    const requestOptions = {
      headers: {
        Authorization:
          "Bearer " + JSON.parse(localStorage.getItem("accessToken")),
      },
    };

    axios
      .get("http/localhost:8080/", requestOptions)
      .then((response) => {
        console.log(
          JSON.stringify(response),
          "local: " + localStorage.getItem("accessToken")
        );
      })
      .catch((error) => {
        console.log(error, "local: " + localStorage.getItem("accessToken"));
      });
  };

  return (
    <div>
      <h1>{location.state.message}</h1>
      <button onClick={saveToken}></button>
    </div>
  );
}

export default Protected;
