<template>
  <!--******************************************* Virtual 主体 Elements Start *******************************************-->
  <div class="container">
    <div class="head-unit">
      <div class="logo">
        <el-avatar :size="56" :src="logoURL" fit="cover"/>
        <span class="title">耽次元漫画</span>
      </div>
      <div class="toolbar">
        <div class="breadcrumb">
          <el-breadcrumb separator-icon="ArrowRight">
            <el-breadcrumb-item v-for="(breadcrumb, index) in this.$store.getters.getBreadcrumbs"
                                :to="{path: breadcrumb.path}" :key="index">
              <span>{{ breadcrumb.meta.title }}</span>
            </el-breadcrumb-item>
          </el-breadcrumb>
        </div>
        <el-dropdown trigger="click">
          <span class="dropdown-link">
            <el-avatar :size="48" :src="avatarURL" fit="cover"/>
          </span>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item @click="personalInfoHandler">
                <el-icon>
                  <User/>
                </el-icon>
                <span>我的信息</span>
              </el-dropdown-item>
              <el-dropdown-item disabled>
                <el-icon>
                  <CloseBold/>
                </el-icon>
                <span>禁用功能</span>
              </el-dropdown-item>
              <el-dropdown-item divided @click="exitLogoutHandler">
                <el-icon>
                  <SwitchButton/>
                </el-icon>
                <span>退出注销</span>
              </el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </div>
    </div>
    <div class="menu-unit">
      <MenuView/>
    </div>
    <div class="main-unit">
      <router-view/>
    </div>
  </div>
  <!--*****************************************   Virtual 主体 Elements End     *****************************************-->

  <!--******************************************* Virtual 临时 Elements Start *******************************************-->
  <HorizonDialog v-if="this.$options.name === this.$store.getters['messengerStore/getDialogCurrentView']">
    <!--<template v-slot:header></template>-->
    <template v-slot:body>
      <el-descriptions title="信息详情">
        <el-descriptions-item label="账号">super</el-descriptions-item>
        <el-descriptions-item label="密码">123456</el-descriptions-item>
      </el-descriptions>
    </template>
  </HorizonDialog>
  <!--*****************************************   Virtual 临时 Elements End     *****************************************-->
</template>

<script>
import MenuView from '@/assembly/after/components/MenuView.vue';

export default {
  name: "IndexView",
  data() {
    return {
      logoURL: require('@/assets/logo.svg'),
      avatarURL: require('@/assets/avatar.jpeg'),
    }
  },
  props: {},
  components: {MenuView},
  methods: {
    personalInfoHandler() {
      this.$store.commit('messengerStore/setDialogCurrentView', this.$options.name);
      this.$store.commit('messengerStore/setDialogVisible', true);
      this.$store.commit('messengerStore/setDialogTitle', '我的信息');
      this.$store.commit('messengerStore/setDialogWidth', '38%');
      this.$store.commit('messengerStore/setDialogFooter', false);
    },
    exitLogoutHandler() {
      this.$message({
        message: '退出注销成功',
        type: 'success',
        grouping: true,
        center: true,
        duration: 1000,
        onClose: () => this.$router.push('/login.html')
      });
    }
  }
};
</script>

<style scoped lang="scss">
.container {
  display: flex;

  .head-unit {
    position: fixed;
    z-index: 9;
    top: 0;
    left: 0;
    right: 0;
    height: 64px;
    box-shadow: 0 0 18px -4px rgba(0, 0, 0, 0.2);
    background-color: #ffffff;

    //background-image: radial-gradient(transparent 1px, #ffffff 1px);
    //background-size: 4px 4px;
    //backdrop-filter: saturate(50%) blur(4px);

    display: flex;
    justify-content: center;
    align-items: center;

    .logo {
      flex: 0 0 256px;
      display: flex;
      justify-content: center;
      align-items: center;
      width: 100%;
      height: 100%;

      :deep(.el-avatar--circle) {
        background-color: transparent;
      }

      .title {
        padding-left: 8px;
        font-weight: bold;
      }
    }

    .toolbar {
      flex: 1 1 auto;
      display: flex;
      justify-content: space-between;
      align-items: center;
      width: 100%;
      height: 100%;
      padding-right: 12px;

      .breadcrumb{
        height: 100%;
        width: auto;
        display: flex;
        justify-content: center;
        align-items: center;
      }

      .dropdown-link {
        cursor: pointer;
      }
    }
  }

  .menu-unit {
    flex: 0 0 256px;
    background-color: #ffffff;
    box-shadow: 0 0 18px -4px rgba(0, 0, 0, 0.2);
    box-sizing: border-box;
    border-right: 1px solid #e8e8e8;
    padding-top: 64px;
  }

  .menu-unit::before {
    content: "";
    width: 16px;
    height: 16px;
    background: url(@/assets/arrowhead.svg) no-repeat center, linear-gradient(0deg, #ffffff, #ffffff);
    background-size: cover;
    display: flex;
    justify-content: center;
    align-items: center;
    border-radius: 12px;
    position: fixed;
    z-index: 1;
    left: calc(256px - (16px / 2));
    top: calc((64px - (16px / 2)) + 80%);
    cursor: pointer;
    transform: rotate(90deg);
    box-shadow: 0 0 18px -2px #6a75ca;
    border: 1px dashed #6a75ca;
    box-sizing: border-box;
  }

  .main-unit {
    flex: 1 1 auto;
    background-color: #ffffff;
    overflow: auto;
    padding-top: 64px;

    :deep(.el-breadcrumb) {
      padding: 16px 16px 0;
    }

    :deep(.el-breadcrumb__inner > *) {
      font-size: 14px !important;
    }

    :deep(.el-breadcrumb__separator) {
      position: unset;
    }
  }
}
</style>