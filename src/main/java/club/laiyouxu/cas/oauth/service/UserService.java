package club.laiyouxu.cas.oauth.service;

import java.util.Map;

/**
 * Created by mengliang on 2018/12/27.
 */

public interface UserService {
    Map<String, Object> findByUserName(String username);
}
