<template>
  <div class="module-container">
    <ul class="menu-list">
      <li class="menu-item" v-for="({name, path, icon}, index) in menus" :key="index">
        <div class="menu-item-layout">
          <router-link :to="path">
            <el-icon>
              <component :is="icon"/>
            </el-icon>
            <span class="menu-name" v-html="name"/>
          </router-link>
        </div>
      </li>
    </ul>
  </div>
</template>

<script>
import {getCookie, getSession} from '@/utils';

export default {
  name: "MenuView",
  data() {
    return {
      menus: [
        {
          name: '主&#12288;&#12288;页',
          route: '/home.html',
          icon: 'House'
        },
        {
          name: '漫画列表',
          route: '/comicList.html',
          icon: 'Operation'
        },
        {
          name: '账号管理',
          route: '/account.html',
          icon: 'User'
        },
        {
          name: '角色管理',
          route: '/role.html',
          icon: 'User'
        },
        {
          name: '菜单管理',
          route: '/menu.html',
          icon: 'Connection'
        }
      ]
    }
  },
  props: {},
  components: {},
  methods: {},
  created() {
    this.menus = JSON.parse(getSession(getCookie('UUID')));
  }
};
</script>

<style scoped lang="scss">
.module-container {

  .menu-list {
    list-style-type: none;
    margin: 0;
    padding: 0;
  }

  .menu-item {
    padding: 16px 16px 0;


    .menu-item-layout {
      width: 100%;
      height: 35px;
      background-color: #f0f4ff;
      border-radius: 12px;

      .menu-name {
        margin-left: 6px;
      }

      :deep(a) {
        font-weight: bold;
        text-decoration: inherit;
        color: #6a75ca;
        font-size: 14px;
        width: 100%;
        height: 100%;
        display: flex;
        justify-content: center;
        align-items: center;
      }
    }

    .menu-item-layout:hover {

    }
  }

}
</style>