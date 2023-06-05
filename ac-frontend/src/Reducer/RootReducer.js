import { combineReducers } from 'redux';
import authenticationReducer from "../redux/authentication/AuthenticationReducer";

const rootReducer = combineReducers({
    auth: authenticationReducer
});
export default rootReducer;