package meng.xing.common.User;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import meng.xing.common.MbResponse;


import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class ResponseUser extends MbResponse {
    @ApiModelProperty(value = "用户姓名")
    private String username;
    @ApiModelProperty(value = "用户昵称")
    private String nickname;
    @ApiModelProperty(value = "true为主账号，false为子账号")
    private boolean main;
    @ApiModelProperty(value = "主账号姓名，子账号时生效")
    private String mainUsername;
    @ApiModelProperty(value = "用户权限")
    private String role;

    @ApiModelProperty(value = "登录用的token")
    private String token;

    private String password = "";

    @JsonIgnore
    public Boolean isEmpty() {
        return username == null || nickname == null;
    }
    public ResponseUser(String username, String nickname, boolean isMain,String mainUsername, String role, String token){

        this.username = username;
        this.nickname = nickname;
        main = isMain;
        this.mainUsername = mainUsername;
        this.role = role;
        this.token = token;
    }
    public void setCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
