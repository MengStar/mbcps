package meng.xing.mbcps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;


@Configuration
public class RedisConfig {
    private final static Logger logger = LoggerFactory.getLogger(RedisConfig.class);
    @Value("${redis.host}")
    String redisHost;
    @Value("${redis.port}")
    Integer redisPort;

    @Bean
    @RefreshScope
    public ReactiveRedisConnectionFactory connectionFactory() {
        return new LettuceConnectionFactory(redisHost, redisPort);
    }

    @Bean
    ReactiveRedisTemplate<String, String> reactiveRedisTemplate(ReactiveRedisConnectionFactory connectionFactory) {
        logger.info("获取ReactiveRedisTemplate：" + redisHost + ":" + redisPort);
        return new ReactiveRedisTemplate<>(connectionFactory, RedisSerializationContext.string());
    }
}
