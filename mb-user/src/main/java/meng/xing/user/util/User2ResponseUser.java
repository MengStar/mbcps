package meng.xing.user.util;

import meng.xing.common.User.ResponseUser;
import meng.xing.common.User.RoleType;
import meng.xing.user.entity.Role;
import meng.xing.user.entity.User;

import java.util.HashSet;
import java.util.Set;

public class User2ResponseUser {
    public static ResponseUser transfer(User user, String token, int code, String msg) {
        String nickname = user.getNickname();
        String username = user.getUsername();
        Boolean isMain = user.isMain();
        String mainUsername = user.getMainUser() == null ? null : user.getMainUser().getUsername();
        Set<RoleType> roles = new HashSet<>();
        Set<Role> _roles = user.getRoles();
        if (_roles != null) {
            for (Role r : _roles
            ) {
                roles.add(RoleType.valueOf(r.getRole()));
            }
        }
        ResponseUser ret = new ResponseUser(username, nickname, isMain, mainUsername, roles, token);
        ret.setCode(code, msg);
        return ret;
    }
}
