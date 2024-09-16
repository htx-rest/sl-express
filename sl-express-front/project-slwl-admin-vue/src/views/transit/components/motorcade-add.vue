<template>
  <div v-if="dialogFormVisible" class="add-form">
    <el-dialog :title="titleInfo.text" :visible.sync="dialogFormVisible" width="600px">
      <el-form
        ref="dataForm"
        :rules="ruleInline"
        :model="formBase"
        label-position="right"
        label-width="110px"
        style="width: 100%;"
      >
        <!-- <el-form-item label="所属机构" prop="agencyName">
          <el-input v-model="formBase.agencyName" :disabled="true" placeholder="请输入所属机构" />
        </el-form-item> -->
        <el-form-item label="车队名称" prop="name">
          <el-input v-model="formBase.name" placeholder="请输入车队名称" maxlength="15" />
        </el-form-item>
        <el-form-item label="负责人" prop="managerName">
          <el-select
            v-model="formBase.managerName"
            value-key="item.id"
            placeholder="请选择负责人"
            style="width:100%"
            @change="selectEndGet"
          >
            <el-option v-for="item in options" :key="item.id" :value="item.name" />
          </el-select>
        </el-form-item>
        <el-form-item label="负责人电话" prop="managerMobile">
          <el-input v-model="formBase.managerMobile" placeholder="请输入负责人电话" maxlength="20" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer" style="text-align:center;">
        <el-button class="save-btn" @click="createData">确认</el-button>
        <el-button class="cancel-btn" @click="dialogFormH">取消</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import { fleetAdd, fleetUpdate } from '@/api/transit'
import { principalCommonList } from '@/api/common'
export default {
  name: 'CarModelsAdd',
  props: {
    titleInfo: {
      type: Object,
      required: true
    },
    formBase: {
      type: Object,
      required: true
    }
  },
  data() {
    return {
      requestParameters: {
        page: 1,
        pageSize: 10
      },
      agency: {},
      manager: {},
      dialogFormVisible: false,
      options: [],
      // 表单验证
      ruleInline: {
        name: [
          { required: true, message: '车队名称不能为空', trigger: 'blur' }
        ],
        managerName: [
          { required: true, message: '负责人不能为空', trigger: 'blur' }
        ]
      }
    }
  },
  computed: {},
  // 挂载结束

  mounted: function() {
  },
  // 创建完毕状态
  created() {
    this.initialData()
  },
  // 组件更新
  updated: function() {},
  methods: {
    selectDepart(val) {
      this.fleetName = val.name
    },
    // 初始数据
    initialData() {
      // 读取数据
      this.getList()
    },
    selectEndGet(vId) {
      let obj = {}
      obj = this.options.find(item => {
        return item.name === vId
      })
      this.formBase.managerName = obj.name
      this.formBase.managerMobile = obj.mobile
      this.manager.name = obj.name
      this.manager.userId = obj.userId
    },
    // 弹层显示
    dialogFormV() {
      this.dialogFormVisible = true
    },
    // 弹层隐藏
    dialogFormH() {
      this.dialogFormVisible = false
    },
    // 获取列表数据
    async getList() {
      const { data: res } = await principalCommonList()
      this.options = res
    },
    // 表单提交
    createData() {
      this.$refs['dataForm'].validate(async valid => {
        if (valid) {
          this.dialogFormH()
          const data = {
            name: this.formBase.name,
            agency: {
              id: '1213016294585143296',
              name: '北京一级转运中心'
            },
            manager: {
              userId: this.manager.userId,
              name: this.manager.name,
              mobile: this.formBase.managerMobile
            }
          }
          if (this.formBase.id) {
            await fleetUpdate(this.formBase.id, data).then((res) => {
              //  && String(res.data.code) === '0'
              if (res && res.data) {
                // this.$message({
                //   message: '车队保存成功！',
                //   type: 'success'
                // })
                this.$emit('newDataes')
              }
            })
          } else {
            await fleetAdd(data).then((res) => {
              //  && String(res.data.code) === '0'
              if (res && res.data) {
                // this.$message({
                //   message: '车队保存成功！',
                //   type: 'success'
                // })
                this.$emit('newDataes')
              }
            })
          }
        } else {
          this.$message.error('*号为必填项!')
        }
      })
    }
  }
}
</script>
<style>
.el-form--label-left .el-form-item__label {
  /* text-align: right; */
}
.el-form-item--medium {
  /* margin-bottom: 22px; */
}
.el-dialog__footer {
  /* text-align: center; */
}
</style>
