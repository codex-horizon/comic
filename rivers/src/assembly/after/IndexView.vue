<template>
  <div class="container">
    <div class="menu-unit">
      <ScrollbarView>
        <MenuView/>
      </ScrollbarView>
    </div>
    <div class="main-unit">
      <ScrollbarView>
        <div class="head-unit">
          1
        </div>
        <div class="cont-unit">
          <div class="asd">
            <router-view/>
          </div>
        </div>
      </ScrollbarView>
    </div>
  </div>
</template>

<script>
import ScrollbarView from '@/components/ScrollbarView.vue';
import MenuView from "@/assembly/after/components/MenuView.vue";

export default {
  name: "IndexView",
  data() {
    return {}
  },
  props: {},
  components: {
    ScrollbarView,
    MenuView
  },
  methods: {}
};
</script>

<style scoped lang="scss">
.container {
  display: flex;

  .menu-unit, .main-unit {
    border-radius: 12px;
    box-sizing: border-box;
    border: 1px dashed #663399;
  }

  .menu-unit {
    flex: 0 0 calc(100% - 80%);
    margin: 18px 9px 18px 18px;
  }

  .main-unit {
    flex: 1 1 auto;
    margin: 18px 18px 18px 9px;
  }

  .head-unit {
    position: absolute;
    top: 0;

    width: 100%;
    height: 56px;
    border-top-left-radius: 12px;
    border-top-right-radius: 12px;
    background-image: radial-gradient(transparent 1px, #ffffff 1px);
    background-size: 4px 4px;
    backdrop-filter: saturate(50%) blur(4px);
  }

  .cont-unit {
    border-radius: 12px;

    display: flex;
  }

  .asd {
    width: 100px;
    background-color: gray;
  }
}

</style>