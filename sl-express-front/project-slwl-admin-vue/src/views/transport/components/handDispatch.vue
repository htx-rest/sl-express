<template>
  <div class="HandDispatch">
    <el-dialog
      title="手动调度"
      :visible.sync="dialogFormVisible"
      :close-on-click-modal="false"
      :before-close="dialogFormH"
      width="600px"
    >
      <el-form
        ref="dataForm"
        :rules="ruleInline"
        :model="formBase"
        label-position="right"
        label-width="130px"
        style="width: 100%"
      >
        <div class="info-box">
          <div class="id">
            <label>运单编号：</label>
            <span>PD2366095907995670</span>
          </div>
          <div class="type">
            <label>类型：</label>
            <span>文件</span>
          </div>
          <div class="weight">
            <label>重量：</label>
            <span>0.5kg</span>
          </div>
          <div class="volume">
            <label>体积：</label>
            <span>0.2平方米</span>
          </div>
        </div>

        <el-form-item label="可选路线：" prop="goodsTypes">
          <el-select
            ref="selectInput"
            v-model="formBase.goodsTypes"
            value-key="id"
            placeholder="请选择"
            clearable
            style="width: 100%"
            multiple
          >
            <el-option
              v-for="item in options"
              :key="item.id"
              :label="item.name"
              :value="item"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="可选车次：" prop="goodsTypes">
          <el-select
            ref="selectInput"
            v-model="formBase.goodsTypes"
            value-key="id"
            placeholder="请选择"
            clearable
            style="width: 100%"
            multiple
          >
            <el-option
              v-for="item in options"
              :key="item.id"
              :label="item.name"
              :value="item"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="可选车辆：" prop="goodsTypes">
          <el-select
            ref="selectInput"
            v-model="formBase.goodsTypes"
            value-key="id"
            placeholder="请选择"
            clearable
            style="width: 100%"
            multiple
          >
            <el-option
              v-for="item in options"
              :key="item.id"
              :label="item.name"
              :value="item"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="跟车司机：">
          <el-select
            ref="selectInput"
            v-model="formBase.goodsTypes"
            value-key="id"
            placeholder="请选择"
            clearable
            style="width: 100%"
            multiple
          >
            <el-option
              v-for="item in options"
              :key="item.id"
              :label="item.name"
              :value="item"
            />
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer" style="text-align: center">
        <el-button type="warning" class="save-btn" @click="createData"
          >确认</el-button
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
  name: 'HandDispatch',
  data() {
    return {
      dialogFormVisible: true,
      ruleInline: {},
      formBase: {
        goodsTypes: []
      }
    }
  }
}
</script>

<style lang="scss">
.HandDispatch {
  .el-dialog__body {
    padding: 40px 70px;
    padding-bottom: 0px;
  }
  .info-box {
    display: flex;
    flex-wrap: wrap;
    margin-bottom: 44px;
    .id {
      flex: 60%;
      margin-bottom: 24px;
    }
    .type {
      flex: 40px;
    }
    .weight {
      flex: 60%;
      padding-left: 28px;
    }
    .volume {
      flex: 40px;
    }
    label {
      color: #20232a;
      font-size: 14px;
    }
    span {
      color: #818693;
    }
  }
  .el-form-item__label {
    width: 90px !important;
  }
  .el-form-item__content {
    margin-left: 90px !important;
    width: 293px;
  }
}
</style>
