package club.laiyouxu.cas.param;

import club.laiyouxu.cas.param.cache.Cache;

import java.util.Iterator;
import java.util.Map;

/**
 * @Description: 参数管理实现类 - 一次加载多个参数
 * @Author: Kurt Xu
 * @Date: 2021/12/21 9:14
 * @Version: 1.0
 */
public class MultiParamService extends CacheParamManager{

    public MultiParamService(Cache cache) {
        super(cache);
    }

    /**
     * 通过标识加载参数
     * @param key 标识
     * @return 参数
     * @author Beason Fang
     * @date 2021年9月8日 下午5:13:18
     */
    @Override
    public Object loadParam(String key) {
        //一次加载多个参数
        Map<String, String> map = loadAllParam();
        if(null==map){
            return null;
        }
        Iterator<Map.Entry<String,String>> iterator = map.entrySet().iterator();
        Object result = null;
        //循环存储值
        while(iterator.hasNext()){
            Map.Entry<String,String> entry = iterator.next();
            set(entry.getKey(),entry.getValue());
            if(key.equals(entry.getKey())){
                result = entry.getValue();
            }
        }
        return result;
    }

    /**
     * 加载多次参数
     * @return 多参数对象集合
     * @author Beason Fang
     * @date 2021年9月8日 下午5:18:36
     */
    public Map<String, String> loadAllParam(){
        return null;
    }
}
