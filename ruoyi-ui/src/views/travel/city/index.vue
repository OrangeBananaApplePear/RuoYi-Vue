<template>
  <div class="app-container">
    <!-- 查询条件 -->
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="城市名称" prop="cityName">
        <el-input v-model="queryParams.cityName" placeholder="请输入城市名称" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="层级" prop="level">
        <el-select v-model="queryParams.level" placeholder="请选择层级" clearable @change="handleQuery">
          <el-option label="省" :value="1" />
          <el-option label="市" :value="2" />
        </el-select>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable @change="handleQuery">
          <el-option label="正常" :value="0" />
          <el-option label="停用" :value="1" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" @click="handleAdd" v-hasPermi="['travel:city:add']">新增</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <!-- 表格 -->
    <el-table v-loading="loading" :data="cityList" row-key="cityId" :tree-props="{children: 'children', hasChildren: 'hasChildren'}">
      <el-table-column label="城市名称" prop="cityName" width="200"></el-table-column>
      <el-table-column label="城市代码" prop="cityCode" width="150"></el-table-column>
      <el-table-column label="层级" prop="level" width="80">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.level === 1" type="warning">省</el-tag>
          <el-tag v-else type="success">市</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="经纬度" width="180">
        <template slot-scope="scope">{{ scope.row.latitude }}, {{ scope.row.longitude }}</template>
      </el-table-column>
      <el-table-column label="显示顺序" prop="sort" width="100"></el-table-column>
      <el-table-column label="状态" prop="status" width="80">
        <template slot-scope="scope">
          <el-switch v-model="scope.row.status" active-value="0" inactive-value="1" @change="handleStatusChange(scope.row)"></el-switch>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" prop="createTime" width="180">
        <template slot-scope="scope">{{ parseTime(scope.row.createTime) }}</template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button link type="primary" icon="el-icon-edit" @click="handleUpdate(scope.row)" v-hasPermi="['travel:city:edit']">修改</el-button>
          <el-button link type="primary" icon="el-icon-plus" @click="handleAdd(scope.row)" v-hasPermi="['travel:city:add']">新增</el-button>
          <el-button link type="primary" icon="el-icon-delete" @click="handleDelete(scope.row)" v-hasPermi="['travel:city:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改城市对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="24">
            <el-form-item label="上级城市" prop="parentId">
              <el-tree-select v-model="form.parentId" :data="cityOptions" :props="{value: 'cityId', label: 'cityName', children: 'children'}" value-key="cityId" placeholder="选择上级城市" check-strictly />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="城市名称" prop="cityName">
              <el-input v-model="form.cityName" placeholder="请输入城市名称" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="城市代码">
              <el-input v-model="form.cityCode" placeholder="请输入城市代码" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="纬度">
              <el-input v-model="form.latitude" placeholder="请输入纬度" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="经度">
              <el-input v-model="form.longitude" placeholder="请输入经度" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
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
import { listCity, treeCity, getCity, addCity, updateCity, delCity } from '@/api/travel/city'
import { handleTree } from '@/utils/ruoyi'
import Pagination from '@/components/Pagination'

export default {
  name: 'TravelCity',
  components: { Pagination },
  data() {
    return {
      total: 0,
      loading: true,
      cityList: [],
      cityOptions: [],
      showSearch: true,
      title: '',
      open: false,
      queryParams: { pageNum: 1, pageSize: 10, cityName: undefined, level: 1, status: undefined },
      form: {},
      rules: { cityName: [{ required: true, message: '城市名称不能为空', trigger: 'blur' }] }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.loading = true
      // 查询树形数据，根据搜索条件过滤
      treeCity().then(response => {
        let list = response.data
        // 根据搜索条件过滤
        if (this.queryParams.cityName) {
          list = list.filter(item => item.cityName.includes(this.queryParams.cityName))
        }
        if (this.queryParams.level) {
          list = list.filter(item => item.level === this.queryParams.level)
        }
        if (this.queryParams.status !== undefined && this.queryParams.status !== '') {
          list = list.filter(item => item.status === String(this.queryParams.status))
        }
        // 构建树形结构
        this.cityList = handleTree(list, 'cityId', 'parentId')
        this.total = list.length
        this.loading = false
      })
    },
    getTreeselect() {
      treeCity().then(response => {
        // 构建树形选择器数据，顶级节点parentId为0
        const treeData = handleTree(response.data, 'cityId', 'parentId')
        this.cityOptions = [{ cityId: 0, cityName: '顶级节点', children: treeData }]
      })
    },
    cancel() {
      this.open = false
      this.reset()
    },
    reset() {
      this.form = { cityId: undefined, parentId: 0, cityName: undefined, cityCode: undefined, level: 1, latitude: undefined, longitude: undefined, sort: 0, status: '0', remark: undefined }
      this.resetForm('form')
    },
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    resetQuery() {
      this.resetForm('queryForm')
      this.queryParams.level = 1
      this.handleQuery()
    },
    handleAdd(row) {
      this.reset()
      this.getTreeselect()
      if (row != null && row.cityId) {
        this.form.parentId = row.cityId
        this.form.level = (row.level || 0) + 1
      }
      this.open = true
      this.title = '添加城市'
    },
    handleUpdate(row) {
      this.reset()
      this.getTreeselect()
      getCity(row.cityId).then(response => {
        this.form = response.data
        // 确保parentId为数字类型，0表示顶级节点
        if (this.form.parentId === null || this.form.parentId === undefined) {
          this.form.parentId = 0
        }
        this.open = true
        this.title = '修改城市'
      })
    },
    submitForm() {
      this.$refs.form.validate(valid => {
        if (valid) {
          if (this.form.cityId != undefined) {
            updateCity(this.form).then(() => {
              this.$modal.msgSuccess('修改成功')
              this.open = false
              this.getList()
            })
          } else {
            addCity(this.form).then(() => {
              this.$modal.msgSuccess('新增成功')
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    handleDelete(row) {
      this.$modal.confirm('是否确认删除名称为"' + row.cityName + '"的数据项?').then(function() {
        return delCity(row.cityId)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess('删除成功')
      })
    },
    handleStatusChange(row) {
      const text = row.status === '0' ? '启用' : '停用'
      this.$modal.confirm('确认要' + text + '城市"' + row.cityName + '"吗?').then(function() {
        return updateCity(row)
      }).then(() => {
        this.$modal.msgSuccess(text + '成功')
      }).catch(function() {
        row.status = row.status === '0' ? '1' : '0'
      })
    }
  }
}
</script>
