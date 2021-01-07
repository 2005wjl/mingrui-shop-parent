package com.baidu.shop.mapper;

import com.baidu.shop.dto.SkuDTO;
import com.baidu.shop.entity.SkuEntity;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

import java.util.List;

public interface SkuMapper extends Mapper<SkuEntity>, InsertListMapper<SkuEntity> {

    @Select(value="select s.*,b.stock from tb_sku s, tb_stock b where s.id = b.sku_id and s.spu_id = #{spuId}")
    List<SkuDTO> SkusBySpuId(Integer spuId);
}
