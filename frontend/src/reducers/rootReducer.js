import { combineReducers } from "redux";
import blogReducer from "../container/Home/reducer";
export default combineReducers({
  blogReducer,
  keycloak: (keycloak = {}) => keycloak
});
