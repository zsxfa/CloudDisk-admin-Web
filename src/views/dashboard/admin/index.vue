<template>
  <div class="dashboard-editor-container">
    <panel-group @handleSetLineChartData="handleSetLineChartData" />
    <line-chart
      :chart-data="lineChartData"
      @handlePushLineChartData="handlePushLineChartData"
    />

    <!-- <el-row style="background: #fff; padding: 16px 16px 0; margin-bottom: 32px">
      <line-chart :chart-data="lineChartData" />
    </el-row> -->
  </div>
</template>

<script>
import PanelGroup from './components/PanelGroup'
import LineChart from './components/LineChart'
import fileApi from '@/api/core/file'

const lineChartData = {
  all: {},
  picture: {},
  doc: {},
  music: {},
  video: {},
  other: {},
}

export default {
  name: 'DashboardAdmin',
  components: {
    PanelGroup,
    LineChart,
  },

  data() {
    return {
      lineChartData: lineChartData.all,
    }
  },

  // 页面渲染成功后获取数据
  created() {
    // this.handleSetLineChartData()
  },

  methods: {
    handleSetLineChartData(type) {
      console.log('折线图切换被子组件调用')
      // 根据子集的调用切换对应的数据,
      if (type == 'all') {
        this.lineChartData.all = this.lineChartData.all
        console.log(this.lineChartData.all)
      } else if (type == 'picture') {
        this.lineChartData.picture = this.lineChartData.all[0]
        console.log(this.lineChartData.picture)
      } else if (type == 'music') {
        this.lineChartData.music = this.lineChartData.all[3]
        console.log(this.lineChartData.music)
      } else if (type == 'doc') {
        this.lineChartData.doc = this.lineChartData.all[1]
        console.log(this.lineChartData.doc)
      } else if (type == 'video') {
        this.lineChartData.video = this.lineChartData.all[2]
        console.log(this.lineChartData.video)
      } else if (type == 'other') {
        this.lineChartData.other = this.lineChartData.all[4]
        console.log(this.lineChartData.other)
      }
    },
    handlePushLineChartData(data) {
      console.log('有没有执行哇')
      console.log('折线图收到子组件传来的数据')
      console.log(data)
      console.log(data.displayPage.abc[0])
      this.lineChartData.all = data.displayPage.abc
      console.log(this.lineChartData.all)
      this.lineChartData.picture = data.displayPage.abc[0]
      this.lineChartData.doc = data.displayPage.abc[1]
      this.lineChartData.video = data.displayPage.abc[2]
      this.lineChartData.music = data.displayPage.abc[3]
      this.lineChartData.other = data.displayPage.abc[4]
    },
  },
}
</script>

<style lang="scss" scoped>
.dashboard-editor-container {
  padding: 32px;
  background-color: rgb(240, 242, 245);
  position: relative;

  .chart-wrapper {
    background: #fff;
    padding: 16px 16px 0;
    margin-bottom: 32px;
  }
}

@media (max-width: 1024px) {
  .chart-wrapper {
    padding: 8px;
  }
}
</style>
