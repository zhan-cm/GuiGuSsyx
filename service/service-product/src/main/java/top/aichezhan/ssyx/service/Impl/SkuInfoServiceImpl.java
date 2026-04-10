package top.aichezhan.ssyx.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import top.aichezhan.ssyx.mapper.SkuInfoMapper;
import top.aichezhan.ssyx.model.product.SkuAttrValue;
import top.aichezhan.ssyx.model.product.SkuImage;
import top.aichezhan.ssyx.model.product.SkuInfo;
import top.aichezhan.ssyx.model.product.SkuPoster;
import top.aichezhan.ssyx.service.SkuAttrValueService;
import top.aichezhan.ssyx.service.SkuImageService;
import top.aichezhan.ssyx.service.SkuInfoService;
import top.aichezhan.ssyx.service.SkuPosterService;
import top.aichezhan.ssyx.vo.product.SkuInfoQueryVo;
import top.aichezhan.ssyx.vo.product.SkuInfoVo;

import java.util.List;

@Service
public class SkuInfoServiceImpl extends ServiceImpl<SkuInfoMapper,SkuInfo> implements SkuInfoService {

    @Autowired
    private SkuInfoMapper skuInfoMapper;

    @Autowired
    private SkuPosterService skuPosterService;

    @Autowired
    private SkuImageService skuImagesService;

    @Autowired
    private SkuAttrValueService skuAttrValueService;

    //获取sku分页列表
    @Override
    public IPage<SkuInfo> selectPage(Page<SkuInfo> pageParam, SkuInfoQueryVo skuInfoQueryVo) {
        //获取条件值
        String keyword = skuInfoQueryVo.getKeyword();
        String skuType = skuInfoQueryVo.getSkuType();
        Long categoryId = skuInfoQueryVo.getCategoryId();
        //封装条件
        LambdaQueryWrapper<SkuInfo> wrapper = new LambdaQueryWrapper<>();
        if(!StringUtils.isEmpty(keyword)) {
            wrapper.like(SkuInfo::getSkuName,keyword);
        }
        if(!StringUtils.isEmpty(skuType)) {
            wrapper.eq(SkuInfo::getSkuType,skuType);
        }
        if(!StringUtils.isEmpty(categoryId)) {
            wrapper.eq(SkuInfo::getCategoryId,categoryId);
        }
        //调用方法查询
        IPage<SkuInfo> skuInfoPage = baseMapper.selectPage(pageParam, wrapper);
        return skuInfoPage;
    }

    //添加商品
    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void saveSkuInfo(SkuInfoVo skuInfoVo) {
        //保存sku信息
        SkuInfo skuInfo = new SkuInfo();
        BeanUtils.copyProperties(skuInfoVo, skuInfo);
        this.save(skuInfo);

        //保存sku海报
        List<SkuPoster> skuPosterList = skuInfoVo.getSkuPosterList();
        if (!CollectionUtils.isEmpty(skuPosterList)) {
            int sort = 1;
            for (SkuPoster skuPoster : skuPosterList) {
                skuPoster.setSkuId(skuInfo.getId());
                sort++;
            }
            skuPosterService.saveBatch(skuPosterList);
        }

        //保存sku图片
        List<SkuImage> skuImagesList = skuInfoVo.getSkuImagesList();
        if (!CollectionUtils.isEmpty(skuImagesList)) {
            int sort = 1;
            for (SkuImage skuImages : skuImagesList) {
                skuImages.setSkuId(skuInfo.getId());
                skuImages.setSort(sort);
                sort++;
            }
            skuImagesService.saveBatch(skuImagesList);
        }

        //保存sku平台属性
        List<SkuAttrValue> skuAttrValueList = skuInfoVo.getSkuAttrValueList();
        if (!CollectionUtils.isEmpty(skuAttrValueList)) {
            int sort = 1;
            for (SkuAttrValue skuAttrValue : skuAttrValueList) {
                skuAttrValue.setSkuId(skuInfo.getId());
                skuAttrValue.setSort(sort);
                sort++;
            }
            skuAttrValueService.saveBatch(skuAttrValueList);
        }
    }

    //获取商品
    @Override
    public SkuInfoVo getSkuInfoVo(Long skuId) {
        return getSkuInfoDB(skuId);
    }

    //修改商品
    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void updateSkuInfo(SkuInfoVo skuInfoVo) {
        Long id = skuInfoVo.getId();
        //更新sku信息
        this.updateById(skuInfoVo);

        //保存sku详情
        skuPosterService.remove(new LambdaQueryWrapper<SkuPoster>().eq(SkuPoster::getSkuId, id));
        //保存sku海报
        List<SkuPoster> skuPosterList = skuInfoVo.getSkuPosterList();
        if(!CollectionUtils.isEmpty(skuPosterList)) {
            int sort = 1;
            for(SkuPoster skuPoster : skuPosterList) {
                skuPoster.setSkuId(id);
                sort++;
            }
            skuPosterService.saveBatch(skuPosterList);
        }

        //删除sku图片
        skuImagesService.remove(new LambdaQueryWrapper<SkuImage>().eq(SkuImage::getSkuId, id));
        //保存sku图片
        List<SkuImage> skuImagesList = skuInfoVo.getSkuImagesList();
        if(!CollectionUtils.isEmpty(skuImagesList)) {
            int sort = 1;
            for(SkuImage skuImages : skuImagesList) {
                skuImages.setSkuId(id);
                skuImages.setSort(sort);
                sort++;
            }
            skuImagesService.saveBatch(skuImagesList);
        }

        //删除sku平台属性
        skuAttrValueService.remove(new LambdaQueryWrapper<SkuAttrValue>().eq(SkuAttrValue::getSkuId, id));
        //保存sku平台属性
        List<SkuAttrValue> skuAttrValueList = skuInfoVo.getSkuAttrValueList();
        if(!CollectionUtils.isEmpty(skuAttrValueList)) {
            int sort = 1;
            for(SkuAttrValue skuAttrValue : skuAttrValueList) {
                skuAttrValue.setSkuId(id);
                skuAttrValue.setSort(sort);
                sort++;
            }
            skuAttrValueService.saveBatch(skuAttrValueList);
        }
    }

    //商品审核
    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void check(Long skuId, Integer status) {
        // 更改发布状态
        SkuInfo skuInfoUp = new SkuInfo();
        skuInfoUp.setId(skuId);
        skuInfoUp.setCheckStatus(status);
        baseMapper.updateById(skuInfoUp);
    }

    //商品上架
    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void publish(Long skuId, Integer status) {
        // 更改发布状态
        if(status == 1) {
            SkuInfo skuInfoUp = new SkuInfo();
            skuInfoUp.setId(skuId);
            skuInfoUp.setPublishStatus(1);
            skuInfoMapper.updateById(skuInfoUp);
            //TODO 商品上架 后续会完善：发送mq消息更新es数据
        } else {
            SkuInfo skuInfoUp = new SkuInfo();
            skuInfoUp.setId(skuId);
            skuInfoUp.setPublishStatus(0);
            skuInfoMapper.updateById(skuInfoUp);
            //TODO 商品下架 后续会完善：发送mq消息更新es数据
        }
    }

    //新人专享
    @Override
    public void isNewPerson(Long skuId, Integer status) {
        SkuInfo skuInfoUp = new SkuInfo();
        skuInfoUp.setId(skuId);
        skuInfoUp.setIsNewPerson(status);
        skuInfoMapper.updateById(skuInfoUp);
    }

    private SkuInfoVo getSkuInfoDB(Long skuId) {
        SkuInfoVo skuInfoVo = new SkuInfoVo();

        SkuInfo skuInfo = skuInfoMapper.selectById(skuId);
        //TODO skuImagesService  skuPosterService  skuAttrValueService分别添加方法
        List<SkuImage> skuImageList = skuImagesService.findBySkuId(skuId);
        List<SkuPoster> skuPosterList = skuPosterService.findBySkuId(skuId);
        List<SkuAttrValue> skuAttrValueList = skuAttrValueService.findBySkuId(skuId);

        BeanUtils.copyProperties(skuInfo, skuInfoVo);
        skuInfoVo.setSkuImagesList(skuImageList);
        skuInfoVo.setSkuPosterList(skuPosterList);
        skuInfoVo.setSkuAttrValueList(skuAttrValueList);
        return skuInfoVo;
    }
}
