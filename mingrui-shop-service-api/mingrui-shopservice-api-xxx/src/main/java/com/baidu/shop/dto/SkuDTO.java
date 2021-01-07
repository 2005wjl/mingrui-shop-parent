package com.baidu.shop.dto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel(value="sku数传输类")
@Data
public class SkuDTO {

    @ApiModelProperty(value="主键ID",example = "1")
    private Long id;

    @ApiModelProperty(value = "spu主键Id",example = "1")
    private Integer spuId;

    @ApiModelProperty(value = "商品标题")
    private String title;

    @ApiModelProperty(value="商品的图片")
    private String image;

    @ApiModelProperty(value="销售价格",example = "1")
    private Integer price;

    @ApiModelProperty(value="规格属性在spu对应的下标")
    private String indexes;

    @ApiModelProperty(value="sku的规格键值对")
    private String ownSpec;

    @ApiModelProperty(value = "是否有效,1有效,0无效",example = "1")
    private Boolean enable;

    @ApiModelProperty(value="创建时间")
    private Date createTime;

    @ApiModelProperty(value="最后的修改时间")
    private Date lastUpdateTime;

    @ApiModelProperty(value="库存")
    private Integer stock;
}
