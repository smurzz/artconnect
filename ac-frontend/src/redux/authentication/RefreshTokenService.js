import axios from "axios";
import jwt_decode from "jwt-decode";
import RefreshTokenExpiredError from "../exceptions/RefreshTokenExpiredError";

export async function checkTockens() {
    let sessionData = JSON.parse(localStorage.getItem('userSession'));

    let accessToken = sessionData.accessToken;
    let refreshToken = sessionData.refreshToken;
    
    let expAccessToken = sessionData.accessTokenData.exp;
    let expRefreshToken = sessionData.refreshTokenData.exp;

    if (isTokenExpired(expAccessToken) && !isTokenExpired(expRefreshToken)) {
        console.log("Old Token " +  accessToken);
        var _headers = {
            headers: {
                'Authorization': "Bearer " + refreshToken,
            },
        };
        await axios.post("/auth/refresh", null, _headers)
            .then(async response => {
                var newToken = await response.data.access_token;
                var accessTokenData = await jwt_decode(response.data.access_token);
                console.log("New Token " + newToken);
                var tokens = JSON.parse(localStorage.getItem("userSession"));
                tokens.accessToken = newToken;
                tokens.accessTokenData = accessTokenData;
                localStorage.setItem("userSession", JSON.stringify(tokens));
                console.log("New Token saved " + JSON.parse(localStorage.getItem("userSession")).accessToken);
            })
            .catch(error => {
                console.error(error);
            })
    } else if (isTokenExpired(expRefreshToken)) {
        throw new RefreshTokenExpiredError("Refresh Tocken is expired");
    }
}

function isTokenExpired(expTime) {
    return Date.now() >= expTime * 1000; // true;
}