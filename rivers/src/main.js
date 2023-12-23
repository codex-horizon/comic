'use strict';
import {createApp} from 'vue';
import ElementPlus from 'element-plus';
import 'element-plus/dist/index.css';
import '@/theme/index.scss';
import * as ElementPlusIcons from '@element-plus/icons-vue';
import App from './App.vue';
import VuexStore from '@/store/index.js';
import VueRouter from '@/router/index.js';

/* 自定义组件全局注册 */
import HorizonDialog from '@/components/HorizonDialog.js';
/* 自定义组件全局注册 */

const Rivers = createApp(App);
Rivers.use(VueRouter).use(VuexStore).use(ElementPlus).use(HorizonDialog);
for (const [componentName, component] of Object.entries(ElementPlusIcons)) {
    Rivers.component(componentName, component);
}
Rivers.mount('#app');