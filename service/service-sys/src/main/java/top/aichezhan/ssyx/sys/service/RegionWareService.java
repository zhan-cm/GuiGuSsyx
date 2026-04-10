package top.aichezhan.ssyx.sys.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import top.aichezhan.ssyx.model.sys.RegionWare;
import top.aichezhan.ssyx.vo.sys.RegionWareQueryVo;

public interface RegionWareService {

    IPage<RegionWare> selectPage(Page<RegionWare> pageParam, RegionWareQueryVo regionWareQueryVo);

    //添加开通区域
    void saveRegionWare(RegionWare regionWare);

    //取消开通区域
    public void updateStatus(Long id,Integer status);
}
