package club.laiyouxu.cas.param;

import org.apache.commons.lang3.StringUtils;

/**
 * @Description: 参数管理
 * @Author: Kurt Xu
 * @Date: 2021/12/20 17:28
 * @Version: 1.0
 */
public interface ParamManager {
    /**
     * 根据标识获取参数
     * @param key 标识
     * @return 参数值
     * @author Beason Fang
     * @date 2021年9月8日 下午5:09:31
     */
    public Object get(String key);

    /**
     * 根据标识获取字符串参数
     * @param key 标识
     * @return 字符串参数
     * @author Beason Fang
     * @date 2021年9月8日 下午5:09:31
     */
    public default String getString(String key){
        Object result = get(key);
        if(null!=result){
            return result.toString();
        }
        return null;
    }


    /**
     * 根据标识获取参数
     * @param key 标识
     * @param defaultValue 默认值，如果key值为空时返回
     * @return 参数值
     * @author Beason Fang
     * @date 2021年11月8日 上午10:38:13
     */
    public default String getString(String key,String defaultValue){
        String result = getString(key);
        if(null!=result){
            return result;
        }
        return defaultValue;
    }

    /**
     * 根据标识获取整形参数
     * @param key 标识
     * @return 整形参数
     * @author Beason Fang
     * @date 2021年9月8日 下午5:09:31
     */
    public default int getIntValue(String key){
        Integer result = getInteger(key);
        if(null!=result){
            return result.intValue();
        }
        return 0;
    }

    /**
     * 根据标识获取整形参数
     * @param key 标识
     * @return 整形参数
     * @author Beason Fang
     * @date 2021年9月8日 下午5:09:31
     */
    public default Integer getInteger(String key){
        String result = getString(key);
        if(StringUtils.isNoneBlank(result)) {
            return Integer.valueOf(result);
        }
        return null;
    }

    /**
     * 根据标识获取长整形参数
     * @param key 标识
     * @return 长整形参数
     * @author Beason Fang
     * @date 2021年9月8日 下午5:09:31
     */
    public default long getLongValue(String key){
        return getLongValue(key,0);
    }

    /**
     * 根据标识获取长整形参数,不存在时返回默认值
     * @param key 标识
     * @param defaultValue 默认值
     * @return 长整形参数
     * @author Beason Fang
     * @date 2021年9月8日 下午5:09:31
     */
    public default long getLongValue(String key,long defaultValue){
        Long result = getLong(key);
        if(null!=result){
            return result.longValue();
        }
        return defaultValue;
    }

    /**
     * 根据标识获取长整形参数
     * @param key 标识
     * @return 长整形参数
     * @author Beason Fang
     * @date 2021年9月8日 下午5:09:31
     */
    public default Long getLong(String key){
        String result = getString(key);
        if(StringUtils.isNoneBlank(result)) {
            return Long.valueOf(result);
        }
        return null;
    }
}
