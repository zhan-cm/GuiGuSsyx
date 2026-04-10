package top.aichezhan.ssyx.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.aichezhan.ssyx.mapper.SkuImageMapper;
import top.aichezhan.ssyx.model.product.SkuImage;
import top.aichezhan.ssyx.service.SkuImageService;

import java.util.Collections;
import java.util.List;

@Service
public class SkuImageServiceImpl extends ServiceImpl<SkuImageMapper,SkuImage> implements SkuImageService {
    @Override
    public List<SkuImage> findBySkuId(Long skuId) {
        return Collections.emptyList();
    }
}
