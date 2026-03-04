package com.ruoyi.system.service.impl.travel;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.system.domain.travel.TravelCheckin;
import com.ruoyi.system.domain.travel.TravelCheckinLike;
import com.ruoyi.system.domain.travel.TravelCheckinComment;
import com.ruoyi.system.domain.travel.TravelFootprint;
import com.ruoyi.system.domain.travel.TravelSpot;
import com.ruoyi.system.domain.travel.TravelUser;
import com.ruoyi.system.mapper.travel.TravelCheckinMapper;
import com.ruoyi.system.mapper.travel.TravelCheckinLikeMapper;
import com.ruoyi.system.mapper.travel.TravelCheckinCommentMapper;
import com.ruoyi.system.mapper.travel.TravelSpotMapper;
import com.ruoyi.system.mapper.travel.TravelFootprintMapper;
import com.ruoyi.system.mapper.travel.TravelUserMapper;
import com.ruoyi.system.service.travel.ITravelCheckinService;

/**
 * 打卡记录Service实现
 * 
 * @author 旅途
 */
@Service
public class TravelCheckinServiceImpl implements ITravelCheckinService
{
    @Autowired
    private TravelCheckinMapper checkinMapper;
    
    @Autowired
    private TravelCheckinLikeMapper likeMapper;
    
    @Autowired
    private TravelCheckinCommentMapper commentMapper;
    
    @Autowired
    private TravelSpotMapper spotMapper;
    
    @Autowired
    private TravelFootprintMapper footprintMapper;
    
    @Autowired
    private TravelUserMapper userMapper;

    @Override
    public TravelCheckin selectTravelCheckinById(Long checkinId)
    {
        return checkinMapper.selectTravelCheckinById(checkinId);
    }

    @Override
    public List<TravelCheckin> selectTravelCheckinList(TravelCheckin travelCheckin)
    {
        return checkinMapper.selectTravelCheckinList(travelCheckin);
    }

    @Override
    public List<TravelCheckin> selectPublicCheckinsBySpotId(Long spotId)
    {
        return checkinMapper.selectPublicCheckinsBySpotId(spotId);
    }

    @Override
    public List<TravelCheckin> selectCheckinsByUserId(Long userId)
    {
        return checkinMapper.selectCheckinsByUserId(userId);
    }

    @Override
    public List<TravelCheckin> selectUserCheckinsBySpotId(Long userId, Long spotId)
    {
        return checkinMapper.selectUserCheckinsBySpotId(userId, spotId);
    }

    @Override
    @Transactional
    public int insertTravelCheckin(TravelCheckin travelCheckin)
    {
        // 1. 添加打卡记录
        int result = checkinMapper.insertTravelCheckin(travelCheckin);
        
        // 2. 增加景点打卡人数
        spotMapper.incrementCheckinCount(travelCheckin.getSpotId());
        
        // 3. 更新用户打卡次数
        userMapper.incrementCheckinCount(travelCheckin.getUserId());
        
        // 4. 记录足迹
        TravelFootprint footprint = new TravelFootprint();
        footprint.setUserId(travelCheckin.getUserId());
        footprint.setCityId(travelCheckin.getCityId());
        footprint.setSpotId(travelCheckin.getSpotId());
        footprint.setType("2"); // 景点
        
        // 检查是否已存在足迹
        TravelFootprint existCityFootprint = footprintMapper.selectCityFootprint(
            travelCheckin.getUserId(), travelCheckin.getCityId());
        if (existCityFootprint == null)
        {
            footprint.setType("1");
            footprintMapper.insertTravelFootprint(footprint);
            userMapper.incrementCityCount(travelCheckin.getUserId());
        }
        
        TravelFootprint existSpotFootprint = footprintMapper.selectSpotFootprint(
            travelCheckin.getUserId(), travelCheckin.getSpotId());
        if (existSpotFootprint == null)
        {
            footprint.setType("2");
            footprint.setCityId(null);
            footprintMapper.insertTravelFootprint(footprint);
            userMapper.incrementSpotCount(travelCheckin.getUserId());
        }
        
        return result;
    }

    @Override
    public int updateTravelCheckin(TravelCheckin travelCheckin)
    {
        return checkinMapper.updateTravelCheckin(travelCheckin);
    }

    @Override
    @Transactional
    public int deleteTravelCheckinById(Long checkinId)
    {
        TravelCheckin checkin = checkinMapper.selectTravelCheckinById(checkinId);
        if (checkin != null)
        {
            spotMapper.decrementCheckinCount(checkin.getSpotId());
        }
        return checkinMapper.deleteTravelCheckinById(checkinId);
    }

    @Override
    public int deleteTravelCheckinByIds(Long[] checkinIds)
    {
        return checkinMapper.deleteTravelCheckinByIds(checkinIds);
    }

    @Override
    @Transactional
    public int likeCheckin(Long checkinId, Long userId)
    {
        TravelCheckinLike exist = likeMapper.selectLike(checkinId, userId);
        if (exist != null)
        {
            return 0; // 已点赞
        }
        
        TravelCheckinLike like = new TravelCheckinLike();
        like.setCheckinId(checkinId);
        like.setUserId(userId);
        likeMapper.insertTravelCheckinLike(like);
        
        return checkinMapper.incrementLikeCount(checkinId);
    }

    @Override
    @Transactional
    public int unlikeCheckin(Long checkinId, Long userId)
    {
        int result = likeMapper.deleteTravelCheckinLike(checkinId, userId);
        if (result > 0)
        {
            checkinMapper.decrementLikeCount(checkinId);
        }
        return result;
    }

    @Override
    public int commentCheckin(TravelCheckinComment comment)
    {
        int result = commentMapper.insertTravelCheckinComment(comment);
        if (result > 0)
        {
            checkinMapper.incrementCommentCount(comment.getCheckinId());
        }
        return result;
    }

    @Override
    @Transactional
    public int deleteComment(Long commentId)
    {
        TravelCheckinComment comment = commentMapper.selectTravelCheckinCommentById(commentId);
        int result = commentMapper.deleteTravelCheckinCommentById(commentId);
        if (result > 0 && comment != null)
        {
            checkinMapper.decrementCommentCount(comment.getCheckinId());
        }
        return result;
    }
}
