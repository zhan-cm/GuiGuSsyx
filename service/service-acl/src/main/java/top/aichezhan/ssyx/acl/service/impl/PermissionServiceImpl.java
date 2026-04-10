package top.aichezhan.ssyx.acl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.aichezhan.ssyx.acl.helper.PermissionHelper;
import top.aichezhan.ssyx.acl.mapper.PermissionMapper;
import top.aichezhan.ssyx.acl.service.PermissionService;
import top.aichezhan.ssyx.model.acl.Permission;


import java.util.ArrayList;
import java.util.List;

@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper,Permission> implements PermissionService {


    //获取所有菜单
    @Override
    public List<Permission> queryAllMenu() {
        //获取全部权限
        List<Permission> allPermissionList = baseMapper.selectList(
                new QueryWrapper<Permission>()
                        .orderByAsc("CAST(id AS SIGNED)"));//按 id 升序排序

        //把权限数据构建成树形结构数据
        List<Permission> result = PermissionHelper.build(allPermissionList);
        return result;
    }

    @Override
    public boolean removeChildById(Long id) {
        List<Long> idList = new ArrayList<>();
        this.selectChildListById(id,idList);
        idList.add(id);
        baseMapper.deleteBatchIds(idList);
        return true;
    }

    /**
     *	递归获取子节点
     * @param id
     * @param idList
     */
    private void selectChildListById(Long id, List<Long> idList) {
        List<Permission> childList = baseMapper.selectList(new QueryWrapper<Permission>()
                .eq("pid", id)
                .select("id"));
        /**
         * baseMapper.selectList() - MyBatis-Plus 提供的查询方法，执行 SELECT 语句
         * new QueryWrapper<Permission>() - 创建查询条件构造器
         * .eq("pid", id) - 添加条件：pid = id（查询父节点ID等于当前ID的子节点）找出所有"把我当作父亲"的孩子节点
         * .select("id") - 只查询 id 字段，不查询其他字段，提高查询效率
         * 执行SQL：SELECT id FROM permission WHERE pid = ?
         */
        childList.stream().forEach(item -> {
            idList.add(item.getId());
            this.selectChildListById(item.getId(), idList);
        });
        /**
         * stream().forEach() - 遍历每个子节点
         * idList.add(item.getId()) - 将当前子节点的ID添加到列表中
         * this.selectChildListById(item.getId(), idList) - 递归调用，继续查询当前子节点的子节点
         */
    }
}
