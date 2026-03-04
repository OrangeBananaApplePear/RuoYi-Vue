<template>
  <div class="app-container">
    <!-- 查询条件 -->
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="90px">
      <el-form-item label="城市名称" prop="cityName">
        <el-input v-model="queryParams.cityName" placeholder="请输入城市/省份名称" clearable @keyup.enter.native="handleQuery" />
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

    <!-- 表格 - 树形表格 + 懒加载 -->
    <el-table v-loading="loading" :data="cityList" row-key="cityId" :load="loadChildren" lazy :tree-props="{children: 'children', hasChildren: 'hasChildren'}">
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
          <el-button v-if="scope.row.level === 1" link type="primary" icon="el-icon-edit" @click="handleUpdate(scope.row)" v-hasPermi="['travel:city:edit']">修改</el-button>
          <el-button v-if="scope.row.level === 1" link type="primary" icon="el-icon-plus" @click="handleAdd(scope.row)" v-hasPermi="['travel:city:add']">新增下级</el-button>
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
              <!-- 上级城市只显示省级城市 -->
              <el-select v-model="form.parentId" placeholder="请选择上级城市（省级或空）" clearable style="width: 100%">
                <el-option label="无（顶级）" :value="0" />
                <el-option v-for="province in provinceList" :key="province.cityId" :label="province.cityName" :value="province.cityId" />
              </el-select>
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
import { listCity, getCity, addCity, updateCity, delCity } from '@/api/travel/city'
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
      provinceList: [], // 省级列表（用于上级城市选择）
      showSearch: true,
      title: '',
      open: false,
      // 查询参数 - 只查省级，搜索时支持城市名称
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        cityName: undefined,
        level: 1 // 只查询省级
      },
      form: {},
      rules: { cityName: [{ required: true, message: '城市名称不能为空', trigger: 'blur' }] }
    }
  },
  created() {
    this.getList()
    this.getProvinceList()
  },
  methods: {
    // 获取省级列表（用于上级城市选择）
    getProvinceList() {
      listCity({ level: 1 }).then(response => {
        this.provinceList = response.rows || []
      })
    },
    // 获取省级列表数据（分页）
    getList() {
      this.loading = true
      // 查询省级数据，支持城市名称搜索
      listCity(this.queryParams).then(response => {
        // 标记是否有子节点
        const list = response.rows.map(item => {
          item.hasChildren = item.hasChildren !== undefined ? item.hasChildren : true
          return item
        })
        this.cityList = list
        this.total = response.total
        this.loading = false
      })
    },
    // 懒加载子城市（点击展开时触发）
    loadChildren(row, treeNode, resolve) {
      // 根据parentId查询子城市（不限层级，统一查询）
      listCity({ parentId: row.cityId }).then(response => {
        // 标记是否有子节点
        const children = response.rows.map(item => {
          item.hasChildren = item.level === 1 // 只有省级可能有下级
          return item
        })
        resolve(children)
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
      this.queryParams.cityName = undefined
      this.queryParams.level = 1
      this.handleQuery()
    },
    handleAdd(row) {
      this.reset()
      if (row != null && row.cityId) {
        // 新增下级城市
        this.form.parentId = row.cityId
        this.form.level = 2 // 下级城市为市级
      } else {
        // 新增省级
        this.form.parentId = 0
        this.form.level = 1
      }
      this.open = true
      this.title = '添加城市'
    },
    async handleUpdate(row) {
      this.reset()
      await getCity(row.cityId).then(response => {
        this.form = response.data
        // 处理parentId
        if (this.form.parentId === null || this.form.parentId === undefined) {
          this.form.parentId = 0
        }
      })
      this.open = true
      this.title = '修改城市'
    },
    submitForm() {
      this.$refs.form.validate(valid => {
        if (valid) {
          // 处理level：parentId为0时是省级，否则是市级
          if (this.form.parentId === 0) {
            this.form.level = 1
          } else if (this.form.parentId > 0) {
            this.form.level = 2
          }
          
          if (this.form.cityId != undefined) {
            updateCity(this.form).then(() => {
              this.$modal.msgSuccess('修改成功')
              this.open = false
              this.getList()
              this.getProvinceList() // 刷新省级列表
            })
          } else {
            addCity(this.form).then(() => {
              this.$modal.msgSuccess('新增成功')
              this.open = false
              this.getList()
              this.getProvinceList() // 刷新省级列表
            })
          }
        }
      })
    },
    handleDelete(row) {
      // 如果有children且有数据，提示先删除子节点
      if (row.children && row.children.length > 0) {
        this.$modal.msgError('请先删除下级城市')
        return
      }
      this.$modal.confirm('是否确认删除名称为"' + row.cityName + '"的数据项?').then(function() {
        return delCity(row.cityId)
      }).then(() => {
        this.getList()
        this.getProvinceList()
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
