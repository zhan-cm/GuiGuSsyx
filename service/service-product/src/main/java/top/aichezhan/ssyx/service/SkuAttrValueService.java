package top.aichezhan.ssyx.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.aichezhan.ssyx.model.product.SkuAttrValue;

import java.util.List;

public interface SkuAttrValueService extends IService<SkuAttrValue> {
    List<SkuAttrValue> findBySkuId(Long skuId);
}
