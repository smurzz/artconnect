import axios from "../api/axios";

async function postData(url, payload) {
  var _headers = {
    headers: {
      "Content-Type": "application/json",
    },
  };
  let result = await axios.post(url, JSON.stringify(payload), _headers);
  if (result.status === 200) {
    return result;
  }
}

export default postData;
