package com.ruoyi.system.service.impl.travel;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.domain.travel.TravelSpot;
import com.ruoyi.system.mapper.travel.TravelSpotMapper;
import com.ruoyi.system.service.travel.ITravelSpotService;

/**
 * 景点Service实现
 * 
 * @author 旅途
 */
@Service
public class TravelSpotServiceImpl implements ITravelSpotService
{
    @Autowired
    private TravelSpotMapper travelSpotMapper;

    @Override
    public TravelSpot selectTravelSpotById(Long spotId)
    {
        return travelSpotMapper.selectTravelSpotById(spotId);
    }

    @Override
    public List<TravelSpot> selectTravelSpotList(TravelSpot travelSpot)
    {
        return travelSpotMapper.selectTravelSpotList(travelSpot);
    }

    @Override
    public List<TravelSpot> selectSpotListByCityId(Long cityId)
    {
        return travelSpotMapper.selectTravelSpotByCityId(cityId);
    }

    @Override
    public List<TravelSpot> selectCheckedSpotsByUserId(Long userId)
    {
        return travelSpotMapper.selectCheckedSpotsByUserId(userId);
    }

    @Override
    public int insertTravelSpot(TravelSpot travelSpot)
    {
        return travelSpotMapper.insertTravelSpot(travelSpot);
    }

    @Override
    public int updateTravelSpot(TravelSpot travelSpot)
    {
        return travelSpotMapper.updateTravelSpot(travelSpot);
    }

    @Override
    public int deleteTravelSpotById(Long spotId)
    {
        return travelSpotMapper.deleteTravelSpotById(spotId);
    }

    @Override
    public int deleteTravelSpotByIds(Long[] spotIds)
    {
        return travelSpotMapper.deleteTravelSpotByIds(spotIds);
    }
}
