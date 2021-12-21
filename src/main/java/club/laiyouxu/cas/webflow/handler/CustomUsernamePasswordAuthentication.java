package club.laiyouxu.cas.webflow.handler;

import club.laiyouxu.cas.webflow.credential.CustomUsernamePasswordCredential;
import club.laiyouxu.cas.exception.UsernameOrPasswordError;
import club.laiyouxu.cas.param.ParamManager;
import club.laiyouxu.cas.param.SysParamKey;
import club.laiyouxu.user.service.UserService;
import club.laiyouxu.utils.SM3;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.authentication.AuthenticationHandlerExecutionResult;
import org.apereo.cas.authentication.Credential;
import org.apereo.cas.authentication.PreventedException;
import org.apereo.cas.authentication.handler.support.AbstractPreAndPostProcessingAuthenticationHandler;
import org.apereo.cas.authentication.principal.PrincipalFactory;
import org.apereo.cas.services.ServicesManager;
import org.springframework.data.redis.core.RedisTemplate;

import javax.security.auth.login.AccountLockedException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
public class CustomUsernamePasswordAuthentication extends AbstractPreAndPostProcessingAuthenticationHandler {

    private RedisTemplate redisTemplate;

    private UserService userService;

    private ParamManager paramManager;

    //UserMapper 为mybatis的 mapper 接口，在构造方法中手动赋值
    public CustomUsernamePasswordAuthentication(String name, ServicesManager servicesManager, PrincipalFactory principalFactory, Integer order,
                                                RedisTemplate redisTemplate,
                                                UserService userService,
                                                ParamManager paramManager) {
        super(name, servicesManager, principalFactory, order);
        this.redisTemplate = redisTemplate;
        this.userService = userService;
        this.paramManager = paramManager;
    }

    //设置只支持验证 CustomUsernamePasswordCredential 类型的 Credential
    @Override
    public boolean supports(Credential credential) {
        return credential instanceof CustomUsernamePasswordCredential;
    }

    @Override
    protected AuthenticationHandlerExecutionResult doAuthentication(Credential credential)
            throws GeneralSecurityException, PreventedException {

        CustomUsernamePasswordCredential customUsernamePasswordCredential
                = (CustomUsernamePasswordCredential) credential;
        //如果从数据库中根据用户和密码查出的用户id不为空则用户存在
        Map<String, Object> user = userService.findByUserName(customUsernamePasswordCredential.getUsername());
        if (user == null) {
            throw new UsernameOrPasswordError();
        }

        if (user.get("isLock") == "1") {
            //此处抛出禁用或者锁定异常有争议
            throw new AccountLockedException();
        }

        int maxErrNum = paramManager.getIntValue(SysParamKey.MAX_ERR_NUM.getValue());
        int maxErrTime = paramManager.getIntValue(SysParamKey.MAX_ERR_TIME.getValue());
        String maxLockTime = paramManager.getString(SysParamKey.MAX_LOCK_TIME.getValue());
        String redisKey = user.get("GmtTenant") + ":" + user.get("Username");
        Object limit = redisTemplate.opsForValue().get(redisKey);

        if (limit != null && Integer.parseInt(limit.toString()) >= maxErrNum) {
            if (StringUtils.isNotBlank(maxLockTime) && Integer.parseInt(maxLockTime) > 0) {
                redisTemplate.opsForValue().set("Lock:" + redisKey, limit
                        , Integer.parseInt(maxLockTime), TimeUnit.MINUTES);
            } else {
                userService.lockUser(user.get("GmtTenant").toString(), user.get("Username").toString());
            }
        }

        if (!SM3.backEncrypt(customUsernamePasswordCredential.getPassword()).equals(user.get("Password"))) {
            int num = 1;
            if (limit != null) {
                num += Integer.parseInt(limit.toString());
            }
            redisTemplate.opsForValue().set(redisKey, num, maxErrTime, TimeUnit.HOURS);
            throw new UsernameOrPasswordError();
        }

        //boolean strategy = tokenService
        //        .setLoginStrategy(user.getGmtTenant(), user.getUsername(), HttpUtil.getClientIp(request));
        //if (!strategy) {
        //    setErrorMsg(request, response, "该账号正在使用中，不允许重复登录");
        //    return false;
        //}
        return createHandlerResult(credential, this.principalFactory.createPrincipal(credential.getId()), new ArrayList<>(0));
    }
}
