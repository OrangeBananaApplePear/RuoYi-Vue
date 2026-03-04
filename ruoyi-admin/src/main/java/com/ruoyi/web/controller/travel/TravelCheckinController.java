package com.ruoyi.web.controller.travel;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.domain.travel.TravelCheckin;
import com.ruoyi.system.service.travel.ITravelCheckinService;

/**
 * 管理端-打卡.ITravelCheckin管理
 */
@RestController
@RequestMapping("/travel/checkin")
public class TravelCheckinController extends BaseController
{
    @Autowired
    private ITravelCheckinService travelCheckinService;

    @GetMapping("/list")
    public TableDataInfo list(TravelCheckin travelCheckin)
    {
        startPage();
        List<TravelCheckin> list = travelCheckinService.selectTravelCheckinList(travelCheckin);
        return getDataTable(list);
    }

    @GetMapping("/{checkinId}")
    public AjaxResult get(@PathVariable Long checkinId)
    {
        return success(travelCheckinService.selectTravelCheckinById(checkinId));
    }

    @DeleteMapping("/{checkinIds}")
    public AjaxResult remove(@PathVariable Long[] checkinIds)
    {
        return toAjax(travelCheckinService.deleteTravelCheckinByIds(checkinIds));
    }
}
