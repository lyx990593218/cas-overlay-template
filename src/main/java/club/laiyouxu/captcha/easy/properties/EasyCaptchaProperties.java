package club.laiyouxu.captcha.easy.properties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * @Description: easyCaptcha配置文件
 * @Author: Kurt Xu
 * @DCte: 2021/12/22 17:17
 * @Version: 1.0
 */
@Setter
@Getter
@RefreshScope
@ConfigurationProperties(value = EasyCaptchaProperties.PREFIX)
public class EasyCaptchaProperties {
    public static final String PREFIX = "scp.cas.captcha.easy";

    private CaptchaType type = CaptchaType.GIF;
    /**
     * 图片宽度
     */
    private Integer width = 158;
    /**
     * 图片高度
     */
    private Integer height = 58;
    /**
     * 验证码长度
     */
    private Integer len = 4;
    /**
     * 验证码文本类型
     * 1：字母数字混合
     * 2：纯数字
     * 3：纯字母
     * 4：纯大写字母
     * 5：纯小写字母
     * 6：数字大写字母
     */
    private Integer charType = 1;


    @Getter
    @AllArgsConstructor
    public enum CaptchaType {
        /**
         * 动图
         */
        GIF("image/gif"),
        /**
         * png
         */
        SPEC("image/png"),
        /**
         * 中文
         */
        CHINESE("image/png"),
        /**
         * 中文动图
         */
        CHINESE_GIF("image/gif"),
        /**
         * 算数
         */
        ARITHMETIC("image/png"),
        ;

        String contentType;

    }
}
