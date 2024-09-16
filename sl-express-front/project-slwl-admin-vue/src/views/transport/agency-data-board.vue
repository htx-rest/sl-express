<template>
  <div id="screen-container" class="screen-box">
    <div class="container-box">
      <div class="l-box">
        <div class="l-t-box">
          <div class="title-box">
            <span class="icon"></span>
            <span class="title">司机</span>
          </div>
          <div class="content-box">
            <div v-for="item in driverData" :key="item.id" class="content-item">
              <div class="num">{{ item.num }}</div>
              <div class="name">{{ item.name }}</div>
            </div>
          </div>
        </div>
        <div class="l-m-box">
          <div class="title-box">
            <span class="icon"></span>
            <span class="title">车辆装载</span>
          </div>
          <div id="vehicle-loading-chart" class="chart-box">
            <!-- <div id="vehicle-loading-chart" class="chart-con"></div> -->
          </div>
        </div>
        <div class="l-b-box">
          <div class="title-box">
            <span class="icon"></span>
            <span class="title">目的地运输量</span>
          </div>
          <div id="destination-traffic-chart" class="chart-box">
            <!-- <div id="destination-traffic-chart" class="chart-con"></div> -->
          </div>
        </div>
      </div>
      <div class="c-box">
        <div class="c-t-box">
          <div class="title-box">
            <span class="icon"></span>
            <span class="title">运单统计</span>
          </div>
          <div class="total-box">
            <div class="item-one-list">
              <img class="icon" src="../../assets/1.png" alt />
              <div class="item">
                <span class="num">2035</span>
                <span class="name">运单总数</span>
              </div>
            </div>
            <div class="item-two-list">
              <div v-for="item in totalData" :key="item.id" class="item-list">
                <img class="icon" :src="item.url" alt />
                <div class="item">
                  <span class="num">{{ item.num }}</span>
                  <span class="name">{{ item.name }}</span>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="c-b-box">
          <div class="title-box">
            <span class="icon"></span>
            <span class="title">运单线路图</span>
          </div>
          <div id="main-chart" class="chart-box">
            <!-- <div id="main-chart" class="chart-con"></div> -->
          </div>
        </div>
      </div>
      <div class="r-box">
        <div class="r-t-box">
          <div class="title-box">
            <span class="icon"></span>
            <span class="title">运输准时率</span>
          </div>
          <div class="content-box">
            <div class="con-l-box con-box">
              <img class="icon" src="../../assets/fachu.png" alt="" />
              <span class="num">75%</span>
              <span class="name">发出准时率</span>
            </div>
            <div class="con-c-box"></div>
            <img class="line" src="../../assets/line.png" alt="" />
            <div class="con-r-box con-box">
              <img class="icon" src="../../assets/dadao.png" alt="" />
              <span class="num">75%</span>
              <span class="name">到达准时率</span>
            </div>
          </div>
        </div>
        <div class="r-m-box">
          <div class="con-t-box">
            <div class="title-box">
              <span class="icon"></span>
              <span class="title">异常运输</span>
            </div>
            <div class="nor-box">
              <div class="nor-l">
                <span class="num">2378,400</span>
                <span class="name">异常运输任务总数</span>
              </div>
              <div id="chart04" class="chart-box">
                <!-- <el-progress type="circle" :percentage="68" :stroke-width="10"></el-progress> -->
              </div>
            </div>
            <img class="img1" src="../../assets/yunshu.png" alt="" />
            <img class="img2" src="../../assets/yunshu.png" alt="" />
          </div>
          <div class="con-b-box">
            <div class="cus-table-box">
              <div class="table-header">
                <span class="header one">运输任务编号</span>
                <span class="header two">目的地</span>
                <span class="header three">延长时间</span>
              </div>
              <div class="table-body">
                <div v-for="item in dataList" :key="item.id" class="body-item">
                  <span class="item one ellipsis">{{ item.number }}</span>
                  <span class="item two ellipsis">{{ item.address }}</span>
                  <span class="item three ellipsis">{{ item.date }}</span>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="r-b-box">
          <div class="title-box">
            <span class="icon"></span>
            <span class="title">实时到达</span>
          </div>
          <div class="cus-table-box">
            <div class="table-header">
              <span class="header one">运输任务编号</span>
              <span class="header two">目的地</span>
              <span class="header three">交付时间</span>
            </div>
            <div class="table-body">
              <div
                v-for="item in arriveDataList"
                :key="item.id"
                class="body-item"
              >
                <span class="item one ellipsis">{{ item.number }}</span>
                <span class="item two ellipsis">{{ item.address }}</span>
                <span class="item three ellipsis">{{ item.date }}</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import echarts from 'echarts'
import 'echarts/map/js/china.js'
import { resizeRem } from '@/utils/rem'
export default {
  components: {},
  filters: {},
  data() {
    return {
      screenWidth:
        document.documentElement.clientWidth || document.body.clientWidth,
      resizeEvt: null,
      myChart: null,
      myChart1: null,
      myChart3: null,
      myChart4: null,
      driverData: [
        {
          id: 1,
          name: '司机总数',
          num: 300
        },
        {
          id: 2,
          name: '待发车司机',
          num: 190
        },
        {
          id: 3,
          name: '在途司机',
          num: 67
        },
        {
          id: 4,
          name: '待返回司机',
          num: 108
        },
        {
          id: 5,
          name: '人均运输时长',
          num: '10h'
        }
      ],
      totalData: [
        {
          id: 2,
          name: '待发车',
          num: 203,
          url: require('../../assets/2.png')
        },
        {
          id: 3,
          name: '运输中',
          num: 622,
          url: require('../../assets/3.png')
        },
        {
          id: 4,
          name: '已到达',
          num: 562,
          url: require('../../assets/4.png')
        }
      ],
      dataList: [
        {
          id: 1,
          number: '3116869653465101',
          address: '神领河北分公司',
          date: '09:02:10'
        },
        {
          id: 2,
          number: '2681259653423402',
          address: '廊坊转运中心',
          date: '09:06:22'
        },
        {
          id: 3,
          number: '3490469653400103',
          address: '燕郊网点',
          date: '09:10:31'
        },
        {
          id: 4,
          number: '2416856653445604',
          address: '昌平转运中心',
          date: '10:27:42'
        }
      ],
      arriveDataList: [
        {
          id: 1,
          number: '2216568693499901',
          address: '神领上海分公司',
          date: '10:04:34'
        },
        {
          id: 2,
          number: '2349068696511992',
          address: '徐汇一级转运中心',
          date: '10:11:19'
        },
        {
          id: 3,
          number: '3403869653453903',
          address: '神领北京分公司',
          date: '10:33:45'
        },
        {
          id: 4,
          number: '2534269346590499',
          address: '朝阳一级转运中心',
          date: '10:59:04'
        },
        {
          id: 5,
          number: '4113477695564405',
          address: '昌平转运中心',
          date: '11:17:16'
        },
        {
          id: 6,
          number: '2686119695349906',
          address: '昌平沙河转运中心',
          date: '11:25:49'
        },
        {
          id: 7,
          number: '1349114861458707',
          address: '建材城网点',
          date: '11:50:04'
        }
      ],
      XAData: [
        [{ name: '长沙' }, { name: '北京', value: 100 }],
        [{ name: '长沙' }, { name: '上海', value: 100 }],
        [{ name: '长沙' }, { name: '广州', value: 100 }],
        [{ name: '长沙' }, { name: '深圳', value: 100 }],
        [{ name: '长沙' }, { name: '西安', value: 100 }]
      ],
      XNData: [
        [{ name: '长沙' }, { name: '西宁', value: 100 }],
        [{ name: '长沙' }, { name: '拉萨', value: 100 }],
        [{ name: '长沙' }, { name: '哈尔滨', value: 100 }],
        [{ name: '长沙' }, { name: '成都', value: 100 }],
        [{ name: '长沙' }, { name: '重庆', value: 100 }]
      ],
      YCData: [
        [{ name: '北京' }, { name: '长沙', value: 100 }],
        [{ name: '北京' }, { name: '贵阳', value: 100 }],
        [{ name: '北京' }, { name: '杭州', value: 100 }]
      ]
    }
  },
  computed: {},
  watch: {},
  beforeDestroy() {
    // 关闭resize事件
    window.removeEventListener(this.resizeEvt, resizeRem, false)
    window.removeEventListener('resize', this.windowResizeHandle)
  },
  created() {},
  mounted() {
    this.remHandel()
    this.vehicleLoading()
    this.destinationTraffic()
    this.getEchart()
    this.getPieChart()
    this.windowResizeHandle()
    window.addEventListener('resize', this.windowResizeHandle)
  },
  methods: {
    // rem
    remHandel() {
      this.resizeEvt =
        'orientationchange' in window ? 'orientationchange' : 'resize'
      resizeRem()
      window.addEventListener(this.resizeEvt, resizeRem, false)
    },
    windowResizeHandle() {
      this.screenWidth =
        document.documentElement.clientWidth || document.body.clientWidth
      if (this.myChart) {
        this.myChart.resize()
      }
      if (this.myChart1) {
        this.myChart1.resize()
      }
      if (this.myChart3) {
        this.myChart3.resize()
      }
      if (this.myChart4) {
        this.myChart4.resize()
      }
    },
    vehicleLoading() {
      this.myChart = echarts.init(
        document.getElementById('vehicle-loading-chart')
      )
      const option = {
        title: {
          left: 'center'
        },
        tooltip: {
          trigger: 'item',
          // formatter: (value) => {
          //   '{a} <br/>{b} : {c} ({d}%)'
          // }
          // <span style="color:red;">.</span>可添加样式
          formatter: '{b} : {c}<br/>({d}%)'
        },
        legend: {
          show: false,
          bottom: 0,
          itemWidth: 2,
          itemHeight: 8,
          data: ['空载车', '半载车', '满载车']
        },
        series: [
          {
            name: '半径模式',
            type: 'pie',
            radius: ['30%', '65%'],
            center: ['48%', '43%'],
            roseType: 'radius',
            label: {
              fontSize: 12,
              color: '#262335'
            },
            emphasis: {
              label: {
                show: true
              }
            },
            data: [
              { value: 335, name: '空载车' },
              { value: 310, name: '半载车' },
              { value: 234, name: '满载车' }
            ],
            itemStyle: {
              emphasis: {
                // shadowBlur: 10,
                // shadowOffsetX: 0
                // shadowColor: 'rgba(0, 0, 0, 0.5)'
              },
              normal: {
                color: function (params) {
                  var colorList = [
                    {
                      c1: ' #fce5ca', // 管理
                      c2: 'red'
                    },
                    {
                      c1: ' #508DFF', // 实践
                      c2: '#26C5FE'
                    },
                    {
                      c1: '#63E587', // 操作
                      c2: '#5FE2E4'
                    }
                  ]
                  return new echarts.graphic.LinearGradient(1, 0, 0, 0, [
                    {
                      // 颜色渐变函数 前四个参数分别表示四个位置依次为左、下、右、上
                      offset: 0,
                      color: colorList[params.dataIndex].c1
                    },
                    {
                      offset: 1,
                      color: colorList[params.dataIndex].c2
                    }
                  ])
                }
              }
            }
          }
        ]
      }
      this.myChart.clear()
      this.myChart.setOption(option)
      this.myChart.resize()
    },
    destinationTraffic() {
      this.$nextTick(() => {
        this.myChart1 = echarts.init(
          document.getElementById('destination-traffic-chart')
        )
        const option = {
          color: new echarts.graphic.LinearGradient(1, 0, 0, 1, [
            { offset: 0, color: '#FEAEA1' },
            { offset: 0.5, color: '#FEA198' },
            { offset: 0.8, color: '#FD7B78' },
            { offset: 1, color: '#FD7371' }
          ]),
          tooltip: {
            trigger: 'axis',
            axisPointer: {
              type: 'cross',
              label: {
                backgroundColor: '#283b56'
              }
            }
          },
          grid: {
            top: '5%',
            left: '5%',
            right: '5%',
            containLabel: true
          },
          legend: {
            bottom: '6%',
            itemGap: 20,
            itemWidth: 2,
            itemHeight: 8,
            data: ['订单数量(万件)', '同比增长'],
            textStyle: {
              color: '#999999'
            }
          },
          xAxis: [
            {
              type: 'category',
              // boundaryGap: true,
              // data: (function() {
              //   var now = new Date()
              //   var res = []
              //   var len = 10
              //   while (len--) {
              //     res.unshift(now.toLocaleTimeString().replace(/^\D*/, ''))
              //     now = new Date(now - 2000)
              //   }
              //   return res
              // })()
              data: [
                '北京',
                '天津',
                '上海',
                '重庆',
                '河北',
                '河南',
                '云南',
                '济南'
              ],
              axisPointer: {
                type: 'shadow'
              },
              axisTick: {
                show: false
              },
              axisLabel: {
                color: '#262335'
              },
              axisLine: {
                lineStyle: {
                  color: '#f2f2f2'
                }
              }
            }
          ],
          yAxis: [
            {
              type: 'value',
              // scale: true,
              max: 30,
              min: 0,
              boundaryGap: [0.2, 0.2],
              splitLine: {
                lineStyle: {
                  color: '#f2f2f2'
                }
              },
              axisLine: {
                show: false
              },
              axisTick: {
                show: false
              },
              axisLabel: {
                color: '#262335'
              }
            },
            {
              type: 'value',
              // scale: true,
              max: 1200,
              min: 0,
              boundaryGap: [0.2, 0.2],
              splitLine: {
                lineStyle: {
                  color: '#f2f2f2'
                }
              },
              axisLine: {
                show: false
              },
              axisTick: {
                show: false
              },
              axisLabel: {
                color: '#262335'
              }
            }
          ],
          series: [
            {
              name: '订单数量(万件)',
              type: 'bar',
              itemStyle: {
                // 柱形图圆角，鼠标移上去效果，如果只是一个数字则说明四个参数全部设置为那么多
                emphasis: {
                  barBorderRadius: 30
                },
                normal: {
                  color: new echarts.graphic.LinearGradient(1, 0, 0, 1, [
                    { offset: 0, color: '#FEAEA1' },
                    { offset: 0.5, color: '#FEA198' },
                    { offset: 0.8, color: '#FD7B78' },
                    { offset: 1, color: '#FD7371' }
                  ]),
                  // 柱形图圆角，初始化效果
                  barBorderRadius: [10, 10, 10, 10]
                }
              },
              barWidth: '30%',
              // xAxisIndex: 1,
              yAxisIndex: 1,
              data: (function () {
                var res = []
                var len = 8
                while (len--) {
                  res.push(Math.round(Math.random() * 1000))
                }
                return res
              })()
            },
            {
              name: '同比增长',
              type: 'line',
              itemStyle: {
                color: '#699AFC'
              },
              data: (function () {
                var res = []
                var len = 0
                while (len < 8) {
                  res.push((Math.random() * 10 + 5).toFixed(1) - 0)
                  len++
                }
                return res
              })()
            }
          ]
        }
        this.myChart1.clear()
        this.myChart1.setOption(option)
        this.myChart1.resize()
      })
    },
    convertData(data) {
      // 地图数据转换
      const res = []
      for (let i = 0; i < data.length; i++) {
        const dataItem = data[i]
        const fromCoord = this.geoCoordMap[dataItem[0].name]
        const toCoord = this.geoCoordMap[dataItem[1].name]
        if (fromCoord && toCoord) {
          res.push({
            fromName: dataItem[0].name,
            toName: dataItem[1].name,
            coords: [fromCoord, toCoord],
            value: dataItem[1].value
          })
        }
      }
      return res
    },
    getEchart() {
      // 初始化地图数据
      this.myChart3 = echarts.init(document.getElementById('main-chart'))

      this.geoCoordMap = {
        上海: [121.4648, 31.2891],
        东莞: [113.8953, 22.901],
        东营: [118.7073, 37.5513],
        中山: [113.4229, 22.478],
        临汾: [111.4783, 36.1615],
        临沂: [118.3118, 35.2936],
        丹东: [124.541, 40.4242],
        丽水: [119.5642, 28.1854],
        乌鲁木齐: [87.9236, 43.5883],
        佛山: [112.8955, 23.1097],
        保定: [115.0488, 39.0948],
        兰州: [103.5901, 36.3043],
        包头: [110.3467, 41.4899],
        北京: [116.4551, 40.2539],
        北海: [109.314, 21.6211],
        南京: [118.8062, 31.9208],
        南宁: [108.479, 23.1152],
        南昌: [116.0046, 28.6633],
        南通: [121.1023, 32.1625],
        厦门: [118.1689, 24.6478],
        台州: [121.1353, 28.6688],
        合肥: [117.29, 32.0581],
        呼和浩特: [111.4124, 40.4901],
        咸阳: [108.4131, 34.8706],
        哈尔滨: [127.9688, 45.368],
        唐山: [118.4766, 39.6826],
        嘉兴: [120.9155, 30.6354],
        大同: [113.7854, 39.8035],
        大连: [122.2229, 39.4409],
        天津: [117.4219, 39.4189],
        太原: [112.3352, 37.9413],
        威海: [121.9482, 37.1393],
        宁波: [121.5967, 29.6466],
        宝鸡: [107.1826, 34.3433],
        宿迁: [118.5535, 33.7775],
        常州: [119.4543, 31.5582],
        广州: [113.5107, 23.2196],
        廊坊: [116.521, 39.0509],
        延安: [109.1052, 36.4252],
        张家口: [115.1477, 40.8527],
        徐州: [117.5208, 34.3268],
        德州: [116.6858, 37.2107],
        惠州: [114.6204, 23.1647],
        成都: [103.9526, 30.7617],
        扬州: [119.4653, 32.8162],
        承德: [117.5757, 41.4075],
        拉萨: [91.1865, 30.1465],
        无锡: [120.3442, 31.5527],
        日照: [119.2786, 35.5023],
        昆明: [102.9199, 25.4663],
        杭州: [119.5313, 29.8773],
        枣庄: [117.323, 34.8926],
        柳州: [109.3799, 24.9774],
        株洲: [113.5327, 27.0319],
        武汉: [114.3896, 30.6628],
        汕头: [117.1692, 23.3405],
        江门: [112.6318, 22.1484],
        沈阳: [123.1238, 42.1216],
        沧州: [116.8286, 38.2104],
        河源: [114.917, 23.9722],
        泉州: [118.3228, 25.1147],
        泰安: [117.0264, 36.0516],
        泰州: [120.0586, 32.5525],
        济南: [117.1582, 36.8701],
        济宁: [116.8286, 35.3375],
        海口: [110.3893, 19.8516],
        淄博: [118.0371, 36.6064],
        淮安: [118.927, 33.4039],
        深圳: [114.5435, 22.5439],
        清远: [112.9175, 24.3292],
        温州: [120.498, 27.8119],
        渭南: [109.7864, 35.0299],
        湖州: [119.8608, 30.7782],
        湘潭: [112.5439, 27.7075],
        滨州: [117.8174, 37.4963],
        潍坊: [119.0918, 36.524],
        烟台: [120.7397, 37.5128],
        玉溪: [101.9312, 23.8898],
        珠海: [113.7305, 22.1155],
        盐城: [120.2234, 33.5577],
        盘锦: [121.9482, 41.0449],
        石家庄: [114.4995, 38.1006],
        福州: [119.4543, 25.9222],
        秦皇岛: [119.2126, 40.0232],
        绍兴: [120.564, 29.7565],
        聊城: [115.9167, 36.4032],
        肇庆: [112.1265, 23.5822],
        舟山: [122.2559, 30.2234],
        苏州: [120.6519, 31.3989],
        莱芜: [117.6526, 36.2714],
        菏泽: [115.6201, 35.2057],
        营口: [122.4316, 40.4297],
        葫芦岛: [120.1575, 40.578],
        衡水: [115.8838, 37.7161],
        衢州: [118.6853, 28.8666],
        西宁: [101.4038, 36.8207],
        西安: [109.1162, 34.2004],
        贵阳: [106.6992, 26.7682],
        连云港: [119.1248, 34.552],
        邢台: [114.8071, 37.2821],
        邯郸: [114.4775, 36.535],
        郑州: [113.4668, 34.6234],
        鄂尔多斯: [108.9734, 39.2487],
        重庆: [107.7539, 30.1904],
        金华: [120.0037, 29.1028],
        铜川: [109.0393, 35.1947],
        银川: [106.3586, 38.1775],
        镇江: [119.4763, 31.9702],
        长春: [125.8154, 44.2584],
        长沙: [113.0823, 28.2568],
        长治: [112.8625, 36.4746],
        阳泉: [113.4778, 38.0951],
        青岛: [120.4651, 36.3373],
        韶关: [113.7964, 24.7028]
      }
      // 设置飞行航线样式
      // const planePath = 'path://M1705.06,1318.313v-89.254l-319.9-221.799l0.073-208.063c0.521-84.662-26.629-121.796-63.961-121.491c-37.332-0.305-64.482,36.829-63.961,121.491l0.073,208.063l-319.9,221.799v89.254l330.343-157.288l12.238,241.308l-134.449,92.931l0.531,42.034l175.125-42.917l175.125,42.917l0.531-42.034l-134.449-92.931l12.238-241.308L1705.06,1318.313z'
      // const planePath = 'path://M1705.06,1318.313v-89.254l-319.9-221.799l0.073-208.063c0.521-84.662-26.629-121.796-63.961-121.491c-37.332-0.305-64.482,36.829-63.961,121.491l0.073,208.063l-319.9,221.799v89.254l330.343-157.288l12.238,241.308l-134.449,92.931l0.531,42.034l175.125-42.917l175.125,42.917l0.531-42.034l-134.449-92.931l12.238-241.308L1705.06,1318.313z'
      // const planePath = 'path://M402.986 490.667c0 70.692 57.308 128 128 128s128-57.308 128-128c0-70.692-57.308-128-128-128-70.692 0-128 57.308-128 128z'
      const planePath =
        'path://M306.28092891 358.28692148L515.86227332 1.43220042l215.24570478 356.85472106-424.82704919 0z'
      // const color = ['rgba(227,74,64,0.45)', 'rgba(227,74,64,0.45)', 'rgba(227,74,64,0.45)'] // 航线的颜色
      const series = []

      ;[
        ['长沙', this.XAData],
        ['长沙', this.XNData],
        ['北京', this.YCData]
      ].forEach((item, i) => {
        series.push(
          {
            name: item[0] + ' Top3',
            type: 'lines',
            zlevel: 1,
            effect: {
              show: true,
              period: 6,
              trailLength: 0.7,
              // arrow箭头的颜色
              // color: 'red',
              symbolSize: 4
            },
            lineStyle: {
              normal: {
                color: 'rgba(227,74,64,0.1)',
                width: 0,
                curveness: 0.2
              }
            },
            data: this.convertData(item[1])
          },
          {
            name: item[0] + ' Top3',
            type: 'lines',
            zlevel: 2,
            symbol: ['none', 'arrow'],
            symbolSize: 10,
            effect: {
              show: true,
              period: 6,
              trailLength: 0,
              // 飞行样式
              symbol: planePath,
              symbolSize: 2
            },
            lineStyle: {
              normal: {
                color: 'rgba(227,74,64,0.7)',
                width: 1,
                opacity: 0.6,
                curveness: 0.2
              }
            },
            data: this.convertData(item[1])
          },
          {
            name: item[0] + ' Top3',
            type: 'effectScatter',
            coordinateSystem: 'geo',
            zlevel: 2,
            rippleEffect: {
              brushType: 'stroke'
            },
            label: {
              normal: {
                show: true,
                position: 'right',
                formatter: '{b}',
                color: '#333333'
              }
            },
            symbolSize: (val) => {
              return val[2] / 8
            },
            itemStyle: {
              normal: {
                // color: 'rgba(227,74,64,0.6)'
                color: {
                  type: 'radial',
                  x: 0.5,
                  y: 0.5,
                  r: 0.5,
                  colorStops: [
                    {
                      offset: 0,
                      color: 'rgba(219,52,45,0.7)' // 0% 处的颜色
                    },
                    {
                      offset: 1,
                      color: '#fe977e' // 100% 处的颜色
                    }
                  ],
                  global: false // 缺省为 false
                }
              },
              emphasis: {
                itemStyle: {
                  color: 'red'
                }
                // areaColor: '#5389f0'
              }
            },
            data: item[1].map((dataItem) => {
              return {
                name: dataItem[1].name,
                value: this.geoCoordMap[dataItem[1].name].concat([
                  dataItem[1].value
                ])
              }
            })
          }
        )
      })

      const option = {
        tooltip: {
          trigger: 'item',
          formatter: (params, ticket, callback) => {
            if (params.seriesType === 'effectScatter') {
              return '线路：' + params.data.name + '' + params.data.value[2]
            } else if (params.seriesType === 'lines') {
              return (
                params.data.fromName +
                '>' +
                params.data.toName +
                '<br />' +
                params.data.value
              )
            } else {
              return params.name
            }
          }
        },
        geo: {
          map: 'china',
          label: {
            emphasis: {
              show: true,
              color: '#fff'
            }
          },
          roam: false,
          //   放大我们的地图
          zoom: 1,
          itemStyle: {
            normal: {
              // areaColor: 'rgba(83,137,240, 0.42)',
              // borderColor: 'rgba(43, 196, 243, 1)',
              // 地域边框颜色
              borderColor: '#FDB892',
              borderWidth: 1
            },
            emphasis: {
              // areaColor: '#2B91B7'
              // hover悬停效果
              areaColor: '#ba586f'
            }
          },
          regions: [
            {
              name: '北京',
              itemStyle: {
                normal: {
                  areaColor: '#cfc5de'
                }
              }
            },
            {
              name: '天津',
              itemStyle: {
                normal: {
                  areaColor: '#F19798'
                }
              }
            },
            {
              name: '上海',
              itemStyle: {
                normal: {
                  areaColor: '#feffdb'
                }
              }
            },
            {
              name: '重庆',
              itemStyle: {
                normal: {
                  areaColor: '#e0cee4'
                }
              }
            },
            {
              name: '河北',
              itemStyle: {
                normal: {
                  areaColor: '#EFE2EF'
                }
              }
            },
            {
              name: '河南',
              itemStyle: {
                normal: {
                  areaColor: '#C46C7E'
                }
              }
            },
            {
              name: '云南',
              itemStyle: {
                normal: {
                  areaColor: '#F8C5BC'
                }
              }
            },
            {
              name: '辽宁',
              itemStyle: {
                normal: {
                  areaColor: '#F19798'
                }
              }
            },
            {
              name: '黑龙江',
              itemStyle: {
                normal: {
                  areaColor: '#F19798'
                }
              }
            },
            {
              name: '湖南',
              itemStyle: {
                normal: {
                  areaColor: '#F7CDDF'
                }
              }
            },
            {
              name: '安徽',
              itemStyle: {
                normal: {
                  areaColor: '#F8C5BC'
                }
              }
            },
            {
              name: '山东',
              itemStyle: {
                normal: {
                  areaColor: '#F19798'
                }
              }
            },
            {
              name: '新疆',
              itemStyle: {
                normal: {
                  areaColor: '#C46C7E'
                }
              }
            },
            {
              name: '江苏',
              itemStyle: {
                normal: {
                  areaColor: '#EFE2EF'
                }
              }
            },
            {
              name: '浙江',
              itemStyle: {
                normal: {
                  areaColor: '#C46C7E'
                }
              }
            },
            {
              name: '江西',
              itemStyle: {
                normal: {
                  areaColor: '#EFE2EF'
                }
              }
            },
            {
              name: '湖北',
              itemStyle: {
                normal: {
                  areaColor: '#F19798'
                }
              }
            },
            {
              name: '广西',
              itemStyle: {
                normal: {
                  areaColor: '#EFE2EF'
                }
              }
            },
            {
              name: '甘肃',
              itemStyle: {
                normal: {
                  areaColor: '#F19798'
                }
              }
            },
            {
              name: '山西',
              itemStyle: {
                normal: {
                  areaColor: '#F19798'
                }
              }
            },
            {
              name: '内蒙古',
              itemStyle: {
                normal: {
                  areaColor: '#C46C7E'
                }
              }
            },
            {
              name: '陕西',
              itemStyle: {
                normal: {
                  areaColor: '#F8C5BC'
                }
              }
            },
            {
              name: '吉林',
              itemStyle: {
                normal: {
                  areaColor: '#F8C5BC'
                }
              }
            },
            {
              name: '福建',
              itemStyle: {
                normal: {
                  areaColor: '#F19798'
                }
              }
            },
            {
              name: '贵州',
              itemStyle: {
                normal: {
                  areaColor: '#F19798'
                }
              }
            },
            {
              name: '广东',
              itemStyle: {
                normal: {
                  areaColor: '#C46C7E'
                }
              }
            },
            {
              name: '青海',
              itemStyle: {
                normal: {
                  areaColor: '#F7CDDF'
                }
              }
            },
            {
              name: '西藏',
              itemStyle: {
                normal: {
                  areaColor: '#F19798'
                }
              }
            },
            {
              name: '四川',
              itemStyle: {
                normal: {
                  areaColor: '#C46C7E'
                }
              }
            },
            {
              name: '宁夏',
              itemStyle: {
                normal: {
                  areaColor: '#fefcd5'
                }
              }
            },
            {
              name: '海南',
              itemStyle: {
                normal: {
                  areaColor: '#F19798'
                }
              }
            },
            {
              name: '台湾',
              itemStyle: {
                normal: {
                  areaColor: '#F19798'
                }
              }
            },
            {
              name: '香港',
              itemStyle: {
                normal: {
                  areaColor: '#dc9bbb'
                }
              }
            },
            {
              name: '澳门',
              itemStyle: {
                normal: {
                  areaColor: '#e0f7cc'
                }
              }
            }
          ]
        },
        series: series
      }
      this.myChart3.clear()
      this.myChart3.setOption(option)

      this.myChart3.resize()
    },
    getPieChart() {
      this.myChart4 = echarts.init(document.getElementById('chart04'))
      const getvalue = [68]
      const option = {
        title: {
          text: getvalue + '%',
          textStyle: {
            color: '#ffffff',
            fontSize: 14
          },
          subtext: '异常占比',
          subtextStyle: {
            color: 'rgba(255,255,255,0.5)',
            fontSize: 12
          },
          itemGap: 6,
          left: 'center',
          top: '40%'
        },
        grid: {
          left: 0,
          right: 0,
          top: 0,
          bottom: 0,
          containLabel: true
        },
        tooltip: {
          formatter: function (params) {
            return (
              '<span style="color: #fff;">异常占比：' + getvalue + '%</span>'
            )
          }
        },
        angleAxis: {
          max: 100,
          clockwise: true, // 逆时针
          // 隐藏刻度线
          show: false
        },
        radiusAxis: {
          type: 'category',
          show: true,
          axisLabel: {
            show: false
          },
          axisLine: {
            show: false
          },
          axisTick: {
            show: false
          }
        },
        polar: {
          center: ['50%', '50%'],
          radius: '100%' // 图形大小
        },
        series: [
          {
            type: 'bar',
            data: getvalue,
            showBackground: true,
            backgroundStyle: {
              color: 'rgba(255,255,255,0.3)'
            },
            coordinateSystem: 'polar',
            roundCap: true,
            barWidth: 10,
            itemStyle: {
              normal: {
                opacity: 1,
                color: '#ffffff'
                // color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                //   offset: 0,
                //   color: '#25BFFF'
                // }, {
                //   offset: 1,
                //   color: '#5284DE'
                // }]),
                // shadowBlur: 5,
                // shadowColor: '#2A95F9'
              }
            }
          }
        ]
      }
      this.myChart4.clear()
      this.myChart4.setOption(option)
      this.myChart4.resize()
    }
  }
}
</script>
<style lang="scss" scoped>
@media (max-width: 1600px) {
  .l-box {
    .l-t-box {
      .content-box {
        .content-item {
          &:nth-child(1),
          &:nth-child(4) {
            width: 34% !important;
          }
          &:nth-child(3) {
            width: 24% !important;
          }
        }
      }
    }
  }
}
.screen-box {
  width: 100vw;
  height: 100vh;
  min-height: 768px;
  padding: 20px;
  box-sizing: border-box;
  .container-box {
    width: 100%;
    height: 100%;
    display: flex;
    box-sizing: border-box;
    .l-box {
      width: 25%;
      height: 100%;
      box-sizing: border-box;
      display: flex;
      flex-flow: column;
      .title-box {
        padding: 0.2em 0.2em 0.2em 5px;
        display: flex;
        align-items: center;
        box-sizing: border-box;
        .icon {
          width: 7px;
          height: 11px;
          background: linear-gradient(
            136deg,
            #db342d 0%,
            #fe977e 79%,
            #fdb892 99%
          );
          margin-right: 8px;
        }
        .title {
          font-size: 0.16em;
          font-weight: 700;
          font-weight: bold;
          color: #262335;
        }
      }
      .chart-box {
        width: 100%;
        height: calc(100% - 0.58em);
        // padding: 0 0.1em 0.1em;
        box-sizing: border-box;
        display: flex;
        flex-flow: column;
        .chart-con {
          width: 100%;
          height: 100%;
        }
      }
      .l-t-box {
        width: 100%;
        height: 25%;
        margin-bottom: 20px;
        box-sizing: border-box;
        background: #ffffff;
        border-radius: 4px;
        .content-box {
          display: flex;
          flex-wrap: wrap;
          padding: 10px 30px;
          box-sizing: border-box;
          .content-item {
            // width: calc(100% / 3);
            &:nth-child(4),
            &:nth-child(5) {
              margin-top: 30px;
            }
            &:nth-child(1),
            &:nth-child(4) {
              width: 40%;
            }
            &:nth-child(3) {
              width: 18%;
            }
            &:nth-child(2),
            &:nth-child(5) {
              width: 42%;
            }
            .num {
              font-weight: 400;
              color: #75a3fd;
              font-size: 0.2em;
            }
            .name {
              margin: 0.6em 0 0;
              font-size: 0.14em;
              font-weight: 400;
              color: #262335;
            }
          }
        }
      }
      .l-m-box {
        width: 100%;
        height: 31%;
        box-sizing: border-box;
        background: #ffffff;
        // display: flex;
        // flex-flow: column;
        border-radius: 4px;
      }
      .l-b-box {
        width: 100%;
        height: 44%;
        margin-top: 20px;
        box-sizing: border-box;
        background: #ffffff;
        border-radius: 4px;
        display: flex;
        flex-flow: column;
      }
    }
    .c-box {
      width: 50%;
      height: 100%;
      background: #ffffff;
      margin: 0 20px;
      box-sizing: border-box;
      border-radius: 4px;
      display: flex;
      flex-flow: column;
      .title-box {
        padding: 20px 28px;
        display: flex;
        align-items: center;
        box-sizing: border-box;
        .icon {
          width: 7px;
          height: 11px;
          background: linear-gradient(
            136deg,
            #db342d 0%,
            #fe977e 79%,
            #fdb892 99%
          );
          margin-right: 8px;
        }
        .title {
          font-size: 0.16em;
          font-weight: 700;
          font-weight: bold;
          color: #262335;
        }
      }
      .c-t-box {
        width: 100%;
        .total-box {
          width: 100%;
          height: 1.2em;
          display: flex;
          align-items: center;
          justify-content: center;
          padding: 0 28px;
          margin: 20px 0;
          box-sizing: border-box;
          .item-one-list {
            width: 25%;
            height: 1.16em;
            background: #ffffff;
            border-radius: 10px;
            box-shadow: 0px 1px 13px 0px rgba(220, 224, 229, 0.64);
            display: flex;
            align-items: center;
            justify-content: center;
            z-index: 1;
            .icon {
              width: 34px;
              height: 34px;
            }
            .item {
              // flex: 1;
              min-height: 0.4em;
              margin-left: 0.15em;
              display: flex;
              flex-flow: column;
              .num {
                font-size: 0.24em;
                color: #f04b30;
              }
              .name {
                margin-top: 10px;
                font-size: 0.14em;
                color: #262335;
              }
            }
          }
          .item-two-list {
            width: 75%;
            height: 0.92em;
            background: #fbfdff;
            border-radius: 0 10px 10px 0;
            box-shadow: 0px 1px 13px 0px rgba(220, 224, 229, 0.64);
            display: flex;
            align-items: center;
            justify-content: center;
            .item-list {
              width: calc(100% / 3);
              height: 0.4em;
              display: flex;
              align-items: center;
              justify-content: center;
              .icon {
                width: 34px;
                height: 34px;
              }
              .item {
                // flex: 1;
                min-height: 0.4em;
                margin-left: 0.15em;
                display: flex;
                flex-flow: column;
                .num {
                  font-size: 0.2em;
                  color: #75a3fd;
                }
                .name {
                  margin-top: 8px;
                  font-size: 0.14em;
                  color: #262335;
                }
              }
            }
          }
        }
      }
      .c-b-box {
        width: 100%;
        //  height: 100%;
        height: calc(100% - 1.2em - 96px);
        //  display: flex;
        //  flex-flow: column;
        .chart-box {
          width: 100%;
          height: calc(100% - 58px);
          // padding: 0 0.1em 0.1em;
          box-sizing: border-box;
          .chart-con {
            width: 100%;
            height: 100%;
          }
        }
      }
    }
    .r-box {
      width: 25%;
      height: 100%;
      box-sizing: border-box;
      display: flex;
      flex-flow: column;
      .title-box {
        padding: 0.2em 0.2em 0.2em 5px;
        display: flex;
        align-items: center;
        box-sizing: border-box;
        .icon {
          width: 7px;
          height: 11px;
          background: linear-gradient(
            136deg,
            #db342d 0%,
            #fe977e 79%,
            #fdb892 99%
          );
          margin-right: 8px;
        }
        .title {
          font-size: 0.16em;
          font-weight: 700;
          font-weight: bold;
          color: #262335;
        }
      }
      .r-t-box {
        width: 100%;
        height: 20%;
        margin-bottom: 20px;
        box-sizing: border-box;
        background: #ffffff;
        border-radius: 4px;
        .content-box {
          width: 100%;
          height: calc(100% - 0.58em);
          display: flex;
          padding: 0.1em 0.3em;
          box-sizing: border-box;
          position: relative;
          .con-l-box {
            .icon {
              width: 0.44em;
              height: 0.3em;
              margin-bottom: 0.2em;
            }
          }
          .con-c-box {
            flex: 1;
          }
          .con-r-box {
            .icon {
              width: 0.32em;
              height: 0.37em;
              margin-bottom: 0.13em;
            }
          }
          .con-box {
            width: 1.2em;
            display: flex;
            flex-flow: column;
            align-items: center;
            .num {
              font-size: 0.2em;
              font-weight: 600;
              color: #75a3fd;
              margin-bottom: 0.4em;
            }
            .name {
              font-size: 0.14em;
              font-weight: 400;
              color: #333333;
            }
          }
          .line {
            // flex: 1;
            height: 0.38em;
            position: absolute;
            width: 55%;
            left: 23%;
            top: 0;
          }
        }
      }
      .r-m-box {
        width: 100%;
        height: 45%;
        box-sizing: border-box;
        display: flex;
        flex-flow: column;
        .con-t-box {
          width: 100%;
          height: 52%;
          margin-bottom: 14px;
          box-sizing: border-box;
          background: linear-gradient(136deg, #db342d 0%, #fe977e 99%);
          border-radius: 4px;
          display: flex;
          flex-flow: column;
          position: relative;
          .img1 {
            width: 0.15em;
            height: 0.62em;
            position: absolute;
            bottom: -15%;
            left: 18%;
          }
          .img2 {
            width: 0.15em;
            height: 0.62em;
            position: absolute;
            bottom: 2%;
            bottom: -15%;
            right: 18%;
          }
          .title-box {
            padding: 0.2em 0.2em 0 5px;
            display: flex;
            align-items: center;
            box-sizing: border-box;
            .icon {
              width: 7px;
              height: 11px;
              background: rgba(255, 255, 255, 0.38);
              margin-right: 8px;
            }
            .title {
              font-size: 0.16em;
              font-weight: 700;
              font-weight: bold;
              color: #ffffff;
            }
          }
          .nor-box {
            width: 100%;
            height: calc(100% - 0.38em);
            padding: 0 25px 0 30px;
            box-sizing: border-box;
            display: flex;
            align-items: center;
            .nor-l {
              width: 50%;
              height: 100%;
              display: flex;
              flex-flow: column;
              margin-bottom: 8%;
              justify-content: center;
              // align-items: center;
              .num {
                font-size: 0.24em;
                font-weight: 600;
                color: #ffffff;
                margin-bottom: 0.5em;
              }
              .name {
                font-size: 0.14em;
                font-weight: bold;
                color: #ffffff;
              }
            }
            .chart-box {
              position: absolute;
              top: 5%;
              right: 0;
              width: 50%;
              height: 100%;
            }
          }
        }
        .con-b-box {
          width: 100%;
          height: 48%;
          background: #ffffff;
          border-radius: 4px;
          display: flex;
          flex-flow: column;
          box-sizing: border-box;
        }
      }
      .cus-table-box {
        width: 100%;
        height: 100%;
        padding: 0.2em 0.3em;
        box-sizing: border-box;
        display: flex;
        flex-flow: column;
        .one {
          width: 42%;
        }
        .two {
          width: 36%;
          padding-left: 2px;
        }
        .three {
          width: 22%;
          padding-left: 2px;
        }
        .table-header {
          width: 100%;
          display: flex;
          .header {
            // width: calc(100% / 3);
            font-size: 12px;
            font-weight: 400;
            text-align: left;
            color: #999999;
          }
        }
        .table-body {
          width: 100%;
          display: flex;
          flex-flow: column;
          // margin-top: 0.1em;
          .body-item {
            width: 100%;
            display: flex;
            // line-height: 0.3em;
            margin-top: 0.2em;
            .item {
              // width: calc(100% / 3);
              height: 13px;
              font-size: 12px;
              font-weight: 400;
              text-align: left;
              color: #333333;
            }
          }
        }
      }
      .r-b-box {
        width: 100%;
        height: 35%;
        margin-top: 20px;
        box-sizing: border-box;
        background: #ffffff;
        border-radius: 4px;
        display: flex;
        flex-flow: column;
        .cus-table-box {
          padding: 0.1em 0.3em 0.2em;
          height: calc(100% - 0.58em);
        }
      }
    }
  }
  .ellipsis {
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
  }
}
</style>
