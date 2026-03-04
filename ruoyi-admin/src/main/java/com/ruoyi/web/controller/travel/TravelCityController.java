package com.ruoyi.web.controller.travel;

import java.util.ArrayList;
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

    /**
     * 查询城市列表（分页，用于列表页面）
     */
    @GetMapping("/list")
    public TableDataInfo list(TravelCity travelCity)
    {
        startPage();
        List<TravelCity> list = travelCityService.selectTravelCityList(travelCity);
        return getDataTable(list);
    }

    /**
     * 查询城市树形列表（不分页，用于树形展示）
     */
    @GetMapping("/tree")
    public AjaxResult tree()
    {
        List<TravelCity> list = travelCityService.selectTravelCityList(new TravelCity());
        // 构建树形结构
        List<TravelCity> treeList = buildCityTree(list);
        return success(treeList);
    }
    
    /**
     * 构建城市树形结构
     */
    private List<TravelCity> buildCityTree(List<TravelCity> list) {
        List<TravelCity> result = new ArrayList<>();
        // 先把所有数据放入map
        java.util.Map<Long, TravelCity> map = new java.util.HashMap<>();
        for (TravelCity city : list) {
            map.put(city.getCityId(), city);
            city.setChildren(new ArrayList<>());
        }
        // 构建树形结构
        for (TravelCity city : list) {
            if (city.getParentId() == null || city.getParentId() == 0) {
                result.add(city);
            } else {
                TravelCity parent = map.get(city.getParentId());
                if (parent != null) {
                    parent.getChildren().add(city);
                }
            }
        }
        return result;
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
