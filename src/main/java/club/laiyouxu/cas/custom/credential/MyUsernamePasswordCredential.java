package club.laiyouxu.cas.custom.credential;

import lombok.Data;
import org.apereo.cas.authentication.UsernamePasswordCredential;

@Data
public class MyUsernamePasswordCredential extends UsernamePasswordCredential {

    private String validCode;

    private String deviceId;

    private String loginMethod;

    public MyUsernamePasswordCredential(String username, String password, String validCode,
                                        String loginMethod,String deviceId) {
        super(username, password);
        this.validCode = validCode;
        this.deviceId = deviceId;
        this.loginMethod = loginMethod;
    }

    public MyUsernamePasswordCredential(String username, String password, String validCode, String loginMethod) {
        super(username, password);
        this.validCode = validCode;
        this.loginMethod = loginMethod;
    }

    //必须添加空参构造，不然Web Flow无法注入
    public MyUsernamePasswordCredential() {

    }
}
