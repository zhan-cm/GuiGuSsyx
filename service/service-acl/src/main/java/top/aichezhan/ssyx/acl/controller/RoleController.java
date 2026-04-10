package top.aichezhan.ssyx.acl.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.aichezhan.ssyx.acl.service.RoleService;
import top.aichezhan.ssyx.model.acl.Role;
import top.aichezhan.ssyx.result.Result;
import top.aichezhan.ssyx.vo.acl.RoleQueryVo;

import java.util.List;

//角色管理
@RestController
@RequestMapping("/admin/acl/role")
@Api(tags = "用户管理")
@Slf4j
public class RoleController {

    @Autowired
    private RoleService roleService;

    @ApiOperation(value = "RoleController获取角色分页列表")
    @GetMapping("{page}/{limit}")
    public Result index(
            @ApiParam(name = "page",value = "当前页码",required = true)
            @PathVariable Long page,

            @ApiParam(name = "limit",value = "每页记录数",required = true)
            @PathVariable Long limit,

            @ApiParam(name = "roleQueryVo",value = "查询对象",required = false)
            RoleQueryVo roleQueryVo){
        Page<Role> pageParam = new Page<>(page,limit);
        IPage<Role> pageModel = roleService.selectRolePage(pageParam,roleQueryVo);
        return Result.ok(pageModel);
    }

    @ApiOperation(value = "RoleController获取角色")
    @GetMapping("get/{id}")
    public Result get(@PathVariable Long id) {
        Role role = roleService.getById(id);
        return Result.ok(role);
    }

    @ApiOperation(value = "RoleController新增角色")
    @PostMapping("save")
    public Result save(@RequestBody Role role) {
        roleService.save(role);
        return Result.ok();
    }

    @ApiOperation(value = "RoleController修改角色")
    @PutMapping("update")
    public Result updateById(@RequestBody Role role) {
        roleService.updateById(role);
        return Result.ok();
    }

    @ApiOperation(value = "RoleController删除角色")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable Long id) {
        roleService.removeById(id);
        return Result.ok();
    }

    @ApiOperation(value = "RoleController根据id列表删除角色")
    @DeleteMapping("batchRemove")
    public Result batchRemove(@RequestBody List<Long> idList) {
        roleService.removeByIds(idList);
        return Result.ok();
    }
}
