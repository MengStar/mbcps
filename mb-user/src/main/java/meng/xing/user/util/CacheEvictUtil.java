package meng.xing.user.util;

import meng.xing.user.entity.User;
import meng.xing.user.service.CacheTokenService;
import meng.xing.user.service.CacheUserService;
import org.slf4j.Logger;

public class CacheEvictUtil {
    public static void cacheEvict(User user, Logger logger, CacheUserService userService, CacheTokenService tokenService) {
        logger.info("清除details缓存，username:{}", user.getUsername());
        userService.detailsCacheEvict(user.getUsername());
        logger.info("清除token缓存，username:{}", user.getUsername());
        tokenService.tokenCacheEvict(user.getUsername());
        if (user.getMainUser() != null) {
            logger.info("属于子账号，清除对应主账号的子账号缓存，mainUsername:{}", user.getMainUser().getUsername());
            userService.subDetailsCacheEvict(user.getMainUser().getUsername());
        }
    }
}
