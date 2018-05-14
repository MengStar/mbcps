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
     * 只会修改{@code password}和{@code nickname}
     *
     * @param requestUser 用户实体
     * @return {@code Optional} with user
     */
    Optional<User> updateUser(RequestUser requestUser);

    /**
     * 返回用户
     *
     * @param username 姓名
     * @return user
     */
    Optional<User> findUser(String username);

    /**
     * 返回用户
     *
     * @param id 主键
     * @return user
     */
    Optional<User> findUser(Long id);

    /**
     * 返回子账号
     *
     * @param mainUsername 主账号姓名
     * @return Set with user
     */
    Set<User> findSubUsers(String mainUsername);

    /**
     * 返回子账号
     *
     * @param mainId user
     * @return Set with user
     */
    Set<User> findSubUsers(Long mainId);

    /**
     * 设置用户权限
     *
     * @param user   用户
     * @param roles 权限集合
     * @return {@code user} with roles
     */
    User setRoles(User user, Set<RoleType> roles);


}
