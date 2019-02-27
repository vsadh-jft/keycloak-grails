import { createStore, applyMiddleware } from "redux";
import thunk from "redux-thunk";
import blogReducer from "./reducers/rootReducer";
export default function configureStore(initialState = {}) {
  return createStore(blogReducer, initialState, applyMiddleware(thunk));
}
