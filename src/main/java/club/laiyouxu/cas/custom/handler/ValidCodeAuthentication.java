package club.laiyouxu.cas.custom.handler;

import club.laiyouxu.constants.KeyConstants;
import club.laiyouxu.cas.custom.credential.ValidCodeCredential;
import club.laiyouxu.utils.SM3;
import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.authentication.AuthenticationHandlerExecutionResult;
import org.apereo.cas.authentication.Credential;
import org.apereo.cas.authentication.PreventedException;
import org.apereo.cas.authentication.handler.support.AbstractPreAndPostProcessingAuthenticationHandler;
import org.apereo.cas.authentication.principal.PrincipalFactory;
import org.apereo.cas.services.ServicesManager;
import org.springframework.data.redis.core.RedisTemplate;

import javax.security.auth.login.FailedLoginException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Locale;

/**
 * @Description: 验证码登录方式
 * @Author: Kurt Xu
 * @Date: 2021/12/17 15:03
 * @Version: 1.0
 */
public class ValidCodeAuthentication extends AbstractPreAndPostProcessingAuthenticationHandler {

    private RedisTemplate redisTemplate;

    public ValidCodeAuthentication(String name, ServicesManager servicesManager, PrincipalFactory principalFactory, Integer order, RedisTemplate redisTemplate) {
        super(name, servicesManager, principalFactory, order);
        this.redisTemplate = redisTemplate;
    }

    @Override
    protected AuthenticationHandlerExecutionResult doAuthentication(Credential credential) throws GeneralSecurityException, PreventedException {
        ValidCodeCredential validCodeCredential = (ValidCodeCredential) credential;
        String captcha = validCodeCredential.getValidCode();
        String realCaptcha = (String) redisTemplate.opsForValue().get(KeyConstants.CAPTCHA_KEY + validCodeCredential.getDeviceId());
        redisTemplate.delete(KeyConstants.CAPTCHA_KEY + validCodeCredential.getDeviceId());
        if (StringUtils.isEmpty(captcha) || realCaptcha == null || !(captcha.toLowerCase(Locale.US)).equals(realCaptcha.toLowerCase(Locale.US))){
            throw new FailedLoginException("验证码错误");
        }
        //TODO 获取用户信息
        //User user =
        if ("user" == null || "user.password" == null || !"user.password".equals(SM3.backEncrypt(validCodeCredential.getPassword()))){
            throw new FailedLoginException("用户名或密码错误");
        }

        return createHandlerResult(credential, this.principalFactory.createPrincipal(credential.getId()), new ArrayList<>());
    }

    @Override
    public boolean supports(Credential credential) {
        return credential instanceof ValidCodeCredential;
    }
}
