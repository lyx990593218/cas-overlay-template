package club.laiyouxu.cas.param.config;

import club.laiyouxu.cas.param.ParamManager;
import club.laiyouxu.cas.param.SysParamManager;
import club.laiyouxu.cas.param.cache.Cache;
import club.laiyouxu.cas.param.cache.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @Description: 参数管理配置
 * @Author: Kurt Xu
 * @Date: 2021/12/21 9:03
 * @Version: 1.0
 */
public class ParamManagerConfig {

    @Autowired
    private RedisTemplate redisTemplate;

    @Bean
    @ConditionalOnMissingBean(name = "defaultCache")
    public Cache defaultCache() {
        RedisCache redisCache = new RedisCache();
        redisCache.setRedisTemplate(redisTemplate);
        return redisCache;
    }

    @Bean
    @ConditionalOnMissingBean(name = "paramManager")
    public ParamManager paramManager(@Qualifier("defaultCache") Cache cache) {
        SysParamManager paramManager = new SysParamManager(cache);
        //paramManager.setSysparamRemote(sysparamRemote);
        return paramManager;
    }
}
