package club.laiyouxu.cas.param.cache;

import lombok.Setter;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @Description: redis缓存
 * @Author: Kurt Xu
 * @Date: 2021/12/21 9:30
 * @Version: 1.0
 */
@Setter
public class RedisCache extends AbstractCache {

    private RedisTemplate redisTemplate;

    @Override
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }
}
