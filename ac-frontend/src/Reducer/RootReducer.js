import { combineReducers } from 'redux';
import authenticationReducer from "../Redux/authentication/AuthenticationReducer";

const rootReducer = combineReducers({
    auth: authenticationReducer
});
export default rootReducer;