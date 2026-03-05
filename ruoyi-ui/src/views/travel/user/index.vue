<template>
  <div class="app-container">
    <!-- 查询条件 -->
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="80px">
      <el-form-item label="手机号" prop="phone">
        <el-input v-model="queryParams.phone" placeholder="请输入手机号" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="昵称" prop="nickname">
        <el-input v-model="queryParams.nickname" placeholder="请输入昵称" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" @click="handleAdd" v-hasPermi="['travel:user:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" :disabled="multiple" @click="handleDelete" v-hasPermi="['travel:user:remove']">删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <!-- 表格 -->
    <el-table v-loading="loading" :data="userList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="用户ID" align="center" prop="userId" width="80" />
      <el-table-column label="头像" align="center" width="80">
        <template slot-scope="scope">
          <el-avatar v-if="scope.row.avatar" :src="scope.row.avatar" :size="40" />
          <el-avatar v-else :size="40" icon="el-icon-user" />
        </template>
      </el-table-column>
      <el-table-column label="昵称" align="center" prop="nickname" width="120" />
      <el-table-column label="手机号" align="center" prop="phone" width="130" />
      <el-table-column label="累计打卡" align="center" prop="totalCheckins" width="100" />
      <el-table-column label="已打卡城市" align="center" prop="totalCities" width="100" />
      <el-table-column label="已打卡景点" align="center" prop="totalSpots" width="100" />
      <el-table-column label="已获成就" align="center" prop="totalAchievements" width="100" />
      <el-table-column label="注册时间" align="center" prop="createTime" width="180" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button link type="primary" icon="el-icon-edit" @click="handleUpdate(scope.row)" v-hasPermi="['travel:user:edit']">修改</el-button>
          <el-button link type="primary" icon="el-icon-delete" @click="handleDelete(scope.row)" v-hasPermi="['travel:user:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList" />

    <!-- 添加或修改用户对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="手机号" prop="phone">
              <el-input v-model="form.phone" placeholder="请输入手机号" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="昵称" prop="nickname">
              <el-input v-model="form.nickname" placeholder="请输入昵称" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="头像">
              <image-upload v-model="form.avatar" />
            </el-form-item>
          </el-col>
        </el-row>
        <!-- APP登录信息 -->
        <el-divider content-position="left">APP登录信息</el-divider>
        <el-row>
          <el-col :span="12">
            <el-form-item label="APP账号" prop="appAccount">
              <el-input v-model="form.appAccount" placeholder="请输入APP账号" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="APP密码" prop="appPassword">
              <el-input v-model="form.appPassword" placeholder="请输入APP密码" type="password" show-password />
            </el-form-item>
          </el-col>
        </el-row>
        <!-- 打卡统计（只读） -->
        <el-divider content-position="left">打卡统计</el-divider>
        <el-row>
          <el-col :span="6">
            <el-form-item label="累计打卡">
              <el-input v-model="form.totalCheckins" disabled />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="已打卡城市">
              <el-input v-model="form.totalCities" disabled />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="已打卡景点">
              <el-input v-model="form.totalSpots" disabled />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="已获成就">
              <el-input v-model="form.totalAchievements" disabled />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listUser, getUser, addUser, updateUser, delUser } from '@/api/travel/user'
import Pagination from '@/components/Pagination'

export default {
  name: 'TravelUser',
  components: { Pagination },
  data() {
    return {
      loading: true,
      userList: [],
      ids: [],
      multiple: true,
      showSearch: true,
      total: 0,
      title: '',
      open: false,
      queryParams: { pageNum: 1, pageSize: 10, phone: undefined, nickname: undefined },
      form: {},
      rules: { phone: [{ required: true, message: '手机号不能为空', trigger: 'blur' }] }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.loading = true
      listUser(this.queryParams).then(response => {
        this.userList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    cancel() {
      this.open = false
      this.reset()
    },
    reset() {
      this.form = {
        userId: undefined,
        phone: undefined,
        nickname: undefined,
        avatar: '',
        totalCheckins: 0,
        totalCities: 0,
        totalSpots: 0,
        totalAchievements: 0,
        appAccount: undefined,
        appPassword: undefined
      }
      this.resetForm('form')
    },
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    resetQuery() {
      this.resetForm('queryForm')
      this.handleQuery()
    },
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.userId)
      this.multiple = !selection.length
    },
    handleAdd() {
      this.reset()
      this.open = true
      this.title = '添加用户'
    },
    handleUpdate(row) {
      this.reset()
      const userId = row.userId || this.ids[0]
      getUser(userId).then(response => {
        this.form = response.data
        this.open = true
        this.title = '修改用户'
      })
    },
    submitForm() {
      this.$refs.form.validate(valid => {
        if (valid) {
          if (this.form.userId != undefined) {
            updateUser(this.form).then(() => {
              this.$modal.msgSuccess('修改成功')
              this.open = false
              this.getList()
            })
          } else {
            addUser(this.form).then(() => {
              this.$modal.msgSuccess('新增成功')
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    handleDelete(row) {
      const userIds = row.userId || this.ids
      this.$modal.confirm('是否确认删除用户ID为"' + userIds + '"的数据项?').then(function() {
        return delUser(userIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess('删除成功')
      })
    }
  }
}
</script>
