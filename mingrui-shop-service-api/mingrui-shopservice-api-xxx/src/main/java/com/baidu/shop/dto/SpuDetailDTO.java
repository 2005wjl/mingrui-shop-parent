package com.baidu.shop.dto;

import com.baidu.shop.validate.group.MingruiOperation;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@ApiModel(value="spu数据传输类")
@Data
public class SpuDetailDTO {

    @ApiModelProperty(value="spu主键Id",example = "1")
    @NotNull(message = "spuId主键id不能为空",groups = {MingruiOperation.Update.class})
    private Integer spuId;

    @ApiModelProperty(value="商品描述的信息")
    private String description;

    @ApiModelProperty(value="规格参数数据")
    @NotEmpty(message = "规格参数不能为空",groups = {MingruiOperation.Update.class,MingruiOperation.Add.class})
    private String genericSpec;

    @ApiModelProperty(value="规格参数和可选的信息")
    @NotNull(message = "规格参数可选信息不能为空",groups = {MingruiOperation.Add.class,MingruiOperation.Update.class})
    private String specialSpec;

    @ApiModelProperty(value="售后服务")
    private String afterService;
}
