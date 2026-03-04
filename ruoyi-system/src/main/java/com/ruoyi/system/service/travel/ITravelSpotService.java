package com.ruoyi.system.service.travel;

import java.util.List;
import com.ruoyi.system.domain.travel.TravelSpot;

/**
 * 景点Service接口
 * 
 * @author 旅途
 */
public interface ITravelSpotService
{
    /**
     * 查询景点
     */
    public TravelSpot selectTravelSpotById(Long spotId);

    /**
     * 查询景点列表
     */
    public List<TravelSpot> selectTravelSpotList(TravelSpot travelSpot);

    /**
     * 获取某城市的所有景点
     */
    public List<TravelSpot> selectSpotListByCityId(Long cityId);

    /**
     * 获取用户已打卡景点
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
}
