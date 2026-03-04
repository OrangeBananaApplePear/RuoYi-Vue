package com.ruoyi.system.mapper.travel;

import java.util.List;
import com.ruoyi.system.domain.travel.TravelCheckinComment;

/**
 * 打卡评论Mapper接口
 * 
 * @author 旅途
 */
public interface TravelCheckinCommentMapper
{
    /**
     * 查询评论
     */
    public TravelCheckinComment selectTravelCheckinCommentById(Long commentId);

    /**
     * 查询打卡的所有评论
     */
    public List<TravelCheckinComment> selectCommentsByCheckinId(Long checkinId);

    /**
     * 新增评论
     */
    public int insertTravelCheckinComment(TravelCheckinComment travelCheckinComment);

    /**
     * 删除评论
     */
    public int deleteTravelCheckinCommentById(Long commentId);

    /**
     * 删除打卡的所有评论
     */
    public int deleteCommentsByCheckinId(Long checkinId);
}
