package meng.xing.user.service;

import meng.xing.user.controller.Meta.RequestUser;
import meng.xing.user.entity.Role;
import meng.xing.user.entity.RoleType;
import meng.xing.user.entity.User;
import meng.xing.user.repository.RoleRepository;
import meng.xing.user.repository.UserRepository;
import meng.xing.user.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceIml implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    public UserServiceIml(UserRepository userRepository, RoleRepository roleRepository, JwtTokenUtil jwtTokenUtil) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;

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
    public Optional<User> updateUser(RequestUser requestUser) {

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
    public Optional<User> findUser(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<User> findUser(Long id) {
        return userRepository.findById(id);

    }


    @Override
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
            _roles.add(roleRepository.findByRole(role.toString()));
        }
        user.setRoles(_roles);
        return userRepository.save(user);
    }

    @Override
    public String getToken(String username, String password) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (!optionalUser.isPresent()) return null;
        User user = optionalUser.get();
        if (!user.getPassword().equals(password)) return null;
        return jwtTokenUtil.generateToken(username);
    }

    @Override
    public String getToken(User user, String password) {
        if (!user.getPassword().equals(password)) return null;
        return jwtTokenUtil.generateToken(user.getUsername());
    }


    @Override
    public Optional<User> getUserByToken(String token) {
        String username = jwtTokenUtil.getUsernameFromToken(token);
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent() && jwtTokenUtil.validateToken(token, optionalUser.get()))
            return optionalUser;
        return Optional.empty();


    }
}
