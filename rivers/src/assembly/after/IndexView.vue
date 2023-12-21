<template>
  <div class="container">
    <div class="head-unit">
      <div class="logo">
        <el-avatar :size="56" :src="logoURL" fit="cover"/>
        <span class="title">耽次元漫画</span>
      </div>
      <div class="toolbar">
        <el-dropdown trigger="click">
          <span class="dropdown-link">
            <el-avatar :size="48" :src="avatarURL" fit="cover"/>
          </span>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item>
                <el-icon><User /></el-icon>
                <span>我的信息</span>
              </el-dropdown-item>
              <el-dropdown-item disabled>
                <el-icon><CloseBold /></el-icon>
                <span>禁用功能</span>
              </el-dropdown-item>
              <el-dropdown-item divided>
                <el-icon><SwitchButton /></el-icon>
                <span>退出登入</span>
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
<!--      <router-view/>-->
    </div>
  </div>
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
  methods: {}
};
</script>

<style scoped lang="scss">
.container {
  display: flex;

  .head-unit {
    position: fixed;
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
      justify-content: right;
      align-items: center;
      width: 100%;
      height: 100%;
      padding-right: 12px;

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

  .main-unit {
    flex: 1 1 auto;
    background-color: #f5f5f5;
    overflow: auto;
    padding-top: 64px;
  }
}
</style>