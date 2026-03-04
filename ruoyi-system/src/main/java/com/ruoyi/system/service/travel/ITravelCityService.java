package com.ruoyi.system.service.travel;

import java.util.List;
import com.ruoyi.system.domain.travel.TravelCity;

/**
 * 城市Service接口
 * 
 * @author 旅途
 */
public interface ITravelCityService
{
    /**
     * 查询城市
     */
    public TravelCity selectTravelCityById(Long cityId);

    /**
     * 查询城市列表
     */
    public List<TravelCity> selectTravelCityList(TravelCity travelCity);

    /**
     * 获取所有省份
     */
    public List<TravelCity> selectProvinceList();

    /**
     * 获取子城市列表
     */
    public List<TravelCity> selectCityChildren(Long parentId);

    /**
     * 获取用户已打卡城市
     */
    public List<TravelCity> selectCheckedCitiesByUserId(Long userId);

    /**
     * 新增城市
     */
    public int insertTravelCity(TravelCity travelCity);

    /**
     * 修改城市
     */
    public int updateTravelCity(TravelCity travelCity);

    /**
     * 删除城市
     */
    public int deleteTravelCityById(Long cityId);

    /**
     * 批量删除城市
     */
    public int deleteTravelCityByIds(Long[] cityIds);
}
