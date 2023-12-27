
// ------------------------ 系统入口 ------------------------
export const LoginView = () => import('@/assembly/before/LoginView.vue');
export const IndexView = () => import('@/assembly/after/IndexView.vue');
export const HomeView = () => import('@/assembly/after/HomeView.vue');
export const ComicEditorView = () => import('@/assembly/after/ComicEditorView.vue');
export const ComicsView = () => import('@/assembly/after/ComicsView.vue');
export const AccountView = () => import('@/assembly/after/AccountView.vue');
export const PermissionView = () => import('@/assembly/after/PermissionView.vue');


export default {
    LoginView,
    IndexView,
    HomeView,
    ComicEditorView,
    ComicsView,
    AccountView,
    PermissionView
}