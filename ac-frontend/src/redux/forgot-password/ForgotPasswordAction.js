import axios from "axios";

export const FORGOT_PASSWORD_PENDING = 'FORGOT_PASSWORD_PENDING';
export const FORGOT_PASSWORD_SUCCESS = 'FORGOT_PASSWORD_SUCCESS'
export const FORGOT_PASSWORD_ERROR = 'FORGOT_PASSWORD_ERROR'

export const RESET_PASSWORD_PENDING = 'RESET_PASSWORD_PENDING'
export const RESET_PASSWORD_SUCCESS = 'RESET_PASSWORD_SUCCESS'
export const RESET_PASSWORD_ERROR = 'RESET_PASSWORD_ERROR'

export function getForgotPasswordPendingAction() {
    return {
        type: FORGOT_PASSWORD_PENDING
    }
}

export function getForgotPasswordSuccessAction(res) {
    return {
        type: FORGOT_PASSWORD_SUCCESS,
        status: res.status
    }
}

export function getForgotPasswordErrorAction(error) {
    return {
        type: FORGOT_PASSWORD_ERROR,
        error: error
    }
}

export function getResetPasswordPendingAction() {
    return {
        type: RESET_PASSWORD_PENDING
    }
}

export function getResetPasswordSuccessAction(res) {
    return {
        type: RESET_PASSWORD_SUCCESS,
        status: res.status
    }
}

export function getResetPasswordErrorAction(error) {
    return {
        type: RESET_PASSWORD_ERROR,
        error: error
    }
}

export function forgotPassword(userEmail) {
    return dispatch => {
        dispatch(getForgotPasswordPendingAction());
        axios.get('/forgot-password', { params: { email: userEmail } })
            .then(response => {
                dispatch(getForgotPasswordSuccessAction(response))
            })
            .catch(error => {
                dispatch(getForgotPasswordErrorAction(error.response.data));
            })

    }
}

export function resetPassword(resetPassword) {
    return dispatch => {
        dispatch(getResetPasswordPendingAction());
        
        const requestOptions = {
            headers: {
                'Content-Type': 'application/json'
            }
        };
        axios.post('/reset-password', resetPassword, requestOptions)
            .then(response => {
                dispatch(getResetPasswordSuccessAction(response))
            })
            .catch(error => {
                dispatch(getResetPasswordErrorAction(error.response.data));
            })
    }
}