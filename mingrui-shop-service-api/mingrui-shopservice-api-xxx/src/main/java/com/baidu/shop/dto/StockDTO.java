package com.baidu.shop.dto;

import com.baidu.shop.validate.group.MingruiOperation;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@ApiModel(value="库存数据传输类")
@Data
public class StockDTO {

    @ApiModelProperty(value="sku的主键",example = "1")
    @NotNull(message = "sku主键不能为空",groups = {MingruiOperation.Update.class})
    private Long skuId;

    @ApiModelProperty(value = "可以秒杀库存",example = "1")
    private Integer seckillStoke;

    @ApiModelProperty(value="秒杀的总数量",example = "1")
    private Integer seckillTotal;

    @ApiModelProperty(value="库存数量",example = "1")
    @NotNull(message = "库存数量不能为空",groups = {MingruiOperation.Add.class,MingruiOperation.Update.class})
    private Integer stock;
}
