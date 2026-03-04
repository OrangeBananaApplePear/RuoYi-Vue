package com.ruoyi.system.service.impl.travel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.domain.travel.TravelUser;
import com.ruoyi.system.mapper.travel.TravelUserMapper;
import com.ruoyi.system.service.travel.ITravelUserService;

/**
 * 用户扩展Service实现
 * 
 * @author 旅途
 */
@Service
public class TravelUserServiceImpl implements ITravelUserService
{
    @Autowired
    private TravelUserMapper travelUserMapper;

    @Override
    public TravelUser selectTravelUserById(Long userId)
    {
        return travelUserMapper.selectTravelUserById(userId);
    }

    @Override
    public TravelUser selectTravelUserByPhone(String phone)
    {
        return travelUserMapper.selectTravelUserByPhone(phone);
    }

    @Override
    public TravelUser createUser(String phone, String nickname)
    {
        // 先查询是否已存在
        TravelUser existUser = travelUserMapper.selectTravelUserByPhone(phone);
        if (existUser != null)
        {
            return existUser;
        }
        
        // 创建新用户 - userId需要从RuoYi的sys_user表获取
        // 这里暂时返回null，实际需要结合系统用户创建
        TravelUser newUser = new TravelUser();
        newUser.setPhone(phone);
        newUser.setNickname(nickname);
        newUser.setTotalCheckins(0);
        newUser.setTotalCities(0);
        newUser.setTotalSpots(0);
        
        // 实际项目中需要先创建sys_user，然后获取userId
        return null;
    }

    @Override
    public int updateTravelUser(TravelUser travelUser)
    {
        return travelUserMapper.updateTravelUser(travelUser);
    }

    @Override
    public int updateAvatar(Long userId, String avatar)
    {
        TravelUser user = new TravelUser();
        user.setUserId(userId);
        user.setAvatar(avatar);
        return travelUserMapper.updateTravelUser(user);
    }

    @Override
    public int updateNickname(Long userId, String nickname)
    {
        TravelUser user = new TravelUser();
        user.setUserId(userId);
        user.setNickname(nickname);
        return travelUserMapper.updateTravelUser(user);
    }

    @Override
    public void incrementCheckinCount(Long userId)
    {
        travelUserMapper.incrementCheckinCount(userId);
    }

    @Override
    public void addNewCityCount(Long userId)
    {
        travelUserMapper.incrementCityCount(userId);
    }

    @Override
    public void addNewSpotCount(Long userId)
    {
        travelUserMapper.incrementSpotCount(userId);
    }
}
