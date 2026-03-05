package com.ruoyi.system.service.impl.travel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruoyi.system.domain.travel.TravelAchievement;
import com.ruoyi.system.domain.travel.TravelFootprint;
import com.ruoyi.system.domain.travel.TravelUserAchievement;
import com.ruoyi.system.mapper.travel.TravelFootprintMapper;
import com.ruoyi.system.service.travel.IAchievementCheckService;
import com.ruoyi.system.service.travel.ITravelAchievementService;
import com.ruoyi.system.service.travel.ITravelUserAchievementService;

/**
 * 成就检测Service实现
 * 
 * @author ruoyi
 */
@Service
public class AchievementCheckServiceImpl implements IAchievementCheckService
{
    @Autowired
    private ITravelAchievementService achievementService;
    
    @Autowired
    private ITravelUserAchievementService userAchievementService;
    
    @Autowired
    private TravelFootprintMapper footprintMapper;
    
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    @Transactional
    public List<TravelAchievement> checkAndGrantAchievements(Long userId, Long cityId, Long spotId, Date checkinTime)
    {
        List<TravelAchievement> newAchievements = new ArrayList<>();
        
        // 获取所有启用的成就
        List<TravelAchievement> enabledAchievements = achievementService.selectEnabledAchievements();
        
        // 获取用户已达成的成就ID
        List<Long> achievedIds = userAchievementService.selectAchievedAchievementIds(userId);
        
        // 获取用户所有打卡记录
        List<TravelFootprint> userFootprints = footprintMapper.selectTravelFootprintListByUserId(userId);
        
        // 构建用户已打卡的城市和景点集合
        List<Long> userCityIds = new ArrayList<>();
        List<Long> userSpotIds = new ArrayList<>();
        
        for (TravelFootprint fp : userFootprints) {
            if (fp.getCityId() != null && !userCityIds.contains(fp.getCityId())) {
                userCityIds.add(fp.getCityId());
            }
            if (fp.getSpotId() != null && !userSpotIds.contains(fp.getSpotId())) {
                userSpotIds.add(fp.getSpotId());
            }
        }
        
        // 打卡时间月份
        int checkinMonth = getMonth(checkinTime);
        
        // 检测每个成就
        for (TravelAchievement achievement : enabledAchievements) {
            // 跳过已达成的成就
            if (achievedIds.contains(achievement.getAchievementId())) {
                continue;
            }
            
            // 检测是否满足条件
            if (checkAchievement(achievement, userCityIds, userSpotIds, checkinMonth)) {
                // 授予成就
                TravelUserAchievement userAchievement = new TravelUserAchievement();
                userAchievement.setUserId(userId);
                userAchievement.setAchievementId(achievement.getAchievementId());
                userAchievement.setAchievedTime(new Date());
                userAchievementService.insertUserAchievement(userAchievement);
                
                newAchievements.add(achievement);
            }
        }
        
        return newAchievements;
    }
    
    /**
     * 检测成就是否达成
     */
    private boolean checkAchievement(TravelAchievement achievement, List<Long> userCityIds, List<Long> userSpotIds, int checkinMonth)
    {
        // 解析条件城市
        List<Long> conditionCityIds = parseJsonToList(achievement.getConditionCities(), Long.class);
        // 解析条件景点
        List<Long> conditionSpotIds = parseJsonToList(achievement.getConditionSpots(), Long.class);
        
        // 检测城市条件
        if ("city".equals(achievement.getConditionType()) && conditionCityIds != null && !conditionCityIds.isEmpty()) {
            // 必须包含所有指定城市
            for (Long cityId : conditionCityIds) {
                if (!userCityIds.contains(cityId)) {
                    return false;
                }
            }
        }
        
        // 检测景点条件
        if ("spot".equals(achievement.getConditionType()) && conditionSpotIds != null && !conditionSpotIds.isEmpty()) {
            // 必须包含所有指定景点
            for (Long spotId : conditionSpotIds) {
                if (!userSpotIds.contains(spotId)) {
                    return false;
                }
            }
        }
        
        // 混合条件：同时有城市和景点要求
        if (conditionCityIds != null && !conditionCityIds.isEmpty() && conditionSpotIds != null && !conditionSpotIds.isEmpty()) {
            // 需要同时满足城市和景点条件
            for (Long cityId : conditionCityIds) {
                if (!userCityIds.contains(cityId)) {
                    return false;
                }
            }
            for (Long spotId : conditionSpotIds) {
                if (!userSpotIds.contains(spotId)) {
                    return false;
                }
            }
        }
        
        // 检测时间条件
        if ("specific".equals(achievement.getTimeType())) {
            List<Integer> months = parseJsonToList(achievement.getMonths(), Integer.class);
            if (months == null || months.isEmpty() || !months.contains(checkinMonth)) {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * 解析JSON字符串为List
     */
    private <T> List<T> parseJsonToList(String json, Class<T> elementClass) {
        if (json == null || json.isEmpty()) {
            return new ArrayList<>();
        }
        try {
            return objectMapper.readValue(json, new TypeReference<List<T>>() {});
        } catch (JsonProcessingException e) {
            return new ArrayList<>();
        }
    }
    
    /**
     * 获取日期的月份
     */
    private int getMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MONTH) + 1; // Calendar.MONTH 是 0-11
    }
}
