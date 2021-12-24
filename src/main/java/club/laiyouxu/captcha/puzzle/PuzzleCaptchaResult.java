package club.laiyouxu.captcha.puzzle;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description: 拼图验证码校验结果类
 * @Author: Kurt Xu
 * @Date: 2021/12/6 10:34
 * @Version: 1.0
 */
@Data
//@ApiModel(
//        value = "拼图验证码校验结果类",
//        description = "拼图验证码校验结果类"
//)
public class PuzzleCaptchaResult implements Serializable {
    /**
     * 程序序列化ID
     */
    private static final long serialVersionUID = 1L;

    //@ApiModelProperty("是否成功，true-成功，false-失败")
    private boolean success;

    //@ApiModelProperty("信息")
    private String message;

    //@ApiModelProperty("通道")
    private String challenge;

    //@ApiModelProperty("结果id")
    private String validCode;
}
