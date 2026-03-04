package com.ruoyi.web.controller.travel;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.domain.travel.TravelSpot;
import com.ruoyi.system.service.travel.ITravelSpotService;

/**
 * 管理端-景点管理
 */
@RestController
@RequestMapping("/travel/spot")
public class TravelSpotController extends BaseController
{
    @Autowired
    private ITravelSpotService travelSpotService;

    @GetMapping("/list")
    public TableDataInfo list(TravelSpot travelSpot)
    {
        startPage();
        List<TravelSpot> list = travelSpotService.selectTravelSpotList(travelSpot);
        return getDataTable(list);
    }

    @GetMapping("/{spotId}")
    public AjaxResult get(@PathVariable Long spotId)
    {
        return success(travelSpotService.selectTravelSpotById(spotId));
    }

    @PostMapping
    public AjaxResult add(@RequestBody TravelSpot travelSpot)
    {
        return toAjax(travelSpotService.insertTravelSpot(travelSpot));
    }

    @PutMapping
    public AjaxResult edit(@RequestBody TravelSpot travelSpot)
    {
        return toAjax(travelSpotService.updateTravelSpot(travelSpot));
    }

    @DeleteMapping("/{spotIds}")
    public AjaxResult remove(@PathVariable Long[] spotIds)
    {
        return toAjax(travelSpotService.deleteTravelSpotByIds(spotIds));
    }
}
