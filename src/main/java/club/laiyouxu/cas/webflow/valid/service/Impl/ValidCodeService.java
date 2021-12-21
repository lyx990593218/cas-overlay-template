package club.laiyouxu.cas.webflow.valid.service.Impl;

import club.laiyouxu.cas.webflow.valid.service.ValidService;
import org.apereo.cas.authentication.Credential;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @Description: 验证码校验service
 * @Author: Kurt Xu
 * @Date: 2021/12/20 10:27
 * @Version: 1.0
 */
public class ValidCodeService implements ValidService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void valid(Credential credential) {
//        MyUsernamePasswordCredential myCredential = (MyUsernamePasswordCredential) credential;
//        String captcha = (String) redisTemplate.opsForValue().get(KeyConstants.CAPTCHA_KEY + myCredential.getDeviceId());
//        redisTemplate.delete(KeyConstants.CAPTCHA_KEY + myCredential.getDeviceId());
//        if (StringUtils.isBlank(myCredential.getValidCode()) || !myCredential.getValidCode().equals(captcha)) {
//            throw new ValidCodeError();
//        }
    }
}
