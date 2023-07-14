import * as artworkActions from "./ArtworkAction";

const initialState = {
    artworks: [],
    artwork: null,
    status: null,
    image: null,
    pending: false,
    error: null
};

export default function artworkReducer(state = initialState, action) {
    switch (action.type) {
        case artworkActions.REQUEST_READ_ARTWORKS:
            return {
                ...state,
                pending: action.pending,
                error: null
            }
        case artworkActions.SUCCESS_READ_ARTWORKS:
            return {
                ...state,
                pending: false,
                artworks: action.artworks,
                error: null
            }
        case artworkActions.FAIL_READ_ARTWORKS:
            return {
                ...state,
                pending: false,
                error: action.error
            }
        case artworkActions.REQUEST_READ_ARTWORK:
            return {
                ...state,
                pending: true,
                error: null
            }
        case artworkActions.SUCCESS_READ_ARTWORK:
            return {
                ...state,
                pending: false,
                artwork: action.artwork,
                error: null
            }
        case artworkActions.FAIL_READ_ARTWORK:
            return {
                ...state,
                pending: false,
                artwork: null,
                error: action.error
            }
        case artworkActions.REQUEST_CREATE_ARTWORK:
            return {
                ...state,
                pending: true,
                error: null
            }
        case artworkActions.SUCCESS_CREATE_ARTWORK:
            return {
                ...state,
                pending: false,
                status: action.status,
                artwork: action.artwork,
                error: null
            }
        case artworkActions.FAIL_CREATE_ARTWORK:
            return {
                ...state,
                pending: false,
                error: action.error
            }
        case artworkActions.REQUEST_EDIT_ARTWORK:
            return {
                ...state,
                pending: true,
                error: null
            }
        case artworkActions.SUCCESS_EDIT_ARTWORK:
            return {
                ...state,
                pending: false,
                status: action.status,
                artwork: action.artwork,
                error: null
            }
        case artworkActions.FAIL_EDIT_ARTWORK:
            return {
                ...state,
                pending: false,
                error: action.error
            }
        case artworkActions.REQUEST_DELETE_ARTWORK:
            return {
                ...state,
                pending: true,
                error: null
            }
        case artworkActions.SUCCESS_DELETE_ARTWORK:
            return {
                ...state,
                pending: false,
                status: action.status,
                error: null
            }
        case artworkActions.FAIL_DELETE_ARTWORK:
            return {
                ...state,
                pending: false,
                error: action.error
            }
        case artworkActions.REQUEST_CREATE_ARTWORK_IMAGE:
            return {
                ...state,
                pending: true,
                error: null
            }
        case artworkActions.SUCCESS_CREATE_ARTWORK_IMAGE:
            return {
                ...state,
                pending: false,
                status: action.status,
                error: null
            }
        case artworkActions.FAIL_CREATE_ARTWORK_IMAGE:
            return {
                ...state,
                pending: false,
                error: action.error
            }
        case artworkActions.REQUEST_CREATE_REMOVE_ARTWORK_LIKE:
            return {
                ...state,
                pending: true,
                error: null
            }
        case artworkActions.SUCCESS_CREATE_REMOVE_ARTWORK_LIKE:
            return {
                ...state,
                pending: false,
                status: action.status,
                error: null
            }
        case artworkActions.FAIL_CREATE_REMOVE_ARTWORK_LIKE:
            return {
                ...state,
                pending: false,
                error: action.error
            }
        case artworkActions.POST_COMMENT_PENDING:
            return {
                ...state,
                pending: true,
                error: null
            }
        case artworkActions.POST_COMMENT_SUCCESS:
            return {
                ...state,
                pending: false,
                status: action.status,
                artwork: action.artwork,
                error: null
            }
        case artworkActions.POST_COMMENT_ERROR:
            return {
                ...state,
                pending: false,
                error: action.error
            }
        default:
            return state;
    }
}