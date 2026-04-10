package top.aichezhan.ssyx.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.aichezhan.ssyx.model.product.SkuPoster;

import java.util.List;

public interface SkuPosterService extends IService<SkuPoster> {
    List<SkuPoster> findBySkuId(Long skuId);
}
