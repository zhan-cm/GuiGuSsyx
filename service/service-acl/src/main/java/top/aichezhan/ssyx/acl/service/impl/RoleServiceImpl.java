package top.aichezhan.ssyx.acl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import top.aichezhan.ssyx.acl.mapper.RoleMapper;
import top.aichezhan.ssyx.acl.service.AdminRoleService;
import top.aichezhan.ssyx.acl.service.RoleService;
import top.aichezhan.ssyx.model.acl.AdminRole;
import top.aichezhan.ssyx.model.acl.Role;
import top.aichezhan.ssyx.vo.acl.RoleQueryVo;

import java.util.Collections;
import java.util.Map;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private AdminRoleService adminRoleService;

    @Override
    public IPage<Role> selectRolePage(Page<Role> pageParam, RoleQueryVo roleQueryVo) {
        //获取条件值：角色名称
        String roleName = roleQueryVo.getRoleName();
        //创建条件构造器对象
        LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
        //判断条件值是否为空
        if(!StringUtils.isEmpty(roleName)){
            //封装条件
            wrapper.like(Role::getRoleName,roleName);
        }
        //调用mapper方法实现条件分页查询
        IPage<Role> pageModel = baseMapper.selectPage(pageParam,wrapper);//要查哪一页，每页查多少条。要查什么数据
        return pageModel;
    }

    @Override
    public Map<String, Object> findRoleByUserId(Long adminId) {
        return Collections.emptyMap();
    }

    /**
     * 分配角色
     * @param adminId
     * @param roleId
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveUserRoleRealtionShip(Long adminId, Long[] roleId) {
        //删除用户分配的角色数据
        adminRoleService.remove(new QueryWrapper<AdminRole>().eq("admin_id",adminId));
    }
}
