package com.ruoyi.system.mapper.travel;

import java.util.List;
import com.ruoyi.system.domain.travel.TravelSpot;

/**
 * 景点Mapper接口
 * 
 * @author 旅途
 */
public interface TravelSpotMapper
{
    /**
     * 查询景点
     */
    public TravelSpot selectTravelSpotById(Long spotId);

    /**
     * 查询城市下的景点列表
     */
    public List<TravelSpot> selectTravelSpotList(TravelSpot travelSpot);

    /**
     * 查询某城市的景点
     */
    public List<TravelSpot> selectTravelSpotByCityId(Long cityId);

    /**
     * 根据用户ID查询已打卡景点
     */
    public List<TravelSpot> selectCheckedSpotsByUserId(Long userId);

    /**
     * 新增景点
     */
    public int insertTravelSpot(TravelSpot travelSpot);

    /**
     * 修改景点
     */
    public int updateTravelSpot(TravelSpot travelSpot);

    /**
     * 删除景点
     */
    public int deleteTravelSpotById(Long spotId);

    /**
     * 批量删除景点
     */
    public int deleteTravelSpotByIds(Long[] spotIds);

    /**
     * 增加打卡人数
     */
    public int incrementCheckinCount(Long spotId);

    /**
     * 减少打卡人数
     */
    public int decrementCheckinCount(Long spotId);
}
