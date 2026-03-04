package com.ruoyi.system.domain.travel;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 打卡点赞对象 travel_checkin_like
 * 
 * @author 旅途
 */
public class TravelCheckinLike extends com.ruoyi.common.core.domain.BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 点赞ID */
    private Long likeId;

    /** 打卡ID */
    private Long checkinId;

    /** 用户ID */
    private Long userId;

    /** 点赞时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 用户昵称 */
    private String nickname;
    
    /** 用户头像 */
    private String avatar;

    public Long getLikeId() {
        return likeId;
    }

    public void setLikeId(Long likeId) {
        this.likeId = likeId;
    }

    public Long getCheckinId() {
        return checkinId;
    }

    public void setCheckinId(Long checkinId) {
        this.checkinId = checkinId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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
}
