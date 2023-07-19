import * as userAction from "./UserAction";

const initialState = {
    users: [],
    user: null,
    profilePhoto: null,
    status: null,
    pending: false,
    error: null
};

export default function userReducer(state = initialState, action) {

    console.log('Bin in User Reducer: ' + action.type);

    switch (action.type) {
        case userAction.REQUEST_READ_USERS:
            return {
                ...state,
                pending: true,
                error: null
            }
        case userAction.SUCCESS_READ_USERS:
            return {
                ...state,
                pending: false,
                users: action.users,
                status: action.status,
                error: null
            }
        case userAction.FAIL_READ_USERS:
            return {
                ...state,
                pending: false,
                error: action.error
            }
        case userAction.REQUEST_READ_USER:
            return {
                ...state,
                pending: true,
                error: null
            }
        case userAction.SUCCESS_READ_USER:
            return {
                ...state,
                pending: false,
                user: action.user,
                error: null
            }
        case userAction.FAIL_READ_USER:
            return {
                ...state,
                pending: false,
                error: action.error
            }
        case userAction.REQUEST_READ_PROFILE_PHOTO:
            return {
                ...state,
                pending: true,
                error: null
            }
        case userAction.SUCCESS_READ_PROFILE_PHOTO:
            return {
                ...state,
                pending: false,
                profilePhoto: action.photo,
                error: null
            }
        case userAction.FAIL_READ_PROFILE_PHOTO:
            return {
                ...state,
                pending: false,
                user: null,
                error: action.error
            }
        case userAction.REQUEST_CREATE_PROFILE_PHOTO:
            return {
                ...state,
                pending: true,
                error: null
            }
        case userAction.SUCCESS_CREATE_PROFILE_PHOTO:
            return {
                ...state,
                pending: false,
                profilePhoto: action.photo,
                error: null
            }
        case userAction.FAIL_CREATE_PROFILE_PHOTO:
            return {
                ...state,
                pending: false,
                error: action.error
            }
        case userAction.REQUEST_EDIT_USER:
            return {
                ...state,
                pending: true,
                error: null
            }
        case userAction.SUCCESS_EDIT_USER:
            return {
                ...state,
                pending: false,
                status: action.status,
                error: null
            }
        case userAction.FAIL_EDIT_USER:
            return {
                ...state,
                pending: false,
                error: action.error
            }
        case userAction.REQUEST_DELETE_USER:
            return {
                ...state,
                pending: true,
                error: null
            }
        case userAction.SUCCESS_DELETE_USER:
            return {
                ...state,
                pending: false,
                status: action.status,
                error: null
            }
        case userAction.FAIL_DELETE_USER:
            return {
                ...state,
                pending: false,
                error: action.error
            }
        default:
            return state;
    }
}