package com.ruoyi.system.service.impl.travel;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.domain.travel.TravelUserAchievement;
import com.ruoyi.system.mapper.travel.TravelUserAchievementMapper;
import com.ruoyi.system.service.travel.ITravelUserAchievementService;

/**
 * 用户成就Service实现
 * 
 * @author ruoyi
 */
@Service
public class TravelUserAchievementServiceImpl implements ITravelUserAchievementService
{
    @Autowired
    private TravelUserAchievementMapper userAchievementMapper;

    @Override
    public TravelUserAchievement selectUserAchievementById(Long id)
    {
        return userAchievementMapper.selectUserAchievementById(id);
    }

    @Override
    public List<TravelUserAchievement> selectUserAchievementList(TravelUserAchievement userAchievement)
    {
        return userAchievementMapper.selectUserAchievementList(userAchievement);
    }

    @Override
    public List<Long> selectAchievedAchievementIds(Long userId)
    {
        return userAchievementMapper.selectAchievedAchievementIds(userId);
    }

    @Override
    public boolean checkUserAchievementExists(Long userId, Long achievementId)
    {
        return userAchievementMapper.checkUserAchievementExists(userId, achievementId) > 0;
    }

    @Override
    public int insertUserAchievement(TravelUserAchievement userAchievement)
    {
        return userAchievementMapper.insertUserAchievement(userAchievement);
    }

    @Override
    public int deleteUserAchievementById(Long id)
    {
        return userAchievementMapper.deleteUserAchievementById(id);
    }

    @Override
    public int deleteUserAchievementByIds(Long[] ids)
    {
        return userAchievementMapper.deleteUserAchievementByIds(ids);
    }
}
