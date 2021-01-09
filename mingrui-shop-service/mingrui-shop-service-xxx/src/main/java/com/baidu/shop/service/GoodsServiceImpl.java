package com.baidu.shop.service;

import com.alibaba.fastjson.JSONObject;
import com.baidu.shop.base.BaseApiService;
import com.baidu.shop.base.Result;
import com.baidu.shop.dto.SkuDTO;
import com.baidu.shop.dto.SpuDTO;
import com.baidu.shop.dto.SpuDetailDTO;
import com.baidu.shop.entity.*;
import com.baidu.shop.mapper.*;
import com.baidu.shop.status.HTTPStatus;
import com.baidu.shop.utils.BaiduBeanUtil;
import com.baidu.shop.utils.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class GoodsServiceImpl extends BaseApiService implements GoodsService {

    @Resource
    private SpuMapper spuMapper;

    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private BrandMapper brandMapper;

    @Resource
    private SpuDetailMapper spuDetailMapper;

    @Resource
    private StockMapper stockMapper;

    @Resource
    private SkuMapper skuMapper;

    @Override
    @Transactional
    public Result<JSONObject> upGoods(SpuDTO spuDTO) {

        this.upDownGoods(spuDTO,1);

        return this.setResultSuccess("上架成功");
    }

    @Override
    @Transactional
    public Result<JSONObject> downGoods(SpuDTO spuDTO) {

        this.upDownGoods(spuDTO,0);

        return this.setResultSuccess("下架成功");
    }


    @Override
    @Transactional
    public Result<JSONObject> deleteGoods(Integer spuId) {

        //删除spu
        spuMapper.deleteByPrimaryKey(spuId);

        //删除spuDetail
        spuDetailMapper.deleteByPrimaryKey(spuId);

        this.deleteStockAndSku(spuId);

        return this.setResultSuccess("删除成功");
    }

    @Override
    @Transactional
    public Result<JSONObject> updateGoods(SpuDTO spuDTO) {

        final Date date = new Date();
        //修改spu
        SpuEntity spuEntity = BaiduBeanUtil.copyProperties(spuDTO,SpuEntity.class);
        spuEntity.setLastUpdateTime(date);
        spuMapper.updateByPrimaryKeySelective(spuEntity);

        //修改spuDetail
        spuDetailMapper.updateByPrimaryKeySelective(BaiduBeanUtil.copyProperties(
                spuDTO.getSpuDetail(),SpuDetailEntity.class));

        //修改sku和spuDetail
        this.saveSkuAndStoke(spuDTO,spuEntity.getId(),date);

        return this.setResultSuccess("修改成功");
    }



    @Override
    @Transactional
    public Result<SpuDetailEntity> SpuDetailBySpuId(Integer spuId) {
        SpuDetailEntity spuDetailEntity  = spuDetailMapper.selectByPrimaryKey(spuId);
        return this.setResultSuccess(spuDetailEntity);
    }

    @Override
    @Transactional
    public Result<List<SkuDTO>> SkusBySpuId(Integer spuId) {
        List<SkuDTO> list = skuMapper.SkusBySpuId(spuId);
        return this.setResultSuccess(list);
    }

    @Transactional
    @Override
    public Result<JSONObject> saveGoods(SpuDTO spuDTO) {

        final Date date = new Date();
        //新增spu
        SpuEntity spuEntity = BaiduBeanUtil.copyProperties(spuDTO,SpuEntity.class);

        spuEntity.setSaleable(1);
        spuEntity.setValid(1);
        spuEntity.setCreateTime(date);
        spuEntity.setLastUpdateTime(date);
        spuMapper.insertSelective(spuEntity);

        //新增SpuDetail
        SpuDetailDTO spuDetail = spuDTO.getSpuDetail();
        SpuDetailEntity spuDetailEntity = BaiduBeanUtil.copyProperties(spuDetail,SpuDetailEntity.class);
        spuDetailEntity.setSpuId(spuEntity.getId());
        spuDetailMapper.insertSelective(spuDetailEntity);

        this.saveSkuAndStoke(spuDTO,spuEntity.getId(),date);

        return this.setResultSuccess("新增成功");
    }

    @Transactional
    @Override
    public Result<List<SpuDTO>> getSpuListSpuInfo(SpuDTO spuDTO) {

        if(ObjectUtil.isNotNull(spuDTO.getPage()) && ObjectUtil.isNotNull(spuDTO.getRows()))
            PageHelper.startPage(spuDTO.getPage(),spuDTO.getRows());

        if(!StringUtils.isEmpty(spuDTO.getSort()) && !StringUtils.isEmpty(spuDTO.getOrder()))
            PageHelper.orderBy(spuDTO.getOrderByClause());

        Example example = new Example(SpuEntity.class);
        Example.Criteria criteria = example.createCriteria();
        if(ObjectUtil.isNotNull(spuDTO.getSaleable()) && spuDTO.getSaleable() != 2)
            criteria.andEqualTo("saleable",spuDTO.getSaleable());

        if(!StringUtils.isEmpty(spuDTO.getTitle()))
            criteria.andLike("title","%"+spuDTO.getTitle()+"%");

        List<SpuEntity> spuEntities = spuMapper.selectByExample(example);

        List<SpuDTO> spuDTOS = spuEntities.stream().map(spuEntity -> {
           SpuDTO spuDTO1 = BaiduBeanUtil.copyProperties(spuEntity,SpuDTO.class);
           //通过分类id集合查询数据
            List<CategoryEntity> categoryEntities = categoryMapper.selectByIdList(
                    Arrays.asList(spuEntity.getCid1(),spuEntity.getCid2(),spuEntity.getCid3()));

            String categoryName = categoryEntities.stream().map(categoryEntity ->
                    categoryEntity.getName()).collect(Collectors.joining("/"));
            spuDTO1.setCategoryName(categoryName);

            BrandEntity brandEntity = brandMapper.selectByPrimaryKey(spuEntity.getBrandId());
            spuDTO1.setBrandName(brandEntity.getName());
            return spuDTO1;
        }).collect(Collectors.toList());

        PageInfo<SpuEntity> pageInfo = new PageInfo<>(spuEntities);

        return this.setResult(HTTPStatus.OK,pageInfo.getTotal()+"",spuDTOS);
    }

    private void saveSkuAndStoke(SpuDTO spuDTO,Integer spuId,Date date){

        List<SkuDTO> skuDTOS = spuDTO.getSkus();
        skuDTOS.stream().forEach(skuDTO -> {

            SkuEntity skuEntity = BaiduBeanUtil.copyProperties(skuDTO,SkuEntity.class);
            skuEntity.setSpuId(spuId);
            skuEntity.setCreateTime(date);
            skuEntity.setLastUpdateTime(date);
            skuMapper.insertSelective(skuEntity);

            //新增stock
            StockEntity stockEntity = new StockEntity();
            Long id = skuEntity.getId();
            stockEntity.setSkuId(id);
            stockEntity.setStock(skuDTO.getStock());
            stockMapper.insertSelective(stockEntity);
        });
    }

    private void deleteStockAndSku(Integer spuId){

        Example example = new Example(SkuEntity.class);
        example.createCriteria().andEqualTo("spuId",spuId);
        List<SkuEntity> sku = skuMapper.selectByExample(example);

        List<Long> list =   sku.stream().map(skuEntity -> skuEntity.getId()).collect(Collectors.toList());
        skuMapper.deleteByIdList(list);
        stockMapper.deleteByIdList(list);
    }

    private void upDownGoods(SpuDTO spuDTO,Integer t){
        SpuEntity spuEntity = BaiduBeanUtil.copyProperties(spuDTO,SpuEntity.class);
        spuEntity.setSaleable(t);
        spuMapper.updateByPrimaryKeySelective(spuEntity);
    }
}
