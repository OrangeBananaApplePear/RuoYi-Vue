<template>
  <view class="login-page">
    <view class="login-header">
      <text class="app-name">旅途</text>
      <text class="app-slogan">记录你的每一次旅行</text>
    </view>
    
    <view class="login-form">
      <view class="input-group">
        <text class="input-label">手机号</text>
        <input 
          v-model="loginForm.phone" 
          type="number" 
          placeholder="请输入手机号" 
          maxlength="11"
          class="input-field"
        />
      </view>
      
      <view class="input-group">
        <text class="input-label">验证码</text>
        <view class="code-input">
          <input 
            v-model="loginForm.code" 
            type="number" 
            placeholder="请输入验证码" 
            maxlength="6"
            class="input-field"
          />
          <view 
            class="code-btn" 
            :class="{ disabled: countdown > 0 }"
            @click="sendCode"
          >
            {{ countdown > 0 ? `${countdown}s` : '获取验证码' }}
          </view>
        </view>
      </view>
      
      <button class="login-btn" @click="handleLogin">登录</button>
      
      <view class="agreement">
        <text>登录即表示同意</text>
        <text class="link">《用户协议》</text>
        <text>和</text>
        <text class="link">《隐私政策》</text>
      </view>
    </view>
  </view>
</template>

<script setup>
import { ref } from 'vue'
import { useUserStore } from '@/store/user'

const userStore = useUserStore()

const loginForm = ref({
  phone: '',
  code: ''
})
const countdown = ref(0)

let timer = null

// 发送验证码
function sendCode() {
  if (countdown.value > 0) return
  
  if (!loginForm.value.phone || loginForm.value.phone.length !== 11) {
    uni.showToast({ title: '请输入正确的手机号', icon: 'none' })
    return
  }
  
  // 这里调用发送验证码API
  uni.showToast({ title: '验证码已发送', icon: 'success' })
  
  countdown.value = 60
  timer = setInterval(() => {
    countdown.value--
    if (countdown.value <= 0) {
      clearInterval(timer)
    }
  }, 1000)
}

// 登录
async function handleLogin() {
  if (!loginForm.value.phone || loginForm.value.phone.length !== 11) {
    uni.showToast({ title: '请输入手机号', icon: 'none' })
    return
  }
  
  if (!loginForm.value.code || loginForm.value.code.length < 4) {
    uni.showToast({ title: '请输入验证码', icon: 'none' })
    return
  }
  
  uni.showLoading({ title: '登录中...' })
  
  try {
    const success = await userStore.login(loginForm.value.phone, loginForm.value.code)
    uni.hideLoading()
    
    if (success) {
      uni.showToast({ title: '登录成功', icon: 'success' })
      setTimeout(() => {
        uni.switchTab({ url: '/pages/index/index' })
      }, 1000)
    } else {
      uni.showToast({ title: '登录失败', icon: 'none' })
    }
  } catch (e) {
    uni.hideLoading()
    uni.showToast({ title: '登录失败', icon: 'none' })
  }
}
</script>

<style lang="scss" scoped>
.login-page {
  min-height: 100vh;
  background: white;
  padding: 60px 30px;
}

.login-header {
  text-align: center;
  margin-bottom: 60px;
  
  .app-name {
    font-size: 40px;
    font-weight: bold;
    color: #409EFF;
    display: block;
    margin-bottom: 12px;
  }
  
  .app-slogan {
    font-size: 16px;
    color: #999;
  }
}

.login-form {
  .input-group {
    margin-bottom: 24px;
    
    .input-label {
      font-size: 14px;
      color: #333;
      display: block;
      margin-bottom: 8px;
    }
    
    .input-field {
      width: 100%;
      height: 50px;
      border: 1px solid #eee;
      border-radius: 25px;
      padding: 0 20px;
      font-size: 16px;
      box-sizing: border-box;
    }
    
    .code-input {
      display: flex;
      gap: 12px;
      
      .input-field {
        flex: 1;
      }
      
      .code-btn {
        width: 110px;
        height: 50px;
        line-height: 50px;
        text-align: center;
        background: #409EFF;
        color: white;
        border-radius: 25px;
        font-size: 14px;
        
        &.disabled {
          background: #ccc;
        }
      }
    }
  }
  
  .login-btn {
    width: 100%;
    height: 50px;
    line-height: 50px;
    background: #409EFF;
    color: white;
    border-radius: 25px;
    font-size: 18px;
    font-weight: bold;
    margin-top: 20px;
  }
  
  .agreement {
    text-align: center;
    font-size: 12px;
    color: #999;
    margin-top: 30px;
    
    .link {
      color: #409EFF;
    }
  }
}
</style>
