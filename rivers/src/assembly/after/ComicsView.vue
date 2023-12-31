<template>
  <div class="container-layout">
    <div class="search-layout">
      <div class="search-title"/>
      <el-form :inline="true" :model="formSearch">
        <el-form-item label="漫画名称：">
          <el-input v-model="formSearch.name" placeholder="漫画名称"/>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" icon="Search" circle @click="onSearchQuery"/>
        </el-form-item>
      </el-form>
    </div>
    <div class="table-layout">
      <el-table :data="tableData" style="width: 100%" height="440"
                :default-sort="{ prop: 'comicId', order: 'descending' }">
        <el-table-column fixed sortable prop="comicId" label="漫画识别号" width="200"/>
        <el-table-column prop="name" label="漫画名称" width="200"/>
        <el-table-column prop="pic" label="竖版封面" width="200">
          <template #default="scope">
            <el-image :src="`http://image.fm1100.com/${scope.row.pic}`" fit="cover"/>
          </template>
        </el-table-column>
        <el-table-column prop="picx" label="横版封面" width="200">
          <template #default="scope">
            <el-image :src="`http://image.fm1100.com/${scope.row.picx}`" fit="cover"/>
          </template>
        </el-table-column>
        <el-table-column prop="comicChapters" label="章节" width="200">
          <template #default="scope">
            <div v-for="({ name, pic }, index) in scope.row.comicChapters" :key="index" style="width: 100%;">
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
        <el-table-column prop="author" label="漫画作者" width="200"/>
        <el-table-column prop="serialize" label="连载状态" width="200"/>
        <el-table-column prop="uptime" label="更新日期" width="200">
          <template #default="scope">
            <el-date-picker v-model="scope.row.uptime" type="datetime" disabled size="small" style="width: 160px;"/>
          </template>
        </el-table-column>
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
        <div class="comic-editor-layout">
          <div class="comic-carousel">

            <el-carousel height="200px" direction="vertical" :autoplay="false">
              <el-carousel-item v-for="item in 4" :key="item">
                <h3 text="2xl" justify="center">{{ item }}</h3>
              </el-carousel-item>
            </el-carousel>

          </div>
          <div class="comic-editor">2</div>
          <div class="comic-ribbon">3</div>
        </div>
      </template>
      <!--<template v-slot:footer></template>-->
    </HorizonDialog>
  </div>
</template>
<script>
import {comicApi} from '@/api/index.js';
import {getCookie} from "@/utils";

export default {
  name: 'ComicsView',
  data() {
    return {
      currentAction: '',
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
    onPreEditorHandler() {
      this.$store.commit('messengerStore/setDialogCurrentView', this.$options.name);
      this.$store.commit('messengerStore/setDialogVisible', true);
      this.$store.commit('messengerStore/setDialogTitle', '漫画编辑');
      this.$store.commit('messengerStore/setDialogWidth', '80%');
      this.$store.commit('messengerStore/setDialogFooter', false);
      this.currentAction = 'editor';
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
      comicApi.fetchPageable({'xToken': getCookie('UUID')}, this.pageableQry).then(res => {
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

  :deep(.el-tag) {
    margin: 0 0 2px;
  }

  :deep(.el-tag), :deep(.el-tag__content) {
    width: 100%;
  }

  :deep(.el-tag__content) {
    display: flex;
    justify-content: left;
    align-items: center;;
  }

  .comic-editor-layout {
    display: flex;

    .comic-carousel {
      flex: 0 0 256px;
      background-color: #ececec;
    }

    .comic-editor {
      flex: 1 1 auto;
      background-color: #f0f8ff;
    }

    .comic-ribbon {
      flex: 0 0 256px;
      background-color: #99c236;
    }
  }
}
</style>