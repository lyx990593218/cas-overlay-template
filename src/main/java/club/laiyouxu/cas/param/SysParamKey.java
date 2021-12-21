package club.laiyouxu.cas.param;

import lombok.Data;

/**
 * @Description: 系统参数key
 * @Author: Kurt Xu
 * @Date: 2021/12/20 17:23
 * @Version: 1.0
 */
public enum SysParamKey {

    /**
     * PC-WEB端token有效期
     */
    PC_TOKEN_VALID("PCTokenValid"),
    /**
     * App端token有效期
     */
    APP_TOKEN_VALID("AppTokenValid"),
    /**
     * 全局Token有效期
     */
    TGT_TOKEN_VALID("TgtTokenValid"),
    /**
     * 刷新Token有效期
     */
    REFRESH_TOKEN_VALID("RefreshTokenValid"),
    /**
     * 最大错误数
     */
    MAX_ERR_NUM("maxErrNum"),
    /**
     * 登录错误次数失效时间
     */
    MAX_ERR_TIME("maxErrTime"),
    /**
     * 登录最大用户锁时间
     */
    MAX_LOCK_TIME("maxLockTime"),
    /**
     * 登录策略
     */
    LOGIN_STRATEGY("LoginStrategy");

    private String value;

    SysParamKey(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
