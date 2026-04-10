package top.aichezhan.ssyx.acl.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import top.aichezhan.ssyx.model.acl.Role;
import top.aichezhan.ssyx.vo.acl.RoleQueryVo;

import java.util.Map;

public interface RoleService extends IService<Role> {
    //角色分页列表
    IPage<Role> selectRolePage(Page<Role> pageParam, RoleQueryVo roleQueryVo);

    Map<String, Object> findRoleByUserId(Long adminId);

    void saveUserRoleRealtionShip(Long adminId, Long[] roleId);
}
