<template>
  <div class="profile-page">
    <!-- 用户信息头部 -->
    <div class="profile-header">
      <div class="avatar-section">
        <img :src="userInfo.avatar || defaultAvatar" class="user-avatar" @click="changeAvatar" />
        <div class="user-name">{{ userInfo.nickname || '旅行者' }}</div>
        <div class="user-phone" v-if="userInfo.phone">{{ maskPhone(userInfo.phone) }}</div>
      </div>
    </div>

    <!-- 统计数据 -->
    <div class="stats-card">
      <div class="stat-item" @click="goToFootprint('cities')">
        <span class="stat-num">{{ userInfo.totalCities || 0 }}</span>
        <span class="stat-label">城市</span>
      </div>
      <div class="stat-item" @click="goToFootprint('spots')">
        <span class="stat-num">{{ userInfo.totalSpots || 0 }}</span>
        <span class="stat-label">景点</span>
      </div>
      <div class="stat-item">
        <span class="stat-num">{{ userInfo.totalCheckins || 0 }}</span>
        <span class="stat-label">打卡</span>
      </div>
    </div>

    <!-- 功能菜单 -->
    <div class="menu-section">
      <div class="menu-item" @click="goToFootprint('cities')">
        <span class="menu-icon">🏙️</span>
        <span class="menu-text">我的足迹</span>
        <span class="menu-arrow">→</span>
      </div>
      <div class="menu-item" @click="goToCheckins">
        <span class="menu-icon">📸</span>
        <span class="menu-text">我的打卡</span>
        <span class="menu-arrow">→</span>
      </div>
      <div class="menu-item" @click="goToMap">
        <span class="menu-icon">🗺️</span>
        <span class="menu-text">探索中国</span>
        <span class="menu-arrow">→</span>
      </div>
    </div>

    <!-- 设置菜单 -->
    <div class="menu-section">
      <div class="menu-item" @click="editNickname">
        <span class="menu-icon">✏️</span>
        <span class="menu-text">修改昵称</span>
        <span class="menu-arrow">→</span>
      </div>
      <div class="menu-item" @click="changeAvatar">
        <span class="menu-icon">🖼️</span>
        <span class="menu-text">更换头像</span>
        <span class="menu-arrow">→</span>
      </div>
      <div class="menu-item" @click="handleLogout">
        <span class="menu-icon">🚪</span>
        <span class="menu-text">退出登录</span>
        <span class="menu-arrow">→</span>
      </div>
    </div>

    <!-- 修改昵称弹窗 -->
    <el-dialog v-model="nicknameDialogVisible" title="修改昵称" width="80%">
      <el-input v-model="nicknameForm.nickname" placeholder="请输入新昵称" />
      <template #footer>
        <el-button @click="nicknameDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitNickname">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getUserInfo, updateNickname, updateAvatar } from '@/api/travel/user'

const router = useRouter()

const userInfo = ref({})
const nicknameDialogVisible = ref(false)
const nicknameForm = ref({ nickname: '' })
const defaultAvatar = 'https://picx.zhimg.com/80/v2-default-avatar.jpg'

onMounted(async () => {
  await loadUserInfo()
})

async function loadUserInfo() {
  try {
    const res = await getUserInfo()
    if (res.code === 200) {
      userInfo.value = res.msg || res.data || {}
    }
  } catch (e) {
    console.error('获取用户信息失败', e)
  }
}

function maskPhone(phone) {
  if (!phone) return ''
  return phone.replace(/(\d{3})\d{4}(\d{4})/, '$1****$2')
}

function goToFootprint(type) {
  router.push(`/travel/footprint?type=${type}`)
}

function goToCheckins() {
  router.push('/travel/checkins')
}

function goToMap() {
  router.push('/travel/map')
}

function editNickname() {
  nicknameForm.value.nickname = userInfo.value.nickname || ''
  nicknameDialogVisible.value = true
}

async function submitNickname() {
  if (!nicknameForm.value.nickname.trim()) {
    ElMessage.warning('昵称不能为空')
    return
  }
  try {
    await updateNickname(nicknameForm.value.nickname)
    ElMessage.success('修改成功')
    nicknameDialogVisible.value = false
    await loadUserInfo()
  } catch (e) {
    ElMessage.error('修改失败')
  }
}

function changeAvatar() {
  // 这里可以调用图片上传组件
  ElMessage.info('头像上传功能开发中')
}

async function handleLogout() {
  try {
    await ElMessageBox.confirm('确定要退出登录吗？', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    // 清除登录状态
    localStorage.removeItem('token')
    router.push('/login')
  } catch (e) {
    // 用户取消
  }
}
</script>

<style scoped>
.profile-page {
  min-height: 100vh;
  background: #f5f5f5;
}

.profile-header {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  padding: 40px 20px;
  text-align: center;
  color: white;
}

.avatar-section {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.user-avatar {
  width: 80px;
  height: 80px;
  border-radius: 50%;
  border: 3px solid white;
  margin-bottom: 12px;
}

.user-name {
  font-size: 20px;
  font-weight: bold;
  margin-bottom: 4px;
}

.user-phone {
  font-size: 14px;
  opacity: 0.8;
}

.stats-card {
  display: flex;
  background: white;
  margin: -20px 16px 16px;
  border-radius: 12px;
  padding: 20px;
  box-shadow: 0 2px 12px rgba(0,0,0,0.1);
}

.stat-item {
  flex: 1;
  text-align: center;
  cursor: pointer;
}

.stat-num {
  display: block;
  font-size: 24px;
  font-weight: bold;
  color: #409eff;
  margin-bottom: 4px;
}

.stat-label {
  font-size: 12px;
  color: #999;
}

.menu-section {
  background: white;
  margin: 0 16px 16px;
  border-radius: 12px;
  overflow: hidden;
}

.menu-item {
  display: flex;
  align-items: center;
  padding: 16px;
  border-bottom: 1px solid #f0f0f0;
  cursor: pointer;
}

.menu-item:last-child {
  border-bottom: none;
}

.menu-icon {
  font-size: 20px;
  margin-right: 12px;
}

.menu-text {
  flex: 1;
  font-size: 16px;
}

.menu-arrow {
  color: #999;
}
</style>
