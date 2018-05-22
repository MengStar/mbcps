package meng.xing.user.util;

import meng.xing.common.User.ResponseUser;
import meng.xing.common.User.RoleType;
import meng.xing.user.entity.Role;
import meng.xing.user.entity.User;

import java.util.Optional;

public class User2ResponseUser {

    public static ResponseUser transfer(User user, String token, int code, String msg) {
        String nickname = user.getNickname();
        String username = user.getUsername();
        Boolean isMain = user.isMain();
        String mainUsername = user.getMainUser() == null ? null : user.getMainUser().getUsername();

        Optional<String> resultRole = (user.getRoles() != null) ? user.getRoles().stream().map(User2ResponseUser::role2Int).max(Integer::compare).map(User2ResponseUser::int2Role) : Optional.empty();

        ResponseUser ret = new ResponseUser(username, nickname, isMain, mainUsername, resultRole.orElse(""), token);
        ret.setCode(code, msg);
        return ret;
    }

    private static int role2Int(Role role) {
        switch (RoleType.valueOf(role.getRole())) {
            case ROLE_DEFAULT:
                return 0;
            case ROLE_EMPLOY:
                return 1;
            case ROLE_MANAGE:
                return 2;
            case ROLE_ADMIN:
                return 3;
            default:
                return -1;
        }
    }

    private static String int2Role(int level) {
        switch (level) {
            case 0:
                return RoleType.ROLE_DEFAULT.toString();
            case 1:
                return RoleType.ROLE_EMPLOY.toString();
            case 2:
                return RoleType.ROLE_MANAGE.toString();
            case 3:
                return RoleType.ROLE_ADMIN.toString();
            default:
                return "";
        }
    }


}
