package com.ruoyi.web.api.travel;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.domain.travel.TravelCheckin;
import com.ruoyi.system.domain.travel.TravelCheckinComment;
import com.ruoyi.system.service.travel.ITravelCheckinService;

/**
 * 移动端-打卡接口
 */
@RestController
@RequestMapping("/api/travel/checkin")
public class TravelCheckinApiController extends BaseController
{
    @Autowired
    private ITravelCheckinService travelCheckinService;

    @GetMapping("/public/{spotId}")
    public AjaxResult publicList(@PathVariable Long spotId)
    {
        return success(travelCheckinService.selectPublicCheckinsBySpotId(spotId));
    }

    @GetMapping("/user/{userId}")
    public AjaxResult userList(@PathVariable Long userId)
    {
        return success(travelCheckinService.selectCheckinsByUserId(userId));
    }

    @GetMapping("/user/{userId}/spot/{spotId}")
    public AjaxResult userSpotList(@PathVariable Long userId, @PathVariable Long spotId)
    {
        return success(travelCheckinService.selectUserCheckinsBySpotId(userId, spotId));
    }

    @PostMapping
    public AjaxResult add(@RequestBody TravelCheckin travelCheckin)
    {
        Long userId = SecurityUtils.getUserId();
        travelCheckin.setUserId(userId);
        travelCheckin.setCreateBy(String.valueOf(userId));
        return toAjax(travelCheckinService.insertTravelCheckin(travelCheckin));
    }

    @PostMapping("/like/{checkinId}")
    public AjaxResult like(@PathVariable Long checkinId)
    {
        Long userId = SecurityUtils.getUserId();
        return toAjax(travelCheckinService.likeCheckin(checkinId, userId));
    }

    @DeleteMapping("/like/{checkinId}")
    public AjaxResult unlike(@PathVariable Long checkinId)
    {
        Long userId = SecurityUtils.getUserId();
        return toAjax(travelCheckinService.unlikeCheckin(checkinId, userId));
    }

    @PostMapping("/comment")
    public AjaxResult comment(@RequestBody TravelCheckinComment comment)
    {
        Long userId = SecurityUtils.getUserId();
        comment.setUserId(userId);
        comment.setCreateBy(String.valueOf(userId));
        return toAjax(travelCheckinService.commentCheckin(comment));
    }

    @DeleteMapping("/comment/{commentId}")
    public AjaxResult deleteComment(@PathVariable Long commentId)
    {
        return toAjax(travelCheckinService.deleteComment(commentId));
    }
}
