package com.ruoyi.system.service.impl.travel;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.system.domain.travel.TravelCity;
import com.ruoyi.system.mapper.travel.TravelCityMapper;
import com.ruoyi.system.service.travel.ITravelCityService;

/**
 * 城市Service实现
 * 
 * @author 旅途
 */
@Service
public class TravelCityServiceImpl implements ITravelCityService
{
    @Autowired
    private TravelCityMapper travelCityMapper;

    @Override
    public TravelCity selectTravelCityById(Long cityId)
    {
        return travelCityMapper.selectTravelCityById(cityId);
    }

    @Override
    public List<TravelCity> selectTravelCityList(TravelCity travelCity)
    {
        return travelCityMapper.selectTravelCityList(travelCity);
    }

    @Override
    public List<TravelCity> selectProvinceList()
    {
        // 查询所有省份(parent_id = 0)
        return travelCityMapper.selectTravelCityByParentId(0L);
    }

    @Override
    public List<TravelCity> selectCityChildren(Long parentId)
    {
        return travelCityMapper.selectTravelCityByParentId(parentId);
    }

    @Override
    public List<TravelCity> selectCheckedCitiesByUserId(Long userId)
    {
        return travelCityMapper.selectCheckedCitiesByUserId(userId);
    }

    @Override
    public int insertTravelCity(TravelCity travelCity)
    {
        return travelCityMapper.insertTravelCity(travelCity);
    }

    @Override
    public int updateTravelCity(TravelCity travelCity)
    {
        return travelCityMapper.updateTravelCity(travelCity);
    }

    @Override
    public int deleteTravelCityById(Long cityId)
    {
        return travelCityMapper.deleteTravelCityById(cityId);
    }

    @Override
    public int deleteTravelCityByIds(Long[] cityIds)
    {
        return travelCityMapper.deleteTravelCityByIds(cityIds);
    }
}
