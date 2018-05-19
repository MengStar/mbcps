package meng.xing.user.service;

import meng.xing.common.User.RequestNickPass;
import meng.xing.common.User.RequestUser;
import meng.xing.common.User.RoleType;
import meng.xing.user.entity.Role;

import meng.xing.user.entity.User;
import meng.xing.user.repository.UserRepository;
import meng.xing.user.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class CacheUserService implements UserService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    public CacheUserService(UserRepository userRepository, RoleService roleService, JwtTokenUtil jwtTokenUtil) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    public Optional<User> addUser(RequestUser requestUser) {
        boolean isMain = requestUser.isMain();
        String username = requestUser.getUsername();
        String password = requestUser.getPassword();
        String nickname = requestUser.getNickname();
        String mainUsername = requestUser.getMainUsername();
        User user = new User(username, password, nickname, isMain);
        if (isMain) {
            user.setMainUser(null);
            try {
                user = userRepository.save(user);
                return Optional.of(user);
            } catch (Exception e) {
                return Optional.empty();
            }
        } else {
            Optional<User> optionalMainUser = userRepository.findByUsername(mainUsername);
            if (!optionalMainUser.isPresent()) return Optional.empty();
            user.setMainUser(optionalMainUser.get());
            try {
                user = userRepository.save(user);
                return Optional.of(user);
            } catch (Exception e) {
                return Optional.empty();
            }
        }


    }

    @Override
    public Optional<User> updateUser(RequestNickPass requestUser) {

        Optional<User> optionalOldUser = userRepository.findByUsername(requestUser.getUsername());
        if (!optionalOldUser.isPresent())
            return Optional.empty();
        User oldUser = optionalOldUser.get();
        if (!requestUser.getNickname().isEmpty()) oldUser.setNickname(requestUser.getNickname());
        if (!requestUser.getPassword().isEmpty()) {
            oldUser.setLastPasswordResetDate(new Date());
            oldUser.setPassword(requestUser.getPassword());
        }
        return Optional.of(userRepository.save(oldUser));


    }

    @Override
    @Cacheable(value = "user", key = "'detailsByUsername@'+#username")
    public Optional<User> findUser(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<User> findUser(Long id) {
        return userRepository.findById(id);

    }


    @Override
    @Cacheable(value = "user", key = "'subDetailsBymain@'+#mainUsername")
    public Set<User> findSubUsers(String mainUsername) {
        Optional<User> main = userRepository.findByUsername(mainUsername);
        return main.map(userRepository::findSubUsersByMainUser).orElse(new HashSet<>());
    }

    @Override
    public Set<User> findSubUsers(Long mainId) {
        return userRepository.findSubUsersByMainUserId(mainId);
    }


    @Override
    public User setRoles(User user, Set<RoleType> roles) {
        Set<Role> _roles = new HashSet<>();
        for (RoleType role : roles
                ) {
            _roles.add(roleService.findBy(role));
        }
        user.setRoles(_roles);
        return userRepository.save(user);
    }

    @Override
    @Cacheable(value = "user", key = "'isMainByUsername@'+#username")
    public boolean isMain(String username) {
        Optional<User> optionalUser = findUser(username);
        return optionalUser.map(User::isMain).orElse(false);
    }


    @CacheEvict(value = "user", key = "'detailsByUsername@'+#username")
    public void detailsCacheEvict(String username) {
    }

    @CacheEvict(value = "user", key = "'subDetailsBymain@'+#mainUsername")
    public void subDetailsCacheEvict(String mainUsername) {
    }
}
