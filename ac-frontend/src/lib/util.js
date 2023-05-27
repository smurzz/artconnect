import axios from "../api/axios";
import jwt_decode from "jwt-decode";
import TokenExpiredError from "./Exceptions/RefreshTokenExpiredError"

async function handleExpirationToken(){
  /*
  let tokens = await getCurrentLokalStorage();
  let expSessionToken = tokens.refreshTime;
  let expAccessToken = tokens.tokenTime;
  if(Date.now() >= expSessionToken* 1000 ){ //nur damit wir testen können, dass wenn session token abgelaufen ist, eine error Message geworfen wird
    throw new TokenExpiredError("Token has expired");
  }else{/*
    if(Date.now() >= expAccessToken){

    const response = await accessToken("/auth/refresh");*/
      //return false;
    //}
  return false;
  }


async function getCurrentLokalStorage() {
  const tokens = JSON.parse(localStorage.getItem("tokens"));
  console.log("currentToken: " + JSON.parse(tokens.accessToken));
  return tokens;
}
async function logout() {
  localStorage.removeItem("tokens");
}
function isLoggedIn(){
  const localStorage = getCurrentLokalStorage();
  if(localStorage.accessToken){
    console.log("localStorage: "+JSON.stringify(localStorage.accessToken));
    return true;
  }
  console.log("localStorage: "+JSON.stringify(localStorage.accessToken));
  return false;
}


export const AuthService = {
  logout,
  isLoggedIn
};
