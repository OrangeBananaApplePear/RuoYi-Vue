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
 * 城市Controller
 * 
 * @author 旅途
 */
@RestController
@RequestMapping("/travel/city")
public class TravelCityController extends BaseController
{
    @Autowired
    private ITravelCityService travelCityService;

    /**
     * 获取所有省份列表
     */
    @GetMapping("/provinces")
    public AjaxResult getProvinces()
    {
        List<TravelCity> list = travelCityService.selectProvinceList();
        return success(list);
    }

    /**
     * 获取子城市列表
     */
    @GetMapping("/children/{parentId}")
    public AjaxResult getCityChildren(@PathVariable Long parentId)
    {
        List<TravelCity> list = travelCityService.selectCityChildren(parentId);
        return success(list);
    }

    /**
     * 获取用户已打卡城市
     */
    @GetMapping("/checked/{userId}")
    public AjaxResult getCheckedCities(@PathVariable Long userId)
    {
        List<TravelCity> list = travelCityService.selectCheckedCitiesByUserId(userId);
        return success(list);
    }

    /**
     * 查询城市列表
     */
    @GetMapping("/list")
    public TableDataInfo list(TravelCity travelCity)
    {
        startPage();
        List<TravelCity> list = travelCityService.selectTravelCityList(travelCity);
        return getDataTable(list);
    }

    /**
     * 获取城市详细信息
     */
    @GetMapping(value = "/{cityId}")
    public AjaxResult getInfo(@PathVariable Long cityId)
    {
        return success(travelCityService.selectTravelCityById(cityId));
    }

    /**
     * 新增城市
     */
    @PostMapping
    public AjaxResult add(@RequestBody TravelCity travelCity)
    {
        return toAjax(travelCityService.insertTravelCity(travelCity));
    }

    /**
     * 修改城市
     */
    @PutMapping
    public AjaxResult edit(@RequestBody TravelCity travelCity)
    {
        return toAjax(travelCityService.updateTravelCity(travelCity));
    }

    /**
     * 删除城市
     */
    @DeleteMapping("/{cityIds}")
    public AjaxResult remove(@PathVariable Long[] cityIds)
    {
        return toAjax(travelCityService.deleteTravelCityByIds(cityIds));
    }
}
