import axios from "axios";
import * as refreshTokenService from '../authentication/RefreshTokenService';

export const SEND_MESSAGE_PENDING = 'SEND_MESSAGE_PENDING';
export const SEND_MESSAGE_SUCCESS = 'SEND_MESSAGE_SUCCESS';
export const SEND_MESSAGE_ERROR = 'SEND_MESSAGE_ERROR';

export function sentMessagePendingAction() {
    return {
        type: SEND_MESSAGE_PENDING
    }
}

export function sentMessageSuccessAction(res) {
    return {
        type: SEND_MESSAGE_SUCCESS,
        status: res.status
    }
}

export function sentMessageErrorAction(error) {
    return {
        type: SEND_MESSAGE_ERROR,
        error: error
    }
}

export function sentMessageByUserId(id, message) {
    return async dispatch => {
        dispatch(sentMessagePendingAction());

        await refreshTokenService.checkTockens()
            .then(() => {
                const requestOptions = {
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': 'Bearer ' + JSON.parse(localStorage.getItem('userSession')).accessToken
                    }
                };
                axios.post('/users/' + id + '/contact', message, requestOptions)
                    .then(response => {
                        dispatch(sentMessageSuccessAction(response))
                    })
                    .catch(error => {
                        dispatch(sentMessageErrorAction(error.response.data));
                    })
            })
            .catch(error => {
                dispatch(sentMessageErrorAction(error));
            });
    }
}

export function sentMessageByArtworkId(id, message) {
    return async dispatch => {
        dispatch(sentMessagePendingAction());

        await refreshTokenService.checkTockens()
            .then(() => {
                const requestOptions = {
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': 'Bearer ' + JSON.parse(localStorage.getItem('userSession')).accessToken
                    }
                };
                axios.post('/artworks/' + id + '/contact', message, requestOptions)
                    .then(response => {
                        dispatch(sentMessageSuccessAction(response))
                    })
                    .catch(error => {
                        dispatch(sentMessageErrorAction(error.response.data));
                    })
            })
            .catch(error => {
                dispatch(sentMessageErrorAction(error));
            });
    }
}

