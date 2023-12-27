<template>
  <div class="container-layout">
    <HorizonTable>
      <template v-slot:search>
        <div class="search-list">
          <el-row :gutter="20">
            <el-col :span="6">
              <el-input>
                <template #prepend>
                  <el-select placeholder="条件项" style="width: 100px;">
                    <el-option label="Restaurant" value="1"/>
                    <el-option label="Order No." value="2"/>
                    <el-option label="Tel" value="3"/>
                  </el-select>
                </template>
              </el-input>
            </el-col>
            <el-col :span="6">
              <el-input>
                <template #prepend>
                  <el-select placeholder="条件项" style="width: 115px">
                    <el-option label="Restaurant" value="1"/>
                    <el-option label="Order No." value="2"/>
                    <el-option label="Tel" value="3"/>
                  </el-select>
                </template>
              </el-input>
            </el-col>
            <el-col :span="6">
              <el-input>
                <template #prepend>
                  <el-select placeholder="条件项" style="width: 115px">
                    <el-option label="Restaurant" value="1"/>
                    <el-option label="Order No." value="2"/>
                    <el-option label="Tel" value="3"/>
                  </el-select>
                </template>
              </el-input>
            </el-col>
            <el-col :span="6" class="search-block">
              <el-button icon="Search" type="primary"/>
              <el-button icon="More"/>
            </el-col>
          </el-row>
        </div>
      </template>
    </HorizonTable>
  </div>
</template>
<script>
export default {
  name: 'ComicEditorView',
  methods: {
    editorHandler() {
      this.$store.commit('messengerStore/setDialogCurrentView', this.$options.name);
      this.$store.commit('messengerStore/setDialogVisible', true);
      this.$store.commit('messengerStore/setDialogTitle', '我的信息');
      this.$store.commit('messengerStore/setDialogWidth', '38%');
      this.$store.commit('messengerStore/setDialogFooter', false);
    }
  }
}
</script>
<style scoped lang="scss">
.container-layout {
  padding: 16px;

  .search-list {
    padding-top: 12px;

    .search-block {
      display: flex;
      justify-content: center;
      align-items: center;
    }

    :deep(.el-input-group__prepend){
      background: transparent;
    }
  }
}
</style>