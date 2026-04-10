package top.aichezhan.ssyx.acl.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.aichezhan.ssyx.model.acl.Permission;

import java.util.List;

public interface PermissionService extends IService<Permission> {
    //获取所有菜单列表
    List<Permission> queryAllMenu();

    //递归删除
    boolean removeChildById(Long id);
}