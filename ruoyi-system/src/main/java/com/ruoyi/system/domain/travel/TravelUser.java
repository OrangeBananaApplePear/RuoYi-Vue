package com.ruoyi.system.domain.travel;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 用户扩展对象 travel_user
 * 
 * @author 旅途
 */
public class TravelUser extends com.ruoyi.common.core.domain.BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 用户ID(对应sys_user) */
    private Long userId;

    /** 手机号 */
    private String phone;

    /** 昵称 */
    private String nickname;

    /** 头像URL */
    private String avatar;

    /** 累计打卡次数 */
    private Integer totalCheckins;

    /** 已打卡城市数 */
    private Integer totalCities;

    /** 已打卡景点数 */
    private Integer totalSpots;

    /** 注册时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getTotalCheckins() {
        return totalCheckins;
    }

    public void setTotalCheckins(Integer totalCheckins) {
        this.totalCheckins = totalCheckins;
    }

    public Integer getTotalCities() {
        return totalCities;
    }

    public void setTotalCities(Integer totalCities) {
        this.totalCities = totalCities;
    }

    public Integer getTotalSpots() {
        return totalSpots;
    }

    public void setTotalSpots(Integer totalSpots) {
        this.totalSpots = totalSpots;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
