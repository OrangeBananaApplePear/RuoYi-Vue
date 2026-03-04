<template>
  <div class="app-container">
    <!-- 查询条件 -->
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="景点名称" prop="spotName">
        <el-input v-model="queryParams.spotName" placeholder="请输入景点名称" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="所属城市" prop="cityId">
        <el-tree-select v-model="queryParams.cityId" :data="cityTreeList" :props="{value: 'cityId', label: 'cityName', children: 'children'}" value-key="cityId" placeholder="请选择城市" check-strictly clearable />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" @click="handleAdd" v-hasPermi="['travel:spot:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" :disabled="multiple" @click="handleDelete" v-hasPermi="['travel:spot:remove']">删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <!-- 表格 -->
    <el-table v-loading="loading" :data="spotList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="景点ID" align="center" prop="spotId" width="80" />
      <el-table-column label="景点名称" align="center" prop="spotName" :show-overflow-tooltip="true" />
      <el-table-column label="所属城市" align="center" prop="cityName" width="120" />
      <el-table-column label="封面图" align="center" width="120">
        <template slot-scope="scope">
          <image-preview :src="scope.row.coverImage" :width="50" :height="50" />
        </template>
      </el-table-column>
      <el-table-column label="简介" align="center" prop="introduction" :show-overflow-tooltip="true" />
      <el-table-column label="打卡人数" align="center" prop="checkinCount" width="100" />
      <el-table-column label="门票" align="center" prop="ticketPrice" width="100" />
      <el-table-column label="状态" prop="status" width="80">
        <template slot-scope="scope">
          <el-switch v-model="scope.row.status" active-value="0" inactive-value="1" @change="handleStatusChange(scope.row)"></el-switch>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button link type="primary" icon="el-icon-edit" @click="handleUpdate(scope.row)" v-hasPermi="['travel:spot:edit']">修改</el-button>
          <el-button link type="primary" icon="el-icon-delete" @click="handleDelete(scope.row)" v-hasPermi="['travel:spot:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList" />

    <!-- 添加或修改景点对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="所属城市" prop="cityId">
              <el-select v-model="form.cityId" placeholder="请选择城市">
                <el-option v-for="city in cityList" :key="city.cityId" :label="city.cityName" :value="city.cityId" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="景点名称" prop="spotName">
              <el-input v-model="form.spotName" placeholder="请输入景点名称" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="封面图片">
              <image-upload v-model="form.coverImage" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="简短介绍">
              <el-input v-model="form.introduction" type="textarea" :rows="2" placeholder="请输入简短介绍" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="详细介绍">
              <el-input v-model="form.description" type="textarea" :rows="4" placeholder="请输入详细介绍" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="地址">
              <el-input v-model="form.address" placeholder="请输入地址" />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="纬度">
              <el-input v-model="form.latitude" placeholder="如: 39.9042" />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="经度">
              <el-input v-model="form.longitude" placeholder="如: 116.4074" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="门票参考">
              <el-input v-model="form.ticketPrice" placeholder="如: 免费" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="开放时间">
              <el-input v-model="form.openTime" placeholder="如: 09:00-18:00" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="显示顺序">
              <el-input-number v-model="form.sort" :min="0" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="状态">
              <el-radio-group v-model="form.status">
                <el-radio label="0">正常</el-radio>
                <el-radio label="1">停用</el-radio>
              </el-radio-group>
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
import { listSpot, getSpot, addSpot, updateSpot, delSpot } from '@/api/travel/spot'
import { listCity, treeCity } from '@/api/travel/city'
import { handleTree } from '@/utils/ruoyi'

export default {
  name: 'TravelSpot',
  data() {
    return {
      loading: true,
      spotList: [],
      cityList: [],
      cityTreeList: [],
      ids: [],
      multiple: true,
      showSearch: true,
      total: 0,
      title: '',
      open: false,
      queryParams: { pageNum: 1, pageSize: 10, spotName: undefined, cityId: undefined, status: undefined },
      form: {},
      rules: { cityId: [{ required: true, message: '所属城市不能为空', trigger: 'change' }], spotName: [{ required: true, message: '景点名称不能为空', trigger: 'blur' }] }
    }
  },
  created() {
    this.getList()
    this.getCityList()
  },
  methods: {
    getList() {
      this.loading = true
      listSpot(this.queryParams).then(response => {
        this.spotList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    getCityList() {
      // 获取城市列表（平铺，用于下拉选择）
      listCity({}).then(response => {
        this.cityList = response.rows
      })
      // 获取城市树形（用于搜索条件）
      treeCity().then(response => {
        this.cityTreeList = handleTree(response.data, 'cityId', 'parentId')
      })
    },
    cancel() {
      this.open = false
      this.reset()
    },
    reset() {
      this.form = { spotId: undefined, cityId: undefined, spotName: undefined, coverImage: undefined, introduction: undefined, description: undefined, address: undefined, latitude: undefined, longitude: undefined, ticketPrice: undefined, openTime: undefined, sort: 0, status: '0', remark: undefined }
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
      this.ids = selection.map(item => item.spotId)
      this.multiple = !selection.length
    },
    handleAdd() {
      this.reset()
      this.open = true
      this.title = '添加景点'
    },
    handleUpdate(row) {
      this.reset()
      const spotId = row.spotId || this.ids
      getSpot(spotId).then(response => {
        this.form = response.data
        this.open = true
        this.title = '修改景点'
      })
    },
    submitForm() {
      this.$refs.form.validate(valid => {
        if (valid) {
          if (this.form.spotId != undefined) {
            updateSpot(this.form).then(() => {
              this.$modal.msgSuccess('修改成功')
              this.open = false
              this.getList()
            })
          } else {
            addSpot(this.form).then(() => {
              this.$modal.msgSuccess('新增成功')
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    handleDelete(row) {
      const spotIds = row.spotId || this.ids
      this.$modal.confirm('是否确认删除景点ID为"' + spotIds + '"的数据项?').then(function() {
        return delSpot(spotIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess('删除成功')
      })
    },
    handleStatusChange(row) {
      const text = row.status === '0' ? '启用' : '停用'
      this.$modal.confirm('确认要' + text + '景点"' + row.spotName + '"吗?').then(function() {
        return updateSpot(row)
      }).then(() => {
        this.$modal.msgSuccess(text + '成功')
      }).catch(function() {
        row.status = row.status === '0' ? '1' : '0'
      })
    }
  }
}
</script>
