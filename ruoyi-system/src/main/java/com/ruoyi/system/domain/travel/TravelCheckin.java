package com.ruoyi.system.domain.travel;

import java.util.List;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 打卡记录对象 travel_checkin
 * 
 * @author 旅途
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TravelCheckin extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 打卡ID */
    private Long checkinId;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;

    /** 景点ID */
    @Excel(name = "景点ID")
    private Long spotId;

    /** 城市ID */
    private Long cityId;

    /** 打卡内容 */
    private String content;

    /** 图片URLs(JSON数组) */
    private String images;

    /** 打卡时纬度 */
    private String locationLat;

    /** 打卡时经度 */
    private String locationLng;

    /** 是否公开 */
    @Excel(name = "是否公开", readConverterExp = "0=私密,1=公开")
    private String isPublic;

    /** 点赞数 */
    @Excel(name = "点赞数")
    private Integer likeCount;

    /** 评论数 */
    @Excel(name = "评论数")
    private Integer commentCount;

    /** 状态 */
    private String status;

    /** 删除标志 */
    private String delFlag;

    /** 点赞状态(当前用户是否已点赞) */
    private Boolean isLiked;

    /** 用户信息(非数据库字段) */
    private String nickname;
    private String avatar;
    
    /** 景点信息 */
    private String spotName;
    private String spotCover;
    private String cityName;
    
    /** 图片列表(解析后的) */
    private List<String> imageList;

    /** 点赞列表 */
    private List<TravelCheckinLike> likeList;

    /** 评论列表 */
    private List<TravelCheckinComment> commentList;
}
