package com.ruoyi.system.service.travel;

import java.util.List;
import com.ruoyi.system.domain.travel.TravelCheckin;

/**
 * 打卡记录Service接口
 * 
 * @author 旅途
 */
public interface ITravelCheckinService
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
     * 获取某景点的公开打卡列表
     */
    public List<TravelCheckin> selectPublicCheckinsBySpotId(Long spotId);

    /**
     * 获取用户的所有打卡记录
     */
    public List<TravelCheckin> selectCheckinsByUserId(Long userId);

    /**
     * 获取用户在某景点的打卡
     */
    public List<TravelCheckin> selectUserCheckinsBySpotId(Long userId, Long spotId);

    /**
     * 打卡
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
     * 点赞打卡
     */
    public int likeCheckin(Long checkinId, Long userId);

    /**
     * 取消点赞
     */
    public int unlikeCheckin(Long checkinId, Long userId);

    /**
     * 评论打卡
     */
    public int commentCheckin(TravelCheckin comment);

    /**
     * 删除评论
     */
    public int deleteComment(Long commentId);
}
