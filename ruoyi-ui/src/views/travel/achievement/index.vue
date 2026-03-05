<template>
  <div class="app-container">
    <!-- 查询条件 -->
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="80px">
      <el-form-item label="成就名称" prop="achievementName">
        <el-input v-model="queryParams.achievementName" placeholder="请输入成就名称" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable>
          <el-option label="启用" value="0" />
          <el-option label="禁用" value="1" />
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
        <el-button type="primary" plain icon="el-icon-plus" @click="handleAdd" v-hasPermi="['travel:achievement:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" :disabled="multiple" @click="handleDelete" v-hasPermi="['travel:achievement:remove']">删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <!-- 表格 -->
    <el-table v-loading="loading" :data="achievementList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="成就ID" align="center" prop="achievementId" width="80" />
      <el-table-column label="成就图标" align="center" width="80">
        <template slot-scope="scope">
          <el-image v-if="scope.row.icon" :src="scope.row.icon" style="width: 40px; height: 40px" fit="cover" />
          <span v-else>-</span>
        </template>
      </el-table-column>
      <el-table-column label="成就名称" align="center" prop="achievementName" width="150" />
      <el-table-column label="成就描述" align="center" prop="description" :show-overflow-tooltip="true" />
      <el-table-column label="条件类型" align="center" width="100">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.conditionType === 'city'" type="warning">城市</el-tag>
          <el-tag v-else-if="scope.row.conditionType === 'spot'" type="success">景点</el-tag>
          <el-tag v-else type="info">混合</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="时间条件" align="center" width="100">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.timeType === 'any'" type="info">任意时间</el-tag>
          <el-tag v-else type="primary">指定月份</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" width="80">
        <template slot-scope="scope">
          <el-switch v-model="scope.row.status" active-value="0" inactive-value="1" @change="handleStatusChange(scope.row)"></el-switch>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime" width="180" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button link type="primary" icon="el-icon-edit" @click="handleUpdate(scope.row)" v-hasPermi="['travel:achievement:edit']">修改</el-button>
          <el-button link type="primary" icon="el-icon-delete" @click="handleDelete(scope.row)" v-hasPermi="['travel:achievement:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList" />

    <!-- 添加或修改成就对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="成就名称" prop="achievementName">
              <el-input v-model="form.achievementName" placeholder="请输入成就名称" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="成就图标">
              <image-upload v-model="form.icon" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="成就描述">
              <el-input v-model="form.description" type="textarea" :rows="2" placeholder="请输入成就描述" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="条件类型" prop="conditionType">
              <el-radio-group v-model="form.conditionType">
                <el-radio label="city">城市</el-radio>
                <el-radio label="spot">景点</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="时间条件" prop="timeType">
              <el-radio-group v-model="form.timeType">
                <el-radio label="any">任意时间</el-radio>
                <el-radio label="specific">指定月份</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <!-- 指定月份选择 -->
          <el-col :span="24" v-if="form.timeType === 'specific'">
            <el-form-item label="选择月份">
              <el-checkbox-group v-model="selectedMonths">
                <el-checkbox :label="1">1月</el-checkbox>
                <el-checkbox :label="2">2月</el-checkbox>
                <el-checkbox :label="3">3月</el-checkbox>
                <el-checkbox :label="4">4月</el-checkbox>
                <el-checkbox :label="5">5月</el-checkbox>
                <el-checkbox :label="6">6月</el-checkbox>
                <el-checkbox :label="7">7月</el-checkbox>
                <el-checkbox :label="8">8月</el-checkbox>
                <el-checkbox :label="9">9月</el-checkbox>
                <el-checkbox :label="10">10月</el-checkbox>
                <el-checkbox :label="11">11月</el-checkbox>
                <el-checkbox :label="12">12月</el-checkbox>
              </el-checkbox-group>
            </el-form-item>
          </el-col>
          <!-- 城市/景点选择 -->
          <el-col :span="24">
            <el-form-item :label="form.conditionType === 'city' ? '选择城市' : '选择景点'">
              <el-select
                v-if="form.conditionType === 'city'"
                v-model="selectedPlaces"
                multiple
                filterable
                remote
                reserve-keyword
                placeholder="请输入城市名称搜索"
                :remote-method="searchCity"
                :loading="cityLoading"
                style="width: 100%"
              >
                <el-option
                  v-for="city in cityList"
                  :key="city.cityId"
                  :label="city.cityName"
                  :value="city.cityId"
                />
                <el-option v-if="cityHasMore" label="加载更多..." value="" disabled @click.native="loadMoreCity" />
              </el-select>
              <el-select
                v-else
                v-model="selectedPlaces"
                multiple
                filterable
                remote
                reserve-keyword
                placeholder="请输入景点名称搜索"
                :remote-method="searchSpot"
                :loading="spotLoading"
                style="width: 100%"
              >
                <el-option
                  v-for="spot in spotList"
                  :key="spot.spotId"
                  :label="spot.spotName"
                  :value="spot.spotId"
                />
                <el-option v-if="spotHasMore" label="加载更多..." value="" disabled @click.native="loadMoreSpot" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="状态">
              <el-radio-group v-model="form.status">
                <el-radio label="0">启用</el-radio>
                <el-radio label="1">禁用</el-radio>
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
import { listAchievement, getAchievement, addAchievement, updateAchievement, delAchievement } from '@/api/travel/achievement'
import { listCity, treeCity } from '@/api/travel/city'
import { listSpot } from '@/api/travel/spot'
import { handleTree } from '@/utils/ruoyi'
import Pagination from '@/components/Pagination'

export default {
  name: 'TravelAchievement',
  components: { Pagination },
  data() {
    return {
      loading: true,
      achievementList: [],
      cityTreeList: [],
      cityList: [],
      cityPageNum: 1,
      cityPageSize: 50,
      cityKeyword: '',
      cityHasMore: true,
      cityLoading: false,
      spotList: [],
      spotPageNum: 1,
      spotPageSize: 50,
      spotKeyword: '',
      spotHasMore: true,
      spotLoading: false,
      // 临时保存城市/景点的选择
      tempCityPlaces: [],
      tempSpotPlaces: [],
      ids: [],
      multiple: true,
      showSearch: true,
      total: 0,
      title: '',
      open: false,
      queryParams: { pageNum: 1, pageSize: 10, achievementName: undefined, status: undefined },
      form: {},
      rules: { 
        achievementName: [{ required: true, message: '成就名称不能为空', trigger: 'blur' }],
        conditionType: [{ required: true, message: '请选择条件类型', trigger: 'change' }],
        timeType: [{ required: true, message: '请选择时间条件', trigger: 'change' }]
      },
      selectedMonths: [],
      selectedPlaces: []
    }
  },
  created() {
    this.getList()
    this.getCityTree()
    this.getSpotList()
  },
  watch: {
    // 切换条件类型时
    'form.conditionType'(newVal, oldVal) {
      if (newVal !== oldVal) {
        // 切换时保存当前选择到对应类型的临时变量
        if (oldVal === 'city' && this.selectedPlaces.length > 0) {
          this.tempCityPlaces = [...this.selectedPlaces]
        } else if (oldVal === 'spot' && this.selectedPlaces.length > 0) {
          this.tempSpotPlaces = [...this.selectedPlaces]
        }
        // 切换到新类型时，清空当前选择
        this.selectedPlaces = []
        // 如果之前有保存的选择且现在没有新选择，则恢复
        if (newVal === 'city' && this.tempCityPlaces.length > 0) {
          this.selectedPlaces = [...this.tempCityPlaces]
        } else if (newVal === 'spot' && this.tempSpotPlaces.length > 0) {
          this.selectedPlaces = [...this.tempSpotPlaces]
        }
      }
    },
    // 监听选择变化，一旦用户做了新选择就清除旧类型的临时保存
    selectedPlaces(newVal) {
      if (newVal.length > 0) {
        if (this.form.conditionType === 'city' && this.tempSpotPlaces.length > 0) {
          // 城市有新选择，清除景点的临时保存
          this.tempSpotPlaces = []
        } else if (this.form.conditionType === 'spot' && this.tempCityPlaces.length > 0) {
          // 景点有新选择，清除城市的临时保存
          this.tempCityPlaces = []
        }
      }
    }
  },
  methods: {
    getList() {
      this.loading = true
      listAchievement(this.queryParams).then(response => {
        this.achievementList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    getCityTree() {
      treeCity().then(response => {
        this.cityTreeList = response.data || []
      })
      // 获取城市列表（分页，用于选择）
      this.loadCityList()
    },
    loadCityList() {
      this.cityLoading = true
      listCity({
        pageNum: this.cityPageNum,
        pageSize: this.cityPageSize,
        cityName: this.cityKeyword || undefined
      }).then(response => {
        if (this.cityPageNum === 1) {
          this.cityList = response.rows || []
        } else {
          this.cityList = [...this.cityList, ...(response.rows || [])]
        }
        this.cityHasMore = this.cityList.length < response.total
        this.cityLoading = false
      })
    },
    searchCity(query) {
      this.cityKeyword = query
      this.cityPageNum = 1
      this.cityHasMore = true
      this.loadCityList()
    },
    loadMoreCity() {
      this.cityPageNum++
      this.loadCityList()
    },
    getSpotList() {
      this.spotLoading = true
      listSpot({ 
        pageNum: this.spotPageNum, 
        pageSize: this.spotPageSize,
        spotName: this.spotKeyword || undefined
      }).then(response => {
        if (this.spotPageNum === 1) {
          this.spotList = response.rows || []
        } else {
          this.spotList = [...this.spotList, ...(response.rows || [])]
        }
        this.spotHasMore = this.spotList.length < response.total
        this.spotLoading = false
      })
    },
    // 搜索景点
    searchSpot(query) {
      this.spotKeyword = query
      this.spotPageNum = 1
      this.spotHasMore = true
      this.getSpotList()
    },
    // 加载更多景点
    loadMoreSpot() {
      this.spotPageNum++
      this.getSpotList()
    },
    cancel() {
      this.open = false
      this.reset()
    },
    reset() {
      this.form = { achievementId: undefined, achievementName: undefined, icon: '', description: '', conditionType: 'city', conditionCities: '', conditionSpots: '', timeType: 'any', months: '', status: '0', remark: '' }
      this.selectedMonths = []
      this.selectedPlaces = []
      // 重置临时保存的选择
      this.tempCityPlaces = []
      this.tempSpotPlaces = []
      // 重置城市和景点分页
      this.cityPageNum = 1
      this.cityKeyword = ''
      this.cityHasMore = true
      this.spotPageNum = 1
      this.spotKeyword = ''
      this.spotHasMore = true
      this.getCityTree()
      this.getSpotList()
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
      this.ids = selection.map(item => item.achievementId)
      this.multiple = !selection.length
    },
    handleAdd() {
      this.reset()
      this.open = true
      this.title = '添加成就'
    },
    handleUpdate(row) {
      this.reset()
      const achievementId = row.achievementId || this.ids
      getAchievement(achievementId).then(response => {
        this.form = response.data
        // 解析JSON字段
        if (this.form.conditionCities) {
          try {
            this.selectedPlaces = JSON.parse(this.form.conditionCities)
          } catch (e) {
            this.selectedPlaces = []
          }
        }
        if (this.form.conditionSpots) {
          try {
            this.selectedPlaces = JSON.parse(this.form.conditionSpots)
          } catch (e) {
            this.selectedPlaces = []
          }
        }
        if (this.form.months) {
          try {
            this.selectedMonths = JSON.parse(this.form.months)
          } catch (e) {
            this.selectedMonths = []
          }
        }
        this.open = true
        this.title = '修改成就'
      })
    },
    submitForm() {
      this.$refs.form.validate(valid => {
        if (valid) {
          // 处理条件数据
          if (this.form.conditionType === 'city') {
            // el-tree-select multiple 直接返回数组
            this.form.conditionCities = JSON.stringify(this.selectedPlaces)
            this.form.conditionSpots = ''
          } else {
            this.form.conditionCities = ''
            this.form.conditionSpots = JSON.stringify(this.selectedPlaces)
          }
          // 处理月份数据
          if (this.form.timeType === 'specific') {
            this.form.months = JSON.stringify(this.selectedMonths)
          } else {
            this.form.months = ''
          }
          
          if (this.form.achievementId != undefined) {
            updateAchievement(this.form).then(() => {
              this.$modal.msgSuccess('修改成功')
              this.open = false
              this.getList()
            })
          } else {
            addAchievement(this.form).then(() => {
              this.$modal.msgSuccess('新增成功')
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    handleDelete(row) {
      const achievementIds = row.achievementId || this.ids
      this.$modal.confirm('是否确认删除成就ID为"' + achievementIds + '"的数据项?').then(function() {
        return delAchievement(achievementIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess('删除成功')
      })
    },
    handleStatusChange(row) {
      const text = row.status === '0' ? '启用' : '禁用'
      this.$modal.confirm('确认要' + text + '成就"' + row.achievementName + '"吗?').then(function() {
        return updateAchievement(row)
      }).then(() => {
        this.$modal.msgSuccess(text + '成功')
      }).catch(function() {
        row.status = row.status === '0' ? '1' : '0'
      })
    }
  }
}
</script>
