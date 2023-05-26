import axios from "../api/axios";
import jwt_decode from "jwt-decode";

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
    return;
  } else {
    console.log(getCurrentToken());
    const bearerToken = await Json.parse(getCurrentToken().accessToken);
    var _headers = {
      headers: {
        Authorization: "Bearer " + bearerToken,
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

async function handleExpirationToken() {
  return false;
}

/*async function checkIfRefreshTokenAreExpired() {
  let expireAt = sessionData.user.exp;
  return Date.now() >= expireAt * 1000; /* true */
//}

/*export async function checkIfTokenAreExpired() {
  let isExpire = checkIfTokenIsExpired();

  if (isExpire) {
    let sessionData = JSON.parse(localStorage.getItem("userSession"));
    let refreshToken = sessionData.refreshToken;
    let email = sessionData.user.sub;

    var dataRequest = {
      email: email,
      refreshToken: refreshToken,
    };
    await axios
      .post("auth/refresh", dataRequest)
      .then(async (response) => {
        var newToken = response.data.access_token;
        var sessionData = JSON.parse(localStorage.getItem("userSession"));
        sessionData.accessToken = await newToken;
        localStorage.setItem("userSession", JSON.stringify(sessionData));
      })
      .catch(async (error) => {
        return Promise.reject(await error);
      });
  }
}*/

/*export const checkIfTokenIsExpired = () => {
  let expireAt = sessionData.user.exp;
  return Date.now() >= expireAt * 1000; /* true */
//};

async function saveToken(response) {
  const accessToken = response.data.access_token;
  const refreshToken = response.data.refresh_token;

  if (accessToken && refreshToken) {
    const tokenTime = jwt_decode(accessToken).exp;
    const refreshTime = jwt_decode(refreshToken).exp;
    const tokens = {
      accessToken: JSON.stringify(accessToken),
      refreshToken: JSON.stringify(refreshToken),
      tokenTime: tokenTime,
      refreshTIme: refreshTime,
    };

    localStorage.setItem("tokens", JSON.stringify(tokens));
  }

  return response.data;
}

async function getCurrentLokalStorage() {
  const tokens = JSON.parse(localStorage.getItem("tokens"));
  console.log("currentToken: " + JSON.parse(tokens.accessToken));
  return JSON.parse(tokens);
}

async function logout() {
  localStorage.removeItem("accessToken");
  localStorage.removeItem("refreshToken");
}

export const AuthService = {
  logout,
  postData,
  saveToken,
  getCurrentToken,
  getDataSecured,
};
