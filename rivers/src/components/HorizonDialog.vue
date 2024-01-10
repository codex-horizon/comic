<template>
  <el-dialog ref="dialog"
             v-model="dialogVisible"
             :fullscreen="Boolean(dialogFullscreen)"
             v-bind:before-close=" () => this.$store.commit('messengerStore/setDialogVisible', false) "
             v-bind:destroy-on-close="true"
             v-bind:title="`${dialogTitle}`"
             v-bind:width="`${dialogWidth}`"
             draggable>

    <!--<template #header>
      <slot name="header"/>
    </template>-->

    <template #default>
      <slot name="body"/>
    </template>

    <!--<template #footer>
      <slot name="footer"/>
    </template>-->

    <!--<template v-slot:footer>
      <slot name="footer"/>
    </template>-->

  </el-dialog>
</template>
<script>
export default {
  name: 'HorizonDialog',
  computed: {
    // 函数形式的计算属性
    dialogWidth: function () {
      return this.$store.getters['messengerStore/getDialogWidth'];
    },
    dialogTitle: function () {
      return this.$store.getters['messengerStore/getDialogTitle'];
    },
    dialogFullscreen: function () {
      return this.$store.getters['messengerStore/getDialogFullScreen'];
    },
    // 对象形式的计算属性
    dialogVisible: {
      get: function () {
        return this.$store.getters['messengerStore/getDialogVisible'];
      },
      set: function (newValue) {
        this.$store.commit('messengerStore/setDialogVisible', newValue);
      }
    }
  }
}
</script>
<style lang="scss">
.el-dialog {
  border-radius: 8px;
}
</style>