package top.aichezhan.ssyx.acl.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import top.aichezhan.ssyx.result.Result;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/admin/acl/index")
@Api(tags = "登录相关功能")
@CrossOrigin //跨域
public class IndexController {

    //请求登录的login
    @GetMapping("请求登录")
    @ApiOperation(value = "登录")
    public Result login(){
        Map<String,Object> map = new HashMap<>();
        map.put("token","admin-token");
        return Result.ok(map);
    }

    //获取用户信息
    @GetMapping("获取用户信息")
    public Result info(){
        Map<String,Object> map = new HashMap<>();
        map.put("name","aichezhan");
        map.put("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        return Result.ok(map);
    }

    //退出
    @PostMapping("退出")
    public Result logout(){
        return Result.ok();
    }
}
