package com.ruoyi.system.service.impl.travel;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.domain.travel.TravelFootprint;
import com.ruoyi.system.domain.travel.TravelUser;
import com.ruoyi.system.mapper.travel.TravelFootprintMapper;
import com.ruoyi.system.mapper.travel.TravelUserMapper;
import com.ruoyi.system.service.travel.ITravelFootprintService;

/**
 * 用户足迹Service实现
 * 
 * @author 旅途
 */
@Service
public class TravelFootprintServiceImpl implements ITravelFootprintService
{
    @Autowired
    private TravelFootprintMapper footprintMapper;
    
    @Autowired
    private TravelUserMapper userMapper;

    @Override
    public List<TravelFootprint> selectTravelFootprintList(TravelFootprint travelFootprint)
    {
        return footprintMapper.selectTravelFootprintList(travelFootprint);
    }

    @Override
    public List<TravelFootprint> selectCitiesByUserId(Long userId)
    {
        return footprintMapper.selectCitiesByUserId(userId);
    }

    @Override
    public List<TravelFootprint> selectSpotsByUserId(Long userId)
    {
        return footprintMapper.selectSpotsByUserId(userId);
    }

    @Override
    public boolean checkCityChecked(Long userId, Long cityId)
    {
        return footprintMapper.selectCityFootprint(userId, cityId) != null;
    }

    @Override
    public boolean checkSpotChecked(Long userId, Long spotId)
    {
        return footprintMapper.selectSpotFootprint(userId, spotId) != null;
    }

    @Override
    public int addFootprint(Long userId, Long cityId, Long spotId, String type)
    {
        TravelFootprint footprint = new TravelFootprint();
        footprint.setUserId(userId);
        footprint.setCityId(cityId);
        footprint.setSpotId(spotId);
        footprint.setType(type);
        return footprintMapper.insertTravelFootprint(footprint);
    }

    @Override
    public TravelFootprint getUserStats(Long userId)
    {
        // 用户统计通过travel_user表获取
        TravelUser user = userMapper.selectTravelUserById(userId);
        if (user == null)
        {
            return null;
        }
        
        TravelFootprint stats = new TravelFootprint();
        // 这里可以返回用户的统计数据
        // 实际业务中可能需要从user表获取total_cities, total_spots等
        return stats;
    }
}
