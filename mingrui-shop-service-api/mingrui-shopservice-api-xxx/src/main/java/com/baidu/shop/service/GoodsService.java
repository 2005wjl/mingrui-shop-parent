package com.baidu.shop.service;

import com.alibaba.fastjson.JSONObject;
import com.baidu.shop.base.Result;
import com.baidu.shop.dto.SkuDTO;
import com.baidu.shop.dto.SpuDTO;

import com.baidu.shop.entity.SpuDetailEntity;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation(value="修改商品")
    @PutMapping(value="goods/save")
    Result<JSONObject> updateGoods(@RequestBody SpuDTO spuDTO);

    @ApiOperation(value="删除商品")
    @DeleteMapping(value="goods/delete")
    Result<JSONObject> deleteGoods(Integer spuId);

    @ApiOperation(value="下架商品")
    @PutMapping(value="goods/down")
    Result<JSONObject> downGoods(@RequestBody SpuDTO spuDTO);

    @ApiOperation(value="上架商品")
    @PutMapping(value="goods/up")
    Result<JSONObject> upGoods(@RequestBody SpuDTO spuDTO);
}
