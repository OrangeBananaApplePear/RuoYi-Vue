package com.ruoyi.system.domain.travel;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户足迹对象 travel_footprint
 * 
 * @author 旅途
 */
@Data
@EqualsAndHashCode(callSuper = true)
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

    /** 城市名称 */
    private String cityName;
    
    /** 景点名称 */
    private String spotName;
    
    /** 封面图 */
    private String coverImage;
}
