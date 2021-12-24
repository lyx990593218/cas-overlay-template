package club.laiyouxu.captcha.puzzle.path;

import java.awt.geom.GeneralPath;

/**
 * @Description: 第二种路径
 * @Author: Kurt Xu
 * @Date: 2021/12/14 11:40
 * @Version: 1.0
 */
public class UpperLeftConvexRightConcavePath implements PuzzlePath {
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
        path.append(arc(x + xMoveL, y - yMoveL / 2, xMoveL, yMoveL, 180, -180), true);
        path.lineTo(x + xMoveL * 3, y);
        path.lineTo(x + xMoveL * 3, y + yMoveL);
        path.append(arc(x + xMoveL * 2 + xMoveL / 2, y + yMoveL, xMoveL, yMoveL, 90, 180), true);
        path.lineTo(x + xMoveL * 3, y + yMoveL * 3);
        path.lineTo(x, y + yMoveL * 3);
        path.lineTo(x, y + yMoveL * 2);
        path.append(arc(x - xMoveL / 2, y + yMoveL, xMoveL, yMoveL, -90, -180), true);
        path.lineTo(x, y);
        path.closePath();
        return path;
    }
}
