<template>
  <div class="app-container">
    <!-- 查询条件 -->
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="用户ID" prop="userId">
        <el-input v-model="queryParams.userId" placeholder="请输入用户ID" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="景点ID" prop="spotId">
        <el-input v-model="queryParams.spotId" placeholder="请输入景点ID" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="是否公开" prop="isPublic">
        <el-select v-model="queryParams.isPublic" placeholder="请选择" clearable>
          <el-option label="公开" value="1" />
          <el-option label="私密" value="0" />
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
        <el-button type="danger" plain icon="el-icon-delete" :disabled="multiple" @click="handleDelete" v-hasPermi="['travel:checkin:remove']">删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <!-- 表格 -->
    <el-table v-loading="loading" :data="checkinList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="打卡ID" align="center" prop="checkinId" width="80" />
      <el-table-column label="用户" align="center" width="120">
        <template slot-scope="scope">{{ scope.row.nickname || scope.row.userId }}</template>
      </el-table-column>
      <el-table-column label="景点" align="center" prop="spotName" :show-overflow-tooltip="true" />
      <el-table-column label="城市" align="center" prop="cityName" width="100" />
      <el-table-column label="打卡内容" align="center" prop="content" :show-overflow-tooltip="true" />
      <el-table-column label="图片" align="center" width="100">
        <template slot-scope="scope">
          <image-preview v-if="scope.row.imageList && scope.row.imageList.length > 0" :src="scope.row.imageList[0]" :width="40" :height="40" />
          <span v-else>-</span>
        </template>
      </el-table-column>
      <el-table-column label="公开" align="center" prop="isPublic" width="60">
        <template slot-scope="scope">
          <el-tag :type="scope.row.isPublic === '1' ? 'success' : 'info'">{{ scope.row.isPublic === '1' ? '是' : '否' }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="点赞" align="center" prop="likeCount" width="60" />
      <el-table-column label="评论" align="center" prop="commentCount" width="60" />
      <el-table-column label="打卡时间" align="center" width="180">
        <template slot-scope="scope">{{ parseTime(scope.row.createTime) }}</template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button link type="primary" icon="el-icon-view" @click="handleView(scope.row)">查看</el-button>
          <el-button link type="primary" icon="el-icon-delete" @click="handleDelete(scope.row)" v-hasPermi="['travel:checkin:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList" />

    <!-- 打卡详情对话框 -->
    <el-dialog title="打卡详情" :visible.sync="detailVisible" width="800px" append-to-body>
      <el-form :model="checkinDetail" label-width="100px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="用户昵称">{{ checkinDetail.nickname }}</el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="景点名称">{{ checkinDetail.spotName }}</el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="打卡内容">{{ checkinDetail.content || '无' }}</el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="打卡图片">
              <div v-if="checkinDetail.imageList && checkinDetail.imageList.length > 0" class="image-list">
                <image-preview v-for="(img, index) in checkinDetail.imageList" :key="index" :src="img" :width="100" :height="100" style="margin-right: 8px; margin-bottom: 8px;" />
              </div>
              <span v-else>无</span>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="detailVisible = false">关 闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listCheckin, delCheckin } from '@/api/travel/checkin'

export default {
  name: 'TravelCheckin',
  data() {
    return {
      loading: true,
      checkinList: [],
      ids: [],
      multiple: true,
      showSearch: true,
      total: 0,
      detailVisible: false,
      checkinDetail: {},
      queryParams: { pageNum: 1, pageSize: 10, userId: undefined, spotId: undefined, isPublic: undefined }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.loading = true
      listCheckin(this.queryParams).then(response => {
        response.rows.forEach(row => {
          if (row.images) {
            try {
              row.imageList = JSON.parse(row.images)
            } catch (e) {
              row.imageList = []
            }
          } else {
            row.imageList = []
          }
        })
        this.checkinList = response.rows
        this.total = response.total
        this.loading = false
      })
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
      this.ids = selection.map(item => item.checkinId)
      this.multiple = !selection.length
    },
    handleView(row) {
      this.checkinDetail = row
      this.detailVisible = true
    },
    handleDelete(row) {
      const checkinIds = row.checkinId || this.ids
      this.$modal.confirm('是否确认删除打卡ID为"' + checkinIds + '"的数据项?').then(function() {
        return delCheckin(checkinIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess('删除成功')
      })
    }
  }
}
</script>

<style scoped>
.image-list {
  display: flex;
  flex-wrap: wrap;
}
</style>
