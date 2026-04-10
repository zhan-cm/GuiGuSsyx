package top.aichezhan.ssyx.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.aichezhan.ssyx.mapper.AttrMapper;
import top.aichezhan.ssyx.model.product.Attr;
import top.aichezhan.ssyx.service.AttrService;

import java.util.Collections;
import java.util.List;

@Service
public class AttrServiceImpl extends ServiceImpl<AttrMapper,Attr> implements AttrService {
    //根据属性分组id 获取属性列表
    @Override
    public List<Attr> findByAttrGroupId(Long attrGroupId) {
        LambdaQueryWrapper<Attr> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Attr::getAttrGroupId,attrGroupId);
        List<Attr> attrs = baseMapper.selectList(queryWrapper);
        return attrs;
    }
}
