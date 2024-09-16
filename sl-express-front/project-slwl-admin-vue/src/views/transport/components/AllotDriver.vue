<template>
  <div>
    <el-dialog
      title="分配司机"
      :visible.sync="dialogFormVisible"
      :close-on-click-modal="false"
      :before-close="dialogFormH"
      width="900px"
    >
      <div class="header">
        <div class="driver-box">
          <span>
            总司机：<span style="color: #20232a; margin-right: 20px">22</span>
          </span>
          <span>
            工作中：<span style="color: #489a48; margin-right: 20px">9</span>
          </span>
          <span> 空闲中：<span style="color: #f73717">13</span> </span>
        </div>
        <el-input
          suffix-icon="el-icon-search"
          placeholder="请输入司机姓名/电话"
        ></el-input>
      </div>
      <el-table
        v-show="dataList && dataList.length > 0"
        :key="tableKey"
        ref="multipleTable"
        :data="dataList"
        fit
        style="width: 100%"
        stripe
        :header-cell-style="{ background: 'rgba(250,252,255,1)' }"
        @selection-change="handleSelectionChange"
        ><el-table-column align="left" type="selection" width="50" />
        <el-table-column align="left" label="当前状态" min-width="100">
        </el-table-column>
        <el-table-column align="left" label="司机名称" min-width="100">
        </el-table-column>
        <el-table-column align="left" label="司机电话" min-width="100">
        </el-table-column>
        <el-table-column align="left" label="擅长车型" min-width="100">
        </el-table-column>
      </el-table>
      <div class="selected-driver">
        当前已选择 <span style="color: #e15536">2</span> 名司机
      </div>
      <!-- 分页 -->
      <div v-show="dataList && dataList.length > 0" class="pagination">
        <div class="pages">
          <el-pagination
            :current-page="Number(params.page)"
            :total="Number(total)"
            :page-size="Number(params.pageSize)"
            :page-sizes="[10, 20, 30, 50]"
            layout="total, sizes, prev, pager, next, jumper"
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
          />
        </div>
      </div>
      <empty
        v-show="(!dataList || dataList.length <= 0) && !listLoading"
      ></empty>
      <div slot="footer" class="dialog-footer" style="text-align: center">
        <el-button type="warning" class="save-btn" @click="createData"
          >确认分配</el-button
        >
        <el-button plain class="cancel-btn" @click="dialogFormH"
          >取消</el-button
        >
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: 'AllotDriver',
  data() {
    return {
      dialogFormVisible: false,
      params: {
        page: 1,
        pageSize: 10
      },
      dataList: [{}]
    }
  }
}
</script>

<style lang="scss" scoped>
.header {
  font-size: 13px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 18px;
  .el-input {
    width: 240px;
  }
}
.pagination {
  margin: 32px 0 44px !important;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 14px !important;
  color: #20232a !important;
}
.selected-driver {
  margin-top: 18px;
}
</style>
