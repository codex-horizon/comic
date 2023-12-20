
// ------------------------ 系统入口 ------------------------
export const LoginView = () => import('@/assembly/before/LoginView.vue');
export const IndexView = () => import('@/assembly/after/IndexView.vue');
export const HomeView = () => import('@/assembly/after/HomeView.vue');

// ------------------------ 功能类别 ------------------------
// export const FuncCategoryView = () => import('@/assembly/after/funcCategory/FuncCategoryView.vue');
// export const ActiveUsersView = () => import('@/assembly/after/funcCategory/ActiveUsersView.vue');
// export const UserLotteryRecordsView = () => import('@/assembly/after/funcCategory/UserLotteryRecordsView.vue');
// export const CustomerServiceConfigView = () => import('@/assembly/after/funcCategory/CustomerServiceConfigView.vue');
// export const AreaCodeListView = () => import('@/assembly/after/funcCategory/AreaCodeListView.vue');
// export const UserManagementView = () => import('@/assembly/after/funcCategory/UserManagementView.vue');
// export const AuthorityManagementView = () => import('@/assembly/after/funcCategory/AuthorityManagementView.vue');

// ------------------------ 系统设置 ------------------------
// export const SystemManagementView = () => import('@/assembly/after/systemManagement/SystemManagementView.vue');
// export const UsersManagementView = () => import('@/assembly/after/systemManagement/UsersManagementView.vue');
// export const PlatformManagementView = () => import('@/assembly/after/systemManagement/PlatformManagementView.vue');
// export const MenuManagementView = () => import('@/assembly/after/systemManagement/MenuManagementView.vue');

// ------------------------ 个人中心 ------------------------
// export const PersonalView = () => import('@/assembly/after/personal/PersonalView.vue');
// export const PersonalInformationView = () => import('@/assembly/after/personal/PersonalInformationView.vue');
// export const PasswordModifyView = () => import('@/assembly/after/personal/PasswordModifyView.vue');


export default {
    LoginView,
    IndexView,
    HomeView,
    //
    // FuncCategoryView,
    // ActiveUsersView,
    // UserLotteryRecordsView,
    // CustomerServiceConfigView,
    // AreaCodeListView,
    // UserManagementView,
    // AuthorityManagementView,
    //
    // SystemManagementView,
    // UsersManagementView,
    // PlatformManagementView,
    // MenuManagementView,
    //
    // PersonalView,
    // PersonalInformationView,
    // PasswordModifyView
}