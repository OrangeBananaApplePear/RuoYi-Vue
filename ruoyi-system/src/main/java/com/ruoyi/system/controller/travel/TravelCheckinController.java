package com.ruoyi.system.controller.travel;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.system.domain.travel.TravelCheckin;
import com.ruoyi.system.domain.travel.TravelCheckinComment;
import com.ruoyi.system.domain.travel.TravelCheckinLike;
import com.ruoyi.system.service.travel.ITravelCheckinService;

/**
 * 打卡记录Controller
 * 
 * @author 旅途
 */
@RestController
@RequestMapping("/travel/checkin")
public class TravelCheckinController extends BaseController
{
    @Autowired
    private ITravelCheckinService travelCheckinService;

    /**
     * 获取某景点的公开打卡列表
     */
    @GetMapping("/public/{spotId}")
    public AjaxResult getPublicCheckins(@PathVariable Long spotId)
    {
        List<TravelCheckin> list = travelCheckinService.selectPublicCheckinsBySpotId(spotId);
        return success(list);
    }

    /**
     * 获取用户的所有打卡
     */
    @GetMapping("/user/{userId}")
    public AjaxResult getUserCheckins(@PathVariable Long userId)
    {
        List<TravelCheckin> list = travelCheckinService.selectCheckinsByUserId(userId);
        return success(list);
    }

    /**
     * 获取用户在某景点的打卡
     */
    @GetMapping("/user/{userId}/spot/{spotId}")
    public AjaxResult getUserSpotCheckins(@PathVariable Long userId, @PathVariable Long spotId)
    {
        List<TravelCheckin> list = travelCheckinService.selectUserCheckinsBySpotId(userId, spotId);
        return success(list);
    }

    /**
     * 打卡
     */
    @PostMapping
    public AjaxResult add(@RequestBody TravelCheckin travelCheckin)
    {
        // 从Security获取当前用户ID
        Long userId = SecurityUtils.getUserId();
        travelCheckin.setUserId(userId);
        travelCheckin.setCreateBy(String.valueOf(userId));
        return toAjax(travelCheckinService.insertTravelCheckin(travelCheckin));
    }

    /**
     * 修改打卡
     */
    @PutMapping
    public AjaxResult edit(@RequestBody TravelCheckin travelCheckin)
    {
        travelCheckin.setUpdateBy(getUsername());
        return toAjax(travelCheckinService.updateTravelCheckin(travelCheckin));
    }

    /**
     * 删除打卡
     */
    @DeleteMapping("/{checkinIds}")
    public AjaxResult remove(@PathVariable Long[] checkinIds)
    {
        return toAjax(travelCheckinService.deleteTravelCheckinByIds(checkinIds));
    }

    /**
     * 点赞
     */
    @PostMapping("/like/{checkinId}")
    public AjaxResult like(@PathVariable Long checkinId)
    {
        Long userId = SecurityUtils.getUserId();
        return toAjax(travelCheckinService.likeCheckin(checkinId, userId));
    }

    /**
     * 取消点赞
     */
    @DeleteMapping("/like/{checkinId}")
    public AjaxResult unlike(@PathVariable Long checkinId)
    {
        Long userId = SecurityUtils.getUserId();
        return toAjax(travelCheckinService.unlikeCheckin(checkinId, userId));
    }

    /**
     * 评论
     */
    @PostMapping("/comment")
    public AjaxResult comment(@RequestBody TravelCheckinComment comment)
    {
        Long userId = SecurityUtils.getUserId();
        comment.setUserId(userId);
        comment.setCreateBy(String.valueOf(userId));
        return toAjax(travelCheckinService.commentCheckin(comment));
    }

    /**
     * 删除评论
     */
    @DeleteMapping("/comment/{commentId}")
    public AjaxResult deleteComment(@PathVariable Long commentId)
    {
        return toAjax(travelCheckinService.deleteComment(commentId));
    }

    /**
     * 查询打卡列表(管理端)
     */
    @RequiresPermissions("travel:checkin:list")
    @GetMapping("/list")
    public TableDataInfo list(TravelCheckin travelCheckin)
    {
        startPage();
        List<TravelCheckin> list = travelCheckinService.selectTravelCheckinList(travelCheckin);
        return getDataTable(list);
    }
}
