package com.ruoyi.system.domain.travel;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 城市对象 travel_city
 * 
 * @author 旅途
 */
public class TravelCity extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 城市ID */
    private Long cityId;

    /** 父城市ID */
    private Long parentId;

    /** 城市名称 */
    @Excel(name = "城市名称")
    private String cityName;

    /** 城市代码 */
    @Excel(name = "城市代码")
    private String cityCode;

    /** 层级 */
    @Excel(name = "层级")
    private Integer level;

    /** 纬度 */
    private String latitude;

    /** 经度 */
    private String longitude;

    /** 显示顺序 */
    @Excel(name = "显示顺序")
    private Integer sort;

    /** 状态 */
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
    private String status;

    /** 删除标志 */
    private String delFlag;

    // ========== 非数据库字段 ==========
    /** 子城市列表 */
    private java.util.List<TravelCity> children;

    /** 已打卡数量(用户) */
    private Integer checkinCount;

    /** 是否已打卡(用户) */
    private Boolean isChecked;

    public Long getCityId()
    {
        return cityId;
    }

    public void setCityId(Long cityId)
    {
        this.cityId = cityId;
    }

    public Long getParentId()
    {
        return parentId;
    }

    public void setParentId(Long parentId)
    {
        this.parentId = parentId;
    }

    public String getCityName()
    {
        return cityName;
    }

    public void setCityName(String cityName)
    {
        this.cityName = cityName;
    }

    public String getCityCode()
    {
        return cityCode;
    }

    public void setCityCode(String cityCode)
    {
        this.cityCode = cityCode;
    }

    public Integer getLevel()
    {
        return level;
    }

    public void setLevel(Integer level)
    {
        this.level = level;
    }

    public String getLatitude()
    {
        return latitude;
    }

    public void setLatitude(String latitude)
    {
        this.latitude = latitude;
    }

    public String getLongitude()
    {
        return longitude;
    }

    public void setLongitude(String longitude)
    {
        this.longitude = longitude;
    }

    public Integer getSort()
    {
        return sort;
    }

    public void setSort(Integer sort)
    {
        this.sort = sort;
    }

    @Override
    public String getStatus()
    {
        return status;
    }

    @Override
    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getDelFlag()
    {
        return delFlag;
    }

    public void setDelFlag(String delFlag)
    {
        this.delFlag = delFlag;
    }

    public java.util.List<TravelCity> getChildren()
    {
        return children;
    }

    public void setChildren(java.util.List<TravelCity> children)
    {
        this.children = children;
    }

    public Integer getCheckinCount()
    {
        return checkinCount;
    }

    public void setCheckinCount(Integer checkinCount)
    {
        this.checkinCount = checkinCount;
    }

    public Boolean getIsChecked()
    {
        return isChecked;
    }

    public void setIsChecked(Boolean isChecked)
    {
        this.isChecked = isChecked;
    }

    @Override
    public String toString()
    {
        return "TravelCity{" +
                "cityId=" + cityId +
                ", parentId=" + parentId +
                ", cityName='" + cityName + '\'' +
                ", cityCode='" + cityCode + '\'' +
                ", level=" + level +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", sort=" + sort +
                ", status='" + status + '\'' +
                '}';
    }
}
