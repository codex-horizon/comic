'use strict';
import {createApp} from 'vue';
import ElementPlus from 'element-plus';
import 'element-plus/dist/index.css';
import '@/theme/index.scss';
import App from './App.vue';

import VuexStore from '@/store/index.js';
import VueRouter from '@/router/index.js';
import HorizonIcons from '@/components/HorizonIcons.js';
import HorizonDialog from '@/components/HorizonDialog.js';
import HorizonTable from '@/components/HorizonTable.js';
import HorizonScrollbar from '@/components/HorizonScrollbar.js';

createApp(App)
    .use(VueRouter)
    .use(VuexStore)
    .use(ElementPlus)
    .use(HorizonIcons)
    .use(HorizonDialog)
    .use(HorizonTable)
    .use(HorizonScrollbar)
    .mount('#app');


const debounce = (fn, delay) => {
    let timer = null;
    return function () {
        let context = this;
        let args = arguments;
        clearTimeout(timer);
        timer = setTimeout(function () {
            fn.apply(context, args);
        }, delay);
    }
}

const _ResizeObserver = window.ResizeObserver;
window.ResizeObserver = class ResizeObserver extends _ResizeObserver {
    constructor(callback) {
        callback = debounce(callback, 16);
        super(callback);
    }
}
