import * as authenticationActions from '../authentication/AuthenticationAction';

const initialState = {
    user: null,
    pending: false,
    errorSignup: null,
    status: '',
    message: '',
};

export default function authenticationReducer(state = initialState, action) {
    console.log('Bin in Reducer: ' + action.type);
    switch (action.type) {
        case authenticationActions.SING_UP_PENDING:
            return {
                ...state,
                pending: true,
            }
        case authenticationActions.SING_UP_SUCCESS:
            console.log("authenticationActions.SING_UP_SUCCESS: "+ action.status)
            return {
                ...state,
                pending: false,
                status: action.status,
                message: action.message,
            }
        case authenticationActions.SING_UP_ERROR:
            return {
                ...state,
                pending: false,
                errorSignup: action.errorSignup
            }
        default:
            return state;
    }
    }
