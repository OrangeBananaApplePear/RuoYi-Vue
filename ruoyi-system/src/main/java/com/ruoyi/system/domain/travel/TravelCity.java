package com.ruoyi.system.domain.travel;

import java.util.Date;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 城市对象 travel_city
 * 
 * @author 旅途
 */
@Data
@EqualsAndHashCode(callSuper = true)
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

    /** 子城市列表 */
    private List<TravelCity> children;

    /** 已打卡数量(用户) */
    private Integer checkinCount;

    /** 是否已打卡(用户) */
    private Boolean isChecked;
}
