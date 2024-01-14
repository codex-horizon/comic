<template>
  <div>
    <HorizonDialog v-if="this.$options.name === this.$store.getters['messengerStore/getDialogCurrentView']">
      <!-- <template v-slot:header></template> -->
      <template v-slot:body>
        <div class="comic-operation-layout">
          <div class="operation-picture">
            <div class="choose-picture">
              <el-select v-model="comicChaptersModel"
                         placeholder="请选择编辑的章节"
                         @change="onComicChapters"
                         :teleported="false">
                <el-option v-for="(comicChaptersOption, index) in comicChaptersOptions"
                           :key="index" :label="comicChaptersOption.name"
                           :value="comicChaptersOption">
                  <el-tag>
                    <div class="dropdown-item">
                      <el-avatar v-if="comicChaptersOption['pic']"
                                 :src="`http://image.fm1100.com/${comicChaptersOption['pic']}`"
                                 size="small"/>
                      <el-avatar v-else
                                 size="small"
                      >{{ comicChaptersOption.name.charAt(0) }}
                      </el-avatar>
                      <span style="margin-left: 8px;">{{ comicChaptersOption.name }}</span>
                    </div>
                  </el-tag>
                </el-option>
              </el-select>
            </div>
            <div class="scroll-picture">
              <el-carousel height="auto" :autoplay="false" indicator-position="none" @change="onComicChaptersPictures">
                <el-carousel-item v-for="({url, lastModifiedUrl}, index) in comicChaptersPictures" :key="index">
                  <div>
                    <el-image v-if="lastModifiedUrl" :src="`http://image.fm1100.com/${lastModifiedUrl}`"
                              :preview-src-list="[`http://image.fm1100.com/${lastModifiedUrl}`, `http://image.fm1100.com/${url}`]"
                              :zoom-rate="1.2"
                              :max-scale="7"
                              :min-scale="0.2"
                              fit="cover"
                              :preview-teleported="true"
                    />
                    <el-image v-else :src="`http://image.fm1100.com/${url}`"
                              :preview-src-list="[`http://image.fm1100.com/${url}`]"
                              :zoom-rate="1.2"
                              :max-scale="7"
                              :min-scale="0.2"
                              fit="cover"
                              :preview-teleported="true"
                    />
                    <el-tag><span style="width: 100%; text-align: center;">共 <strong>{{
                        comicChaptersPictures.length
                      }}</strong> 张，第 <strong>{{
                        index + 1
                      }}</strong> 张</span></el-tag>
                  </div>
                </el-carousel-item>
              </el-carousel>
            </div>
          </div>
          <div class="operation-editor">
            <div class="editor-layout">
              <canvas ref="drawCanvas" id="drawCanvas"/>
              <canvas ref="clipCanvas" id="clipCanvas" @contextmenu.prevent/>
            </div>
          </div>
          <div class="operation-function" v-loading="loading">
            <el-collapse v-model="collapseActiveNameModel" accordion class="collapse-layout">
              <el-collapse-item name="1">
                <template #title>
                  <el-tag>
                    <el-icon>
                      <ZoomIn/>
                    </el-icon>
                    <span class="collapse-text">图片定义缩放</span>
                  </el-tag>
                </template>
                <div>
                  <div class="flex-space">
                    <el-slider v-model="transformScaleModel"
                               :format-tooltip="onSliderFormat"
                               show-input size="small"/>
                  </div>
                  <div class="flex-space">
                    <div>
                      <el-switch v-model="hasScreenshot" inline-prompt
                                 active-text="截图开启"
                                 inactive-text="截图关闭"
                                 disabled
                      />
                    </div>
                    <div>
                      <el-switch
                          v-model="hasDrag" inline-prompt
                          active-text="移动开启"
                          inactive-text="移动关闭"
                          disabled
                      />
                    </div>
                  </div>
                  <div class="flex-space">
                    <el-input v-model="identifyTextBefore" rows="2" type="textarea" placeholder="原文" @input="async (value) => {
                      this.text = value;
                      await this.onFillTextPreview();
                    }"/>
                  </div>
                  <div class="flex-space">
                    <div>
                      <el-select disabled v-model="originalLanguage" placeholder="选择原文语言" size="small"
                                 style="margin: 0 12px 0 0;">
                        <el-option v-for="({id, name}, index) in ocrOptionLanguages"
                                   :key="index" :label="name" :value="id"/>
                      </el-select>
                    </div>
                    <div>
                      <el-select disabled v-model="translationLanguage" placeholder="选择译文语言" size="small">
                        <el-option v-for="({id, name}, index) in translationLanguages"
                                   :key="index" :label="name" :value="id"/>
                      </el-select>
                    </div>
                  </div>
                  <div class="flex-space">
                    <div>
                      <el-switch v-model="hasIdentify" inline-prompt
                                 active-text="在线开启" inactive-text="离线开启"/>
                    </div>
                    <div>
                      <el-switch
                          v-model="hasTranslate" inline-prompt
                          active-text="翻译开启"
                          inactive-text="翻译关闭"
                          @change="async (bool) => {
                          if(bool) {
                            if(!this.identifyTextBefore) {
                              this.hasTranslate = !bool;
                              this.$message.warning('待翻译内容空！');
                            } else {
                              await this.fetchTranslateText(this.identifyTextBefore);
                            }
                          }
                      }"
                      />
                    </div>
                  </div>
                  <div class="flex-space">
                    <el-input v-model="identifyTextAfter" rows="2" type="textarea" placeholder="译文" @input="async (value) => {
                      this.text = value;
                      await this.onFillTextPreview();
                    }"/>
                  </div>
                  <div class="flex-space">
                    <div>字体选项：</div>
                    <div>
                      <el-select v-model="fontFamilyModel" placeholder="选择字体" size="small" filterable
                                 @focus="onLocalFonts"
                                 @change="(val)=>{
                        this.onFillTextPreview();
                      }">
                        <el-option v-for="({family, fullName}, index) in fontFamilyList" :key="index" :label="fullName"
                                   :value="family"/>
                      </el-select>
                    </div>
                  </div>
                  <div class="flex-space">
                    <div>字体大小：</div>
                    <div>
                      <el-input-number v-model="fontSizeModel" size="small" :min="9" :max="99"
                                       controls-position="right" @change="(val)=>{
                                         this.fontSizeModel = val;
                        this.onFillTextPreview();
                      }"/>
                    </div>
                  </div>
                  <div class="flex-space">
                    <div>字体颜色：</div>
                    <div>
                      <el-color-picker v-model="fontColorModel" size="small" show-alpha
                                       :predefine="predefineColors"
                                       @focus="onFontColor" @change="(val)=>{
                        this.onFillTextPreview();
                      }"/>
                    </div>
                  </div>
                  <div class="flex-space">
                    <div>字体粗细：</div>
                    <div>
                      <el-select v-model="fontWeightModel" placeholder="选择字体粗细" size="small" filterable
                                 @change="(val)=>{
                        this.onFillTextPreview();
                      }">
                        <el-option v-for="(fontWeight, index) in fontWeights" :key="index" :label="fontWeight"
                                   :value="fontWeight"/>
                      </el-select>
                    </div>
                  </div>
                  <div class="flex-space">
                    <el-button-group style="width: 100%;">
                      <el-button type="primary" icon="RefreshLeft" size="small" @click="onRollBackRecord(true)"
                                 style="width: calc(100% / 3);">
                        撤&nbsp;回
                      </el-button>
                      <el-button type="primary" icon="Refresh" size="small" @click="onRecover"
                                 style="width: calc(100% / 3);">
                        恢&nbsp;复
                      </el-button>
                      <el-button type="primary" icon="FolderChecked" size="small" @click="onUpload"
                                 style="width: calc(100% / 3);">
                        保&nbsp;存
                      </el-button>
                    </el-button-group>
                  </div>
                </div>
              </el-collapse-item>
            </el-collapse>
          </div>
        </div>
      </template>
      <!-- <template v-slot:footer></template> -->
    </HorizonDialog>
  </div>
</template>
<script>
import Tesseract from "tesseract.js";
import {ocrApi, translateApi, ossApi} from "@/api";
import rgbaster from "rgbaster";

export default {
  name: 'ComicOperationView',
  data() {
    return {
      // ------------------- 左侧数据模型 -------------------
      currentData: null,

      // 选择相应章节
      comicChaptersModel: '',
      comicChaptersOptions: [],


      // 章节轮播图片
      comicChaptersPicturesId: null,
      comicChaptersPictures: [],

      // ------------------- 中间数据模型 -------------------
      imageInstance: null,
      imageWidth: 0,
      imageHeight: 0,

      drawCanvasInstance: null,
      clipContextInstance: null,

      clipCanvasInstance: null,
      drawContextInstance: null,

      screenshotStartPoint: {},
      screenshotRangeArea: {},

      base64ImageData: null,

      text: '',

      isDrag: false,
      x: 0,
      y: 0,
      dragStartX: 0,
      dragStartY: 0,

      // ------------------- 右侧数据模型 -------------------
      loading: false,
      collapseActiveNameModel: '1',
      transformScaleModel: 100,
      transformScale: 'scale(1, 1)',

      hasScreenshot: true,
      hasDrag: true,

      identifyTextBefore: '',
      hasIdentify: false,
      hasTranslate: false,
      identifyTextAfter: '',

      ocrOptionLanguages: [
        {'id': 0, name: '中文'},
        {'id': 1, name: '韩文'},
        {'id': 2, name: '英文'},
      ],
      originalLanguage: 1,
      translationLanguages: [
        {'id': 0, name: '中文'},
        {'id': 1, name: '韩文'},
        {'id': 2, name: '英文'},
      ],
      translationLanguage: 0,

      fontFamilyList: [],
      fontFamilyModel: 'Microsoft YaHei',

      fontSizeModel: 12,

      fontColorModel: 'rgba(0, 0, 0, 1)',
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

      fontWeightModel: '400',
      fontWeights: ['100', '200', '300', '400', '500', '600', '700', '800', '900'],

      comicChaptersPictureURI: '',

      rollBackRecord: 0
    }
  },
  watch: {
    text: async function (val, old) {
      if (val !== '' ||  val !== old) {
        await this.onRollBackRecord(false);
      }
    }
  },
  methods: {
    // ------------------- 左侧数据模型 -------------------
    onComicChapters(comicChapter) {
      this.comicChaptersModel = comicChapter.name;
      this.comicChaptersPictures = comicChapter.comicPictures;

      this.comicChaptersPicturesId = this.comicChaptersPictures[0]['id'];
      if (this.comicChaptersPictures[0]['lastModifiedUrl']) {
        this.onProtractCanvas(this.comicChaptersPictures[0]['lastModifiedUrl']);
      } else {
        this.onProtractCanvas(this.comicChaptersPictures[0]['url']);
      }
    },
    onComicChaptersPictures(index) {
      let comicChaptersPicture = this.comicChaptersPictures[index];
      this.comicChaptersPicturesId = comicChaptersPicture['id'];
      if (comicChaptersPicture['lastModifiedUrl']) {
        this.onProtractCanvas(comicChaptersPicture['lastModifiedUrl']);
      } else {
        this.onProtractCanvas(comicChaptersPicture['url']);
      }
    },
    // ------------------- 中间数据模型 -------------------
    initImage(comicChaptersPictureURI) {
      return new Promise((resolve, reject) => {
        this.imageInstance = new Image();
        this.imageInstance.onload = () => {
          let _this = this;
          setTimeout(function () {
            _this.loading = false;
          }, 500);
          resolve(this.imageInstance);
        }
        this.imageInstance.onerror = () => {
          debugger;
          reject(`'${this.imageInstance}' is failed to load.`);
        }
        this.imageInstance.crossOrigin = "Anonymous";
        this.imageInstance.src = new URL(`http://image.fm1100.com/${comicChaptersPictureURI}`).href;
      })
    },
    async onProtractCanvas(comicChaptersPictureURI) {
      this.loading = true;
      this.comicChaptersPictureURI = comicChaptersPictureURI
      let image = await this.initImage(comicChaptersPictureURI);
      console.log(image);
      this.imageWidth = this.imageInstance.naturalWidth;
      this.imageHeight = this.imageInstance.naturalHeight;

      this.drawCanvasInstance = this.$refs['drawCanvas'];
      this.drawCanvasInstance.width = this.imageWidth;
      this.drawCanvasInstance.height = this.imageHeight;
      this.drawContextInstance = this.drawCanvasInstance.getContext("2d", {willReadFrequently: true});
      this.drawContextInstance.scale(1, 1);
      this.drawContextInstance.drawImage(this.imageInstance, 0, 0);

      this.clipCanvasInstance = this.$refs['clipCanvas'];
      this.clipCanvasInstance.width = this.imageWidth;
      this.clipCanvasInstance.height = this.imageHeight;
      this.clipContextInstance = this.clipCanvasInstance.getContext("2d", {willReadFrequently: true});
      this.clipContextInstance.scale(1, 1);
      this.clipContextInstance.fillStyle = '#00000069';

      this.clipCanvasInstance.onmousedown = this.onMouseDown;
      this.clipCanvasInstance.onmousemove = this.onMuseMove;
      this.clipCanvasInstance.onmouseup = this.onMouseUp;
    },
    onMouseDown(e) {
      if (e.button === 0) { // 这个是左键
        if (this.hasScreenshot) {
          this.screenshotStartPoint = {
            x: e.offsetX,
            y: e.offsetY
          };
        }
      }
      if (e.button === 2) { // 这个是右键
        if (this.hasDrag) {
          this.isDrag = true;
          this.dragStartX = e.offsetX - this.clipCanvasInstance.offsetLeft;
          this.dragStartY = e.offsetY - this.clipCanvasInstance.offsetTop;
        }
      }
    },
    onMuseMove(e) {
      if (this.hasScreenshot) {
        if (Object.getOwnPropertyNames(this.screenshotStartPoint).length === 0 && Object.getOwnPropertySymbols(this.screenshotStartPoint).length === 0) {
          // this.$message.warning('起始区域空');
        } else {
          let x = this.screenshotStartPoint.x;
          let y = this.screenshotStartPoint.y;
          let w = e.offsetX - this.screenshotStartPoint.x;
          let h = e.offsetY - this.screenshotStartPoint.y;
          this.clipContextInstance.clearRect(0, 0, this.imageWidth, this.imageHeight);
          this.clipContextInstance.beginPath();
          //遮罩层
          this.clipContextInstance.globalCompositeOperation = "source-over";
          this.clipContextInstance.fillRect(0, 0, this.imageWidth, this.imageHeight);
          //画框
          this.clipContextInstance.globalCompositeOperation = 'destination-out';
          this.clipContextInstance.fillRect(x, y, w, h);
          //描边
          this.clipContextInstance.globalCompositeOperation = "source-over";
          this.clipContextInstance.moveTo(x, y);
          this.clipContextInstance.lineTo(x + w, y);
          this.clipContextInstance.lineTo(x + w, y + h);
          this.clipContextInstance.lineTo(x, y + h);
          this.clipContextInstance.lineTo(x, y);
          this.clipContextInstance.closePath();
          this.clipContextInstance.stroke();
          this.screenshotRangeArea = {
            x,
            y,
            w,
            h
          };
        }
      }
      if (this.hasDrag && this.isDrag) {
        let mouseX = e.offsetX - this.clipCanvasInstance.offsetLeft; // 获取鼠标相对于Canvas的x坐标
        let mouseY = e.offsetY - this.clipCanvasInstance.offsetTop; // 获取鼠标相对于Canvas的y坐标

        let deltaX = mouseX - this.dragStartX;
        let deltaY = mouseY - this.dragStartY;
        this.x += deltaX;
        this.y += deltaY;
        this.dragStartX = mouseX;
        this.dragStartY = mouseY;

        this.onFillTextPreview();
      }
    },
    async onMouseUp(e) {
      if (e.button === 0) { // 这个是左键
        if (this.hasScreenshot) {
          if (Object.getOwnPropertyNames(this.screenshotStartPoint).length === 0 && Object.getOwnPropertySymbols(this.screenshotStartPoint).length === 0) {
            this.$message.warning('起始区域空');
          } else {
            if (Object.getOwnPropertyNames(this.screenshotRangeArea).length === 0 && Object.getOwnPropertySymbols(this.screenshotRangeArea).length === 0) {
              this.$message.warning('未选中区域');
            } else {
              let canvas = document.createElement("canvas");
              canvas.width = this.screenshotRangeArea.w;
              canvas.height = this.screenshotRangeArea.h;
              if (this.screenshotRangeArea.x && this.screenshotRangeArea.y && this.screenshotRangeArea.w && this.screenshotRangeArea.h) {
                let imageData = this.drawContextInstance.getImageData(this.screenshotRangeArea.x, this.screenshotRangeArea.y, this.screenshotRangeArea.w, this.screenshotRangeArea.h);
                let context = canvas.getContext("2d");
                context.scale(1, 1);
                context.putImageData(imageData, 0, 0);
                let base64Image = canvas.toDataURL("image/png", 1.0);
                await this.fetchOcrText(base64Image);

                let rgbAster = await this.fetchRgbAster(base64Image);
                this.drawContextInstance.fillStyle = rgbAster[0]['color']; // 填充颜色
                this.drawContextInstance.fillRect(this.screenshotRangeArea.x, this.screenshotRangeArea.y, this.screenshotRangeArea.w, this.screenshotRangeArea.h);
              } else {
                this.$message.warning('画布未选中！');
              }
            }
          }

          this.x = this.screenshotStartPoint.x;
          this.y = this.screenshotStartPoint.y;
          this.screenshotStartPoint = {};
        }
      }
      if (e.button === 2) { // 这个是右键
        if (this.hasDrag) {
          this.isDrag = false;
        }
      }
    },
    async onFillTextPreview() {
      this.clipContextInstance.clearRect(0, 0, this.imageWidth, this.imageHeight);

      this.clipContextInstance.fillStyle = this.fontColorModel;
      this.clipContextInstance.font = `normal ${this.fontWeightModel} ${this.fontSizeModel}px ${this.fontFamilyModel}`;
      this.clipContextInstance.textAlign = 'center';
      if (this.isDrag || Object.getOwnPropertyNames(this.screenshotStartPoint).length === 0 && Object.getOwnPropertySymbols(this.screenshotStartPoint).length === 0) {
        this.clipContextInstance.fillText(this.text, this.x, this.y);
      } else {
        this.clipContextInstance.fillText(this.text, this.screenshotRangeArea.x, this.screenshotRangeArea.y);
      }

      this.clipContextInstance.fillStyle = '#00000069';
    },
    async fetchOcrText(base64Image) {
      if (this.hasIdentify) {
        const res = await ocrApi.fetchOcrText({'image': base64Image});
        this.text = res.data;
      } else {
        const res = await Tesseract.recognize(base64Image, 'kor', {});
        this.text = res.data.text;
      }
      this.identifyTextBefore = this.text;
      await this.onFillTextPreview();
      await this.fetchTranslateText(this.identifyTextBefore);
    },
    async fetchTranslateText(text) {
      if (this.hasTranslate && text) {
        const res = await translateApi.fetchTranslateText({'text': text});
        const result = JSON.parse(res.data.result)['trans_result'];
        this.identifyTextBefore = result[0]['src'];
        this.identifyTextAfter = result[0]['dst'];
        this.text = this.identifyTextAfter;
        await this.onFillTextPreview();
      }
    },
    async fetchRgbAster(base64Image) {
      return await rgbaster(base64Image, {scale: 1.0});
    },

    // ------------------- 右侧数据模型 -------------------
    onSliderFormat(number) {
      this.transformScaleModel = number;
      const val = number / 100;
      this.transformScale = `scale(${val},${val})`;
      return val;
    },
    async onFontColor() {
      if (!window.EyeDropper) {
        this.$message.warning("你的浏览器不支持 EyeDropper API");
      } else {
        const eyeDropper = new window.EyeDropper() // 初始化一个EyeDropper对象
        this.$message.info("按Esc可退出");
        try {
          const res = await eyeDropper.open(); // 开始拾取颜色
          this.fontColorModel = res.sRGBHex;
          await this.onFillTextPreview();
        } catch (e) {
          this.$message.success("用户取消了取色");
        }
      }
    },
    async onLocalFonts() {
      if (!window.queryLocalFonts) {
        this.$message.warning("你的浏览器不支持 queryLocalFonts API");
      } else {
        const localFamilyFonts = await window.queryLocalFonts();
        let has = {};
        this.fontFamilyList = localFamilyFonts.reduce(function (arr, item) {
          !has[item.family] && (has[item.family] = arr.push(item));
          return arr;
        }, []);
      }
    },
    async onRollBackRecord(hasRollBackRecord) {
      if (hasRollBackRecord) {
        if (this.rollBackRecord === 0) {
          this.$message.error('不可撤回！');
        } else {
          let uri = this.comicChaptersPictureURI.replace(/rollBackRecord\/[0-9]\//, "");
          const finalURI = "rollBackRecord/" + --this.rollBackRecord + "/" + uri;
          await this.onProtractCanvas(finalURI);
          // await this.onFillTextPreview();
        }
      } else {
        this.rollBackRecord++;
        let canvas1 = document.createElement("canvas");
        canvas1.width = this.imageWidth;
        canvas1.height = this.imageHeight;
        let context1 = canvas1.getContext("2d");
        context1.scale(1, 1);
        let t1 = this.drawContextInstance.getImageData(0, 0, this.imageWidth, this.imageHeight);
        context1.putImageData(t1, 0, 0);
        let base641 = canvas1.toDataURL("image/png", 1.0);
        console.log("%c ", `background-image: url(${base641});
                 background-size: contain;
                 background-repeat: no-repeat;
                 padding: 200px;`
        );

        let canvas2 = document.createElement("canvas");
        canvas2.width = this.imageWidth;
        canvas2.height = this.imageHeight;
        let context2 = canvas2.getContext("2d");
        context2.scale(1, 1);
        let t2 = this.clipContextInstance.getImageData(0, 0, this.imageWidth, this.imageHeight);
        context2.putImageData(t2, 0, 0);
        let base642 = canvas2.toDataURL("image/png", 1.0);
        console.log("%c ", `background-image: url(${base642});
                 background-size: contain;
                 background-repeat: no-repeat;
                 padding: 200px;`
        );


        context1.drawImage(canvas2, 0, 0);
        let base64 = canvas1.toDataURL("image/png", 1.0);

        // this.drawContextInstance.drawImage(this.clipCanvasInstance, 0, 0);
        // let base64 = this.drawCanvasInstance.toDataURL("image/png", 1.0);
        // let base64 = canvas.toDataURL("image/png", 1.0);
        console.log("%c ", `background-image: url(${base64});
                 background-size: contain;
                 background-repeat: no-repeat;
                 padding: 200px;`
        );
        // this.identifyTextBefore = '';
        // this.text = '';
        // this.identifyTextAfter = '';
        await ossApi.rollBackRecord(
            {
              'id': this.comicChaptersPicturesId,
              'image': base64,
              'uri': this.comicChaptersPictureURI,
              'hasRollBackRecord': false
            }
        );
      }
    },
    async onRecover() {
      this.identifyTextBefore = '';
      this.text = '';
      this.identifyTextAfter = '';
      this.rollBackRecord = 0;

      let res = await ossApi.recover({
        'id': this.comicChaptersPicturesId
      });
      if (res.code === 'Biz_Ok_Response') {
        this.$message.success('恢复成功');
        let lastModifiedUrl = res.data;
        for (let comicChaptersPicture of this.comicChaptersPictures) {
          if (comicChaptersPicture['url'] === this.comicChaptersPictureURI || comicChaptersPicture['lastModifiedUrl'] === this.comicChaptersPictureURI) {
            comicChaptersPicture['lastModifiedUrl'] = lastModifiedUrl;
            break;
          }
        }

        await this.onProtractCanvas(lastModifiedUrl);
        await this.onFillTextPreview();

      }
    },
    async onUpload() {
      this.drawContextInstance.drawImage(this.clipCanvasInstance, 0, 0);
      let base64 = this.drawCanvasInstance.toDataURL("image/png", 1.0);
      console.log("%c ", `background-image: url(${base64});
                 background-size: contain;
                 background-repeat: no-repeat;
                 padding: 200px;`
      );
      this.identifyTextBefore = '';
      this.text = '';
      this.identifyTextAfter = '';
      this.rollBackRecord = 0;

      let res = await ossApi.upload(
          {
            'id': this.comicChaptersPicturesId,
            'image': base64,
            'uri': this.comicChaptersPictureURI
          }
      );
      if (res.code === 'Biz_Ok_Response') {
        this.$message.success('保存成功');
        let lastModifiedUrl = res.data;
        for (let comicChaptersPicture of this.comicChaptersPictures) {
          if (comicChaptersPicture['url'] === this.comicChaptersPictureURI || comicChaptersPicture['lastModifiedUrl'] === this.comicChaptersPictureURI) {
            comicChaptersPicture['lastModifiedUrl'] = lastModifiedUrl;
            break;
          }
        }

        await this.onProtractCanvas(lastModifiedUrl);
        await this.onFillTextPreview();
      }
    },

    async initialize() {
      this.currentData = this.$store.getters['messengerStore/getDialogCurrentData'];

      // 初始化 选择相应章节
      this.comicChaptersOptions = this.currentData['comicChapters'];
      this.comicChaptersModel = this.comicChaptersOptions[0].name;
      // 初始化 章节轮播图片
      this.comicChaptersPictures = this.comicChaptersOptions[0]['comicPictures'];
      this.comicChaptersPicturesId = this.comicChaptersPictures[0].id;
      // 初始化 画布图片区域
      if (this.comicChaptersPictures[0]['lastModifiedUrl']) {
        await this.onProtractCanvas(this.comicChaptersPictures[0]['lastModifiedUrl']);
      } else {
        await this.onProtractCanvas(this.comicChaptersPictures[0].url);
      }
    }
  },
  mounted() {
    this.initialize();
  }
}
</script>
<style scoped lang="scss">
:global(.el-dialog__body) {
  width: calc(100% - 40px);
  height: calc(100% - (54px + 60px));
}

.comic-operation-layout {
  width: 100%;
  height: 100%;
  display: flex;

  .operation-picture {
    flex: 0 0 256px;
    display: flex;
    flex-direction: column;

    .choose-picture {
      width: 100%;
      height: 46px;
      display: flex;
      justify-content: center;
      align-items: center;

      :deep(.el-select) {
        width: 100%;
        height: 100%;
      }

      :deep(.el-select-dropdown__item) {
        display: flex;
        justify-content: left;
        align-items: center;

        .dropdown-item {
          width: 100%;
          display: flex;
          justify-content: left;
          align-items: center;
        }
      }

      :deep(.el-tag) {
        width: 100%;
        display: flex;
        justify-content: left;
        align-items: center;
      }
    }

    .scroll-picture {
      width: 100%;
      height: calc(100% - 46px) !important;

      :deep(.el-carousel), :deep(.el-carousel__container) {
        width: 100%;
        height: 100% !important;
        border-radius: 4px;
      }

      :deep(.el-carousel__arrow) {
        background-color: #1f2d3dd4;
      }

      :deep(.el-carousel__item) {
        display: flex;
        justify-content: center;
        align-items: center;
      }

      :deep(.el-tag) {
        display: flex;
        justify-content: center;
        align-items: center;
      }
    }

  }

  .operation-editor {
    flex: 1 1 auto;
    padding: 0 12px;

    .editor-layout {
      width: 100%;
      height: 100%;
      border-radius: 4px;

      position: relative;
      overflow: auto;

      #drawCanvas, #clipCanvas {
        position: absolute;
        transform: v-bind(transformScale);
      }

      #drawCanvas {
        z-index: 1;
      }

      #clipCanvas {
        z-index: 2;
      }
    }

  }

  .operation-function {
    flex: 0 0 369px;
    display: flex;
    flex-direction: column;

    .collapse-layout {

      .flex-space {
        display: flex;
        justify-content: space-between;
        align-items: center;
        padding: 0 22px 6px 0;
      }

      :deep(.el-tag) {
        width: 100%;
        display: flex;
        justify-content: left;
        align-items: center;
      }

      :deep(.el-tag__content) {
        display: flex;
        justify-content: center;
        align-items: center;

        .collapse-text {
          margin-left: 4px;
        }
      }
    }
  }
}
</style>