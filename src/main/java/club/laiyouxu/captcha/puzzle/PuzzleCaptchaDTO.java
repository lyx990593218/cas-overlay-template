package club.laiyouxu.captcha.puzzle;

import lombok.Data;

/**
 * @Description: 拼图验证码VO
 * @Author: Kurt Xu
 * @Date: 2021/12/6 10:28
 * @Version: 1.0
 */
@Data
//@ApiModel(
//        value = "拼图验证码VO",
//        description = "拼图验证码VO"
//)
public class PuzzleCaptchaDTO {

    //@ApiModelProperty("通道")
    private String challenge;

    //@ApiModelProperty("大图")
    private String bigImage;

    //@ApiModelProperty("小图")
    private String smallImage;
}
