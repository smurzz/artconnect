import jwt_decode from "jwt-decode";
import axios from "axios";

export const AUTHENTICATION_PENDING = 'AUTHENTICATION_PENDING';
export const AUTHENTICATION_SUCCESS = 'AUTHENTICATION_SUCCESS';
export const AUTHENTICATION_ERROR = 'AUTHENTICATION_ERROR';

export const SING_UP_PENDING = 'SING_UP_PENDING';
export const SING_UP_SUCCESS = 'SING_UP_SUCCESS';
export const SING_UP_ERROR = 'SING_UP_ERROR';

export const LOG_OUT = 'LOG_OUT';
export const IS_AUTHENTICATED = 'IS_AUTHENTICATED';

export function getAuthenticateUserPendingAction() {
    return {
        type: AUTHENTICATION_PENDING
    }
}

export function getAuthenticationSuccessAction(res) {
    return {
        type: AUTHENTICATION_SUCCESS,
        status: res.status
    }
}

export function getAuthenticationErrorAction(error) {
    return {
        type: AUTHENTICATION_ERROR,
        error: error
    }
}

export function getSignupePendingAction() {
    return {
        type: SING_UP_PENDING
    }
}

export function getSignupSuccessAction(res) {
    return {
        type: SING_UP_SUCCESS,
        status: res.status
    }
}

export function getSignupErrorAction(error) {
    return {
        type: SING_UP_ERROR,
        error: error
    }
}

export function getLogOutAction(isAuth) {
    return {
        type: LOG_OUT,
        isAuthenticated: isAuth
    }
}

export function isAuthenticatedAction(isAuth) {
    return {
        type: IS_AUTHENTICATED,
        isAuthenticated: isAuth
    }
}

export function authenticateUser(email, password) {

    return dispatch => {

        dispatch(getAuthenticateUserPendingAction());
        login(email, password)
            .then(
                response => {
                    dispatch(getAuthenticationSuccessAction(response));
                },
                error => {
                    dispatch(getAuthenticationErrorAction(error));
                }
            )
            .catch(error => {
                dispatch(getAuthenticationErrorAction(error));
            })
    }
}

export function logoutUser() {
    logout();

    return dispatch => {
        const action = getLogOutAction(false);
        dispatch(action);
    }
}

export function signupUser(user) {
    return dispatch => {
        dispatch(getSignupePendingAction());
        const requestOptions = {
            headers: {
                'Content-Type': 'application/json'
            }
        };
        axios.post('/auth/register', user, requestOptions)
            .then(response => {
                dispatch(getSignupSuccessAction(response))
            })
            .catch(error => {
                dispatch(getSignupErrorAction(error.response.data));
            })

    }
}

export function isAuthenticatedUser() {
    const isAuth = JSON.parse(localStorage.getItem('userSession')) &&
        JSON.parse(localStorage.getItem('userSession')).accessToken !== null;

    return dispatch => {
        const action = isAuthenticatedAction(isAuth);
        dispatch(action);
    }
}

function login(email, password) {

    return axios.post('/auth/login', {
        email: email,
        password: password
    }).then(response => {
        var accessTokenData = jwt_decode(response.data.access_token);
        var refreshTokenData = jwt_decode(response.data.refresh_token);
        var userSession = {
            accessTokenData: accessTokenData,
            refreshTokenData: refreshTokenData,
            accessToken: response.data.access_token,
            refreshToken: response.data.refresh_token
        }
        localStorage.setItem('userSession', JSON.stringify(userSession));
        return response;
    })
        .catch(error => {
            return Promise.reject(error.response.data);
        });
}

function logout() {
    console.error('Logout user');
    localStorage.removeItem('userSession');
}