import jwt_decode from "jwt-decode";
import axios from "../../api/axios";

export const AUTHENTICATION_PENDING = 'AUTHENTICATION_PENDING';
export const AUTHENTICATION_SUCCESS = 'AUTHENTICATION_SUCCESS';
export const AUTHENTICATION_ERROR = 'AUTHENTICATION_ERROR';

export const SING_UP_PENDING = 'SING_UP_PENDING';
export const SING_UP_SUCCESS = 'SING_UP_SUCCESS';
export const SING_UP_ERROR = 'SING_UP_ERROR';

export function getSignupePendingAction() {
    return {
        type: SING_UP_PENDING,
    }
}

export function getSignupSuccessAction(res) {
    return {
        type: SING_UP_SUCCESS,
        status: res.status,
            message: res.data
    }
}

export function getSignupErrorAction(error) {
    return {
        type: SING_UP_ERROR,
        errorSignup: error,
    }
}

export function signupUser(user) {
    return dispatch => {
        dispatch(getSignupePendingAction());
        var _headers = {
            headers: {
                "Content-Type": "application/json",
            },
        };
        axios.post('/auth/register', JSON.stringify(user), _headers)
            .then(response => {
                console.log(JSON.stringify(response.data));
                dispatch(getSignupSuccessAction(response))
            })
            .catch(error => {
                const errorMessage = error.response.data;
                dispatch(getSignupErrorAction(errorMessage));
            })

    }
}