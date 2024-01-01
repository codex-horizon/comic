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
        <div class="comic-chapters-layout">
          <el-select v-model="comicChaptersModel" placeholder="请选择编辑的章节" @change="onComicChaptersChangeHandler"
                     style="width: 100%;">
            <el-option v-for="(comicChapter, index) in comicChaptersOptions" :key="index" :label="comicChapter.name"
                       :value="comicChapter">
              <el-tag>
                <div style="width: 100%; display: flex; justify-content: left; align-items: center;">
                  <el-avatar v-if="comicChapter.pic" size="small" :src="`http://image.fm1100.com/${comicChapter.pic}`"/>
                  <el-avatar v-else size="small">{{ comicChapter.name.charAt(0) }}</el-avatar>
                  <div class="singe-line">
                    <span v-html="comicChapter.name" style="margin-left: 8px;"></span>
                  </div>
                </div>
              </el-tag>
            </el-option>
          </el-select>
        </div>
        <div class="comic-editor-layout">
          <div class="comic-carousel">
            <el-carousel height="380px" :autoplay="false" indicator-position="none" @change="onCarouselChangeHandler">
              <el-carousel-item v-for="({url}, index) in comicPictures" :key="index">
                <el-image :src="`http://image.fm1100.com/${url}`"/>
              </el-carousel-item>
            </el-carousel>
          </div>
          <div class="comic-editor" ref="comicEditor">
            <div class="comic-editor-container">
              <canvas ref="canvas" id="canvas"/>
            </div>
          </div>
          <div class="comic-ribbon">
            <div class="slider-demo-block">
              <span class="demonstration">图片缩放：</span>
              <el-slider v-model="scroll.canvas.sliderModel" :format-tooltip="onSliderFormatHandler" show-input
                         size="small"/>
            </div>
          </div>
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
      },

      comicChaptersOptions: [],
      comicChaptersModel: '',
      comicPictures: [],
      monitorUrl: '',

      scroll: {
        lock: false,
        width: '',
        height: '',
        canvas: {
          sliderModel: 40,
          scale: 'scale(.4)'
        }
      },

    }
  },
  methods: {
    onSliderFormatHandler(number) {
      this.scroll.canvas.sliderModel = number;
      debugger;
      const val = number / 100;
      this.scroll.canvas.scale = `scale(${val})`;
      return val;
    },
    onCanvasDrawHandler(comicPictureURL) {
      let canvas = this.$refs.canvas;
      // 加载图片
      let image = new Image();
      image.src = new URL(`http://image.fm1100.com/${comicPictureURL}`).href;
      image.onload = function () {
        // 1. 在创建Canvas元素时，指定与原始图片相同的宽度和高度。
        canvas.width = image.naturalWidth;
        canvas.height = image.naturalHeight;
        // 2. 在绘制图片之前，将Canvas的缩放比例设置为1，以保持与原始图片一致。
        let context = canvas.getContext('2d');
        context.scale(1, 1);

        // 3. 使用 drawImage 方法将原始图片绘制到Canvas上。
        context.drawImage(image, 0, 0);

        // context.fillStyle=context.createPattern(image,'no-repeat');

        // 添加文字
        // context.font = '24px Arial';
        // context.fillStyle = 'red';
        // context.fillText('Hello, World!', 50, 50);

        // let dataURL = canvas.toDataURL();
        // let newImage = document.createElement('img');
        // newImage.src = dataURL;
        // document.body.appendChild(newImage);
      }

    },
    onInitializeCanvasLayoutHandler() {
      if (!this.scroll.lock) {
        this.scroll.lock = true;
        let comicEditor = this.$refs.comicEditor;
        this.scroll.width = comicEditor.scrollWidth - (8 * 2) + 'px';
        this.scroll.height = comicEditor.scrollHeight - (8 * 2) + 'px';
      }
    },
    onCarouselChangeHandler(index) {
      this.onInitializeCanvasLayoutHandler();
      this.onCanvasDrawHandler(this.comicPictures[index].url);
    },
    onComicChaptersChangeHandler(comicChapter) {
      this.onInitializeCanvasLayoutHandler();
      this.comicPictures = comicChapter.comicPictures;
      this.onCanvasDrawHandler(this.comicPictures[0].url);
    },
    onPreEditorHandler(editor) {
      this.$store.commit('messengerStore/setDialogCurrentView', this.$options.name);
      this.$store.commit('messengerStore/setDialogVisible', true);
      this.$store.commit('messengerStore/setDialogTitle', `漫画编辑：${editor.name}`);
      this.$store.commit('messengerStore/setDialogWidth', '96%');
      this.$store.commit('messengerStore/setDialogFooter', false);
      this.currentAction = 'editor';

      console.log(editor.comicChapters);
      this.comicChaptersOptions = editor.comicChapters;
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

  .comic-chapters-layout {
    margin-bottom: 12px;
  }

  .comic-editor-layout {
    display: flex;

    .comic-carousel, .comic-editor, .comic-ribbon {
      padding: 8px;
    }

    .comic-carousel {
      flex: 0 0 240px;
      background-color: transparent;

      :deep(.el-carousel__item) {
        display: flex;
        justify-content: center;
        align-items: center;
      }
    }

    .comic-editor {
      flex: 1 1 auto;
      background-color: #f6f6f6;

      .comic-editor-container {
        width: v-bind('scroll.width');
        height: v-bind('scroll.height');
        overflow: auto;

        #canvas {
          transform: v-bind('scroll.canvas.scale');
          //image-rendering: pixelated;
          //display: block;
        }
      }

    }

    .comic-ribbon {
      flex: 0 0 390px;
      background-color: transparent;
    }
  }

}
</style>