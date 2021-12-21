package club.laiyouxu.user.mock;

import club.laiyouxu.user.service.UserService;
import club.laiyouxu.utils.SM3;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: TODO
 * @Author: Kurt Xu
 * @Date: 2021/12/20 9:51
 * @Version: 1.0
 */
public class MockUserService implements UserService {
    @Override
    public Map<String, Object> findByUserName(String username) {
        HashMap hashMap = new HashMap();
        hashMap.put("Username", "laiyx");
        hashMap.put("Password", SM3.backEncrypt("laiyx"));
        hashMap.put("tel", "18600000000");
        hashMap.put("region", "china");
        hashMap.put("GmtTenant", "china");
        hashMap.put("isLock","0");
        return hashMap;
    }

    @Override
    public void lockUser(String tenant, String userName) {
        //TODO  做锁定用户操作
    }
}
