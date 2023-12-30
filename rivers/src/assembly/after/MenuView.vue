<template>
  <div class="container-layout">
    <div class="search-layout">
      <div class="search-title"/>
      <el-form :inline="true" :model="formSearch">
        <el-form-item label="菜单名称：">
          <el-input v-model="formSearch.name" placeholder="菜单名称"/>
        </el-form-item>
        <el-form-item label="菜单路由：">
          <el-input v-model="formSearch.path" placeholder="菜单路由"/>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="Search" circle @click="onSearchQuery"/>
          <el-button type="primary" icon="Plus" circle plain @click="onPreAddHandler"/>
        </el-form-item>
      </el-form>
    </div>
    <div class="table-layout">
      <el-table :data="tableData" style="width: 100%" height="440">
        <el-table-column fixed prop="id" label="标识" width="200"/>
        <el-table-column prop="name" label="菜单名称" width="200">
          <template #default="scope">
            <span v-html="scope.row.name"/>
          </template>
        </el-table-column>
        <el-table-column prop="path" label="路由" width="200"/>
        <el-table-column prop="icon" label="图标" width="200">
          <template #default="scope">
            <el-icon>
              <component :is="scope.row.icon"/>
            </el-icon>
          </template>
        </el-table-column>
        <el-table-column prop="sort" label="排序" width="200"/>
        <el-table-column prop="createdBy" label="创建人" width="200"/>
        <el-table-column prop="createdDate" label="创建时间" width="200"/>
        <el-table-column prop="lastModifiedBy" label="最后修改人" width="200"/>
        <el-table-column prop="lastModifiedDate" label="最后修改时间" width="200"/>
        <el-table-column fixed="right" label="操作" width="120">
          <template #default="scope">
            <el-button link type="primary" size="small" @click="onPreEditorHandler(scope.row)">编辑</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div class="pagination-layout">
      <el-pagination v-model:current-page="pageableQry.currentPage"
                     @current-change="currentChangeHandler"
                     :total="pageableQry.total"
                     background layout="prev, pager, next"/>
    </div>
    <HorizonDialog v-if="this.$options.name === this.$store.getters['messengerStore/getDialogCurrentView']">
      <!--<template v-slot:header></template>-->
      <template v-slot:body>
        <el-form class="login" status-icon :model="form" ref="formRef">
          <el-form-item prop="name" :rules="[{required: true, message: '角色名称 空', trigger: 'blur'}]">
            <el-input v-model="form.name" placeholder="角色名称" prefix-icon="User" clearable/>
          </el-form-item>
          <el-form-item prop="path" :rules="[{required: true, message: '路由 空', trigger: 'blur'}]">
            <el-input v-model="form.path" placeholder="路由" prefix-icon="User" clearable/>
          </el-form-item>
          <el-form-item prop="icon" :rules="[{required: true, message: '图标 空', trigger: 'blur'}]">
            <el-input v-model="form.icon" placeholder="图标" prefix-icon="User" clearable/>
          </el-form-item>
          <el-form-item prop="sort" :rules="[{required: true, message: '排序 空', trigger: 'blur'}]">
            <el-input v-model="form.sort" placeholder="排序" prefix-icon="User" clearable/>
          </el-form-item>
          <!--          <el-form-item prop="parentId" :rules="[{required: true, message: '父节点 空', trigger: 'blur'}]">-->
          <!--            <el-select v-model="form.parentId" placeholder="父节点">-->
          <!--              <el-option-->
          <!--                  v-for="item in options"-->
          <!--                  :key="item.value"-->
          <!--                  :label="item.label"-->
          <!--                  :value="item.value"/>-->
          <!--            </el-select>-->
          <!--          </el-form-item>-->
          <el-form-item>
            <el-button v-if="currentAction === 'add'" type="primary" icon="Plus" circle plain
                       @click="onAddHandler('formRef')" :disabled="disabled"/>
            <el-button v-if="currentAction === 'editor'" type="primary" icon="EditPen" circle plain
                       @click="onEditorHandler('formRef')" :disabled="disabled"/>
          </el-form-item>
        </el-form>
      </template>
      <!--<template v-slot:footer></template>-->
    </HorizonDialog>
  </div>
</template>
<script>
import {menuApi} from "@/api/index.js";

export default {
  name: 'MenuView',
  data() {
    return {
      currentAction: '',
      form: {
        id: '',
        name: '',
        path: '',
        icon: '',
        sort: 0
      },
      // options:[
      //   {
      //     value: '',
      //     label: '',
      //   },
      //   {
      //     value: 'Option1',
      //     label: 'Option1',
      //   },
      //   {
      //     value: 'Option2',
      //     label: 'Option2',
      //   },
      //   {
      //     value: 'Option3',
      //     label: 'Option3',
      //   },
      //   {
      //     value: 'Option4',
      //     label: 'Option4',
      //   },
      //   {
      //     value: 'Option5',
      //     label: 'Option5',
      //   },
      // ],
      disabled: false,
      formSearch: {
        name: '',
        path: ''
      },
      tableData: [],
      pageableQry: {
        currentIndex: 1,
        pageableSize: 10,
        total: 0,
        conditions: [{name: '', calc: '', value: '', logic: ''}],
        direction: 'DESC',
        properties: ['uptime']
      }
    }
  },
  methods: {
    onPreEditorHandler(editor) {
      this.$store.commit('messengerStore/setDialogCurrentView', this.$options.name);
      this.$store.commit('messengerStore/setDialogVisible', true);
      this.$store.commit('messengerStore/setDialogTitle', '菜单编辑');
      this.$store.commit('messengerStore/setDialogWidth', '46%');
      this.$store.commit('messengerStore/setDialogFooter', false);
      this.currentAction = 'editor';

      this.form.id = editor.id;
      this.form.name = editor.name;
      this.form.path = editor.path;
      this.form.icon = editor.icon;
      this.form.sort = editor.sort;
    },
    onEditorHandler(formName) {
      this.$refs[formName].validate(async valid => {
        if (valid) {
          this.disabled = true;
          menuApi.update(this.form).then(res => {
            this.disabled = false;
            if ('Biz_Ok_Response' === res.code) {
              this.$message.success('更新成功。');
              this.onSearchQuery();
              this.$store.commit('messengerStore/setDialogVisible', false);
            }
            if ('Biz_Failed_Response' === res.code) {
              this.$message.error(res.message);
            }
          });
        }
      });
    },
    onPreAddHandler() {
      this.$store.commit('messengerStore/setDialogCurrentView', this.$options.name);
      this.$store.commit('messengerStore/setDialogVisible', true);
      this.$store.commit('messengerStore/setDialogTitle', '菜单新增');
      this.$store.commit('messengerStore/setDialogWidth', '46%');
      this.$store.commit('messengerStore/setDialogFooter', false);
      this.currentAction = 'add';
      this.form.id = '';
      this.form.name = '';
      this.form.path = '';
      this.form.icon = '';
      this.form.sort = 0;
    },
    onAddHandler(formName) {
      this.$refs[formName].validate(async valid => {
        if (valid) {
          this.disabled = true;
          menuApi.add(this.form).then(res => {
            this.disabled = false;
            if ('Biz_Ok_Response' === res.code) {
              this.$message.success('新增成功。');
              this.onSearchQuery();
              this.$store.commit('messengerStore/setDialogVisible', false);
            }
            if ('Biz_Failed_Response' === res.code) {
              this.$message.error(res.message);
            }
          });
        }
      });
    },
    onSearchQuery() {
      this.fetchPageable();
    },
    currentChangeHandler(currentPage) {
      this.pageableQry.currentIndex = currentPage;
      this.onSearchQuery();
    },
    fetchPageable() {
      Object.assign(this.pageableQry, this.formSearch);
      console.log(this.pageableQry);
      menuApi.fetchPageable(this.pageableQry).then(res => {
        this.pageableQry.total = res.data.total;
        this.tableData = res.data.list;
      })
    },
    initialize() {
      this.fetchPageable();
    }
  },
  mounted() {
    this.initialize()
  }
}
</script>
<style scoped lang="scss">
.container-layout {
  padding: 16px;

  .search-layout {
    margin-bottom: 12px;

    .search-title::before {
      content: "查询条件";
      border-left: 4px solid #05a9d9;
      box-sizing: border-box;
      padding: 0 0 0 8px;
    }

    :deep(.el-form) {
      margin-top: 12px;
      padding: 0 12px;
    }
  }

  .table-layout {
    margin-bottom: 12px;
  }

  .pagination-layout {
    margin-bottom: 12px;

    :deep(.el-pagination) {
      padding-top: 12px;
      display: flex;
      justify-content: right;
      align-items: center;
    }
  }

  :deep(.el-form-item:last-child > .el-form-item__content) {
    justify-content: right;
  }
}
</style>