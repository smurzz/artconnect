import * as galleryAction from "./GalleryAction";

const initialState = {
    galleries: [],
    gallery: null,
    status: null,
    pending: false,
    error: null
};

export default function galleryReducer(state = initialState, action) {

    console.log('Bin in Gallery Reducer: ' + action.type);

    switch (action.type) {
        case galleryAction.REQUEST_READ_GALLERIES:
            return {
                ...state,
                pending: true,
                error: null
            }
        case galleryAction.SUCCESS_READ_GALLERIES:
            return {
                ...state,
                pending: false,
                galleries: action.galleries,
                error: null
            }
        case galleryAction.FAIL_READ_GALLERIES:
            return {
                ...state,
                pending: false,
                error: action.error
            }
        case galleryAction.REQUEST_READ_GALLERY:
            return {
                ...state,
                pending: true,
                error: null
            }
        case galleryAction.SUCCESS_READ_GALLERY:
            return {
                ...state,
                pending: false,
                gallery: action.gallery,
                error: null
            }
        case galleryAction.FAIL_READ_GALLERY:
            return {
                ...state,
                pending: false,
                gallery: null,
                error: action.error
            }
        case galleryAction.REQUEST_CREATE_GALLERY:
            return {
                ...state,
                pending: true,
                error: null
            }
        case galleryAction.SUCCESS_CREATE_GALLERY:
            return {
                ...state,
                pending: false,
                status: action.status,
                gallery: action.gallery,
                error: null
            }
        case galleryAction.FAIL_CREATE_GALLERY:
            return {
                ...state,
                pending: false,
                error: action.error
            }
        case galleryAction.REQUEST_EDIT_GALLERY:
            return {
                ...state,
                pending: true,
                error: null
            }
        case galleryAction.SUCCESS_EDIT_GALLERY:
            return {
                ...state,
                pending: false,
                status: action.status,
                gallery: action.gallery,
                error: null
            }
        case galleryAction.FAIL_EDIT_GALLERY:
            return {
                ...state,
                pending: false,
                error: action.error
            }
        case galleryAction.REQUEST_DELETE_GALLERY:
            return {
                ...state,
                pending: true,
                error: null
            }
        case galleryAction.SUCCESS_DELETE_GALLERY:
            return {
                ...state,
                pending: false,
                status: action.status,
                error: null
            }
        case galleryAction.FAIL_DELETE_GALLERY:
            return {
                ...state,
                pending: false,
                error: action.error
            }
        case galleryAction.REQUEST_PUT_RATING:
            return {
                ...state,
                pending: true,
                error: null
            }
        case galleryAction.SUCCESS_PUT_RATING:
            return {
                ...state,
                pending: false,
                status: action.status,
                gallery: action.gallery,
                error: null
            }
        case galleryAction.FAIL_PUT_RATING:
            return {
                ...state,
                pending: false,
                error: action.error
            }
        default:
            return state;
    }
}