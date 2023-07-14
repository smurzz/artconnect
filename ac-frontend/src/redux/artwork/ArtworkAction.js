import axios from "axios";
import * as refreshTokenService from "../authentication/RefreshTokenService";

export const REQUEST_READ_ARTWORKS = 'REQUEST_READ_ARTWORKS';
export const SUCCESS_READ_ARTWORKS = 'SUCCESS_READ_ARTWORKS';
export const FAIL_READ_ARTWORKS = 'FAIL_READ_ARTWORKS';

export const REQUEST_READ_ARTWORK = 'REQUEST_READ_ARTWORK';
export const SUCCESS_READ_ARTWORK = 'SUCCESS_READ_ARTWORK';
export const FAIL_READ_ARTWORK = 'FAIL_READ_ARTWORK';

export const REQUEST_CREATE_ARTWORK = 'REQUEST_CREATE_ARTWORK';
export const SUCCESS_CREATE_ARTWORK = 'SUCCESS_CREATE_ARTWORK';
export const FAIL_CREATE_ARTWORK = 'FAIL_CREATE_ARTWORK';

export const REQUEST_EDIT_ARTWORK = 'REQUEST_EDIT_ARTWORK';
export const SUCCESS_EDIT_ARTWORK = 'SUCCESS_EDIT_ARTWORK';
export const FAIL_EDIT_ARTWORK = 'FAIL_EDIT_ARTWORK';

export const REQUEST_DELETE_ARTWORK = 'REQUEST_DELETE_ARTWORK';
export const SUCCESS_DELETE_ARTWORK = 'SUCCESS_DELETE_ARTWORK';
export const FAIL_DELETE_ARTWORK = 'FAIL_DELETE_ARTWORK';

export const REQUEST_CREATE_ARTWORK_IMAGE = 'REQUEST_CREATE_ARTWORK_IMAGE';
export const SUCCESS_CREATE_ARTWORK_IMAGE = 'SUCCESS_CREATE_ARTWORK_IMAGE';
export const FAIL_CREATE_ARTWORK_IMAGE = 'FAIL_CREATE_ARTWORK_IMAGE';

export const REQUEST_CREATE_REMOVE_ARTWORK_LIKE = 'REQUEST_CREATE_REMOVE_ARTWORK_LIKE';
export const SUCCESS_CREATE_REMOVE_ARTWORK_LIKE = 'SUCCESS_CREATE_REMOVE_ARTWORK_LIKE';
export const FAIL_CREATE_REMOVE_ARTWORK_LIKE = 'FAIL_CREATE_REMOVE_ARTWORK_LIKE';

export const POST_COMMENT_PENDING = 'POST_COMMENT_PENDING';
export const POST_COMMENT_SUCCESS = 'POST_COMMENT_SUCCESS'
export const POST_COMMENT_ERROR = 'POST_COMMENT_ERROR'

export function getArtworksPendingAction() {
    return {
        type: REQUEST_READ_ARTWORKS
    }
}

export function getArtworksSuccessAction(artworks) {
    return {
        type: SUCCESS_READ_ARTWORKS,
        artworks: artworks
    }
}

export function getArtworksErrorAction(error) {
    return {
        type: FAIL_READ_ARTWORKS,
        error: error
    }
}

export function getArtworkPendingAction() {
    return {
        type: REQUEST_READ_ARTWORK
    }
}

export function getArtworkSuccessAction(artwork) {
    return {
        type: SUCCESS_READ_ARTWORK,
        artwork: artwork
    }
}

export function getArtworkErrorAction(error) {
    return {
        type: FAIL_READ_ARTWORK,
        error: error
    }
}

export function createArtworkPendingAction() {
    return {
        type: REQUEST_CREATE_ARTWORK
    }
}

export function createArtworkSuccessAction(res) {
    return {
        type: SUCCESS_CREATE_ARTWORK,
        status: res.status,
        artwork: res.data
    }
}

export function createArtworkErrorAction(error) {
    return {
        type: FAIL_CREATE_ARTWORK,
        error: error
    }
}

export function editArtworkPendingAction() {
    return {
        type: REQUEST_EDIT_ARTWORK
    }
}

export function editArtworkSuccessAction(res) {
    return {
        type: SUCCESS_EDIT_ARTWORK,
        status: res.status,
        artwork: res.data
    }
}

export function editArtworkErrorAction(error) {
    return {
        type: FAIL_EDIT_ARTWORK,
        error: error
    }
}

export function deleteArtworkPendingAction() {
    return {
        type: REQUEST_DELETE_ARTWORK
    }
}

export function deleteArtworkSuccessAction(res) {
    return {
        type: SUCCESS_DELETE_ARTWORK,
        status: res.status
    }
}

export function deleteArtworkErrorAction(error) {
    return {
        type: FAIL_DELETE_ARTWORK,
        error: error
    }
}

export function createArtworkIamgePendingAction() {
    return {
        type: REQUEST_CREATE_ARTWORK_IMAGE
    }
}

export function createArtworkIamgeSuccessAction(res) {
    return {
        type: SUCCESS_CREATE_ARTWORK_IMAGE,
        status: res.status
    }
}

export function createArtworkIamgeErrorAction(error) {
    return {
        type: FAIL_CREATE_ARTWORK_IMAGE,
        error: error
    }
}

export function createRemoveArtworkLikePendingAction() {
    return {
        type: REQUEST_CREATE_REMOVE_ARTWORK_LIKE
    }
}

export function createRemoveArtworkLikeSuccessAction(res) {
    return {
        type: SUCCESS_CREATE_REMOVE_ARTWORK_LIKE,
        status: res.status
    }
}

export function createRemoveArtworkLikeErrorAction(error) {
    return {
        type: FAIL_CREATE_REMOVE_ARTWORK_LIKE,
        error: error
    }
}

export function postCommentPendingAction() {
    return {
        type: POST_COMMENT_PENDING
    }
}

export function postCommentSuccessAction(res) {
    return {
        type: POST_COMMENT_SUCCESS,
        status: res.status,
        artwork: res.data
    }
}

export function postCommentErrorAction(error) {
    return {
        type: POST_COMMENT_ERROR,
        error: error
    }
}

export function getArtworks() {

    return async dispatch => {
        dispatch(getArtworksPendingAction());

        axios.get('/artworks')
            .then(response => {
                const artworks = response.data;
                const action = getArtworksSuccessAction(artworks);
                dispatch(action);
            })
            .catch(error => {
                dispatch(getArtworksErrorAction(error.response?.data));
            });
    }
}

export function getArtworksByTags(tagsArray) {

    return async dispatch => {
        dispatch(getArtworksPendingAction());

        axios.get('/artworks/search', { params: { tags: tagsArray }})
            .then(response => {
                const artworks = response.data;
                const action = getArtworksSuccessAction(artworks);
                dispatch(action);
            })
            .catch(error => {
                dispatch(getArtworksErrorAction(error.response?.data));
            });
    }
}

export function getArtwork(id) {

    return async dispatch => {
        dispatch(getArtworkPendingAction());

        axios.get('/artworks/' + id)
            .then(response => {
                const artwork = response.data;
                const action = getArtworkSuccessAction(artwork);
                dispatch(action);
            })
            .catch(error => {
                dispatch(getArtworkErrorAction(error.response?.data));
            });
    }
}

export function createArtwork(artwork) {

    return async dispatch => {
        dispatch(createArtworkPendingAction());

        await refreshTokenService.checkTockens()
            .then(() => {
                const requestOptions = {
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': 'Bearer ' + JSON.parse(localStorage.getItem('userSession')).accessToken
                    }
                };
                axios.post('/artworks', artwork, requestOptions)
                    .then(response => {
                        const action = createArtworkSuccessAction(response);
                        dispatch(action);
                    })
                    .catch(error => {
                        dispatch(createArtworkErrorAction(error.response?.data));
                    })
            })
            .catch(error => {
                dispatch(createArtworkErrorAction(error.response?.data));
            });
    }
}

export function editArtwork(id, artwork) {

    return async dispatch => {
        dispatch(editArtworkPendingAction());

        await refreshTokenService.checkTockens()
            .then(() => {
                const requestOptions = {
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': 'Bearer ' + JSON.parse(localStorage.getItem('userSession')).accessToken
                    }
                };
                axios.put('/artworks/' + id, artwork, requestOptions)
                    .then(response => {
                        const action = editArtworkSuccessAction(response);
                        dispatch(action);
                    })
                    .catch(error => {
                        dispatch(editArtworkErrorAction(error.response?.data));
                    })
            })
            .catch(error => {
                dispatch(editArtworkErrorAction(error.response?.data));
            });
    }
}

export function deleteArtwork(id) {
    
    return async dispatch => {
        dispatch(deleteArtworkPendingAction());

        await refreshTokenService.checkTockens()
        .then(() => {
            const requestOptions = {
                headers: {
                    'Authorization': 'Bearer ' + JSON.parse(localStorage.getItem('userSession')).accessToken
                }
            };
            axios.delete('/artworks/' + id, requestOptions)
                .then(response => {
                    const action = deleteArtworkSuccessAction(response);
                    dispatch(action);
                })
                .catch(error => {
                    dispatch(deleteArtworkErrorAction(error.respone?.data));
                })
        })
        .catch(error => {
            dispatch(editArtworkErrorAction(error));
        });
    }
}

export function addArtworkImage(id, formData) {
    return async dispatch => {
        dispatch(createArtworkIamgePendingAction());

        await refreshTokenService.checkTockens()
        .then(() => {
            const requestOptions = {
                headers: {
                    'Content-Type': 'multipart/form-data',
                    'Authorization': 'Bearer ' + JSON.parse(localStorage.getItem('userSession')).accessToken
                }
            };
            axios.post("/artworks/add-photo/" + id, formData, requestOptions)
            .then(response => {
                const action = createArtworkIamgeSuccessAction(response);
                dispatch(action);
            })
            .catch(error => {
                dispatch(createArtworkIamgeErrorAction(error.response?.data));
            });
        })
        .catch(error => {
            dispatch(createArtworkIamgeErrorAction(error.response?.data));
        });
    }
}

export function addRemoveLike(id) {

    return async dispatch => {
        dispatch(createArtworkPendingAction());

        await refreshTokenService.checkTockens()
            .then(() => {
                const requestOptions = {
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': 'Bearer ' + JSON.parse(localStorage.getItem('userSession')).accessToken
                    }
                };
                axios.post('/artworks/' + id + '/like', null, requestOptions)
                    .then(response => {
                        const action = createArtworkSuccessAction(response);
                        dispatch(action);
                    })
                    .catch(error => {
                        dispatch(createArtworkErrorAction(error.response?.data));
                    })
            })
            .catch(error => {
                dispatch(createArtworkErrorAction(error.response?.data));
            });
    }
}

export function postComment(artworkId, commentText) {

    return async dispatch => {
        dispatch(postCommentPendingAction());

        await refreshTokenService.checkTockens()
            .then(() => {
                const requestOptions = {
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': 'Bearer ' + JSON.parse(localStorage.getItem('userSession')).accessToken
                    }
                };
                axios.post('/artworks/' + artworkId + '/comments', commentText, requestOptions)
                    .then(response => {
                        dispatch(postCommentSuccessAction(response))
                    })
                    .catch(error => {
                        dispatch(postCommentErrorAction(error.response?.data));
                    });
            })
            .catch(error => {
                dispatch(createArtworkErrorAction(error.response?.data));
            });
    }
}
