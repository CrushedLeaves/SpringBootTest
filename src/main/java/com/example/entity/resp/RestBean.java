package com.example.entity.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@ApiModel(description = "数据相应实体封装类")
@Data
@AllArgsConstructor
public class RestBean<T> {
    @ApiModelProperty("状态码")
    int code;
    @ApiModelProperty("状态描述信息")
    String reason;
    @ApiModelProperty("响应数据")
    T data;

    public RestBean(int code, String reason) {
        this.code = code;
        this.reason = reason;
    }
}
