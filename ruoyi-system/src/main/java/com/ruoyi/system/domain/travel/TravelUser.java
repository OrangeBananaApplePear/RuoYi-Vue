package com.ruoyi.system.domain.travel;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户扩展对象 travel_user
 * 
 * @author 旅途
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TravelUser extends BaseEntity
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
    @Excel(name = "累计打卡次数")
    private Integer totalCheckins;

    /** 已打卡城市数 */
    @Excel(name = "已打卡城市数")
    private Integer totalCities;

    /** 已打卡景点数 */
    @Excel(name = "已打卡景点数")
    private Integer totalSpots;

    /** 注册时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}
