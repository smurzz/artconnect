import axios from "../api/axios";
import jwt_decode from "jwt-decode";
import TokenExpiredError from "./Exceptions/RefreshTokenExpiredError"
async function saveToken(response){
    const accessToken = response.data.access_token;
    const refreshToken = response.data.refresh_token;
    if (accessToken && refreshToken) {
        const tokenTime = jwt_decode(accessToken).exp;
        const refreshTime = jwt_decode(refreshToken).exp;
        const tokens = {
            accessToken: JSON.stringify(accessToken),
            refreshToken: JSON.stringify(refreshToken),
            tokenTime: tokenTime,
            refreshTime: refreshTime,
        };
        console.log("tokens: "+ JSON.stringify(tokens));
       await localStorage.setItem("tokens", JSON.stringify(tokens));
           return "success";
    }
    return "something went wrong";
}
async function getTokenInformation(){
    const storage = await getLocalStorage();
    const accessToken = JSON.parse(storage.accessToken);
    const refreshToken = JSON.parse(storage.refreshToken);
    const tokenTime = storage.tokenTime;
    const refreshTime = storage.refreshTime;
    const tokens = {
        accessToken: accessToken,
        refreshToken: refreshToken,
        tokenTime: tokenTime,
        refreshTime: refreshTime,
    };

    return tokens;
}

async function getRefreshToken(){
    const storage = await getLocalStorage();
    const refreshToken = JSON.parse(storage.refreshToken);
    return refreshToken;
}

async function getLocalStorage() {
    const tokens = JSON.parse(localStorage.getItem("tokens"));
    console.log("inside localstorage:" + tokens.accessToken);
    return tokens;
}

async function logout() {
    await localStorage.removeItem("tokens");
    await localStorage.removeItem("userObject");
    console.log("logout localStorage");
    return "success";
}

async function setUser(userObject){
    return await localStorage.setItem("userObject", userObject);
}

async function getUser() {
    const user = JSON.stringify(localStorage.getItem("userObject"));
    return user;
}

export const storageService = {
    setUser,
    getUser,
    saveToken,
    getTokenInformation,
    getRefreshToken,
    logout
};
