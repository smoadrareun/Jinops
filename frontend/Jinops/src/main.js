import {createApp} from 'vue';
import App from './App';
import {router} from './router/index';
import 'jquery/dist/jquery.min';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.bundle.min'
import 'bootstrap-icons/font/bootstrap-icons.css'

const app = createApp(App);

app.use(router);
app.mount('#app');