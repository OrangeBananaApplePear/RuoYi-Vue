<script setup>
import { onLaunch, onShow, onHide } from '@dcloudio/uni-app'

onLaunch(() => {
  console.log('App Launch')
  // 初始化用户信息
  const userInfo = uni.getStorageSync('userInfo')
  if (!userInfo) {
    // 未登录，跳转到登录页
    // uni.reLaunch({ url: '/pages/login/login' })
  }
})

onShow(() => {
  console.log('App Show')
})

onHide(() => {
  console.log('App Hide')
})
</script>

<style lang="scss">
@import '@/static/styles/common.scss';
</style>
