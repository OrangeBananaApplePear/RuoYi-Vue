package com.ruoyi.system.mapper.travel;

import java.util.List;
import com.ruoyi.system.domain.travel.TravelCheckin;

/**
 * 打卡记录Mapper接口
 * 
 * @author 旅途
 */
public interface TravelCheckinMapper
{
    /**
     * 查询打卡记录
     */
    public TravelCheckin selectTravelCheckinById(Long checkinId);

    /**
     * 查询打卡记录列表
     */
    public List<TravelCheckin> selectTravelCheckinList(TravelCheckin travelCheckin);

    /**
     * 查询某景点的所有打卡(公开)
     */
    public List<TravelCheckin> selectPublicCheckinsBySpotId(Long spotId);

    /**
     * 查询某用户的打卡记录
     */
    public List<TravelCheckin> selectCheckinsByUserId(Long userId);

    /**
     * 查询某用户在某景点的打卡
     */
    public List<TravelCheckin> selectUserCheckinsBySpotId(Long userId, Long spotId);

    /**
     * 新增打卡记录
     */
    public int insertTravelCheckin(TravelCheckin travelCheckin);

    /**
     * 修改打卡记录
     */
    public int updateTravelCheckin(TravelCheckin travelCheckin);

    /**
     * 删除打卡记录
     */
    public int deleteTravelCheckinById(Long checkinId);

    /**
     * 批量删除打卡记录
     */
    public int deleteTravelCheckinByIds(Long[] checkinIds);

    /**
     * 增加点赞数
     */
    public int incrementLikeCount(Long checkinId);

    /**
     * 减少点赞数
     */
    public int decrementLikeCount(Long checkinId);

    /**
     * 增加评论数
     */
    public int incrementCommentCount(Long checkinId);

    /**
     * 减少评论数
     */
    public int decrementCommentCount(Long checkinId);
}
