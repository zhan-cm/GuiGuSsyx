package top.aichezhan.ssyx.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.aichezhan.ssyx.mapper.SkuAttrValueMapper;
import top.aichezhan.ssyx.model.product.SkuAttrValue;
import top.aichezhan.ssyx.service.SkuAttrValueService;

import java.util.Collections;
import java.util.List;

@Service
public class SkuAttrValueServiceImpl extends ServiceImpl<SkuAttrValueMapper, SkuAttrValue> implements SkuAttrValueService {
    @Override
    public List<SkuAttrValue> findBySkuId(Long skuId) {
        return Collections.emptyList();
    }
}
