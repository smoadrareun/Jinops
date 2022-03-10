import request from '@/utils/request.js'

export const getdata = ()=>{
    return request({
        method:"get",
        url:'data/test/getInfo',
       
    })
}

export const user = ()=>{
    return request({
        method:"get",
        url:'list',
       
    })
}
export const login = (data)=>{
    return request({
        method:'get',
        url:'list',
        data
    })
}
export const loginUser = ()=>{
    return request({
        method:'get',
        url:'user'
    })
}
export const loginAdmin = ()=>{
    return request({method:'get',url:'admin'})
}
export const maintainList = (params)=>{
    return request({
        method:'get',
        url:'maintainList',
        params
    })
}
export const customer = (params)=>{
    return request({
        method:'get',
        url:'customer',
        params
    })
}
export const service = (params)=>{
    return request({
        method:'get',
        url:'service',
        params
    })
}
export const owners =(params)=>{
    return request({method:'get',url:'owners',params})
}
export const totalpage = ()=>{
    return request({
        method:'get',
        url:'total'
    })
}