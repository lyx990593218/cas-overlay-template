package club.laiyouxu.cas.param;

import club.laiyouxu.cas.param.cache.Cache;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: TODO
 * @Author: Kurt Xu
 * @Date: 2021/12/21 9:16
 * @Version: 1.0
 */
@Setter
@Slf4j
public class SysParamManager extends MultiParamService {

    public SysParamManager(Cache cache) {
        super(cache);
    }

    /**
     * 远程调用参数
     */
    //SysparamRemote sysparamRemote;

    /**
     * 加载多次参数
     *
     * @return 多参数对象集合
     * @author Beason Fang
     * @date 2021年9月8日 下午5:18:36
     */
    @Override
    public Map<String, String> loadAllParam() {
        Map<String, String> configMap = new HashMap<>();
        configMap.put("maxErrNum", "10");
        configMap.put("maxErrTime", "24");
        configMap.put("maxLockTime", "10");
        configMap.put("LoginStrategy", "3");
        configMap.put("PCTokenValid", "30");
        return configMap;
        /*
        String selectedLoginedTenant = RequestContextUtil.getRequest().getParameter("selectedLoginedTenant");
        String tenant = StringUtils.isBlank(selectedLoginedTenant)?"master":selectedLoginedTenant;
        List<String> codes = Lists.newArrayList(
                SysParamKey.PC_TOKEN_VALID,SysParamKey.APP_TOKEN_VALID,
                SysParamKey.REFRESH_TOKEN_VALID,SysParamKey.MAX_ERR_NUM,
                SysParamKey.MAX_ERR_TIME,SysParamKey.MAX_LOCK_TIME,
                SysParamKey.LOGIN_STRATEGY,SysParamKey.TGT_TOKEN_VALID);

        try{
            CommonResp resp = sysparamRemote.findByTenantAndCodes(tenant,codes);
            if(null!=resp&&resp.isSuccess()){
                return  (Map<String, String>) resp.getData();
            }
        }catch (Exception ex){
            log.error("获取系统参数对象失败");
            throw new RuntimeException(ex);
        }
        return null;
         */
    }
}
