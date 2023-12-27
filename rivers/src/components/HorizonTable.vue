<template>
  <div class="search-layout">
    <div class="search-title"/>
    <slot name="search"/>
  </div>
  <div class="table-layout">
    <div class="table-title"/>
    <el-table :data="this.$store.getters['messengerStore/getTableData']">
      <el-table-column label="Date" prop="date"/>
      <el-table-column label="Name" prop="name"/>
      <el-table-column align="right">
        <template #header>
          <span>操作</span>
        </template>
        <template #default="scope">
          <el-button @click="viewHandler(scope.$index, scope.row)" size="small" icon="View"/>
          <el-button @click="editHandler(scope.$index, scope.row)" size="small" type="primary" plain icon="Edit"/>
          <el-button @click="deleteHandler(scope.$index, scope.row)" size="small" type="danger" icon="Delete"/>
        </template>
      </el-table-column>
    </el-table>
  </div>
  <div class="pagination-layout">
    <div class="pagination-title"/>
    <el-pagination
        v-model:current-page="this.$store.getters['messengerStore/getTablePaginationCurrentPage']"
        v-model:page-size="this.$store.getters['messengerStore/getTablePaginationPageSize']"
        :page-sizes="this.$store.getters['messengerStore/getTablePaginationPageSizes']"
        :background="this.$store.getters['messengerStore/getTablePaginationBackground']"
        :layout="this.$store.getters['messengerStore/getTablePaginationLayout']"
        :total="this.$store.getters['messengerStore/getTablePaginationTotal']"
        @size-change="sizeChangeHandler" @current-change="currentChangeHandler"/>
  </div>

  <HorizonDialog v-if="this.$options.name === this.$store.getters['messengerStore/getDialogCurrentView']">
    <!--<template v-slot:header></template>-->
    <template v-slot:body>
    </template>
    <!--<template v-slot:footer></template>-->
  </HorizonDialog>
</template>
<script>
export default {
  name: 'HorizonTable',
  computed: {
    hasSlot() {
      return Boolean(this.$slots.default)
    }
  },
  methods: {
    sizeChangeHandler() {
      alert('sizeChangeHandler');
    },
    currentChangeHandler() {
      alert('currentChangeHandler');
    },
    viewHandler(index, row) {
    },
    editHandler(index, row) {
    },
    deleteHandler(index, row) {
    }
  }
}
</script>
<style scoped lang="scss">
.search-layout {
  margin-bottom: 12px;

  .search-title::before {
    content: "查询条件";
    border-left: 4px solid #05a9d9;
    box-sizing: border-box;
    padding: 0 0 0 8px;
  }
}

.table-layout {
  margin-bottom: 12px;

  .table-title::before {
    content: "查询结果";
    border-left: 4px solid #99c236;
    box-sizing: border-box;
    padding: 0 0 0 8px;
  }
}

.pagination-layout {
  margin-bottom: 12px;

  .pagination-title::before {
    content: "分页参数";
    border-left: 4px solid #99c236;
    box-sizing: border-box;
    padding: 0 0 0 8px;
  }

  :deep(.el-pagination) {
    padding-top: 12px;
    display: flex;
    justify-content: right;
    align-items: center;
  }
}

</style>