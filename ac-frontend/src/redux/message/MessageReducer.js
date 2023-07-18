import * as messagedActions from './MessageAction';

const initialState = {
    pending: false,
    error: null,
    status: ''
};

export default function messageReducer(state = initialState, action) {

    console.log('Bin in Message Reducer: ' + action.type);

    switch (action.type) {
        case messagedActions.SEND_MESSAGE_PENDING:
            return {
                ...state,
                pending: true,
                error: null
            }
        case messagedActions.SEND_MESSAGE_SUCCESS:
            return {
                ...state,
                pending: false,
                status: action.status
            }
        case messagedActions.SEND_MESSAGE_ERROR:
            return {
                ...state,
                pending: false,
                error: action.error
            }
        default:
            return state;
    }

}