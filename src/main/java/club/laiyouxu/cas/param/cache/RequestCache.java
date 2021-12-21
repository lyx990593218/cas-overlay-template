//package club.laiyouxu.cas.param.cache;
//
//import net.evecom.scplatform.support.utils.RequestContextUtil;
//
//import javax.servlet.http.HttpServletRequest;
//
///**
// * 请求级别缓存类
// * @author Beason Fang
// * @date 2021年9月8日 下午4:58:06
// */
//public class RequestCache extends AbstractCache{
//
//    /**
//     * 按照标识存储值
//     * @param key 标识
//     * @param value 值
//     * @author Beason Fang
//     * @date 2021年9月8日 下午4:58:06
//     */
//    @Override
//    public void set(String key, Object value) {
//        HttpServletRequest request = RequestContextUtil.getRequest();
//        if(null!=request){
//            request.setAttribute(key,value);
//        }
//    }
//
//    /**
//     * 按照存储key获取值
//     * @param key 标识
//     * @return 值
//     * @author Beason Fang
//     * @date 2021年9月8日 下午4:58:06
//     */
//    @Override
//    public Object get(String key) {
//        HttpServletRequest request = RequestContextUtil.getRequest();
//        if(null!=request){
//            return request.getAttribute(key);
//        }
//        return null;
//    }
//}
