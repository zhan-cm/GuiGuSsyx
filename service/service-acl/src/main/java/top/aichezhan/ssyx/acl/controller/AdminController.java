package top.aichezhan.ssyx.acl.controller;


import org.springframework.util.DigestUtils;
import top.aichezhan.ssyx.acl.service.AdminService;
import top.aichezhan.ssyx.acl.service.RoleService;
import top.aichezhan.ssyx.model.acl.Admin;
import top.aichezhan.ssyx.result.Result;
import top.aichezhan.ssyx.vo.acl.AdminQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

/**
 * 用户管理 前端控制器
 */
@RestController
@RequestMapping("/admin/acl/user")
@Api(tags = "用户管理")
@CrossOrigin //跨域
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private RoleService roleService;

    @ApiOperation(value = "AdminController获取管理用户分页列表")
    @GetMapping("{page}/{limit}")
    public Result index(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,

            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,

            @ApiParam(name = "userQueryVo", value = "查询对象", required = false)
            AdminQueryVo userQueryVo) {
        Page<Admin> pageParam = new Page<>(page, limit);
        IPage<Admin> pageModel = adminService.selectPage(pageParam, userQueryVo);
        return Result.ok(pageModel);
    }

    @ApiOperation(value = "AdminController获取管理用户")
    @GetMapping("get/{id}")
    public Result get(@PathVariable Long id) {
        Admin user = adminService.getById(id);
        return Result.ok(user);
    }

    @ApiOperation(value = "AdminController新增管理用户")
    @PostMapping("save")
    public Result save(@RequestBody Admin user) {//@RequestBody json转java
        //对密码进行MD5处理
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        adminService.save(user);
        return Result.ok();
    }

    @ApiOperation(value = "AdminController修改管理用户")
    @PutMapping("update")
    public Result updateById(@RequestBody Admin user) {
        adminService.updateById(user);
        return Result.ok();
    }

    @ApiOperation(value = "AdminController删除管理用户")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable Long id) {
        adminService.removeById(id);
        return Result.ok();
    }

    @ApiOperation(value = "AdminController根据id列表删除管理用户")
    @DeleteMapping("batchRemove")
    public Result batchRemove(@RequestBody List<Long> idList) {
        adminService.removeByIds(idList);
        return Result.ok();
    }


    @ApiOperation(value = "AdminController根据用户获取角色数据")
    @GetMapping("/toAssign/{adminId}")
    public Result toAssign(@PathVariable Long adminId) {
        Map<String,Object> rolemap = roleService.findRoleByUserId(adminId);
        return Result.ok(rolemap);
    }

    @ApiOperation(value = "AdminController根据用户分配角色")
    @PostMapping("/doAssign")
    public Result doAssign(@PathVariable Long adminId,@RequestBody Long[] roleId) {
        roleService.saveUserRoleRealtionShip(adminId,roleId);
        return Result.ok();
    }
}