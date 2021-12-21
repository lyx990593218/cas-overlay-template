package club.laiyouxu.cas.webflow.valid.service;

/**
 * @Description: 验证service抽象工厂
 * @Author: Kurt Xu
 * @Date: 2021/12/20 10:12
 * @Version: 1.0
 */
public interface ValidServiceAbstractFactory {
    ValidService getValid(String validType);
}
