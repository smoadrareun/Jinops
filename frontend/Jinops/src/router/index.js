import {createRouter, createWebHistory} from "vue-router";
const Hello = () => import("../components/Hello")
const Example = () => import("../components/Example")
const HelloWorld = () => import("../components/HelloWorld")
const Index = () => import("../views/vendor/index")

const routes = [
    { path: "/index",name: "HelloWorld",component: HelloWorld },
    { path: "/",name: "Hello",component: Hello },
    { path: "/example",name: "Example",component: Example },
    { path: "/vendor",name: "Index",component: Index,
        children:[
            { path: "index",name: "Index",component: Index }
        ]
    }
]
export const router = createRouter({
    history: createWebHistory("/"),
    routes: routes
})