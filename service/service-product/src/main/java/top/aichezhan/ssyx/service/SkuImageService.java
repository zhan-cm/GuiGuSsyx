package top.aichezhan.ssyx.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.aichezhan.ssyx.model.product.SkuImage;

import java.util.List;

public interface SkuImageService extends IService<SkuImage> {

    List<SkuImage> findBySkuId(Long skuId);
}
