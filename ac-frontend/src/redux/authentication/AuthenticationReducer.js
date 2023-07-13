import * as authenticationActions from '../authentication/AuthenticationAction';

const initialState = {
    pending: false,
    error: null,
    isAuthenticated: false,
    status: ''
};

export default function authenticationReducer(state = initialState, action) {

    console.log('Bin in Authentication Reducer: ' + action.type);

    switch (action.type) {
        case authenticationActions.AUTHENTICATION_PENDING:
            return {
                ...state,
                pending: true,
                error: null
            }
        case authenticationActions.AUTHENTICATION_SUCCESS:
            return {
                ...state,
                pending: false,
                status: action.status
            }
        case authenticationActions.AUTHENTICATION_ERROR:
            return {
                ...state,
                pending: false,
                error: action.error
            }
        case authenticationActions.SING_UP_PENDING:
            return {
                ...state,
                pending: true,
                error: null
            }
        case authenticationActions.SING_UP_SUCCESS:
            return {
                ...state,
                pending: false,
                status: action.status,
            }
        case authenticationActions.SING_UP_ERROR:
            return {
                ...state,
                pending: false,
                error: action.error
            }
        case authenticationActions.LOG_OUT:
            return {
                ...state,
                pending: false,
                error: null,
                status: ''
            }
        case authenticationActions.IS_AUTHENTICATED:
            return {
                ...state,
                pending: false,
                isAuthenticated: action.isAuthenticated
            }
        default:
            return state;
    }
};