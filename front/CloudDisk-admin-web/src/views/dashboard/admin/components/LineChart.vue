<template>
  <div :class="className" :style="{ height: height, width: width }" />
</template>

<script>
import * as echarts from 'echarts'
require('echarts/theme/macarons') // echarts theme
import resize from './mixins/resize'
import fileApi from '@/api/core/file'
import Cookies from 'js-cookie'
export default {
  mixins: [resize],
  props: {
    className: {
      type: String,
      default: 'chart',
    },
    width: {
      type: String,
      default: '100%',
    },
    height: {
      type: String,
      default: '350px',
    },
    autoResize: {
      type: Boolean,
      default: true,
    },
    chartData: {
      type: Object,
      required: true,
    },
  },
  data() {
    return {
      chart: null,
      day: [],
    }
  },
  watch: {
    chartData: {
      deep: true,
      handler(val) {
        console.log('Panel组件点击卡片时触发Line组件调用x轴初始化')
        this.setOptions(val)
      },
    },
  },
  created() {
    console.log('created')
  },
  activated() {
    console.log('activated')
    this.getLineChar()
  },
  mounted() {
    // 等待一切加载完成后调用初始化方法
    this.$nextTick(() => {
      console.log('mounted')
      // this.initChart()
      this.getLineChar()
      console.log('mounted执行结束')
    })
  },
  beforeDestroy() {
    if (!this.chart) {
      return
    }
    this.chart.dispose()
    this.chart = null
  },
  methods: {
    handlePushLineChartData(data) {
      console.log('handlePushLineChartData的结果是：' + data.displayPage.abc[0])
      this.$emit('handlePushLineChartData', data)
      // this.$parent.handlePushLineChartData(data)
    },
    // 获取数据
    getLineChar() {
      console.log('进入getLineChar方法')
      let username = Cookies.get('username')
      fileApi.showData(username).then((response) => {
        this.day = response.data.displayPage.date
        // this.all = response.data.displayPage.abc
        console.log(response)
        this.handlePushLineChartData(response.data)
        this.initChart()
        // this.$emit('getLineChar', response.data)
      })
    },

    initChart() {
      console.log('初始化方法')
      this.chart = echarts.init(this.$el, 'macarons')
      console.log(this.chartData)
      this.setOptions(this.chartData)
    },
    setOptions({ picture, music, doc, video, other } = {}) {
      console.log('x轴分区被初始化')
      var picture = picture.split(',')
      var music = music.split(',')
      var doc = doc.split(',')
      var video = video.split(',')
      var other = other.split(',')
      console.log(music)
      this.chart.setOption({
        xAxis: {
          // data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'],
          data: this.day,
          boundaryGap: false,
          axisTick: {
            show: true,
          },
          axisLabel: {
            interval: 0, //X轴信息全部展示
            // rotate: -10, //60 标签倾斜的角度
          },
        },
        grid: {
          left: '3%',
          right: '3%',
          bottom: 20,
          top: 30,
          containLabel: true,
        },
        tooltip: {
          trigger: 'axis',
          backgroundColor: 'rgba(255,255,255,0.8)', //通过设置rgba调节背景颜色与透明度
          axisPointer: {
            type: 'cross',
          },
          padding: [5, 10],
        },
        yAxis: {
          axisTick: {
            show: false,
          },
        },
        legend: {
          data: ['picture', 'music', 'doc', 'video', 'other'],
        },
        series: [
          {
            name: 'picture',
            itemStyle: {
              normal: {
                color: '#FF005A',
                lineStyle: {
                  color: '#FF005A',
                  width: 2,
                },
                areaStyle: {
                  color: '#FF005A',
                },
              },
            },
            smooth: true,
            type: 'line',
            data: picture,
            animationDuration: 2800,
            animationEasing: 'cubicInOut',
          },
          {
            name: 'music',
            smooth: true,
            type: 'line',
            itemStyle: {
              normal: {
                color: '#3888fa',
                lineStyle: {
                  color: '#3888fa',
                  width: 2,
                },
                areaStyle: {
                  color: '#3888fa',
                },
              },
            },
            data: music,
            animationDuration: 2800,
            animationEasing: 'quadraticOut',
          },
          {
            name: 'doc',
            smooth: true,
            type: 'line',
            itemStyle: {
              normal: {
                color: '#00FFFF',
                lineStyle: {
                  color: '#00FFFF',
                  width: 2,
                },
                areaStyle: {
                  color: '#00FFFF',
                },
              },
            },
            data: doc,
            animationDuration: 2800,
            animationEasing: 'cubicInOut',
          },
          {
            name: 'video',
            smooth: true,
            type: 'line',
            itemStyle: {
              normal: {
                color: '#7CFC00',
                lineStyle: {
                  color: '#7CFC00',
                  width: 2,
                },
                areaStyle: {
                  color: '#7CFC00',
                },
              },
            },
            data: video,
            animationDuration: 2800,
            animationEasing: 'quadraticOut',
          },
          {
            name: 'other',
            smooth: true,
            type: 'line',
            itemStyle: {
              normal: {
                color: '#FF8C00',
                lineStyle: {
                  color: '#FF8C00',
                  width: 2,
                },
                areaStyle: {
                  color: '#FF8C00',
                },
              },
            },
            data: other,
            animationDuration: 2800,
            animationEasing: 'quadraticOut',
          },
        ],
      })
    },
  },
}
</script>
