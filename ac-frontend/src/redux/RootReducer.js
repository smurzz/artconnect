import { combineReducers } from 'redux';
import authenticationReducer from './authentication/AuthenticationReducer';
import userReducer from './user/UserReducer';
import forgotPasswordReducer from './forgot-password/ForgotPasswordReducer';
import messageReducer from './message/MessageReducer';
import galleryReduser from './gallery/GalleryReducer';
import artworkReduser from './artwork/ArtworkReducer';

const rootReducer = combineReducers({
    auth: authenticationReducer,
    users: userReducer,
    fPassword: forgotPasswordReducer,
    message: messageReducer,
    gallery: galleryReduser,
    artwork: artworkReduser
});
export default rootReducer;