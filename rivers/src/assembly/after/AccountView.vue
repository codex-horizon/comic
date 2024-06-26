<template>
  <div class="container-layout">
    <div class="search-layout">
      <div class="search-title"/>
      <el-form :inline="true" :model="formSearch">
        <el-form-item label="账户名称：">
          <el-input v-model="formSearch.username" placeholder="账户名称"/>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" icon="Search" circle @click="onSearchQuery"/>
          <el-button type="primary" icon="Plus" circle plain @click="onPreAddHandler"/>
        </el-form-item>
      </el-form>
    </div>
    <div class="table-layout">
      <el-table :data="tableData" style="width: 100%" height="440">
        <el-table-column fixed prop="id" label="标识" width="120"/>
        <el-table-column prop="username" label="账号" width="120"/>
        <el-table-column prop="role" label="角色名称" width="120">
          <template #default="scope">
            <el-tag :type="scope.row.username !== 'admin'?'info':'success'">
              {{ scope.row?.role?.name }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="comics" label="漫画集" width="200">
          <template #default="scope">
            <div v-for="({ name, pic }, index) in scope.row.comics" :key="index" style="width: 100%;">
              <el-tag>
                <el-avatar v-if="pic" size="small" :src="`http://image.fm1100.com/${pic}`"/>
                <el-avatar v-else size="small">{{ name.charAt(0) }}</el-avatar>
                <div class="singe-line">
                  <span v-html="name" style="margin-left: 8px;"></span>
                </div>
              </el-tag>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="password" label="密码" width="100"/>
        <el-table-column prop="createdBy" label="创建人" width="200"/>
        <el-table-column prop="createdDate" label="创建时间" width="200"/>
        <el-table-column prop="lastModifiedBy" label="最后修改人" width="200"/>
        <el-table-column prop="lastModifiedDate" label="最后修改时间" width="200"/>
        <el-table-column fixed="right" label="操作" width="120">
          <template #default="scope">
            <el-button link type="primary" size="small" @click="onPreEditorHandler(scope.row)">编辑</el-button>
            <el-button v-if="scope.row.username !== 'admin'" link type="warning" size="small"
                       @click="onDeleteHandler(scope.row.id)">删除
            </el-button>
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
          <el-form-item prop="username" :rules="[{required: true, message: '账号 空', trigger: 'blur'}]">
            <el-input v-model="form.username" placeholder="账号" prefix-icon="User" clearable/>
          </el-form-item>
          <el-form-item prop="password" :rules="[{required: true, message: '密码 空', trigger: 'blur'}]">
            <el-input v-model="form.password" placeholder="密码" prefix-icon="Key" show-password clearable/>
          </el-form-item>
          <el-form-item prop="roleId" :rules="[{required: true, message: '角色 空', trigger: 'blur'}]"
                        v-if="form.username !== 'admin'">
            <el-select v-model="form.roleId" placeholder="角色" prefix-icon="MilkTea" clearable>
              <el-option v-for="({id, name}, index) in roles" :key="index" :label="name" :value="id"/>
            </el-select>
          </el-form-item>
          <el-form-item prop="comicIds" :rules="[{required: true, message: '漫画集 空', trigger: 'blur'}]"
                        v-if="form.username !== 'admin'">
            <el-select v-model="form.comicIds" placeholder="漫画集" prefix-icon="MilkTea" multiple clearable>
              <el-option v-for="({id, name, pic}, index) in comics" :key="index" :label="name" :value="id">
                <template #default>
                  <div style="width: 100%; display: flex; justify-content: left; align-items: center;">
                    <el-avatar v-if="pic" size="small" :src="`http://image.fm1100.com/${pic}`"/>
                    <el-avatar v-else size="small">{{ name.charAt(0) }}</el-avatar>
                    <span v-html="name" style="margin-left: 8px;"></span>
                  </div>
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
import {userApi, roleApi, comicApi} from "@/api/index.js";

export default {
  name: 'AccountView',
  data() {
    return {
      currentAction: '',
      form: {
        id: '',
        username: '',
        password: '',
        roleId: '',
        comicIds: []
      },
      roles: [],
      comics: [],
      disabled: false,
      formSearch: {
        username: ''
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
      userApi.deleteById(id).then(res => {
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
      this.$store.commit('messengerStore/setDialogTitle', '账户编辑');
      this.$store.commit('messengerStore/setDialogWidth', '46%');
      this.$store.commit('messengerStore/setDialogFooter', false);
      this.currentAction = 'editor';

      this.form.id = editor.id;
      this.form.username = editor.username;
      this.form.password = editor.password;
      this.form.roleId = editor.role.id;
      this.form.comicIds = editor.comics.map(comic => comic.id);
    },
    onEditorHandler(formName) {
      this.$refs[formName].validate(async valid => {
        if (valid) {
          this.disabled = true;
          userApi.update(this.form).then(res => {
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
      this.$store.commit('messengerStore/setDialogTitle', '账户新增');
      this.$store.commit('messengerStore/setDialogWidth', '46%');
      this.$store.commit('messengerStore/setDialogFooter', false);
      this.currentAction = 'add';
      this.form.id = '';
      this.form.username = '';
      this.form.password = '';
      this.form.roleId = '';
      this.form.comicIds = [];
    },
    onAddHandler(formName) {
      this.$refs[formName].validate(async valid => {
        if (valid) {
          this.disabled = true;
          userApi.add(this.form).then(res => {
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
      userApi.fetchPageable(this.pageableQry).then(res => {
        this.pageableQry.total = res.data.total;
        this.tableData = res.data.list;
      })
    },
    fetchRoleList() {
      roleApi.fetchList({}).then(res => {
        this.roles = res.data;
      })
    },
    fetchComicList() {
      comicApi.fetchList({}).then(res => {
        this.comics = res.data;
      })
    },
    initialize() {
      this.fetchPageable();
      this.fetchRoleList();
      this.fetchComicList();
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

  :deep(.el-tag) {
    margin: 0 0 2px;
  }

  :deep(.el-tag), :deep(.el-tag__content) {
    width: 100%;
  }

  :deep(.el-tag__content) {
    display: flex;
    justify-content: left;
    align-items: center;
  }

  .singe-line {
    text-overflow: ellipsis;
    overflow: hidden;
    word-break: break-all;
    white-space: nowrap;
    width: 139px;
  }
}
</style>