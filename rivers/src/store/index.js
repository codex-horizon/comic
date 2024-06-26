'use strict';
import {createStore} from 'vuex';
import messengerStore from './modules/messengerStore.js';

export default createStore({
    strict: /*process.env.NODE_ENV !== 'production'*/false,
    namespaced: true,
    state() {
        return {
            menus: [
                {pid: 1, id: 11, name: '主页', icon: 'HomeFilled', route: '/home.html', children: []},
                {
                    pid: 2, id: 21, name: '功能类别', icon: 'Operation', route: '/funcCategory', children: [
                        {
                            pid: 21,
                            id: 211,
                            name: '活动用户',
                            icon: 'ElementPlus',
                            route: '/activeUsers.html',
                            children: []
                        },
                        {
                            pid: 21,
                            id: 212,
                            name: '用户抽奖记录',
                            icon: 'Tickets',
                            route: '/userLotteryRecords.html',
                            children: []
                        },
                        {
                            pid: 21,
                            id: 213,
                            name: '客服配置',
                            icon: 'Guide',
                            route: '/customerServiceConfig.html',
                            children: []
                        },
                        {
                            pid: 21,
                            id: 214,
                            name: '区号列表',
                            icon: 'Location',
                            route: '/areaCodeList.html',
                            children: []
                        },
                        {pid: 21, id: 215, name: '用户管理', icon: 'User', route: '/userManagement.html', children: []},
                        {
                            pid: 21,
                            id: 216,
                            name: '权限管理',
                            icon: 'Connection',
                            route: '/authorityManagement.html',
                            children: []
                        },
                    ]
                },
                {
                    pid: 3, id: 31, name: '系统设置', icon: 'Tools', route: '/systemManagement', children: [
                        {
                            pid: 31,
                            id: 311,
                            name: '用户管理',
                            icon: 'UserFilled',
                            route: '/usersManagement.html',
                            children: []
                        },
                        {
                            pid: 31,
                            id: 312,
                            name: '平台管理',
                            icon: 'Monitor',
                            route: '/platformManagement.html',
                            children: []
                        },
                        {pid: 31, id: 313, name: '菜单管理', icon: 'Menu', route: '/menuManagement.html', children: []},
                    ]
                },
                {
                    pid: 4, id: 41, name: '个人中心', icon: 'User', route: '/personal', children: [
                        {
                            pid: 41,
                            id: 411,
                            name: '个人信息',
                            icon: 'UserFilled',
                            route: '/personalInformation.html',
                            children: []
                        },
                        {
                            pid: 42,
                            id: 412,
                            name: '密码修改',
                            icon: 'UserFilled',
                            route: '/passwordModify.html',
                            children: []
                        },
                        {
                            pid: 43, id: 413, name: '二级菜单', icon: 'ArrowDown', route: '#', children: [
                                {pid: 414, id: 4141, name: '三级菜单', icon: 'ArrowRight', route: '#', children: []}
                            ]
                        },
                    ]
                },
            ],
            breadcrumbs: [],
            icons:[]
        }
    },
    getters: {
        getMenus(state) {
            return state.menus;
        },
        getBreadcrumbs(state) {
            return state.breadcrumbs;
        },
        getIcons(state) {
            return state.icons;
        }
    },
    mutations: {
        setMenus(state, menus) {
            state.menus = menus;
        },
        setBreadcrumbs(state, breadcrumbs) {
            state.breadcrumbs = breadcrumbs
        },
        setIcons(state, icons) {
            state.icons = icons
        }
    },
    actions: {
        asyncSetMenus(context, menus) {
            context.commit('setMenus', menus);
        },
        asyncSetBreadcrumbs({commit}, breadcrumbs) {
            // const filteredBreadcrumbs = breadcrumbs.filter(breadcrumb => {
            //     return breadcrumb.redirect === undefined;
            // });
            commit('setBreadcrumbs', breadcrumbs);
        },
        asyncSetIcons(context, icons) {
            context.commit('setIcons', icons);
        },
    },
    modules: {
        messengerStore
    }
});