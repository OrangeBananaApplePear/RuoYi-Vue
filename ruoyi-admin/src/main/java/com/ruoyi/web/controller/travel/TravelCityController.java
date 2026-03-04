package com.ruoyi.web.controller.travel;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.domain.travel.TravelCity;
import com.ruoyi.system.service.travel.ITravelCityService;

/**
 * 管理端-城市管理
 */
@RestController
@RequestMapping("/travel/city")
public class TravelCityController extends BaseController
{
    @Autowired
    private ITravelCityService travelCityService;

    @GetMapping("/list")
    public TableDataInfo list(TravelCity travelCity)
    {
        startPage();
        List<TravelCity> list = travelCityService.selectTravelCityList(travelCity);
        return getDataTable(list);
    }

    @GetMapping("/{cityId}")
    public AjaxResult get(@PathVariable Long cityId)
    {
        return success(travelCityService.selectTravelCityById(cityId));
    }

    @PostMapping
    public AjaxResult add(@RequestBody TravelCity travelCity)
    {
        return toAjax(travelCityService.insertTravelCity(travelCity));
    }

    @PutMapping
    public AjaxResult edit(@RequestBody TravelCity travelCity)
    {
        return toAjax(travelCityService.updateTravelCity(travelCity));
    }

    @DeleteMapping("/{cityIds}")
    public AjaxResult remove(@PathVariable Long[] cityIds)
    {
        return toAjax(travelCityService.deleteTravelCityByIds(cityIds));
    }
}
