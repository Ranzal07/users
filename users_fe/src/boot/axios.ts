import { boot } from 'quasar/wrappers';
import axios, { AxiosInstance } from 'axios';

const api: AxiosInstance = axios.create({ baseURL: 'http://localhost:8080/api/v1/' });

export default boot(({ app }) => {
  app.config.globalProperties.$axios = axios;
  app.config.globalProperties.$api = api;
});

export { axios, api };
