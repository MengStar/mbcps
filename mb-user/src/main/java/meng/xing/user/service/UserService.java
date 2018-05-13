package meng.xing.user.service;

import meng.xing.user.controller.Meta.RequestUser;
import meng.xing.user.entity.RoleType;
import meng.xing.user.entity.User;

import java.util.Optional;
import java.util.Set;

public interface UserService {
    /**
     * 新增用户
     *
     * @param requestUser 用户实体
     * @return {@code Optional} with user
     */
    Optional<User> addUser(RequestUser requestUser);

    /**
     * 修改用户
     *
     * @param requestUser 用户实体
     * @return {@code Optional} with user
     */
    Optional<User> updateUser(RequestUser requestUser);

    /**
     * 设置用户权限
     *
     * @param id    主键
     * @param roles 权限集合
     * @return {@code user} with roles
     */
    User setRoles(Long id, Set<RoleType> roles);


}
