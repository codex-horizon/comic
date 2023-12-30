
// ------------------------ 系统入口 ------------------------
export const LoginView = () => import('@/assembly/before/LoginView.vue');
export const IndexView = () => import('@/assembly/after/IndexView.vue');
export const HomeView = () => import('@/assembly/after/HomeView.vue');
export const ComicsView = () => import('@/assembly/after/ComicsView.vue');
export const AccountView = () => import('@/assembly/after/AccountView.vue');
export const RoleView = () => import('@/assembly/after/RoleView.vue');
export const MenuView = () => import('@/assembly/after/MenuView.vue');


export default {
    LoginView,
    IndexView,
    HomeView,
    ComicsView,
    AccountView,
    RoleView,
    MenuView
}