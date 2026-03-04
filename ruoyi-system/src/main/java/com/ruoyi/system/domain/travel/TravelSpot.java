package com.ruoyi.system.domain.travel;

import java.util.List;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 景点对象 travel_spot
 * 
 * @author 旅途
 */
@Data
@EqualsAndHashCode(callSuper = true)
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

    /** 是否已打卡(用户) */
    private Boolean isChecked;

    /** 用户打卡记录 */
    private List<TravelCheckin> myCheckins;
}
