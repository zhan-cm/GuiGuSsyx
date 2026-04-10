package top.aichezhan.ssyx.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import top.aichezhan.ssyx.model.product.SkuInfo;
import top.aichezhan.ssyx.vo.product.SkuInfoQueryVo;
import top.aichezhan.ssyx.vo.product.SkuInfoVo;


public interface SkuInfoService extends IService<SkuInfo> {
    //获取sku分页列表
    IPage<SkuInfo> selectPage(Page<SkuInfo> pageParam, SkuInfoQueryVo skuInfoQueryVo);

    //添加商品
    void saveSkuInfo(SkuInfoVo skuInfoVo);

    //获取商品
    SkuInfoVo getSkuInfoVo(Long id);

    void updateSkuInfo(SkuInfoVo skuInfoVo);

    void check(Long skuId, Integer status);

    void publish(Long skuId, Integer status);

    void isNewPerson(Long skuId, Integer status);
}
