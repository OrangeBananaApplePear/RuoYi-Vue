package com.ruoyi.system.mapper.travel;

import java.util.List;
import com.ruoyi.system.domain.travel.TravelFootprint;

/**
 * 用户足迹Mapper接口
 * 
 * @author 旅途
 */
public interface TravelFootprintMapper
{
    /**
     * 查询用户足迹
     */
    public TravelFootprint selectTravelFootprintById(Long footprintId);

    /**
     * 查询用户足迹列表
     */
    public List<TravelFootprint> selectTravelFootprintList(TravelFootprint travelFootprint);

    /**
     * 查询用户已打卡城市
     */
    public List<TravelFootprint> selectCitiesByUserId(Long userId);

    /**
     * 查询用户已打卡景点
     */
    public List<TravelFootprint> selectSpotsByUserId(Long userId);

    /**
     * 查询用户所有打卡记录（用于成就检测）
     */
    public List<TravelFootprint> selectTravelFootprintListByUserId(Long userId);

    /**
     * 查询用户是否已打卡某城市
     */
    public TravelFootprint selectCityFootprint(Long userId, Long cityId);

    /**
     * 查询用户是否已打卡某景点
     */
    public TravelFootprint selectSpotFootprint(Long userId, Long spotId);

    /**
     * 新增足迹
     */
    public int insertTravelFootprint(TravelFootprint travelFootprint);

    /**
     * 删除足迹
     */
    public int deleteTravelFootprintById(Long footprintId);
}
