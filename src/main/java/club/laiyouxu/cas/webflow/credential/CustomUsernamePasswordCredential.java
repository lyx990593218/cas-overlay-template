package club.laiyouxu.cas.webflow.credential;

import org.apereo.cas.authentication.UsernamePasswordCredential;

public class CustomUsernamePasswordCredential extends UsernamePasswordCredential {

    public CustomUsernamePasswordCredential(String username, String password) {
        super(username, password);
    }

    //必须添加空参构造，不然Web Flow无法注入
    public CustomUsernamePasswordCredential() {

    }

}
