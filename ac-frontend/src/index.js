import React from "react";
import ReactDOM from "react-dom/client";
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap/dist/js/bootstrap.bundle.min";
import "./index.scss";
import App from "./App";
import { Route, Routes, BrowserRouter } from "react-router-dom";
import reportWebVitals from "./reportWebVitals";
import {createStore, applyMiddleware, compose } from "redux";
import {Provider}  from "react-redux";
import rootReducer from "./Reducer/RootReducer"
import thunk from 'redux-thunk';

const initialState = {};
const middlewares = [thunk];

const store = createStore(rootReducer , initialState, compose(applyMiddleware(...middlewares)));

const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(
    <Provider store={store}>
    <BrowserRouter>
      <Routes>
        <Route path="/*" element={<App />}></Route>
      </Routes>
    </BrowserRouter>
    </Provider>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
