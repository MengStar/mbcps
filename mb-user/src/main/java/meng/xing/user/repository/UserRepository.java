package meng.xing.user.repository;

import meng.xing.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * jpa自动实现接口，默认实现了常见的方法，比如save(),findAll()等等
 * 根据方法名自动操作数据库数据
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
}