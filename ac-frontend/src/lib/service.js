import axios from "../api/axios";
import jwt_decode from "jwt-decode";
import TokenExpiredError from "./Exceptions/RefreshTokenExpiredError";
import { storageService } from "./localStorage";

//Refresh Tokenlogik
async function checkTokens(accessToken, refreshToken, accessTime, refreshTime){
    if (isTokenExpired(accessTime) && !isTokenExpired(refreshTime*2000)) {
        console.log("Old Token " +  accessToken);
        var _headers = {
            headers: {
                'Authorization': "Bearer " + refreshToken,
            },
        };
        await axios.post("/auth/refresh", null, _headers)
            .then(async response => {
                await storageService.saveToken(response);
                console.log("inside response, new tokens have been saved");
            })
            .catch(error => {
                throw new TokenExpiredError("something went wrong");
            })

    } else if (isTokenExpired(refreshTime)) {
        throw new TokenExpiredError("Refresh Tocken is expired");
    }
    return;
}

function isTokenExpired(expTime) {
    return Date.now() >= expTime ; // true
}

//private Routing Logik
async function isLoggedIn(){
    try {
        const token = await storageService.getTokenInformation();
        console.log("isLoggedIn: " + JSON.stringify(token));
        return true;
    }catch(error){
        return false;
    }
}
export const logikService = {
    checkTokens,
    isLoggedIn
}

