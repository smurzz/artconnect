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
            console.log("Api Service - sendImage: no Token")
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
        console.log("Api Service - sendImage: success")
        return "success";
    }catch(error){
        console.log("Api Service - sendImage: error")
        return null;
    }
}


//Gallerie Endpunkte
async function postGalerieWithId(param, payload) {

}

async function getGalerieByUserid(param) {
    try {
            //relative URL und Body mit JSON
            const tokenInfo = await storageService.getTokenInformation();
            console.log("getDataSecured: " + JSON.stringify(tokenInfo));
            if (!tokenInfo) return null;
            const tokensValid = await logikService.checkTokens(tokenInfo.accessToken, tokenInfo.refreshToken, tokenInfo.tokenTime, tokenInfo.refreshTime);
            var _headers = {
                headers: {
                    Authorization: "Bearer " + tokenInfo.accessToken,
                },
            };
        let result;
        if (!param) {
            console.log("securedData Error");
        } else {
            let url = "/galleries/" + param;
            result = await axios.get(url, _headers);
        }

        return result;
    } catch (error) {
        console.log("getImage Error");
        return null;
    }
}

//Bilder Endpunkte
/**
 * liefert alle Bilder in der Datenbank zurück
 * @return returned ein JSON mit allen Bildern
 * */
async function getAllImages() {
    try {
        var _headers = {
            headers: {
                "Content-Type": "application/json",
            },
        };
        let result = await axios.get(IMAGES_URL, _headers);
        if (result.status === 200) {
            return result;
        }
    } catch (err) {
        return "Something went wrong";
    }
}

/**
 * lieft ein Bild anhand seiner Id zurück
 * @param param id des Bildes
 * @param publicGalerie wenn true, dann wird der Header ohne Token gesendet, wenn false dann mit Token
 * @return gibt einen Json mit der Bild Info zurück, wenn ein Fehler auftritt gibt er den String: "getImage Error" zurück
 * */
async function getAllImagesWithId(param, publicGalerie) {
    try {
        if (publicGalerie) {
            var _headers = {
                headers: {
                    "Content-Type": "application/json",
                },
            };
        } else {
            //relative URL und Body mit JSON
            const tokenInfo = await storageService.getTokenInformation();
            console.log("getDataSecured: " + JSON.stringify(tokenInfo));
            if (!tokenInfo) return null;
            const tokensValid = await logikService.checkTokens(tokenInfo.accessToken, tokenInfo.refreshToken, tokenInfo.tokenTime, tokenInfo.refreshTime);
            var _headers = {
                headers: {
                    Authorization: "Bearer " + tokenInfo.accessToken,
                },
            };
        }

        let result;
        if (!param) {
            console.log("securedData Error");
        } else {
            let url = IMAGES_URL + param;
            result = await axios.get(url, _headers);
        }

        return result;
    } catch (error) {
        console.log("getImage Error");
        return null;
    }
}

/**
 * Upload GalerieBild
 * @param param id des Users
 * @param payload der gespeichert werden soll
 * @return gibt einen Json mit der Bild Info zurück, wenn ein Fehler auftritt gibt er den String: "getImage Error" zurück
 * */
async function postImagesWithId(param, payload) {
    const tokenInfo = await storageService.getTokenInformation();
    console.log("getDataSecured: " + JSON.stringify(tokenInfo));
    if (!tokenInfo) return null;
    try {
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
            axios.post(POST_IMGES_URL + param, payload, _headers)
                .then(response => {
                    const photoData = btoa(
                        new Uint8Array(response.data).reduce((data, byte) => data + String.fromCharCode(byte), '')
                    );
                    const contentType = response.headers['content-type'];
                    const imageUrl = `data:${contentType};base64,${photoData}`;
                })
        }
        console.log("Api Service - sendImage: success")
        return "success";
    } catch (error) {
        return error;
    }
}


/**
 * Upload Galeriebild Info
 * @param param id des Users
 * @param payload der gespeichert werden soll
 * @return gibt einen Json mit der Bild Info zurück, wenn ein Fehler auftritt gibt er den String: "getImage Error" zurück
 * */
async function postArtWorkinfo(payload){
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
            let result = await axios.post(IMAGES_URL ,payload, _headers);
        }
        return "success";
    }catch(error){
        return error;
    }
}

    export const GalerieApiService = {
        getUnsecuredData,
        getSecuredData,
        postSecuredData,
        putSecuredData,
        postSecuredImage,

        postGalerieWithId,
        postImagesWithId,
        getAllImagesWithId,
        getAllImages,
        getGalerieByUserid
    }

