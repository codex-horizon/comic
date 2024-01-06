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
      <el-table :data="tableData" style="width: 100%" height="440">
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
        <!-- 编辑章节区域 -->
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
        <!-- 图片功能区域 -->
        <div class="comic-editor-layout">
          <!-- 图片展示区域 -->
          <div class="comic-carousel">
            <el-carousel height="auto" :autoplay="false" indicator-position="none" @change="onCarouselChangeHandler">
              <el-carousel-item v-for="({url}, index) in comicPictures" :key="index">
                <div>
                  <el-image :src="`http://image.fm1100.com/${url}`"/>
                  <el-tag><span style="width: 100%; text-align: center;">共 <strong>{{ comicPictures.length }}</strong> 张，第 <strong>{{
                      carouselIndex + 1
                    }}</strong> 张</span></el-tag>
                </div>
              </el-carousel-item>
            </el-carousel>
          </div>

          <!-- 图片工作区域 -->
          <div class="comic-editor">
            <div class="comic-editor-container" ref="wrap">
              <canvas ref="drawCanvas" id="drawCanvas"/>
              <canvas ref="clipCanvas" id="clipCanvas"/>
            </div>
          </div>

          <!-- 图片功能区域 -->
          <div class="comic-ribbon">
            <el-collapse v-model="collapseModel" accordion>
              <el-collapse-item name="1">
                <template #title>
                  <el-tag>
                    <el-icon>
                      <Picture/>
                    </el-icon>
                    <span style="margin-left: 6px;">图片自定义缩放</span></el-tag>
                </template>
                <div>
                  <el-slider v-model="scroll.canvas.sliderModel" :format-tooltip="onSliderFormatHandler" show-input
                             size="small"/>
                </div>
              </el-collapse-item>
              <el-collapse-item name="2">
                <template #title>
                  <el-tag type="info">
                    <el-icon>
                      <DataLine/>
                    </el-icon>
                    <span style="margin-left: 6px;">图片识别翻译</span>
                  </el-tag>
                </template>
                <div>
                  <div>
                    <el-input v-model="beforePhrases" rows="2" type="textarea" placeholder="原文"/>
                  </div>
                  <div style="display: flex; justify-content: space-between; align-items: center; margin: 12px 0 6px;">
                    <el-switch
                        v-model="activeOCR"
                        inline-prompt
                        style="--el-switch-on-color: #13ce66; --el-switch-off-color: #ff4949"
                        active-text="OCR开启"
                        inactive-text="OCR关闭"
                    />
                    <el-select v-model="originalLanguage" placeholder="选择原文语言" size="small"
                               style="margin: 0 12px;">
                      <el-option v-for="({id, name}, index) in ocrOptionLanguages"
                                 :key="index" :label="name" :value="id"/>
                    </el-select>
                    <el-select v-model="translationLanguage" placeholder="选择译文语言" size="small">
                      <el-option v-for="({id, name}, index) in ocrOptionLanguages"
                                 :key="index" :label="name" :value="id"/>
                    </el-select>
                  </div>
                  <div>
                    <el-input v-model="afterPhrases" rows="2" type="textarea" placeholder="译文"/>
                  </div>
                  <div style="display: flex; justify-content: space-between; align-items: center; margin: 6px 0 12px;">
                    <el-tooltip
                        effect="dark"
                        content="译文字体颜色"
                        placement="top-start"
                    >
                      <el-color-picker v-model="characterColor" show-alpha :predefine="predefineColors"/>
                    </el-tooltip>
                    <el-tooltip
                        effect="dark"
                        content="译文背景颜色"
                        placement="top-start"
                    >
                      <el-color-picker v-model="backgroundColor" show-alpha :predefine="predefineColors"
                                       @focus="onBackgroundColorHandler"/>
                    </el-tooltip>
                    <el-tooltip
                        effect="dark"
                        content="译文字体选项"
                        placement="top"
                    >
                      <el-select v-model="translatedFontModel" placeholder="选择译文字体" size="small" filterable>
                        <el-option
                            v-for="({family, fullName}, index) in availableFonts" :key="index" :label="fullName"
                            :value="family"/>
                      </el-select>
                    </el-tooltip>
                    <el-tooltip
                        effect="dark"
                        content="译文字体大小"
                        placement="top"
                    >
                      <el-input-number v-model="translatedFontSizeModel" size="small" :min="9" :max="36"
                                       controls-position="right"/>
                    </el-tooltip>
                  </div>
                </div>
              </el-collapse-item>
              <el-collapse-item name="3">
                <template #title>
                  <el-tag type="success">
                    <el-icon>
                      <FolderOpened/>
                    </el-icon>
                    <span style="margin-left: 6px;">图片存储操作</span></el-tag>
                </template>
                <div>
                  <img src="" ref="img" id="img">
                </div>
              </el-collapse-item>
            </el-collapse>
          </div>
        </div>
      </template>
      <!--      <template v-slot:footer></template>-->
    </HorizonDialog>
  </div>
</template>
<script>
import {comicApi, ocrApi, translateApi} from '@/api/index.js';
import {getCookie} from "@/utils";
import Tesseract from 'tesseract.js';
import rgbaster from 'rgbaster';

export default {
  name: 'ComicsView',
  data() {
    return {
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
      carouselIndex: 0,

      scroll: {
        canvas: {
          sliderModel: 100,
          scale: 'scale(1)'
        }
      },

      collapseModel: '2',
      activeOCR: false,
      ocrOptionLanguages: [
        {'id': 0, name: '中文'},
        {'id': 1, name: '韩文'},
        {'id': 2, name: '英文'},
      ],
      beforePhrases: '',
      afterPhrases: '',

      originalLanguage: 1,
      translationLanguage: 0,

      predefineColors: [
        '#ff4500',
        '#ff8c00',
        '#ffd700',
        '#90ee90',
        '#00ced1',
        '#1e90ff',
        '#c71585',
        'rgba(255, 69, 0, 0.68)',
        'rgb(255, 120, 0)',
        'hsv(51, 100, 98)',
        'hsva(120, 40, 94, 0.5)',
        'hsl(181, 100%, 37%)',
        'hsla(209, 100%, 56%, 0.73)',
        '#c7158577',
      ],
      characterColor: 'rgba(0, 0, 0, 1)',
      backgroundColor: '',
      availableFonts: [],
      translatedFontModel: '',
      translatedFontSizeModel: 12
    }
  },
  methods: {
    async onBackgroundColorHandler() {
      if (!window.EyeDropper) {
        this.$message.warning("你的浏览器不支持 EyeDropper API");
      } else {
        const eyeDropper = new window.EyeDropper() // 初始化一个EyeDropper对象
        this.$message.info("按Esc可退出");
        try {
          const result = await eyeDropper.open(); // 开始拾取颜色
          this.backgroundColor = result.sRGBHex;
        } catch (e) {
          this.$message.success("用户取消了取色");
        }
      }
    },
    onSliderFormatHandler(number) {
      this.scroll.canvas.sliderModel = number;
      const val = number / 100;
      this.scroll.canvas.scale = `scale(${val},${val})`;
      return val;
    },
    async fetchImageLoad(image, callback) {
      let timer = setInterval(function () {
        if (image.complete) {
          callback(image)
          clearInterval(timer)
        }
      }, 1000)
    },
    onCanvasDrawHandler(comicPictureURL) {
      let _this = this;
      let image = new Image();
      image.crossOrigin = "Anonymous";
      // image.src = new URL(`http://image.fm1100.com/bomtoon/20240101/chapter/60/1704074081894.jpg`).href;
      image.src = new URL(`http://image.fm1100.com/${comicPictureURL}`).href;
      this.fetchImageLoad(image, function () {

        let width = image.naturalWidth;
        let height = image.naturalHeight;

        let drawCanvas = _this.$refs.drawCanvas;
        drawCanvas.width = width;
        drawCanvas.height = height;
        let clipContext = drawCanvas.getContext("2d");
        clipContext.scale(1, 1);
        clipContext.drawImage(image, 0, 0);

        let clipCanvas = _this.$refs.clipCanvas;
        clipCanvas.width = width;
        clipCanvas.height = height;
        let drawContext = clipCanvas.getContext("2d");
        drawContext.fillStyle = '#00000069';
        let clipStart = null;
        let clipArea = {};



        let onmousedown2 = function (event) {
          if (event.button === 0) { // 左键单击
            let mouseX = event.clientX - clipCanvas.offsetLeft; // 获取鼠标相对于Canvas的x坐标
            let mouseY = event.clientY - clipCanvas.offsetTop; // 获取鼠标相对于Canvas的y坐标
            // if (mouseX > drawTextAction.x && mouseX < drawTextAction.x + drawContext.measureText(drawTextAction.text).width && mouseY > drawTextAction.y - 30 && mouseY < drawTextAction.y) { // 如果鼠标在文本上
              drawTextAction.startDragging(mouseX, mouseY);
            // }
          }
        };
        let onmousemove2 = function (event) {
          if (drawTextAction.isDragging) {
            let mouseX = event.clientX - clipCanvas.offsetLeft;
            let mouseY = event.clientY - clipCanvas.offsetTop;
            drawTextAction.drag(mouseX, mouseY);
            drawContext.clearRect(0, 0, clipCanvas.width, clipCanvas.height); // 清除当前Canvas
            drawTextAction.draw(); // 重新绘制文本对象
          }
        }
        let onmouseup2 = function (event) {
          drawTextAction.stopDragging();
        }

        let drawTextAction = {
          x: 50,
          y: 50,
          text: "Drag me!",
          color: "black",
          isDragging: false,
          dragStartX: 0,
          dragStartY: 0,
          drawContext:null,
          // 绘制文本
          draw: function (drawContext) {
            if(drawContext){
              this.drawContext = drawContext;
            }
            this.drawContext.fillStyle = this.color;
            this.drawContext.font = "30px Arial";
            this.drawContext.fillText(this.text, this.x, this.y);
          },
          // 开始拖动
          startDragging: function (x, y) {
            this.isDragging = true;
            this.dragStartX = x;
            this.dragStartY = y;
          },
          // 停止拖动
          stopDragging: function () {
            this.isDragging = false;
          },
          // 拖动文本
          drag: function (x, y) {
            let deltaX = x - this.dragStartX;
            let deltaY = y - this.dragStartY;
            this.x += deltaX;
            this.y += deltaY;
            this.dragStartX = x;
            this.dragStartY = y;
          }
        };

        let onmousedown1 = function (e) {
          clipStart = {
            x: e.offsetX,
            y: e.offsetY
          };
        };
        let onmousemove1 = function (e) {
          if (clipStart) {
            let x = clipStart.x;
            let y = clipStart.y;
            let w = e.offsetX - clipStart.x;
            let h = e.offsetY - clipStart.y;
            drawContext.clearRect(0, 0, width, height);
            drawContext.beginPath();
            //遮罩层
            drawContext.globalCompositeOperation = "source-over";
            drawContext.fillRect(0, 0, width, height);
            //画框
            drawContext.globalCompositeOperation = 'destination-out';
            drawContext.fillRect(x, y, w, h);
            //描边
            drawContext.globalCompositeOperation = "source-over";
            drawContext.moveTo(x, y);
            drawContext.lineTo(x + w, y);
            drawContext.lineTo(x + w, y + h);
            drawContext.lineTo(x, y + h);
            drawContext.lineTo(x, y);
            drawContext.closePath();
            drawContext.stroke();
            clipArea = {
              x,
              y,
              w,
              h
            };
          }
        };
        let onmouseup1 = function (e) {
          if (clipStart) {
            if (Object.getOwnPropertyNames(clipArea).length === 0 && Object.getOwnPropertySymbols(clipArea).length === 0) {
              _this.$message.warning('未选中区域');
            } else {
              clipStart = null;
              let canvas = document.createElement("canvas");
              canvas.width = clipArea.w;
              canvas.height = clipArea.h;
              let data = clipContext.getImageData(clipArea.x, clipArea.y, clipArea.w, clipArea.h);
              let context = canvas.getContext("2d");
              context.scale(1, 1);
              context.putImageData(data, 0, 0);
              let img = document.getElementById("img");
              img.style.display = "none";
              let base64Image = canvas.toDataURL("image/png", 1.0);
              img.src = base64Image;
              if (_this.activeOCR) {
                // 开启服务OCR
                _this.fetchOcrText(base64Image);
              } else {
                // 开启本地OCR
                Tesseract.recognize(base64Image, 'kor', {
                  // logger: m => console.log(m)
                }).then(async result => {
                  _this.fetchTranslateText(result.data.text);
                  drawContext.clearRect(0, 0, width, height); // 清空画布
                  let rgbAster = await _this.fetchRgbAster(base64Image);
                  debugger;
                  clipContext.fillStyle = rgbAster[0]['color'];    //填充颜色
                  clipContext.fillRect(clipArea.x, clipArea.y, clipArea.w, clipArea.h); //透明无填充；x，y，width,height
                  clipContext.stroke();//相当于完成提交

                  eventReversal(clipCanvas, drawContext,drawTextAction, onmousedown2, onmousemove2,onmouseup2);
                }).catch(err => {
                  console.error(err);
                })
              }
            }
          }
        };

        clipCanvas.onmousedown = onmousedown1;
        clipCanvas.onmousemove = onmousemove1;
        clipCanvas.onmouseup = onmouseup1;

        function eventReversal(clipCanvas, drawContext, drawTextAction, onmousedown2, onmousemove2,onmouseup2 ){
          debugger;
          // 创建文本对象
          // // 绘制文本对象
          drawTextAction.draw(drawContext);
          clipCanvas.onmousedown = onmousedown2;
          clipCanvas.onmousemove = onmousemove2;
          clipCanvas.onmouseup = onmouseup2;
        }



      })
    },
    fetchOcrText(base64Image) {
      ocrApi.fetchOcrText({'image': base64Image}).then(res => {
        console.log(res);
        let result = JSON.parse(res.data.result)['trans_result'];
        this.beforePhrases = result[0].src;
        this.afterPhrases = result[0].dst;
      }).catch(error => {
        console.log(error);
      });
    },
    fetchTranslateText(text) {
      translateApi.fetchTranslateText({'text': text}).then(res => {
        console.log(res);
        let result = JSON.parse(res.data.result)['trans_result'];
        this.beforePhrases = result[0].src;
        this.afterPhrases = result[0].dst;
      }).catch(error => {
        console.log(error);
      });
    },
    async fetchRgbAster(base64Image) {
      return await rgbaster(base64Image, {scale: 1.0});
    },
    async onInitializeCanvasLayoutHandler() {
      if (!window.queryLocalFonts) {
        this.$message.warning("你的浏览器不支持 queryLocalFonts API");
      } else {
        const availableLocalFonts = await window.queryLocalFonts();
        this.availableFonts = availableLocalFonts.map(fontData => ({
          family: fontData.family,
          fullName: fontData.fullName,
        }));
      }
    },
    onCarouselChangeHandler(index) {
      this.onInitializeCanvasLayoutHandler();
      this.onCanvasDrawHandler(this.comicPictures[index].url);
      setTimeout(() => this.carouselIndex = index, 500);
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
      this.$store.commit('messengerStore/setDialogFullScreen', true);
      if (editor.comicChapters) {
        this.comicChaptersOptions = editor.comicChapters;
        // this.onComicChaptersChangeHandler(this.comicChaptersOptions[0]);
      }
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

    :deep(.el-select-dropdown__item.selected) {
      display: flex;
      justify-content: left;
      align-items: center;
    }
  }

  .comic-editor-layout {
    #img {
      transform: v-bind('scroll.canvas.scale');
    }

    display: flex;
    width: 100%;
    height: calc(100% - 32px);

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

      :deep(.el-carousel__arrow--left) {
        color: #ff0;
        background-color: #000000b8;
      }

      :deep(.el-carousel__arrow--right) {
        color: #ff0;
        background-color: #000000b8;
      }
    }

    .comic-editor {
      flex: 1 1 auto;

      .comic-editor-container {
        width: 100%;
        height: 100%;
        overflow: auto;
        position: relative;

        #drawCanvas, #clipCanvas, #img {
          transform: v-bind('scroll.canvas.scale');
        }

        #drawCanvas {
          position: absolute;
          z-index: 1;
        }

        #clipCanvas {
          position: absolute;
          z-index: 2;
        }
      }

    }

    .comic-ribbon {
      flex: 0 0 390px;
      background-color: transparent;
    }
  }

  :deep(.el-dialog__body) {
    height: calc(100% - (60px + 54px));
  }

  :deep(.el-carousel), :deep(.el-carousel__container) {
    width: 100%;
    height: 100% !important;
  }
}
</style>