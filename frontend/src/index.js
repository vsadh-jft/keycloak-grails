import React from "react";
import ReactDOM from "react-dom";
import { Provider } from "react-redux";
import configureStore from "./store";
import axios from "axios";
import Keycloak from "keycloak-js";
import "./index.css";
import App from "./App";
import * as serviceWorker from "./serviceWorker";
import { BrowserRouter } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.css";
import { routerMiddleware } from "react-router-redux";
import thunk from "redux-thunk";
import { createStore, applyMiddleware } from "redux";
import rootReducer from "./reducers/rootReducer";

const middleware = [thunk, routerMiddleware()];
const store = createStore(rootReducer, applyMiddleware(...middleware));

const kcloak = Keycloak("/keycloak.json");
const token = localStorage.getItem("keycloak_token");
const refreshToken = localStorage.getItem("keycloak_refreshToken");

const app = (
  <Provider store={store}>
    <App />
  </Provider>
);

kcloak
  .init({
    onLoad: "login-required",
    promiseType: "native",
    token,
    refreshToken
  })
  .then(authenticated => {
    if (authenticated) {
      store.getState().keycloak = kcloak;
      updateLocalStorage();
      ReactDOM.render(app, document.getElementById("root"));
    }
  });

axios.interceptors.request.use(config =>
  kcloak
    .updateToken(5)
    .then(refreshed => {
      if (refreshed) {
        updateLocalStorage();
      }
      config.headers.Authorization = "Bearer " + kcloak.token;
      return Promise.resolve(config);
    })
    .catch(() => {
      kcloak.login();
    })
);

const updateLocalStorage = () => {
  localStorage.setItem("keycloak_token", kcloak.token);
  localStorage.setItem("keycloak_refreshToken", kcloak.refreshToken);
};

serviceWorker.unregister();
