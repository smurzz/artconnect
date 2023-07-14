import * as forgotPasswordActions from '../forgot-password/ForgotPasswordAction';

const initialState = {
    pending: false,
    error: null,
    status: ''
};

export default function forgotPasswordReducer(state = initialState, action) {

    console.log('Bin in Forgot-Password Reducer: ' + action.type);

    switch (action.type) {
        case forgotPasswordActions.FORGOT_PASSWORD_PENDING:
            return {
                ...state,
                pending: true,
                error: null
            }
        case forgotPasswordActions.FORGOT_PASSWORD_SUCCESS:
            return {
                ...state,
                pending: false,
                status: action.status
            }
        case forgotPasswordActions.FORGOT_PASSWORD_ERROR:
            return {
                ...state,
                pending: false,
                error: action.error
            }
        case forgotPasswordActions.RESET_PASSWORD_PENDING:
            return {
                ...state,
                pending: true,
                error: null
            }
        case forgotPasswordActions.RESET_PASSWORD_SUCCESS:
            return {
                ...state,
                pending: false,
                status: action.status,
            }
        case forgotPasswordActions.RESET_PASSWORD_ERROR:
            return {
                ...state,
                pending: false,
                error: action.error
            }
        default:
            return state;
    }

}