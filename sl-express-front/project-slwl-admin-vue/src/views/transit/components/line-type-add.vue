<template>
  <div>
    <el-dialog :title="titleInfo.text" :visible.sync="dialogFormVisible" width="600px">
      <el-form
        ref="dataForm"
        :rules="ruleInline"
        :model="formBase"
        label-position="right"
        label-width="120px"
        style="width:100%;"
      >
        <el-form-item label="线路类型编号" prop="typeNumber">
          <el-input v-model="formBase.typeNumber" clearable placeholder="请输入线路类型编号" maxlength="20" />
        </el-form-item>
        <el-form-item label="线路类型名称" prop="name">
          <el-input v-model="formBase.name" clearable placeholder="请输入线路类型名称" maxlength="15" />
        </el-form-item>
        <el-form-item label="起始机构类型" prop="startAgencyTypeName">
          <el-select
            v-model="formBase.startAgencyTypeName"
            clearable
            placeholder="请选择起始机构类型"
            style="width:100%"
            @change="selectStartGet"
          >
            <el-option
              v-for="item in agencyOptions"
              :key="item.agencyType"
              :value="item.agencyTypeName"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="终点机构类型" prop="endAgencyTypeName">
          <el-select
            v-model="formBase.endAgencyTypeName"
            clearable
            style="width:100%"
            placeholder="请选择终点机构类型"
            @change="selectEndGet"
          >
            <el-option
              v-for="item in agencyOptions"
              :key="item.agencyEType"
              :value="item.agencyTypeName"
            />
          </el-select>
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
import { lineTypeAdd, lineTypeUpdate } from '@/api/transit'
var padaDate = function(value) {
  return value < 10 ? '0' + value : value
}
export default {
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
      startAgencyTypeName: '',
      endAgencyTypeName: '',
      startAgency: [],
      endAgency: [],
      agencyOptions: [
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
      dialogFormVisible: false,
      // 表单验证
      ruleInline: {
        typeNumber: [
          { required: true, message: '线路编号不能为空', trigger: 'blur' }
        ],
        name: [
          { required: true, message: '线路类型不能为空', trigger: 'blur' }
        ],
        startAgencyTypeName: [
          { required: true, message: '起始机构类型不能为空', trigger: 'blur' }
        ],
        endAgencyTypeName: [
          { required: true, message: '终点机构类型不能为空', trigger: 'blur' }
        ]
      }
    }
  },
  computed: {},
  // 挂载结束
  mounted: function() {},
  // 创建完毕状态
  created() {},
  // 组件更新
  updated: function() {},
  methods: {
    selectStartGet(vId) {
      let obj = {}
      obj = this.agencyOptions.find(item => {
        return item.agencyTypeName === vId
      })
      this.formBase.startAgencyTypeName = obj.agencyTypeName
      this.startAgency.startAgencyType = obj.agencyType
      this.startAgency.startAgencyTypeName = obj.agencyTypeName
    },
    selectEndGet(vId) {
      let obj = {}
      obj = this.agencyOptions.find(item => {
        return item.agencyTypeName === vId
      })
      this.formBase.endAgencyTypeName = obj.agencyTypeName
      this.endAgency.endAgencyType = obj.agencyType
      this.endAgency.endAgencyTypeName = obj.agencyTypeName
    },
    // 弹层显示
    dialogFormV() {
      this.dialogFormVisible = true
    },
    // 弹层隐藏
    dialogFormH() {
      this.dialogFormVisible = false
      this.$refs['dataForm'].resetFields()
    },
    // 表单提交
    createData() {
      this.$refs['dataForm'].validate(async valid => {
        if (valid) {
          var date = new Date()
          var year = date.getFullYear() // 存储年
          var month = padaDate(date.getMonth() + 1) // 存储月份
          var day = padaDate(date.getDate()) // 存储日期
          var hours = padaDate(date.getHours()) // 存储时
          var minutes = padaDate(date.getMinutes()) // 存储分
          var seconds = padaDate(date.getSeconds()) // 存储秒
          const lastTime =
            year +
            '-' +
            month +
            '-' +
            day +
            ' ' +
            hours +
            ':' +
            minutes +
            ':' +
            seconds
          this.formBase.lastUpdateTime = lastTime
          const data = {
            name: this.formBase.name,
            typeNumber: this.formBase.typeNumber,
            lastUpdateTime: lastTime,
            startAgencyType: this.startAgency.startAgencyType,
            startAgencyTypeName: this.startAgency.startAgencyTypeName,
            endAgencyType: this.endAgency.endAgencyType,
            endAgencyTypeName: this.endAgency.endAgencyTypeName
          }
          if (this.formBase.id) {
            // eslint-disable-next-line no-undef
            await lineTypeUpdate(this.formBase.id, data).then((res) => {
              //  && String(res.data.code) === '0'
              if (String(res.status) === '200') {
                // this.$message.success('线路类型修改成功')
                this.$emit('newDataes', this.formBase)
                this.dialogFormH()
              }
            })
          } else {
            // eslint-disable-next-line no-undef
            await lineTypeAdd(data).then((res) => {
              //  && String(res.data.code) === '0'
              if (String(res.status) === '200') {
                // this.$message.success('新增线路类型成功')
                this.$emit('newDataes', this.formBase)
                this.dialogFormH()
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
