package top.aichezhan.ssyx.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.aichezhan.ssyx.mapper.SkuPosterMapper;
import top.aichezhan.ssyx.model.product.SkuPoster;
import top.aichezhan.ssyx.service.SkuPosterService;

import java.util.Collections;
import java.util.List;

@Service
public class SkuPosterServiceImpl extends ServiceImpl<SkuPosterMapper, SkuPoster> implements SkuPosterService {

    @Override
    public List<SkuPoster> findBySkuId(Long skuId) {
        return Collections.emptyList();
    }
}
