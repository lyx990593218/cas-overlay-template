package club.laiyouxu.cas.custom.credential;

import org.apereo.cas.authentication.UsernamePasswordCredential;

public class MyUsernamePasswordCredential extends UsernamePasswordCredential {

    public MyUsernamePasswordCredential(String username, String password, String code) {
        super(username, password);
        this.code = code;
    }

    //必须添加空参构造，不然Web Flow无法注入
    public MyUsernamePasswordCredential() {

    }

    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
