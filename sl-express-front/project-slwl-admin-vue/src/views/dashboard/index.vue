<template>
  <div
    class="dashboard-container"
  >
    <el-row
      :gutter="24"
      style="margin-top: 20px; margin-bottom: 20px"
      justify="center"
    >
      <el-col
        :span="14"
        style="padding-left: 20px; padding-right: 10px"
      >
        <el-card class="box-card institution">
          <p style="margin-top:0">机构概述</p>
          <div class="content">
            <div class="left-content">
              <div class="name">{{ organOverview.organName }}</div>
              <div class="address">地址：{{ organOverview.organAddress }}</div>
              <div class="duty-people">负责人：{{ organOverview.principal }} {{ organOverview.phone }}</div>
              <div
                class="search-sales-department el-button el-button--warning is-plain"
                @click="showDepartment()"
              >
                查看营业部分布
              </div>
            </div>
            <div class="right-content">
              <div class="item">
                <div>
                  <div class="label">分拣中心(个)</div>
                  <div
                    class="num"
                    @click="()=>{
                      this.$router.push({
                        path: '/branches/organization-manage'
                      })
                    }"
                  >{{ organOverview.sortingCenterNumber }}</div>
                </div>
              </div>
              <div class="item">
                <div>
                  <div class="label">营业部(个)</div>
                  <div
                    class="num"
                    @click="()=>{
                      this.$router.push({
                        path: '/branches/organization-manage'
                      })
                    }"
                  >{{ organOverview.agencyNumber }}</div>
                </div>
              </div>
              <div class="item">
                <div>
                  <div class="label">司机人数(个)</div>
                  <div
                    class="num"
                    @click="()=>{
                      this.$router.push({
                        path: '/transit/driver'
                      })
                    }"
                  >{{ organOverview.driverNumber }}</div>
                </div>
              </div>
              <div class="item">
                <div>
                  <div class="label">快递员人数(个)</div>
                  <div
                    class="num"
                    @click="()=>{
                      this.$router.push({
                        path: '/branches/operational-range'
                      })
                    }"
                  >{{ organOverview.courierNumber }}</div>
                </div>
              </div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col
        :span="10"
        style="padding-left: 10px; padding-right: 20px"
      >
        <el-card
          class="box-card right-info"
          :body-style="{ paddingTop: '21px', minHeight: '60px' }"
        >
          <div class="header">
            <p>今日数据</p>
            <div
              class="refresh-box"
              @click="handleRefreshTodayDataTime()"
            >
              {{ todayDataTime }}
            </div>
          </div>
          <el-row
            :span="24"
            type="flex"
            class="row-bg"
            justify="space-around"
          >
            <el-col :span="8">
              <div class="label">订单金额(元)</div>
              <div
                id="my-number1"
                class="value"
              >{{ todayData.orderAmount }}</div>
              <div
                class="bottom"
                :class="todayData.orderAmountChanges<=0?'active':''"
              >较昨日 {{ todayData.orderAmountChanges<=0? '-':"+" }}{{ todayData.orderAmountChanges }}</div>
            </el-col>
            <el-col :span="8">
              <div class="label">订单数量(笔)</div>
              <div
                id="my-number2"
                class="value"
              >{{ todayData.orderNumber }}</div>
              <div
                class="bottom"
                :class="todayData.orderNumberChanges<=0?'active':''"
              >较昨日 {{ todayData.orderNumberChanges<=0? '-':"+" }}{{ todayData.orderNumberChanges }}</div>
            </el-col>
            <el-col :span="8">
              <div class="label">运输任务(次)</div>
              <div
                id="my-number3"
                class="value"
              >{{ todayData.transportTaskNumber }}</div>
              <div
                class="bottom"
                :class="todayData.transportTaskNumberChanges<=0?'active':''"
              >较昨日 {{ todayData.transportTaskNumberChanges<=0? '-':"+" }}{{ todayData.transportTaskNumberChanges }}</div>
            </el-col>
          </el-row>
        </el-card>
      </el-col>
    </el-row>
    <!-- 订单总量  订单分布-->
    <el-row
      :gutter="24"
      style="margin-bottom: 20px"
    >
      <el-col
        :span="14"
        style="padding-left:20px;padding-right:10px"
      >
        <el-card
          class="hots cover-card backlog notTasking"
          style="position:relative"
        >
          <div class="header">
            <p style="display:flex;align-items:center">
              待办任务
              <el-tooltip
                class="item"
                effect="light"
                placement="bottom-start"
                style="margin-left:6px"
              >
                <div
                  slot="content"
                  style="line-height:28px;color:#818693;font-size:14px;background:white"
                >
                  待取件率=待取件/(下单数量-取消数量)，且取件类型=上门取件<br />
                  待派送率=待派送/(待派送+派送中+已签收+拒收)<br />
                  未分配率=未分配/全部数据<br />
                  超时率=超时任务/(已完成+进行中+已取消）<br />
                </div>
                <img src="../../assets/explain.png"></img>
              </el-tooltip>
            </p>
            <div
              class="refresh-box"
              @click="handleRefreshNotTaskDataTime()"
            >
              {{ notTaskDataTime }}
            </div>
          </div>
          <div>
            <div
              id="order-total-chart"
            />
          </div>

          <div class="bottom-label">
            <div
              v-for="(item,index) in backlogListdata"
              :key="index"
              class="item"
              @click="handleBacklog(item.url,item.status)"
            >
              <span>{{ item.label }}</span> <span>{{ item.value }}</span>

            </div>
          </div>
        </el-card>
      </el-col>
      <el-col
        :span="10"
        style="padding-left:10px;padding-right:20px"
      >
        <el-card class="hots cover-card backlog tasking">
          <div class="header">
            <p style="display:flex;align-items:center">
              执行中任务
              <el-tooltip
                class="item"
                effect="light"
                placement="bottom"
                style="margin-left:6px"
              >
                <div
                  slot="content"
                  style="line-height:28px;color:#818693;font-size:14px;background:white"
                >
                  运输率=运输中/(全部订单-待取件-已取件-网点入库-待装车-已取消）<br />
                  派送率=派送中/(待派送+派送中+已签收+拒收）<br />
                </div>
                <img src="../../assets/explain.png"></img>
              </el-tooltip>
            </p>
            <div
              class="refresh-box"
              @click="handleRefreshTaskingDataTime()"
            >
              {{ taskingDataTime }}
            </div>
          </div>
          <div
            id="order-fenbu-chart"
          />
          <div class="bottom-label">
            <div
              v-for="(item,index) in taskingListData"
              :key="index"
              class="item"
              @click="handleBacklog(item.url,item.status)"
            >
              <span>{{ item.label }}</span> <span>{{ item.value }}</span>

            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 常用功能 -->
    <el-row
      :gutter="20"
      style="margin-bottom: 20px"
    >
      <el-col
        :xs="24"
        :sm="24"
        style="padding-left:20px;padding-right:20px"
      >
        <el-card class="hots cover-card useFeature">
          <p style="font-size:16px;font-weight:bold;margin-top:0">常用功能</p>
          <div class="useFeaturelist">
            <div
              v-for="(item,index) in useFeatureList"
              :key="index"
              class="item"
              @click="handleClick(item.link)"
            >
              <img :src="item.url" />
              <p>{{ item.label }}</p>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 线路管理和运输任务 -->
    <el-row
      :gutter="24"
      style="margin-bottom: 20px"
    >
      <el-col
        :span="12"
        style="padding-left:20px;padding-right:10px"
      >
        <el-card class="hots cover-card lineManage">
          <p style="font-size:16px;font-weight:bold;margin-bottom:20px;margin-top:0">线路管理</p>
          <div id="line-manage-chart"></div>
        </el-card>
      </el-col>
      <el-col
        :span="12"
        style="padding-left:10px;padding-right:20px"
      >
        <el-card class="hots cover-card transport-task">
          <div class="header">
            <p>
              运输任务
            </p>
            <div
              class="search-more"
              @click="handleToTransportTask"
            >
              查看更多
            </div>
          </div>
          <div class="table_body">
            <div class="table_th">
              <div class="tr1 th_style">任务编号</div>
              <div class="tr2 th_style">起始地</div>
              <div class="tr3 th_style">目的地</div>
              <div class="tr4 th_style">车辆</div>
              <div class="tr4 th_style">任务状态</div>
            </div>
            <div
              class="table_main_body"
              @mousemove="handleStopScroll()"
              @mouseleave="handleStartScroll()"
            >
              <div
                class="table_inner_body"
                :class="!tableTop?'':'startTransition'"
                :style="{top: tableTop + 'px'}"
              >
                <div
                  v-for="(item,index) in transportTaskListData"
                  :key="index"
                  class="table_tr"
                >
                  <div class="tr1 tr">{{ item.id }}</div>
                  <div class="tr2 tr">{{ item.startAgency }}</div>
                  <div class="tr3 tr"> {{ item.endAgency }}</div>
                  <div class="tr4 tr">{{ item.licensePlate }}</div>
                  <div class="tr4 tr ">{{
                    item.status =='1'?'待执行':item.status == '2'?'进行中':item.status == '3'?'待确认':item.status == '4'?'已完成':'已取消'
                  }}</div>
                </div>
              </div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
    <!-- 订单总量和订单分布 -->
    <el-row
      :gutter="24"
      style="margin-bottom: 20px"
    >
      <el-col
        :span="12"
        style="padding-left:20px;padding-right:10px"
      >
        <el-card class="hots cover-card orderAccount">
          <div class="header">
            <p style="margin-bottom:12px">
              订单总量
            </p>
            <div style="font-size:14px;color:#818693">
              {{ getMonthsBeforeSix() }}
            </div>
          </div>
          <p style="font-size:14px;color:#818693;margin-top:0">单位：笔</p>
          <div
            ref="orderStatic"
            class="order-static"
          >
            <div class="item">
              <div
                id="my-number4"
                class="num"
              >{{ orderAccountTitleData.orderMaxNumber }}</div>
              <div class="label">订单最高值</div>
            </div>
            <div class="item">
              <div
                id="my-number5"
                class="num"
              >{{ orderAccountTitleData.orderAverageNumber }}</div>
              <div class="label">订单平均值</div>
            </div>
            <div class="item">
              <div
                id="my-number6"
                class="num"
              >{{ orderAccountTitleData.orderMinNumber }}</div>
              <div class="label">订单最低值</div>
            </div>
          </div>
          <div id="order-account-chart"></div>
        </el-card>

      </el-col>
      <el-col
        :span="12"
        style="padding-left:10px;padding-right:20px"
      >
        <el-card class="hots cover-card orderDistribute">
          <div class="header">
            <p style="margin-bottom:12px">
              订单分布
            </p>
            <div style="font-size:14px;color:#818693">
              {{ getMonthsBeforeSix() }}
            </div>
          </div>
          <p style="font-size:14px;color:#818693;margin-top:0">单位：笔</p>
          <div id="order-distribute-chart"></div>
        </el-card>
      </el-col>
    </el-row>
    <el-dialog
      :visible.sync="dialogVisible"
      width="800px"
      :before-close="handleClose"
      class="deparment"
    >
      <div
        class="close-btn"
        @click="handleClose"
      ></div>
      <img src="../../assets/department_map.png" />
    </el-dialog>
    <span class="gzt-remark">注：工作台展示数据为虚拟数据</span>
  </div>
</template>
<script>
import echarts from 'echarts'
import 'echarts-liquidfill'
import 'echarts/map/js/china.js'
import { dashboardData } from '@/api/common'

export default {
  name: 'Dashboard',
  data() {
    return {
      lineHeight: 49, // 每行的实际高度
      tableTimerInterval: 2000,
      tableTimer: null,
      tableTop: 0,
      tableListSize: 0,
      visibleSize: 5,
      notTaskDataTime: '',
      taskingDataTime: '',
      todayDataTime: '',
      dialogVisible: false,
      transportTaskListData: [],
      useFeatureList: [
        {
          url: require('../../assets/useFeature1.png'),
          label: '快递作业',
          link: '/branches/operational'
        },
        {
          url: require('../../assets/useFeature2.png'),
          label: '运输任务',
          link: '/transport/transport-task'
        },
        {
          url: require('../../assets/useFeature3.png'),
          label: '线路管理',
          link: '/transport/line-manage'
        },
        {
          url: require('../../assets/useFeature4.png'),
          label: '车辆管理',
          link: '/transit/vehicle'
        },
        {
          url: require('../../assets/useFeature5.png'),
          label: '司机管理',
          link: '/transit/driver'
        },
        {
          url: require('../../assets/useFeature6.png'),
          label: '运费查询',
          link: '/transit/freight-manage'
        }
      ],
      backlogListdata: [
        {
          value: 0,
          label: '待取件',
          url: '/business/order-manage?status=',
          status: 23000
        },
        {
          value: 0,
          label: '待派件',
          url: '/business/order-manage?status=',
          status: 23007
        },
        {
          value: 0,
          label: '未分配运输',
          url: '/transport/transport-task?status=',
          status: 1
        },
        {
          value: 0,
          label: '超时运输',
          url: '/transport/transport-task?status=',
          status: 5
        }
      ],
      backlogChartData: [
        {
          value: 0,
          num: 100
        },
        {
          value: 0,
          num: 100
        },
        {
          value: 0,
          num: 100
        },
        {
          value: 0,
          num: 100
        }
      ],
      taskingChartData: [
        {
          value: 0,
          num: 100
        },
        {
          value: 0,
          num: 100
        }
      ],
      taskingListData: [
        {
          value: 0,
          label: '运输中',
          url: '/business/order-manage?status=',
          status: 23005
        },
        {
          value: 0,
          label: '派送中',
          url: '/business/order-manage?status=',
          status: 23008
        }
      ],
      geoCoordMap: {
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
        韶关: [113.7964, 24.7028],
        台北: [121.5, 25.05]
      },
      // 机构概述
      organOverview: {
        organName: '',
        organAddress: '',
        principal: '',
        phone: '',
        sortingCenterNumber: '',
        agencyNumber: '',
        driverNumber: '',
        courierNumber: ''
      },
      // 今日数据
      todayData: {
        orderAmount: '',
        orderAmountChanges: '',
        orderNumber: '',
        orderNumberChanges: '',
        transportTaskNumber: '',
        transportTaskNumberChanges: ''
      },
      // 待办事项
      backlog: {
        waitingPickupNumber: '',
        waitingPickupRate: '',
        waitingDispatchNumber: '',
        waitingDispatchRate: '',
        unassignedTransportTaskNumber: '',
        unassignedTransportTaskRate: '',
        timeoutTransportTaskNumber: '',
        timeoutTransportTaskRate: ''
      },
      // 订单总量
      orderAccountTitleData: {
        orderMaxNumber: '',
        orderAverageNumber: '',
        orderMinNumber: ''
      },
      // 订单总量折线图Y轴数据
      orderAccountChartY: [],
      // 订单总量折线图X轴数据
      orderAccountChartX: [],
      isScrollNum: true
    }
  },
  computed: {},
  mounted() {
    this.todayDataTime = this.getCurrentTime()
    this.taskingDataTime = this.getCurrentTime()
    this.notTaskDataTime =
       this.getCurrentTime() + ' - ' + this.getAfterThreeDays()
    this.getPageData()
    document.querySelector('.dashboard-container').addEventListener('scroll', this.load)
  },
  methods: {
    load() {
      if (!this.isScrollNum) {
        return
      }
      if (!!document.querySelector('.dashboard-container').scrollTop &&
        document.querySelector('.dashboard-container').scrollTop > 600) {
        this.isScrollNum = false
        this.addNumber(
          0,
          this.orderAccountTitleData.orderMaxNumber,
          'my-number4',
          300
        )
        this.addNumber(
          0,
          this.orderAccountTitleData.orderAverageNumber,
          'my-number5',
          300
        )
        this.addNumber(
          0,
          this.orderAccountTitleData.orderMinNumber,
          'my-number6',
          300
        )
      }
    },
    // 根据列表长度是否超过可视范围内能够显示的最大完整数据条数，来控制列表是否需要滚动
    tableActionFun() {
      this.tableListSize = this.transportTaskListData.length
      this.transportTaskListData = this.notChangeTransportTaskListData.concat(this.notChangeTransportTaskListData)// 此处重新赋值避免出现transportTaskListData无限累加造成DOM过多导致页面崩溃
      // 下面的visibleSize是可视范围内能够显示的最大完整数据条数
      if (this.tableListSize > this.visibleSize) {
        this.transportTaskListData = this.transportTaskListData.concat(
          this.transportTaskListData
        )
        this.tableTimerFun() // 列表滚动方法
      } else {
        // this.fillTableList() // 无需滚动时的数据填充方法，如果没必要，可以删掉
      }
    },
    // 停止滚动
    handleStopScroll() {
      clearInterval(this.tableTimer)
    },
    // 开始滚动
    handleStartScroll() {
      this.tableActionFun()
    },
    // 列表滚动方法
    tableTimerFun() {
      var count = 0
      this.tableTimer = setInterval(() => {
        if (count < this.transportTaskListData.length / 2) {
          this.tableTop -= 50
          count++
        } else {
          count = 0
          this.tableTop = 0
        }
      }, this.tableTimerInterval)
    },
    // 关闭弹窗
    handleClose() {
      this.dialogVisible = false
    },
    // 展示营业部分布
    showDepartment() {
      this.dialogVisible = true
    },
    // 处理待办事项跳转
    handleBacklog(url, status) {
      this.$router.push({
        path: url + status
      })
    },
    // 获取当前月份之前6个月的时间节点
    getMonthsBeforeSix() {
      const date = new Date()
      const year = date.getFullYear()
      const month = date.getMonth()
      let newYear = 0
      let newMonth = 0
      const newDateArr = []
      for (let i = 0; i < 6; i++) {
        // 这里是获取前六个月,所以循环6次,根据需要修改
        if (month - i < 1) {
          // 这里的判断是如果当前月份往前推到了去年  需要做的处理
          newYear = year - 1
          newMonth =
            month - i + 12 >= 10 ? month - i + 12 : '0' + (month - i + 12)
          newDateArr.push(newYear + '-' + newMonth) // 这里拼接格式化,在时间中间加了个-,根据实际需求来
        } else {
          newMonth = month - i >= 10 ? month - i : '0' + (month - i) // 这里是对月份小于10做加前面加0处理
          newDateArr.push(year + '-' + newMonth)
        }
      }
      return newDateArr[5] + ' - ' + newDateArr[0]
    },
    // 获取前三天时间节点
    getAfterThreeDays() {
      const date = new Date(new Date().getTime() + 172800000) // 时间戳为10位需*1000，时间戳为13位的话不需乘1000
      const Y = date.getFullYear() + '-'
      const M =
        (date.getMonth() + 1 < 10
          ? '0' + (date.getMonth() + 1)
          : date.getMonth() + 1) + '-'
      const D =
        (date.getDate() < 10
          ? '0' + (date.getDate())
          : date.getDate()) + ' '
      return Y + M + D + '23' + ':' + '59'
    },
    // 待办任务刷新
    handleRefreshNotTaskDataTime() {
      this.notTaskDataTime =
         String(this.getCurrentTime()) +
        ' - ' +
        String(this.getAfterThreeDays())
    },
    // 今日数据刷新
    handleRefreshTodayDataTime() {
      this.todayDataTime = this.getCurrentTime()
    },
    // 执行中任务刷新
    handleRefreshTaskingDataTime() {
      this.taskingDataTime = this.getCurrentTime()
    },
    // 获取当前时间节点
    getCurrentTime() {
      var date = new Date() // 时间戳为10位需*1000，时间戳为13位的话不需乘1000
      var Y = date.getFullYear() + '-'
      var M =
        (date.getMonth() + 1 < 10
          ? '0' + (date.getMonth() + 1)
          : date.getMonth() + 1) + '-'
      var D =
        (date.getDate() < 10 ? '0' + date.getDate() : date.getDate()) + ' '

      var h =
        (date.getHours() < 10 ? '0' + date.getHours() : date.getHours()) + ':'
      var m =
        date.getMinutes() < 10 ? '0' + date.getMinutes() : date.getMinutes()
      return Y + M + D + h + m
    },
    // 获取页面数据
    getPageData() {
      dashboardData().then((res) => {
        this.organOverview = res.data.organOverview
        this.todayData = res.data.todayData
        this.addNumber(0, res.data.todayData.orderAmount, 'my-number1', 25)
        this.addNumber(0, res.data.todayData.orderNumber, 'my-number2', 2)
        this.addNumber(
          0,
          res.data.todayData.transportTaskNumber,
          'my-number3',
          1
        )

        const arr = [
          res.data.backlog.waitingPickupNumber,
          res.data.backlog.waitingDispatchNumber,
          res.data.backlog.unassignedTransportTaskNumber,
          res.data.backlog.timeoutTransportTaskNumber
        ]
        this.backlogListdata = this.backlogListdata.map((item, index) => {
          return Object.assign({}, item, {
            value: arr[index]
          })
        })
        const backlogChartArr = [
          res.data.backlog.waitingPickupRate,
          res.data.backlog.waitingDispatchRate,
          res.data.backlog.unassignedTransportTaskRate,
          res.data.backlog.timeoutTransportTaskRate
        ]
        this.backlogChartData = this.backlogChartData.map((item, index) => {
          return {
            value: backlogChartArr[index],
            num: item.num
          }
        })
        const taskingChartArr = [
          res.data.todayData.taskInTransitNumber,
          res.data.todayData.taskInDeliveryNumber
        ]
        this.taskingListData = this.taskingListData.map((item, index) => {
          return Object.assign({}, item, {
            value: taskingChartArr[index]
          })
        })
        const taskingChartArrBottom = [
          res.data.todayData.taskInTransitRate,
          res.data.todayData.taskInDeliveryRate
        ]
        this.taskingChartData = this.taskingChartData.map((item, index) => {
          return {
            value: taskingChartArrBottom[index],
            num: item.num
          }
        })
        this.transportTaskListData = res.data.transportTaskList
        this.notChangeTransportTaskListData = res.data.transportTaskList
        this.orderAccountTitleData = res.data.orderLineChart

        this.orderAccountChartY = res.data.orderLineChart.monthlyOrderList.map(
          (item) => {
            return item.dateTime
          }
        )
        this.orderAccountChartX = res.data.orderLineChart.monthlyOrderList.map(
          (item) => {
            return item.orderNumber
          }
        )
        this.$nextTick(() => {
          this.loadBacklog()
          this.loadTasking()
          this.loadLineManage()
          this.loadingOrderAccount()
          this.loadingOrderDistribute()
          this.tableActionFun()
        })
      })
    },
    // 跳转到运输任务列表
    handleToTransportTask() {
      this.$router.push({
        path: '/transport/transport-task'
      })
    },
    // 常用功能点击
    handleClick(link) {
      this.$router.push({
        path: link
      })
    },
    // 处理线路地图图表数据
    convertData(data) {
      var res = []

      for (var i = 0; i < data.length; i++) {
        var geoCoord = this.geoCoordMap[data[i].toName]
        var geoCoords = this.geoCoordMap[data[i].fromName]

        if (geoCoord) {
          res.push({
            name: data[i].toName,
            value: geoCoord.concat(data[i].value)
          })
        }
        if (geoCoords) {
          res.push({
            name: data[i].fromName,
            value: geoCoords.concat(data[i].value)
          })
        }
      }

      return res
    },
    // 加载订单分布图标
    loadingOrderDistribute() {
      const myChart = echarts.init(
        document.getElementById('order-distribute-chart')
      )
      const data = [
        { name: '广东', value: 2500 },
        { name: '山东', value: 2400 },
        { name: '江苏', value: 2200 },
        { name: '浙江', value: 2100 },
        { name: '河南', value: 1900 },
        { name: '四川', value: 1700 },
        { name: '湖北', value: 1600 },
        { name: '台湾', value: 1400 },
        { name: '福建', value: 1300 },
        { name: '湖南', value: 1200 },
        { name: '上海', value: 1050 },
        { name: '安徽', value: 950 },
        { name: '河北', value: 800 },
        { name: '北京', value: 700 },
        { name: '陕西', value: 600 },
        { name: '江西', value: 550 },
        { name: '重庆', value: 450 },
        { name: '辽宁', value: 400 },
        { name: '云南', value: 390 },
        { name: '广西', value: 380 },
        { name: '香港', value: 370 },
        { name: '山西', value: 350 },
        { name: '内蒙古', value: 320 },
        { name: '贵州', value: 300 },
        { name: '新疆', value: 280 },
        { name: '天津', value: 260 },
        { name: '黑龙江', value: 240 },
        { name: '吉林', value: 220 },
        { name: '甘肃', value: 200 },
        { name: '海南', value: 180 },
        { name: '宁夏', value: 160 },
        { name: '青海', value: 140 },
        { name: '西藏', value: 120 },
        { name: '澳门', value: 100 }
      ]
      data.sort(function (a, b) {
        return a.value - b.value
      })
      const mapOption = {
        geo: {
          universalTransition: true,
          silent: true,
          map: 'china',
          label: {
            emphasis: {
              show: true,
              color: '#fff'
            }
          },
          roam: false,
          //   放大我们的地图
          zoom: 1.25,
          itemStyle: {
            normal: {
              // areaColor: 'rgba(83,137,240, 0.42)',
              // borderColor: 'rgba(43, 196, 243, 1)',
              // 地域边框颜色
              borderColor: '#FDB892',
              borderWidth: 0
            },
            emphasis: {
              // areaColor: '#2B91B7'
              // hover悬停效果
              areaColor: '#ba586f'
            }
          },
          regions: [
            {
              name: '南海诸岛',
              value: 0,
              itemStyle: {
                normal: {
                  opacity: 0,
                  label: {
                    show: false
                  }
                }
              }
            },
            {
              name: '北京',
              itemStyle: {
                normal: {
                  areaColor: '#FFE2B4'
                }
              }
            },
            {
              name: '天津',
              itemStyle: {
                normal: {
                  areaColor: '#E15536'
                }
              }
            },
            {
              name: '上海',
              itemStyle: {
                normal: {
                  areaColor: '#E15536'
                }
              }
            },
            {
              name: '重庆',
              itemStyle: {
                normal: {
                  areaColor: '#FFE2B4'
                }
              }
            },
            {
              name: '河北',
              itemStyle: {
                normal: {
                  areaColor: '#FF9739'
                }
              }
            },
            {
              name: '河南',
              itemStyle: {
                normal: {
                  areaColor: '#FFE2B4'
                }
              }
            },
            {
              name: '云南',
              itemStyle: {
                normal: {
                  areaColor: '#FFC257'
                }
              }
            },
            {
              name: '辽宁',
              itemStyle: {
                normal: {
                  areaColor: '#E15536'
                }
              }
            },
            {
              name: '黑龙江',
              itemStyle: {
                normal: {
                  areaColor: '#E15536'
                }
              }
            },
            {
              name: '湖南',
              itemStyle: {
                normal: {
                  areaColor: '#FFC257'
                }
              }
            },
            {
              name: '安徽',
              itemStyle: {
                normal: {
                  areaColor: '#FFC257'
                }
              }
            },
            {
              name: '山东',
              itemStyle: {
                normal: {
                  areaColor: '#E15536'
                }
              }
            },
            {
              name: '新疆',
              itemStyle: {
                normal: {
                  areaColor: '#E15536'
                }
              }
            },
            {
              name: '江苏',
              itemStyle: {
                normal: {
                  areaColor: '#FF9739'
                }
              }
            },
            {
              name: '浙江',
              itemStyle: {
                normal: {
                  areaColor: '#FFE2B4'
                }
              }
            },
            {
              name: '江西',
              itemStyle: {
                normal: {
                  areaColor: '#E15536'
                }
              }
            },
            {
              name: '湖北',
              itemStyle: {
                normal: {
                  areaColor: '#FF9739'
                }
              }
            },
            {
              name: '广西',
              itemStyle: {
                normal: {
                  areaColor: '#E15536'
                }
              }
            },
            {
              name: '甘肃',
              itemStyle: {
                normal: {
                  areaColor: '#FF9739'
                }
              }
            },
            {
              name: '山西',
              itemStyle: {
                normal: {
                  areaColor: '#E15536'
                }
              }
            },
            {
              name: '内蒙古',
              itemStyle: {
                normal: {
                  areaColor: '#FFE2B4'
                }
              }
            },
            {
              name: '陕西',
              itemStyle: {
                normal: {
                  areaColor: '#FFC257'
                }
              }
            },
            {
              name: '吉林',
              itemStyle: {
                normal: {
                  areaColor: '#FF9739'
                }
              }
            },
            {
              name: '福建',
              itemStyle: {
                normal: {
                  areaColor: '#FF9739'
                }
              }
            },
            {
              name: '贵州',
              itemStyle: {
                normal: {
                  areaColor: '#FF9739'
                }
              }
            },
            {
              name: '广东',
              itemStyle: {
                normal: {
                  areaColor: '#FFE2B4'
                }
              }
            },
            {
              name: '青海',
              itemStyle: {
                normal: {
                  areaColor: '#FFC257'
                }
              }
            },
            {
              name: '西藏',
              itemStyle: {
                normal: {
                  areaColor: '#FFE2B4'
                }
              }
            },
            {
              name: '四川',
              itemStyle: {
                normal: {
                  areaColor: '#E15536'
                }
              }
            },
            {
              name: '宁夏',
              itemStyle: {
                normal: {
                  areaColor: '#E15536'
                }
              }
            },
            {
              name: '海南',
              itemStyle: {
                normal: {
                  areaColor: '#FFC257'
                }
              }
            },
            {
              name: '台湾',
              itemStyle: {
                normal: {
                  areaColor: '#FFC257'
                }
              }
            },
            {
              name: '香港',
              itemStyle: {
                normal: {
                  areaColor: '#E15536'
                }
              }
            },
            {
              name: '澳门',
              itemStyle: {
                normal: {
                  areaColor: '#E15536'
                }
              }
            }
          ]
        }
      }

      const barOption = {
        tooltip: {
          borderColor: '#EBEEF5',
          borderWidth: 1,
          padding: [5, 16, 5, 14],
          trigger: 'axis',
          backgroundColor: 'rgba(255,255,255,0.96)',
          formatter: (params) => {
            return (
              '<span style="color:#818693;font-size:12px;margin-right:25px;margin-bottom:4px;display:inline-block">省份：</span>' +
              '<span style="color:#20232A;font-size:12px;display:inline-block">' +
              params[0].name +
              '</span>' +
              '<br />' +
              ('<span style="color:#818693;font-size:12px;margin-bottom:4px;display:inline-block">订单总量：</span>' +
                '<span style="color:#20232A;font-size:12px;display:inline-block;float:right">' +
                params[0].data +
                '笔' +
                '</span>')
            )
          }
        },
        xAxis: {
          type: 'value',
          axisLine: {
            show: false
          },
          axisTick: {
            show: false
          },
          axisLabel: {
            margin: 17
          },
          splitLine: {
            lineStyle: {
              color: '#EBEEF5'
            }
          }
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '0%',
          height: '100%',
          containLabel: true
        },
        yAxis: {
          type: 'category',
          axisLabel: {
            // rotate: 30,
            interval: 0,
            fontSize: 11,
            color: '#20232A'
          },
          axisLine: {
            lineStyle: {
              color: '#EBEEF5'
            }
          },
          axisTick: {
            show: false
          },
          data: data.map(function (item) {
            return item.name
          })
        },
        animationDurationUpdate: 1000,
        series: {
          type: 'bar',
          id: 'population',
          data: data.map(function (item) {
            return item.value
          }),
          universalTransition: true,
          barWidth: 5,
          itemStyle: {
            color: '#E15536'
          }
        }
      }
      myChart.setOption(barOption)
    },
    // 加载订单总量折线图
    loadingOrderAccount() {
      const myChart = echarts.init(
        document.getElementById('order-account-chart')
      )
      const option = {
        tooltip: {
          borderColor: '#EBEEF5',
          borderWidth: 1,
          padding: [5, 16, 5, 14],
          trigger: 'axis',
          backgroundColor: 'rgba(255,255,255,0.96)',
          formatter: (params) => {
            return (
              '<span style="color:#818693;font-size:12px;margin-right:17px;margin-bottom:4px;display:inline-block">日期：</span>' +
              '<span style="color:#20232A;font-size:12px;display:inline-block">' +
              params[0].name +
              '</span>' +
              '<br />' +
              ('<span style="color:#818693;font-size:12px;margin-bottom:4px;display:inline-block">订单总量：</span>' +
                '<span style="color:#20232A;font-size:12px;display:inline-block;float:right">' +
                params[0].data +
                '笔' +
                '</span>')
            )
          }
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: {
          type: 'category',
          boundaryGap: true, // 坐标轴两边留白
          data: this.orderAccountChartY,
          axisLabel: {
            show: true,
            // 坐标轴刻度标签的相关设置。
            interval: 0, // 设置为 1，表示『隔一个标签显示一个标签』
            textStyle: {
              color: '#20232A',
              fontStyle: 'normal',
              fontFamily: '微软雅黑',
              fontSize: 12
            },
            margin: 17
          },
          axisTick: {
            // 坐标轴刻度相关设置。
            show: false
          },
          axisLine: {
            // 坐标轴轴线相关设置
            lineStyle: {
              color: '#E5E9ED'
              // opacity:0.2
            }
          },
          splitLine: {
            // 坐标轴在 grid 区域中的分隔线。
            show: false,
            lineStyle: {
              color: '#E5E9ED'
              // 	opacity:0.1
            }
          }
        },
        yAxis: [
          {
            type: 'value',
            splitNumber: 5,
            axisLabel: {
              textStyle: {
                color: '#a8aab0',
                fontStyle: 'normal',
                fontFamily: '微软雅黑',
                fontSize: 12
              }
            },
            axisLine: {
              show: false
            },
            axisTick: {
              show: false
            },
            splitLine: {
              show: true,
              lineStyle: {
                color: '#E5E9ED'
                // 	opacity:0.1
              }
            }
          }
        ],
        series: [
          {
            symbol: 'circle',
            symbolSize: 6,
            name: '2019',
            type: 'line',
            itemStyle: {
              normal: {
                color: '#E25433',
                lineStyle: {
                  color: '#E25433',
                  width: 1
                },
                areaStyle: {
                  color: new echarts.graphic.LinearGradient(0, 1, 0, 0, [
                    {
                      offset: 0,
                      color: 'rgba(225,85,54,0.21)'
                    },
                    {
                      offset: 1,
                      color: 'rgba(225,85,54,0.00)'
                    }
                  ])
                }
              }
            },
            data: this.orderAccountChartX
          }
        ]
      }
      myChart.setOption(option)
    },

    // 加载初始化线路地图图表
    loadLineManage() {
      // 初始化地图数据
      this.myChart3 = echarts.init(document.getElementById('line-manage-chart'))
      var data = [
        {
          toName: '西安',
          fromName: '乌鲁木齐',
          value: 100,
          name: {
            lineNumber: 'XL877654',
            lineName: '乌鲁木齐直达西安',
            lineType: '接驳路线',
            fromAgency: '乌鲁木齐分拣中心',
            toAgency: '西安分拣中心',
            distance: '2561千米',
            cost: '8368元',
            time: '1678分钟'
          }
        },
        {
          toName: '成都',
          fromName: '拉萨',
          value: 100,
          name: {
            lineNumber: 'XL877655',
            lineName: '拉萨直达成都',
            lineType: '接驳路线',
            fromAgency: '拉萨分拣中心',
            toAgency: '成都分拣中心',
            distance: '1991千米',
            cost: '8368元',
            time: '1678分钟'
          }
        },
        {
          toName: '北京',
          fromName: '西安',
          value: 100,
          name: {
            lineNumber: 'XL877655',
            lineName: '西安直达北京',
            lineType: '接驳路线',
            fromAgency: '西安分拣中心',
            toAgency: '北京分拣中心',
            distance: '1075千米',
            cost: '8368元',
            time: '1678分钟'
          }
        },
        {
          toName: '哈尔滨',
          fromName: '北京',
          value: 100,
          name: {
            lineNumber: 'XL877655',
            lineName: '北京直达哈尔滨',
            lineType: '接驳路线',
            fromAgency: '北京分拣中心',
            toAgency: '哈尔滨分拣中心',
            distance: '1233千米',
            cost: '8368元',
            time: '1678分钟'
          }
        },
        {
          toName: '西安',
          fromName: '成都',
          value: 100,
          name: {
            lineNumber: 'XL877655',
            lineName: '成都直达西安',
            lineType: '接驳路线',
            fromAgency: '成都分拣中心',
            toAgency: '西安分拣中心',
            distance: '797千米',
            cost: '8368元',
            time: '1678分钟'
          }
        },
        {
          toName: '西安',
          fromName: '海口',
          value: 100,
          name: {
            lineNumber: 'XL877655',
            lineName: '海口直达西安',
            lineType: '接驳路线',
            fromAgency: '海口分拣中心',
            toAgency: '西安分拣中心',
            distance: '2069千米',
            cost: '8368元',
            time: '1678分钟'
          }
        },
        {
          toName: '北京',
          fromName: '海口',
          value: 100,
          name: {
            lineNumber: 'XL877655',
            lineName: '海口直达北京',
            lineType: '接驳路线',
            fromAgency: '海口分拣中心',
            toAgency: '北京分拣中心',
            distance: '2598千米',
            cost: '8368元',
            time: '1678分钟'
          }
        },
        {
          toName: '上海',
          fromName: '海口',
          value: 100,
          name: {
            lineNumber: 'XL877655',
            lineName: '海口直达上海',
            lineType: '接驳路线',
            fromAgency: '海口分拣中心',
            toAgency: '上海分拣中心',
            distance: '1987千米',
            cost: '8368元',
            time: '1678分钟'
          }
        },
        {
          toName: '上海',
          fromName: '北京',
          value: 100,
          name: {
            lineNumber: 'XL877655',
            lineName: '北京直达上海',
            lineType: '接驳路线',
            fromAgency: '北京分拣中心',
            toAgency: '上海分拣中心',
            distance: '1210千米',
            cost: '8368元',
            time: '1678分钟'
          }
        },
        {
          toName: '台北',
          fromName: '上海',
          value: 100,
          name: {
            lineNumber: 'XL877655',
            lineName: '上海直达台北',
            lineType: '接驳路线',
            fromAgency: '上海分拣中心',
            toAgency: '台北分拣中心',
            distance: '1210千米',
            cost: '8368元',
            time: '1678分钟'
          }
        }
      ]
      const series = [
        {
          name: data[0].fromName + ' Top3',
          type: 'lines',
          zlevel: 1,
          effect: {
            show: true,
            period: 6,
            trailLength: 0,
            // arrow箭头的颜色
            color: '#E15536',
            symbolSize: 10,
            symbol: 'arrow'
          },
          lineStyle: {
            normal: {
              color: '#E15536',
              width: 3,
              curveness: 0.2,
              type: 'dashed'
            }
          },
          data: data.map((dataItem) => {
            return {
              fromName: dataItem.fromName,
              toName: dataItem.toName,
              coords: [
                this.geoCoordMap[dataItem.fromName],
                this.geoCoordMap[dataItem.toName]
              ],
              name: dataItem.name
            }
          })
        },
        {
          cursor: 'auto',
          name: data[0].fromName + ' Top3',
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
          // 图标上的点的大小
          symbolSize: (val) => {
            return val[2] / 10
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
                    color: '#E15536' // 0% 处的颜色
                  },
                  {
                    offset: 1,
                    color: '#E15536' // 100% 处的颜色
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
          data: this.convertData(data)
        }
      ]

      const option = {
        tooltip: {
          position: (point) => {
            return ['0%', '35%']
          },
          borderColor: '#EBEEF5',
          borderWidth: 1,
          padding: [15, 18, 15, 18],
          trigger: 'item',
          backgroundColor: 'rgba(255,255,255,0.96)',
          formatter: (params, ticket, callback) => {
            const {
              name: {
                lineNumber,
                lineName,
                lineType,
                fromAgency,
                toAgency,
                distance,
                cost,
                time
              }
            } = params.data
            if (params.seriesType === 'effectScatter') {
              return
            } else if (params.seriesType === 'lines') {
              return (
                '<span style="color:#20232A;font-weight:bold;margin-bottom:18px;display:inline-block">线路信息</span>' +
                '<br />' +
                ('<span style="color:#818693;font-size:12px;margin-right:100px;margin-bottom:4px;display:inline-block">线路编号：</span>' +
                  '<span style="color:#20232A;font-size:12px;display:inline-block">' +
                  lineNumber +
                  '</span>') +
                '<br />' +
                ('<span style="color:#818693;font-size:12px;margin-right:100px;margin-bottom:4px;display:inline-block">线路名称：</span>' +
                  '<span style="color:#20232A;font-size:12px">' +
                  lineName +
                  '</span>') +
                '<br />' +
                ('<span style="color:#818693;font-size:12px;margin-right:100px;margin-bottom:4px;display:inline-block">线路类型：</span>' +
                  '<span style="color:#20232A;font-size:12px">' +
                  lineType +
                  '</span>') +
                '<br />' +
                ('<span style="color:#818693;font-size:12px;margin-right:88px;margin-bottom:4px;display:inline-block">起始地机构：</span>' +
                  '<span style="color:#20232A;font-size:12px">' +
                  fromAgency +
                  '</span>') +
                '<br />' +
                ('<span style="color:#818693;font-size:12px;margin-right:88px;margin-bottom:4px;display:inline-block">目的地机构：</span>' +
                  '<span style="color:#20232A;font-size:12px">' +
                  toAgency +
                  '</span>') +
                '<br />' +
                  ('<span style="color:#818693;font-size:12px;margin-right:123px;margin-bottom:4px;display:inline-block">距离：</span>' +
                  '<span style="color:#20232A;font-size:12px">' +
                  distance +
                  '</span>') +
                '<br />' +
                ('<span style="color:#818693;font-size:12px;margin-right:100px;margin-bottom:4px;display:inline-block">平均成本：</span>' +
                  '<span style="color:#20232A;font-size:12px">' +
                  cost +
                  '</span>') +
                '<br />' +
                ('<span style="color:#818693;font-size:12px;margin-right:100px;margin-bottom:4px;display:inline-block">预计时间：</span>' +
                  '<span style="color:#20232A;font-size:12px">' +
                  time +
                  '</span>')
              )
            } else {
              return params.name
            }
          }
        },
        geo: {
          silent: true,
          map: 'china',
          label: {
            emphasis: {
              show: true,
              color: '#fff'
            }
          },
          roam: false,
          //   放大我们的地图
          zoom: 1.25,
          itemStyle: {
            normal: {
              // 地域边框颜色
              borderColor: '#FDB892',
              borderWidth: 0
            },
            emphasis: {
              areaColor: '#ba586f'
            }
          },
          regions: [
            {
              name: '南海诸岛',
              value: 0,
              itemStyle: {
                normal: {
                  opacity: 0,
                  label: {
                    show: false
                  }
                }
              }
            },
            {
              name: '北京',
              itemStyle: {
                normal: {
                  areaColor: '#FFE4B5'
                }
              }
            },
            {
              name: '天津',
              itemStyle: {
                normal: {
                  areaColor: '#ECDC7E'
                }
              }
            },
            {
              name: '上海',
              itemStyle: {
                normal: {
                  areaColor: '#ECDC7E'
                }
              }
            },
            {
              name: '重庆',
              itemStyle: {
                normal: {
                  areaColor: '#FFE4B5'
                }
              }
            },
            {
              name: '河北',
              itemStyle: {
                normal: {
                  areaColor: '#FF9739'
                }
              }
            },
            {
              name: '河南',
              itemStyle: {
                normal: {
                  areaColor: '#FFE4B5'
                }
              }
            },
            {
              name: '云南',
              itemStyle: {
                normal: {
                  areaColor: '#FFC257'
                }
              }
            },
            {
              name: '辽宁',
              itemStyle: {
                normal: {
                  areaColor: '#ECDC7E'
                }
              }
            },
            {
              name: '黑龙江',
              itemStyle: {
                normal: {
                  areaColor: '#ECDC7E'
                }
              }
            },
            {
              name: '湖南',
              itemStyle: {
                normal: {
                  areaColor: '#FFC257'
                }
              }
            },
            {
              name: '安徽',
              itemStyle: {
                normal: {
                  areaColor: '#FFC257'
                }
              }
            },
            {
              name: '山东',
              itemStyle: {
                normal: {
                  areaColor: '#ECDC7E'
                }
              }
            },
            {
              name: '新疆',
              itemStyle: {
                normal: {
                  areaColor: '#ECDC7E'
                }
              }
            },
            {
              name: '江苏',
              itemStyle: {
                normal: {
                  areaColor: '#FF9739'
                }
              }
            },
            {
              name: '浙江',
              itemStyle: {
                normal: {
                  areaColor: '#FFE4B5'
                }
              }
            },
            {
              name: '江西',
              itemStyle: {
                normal: {
                  areaColor: '#ECDC7E'
                }
              }
            },
            {
              name: '湖北',
              itemStyle: {
                normal: {
                  areaColor: '#FF9739'
                }
              }
            },
            {
              name: '广西',
              itemStyle: {
                normal: {
                  areaColor: '#ECDC7E'
                }
              }
            },
            {
              name: '甘肃',
              itemStyle: {
                normal: {
                  areaColor: '#FF9739'
                }
              }
            },
            {
              name: '山西',
              itemStyle: {
                normal: {
                  areaColor: '#ECDC7E'
                }
              }
            },
            {
              name: '内蒙古',
              itemStyle: {
                normal: {
                  areaColor: '#FFE4B5'
                }
              }
            },
            {
              name: '陕西',
              itemStyle: {
                normal: {
                  areaColor: '#FFC257'
                }
              }
            },
            {
              name: '吉林',
              itemStyle: {
                normal: {
                  areaColor: '#FF9739'
                }
              }
            },
            {
              name: '福建',
              itemStyle: {
                normal: {
                  areaColor: '#FF9739'
                }
              }
            },
            {
              name: '贵州',
              itemStyle: {
                normal: {
                  areaColor: '#FF9739'
                }
              }
            },
            {
              name: '广东',
              itemStyle: {
                normal: {
                  areaColor: '#FFE4B5'
                }
              }
            },
            {
              name: '青海',
              itemStyle: {
                normal: {
                  areaColor: '#FFC257'
                }
              }
            },
            {
              name: '西藏',
              itemStyle: {
                normal: {
                  areaColor: '#FFE4B5'
                }
              }
            },
            {
              name: '四川',
              itemStyle: {
                normal: {
                  areaColor: '#ECDC7E'
                }
              }
            },
            {
              name: '宁夏',
              itemStyle: {
                normal: {
                  areaColor: '#ECDC7E'
                }
              }
            },
            {
              name: '海南',
              itemStyle: {
                normal: {
                  areaColor: '#FFC257'
                }
              }
            },
            {
              name: '台湾',
              itemStyle: {
                normal: {
                  areaColor: '#FFC257'
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
    // 加载初始化执行中任务图表
    loadTasking() {
      const myChart = echarts.init(document.getElementById('order-fenbu-chart'))
      const ColorList = ['#E15536', '#FFC257'] // 链群颜色值
      const LabelColorList = ['#20232A', '#20232A'] // 文本字体颜色
      const OtherColorList = ['#FFE5E0', '#FFF1D9'] // 波浪其他地方填充色
      var titleData = []
      var series = []
      this.taskingChartData.forEach((item, index) => {
        const color = ColorList[index]
        const labelColor = LabelColorList[index]
        const otherColer = OtherColorList[index]
        series.push({
          cursor: 'auto',
          type: 'liquidFill',
          name: item.name,
          radius: '60%',
          center: [`${48 * (index + 0.5)}%`, '45%'],
          data: [
            {
              // NOTE 为了扩大鼠标悬浮热区 tooltip展示
              value: 1,
              itemStyle: {
                color: 'transparent'
              }
            },
            item.value / 100
          ],
          backgroundStyle: {
            color: [otherColer]
          },
          color: [color],
          itemStyle: {
            shadowColor: 'transparent'
          },
          outline: {
            borderDistance: 5,
            itemStyle: {
              borderWidth: 1,
              borderColor: color
            }
          },
          label: {
            position: ['50%', '50%'],
            formatter: function () {
              return `${item.value}%`
            },
            fontSize: 16,
            color: [labelColor],
            insideColor: [labelColor],
            fontWeight: 'normal'
          }
        })
      })
      var option = {
        title: titleData,
        series: series,
        backgroundColor: 'white',
        tooltip: {
          show: false
        }
      }
      myChart.setOption(option)
      window.addEventListener('resize', function () {
        myChart.resize()
      })
    },
    // 加载初始化待办事项图表
    loadBacklog() {
      const myChart = echarts.init(document.getElementById('order-total-chart'))
      const ColorList = ['#E15536', '#FFC257', '#FF9739', '#ECDC7E'] // 链群颜色值
      const LabelColorList = ['#20232A', '#20232A', '#20232A', '#20232A'] // 文本字体颜色
      const OtherColorList = ['#FFE5E0', '#FFF1D9', '#FFEAD7', '#FAFADE'] // 波浪其他地方填充色
      var titleData = []
      var series = []
      this.backlogChartData.forEach((item, index) => {
        const color = ColorList[index]
        const labelColor = LabelColorList[index]
        const otherColer = OtherColorList[index]
        series.push({
          type: 'liquidFill',
          name: item.name,
          radius: '60%',
          center: [`${25 * (index + 0.5)}%`, '45%'],
          data: [
            {
              // NOTE 为了扩大鼠标悬浮热区 tooltip展示
              value: 1,
              itemStyle: {
                color: 'transparent'
              }
            },
            item.value / 100
          ],
          backgroundStyle: {
            color: [otherColer]
          },
          color: [color],
          itemStyle: {
            shadowColor: 'transparent'
          },
          outline: {
            borderDistance: 5,
            itemStyle: {
              borderWidth: 1,
              borderColor: color
            }
          },
          label: {
            position: ['50%', '50%'],
            formatter: function () {
              return `${item.value}%`
            },
            fontSize: 16,
            color: [labelColor],
            insideColor: [labelColor],
            fontWeight: 'normal'
          }
        })
      })
      var option = {
        title: titleData,
        series: series,
        backgroundColor: 'white'
      }
      myChart.setOption(option)
      window.addEventListener('resize', function () {
        myChart.resize()
      })
    },
    // 动态实现数字上升
    addNumber(start, end, id, interval) {
      var o = document.getElementById(id)
      var i = start
      var Interval
      if (i < end) {
        Interval = setInterval(function () {
          i += interval // 设置每次增加的动态数字，可调整
          if (i > end) {
            clearInterval(Interval) // 清除setInterval的time，这个方法w3c可具体查看文档
            o.innerHTML = end.toLocaleString() // 此赋值是为了避免最后一次增加过后的数据和真实数据不同
            i = 0
          } else {
            o.innerHTML = i.toLocaleString()
          }
        }, 10) // 数据跳转的速度控制
      }
    }
  }
}
</script>
<style rel="stylesheet/scss" lang="scss" scoped>
/deep/ .gzt-remark {
  font-size: 13px;
  color: #818693;
  float: right;
  margin-right: 7px;
}
/deep/ .el-card {
  box-shadow: none;
}
// 头部样式汇总
.net-left {
  width: 100%;
  height: 100%;
  position: relative;
  .network-div {
    float: left;
    margin-left: -10px;
    img {
      vertical-align: middle;
    }
  }
  .div-right {
    position: absolute;
    left: 15%;
    top: 50%;
    transform: translateY(-50%);
    .details-div {
      color: #999999;
      font-size: 14px;
      margin-top: 5px;
    }
  }
}
.org-center-div,
.sys-div {
  margin: 10px;
  float: left;
  .first-div {
    text-align: center;
    font-size: 18px;
    font-family: PingFang SC;
    font-weight: bold;
    color: rgba(54, 129, 255, 1);
  }
  .second-div {
    margin-top: 10px;
    font-size: 14px;
    font-family: PingFang SC;
    font-weight: 400;
    color: rgba(153, 153, 153, 1);
  }
}
// 右边部分
.card-right,
.center {
  width: 100%;
  height: 76px;
  position: relative;
  .card-img,
  .center-img {
    img {
      vertical-align: middle;
      position: absolute;
      top: 10%;
    }
  }
  .card-content,
  .center-word {
    position: absolute;
    left: 20%;
    top: 10%;
    // transform: translateY(-50%);
    .card-left,
    .word-num {
      font-size: 18px;
      font-family: PingFang SC;
      font-weight: bold;
      color: rgba(240, 75, 48, 1);
    }
    .card-right,
    .word-content {
      margin-top: 10px;
      font-size: 14px;
      font-family: PingFang SC;
      font-weight: 400;
      color: rgba(153, 153, 153, 1);
    }
  }
}

.chart-content {
  width: 100%;
  height: 100%;
  display: flex;
  // overflow: auto;
  .chart-left {
    // height: 300px;
    flex: 1;
    margin-right: 10px;
    overflow: auto;
    .ul-div {
      ul {
        margin: 0;
        list-style: none;
        display: flex;
        li {
          flex: 1;
          background-color: #f6f7fb;
          p {
            text-align: left;
            padding-left: 20px;
          }
          .click-p {
            color: rgba(240, 75, 48, 1);
            font-size: 16px;
            font-weight: bold;
          }
          .click-text {
            font-size: 14px;
            font-family: PingFang SC;
            font-weight: 400;
            color: rgba(51, 51, 51, 1);
          }
          .text-color {
            color: rgba(28, 119, 255, 1);
          }
          .text-p {
            font-size: 12px;
            font-family: PingFang SC;
            font-weight: 400;
            color: rgba(51, 51, 51, 1);
          }
        }
        .click-li {
          background: rgba(255, 255, 255, 1);
          // box-shadow:0px 0px 16px 0px rgba(5,13,43,0.09);
          // opacity:0.46;
          border-radius: 10px;
          cursor: pointer;
        }
      }
    }
  }
  .chart-right {
    flex: 1;
    margin-left: 10px;
    span {
      // display: inline-block;
      // text-align: left;
      // width: 100%;
      color: #97a8be;
      font-size: 14px;
    }
  }
}
// 订单总量样式覆盖
.cover-card /deep/ .el-card__header {
  padding: 18px 20px;
  border-bottom: none;
  -webkit-box-sizing: border-box;
  box-sizing: border-box;
}
.right-info /deep/ .el-card__body {
  // height: 76px;
}

.dashboard-container {
  margin: 10px;
  margin-top: 0px;
  overflow-x: hidden;
  // overflow-y: scroll;
  height: calc( 100vh - 100px );

}
  ::-webkit-scrollbar{
    width: 1px;
  }
#order-total-chart {
  height: 208px;
  margin: 0 auto;
}
/deep/ #order-total-chart>div{
  cursor: default!important;
}
/deep/ #order-fenbu-chart>div{
  cursor: default!important;
}
#order-fenbu-chart {
  height: 208px;
  margin: 0 auto;
}
#chart_example2 {
  // width: 840px;
  transition: all 2s ease-in;
  height: 254px;
  margin-top: 15px;
}
#chart_example3 {
  // width: 100%;
  transition: all 2s ease-in;
  height: 254px;
  margin-top: 15px;
}
#chart_example4 {
  // width: 840px;
  transition: all 2s ease-in;
  height: 254px;
  margin-top: 15px;
}
.fade-enter-active,
.fade-leave-active {
  transition: 5s;
  opacity: 0;
}
.fade-enter,
.fade-leave-active {
  opacity: 1;
}
.box-card {
  // padding: 5px 10px;
  p {
    font-size: 16px;
    font-weight: bold;
  }

  // 总销售额
  .total {
    font-size: 30px;
    height: 140px;
    line-height: 100px;
  }
  .trends {
    height: 22px;
    font-size: 14px;
    span {
      display: inline;
      i {
        color: red;
      }
    }
    span:last-child {
      margin-left: 20px;
      display: inline;
      i {
        color: green;
      }
    }
  }
  .chart {
    height: 160px;
  }
  .hr {
    border-top: 1px solid #e8e8e8;
    margin: 0px 0px 10px 0px;
  }
  .footer {
    span {
      font-size: 14px;
      line-height: 22px;
    }
    span:last-child {
      margin-left: 8px;
      color: rgba(0, 0, 0, 0.85);
    }
  }
  // 总销售额 end
}
// 销售额
.sales-card {
  position: relative;
  .header {
    position: absolute;
    right: 20px;
    top: 15px;
    z-index: 1;
  }
  .chart {
    widows: 100%;
    height: 300px;
  }
  .table {
    color: rgba(0, 0, 0, 0.65);
    h4 {
      color: #000;
      font-weight: 500;
    }
    ul {
      list-style: none;
      margin: 0px;
      padding: 0px;
      .row {
        margin-bottom: 10px;
      }
    }
    .circular {
      width: 20px;
      height: 20px;
      background-color: #314659;
      color: #fff;
      text-align: center;
      font-size: 12px;
      line-height: 20px;
      font-weight: 600;
      border-radius: 50%;
    }
    .light {
      background-color: #f5f5f5;
      color: rgba(0, 0, 0, 0.65);
    }
  }
}

// 线上热门搜索
.hots {
  height: 293px;
  position: relative;
  .pagination {
    position: absolute;
    right: 12px;
    bottom: 5px;
  }
  .chart {
    height: 300px;
  }
}

// 周搜索
.month-card {
  height: 400px;
  .chart {
    height: 300px;
  }
}

//机构概述
.institution {
  .content {
    position: relative;
    display: flex;
    .left-content {
      padding-left: 10px;
      flex: 50%;
      border-right: 1px solid #ebeef5;
      .name {
        font-size: 16px;
        margin-top: 20px;
        margin-bottom: 13px;
      }
      .address {
        margin-bottom: 8px;
      }
      .duty-people {
        margin-bottom: 23px;
      }
      .address,
      .duty-people {
        font-size: 14px;
        color: #818693;
      }
      .search-sales-department {
        font-size: 14px;
        color: #e15536;
        width: 158px;
        height: 40px;
        background: #ffeeeb;
        border: 1px solid #f3917c;
        border-radius: 4px;
        display: flex;
        justify-content: center;
        align-items: center;
        cursor: pointer;
        .btn {
          display: flex;
          justify-content: center;
          align-items: center;
        }
        .btn::before {
          content: '';
          width: 20px;
          height: 20px;
          display: block;
          background-image: url('../../assets/position.png');
          background-repeat: no-repeat;
          background-size: contain;
        }
      }
    }
    .right-content {
      display: flex;
      flex: 50%;
      width: 300px;
      flex-wrap: wrap;
      .item {
        flex: 50%;
        flex-direction: column;
        align-items: center;
        display: flex;
        .label {
          font-size: 14px;
          // font-weight: bold;
          margin-bottom: 10px;
        }
        .num {
          font-size: 32px;
          font-weight: bold;
          color: #e15536;
          cursor: pointer;
        }
      }
      .item > div {
      }
      .item:nth-child(2) {
        padding-right: 25px;
      }
      .item:nth-child(1),
      .item:nth-child(2) {
        height: 85px;
      }
    }
    .line {
      position: absolute;
      height: 156px;
      width: 1px;
      background-color: #ebeef5;
      left: 330px;
    }
  }
}
/deep/ .header {
  display: flex;
  justify-content: space-between;
  align-items: baseline;
  p {
    margin-top: 0px;
    font-size: 16px;
    color: #20232a;
    font-weight: bold;
  }
}
//今日数据
.right-info {
  .el-card__body {
    padding: 20px !important;
  }

  .row-bg {
    margin-top: 16px;
    padding-left: 10px;
    .el-col {
      display: flex;
      flex-direction: column;
      // padding-left: 5%;
      // align-items: center;
      text-align: center;
    }
    .label {
      font-size: 14px;
      color: #20232a;
      margin-bottom: 21px;
    }
    .value {
      font-size: 36px;
      color: #20232a;
      font-weight: bold;
      margin-bottom: 24px;
    }
    .bottom {
      font-size: 14px;
      color: #818693;
      margin-bottom: 20px;
      display: flex;
      justify-content: center;
      padding-left: 9px;
    }
    .bottom::after {
      content: '';
      display: inline-block;
      width: 15px;
      height: 15px;
      background-image: url('../../assets/rise.png');
      background-repeat: no-repeat;
      background-size: contain;
    }
    .bottom.active::after {
      background-image: url('../../assets/down.png');
    }
  }
}

//刷新部分
.refresh-box {
  font-size: 14px;
  color: #818693;
  display: flex;
  align-items: center;
  cursor: pointer;
}
.refresh-box::before {
  content: '';
  display: inline-block;
  width: 15px;
  height: 14px;
  background-image: url('../../assets/refresh.png');
  background-repeat: no-repeat;
  background-size: contain;
  margin-right: 8px;
  // cursor: pointer;
}

//待办事项
.notTasking{
  .bottom-label{
    justify-content: flex-start;
    .item{
      flex: 25%;
      text-align: center;
    }
  }
}
.backlog {
  img {
    width: 20px;
    height: 20px;
  }
  .bottom-label {
    cursor: pointer;
    position: absolute;
    top: 235px;
    display: flex;
    width: 100%;
    justify-content: space-around;
    padding-right: 40px;
    .item span:nth-child(1),
    .item span:nth-child(2) {
      font-weight: bold;
    }
    .item:nth-child(1) {
      span:nth-child(2) {
        color: #e15536;
      }
    }
    .item:nth-child(2) {
      span:nth-child(2) {
        color: #ffc257;
      }
    }
    .item:nth-child(3) {
      span:nth-child(2) {
        color: #ff9739;
      }
    }
    .item:nth-child(4) {
      span:nth-child(2) {
        color: #DFCB59;
      }
    }
  }
}
.tasking {
  .bottom-label {
    padding-right: 55px;
  }
}
//常用功能
.useFeature {
  height: 193px;
  /deep/ .el-card__body {
    padding: 20px !important;
  }
  .useFeaturelist {
    display: flex;
    justify-content: space-between;

    .item {
      width: 15.15%;
      height: 116px;
      background: #fafafb;
      border-radius: 10px;
      cursor: pointer;

      img {
        width: 40px;
        height: 40px;
        margin: 0 auto;
        display: block;
        margin-top: 20px;
      }
      p {
        font-size: 16px;
        color: #20232a;
        font-weight: bold;
        text-align: center;
      }
    }
    .item:hover{
      p{
        color:#e15536 ;
      }
    }
  }
}
/deep/ .el-card__body {
  padding: 23px 20px;
}
//线路管理
.lineManage {
  height: 480px;
  #line-manage-chart {
    height: 400px;
  }
}
//运输任务
.transport-task {
  height: 480px;
  .search-more {
    font-size: 14px;
    color: #818693;
    display: flex;
    align-items: center;
    cursor: pointer;
  }
  .search-more::after {
    content: '';
    display: inline-block;
    width: 12px;
    height: 12px;
    background-image: url('../../assets/arrow-more.png');
    background-repeat: no-repeat;
    background-size: contain;
    margin-left: 8px;
  }
  .scroll-view {
    position: absolute;
  }
  .el-table {
    margin-top: 6px;
  }
  /deep/ td.id {
    .cell {
      -webkit-line-clamp: 1;
    }
  }
  /deep/ td {
    padding: 13px 0;
  }
  .table_body {
    width: 100%;
  }
  .table_th {
    width: 100%;
    display: flex;
    height: 50px;
    line-height: 50px;
    border: 1px solid #ebeef5;
    border-bottom: none;
    background-color: #f9fafe;
  }
  .tr {
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    box-sizing: border-box;
    padding: 0 5px;
    text-align: center;
    font-size: 14px;
    color: black;
  }
  .tr1 {
    width: 20%;
  }
  .tr2 {
    width: 20%;
  }
  .tr3 {
    width: 20%;
    font-size: 13px;
  }

  .tr4 {
    flex: 1;
  }

  .th_style {
    font-weight: bold;
    font-size: 18px;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    box-sizing: border-box;
    padding: 0 5px;
    text-align: center;
    color: #818693;
    font-size: 14px;
  }
  .table_main_body {
    width: 100%;
    height: 351px;
    overflow: hidden;
    position: relative;
  }
  .table_inner_body {
    width: 100%;
    position: absolute;
    left: 0;

  }
  .startTransition{
    transition: all 0.5s;
  }
  .table_tr:nth-child(odd) {
    background-color: #ffffff;
  }
  .table_tr:nth-child(even) {
    background-color: #fafafa;
  }
  .table_tr:hover{
    background-color: rgb(245, 247, 250);
  }
  .table_tr {
    display: flex;
    height: 50px;
    line-height: 50px;
    color: #eee;
    font-size: 15px;
    cursor: pointer;
    border: 1px solid #ebeef5;
    border-bottom: none;
    .tr1 {
      padding-left: 20px;
    }
  }
  .table_tr:last-child {
    border-bottom: 1px solid #ebeef5;
  }
}

//订单总量
.orderAccount {
  height: 595px;
  .order-static {
    display: flex;
    justify-content: space-evenly;
    margin-top: 38px;
    .item {
      .num {
        font-size: 28px;
        color: #e15536;
        font-weight: bold;
        text-align: center;
        margin-bottom: 5px;
      }
      .label {
        font-size: 12px;
        color: #818693;
        text-align: center;
      }
    }
  }
  #order-account-chart {
    height: 400px;
  }
}
@media screen and (min-width: 1920px) {
  .col {
    width: 33%;
  }
}

//订单分布
.orderDistribute {
  height: 595px;
  #order-distribute-chart {
    height: 480px;
  }
}
//查看营业部分布
/deep/ .deparment {
  .el-dialog {
    border-radius: 0px;
    height: 600px;
  }
  .el-dialog__header {
    display: none;
  }
  img {
    width: 800px;
    height: 600px;
  }
  .el-dialog__body {
    padding: 0px;
    position: relative;
  }
  .close-btn {
    width: 40px;
    height: 40px;
    background-image: url('../../assets/department-close-btn.png');
    background-repeat: no-repeat;
    background-size: contain;
    position: absolute;
    right: 18px;
    top: 15px;
    cursor: pointer;
  }
}
</style>
