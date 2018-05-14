package meng.xing.user.repository;

import meng.xing.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;


/**
 * jpa自动实现接口，默认实现了常见的方法，比如save(),findAll()等等
 * 根据方法名自动操作数据库数据
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "select * from user u where u.main_user_id=?1 ",nativeQuery = true)
    Set<User> findSubUsersByMainUserId(Long mainUserId);

    @Query(value = "select u from User u where u.mainUser =?1")
    Set<User> findSubUsersByMainUser(User mainUser);

    Optional<User> findByUsername(String username);
}