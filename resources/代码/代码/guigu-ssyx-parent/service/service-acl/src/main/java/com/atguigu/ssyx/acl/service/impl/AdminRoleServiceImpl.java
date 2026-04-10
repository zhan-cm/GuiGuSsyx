package top.aichezhan.ssyx.acl.service.impl;

import top.aichezhan.ssyx.acl.mapper.AdminRoleMapper;
import top.aichezhan.ssyx.acl.service.AdminRoleService;
import top.aichezhan.ssyx.model.acl.AdminRole;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class AdminRoleServiceImpl extends ServiceImpl<AdminRoleMapper, AdminRole> implements AdminRoleService {
}
