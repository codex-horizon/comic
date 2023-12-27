'use strict';
import {useStore} from 'vuex';
import {createRouter, createWebHashHistory} from 'vue-router';
import {
    LoginView,
    IndexView,
    HomeView,
    ComicEditorView,
    ComicsView,
    AccountView,
    PermissionView
} from './modules/router.js';

const router = createRouter({
    strict: true,
    history: createWebHashHistory(process.env.VUE_APP_CONTEXT_PATH),
    routes: [
        {
            path: '/:pathMatch(.*)*',
            redirect: '/login.html'
        }, {
            meta: {
                title: '登录'
            },
            path: '/login.html',
            name: LoginView.name,
            component: LoginView
        }, {
            meta: {
                title: '首页'
            },
            path: '/index',
            name: IndexView.name,
            component: IndexView,
            redirect: '/home.html',
            children: [
                {
                    meta: {
                        title: '主页'
                    },
                    path: '/home.html',
                    name: HomeView.name,
                    component: HomeView
                },{
                    meta: {
                        title: '漫画编辑'
                    },
                    path: '/comicEditor.html',
                    name: ComicEditorView.name,
                    component: ComicEditorView
                },{
                    meta: {
                        title: '漫画列表'
                    },
                    path: '/comics.html',
                    name: ComicsView.name,
                    component: ComicsView
                },{
                    meta: {
                        title: '账号管理'
                    },
                    path: '/account.html',
                    name: AccountView.name,
                    component: AccountView
                },{
                    meta: {
                        title: '权限分配'
                    },
                    path: '/permission.html',
                    name: PermissionView.name,
                    component: PermissionView
                }
            ]
        }
    ]
});

/**
 * 全局前置守卫
 * Router.beforeEach 每次发生路由的导航跳转时，都会触发全局前置守卫，因此，在全局前置守卫中，程序员可以对每个路由进行访问权限的控制
 */
router.beforeEach(async (to, from, next) => {
    console.log('全局前置守卫', to, from, next());
});

/**
 * 全局解析守卫
 * Router.beforeResolve 是获取数据或执行任何其他操作（如果用户无法进入页面时你希望避免执行的操作）的理想位置
 */
router.beforeResolve(async (to, from, next) => {
    console.log('全局解析守卫', to, from, next());
});

/**
 * 全局后置守卫
 * Router.afterEach 对于分析、更改页面标题、声明页面等辅助功能以及许多其他事情都很有用。
 */
router.afterEach(async (to, from, failure) => {
    await useStore().dispatch('asyncSetBreadcrumbs', to.matched);
    console.log('全局后置守卫', to, from, failure);
});

export default router;