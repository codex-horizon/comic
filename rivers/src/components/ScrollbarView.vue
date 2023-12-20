<template>
  <el-scrollbar class="scrollbar-container" v-bind:style="this.$store.getters['messengerStore/getInlineStyleScrollbar']">
    <slot/>
  </el-scrollbar>
</template>
<script>
export default {
  name: "ScrollbarView"
}
</script>
<style scoped lang="scss">
</style>