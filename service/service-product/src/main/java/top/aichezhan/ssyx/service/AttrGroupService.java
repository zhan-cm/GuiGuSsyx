package top.aichezhan.ssyx.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import top.aichezhan.ssyx.model.product.AttrGroup;
import top.aichezhan.ssyx.vo.product.AttrGroupQueryVo;

import java.util.List;

public interface AttrGroupService extends IService<AttrGroup> {

    //平台属性分组列表
    IPage<AttrGroup> selectPage(Page<AttrGroup> pageParam, AttrGroupQueryVo attrGroupQueryVo);

    //查询所有属性分组
    List<AttrGroup> findAllList();
}
