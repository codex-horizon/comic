<template>
  <div class="module-container">
    <ul class="menu-list">
      <li class="menu-item" v-for="(menu,index) in menus" :key="index">
        <div class="menu-item-layout">
          <router-link :to="menu.route">
            <el-icon>
              <component :is="menu.icon"/>
            </el-icon>
            <span class="menu-name" v-html="menu.name"/>
          </router-link>
        </div>
      </li>
    </ul>
  </div>
</template>

<script>

export default {
  name: "MenuView",
  data() {
    return {
      menus: [
        {
          name:'主&#12288;&#12288;页',
          route: '/home.html',
          icon: 'House'
        },
        {
          name: '漫画编辑',
          route: '/comicEditor.html',
          icon: 'EditPen'
        },
        {
          name: '漫画列表',
          route: '/comics.html',
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
          name: '权限分配',
          route: '/permission.html',
          icon: 'Connection'
        },
        {
          name: '漫画权限',
          route: '/comicPermission.html',
          icon: 'Connection'
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
  methods: {}
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