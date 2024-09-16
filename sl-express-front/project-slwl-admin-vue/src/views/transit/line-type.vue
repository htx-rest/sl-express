<template>
  <div class="dashboard-container line-type customer-list-box">
    <div class="app-container">
      <el-card shadow="never" class="search-card-box">
        <el-form ref="lineTypeSearchFormData" :model="lineTypeSearchFormData" label-width="120px">
          <!-- <div class="filter-container"> -->
          <el-row :gutter="60">
            <el-col :span="8">
              <el-form-item prop="typeNumber" label="类型编号:">
                <el-input
                  v-model="lineTypeSearchFormData.typeNumber"
                  placeholder="请输入类型编号"
                  clearable
                />
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item prop="name" label="类型名称:">
                <el-input
                  v-model="lineTypeSearchFormData.name"
                  placeholder="请输入类型名称"
                  clearable
                />
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item prop="agencyType" label="机构类型:">
                <el-select
                  v-model="lineTypeSearchFormData.agencyType"
                  clearable
                  placeholder="请选择"
                >
                  <el-option
                    v-for="item in options"
                    :key="item.agencyType"
                    :label="item.agencyTypeName"
                    :value="item.agencyType"
                  />
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-button
                @click="handleFilter"
              >搜索</el-button>
              <el-button
                plain
                class="reset-btn"
                @click="resetForm('lineTypeSearchFormData')"
              >重置</el-button>
            </el-col>
          </el-row>
          <!-- </div> -->
        </el-form>
      </el-card>
      <el-card v-loading="listLoading" element-loading-text="加载中" class="table-card-box" :class="{'table-empty-box':(!dataList || dataList.length<=0),'loading-box':listLoading}" shadow="never" style="margin-top:20px">
        <el-button
          class="customer-add-btn"
          @click="handleAddLineType()"
        >+新增线路类型</el-button>
        <el-table
          v-show="dataList && dataList.length>0"
          :key="tableKey"
          ref="multipleTable"
          :data="dataList"
          fit
          style="width:100%;"
          stripe
          :header-cell-style="{background:'rgba(250,252,255,1)'}"
          @selection-change="handleSelectionChange"
        >
          <!-- <el-table-column align="left" type="selection" width="40" /> -->
          <el-table-column align="left" type="index" label="序号" width="60">
            <template slot-scope="scope">
              <span>{{ scope.$index+(lineTypeSearchFormData.page - 1) * lineTypeSearchFormData.pageSize + 1 }}</span>
            </template>
          </el-table-column>
          <el-table-column align="left" label="线路类型编号">
            <template slot-scope="scope">
              <span>{{ scope.row.typeNumber }}</span>
            </template>
          </el-table-column>
          <el-table-column align="left" label="线路类型名称">
            <template slot-scope="scope">
              <span>{{ scope.row.name }}</span>
            </template>
          </el-table-column>
          <el-table-column align="left" label="起始机构类型">
            <template slot-scope="scope">
              <span>{{ scope.row.startAgencyTypeName }}</span>
            </template>
          </el-table-column>
          <el-table-column align="left" label="终点机构类型">
            <template slot-scope="scope">
              <span>{{ scope.row.endAgencyTypeName }}</span>
            </template>
          </el-table-column>
          <el-table-column align="left" width="160" label="最近更新时间">
            <template slot-scope="scope">
              <span>{{ scope.row.lastUpdateTime }}</span>
            </template>
          </el-table-column>
          <el-table-column align="left" label="更新人">
            <template slot-scope="scope">
              <span>{{ scope.row.updater === null ? '' : scope.row.updater.name }}</span>
            </template>
          </el-table-column>
          <!-- 操作按钮 -->
          <el-table-column
            align="left"
            label="操作"
            fixed="right"
            width="110"
            class-name="small-padding fixed-width"
          >
            <template slot-scope="scope">
              <el-link type="primary" :underline="false" @click="handleAddLineType(scope.row.id)">修改</el-link>
              <el-divider direction="vertical" />
              <el-link type="danger" :underline="false" @click="handleDelete(scope.row.id)">删除</el-link>
            </template>
          </el-table-column>
        </el-table>
        <!-- end -->
        <!-- 分页 -->
        <div v-show="dataList && dataList.length>0" class="pagination">
          <div class="pages">
            <el-pagination
              :current-page="Number(lineTypeSearchFormData.page)"
              :total="Number(total)"
              :page-size="Number(lineTypeSearchFormData.pageSize)"
              :page-sizes="[10,20,30,50]"
              layout="total, sizes, prev, pager, next, jumper"
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
            />
          </div>
        </div>
        <empty v-show="(!dataList || dataList.length<=0) && !listLoading"></empty>
        <!-- end -->
        <lineTypeDialog
          ref="addLineType"
          class="add-form-dialog"
          :title-info="titleInfo"
          :form-base="formData"
          @newDataes="getList"
        />
      </el-card>
    </div>
    <!-- 删除弹框重绘 -->
    <el-dialog class="customer-dialog" title="删除确认" :visible.sync="dialogVisible" width="394px" :before-close="handleClose">
      <img class="warn-img" src="@/assets/warn.png" alt="" />
      <p>确认删除？</p>
      <span slot="footer" class="dialog-footer" style="text-align:right;">
        <el-button type="primary" class="confirm-btn" @click="sumbitDelte">确定</el-button>
        <el-button class="cancel-btn" @click="dialogVisible = false">取消</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
import { lineTypeList, lineTypeDetail, lineTypeDel } from '@/api/transit'
import lineTypeDialog from './components/line-type-add'
export default {
  name: 'Courses',
  components: {
    lineTypeDialog
  },
  data() {
    return {
      dialogVisible: false,
      handleDeleteId: '',
      options: [
        {
          agencyType: 1,
          agencyTypeName: '分公司'
        },
        {
          agencyType: 2,
          agencyTypeName: '一级转运中心'
        },
        {
          agencyType: 3,
          agencyTypeName: '二级转运中心'
        },
        {
          agencyType: 4,
          agencyTypeName: '网点'
        }
      ],
      multipleSelection: [],
      tableKey: 0,
      dataList: [],
      total: null,
      listLoading: true,
      alertText: '',
      lineTypeSearchFormData: {
        page: 1,
        pageSize: 10,
        typeNumber: '',
        agencyType: '',
        name: ''
      },
      titleInfo: {
        pageTitle: '',
        text: ''
      },
      formData: {
        typeNumber: '',
        name: '',
        startAgencyType: '',
        startAgencyTypeName: '',
        endAgencyType: '',
        endAgencyTypeName: ''
      }
    }
  },
  computed: {
    status() {
      return status
    }
  },
  // 挂载结束
  mounted: function() {},
  // 创建完毕状态
  created() {
    var _this = this
    this.initialDate()
    // 键盘enter操作
    document.onkeydown = function(e) {
      var key = window.event.keyCode
      if (key === 13) {
        _this.handleFilter(this.lineTypeSearchFormData)
      }
    }
  },
  // 组件更新
  updated: function() {},
  methods: {
    // 删除弹框重构
    handleClose() {
      this.dialogVisible = false
    },
    // 初始数据
    initialDate() {
      // 读取数据
      this.clearField()
      this.getList()
    },
    clearField() {
      if (this.lineTypeSearchFormData.typeNumber === '') {
        this.$delete(this.lineTypeSearchFormData, 'typeNumber')
      }
      if (this.lineTypeSearchFormData.agencyType === '') {
        this.$delete(this.lineTypeSearchFormData, 'agencyType')
      }
      if (this.lineTypeSearchFormData.name === '') {
        this.$delete(this.lineTypeSearchFormData, 'name')
      }
    },
    setField() {
      this.$set(this.lineTypeSearchFormData, 'typeNumber')
      this.$set(this.lineTypeSearchFormData, 'agencyType')
      this.$set(this.lineTypeSearchFormData, 'name')
    },
    handleSelectionChange(val) {
      this.multipleSelection = val
    },
    // 获取列表数据
    async getList() {
      this.listLoading = true
      const { data: res } = await lineTypeList(this.lineTypeSearchFormData)
      this.listLoading = false
      // 获取数据给dataList
      this.dataList = res.items
      // 获取总条数
      this.total = res.counts
    },
    // 重置
    resetForm(lineTypeSearchFormData) {
      this.setField()

      this.$refs[lineTypeSearchFormData].resetFields()
      this.getList()
    },
    // 搜索信息
    handleFilter() {
      this.clearField()
      this.lineTypeSearchFormData.page = 1
      this.getList(this.lineTypeSearchFormData)
    },
    // 每页显示信息条数
    handleSizeChange(val) {
      this.lineTypeSearchFormData.pageSize = val
      if (this.lineTypeSearchFormData.page === 1) {
        this.getList(this.lineTypeSearchFormData)
      }
    },
    // 进入某一页
    handleCurrentChange(val) {
      this.lineTypeSearchFormData.page = val
      this.getList()
    },

    // **********************************
    // 搜索的项目
    // 添加、编辑之后表单清空
    query() {
      this.formData = {}
    },
    // 新增线路类型
    handleAddLineType(id) {
      if (id) {
        this.$refs.addLineType.dialogFormV()
        this.titleInfo.text = '编辑线路类型'
        this.hanldeEditForm(id)
      } else {
        this.$refs.addLineType.dialogFormV()
        this.titleInfo.text = '新增线路类型'
        this.formData.id = ''
        this.formData.name = ''
        this.formData.typeNumber = ''
        this.formData.startAgencyTypeName = ''
        this.formData.endAgencyTypeName = ''
        this.formData.startAgencyType = ''
        this.formData.endAgencyType = ''
      }
    },
    // 编辑线路类型
    async hanldeEditForm(id) {
      const { data: res } = await lineTypeDetail(id)
      this.formData.id = res.id
      this.formData.name = res.name
      this.formData.typeNumber = res.typeNumber
      this.formData.startAgencyType = res.startAgencyType
      this.formData.endAgencyType = res.endAgencyType
      this.formData.startAgencyTypeName = res.startAgencyTypeName
      this.formData.endAgencyTypeName = res.endAgencyTypeName
    },
    // 删除线路类型
    async handleDelete(id) {
      this.handleDeleteId = ''
      this.handleDeleteId = id
      this.dialogVisible = true
      // this.$confirm('确定要删除线路类型吗?', '提示', {
      //   confirmButtonText: '确定',
      //   cancelButtonText: '取消',
      //   type: 'info'
      // })
      //   .then(() => {
      //     this.change(id)
      //   })
      //   .catch(() => {
      //     this.$message({
      //       type: 'info',
      //       message: '已取消'
      //     })
      //   })
    },
    sumbitDelte() {
      this.change(this.handleDeleteId)
    },
    async change(id) {
      await lineTypeDel(id).then((res) => {
        // this.getList()
        // 异常不处理，信息不外抛。。。。
        if (res && res.data && String(res.data.code) === '0') {
          this.$message.success('删除成功')
          this.getList()
          this.dialogVisible = false
        } else {
          // this.$message.error(res.data.msg)
        }
      })
    }
  }
}
</script>
<style rel="stylesheet/scss" lang="scss" scoped>
.alert {
  margin: 10px 0px;
}
.pagination {
  margin-top: 40px;
}
.line-type {
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
</style>

<style>
/* .el-table th {
  background-color: #ffffff;
}
.el-table th.is-leaf {
  border-bottom: 2px solid #e8e8e8;
}
.disabled td {
  background-color: #f9f9f9;
  color: #c1c1c1;
}
.disabled .el-button--primary,
.disabled .el-button--danger {
  background-color: #dbdada;
  border: 1px solid #dbdada;
  color: #999;
  cursor: not-allowed;
}
.el-dropdown-menu--medium .el-dropdown-menu__item {
  width: 125px;
}
.grid-content {
  border-radius: 4px;
  min-height: 36px;
  margin-bottom: 10px;
}
.bg-purple-light {
  background: #e5e9f2;
}
.bg-purple-dark {
  background: #ffffff;
} */
</style>
