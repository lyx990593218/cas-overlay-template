package club.laiyouxu.cas.oauth.mock;


import club.laiyouxu.cas.oauth.service.UserService;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mengliang on 2018/12/30.
 */
public class MockUserService implements UserService {
    @Override
    public Map<String, Object> findByUserName(String username) {
        HashMap hashMap = new HashMap();
        hashMap.put("username", "laiyx");
        hashMap.put("tel","18600000000");
        hashMap.put("region","china");
        return hashMap;
    }
}
