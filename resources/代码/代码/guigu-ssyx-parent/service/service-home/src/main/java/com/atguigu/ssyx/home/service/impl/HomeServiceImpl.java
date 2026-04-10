package top.aichezhan.ssyx.home.service.impl;

import top.aichezhan.ssyx.client.product.ProductFeignClient;
import top.aichezhan.ssyx.client.search.SkuFeignClient;
import top.aichezhan.ssyx.client.user.UserFeignClient;
import top.aichezhan.ssyx.home.service.HomeService;
import top.aichezhan.ssyx.model.product.Category;
import top.aichezhan.ssyx.model.product.SkuInfo;
import top.aichezhan.ssyx.model.search.SkuEs;
import top.aichezhan.ssyx.vo.user.LeaderAddressVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

@Service
public class HomeServiceImpl implements HomeService {

    @Autowired
    private UserFeignClient userFeignClient;

    @Autowired
    private ProductFeignClient productFeignClient;

    @Autowired
    private SkuFeignClient skuFeignClient;

    //首页数据显示接口
    @Override
    public Map<String, Object> homeData(Long userId) {

        Map<String,Object> result = new HashMap<>();
        //1 根据userId获取当前登录用户提货地址信息
        // 远程调用service-user模块接口获取需要数据
        LeaderAddressVo leaderAddressVo =
                userFeignClient.getUserAddressByUserId(userId);
        result.put("leaderAddressVo",leaderAddressVo);

        //2 获取所有分类
        // 远程调用service-product模块接口
        List<Category> categoryList = productFeignClient.findAllCategoryList();
        result.put("categoryList",categoryList);

        //3 获取新人专享商品
        // 远程调用service-product模块接口
        List<SkuInfo> newPersonSkuInfoList = productFeignClient.findNewPersonSkuInfoList();
        result.put("newPersonSkuInfoList",newPersonSkuInfoList);

        //4 获取爆款商品
        // 远程调用service-search模块接口
        // hotscore 热门评分降序排序
        List<SkuEs> hotSkuList = skuFeignClient.findHotSkuList();
        result.put("hotSkuList",hotSkuList);

        //5 封装获取数据到map集合，返回
        return result;
    }
}
