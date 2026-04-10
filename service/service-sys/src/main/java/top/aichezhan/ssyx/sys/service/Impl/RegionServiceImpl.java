package top.aichezhan.ssyx.sys.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.aichezhan.ssyx.model.sys.Region;
import top.aichezhan.ssyx.sys.mapper.RegionMapper;
import top.aichezhan.ssyx.sys.service.RegionService;
import java.util.List;

@Service
public class RegionServiceImpl extends ServiceImpl<RegionMapper, Region> implements RegionService {


    @Override
    public List<Region> findRegionByKeyword(String keyword) {
        LambdaQueryWrapper<Region> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(Region::getName,keyword);
        return baseMapper.selectList(queryWrapper);
    }
}
