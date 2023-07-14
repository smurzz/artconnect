import axios from "axios";
import * as refreshTokenService from "../authentication/RefreshTokenService";

export const REQUEST_READ_USERS = 'REQUEST_READ_USERS';
export const SUCCESS_READ_USERS = 'SUCCESS_READ_USERS';
export const FAIL_READ_USERS = 'FAIL_READ_USERS';

export const REQUEST_READ_USER = 'REQUEST_READ_USER';
export const SUCCESS_READ_USER = 'SUCCESS_READ_USER';
export const FAIL_READ_USER = 'FAIL_READ_USER';

export const REQUEST_CREATE_USER = 'REQUEST_CREATE_USER';
export const SUCCESS_CREATE_USER = 'SUCCESS_CREATE_USER';
export const FAIL_CREATE_USER = 'FAIL_CREATE_USER';

export const REQUEST_DELETE_USER = 'REQUEST_DELETE_USER';
export const SUCCESS_DELETE_USER = 'SUCCESS_DELETE_USER';
export const FAIL_DELETE_USER = 'FAIL_DELETE_USER';

export const REQUEST_EDIT_USER = 'REQUEST_EDIT_USER';
export const SUCCESS_EDIT_USER = 'SUCCESS_EDIT_USER';
export const FAIL_EDIT_USER = 'FAIL_EDIT_USER';

export const REQUEST_READ_PROFILE_PHOTO = 'REQUEST_READ_PROFILE_PHOTO';
export const SUCCESS_READ_PROFILE_PHOTO = 'SUCCESS_READ_PROFILE_PHOTO';
export const FAIL_READ_PROFILE_PHOTO = 'FAIL_READ_PROFILE_PHOTO';

export const REQUEST_CREATE_PROFILE_PHOTO = 'REQUEST_CREATE_PROFILE_PHOTO';
export const SUCCESS_CREATE_PROFILE_PHOTO = 'SUCCESS_CREATE_PROFILE_PHOTO';
export const FAIL_CREATE_PROFILE_PHOTO = 'FAIL_CREATE_PROFILE_PHOTO';


export function getUsersPendingAction() {
    return {
        type: REQUEST_READ_USERS
    }
}

export function getUsersSuccessAction(users) {
    return {
        type: SUCCESS_READ_USERS,
        users: users
    }
}

export function getUsersErrorAction(error) {
    return {
        type: FAIL_READ_USERS,
        error: error
    }
}

export function getUserPendingAction() {
    return {
        type: REQUEST_READ_USER,
        pending: true
    }
}

export function getUserSuccessAction(user) {
    return {
        type: SUCCESS_READ_USER,
        user: user
    }
}

export function getUserErrorAction(error) {
    return {
        type: FAIL_READ_USER,
        error: error
    }
}

export function createUserPendingAction() {
    return {
        type: REQUEST_CREATE_USER
    }
}

export function createUseruccessAction(res) {
    return {
        type: SUCCESS_CREATE_USER,
        status: res.status
    }
}

export function createUserErrorAction(error) {
    return {
        type: FAIL_CREATE_USER,
        error: error
    }
}

export function deleteUserPendingAction() {
    return {
        type: REQUEST_DELETE_USER
    }
}

export function deleteUserSuccessAction(res) {
    return {
        type: SUCCESS_EDIT_USER,
        status: res.status
    }
}

export function deleteUserErrorAction(error) {
    return {
        type: FAIL_EDIT_USER,
        status: error.status
    }
}

export function editUserPendingAction() {
    return {
        type: REQUEST_EDIT_USER
    }
}

export function editUserSuccessAction(res) {
    return {
        type: SUCCESS_EDIT_USER,
        status: res.status
    }
}

export function editUserErrorAction(error) {
    return {
        type: FAIL_EDIT_USER,
        error: error
    }
}

export function getUserProfilePhotoPendingAction() {
    return {
        type: REQUEST_READ_PROFILE_PHOTO,
        pending: true
    }
}

export function getUserProfilePhotoSuccessAction(photoUrl) {
    return {
        type: SUCCESS_READ_PROFILE_PHOTO,
        photo: photoUrl
    }
}

export function getUserProfilePhotoErrorAction(error) {
    return {
        type: FAIL_READ_PROFILE_PHOTO,
        error: error
    }
}

export function postUserProfilePhotoPendingAction() {
    return {
        type: REQUEST_CREATE_PROFILE_PHOTO,
        pending: true
    }
}

export function postUserProfilePhotoSuccessAction(photoUrl) {
    return {
        type: SUCCESS_CREATE_PROFILE_PHOTO,
        photo: photoUrl
    }
}

export function postUserProfilePhotoErrorAction(error) {
    return {
        type: FAIL_CREATE_PROFILE_PHOTO,
        error: error
    }
}

export function getUsers() {

    return async dispatch => {
        dispatch(getUsersPendingAction());

        axios.get('/users')
            .then(response => {
                const users = response.data;
                const action = getUsersSuccessAction(users);
                dispatch(action);
            })
            .catch(error => {
                const errorMessage = error.message;
                dispatch(getUsersErrorAction(errorMessage));
            });
    }
}

export function getUsersByName(fname, lname) {

    return async dispatch => {
        dispatch(getUsersPendingAction());

        axios.get('/users/search', { params: { firstname: fname, lastname: lname } })
            .then(response => {
                const users = response.data;
                const action = getUsersSuccessAction(users);
                dispatch(action);
            })
            .catch(error => {
                const errorMessage = error.message;
                dispatch(getUsersErrorAction(errorMessage));
            });
    }
}

export function getUserByEmail(userEmail) {

    return async dispatch => {
        dispatch(getUserPendingAction());

        axios.get('/users', { params: { email: userEmail } })
            .then(response => {
                const user = response.data;
                const action = getUserSuccessAction(user);
                dispatch(action);
            })
            .catch(error => {
                dispatch(getUserErrorAction(error));
            });
    }
}

export function getUserById(id) {

    return async dispatch => {
        dispatch(getUserPendingAction());

        axios.get('/users/' + id)
            .then(response => {
                const user = response.data;
                const action = getUserSuccessAction(user);
                dispatch(action);
            })
            .catch(error => {
                dispatch(getUserErrorAction(error));
            });
    }
}

export function getUserProfilePhoto(userId) {
    return async dispatch => {
        dispatch(getUserPendingAction());

        axios.get('/users/' + userId + "/profile-photo", { responseType: 'arraybuffer' })
            .then(response => {
                const photoData = btoa(
                    new Uint8Array(response.data).reduce((data, byte) => data + String.fromCharCode(byte), '')
                );
                const contentType = response.headers['content-type'];
                const imageUrl = `data:${contentType};base64,${photoData}`;
                const action = getUserProfilePhotoSuccessAction(imageUrl);
                dispatch(action);
            })
            .catch(error => {
                dispatch(getUserProfilePhotoErrorAction(error));
            });
    }
}

export function addUserProfilePhoto(formData) {
    return async dispatch => {
        dispatch(postUserProfilePhotoPendingAction());

        await refreshTokenService.checkTockens()
        .then(() => {
            const requestOptions = {
                headers: {
                    'Content-Type': 'multipart/form-data',
                    'Authorization': 'Bearer ' + JSON.parse(localStorage.getItem('userSession')).accessToken
                }
            };
            axios.post("/users/profile-photo", formData, requestOptions)
            .then(response => {
                console.log(response.data);
                const photoData = btoa(
                    new Uint8Array(response.data).reduce((data, byte) => data + String.fromCharCode(byte), '')
                );
                const contentType = response.headers['content-type'];
                const imageUrl = `data:${contentType};base64,${photoData}`;
                const action = postUserProfilePhotoSuccessAction(imageUrl);
                dispatch(action);
            })
            .catch(error => {
                dispatch(postUserProfilePhotoErrorAction(error));
            });
        })
        .catch(error => {
            dispatch(postUserProfilePhotoErrorAction(error));
        });
    }
}

export function editUser(userID, user) {

    return async dispatch => {
        dispatch(editUserPendingAction());

        await refreshTokenService.checkTockens()
            .then(() => {
                const requestOptions = {
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': 'Bearer ' + JSON.parse(localStorage.getItem('userSession')).accessToken
                    }
                };
                axios.put('/users/' + userID, user, requestOptions)
                    .then(response => {
                        const action = editUserSuccessAction(response);
                        dispatch(action);
                    })
                    .catch(error => {
                        dispatch(editUserErrorAction(error));
                    })
            })
            .catch(error => {
                dispatch(editUserErrorAction(error));
            });
    }
}

export function deleteUser(userID) {
    
    return async dispatch => {
        dispatch(deleteUserPendingAction());

        await refreshTokenService.checkTockens()
        .then(() => {
            const requestOptions = {
                headers: {
                    'Authorization': 'Bearer ' + JSON.parse(localStorage.getItem('userSession')).accessToken
                }
            };
            axios.delete('/users/' + userID, requestOptions)
                .then(response => {
                    const action = deleteUserSuccessAction(response);
                    dispatch(action);
                })
                .catch(error => {
                    dispatch(deleteUserErrorAction(error));
                })
        })
        .catch(error => {
            dispatch(editUserErrorAction(error));
        });
    }
}