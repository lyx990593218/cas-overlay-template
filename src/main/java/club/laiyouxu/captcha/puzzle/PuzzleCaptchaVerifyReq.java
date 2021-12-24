package club.laiyouxu.captcha.puzzle;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @Description: 拼图验证码校验请求类
 * @Author: Kurt Xu
 * @Date: 2021/12/6 11:01
 * @Version: 1.0
 */
@Data
//@ApiModel(
//        value = "拼图验证码校验请求类",
//        description = "拼图验证码校验请求类"
//)
public class PuzzleCaptchaVerifyReq {

    //@ApiModelProperty("通道")
    @NotBlank(message = "通道不能为空")
    private String challenge;

    //@ApiModelProperty("x轴偏移量")
    @NotNull(message = "偏移量不能为null")
    @Min(value = 0)
    private int x;

    //@ApiModelProperty("图片宽度")
    @NotNull(message = "图片宽度不能为null")
    private int width;
}
