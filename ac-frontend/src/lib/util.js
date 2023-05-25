import axios from "../api/axios";

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

async function postDataSecured(url, header) {
  var _headers = {
    headers: {
      header
    },
  };
  let result = await axios.get(url, _headers);
  if (result.status === 200) {
    return result;
  }
}

async function saveToken(response) {
  console.log("saveToken: " + response.data.access_token);
  if (response.data.access_token) {
    localStorage.setItem(
      "accessToken",
      JSON.stringify(response.data.access_token)
    );
    localStorage.setItem(
      "refreshToken",
      JSON.stringify(response.data.refresh_token)
    );
  }

  return response.data;
}

async function getCurrentToken() {
  return JSON.parse(localStorage.getItem("accessToken"));
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
  postDataSecured,
};
