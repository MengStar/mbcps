package meng.xing.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public  class MbResponse {

    @ApiModelProperty(value = "1：成功，-1：失败")
    protected int code;
    @ApiModelProperty(value = "操作描述")
    protected String msg;
}
