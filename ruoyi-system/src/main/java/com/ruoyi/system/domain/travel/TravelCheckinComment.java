package com.ruoyi.system.domain.travel;

import java.util.Date;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 打卡评论对象 travel_checkin_comment
 * 
 * @author 旅途
 */
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

    // ========== 非数据库字段 ==========
    /** 用户昵称 */
    private String nickname;
    
    /** 用户头像 */
    private String avatar;
    
    /** 子评论列表 */
    private List<TravelCheckinComment> children;

    public Long getCommentId()
    {
        return commentId;
    }

    public void setCommentId(Long commentId)
    {
        this.commentId = commentId;
    }

    public Long getCheckinId()
    {
        return checkinId;
    }

    public void setCheckinId(Long checkinId)
    {
        this.checkinId = checkinId;
    }

    public Long getUserId()
    {
        return userId;
    }

    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public Long getParentId()
    {
        return parentId;
    }

    public void setParentId(Long parentId)
    {
        this.parentId = parentId;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public String getDelFlag()
    {
        return delFlag;
    }

    public void setDelFlag(String delFlag)
    {
        this.delFlag = delFlag;
    }

    public String getNickname()
    {
        return nickname;
    }

    public void setNickname(String nickname)
    {
        this.nickname = nickname;
    }

    public String getAvatar()
    {
        return avatar;
    }

    public void setAvatar(String avatar)
    {
        this.avatar = avatar;
    }

    public List<TravelCheckinComment> getChildren()
    {
        return children;
    }

    public void setChildren(List<TravelCheckinComment> children)
    {
        this.children = children;
    }

    @Override
    public String toString()
    {
        return "TravelCheckinComment{" +
                "commentId=" + commentId +
                ", checkinId=" + checkinId +
                ", userId=" + userId +
                ", content='" + content + '\'' +
                '}';
    }
}
