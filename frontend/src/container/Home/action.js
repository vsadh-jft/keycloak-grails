import axios from "axios";
import { NotificationManager } from "react-notifications";
const BLOG_API = "http://localhost:8080/blog";

export const addBlog = entity => {
  return dispatch => {
    dispatch({
      type: "On-Loader"
    });
    axios({
      url: `${BLOG_API}/save`,
      method: "post",
      responseType: "json",
      data: entity
    })
      .then(response => {
        notify("success", "Success", "Successfully Added");
        dispatch({
          type: "Off-Loader"
        });
        dispatch(fetchListOfBlog());
      })
      .catch(response => {
        notify("error", "Error", response.message);
        dispatch({
          type: "Off-Loader"
        });
      });
  };
};

export const fetchListOfBlog = () => {
  return dispatch => {
    dispatch({
      type: "On-Loader"
    });
    axios
      .get(`${BLOG_API}/index`)
      .then(response => {
        dispatch({
          type: "List-Fetch",
          payload: response.data
        });
      })
      .catch(response => {
        notify("error", "Error", response.message);
        dispatch({
          type: "Off-Loader"
        });
      });
  };
};

const notify = (type, title, message) => {
  switch (type) {
    case "success":
      NotificationManager.success(title, message);
      break;
    case "error":
      NotificationManager.error(title, message);
      break;
  }
};
