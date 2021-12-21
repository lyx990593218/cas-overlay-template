package club.laiyouxu.user.service;

import java.util.Map;

/**
 * @Description: 用户服务
 * @Author: Kurt Xu
 * @Date: 2021/12/20 9:45
 * @Version: 1.0
 */
public interface UserService {
    Map<String, Object> findByUserName(String username);

    void lockUser(String tenant, String userName);
}
