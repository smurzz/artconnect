import axios from "axios";
import * as refreshTokenService from "../authentication/RefreshTokenService";

export const REQUEST_READ_GALLERIES = 'REQUEST_READ_GALLERIES';
export const SUCCESS_READ_GALLERIES = 'SUCCESS_READ_GALLERIES';
export const FAIL_READ_GALLERIES = 'FAIL_READ_GALLERIES';

export const REQUEST_READ_GALLERY = 'REQUEST_READ_GALLERY';
export const SUCCESS_READ_GALLERY = 'SUCCESS_READ_GALLERY';
export const FAIL_READ_GALLERY = 'FAIL_READ_GALLERY';

export const REQUEST_CREATE_GALLERY = 'REQUEST_CREATE_GALLERY';
export const SUCCESS_CREATE_GALLERY = 'SUCCESS_CREATE_GALLERY';
export const FAIL_CREATE_GALLERY = 'FAIL_CREATE_GALLERY';

export const REQUEST_EDIT_GALLERY = 'REQUEST_EDIT_GALLERY';
export const SUCCESS_EDIT_GALLERY = 'SUCCESS_EDIT_GALLERY';
export const FAIL_EDIT_GALLERY = 'FAIL_EDIT_GALLERY';

export const REQUEST_DELETE_GALLERY = 'REQUEST_DELETE_GALLERY';
export const SUCCESS_DELETE_GALLERY = 'SUCCESS_DELETE_GALLERY';
export const FAIL_DELETE_GALLERY = 'FAIL_DELETE_GALLERY';

export const REQUEST_PUT_RATING = 'REQUEST_PUT_RATING';
export const SUCCESS_PUT_RATING= 'SUCCESS_PUT_RATING';
export const FAIL_PUT_RATING = 'FAIL_PUT_RATING';

export function getGalleriesPendingAction() {
    return {
        type: REQUEST_READ_GALLERIES
    }
}

export function getGalleriesSuccessAction(galleries) {
    return {
        type: SUCCESS_READ_GALLERIES,
        galleries: galleries
    }
}

export function getGalleriesErrorAction(error) {
    return {
        type: FAIL_READ_GALLERIES,
        error: error
    }
}

export function getGalleryPendingAction() {
    return {
        type: REQUEST_READ_GALLERY
    }
}

export function getGallerySuccessAction(gallery) {
    return {
        type: SUCCESS_READ_GALLERY,
        gallery: gallery
    }
}

export function getGalleryErrorAction(error) {
    return {
        type: FAIL_READ_GALLERY,
        error: error
    }
}

export function createGalleryPendingAction() {
    return {
        type: REQUEST_CREATE_GALLERY
    }
}

export function createGallerySuccessAction(res) {
    return {
        type: SUCCESS_CREATE_GALLERY,
        status: res.status,
        gallery: res.data
    }
}

export function createGalleryErrorAction(error) {
    return {
        type: FAIL_READ_GALLERY,
        error: error
    }
}

export function editGalleryPendingAction() {
    return {
        type: REQUEST_EDIT_GALLERY
    }
}

export function editGallerySuccessAction(res) {
    return {
        type: SUCCESS_EDIT_GALLERY,
        status: res.status,
        gallery: res.data
    }
}

export function editGalleryErrorAction(error) {
    return {
        type: FAIL_EDIT_GALLERY,
        error: error
    }
}

export function deleteGalleryPendingAction() {
    return {
        type: REQUEST_DELETE_GALLERY
    }
}

export function deleteGallerySuccessAction(res) {
    return {
        type: SUCCESS_DELETE_GALLERY,
        status: res.status
    }
}

export function deleteGalleryErrorAction(error) {
    return {
        type: FAIL_DELETE_GALLERY,
        error: error
    }
}

export function putRatingPendingAction() {
    return {
        type: REQUEST_PUT_RATING
    }
}

export function putRatingSuccessAction(res) {
    return {
        type: SUCCESS_PUT_RATING,
        status: res.status,
        gallery: res.data
    }
}

export function putRatingErrorAction(error) {
    return {
        type: FAIL_PUT_RATING,
        error: error
    }
}

export function getGalleries() {

    return async dispatch => {
        dispatch(getGalleriesPendingAction());

        axios.get('/galleries')
            .then(response => {
                const galleries = response.data;
                const action = getGalleriesSuccessAction(galleries);
                dispatch(action);
            })
            .catch(error => {
                dispatch(getGalleriesErrorAction(error.response?.data));
            });
    }
}

export function getGallery(id) {

    return async dispatch => {
        dispatch(getGalleryPendingAction());

        axios.get('/galleries/' + id)
            .then(response => {
                const gallery = response.data;
                const action = getGallerySuccessAction(gallery);
                dispatch(action);
            })
            .catch(error => {
                dispatch(getGalleryErrorAction(error.response?.data));
            });
    }
}

export function getMyGallery() {

    return async dispatch => {
        dispatch(getGalleryPendingAction());

        await refreshTokenService.checkTockens()
        .then(() => {
            const requestOptions = {
                headers: {
                    'Content-Type': 'multipart/form-data',
                    'Authorization': 'Bearer ' + JSON.parse(localStorage.getItem('userSession')).accessToken
                }
            };
            axios.get('/galleries/myGallery', requestOptions)
            .then(response => {
                const gallery = response.data;
                const action = getGallerySuccessAction(gallery);
                dispatch(action);
            })
            .catch(error => {
                dispatch(getGalleryErrorAction(error.response?.data));
            });
        })
        .catch(error => {
            dispatch(getGalleryErrorAction(error.response?.data));
        });
    }
}

export function createGallery(gallery) {

    return async dispatch => {
        dispatch(createGalleryPendingAction());

        await refreshTokenService.checkTockens()
            .then(() => {
                const requestOptions = {
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': 'Bearer ' + JSON.parse(localStorage.getItem('userSession')).accessToken
                    }
                };
                axios.post('/galleries', gallery, requestOptions)
                    .then(response => {
                        const action = createGallerySuccessAction(response);
                        dispatch(action);
                    })
                    .catch(error => {
                        dispatch(createGalleryErrorAction(error.response?.data));
                    })
            })
            .catch(error => {
                dispatch(createGalleryErrorAction(error.response?.data));
            });
    }
}

export function editGallery(id, gallery) {

    return async dispatch => {
        dispatch(editGalleryPendingAction());

        await refreshTokenService.checkTockens()
            .then(() => {
                const requestOptions = {
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': 'Bearer ' + JSON.parse(localStorage.getItem('userSession')).accessToken
                    }
                };
                axios.put('/galleries/' + id, gallery, requestOptions)
                    .then(response => {
                        const action = editGallerySuccessAction(response);
                        dispatch(action);
                    })
                    .catch(error => {
                        dispatch(editGalleryErrorAction(error.response?.data));
                    })
            })
            .catch(error => {
                dispatch(editGalleryErrorAction(error.response?.data));
            });
    }
}

export function deleteGallery(id) {
    
    return async dispatch => {
        dispatch(deleteGalleryPendingAction());

        await refreshTokenService.checkTockens()
        .then(() => {
            const requestOptions = {
                headers: {
                    'Authorization': 'Bearer ' + JSON.parse(localStorage.getItem('userSession')).accessToken
                }
            };
            axios.delete('/galleries/' + id, requestOptions)
                .then(response => {
                    const action = deleteGallerySuccessAction(response);
                    dispatch(action);
                })
                .catch(error => {
                    dispatch(deleteGalleryErrorAction(error.response?.data));
                })
        })
        .catch(error => {
            dispatch(editGalleryErrorAction(error.response?.data));
        });
    }
}

export function putRating(id, valueRating) {

    return async dispatch => {
        dispatch(putRatingPendingAction());

        await refreshTokenService.checkTockens()
            .then(() => {
                const requestOptions = {
                    headers: {
                      'Content-Type': 'application/json',
                      'Authorization': 'Bearer ' + JSON.parse(localStorage.getItem('userSession')).accessToken
                    },
                    params: {
                      value: valueRating
                    }
                  };
                  
                axios.post('/galleries/' + id + '/rating', null, requestOptions)
                    .then(response => {
                        const action = putRatingSuccessAction(response);
                        dispatch(action);
                    })
                    .catch(error => {
                        dispatch(putRatingErrorAction(error.response?.data));
                    })
            })
            .catch(error => {
                dispatch(putRatingErrorAction(error.response?.data));
            });
    }
}


