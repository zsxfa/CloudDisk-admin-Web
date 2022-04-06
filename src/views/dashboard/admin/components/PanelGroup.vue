<template>
  <el-row :gutter="40" class="panel-group">
    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
      <div class="card-panel" @click="handleSetLineChartData('all')">
        <div class="card-panel-icon-wrapper icon-all">
          <svg-icon icon-class="all" class-name="card-panel-icon" />
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">全部</div>
          <!--数字动态增加效果由于组件存在数字过大数字加载不完的BUG，这边使用-10000，由于数字负数会导致错误，所以使用三元运算计算合适字符-->
          <count-to
            :start-val="panelGroup.all < 10000 ? 0 : panelGroup.all - 10000"
            :end-val="panelGroup.all"
            :duration="3000"
            class="card-panel-num"
          />
        </div>
      </div>
    </el-col>
    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
      <div class="card-panel" @click="handleSetLineChartData('picture')">
        <div class="card-panel-icon-wrapper icon-picture">
          <svg-icon icon-class="picture" class-name="card-panel-icon" />
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">图片</div>
          <count-to
            :start-val="
              panelGroup.picture < 10000 ? 0 : panelGroup.picture - 10000
            "
            :end-val="panelGroup.picture"
            :duration="3000"
            class="card-panel-num"
          />
        </div>
      </div>
    </el-col>
    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
      <div class="card-panel" @click="handleSetLineChartData('doc')">
        <div class="card-panel-icon-wrapper icon-doc">
          <svg-icon icon-class="doc" class-name="card-panel-icon" />
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">文档</div>
          <count-to
            :start-val="panelGroup.doc < 10000 ? 0 : panelGroup.doc - 10000"
            :end-val="panelGroup.doc"
            :duration="3000"
            class="card-panel-num"
          />
        </div>
      </div>
    </el-col>
    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
      <div class="card-panel" @click="handleSetLineChartData('music')">
        <div class="card-panel-icon-wrapper icon-music">
          <svg-icon icon-class="music" class-name="card-panel-icon" />
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">音乐</div>
          <count-to
            :start-val="panelGroup.music < 10000 ? 0 : panelGroup.music - 10000"
            :end-val="panelGroup.music"
            :duration="3000"
            class="card-panel-num"
          />
        </div>
      </div>
    </el-col>
    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
      <div class="card-panel" @click="handleSetLineChartData('video')">
        <div class="card-panel-icon-wrapper icon-video">
          <svg-icon icon-class="video" class-name="card-panel-icon" />
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">视频</div>
          <count-to
            :start-val="panelGroup.video < 10000 ? 0 : panelGroup.video - 10000"
            :end-val="panelGroup.video"
            :duration="3000"
            class="card-panel-num"
          />
        </div>
      </div>
    </el-col>
    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
      <div class="card-panel" @click="handleSetLineChartData('other')">
        <div class="card-panel-icon-wrapper icon-other">
          <svg-icon icon-class="other" class-name="card-panel-icon" />
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">其他</div>
          <count-to
            :start-val="panelGroup.other < 10000 ? 0 : panelGroup.other - 10000"
            :end-val="panelGroup.other"
            :duration="3000"
            class="card-panel-num"
          />
        </div>
      </div>
    </el-col>
  </el-row>
</template>

<script>
import CountTo from 'vue-count-to'
import fileApi from '@/api/core/file'
import Cookies from 'js-cookie'
export default {
  components: {
    CountTo,
  },
  data: function () {
    return {
      panelGroup: {
        all: 0,
        picture: 0,
        doc: 0,
        music: 0,
        video: 0,
        other: 0,
      },
    }
  },
  created() {
    // 页面初始化钩子
    this.getPanelGroup()
  },
  activated() {
    // 组件刷新钩子,由于组件被调用时不会触发初始化钩子，这里添加一个刷新的函数
    this.getPanelGroup()
  },

  methods: {
    handleSetLineChartData(type) {
      this.$emit('handleSetLineChartData', type)
    },
    // 获取4个基本数据
    getPanelGroup() {
      let username = Cookies.get('username')
      fileApi.showData(username).then((response) => {
        // this.alertMsg(response)

        this.panelGroup.all = response.data.displayPage.allCount
        this.panelGroup.picture = response.data.displayPage.pictureCount
        this.panelGroup.doc = response.data.displayPage.docCount
        this.panelGroup.music = response.data.displayPage.musicCount
        this.panelGroup.video = response.data.displayPage.videoCount
        this.panelGroup.other = response.data.displayPage.otherCount
      })
    },
  },
}
</script>

<style lang="scss" scoped>
.panel-group {
  margin-top: 18px;

  .card-panel-col {
    margin-bottom: 32px;
  }

  .card-panel {
    height: 108px;
    cursor: pointer;
    font-size: 12px;
    position: relative;
    overflow: hidden;
    color: #666;
    background: #fff;
    // box-shadow: 4px 4px 40px rgba(0, 0, 0, 0.05);
    border-color: rgba(0, 0, 0, 0.05);

    &:hover {
      .card-panel-icon-wrapper {
        color: #fff;
      }

      // .icon-all {
      //   background: #36a3f7;
      // }

      // .icon-picture {
      //   background: #36a3f7;
      // }

      // .icon-doc {
      //   background: #36a3f7;
      // }

      // .icon-music {
      //   background: #36a3f7;
      // }

      // .icon-video {
      //   background: #36a3f7;
      // }

      // .icon-other {
      //   background: #36a3f7;
      // }
    }

    .card-panel-icon-wrapper {
      float: left;
      margin: 14px 0 0 14px;
      padding: 16px;
      transition: all 0.38s ease-out;
      border-radius: 6px;
    }

    .card-panel-icon {
      float: left;
      font-size: 48px;
    }

    .card-panel-description {
      float: right;
      font-weight: bold;
      margin: 26px;
      margin-left: 0px;

      .card-panel-text {
        line-height: 18px;
        color: rgba(0, 0, 0, 0.45);
        font-size: 16px;
        margin-bottom: 12px;
      }

      .card-panel-num {
        font-size: 20px;
      }
    }
  }
}

@media (max-width: 550px) {
  .card-panel-description {
    display: none;
  }

  .card-panel-icon-wrapper {
    float: none !important;
    width: 100%;
    height: 100%;
    margin: 0 !important;

    .svg-icon {
      display: block;
      margin: 14px auto !important;
      float: none !important;
    }
  }
}
</style>
