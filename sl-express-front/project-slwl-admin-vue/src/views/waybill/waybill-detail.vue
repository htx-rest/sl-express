<template>
  <div class="dashboard-container waybill-detail">
    <div class="app-container">
      <el-card
        shadow="never"
        class="order-box"
      >
        <el-row
          :gutter="20"
          justify="center"
        >
          <el-col :span="6">
            <div class="ddbh">
              订单编号：
              <label>{{ waybillDetailShow.orderId }}</label>
            </div>
          </el-col>
          <el-col :span="6">
            <div class="ydbh">
              运单编号：
              <label>{{ waybillDetailShow.ydId }}</label>
            </div>
          </el-col>
          <el-col :span="6">
            <div class="xdsj">
              下单时间：
              <label>{{ waybillDetailShow.createTime }}</label>
            </div>
          </el-col>
          <el-col :span="6">
            <div class="ddzt">
              订单状态：
              <label v-if="waybillDetailShow.status == ' 23000'">待取件</label>
              <label
                v-else-if="waybillDetailShow.status == '23001'"
              >已取件</label>
              <label
                v-else-if="waybillDetailShow.status == '23003'"
              >网点入库</label>
              <label
                v-else-if="waybillDetailShow.status == '23004'"
              >待装车</label>
              <label
                v-else-if="waybillDetailShow.status == '23005'"
              >运输中</label>
              <label
                v-else-if="waybillDetailShow.status == '23006'"
              >网点出库</label>
              <label
                v-else-if="waybillDetailShow.status == '23007'"
              >待派送</label>
              <label
                v-else-if="waybillDetailShow.status == '23008'"
              >派送中</label>
              <label
                v-else-if="waybillDetailShow.status == '23009'"
              >已签收</label>
              <label
                v-else-if="waybillDetailShow.status == '23010'"
              >拒收</label>
              <label v-else>已取消</label>
            </div>
          </el-col>
          <el-col :span="6">
            <div class="yjddrq">
              预计到达日期：
              <label>{{ waybillDetailShow.estimatedArrivalTime }}</label>
            </div>
          </el-col>
        </el-row>
      </el-card>
      <el-collapse
        v-model="activeNames"
        style="margin-top: 20px; border: none"
      >
        <el-collapse-item name="1">
          <template slot="title">
            <div class="collapse-detail">基本信息</div>
          </template>
          <div class="block">
            <el-timeline>
              <div class="line"></div>
              <div
                class="pake-info"
                style="margin-top: 10px"
              >
                <img
                  class="img-info1"
                  src="../../assets/fahuo.png"
                  alt=""
                />
                <span>发货方</span>
              </div>
              <div style="margin-top: 0px; margin-left: 40px; display: flex">
                <div style="">
                  <div class="demo-input-suffix base-info">
                    发货方姓名：
                    <span>{{ waybillDetailShow.senderName }}</span>
                  </div>

                  <div class="demo-input-suffix base-info">
                    发货方地址：
                    <span>{{
                      waybillDetailShow.senderProvince +
                        waybillDetailShow.senderCity +
                        waybillDetailShow.senderCounty
                    }}</span>
                  </div>
                </div>
                <div style="margin-left: 174px">
                  <div class="demo-input-suffix base-info">
                    发货方电话：

                    <span>{{ waybillDetailShow.senderPhone }}</span>
                  </div>
                  <div class="demo-input-suffix base-info">
                    &nbsp;&nbsp;&nbsp;详细地址：

                    <span>{{ waybillDetailShow.senderAddress }}</span>
                  </div>
                </div>
              </div>
              <div
                class="pake-info"
                style="margin-top: 20px"
              >
                <img
                  class="img-info2"
                  src="../../assets/shouhuo.png"
                  alt=""
                />
                <span
                  style="display: inline-block; margin-top: 6px"
                >收货方</span>
              </div>
              <div style="margin-top: 0px; margin-left: 40px; display: flex">
                <div style="">
                  <div class="demo-input-suffix base-info">
                    收货方姓名：
                    <span>{{ waybillDetailShow.receiverName }}</span>
                  </div>

                  <div class="demo-input-suffix base-info">
                    收货方地址：
                    <span>{{
                      waybillDetailShow.receiverProvince +
                        waybillDetailShow.receiverCity +
                        waybillDetailShow.receiverCounty
                    }}</span>
                  </div>
                </div>
                <div style="margin-left: 174px">
                  <div class="demo-input-suffix base-info">
                    收货方电话：
                    <span>{{ waybillDetailShow.receiverPhone }}</span>
                  </div>
                  <div class="demo-input-suffix base-info">
                    &nbsp;&nbsp;&nbsp;详细地址：
                    <span>{{ waybillDetailShow.receiverAddress }}</span>
                  </div>
                </div>
              </div>
              <!-- <div style="margin-top: 20px; display: flex; align-items: center">
                <img
                  src="@/assets/juli.png"
                  alt=""
                  style="
                    width: 15px;
                    height: 18px;
                    vertical-align: middle;
                    margin-top: 2px;
                  "
                />
                <span
                  style="margin-left: 16px; font-size: 16px; color: #20232a"
                >距离：</span>
                <span
                  style="color: #20232a"
                  v-html="juli"
                ></span>
                <span style="margin-left: 48px; font-size: 16px">地图：</span>
                <el-link
                  type="primary"
                  :underline="false"
                  style="font-size: 16px; font-weight: 400; color: #419eff"
                  @click="handleShowMaps"
                >查看地图</el-link>
              </div> -->
            </el-timeline>
          </div>
        </el-collapse-item>
      </el-collapse>
      <el-collapse
        v-model="activeNames"
        class="transport-box"
        style="margin-top: 20px"
      >
        <el-collapse-item name="6">
          <template slot="title">
            <div class="collapse-detail">运输信息</div>
          </template>
          <el-scrollbar
            scroll-x
            class="order-scroll"
          >
            <el-steps
              v-show="
                waybillDetailShow.taskTransports &&
                  waybillDetailShow.taskTransports.length > 0
              "
              shadow="never"
              align-center
              finish-status="success"
            >
              <el-step
                v-for="(item, index) in waybillDetailShow.taskTransports"
                :key="item.id"
                :class="{
                  lastBefore: !(
                    index !==
                    waybillDetailShow.taskTransports.length - 1 - 1
                  ),
                  solid:
                    item.status === 4 &&
                    index === waybillDetailShow.taskTransports.length - 1 - 1
                }"
                :title="item.startAgency === null ? '' : item.startAgency.name"
                :status="
                  item.status === 1
                    ? 'wait'
                    : item.status === 2
                      ? index === waybillDetailShow.taskTransports.length - 1 - 1
                        ? 'success'
                        : 'process'
                      : 'success'
                "
              >
                <template
                  v-if="index !== waybillDetailShow.taskTransports.length - 1"
                  slot="description"
                >
                  <div class="step-row">
                    <table
                      width="100%"
                      border="0"
                      cellspacing="0"
                      cellpadding="0"
                      class="processing_content"
                    >
                      <tr>
                        <td>运输任务：</td>
                        <td class="num">
                          {{ item.id === null ? '--' : item.id }}
                        </td>
                      </tr>
                      <tr>
                        <td>车辆：</td>
                        <td>
                          {{
                            item.truck === null ? '--' : item.truck.licensePlate
                          }}
                        </td>
                      </tr>
                      <tr>
                        <td>司机：</td>
                        <td>
                          {{
                            item.drivers === null
                              ? '--'
                              : item.drivers.map((item) => item.name).join(',')
                          }}
                        </td>
                      </tr>
                      <tr>
                        <td>到达时间：</td>
                        <td>
                          {{
                            item.actualArrivalTime === null
                              ? '--'
                              : item.actualArrivalTime
                          }}
                        </td>
                      </tr>
                    </table>
                  </div>
                </template>
              </el-step>
            </el-steps>
          </el-scrollbar>

          <empty
            v-show="
              !waybillDetailShow.taskTransports ||
                waybillDetailShow.taskTransports.length <= 0
            "
            src="@/assets/empty.png"
            desc="这里空空如也~"
          ></empty>
        </el-collapse-item>
      </el-collapse>
      <el-collapse
        v-model="activeNames"
        class="customer-table-box"
        style="margin-top: 20px; margin-bottom: 40px"
      >
        <el-collapse-item name="3">
          <template slot="title">
            <div class="collapse-detail">货品信息</div>
          </template>
          <div style="box-sizing: border-box">
            <el-table
              :data="propTableData.col"
              style="width: 100%"
              stripe
              :header-cell-style="{ background: 'rgba(250,252,255,1)' }"
            >
              <div
                v-show="
                  (!propTableData.col || propTableData.col.length <= 0) &&
                    !listLoading
                "
                slot="empty"
              >
                <img
                  src="@/icons/pic-kong.png"
                  alt="img"
                  style="margin-top: 20px; width: 25%; height: 25%"
                />
                <p style="margin-top: -10px; padding-bottom: 10px">
                  这里空空如也
                </p>
              </div>
              <el-table-column
                label="序号"
                type="index"
                align="left"
                width="60"
              />
              <el-table-column label="货品名称">
                <template slot-scope="scope">
                  <span>{{ scope.row.name || '--' }}</span>
                </template>
              </el-table-column>
              <el-table-column label="货品类型">
                <template slot-scope="scope">
                  <span>{{
                    scope.row.goodsType === null
                      ? '--'
                      : scope.row.goodsType.name
                  }}</span>
                </template>
              </el-table-column>
              <el-table-column label="重量（千克）">
                <template slot-scope="scope">
                  <span>{{ scope.row.totalWeight || '--' }}</span>
                </template>
              </el-table-column>
              <el-table-column label="体积（立方）">
                <template slot-scope="scope">
                  <span>{{ scope.row.totalVolume || '--' }}</span>
                </template>
              </el-table-column>
            </el-table>
          </div>
        </el-collapse-item>
      </el-collapse>
      <!-- <div style="margin-top: 30px; text-align: center">
        <el-button
          style="
            margin-bottom: 40px;
            margin-left: 10px;
            width: 111px;
            height: 40px;
          "
          @click="hadlleCancel()"
        >返回</el-button>
      </div> -->
    </div>
    <waybillMapsDialog
      ref="wayBillMaps"
      :title-info="titleInfo"
      :form-base="formData"
    />
  </div>
</template>
<script>
import waybillMapsDialog from './components/show-waybill-maps'
import {
  waybillDetail,
  orderLocusParamsList,
  orderLocusList,
  orderLocusPointList
} from '@/api/waybill'
import { carGoList } from '@/api/cargo'
import { BmlLushu, BmPolyline } from 'vue-baidu-map'
export default {
  components: {
    waybillMapsDialog,
    BmlLushu,
    BmPolyline
  },
  data() {
    return {
      logisticsInfo: [], // 运输流信息
      centerList: [],
      scrollWheelZoom: true,
      juli: '',
      polylinePath: [
        // {
        //   lng: '125.1914841066626',
        //   lat: '44.43890395126124'
        // },
        // {
        //   lng: '125.3306020759069',
        //   lat: '43.82195350104314'
        // },
        // {
        //   lng: '118.8955203975195',
        //   lat: '42.2616861034116'
        // },
        // {
        //   lng: '119.92757793860845',
        //   lat: '42.29650708524341'
        // },
        // {
        //   lng: '119.96370036172472',
        //   lat: '42.235154042290719'
        // },
        // {
        //   lng: '116.312857',
        //   lat: '40.010497'
        // },
        // {
        //   lng: '114.483458',
        //   lat: '38.076413'
        // },
        // {
        //   lng: '117.902161',
        //   lat: '37.452814'
        // },
        // {
        //   lng: '116.935204',
        //   lat: '36.72649'
        // },
        // {
        //   lng: '121.452066',
        //   lat: '31.30643'
        // }
        // {
        //   lng: '117.041778',
        //   lat: '25.104687'
        // }
      ],
      points: [
        {
          name: '出发点',
          markerPoint: {
            lng: '116.312857',
            lat: '40.010497'
          }
        },
        {
          name: '途径点',
          markerPoint: {
            lng: '114.483458',
            lat: '38.076413'
          }
        },
        {
          name: '途径点',
          markerPoint: {
            lng: '117.902161',
            lat: '37.452814'
          }
        },
        {
          name: '途径点',
          markerPoint: {
            lng: '116.935204',
            lat: '36.72649'
          }
        },
        {
          name: '途径点',
          markerPoint: {
            lng: '121.452066',
            lat: '31.30643'
          }
        },
        {
          name: '终点',
          markerPoint: {
            lng: '117.041778',
            lat: '25.104687'
          }
        }
      ],
      zoom: 10,
      speed: 10000,
      autoView: true,
      readonly: true,
      play: true,
      infoWindow: true,
      queryParams: {},
      content1: '京A6666611',
      labelStyle: {
        padding: '5px',
        border: '1px solid #ccc',
        borderRadius: '10px',
        fontSize: '12px',
        backgroundColor: '#7BA6FF',
        color: '#fff'
      },
      rotation: true,
      icon: {
        url: 'https://developer.baidu.com/map/jsdemo/img/car.png',
        size: { width: 52, height: 26 },
        // eslint-disable-next-line object-curly-spacing
        opts: {
          anchor: { width: 27, height: 13 }
        }
      },
      requestParameters: {
        orderId: ''
      },
      formData: {
        startLat: '',
        startLng: '',
        endLat: '',
        endLng: ''
      },
      wayId: '',
      orderId: '',
      titleInfo: {
        pageTitle: '',
        text: ''
      },
      activeNames: ['1', '2', '3', '4', '5', '6', '7'],
      propTableData: {
        col: []
      },
      waybillDetailShow: {
        orderId: '',
        ydId: '',
        createTime: '',
        status: '',
        estimatedArrivalTime: '',
        senderName: '',
        senderPhone: '',
        senderAddress: '',
        receiverName: '',
        receiverPhone: '',
        receiverAddress: '',
        senderProvince: '',
        senderCity: '',
        senderCounty: '',
        receiverProvince: '',
        receiverCity: '',
        receiverCounty: '',
        amount: '',
        paymentMethod: '',
        paymentStatus: '',
        // 取件信息
        agencyName: '',
        courierName: '',
        pickupType: '',
        zystatus: '',
        courierMobile: '',
        estimatedStartTime: '',
        actualEndTime: '',
        // 派送信息
        psagencyName: '',
        pscourierName: '',
        pszystatus: '',
        pscourierMobile: '',
        psestimatedStartTime: '',
        psactualEndTime: '',
        taskTransports: {},
        taskDispatch: {}
      }
    }
  },
  created() {
    this.wayId = this.$route.query.id
    this.orderId = this.$route.query.orderId
    this.getList(this.wayId)
    // this.getCarGoList(this.orderId)
    this.getOrderLocusParams(this.orderId)
    // 存在不去掉用这个接口
    // !this.$route.query.type && this.getOrderLocusPoint(this.orderId)
  },

  mounted() {
    this.$router.afterEach((to, form, next) => {
      this.$nextTick(() => {
        window.scrollTo(0, 0)
      })
    })
  },
  methods: {
    getAllOrderLocusPoint() {
      // this.getOrderLocusPoint(this.waybillDetailShow.orderId)
      this.getOrderLocusParams(this.waybillDetailShow.orderId)
    },
    updatePolylinePath(e) {
      this.polylinePath = e.target.getPath()
    },
    // 获取订单画线-轨迹 这个接口有问题等后端查看为啥缺失运输id在修改
    async getOrderLocusParams(orderId) {
      // const { data: res } = await orderLocusParamsList(orderId)
      // const { data: result } = await orderLocusList(res)
      // this.polylinePath = result.data && result.data.polyLinePath
    },
    // 获取订单画线-轨迹 此处接口有问题提示系统维护中
    async getOrderLocusPoint(orderId) {
      const { data: res } = await orderLocusPointList(orderId)
      if (res.code === -2) {
        this.$message.error(res.msg)
        return
      } else {
        this.points = res
        this.centerList = []
        this.centerList = res.map((obj) => {
          return {
            lng: obj.markerPoint.lng,
            lat: obj.markerPoint.lat
          }
        })
      }
    },
    // 计算经纬度距离
    getDistance(lat1, lng1, lat2, lng2) {
      var radLat1 = (lat1 * Math.PI) / 180.0
      var radLat2 = (lat2 * Math.PI) / 180.0
      var a = radLat1 - radLat2
      var b = (lng1 * Math.PI) / 180.0 - (lng2 * Math.PI) / 180.0
      var s =
        2 *
        Math.asin(
          Math.sqrt(
            Math.pow(Math.sin(a / 2), 2) +
              Math.cos(radLat1) *
                Math.cos(radLat2) *
                Math.pow(Math.sin(b / 2), 2)
          )
        )
      s = s * 6378.137 // EARTH_RADIUS;
      s = Math.round(s * 100) / 100
      return s
    },
    // 获取货物列表
    // async getCarGoList(orderId) {
    //   this.requestParameters.orderId = orderId
    //   const { data: res } = await carGoList(this.requestParameters)
    //   this.propTableData.col = res
    // },
    handleChange(val) {},
    Message(msg, type) {
      this.$message({ type: type || 'info', message: msg })
    },
    // 返回运单列表
    hadlleCancel() {
      history.go(-1)
    },
    // 查看地图
    handleShowMaps() {
      this.$nextTick(() => {
        this.$refs.wayBillMaps.dialogFormV()
        this.titleInfo.text = '地图查看'
      })
    },
    strInit(value) {
      let strText = value
      const replaceText = []
      for (let i = 0; i <= 10; i++) {
        replaceText.push('' + i)
      }

      // 转换成html形式解析
      for (let i = 0; i < replaceText.length; i++) {
        var replaceString =
          '<span style="color: red;">' + replaceText[i] + '</span>'
        strText = strText.replace(RegExp(replaceText[i], 'g'), replaceString)
      }

      // 这里再把这个红色替换成你想要的颜色
      // 由于在循环体里面数字会被替换，所以用了一个单词（red）来当成初始色
      strText = strText.replace(RegExp('red', 'g'), '#E63E32')
      return strText
    },
    // 获取运单详情
    async getList(wayId) {
      const { data: res } = await waybillDetail(wayId)
      this.waybillDetailShow.ydId = res.id
      this.waybillDetailShow.taskDispatch = res.taskDispatch
      this.waybillDetailShow.taskRejectionDispatch = res.taskRejectionDispatch
      this.waybillDetailShow.taskPickup = res.taskPickup
      // this.logisticsInfo = res.transportOrderPointVOS.reverse()
      // this.logisticsInfo = res.transportOrderPointVOS.reverse().concat(res.transportOrderPointVOS.reverse()).concat(res.transportOrderPointVOS.reverse())
      // 订单信息
      if (res.order != null) {
        this.waybillDetailShow.orderId = res.order.id
        this.waybillDetailShow.createTime = res.order.createTime
        this.waybillDetailShow.status = res.order.status
        this.waybillDetailShow.estimatedArrivalTime =
          res.order.estimatedArrivalTime
        this.waybillDetailShow.senderName = res.order.senderName
        this.waybillDetailShow.senderPhone = res.order.senderPhone
        this.waybillDetailShow.senderAddress = res.order.senderAddress
        this.waybillDetailShow.receiverName = res.order.receiverName
        this.waybillDetailShow.receiverPhone = res.order.receiverPhone
        this.waybillDetailShow.receiverAddress = res.order.receiverAddress
        this.waybillDetailShow.amount = res.order.amount
        this.propTableData.col = res.order.orderCargoDTOS
        if (res.paymentMethod === 1) {
          this.waybillDetailShow.paymentMethod = '预结'
        } else {
          this.waybillDetailShow.paymentMethod = '到付'
        }
        if (res.order.paymentStatus === 1) {
          this.waybillDetailShow.paymentStatus = '未付'
        } else {
          this.waybillDetailShow.paymentStatus = '已付'
        }
        if (res.senderProvince !== null) {
          this.waybillDetailShow.senderProvince = res.order.senderProvince.name
        }
        if (res.senderCity !== null) {
          this.waybillDetailShow.senderCity = res.order.senderCity.name
        }
        if (res.senderCounty !== null) {
          this.waybillDetailShow.senderCounty = res.order.senderCounty.name
        }
        if (res.receiverProvince !== null) {
          this.waybillDetailShow.receiverProvince =
            res.order.receiverProvince.name
        }
        if (res.receiverCity !== null) {
          this.waybillDetailShow.receiverCity = res.order.receiverCity.name
        }
        if (res.receiverCounty !== null) {
          this.waybillDetailShow.receiverCounty = res.order.receiverCounty.name
        }
        if (res.order.pickupType === 1) {
          this.waybillDetailShow.pickupType = '网点自寄'
        } else {
          this.waybillDetailShow.pickupType = '上门取件'
        }
        // 计算距离
        const result = this.getDistance(
          res.order.senderCity.lat,
          res.order.senderCity.lng,
          res.order.receiverCity.lat,
          res.order.receiverCity.lng
        )
        this.juli = result + 'km'
        this.formData.startLat = res.order.senderCity.lat
        this.formData.startLng = res.order.senderCity.lng
        this.formData.endLat = res.order.receiverCity.lat
        this.formData.endLng = res.order.receiverCity.lng
      }
      // 运输信息
      if (res.taskTransports !== null) {
        this.waybillDetailShow.taskTransports = res.taskTransports.reverse()
        this.waybillDetailShow.taskTransports.push(
          Object.assign(
            {},
            {
              startAgency: {
                name: this.waybillDetailShow.taskTransports[
                  this.waybillDetailShow.taskTransports.length - 1
                ].endAgency.name
              },
              status:
                this.waybillDetailShow.taskTransports[
                  this.waybillDetailShow.taskTransports.length - 1
                ].status
            }
          )
        )
      }
    }
  }
}
</script>
<style rel="stylesheet/scss" lang="scss" scoped>
.app-container {
  .order-box {
    box-shadow: none;
    /deep/ .el-card__body {
      padding: 25px 44px;
      padding-right: 0px;
    }
  }
  /deep/ .el-collapse.maps {
    .el-collapse-item__content {
      padding: 30px !important;
    }
  }
  /deep/ .order-collapse {
    .el-collapse-item__content {
      padding-bottom: 0px !important;
    }
  }
    /deep/ .transport-box {
    .el-collapse-item__content {
      padding-bottom: 0px !important;
    }
  }
  /deep/ .el-collapse-item {
    .el-collapse-item__content {
      padding: 24px 44px;
      .el-timeline {
        margin: 0 !important;
        padding: 0 !important;
        position: relative;
        .line {
          width: 1px;
          height: 100px;
          border-left: 1px dashed#DCDCDD;
          position: absolute;
          top: 26px;
          left: 8px;
        }
        .pake-info {
          span {
            margin-left: 10px;
          }
        }
      }
      label {
        font-weight: 400;
      }
    }
  }
  .customer-table-box {
    /deep/ .el-collapse-item {
      .el-collapse-item__content {
        padding: 24px 28px;
      }
    }
  }
  /deep/ .el-collapse-item__header {
    display: -webkit-box;
    display: -ms-flexbox;
    display: flex;
    -webkit-box-align: center;
    -ms-flex-align: center;
    align-items: center;
    height: 60px;
    line-height: 60px;
    background: #f8faff !important;
    border-radius: 4px 4px 0px 0px;
    color: #2a2929;
    cursor: pointer;
    border-bottom: 0;
    font-size: 16px;
    font-weight: 700;
    -webkit-transition: border-bottom-color 0.3s;
    transition: border-bottom-color 0.3s;
    outline: 0;
    padding: 0 44px;
    .el-collapse-item__arrow {
      margin: 0 0 0 auto;
    }
  }
}
.demo-input-suffix {
  font-size: 14px;
  font-weight: 400;
  margin-top: 10px;
  span {
    font-size: 14px;
    font-family: PingFangSC-Regular, PingFang SC;
    font-weight: 400;
    color: rgba(129, 134, 147, 1);
  }
}

.map {
  width: 100%;
  height: 562px;
  margin-bottom: 30px;
}
.map-btn-box {
  text-align: right;
  margin-bottom: 10px;
  .refresh {
    width: 14px;
    height: 14px;
    cursor: pointer;
  }
}
.ddbh {
  float: left;
  font-size: 14px;
  font-family: PingFangSC-Regular, PingFang SC;
  font-weight: 400;
  color: #20232a;
}
.ddbh label {
  font-size: 14px;
  font-family: PingFangSC-Regular, PingFang SC;
  font-weight: 400;
  color: #818693;
}
.ydbh {
  float: left;
  font-size: 14px;
  font-family: PingFangSC-Regular, PingFang SC;
  font-weight: 400;
  color: #20232a;
}
.ydbh label {
  font-size: 14px;
  font-family: PingFangSC-Regular, PingFang SC;
  font-weight: 400;
  color: #818693;
}
.xdsj {
  float: left;
  font-size: 14px;
  font-family: PingFangSC-Regular, PingFang SC;
  font-weight: 400;
  color: #20232a;
}
.xdsj label {
  font-size: 14px;
  font-family: PingFangSC-Regular, PingFang SC;
  font-weight: 400;
  color: #818693;
}
.ddzt {
  float: left;
  font-size: 14px;
  font-family: PingFangSC-Regular, PingFang SC;
  font-weight: 400;
  color: #20232a;
}
.ddzt label {
  font-size: 14px;
  font-family: PingFangSC-Regular, PingFang SC;
  font-weight: 400;
  color: #818693;
}
.yjddrq {
  float: left;
  color: #20232a;
  font-size: 14px;
  margin-top: 25px;
}
.yjddrq label {
  font-size: 14px;
  font-family: PingFangSC-Regular, PingFang SC;
  font-weight: 400;
  color: #818693;
}

.aa >>> .el-collapse-item >>> .el-collapse-item__header {
  background-color: aqua !important;
}
/deep/ .el-steps--horizontal {
  margin-bottom: 20px;
}
.processing_content {
  background: #f4f8fc;
  border-radius: 8px;
  padding: 14px 15px;
  font-size: 14px;
  color: #20232a;
  font-weight: 400;
  tr {
    margin-top: 8px;
    td {
      text-align: left;
      padding-top: 8px;
      &:last-child {
        color: #818693;
      }
      &.num {
        color: #419eff;
      }
    }
    &:first-child {
      td {
        padding-top: 0;
        white-space: nowrap;
      }
    }
  }
}
.processing_content_detail {
  margin-left: 10px;
  margin-top: 3.5px;
  margin-bottom: 3.5px;
  width: 150px;
  display: inline-block;
}

/deep/ .collapse-detail {
  font-size: 16px;
  font-family: MicrosoftYaHei-Bold, MicrosoftYaHei;
  font-weight: 700;
  color: #2a2929;
}

/deep/ .el-collapse-item__header.is-active {
  border-bottom-color: transparent;
  background-color: #f8faff;
}
.pake-info {
  .img-info1 {
    width: 14px;
    height: 18px;
    vertical-align: middle;
    margin-left: 2px;
  }
  .img-info2 {
    width: 17px;
    height: 17px;
    vertical-align: middle;
    margin-bottom: 4px;
  }
  span {
    font-size: 16px;
    font-family: PingFangSC-Regular, PingFang SC;
    font-weight: 400;
    color: #20232a;
    line-height: 28px;
  }
}

.base-info {
  span {
    font-size: 14px;
    font-family: PingFangSC-Regular, PingFang SC;
    font-weight: 400;
    color: #818693;
  }
}

.costs {
  display: flex;
  .costs-item {
    flex: 1;
    font-size: 14px;
    font-weight: 400;
    color: #20232a;
    margin-top: 0;
    span {
      font-size: 14px;
      font-family: PingFangSC-Regular, PingFang SC;
      font-weight: 400;
      color: #818693;
      .money {
        font-size: 18px;
        color: #ffb302;
        margin-right: 10px;
      }
    }
  }
}

/deep/ path {
  stroke: rgba(40, 193, 64, 1);
}
.transport-box {
  /deep/ .el-collapse-item__content {
    overflow-x: auto;
  }
  /deep/ .el-steps {
    position: relative;
    right: 100px;
    .el-step.is-center .el-step__description {
      padding-left: 90%;
      padding-right: 10%;
    }
    .el-step {
      min-width: 300px;
      flex-basis: auto !important;
      .el-step__head.is-success {
        color: #419eff;
        border-color: #419eff;
        .el-step__line {
          top: 20px;
          border-bottom: 1px solid #419eff;
          height: 0;
          background: transparent;
        }
      }

      .el-step__icon {
        width: 40px;
        height: 40px;
        font-size: 20px;
        &.is-text {
          border: 1px solid;
        }
        .el-step__icon-inner {
          font-weight: 400;
          color: #20232a;
        }
      }
      .el-step__head.is-wait {
        .el-step__icon-inner {
          font-weight: 400;
          color: #c0c4cc;
        }
      }
      .el-step__head.is-success {
        .el-step__icon-inner {
          font-weight: 400;
          color: #419eff;
        }
      }
      .el-step__head.is-process {
        .el-step__icon.is-text {
          background: #e8f3ff;
          border: 1px solid #419eff;
        }
        .el-step__line {
          border-bottom: 1px dashed #419eff;
        }
      }
      .el-step__line {
        top: 20px;
        border-bottom: 1px dashed #a8a9ac;
        height: 0;
        background: transparent;
      }
      .el-step__title.is-process {
        font-size: 14px;
        font-weight: 400;
        color: #20232a;
      }
      .el-step__title.is-wait {
        font-size: 14px;
        font-weight: 400;
        color: #c0c4cc;
      }
      .el-step__title.is-success {
        font-size: 14px;
        font-weight: 400;
        color: #20232a;
      }
      .el-step__description {
        display: flex;
        justify-content: center;
        .step-row {
          min-width: 200px;
          margin-top: 10px;
          position: relative;
        }
        .step-row::before{
          content: '';
          position: absolute;
          display: block;
          width: 0px;
          height: 0px;
          border-style: solid;
          border-width:50px 16px 50px 16px ;
          border-color:   transparent transparent #F4F8FC transparent;
          top: -90px;
          left: 53%;
        }
      }
      .is-process {
        .el-step__icon-inner {
          background: url('~@/assets/icon-car.png') no-repeat;
          width: 25px;
          height: 18px;
          background-size: 100% 100%;
          color: #e8f3ff;
        }
      }
    }
    .lastBefore .el-step__head.is-success {
      .el-step__line {
        border-bottom: 1px dashed #419eff;
      }
    }
    .solid .el-step__head.is-success {
      .el-step__line {
        border-bottom: 1px solid #419eff;
      }
    }
  }
}
//订单跟踪（运输流）
/deep/ .logistics-orderInfo {
  display: flex;
  flex-direction: row-reverse;
  justify-content: flex-end;
  .logistics-orderInfo-item.active {
    .logistics-orderInfo-left {
      .circle {
        background-color: #e63e32 !important;
      }
    }
  }
  .logistics-orderInfo-item {
    margin-right: 20px;
    .logistics-orderInfo-left {
      justify-content: center;
      width: 190px;
      display: flex;
      position: relative;
      .gray.circle {
        background-color: #818181;
      }
      .circle {
        position: relative;
        right: -10px;
        width: 40px;
        height: 40px;
        text-align: center;
        line-height: 26px;
        border-radius: 50%;
        font-size: 16px;
        display: flex;
        align-items: center;
        justify-content: center;
        color: white;
        img {
          width: 26px;
          height: 21px;
        }
      }
      .point {
        width: 5px;
        height: 5px;
        border-radius: 50%;
        background-color: #818181;
        margin-right: 20px;
      }
      .line {
        height: 1px;
        width: 180px;
        border-top: 1px dashed #dfdfdf;
        position: absolute;
        left: 125px;
        top: 20px;
      }
      .line.short {
        height: 60px;
      }
    }
    .logistics-orderInfo-right {
      margin-left: 10px;
      .status {
        font-size: 14px;
        color: #20232a;
        font-weight: normal;
        text-align: center;
        margin-top: 8px;
      }

      .time,
      .desc {
        color: #818181;
        width: 190px;
      }
      .desc {
        background-color: #f4f8fc;
        padding: 8px 15px;

        border-radius: 8px;
        color: #20232a;
        font-size: 14px;
      }
      .time {
        margin-bottom: 3px;
        color: #818693;
        text-align: center;
        font-size: 14px;
      }
    }
  }
}
/deep/ .empty-box {
  .icon {
    width: 14%;
    height: 14%;
  }
}
/deep/ .el-table tr td:last-child .cell {
  text-align: left;
}
/deep/ .el-scrollbar {
  .el-scrollbar__wrap {
    overflow-x: scroll !important;
  }
}
/deep/ .el-collapse-item__wrap {
  border-bottom: none;
  border-radius: 0 0 4px 4px;
}
/deep/ .el-collapse {
  border: none;
}
</style>
