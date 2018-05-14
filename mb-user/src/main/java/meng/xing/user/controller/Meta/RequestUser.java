package meng.xing.user.controller.Meta;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestUser {
    @NotEmpty
    @ApiModelProperty(value = "用户姓名")
    private String username;
    @NotEmpty
    @ApiModelProperty(value = "用户密码")
    private String password;
    @NotEmpty
    @ApiModelProperty(value = "用户昵称")
    private String nickname;

    @ApiModelProperty(value = "true为主账号，false为子账号")
    private boolean main;
    @ApiModelProperty(value = "主账号姓名，子账号时生效")
    private String mainUsername;
}
