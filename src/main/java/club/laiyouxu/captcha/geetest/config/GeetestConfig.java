/*
 * Copyright (c) 2005, 2019, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package club.laiyouxu.captcha.geetest.config;

import club.laiyouxu.captcha.geetest.utils.HttpClientUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ben Yang
 * @date 2021/6/9 10:46
 * @desc 极验的配置
 * @since 1.0
 */
@Configuration
@EnableScheduling
public class GeetestConfig {
    /**
     * redis中存放的极验云的状态的key
     */
    public static final String REDIS_CHECK_GEETEST_STATUS_KEY = "REDIS_CHECK_GEETEST_STATUS_KEY";

    /**
     * 极验配置属性
     *
     * @author Ben Yang
     * @date 2021/6/9
     */
    @Data
    @Configuration
    @ConfigurationProperties(prefix = "geetest")
    public static class GeetestProperties {
        private String id;
        private String key;
        private String url;
        private boolean enabled = false;
    }

    /**
     * 配置一个定时任务，因为官方文档是这么配置的
     */
    @Configuration
    @ConditionalOnProperty(value = "geetest.enabled")
    @Slf4j
    public static class CheckGeetestStatusTaskConfig implements InitializingBean {
        @Autowired
        private GeetestProperties geetestProperties;
        @Autowired
        private RedisTemplate redisTemplate;

        /**
         * 定时检测云端状态
         *
         * @param
         * @return
         * @author Ben Yang
         * @date 2021/6/24
         */
        @Scheduled(cron = "${geetest.health-check-period}")
        public void checkGeetestStatusTask() {
            checkYumPoint();
        }

        /**
         * 检测云端状态（系统启动的时候检测一下）
         *
         * @param
         * @return
         * @author Ben Yang
         * @date 2021/6/24
         */
        @Override
        public void afterPropertiesSet() throws Exception {
            checkYumPoint();
        }

        /**
         * 检测云端状态
         *
         * @param
         * @return
         * @author Ben Yang
         * @date 2021/6/24
         */
        private void checkYumPoint() {
            String geetest_status = "";
            try {
                Map<String, String> paramMap = new HashMap<String, String>();
                paramMap.put("gt", geetestProperties.getId());
                String resBody = HttpClientUtils.doGet(geetestProperties.getUrl(), paramMap);
                log.info("gtlog: CheckGeetestStatusTask: 极验云监控，返回body={}.",resBody);
                JSONObject jsonObject = new JSONObject(resBody);
                geetest_status = jsonObject.getString("status");
            } catch (Exception e) {
                log.error("调用极验云监控异常", e);
            }
            if (geetest_status != null && !geetest_status.isEmpty()) {
                redisTemplate.opsForValue().set(REDIS_CHECK_GEETEST_STATUS_KEY, geetest_status );
                log.info("gtlog: CheckGeetestStatusTask: 正确获取到状态值, 保存值到redis中");
            } else {
                redisTemplate.opsForValue().set(REDIS_CHECK_GEETEST_STATUS_KEY, "fail");
                log.info("gtlog: CheckGeetestStatusTask: 未获取到状态值, 保存默认值fail到redis中");
            }
        }
    }
}
