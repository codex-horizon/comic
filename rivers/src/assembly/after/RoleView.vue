<template>
  <div class="container-layout">
    <div class="search-layout">
      <div class="search-title"/>
      <el-form :inline="true" :model="formSearch">
        <el-form-item label="角色名称：">
          <el-input v-model="formSearch.name" placeholder="角色名称"/>
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
        <el-table-column prop="name" label="角色名称" width="200"/>
        <el-table-column prop="menus" label="菜单项" width="260">
          <template #default="scope">
            <el-tag v-for="({ name,path }, index) in scope.row.menus" :key="index">
              <span v-html="name"></span> - {{ path }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createdBy" label="创建人" width="200"/>
        <el-table-column prop="createdDate" label="创建时间" width="200"/>
        <el-table-column prop="lastModifiedBy" label="最后修改人" width="200"/>
        <el-table-column prop="lastModifiedDate" label="最后修改时间" width="200"/>
        <el-table-column fixed="right" label="操作" width="120">
          <template #default="scope">
            <el-button link type="primary" size="small" @click="onPreEditorHandler(scope.row)">编辑</el-button>
            <el-button link type="warning" size="small" @click="onDeleteHandler(scope.row.id)">删除</el-button>
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
          <el-form-item prop="menuIds" :rules="[{required: true, message: '菜单 空', trigger: 'blur'}]">
            <el-select v-model="form.menuIds" placeholder="菜单" multiple>
              <el-option v-for="menu in menus" :key="menu.id" :label="menu.name" :value="menu.id">
                <template #default>
                  <span v-html="menu.name"/>
                </template>
              </el-option>
            </el-select>
          </el-form-item>
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
import {roleApi, menuApi} from "@/api/index.js";

export default {
  name: 'RoleView',
  data() {
    return {
      currentAction: '',
      form: {
        id: '',
        name: '',
        menuIds: []
      },
      menus: [],
      disabled: false,
      formSearch: {
        name: ''
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
    onDeleteHandler(id) {
      roleApi.deleteById(id).then(res => {
        if ('Biz_Ok_Response' === res.code) {
          this.$message.success('删除成功。');
          this.onSearchQuery();
        }
        if ('Biz_Failed_Response' === res.code) {
          this.$message.error(res.message);
        }
      });
    },
    onPreEditorHandler(editor) {
      this.$store.commit('messengerStore/setDialogCurrentView', this.$options.name);
      this.$store.commit('messengerStore/setDialogVisible', true);
      this.$store.commit('messengerStore/setDialogTitle', '角色编辑');
      this.$store.commit('messengerStore/setDialogWidth', '46%');
      this.$store.commit('messengerStore/setDialogFooter', false);
      this.currentAction = 'editor';

      this.form.id = editor.id;
      this.form.name = editor.name;
      this.form.menuIds = [];
    },
    onEditorHandler(formName) {
      this.$refs[formName].validate(async valid => {
        if (valid) {
          this.disabled = true;
          roleApi.update(this.form).then(res => {
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
      this.$store.commit('messengerStore/setDialogTitle', '角色新增');
      this.$store.commit('messengerStore/setDialogWidth', '46%');
      this.$store.commit('messengerStore/setDialogFooter', false);
      this.currentAction = 'add';
      this.form.id = '';
      this.form.name = '';
      this.form.menuIds = [];
    },
    onAddHandler(formName) {
      this.$refs[formName].validate(async valid => {
        if (valid) {
          this.disabled = true;
          roleApi.add(this.form).then(res => {
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
      roleApi.fetchPageable(this.pageableQry).then(res => {
        this.pageableQry.total = res.data.total;
        this.tableData = res.data.list;
      })
    },
    fetchMenuList() {
      menuApi.fetchList({}).then(res => {
        this.menus = res.data;
      })
    },
    initialize() {
      this.fetchPageable();
      this.fetchMenuList();
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

  :deep(.el-select, .el-input-number) {
    width: 100%;
  }
}
</style>