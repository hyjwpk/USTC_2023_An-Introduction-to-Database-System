import axios from "axios";

const request = axios.create({
    baseURL: "http://localhost:8081",
    timeout: 5000,
});

request.interceptors.request.use((config) => {
    config.headers['token'] = sessionStorage.getItem('token');
    return config;
}, (error) => {
    return Promise.reject(error);
});

request.interceptors.response.use((response) => {
    if (response.data.code === 401 || response.data.code === 402) {
        sessionStorage.clear();
    }
    return response;
}, (error) => {
    return Promise.reject(error);
});

export default request;
