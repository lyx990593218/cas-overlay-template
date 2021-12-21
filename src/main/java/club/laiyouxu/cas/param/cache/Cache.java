package club.laiyouxu.cas.param.cache;


/**
 * 缓存接口
 * @author Beason Fang
 * @date 2021年9月8日 下午4:58:06
 */
public interface Cache {

    /**
     * 按照标识存储值
     * @param key 标识
     * @param value 值
     * @author Beason Fang
     * @date 2021年9月8日 下午4:58:06
     */
    public void set(String key,Object value);

    /**
     * 按照存储key获取值
     * @param key 标识
     * @return 值
     * @author Beason Fang
     * @date 2021年9月8日 下午4:58:06
     */
    public Object get(String key);
}
