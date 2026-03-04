package com.ruoyi.system.domain.travel;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 景点对象 travel_spot
 * 
 * @author 旅途
 */
public class TravelSpot extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 景点ID */
    private Long spotId;

    /** 所属城市ID */
    @Excel(name = "所属城市ID")
    private Long cityId;

    /** 城市名称(非数据库字段) */
    private String cityName;

    /** 景点名称 */
    @Excel(name = "景点名称")
    private String spotName;

    /** 封面图URL */
    private String coverImage;

    /** 简短介绍 */
    private String introduction;

    /** 详细介绍 */
    private String description;

    /** 地址 */
    private String address;

    /** 纬度 */
    private String latitude;

    /** 经度 */
    private String longitude;

    /** 门票参考 */
    private String ticketPrice;

    /** 开放时间 */
    private String openTime;

    /** 打卡人数 */
    @Excel(name = "打卡人数")
    private Integer checkinCount;

    /** 显示顺序 */
    @Excel(name = "显示顺序")
    private Integer sort;

    /** 状态 */
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
    private String status;

    /** 删除标志 */
    private String delFlag;

    // ========== 非数据库字段 ==========
    /** 是否已打卡(用户) */
    private Boolean isChecked;

    /** 用户打卡记录 */
    private java.util.List<TravelCheckin> myCheckins;

    public Long getSpotId()
    {
        return spotId;
    }

    public void setSpotId(Long spotId)
    {
        this.spotId = spotId;
    }

    public Long getCityId()
    {
        return cityId;
    }

    public void setCityId(Long cityId)
    {
        this.cityId = cityId;
    }

    public String getCityName()
    {
        return cityName;
    }

    public void setCityName(String cityName)
    {
        this.cityName = cityName;
    }

    public String getSpotName()
    {
        return spotName;
    }

    public void setSpotName(String spotName)
    {
        this.spotName = spotName;
    }

    public String getCoverImage()
    {
        return coverImage;
    }

    public void setCoverImage(String coverImage)
    {
        this.coverImage = coverImage;
    }

    public String getIntroduction()
    {
        return introduction;
    }

    public void setIntroduction(String introduction)
    {
        this.introduction = introduction;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
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

    public String getTicketPrice()
    {
        return ticketPrice;
    }

    public void setTicketPrice(String ticketPrice)
    {
        this.ticketPrice = ticketPrice;
    }

    public String getOpenTime()
    {
        return openTime;
    }

    public void setOpenTime(String openTime)
    {
        this.openTime = openTime;
    }

    public Integer getCheckinCount()
    {
        return checkinCount;
    }

    public void setCheckinCount(Integer checkinCount)
    {
        this.checkinCount = checkinCount;
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

    public Boolean getIsChecked()
    {
        return isChecked;
    }

    public void setIsChecked(Boolean isChecked)
    {
        this.isChecked = isChecked;
    }

    public java.util.List<TravelCheckin> getMyCheckins()
    {
        return myCheckins;
    }

    public void setMyCheckins(java.util.List<TravelCheckin> myCheckins)
    {
        this.myCheckins = myCheckins;
    }

    @Override
    public String toString()
    {
        return "TravelSpot{" +
                "spotId=" + spotId +
                ", cityId=" + cityId +
                ", spotName='" + spotName + '\'' +
                ", checkinCount=" + checkinCount +
                ", isChecked=" + isChecked +
                '}';
    }
}
