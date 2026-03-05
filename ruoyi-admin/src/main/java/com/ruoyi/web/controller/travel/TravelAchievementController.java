package com.ruoyi.web.controller.travel;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.domain.travel.TravelAchievement;
import com.ruoyi.system.service.travel.ITravelAchievementService;

/**
 * 管理端-成就管理
 */
@RestController
@RequestMapping("/travel/achievement")
public class TravelAchievementController extends BaseController
{
    @Autowired
    private ITravelAchievementService travelAchievementService;

    /**
     * 查询成就列表
     */
    @GetMapping("/list")
    public TableDataInfo list(TravelAchievement travelAchievement)
    {
        startPage();
        List<TravelAchievement> list = travelAchievementService.selectTravelAchievementList(travelAchievement);
        return getDataTable(list);
    }

    /**
     * 获取成就详情
     */
    @GetMapping("/{achievementId}")
    public AjaxResult get(@PathVariable Long achievementId)
    {
        return success(travelAchievementService.selectTravelAchievementById(achievementId));
    }

    /**
     * 新增成就
     */
    @PostMapping
    public AjaxResult add(@RequestBody TravelAchievement travelAchievement)
    {
        return toAjax(travelAchievementService.insertTravelAchievement(travelAchievement));
    }

    /**
     * 修改成就
     */
    @PutMapping
    public AjaxResult edit(@RequestBody TravelAchievement travelAchievement)
    {
        return toAjax(travelAchievementService.updateTravelAchievement(travelAchievement));
    }

    /**
     * 删除成就
     */
    @DeleteMapping("/{achievementIds}")
    public AjaxResult remove(@PathVariable Long[] achievementIds)
    {
        return toAjax(travelAchievementService.deleteTravelAchievementByIds(achievementIds));
    }
}
