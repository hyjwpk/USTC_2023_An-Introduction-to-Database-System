import axios from "axios";

const request = axios.create({
    baseURL: "",
    timeout: 5000,
});

export default request;
