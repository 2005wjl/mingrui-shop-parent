package com.baidu.shop.dto;

import com.baidu.shop.validate.group.MingruiOperation;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@ApiModel(value="sku数传输类")
@Data
public class SkuDTO {

    @ApiModelProperty(value="主键ID",example = "1")
    @NotNull(message = "主键id不能为空",groups = {MingruiOperation.Update.class})
    private Long id;

    @ApiModelProperty(value = "spu主键Id",example = "1")
    @NotNull(message = "spuId不能为空",groups = {MingruiOperation.Update.class})
    private Integer spuId;

    @ApiModelProperty(value = "商品标题")
    @NotEmpty(message = "商品标题不能为空",groups = {MingruiOperation.Add.class,MingruiOperation.Update.class})
    private String title;

    @ApiModelProperty(value="商品的图片")
    private String images;

    @ApiModelProperty(value="销售价格",example = "1")
    @NotNull(message = "销售价格不能为空",groups = {MingruiOperation.Add.class,MingruiOperation.Update.class})
    private Integer price;

    @ApiModelProperty(value="规格属性在spu对应的下标")
    private String indexes;

    @ApiModelProperty(value="sku的规格键值对")
    private String ownSpec;

    @ApiModelProperty(value = "是否有效",example = "1")
    @NotNull(message = "是否有效不能为空",groups = {MingruiOperation.Add.class,MingruiOperation.Update.class})
    private Boolean enable;

    @ApiModelProperty(value="创建时间")
    @NotNull(message = "创建时间不能为空",groups = MingruiOperation.Add.class)
    private Date createTime;

    @ApiModelProperty(value="最后的修改时间")
    @NotNull(message = "最后的修改时间不能为空",groups = {MingruiOperation.Update.class})
    private Date lastUpdateTime;

    @ApiModelProperty(value="库存")
    private Integer stock;
}
