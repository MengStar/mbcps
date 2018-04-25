package meng.xing.mbcps;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

public class RedisConfig {
    @Value("${redis.host}")
    String redisHost;
    @Value("${redis.port}")
    Integer redisPort;
    @Bean
    @RefreshScope
    public ReactiveRedisConnectionFactory connectionFactory() {
        return new LettuceConnectionFactory(redisHost, redisPort);
    }
}
