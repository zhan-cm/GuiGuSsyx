package top.aichezhan.ssyx.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.aichezhan.ssyx.model.product.Attr;

import java.util.List;

public interface AttrService extends IService<Attr> {

    //根据属性分组id 获取属性列表
    List<Attr> findByAttrGroupId(Long attrGroupId);
}
