package net.engineeringdigest.journalApp.Service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class RedisTests {

    @Autowired
    private RedisTemplate redisTemplate;


   // @Disabled
    @Test
    void testSendMail() {
        redisTemplate.opsForValue().set("hey","gm@email.com");
        Object hey = redisTemplate.opsForValue().get("hey");
        int a = 1;
    }
}