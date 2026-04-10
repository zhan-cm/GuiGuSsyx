package top.aichezhan.ssyx.user.service;

import top.aichezhan.ssyx.enums.user.User;
import top.aichezhan.ssyx.vo.user.LeaderAddressVo;
import top.aichezhan.ssyx.vo.user.UserLoginVo;
import com.baomidou.mybatisplus.extension.service.IService;

public interface UserService extends IService<User> {
    //// 判断是否是第一次使用微信授权登录：如何判断？openId
    User getUserByOpenId(String openid);

    //5 根据userId查询提货点和团长信息
    LeaderAddressVo getLeaderAddressByUserId(Long userId);

    //7 获取当前登录用户信息，
    UserLoginVo getUserLoginVo(Long id);
}
