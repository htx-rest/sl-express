<template>
  <div class="dashboard-container line-manage customer-list-box">
    <div class="app-container">
      <el-card
        shadow="never"
        class="search-card-box"
      >
        <el-form
          ref="lineManageSearchFormData"
          :model="lineManageSearchFormData"
          label-width="90px"
        >
          <el-row :gutter="60">
            <el-col :span="8">
              <el-form-item
                label="线路编号:"
                style="margin-bottom: 0px"
              >
                <el-input
                  v-model="lineManageSearchFormData.number"
                  placeholder="请输入线路编号"
                  clearable
                />
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item
                label="线路名称:"
                style="margin-bottom: 0px"
                label-width="80px"
              >
                <el-input
                  v-model="lineManageSearchFormData.name"
                  placeholder="请输入线路名称"
                  clearable
                />
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item
                prop="startOrganId"
                label="起始机构:"
                style="margin-bottom: 20px"
                label-width="80px"
              >
                <treeselect
                  v-model="lineManageSearchFormData.startOrganId"
                  :options="agencyOptions"
                  :normalizer="normalizer"
                  :searchable="true"
                  style="width: 100%"
                  placeholder="请选择起始机构"
                  no-options-text="暂无数据"
                  no-results-text="暂无数据"
                  @select="selectStartAgency"
                  @open="open"
                  @close="close"
                />
                <img
                  ref="arrow"
                  src="../../assets/pullDown.png"
                  style="position:absolute;width:18px;height:18px;right:9px;top:12px;cursor:pointer;pointer-events:none;transition-duration:0.3s"
                />
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item
                prop="endOrganId"
                label="目的地机构:"
                style="margin-bottom: 0px"
              >
                <treeselect
                  v-model="lineManageSearchFormData.endOrganId"
                  :options="agencyOptions"
                  :normalizer="normalizer"
                  :searchable="true"
                  style="width: 100%"
                  no-options-text="暂无数据"
                  no-results-text="暂无数据"
                  placeholder="请选择目的地机构"
                  @select="selectEndAgency"
                  @open="opens"
                  @close="closes"
                />
                <img
                  ref="arrows"
                  src="../../assets/pullDown.png"
                  style="position:absolute;width:18px;height:18px;right:9px;top:12px;cursor:pointer;pointer-events:none;transition-duration:0.3s"
                />
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-button
                type="warning"
                @click="handleFilter"
              >搜索</el-button>
              <el-button
                plain
                class="reset-btn"
                @click="resetForm('lineManageSearchFormData')"
              >重置</el-button>
            </el-col>
          </el-row>
        </el-form>
      </el-card>

      <el-card
        v-loading="listLoading"
        element-loading-text="加载中"
        class="table-card-box"
        :class="{
          'table-empty-box': !dataList || dataList.length <= 0,
          'loading-box': listLoading
        }"
        shadow="never"
        style="margin-top: 20px"
      >
        <el-button
          class="customer-add-btn"
          style="margin-bottom:10px"
          @click="handleAddLine()"
        >+新增线路</el-button>
        <el-table
          :data="dataList"
          style="width: 100%; margin-top: 10px"
          :row-key="getRowKeys"
          :expand-row-keys="expands"
          stripe
          :header-cell-style="{ background: 'rgba(250,252,255,1)' }"
          @expand-change="expandChange"
          @selection-change="handleSelectionChange"
        >
          <div
            v-show="(!dataList || dataList.length <= 0) && !listLoading && !searchkey"
            slot="empty"
          >
            <img
              src="@/icons/pic-kong.png"
              alt="img"
              style="margin-top: 20px;width: 25%;height: 25%;"
            >
            <p
              style="
          margin-top: -10px;
          padding-bottom: 10px;
          "
            >这里空空如也</p>
          </div>
          <el-table-column type="expand">
            <template slot-scope="scope">
              <div
                class="children-table-box"
              >
                <el-table
                  ref="multipleTable"
                  v-loading="loadingTable"
                  :data="tripsData"
                  element-loading-text="加载中"
                  style="width: 100%; margin: auto;border-color:#ebeef5;border-style:solid ;border-width:1px 1px 0 1px"
                  :row-key="getRowKeys"
                  @selection-change="handleSelectionChange"
                >
                  <div
                    v-show="(!tripsData || tripsData.length <= 0) && !listLoading"
                    slot="empty"
                  >
                    <img
                      src="@/icons/pic-kong.png"
                      alt="img"
                      style="margin-top: 20px;width: 25%;height: 25%;"
                    >
                    <p
                      style="
                      margin-top: -10px;
                      padding-bottom: 10px;
                      "
                    >这里空空如也</p>
                  </div>
                  <el-table-column
                    prop="name"
                    label="车次名称"
                  ></el-table-column>
                  <el-table-column
                    prop="departureTime"
                    label="发车频次"
                  >
                    <template slot-scope="scope1">
                      <span>{{ handleDate(scope1.row.departureTime) }} /
                        {{ scope1.row.periodName }}</span>
                    </template>
                  </el-table-column>
                  <el-table-column
                    prop="arrivalTime"
                    label="到达时间"
                  >
                    <template slot-scope="scope1">
                      <span>{{ scope1.row.estimatedTime }}分钟</span>
                    </template>
                  </el-table-column>
                  <el-table-column
                    prop="legal"
                    label="车辆安排"
                  >
                    <template
                      v-if="scope1.row.truckDrivers"
                      slot-scope="scope1"
                    >
                      <span
                        v-for="item in ([...(scope1.row.truckDrivers.map(item=>item.truck.licensePlate).filter((value, index) => scope1.row.truckDrivers.map(item=>item.truck.licensePlate).indexOf(value) === index))])"
                        :key="item"
                      >
                        <!-- {{ item.truck === null ? '' : item.truck.licensePlate }} -->
                        {{ item }}<br>
                      </span>
                    </template>
                  </el-table-column>
                  <el-table-column
                    prop="legal"
                    label="司机安排"
                  >
                    <template slot-scope="scope1">
                      <span
                        v-for="(item,index) in scope1.row.truckDrivers"
                        :key="item.id"
                      >
                        <!-- {{ item.truck === null ? '' : item.truck.licensePlate }}
                        -- -->
                        {{ item.driver === null ? '' : item.driver.name }}，<br v-if="(index%2)!==0">
                      </span>
                    </template>
                  </el-table-column>
                  <el-table-column
                    prop="cat_id"
                    label="操作"
                    fixed="right"
                    width="314"
                    align="center"
                  >
                    <template slot-scope="scope1">
                      <el-button
                        type="text"
                        size="mini"
                        @click="handleEditTrips(scope1.row.id, scope.row.name)"
                      >编辑车次</el-button>
                      <el-divider direction="vertical"></el-divider>
                      <el-button
                        type="text"
                        size="small"
                        style="color: rgba(245, 108, 108, 1)"
                        @click="handleDelTrips(scope1.row.id)"
                      >删除车次</el-button>
                      <el-divider direction="vertical"></el-divider>
                      <el-button
                        type="text"
                        size="small"
                        @click="
                          arrangeVehicles(
                            scope.row.id,
                            scope.row.name,
                            scope1.row.id,
                            scope1.row.name,
                            scope1.row.departureTime,
                            scope1.row.arrivalTime
                          )
                        "
                      >安排车辆</el-button>
                    </template>
                  </el-table-column>
                </el-table>
                <!--
                  !tripsData.length && !loadingTable是为了防止没有数据时，没有加载中的效果，!tripsData.length的意思是如果tripsData为空，则显示加载中，如果不为空，则不显示加载中!loadingTable的意思是如果loadingTable为true，则显示加载中，如果不为true，则不显示加载中
                 -->
                <empty
                  v-show="
                    (!dataList || dataList.length <= 0) && !listLoading
                  "
                ></empty>
              </div>
            </template>
          </el-table-column>
          <el-table-column
            width="120"
            prop="number"
            label="线路编号"
          ></el-table-column>
          <el-table-column
            width="160"
            prop="name"
            label="线路名称"
          ></el-table-column>
          <el-table-column
            width="100"
            label="线路类型"
          >
            <template slot-scope="{ row }">
              <span>{{
                row.type === 1
                  ? '干线'
                  : row.type === 2
                    ? '支线'
                    : row.type === 3
                      ? '接驳路线'
                      : ''
              }}</span>
            </template>
          </el-table-column>
          <el-table-column
            prop="spu_desc"
            min-width="120"
            label="起始地机构"
          >
            <template slot-scope="{ row }">
              <span>{{ row.startOrganName || '' }}</span>
            </template>
          </el-table-column>
          <el-table-column
            prop="pay_type"
            min-width="120"
            label="目的地机构"
          >
            <template slot-scope="{ row }">
              <span>{{ row.endOrganName || '' }}</span>
            </template>
          </el-table-column>
          <el-table-column
            prop="distance"
            width="120"
            label="距离（千米）"
          ></el-table-column>
          <el-table-column
            prop="cost"
            width="130"
            label="平均成本（元）"
          ></el-table-column>
          <el-table-column
            prop="time"
            width="140"
            label="预计时间（分钟）"
          ></el-table-column>
          <el-table-column
            prop="pay"
            label="操作"
            width="184"
            align="center"
          >
            <template slot-scope="scope">
              <el-link
                type="primary"
                :underline="false"
                @click="handleAddLine(scope.row.id)"
              >修改</el-link>
              <el-divider direction="vertical"></el-divider>
              <el-link
                type="danger"
                :underline="false"
                @click="handleDeleteLine(scope.row.id)"
              >删除</el-link>
              <el-divider direction="vertical"></el-divider>
              <el-link
                type="primary"
                :underline="false"
                @click="handleAddTrips(scope.row.id, scope.row.name)"
              >增加车次</el-link>
            </template>
          </el-table-column>
          <el-card
            v-show="(!dataList || dataList.length <= 0) && !listLoading && searchkey"
            slot="empty"
            shadow="never"
          >
            <empty
              style="
            text-align: center;
          "
            ></empty>
          </el-card>
        </el-table>
        <!-- end -->
        <!-- 分页 -->
        <div
          v-show="dataList && dataList.length > 0"
          class="pagination"
        >
          <div class="pages">
            <el-pagination
              :current-page="Number(lineManageSearchFormData.page)"
              :total="Number(total)"
              :page-size="Number(lineManageSearchFormData.pageSize)"
              :page-sizes="[10, 20, 30, 50]"
              layout="total, sizes, prev, pager, next, jumper"
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
            />
          </div>
        </div>
        <!-- end -->
        <!-- 新增线路 -->
        <lineDialog
          ref="addLine"
          class="add-form-dialog"
          :title-info="titleInfo"
          :form-base="formData"
          @newDataes="getListDia"
        />
        <!-- 新增车次 -->
        <tripsDialog
          ref="addTrips"
          class="add-form-dialog"
          :transport-line-name="transportLineName"
          :transport-line-id="transportLineId"
          :title-info="titleInfo"
          @newDataes1="expandChangeDia"
        />
        <!-- 安排车辆 -->
        <arrangeVehiclesDialog
          ref="arrangeVehicles"
          class="add-form-dialog"
          :title-info="titleInfo"
          :form-base-av="formDataAv"
          @newDataesAv="getListDia"
        />
        <lineMapDialog
          class="add-form-dialog"
          :dialog-show="dialogShow"
          :title="wholeTitle"
          form-data-map
          :form-base="formDataMap"
          @closeDialog="closeDialog"
        />
      </el-card>
    </div>
    <!-- 删除车次二级表格弹窗重构 -->
    <el-dialog
      class="customer-dialog"
      title="删除确认"
      :visible.sync="dialogVisibleTrainNumber"
      width="394px"
      :before-close="handleCloseTrainNumber"
    >
      <img
        class="warn-img"
        src="@/assets/warn.png"
        alt=""
      />
      <p>确认删除车次？</p>
      <span
        slot="footer"
        class="dialog-footer"
      >
        <el-button
          type="primary"
          class="confirm-btn"
          @click="sumbitDelteTrainNumber"
        >确定</el-button>
        <el-button
          class="cancel-btn"
          @click="dialogVisibleTrainNumber = false"
        >取消</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
import {
  lineList,
  lineDel,
  lineDetail,
  tripsList,
  tripsDel
} from '@/api/transit'
import { agencyList } from '@/api/institutions'
import { lineTypeCommonList } from '@/api/common'
import lineDialog from './components/line-add'
import tripsDialog from './components/trips-add'
import arrangeVehiclesDialog from './components/arrange-vehicles'
import lineMapDialog from './components/LineMapDialog.vue'
import Treeselect from '@riophae/vue-treeselect'

export default {
  name: 'LineManage',
  components: {
    lineDialog,
    tripsDialog,
    arrangeVehiclesDialog,
    lineMapDialog,
    Treeselect
  },
  data() {
    return {
      normalizer(node) {
        return {
          id: node.id,
          label: node.name,
          children: node.children
        }
      },
      transportLineName: '',
      transportLineId: '',
      dialogVisible: false,
      handleDeleteId: '',
      dialogVisibleTrainNumber: false,
      handleDeleteIdTrainNumber: '',
      dialogShow: false,
      wholeTitle: '地图线路展示',
      expands: [],
      getRowKeys: (row) => {
        return row.id
      },
      agencyOptions: [],
      trips: [],
      multipleSelection: [],
      options: [
        {
          id: 1,
          name: '干线'
        },
        {
          id: 2,
          name: '支线'
        },
        {
          id: 3,
          name: '接驳路线'
        }
        // {
        //   id: 4,
        //   name: '专线'
        // },
        // {
        //   id: 5,
        //   name: '临时线路'
        // }
      ],
      lineType: '',
      tableKey: 0,
      tableKey1: 0,
      loadingTable: false,
      tripsData: [],
      dataList: [],
      tripsDataList: [],
      total: null,
      listLoading: true,
      searchkey: false,
      alertText: '',
      lineManageSearchFormData: {
        page: 1,
        pageSize: 10,
        number: '',
        name: '',
        transportLineTypeId: '',
        startOrganId: null,
        endOrganId: null
      },
      requestParameters1: {
        transportLineId: ''
      },
      titleInfo: {
        pageTitle: '',
        text: ''
      },
      formData: {
        number: 'XL',
        lineName: '',
        name: '',
        lineNumber: '',
        distance: '',
        cost: '',
        estimatedTime: '',
        lineCommonName: '',
        startAgency: null,
        endAgency: null,
        type: ''
      },
      formData1: {
        name: '',
        lineId: '',
        lineName: '',
        period: '',
        periodName: '',
        departureTime: ''
      },
      formDataAv: {},
      formDataAd: {},
      formDataMap: {
        startLat: '',
        startLng: '',
        endLat: '',
        endLng: ''
      }
    }
  },
  computed: {
    status() {
      return status
    }
  },
  // 挂载结束
  mounted: function () {},
  // 创建完毕状态
  created() {
    var _this = this

    this.initialDate()
    // 键盘enter操作
    document.onkeydown = function (e) {
      var key = window.event.keyCode
      if (key === 13) {
        _this.handleFilter(this.lineManageSearchFormData)
      }
    }
  },
  // 组件更新
  updated: function () {},
  methods: {
    opens() {
      this.$refs.arrows.style.transform = 'rotate(-180deg)'
    },
    closes() {
      this.$refs.arrows.style.transform = 'rotate(0deg)'
    },
    open() {
      this.$refs.arrow.style.transform = 'rotate(-180deg)'
    },
    close() {
      this.$refs.arrow.style.transform = 'rotate(0deg)'
    },
    // 处理时间(将时间转为当前时间距离当日0点0分的分钟差值)
    handleDate(val) {
      return (
        String(parseInt((val / 60).toFixed(2))) +
        ':' +
        String(
          (
            ((val / 60).toFixed(2) - parseInt((val / 60).toFixed(2))) *
            60
          ).toFixed() === '0' ? '00' : (
              ((val / 60).toFixed(2) - parseInt((val / 60).toFixed(2))) *
            60
            ).toFixed()
        )
      )
    },
    selectEndAgency(val) {
      this.lineManageSearchFormData.endOrganId = val.id
    },
    selectStartAgency(val) {
      this.lineManageSearchFormData.startOrganId = val.id
    },
    // 获取起始/目的地机构列表数据
    async getAgencyList() {
      const { data: res } = await agencyList()
      // 获取数据给options
      this.agencyOptions = JSON.parse(res)
    },
    // 删除一级表格弹框重构
    handleClose() {
      this.dialogVisible = false
    },
    handleCloseTrainNumber() {
      this.dialogVisibleTrainNumber = false
    },
    getImgMap(row) {
      this.formDataMap = {}
      // TODO
      this.dialogShow = true
      this.wholeTitle = row.name + '网点线路展示'
      this.formDataMap.startLat = row.startAgency && row.startAgency.latitude
      this.formDataMap.startLng = row.startAgency && row.startAgency.longitude
      this.formDataMap.endLat = row.endAgency && row.endAgency.latitude
      this.formDataMap.endLng = row.endAgency && row.endAgency.longitude
    },
    closeDialog() {
      this.dialogShow = false
    },
    // 初始数据
    initialDate() {
      // 读取数据
      this.clearField()
      this.getList()
      this.getAgencyList()
      // this.getLineList()
    },
    clearField() {
      if (this.lineManageSearchFormData.number === '') {
        this.$delete(this.lineManageSearchFormData, 'number')
      }
      if (this.lineManageSearchFormData.transportLineTypeId === '') {
        this.$delete(this.lineManageSearchFormData, 'transportLineTypeId')
      }
      if (this.lineManageSearchFormData.name === '') {
        this.$delete(this.lineManageSearchFormData, 'name')
      }
    },
    setField() {
      this.$set(this.lineManageSearchFormData, 'number')
      this.$set(this.lineManageSearchFormData, 'transportLineTypeId')
      this.$set(this.lineManageSearchFormData, 'name')
    },
    // 展开 获取车次列表
    async expandChange(row, rowList) {
      this.tripsData = []
      this.loadingTable = true
      if (rowList.length) {
        this.expands = []
        if (row) {
          this.expands.push(row.id)
        }
        this.transportLineId = row.id
        this.requestParameters1.transportLineId = row.id
        const { data: res } = await tripsList(this.requestParameters1)
        this.tripsData = res || []
        this.loadingTable = false
      } else {
        this.expands = []
      }
    },
    async expandChangeDia() {
      const { data: res } = await tripsList(this.requestParameters1)
      this.tripsData = res
      this.loadingTable = false
    },
    handleSelectionChange(val) {
      this.multipleSelection = val
    },
    // 获取线路类型列表数据
    async getLineList() {
      const { data: res } = await lineTypeCommonList()
      // 获取数据给options
      // 处理status只为1的状态
      this.options = res.filter((obj) => obj.status === 1)
    },
    async getListDia(data) {
      this.getList()
      this.formData = Object.assign({}, this.formData, { number: 'XL', type: '' })
      const { data: res } = await tripsList(this.requestParameters1)
      this.tripsData = res
      this.loadingTable = false

      // this.formData.number = 'XL'
      // this.formData.type = ''
    },
    // 获取列表数据
    async getList() {
      this.listLoading = true
      const { data: res } = await lineList(this.lineManageSearchFormData)
      this.listLoading = false
      // 获取数据给dataList
      this.dataList = res.items
      // 获取总条数
      this.total = res.counts
    },
    // 重置
    resetForm(lineManageSearchFormData) {
      this.setField()
      this.searchkey = false
      this.$refs[lineManageSearchFormData].resetFields()
      this.getList()
    },
    // 搜索信息
    handleFilter() {
      this.clearField()
      this.lineManageSearchFormData.page = 1
      this.getList(this.lineManageSearchFormData)
      this.searchkey = true
    },
    // 每页显示信息条数
    handleSizeChange(val) {
      this.lineManageSearchFormData.pageSize = val
      if (this.lineManageSearchFormData.page === 1) {
        this.getList(this.lineManageSearchFormData)
      }
    },
    // 进入某一页
    handleCurrentChange(val) {
      this.lineManageSearchFormData.page = val
      this.getList()
    },
    // **********************************
    // 搜索的项目
    // 添加、编辑之后表单清空
    query() {
      this.formData = {}
    },
    // 安排车辆
    async arrangeVehicles(
      lineId,
      lineName,
      tripsId,
      tripsName,
      departureTime,
      arrivalTime
    ) {
      this.titleInfo.text = '安排车辆'
      this.$set(this.formDataAv, 'lineName', lineName)
      this.formDataAv.lineId = lineId
      this.formDataAv.lineName = lineName
      this.formDataAv.tripsId = tripsId
      this.formDataAv.tripsName = tripsName
      this.formDataAv.departureTime = departureTime
      this.formDataAv.arrivalTime = arrivalTime
      this.$refs.arrangeVehicles.dialogFormV()
    },
    // 新增车次
    async handleAddTrips(id, name) {
      this.$refs.addTrips.dialogFormV()
      this.transportLineId = id
      this.transportLineName = name
      this.titleInfo.text = '新增车次'
    },
    // 编辑车次
    async handleEditTrips(id, name) {
      this.$refs.addTrips.dialogFormV()
      this.$refs.addTrips.hanldeEditTripsForm(id)
      this.titleInfo.text = '编辑车次'
      this.transportLineName = name
    },

    // 新增线路
    async handleAddLine(id) {
      if (id) {
        this.hanldeEditForm(id)
        this.titleInfo.text = '编辑线路'
      } else {
        this.$refs.addLine.dialogFormV()
        this.titleInfo.text = '新增线路'
        // this.formData.id = ''
        this.formData.lineName = ''
        this.formData.name = ''
        this.formData.lineNumber = ''
        this.formData.distance = ''
        this.formData.cost = ''
        this.formData.estimatedTime = ''
        this.formData.lineCommonName = ''
        this.formData.startAgency = null
        this.formData.endAgency = null
      }
    },
    // 更新线路
    hanldeEditForm(id) {
      lineDetail(id).then((res) => {
        const { data } = res
        this.formData.number = data.number // 线路编号
        this.formData.name = data.name // 线路名称
        this.formData.type = data.type // 线路类型
        this.formData.startAgency = data.startOrganId // 起始地机构
        this.formData.endAgency = data.endOrganId // 目的地机构
        this.formData.distance = data.distance // 距离
        this.formData.cost = data.cost // 成本
        this.formData.id = data.id
        this.$refs.addLine.dialogFormV()
      })
    },
    // 删除线路
    async handleDeleteLine(id) {
      this.handleDeleteId = ''
      this.handleDeleteId = id
      // this.dialogVisible = true
      this.$confirm('确定要删除线路吗?', '删除确认', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          this.change(id)
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '已取消'
          })
        })
    },
    // 一级表格删除重构
    sumbitDelte() {
      this.change(this.handleDeleteId)
    },
    async change(id) {
      await lineDel(id).then((res) => {
        // 异常不处理，信息不外抛。。。。
        if (String(res.code) === '200') {
          this.$message.success('删除成功')
          this.getList()
          this.dialogVisible = false
        } else {
          this.$message.error(res.msg || '删除失败')
        }
      })
    },
    // 删除车次
    async handleDelTrips(id) {
      this.handleDeleteIdTrainNumber = ''
      this.handleDeleteIdTrainNumber = id
      this.$confirm('确定要删除车次吗?', '删除确认', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          this.changeTrips(this.handleDeleteIdTrainNumber)
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '已取消'
          })
        })
    },
    sumbitDelteTrainNumber() {
      this.changeTrips(this.handleDeleteIdTrainNumber)
    },
    // 刷新二级表格接口
    async getReflaushFn() {
      // 刷新接口
      const { data: res } = await tripsList(this.requestParameters1)
      this.tripsData = res
    },
    async changeTrips(id) {
      await tripsDel(id).then((res) => {
        // this.getList()
        // 异常不处理，信息不外抛。。。。
        if (String(res.code) === '200') {
          this.$message.success('删除车次成功')
          this.getReflaushFn()
          this.getList()

          this.dialogVisibleTrainNumber = false
        } else {
          this.$message.error(res.msg || '删除车次失败')
        }
      })
    }
  }
}
</script>
<style rel="stylesheet/scss" lang="scss" scoped>
/deep/ .empty-box {
  min-height: auto !important;
  img {
    width: 102px;
    height: 79px;
  }
}
.alert {
  margin: 10px 0px;
}
.pagination {
  margin-top: 10px;
}

/deep/ .el-table__expanded-cell[class*='cell'] {
  padding: 0;
}

/deep/ .el-table .cell.el-tooltip {
  white-space: nowrap;
  min-width: 50px;
  overflow: hidden;
}

.img-div {
  cursor: pointer;
  width: 100%;
  height: 100%;
  position: relative;
  img {
    cursor: pointer;
    width: 18px;
    height: 18px;
    position: absolute;
    left: 10%;
    top: 50%;
    transform: translate(-50%, -50%);
  }
  .position-img-hover {
    display: none;
    text-align: left;
    transition: opacity 1s ease-in-out;
  }
}

.line-manage /deep/ .el-table td,
.el-table th {
  padding: 12px 0;
  white-space: nowrap;
  text-overflow: ellipsis;
  width: 120px;
}

.line-manage {
  /deep/ .children-table-box {
    background-color: white;
    box-shadow: none;
  }
  /deep/ .none {
    .el-table__body-wrapper {
      display: none;
    }
  }
  /deep/ .el-dialog__title {
    width: 73px;
    height: 25px;
    font-size: 18px;
    font-family: PingFangSC, PingFangSC-Regular;
    font-weight: 400;
    text-align: left;
    color: #20232a;
    line-height: 25px;
    letter-spacing: 0px;
  }
  /deep/ .el-dialog__body {
    text-align: center;
    padding: 20px 20px 0px 20px;
  }
  .warn-img {
    width: 40px;
  }
  p {
    height: 24px;
    font-size: 16px;
    font-family: PingFangSC, PingFangSC-Regular;
    font-weight: 400;
    color: #818693;
    line-height: 24px;
  }
}

// 下拉框
/deep/ .el-select {
  width: 100%;
}
.children-table-box {
  width: 94%;
  margin: auto;
  padding: 0 8px 8px;
  background: #f4f8fc;
  border-radius: 4px;
  box-shadow: 0px 2px 8px 0px rgba(202, 217, 229, 0.42);
  box-sizing: border-box;
  /deep/ .el-table {
    border: 0;
    .el-table__body-wrapper {
      background: #ffffff;
      border-radius: 4px;
    }
    tr {
      th {
        background: #f4f8fc !important;
        border-bottom: 0 !important;
      }
      td {
        border-bottom: 0 !important;
        padding: 4px 0 !important;
      }
      &:first-child {
        td {
          padding-top: 8px !important;
        }
      }
      &:last-child {
        td {
          padding-bottom: 8px !important;
        }
      }
    }
  }
}
</style>
<style>
.el-card {
  overflow: initial;
}
</style>
