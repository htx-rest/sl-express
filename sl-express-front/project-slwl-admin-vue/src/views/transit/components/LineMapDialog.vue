<template>
  <div v-if="dialogShow">
    <el-dialog center :title="title" :visible.sync="dialogShow" :before-close="closeDialog" :show-close="true" :close-on-click-modal="false">
      <div class="content">
        <baidu-map
          v-if="dialogShow"
          class="mapsClass"
          :center="center"
          :zoom="zoom"
          :scroll-wheel-zoom="scrollWheelZoom"
          :map-click="false"
          @ready="handler"
        >
          <bm-driving :select-first-result="false" :panel="false" :start="startArea" :end="endArea" :auto-viewport="true" location="中国" @markersset="markersset"></bm-driving>
          <!-- <bml-lushu :path="path" :icon="icon" :play="play" :rotation="true" :speed="10000" @stop="reset"></bml-lushu> -->
        </baidu-map>
      </div>
      <div slot="footer" style="text-align:center;">
      </div>
    </el-dialog>
  </div>
</template>

<script>
// import { BmlLushu } from 'vue-baidu-map'
export default {
  props: {
    dialogShow: {
      type: Boolean,
      default: false
    },
    title: {
      type: String,
      default: ''
    },
    formBase: {
      type: Object,
      required: true
    }
  },
  data() {
    return {
      scrollWheelZoom: true,
      zoom: 6,
      center: {
        lng: 116.404,
        lat: 39.915
      },
      dialogFormVisible: false,
      startArea: {},
      endArea: {},
      play: true,
      path: [],
      icon: {
        url: 'http://api.map.baidu.com/library/LuShu/1.2/examples/car.png',
        size: { width: 52, height: 26 },
        opts: { anchor: { width: 27, height: 13 }}
      }
    }
  },
  mounted() {
    // this.handler()
  },
  methods: {
    closeDialog() {
      this.$emit('closeDialog')
      // this.formBase = {}
      this.startArea = {}
      this.endArea = {}
    },
    handler({ BMap, map }) {
      // const _this = this
      // _this.startArea = ''
      // _this.endArea = ''
      // map.enableScrollWheelZoom(true)
      // _this.BMap = BMap
      this.BMap = BMap
      // _this.map = map
      this.map = map
      // 经纬度
      // const startPoint = new BMap.Point(this.formBase.startLng, this.formBase.startLat)
      // const endPoint = new BMap.Point(this.formBase.endLng, this.formBase.endLat)

      this.startArea = new BMap.Point(this.formBase.startLng, this.formBase.startLat)
      this.endArea = new BMap.Point(this.formBase.endLng, this.formBase.endLat)
      // 查地址
      // var gc = new BMap.Geocoder()
      // gc.getLocation(startPoint, (rs) => {
      //   this.startArea = rs.address
      // })
      // gc.getLocation(endPoint, (rs) => {
      //   this.startArea = rs.address
      // })
    },
    markersset(routes) {
      const startIcon = new this.BMap.Icon(require('../../../assets/icon-start.png'), new this.BMap.Size(20, 32), { anchor: new this.BMap.Size(10, 25) })
      const endIcon = new this.BMap.Icon(require('../../../assets/icon-end.png'), new this.BMap.Size(24, 32), { anchor: new this.BMap.Size(10, 25) })
      routes[0].marker.setIcon(startIcon)
      routes[1].marker.setIcon(endIcon)
    },
    update(e) {
      this.points = e.target.cornerPoints
    },
    reset() {
      this.play = false
    },
    handleSearchComplete(res) {
      this.path = res.getPlan(0).getRoute(0).getPath()
    }
  }
}
</script>

<style lang="scss" scoped>
.mapsClass {
  width: 100%;
  height: 455px;
  text-align: center;
}
/deep/ .el-dialog {
  // left: 50%;
  width: 750px;
  height: 550px;
  .el-dialog__body{
    padding: 20px !important;
  }
}
/deep/ .BMap_Marker {
  // display: none;
}

/deep/ path {
  stroke:rgba(40,193,64,1);
}
</style>
