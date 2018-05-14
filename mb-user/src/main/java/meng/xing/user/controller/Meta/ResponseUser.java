package meng.xing.user.controller.Meta;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import meng.xing.user.entity.User;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseUser {
    @ApiModelProperty(value = "用户姓名")
    private String username;
    @ApiModelProperty(value = "用户昵称")
    private String nickname;
    @ApiModelProperty(value = "true为主账号，false为子账号")
    private boolean isMain;
    @ApiModelProperty(value = "登录用的token")
    private String token;
    @ApiModelProperty(value = "1：成功，-1：失败")
    private int code;
    @ApiModelProperty(value = "操作描述")
    private String msg;
    @ApiModelProperty(value = "主账号姓名，子账号时生效")
    private String mainUsername;

    public ResponseUser(User user, String token, int code, String msg) {
        username = user.getUsername();
        nickname = user.getNickname();
        isMain = user.isMain();
        mainUsername = user.getMainUser() == null ? null : user.getMainUser().getUsername();
        this.token = token;
        this.code = code;
        this.msg = msg;
    }

    public ResponseUser(RequestUser requestUser, int code, String msg) {
        username = requestUser.getUsername();
        nickname = requestUser.getNickname();
        isMain = requestUser.isMain();
        mainUsername = requestUser.getMainUsername();
        this.code = code;
        this.msg = msg;
    }
}
