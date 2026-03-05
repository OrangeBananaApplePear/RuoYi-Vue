package com.ruoyi.system.domain.travel;

import java.util.Date;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 成就对象 travel_achievement
 * 
 * @author ruoyi
 */
public class TravelAchievement
{
    private static final long serialVersionUID = 1L;

    /** 成就ID */
    private Long achievementId;

    /** 成就名称 */
    private String achievementName;

    /** 成就图标 */
    private String icon;

    /** 成就描述 */
    private String description;

    /** 条件类型: city-城市, spot-景点 */
    private String conditionType;

    /** 条件城市ID列表 */
    private String conditionCities;

    /** 条件景点ID列表 */
    private String conditionSpots;

    /** 时间类型: any-任意时间, specific-指定月份 */
    private String timeType;

    /** 指定月份 */
    private String months;

    /** 状态: 0-启用, 1-禁用 */
    private String status;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /** 备注 */
    private String remark;

    /** 条件城市列表（非数据库字段） */
    private List<Long> conditionCityList;

    /** 条件景点列表（非数据库字段） */
    private List<Long> conditionSpotList;

    /** 选中月份列表（非数据库字段） */
    private List<Integer> monthList;

    public Long getAchievementId()
    {
        return achievementId;
    }

    public void setAchievementId(Long achievementId)
    {
        this.achievementId = achievementId;
    }

    public String getAchievementName()
    {
        return achievementName;
    }

    public void setAchievementName(String achievementName)
    {
        this.achievementName = achievementName;
    }

    public String getIcon()
    {
        return icon;
    }

    public void setIcon(String icon)
    {
        this.icon = icon;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getConditionType()
    {
        return conditionType;
    }

    public void setConditionType(String conditionType)
    {
        this.conditionType = conditionType;
    }

    public String getConditionCities()
    {
        return conditionCities;
    }

    public void setConditionCities(String conditionCities)
    {
        this.conditionCities = conditionCities;
    }

    public String getConditionSpots()
    {
        return conditionSpots;
    }

    public void setConditionSpots(String conditionSpots)
    {
        this.conditionSpots = conditionSpots;
    }

    public String getTimeType()
    {
        return timeType;
    }

    public void setTimeType(String timeType)
    {
        this.timeType = timeType;
    }

    public String getMonths()
    {
        return months;
    }

    public void setMonths(String months)
    {
        this.months = months;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public Date getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }

    public Date getUpdateTime()
    {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime)
    {
        this.updateTime = updateTime;
    }

    public String getRemark()
    {
        return remark;
    }

    public void setRemark(String remark)
    {
        this.remark = remark;
    }

    public List<Long> getConditionCityList()
    {
        return conditionCityList;
    }

    public void setConditionCityList(List<Long> conditionCityList)
    {
        this.conditionCityList = conditionCityList;
    }

    public List<Long> getConditionSpotList()
    {
        return conditionSpotList;
    }

    public void setConditionSpotList(List<Long> conditionSpotList)
    {
        this.conditionSpotList = conditionSpotList;
    }

    public List<Integer> getMonthList()
    {
        return monthList;
    }

    public void setMonthList(List<Integer> monthList)
    {
        this.monthList = monthList;
    }
}
