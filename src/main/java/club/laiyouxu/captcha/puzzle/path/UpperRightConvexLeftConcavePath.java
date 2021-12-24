package club.laiyouxu.captcha.puzzle.path;

import java.awt.geom.GeneralPath;

/**
 * @Description: 第一种路径
 * @Author: Kurt Xu
 * @Date: 2021/12/9 15:55
 * @Version: 1.0
 */
public class UpperRightConvexLeftConcavePath implements PuzzlePath {
    @Override
    public GeneralPath plan(int xPoint, int yPoint, double xScale, double yScale, int vwh) {
        double x = xPoint * xScale;
        double y = yPoint * yScale;
        // 直线移动的基础距离
        double yMoveL = vwh / 3f * yScale;
        double xMoveL = vwh / 3f * xScale;
        GeneralPath path = new GeneralPath();
        path.moveTo(x, y);
        path.lineTo(x + xMoveL, y);
        // 上面的圆弧正东方向0°，extent-顺时针负数，逆时针正数
        path.append(arc(x + xMoveL, y - yMoveL / 2, xMoveL, yMoveL, 180, -180), true);
        path.lineTo(x + xMoveL * 3, y);
        path.lineTo(x + xMoveL * 3, y + yMoveL);
        // 右边的圆弧
        path.append(arc(x + xMoveL * 2 + xMoveL / 2, y + yMoveL, xMoveL, yMoveL, 90, -180), true);
        path.lineTo(x + xMoveL * 3, y + yMoveL * 3);
        path.lineTo(x, y + yMoveL * 3);
        path.lineTo(x, y + yMoveL * 2);
        // 左边的内圆弧
        path.append(arc(x - xMoveL / 2, y + yMoveL, xMoveL, yMoveL, -90, 180), true);
        path.lineTo(x, y);
        path.closePath();
        return path;
    }
}
