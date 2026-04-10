package top.aichezhan.ssyx.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import top.aichezhan.ssyx.mapper.AttrGroupMapper;
import top.aichezhan.ssyx.model.product.AttrGroup;
import top.aichezhan.ssyx.service.AttrGroupService;
import top.aichezhan.ssyx.vo.product.AttrGroupQueryVo;

import java.util.Collections;
import java.util.List;

@Service
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupMapper, AttrGroup> implements AttrGroupService {

    //平台属性分组列表
    @Override
    public IPage<AttrGroup> selectPage(Page<AttrGroup> pageParam, AttrGroupQueryVo attrGroupQueryVo) {
        String name = attrGroupQueryVo.getName();
        LambdaQueryWrapper<AttrGroup> queryWrapper = new LambdaQueryWrapper<>();
        if(!StringUtils.isEmpty(name)){
            queryWrapper.like(AttrGroup::getName,name);
        }
        IPage<AttrGroup> attrGroupIPage = baseMapper.selectPage(pageParam, queryWrapper);
        return attrGroupIPage;
    }

    //查询所有属性分组
    @Override
    public List<AttrGroup> findAllList() {
        return this.list();
    }
}
