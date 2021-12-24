package club.laiyouxu.captcha.puzzle.path;

import java.awt.geom.Arc2D;
import java.awt.geom.GeneralPath;

/**
 * @Description: 拼图路径
 * @Author: Kurt Xu
 * @Date: 2021/12/22 18:59
 * @Version: 1.0
 */
public interface PuzzlePath {

    /**
     * 绘制抠图路径
     * @param xPoint 路径起始x坐标
     * @param yPoint 路径起始y坐标
     * @param xScale x轴放大比例
     * @param yScale y轴放大比例
     * @param vwh 拼图长宽
     * @return GeneralPath
     */
    GeneralPath plan(int xPoint, int yPoint, double xScale, double yScale, int vwh);

    /**
     * 绘制圆形、圆弧或者是椭圆形
     * 正东方向0°，顺时针负数，逆时针正数
     *
     * @param x      左上角的x坐标
     * @param y      左上角的y坐标
     * @param w      宽
     * @param h      高
     * @param start  开始的角度
     * @param extent 角度范围-顺时针负数，逆时针正数
     * @return Arc2D
     */
    default Arc2D arc(double x, double y, double w, double h, double start, double extent) {
        return new Arc2D.Double(x, y, w, h, start, extent, Arc2D.OPEN);
    }
}
