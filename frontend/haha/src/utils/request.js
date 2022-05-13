import axios from 'axios';
 const request = axios.create({
     baseURL:'http://60.205.188.33:8888/'
 })
 request.interceptors.request.use(
    config => {
      const id = sessionStorage.getItem('id')
      if (id ) { // 判断是否存在token，如果存在的话，则每个http header都加上token
        config.headers.authorization = id  //请求头加上token
      }
      return config
    },
    err => {
      return Promise.reject(err)
    })
 export default request