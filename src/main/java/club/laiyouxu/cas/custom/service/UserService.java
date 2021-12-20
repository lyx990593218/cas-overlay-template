package club.laiyouxu.cas.custom.service;

import java.util.Map;

/**
 * @Description:
 * @Author: Kurt Xu
 * @Date: 2021/12/20 8:58
 * @Version: 1.0
 */
public interface UserService {
    Map<String, Object> findByUserName(String username);
}
