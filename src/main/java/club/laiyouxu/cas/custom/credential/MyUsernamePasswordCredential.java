package club.laiyouxu.cas.custom.credential;

import lombok.Getter;
import org.apereo.cas.authentication.UsernamePasswordCredential;

//@Getter
public class MyUsernamePasswordCredential extends UsernamePasswordCredential {

//    private static final long serialVersionUID = -700605081472810039L;

//    private String recaptcha;

    public MyUsernamePasswordCredential(String username, String password
//            , String recaptcha
    ) {
        super(username, password);

//        this.recaptcha = recaptcha;
    }

    //必须添加空参构造，不然Web Flow无法注入
    public MyUsernamePasswordCredential() {

    }
}
