<template>
  <div class="module-container">
    <ul class="menu-list">
      <li class="menu-item">
        <div class="menu-item-layout">
          <router-link to="#">主页</router-link>
        </div>
      </li>
      <li class="menu-item">
        <div class="menu-item-layout">
          <router-link to="#">漫画编辑</router-link>
        </div>
      </li>
      <li class="menu-item">
        <div class="menu-item-layout">
          <router-link to="#">漫画列表</router-link>
        </div>
      </li>
      <li class="menu-item">
        <div class="menu-item-layout">
          <router-link to="#">账号管理</router-link>
        </div>
      </li>
    </ul>
  </div>
</template>

<script>

export default {
  name: "MenuView",
  data() {
    return {}
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
    padding: 16px 28px 0;


    .menu-item-layout {
      width: 100%;
      height: 35px;
      background-color: #f0f4ff;
      border-radius: 12px;

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

  }

}
</style>