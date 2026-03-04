package com.ruoyi.system.domain.travel;

import java.util.List;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 打卡评论对象 travel_checkin_comment
 * 
 * @author 旅途
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TravelCheckinComment extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 评论ID */
    private Long commentId;

    /** 打卡ID */
    @Excel(name = "打卡ID")
    private Long checkinId;

    /** 评论用户ID */
    @Excel(name = "评论用户ID")
    private Long userId;

    /** 父评论ID */
    private Long parentId;

    /** 评论内容 */
    @Excel(name = "评论内容")
    private String content;

    /** 删除标志 */
    private String delFlag;

    /** 用户昵称 */
    private String nickname;
    
    /** 用户头像 */
    private String avatar;
    
    /** 子评论列表 */
    private List<TravelCheckinComment> children;
}
