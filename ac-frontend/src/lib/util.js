import axios from "../api/axios";
import jwt_decode from "jwt-decode";
import TokenExpiredError from "./Exceptions/RefreshTokenExpiredError"

async function postData(url, payload) {
  var _headers = {
    headers: {
      "Content-Type": "application/json",
    },
  };
  let result = await axios.post(url, JSON.stringify(payload), _headers);
  if (result.status === 200) {
    return result;
  }
}

async function getDataSecured(url, payload) {
  let tokenExpried = await handleExpirationToken();
  if (tokenExpried === true) {
    throw new TokenExpiredError("Token has expired");
  } else {
    const storage = await getCurrentLokalStorage();
    const token = JSON.parse(storage.accessToken);
    var _headers = {
      headers: {
        Authorization: "Bearer " + token,
      },
    };
    let result;
    if (!payload) {
      result = await axios.get(url, _headers);
    } else {
      result = await axios.get(url, _headers);
    }

    if (result.status === 200) {
      return result;
    }
  }
}

async function accessToken(url) {
    const storage = await getCurrentLokalStorage();
    const token = JSON.parse(storage.refreshToken);
    let _headers = {
      headers: {
        "Authorization": "Bearer " + token,
      },
    };
    let result;
    console.log("inside accessToken: "+ token);
      result = await axios.post(url, _headers);
    if (result.status === 200) {
      return result;
    }
  }

async function handleExpirationToken(){
  let tokens = await getCurrentLokalStorage();
  let expSessionToken = tokens.refreshTime;
  let expAccessToken = tokens.tokenTime;
  if(Date.now() >= expSessionToken* 1000 ){ //nur damit wir testen können, dass wenn session token abgelaufen ist, eine error Message geworfen wird
    throw new TokenExpiredError("Token has expired");
  }else{
    /*
    if(Date.now() >= expAccessToken){
      let email = localStorage.getItem("email");
      console.log("Email: "+email);
    const response = await accessToken("/auth/refresh");
      return false;
    }
    console.log("has to Refresh: inside else");*/
    return false;
  }
  return false;
}
async function saveToken(response){
  const accessToken = response.data.access_token;
  const refreshToken = response.data.refresh_token;
  console.log("saveToken inhalt: "+ JSON.stringify(response));
  if (accessToken && refreshToken) {
    const tokenTime = jwt_decode(accessToken).exp;
    const refreshTime = jwt_decode(refreshToken).exp;
    const tokens = {
      accessToken: JSON.stringify(accessToken),
      refreshToken: JSON.stringify(refreshToken),
      tokenTime: tokenTime,
      refreshTime: refreshTime,
    };
    localStorage.setItem("tokens", JSON.stringify(tokens));
  }
  return response.data;
}

async function saveUserInfo(user){
  localStorage.setItem("email", user);
}

async function getCurrentLokalStorage() {
  const tokens = JSON.parse(localStorage.getItem("tokens"));
  console.log("currentToken: " + JSON.parse(tokens.accessToken));
  return tokens;
}
async function logout() {
  localStorage.removeItem("accessToken");
  localStorage.removeItem("refreshToken");
}

export const AuthService = {
  logout,
  postData,
  saveToken,
  getDataSecured,
  saveUserInfo
};
