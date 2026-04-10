package top.aichezhan.ssyx.acl.helper;

import top.aichezhan.ssyx.model.acl.Permission;
import java.util.ArrayList;
import java.util.List;

/**
 * 权限数据构建树形结构工具类
 */
public class PermissionHelper {

    /**
     * 使用递归方法建树
     */
    public static List<Permission> build(List<Permission> allList) {
        List<Permission> trees = new ArrayList<>();
        // 遍历所有数据，找到最顶层的父节点（pid = 0）
        for (Permission permission : allList) {
            if (permission.getPid() == 0) {
                permission.setLevel(1);
                // 调用递归方法往下找子节点
                trees.add(findChildren(permission, allList));
            }
        }
        return trees;
    }

    /**
     * 递归查找子节点
     */
    private static Permission findChildren(Permission permission, List<Permission> allList) {
        permission.setChildren(new ArrayList<Permission>());
        for (Permission it : allList) {
            // 如果当前遍历的节点的 pid 等于父节点的 id，说明它是前者的子节点
            if (permission.getId().longValue() == it.getPid().longValue()) {
                int level = permission.getLevel() + 1;
                it.setLevel(level);
                if (permission.getChildren() == null) {
                    permission.setChildren(new ArrayList<>());
                }
                // 把找到的子节点放进父节点的 children 集合中，并继续递归往下找
                permission.getChildren().add(findChildren(it, allList));
            }
        }
        return permission;
    }
}