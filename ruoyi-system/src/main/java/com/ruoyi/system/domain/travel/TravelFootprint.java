package com.ruoyi.system.domain.travel;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 用户足迹对象 travel_footprint
 * 
 * @author 旅途
 */
public class TravelFootprint extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 足迹ID */
    private Long footprintId;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;

    /** 城市ID */
    private Long cityId;

    /** 景点ID */
    private Long spotId;

    /** 类型(1=城市,2=景点) */
    @Excel(name = "类型", readConverterExp = "1=城市,2=景点")
    private String type;

    /** 首次打卡时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    // ========== 非数据库字段 ==========
    /** 城市名称 */
    private String cityName;
    
    /** 景点名称 */
    private String spotName;
    
    /** 封面图 */
    private String coverImage;

    public Long getFootprintId()
    {
        return footprintId;
    }

    public void setFootprintId(Long footprintId)
    {
        this.footprintId = footprintId;
    }

    public Long getUserId()
    {
        return userId;
    }

    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public Long getCityId()
    {
        return cityId;
    }

    public void setCityId(Long cityId)
    {
        this.cityId = cityId;
    }

    public Long getSpotId()
    {
        return spotId;
    }

    public void setSpotId(Long spotId)
    {
        this.spotId = spotId;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    @Override
    public Date getCreateTime()
    {
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
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

    @Override
    public String toString()
    {
        return "TravelFootprint{" +
                "footprintId=" + footprintId +
                ", userId=" + userId +
                ", cityId=" + cityId +
                ", spotId=" + spotId +
                ", type='" + type + '\'' +
                '}';
    }
}
