package meng.xing.common.User;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestNickPass {
    @NotEmpty
    @ApiModelProperty(value = "用户姓名")
    private String username;

    @ApiModelProperty(value = "用户密码,若空则不变")
    private String password;

    @ApiModelProperty(value = "用户昵称,若空则不变")
    private String nickname;
}
