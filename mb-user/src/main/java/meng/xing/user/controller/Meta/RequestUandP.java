package meng.xing.user.controller.Meta;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestUandP {
    @NotEmpty
    @ApiModelProperty(value = "用户姓名")
    private String username;
    @NotEmpty
    @ApiModelProperty(value = "用户密码")
    private String password;
}
