package top.aichezhan.ssyx.sys.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;
import top.aichezhan.ssyx.model.sys.RegionWare;
import top.aichezhan.ssyx.result.Result;
import top.aichezhan.ssyx.sys.service.RegionWareService;
import top.aichezhan.ssyx.vo.sys.RegionWareQueryVo;

import javax.annotation.Resource;

@Api(value = "RegionWare管理",tags = "RegionWare管理")
@RestController
@RequestMapping(value = "/admin/sys/regionWare")
@SuppressWarnings({"unchecked", "rawtypes"})
public class RegionWareController {

    @Resource
    private RegionWareService regionWareService;

    //开通区域列表
    public Result index(
            @ApiParam(name = "page",value = "当前页码",required = true)
            @PathVariable Long page,
            @ApiParam(name = "limit",value = "每页记录数",required = true)
            @PathVariable Long limit,
            @ApiParam(name = "regionWareVo",value = "查询对象",required = false)
            RegionWareQueryVo regionWareQueryVo){

        Page<RegionWare> pageParam = new Page(page,limit);
        IPage<RegionWare> pageModel = regionWareService.selectPage(pageParam,regionWareQueryVo);
        return  Result.ok(pageModel);
    }

    //添加开通区域
    @ApiOperation(value = "新增")
    @PostMapping("save")
    public Result save(@RequestBody RegionWare regionWare){
        regionWareService.saveRegionWare(regionWare);
        return Result.ok();
    }

    //删除开通区域
    @ApiOperation(value = "删除")
    @DeleteMapping("remove/{id}")
    public  Result updateStatus(@PathVariable Long id,@PathVariable Integer status){
        regionWareService.updateStatus(id,status);
        return Result.ok();
    }
}
