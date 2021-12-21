package club.laiyouxu.cas.param;

import club.laiyouxu.cas.param.cache.Cache;

/**
 * @Description: 参数管理缓存抽象类
 * @Author: Kurt Xu
 * @Date: 2021/12/21 9:13
 * @Version: 1.0
 */
public abstract class CacheParamManager implements ParamManager {

    /**
     * 缓存存储标识前缀
     * @author Beason Fang
     * @date 2021年9月8日 下午5:13:18
     */
    private final static String PREFIX = "SYS_PARAM_";

    /**
     * 缓存对象
     * @author Beason Fang
     * @date 2021年9月8日 下午5:13:18
     */
    protected Cache cache;

    /**
     * 通过标识加载参数
     * @param key 标识
     * @return 参数
     * @author Beason Fang
     * @date 2021年9月8日 下午5:13:18
     */
    protected abstract Object loadParam(String key);

    public CacheParamManager(Cache cache) {
        this.cache = cache;
    }

    /**
     * 根据标识获取参数
     * @param key 标识
     * @return 参数值
     * @author Beason Fang
     * @date 2021年9月8日 下午5:09:31
     */
    @Override
    public Object get(String key) {
        Object result = cache.get(getRealKey(key));
        if(null==result){
            result = loadParam(key);
            set(key,result);
        }
        return result;
    }

    /**
     * 按照标识缓存参数
     * @param key 标识
     * @param value 参数
     * @author Beason Fang
     * @date 2021年9月8日 下午5:13:18
     */
    public void set(String key,Object value){
        cache.set(getRealKey(key),value);
    }


    /**
     * Get real key string.
     * @param key the key
     * @return the string
     * @author Beason Fang
     * @date 2021年9月14日 上午10:32:48
     */
    protected String getRealKey(String key){
        return PREFIX+key;
    }
}
