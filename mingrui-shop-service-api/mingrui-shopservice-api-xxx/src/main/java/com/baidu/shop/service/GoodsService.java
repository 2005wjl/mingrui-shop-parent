package com.baidu.shop.service;

import com.alibaba.fastjson.JSONObject;
import com.baidu.shop.base.Result;
import com.baidu.shop.dto.SkuDTO;
import com.baidu.shop.dto.SpuDTO;

import com.baidu.shop.entity.SpuDetailEntity;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Api(tags = "商品接口")
public interface GoodsService {

    @ApiOperation(value="查询spu")
    @GetMapping(value="goods/getListSpuInfo")
    Result<List<SpuDTO>> getSpuListSpuInfo(SpuDTO spuDTO);

    @ApiOperation(value="新增商品")
    @PostMapping(value="goods/save")
    Result<JSONObject> saveGoods(@RequestBody SpuDTO spuDTO);

    @ApiOperation(value="通过spuId查询spuDetail的信息")
    @GetMapping(value="goods/listSpuDetailBySpuId")
    Result<SpuDetailEntity> SpuDetailBySpuId(Integer spuId);

    @ApiOperation(value="通过spuId查询sku的信息")
    @GetMapping(value="goods/listSkusBySpuId")
    Result<List<SkuDTO>> SkusBySpuId(Integer spuId);
}
