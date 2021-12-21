package club.laiyouxu.cas.webflow.valid.service.Impl;

import club.laiyouxu.cas.exception.ValidTypeError;
import club.laiyouxu.cas.webflow.valid.service.ValidService;
import club.laiyouxu.cas.webflow.valid.service.ValidServiceAbstractFactory;

/**
 * @Description: 验证service工厂
 * @Author: Kurt Xu
 * @Date: 2021/12/20 10:23
 * @Version: 1.0
 */
public class ValidServiceFactory implements ValidServiceAbstractFactory {
    @Override
    public ValidService getValid(String validType) {
        switch (validType){
            case "valid_code": return new ValidCodeService();
            default:throw new ValidTypeError();
        }
    }
}
