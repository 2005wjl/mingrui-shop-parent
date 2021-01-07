package com.baidu.shop.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value="spu数据传输类")
@Data
public class SpuDetailDTO {

    @ApiModelProperty(value="spu主键Id",example = "1")
    private Integer spuId;

    @ApiModelProperty(value="商品描述的信息")
    private String description;

    @ApiModelProperty(value="规格参数数据")
    private String genericSpec;

    @ApiModelProperty(value="规格参数和可选的信息")
    private String specialSpec;

    @ApiModelProperty(value="售后服务")
    private String afterService;
}
