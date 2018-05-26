//package meng.xing.mbcps.service;
//
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.redis.core.ReactiveRedisTemplate;
//import org.springframework.test.context.junit4.SpringRunner;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class RedisTest {
//    @Autowired
//    private ReactiveRedisTemplate<String, String> template;
//    private final static Logger LOGGER = LoggerFactory.getLogger(RedisTest.class);
//
//    @Test
//    public void testRedis() {
//        if (LOGGER.isInfoEnabled()) {
//            LOGGER.info("开始测试非阻塞的redis");
//            LOGGER.info("发送字符串...");
//        }
//        template.opsForList().leftPush("test", "redisTestString").log().subscribe();
//        if (LOGGER.isInfoEnabled()) {
//            LOGGER.info("字符串数量：" + template.opsForList().size("test").block()); //因为是测试，采用阻塞的方法
//        }
//    }
//}
