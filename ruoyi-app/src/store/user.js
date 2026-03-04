/**
 * 用户 Store - 使用 Pinia
 */
import { defineStore } from 'pinia'
import { userApi, footprintApi } from '@/api'

export const useUserStore = defineStore('user', {
  state: () => ({
    userInfo: uni.getStorageSync('userInfo') || null,
    token: uni.getStorageSync('token') || '',
    footprint: {
      cities: [],
      spots: [],
      totalCities: 0,
      totalSpots: 0,
      totalCheckins: 0
    }
  }),
  
  getters: {
    isLoggedIn: (state) => !!state.token,
    totalCities: (state) => state.footprint.totalCities,
    totalSpots: (state) => state.footprint.totalSpots
  },
  
  actions: {
    // 设置Token
    setToken(token) {
      this.token = token
      uni.setStorageSync('token', token)
    },
    
    // 设置用户信息
    setUserInfo(userInfo) {
      this.userInfo = userInfo
      uni.setStorageSync('userInfo', userInfo)
    },
    
    // 登录
    async login(phone, code) {
      try {
        const res = await userApi.loginByPhone(phone, code)
        if (res.code === 200) {
          this.setToken(res.data.token)
          this.setUserInfo(res.data.userInfo)
          await this.getMyFootprint()
          return true
        }
        return false
      } catch (e) {
        console.error('登录失败', e)
        return false
      }
    },
    
    // 退出登录
    logout() {
      this.token = ''
      this.userInfo = null
      this.footprint = {
        cities: [],
        spots: [],
        totalCities: 0,
        totalSpots: 0,
        totalCheckins: 0
      }
      uni.removeStorageSync('token')
      uni.removeStorageSync('userInfo')
    },
    
    // 获取用户信息
    async getMyInfo() {
      try {
        const res = await userApi.getMyInfo()
        if (res.code === 200) {
          this.setUserInfo(res.data)
        }
      } catch (e) {
        console.error('获取用户信息失败', e)
      }
    },
    
    // 获取足迹
    async getMyFootprint() {
      try {
        const res = await footprintApi.getMyFootprint()
        if (res.code === 200) {
          this.footprint = {
            cities: res.cities || [],
            spots: res.spots || [],
            totalCities: res.totalCities || 0,
            totalSpots: res.totalSpots || 0,
            totalCheckins: res.totalCheckins || 0
          }
        }
      } catch (e) {
        console.error('获取足迹失败', e)
      }
    }
  }
})
