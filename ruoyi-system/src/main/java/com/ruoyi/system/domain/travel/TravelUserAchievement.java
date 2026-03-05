package com.ruoyi.system.domain.travel;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 用户成就记录 travel_user_achievement
 * 
 * @author ruoyi
 */
public class TravelUserAchievement
{
    private static final long serialVersionUID = 1L;

    /** 记录ID */
    private Long id;

    /** 用户ID */
    private Long userId;

    /** 成就ID */
    private Long achievementId;

    /** 达成时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date achievedTime;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 关联的成就信息（非数据库字段） */
    private TravelAchievement achievement;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getUserId()
    {
        return userId;
    }

    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public Long getAchievementId()
    {
        return achievementId;
    }

    public void setAchievementId(Long achievementId)
    {
        this.achievementId = achievementId;
    }

    public Date getAchievedTime()
    {
        return achievedTime;
    }

    public void setAchievedTime(Date achievedTime)
    {
        this.achievedTime = achievedTime;
    }

    public Date getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }

    public TravelAchievement getAchievement()
    {
        return achievement;
    }

    public void setAchievement(TravelAchievement achievement)
    {
        this.achievement = achievement;
    }
}
