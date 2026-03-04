package com.ruoyi.system.domain.travel;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 打卡点赞对象 travel_checkin_like
 * 
 * @author 旅途
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TravelCheckinLike extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 点赞ID */
    private Long likeId;

    /** 打卡ID */
    private Long checkinId;

    /** 用户ID */
    private Long userId;

    /** 点赞时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 用户昵称 */
    private String nickname;
    
    /** 用户头像 */
    private String avatar;
}
