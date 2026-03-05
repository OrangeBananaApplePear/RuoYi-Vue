package com.ruoyi.system.service.impl.travel;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.domain.travel.TravelAchievement;
import com.ruoyi.system.mapper.travel.TravelAchievementMapper;
import com.ruoyi.system.service.travel.ITravelAchievementService;

/**
 * 成就Service实现
 * 
 * @author ruoyi
 */
@Service
public class TravelAchievementServiceImpl implements ITravelAchievementService
{
    @Autowired
    private TravelAchievementMapper travelAchievementMapper;

    @Override
    public TravelAchievement selectTravelAchievementById(Long achievementId)
    {
        return travelAchievementMapper.selectTravelAchievementById(achievementId);
    }

    @Override
    public List<TravelAchievement> selectTravelAchievementList(TravelAchievement travelAchievement)
    {
        return travelAchievementMapper.selectTravelAchievementList(travelAchievement);
    }

    @Override
    public List<TravelAchievement> selectEnabledAchievements()
    {
        return travelAchievementMapper.selectEnabledAchievements();
    }

    @Override
    public int insertTravelAchievement(TravelAchievement travelAchievement)
    {
        return travelAchievementMapper.insertTravelAchievement(travelAchievement);
    }

    @Override
    public int updateTravelAchievement(TravelAchievement travelAchievement)
    {
        return travelAchievementMapper.updateTravelAchievement(travelAchievement);
    }

    @Override
    public int deleteTravelAchievementById(Long achievementId)
    {
        return travelAchievementMapper.deleteTravelAchievementById(achievementId);
    }

    @Override
    public int deleteTravelAchievementByIds(Long[] achievementIds)
    {
        return travelAchievementMapper.deleteTravelAchievementByIds(achievementIds);
    }
}
