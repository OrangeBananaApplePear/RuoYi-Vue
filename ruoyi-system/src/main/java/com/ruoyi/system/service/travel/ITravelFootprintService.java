package com.ruoyi.system.service.travel;

import java.util.List;
import com.ruoyi.system.domain.travel.TravelFootprint;

/**
 * 用户足迹Service接口
 * 
 * @author 旅途
 */
public interface ITravelFootprintService
{
    /**
     * 查询用户足迹列表
     */
    public List<TravelFootprint> selectTravelFootprintList(TravelFootprint travelFootprint);

    /**
     * 获取用户已打卡城市
     */
    public List<TravelFootprint> selectCitiesByUserId(Long userId);

    /**
     * 获取用户已打卡景点
     */
    public List<TravelFootprint> selectSpotsByUserId(Long userId);

    /**
     * 检查用户是否已打卡某城市
     */
    public boolean checkCityChecked(Long userId, Long cityId);

    /**
     * 检查用户是否已打卡某景点
     */
    public boolean checkSpotChecked(Long userId, Long spotId);

    /**
     * 添加足迹
     */
    public int addFootprint(Long userId, Long cityId, Long spotId, String type);

    /**
     * 获取用户打卡统计
     */
    public TravelFootprint getUserStats(Long userId);
}
