import axios from "../api/axios";
import { storageService } from "./localStorage"
import { logikService } from "./service"

const REGISTER_URL = "/auth/register";
const LOGIN_URL = "/auth/login";
const RESET_PASSWORD = "/reset-password"
async function postRegister(payload) {
    try {
        var _headers = {
            headers: {
                "Content-Type": "application/json",
            },
        };
        let result = await axios.post(REGISTER_URL, JSON.stringify(payload), _headers);
        if (result.status === 200) {
            return "success";
        }
    }catch (err) {
        if (!err?.response) {
            return "No Server Response";
        } else if (err.response?.status === 400) {
            return "Email Taken";
        } else {
            return "Registration Failed, try again later";
        }
    }
}

async function postResetPassword(payload) {
    try {
        var _headers = {
            headers: {
                "Content-Type": "application/json",
            },
        };
        let result = await axios.post(RESET_PASSWORD, JSON.stringify(payload), _headers);
        if (result.status === 200) {
            return "success";
        }
    }catch (err) {
        if (!err?.response) {
            console.log(err?.response)
            return "No Server Response";
        } else if (err.response?.status === 400) {
            console.log(err?.response)
            console.log("errResponse: "+ JSON.stringify(err.response))
            return "the password has to contain at least 3. Please try again";
        }else if(err.response?.status === 500){
            console.log(err?.response)
            console.log("errResponse: "+ JSON.stringify(err.response))
            return JSON.stringify(err.response?.data.message);
        }
        else {
            console.log("errResponse: "+ JSON.stringify(err.response))
            return err.response.data;
        }
    }
}

async function postLogin(payload) {
        try {
            var _headers = {
                headers: {
                    "Content-Type": "application/json",
                },
            };
            let result = await axios.post(LOGIN_URL, JSON.stringify(payload), _headers);
            const saveToken = await storageService.saveToken(result);
            return saveToken;
            }catch (err) {
            if (err.response?.status === 403) {
                return "Please confirm the Registration Email, we send to you";
            } else if(err.response?.status === 401){
                return "Your password or Email is wrong";
            }else{
                return "Login Failed";
            }
        }
    }
async function getDataSecured(url, payload) {
    //relative URL und Body mit JSON
    const tokenInfo = await storageService.getTokenInformation();
    console.log("getDataSecured: "+ JSON.stringify(tokenInfo));
    if(!tokenInfo) return null;
    try{
        const tokensValid = await logikService.checkTokens(tokenInfo.accessToken, tokenInfo.refreshToken, tokenInfo.tokenTime, tokenInfo.refreshTime);
        var _headers = {
            headers: {
                Authorization: "Bearer " + tokenInfo.accessToken,
            },
        };
            let result;
            if (!payload) {
                result = await axios.get(url, _headers);
            } else {
                result = await axios.get(url, _headers);
            }
            return result;
    }catch(error){
        //Logged den User bei einem Error aus
        storageService.logout();
        return null;
    }
}
async function patchdataSecured(url, payload) {
    const tokenInfo = await storageService.getTokenInformation();
    console.log("getDataSecured: "+ JSON.stringify(tokenInfo));
    if(!tokenInfo) return null;
    try{
        const tokensValid = await logikService.checkTokens(tokenInfo.accessToken, tokenInfo.refreshToken, tokenInfo.tokenTime, tokenInfo.refreshTime);
        var _headers = {
            headers: {
                Authorization: "Bearer " + tokenInfo.accessToken,
            },
        };
        let result;
        if (!payload) {
            return null;
        } else {
            console.log("inside patch data secured: ");
            let result = await axios.put(url,payload, _headers);
        }
        return result;
    }catch(error){
        return null;
    }
}
async function sendImage( payload) {
    const tokenInfo = await storageService.getTokenInformation();
    console.log("getDataSecured: "+ JSON.stringify(tokenInfo));
    if(!tokenInfo) return null;
    try{
        const tokensValid = await logikService.checkTokens(tokenInfo.accessToken, tokenInfo.refreshToken, tokenInfo.tokenTime, tokenInfo.refreshTime);
        var _headers = {
            headers: {
                Authorization: "Bearer " + tokenInfo.accessToken,
                "Content-type": "multipart/form-data"
            },
        };
        let result;
        if (!payload) {
            return null;
        } else {
            axios.post('/users/profile-photo', payload, _headers)
                .then(response => {
                    const photoData = btoa(
                        new Uint8Array(response.data).reduce((data, byte) => data + String.fromCharCode(byte), '')
                    );
                    const contentType = response.headers['content-type'];
                    const imageUrl = `data:${contentType};base64,${photoData}`;
                })
        }
        return result;
    }catch(error){
        return null;
    }
}


export const ApiService = {
    postRegister,
    postLogin,
    getDataSecured,
    postResetPassword,
    patchdataSecured,
    sendImage
}