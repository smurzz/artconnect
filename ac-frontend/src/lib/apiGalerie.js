import axios from "../api/axios";
import {storageService} from "./localStorage"
import {logikService} from "./service"

const IMAGES_URL = "/artworks/";
const POST_IMGES_URL = "/artworks/add-photo/";


//Endpunkte
async function getUnsecuredData(url){
    try {
            var _headers = {
                headers: {
                    "Content-Type": "application/json",
                },
            };
            let result = await axios.get(url, _headers);
        return result;
        console.log("getUnsecuredDataSuccess")
    } catch (error) {
        console.log("getUnsecuredDataError")
        return null;
    }
}

async function getSecuredData(url){
    if(!url){
        return null;
    }
    try {
        console.log("getSecuredData: "+ url)
        const tokenInfo = await storageService.getTokenInformation();
        console.log("getDataSecured: " + JSON.stringify(tokenInfo));
        if (!tokenInfo) return null;
        const tokensValid = await logikService.checkTokens(tokenInfo.accessToken, tokenInfo.refreshToken, tokenInfo.tokenTime, tokenInfo.refreshTime);
        var _headers = {
            headers: {
                Authorization: "Bearer " + tokenInfo.accessToken,
            },
        };
        console.log("getSecuredData: "+url);
          let  result = await axios.get(url, _headers);
        return result;
    } catch (error) {
        return null;
    }

}

async function postSecuredData(url,payload){
    if(!url || !payload){
        return null;
    }
    const tokenInfo = await storageService.getTokenInformation();
    console.log("getDataSecured: " + JSON.stringify(tokenInfo));
    if (!tokenInfo) return null;
    try {
        const tokensValid = await logikService.checkTokens(tokenInfo.accessToken, tokenInfo.refreshToken, tokenInfo.tokenTime, tokenInfo.refreshTime);
        console.log("token: "+ tokensValid);
        var _headers = {
            headers: {
                Authorization: "Bearer " + tokenInfo.accessToken,
            },
        };
            console.log("inside patch data secured: ");
            console.log(url)
            let result = await axios.post(url,payload, _headers);
            console.log("result");
        return result;
    } catch (error) {
        return null;
    }
}
async function putSecuredData(url,payload){
    if(!url || !payload){
        return null;
    }
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
            let result = await axios.put(url,payload, _headers);
        return result;
    }catch(error){
        return null;
    }
}

async function deleteSecuredData(url) {
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
        axios.delete(url, _headers)
            .then(response => {
                console.log("User was deleted");
            })

        console.log("Api Service - sendImage: success")
        return "success";
    }catch(error){
        console.log("Api Service - sendImage: error")
        return null;
    }
}

async function postSecuredImage(url,payload){
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
            console.log("Api Service - sendImage: no Token")
            return null;
        } else {
            axios.post(url, payload, _headers)
                .then(response => {
                    const photoData = btoa(
                        new Uint8Array(response.data).reduce((data, byte) => data + String.fromCharCode(byte), '')
                    );
                    const contentType = response.headers['content-type'];
                    const imageUrl = `data:${contentType};base64,${photoData}`;
                })
        }
        return "success";
    }catch(error){
        console.log("Api Service - sendImage: error")
        return null;
    }
}

async function putSecuredParameter(url){
    if(!url){
        return null;
    }
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
        let result = await axios.put(url, _headers);
        return result;
    }catch(error){
        return null;
    }
}
    export const GalerieApiService = {
        getUnsecuredData,
        getSecuredData,
        postSecuredData,
        putSecuredData,
        postSecuredImage,
        deleteSecuredData,
        putSecuredParameter
    }

