package com.ruoyi.system.mapper.travel;

import java.util.List;
import com.ruoyi.system.domain.travel.TravelCheckinLike;

/**
 * 打卡点赞Mapper接口
 * 
 * @author 旅途
 */
public interface TravelCheckinLikeMapper
{
    /**
     * 查询是否已点赞
     */
    public TravelCheckinLike selectLike(Long checkinId, Long userId);

    /**
     * 查询打卡的所有点赞
     */
    public List<TravelCheckinLike> selectLikesByCheckinId(Long checkinId);

    /**
     * 新增点赞
     */
    public int insertTravelCheckinLike(TravelCheckinLike travelCheckinLike);

    /**
     * 删除点赞
     */
    public int deleteTravelCheckinLike(Long checkinId, Long userId);

    /**
     * 删除打卡的所有点赞
     */
    public int deleteLikesByCheckinId(Long checkinId);
}
