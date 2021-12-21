package club.laiyouxu.cas.custom.handler;

import club.laiyouxu.cas.custom.credential.MyUsernamePasswordCredential;
import club.laiyouxu.cas.custom.exception.UsernameOrPasswordError;
import lombok.extern.slf4j.Slf4j;
import org.apereo.cas.authentication.AuthenticationHandlerExecutionResult;
import org.apereo.cas.authentication.Credential;
import org.apereo.cas.authentication.MessageDescriptor;
import org.apereo.cas.authentication.PreventedException;
import org.apereo.cas.authentication.handler.support.AbstractPreAndPostProcessingAuthenticationHandler;
import org.apereo.cas.authentication.principal.PrincipalFactory;
import org.apereo.cas.services.ServicesManager;

import javax.security.auth.login.FailedLoginException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class CustomUsernamePasswordAuthentication extends AbstractPreAndPostProcessingAuthenticationHandler {

    //UserMapper 为mybatis的 mapper 接口，在构造方法中手动赋值
    public CustomUsernamePasswordAuthentication(String name, ServicesManager servicesManager, PrincipalFactory principalFactory, Integer order) {
        super(name, servicesManager, principalFactory, order);
    }

    //设置只支持验证 MyUsernamePasswordCredential 类型的 Credential
    @Override
    public boolean supports(Credential credential) {

        log.info("=========进不进==========，{}", credential instanceof MyUsernamePasswordCredential);
        return credential instanceof MyUsernamePasswordCredential;

    }

    @Override
    protected AuthenticationHandlerExecutionResult doAuthentication(Credential credential) throws GeneralSecurityException, PreventedException {

        log.info("进来了");
        MyUsernamePasswordCredential myUsernamePasswordCredential = (MyUsernamePasswordCredential) credential;
        log.debug(myUsernamePasswordCredential.getPassword());
        //如果从数据库中根据用户和密码查出的用户id不为空则用户存在
        if (!"laiyx".equals(myUsernamePasswordCredential.getUsername())
                || !"laiyx".equals(myUsernamePasswordCredential.getPassword())) {
            //登录失败抛出异常
            throw new UsernameOrPasswordError();
        }
        String userId = "1234567890";

        //验证code时候正确
        if (userId != null) {
            List<MessageDescriptor> list = new ArrayList<>();
            return createHandlerResult(credential, this.principalFactory.createPrincipal(credential.getId()), list);
        }
        //登录失败抛出异常
        throw new FailedLoginException("登录失败 ");
    }
}
