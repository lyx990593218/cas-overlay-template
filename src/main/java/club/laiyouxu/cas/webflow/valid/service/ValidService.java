package club.laiyouxu.cas.webflow.valid.service;

import org.apereo.cas.authentication.Credential;

/**
 * @Description: 验证service
 * @Author: Kurt Xu
 * @Date: 2021/12/20 10:10
 * @Version: 1.0
 */
public interface ValidService {
    void valid(Credential credential);
}
