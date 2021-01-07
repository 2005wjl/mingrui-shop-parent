package com.baidu.shop.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value="库存数据传输类")
@Data
public class StockDTO {

    @ApiModelProperty(value="sku的主键",example = "1")
    private Long skuId;

    @ApiModelProperty(value = "可以秒杀库存",example = "1")
    private Integer seckillStoke;

    @ApiModelProperty(value="秒杀的总数量",example = "1")
    private Integer seckillTotal;

    @ApiModelProperty(value="库存数量",example = "1")
    private Integer stock;
}
