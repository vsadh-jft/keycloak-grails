const initialState = {
  loader: false,
  blogs: []
};
export default (state = { ...initialState }, action) => {
  switch (action.type) {
    case "Off-Loader":
      return {
        ...state,
        loader: false
      };
    case "On-Loader":
      return {
        ...state,
        loader: true
      };
    case "List-Fetch":
      return {
        ...state,
        blogs: action.payload || [],
        loader: false
      };
    default:
      return state;
  }
};
