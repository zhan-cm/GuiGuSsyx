package top.aichezhan.ssyx.sys.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.aichezhan.ssyx.exception.SsyxException;
import top.aichezhan.ssyx.model.sys.RegionWare;
import top.aichezhan.ssyx.result.ResultCodeEnum;
import top.aichezhan.ssyx.sys.mapper.RegionWareMapper;
import top.aichezhan.ssyx.sys.service.RegionWareService;
import top.aichezhan.ssyx.vo.sys.RegionWareQueryVo;

import javax.annotation.Resource;

@Service
@SuppressWarnings({"unchecked","rawtypes"})
public class RegionWareServiceImpl extends ServiceImpl<RegionWareMapper, RegionWare> implements RegionWareService {

    @Resource
    private RegionWareMapper regionWareMapper;

    @Override
    public IPage<RegionWare> selectPage(Page<RegionWare> pageParam, RegionWareQueryVo regionWareQueryVo) {

        String keyword = regionWareQueryVo.getKeyword();
        LambdaQueryWrapper<RegionWare> wrapper = new LambdaQueryWrapper<>();
        if(StringUtils.isNotBlank(keyword)){
            wrapper.like(RegionWare::getRegionName,keyword)
                    .or().like(RegionWare::getWareName,keyword);
        }

        IPage<RegionWare> regionWareIPage = baseMapper.selectPage(pageParam,wrapper);
        return regionWareIPage;
    }

    //添加开通区域
    @Override
    public void saveRegionWare(RegionWare regionWare) {
        LambdaQueryWrapper<RegionWare> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RegionWare::getRegionId,regionWare.getRegionId());
        Integer count = regionWareMapper.selectCount(wrapper);

        if(count > 0){
            throw new SsyxException(ResultCodeEnum.REGION_OPEN);
        }
        baseMapper.insert(regionWare);
    }

    //取消开通区域
    @Override
    public void updateStatus(Long id, Integer status) {
        RegionWare regionWare = baseMapper.selectById(id);
        regionWare.setStatus(status);
        baseMapper.updateById(regionWare);
    }
}
